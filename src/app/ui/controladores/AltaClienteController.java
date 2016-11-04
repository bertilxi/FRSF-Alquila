package app.ui.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import app.datos.entidades.Cliente;
import app.datos.entidades.TipoDocumento;
import app.excepciones.EntidadExistenteConEstadoBajaException;
import app.excepciones.GestionException;
import app.excepciones.PersistenciaException;
import app.logica.resultados.ResultadoCrearCliente;
import app.logica.resultados.ResultadoCrearCliente.ErrorCrearCliente;
import app.ui.componentes.ventanas.VentanaConfirmacion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class AltaClienteController extends OlimpoController {

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
			Cliente cliente = new Cliente();
			cliente.setNombre(nombre)
					.setApellido(apellido)
					.setTipoDocumento(tipoDoc)
					.setNumeroDocumento(numeroDocumento)
					.setTelefono(telefono);

			try{
				ResultadoCrearCliente resultado = coordinador.crearCliente(cliente);
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
					presentador.presentarError("No se pudo crear el cliente", stringErrores.toString(), stage);
				}
			} catch(GestionException e){
				if(e.getClass().equals(EntidadExistenteConEstadoBajaException.class)){
					VentanaConfirmacion ventana = presentador.presentarConfirmacion("El cliente ya existe", "El cliente ya existía anteriormente pero fué dado de baja.\n ¿Desea volver a darle de alta?", stage);
					if(ventana.acepta()){
						//TODO mandar a la vista modificar cliente
					}
				}
			} catch(PersistenciaException e){
				presentador.presentarExcepcion(e, stage);
			}
		}
	}

	public void cargarInmueble() throws IOException {
		//TODO hacer esto

	}

	@FXML
	public void cancelAction(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
	}

	@Override
	public void inicializar(URL location, ResourceBundle resources) {
		listaTiposDeDocumento = new ArrayList<>();

		try{
			listaTiposDeDocumento = coordinador.obtenerTiposDeDocumento();
		} catch(PersistenciaException e){
			presentador.presentarExcepcion(e, stage);
		}
		comboBoxTipoDocumento.getItems().addAll(listaTiposDeDocumento);
	}
}
