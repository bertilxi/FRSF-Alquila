package test.app.ui.controladores;

import app.ui.controladores.LoginController;
import app.ui.controladores.Resultado.ResultadoControlador;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LoginControllerTest {
    @Test
    public void registrar() throws Exception {
        LoginController loginController = new LoginController() {
            @Override
            public ResultadoControlador registrar() {
                tfNombre.setText("Dani");
                pfContra.setText("Dani");
                return super.registrar();
            }
        };
        assertEquals("", loginController.registrar());
    }

    @Test
    public void ingresar() throws Exception {

    }

}