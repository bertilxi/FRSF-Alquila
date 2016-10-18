package app.ui.controladores;

import app.ui.controladores.Resultado.ResultadoControlador;

import static org.junit.Assert.*;

public class LoginControllerTest {
    @org.junit.Test
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

    @org.junit.Test
    public void ingresar() throws Exception {

    }

}