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

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;
import org.mockito.Mockito;

import app.datos.entidades.Cliente;
import app.datos.entidades.Direccion;
import app.datos.entidades.Inmueble;
import app.datos.entidades.Reserva;
import app.excepciones.SaveUpdateException;
import app.logica.CoordinadorJavaFX;
import app.logica.resultados.ResultadoCrearReserva;
import app.logica.resultados.ResultadoCrearReserva.ErrorCrearReserva;
import app.ui.componentes.ventanas.PresentadorVentanas;
import app.ui.componentes.ventanas.VentanaError;
import app.ui.componentes.ventanas.VentanaErrorExcepcion;
import app.ui.componentes.ventanas.VentanaErrorExcepcionInesperada;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

/**
 * Test para el controlador de la vista Alta Reserva
 */
@RunWith(JUnitParamsRunner.class)
public class AltaReservaControllerTest {

	@Test
	@Parameters
	public void testCrearReserva(Inmueble inmuebleAReservar,
			Cliente cliente,
			String importe,
			LocalDate fechaInicio,
			LocalDate fechaFin,
			ResultadoCrearReserva resultadoCrearReservaEsperado,
			Integer llamaAPresentadorVentanasPresentarError,
			Integer llamaAPresentadorVentanasPresentarExcepcion,
			Integer llamaAPresentadorVentanasPresentarExcepcionInesperada,
			Integer llamaACrearReserva,
			Exception excepcion)
			throws Throwable {
		//Se crean los mocks necesarios
		CoordinadorJavaFX coordinadorMock = mock(CoordinadorJavaFX.class);
		PresentadorVentanas presentadorVentanasMock = mock(PresentadorVentanas.class);
		VentanaError ventanaErrorMock = mock(VentanaError.class);
		VentanaErrorExcepcion ventanaErrorExcepcionMock = mock(VentanaErrorExcepcion.class);
		VentanaErrorExcepcionInesperada ventanaErrorExcepcionInesperadaMock = mock(VentanaErrorExcepcionInesperada.class);

		//Se setea lo que deben devolver los mocks cuando son invocados por la clase a probar
		when(presentadorVentanasMock.presentarError(any(), any(), any())).thenReturn(ventanaErrorMock);
		when(presentadorVentanasMock.presentarExcepcion(any(), any())).thenReturn(ventanaErrorExcepcionMock);
		when(presentadorVentanasMock.presentarExcepcionInesperada(any(), any())).thenReturn(ventanaErrorExcepcionInesperadaMock);
		doNothing().when(presentadorVentanasMock).presentarToast(any(), any());

		Reserva reserva = new Reserva()
				.setImporte(Double.valueOf(importe))
				.setFechaFin(Date.from(fechaFin.atStartOfDay(ZoneId.systemDefault()).toInstant()))
				.setFechaInicio(Date.from(fechaInicio.atStartOfDay(ZoneId.systemDefault()).toInstant()))
				.setCliente(cliente)
				.setInmueble(inmuebleAReservar);
		
		when(coordinadorMock.crearReserva(reserva)).thenReturn(resultadoCrearReservaEsperado);
		if(excepcion != null){
			when(coordinadorMock.crearReserva(reserva)).thenThrow(excepcion);
		}

		ArrayList<Cliente> clientes = new ArrayList<>();
		clientes.add(cliente);
		when(coordinadorMock.obtenerClientes()).thenReturn(clientes);

		//Controlador a probar, se sobreescriben algunos métodos para setear los mocks y setear los datos que ingresaría el usuario en la vista
		AltaReservaController altaReservaController = new AltaReservaController() {
			@Override
			public void inicializar(URL location, ResourceBundle resources) {
				this.coordinador = coordinadorMock;
				this.presentador = presentadorVentanasMock;
				super.inicializar(location, resources);
			}

			@Override
			public void acceptAction() {
				this.textFieldImporte.setText(importe);
				this.datePickerInicio.setValue(fechaInicio);
				this.datePickerFin.setValue(fechaFin);
				this.comboBoxCliente.getSelectionModel().select(cliente);
				this.inmueble = inmuebleAReservar;
				super.acceptAction();
			};
			
			
			
		};

		//Los controladores de las vistas deben correrse en un thread de JavaFX
		ControladorTest corredorTestEnJavaFXThread = new ControladorTest(AltaReservaController.URLVista, altaReservaController);
		Statement test = new Statement() {
			@Override
			public void evaluate() throws Throwable {
				//Método a probar
				altaReservaController.acceptAction();
				//Se hacen las verificaciones pertinentes para comprobar que el controlador se comporte adecuadamente
				Mockito.verify(coordinadorMock).obtenerClientes();
				Mockito.verify(coordinadorMock, times(llamaACrearReserva)).crearReserva(any());
				Mockito.verify(presentadorVentanasMock, times(llamaAPresentadorVentanasPresentarError)).presentarError(eq("Revise sus campos"), any(), any());
				Mockito.verify(presentadorVentanasMock, times(llamaAPresentadorVentanasPresentarExcepcion)).presentarExcepcion(eq(excepcion), any());
				Mockito.verify(presentadorVentanasMock, times(llamaAPresentadorVentanasPresentarExcepcionInesperada)).presentarExcepcionInesperada(eq(excepcion), any());
			}
		};

		//Se corre el test en el hilo de JavaFX
		corredorTestEnJavaFXThread.apply(test, null).evaluate();
	}

