/**
 * Copyright (C) 2016 Fernando Berti - Daniel Campodonico - Emiliano Gioria - Lucas Moretti - Esteban Rebechi - Andres Leonel Rico
 * This file is part of Olimpo.
 *
 * Olimpo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Olimpo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Olimpo. If not, see <http://www.gnu.org/licenses/>.
 */
package app.logica.gestores;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.FutureTask;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

import app.comun.ConversorFechas;
import app.comun.FormateadorString;
import app.datos.clases.CatalogoVista;
import app.datos.entidades.Inmueble;
import app.datos.entidades.PDF;
import app.datos.entidades.Reserva;
import app.datos.entidades.Venta;
import app.excepciones.GenerarPDFException;
import app.excepciones.GestionException;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

@Service
/**
 * Gestor que implementa la generación de PDFs a partir de un objeto dado.
 */
public class GestorPDF {

	@Resource
	protected FormateadorString formateador;

	@Resource
	protected ConversorFechas conversorFechas;

	private PDF pdf;

	private static final String URLDocumentoReserva = "/res/pdf/documentoReserva.fxml";

	private static final String URLDocumentoVenta = "/res/pdf/documentoVenta.fxml";

	private static final String URLCatalogo = "/res/pdf/catalogoA4.fxml";

	private static final String URLFilaCatalogo = "/res/pdf/filaCatalogoA4.fxml";

	/**
	 * Método para crear un PDF a partir de una pantalla.
	 *
	 * @param pantallaAPDF
	 *            pantalla que se imprimirá en PDF
	 * @return PDF de una captura de la pantalla pasada
	 */
	private PDF generarPDF(Node pantallaAPDF) throws Exception {
		new Scene((Parent) pantallaAPDF);
		WritableImage image = pantallaAPDF.snapshot(new SnapshotParameters(), null);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", baos);
		byte[] imageInByte = baos.toByteArray();
		baos.flush();
		baos.close();
		Image imagen = Image.getInstance(imageInByte);
		Document document = new Document();
		ByteArrayOutputStream pdfbaos = new ByteArrayOutputStream();
		PdfWriter escritor = PdfWriter.getInstance(document, pdfbaos);
		document.open();
		imagen.setAbsolutePosition(0, 0);
		imagen.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
		document.add(imagen);
		document.close();
		byte[] pdfBytes = pdfbaos.toByteArray();
		pdfbaos.flush();
		escritor.close();
		pdfbaos.close();
		return (PDF) new PDF().setArchivo(pdfBytes);
	}

	/**
	 * Método para crear un PDF a partir de varias pantalla.
	 *
	 * @param pantallaAPDF
	 *            pantalla que se imprimirá en PDF
	 * @return PDF de una captura de la pantalla pasada
	 */
	private PDF generarPDF(ArrayList<Node> pantallasAPDF) throws Exception {
		Document document = new Document();
		ByteArrayOutputStream pdfbaos = new ByteArrayOutputStream();
		PdfWriter escritor = PdfWriter.getInstance(document, pdfbaos);
		document.open();

		for(Node pantalla: pantallasAPDF){
			new Scene((Parent) pantalla);
			WritableImage image = pantalla.snapshot(new SnapshotParameters(), null);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", baos);
			byte[] imageInByte = baos.toByteArray();
			baos.flush();
			baos.close();
			Image imagen = Image.getInstance(imageInByte);
			imagen.setAbsolutePosition(0, 0);
			imagen.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
			document.add(imagen);
			document.newPage();
		}

		document.close();

		byte[] pdfBytes = pdfbaos.toByteArray();
		pdfbaos.flush();
		escritor.close();
		pdfbaos.close();
		return (PDF) new PDF().setArchivo(pdfBytes);
	}

