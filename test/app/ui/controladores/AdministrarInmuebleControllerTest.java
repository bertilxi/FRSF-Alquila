package app.ui.controladores;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;

import app.datos.entidades.Inmueble;
import app.excepciones.ObjNotFoundException;
import app.excepciones.PersistenciaException;
import app.logica.CoordinadorJavaFX;
import app.logica.resultados.ResultadoEliminarInmueble;
import app.logica.resultados.ResultadoEliminarInmueble.ErrorEliminarInmueble;
import app.ui.componentes.ventanas.PresentadorVentanas;
import app.ui.componentes.ventanas.mock.PresentadorVentanasMock;
import app.ui.controladores.resultado.ResultadoControlador;
import app.ui.controladores.resultado.ResultadoControlador.ErrorControlador;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

/**
 * Este test prueba los métodos de la clase AdministrarInmuebleController
 */
@RunWith(JUnitParamsRunner.class)
public class AdministrarInmuebleControllerTest {

	@Test
	@Parameters
	/**
	 *
	 * Pertenece a la taskcard 13 de la iteración 1 y a la historia 3
	 *
	 * @param inmueble
	 * @param acepta
	 * @param resultadoControlador
	 * @param resultadoLogica
	 * @param excepcion
	 */
	public void testEliminarInmueble(Inmueble inmueble, Boolean acepta, ResultadoControlador resultadoControlador, ResultadoEliminarInmueble resultadoLogica, Throwable excepcion) throws Exception {
		CoordinadorJavaFX coordinadorMock = new CoordinadorJavaFX() {
			@Override
			public ResultadoEliminarInmueble eliminarInmueble(Inmueble inmbueble) throws PersistenciaException {
				if(resultadoLogica != null){
					return resultadoLogica;
				}
				if(excepcion instanceof PersistenciaException){
					throw (PersistenciaException) excepcion;
				}
				new Integer("asd");
				return null;
			}

			@Override
			public ArrayList<Inmueble> obtenerInmuebles() throws PersistenciaException {
				ArrayList<Inmueble> inmuebles = new ArrayList<>();
				inmuebles.add(inmueble);
				return inmuebles;
			}
		};
		PresentadorVentanas presentadorMock = new PresentadorVentanasMock(acepta);

		AdministrarInmuebleController administrarInmuebleController = new AdministrarInmuebleController() {
			@Override
			public ResultadoControlador eliminarInmueble() {
				tablaInmuebles.getSelectionModel().select(inmueble);
				return super.eliminarInmueble();
			}
		};
		administrarInmuebleController.setCoordinador(coordinadorMock);
		administrarInmuebleController.setPresentador(presentadorMock);

		ControladorTest corredorTestEnJavaFXThread = new ControladorTest(AdministrarInmuebleController.URLVista, administrarInmuebleController);

		administrarInmuebleController.setStage(corredorTestEnJavaFXThread.getStagePrueba());

		Statement test = new Statement() {
			@Override
			public void evaluate() throws Throwable {
				assertEquals(resultadoControlador, administrarInmuebleController.eliminarInmueble());
			}
		};

		try{
			corredorTestEnJavaFXThread.apply(test, null).evaluate();
		} catch(Throwable e){
			throw new Exception(e);
		}
	}

	/**
	 * Método que devuelve los parámetros para probar el método bajaInmueble()
	 *
	 * @return parámetros de prueba
	 */
	protected Object[] parametersForTestEliminarInmueble() {
		Inmueble inmueble = new Inmueble();

		Boolean acepta = true;

		ResultadoControlador resultadoControladorCorrecto = new ResultadoControlador();
		ResultadoControlador resultadoControladorEntidadNoEncontrada = new ResultadoControlador(ErrorControlador.Entidad_No_Encontrada);
		ResultadoControlador resultadoControladorErrorPersistencia = new ResultadoControlador(ErrorControlador.Error_Persistencia);
		ResultadoControlador resultadoControladorErrorDesconocido = new ResultadoControlador(ErrorControlador.Error_Desconocido);

		ResultadoEliminarInmueble resultadoLogicaCorrecto = new ResultadoEliminarInmueble();
		ResultadoEliminarInmueble resultadoLogicaNoExisteInmueble = new ResultadoEliminarInmueble(ErrorEliminarInmueble.No_Existe_Inmueble);

		Throwable excepcionPersistencia = new ObjNotFoundException("", new Exception());
		Throwable excepcionInesperada = new Exception();

		return new Object[] {
				new Object[] { inmueble, acepta, resultadoControladorCorrecto, resultadoLogicaCorrecto, null }, //test donde el usuario acepta y el inmueble se elimina correctamente
				new Object[] { inmueble, acepta, resultadoControladorEntidadNoEncontrada, resultadoLogicaNoExisteInmueble, null }, //test donde el usuario acepta, pero devuelve errores
				new Object[] { inmueble, !acepta, resultadoControladorCorrecto, resultadoLogicaCorrecto, null }, //test donde el usuario no acepta, pero de haber aceptado, se hubiese eliminado el inmueble correctamente
				new Object[] { inmueble, !acepta, resultadoControladorCorrecto, resultadoLogicaNoExisteInmueble, null }, //test donde el usuario no acepta, pero de haber aceptado, se huese devuelto error
				new Object[] { inmueble, acepta, resultadoControladorErrorPersistencia, null, excepcionPersistencia }, //test donde el controlador tira una excepción de persistencia
				new Object[] { inmueble, acepta, resultadoControladorErrorDesconocido, null, excepcionInesperada } //test donde el controlador tira unaexcepción inesperada
		};
	}
}
