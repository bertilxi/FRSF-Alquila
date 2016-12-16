/**
 * Copyright (C) 2016 Fernando Berti - Daniel Campodonico - Emiliano Gioria - Lucas Moretti - Esteban Rebechi - Andres Leonel Rico
 * This file is part of Olimpo.
 *
 * Olimpo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Olimpo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Olimpo. If not, see <http://www.gnu.org/licenses/>.
 */
package app.ui.controladores;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;

import app.datos.entidades.Cliente;
import app.excepciones.ObjNotFoundException;
import app.excepciones.PersistenciaException;
import app.logica.CoordinadorJavaFX;
import app.logica.resultados.ResultadoEliminarCliente;
import app.ui.componentes.ventanas.PresentadorVentanas;
import app.ui.componentes.ventanas.mock.PresentadorVentanasMock;
import app.ui.controladores.resultado.ResultadoControlador;
import app.ui.controladores.resultado.ResultadoControlador.ErrorControlador;
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
	 * Test para probar la baja de un cliente en el controlador de administrar clientes
	 *
	 * @param cliente
	 * 			cliente a eliminar
	 * @param acepta
	 * 			si usuario acepta confirmación de eliminar
	 * @param resultadoControlador
	 * 			resultado que se espera que retorne el método a probar
	 * @param resultadoLogica
	 * 			resultado que retornará el mock de la capa lógica
	 * @param excepcion
	 * 			excepción que se simula lanzar desde la capa lógica
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

			@Override
			protected void setTitulo(String titulo) {

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
	 * Método que devuelve los parámetros para probar el método eliminarCliente()
	 *
	 * @return parámetros de prueba
	 */
	protected Object[] parametersForTestEliminarCliente() {
		Cliente cliente = new Cliente();

		Boolean acepta = true;

		ResultadoControlador resultadoControladorCorrecto = new ResultadoControlador();
		ResultadoControlador resultadoControladorErrorPersistencia = new ResultadoControlador(ErrorControlador.Error_Persistencia);
		ResultadoControlador resultadoControladorErrorDesconocido = new ResultadoControlador(ErrorControlador.Error_Desconocido);

		ResultadoEliminarCliente resultadoLogicaCorrecto = new ResultadoEliminarCliente();

		Throwable excepcionPersistencia = new ObjNotFoundException("", new Exception());
		Throwable excepcionInesperada = new Exception();

		return new Object[] {
				//cliente,acepta,resultadoControlador,resultadoLogica,excepcion
				/*0*/ new Object[] { cliente, acepta, resultadoControladorCorrecto, resultadoLogicaCorrecto, null }, //test donde el usuario acepta y el cliente se elimina correctamente
				/*1*/ new Object[] { cliente, !acepta, resultadoControladorCorrecto, resultadoLogicaCorrecto, null }, //test donde el usuario no acepta, pero de haber aceptado, se hubiese eliminado el cliente correctamente
				/*2*/ new Object[] { cliente, acepta, resultadoControladorErrorPersistencia, null, excepcionPersistencia }, //test donde el controlador tira una excepción de persistencia
				/*3*/ new Object[] { cliente, acepta, resultadoControladorErrorDesconocido, null, excepcionInesperada } //test donde el controlador tira unaexcepción inesperada
		};
	}
}
