package app.comun;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class ValidadorFormatoTest {

	@Test
	@Parameters({ "Juan, true",
			"Juan Pablo, true",
			", false",
			"Nombre Incorrecto por mas de treinta caracteres, false",
			"Nombre Incorrect0 por numer0s, false",
			"ácéntós íú y ñ, true" })
	public void validarNombreTest(String nombre, boolean esperado) {
		assertEquals(esperado, new ValidadorFormato().validarNombre(nombre));
	}

	@Test
	@Parameters({ "juan@juan.com, true",
			"Juan.Pablo@a.com, true",
			", false",
			"email_a_a@juan.com, true",
			"emailmayoramuchoscaracteres@juan.com, false",
			"sinarroba, false",
			"caracte^r~@juan.com, true",
			"ácéntós@juan.com, true" })
	public void validarEmailTest(String email, boolean esperado) {
		assertEquals(esperado, new ValidadorFormato().validarEmail(email));
	}

	@Test
	@Parameters({ "juan@juan.com, true",
	})
	public void validarDomicilioTest(String email, boolean esperado) {
		assertEquals(esperado, new ValidadorFormato().validarEmail(email));
	}
}
