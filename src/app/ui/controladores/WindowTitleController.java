package app.ui.controladores;

import java.net.URL;
import java.util.ResourceBundle;

import app.ui.ScenographyChanger;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class WindowTitleController extends OlimpoController {

	public static final String URLVista = "/app/ui/vistas/windowTitle.fxml";

	@FXML
	private Pane background;

	@Override
	public void inicializar(URL location, ResourceBundle resources) {
		//Primera pantalla a mostrar
		this.agregarScenographyChanger(background, new ScenographyChanger(stage, presentador, coordinador, background));
		cambiarScene(background, LoginController.URLVista);
	}
}
