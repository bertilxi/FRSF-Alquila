package app.ui.controladores;

import app.datos.entidades.Cliente;
import app.excepciones.ObjNotFoundException;
import app.excepciones.PersistenciaException;
import app.logica.CoordinadorJavaFX;
import app.logica.resultados.ResultadoEliminarCliente;
import app.logica.resultados.ResultadoEliminarCliente.ErrorEliminarCliente;
import app.ui.componentes.ventanas.PresentadorVentanas;
import app.ui.componentes.ventanas.mock.PresentadorVentanasMock;
import app.ui.controladores.resultado.ResultadoControlador;
import app.ui.controladores.resultado.ResultadoControlador.ErrorControlador;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

/**
 * Este test prueba los métodos de la clase AdministrarClienteController
 */
@RunWith(JUnitParamsRunner.class)
public class AdministrarClienteControllerTest {

	@Test
	@Parameters
	/**
	 *
	 * Pertenece a la taskcard 13 de la iteración 1 y a la historia 3
	 *
	 * @param cliente
	 * @param acepta
	 * @param resultadoControlador
	 * @param resultadoLogica
	 * @param excepcion
	 */
	public void testEliminarCliente(Cliente cliente, Boolean acepta, ResultadoControlador resultadoControlador, ResultadoEliminarCliente resultadoLogica, Throwable excepcion) throws Exception {
		CoordinadorJavaFX coordinadorMock = new CoordinadorJavaFX() {
			@Override
			public ResultadoEliminarCliente eliminarCliente(Cliente cliente) throws PersistenciaException {
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
			public ArrayList<Cliente> obtenerClientes() throws PersistenciaException {
				ArrayList<Cliente> clientes = new ArrayList<>();
				clientes.add(cliente);
				return clientes;
			}
		};
		PresentadorVentanas presentadorMock = new PresentadorVentanasMock(acepta);

		AdministrarClienteController administrarClienteController = new AdministrarClienteController() {
			@Override
			public ResultadoControlador handleEliminar() {
				tablaClientes.getSelectionModel().select(cliente);
				return super.handleEliminar();
			}
		};
		administrarClienteController.setCoordinador(coordinadorMock);
		administrarClienteController.setPresentador(presentadorMock);

		ControladorTest corredorTestEnJavaFXThread = new ControladorTest(AdministrarClienteController.URLVista, administrarClienteController);

		administrarClienteController.setStage(corredorTestEnJavaFXThread.getStagePrueba());

		Statement test = new Statement() {
			@Override
			public void evaluate() throws Throwable {
				assertEquals(resultadoControlador, administrarClienteController.handleEliminar());
			}
		};

		try{
			corredorTestEnJavaFXThread.apply(test, null).evaluate();
		} catch(Throwable e){
			throw new Exception(e);
		}
	}

	/**
	 * Método que devuelve los parámetros para probar el método bajaCliente()
	 *
	 * @return parámetros de prueba
	 */
	protected Object[] parametersForTestEliminarCliente() {
		Cliente cliente = new Cliente();

		Boolean acepta = true;

		ResultadoControlador resultadoControladorCorrecto = new ResultadoControlador();
		ResultadoControlador resultadoControladorEntidadNoEncontrada = new ResultadoControlador(ErrorControlador.Entidad_No_Encontrada);
		ResultadoControlador resultadoControladorErrorPersistencia = new ResultadoControlador(ErrorControlador.Error_Persistencia);
		ResultadoControlador resultadoControladorErrorDesconocido = new ResultadoControlador(ErrorControlador.Error_Desconocido);

		ResultadoEliminarCliente resultadoLogicaCorrecto = new ResultadoEliminarCliente();
		ResultadoEliminarCliente resultadoLogicaNoExisteCliente = new ResultadoEliminarCliente(ErrorEliminarCliente.No_Existe_Cliente);

		Throwable excepcionPersistencia = new ObjNotFoundException("", new Exception());
		Throwable excepcionInesperada = new Exception();

		return new Object[] {
				new Object[] { cliente, acepta, resultadoControladorCorrecto, resultadoLogicaCorrecto, null }, //test donde el usuario acepta y el cliente se elimina correctamente
				new Object[] { cliente, acepta, resultadoControladorEntidadNoEncontrada, resultadoLogicaNoExisteCliente, null }, //test donde el usuario acepta, pero devuelve errores
				new Object[] { cliente, !acepta, resultadoControladorCorrecto, resultadoLogicaCorrecto, null }, //test donde el usuario no acepta, pero de haber aceptado, se hubiese eliminado el cliente correctamente
				new Object[] { cliente, !acepta, resultadoControladorCorrecto, resultadoLogicaNoExisteCliente, null }, //test donde el usuario no acepta, pero de haber aceptado, se huese devuelto error
				new Object[] { cliente, acepta, resultadoControladorErrorPersistencia, null, excepcionPersistencia }, //test donde el controlador tira una excepción de persistencia
				new Object[] { cliente, acepta, resultadoControladorErrorDesconocido, null, excepcionInesperada } //test donde el controlador tira unaexcepción inesperada
		};
	}
}
