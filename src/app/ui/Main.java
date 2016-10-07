package app.ui;
/**
 * Created by fer on 13/09/16.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		try{
<<<<<<< Updated upstream
			Pane root = FXMLLoader.load(getClass().getResource("/app/ui/vistas/Login.fxml"));
=======
			Pane root = FXMLLoader.load(getClass().getResource("/app/ui/vistas/alta inmueble2.fxml"));
>>>>>>> Stashed changes
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/app/ui/estilos/style.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
