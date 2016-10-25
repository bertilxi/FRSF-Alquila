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

public class LoginController extends WindowTitleController {

	public static final String URLVista = "/app/ui/vistas/Login.fxml";

	protected CoordinadorJavaFX coordinador = new CoordinadorJavaFX();

	@FXML
	protected TextField tfDNI;

	@FXML
	protected PasswordField pfContra;

	protected Boolean desatendido = false;

	@FXML
	public void registrar() {

	}

	@FXML
	public ResultadoControlador ingresar() {
		Set<ErrorControlador> erroresControlador = new HashSet<>();
		ResultadoAutenticacion resultado = null;
		Boolean hayErrores;
		DatosLogin datos;
		String errores = "";

		//Toma de datos de la vista
		String dni = tfDNI.getText().trim();
		char[] pass = pfContra.getText().toCharArray();
		if(dni.isEmpty() || pass.length < 1){
			if(!desatendido){
				new VentanaError("No se ha podido iniciar sesión", "Campos vacíos.", null); //apilador.getStage()
			}
			return new ResultadoControlador(ErrorControlador.Campos_Vacios);
		}
		datos = new DatosLogin(dni, pass);

		//Inicio transacción al gestor
		try{
			resultado = coordinador.autenticarVendedor(datos);
		} catch(PersistenciaException e){
			if(!desatendido){
				ManejadorExcepciones.presentarExcepcion(e, null); //apilador.getStage()
			}
			return new ResultadoControlador(ErrorControlador.Error_Persistencia);
		} catch(Exception e){
			if(!desatendido){
				ManejadorExcepciones.presentarExcepcionInesperada(e, null); //apilador.getStage()
			}
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
				if(!desatendido){
					new VentanaError("No se ha podido iniciar sesión", errores, null); //apilador.getStage()
				}
			}
		}
		else{
			//Operacion exitosa
			// Ir otra pantalla
			// ControladorRomano.cambiarScene(MenuAdministracionController.URLVista, apilador, coordinador);
		}
		return new ResultadoControlador(erroresControlador.toArray(new ErrorControlador[0]));
	}

}
