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

    private static double xOffset = 0;
    private static double yOffset = 0;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		try{
			Pane root = FXMLLoader.load(getClass().getResource("/app/ui/vistas/alta cliente2.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/app/ui/estilos/style.css").toExternalForm());

            // para emular el estilo de windows 10 se usa la ventana sin decorar
            primaryStage.initStyle(StageStyle.UNDECORATED);

            // como se pierden las propiedades del sistema por no tener barra de titulo
            // se implementan dos handlers que manejan el movimiento de arrastre de la ventana
            // se pierden mas propiedades que por el momento ignoro
            root.setOnMousePressed(event -> {
                xOffset = primaryStage.getX() - event.getScreenX();
                yOffset = primaryStage.getY() - event.getScreenY();
            });

            root.setOnMouseDragged(event -> {
                primaryStage.setX(event.getScreenX() + xOffset);
                primaryStage.setY(event.getScreenY() + yOffset);
            });

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
