package app.ui.controladores;

import app.datos.clases.DatosLogin;
import app.excepciones.ManejadorExcepciones;
import app.excepciones.PersistenciaException;
import app.logica.CoordinadorJavaFX;
import app.logica.resultados.ResultadoLogin;
import app.logica.resultados.ResultadoLogin.ErrorResultadoLogin;
import app.ui.componentes.VentanaError;
import app.ui.controladores.Resultado.ResultadoControlador;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController extends WindowTitleController{

    @FXML
    protected TextField tfNombre;

    @FXML
    protected PasswordField pfContra;


    @FXML
    public ResultadoControlador registrar() {
        ResultadoControlador salida = new ResultadoControlador();
        return salida;
    }

    @FXML
    public ResultadoControlador ingresar() {
        //TODO borrar para activar login
        //Ir siguiente pantalla
        ResultadoControlador salida = new ResultadoControlador();
        if (true) {
            return salida;
        }
        @SuppressWarnings("unused")
        CoordinadorJavaFX coordinador = new CoordinadorJavaFX();
        //borrar

        ResultadoLogin resultado = null;
        Boolean hayErrores;
        DatosLogin datos;
        String errores = "";

        //Toma de datos de la vista
        String user = tfNombre.getText().trim();
        char[] pass = pfContra.getText().toCharArray();
        if (user.isEmpty() || pass.length < 1) {
            new VentanaError("No se ha podido iniciar sesión", "Campos vacíos.", new Stage()); //apilador.getStage()
            return salida;
        }
        datos = new DatosLogin(user, pass);

        //Inicio transacción al gestor
        try {
            resultado = coordinador.loguearVendedor(datos);
        } catch (PersistenciaException e) {
            ManejadorExcepciones.presentarExcepcion(e, new Stage()); //apilador.getStage()
            return salida;
        } catch (Exception e) {
            ManejadorExcepciones.presentarExcepcionInesperada(e, new Stage()); //apilador.getStage()
            return salida;
        }

        //Tratamiento de errores
        hayErrores = resultado.hayErrores();
        if (hayErrores) {
            for (ErrorResultadoLogin r : resultado.getErrores()) {
                switch (r) {
                    case Datos_Incorrectos:
                        errores += "Datos inválidos al iniciar sesión.\n";
                        break;
                }
            }
            if (!errores.isEmpty()) {
                new VentanaError("No se ha podido iniciar sesión", errores, new Stage()); //apilador.getStage()
            }
        } else {
            //Operacion exitosa
            // Ir otra pantalla
            // ControladorRomano.cambiarScene(MenuAdministracionController.URLVista, apilador, coordinador);
        }
        return salida;
    }

}
