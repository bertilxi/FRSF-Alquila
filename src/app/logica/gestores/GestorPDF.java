package app.logica.gestores;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfWriter;

import app.datos.clases.CatalogoVista;
import app.datos.entidades.PDF;
import app.datos.entidades.Reserva;
import app.datos.entidades.Venta;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;

@Service
public class GestorPDF {

	private PDF generarPDF(Node pantallaAPDF) throws Exception {
		WritableImage image = pantallaAPDF.snapshot(new SnapshotParameters(), null);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", baos);
		baos.flush();
		byte[] imageInByte = baos.toByteArray();
		baos.close();
		Image imagen = Image.getInstance(imageInByte);

		Document document = new Document();
		FileOutputStream pdf = new FileOutputStream("borrar1234.pdf");
		PdfWriter escritor = PdfWriter.getInstance(document, pdf);
		document.open();
		document.add(imagen);
		document.close();
		escritor.close();
		return null;
	}

	public PDF generarPDF(CatalogoVista catalogo) {
		//TODO hacer
		return null;
	}

	public PDF generarPDF(Reserva reserva) {
		//TODO hacer
		return null;
	}

	public PDF generarPDF(Venta venta) {
		//TODO hacer
		return null;
	}
}
