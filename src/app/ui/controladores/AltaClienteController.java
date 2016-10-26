package app.ui.controladores;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.ResourceBundle;

import app.datos.entidades.*;
import app.excepciones.PersistenciaException;
import app.logica.gestores.GestorDatos;
import app.logica.resultados.ResultadoCrearCliente;
import app.ui.componentes.VentanaError;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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

	@FXML
	private Button buttonCargatInmueble;

	// todo: falta un comboBox barrios

	private ArrayList<TipoDocumento> listaTiposDeDocumento;

	private ArrayList<TipoInmueble> listaTiposInmueble;

	private ArrayList<Localidad> listaLocalidades;

	private ArrayList<Provincia> listaProvincias;

	private ArrayList<Pais> listaPaises;

	private GestorDatos gestorDatos;

	public ResultadoCrearCliente acceptAction() {

		StringBuilder error = new StringBuilder("");

		String nombre = textFieldNombre.getText().trim();
		String apellido = textFieldApellido.getText().trim();
		String numeroDocumento = textFieldNumeroDocumento.getText().trim();
		String telefono = textFieldTelefono.getText().trim();
		String correo = textFieldCorreo.getText().trim();
		String barrio = textFieldBarrio.getText().trim();
		String monto = textFieldMonto.getText().trim();
		TipoDocumento tipoDoc = comboBoxTipoDocumento.getValue();
		TipoInmueble tipoInmueble = comboBoxTipoInmueble.getValue();
		Pais pais = comboBoxPais.getValue();
		Provincia provincia = comboBoxProvincia.getValue();
		Localidad localidad = comboBoxLocalidad.getValue();

		if(nombre.isEmpty()){
			error.append("Inserte un nombre").append("\r\n ");
		}
		if(apellido.isEmpty()){
			error.append("Inserte un apellido").append("\r\n ");
		}
		if(tipoDoc == null){
			error.append("Elija un tipo de documento").append("\r\n ");
		}
		if(numeroDocumento.isEmpty()){
			error.append("Inserte un numero de documento").append("\r\n ");
		}
		if(telefono.isEmpty()){
			error.append("Inserte un telefono").append("\r\n ");
		}
		if(correo.isEmpty()){
			error.append("Inserte un correo").append("\r\n ");
		}
		if(barrio.isEmpty()){
			error.append("Inserte un barrio").append("\r\n ");
		}

		if(!error.toString().isEmpty()){
			VentanaError ventanaError = new VentanaError("Revise sus campos", error.toString());
		}
		else{
			Vendedor vendedor = new Vendedor();
			vendedor.setId(null)
					.setNombre(nombre)
					.setApellido(apellido)
					.setNumeroDocumento(numeroDocumento)
					.setTipoDocumento(tipoDoc);

			// mandar objeto al persistidor
		}

		return null;

	}

	public void cargarInmueble(ActionEvent event) throws IOException {
		Stage stage = new Stage();
		URL location = getClass().getResource("/app/ui/vistas/inmuebleBuscado.fxml");
		FXMLLoader loader = createFXMLLoader(location);
		Parent root = loader.load(location.openStream());
		stage.setScene(new Scene(root));
		stage.setTitle("My modal window");
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.showAndWait();

	}

	public FXMLLoader createFXMLLoader(URL location) {
		return new FXMLLoader(location, null, new JavaFXBuilderFactory(), null, Charset.forName(FXMLLoader.DEFAULT_CHARSET_NAME));
	}

	public void cancelAction() {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
	}

	/*
	 * @Override
	 * public void initialize(URL location, ResourceBundle resources) {
	 * super.initialize(location, resources);
	 * 
	 * listaLocalidades = new ArrayList<Localidad>();
	 * listaProvincias = new ArrayList<Provincia>();
	 * listaPaises = new ArrayList<Pais>();
	 * listaTiposDeDocumento = new ArrayList<TipoDocumento>();
	 * listaTiposInmueble = new ArrayList<TipoInmueble>();
	 * 
	 * try {
	 * listaTiposDeDocumento = gestorDatos.obtenerTiposDeDocumento();
	 * } catch (PersistenciaException e) {
	 * // TODO mostrar error inesperado
	 * }
	 * comboBoxTipoDocumento.getItems().addAll(listaTiposDeDocumento);
	 * 
	 * try {
	 * listaTiposInmueble = gestorDatos.obtenerTiposInmueble();
	 * } catch (PersistenciaException e) {
	 * // TODO mostrar error inesperado
	 * }
	 * comboBoxTipoInmueble.getItems().addAll(listaTiposInmueble);
	 * 
	 * try {
	 * listaPaises = gestorDatos.obtenerPaises();
	 * } catch (PersistenciaException e) {
	 * // TODO mostrar error inesperado
	 * }
	 * comboBoxPais.getItems().addAll(listaPaises);
	 * 
	 * comboBoxPais.getSelectionModel().selectedItemProperty().addListener(
	 * (observable, oldValue, newValue) -> actualizarProvincias(newValue));
	 * 
	 * comboBoxProvincia.getSelectionModel().selectedItemProperty().addListener(
	 * (observable, oldValue, newValue) -> actualizarLocalidades(newValue));
	 * }
	 */

	private void actualizarLocalidades(Provincia provincia) {
		try{
			listaLocalidades = gestorDatos.obtenerLocalidadesDe(provincia);
		} catch(PersistenciaException e){
			// TODO mostrar error inesperado
		}
		comboBoxLocalidad.getItems().addAll(listaLocalidades);
	}

	private void actualizarProvincias(Pais pais) {
		try{
			listaProvincias = gestorDatos.obtenerProvinciasDe(pais);
		} catch(PersistenciaException e){
			// TODO mostrar error inesperado
		}
		comboBoxProvincia.getItems().addAll(listaProvincias);
	}

}
