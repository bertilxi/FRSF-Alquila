package app.ui.controladores;

import java.net.URL;
import java.util.ResourceBundle;

import org.hibernate.cfg.NotYetImplementedException;

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

	private String ventanaInicio = InicioController.URLVista;

	@FXML
	private ToggleButton toggleButtonClientes;

	@FXML
	private ToggleButton toggleButtonInmuebles;

	@FXML
	private ToggleButton toggleButtonVendedores;

	@FXML
	private ToggleButton toggleButtonPropietarios;

	private ToggleGroup toggleGroupSidebar = new ToggleGroup();

	@FXML
	private Pane background;

	@Override
	public void inicializar(URL location, ResourceBundle resources) {
		//Primera pantalla a mostrar
		this.agregarScenographyChanger(background, new ScenographyChanger(stage, presentador, coordinador, background));
		cambiarScene(background, ventanaInicio);

		toggleButtonClientes.setToggleGroup(toggleGroupSidebar);
		toggleButtonInmuebles.setToggleGroup(toggleGroupSidebar);
		toggleButtonPropietarios.setToggleGroup(toggleGroupSidebar);
		toggleButtonVendedores.setToggleGroup(toggleGroupSidebar);
	}

	@FXML
	public void verClientes() {
		cambiarScene(background, AdministrarClienteController.URLVista);
	}

	@FXML
	public void verInmuebles() {
		cambiarScene(background, AdministrarInmuebleController.URLVista);
	}

	@FXML
	public void verVendedores() {
		cambiarScene(background, AdministrarVendedorController.URLVista);
	}

	@FXML
	public void verDueños() {
		cambiarScene(background, AdministrarPropietarioController.URLVista);
	}

	@FXML
	public void verAyuda() {
		throw new NotYetImplementedException();
	}
}
