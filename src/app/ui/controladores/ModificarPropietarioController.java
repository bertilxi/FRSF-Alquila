package app.ui.controladores;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import app.datos.entidades.Barrio;
import app.datos.entidades.Calle;
import app.datos.entidades.Localidad;
import app.datos.entidades.Pais;
import app.datos.entidades.Propietario;
import app.datos.entidades.Provincia;
import app.datos.entidades.TipoDocumento;
import app.excepciones.PersistenciaException;
import app.logica.resultados.ResultadoModificarPropietario;
import app.logica.resultados.ResultadoModificarPropietario.ErrorModificarPropietario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class ModificarPropietarioController extends BaseController {
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
	private ComboBox<TipoDocumento> comboBoxTipoDocumento;
	@FXML
	private ComboBox<Pais> comboBoxPais;
	@FXML
	private ComboBox<Provincia> comboBoxProvincia;
	@FXML
	private ComboBox<Localidad> comboBoxLocalidad;
	@FXML
	private ComboBox<Calle> comboBoxCalle;
	@FXML
	private ComboBox<Barrio> comboBoxBarrio;

	private ArrayList<TipoDocumento> listaTiposDeDocumento;

	private ArrayList<Localidad> listaLocalidades;

	private ArrayList<Provincia> listaProvincias;

	private ArrayList<Pais> listaPaises;

	private Propietario propietarioEnModificacion;

	public void setPropietarioEnModificacion(Propietario propietarioEnModificacion) {
		this.propietarioEnModificacion = propietarioEnModificacion;
		textFieldNombre.setText(propietarioEnModificacion.getNombre());
		textFieldApellido.setText(propietarioEnModificacion.getApellido());
		textFieldNumeroDocumento.setText(propietarioEnModificacion.getNumeroDocumento());
		textFieldAlturaCalle.setText(propietarioEnModificacion.getDireccion().getNumero());
		textFieldPiso.setText(propietarioEnModificacion.getDireccion().getPiso());
		textFieldDepartamento.setText(propietarioEnModificacion.getDireccion().getDepartamento());
		textFieldCorreoElectronico.setText(propietarioEnModificacion.getEmail());
		textFieldTelefono.setText(propietarioEnModificacion.getTelefono());
		comboBoxBarrio.setValue(propietarioEnModificacion.getDireccion().getBarrio());
		comboBoxCalle.setValue(propietarioEnModificacion.getDireccion().getCalle());
		comboBoxLocalidad.setValue(propietarioEnModificacion.getDireccion().getLocalidad());
		comboBoxPais.setValue(propietarioEnModificacion.getDireccion().getLocalidad().getProvincia().getPais());
		comboBoxProvincia.setValue(propietarioEnModificacion.getDireccion().getLocalidad().getProvincia());
		comboBoxTipoDocumento.setValue(propietarioEnModificacion.getTipoDocumento());
	}

	@FXML
	public void acceptAction() {

		StringBuilder error = new StringBuilder("");

		String nombre = textFieldNombre.getText().trim();
		String apellido = textFieldApellido.getText().trim();
		String numeroDocumento = textFieldNumeroDocumento.getText().trim();
		String alturaCalle = textFieldAlturaCalle.getText().trim();
		String piso = textFieldPiso.getText().trim();
		String departamento = textFieldDepartamento.getText().trim();
		String telefono = textFieldTelefono.getText().trim();
		String correoElectronico = textFieldCorreoElectronico.getText().trim();

		Localidad localidad = comboBoxLocalidad.getValue();
		TipoDocumento tipoDoc = comboBoxTipoDocumento.getValue();
		Barrio barrio = comboBoxBarrio.getValue();
		Calle calle = comboBoxCalle.getValue();

		if(nombre.isEmpty()){
			error.append("Inserte un nombre").append("\r\n");
		}
		if(apellido.isEmpty()){
			error.append("Inserte un apellido").append("\r\n ");
		}
		if(numeroDocumento.isEmpty()){
			error.append("Inserte un numero de documento").append("\r\n ");
		}
		if(alturaCalle.isEmpty()){
			error.append("Inserte una altura").append("\r\n ");
		}
		if(telefono.isEmpty()){
			error.append("Inserte un telefono").append("\r\n ");
		}

		if(!error.toString().isEmpty()){
			presentador.presentarError("Revise sus campos", error.toString(), stage);
		}
		else{

			propietarioEnModificacion.getDireccion().setNumero(alturaCalle)
					.setCalle(calle)
					.setBarrio(barrio)
					.setPiso(piso)
					.setDepartamento(departamento)
					.setLocalidad(localidad);

			propietarioEnModificacion.setNombre(nombre)
					.setApellido(apellido)
					.setTipoDocumento(tipoDoc)
					.setNumeroDocumento(numeroDocumento)
					.setTelefono(telefono)
					.setEmail(correoElectronico);

			try{
				ResultadoModificarPropietario resultado = coordinador.modificarPropietario(propietarioEnModificacion);
				if(resultado.hayErrores()){
					StringBuilder stringErrores = new StringBuilder();
					for(ErrorModificarPropietario err: resultado.getErrores()){
						switch(err) {
						case Formato_Nombre_Incorrecto:
							stringErrores.append("Formato de nombre incorrecto.\n");
							break;
						case Formato_Apellido_Incorrecto:
							stringErrores.append("Formato de apellido incorrecto.\n");
							break;
						case Formato_Telefono_Incorrecto:
							stringErrores.append("Formato de teléfono incorrecto.\n");
							break;
						case Formato_Documento_Incorrecto:
							stringErrores.append("Tipo y formato de documento incorrecto.\n");
							break;
						case Formato_Email_Incorrecto:
							stringErrores.append("Formato de email incorrecto.\n");
							break;
						case Formato_Direccion_Incorrecto:
							stringErrores.append("Formato de dirección incorrecto.\n");
							break;
						case Ya_Existe_Propietario:
							stringErrores.append("Otro cliente posee ese tipo y número de documento.\n");
							break;
						}
					}
					presentador.presentarError("No se pudo modificar el propietario", stringErrores.toString(), stage);
				}
			} catch(PersistenciaException e){
				presentador.presentarExcepcion(e, stage);
			}
		}
	}

	@FXML
	public void cancelAction(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		listaLocalidades = new ArrayList<>();
		listaProvincias = new ArrayList<>();
		listaPaises = new ArrayList<>();
		listaTiposDeDocumento = new ArrayList<>();
		try{
			listaTiposDeDocumento = coordinador.obtenerTiposDeDocumento();
		} catch(PersistenciaException e){
			presentador.presentarExcepcion(e, stage);
		}
		comboBoxTipoDocumento.getItems().addAll(listaTiposDeDocumento);
		try{
			listaPaises = coordinador.obtenerPaises();
		} catch(PersistenciaException e){
			presentador.presentarExcepcion(e, stage);
		}
		comboBoxPais.getItems().addAll(listaPaises);
		comboBoxPais.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> actualizarProvincias(newValue));
		comboBoxProvincia.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> actualizarLocalidades(newValue));
	}

	private void actualizarLocalidades(Provincia provincia) {
		try{
			listaLocalidades = coordinador.obtenerLocalidadesDe(provincia);
		} catch(PersistenciaException e){
			presentador.presentarExcepcion(e, stage);
		}
		comboBoxLocalidad.getItems().addAll(listaLocalidades);
	}

	private void actualizarProvincias(Pais pais) {
		try{
			listaProvincias = coordinador.obtenerProvinciasDe(pais);
		} catch(PersistenciaException e){
			presentador.presentarExcepcion(e, stage);
		}
		comboBoxProvincia.getItems().addAll(listaProvincias);
	}
}
