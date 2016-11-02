package app.ui.controladores;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import app.datos.entidades.Vendedor;
import app.excepciones.PersistenciaException;
import app.logica.CoordinadorJavaFX;
import app.ui.componentes.VentanaError;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AdministrarVendedorController extends BaseController {

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

	private ArrayList<Vendedor> listaVendedores;

	protected CoordinadorJavaFX coordinador = new CoordinadorJavaFX();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);

		try{
			listaVendedores = coordinador.obtenerVendedores();
		} catch(PersistenciaException e){
			new VentanaError("Error", "No se pudieron listar los vendedores", null); //falta el stage
		}

		columnaNumeroDocumento.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumeroDocumento()));
		columnaNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
		columnaApellido.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApellido()));

		tablaVendedores.getItems().addAll(listaVendedores);

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
}
