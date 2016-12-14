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

import app.datos.entidades.Cliente;
import app.datos.entidades.Inmueble;
import app.datos.entidades.Reserva;
import app.excepciones.PersistenciaException;
import app.excepciones.SaveUpdateException;
import app.logica.CoordinadorJavaFX;
import app.logica.resultados.ResultadoEliminarReserva;
import app.ui.ScenographyChanger;
import app.ui.componentes.ventanas.PresentadorVentanas;
import app.ui.componentes.ventanas.VentanaConfirmacion;
import app.ui.componentes.ventanas.VentanaError;
import app.ui.componentes.ventanas.VentanaErrorExcepcion;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

/**
 * Test para el controlador de la vista administrar Reserva. Alta, baja y modificación.
 */
@RunWith(JUnitParamsRunner.class)
public class AdministrarReservaControllerTest {

	@Test
	public void testAltaReserva() throws Throwable {

		//Se crean los mocks necesarios
		ScenographyChanger scenographyChangerMock = mock(ScenographyChanger.class);
		AltaReservaController altaReservaControllerMock = mock(AltaReservaController.class);
		CoordinadorJavaFX coordinadorMock = mock(CoordinadorJavaFX.class);

		//Se setea lo que debe devolver el mock cuando es invocado por la clase a probar
		when(scenographyChangerMock.cambiarScenography(any(String.class), any())).thenReturn(altaReservaControllerMock);

		//Controlador a probar;
		AdministrarReservaController administrarReservaController = new AdministrarReservaController() {

			@Override
			public void inicializar(URL location, ResourceBundle resources) {
				this.coordinador = coordinadorMock;
				this.setScenographyChanger(scenographyChangerMock);
				super.inicializar(location, resources);
			}

		};

		//Los controladores de las vistas deben correrse en un thread de JavaFX
		ControladorTest corredorTestEnJavaFXThread = new ControladorTest(AdministrarReservaController.URLVista, administrarReservaController);
		administrarReservaController.setStage(corredorTestEnJavaFXThread.getStagePrueba());
		Statement test = new Statement() {
			@Override
			public void evaluate() throws Throwable {
				//Método a probar
				administrarReservaController.nuevoAction(null);
				//Se hacen las verificaciones pertinentes para comprobar que el controlador se comporte adecuadamente
				Mockito.verify(scenographyChangerMock, times(1)).cambiarScenography(AltaReservaController.URLVista, false);
			}
		};
		//Se corre el test en el hilo de JavaFX
		corredorTestEnJavaFXThread.apply(test, null).evaluate();
		;
	}
	