	/**
	 * Método para crear un PDF de un catalogo a partir de los datos de un CatalogoVista.
	 * Pertenece a la taskcard 23 de la iteración 2 y a la historia 5
	 *
	 * @param catalogo
	 *            datos que se utilizaran para generar el PDF de un catálogo
	 * @return catalogo en PDF
	 */
	public PDF generarPDF(CatalogoVista catalogo) throws GestionException {
		pdf = null;
		Integer numeroInmuebles = catalogo.getFotos().size();
		ArrayList<Node> paginas = new ArrayList<>();
		Integer numeroTotalDePaginas = (numeroInmuebles + 2) / 3;
		Date fechaHoy = new Date();

		ArrayList<Inmueble> inmuebles = new ArrayList<>();
		catalogo.getFotos().forEach((i, f) -> {
			inmuebles.add(i);
		});

		try{
			FutureTask<Throwable> future = new FutureTask<>(() -> {
				try{
					Integer inmueblesProcesados = 0;
					for(int numeroPagina = 1; numeroPagina <= numeroTotalDePaginas; numeroPagina++){
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(getClass().getResource(URLCatalogo));
						Pane paginaCatalogo = (Pane) loader.load();

						Label label = (Label) paginaCatalogo.lookup("#labelFechaEmision");
						label.setText(formateador.nombrePropio(conversorFechas.diaMesYAnioToString(fechaHoy)));
						label = (Label) paginaCatalogo.lookup("#labelNumeroPagina");
						label.setText(numeroPagina + " de " + numeroTotalDePaginas);

						for(int i = 0; i < 3 && inmueblesProcesados < numeroInmuebles; i++, inmueblesProcesados++){
							FXMLLoader loaderFila = new FXMLLoader();
							loaderFila.setLocation(getClass().getResource(URLFilaCatalogo));
							Pane fila = (Pane) loaderFila.load();
							Inmueble inmueble = inmuebles.get(inmueblesProcesados);
							if(catalogo.getFotos().get(inmueble) != null){
								File imagenTMP = new File("imagen_tmp.png");
								FileOutputStream fos = new FileOutputStream(imagenTMP);
								fos.write(catalogo.getFotos().get(inmueble).getArchivo());
								fos.flush();
								fos.close();

								ImageView imagen = (ImageView) fila.lookup("#imageFoto");
								imagen.setImage(new javafx.scene.image.Image(imagenTMP.toURI().toString()));
							}

							label = (Label) fila.lookup("#labelCodigo");
							label.setText("Inmueble Nº " + inmueble.getId());

							label = (Label) fila.lookup("#labelTipoInmueble");
							label.setText(inmueble.getTipo().toString());

							label = (Label) fila.lookup("#labelPais");
							label.setText(formateador.nombrePropio(inmueble.getDireccion().getLocalidad().getProvincia().getPais().toString()));

							label = (Label) fila.lookup("#labelProvincia");
							label.setText(formateador.nombrePropio(inmueble.getDireccion().getLocalidad().getProvincia().toString()));

							label = (Label) fila.lookup("#labelLocalidad");
							label.setText(formateador.nombrePropio(inmueble.getDireccion().getLocalidad().toString()));

							label = (Label) fila.lookup("#labelBarrio");
							label.setText(formateador.nombrePropio(inmueble.getDireccion().getBarrio().toString()));

							StringBuilder direccion = new StringBuilder("");
							direccion.append(inmueble.getDireccion().getCalle());
							direccion.append(" ");
							direccion.append(inmueble.getDireccion().getNumero());

							if(inmueble.getDireccion().getPiso() != null){
								direccion.append(" - Piso ");
								direccion.append(inmueble.getDireccion().getPiso());
							}
							if(inmueble.getDireccion().getDepartamento() != null){
								direccion.append(" - Dpto. ");
								direccion.append(inmueble.getDireccion().getDepartamento());
							}
							if(inmueble.getDireccion().getOtros() != null){
								direccion.append(" - ");
								direccion.append(inmueble.getDireccion().getOtros());
							}
							label = (Label) fila.lookup("#labelDireccion");
							label.setText(formateador.nombrePropio(direccion.toString()));

							label = (Label) fila.lookup("#labelDormitorios");
							if(inmueble.getDatosEdificio().getDormitorios() != null){
								label.setText(inmueble.getDatosEdificio().getDormitorios().toString());
							}
							else{
								label.setText("-");
							}

							label = (Label) fila.lookup("#labelBaños");
							if(inmueble.getDatosEdificio().getBaños() != null){
								label.setText(inmueble.getDatosEdificio().getBaños().toString());
							}
							else{
								label.setText("-");
							}

							label = (Label) fila.lookup("#labelGaraje");
							if(inmueble.getDatosEdificio().getGaraje() != null){
								label.setText(inmueble.getDatosEdificio().getGaraje() ? "SI" : "NO");
							}
							else{
								label.setText("-");
							}

							label = (Label) fila.lookup("#labelPatio");
							if(inmueble.getDatosEdificio().getPatio() != null){
								label.setText(inmueble.getDatosEdificio().getPatio() ? "SI" : "NO");
							}
							else{
								label.setText("-");
							}

							label = (Label) fila.lookup("#labelSuperficieTerreno");
							if(inmueble.getSuperficie() != null){
								label.setText(inmueble.getSuperficie() + " metros cuadrados.");
							}
							else{
								label.setText("-");
							}

							label = (Label) fila.lookup("#labelSuperficieEdificada");
							if(inmueble.getDatosEdificio().getSuperficie() != null){
								label.setText(inmueble.getDatosEdificio().getSuperficie() + " metros cuadrados.");
							}
							else{
								label.setText("-");
							}

							label = (Label) fila.lookup("#labelPrecio");
							DecimalFormat formateadorDouble = new DecimalFormat("#.00");
							label.setText(formateadorDouble.format(inmueble.getPrecio()) + " USD");

							GridPane gridPaneFilas = (GridPane) paginaCatalogo.lookup("#gridPaneFilas");
							gridPaneFilas.add(fila, 0, i);
						}
						paginas.add(paginaCatalogo);
					}
					pdf = generarPDF(paginas);
				} catch(Throwable e){
					return e;
				}
				return null;
			});

			if(!Platform.isFxApplicationThread()){
				Platform.runLater(future);
			}
			else{
				future.run();
			}
			Throwable excepcion = future.get();
			if(excepcion != null){
				throw excepcion;
			}
			if(pdf == null){
				throw new NullPointerException("Error al generar PDF");
			}

		} catch(Throwable e){
			e.printStackTrace();
			throw new GenerarPDFException(e);
		}

		return pdf;
	}

