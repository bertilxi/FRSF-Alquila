package app.ui.controladores;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import app.datos.entidades.Barrio;
import app.datos.entidades.Calle;
import app.datos.entidades.Direccion;
import app.datos.entidades.Localidad;
import app.datos.entidades.Pais;
import app.datos.entidades.Propietario;
import app.datos.entidades.Provincia;
import app.datos.entidades.TipoDocumento;
import app.excepciones.EntidadExistenteConEstadoBajaException;
import app.excepciones.GestionException;
import app.excepciones.ManejadorExcepciones;
import app.excepciones.PersistenciaException;
import app.logica.ValidadorFormato;
import app.logica.gestores.GestorDatos;
import app.logica.gestores.GestorPropietario;
import app.logica.resultados.ResultadoCrearPropietario;
import app.logica.resultados.ResultadoCrearPropietario.ErrorCrearPropietario;
import app.ui.componentes.VentanaConfirmacion;
import app.ui.componentes.VentanaError;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class AltaPropietarioController extends BaseController {

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

	private GestorDatos gestorDatos;

	private GestorPropietario gestorPropietario;

	public EventHandler<KeyEvent> validation() {
		return e -> {

			String text = textFieldNombre.getText().trim();
			Boolean match = ValidadorFormato.validarNombre(text);

			if(text.length() >= 30){
				e.consume();
			}

			if(!match){
				e.consume();
			}
		};
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
			new VentanaError("Revise sus campos", error.toString(), null); //falta el stage
		}
		else{

			Direccion direccion = new Direccion();
			direccion.setNumero(alturaCalle)
					.setCalle(calle)
					.setBarrio(barrio)
					.setPiso(piso)
					.setDepartamento(departamento)
					.setLocalidad(localidad);

			Propietario propietario = new Propietario();
			propietario.setNombre(nombre)
					.setApellido(apellido)
					.setTipoDocumento(tipoDoc)
					.setNumeroDocumento(numeroDocumento)
					.setTelefono(telefono)
					.setEmail(correoElectronico)
					.setDireccion(direccion);

			try{
				ResultadoCrearPropietario resultado = gestorPropietario.crearPropietario(propietario);
				if(resultado.hayErrores()){
					StringBuilder stringErrores = new StringBuilder();
					for(ErrorCrearPropietario err: resultado.getErrores()){
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
							stringErrores.append("Ya existe un cliente con ese tipo y número de documento.\n");
							break;
						}
					}
					new VentanaError("No se pudo crear el propietario", stringErrores.toString(), null); //falta el stage
				}
			} catch(GestionException e){
				if(e.getClass().equals(EntidadExistenteConEstadoBajaException.class)){
					VentanaConfirmacion ventana = new VentanaConfirmacion("El propietario ya existe", "El propietario ya existía anteriormente pero fué dado de baja.\n ¿Desea volver a darle de alta?");
					if(ventana.acepta()){
						//TODO mandar a la vista modificar propietario
					}
				}
			} catch(PersistenciaException e){
				ManejadorExcepciones.presentarExcepcion(e, null); //falta el stage
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
			listaTiposDeDocumento = gestorDatos.obtenerTiposDeDocumento();
		} catch(PersistenciaException e){
			ManejadorExcepciones.presentarExcepcion(e, null); //falta el stage
		}
		comboBoxTipoDocumento.getItems().addAll(listaTiposDeDocumento);
		try{
			listaPaises = gestorDatos.obtenerPaises();
		} catch(PersistenciaException e){
			ManejadorExcepciones.presentarExcepcion(e, null); //falta el stage
		}
		comboBoxPais.getItems().addAll(listaPaises);
		comboBoxPais.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> actualizarProvincias(newValue));
		comboBoxProvincia.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> actualizarLocalidades(newValue));
	}

	private void actualizarLocalidades(Provincia provincia) {
		try{
			listaLocalidades = gestorDatos.obtenerLocalidadesDe(provincia);
		} catch(PersistenciaException e){
			ManejadorExcepciones.presentarExcepcion(e, null); //falta el stage
		}
		comboBoxLocalidad.getItems().addAll(listaLocalidades);
	}

	private void actualizarProvincias(Pais pais) {
		try{
			listaProvincias = gestorDatos.obtenerProvinciasDe(pais);
		} catch(PersistenciaException e){
			ManejadorExcepciones.presentarExcepcion(e, null); //falta el stage
		}
		comboBoxProvincia.getItems().addAll(listaProvincias);
	}
}
