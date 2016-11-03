package app.ui.controladores;

import java.util.ArrayList;

import org.junit.Test;

import app.datos.clases.TipoInmuebleStr;
import app.datos.entidades.Barrio;
import app.datos.entidades.Calle;
import app.datos.entidades.Imagen;
import app.datos.entidades.Localidad;
import app.datos.entidades.Orientacion;
import app.datos.entidades.Propietario;
import app.datos.entidades.Provincia;
import app.datos.entidades.TipoInmueble;
import app.logica.resultados.ResultadoCrearInmueble;
import app.ui.controladores.resultado.ResultadoControlador;
import junitparams.Parameters;

/**
 * Este test prueba los métodos de la clase AltaInmuebleController
 */
public class AltaInmuebleControllerTest {

	@Test
	@Parameters
	public void testCrearInmueble(Propietario propietario,
			Provincia provincia,
			Localidad localidad,
			Barrio barrio,
			Calle calle,
			String numero,
			String piso,
			String departamento,
			String otros,
			TipoInmueble tipoInmueble,
			Double precio,
			Orientacion orientacion,
			Double frente,
			Double fondo,
			Double superficie,
			Boolean propiedadHorizontal,
			Double superficieEdificio,
			Integer antigüedadEdificio,
			Integer dormitorios,
			Integer baños,
			Boolean garaje,
			Boolean patio,
			Boolean piscina,
			Boolean aguaCorriente,
			Boolean cloacas,
			Boolean gasNatural,
			Boolean aguaCaliente,
			Boolean teléfono,
			Boolean lavadero,
			Boolean pavimento,
			ArrayList<Imagen> fotos,
			String observaciones,
			ResultadoControlador resultadoControlador,
			ResultadoCrearInmueble resultadoLogica,
			Throwable excepcion) {

	}

	/**
	 * Método que devuelve los parámetros para probar el método crearInmueble()
	 *
	 * @return parámetros de prueba
	 */
	protected Object[] parametersForTestIngresar() {
		Propietario propietario = new Propietario();
		Provincia provincia = new Provincia();
		Localidad localidad = new Localidad();
		Barrio barrio = new Barrio();
		Calle calle = new Calle();
		String numero = "1234";
		String piso = "2";
		String departamento = "2";
		String otros = "";
		TipoInmueble tipoInmueble = new TipoInmueble(TipoInmuebleStr.DEPARTAMENTO);
		Double precio = 2.5;
		Orientacion orientacion = new Orientacion();
		Double frente = 3.0;
		Double fondo = 2.0;
		Double superficie = 6.0;
		Boolean propiedadHorizontal = true;
		Double superficieEdificio = 6.0;
		Integer antigüedadEdificio = 10;
		Integer dormitorios = 3;
		Integer baños = 5;
		Boolean garaje = true;
		Boolean patio = false;
		Boolean piscina = false;
		Boolean aguaCorriente = true;
		Boolean cloacas = true;
		Boolean gasNatural = true;
		Boolean aguaCaliente = true;
		Boolean teléfono = true;
		Boolean lavadero = true;
		Boolean pavimento = false;
		ArrayList<Imagen> fotos = new ArrayList<>();
		String observaciones = "";
		ResultadoControlador resultadoControlador = new ResultadoControlador();
		ResultadoCrearInmueble resultadoLogica = new ResultadoCrearInmueble();
		Throwable excepcion = new Exception();

		return new Object[] {
				new Object[] { propietario, provincia, localidad, barrio, calle, numero, piso, departamento, otros, tipoInmueble, precio, orientacion, frente, fondo, superficie,
						propiedadHorizontal, superficieEdificio, antigüedadEdificio, dormitorios, baños, garaje, patio, piscina, aguaCorriente, cloacas, gasNatural, aguaCaliente,
						teléfono, lavadero, pavimento, fotos, observaciones, resultadoControlador, resultadoLogica, excepcion }
		};
	}
}
