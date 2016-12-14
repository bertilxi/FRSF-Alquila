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

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;

import app.datos.entidades.Direccion;
import app.datos.entidades.Inmueble;
import app.excepciones.ObjNotFoundException;
import app.excepciones.PersistenciaException;
import app.logica.CoordinadorJavaFX;
import app.logica.resultados.ResultadoEliminarInmueble;
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
	 * Prueba el método eliminarInmueble(), el cual corresponde con la taskcard 13 de la iteración 1 y a la historia 3
	 *
	 * @param inmueble
	 *            inmueble a crear
	 * @param acepta
	 *            si el usuario acepta la eliminacion
	 * @param resultadoControlador
	 *            resultado que se espera que devuelva el metodo a probar
	 * @param resultadoLogica
	 *            resultado que devuelve el gestor
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

			@Override
			protected void inicializar(URL location, ResourceBundle resources) {

			}

			@Override
			protected void setTitulo(String titulo) {

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
		Inmueble inmueble = new Inmueble().setDireccion(new Direccion());

		Boolean acepta = true;

		ResultadoControlador resultadoControladorCorrecto = new ResultadoControlador();
		ResultadoControlador resultadoControladorErrorPersistencia = new ResultadoControlador(ErrorControlador.Error_Persistencia);
		ResultadoControlador resultadoControladorErrorDesconocido = new ResultadoControlador(ErrorControlador.Error_Desconocido);

		ResultadoEliminarInmueble resultadoLogicaCorrecto = new ResultadoEliminarInmueble();

		Throwable excepcionPersistencia = new ObjNotFoundException("", new Exception());
		Throwable excepcionInesperada = new Exception();

		return new Object[] {
				new Object[] { inmueble, acepta, resultadoControladorCorrecto, resultadoLogicaCorrecto, null }, //test donde el usuario acepta y el inmueble se elimina correctamente
				new Object[] { inmueble, !acepta, resultadoControladorCorrecto, resultadoLogicaCorrecto, null }, //test donde el usuario no acepta, pero de haber aceptado, se hubiese eliminado el inmueble correctamente
				new Object[] { inmueble, acepta, resultadoControladorErrorPersistencia, null, excepcionPersistencia }, //test donde el controlador tira una excepción de persistencia
				new Object[] { inmueble, acepta, resultadoControladorErrorDesconocido, null, excepcionInesperada } //test donde el controlador tira unaexcepción inesperada
		};
	}
}
