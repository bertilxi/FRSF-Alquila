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
import java.net.URL;
import java.util.ResourceBundle;

import app.datos.entidades.PDF;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * Controlador de la vista para mostrar un PDF
 */
public class VerPDFController extends OlimpoController {

	public static final String URLVista = "/app/ui/vistas/verPDF.fxml";

	private static final String URL_Visor = "visorPDF/web/viewer.html";

	private static final String URL_PDF = "visorPDF/web/ver.PDF";

	@FXML
	protected WebView visorPDF;

	@FXML
	protected TextField textFieldCorreo;

	/**
	 * Método para cargar el PDF a mostrar
	 */
	public void cargarPDF(PDF pdf) {
		Platform.runLater(() -> {
			try{
				File pdfFile = new File(URL_PDF);
				if(pdfFile.exists()){
					pdfFile.delete();
				}
				FileOutputStream fos = new FileOutputStream(pdfFile);
				fos.write(pdf.getArchivo());
				fos.close();
				WebEngine engine = visorPDF.getEngine();
				String url = "file:///" + new File(URL_Visor).getAbsolutePath();
				engine.load(url);
			} catch(Exception e){
				presentador.presentarExcepcionInesperada(e);
			}
		});
	}

	@Override
	public void inicializar(URL location, ResourceBundle resources) {
		this.setTitulo("Ver pdf");
	}
}
