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

import static org.junit.Assert.assertNotEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;
import org.mockito.Mockito;

import app.datos.entidades.Vendedor;
import app.excepciones.PersistenciaException;
import app.excepciones.SaveUpdateException;
import app.logica.CoordinadorJavaFX;
import app.logica.resultados.ResultadoEliminarVendedor;
import app.ui.ScenographyChanger;
import app.ui.componentes.ventanas.PresentadorVentanas;
import app.ui.componentes.ventanas.VentanaConfirmacion;
import app.ui.componentes.ventanas.VentanaError;
import app.ui.componentes.ventanas.VentanaErrorExcepcion;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

/**
 * Test para el controlador de la vista administrar Vendedor. Alta, baja y modificacion.
 */
@RunWith(JUnitParamsRunner.class)
public class AdministrarVendedorControllerTest {

	@Test
	public void testAgregarVendedor() throws Throwable {

		//Se crean los mocks necesarios
		ScenographyChanger scenographyChangerMock = mock(ScenographyChanger.class);
		AltaVendedorController altaVendedorControllerMock = mock(AltaVendedorController.class);
		CoordinadorJavaFX coordinadorMock = mock(CoordinadorJavaFX.class);

		//Se setea lo que debe devolver el mock cuando es invocado por la clase a probar
		when(scenographyChangerMock.cambiarScenography(any(String.class), any())).thenReturn(altaVendedorControllerMock);

		//Controlador a probar;
		AdministrarVendedorController administrarVendedorController = new AdministrarVendedorController() {

			@Override
			public void inicializar(URL location, ResourceBundle resources) {
				this.coordinador = coordinadorMock;
				this.setScenographyChanger(scenographyChangerMock);
				super.inicializar(location, resources);
			}

			@Override
			protected void setTitulo(String titulo) {

			}
		};

		//Los controladores de las vistas deben correrse en un thread de JavaFX
		ControladorTest corredorTestEnJavaFXThread = new ControladorTest(AdministrarVendedorController.URLVista, administrarVendedorController);
		administrarVendedorController.setStage(corredorTestEnJavaFXThread.getStagePrueba());
		Statement test = new Statement() {
			@Override
			public void evaluate() throws Throwable {
				//Método a probar
				administrarVendedorController.agregarAction(null);
				//Se hacen las verificaciones pertinentes para comprobar que el controlador se comporte adecuadamente
				Mockito.verify(scenographyChangerMock, times(1)).cambiarScenography(AltaVendedorController.URLVista, false);
			}
		};
		//Se corre el test en el hilo de JavaFX
		corredorTestEnJavaFXThread.apply(test, null).evaluate();
		;
	}

	@Test
	@Parameters
	public void testModificarVendedor(ArrayList<Vendedor> listaVendedores, Integer llamaAModificar, String urlModificarVendedor) throws Throwable {

		//Se crean los mocks necesarios
		ScenographyChanger scenographyChangerMock = mock(ScenographyChanger.class);
		CoordinadorJavaFX coordinadorMock = mock(CoordinadorJavaFX.class);
		ModificarVendedorController modificarVendedorControllerMock = mock(ModificarVendedorController.class);

		//Se setea lo que deben devolver los mocks cuando son invocados por la clase a probar
		when(coordinadorMock.obtenerVendedores()).thenReturn(listaVendedores);
		when(scenographyChangerMock.cambiarScenography(any(String.class), any())).thenReturn(modificarVendedorControllerMock);

		//Controlador a probar, se sobreescriben algunos métodos para setear los mocks y setear los datos en la tabla
		AdministrarVendedorController administrarVendedorController = new AdministrarVendedorController() {

			@Override
			public void inicializar(URL location, ResourceBundle resources) {
				this.coordinador = coordinadorMock;
				this.setScenographyChanger(scenographyChangerMock);
				super.inicializar(location, resources);
				this.tablaVendedores.getSelectionModel().select(0);
			}

			@Override
			protected void setTitulo(String titulo) {

			}

		};

		//Los controladores de las vistas deben correrse en un thread de JavaFX
		ControladorTest corredorTestEnJavaFXThread = new ControladorTest(AdministrarVendedorController.URLVista, administrarVendedorController);
		administrarVendedorController.setStage(corredorTestEnJavaFXThread.getStagePrueba());
		Statement test = new Statement() {
			@Override
			public void evaluate() throws Throwable {
				//Método a probar
				administrarVendedorController.modificarAction(null);
				//Se hacen las verificaciones pertinentes para comprobar que el controlador se comporte adecuadamente
				Mockito.verify(scenographyChangerMock, times(llamaAModificar)).cambiarScenography(urlModificarVendedor, false);
			}
		};
		//Se corre el test en el hilo de JavaFX
		corredorTestEnJavaFXThread.apply(test, null).evaluate();

	}

