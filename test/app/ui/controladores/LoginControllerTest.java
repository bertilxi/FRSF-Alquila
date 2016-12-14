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
import java.util.ResourceBundle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;

import app.datos.clases.DatosLogin;
import app.datos.clases.TipoDocumentoStr;
import app.datos.entidades.TipoDocumento;
import app.datos.entidades.Vendedor;
import app.excepciones.ObjNotFoundException;
import app.excepciones.PersistenciaException;
import app.logica.CoordinadorJavaFX;
import app.logica.resultados.ResultadoAutenticacion;
import app.logica.resultados.ResultadoAutenticacion.ErrorAutenticacion;
import app.ui.componentes.ventanas.PresentadorVentanas;
import app.ui.componentes.ventanas.mock.PresentadorVentanasMock;
import app.ui.controladores.resultado.ResultadoControlador;
import app.ui.controladores.resultado.ResultadoControlador.ErrorControlador;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
/**
 * Este test prueba los métodos de la clase LoginController
 */
public class LoginControllerTest {

	private static Vendedor vendedorRetorno;

	@Test
	@Parameters
	/**
	 * Prueba el método ingresar(), el cual corresponde con la taskcard 1 de la iteración 1 y a la historia 1
	 *
	 * @param tipoDocumento
	 *            que se usará en el test
	 * @param numDoc
	 *            que se usará en el test
	 * @param contra
	 *            es la contraseña que se usará en el test
	 * @param resultadoVista
	 *            es lo que se espera que devuelva el metodo
	 * @param resultadoLogica
	 *            es lo que el mock de la lógica debe devolver en el test y que el controlador recibe
	 * @param excepcion
	 *            es la excepcion que debe lanzar el mock de la lógica, si la prueba involucra procesar una excepcion de dicha lógica, debe ser nulo resultadoLogica para que se use
	 * @throws Exception
	 */
	public void testIngresar(TipoDocumento tipoDocumento, String numDoc, String contra, ResultadoControlador resultadoVista, ResultadoAutenticacion resultadoLogica, Throwable excepcion) throws Exception {
		CoordinadorJavaFX coordinadorMock = new CoordinadorJavaFX() {
			@Override
			public ResultadoAutenticacion autenticarVendedor(DatosLogin login) throws PersistenciaException {
				if(resultadoLogica != null){
					return resultadoLogica;
				}
				if(excepcion instanceof PersistenciaException){
					throw (PersistenciaException) excepcion;
				}
				new Integer("asd");
				return null;
			}
		};
		PresentadorVentanas presentadorMock = new PresentadorVentanasMock();

		LoginController loginController = new LoginController() {
			@Override
			public ResultadoControlador ingresar() {
				tfNumeroDocumento.setText(numDoc);
				pfContra.setText(contra);
				cbTipoDocumento.getItems().clear();
				cbTipoDocumento.getItems().add(tipoDocumento);
				cbTipoDocumento.getSelectionModel().select(tipoDocumento);
				return super.ingresar();
			};

			@Override
			public void inicializar(URL location, ResourceBundle resources) {

			}

			@Override
			protected void setTitulo(String titulo) {

			}

			@Override
			protected OlimpoController cambiarmeAScene(String URLVistaACambiar, String URLVistaRetorno, Boolean useSceneSize) {
				return new BaseController() {
					@Override
					public void formatearConVendedor(Vendedor vendedor) {
						assertEquals(vendedorRetorno, vendedor);
					}
				};
			}
		};
		loginController.setCoordinador(coordinadorMock);
		loginController.setPresentador(presentadorMock);

		ControladorTest corredorTestEnJavaFXThread = new ControladorTest(LoginController.URLVista, loginController);

		loginController.setStage(corredorTestEnJavaFXThread.getStagePrueba());

		Statement test = new Statement() {
			@Override
			public void evaluate() throws Throwable {
				assertEquals(resultadoVista, loginController.ingresar());
			}
		};

		try{
			corredorTestEnJavaFXThread.apply(test, null).evaluate();
		} catch(Throwable e){
			throw new Exception(e);
		}
	}

	/**
	 * Método que devuelve los parámetros para probar el método ingresar()
	 *
	 * @return parámetros de prueba
	 */
	protected Object[] parametersForTestIngresar() {
		vendedorRetorno = new Vendedor();
		return new Object[] {
				new Object[] { (new TipoDocumento()).setTipo(TipoDocumentoStr.DNI), "12345678", "pepe", new ResultadoControlador(), new ResultadoAutenticacion(vendedorRetorno), null }, //prueba ingreso correcto
				new Object[] { null, "", "pepe", new ResultadoControlador(ErrorControlador.Campos_Vacios), new ResultadoAutenticacion(null, ErrorAutenticacion.Datos_Incorrectos), null }, //prueba TipoDocumento vacio
				new Object[] { (new TipoDocumento()).setTipo(TipoDocumentoStr.LC), "", "pepe", new ResultadoControlador(ErrorControlador.Campos_Vacios), new ResultadoAutenticacion(null, ErrorAutenticacion.Datos_Incorrectos), null }, //prueba numero de documento vacio
				new Object[] { (new TipoDocumento()).setTipo(TipoDocumentoStr.LE), "12345678", "", new ResultadoControlador(ErrorControlador.Campos_Vacios), new ResultadoAutenticacion(null, ErrorAutenticacion.Datos_Incorrectos), null }, //prueba Contraseña vacia
				new Object[] { (new TipoDocumento()).setTipo(TipoDocumentoStr.CEDULA_EXTRANJERA), "12345678", "pepe", new ResultadoControlador(ErrorControlador.Datos_Incorrectos), new ResultadoAutenticacion(null, ErrorAutenticacion.Datos_Incorrectos), null }, //prueba un ingreso incorrecto
				new Object[] { (new TipoDocumento()).setTipo(TipoDocumentoStr.PASAPORTE), "ñú", "pepe", new ResultadoControlador(ErrorControlador.Datos_Incorrectos), new ResultadoAutenticacion(null, ErrorAutenticacion.Datos_Incorrectos), null }, //prueba un ingreso incorrecto con caracteres UTF8
				new Object[] { (new TipoDocumento()).setTipo(TipoDocumentoStr.LC), "ñú", "pepe", new ResultadoControlador(ErrorControlador.Error_Persistencia), null, new ObjNotFoundException("Error de persistencia. Test.", new Exception()) }, //Prueba una excepcion de persistencia
				new Object[] { (new TipoDocumento()).setTipo(TipoDocumentoStr.DNI), "ñú", "pepe", new ResultadoControlador(ErrorControlador.Error_Desconocido), null, new Exception() } //Prueba una excepcion desconocida
		};
	}

}