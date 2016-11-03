package app.ui.controladores;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.ResourceBundle;

import app.datos.entidades.Cliente;
import app.datos.entidades.TipoDocumento;
import app.excepciones.PersistenciaException;
import app.logica.resultados.ResultadoModificarCliente;
import app.logica.resultados.ResultadoModificarCliente.ErrorModificarCliente;
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

public class ModificarClienteController extends OlimpoController {

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

	private Cliente clienteEnModificacion;

	public void setClienteEnModificacion(Cliente clienteEnModificacion) {
		this.clienteEnModificacion = clienteEnModificacion;
		textFieldNombre.setText(clienteEnModificacion.getNombre());
		textFieldApellido.setText(clienteEnModificacion.getApellido());
		textFieldTelefono.setText(clienteEnModificacion.getTelefono());
		textFieldNumeroDocumento.setText(clienteEnModificacion.getNumeroDocumento());
		comboBoxTipoDocumento.setValue(clienteEnModificacion.getTipoDocumento());
	}

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
			presentador.presentarError("Revise sus campos", error.toString(), stage);
		}
		else{
			clienteEnModificacion.setNombre(nombre)
					.setApellido(apellido)
					.setTipoDocumento(tipoDoc)
					.setNumeroDocumento(numeroDocumento)
					.setTelefono(telefono);

			try{
				ResultadoModificarCliente resultado = coordinador.modificarCliente(clienteEnModificacion);
				if(resultado.hayErrores()){
					StringBuilder stringErrores = new StringBuilder();
					for(ErrorModificarCliente err: resultado.getErrores()){
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
						case Otro_Cliente_Posee_Mismo_Documento_Y_Tipo:
							stringErrores.append("Otro cliente ya posee ese tipo y número de documento.\n");
							break;
						}
					}
					presentador.presentarError("No se pudo modificar el cliente", stringErrores.toString(), stage);
				}
			} catch(PersistenciaException e){
				presentador.presentarExcepcion(e, stage);
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
		listaTiposDeDocumento = new ArrayList<>();

		try{
			listaTiposDeDocumento = coordinador.obtenerTiposDeDocumento();
		} catch(PersistenciaException e){
			presentador.presentarExcepcion(e, stage);
		}
		comboBoxTipoDocumento.getItems().addAll(listaTiposDeDocumento);
	}
}
