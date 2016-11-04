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

	public void setPropietario(Propietario 	propietario) {
		textFieldAlturaCalle.setText(propietario.getDireccion().getNumero());
		textFieldApellido.setText(propietario.getApellido());
		textFieldBarrio.setText(propietario.getDireccion().getBarrio().getNombre());
		textFieldCalle.setText(propietario.getDireccion().getCalle().getNombre());
		textFieldCorreoElectronico.setText(propietario.getEmail());
		textFieldDepartamento.setText(propietario.getDireccion().getDepartamento());
		textFieldLocalidad.setText(propietario.getDireccion().getLocalidad().getNombre());
		textFieldNombre.setText(propietario.getNombre());
		textFieldNumeroDocumento.setText(propietario.getNumeroDocumento());
		textFieldPais.setText(propietario.getDireccion().getLocalidad().getProvincia().getPais().getNombre());
		textFieldPiso.setText(propietario.getDireccion().getPiso());
		textFieldProvincia.setText(propietario.getDireccion().getLocalidad().getProvincia().getNombre());
		textFieldTelefono.setText(propietario.getTelefono());
		textFieldTipoDeDocumento.setText(propietario.getTipoDocumento().toString());
	}

	@FXML
	public void handleAtras( ) {
		cambiarmeAScene(AdministrarPropietarioController.URLVista);
	}

	@Override
	public void inicializar(URL location, ResourceBundle resources) {

	}
}
