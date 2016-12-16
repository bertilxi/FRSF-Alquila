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

import app.datos.entidades.Propietario;
import app.excepciones.ObjNotFoundException;
import app.excepciones.PersistenciaException;
import app.logica.CoordinadorJavaFX;
import app.logica.resultados.ResultadoEliminarPropietario;
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
	 * Test para probar eliminar propietario en el controlador administrar cuando el usuario presiona eliminar
	 *
	 * @param propietario
	 * 			propietario a eliminar
	 * @param acepta
	 * 			si el usuario acepta la confirmación de eliminar
	 * @param resultadoControlador
	 * 			resultado que se espera que retorne el método a probar
	 * @param resultadoLogica
	 * 			resultado que devuelve la operación de capa lógica
	 * @param excepcion
	 * 			excepción que se simula lanzar desde la capa lógica
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

			@Override
			protected void setTitulo(String titulo) {

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
		ResultadoControlador resultadoControladorErrorPersistencia = new ResultadoControlador(ErrorControlador.Error_Persistencia);
		ResultadoControlador resultadoControladorErrorDesconocido = new ResultadoControlador(ErrorControlador.Error_Desconocido);

		ResultadoEliminarPropietario resultadoLogicaCorrecto = new ResultadoEliminarPropietario();

		Throwable excepcionPersistencia = new ObjNotFoundException("", new Exception());
		Throwable excepcionInesperada = new Exception();

		return new Object[] {
				//propietario, acepta, resultadoControlador,resultadoLogica,excepcion
				/*0*/ new Object[] { propietario, acepta, resultadoControladorCorrecto, resultadoLogicaCorrecto, null }, //test donde el usuario acepta y el propietario se elimina correctamente
				/*1*/ new Object[] { propietario, !acepta, resultadoControladorCorrecto, resultadoLogicaCorrecto, null }, //test donde el usuario no acepta, pero de haber aceptado, se hubiese eliminado el propietario correctamente
				/*2*/ new Object[] { propietario, acepta, resultadoControladorErrorPersistencia, null, excepcionPersistencia }, //test donde el controlador tira una excepción de persistencia
				/*3*/ new Object[] { propietario, acepta, resultadoControladorErrorDesconocido, null, excepcionInesperada } //test donde el controlador tira unaexcepción inesperada
		};
	}
}