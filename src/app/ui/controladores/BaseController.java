package app.ui.controladores;

import java.net.URL;
import java.util.ResourceBundle;

import app.ui.ScenographyChanger;
import javafx.collections.ListChangeListener.Change;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
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
		addAlwaysOneSelectedSupport(toggleGroupSidebar);
	}

	private void addAlwaysOneSelectedSupport(final ToggleGroup toggleGroup) {
		toggleGroup.getToggles().addListener((Change<? extends Toggle> c) -> {
			while(c.next()){
				for(final Toggle addedToggle: c.getAddedSubList()){
					addConsumeMouseEventfilter(addedToggle);
				}
			}
		});
		toggleGroup.getToggles().forEach(t -> {
			addConsumeMouseEventfilter(t);
		});
	}

	private void addConsumeMouseEventfilter(Toggle toggle) {
		EventHandler<MouseEvent> consumeMouseEventfilter = (MouseEvent mouseEvent) -> {
			if(((Toggle) mouseEvent.getSource()).isSelected()){
				mouseEvent.consume();
			}
		};

		((ToggleButton) toggle).addEventFilter(MouseEvent.MOUSE_PRESSED, consumeMouseEventfilter);
		((ToggleButton) toggle).addEventFilter(MouseEvent.MOUSE_RELEASED, consumeMouseEventfilter);
		((ToggleButton) toggle).addEventFilter(MouseEvent.MOUSE_CLICKED, consumeMouseEventfilter);
	}

	@FXML
	public void verClientes(Event event) {
		cambiarScene(background, AdministrarClienteController.URLVista);
		if(toggleButtonClientes.isSelected()){

		}
	}

	@FXML
	public void verInmuebles() {
		cambiarScene(background, AdministrarInmuebleController.URLVista);
		if(toggleButtonInmuebles.isSelected()){
		}
	}

	@FXML
	public void verVendedores() {
		cambiarScene(background, AdministrarVendedorController.URLVista);
		if(toggleButtonVendedores.isSelected()){
		}
	}

	@FXML
	public void verPropietarios() {
		cambiarScene(background, AdministrarPropietarioController.URLVista);
		if(toggleButtonPropietarios.isSelected()){
		}
	}
}
