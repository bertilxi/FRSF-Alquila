package app.comun;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUI;
import javax.print.SimpleDoc;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSizeName;

import org.springframework.stereotype.Service;

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
	 *            PDF a imprimir
	 * @throws ImprimirPDFException
	 *             si falla al imprimir
	 */
	public void imprimirPDF(PDF p) throws ImprimirPDFException {
		try{
			PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
			pras.add(MediaSizeName.ISO_A4);
			pras.add(new Copies(1));
			DocFlavor flavor = DocFlavor.INPUT_STREAM.PDF;
			FutureTask<Object[]> futuro = new FutureTask<>(() -> {
				PrintService printService[] = PrintServiceLookup.lookupPrintServices(
						flavor, pras);
				PrintService defaultService = PrintServiceLookup
						.lookupDefaultPrintService();
				return new Object[] { printService, defaultService };
			});

			new Thread(futuro).start();
			Object[] retorno = futuro.get(5000, TimeUnit.MILLISECONDS);
			PrintService printService[] = (PrintService[]) retorno[0];
			PrintService defaultService = (PrintService) retorno[1];
			PrintService service = ServiceUI.printDialog(null, 200, 200,
					printService, defaultService, flavor, pras);
			if(service != null){
				DocPrintJob job = service.createPrintJob();
				InputStream is = new ByteArrayInputStream(p.getArchivo());
				DocAttributeSet das = new HashDocAttributeSet();
				das.add(MediaSizeName.ISO_A4);
				Doc doc = new SimpleDoc(is, flavor, das);
				job.print(doc, pras);
			}

			/*
			 * File pdfFile = new File("tmpImprimir.pdf");
			 * if(pdfFile.exists()){
			 * pdfFile.delete();
			 * }
			 * FileOutputStream fos = new FileOutputStream(pdfFile);
			 * fos.write(p.getArchivo());
			 * fos.close();
			 * 
			 * Executable.printDocument(pdfFile, false);
			 */ } catch(Exception ex){
			throw new ImprimirPDFException(ex);
		}
	}
}
