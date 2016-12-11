package app.logica.gestores;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.Semaphore;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfWriter;

import app.comun.ConversorFechas;
import app.comun.FormateadorString;
import app.datos.clases.CatalogoVista;
import app.datos.clases.ReservaVista;
import app.datos.entidades.PDF;
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

	private Exception exception = null;

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
		baos.flush();
		byte[] imageInByte = baos.toByteArray();
		baos.close();
		Image imagen = Image.getInstance(imageInByte);
		Document document = new Document();
		ByteArrayOutputStream pdfbaos = new ByteArrayOutputStream();
		PdfWriter escritor = PdfWriter.getInstance(document, pdfbaos);
		document.open();
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
	public PDF generarPDF(ReservaVista reserva) throws GestionException {
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource(URLDocumentoReserva));
			Pane documentoReserva = (Pane) loader.load();
			Semaphore semaforo = new Semaphore(0);

			Platform.runLater(() -> {
				Label label = (Label) documentoReserva.lookup("#lblNombreOferente");
				label.setText(formateador.primeraMayuscula(reserva.getCliente().getNombre()));
				label = (Label) documentoReserva.lookup("#lblApellidoOferente");
				label.setText(formateador.primeraMayuscula(reserva.getCliente().getApellido()));
				label = (Label) documentoReserva.lookup("#lblDocumentoOferente");
				label.setText(reserva.getCliente().getTipoDocumento() + " - " + reserva.getCliente().getNumeroDocumento());
				label = (Label) documentoReserva.lookup("#lblNombrePropietario");
				label.setText(formateador.primeraMayuscula(reserva.getInmuble().getPropietario().getNombre()));
				label = (Label) documentoReserva.lookup("#lblApellidoPropietario");
				label.setText(formateador.primeraMayuscula(reserva.getInmuble().getPropietario().getApellido()));
				label = (Label) documentoReserva.lookup("#lblCodigoInmueble");
				label.setText(Integer.toString(reserva.getInmuble().getId()));
				label = (Label) documentoReserva.lookup("#lblTipoInmueble");
				label.setText(reserva.getInmuble().getTipo().getTipo().toString());
				label = (Label) documentoReserva.lookup("#lblLocalidadInmueble");
				label.setText(reserva.getInmuble().getDireccion().getLocalidad().toString());
				label = (Label) documentoReserva.lookup("#lblBarrioInmueble");
				label.setText(reserva.getInmuble().getDireccion().getBarrio().toString());
				label = (Label) documentoReserva.lookup("#lblCalleInmueble");
				label.setText(reserva.getInmuble().getDireccion().getCalle().toString());
				label = (Label) documentoReserva.lookup("#lblAlturaInmueble");
				label.setText(reserva.getInmuble().getDireccion().getNumero());
				label = (Label) documentoReserva.lookup("#lblPisoInmueble");
				label.setText(reserva.getInmuble().getDireccion().getPiso());
				label = (Label) documentoReserva.lookup("#lblDepartamentoInmueble");
				label.setText(reserva.getInmuble().getDireccion().getDepartamento());
				label = (Label) documentoReserva.lookup("#lblOtrosInmueble");
				label.setText(reserva.getInmuble().getDireccion().getOtros());
				label = (Label) documentoReserva.lookup("#lblImporte");
				label.setText(String.format("$ %10.2f", reserva.getImporte()));
				label = (Label) documentoReserva.lookup("#lblFechaRealizacion");
				label.setText(conversorFechas.diaMesYAnioToString(reserva.getFechaInicio()));
				label = (Label) documentoReserva.lookup("#lblFechaVencimiento");
				label.setText(conversorFechas.diaMesYAnioToString(reserva.getFechaFin()));

				try{
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
