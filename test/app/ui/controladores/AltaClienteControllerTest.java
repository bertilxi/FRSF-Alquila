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
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;
import org.mockito.Mockito;

import app.datos.clases.TipoDocumentoStr;
import app.datos.entidades.Cliente;
import app.datos.entidades.InmuebleBuscado;
import app.datos.entidades.TipoDocumento;
import app.excepciones.EntidadExistenteConEstadoBajaException;
import app.excepciones.SaveUpdateException;
import app.logica.CoordinadorJavaFX;
import app.logica.resultados.ResultadoCrearCliente;
import app.logica.resultados.ResultadoCrearCliente.ErrorCrearCliente;
import app.ui.ScenographyChanger;
import app.ui.componentes.ventanas.PresentadorVentanas;
import app.ui.componentes.ventanas.VentanaConfirmacion;
import app.ui.componentes.ventanas.VentanaError;
import app.ui.componentes.ventanas.VentanaErrorExcepcion;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class AltaClienteControllerTest {

	private static Cliente cliente;

	/**
	 * Test para probar crear un cliente cuando el usuario presiona el botón aceptar
	 *
	 * @param nombre
	 * 			nombre que es introducido por el usuario
	 * @param apellido
	 * 			appellido que es introducido por el usuario
	 * @param tipoDocumento
	 * 			tipo de documento que es introducido por el usuario
	 * @param numeroDocumento
	 * 			número de documento que es introducido por el usuario
	 * @param telefono
	 * 			teléfono que es introducido por el usuario
	 * @param correo
	 * 			correo que es introducido por el usuario
	 * @param inmueble
	 * 			inmueble buscado cargado por el usuario en la pantalla de cargar inmueble buscado
	 * @param resultadoCrearClienteEsperado
	 * 			resultado que retornará el mock de capa lógica
	 * @param llamaAPresentadorVentanasPresentarError
	 * 			1 si llama al método presentar error del presentador de ventanas, 0 si no
	 * @param llamaAPresentadorVentanasPresentarExcepcion
	 * 			1 si llama al método presentar excepción del presentador de ventanas, 0 si no
	 * @param llamaACrearCliente
	 * 			1 si llama al método crear cliente de la capa lógica, 0 si no
	 * @param excepcion
	 * 			excepción que se simula lanzar desde la capa lógica
	 * @param aceptarVentanaConfirmacion
	 * 			si el usuario acepta la ventana de confirmación
	 * @param llamaACambiarScene
	 * 			1 si llama al método cambiar scene, 0 si no
	 * @throws Throwable
	 */
	@Test
	@Parameters
	public void testCrearCliente(String nombre,
			String apellido,
			TipoDocumento tipoDocumento,
			String numeroDocumento,
			String telefono,
			String correo,
			InmuebleBuscado inmueble,
			ResultadoCrearCliente resultadoCrearClienteEsperado,
			Integer llamaAPresentadorVentanasPresentarError,
			Integer llamaAPresentadorVentanasPresentarExcepcion,
			Integer llamaACrearCliente,
			Exception excepcion,
			Boolean aceptarVentanaConfirmacion,
			Integer llamaACambiarScene) throws Throwable {

		CoordinadorJavaFX coordinadorMock = Mockito.mock(CoordinadorJavaFX.class);
		PresentadorVentanas presentadorVentanasMock = mock(PresentadorVentanas.class);
		VentanaError ventanaErrorMock = mock(VentanaError.class);
		VentanaErrorExcepcion ventanaErrorExcepcionMock = mock(VentanaErrorExcepcion.class);
		VentanaConfirmacion ventanaConfirmacionMock = mock(VentanaConfirmacion.class);
		ScenographyChanger scenographyChangerMock = mock(ScenographyChanger.class);
		ModificarClienteController modificarClienteControllerMock = mock(ModificarClienteController.class);

		when(presentadorVentanasMock.presentarError(any(), any(), any())).thenReturn(ventanaErrorMock);
		when(presentadorVentanasMock.presentarExcepcion(any(), any())).thenReturn(ventanaErrorExcepcionMock);
		when(presentadorVentanasMock.presentarConfirmacion(any(), any(), any())).thenReturn(ventanaConfirmacionMock);
		when(ventanaConfirmacionMock.acepta()).thenReturn(aceptarVentanaConfirmacion);
		when(scenographyChangerMock.cambiarScenography(any(String.class), any())).thenReturn(modificarClienteControllerMock);
		doNothing().when(modificarClienteControllerMock).setClienteEnModificacion(any());
		doNothing().when(presentadorVentanasMock).presentarToast(any(), any());

		cliente = new Cliente()
				.setNombre(nombre)
				.setApellido(apellido)
				.setTipoDocumento(tipoDocumento)
				.setNumeroDocumento(numeroDocumento)
				.setInmuebleBuscado(inmueble)
				.setTelefono(telefono)
				.setCorreo(correo);

		inmueble.setCliente(cliente);

		when(coordinadorMock.crearCliente(cliente)).thenReturn(resultadoCrearClienteEsperado);
		if(excepcion != null){
			when(coordinadorMock.crearCliente(cliente)).thenThrow(excepcion);
		}

		ArrayList<TipoDocumento> tipos = new ArrayList<>();
		tipos.add(tipoDocumento);
		Mockito.when(coordinadorMock.obtenerTiposDeDocumento()).thenReturn(tipos);

		AltaClienteController altaClienteController = new AltaClienteController() {
			@Override
			public void inicializar(URL location, ResourceBundle resources) {
				this.coordinador = coordinadorMock;
				this.presentador = presentadorVentanasMock;
				setScenographyChanger(scenographyChangerMock);
				super.inicializar(location, resources);
			}

			@Override
			public void acceptAction() {
				this.textFieldNombre.setText(nombre);
				this.textFieldApellido.setText(apellido);
				this.comboBoxTipoDocumento.getSelectionModel().select(tipoDocumento);
				this.textFieldNumeroDocumento.setText(numeroDocumento);
				this.textFieldTelefono.setText(telefono);
				this.textFieldCorreo.setText(correo);
				super.acceptAction();
			}

			@Override
			protected void setTitulo(String titulo) {

			}

			@Override
			public void setCliente(Cliente cliente) {
				if(cliente != null){
					this.cliente = cliente;
				}
				else{
					this.cliente = new Cliente();
				}
			}
		};
		altaClienteController.setCliente(cliente);

		ControladorTest corredorTestEnJavaFXThread =
				new ControladorTest(AltaClienteController.URLVista, altaClienteController);

		Statement test = new Statement() {
			@Override
			public void evaluate() throws Throwable {
				altaClienteController.acceptAction();

				Mockito.verify(coordinadorMock).obtenerTiposDeDocumento();
				Mockito.verify(coordinadorMock, Mockito.times(llamaACrearCliente)).crearCliente(Mockito.any());
				Mockito.verify(presentadorVentanasMock, times(llamaAPresentadorVentanasPresentarError)).presentarError(eq("Revise sus campos"), any(), any());
				Mockito.verify(presentadorVentanasMock, times(llamaAPresentadorVentanasPresentarExcepcion)).presentarExcepcion(eq(excepcion), any());
				Mockito.verify(scenographyChangerMock, times(llamaACambiarScene)).cambiarScenography(ModificarClienteController.URLVista, false);
				Mockito.verify(modificarClienteControllerMock, times(llamaACambiarScene)).setClienteEnModificacion(cliente);
			}
		};

		corredorTestEnJavaFXThread.apply(test, null).evaluate();

	}

	protected Object[] parametersForTestCrearCliente() {
		TipoDocumento doc = new TipoDocumento(TipoDocumentoStr.DNI);
		InmuebleBuscado inm = new InmuebleBuscado(null, 1000000.00, 10.0, 10, 1, 1, true, true, false, false, true, true, true, true, true, true, false);
		return new Object[] {
				//nombre,apellido,tipoDocumento,numeroDocumento,telefono,correo,inmueble,resultadoCrearClienteEsperado,llamaAPresentadorVentanasPresentarError,llamaAPresentadorVentanasPresentarExcepcion,llamaACrearCliente,excepcion,aceptarVentanaConfirmacion,llamaACambiarScene
				//prueba correcta
				/*0*/ new Object[] { "Juan", "Perez", doc, "12345678", "123-123", "asdf@asf.com", inm, resultadoCorrecto, 0, 0, 1, null, true, 0 },
				//prueba nombre incorrecto
				/*1*/ new Object[] { "Juan", "Perez", doc, "12345678", "123-123", "asdf@asf.com", inm, resultadoCrearNombreIncorrecto, 1, 0, 1, null, true, 0 },
				//prueba apellido incorrecto
				/*2*/ new Object[] { "Juan", "Perez", doc, "12345678", "123-123", "asdf@asf.com", inm, resultadoCrearApellidoIncorrecto, 1, 0, 1, null, true, 0 },
				//prueba documento incorrecto
				/*3*/ new Object[] { "Juan", "Perez", doc, "12345678", "123-123", "asdf@asf.com", inm, resultadoCrearDocumentoIncorrecto, 1, 0, 1, null, true, 0 },
				//prueba teléfono incorrecto
				/*4*/ new Object[] { "Juan", "Perez", doc, "12345678", "123-123", "asdf@asf.com", inm, resultadoCrearTelefonoIncorrecto, 1, 0, 1, null, true, 0 },
				//prueba correo incorrecto
				/*5*/ new Object[] { "Juan", "Perez", doc, "12345678", "123-123", "asdf@asf.com", inm, resultadoCrearCorreoIncorrecto, 1, 0, 1, null, true, 0 },
				//prueba ya existe cliente
				/*6*/ new Object[] { "Juan", "Perez", doc, "12345678", "123-123", "asdf@asf.com", inm, resultadoCrearYaExiste, 1, 0, 1, null, true, 0 },
				//prueba ya existe cliente
				/*7*/ new Object[] { "Juan", "Perez", doc, "12345678", "123-123", "asdf@asf.com", inm, new ResultadoCrearCliente(ErrorCrearCliente.Formato_Nombre_Incorrecto, ErrorCrearCliente.Formato_Apellido_Incorrecto), 1, 0, 1, null, true, 0 },
				//prueba nombre vacio
				/*8*/ new Object[] { "", "Perez", doc, "12345678", "123-123", "asdf@asf.com", inm, null, 1, 0, 0, null, true, 0 },
				//prueba cliente Existente y acepta
				/*9*/ new Object[] { "Juan", "Perez", doc, "12345678", "123-123", "asdf@asf.com", inm, resultadoCorrecto, 0, 0, 1, new EntidadExistenteConEstadoBajaException(), true, 1 },
				//prueba cliente Existente y cancela
				/*10*/ new Object[] { "Juan", "Perez", doc, "12345678", "123-123", "asdf@asf.com", inm, resultadoCorrecto, 0, 0, 1, new EntidadExistenteConEstadoBajaException(), false, 0 },
				//prueba PersistenciaException
				/*11*/ new Object[] { "Juan", "Perez", doc, "12345678", "123-123", "asdf@asf.com", inm, resultadoCorrecto, 0, 1, 1, new SaveUpdateException(new Throwable()), false, 0 }

		};
	}

	//Para crearCliente
	private static final ResultadoCrearCliente resultadoCorrecto = new ResultadoCrearCliente();
	private static final ResultadoCrearCliente resultadoCrearNombreIncorrecto =
			new ResultadoCrearCliente(ResultadoCrearCliente.ErrorCrearCliente.Formato_Nombre_Incorrecto);
	private static final ResultadoCrearCliente resultadoCrearApellidoIncorrecto =
			new ResultadoCrearCliente(ResultadoCrearCliente.ErrorCrearCliente.Formato_Apellido_Incorrecto);
	private static final ResultadoCrearCliente resultadoCrearDocumentoIncorrecto =
			new ResultadoCrearCliente(ResultadoCrearCliente.ErrorCrearCliente.Formato_Documento_Incorrecto);
	private static final ResultadoCrearCliente resultadoCrearTelefonoIncorrecto =
			new ResultadoCrearCliente(ResultadoCrearCliente.ErrorCrearCliente.Formato_Telefono_Incorrecto);
	private static final ResultadoCrearCliente resultadoCrearCorreoIncorrecto =
			new ResultadoCrearCliente(ResultadoCrearCliente.ErrorCrearCliente.Formato_Correo_Incorrecto);
	private static final ResultadoCrearCliente resultadoCrearYaExiste =
			new ResultadoCrearCliente(ResultadoCrearCliente.ErrorCrearCliente.Ya_Existe_Cliente);

}
