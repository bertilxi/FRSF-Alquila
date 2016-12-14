package app.comun;

import java.io.File;
import java.io.FileOutputStream;

import org.springframework.stereotype.Service;

import com.lowagie.tools.Executable;

import app.datos.entidades.PDF;
import app.excepciones.ImprimirPDFException;

/**
 * Clase encargada de imprimir PDF
 */
@Service
public class ImpresoraPDF {

	/**
	 * Método que manda a imprimir un PDF
	 *
	 * @param p
	 * 			PDF a imprimir
	 * @throws ImprimirPDFException
	 * 			si falla al imprimir
	 */
	public void imprimirPDF(PDF p) throws ImprimirPDFException {
		try{
			File pdfFile = new File("tmpImprimir.PDF");
			if(pdfFile.exists()){
				pdfFile.delete();
			}
			FileOutputStream fos = new FileOutputStream(pdfFile);
			fos.write(p.getArchivo());
			fos.close();

			Executable.printDocument(pdfFile, false);
		} catch(Exception ex){
			throw new ImprimirPDFException(ex);
		}
	}
}
