package olimpo.ui;
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
			Pane root = FXMLLoader.load(getClass().getResource("/olimpo/ui/vistas/main.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/olimpo/ui/estilos/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
