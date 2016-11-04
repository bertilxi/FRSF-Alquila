package app.ui.controladores;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import app.datos.entidades.Propietario;
import app.excepciones.PersistenciaException;
import app.logica.resultados.ResultadoEliminarPropietario;
import app.logica.resultados.ResultadoEliminarPropietario.ErrorEliminarPropietario;
import app.ui.componentes.ventanas.VentanaConfirmacion;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AdministrarPropietarioController extends OlimpoController {

	public static final String URLVista = "/app/ui/vistas/administrarPropietario.fxml";

	@FXML
	private TableView<Propietario> tablaPropietarios;

	@FXML
	private TableColumn<Propietario, String> columnaNumeroDocumento;
	@FXML
	private TableColumn<Propietario, String> columnaNombre;
	@FXML
	private TableColumn<Propietario, String> columnaApellido;
	@FXML
	private TableColumn<Propietario, String> columnaTelefono;

	@FXML
	private Button botonVer;
	@FXML
	private Button botonAgregar;
	@FXML
	private Button botonModificar;
	@FXML
	private Button botonEliminar;

	private ArrayList<Propietario> listaPropietarios;

	@Override
	public void inicializar(URL location, ResourceBundle resources) {

		try{
			listaPropietarios = coordinador.obtenerPropietarios();
		} catch(PersistenciaException e){
			presentador.presentarError("Error", "No se pudieron listar los propietarios", stage);
		}

		columnaNumeroDocumento.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumeroDocumento()));
		columnaNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
		columnaApellido.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApellido()));
		columnaTelefono.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefono()));

		tablaPropietarios.getItems().addAll(listaPropietarios);

		habilitarBotones(null);

		tablaPropietarios.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> habilitarBotones(newValue));
	}

	private void habilitarBotones(Propietario propietario) {
		if(propietario == null){
			botonVer.setDisable(true);
			botonModificar.setDisable(true);
			botonEliminar.setDisable(true);
		}
		else{
			botonVer.setDisable(false);
			botonModificar.setDisable(false);
			botonEliminar.setDisable(false);
		}
	}

	@FXML
	private void handleVer() {
		//TODO hacer esto
	}

	@FXML
	private void handleAgregar() {
		cambiarmeAScene(AltaPropietarioController.URLVista);
	}

	@FXML
	private void handleModificar() {
		ModificarPropietarioController controlador = (ModificarPropietarioController) cambiarmeAScene(ModificarPropietarioController.URLVista);
		controlador.setPropietarioEnModificacion(tablaPropietarios.getSelectionModel().getSelectedItem());
	}

	@FXML
	private void handleEliminar() {
		VentanaConfirmacion ventana = presentador.presentarConfirmacion("Eliminar propietario", "Está a punto de eliminar al propietario.\n ¿Está seguro que desea hacerlo?", this.stage);
		if(ventana.acepta()) {
			try {
				ResultadoEliminarPropietario resultado = coordinador.eliminarPropietario(tablaPropietarios.getSelectionModel().getSelectedItem());
				if(resultado.hayErrores()) {
					StringBuilder stringErrores = new StringBuilder();
					for(ErrorEliminarPropietario err: resultado.getErrores()){
						switch(err) {
						case No_Existe_Propietario:
							stringErrores.append("No existe el propietario que desea eliminar.\n");
							break;
						}
					}
					presentador.presentarError("No se pudo eliminar el propietario", stringErrores.toString(), stage);

				}
				tablaPropietarios.getItems().clear();
				tablaPropietarios.getItems().addAll(coordinador.obtenerPropietarios());
			} catch (PersistenciaException e) {
				presentador.presentarExcepcion(e, stage);
			}
		}
	}
}
