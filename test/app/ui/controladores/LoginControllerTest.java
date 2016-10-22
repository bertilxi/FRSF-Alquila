package app.ui.controladores;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import app.ui.controladores.resultado.ResultadoControlador;

public class LoginControllerTest {

	@Test
	public void ingresar() throws Exception {
		LoginController loginController = new LoginController() {
			@Override
			public ResultadoControlador ingresar() {
				tfNombre.setText("Dani");
				pfContra.setText("Dani");
				return super.ingresar();
			}
		};
		assertEquals(new ResultadoControlador(), loginController.ingresar());

	}

}