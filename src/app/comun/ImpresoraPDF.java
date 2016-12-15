package app.comun;

import java.awt.print.PrinterJob;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;
import org.springframework.stereotype.Service;

import app.datos.entidades.PDF;
import app.excepciones.ImprimirPDFException;

/**
 * Clase encargada de imprimir PDF
 *
 * Pertenece a la taskcard 31 de la iteración 2 y a la historia 8
 */
@Service
public class ImpresoraPDF {

	/**
	 * Método que manda a imprimir un PDF
	 *
	 * @param p
	 *            PDF a imprimir
	 * @throws ImprimirPDFException
	 *             si falla al imprimir
	 */
	public void imprimirPDF(PDF p) throws ImprimirPDFException {
		try{
			PDDocument document = PDDocument.load(p.getArchivo());
			PrinterJob printJob = PrinterJob.getPrinterJob();
			if(printJob.printDialog()){
				printJob.setPageable(new PDFPageable(document));
				printJob.print();
				document.close();
			}

		} catch(Exception ex){
			throw new ImprimirPDFException(ex);
		}
	}
}