	@Test
	@Parameters
	public void testEliminarReserva(ArrayList<Reserva> listaReservas,
			Integer llamaAPresentadorVentanasPresentarConfirmacion,
			Boolean aceptarVentanaConfirmacion,
			ResultadoEliminarReserva resultadoEliminarReservaEsperado,
			Integer llamaAPresentadorVentanasPresentarError,
			Integer llamaAPresentadorVentanasPresentarExcepcion,
			PersistenciaException excepcion)
			throws Throwable {
		//Se crean los mocks necesarios
		CoordinadorJavaFX coordinadorMock = mock(CoordinadorJavaFX.class);
		PresentadorVentanas presentadorVentanasMock = mock(PresentadorVentanas.class);
		VentanaError ventanaErrorMock = mock(VentanaError.class);
		VentanaErrorExcepcion ventanaErrorExcepcionMock = mock(VentanaErrorExcepcion.class);
		VentanaConfirmacion ventanaConfirmacionMock = mock(VentanaConfirmacion.class);

		//Se setea lo que deben devolver los mocks cuando son invocados por la clase a probar
		when(coordinadorMock.obtenerReservas(any(Cliente.class))).thenReturn(listaReservas);
		when(coordinadorMock.obtenerReservas(any(Inmueble.class))).thenReturn(listaReservas);
		when(presentadorVentanasMock.presentarError(any(), any(), any())).thenReturn(ventanaErrorMock);
		when(presentadorVentanasMock.presentarExcepcion(any(), any())).thenReturn(ventanaErrorExcepcionMock);
		when(presentadorVentanasMock.presentarConfirmacion(any(), any(), any())).thenReturn(ventanaConfirmacionMock);
		when(ventanaConfirmacionMock.acepta()).thenReturn(aceptarVentanaConfirmacion);
		doNothing().when(presentadorVentanasMock).presentarToast(any(), any());

		when(coordinadorMock.eliminarReserva(any(Reserva.class))).thenReturn(resultadoEliminarReservaEsperado);
		if(excepcion != null){
			when(coordinadorMock.eliminarReserva(any(Reserva.class))).thenThrow(excepcion);
		}

		//Controlador a probar, se sobreescriben algunos métodos para setear los mocks y setear los datos en la tabla
		AdministrarReservaController administrarReservaController = new AdministrarReservaController() {

			@Override
			public void inicializar(URL location, ResourceBundle resources) {
				this.coordinador = coordinadorMock;
				this.presentador = new PresentadorVentanas();
				this.presentador = presentadorVentanasMock;
				super.inicializar(location, resources);
				this.setCliente(new Cliente());
				this.tablaReservas.getSelectionModel().select(0); //se selecciona la primer reserva de la lista
			}
		};

		//Los controladores de las vistas deben correrse en un thread de JavaFX
		ControladorTest corredorTestEnJavaFXThread = new ControladorTest(AdministrarReservaController.URLVista, administrarReservaController);
		administrarReservaController.setStage(corredorTestEnJavaFXThread.getStagePrueba());
		Statement test = new Statement() {
			@Override
			public void evaluate() throws Throwable {
				Integer cantidadReservasAntesDeEliminar = administrarReservaController.tablaReservas.getItems().size();
				//Método a probar
				administrarReservaController.eliminarAction(null);
				//Se hacen las verificaciones pertinentes para comprobar que el controlador se comporte adecuadamente
				Mockito.verify(presentadorVentanasMock, times(llamaAPresentadorVentanasPresentarConfirmacion)).presentarConfirmacion(any(), any(), any());
				Mockito.verify(presentadorVentanasMock, times(llamaAPresentadorVentanasPresentarExcepcion)).presentarExcepcion(eq(excepcion), any());

				if(llamaAPresentadorVentanasPresentarExcepcion == 0){
					Mockito.verify(presentadorVentanasMock, times(llamaAPresentadorVentanasPresentarError)).presentarError(any(), any(), any());
				}

				Integer cantidadReservasDespuesDeEliminar = administrarReservaController.tablaReservas.getItems().size();

				if(llamaAPresentadorVentanasPresentarError != 1 && llamaAPresentadorVentanasPresentarExcepcion != 1 && aceptarVentanaConfirmacion){
					assertNotEquals(cantidadReservasAntesDeEliminar, cantidadReservasDespuesDeEliminar);
				}
			}
		};
		//Se corre el test en el hilo de JavaFX
		corredorTestEnJavaFXThread.apply(test, null).evaluate();

	}

	/**
	 * Parámetros para el test eliminar reserva
	 *
	 * @return
	 * 		Objeto con:
	 *         listaReservas para rellenar la tabla,
	 *         las veces que se debería llamar al mostrar la ventana de confirmación,
	 *         un booleano que representa si se acepta o no la ventana devuelto por el mock de ventanaConfirmacion,
	 *         resultadoEliminarReservaEsperado que retorna el mock del coordinador cuando se llama a eliminar la reserva,
	 *         las veces que se debería llamar a mostrar la ventana de error,
	 *         las veces que se debería llamar a mostrar la ventana para presentar una excepción si ocurre,
	 *         una excepción que la lanza el coordinadormock cuando este parámetro es distinto de null
	 */
	protected Object[] parametersForTestEliminarReserva() {
		Reserva reserva = new Reserva();

		Reserva reserva2 = new Reserva();

		ArrayList<Reserva> listaReservas = new ArrayList<>();
		ArrayList<Reserva> listaReservasVacia = new ArrayList<>();
		ResultadoEliminarReserva resultadoSinError = new ResultadoEliminarReserva();

		SaveUpdateException exception = new SaveUpdateException(new Throwable());

		listaReservas.add(reserva);
		listaReservas.add(reserva2);

		return new Object[] {
				new Object[] { listaReservas, 1, true, resultadoSinError, 0, 0, null }, //Reserva correcta y se selecciona eliminar
				new Object[] { listaReservas, 1, true, resultadoSinError, 0, 1, exception }, //La base de datos devuelve una excepción
				new Object[] { listaReservas, 1, false, resultadoSinError, 0, 0, null }, //Reserva correcta y no se acepta la ventana de confirmación
				new Object[] { listaReservasVacia, 0, false, resultadoSinError, 0, 0, null } //La lista de reservas es vacía
		};
	}
}
