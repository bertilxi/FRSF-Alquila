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
package app.ui.componentes;

import java.net.URL;
import java.nio.charset.Charset;

import app.ui.controladores.WindowTitleController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ViewLauncher extends Application {

	private String path;

	public ViewLauncher(String path) {
		super();
		this.path = path;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		URL location = getClass().getResource(path);
		FXMLLoader loader = createFXMLLoader(location);
		Parent root = loader.load(location.openStream());
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/app/ui/estilos/style.css").toExternalForm());

		// para emular el estilo de windows 10 se usa la ventana sin decorar
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setScene(scene);

		WindowTitleController controller = loader.getController();
		controller.setStage(primaryStage);

		primaryStage.show();
	}

	public FXMLLoader createFXMLLoader(URL location) {
		return new FXMLLoader(location, null, new JavaFXBuilderFactory(), null, Charset.forName(FXMLLoader.DEFAULT_CHARSET_NAME));
	}

	public void main(String[] args) {
		launch(args);
	}

	public static void launchView(String path) throws InterruptedException {

		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				new JFXPanel(); // Initializes the JavaFx Platform
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						try{
							new ViewLauncher(path).start(new Stage()); // Create and
						} catch(Exception e){
							e.printStackTrace();
						}

					}
				});
			}
		});
		thread.start();// Initialize the thread
		Thread.sleep(1000000); // Time to use the app, with out this, the thread

	}
}
