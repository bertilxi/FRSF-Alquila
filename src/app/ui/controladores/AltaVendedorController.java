package app.ui.controladores;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.Resource;

import app.datos.entidades.TipoDocumento;
import app.datos.entidades.Vendedor;
import app.excepciones.GestionException;
import app.excepciones.PersistenciaException;
import app.logica.gestores.GestorDatos;
import app.logica.gestores.GestorVendedor;
import app.logica.resultados.ResultadoCrearVendedor;
import app.logica.resultados.ResultadoCrearVendedor.ErrorCrearVendedor;
import app.ui.componentes.VentanaError;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class AltaVendedorController extends BaseController {

	public static final String URLVista = "/app/ui/vistas/altaVendedor.fxml";

	@FXML
	protected TextField textFieldNombre;
	@FXML
	protected TextField textFieldApellido;
	@FXML
	protected TextField textFieldNumeroDocumento;
	@FXML
	protected PasswordField passwordFieldContraseña;
	@FXML
	protected PasswordField passwordFieldRepiteContraseña;
	@FXML
	protected ComboBox<TipoDocumento> comboBoxTipoDocumento;

	protected ArrayList<TipoDocumento> listaTiposDeDocumento;

	@Resource
	protected GestorDatos gestorDatos;

	@Resource
	protected GestorVendedor gestorVendedor;

	public ResultadoCrearVendedor acceptAction() throws PersistenciaException, GestionException {

		StringBuilder error = new StringBuilder("");

		String nombre = textFieldNombre.getText().trim();
		String apellido = textFieldApellido.getText().trim();
		String numeroDocumento = textFieldNumeroDocumento.getText().trim();
		String password1 = passwordFieldContraseña.getText();
		String password2 = passwordFieldRepiteContraseña.getText();
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

		if(password1.isEmpty() && password2.isEmpty()){
			error.append("Inserte su contraseña").append("\r\n");
		}

		if(!password1.isEmpty() && password2.isEmpty()){
			error.append("Inserte su contraseña nuevamente").append("\r\n");
		}

		if(!password1.equals(password2)){
			error.append("Sus contraseñas no coinciden, Ingreselas nuevamente").append("\r\n ");
		}

		if(!error.toString().isEmpty()){
			new VentanaError("Revise sus campos", error.toString());
		}
		else{
			Vendedor vendedor = new Vendedor();
			vendedor.setId(null)
					.setNombre(nombre)
					.setApellido(apellido)
					.setNumeroDocumento(numeroDocumento)
					.setTipoDocumento(tipoDoc)
					.setPassword(" "); //TODO ver manejo contraseña

			ResultadoCrearVendedor resultadoCrearVendedor = null;

			try{
				resultadoCrearVendedor = gestorVendedor.crearVendedor(vendedor);
				error.delete(0, error.length());
				List<ErrorCrearVendedor> listaErrores = resultadoCrearVendedor.getErrores();
				if(listaErrores.contains(ErrorCrearVendedor.Formato_Nombre_Incorrecto)){
					error.append("Nombre Incorrecto").append("\r\n");
				}
				if(listaErrores.contains(ErrorCrearVendedor.Formato_Apellido_Incorrecto)){
					error.append("Apellido Incorrecto").append("\r\n");
				}
				if(listaErrores.contains(ErrorCrearVendedor.Formato_Documento_Incorrecto)){
					error.append("Documento Incorrecto").append("\r\n");
				}
				if(listaErrores.contains(ErrorCrearVendedor.Ya_Existe_Vendedor)){
					error.append("Ya existe un vendedor registrado con ese documento").append("\r\n");
				}

				if(!error.toString().isEmpty()){
					new VentanaError("Revise sus campos", error.toString());
				}

			} catch(PersistenciaException e){

			} catch(Exception e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return resultadoCrearVendedor;
		}

		return null;

	}

	public void cancelAction() {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);

		//		listaTiposDeDocumento = new ArrayList<TipoDocumento>();
		//
		//		try{
		//			listaTiposDeDocumento = gestorDatos.obtenerTiposDeDocumento();
		//		} catch(PersistenciaException e){
		//		}
		//		comboBoxTipoDocumento.getItems().addAll(listaTiposDeDocumento);

	}
}
