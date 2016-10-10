package app.ui;
/**
 * Created by fer on 13/09/16.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		try{
			Pane root = FXMLLoader.load(getClass().getResource("/app/ui/vistas/alta cliente2.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/app/ui/estilos/style.css").toExternalForm());
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
