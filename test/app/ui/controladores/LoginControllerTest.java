package app.ui.controladores;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import app.ui.controladores.resultado.ResultadoControlador;

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