package app.ui.controladores;

import java.net.URL;
import java.util.ResourceBundle;

import app.ui.ScenographyChanger;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;

/**
 * Controlador base que se encarga de manejar la barra de titulos y la barra lateral
 */
public class BaseController extends OlimpoController {

	public static final String URLVista = "/app/ui/vistas/base.fxml";

	@FXML
	private ToggleButton toggleButtonClientes;
	@FXML
	private ToggleButton toggleButtonInmuebles;
	@FXML
	private ToggleButton toggleButtonVendedores;
	@FXML
	private ToggleButton toggleButtonPropietarios;
	@FXML
	private ToggleButton toggleButtonAyuda;

	private ToggleGroup toggleGroupSidebar = new ToggleGroup();

	@FXML
	private Pane background;

	@Override
	public void inicializar(URL location, ResourceBundle resources) {
		this.setScenographyChanger(new ScenographyChanger(stage, presentador, coordinador, background));

		toggleButtonAyuda.setToggleGroup(toggleGroupSidebar);
		toggleButtonClientes.setToggleGroup(toggleGroupSidebar);
		toggleButtonInmuebles.setToggleGroup(toggleGroupSidebar);
		toggleButtonPropietarios.setToggleGroup(toggleGroupSidebar);
		toggleButtonVendedores.setToggleGroup(toggleGroupSidebar);

	}
}