	/**
	 * Parámetros para el test
	 *
	 * @return
	 * 		Objeto con:
	 *         nombre, apellido, tipo de documento, número de documento, contraseña, repite contraseña,
	 *         resultado al crear reserva devuelto por el mock del coordinador, veces que se debería llamar a mostrar un error,
	 *         veces que se debería llamar a mostrar una excepción, veces que se debería llamar a mostrar una excepción inesperada,
	 *         veces que se debería llamar a guardar un reserva, excepción lanzada por el mock del coordinador,
	 *         booleano que simula el aceptar o cancelar de la ventana de confirmación, veces que se debería llamar a cambiar scene
	 */
	protected Object[] parametersForTestCrearReserva() {
		Cliente cliente = new Cliente();
		Inmueble inmueble = new Inmueble().setDireccion(new Direccion());
		LocalDate fechaInicioCorrecta = LocalDate.of(1,1,1);
		LocalDate fechaFinCorrecta = LocalDate.of(1,1,1);
		
		return new Object[] {
				new Object[] {inmueble,cliente,"10",fechaInicioCorrecta,fechaFinCorrecta,resultadoCorrecto,0,0,0,1,null}, //prueba correcta
				new Object[] {inmueble,null,"10",fechaInicioCorrecta,fechaFinCorrecta,resultadoCorrecto,1,0,0,0,null}, //cliente no seleccionado
				new Object[] {inmueble,cliente,"dsfs",fechaInicioCorrecta,fechaFinCorrecta,resultadoCorrecto,1,0,0,0,null}, //formato importe incorrecto
				new Object[] {inmueble,cliente,"-10",fechaInicioCorrecta,fechaFinCorrecta,resultadoCrearFormatoIncorrecto,1,0,0,1,null}, //formato importe incorrecto
				new Object[] {inmueble,cliente,"10",fechaInicioCorrecta,fechaFinCorrecta,resultadoCrearYaExisteReserva,1,0,0,1,null}, //ya existe una reserva
				new Object[] {inmueble,cliente,"10",fechaInicioCorrecta,fechaFinCorrecta,resultadoCorrecto,0,1,0,1,new SaveUpdateException(new Throwable())}, //excepcion al guardar en base de datos
		};
	}

	//Resultados crearReserva
	private static final ResultadoCrearReserva resultadoCorrecto = new ResultadoCrearReserva();
	private static final ResultadoCrearReserva resultadoCrearYaExisteReserva =
			new ResultadoCrearReserva(ErrorCrearReserva.Ya_Existe_Reserva_Entre_Las_Fechas);
	private static final ResultadoCrearReserva resultadoCrearFormatoIncorrecto =
			new ResultadoCrearReserva(ErrorCrearReserva.Formato_Importe_Incorrecto);
}