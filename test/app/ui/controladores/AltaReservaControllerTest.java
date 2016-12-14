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
import app.datos.entidades.Vendedor;
import app.excepciones.SaveUpdateException;
import app.logica.CoordinadorJavaFX;
import app.logica.resultados.ResultadoCrearReserva;
import app.logica.resultados.ResultadoCrearReserva.ErrorCrearReserva;
import app.ui.ScenographyChanger;
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
			Cliente clienteSeleccionado,
			String importe,
			LocalDate fechaInicio,
			LocalDate fechaFin,
			ResultadoCrearReserva resultadoCrearReservaEsperado,
			Integer llamaAPresentadorVentanasPresentarError,
			Integer llamaAPresentadorVentanasPresentarExcepcion,
			Integer llamaAPresentadorVentanasPresentarExcepcionInesperada,
			Integer llamaACrearReserva,
			Exception excepcion,
			Integer reservaExitosa)
			throws Throwable {
		//Se crean los mocks necesarios
		CoordinadorJavaFX coordinadorMock = mock(CoordinadorJavaFX.class);
		PresentadorVentanas presentadorVentanasMock = mock(PresentadorVentanas.class);
		VentanaError ventanaErrorMock = mock(VentanaError.class);
		VentanaErrorExcepcion ventanaErrorExcepcionMock = mock(VentanaErrorExcepcion.class);
		VentanaErrorExcepcionInesperada ventanaErrorExcepcionInesperadaMock = mock(VentanaErrorExcepcionInesperada.class);
		ScenographyChanger scenographyChangerMock = mock(ScenographyChanger.class);
		VerPDFController verPDFControllerMock = mock(VerPDFController.class);

		//Se setea lo que deben devolver los mocks cuando son invocados por la clase a probar
		when(presentadorVentanasMock.presentarError(any(), any(), any())).thenReturn(ventanaErrorMock);
		when(presentadorVentanasMock.presentarExcepcion(any(), any())).thenReturn(ventanaErrorExcepcionMock);
		when(presentadorVentanasMock.presentarExcepcionInesperada(any(), any())).thenReturn(ventanaErrorExcepcionInesperadaMock);
		when(scenographyChangerMock.cambiarScenography(eq(VerPDFController.URLVista), any(Boolean.class))).thenReturn(verPDFControllerMock);
		doNothing().when(verPDFControllerMock).setVendedorLogueado(any(Vendedor.class));
		doNothing().when(verPDFControllerMock).cargarPDF(any());
		doNothing().when(presentadorVentanasMock).presentarToast(any(), any());

		Reserva reserva = new Reserva()
				.setImporte(Double.valueOf(importe))
				.setFechaFin(Date.from(fechaFin.atStartOfDay(ZoneId.systemDefault()).toInstant()))
				.setFechaInicio(Date.from(fechaInicio.atStartOfDay(ZoneId.systemDefault()).toInstant()))
				.setCliente(clienteSeleccionado)
				.setInmueble(inmuebleAReservar);

		when(coordinadorMock.crearReserva(reserva)).thenReturn(resultadoCrearReservaEsperado);
		if(excepcion != null){
			when(coordinadorMock.crearReserva(reserva)).thenThrow(excepcion);
		}

		ArrayList<Cliente> clientes = new ArrayList<>();
		clientes.add(clienteSeleccionado);
		when(coordinadorMock.obtenerClientes()).thenReturn(clientes);
		ArrayList<Inmueble> inmuebles = new ArrayList<>();
		inmuebles.add(inmuebleAReservar);
		when(coordinadorMock.obtenerInmuebles()).thenReturn(inmuebles);

		//Controlador a probar, se sobreescriben algunos métodos para setear los mocks y setear los datos que ingresaría el usuario en la vista
		AltaReservaController altaReservaController = new AltaReservaController() {
			@Override
			public void inicializar(URL location, ResourceBundle resources) {
				this.coordinador = coordinadorMock;
				this.setScenographyChanger(scenographyChangerMock);
				this.presentador = presentadorVentanasMock;
				super.inicializar(location, resources);
			}

			@Override
			protected void setTitulo(String titulo) {

			}

			@Override
			public void acceptAction() {
				this.textFieldImporte.setText(importe);
				this.datePickerInicio.setValue(fechaInicio);
				this.datePickerFin.setValue(fechaFin);
				this.comboBoxCliente.getSelectionModel().select(clienteSeleccionado);
				this.comboBoxInmueble.getSelectionModel().select(inmuebleAReservar);
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
				Mockito.verify(scenographyChangerMock, times(reservaExitosa)).cambiarScenography(eq(VerPDFController.URLVista), any(Boolean.class));
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
		LocalDate fechaInicioCorrecta = LocalDate.of(1, 1, 1);
		LocalDate fechaFinCorrecta = LocalDate.of(1, 1, 1);

		return new Object[] {
				new Object[] { inmueble, cliente, "10", fechaInicioCorrecta, fechaFinCorrecta, resultadoCorrecto, 0, 0, 0, 1, null, 1}, //prueba correcta
				new Object[] { inmueble, null, "10", fechaInicioCorrecta, fechaFinCorrecta, resultadoCorrecto, 1, 0, 0, 0, null, 0}, //cliente no seleccionado
				new Object[] { inmueble, cliente, "10", fechaInicioCorrecta, fechaFinCorrecta, resultadoCrearCliente_Vacío, 1, 0, 0, 1, null, 0}, //cliente null
				new Object[] { inmueble, cliente, "10", fechaInicioCorrecta, fechaFinCorrecta, resultadoCrearNombre_Cliente_Vacío, 1, 0, 0, 1, null, 0}, //cliente sin nombre
				new Object[] { inmueble, cliente, "10", fechaInicioCorrecta, fechaFinCorrecta, resultadoCrearApellido_Cliente_Vacío, 1, 0, 0, 1, null, 0}, //cliente sin apellido
				new Object[] { inmueble, cliente, "10", fechaInicioCorrecta, fechaFinCorrecta, resultadoCrearTipoDocumento_Cliente_Vacío, 1, 0, 0, 1, null, 0}, //cliente sin tipo documento
				new Object[] { inmueble, cliente, "10", fechaInicioCorrecta, fechaFinCorrecta, resultadoCrearNúmeroDocumento_Cliente_Vacío, 1, 0, 0, 1, null, 0}, //cliente sin número de documento 
				new Object[] { inmueble, cliente, "10", fechaInicioCorrecta, fechaFinCorrecta, resultadoCrearPropietario_Vacío, 1, 0, 0, 1, null, 0}, //propietario del inmueble null
				new Object[] { inmueble, cliente, "10", fechaInicioCorrecta, fechaFinCorrecta, resultadoCrearNombre_Propietario_Vacío, 1, 0, 0, 1, null, 0}, //propietario del inmueble sin nombre
				new Object[] { inmueble, cliente, "10", fechaInicioCorrecta, fechaFinCorrecta, resultadoCrearApellido_Propietario_Vacío, 1, 0, 0, 1, null, 0}, //propietario del inmueble sin apellido
				new Object[] { inmueble, cliente, "10", fechaInicioCorrecta, fechaFinCorrecta, resultadoCrearInmueble_Vacío, 1, 0, 0, 1, null, 0}, //inmueble null
				new Object[] { inmueble, cliente, "10", fechaInicioCorrecta, fechaFinCorrecta, resultadoCrearTipo_Inmueble_Vacío, 1, 0, 0, 1, null, 0}, //inmueble sin tipo inmueble
				new Object[] { inmueble, cliente, "10", fechaInicioCorrecta, fechaFinCorrecta, resultadoCrearDirección_Inmueble_Vacía, 1, 0, 0, 1, null, 0}, //inmueble sin dirección
				new Object[] { inmueble, cliente, "10", fechaInicioCorrecta, fechaFinCorrecta, resultadoCrearLocalidad_Inmueble_Vacía, 1, 0, 0, 1, null, 0}, //inmueble sin localidad
				new Object[] { inmueble, cliente, "10", fechaInicioCorrecta, fechaFinCorrecta, resultadoCrearBarrio_Inmueble_Vacío, 1, 0, 0, 1, null, 0}, //inmueble sin barrio 
				new Object[] { inmueble, cliente, "10", fechaInicioCorrecta, fechaFinCorrecta, resultadoCrearCalle_Inmueble_Vacía, 1, 0, 0, 1, null, 0}, //inmueble sin calle
				new Object[] { inmueble, cliente, "10", fechaInicioCorrecta, fechaFinCorrecta, resultadoCrearAltura_Inmueble_Vacía, 1, 0, 0, 1, null, 0}, //inmueble sin altura
				new Object[] { inmueble, cliente, "10", fechaInicioCorrecta, fechaFinCorrecta, resultadoCrearFechaInicio_vacía, 1, 0, 0, 1, null, 0}, //sin fecha inicio
				new Object[] { inmueble, cliente, "10", fechaInicioCorrecta, fechaFinCorrecta, resultadoCrearFechaFin_vacía, 1, 0, 0, 1, null, 0}, //sin fecha fin
				new Object[] { inmueble, cliente, "10", fechaInicioCorrecta, fechaFinCorrecta, resultadoCrearFecha_Inicio_Posterior_A_Fecha_Fin, 1, 0, 0, 1, null, 0}, //fecha inicio posterior a fecha fin
				new Object[] { inmueble, cliente, "10", fechaInicioCorrecta, fechaFinCorrecta, resultadoCrearExiste_Otra_Reserva_Activa, 1, 0, 0, 1, null, 0}, //ya existe otra reserva sobre el inmueble en ese período
				new Object[] { inmueble, cliente, "10", fechaInicioCorrecta, fechaFinCorrecta, resultadoCrearImporte_Vacío, 1, 0, 0, 1, null, 0}, //importe null
				new Object[] { inmueble, cliente, "10", fechaInicioCorrecta, fechaFinCorrecta, resultadoCrearImporte_Menor_O_Igual_A_Cero, 1, 0, 0, 1, null, 0}, //importe menor o igual a cero
				new Object[] { inmueble, cliente, "10", fechaInicioCorrecta, fechaFinCorrecta, resultadoCorrecto, 0, 1, 0, 1, new SaveUpdateException(new Throwable()), 0}, //excepcion al guardar en base de datos
		};
	}

	//Resultados crearReserva
	private static final ResultadoCrearReserva resultadoCorrecto = new ResultadoCrearReserva(null);
	private static final ResultadoCrearReserva resultadoCrearCliente_Vacío =
			new ResultadoCrearReserva(null, ErrorCrearReserva.Cliente_Vacío);
	private static final ResultadoCrearReserva resultadoCrearNombre_Cliente_Vacío =
			new ResultadoCrearReserva(null, ErrorCrearReserva.Nombre_Cliente_Vacío);
	private static final ResultadoCrearReserva resultadoCrearApellido_Cliente_Vacío =
			new ResultadoCrearReserva(null, ErrorCrearReserva.Apellido_Cliente_Vacío);
	private static final ResultadoCrearReserva resultadoCrearTipoDocumento_Cliente_Vacío =
			new ResultadoCrearReserva(null, ErrorCrearReserva.TipoDocumento_Cliente_Vacío);
	private static final ResultadoCrearReserva resultadoCrearNúmeroDocumento_Cliente_Vacío =
			new ResultadoCrearReserva(null, ErrorCrearReserva.NúmeroDocumento_Cliente_Vacío);
	private static final ResultadoCrearReserva resultadoCrearPropietario_Vacío =
			new ResultadoCrearReserva(null, ErrorCrearReserva.Propietario_Vacío);
	private static final ResultadoCrearReserva resultadoCrearNombre_Propietario_Vacío =
			new ResultadoCrearReserva(null, ErrorCrearReserva.Nombre_Propietario_Vacío);
	private static final ResultadoCrearReserva resultadoCrearApellido_Propietario_Vacío =
			new ResultadoCrearReserva(null, ErrorCrearReserva.Apellido_Propietario_Vacío);
	private static final ResultadoCrearReserva resultadoCrearInmueble_Vacío =
			new ResultadoCrearReserva(null, ErrorCrearReserva.Inmueble_Vacío);
	private static final ResultadoCrearReserva resultadoCrearTipo_Inmueble_Vacío =
			new ResultadoCrearReserva(null, ErrorCrearReserva.Tipo_Inmueble_Vacío);
	private static final ResultadoCrearReserva resultadoCrearDirección_Inmueble_Vacía =
			new ResultadoCrearReserva(null, ErrorCrearReserva.Dirección_Inmueble_Vacía);
	private static final ResultadoCrearReserva resultadoCrearLocalidad_Inmueble_Vacía =
			new ResultadoCrearReserva(null, ErrorCrearReserva.Localidad_Inmueble_Vacía);
	private static final ResultadoCrearReserva resultadoCrearBarrio_Inmueble_Vacío =
			new ResultadoCrearReserva(null, ErrorCrearReserva.Barrio_Inmueble_Vacío);
	private static final ResultadoCrearReserva resultadoCrearCalle_Inmueble_Vacía =
			new ResultadoCrearReserva(null, ErrorCrearReserva.Calle_Inmueble_Vacía);
	private static final ResultadoCrearReserva resultadoCrearAltura_Inmueble_Vacía =
			new ResultadoCrearReserva(null, ErrorCrearReserva.Altura_Inmueble_Vacía);
	private static final ResultadoCrearReserva resultadoCrearFechaInicio_vacía =
			new ResultadoCrearReserva(null, ErrorCrearReserva.FechaInicio_vacía);
	private static final ResultadoCrearReserva resultadoCrearFechaFin_vacía =
			new ResultadoCrearReserva(null, ErrorCrearReserva.FechaFin_vacía);
	private static final ResultadoCrearReserva resultadoCrearFecha_Inicio_Posterior_A_Fecha_Fin =
			new ResultadoCrearReserva(null, ErrorCrearReserva.Fecha_Inicio_Posterior_A_Fecha_Fin);
	private static final ResultadoCrearReserva resultadoCrearExiste_Otra_Reserva_Activa =
			new ResultadoCrearReserva(null, ErrorCrearReserva.Existe_Otra_Reserva_Activa);
	private static final ResultadoCrearReserva resultadoCrearImporte_Vacío =
			new ResultadoCrearReserva(null, ErrorCrearReserva.Importe_Vacío);
	private static final ResultadoCrearReserva resultadoCrearImporte_Menor_O_Igual_A_Cero =
			new ResultadoCrearReserva(null, ErrorCrearReserva.Importe_Menor_O_Igual_A_Cero);
}