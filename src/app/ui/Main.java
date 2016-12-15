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
package app.ui;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.teamdev.jxbrowser.chromium.LoggerProvider;

import app.logica.CoordinadorJavaFX;
import app.logica.gestores.GestorEmail;
import app.ui.componentes.IconoAplicacion;
import app.ui.componentes.ventanas.PresentadorVentanas;
import app.ui.componentes.ventanas.VentanaEsperaBaseDeDatos;
import app.ui.controladores.OlimpoController;
import app.ui.controladores.VerPDFController;
import app.ui.controladores.WindowTitleController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

	private CoordinadorJavaFX coordinador;
	private ApplicationContext appContext;
	private Stage primaryStage;
	private PresentadorVentanas presentador;

	public static void main(String[] args) {
		//Ocultar logs
		java.util.Enumeration<String> loggers = java.util.logging.LogManager.getLogManager().getLoggerNames();
		while(loggers.hasMoreElements()){
			String log = loggers.nextElement();
			java.util.logging.Logger.getLogger(log).setLevel(java.util.logging.Level.WARNING);
		}
		LoggerProvider.setLevel(java.util.logging.Level.WARNING);
		//Iniciar aplicacion
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		//Analizar parámetros de entrada
		verParametros(getParameters().getRaw());

		//Inicializar parametros
		this.primaryStage = primaryStage;
		this.presentador = new PresentadorVentanas();

		//Iniciar el stage en el centro de la pantalla
		this.primaryStage.centerOnScreen();

		//Setear icono y titulo de aplicacion
		primaryStage.getIcons().add(new IconoAplicacion());

		//Setear acción de cierre
		primaryStage.setOnCloseRequest((e) -> {
			//Borrar archivos temporales
			File pdfFile = new File(GestorEmail.URL_RESERVA);
			if(pdfFile.exists()){
				pdfFile.delete();
			}
			pdfFile = new File(VerPDFController.URL_PDF);
			if(pdfFile.exists()){
				pdfFile.delete();
			}

			//Cerrar hibernate
			SessionFactory sessionFact = (SessionFactory) appContext.getBean("sessionFactory");
			sessionFact.close();
		});

		iniciarHibernate();
	}

	private void iniciarHibernate() {
		//Crear ventana de espera
		VentanaEsperaBaseDeDatos ventanaEspera = presentador.presentarEsperaBaseDeDatos(primaryStage.getOwner());

		//Crear tarea para iniciar hibernate y el coordinador de la aplicacion
		Task<Boolean> task = new Task<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				appContext = new ClassPathXmlApplicationContext("applicationContext.xml");
				coordinador = appContext.getBean(CoordinadorJavaFX.class);
				return true;
			}
		};

		//mientras se muestra una ventana de espera
		task.setOnRunning(
				(event) -> {
					ventanaEspera.showAndWait();
				});

		//que se cierra al terminar.
		task.setOnSucceeded(
				(event) -> {
					ventanaEspera.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
					ventanaEspera.hide();
					crearPrimeraVentana();
				});

		//Si falla, informa al usuario del error y cierra la aplicacion
		task.setOnFailed(
				(event) -> {
					try{
						throw task.getException();
					} catch(Throwable e){
						e.printStackTrace();
						if(appContext != null){
							SessionFactory sessionFact = (SessionFactory) appContext.getBean("sessionFactory");
							sessionFact.close();
						}
						presentador.presentarExcepcionInesperada(new Exception(e), primaryStage);
						System.exit(1);
					}
				});

		//Iniciar tarea
		Thread hiloHibernate = new Thread(task);
		hiloHibernate.setDaemon(false);
		hiloHibernate.start();
	}

	private void crearPrimeraVentana() {
		Platform.runLater(() -> {
			try{
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource(WindowTitleController.URLVista));

				Parent root = loader.load();
				Scene scene = new Scene(root);

				OlimpoController controller = loader.getController();
				controller.setCoordinador(coordinador).setStage(primaryStage).setPresentador(presentador);

				primaryStage.setScene(scene);
				primaryStage.show();
			} catch(IOException e){
				e.printStackTrace();
				primaryStage.fireEvent(new WindowEvent(primaryStage, WindowEvent.WINDOW_CLOSE_REQUEST));
			}
		});
	}

	private void verParametros(List<String> raw) {
		//Analizar parámetros
	}
}
