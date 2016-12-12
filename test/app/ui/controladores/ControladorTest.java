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

import java.util.concurrent.FutureTask;
import java.util.concurrent.Semaphore;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class ControladorTest implements TestRule {

	private static Stage stagePrueba;

	private static Thread javaFXThread = new Thread("Hilo de inicio de JavaFX") {
		@Override
		public void run() {
			Platform.setImplicitExit(false);
			Application.launch(ClaseAplicacion.class);
		}
	};

	public static class ClaseAplicacion extends Application {

		@Override
		public void start(Stage primaryStage) throws Exception {
			stagePrueba = primaryStage;
			s.release();
		}
	}

	private static Semaphore s = new Semaphore(0);

	//Inicia el hilo de JavaFX e inyecta la vista en el cotrolador
	public <T> ControladorTest(String direccionURLVista, T controladorAProbar) {
		try{
			//Iniciar el hilo de JavaFX
			if(!javaFXThread.isAlive()){
				javaFXThread.setDaemon(true);
				javaFXThread.start();
				s.acquire();
			}

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(this.getClass().getResource(direccionURLVista));
			loader.setControllerFactory(claseControlador -> {
				if(claseControlador != null && !claseControlador.isInstance(controladorAProbar)){
					throw new IllegalArgumentException("¡Instancia del controlador inválida, esperada una instancia de la clase '" + claseControlador.getName() + "'!");
				}

				return controladorAProbar;
			});

			loader.load();
		} catch(Exception e){
			throw new RuntimeException(e);
		}
	}

	//Clase para correr un test en el hilo de JavaFX
	@Override
	public Statement apply(final Statement statement, final Description description) {
		return new Statement() {
			@Override
			public void evaluate() throws Throwable {
				FutureTask<Throwable> future = new FutureTask<>(() -> {

					try{
						statement.evaluate();
						return null;
					} catch(Throwable t){
						return t;
					}
				});

				Platform.runLater(future);
				Throwable excepcion = future.get();
				if(excepcion != null){
					throw excepcion;
				}
			}
		};
	}

	public Stage getStagePrueba() {
		return stagePrueba;
	}

}
