package app.ui.controladores;

import java.util.HashSet;
import java.util.Set;

import app.datos.clases.DatosLogin;
import app.excepciones.ManejadorExcepciones;
import app.excepciones.PersistenciaException;
import app.logica.CoordinadorJavaFX;
import app.logica.resultados.ResultadoAutenticacion;
import app.logica.resultados.ResultadoAutenticacion.ErrorAutenticacion;
import app.ui.componentes.VentanaError;
import app.ui.controladores.resultado.ResultadoControlador;
import app.ui.controladores.resultado.ResultadoControlador.ErrorControlador;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController extends WindowTitleController {

	public static final String URLVista = "/app/ui/vistas/Login.fxml";

	@FXML
	protected TextField tfNombre;

	@FXML
	protected PasswordField pfContra;

	@FXML
	public void registrar() {

	}

	@FXML
	public ResultadoControlador ingresar() {
		//TODO borrar para activar login
		//Ir siguiente pantalla
		if(true){
			return new ResultadoControlador();
		}
		@SuppressWarnings("unused")
		CoordinadorJavaFX coordinador = new CoordinadorJavaFX();
		//borrar

		Set<ErrorControlador> erroresControlador = new HashSet<>();
		ResultadoAutenticacion resultado = null;
		Boolean hayErrores;
		DatosLogin datos;
		String errores = "";

		//Toma de datos de la vista
		String user = tfNombre.getText().trim();
		char[] pass = pfContra.getText().toCharArray();
		if(user.isEmpty() || pass.length < 1){
			new VentanaError("No se ha podido iniciar sesión", "Campos vacíos.", new Stage()); //apilador.getStage()
			return new ResultadoControlador(ErrorControlador.Campos_Vacios);
		}
		datos = new DatosLogin(user, pass);

		//Inicio transacción al gestor
		try{
			resultado = coordinador.loguearVendedor(datos);
		} catch(PersistenciaException e){
			ManejadorExcepciones.presentarExcepcion(e, new Stage()); //apilador.getStage()
			return new ResultadoControlador(ErrorControlador.Error_Persistencia);
		} catch(Exception e){
			ManejadorExcepciones.presentarExcepcionInesperada(e, new Stage()); //apilador.getStage()
			return new ResultadoControlador(ErrorControlador.Error_Desconocido);
		}

		//Tratamiento de errores
		hayErrores = resultado.hayErrores();
		if(hayErrores){
			for(ErrorAutenticacion r: resultado.getErrores()){
				switch(r) {
				case Datos_Incorrectos:
					errores += "Datos inválidos al iniciar sesión.\n";
					erroresControlador.add(ErrorControlador.Datos_Incorrectos);
					break;
				}
			}

			if(!errores.isEmpty()){
				new VentanaError("No se ha podido iniciar sesión", errores, new Stage()); //apilador.getStage()
			}
			return new ResultadoControlador(erroresControlador.toArray(new ErrorControlador[0]));
		}
		else{
			//Operacion exitosa
			// Ir otra pantalla
			// ControladorRomano.cambiarScene(MenuAdministracionController.URLVista, apilador, coordinador);
			return new ResultadoControlador();
		}
	}

}
