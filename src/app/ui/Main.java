package app.ui;
/**
 * Created by fer on 13/09/16.
 */

import java.net.URL;
import java.nio.charset.Charset;

import app.ui.controladores.WindowTitleController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		try{

			URL location = getClass().getResource("/app/ui/vistas/Login.fxml");
			FXMLLoader loader = createFXMLLoader(location);
			Parent root = loader.load(location.openStream());
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/app/ui/estilos/style.css").toExternalForm());

			// para emular el estilo de windows 10 se usa la ventana sin decorar
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setScene(scene);

			WindowTitleController controller = loader.getController();
			controller.controlerPassing(primaryStage);

			primaryStage.show();

		} catch(Exception e){
			e.printStackTrace();
		}
	}

	public FXMLLoader createFXMLLoader(URL location) {
		return new FXMLLoader(location, null, new JavaFXBuilderFactory(), null, Charset.forName(FXMLLoader.DEFAULT_CHARSET_NAME));
	}
}