	/**
	 * Parámetros para el test modificar vendedor
	 *
	 * @return
	 * 		Objeto con:
	 *         listaVendedores para rellenar la tabla,
	 *         las veces que se debería llamar para cambiar la scene a la vista modificar,
	 *         la url de la vista a la cual se debería cambiar
	 */
	protected Object[] parametersForTestModificarVendedor() {
		Vendedor vendedor = new Vendedor()
				.setNombre("Juan")
				.setApellido("Perez")
				.setNumeroDocumento("1234");

		ArrayList<Vendedor> listaVendedores = new ArrayList<>();
		ArrayList<Vendedor> listaVendedoresVacia = new ArrayList<>();
		listaVendedores.add(vendedor);

		return new Object[] {
				new Object[] { listaVendedores, 1, ModificarVendedorController.URLVista },
				new Object[] { listaVendedoresVacia, 0, ModificarVendedorController.URLVista }
		};
	}

	@Test
	@Parameters
	public void testEliminarVendedor(ArrayList<Vendedor> listaVendedores,
			Integer llamaAPresentadorVentanasPresentarConfirmacion,
			Boolean aceptarVentanaConfirmacion,
			ResultadoEliminarVendedor resultadoEliminarVendedorEsperado,
			Integer llamaAPresentadorVentanasPresentarError,
			Integer llamaAPresentadorVentanasPresentarExcepcion,
			PersistenciaException excepcion)
			throws Throwable {
		//Se crean los mocks necesarios
		ScenographyChanger scenographyChangerMock = mock(ScenographyChanger.class);
		CoordinadorJavaFX coordinadorMock = mock(CoordinadorJavaFX.class);
		PresentadorVentanas presentadorVentanasMock = mock(PresentadorVentanas.class);
		VentanaError ventanaErrorMock = mock(VentanaError.class);
		VentanaErrorExcepcion ventanaErrorExcepcionMock = mock(VentanaErrorExcepcion.class);
		VentanaConfirmacion ventanaConfirmacionMock = mock(VentanaConfirmacion.class);

		//Se setea lo que deben devolver los mocks cuando son invocados por la clase a probar
		when(coordinadorMock.obtenerVendedores()).thenReturn(listaVendedores);
		when(presentadorVentanasMock.presentarError(any(), any(), any())).thenReturn(ventanaErrorMock);
		when(presentadorVentanasMock.presentarExcepcion(any(), any())).thenReturn(ventanaErrorExcepcionMock);
		when(presentadorVentanasMock.presentarConfirmacion(any(), any(), any())).thenReturn(ventanaConfirmacionMock);
		when(ventanaConfirmacionMock.acepta()).thenReturn(aceptarVentanaConfirmacion);
		doNothing().when(presentadorVentanasMock).presentarToast(any(), any());

		when(coordinadorMock.eliminarVendedor(any(Vendedor.class))).thenReturn(resultadoEliminarVendedorEsperado);
		if(excepcion != null){
			when(coordinadorMock.eliminarVendedor(any(Vendedor.class))).thenThrow(excepcion);
		}

		//Controlador a probar, se sobreescriben algunos métodos para setear los mocks y setear los datos en la tabla
		AdministrarVendedorController administrarVendedorController = new AdministrarVendedorController() {

			@Override
			public void inicializar(URL location, ResourceBundle resources) {
				this.coordinador = coordinadorMock;
				this.setScenographyChanger(scenographyChangerMock);
				this.presentador = new PresentadorVentanas();
				this.presentador = presentadorVentanasMock;
				super.inicializar(location, resources);
				this.tablaVendedores.getSelectionModel().select(0); //se selecciona el primer vendedor de la lista
			}

			@Override
			protected void setTitulo(String titulo) {
			}

		};

		//Los controladores de las vistas deben correrse en un thread de JavaFX
		ControladorTest corredorTestEnJavaFXThread = new ControladorTest(AdministrarVendedorController.URLVista, administrarVendedorController);
		administrarVendedorController.setStage(corredorTestEnJavaFXThread.getStagePrueba());
		Statement test = new Statement() {
			@Override
			public void evaluate() throws Throwable {
				Integer cantidadVendedoresAntesDeEliminar = administrarVendedorController.tablaVendedores.getItems().size();
				//Método a probar
				administrarVendedorController.eliminarAction(null);
				//Se hacen las verificaciones pertinentes para comprobar que el controlador se comporte adecuadamente
				Mockito.verify(presentadorVentanasMock, times(llamaAPresentadorVentanasPresentarConfirmacion)).presentarConfirmacion(any(), any(), any());
				Mockito.verify(presentadorVentanasMock, times(llamaAPresentadorVentanasPresentarConfirmacion)).presentarConfirmacion(any(), any(), any());
				Mockito.verify(presentadorVentanasMock, times(llamaAPresentadorVentanasPresentarExcepcion)).presentarExcepcion(eq(excepcion), any());

				if(llamaAPresentadorVentanasPresentarExcepcion == 0){
					Mockito.verify(presentadorVentanasMock, times(llamaAPresentadorVentanasPresentarError)).presentarError(any(), any(), any());
				}

				Integer cantidadVendedoresDespuesDeEliminar = administrarVendedorController.tablaVendedores.getItems().size();

				if(llamaAPresentadorVentanasPresentarError != 1 && llamaAPresentadorVentanasPresentarExcepcion != 1 && aceptarVentanaConfirmacion){
					assertNotEquals(cantidadVendedoresAntesDeEliminar, cantidadVendedoresDespuesDeEliminar);
				}
			}
		};
		//Se corre el test en el hilo de JavaFX
		corredorTestEnJavaFXThread.apply(test, null).evaluate();

	}

