package app.ui.controladores;

import java.net.URL;
import java.util.ResourceBundle;

import app.logica.CoordinadorJavaFX;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class WindowTitleController implements Initializable {

	private static double xOffset = 0;
	private static double yOffset = 0;

	@FXML
	private HBox titlebar;
	protected Stage stage;
	protected CoordinadorJavaFX coordinador;

	public void controlerPassing(Stage stage) {
		this.stage = stage;
	}

	@FXML
	private void exitPlatform() {
		Platform.exit();
	}

	@FXML
	private void minimizePlatform() {
		stage.setIconified(true);
	}

	@FXML
	private void maximizePlatform() {

		if(stage.isMaximized()){
			stage.setMaximized(false);
		}
		else{
			stage.setMaximized(true);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// como se pierden las propiedades del sistema por no tener barra de titulo
		// se implementan dos handlers que manejan el movimiento de arrastre de la ventana
		// se pierden mas propiedades que por el momento ignoro
		titlebar.setOnMousePressed(event -> {
			xOffset = stage.getX() - event.getScreenX();
			yOffset = stage.getY() - event.getScreenY();
		});

		titlebar.setOnMouseDragged(event -> {
			stage.setX(event.getScreenX() + xOffset);
			stage.setY(event.getScreenY() + yOffset);
		});
	}

	public Stage getStage() {
		return stage;
	}

	public void setCoordinador(CoordinadorJavaFX coordinador) {
		this.coordinador = coordinador;
	}
}
