package app.ui.controladores;

import java.net.URL;
import java.util.ResourceBundle;

import app.datos.entidades.Propietario;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class VerPropietarioController extends OlimpoController{

	public static final String URLVista = "/app/ui/vistas/VerPropietario.fxml";

	@FXML
	private TextField textFieldNombre;
	@FXML
	private TextField textFieldApellido;
	@FXML
	private TextField textFieldNumeroDocumento;
	@FXML
	private TextField textFieldAlturaCalle;
	@FXML
	private TextField textFieldPiso;
	@FXML
	private TextField textFieldDepartamento;
	@FXML
	private TextField textFieldTelefono;
	@FXML
	private TextField textFieldCorreoElectronico;
	@FXML
	private TextField textFieldTipoDeDocumento;
	@FXML
	private TextField textFieldPais;
	@FXML
	private TextField textFieldProvincia;
	@FXML
	private TextField textFieldLocalidad;
	@FXML
	private TextField textFieldCalle;
	@FXML
	private TextField textFieldBarrio;

	private Propietario propietario;

	public void setPropietario(Propietario propietario) {
		this.propietario = propietario;
		//TODO setear campos
	}

	@FXML
	public void handleAtras( ) {
		//TODO hacer
	}

	@Override
	public void inicializar(URL location, ResourceBundle resources) {

	}
}
