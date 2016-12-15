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
package app.ui.controladores;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import app.comun.ImpresoraPDF;
import app.datos.entidades.PDF;
import app.excepciones.ImprimirPDFException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import netscape.javascript.JSObject;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.javafx.BrowserView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Controlador de la vista para mostrar un PDF
 */
public class VerPDFController extends OlimpoController {

	public static final String URLVista = "/app/ui/vistas/verPDF.fxml";

	private static final String URL_Visor = "visorPDF/web/viewer.html";

	private static final String URL_PDF = "visorPDF/web/ver.pdf";

	@FXML
	protected WebView visorPDF;

	@FXML
	protected TextField textFieldCorreo;

	private PDF pdf;

	private ImpresoraPDF impresora = new ImpresoraPDF();

	/**
	 * Método para cargar el PDF a mostrar
	 */
	public void cargarPDF(PDF pdf) {
		this.pdf = pdf;
		Platform.runLater(() -> {
			try{
				File pdfFile = new File(URL_PDF);
				if(pdfFile.exists()){
					pdfFile.delete();
				}
				FileOutputStream fos = new FileOutputStream(pdfFile);
				fos.write(pdf.getArchivo());
				fos.close();

				Browser browser = new Browser();
				BrowserView view = new BrowserView(browser);
				browser.loadURL("http://www.google.com");
/*
				WebEngine engine = visorPDF.getEngine();
				String url = "file:///" + new File(URL_Visor).getAbsolutePath();
				engine.load(url);

				JSObject jsobj = (JSObject) visorPDF.getEngine().executeScript("window");
				jsobj.setMember("java", this);
				visorPDF.getEngine().executeScript("window.print = function () { };");
				*/
			} catch(Exception e){
				presentador.presentarExcepcionInesperada(e);
			}
		});
	}

	@Override
	public void inicializar(URL location, ResourceBundle resources) {
		this.setTitulo("Ver pdf");
	}

	@FXML
	public void print() {
		try{
			impresora.imprimirPDF(pdf);
		} catch(ImprimirPDFException e){
			presentador.presentarExcepcion(e, stage);
		}
	}

	@FXML
	public void download() {
		try{
			File pdfAGuardar = null;
			String tipo = "(*.pdf ; *.PDF)";
			ArrayList<String> tiposFiltro = new ArrayList<>();
			tiposFiltro.add("*.pdf");
			tiposFiltro.add("*.PDF");

			ExtensionFilter filtro = new ExtensionFilter("Archivo de imágen " + tipo, tiposFiltro);

			FileChooser archivoSeleccionado = new FileChooser();
			archivoSeleccionado.getExtensionFilters().add(filtro);

			pdfAGuardar = archivoSeleccionado.showSaveDialog(stage);
			if(pdfAGuardar != null){
				String nombreArchivo = pdfAGuardar.toString();
				pdfAGuardar = new File(nombreArchivo);
				FileOutputStream fos = new FileOutputStream(pdfAGuardar);
				fos.write(pdf.getArchivo());
				fos.close();
			}
		} catch(IOException e){
			presentador.presentarExcepcionInesperada(e);
		}
	}
}
