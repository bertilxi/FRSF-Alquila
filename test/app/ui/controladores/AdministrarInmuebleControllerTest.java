package app.ui.controladores;

import org.junit.Test;

import app.datos.entidades.Inmueble;
import app.logica.resultados.ResultadoEliminarInmueble;
import app.ui.controladores.resultado.ResultadoControlador;
import junitparams.Parameters;

/**
 * Este test prueba los métodos de la clase AdministrarInmuebleController
 */
public class AdministrarInmuebleControllerTest {

	@Test
	@Parameters
	public void testEliminarInmueble(Inmueble inmueble, Boolean acepta, ResultadoControlador resultadoControlador, ResultadoEliminarInmueble resultadoLogica, Throwable excepcion) {

	}

	/**
	 * Método que devuelve los parámetros para probar el método bajaInmueble()
	 *
	 * @return parámetros de prueba
	 */
	protected Object[] parametersForTestIngresar() {
		Inmueble inmueble = new Inmueble();
		Boolean acepta = true;
		ResultadoControlador resultadoControlador = new ResultadoControlador();
		ResultadoEliminarInmueble resultadoLogica = new ResultadoEliminarInmueble();
		Throwable excepcion = new Exception();

		return new Object[] {
				new Object[] { inmueble, acepta, resultadoControlador, resultadoLogica, excepcion }
		};
	}
}
