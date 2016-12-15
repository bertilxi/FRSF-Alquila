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
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.CancellationException;
import java.util.concurrent.FutureTask;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.DownloadHandler;
import com.teamdev.jxbrowser.chromium.DownloadItem;
import com.teamdev.jxbrowser.chromium.events.FailLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.FrameLoadEvent;
import com.teamdev.jxbrowser.chromium.events.LoadEvent;
import com.teamdev.jxbrowser.chromium.events.LoadListener;
import com.teamdev.jxbrowser.chromium.events.ProvisionalLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.StartLoadingEvent;
import com.teamdev.jxbrowser.chromium.javafx.BrowserView;

import app.datos.entidades.PDF;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * Controlador de la vista para mostrar un PDF
 */
public class VerPDFController extends OlimpoController {

	public static final String URLVista = "/app/ui/vistas/verPDF.fxml";

	public static final String URL_PDF = "ver.pdf";

	@FXML
	protected BorderPane borderPanePDF;

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

				Browser browser = new Browser();
				BrowserView view = new BrowserView(browser);

				borderPanePDF.setCenter(view);

				String url = "file:///" + pdfFile.getAbsolutePath();
				browser.loadURL(url);
				browser.setDownloadHandler(new DownloadHandler() {
					@Override
					public boolean allowDownload(DownloadItem download) {
						return download(download);
					}
				});
				browser.addLoadListener(new LoadListener() {

					@Override
					public void onStartLoadingFrame(StartLoadingEvent arg0) {

					}

					@Override
					public void onProvisionalLoadingFrame(ProvisionalLoadingEvent arg0) {

					}

					@Override
					public void onFinishLoadingFrame(FinishLoadingEvent arg0) {
						arg0.getBrowser().setZoomLevel(-4.029320239182773);
					}

					@Override
					public void onFailLoadingFrame(FailLoadingEvent arg0) {

					}

					@Override
					public void onDocumentLoadedInMainFrame(LoadEvent arg0) {

					}

					@Override
					public void onDocumentLoadedInFrame(FrameLoadEvent arg0) {

					}
				});
			} catch(Exception e){
				presentador.presentarExcepcionInesperada(e);
			}
		});
	}

	private Boolean download(DownloadItem download) {
		String tipo = "(*.pdf ; *.PDF)";
		ArrayList<String> tiposFiltro = new ArrayList<>();
		tiposFiltro.add("*.pdf");
		tiposFiltro.add("*.PDF");

		ExtensionFilter filtro = new ExtensionFilter("Archivo de imágen " + tipo, tiposFiltro);

		FileChooser archivoSeleccionado = new FileChooser();
		archivoSeleccionado.getExtensionFilters().add(filtro);

		FutureTask<Boolean> future = new FutureTask<>(() -> {
			File pdfAGuardar = null;
			pdfAGuardar = archivoSeleccionado.showSaveDialog(stage);
			download.setDestinationFile(pdfAGuardar);
			return true;
		});

		Platform.runLater(future);
		Boolean b = false;
		try{
			b = future.get();
		} catch(CancellationException e){
			//el usuario cancela
			//no se hace nada
		} catch(Exception e){ //cualquier otra excepción
			Platform.runLater(() -> {
				presentador.presentarError("Error", "No se pudo guardar el pdf deseado", stage);
			});
		}
		if(b == null){
			b = false;
		}
		return b;
	}

	@Override
	public void inicializar(URL location, ResourceBundle resources) {
		this.setTitulo("Ver pdf");
	}
}
