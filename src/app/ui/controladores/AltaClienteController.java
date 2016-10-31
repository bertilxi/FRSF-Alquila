package app.ui.controladores;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.ResourceBundle;

import app.datos.entidades.Cliente;
import app.datos.entidades.TipoDocumento;
import app.excepciones.EntidadExistenteConEstadoBajaException;
import app.excepciones.GestionException;
import app.excepciones.PersistenciaException;
import app.logica.gestores.GestorCliente;
import app.logica.gestores.GestorDatos;
import app.logica.resultados.ResultadoCrearCliente;
import app.logica.resultados.ResultadoCrearCliente.ErrorCrearCliente;
import app.ui.PresentadorExcepciones;
import app.ui.componentes.VentanaConfirmacion;
import app.ui.componentes.VentanaError;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
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
	private Button buttonCargarInmueble;

	private ArrayList<TipoDocumento> listaTiposDeDocumento;

	private GestorDatos gestorDatos;

	private GestorCliente gestorCliente;

	@FXML
	public void acceptAction() {

		StringBuilder error = new StringBuilder("");

		String nombre = textFieldNombre.getText().trim();
		String apellido = textFieldApellido.getText().trim();
		String numeroDocumento = textFieldNumeroDocumento.getText().trim();
		String telefono = textFieldTelefono.getText().trim();
		TipoDocumento tipoDoc = comboBoxTipoDocumento.getValue();

		if(nombre.isEmpty()){
			error.append("Inserte un nombre").append("\r\n");
		}
		if(apellido.isEmpty()){
			error.append("Inserte un apellido").append("\r\n");
		}
		if(tipoDoc == null){
			error.append("Elija un tipo de documento").append("\r\n");
		}
		if(numeroDocumento.isEmpty()){
			error.append("Inserte un numero de documento").append("\r\n");
		}
		if(telefono.isEmpty()){
			error.append("Inserte un telefono").append("\r\n");
		}

		if(!error.toString().isEmpty()){
			new VentanaError("Revise sus campos", error.toString(), null); //falta el stage
		}
		else{
			Cliente cliente = new Cliente();
			cliente.setNombre(nombre)
					.setApellido(apellido)
					.setTipoDocumento(tipoDoc)
					.setNumeroDocumento(numeroDocumento)
					.setTelefono(telefono);

			try{
				ResultadoCrearCliente resultado = gestorCliente.crearCliente(cliente);
				if(resultado.hayErrores()){
					StringBuilder stringErrores = new StringBuilder();
					for(ErrorCrearCliente err: resultado.getErrores()){
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
						case Ya_Existe_Cliente:
							stringErrores.append("Ya existe un cliente con ese tipo y número de documento.\n");
							break;
						}
					}
					new VentanaError("No se pudo crear el cliente", stringErrores.toString(), null); //falta el stage
				}
			} catch(GestionException e){
				if(e.getClass().equals(EntidadExistenteConEstadoBajaException.class)){
					VentanaConfirmacion ventana = new VentanaConfirmacion("El cliente ya existe", "El cliente ya existía anteriormente pero fué dado de baja.\n ¿Desea volver a darle de alta?");
					if(ventana.acepta()){
						//TODO mandar a la vista modificar cliente
					}
				}
			} catch(PersistenciaException e){
				PresentadorExcepciones.presentarExcepcion(e, null); //falta el stage
			}
		}
	}

	public void cargarInmueble() throws IOException {
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

	@FXML
	public void cancelAction(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);

		listaTiposDeDocumento = new ArrayList<>();

		try{
			listaTiposDeDocumento = gestorDatos.obtenerTiposDeDocumento();
		} catch(PersistenciaException e){
			PresentadorExcepciones.presentarExcepcion(e, null); //falta el stage
		}
		comboBoxTipoDocumento.getItems().addAll(listaTiposDeDocumento);
	}
}
