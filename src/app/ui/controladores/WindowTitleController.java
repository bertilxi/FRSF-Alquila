package app.ui.controladores;

import java.net.URL;
import java.util.ResourceBundle;

import app.ui.ScenographyChanger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class WindowTitleController extends OlimpoController {

	public static final String URLVista = "/app/ui/vistas/windowTitle.fxml";

	private static double xOffset = 0;
	private static double yOffset = 0;

	@FXML
	private HBox titlebar;

	@FXML
	private Pane background;

	@FXML
	private Label titulo;

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
	public void inicializar(URL location, ResourceBundle resources) {
		this.setScenographyChanger(new ScenographyChanger(stage, presentador, coordinador, background));
		//Primera pantalla a mostrar
		cambiarScene(LoginController.URLVista);

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

		titulo.textProperty().bind(stage.titleProperty());
	}
}