	/**
	 * Método para crear un PDF de una reserva a partir de los datos de una ReservaVista.
	 * Pertenece a la taskcard 25 de la iteración 2 y a la historia 7
	 *
	 * @param reserva
	 *            datos que se utilizaran para generar el PDF de una reserva
	 * @return reserva en PDF
	 */
	public PDF generarPDF(Reserva reserva) throws GestionException {
		try{
			pdf = null;
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource(URLDocumentoReserva));
			Pane documentoReserva = (Pane) loader.load();

			FutureTask<Throwable> future = new FutureTask<>(() -> {
				try{
					Label label = (Label) documentoReserva.lookup("#lblNombreOferente");
					label.setText(formateador.nombrePropio(reserva.getCliente().getNombre()));
					label = (Label) documentoReserva.lookup("#lblApellidoOferente");
					label.setText(formateador.nombrePropio(reserva.getCliente().getApellido()));
					label = (Label) documentoReserva.lookup("#lblDocumentoOferente");
					label.setText(reserva.getCliente().getTipoDocumento() + " - " + reserva.getCliente().getNumeroDocumento());
					label = (Label) documentoReserva.lookup("#lblNombrePropietario");
					label.setText(formateador.nombrePropio(reserva.getInmueble().getPropietario().getNombre()));
					label = (Label) documentoReserva.lookup("#lblApellidoPropietario");
					label.setText(formateador.nombrePropio(reserva.getInmueble().getPropietario().getApellido()));
					label = (Label) documentoReserva.lookup("#lblCodigoInmueble");
					label.setText(Integer.toString(reserva.getInmueble().getId()));
					label = (Label) documentoReserva.lookup("#lblTipoInmueble");
					label.setText(reserva.getInmueble().getTipo().getTipo().toString());
					label = (Label) documentoReserva.lookup("#lblLocalidadInmueble");
					label.setText(reserva.getInmueble().getDireccion().getLocalidad().toString());
					label = (Label) documentoReserva.lookup("#lblBarrioInmueble");
					label.setText(reserva.getInmueble().getDireccion().getBarrio().toString());
					label = (Label) documentoReserva.lookup("#lblCalleInmueble");
					label.setText(reserva.getInmueble().getDireccion().getCalle().toString());
					label = (Label) documentoReserva.lookup("#lblAlturaInmueble");
					label.setText(reserva.getInmueble().getDireccion().getNumero());
					label = (Label) documentoReserva.lookup("#lblPisoInmueble");
					label.setText(reserva.getInmueble().getDireccion().getPiso());
					label = (Label) documentoReserva.lookup("#lblDepartamentoInmueble");
					label.setText(reserva.getInmueble().getDireccion().getDepartamento());
					label = (Label) documentoReserva.lookup("#lblOtrosInmueble");
					label.setText(reserva.getInmueble().getDireccion().getOtros());
					label = (Label) documentoReserva.lookup("#lblImporte");
					label.setText(String.format("$ %10.2f", reserva.getImporte()));
					label = (Label) documentoReserva.lookup("#lblFechaRealizacion");
					label.setText(conversorFechas.diaMesYAnioToString(reserva.getFechaInicio()));
					label = (Label) documentoReserva.lookup("#lblFechaVencimiento");
					label.setText(conversorFechas.diaMesYAnioToString(reserva.getFechaFin()));
					label = (Label) documentoReserva.lookup("#lblHoraGenerado");
					Date ahora = new Date();
					label.setText(String.format(label.getText(), conversorFechas.horaYMinutosToString(ahora), conversorFechas.diaMesYAnioToString(ahora)));

					pdf = generarPDF(documentoReserva);
				} catch(Throwable e){
					return e;
				}
				return null;
			});

			if(!Platform.isFxApplicationThread()){
				Platform.runLater(future);
			}
			else{
				future.run();
			}
			Throwable excepcion = future.get();
			if(excepcion != null){
				throw excepcion;
			}
			if(pdf == null){
				throw new NullPointerException("Error al generar PDF");
			}
		} catch(Throwable e){
			throw new GenerarPDFException(e);
		}

