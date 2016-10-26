package app.ui.controladores;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import app.datos.entidades.Localidad;
import app.datos.entidades.Pais;
import app.datos.entidades.Provincia;
import app.datos.entidades.TipoDocumento;
import app.datos.entidades.TipoInmueble;
import app.excepciones.PersistenciaException;
import app.logica.gestores.GestorDatos;
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
	private ComboBox<Pais> comboBoxPais;

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

	private ArrayList<TipoDocumento> listaTiposDeDocumento;

	private ArrayList<TipoInmueble> listaTiposInmueble;

	private ArrayList<Localidad> listaLocalidades;

	private ArrayList<Provincia> listaProvincias;

	private ArrayList<Pais> listaPaises;

	private GestorDatos gestorDatos;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        try {
			listaTiposDeDocumento = gestorDatos.obtenerTiposDeDocumento();
		} catch (PersistenciaException e) {
			// TODO mostrar error inesperado
		}
        comboBoxTipoDocumento.getItems().addAll(listaTiposDeDocumento);

        try {
			listaTiposInmueble = gestorDatos.obtenerTiposInmueble();
		} catch (PersistenciaException e) {
			// TODO mostrar error inesperado
		}
        comboBoxTipoInmueble.getItems().addAll(listaTiposInmueble);

        try {
			listaPaises = gestorDatos.obtenerPaises();
		} catch (PersistenciaException e) {
			// TODO mostrar error inesperado
		}
        comboBoxPais.getItems().addAll(listaPaises);

        comboBoxPais.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> actualizarProvincias(newValue));

        comboBoxProvincia.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> actualizarLocalidades(newValue));
    }

	private void actualizarLocalidades(Provincia provincia) {
		try {
			listaLocalidades = gestorDatos.obtenerLocalidadesDe(provincia);
		} catch (PersistenciaException e) {
			// TODO mostrar error inesperado
		}
        comboBoxLocalidad.getItems().addAll(listaLocalidades);
	}

	private void actualizarProvincias(Pais pais) {
		try {
			listaProvincias = gestorDatos.obtenerProvinciasDe(pais);
		} catch (PersistenciaException e) {
			// TODO mostrar error inesperado
		}
        comboBoxProvincia.getItems().addAll(listaProvincias);
	}

}