	/**
	 * Parámetros para el test eliminar vendedor
	 *
	 * @return
	 * 		Objeto con:
	 *         listaVendedores para rellenar la tabla,
	 *         las veces que se debería llamar al mostrar la ventana de confirmacion,
	 *         un booleano que representa si se acepta o no la ventana devuelto por el mock de ventanaConfirmacion,
	 *         resultadoEliminarVendedorEsperado que retorna el mock del coordinador cuando se llama a eliminar el vendedor,
	 *         las veces que se debería llamar al mostrar la ventana de error,
	 *         las veces que se debería llamar al mostrar la ventana para presentar una excepcion si ocurre,
	 *         una excepción que la lanza el coordinadormock cuando este parametro es distinto de null
	 */
	protected Object[] parametersForTestEliminarVendedor() {
		Vendedor vendedor = new Vendedor()
				.setNombre("Juan")
				.setApellido("Perez")
				.setNumeroDocumento("1234");
		Vendedor vendedor2 = new Vendedor()
				.setNombre("Juan2")
				.setApellido("Perez2")
				.setNumeroDocumento("12342");

		ArrayList<Vendedor> listaVendedores = new ArrayList<>();
		ArrayList<Vendedor> listaVendedoresVacia = new ArrayList<>();
		ResultadoEliminarVendedor resultadoSinError = new ResultadoEliminarVendedor();

		SaveUpdateException exception = new SaveUpdateException(new Throwable());

		listaVendedores.add(vendedor);
		listaVendedores.add(vendedor2);

		return new Object[] {
				new Object[] { listaVendedores, 1, true, resultadoSinError, 0, 0, null },
				new Object[] { listaVendedores, 1, true, resultadoSinError, 0, 1, exception },
				new Object[] { listaVendedores, 1, false, resultadoSinError, 0, 0, null },
				new Object[] { listaVendedoresVacia, 0, false, resultadoSinError, 0, 0, null },
				new Object[] { listaVendedoresVacia, 0, false, resultadoSinError, 0, 0, null }
		};
	}
}
