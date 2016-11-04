package app.ui.controladores;

import java.net.URL;
import java.util.ResourceBundle;

import app.datos.entidades.Vendedor;
import app.excepciones.PersistenciaException;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AdministrarVendedorController extends OlimpoController {

	public static final String URLVista = "/app/ui/vistas/administrarVendedor.fxml";

	@FXML
	private TableView<Vendedor> tablaVendedores;
	@FXML
	private TableColumn<Vendedor, String> columnaNumeroDocumento;
	@FXML
	private TableColumn<Vendedor, String> columnaNombre;
	@FXML
	private TableColumn<Vendedor, String> columnaApellido;

	@FXML
	private Button botonAgregar;
	@FXML
	private Button botonModificar;
	@FXML
	private Button botonEliminar;

	@Override
	public void inicializar(URL location, ResourceBundle resources) {
		try{
			tablaVendedores.getItems().addAll(coordinador.obtenerVendedores());
		} catch(PersistenciaException e){
			presentador.presentarExcepcion(e, stage);
		} catch(Exception e){
			presentador.presentarExcepcion(e, stage);
		}

		columnaNumeroDocumento.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumeroDocumento()));
		columnaNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
		columnaApellido.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApellido()));

		habilitarBotones(null);

		tablaVendedores.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> habilitarBotones(newValue));
	}

	private void habilitarBotones(Vendedor vendedor) {
		if(vendedor == null){
			botonModificar.setDisable(true);
			botonEliminar.setDisable(true);
		}
		else{
			botonModificar.setDisable(false);
			botonEliminar.setDisable(false);
		}
	}

	public void agregarAction(ActionEvent event) {
		cambiarmeAScene(AltaVendedorController.URLVista);
	}

	public void modificarAction(ActionEvent event) {
		Vendedor vendedor = tablaVendedores.getSelectionModel().getSelectedItem();
		if(vendedor == null){
			return;
		}
		ModificarVendedorController modificarVendedorController = (ModificarVendedorController) cambiarmeAScene(ModificarVendedorController.URLVista);
		modificarVendedorController.setVendedor(vendedor);
	}

	public void eliminarAction(ActionEvent event) {
		Vendedor vendedor = tablaVendedores.getSelectionModel().getSelectedItem();
		if(vendedor == null){
			return;
		}
		try{
			coordinador.eliminarVendedor(vendedor);
			tablaVendedores.getItems().remove(vendedor);
		} catch(PersistenciaException e){
			presentador.presentarExcepcion(e, stage);
		}
	}
}
