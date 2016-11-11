package app.ui.controladores;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;

import app.datos.entidades.Propietario;
import app.excepciones.ObjNotFoundException;
import app.excepciones.PersistenciaException;
import app.logica.CoordinadorJavaFX;
import app.logica.resultados.ResultadoEliminarPropietario;
import app.logica.resultados.ResultadoEliminarPropietario.ErrorEliminarPropietario;
import app.ui.componentes.ventanas.PresentadorVentanas;
import app.ui.componentes.ventanas.mock.PresentadorVentanasMock;
import app.ui.controladores.resultado.ResultadoControlador;
import app.ui.controladores.resultado.ResultadoControlador.ErrorControlador;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

/**
 * Este test prueba los métodos de la clase AdministrarPropietarioController
 */
@RunWith(JUnitParamsRunner.class)
public class AdministrarPropietarioControllerTest {

	@Test
	@Parameters
	/**
	 *
	 *
	 * @param propietario
	 * @param acepta
	 * @param resultadoControlador
	 * @param resultadoLogica
	 * @param excepcion
	 */
	public void testEliminarPropietario(Propietario propietario, Boolean acepta, ResultadoControlador resultadoControlador, ResultadoEliminarPropietario resultadoLogica, Throwable excepcion) throws Exception {
		CoordinadorJavaFX coordinadorMock = new CoordinadorJavaFX() {
			@Override
			public ResultadoEliminarPropietario eliminarPropietario(Propietario propietario) throws PersistenciaException {
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
			public ArrayList<Propietario> obtenerPropietarios() throws PersistenciaException {
				ArrayList<Propietario> propietarios = new ArrayList<>();
				propietarios.add(propietario);
				return propietarios;
			}
		};
		PresentadorVentanas presentadorMock = new PresentadorVentanasMock(acepta);

		AdministrarPropietarioController administrarPropietarioController = new AdministrarPropietarioController() {
			@Override
			public ResultadoControlador handleEliminar() {
				tablaPropietarios.getSelectionModel().select(propietario);
				return super.handleEliminar();
			}
		};
		administrarPropietarioController.setCoordinador(coordinadorMock);
		administrarPropietarioController.setPresentador(presentadorMock);

		ControladorTest corredorTestEnJavaFXThread = new ControladorTest(AdministrarPropietarioController.URLVista, administrarPropietarioController);

		administrarPropietarioController.setStage(corredorTestEnJavaFXThread.getStagePrueba());

		Statement test = new Statement() {
			@Override
			public void evaluate() throws Throwable {
				assertEquals(resultadoControlador, administrarPropietarioController.handleEliminar());
			}
		};

		try{
			corredorTestEnJavaFXThread.apply(test, null).evaluate();
		} catch(Throwable e){
			throw new Exception(e);
		}
	}

	/**
	 * Método que devuelve los parámetros para probar el método eliminarPropietario()
	 *
	 * @return parámetros de prueba
	 */
	protected Object[] parametersForTestEliminarPropietario() {
		Propietario propietario = new Propietario();

		Boolean acepta = true;

		ResultadoControlador resultadoControladorCorrecto = new ResultadoControlador();
		ResultadoControlador resultadoControladorEntidadNoEncontrada = new ResultadoControlador(ErrorControlador.Entidad_No_Encontrada);
		ResultadoControlador resultadoControladorErrorPersistencia = new ResultadoControlador(ErrorControlador.Error_Persistencia);
		ResultadoControlador resultadoControladorErrorDesconocido = new ResultadoControlador(ErrorControlador.Error_Desconocido);

		ResultadoEliminarPropietario resultadoLogicaCorrecto = new ResultadoEliminarPropietario();
		ResultadoEliminarPropietario resultadoLogicaNoExistePropietario = new ResultadoEliminarPropietario(ErrorEliminarPropietario.No_Existe_Propietario);

		Throwable excepcionPersistencia = new ObjNotFoundException("", new Exception());
		Throwable excepcionInesperada = new Exception();

		return new Object[] {
				new Object[] { propietario, acepta, resultadoControladorCorrecto, resultadoLogicaCorrecto, null }, //test donde el usuario acepta y el propietario se elimina correctamente
				new Object[] { propietario, acepta, resultadoControladorEntidadNoEncontrada, resultadoLogicaNoExistePropietario, null }, //test donde el usuario acepta, pero devuelve errores
				new Object[] { propietario, !acepta, resultadoControladorCorrecto, resultadoLogicaCorrecto, null }, //test donde el usuario no acepta, pero de haber aceptado, se hubiese eliminado el propietario correctamente
				new Object[] { propietario, !acepta, resultadoControladorCorrecto, resultadoLogicaNoExistePropietario, null }, //test donde el usuario no acepta, pero de haber aceptado, se huese devuelto error
				new Object[] { propietario, acepta, resultadoControladorErrorPersistencia, null, excepcionPersistencia }, //test donde el controlador tira una excepción de persistencia
				new Object[] { propietario, acepta, resultadoControladorErrorDesconocido, null, excepcionInesperada } //test donde el controlador tira unaexcepción inesperada
		};
	}
}