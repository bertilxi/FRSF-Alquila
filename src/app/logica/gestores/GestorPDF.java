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
import java.util.Date;
import java.util.concurrent.Semaphore;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;

import app.comun.ConversorFechas;
import app.comun.FormateadorString;
import app.datos.clases.CatalogoVista;
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
import javafx.scene.image.WritableImage;
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

	private Exception exception;

	private static final String URLDocumentoReserva = "/res/pdf/documentoReserva.fxml";

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
	 * Método para crear un PDF de un catalogo a partir de los datos de un CatalogoVista.
	 * Pertenece a la taskcard 23 de la iteración 2 y a la historia 5
	 *
	 * @param catalogo
	 *            datos que se utilizaran para generar el PDF de un catalogo
	 * @return catalogo en PDF
	 */
	public PDF generarPDF(CatalogoVista catalogo) {
		//TODO hacer
		return null;
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
		exception = null;
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource(URLDocumentoReserva));
			Pane documentoReserva = (Pane) loader.load();
			Semaphore semaforo = new Semaphore(0);

			Platform.runLater(() -> {
				try{
					Label label = (Label) documentoReserva.lookup("#lblNombreOferente");
					label.setText(formateador.primeraMayuscula(reserva.getCliente().getNombre()));
					label = (Label) documentoReserva.lookup("#lblApellidoOferente");
					label.setText(formateador.primeraMayuscula(reserva.getCliente().getApellido()));
					label = (Label) documentoReserva.lookup("#lblDocumentoOferente");
					label.setText(reserva.getCliente().getTipoDocumento() + " - " + reserva.getCliente().getNumeroDocumento());
					label = (Label) documentoReserva.lookup("#lblNombrePropietario");
					label.setText(formateador.primeraMayuscula(reserva.getInmueble().getPropietario().getNombre()));
					label = (Label) documentoReserva.lookup("#lblApellidoPropietario");
					label.setText(formateador.primeraMayuscula(reserva.getInmueble().getPropietario().getApellido()));
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
				} catch(Exception e){
					exception = e;
				}

				semaforo.release();
			});

			semaforo.acquire();

			if(exception != null){
				throw exception;
			}
			if(pdf == null){
				throw new NullPointerException("Error al generar PDF");
			}
		} catch(Exception e){
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
	public PDF generarPDF(Venta venta) {
		//TODO hacer
		return null;
	}
}
