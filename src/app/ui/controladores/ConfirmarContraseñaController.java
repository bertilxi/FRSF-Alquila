package app.ui.controladores;

import java.net.URL;
import java.util.ResourceBundle;

import app.datos.clases.DatosLogin;
import app.excepciones.PersistenciaException;
import app.logica.resultados.ResultadoAutenticacion;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class ConfirmarContraseñaController extends OlimpoController  {

	public static final String URLVista = "/app/ui/vistas/confirmarContraseña.fxml";

	private Stage dialogStage;

	@FXML
	protected PasswordField pfContra;

	private boolean correcto;

	public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
	}

	public boolean isCorrecto() {
        return correcto;
    }

	@Override
	protected void inicializar(URL location, ResourceBundle resources) {
		correcto = false;
	}

	/**
	 * Acción que se ejecuta al apretar el botón aceptar.
	 *
	 * Valida que se haya insertado la contraseña correcta
	 */
	@FXML
	public void acceptAction() {
		char[] pass = pfContra.getText().toCharArray();
		if(pass.length < 1){
			presentador.presentarError("No se ha podido verificar el vendedor", "Inserte una contraseña.", dialogStage);
		}
		DatosLogin datos = new DatosLogin(vendedorLogueado.getTipoDocumento(), vendedorLogueado.getNumeroDocumento(), pass);

		try{
			ResultadoAutenticacion resultado = coordinador.autenticarVendedor(datos);
			if(resultado.hayErrores()) {
				presentador.presentarError("No se pudo verificar el vendedor", "Contraseña incorrecta", dialogStage);
			} else {
				correcto = true;
				dialogStage.close();
			}
		} catch(PersistenciaException e){
			presentador.presentarExcepcion(e, stage);
		} catch(Exception e){
			presentador.presentarExcepcionInesperada(e, stage);
		}
	}

	/**
	 * Acción que se ejecuta al presionar el botón cancelar.
	 * Se vuelve a la pantalla alta venta.
	 */
	@FXML
	private void cancelAction() {
		dialogStage.close();
	}
}
