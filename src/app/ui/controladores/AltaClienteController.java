package app.ui.controladores;

import java.net.URL;
import java.util.ResourceBundle;

import app.datos.entidades.Localidad;
import app.datos.entidades.Provincia;
import app.datos.entidades.TipoDocumento;
import app.datos.entidades.TipoInmueble;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class AltaClienteController extends BaseController {

	@FXML
	private TextField textFieldNombre;

	@FXML
	private TextField textFieldApellido;

	@FXML
	private ComboBox<TipoDocumento> comboBoxTipoDocumento;

	@FXML
	private TextField textFieldNumeroDocumento;

	@FXML
	private TextField textFieldTelefono;

	@FXML
	private TextField textFieldCorreo;

	@FXML
	private ComboBox<Provincia> comboBoxProvincia;

	@FXML
	private ComboBox<Localidad> comboBoxLocalidad;

	@FXML
	private TextField textFieldBarrio;

	@FXML
	private ComboBox<TipoInmueble> comboBoxTipoInmueble;

	@FXML
	private TextField textFieldMonto;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
    }

}