		return pdf;
	}

	/**
	 * Método para crear un PDF de una venta a partir de los datos de una Venta.
	 * Pertenece a la taskcard 30 de la iteración 2 y a la historia 8
	 *
	 * @param venta
	 *            datos que se utilizaran para generar el PDF de una venta
	 * @return venta en PDF
	 */
	public PDF generarPDF(Venta venta) throws GestionException {
		try{
			//se carga el fxml
			pdf = null;
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource(URLDocumentoVenta));
			Pane documentoVenta = (Pane) loader.load();

			FutureTask<Throwable> future = new FutureTask<>(() -> {
				try{
					//se setean los campos del documento con los datos de la venta
					Label label = (Label) documentoVenta.lookup("#lblNombreComprador");
					label.setText(formateador.nombrePropio(venta.getCliente().getNombre()));
					label = (Label) documentoVenta.lookup("#lblApellidoComprador");
					label.setText(formateador.nombrePropio(venta.getCliente().getApellido()));
					label = (Label) documentoVenta.lookup("#lblDocumentoComprador");
					label.setText(venta.getCliente().getTipoDocumento() + " - " + venta.getCliente().getNumeroDocumento());
					label = (Label) documentoVenta.lookup("#lblNombrePropietario");
					label.setText(formateador.nombrePropio(venta.getInmueble().getPropietario().getNombre()));
					label = (Label) documentoVenta.lookup("#lblApellidoPropietario");
					label.setText(formateador.nombrePropio(venta.getInmueble().getPropietario().getApellido()));
					label = (Label) documentoVenta.lookup("#lblDocumentoPropietario");
					label.setText(venta.getPropietario().getTipoDocumento() + " - " + venta.getPropietario().getNumeroDocumento());
					label = (Label) documentoVenta.lookup("#lblCodigoInmueble");
					label.setText(Integer.toString(venta.getInmueble().getId()));
					label = (Label) documentoVenta.lookup("#lblTipoInmueble");
					label.setText(venta.getInmueble().getTipo().getTipo().toString());
					label = (Label) documentoVenta.lookup("#lblLocalidadInmueble");
					label.setText(venta.getInmueble().getDireccion().getLocalidad().toString());
					label = (Label) documentoVenta.lookup("#lblBarrioInmueble");
					label.setText(venta.getInmueble().getDireccion().getBarrio().toString());
					label = (Label) documentoVenta.lookup("#lblCalleInmueble");
					label.setText(venta.getInmueble().getDireccion().getCalle().toString());
					label = (Label) documentoVenta.lookup("#lblAlturaInmueble");
					label.setText(venta.getInmueble().getDireccion().getNumero());
					label = (Label) documentoVenta.lookup("#lblPisoInmueble");
					label.setText(venta.getInmueble().getDireccion().getPiso());
					label = (Label) documentoVenta.lookup("#lblDepartamentoInmueble");
					label.setText(venta.getInmueble().getDireccion().getDepartamento());
					label = (Label) documentoVenta.lookup("#lblOtrosInmueble");
					label.setText(venta.getInmueble().getDireccion().getOtros());
					label = (Label) documentoVenta.lookup("#lblImporte");
					label.setText(String.format("$ %10.2f", venta.getImporte()));
					label = (Label) documentoVenta.lookup("#lblMedioDePago");
					label.setText(venta.getMedioDePago());
					label = (Label) documentoVenta.lookup("#lblFechaVenta");
					label.setText(conversorFechas.diaMesYAnioToString(venta.getFecha()));
					label = (Label) documentoVenta.lookup("#lblHoraGenerado");
					Date ahora = new Date();
					label.setText(String.format(label.getText(), conversorFechas.horaYMinutosToString(ahora), conversorFechas.diaMesYAnioToString(ahora)));

					//genera el archivo
					pdf = generarPDF(documentoVenta);
				} catch(Throwable e){
					return e; //si algo falla
				}
				return null; //si no falla nada
			});

			//se asegura de que se corra en el hilo de javaFX
			if(!Platform.isFxApplicationThread()){
				Platform.runLater(future);
			}
			else{
				future.run();
			}
			Throwable excepcion = future.get();

			//si hubo error se lanza excepción
			if(excepcion != null){
				throw excepcion;
			}
			if(pdf == null){
				throw new NullPointerException("Error al generar PDF");
			}
		} catch(Throwable e){
			throw new GenerarPDFException(e);
		}

		return pdf;
	}
}
