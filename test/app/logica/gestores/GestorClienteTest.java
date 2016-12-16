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
package app.logica.gestores;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;

import app.comun.ValidadorFormato;
import app.datos.clases.EstadoStr;
import app.datos.clases.TipoDocumentoStr;
import app.datos.entidades.Cliente;
import app.datos.entidades.Estado;
import app.datos.entidades.InmuebleBuscado;
import app.datos.entidades.TipoDocumento;
import app.datos.servicios.ClienteService;
import app.logica.resultados.ResultadoCrearCliente;
import app.logica.resultados.ResultadoCrearCliente.ErrorCrearCliente;
import app.logica.resultados.ResultadoEliminarCliente;
import app.logica.resultados.ResultadoModificarCliente;
import app.logica.resultados.ResultadoModificarCliente.ErrorModificarCliente;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
//Modificada en TaskCard 27 de la iteración 2
public class GestorClienteTest {

	// Para crearCliente
	private static final ResultadoCrearCliente resultadoCorrecto = new ResultadoCrearCliente();
	private static final ResultadoCrearCliente resultadoCrearNombreIncorrecto =
			new ResultadoCrearCliente(ErrorCrearCliente.Formato_Nombre_Incorrecto);
	private static final ResultadoCrearCliente resultadoCrearApellidoIncorrecto =
			new ResultadoCrearCliente(ErrorCrearCliente.Formato_Apellido_Incorrecto);
	private static final ResultadoCrearCliente resultadoCrearDocumentoIncorrecto =
			new ResultadoCrearCliente(ErrorCrearCliente.Formato_Documento_Incorrecto);
	private static final ResultadoCrearCliente resultadoCrearTelefonoIncorrecto =
			new ResultadoCrearCliente(ErrorCrearCliente.Formato_Telefono_Incorrecto);
	private static final ResultadoCrearCliente resultadoCrearCorreoIncorrecto =
			new ResultadoCrearCliente(ErrorCrearCliente.Formato_Correo_Incorrecto);
	private static final ResultadoCrearCliente resultadoCrearYaExiste =
			new ResultadoCrearCliente(ErrorCrearCliente.Ya_Existe_Cliente);
	// Para modificarCliente
	private static final ResultadoModificarCliente resultadoCorrectoModificar = new ResultadoModificarCliente();
	private static final ResultadoModificarCliente resultadoModificarApellidoIncorrecto =
			new ResultadoModificarCliente(ErrorModificarCliente.Formato_Apellido_Incorrecto);
	private static final ResultadoModificarCliente resultadoModificarDocumentoIncorrecto =
			new ResultadoModificarCliente(ErrorModificarCliente.Formato_Documento_Incorrecto);
	private static final ResultadoModificarCliente resultadoModificarNombreIncorrecto =
			new ResultadoModificarCliente(ErrorModificarCliente.Formato_Nombre_Incorrecto);
	private static final ResultadoModificarCliente resultadoModificarTelefonoIncorrecto =
			new ResultadoModificarCliente(ErrorModificarCliente.Formato_Telefono_Incorrecto);
	private static final ResultadoModificarCliente resultadoModificarCorreoIncorrecto =
			new ResultadoModificarCliente(ErrorModificarCliente.Formato_Correo_Incorrecto);
	private static final ResultadoModificarCliente resultadoModificarYaSePoseeMismoDocumento =
			new ResultadoModificarCliente(ErrorModificarCliente.Otro_Cliente_Posee_Mismo_Documento_Y_Tipo);

	//Para eliminar cliente
	private static final ResultadoEliminarCliente resultadoCorrectoEliminar = new ResultadoEliminarCliente();

	private static Cliente cliente;

	protected Object[] parametersForTestCrearCliente() {
		//Se carga cliente con datos básicos solo para evitar punteros nulos
		//Las validaciones se hacen en el validador, por lo que se setean luego en los mocks los valores esperados
		TipoDocumento doc = new TipoDocumento(TipoDocumentoStr.DNI);
		cliente = new Cliente();
		InmuebleBuscado inm = new InmuebleBuscado(cliente, 4567.2, 345.9, 10, 2, 1, true, true, false, false, true, true, true, true, true, true, false);
		cliente.setNombre("Juan")
				.setApellido("Pérez")
				.setNumeroDocumento("12345678")
				.setTipoDocumento(doc)
				.setTelefono("1234-1234")
				.setCorreo("asdf@asf.com")
				.setInmuebleBuscado(inm);

		//Parámetros de JUnitParams
		return new Object[] {
				//resValNombre,resValApellido,resValDocumento,resValTelefono,resValCorreo,resObtenerCliente,guardar,resultadoCrearClienteEsperado
				/*0*/ new Object[] { true, true, true, true, true, null, 1, resultadoCorrecto },
				/*1*/ new Object[] { false, true, true, true, true, null, 0, resultadoCrearNombreIncorrecto },
				/*2*/ new Object[] { true, false, true, true, true, null, 0, resultadoCrearApellidoIncorrecto },
				/*3*/ new Object[] { true, true, false, true, true, null, 0, resultadoCrearDocumentoIncorrecto },
				/*4*/ new Object[] { true, true, true, false, true, null, 0, resultadoCrearTelefonoIncorrecto },
				/*5*/ new Object[] { true, true, true, true, false, null, 0, resultadoCrearCorreoIncorrecto },
				/*6*/ new Object[] { false, false, true, true, true, null, 0, new ResultadoCrearCliente(ErrorCrearCliente.Formato_Nombre_Incorrecto, ErrorCrearCliente.Formato_Apellido_Incorrecto) },
				/*7*/ new Object[] { true, true, true, true, true, cliente, 0, resultadoCrearYaExiste }
		};
	}

	/**
	 * Test para probar el método crearCliente
	 *
	 * @param resValNombre
	 * 			resultado devuelto por el mock validador de formato al validar nombre
	 * @param resValApellido
	 * 			resultado devuelto por el mock validador de formato al validar apellido
	 * @param resValDocumento
	 * 			resultado devuelto por el mock validador de formato al validar documento
	 * @param resValTelefono
	 * 			resultado devuelto por el mock validador de formato al validar teléfono
	 * @param resValCorreo
	 * 			resultado devuelto por el mock validador de formato al validar correo
	 * @param resObtenerCliente
	 * 			resultado devuelto por el mock persistidor al obtener cliente
	 * @param guardar
	 * 			1 si se espera que se ejecute el guardar hacia capa de persistencia, 0 si no
	 * @param resultadoCrearClienteEsperado
	 * 			resultado que se espera que retorne el método a probar
	 * @throws Exception
	 */
	@Test
	@Parameters
	public void testCrearCliente(Boolean resValNombre, Boolean resValApellido, Boolean resValDocumento, Boolean resValTelefono, Boolean resValCorreo, Cliente resObtenerCliente, Integer guardar, ResultadoCrearCliente resultadoCrearClienteEsperado) throws Exception {
		//Inicialización de los mocks
		ClienteService clienteServiceMock = mock(ClienteService.class);
		ValidadorFormato validadorFormatoMock = mock(ValidadorFormato.class);
		GestorDatos gestorDatosMock = mock(GestorDatos.class);

		GestorCliente gestorCliente = new GestorCliente() {
			{
				this.persistidorCliente = clienteServiceMock;
				this.validador = validadorFormatoMock;
				this.gestorDatos = gestorDatosMock;
			}
		};

		ArrayList<Estado> estados = new ArrayList<>();
		estados.add(new Estado(EstadoStr.ALTA));

		//Setear valores esperados a los mocks
		when(validadorFormatoMock.validarNombre(cliente.getNombre())).thenReturn(resValNombre);
		when(validadorFormatoMock.validarApellido(cliente.getApellido())).thenReturn(resValApellido);
		when(validadorFormatoMock.validarDocumento(cliente.getTipoDocumento(), cliente.getNumeroDocumento())).thenReturn(resValDocumento);
		when(validadorFormatoMock.validarTelefono(cliente.getTelefono())).thenReturn(resValTelefono);
		when(validadorFormatoMock.validarEmail(cliente.getCorreo())).thenReturn(resValCorreo);
		when(clienteServiceMock.obtenerCliente(any())).thenReturn(resObtenerCliente);
		when(gestorDatosMock.obtenerEstados()).thenReturn(estados);
		doNothing().when(clienteServiceMock).guardarCliente(cliente); //Para métodos void la sintaxis es distinta

		//Llamar al método a testear
		ResultadoCrearCliente resultadoCrearCliente = gestorCliente.crearCliente(cliente);

		//Comprobar resultados obtenidos, que se llaman a los métodos deseados y con los parámetros correctos
		assertEquals(resultadoCrearClienteEsperado, resultadoCrearCliente);
		if(guardar.equals(1)){
			assertEquals(EstadoStr.ALTA, cliente.getEstado().getEstado());
		}
		verify(validadorFormatoMock).validarNombre(cliente.getNombre());
		verify(validadorFormatoMock).validarApellido(cliente.getApellido());
		verify(validadorFormatoMock).validarDocumento(cliente.getTipoDocumento(), cliente.getNumeroDocumento());
		verify(validadorFormatoMock).validarTelefono(cliente.getTelefono());
		verify(validadorFormatoMock).validarEmail(cliente.getCorreo());
		verify(gestorDatosMock, times(guardar)).obtenerEstados();
		verify(clienteServiceMock, times(guardar)).guardarCliente(cliente);
	}

	private static Cliente clienteM;
	private static Cliente clienteM2;

	protected Object[] parametersForTestModificarCliente() {
		//Se carga cliente con datos básicos solo para evitar punteros nulos
		//Las validaciones se hacen en el validador, por lo que se setean luego en los mocks los valores esperados
		TipoDocumento doc = new TipoDocumento(TipoDocumentoStr.DNI);
		Estado est = new Estado(EstadoStr.ALTA);
		clienteM = new Cliente() {

			public Cliente setId(Integer id) {
				this.id = id;
				return this;
			}

			{
				this.setId(1)
						.setNombre("Juan")
						.setApellido("Pérez")
						.setNumeroDocumento("12345678")
						.setTipoDocumento(doc)
						.setEstado(est);
			}
		};
		InmuebleBuscado inm = new InmuebleBuscado(clienteM, 4567.2, 345.9, 10, 2, 1, true, true, false, false, true, true, true, true, true, true, false);
		clienteM.setInmuebleBuscado(inm);

		clienteM2 = new Cliente() {

			public Cliente setId(Integer id) {
				this.id = id;
				return this;
			}

			{
				this.setId(2)
						.setNombre("Juan")
						.setApellido("Pérez")
						.setNumeroDocumento("12345678")
						.setTipoDocumento(doc);
			}
		};

		InmuebleBuscado inm2 = new InmuebleBuscado(clienteM2, 4567.2, 345.9, 10, 2, 1, true, true, false, false, true, true, true, true, true, true, false);
		clienteM2.setInmuebleBuscado(inm2);

		//Parámetros de JUnitParams
		return new Object[] {
				//resValNombre,resValApellido,resValDocumento,resValTelefono,resValCorreo,resObtenerCliente,modificar,resultadoModificarClienteEsperado
				/*0*/ new Object[] { true, true, true, true, true, clienteM, 1, resultadoCorrectoModificar },
				/*1*/ new Object[] { false, true, true, true, true, clienteM, 0, resultadoModificarNombreIncorrecto },
				/*2*/ new Object[] { true, false, true, true, true, clienteM, 0, resultadoModificarApellidoIncorrecto },
				/*3*/ new Object[] { true, true, false, true, true, clienteM, 0, resultadoModificarDocumentoIncorrecto },
				/*4*/ new Object[] { true, true, true, false, true, clienteM, 0, resultadoModificarTelefonoIncorrecto },
				/*5*/ new Object[] { true, true, true, true, false, clienteM, 0, resultadoModificarCorreoIncorrecto },
				/*6*/ new Object[] { false, false, true, true, true, clienteM, 0, new ResultadoModificarCliente(ErrorModificarCliente.Formato_Nombre_Incorrecto, ErrorModificarCliente.Formato_Apellido_Incorrecto) },
				/*7*/ new Object[] { true, true, true, true, true, clienteM2, 0, resultadoModificarYaSePoseeMismoDocumento }
		};
	}

	/**
	 * Test para probar el método modificarCliente
	 *
	 * @param resValNombre
	 * 			resultado devuelto por el mock validador de formato al validar nombre
	 * @param resValApellido
	 * 			resultado devuelto por el mock validador de formato al validar apellido
	 * @param resValDocumento
	 * 			resultado devuelto por el mock validador de formato al validar documento
	 * @param resValTelefono
	 * 			resultado devuelto por el mock validador de formato al validar teléfono
	 * @param resValCorreo
	 * 			resultado devuelto por el mock validador de formato al validar correo
	 * @param resObtenerCliente
	 * 			resultado devuelto por el mock persistidor al obtener cliente
	 * @param modificar
	 * 			1 si se espera que se ejecute el modificar hacia capa de persistencia, 0 si no
	 * @param resultadoModificarClienteEsperado
	 * 			resultado que se espera que retorne el método a probar
	 * @throws Exception
	 */
	@Test
	@Parameters
	public void testModificarCliente(Boolean resValNombre, Boolean resValApellido, Boolean resValDocumento, Boolean resValTelefono, Boolean resValCorreo, Cliente resObtenerCliente, Integer modificar, ResultadoModificarCliente resultadoModificarClienteEsperado) throws Exception {
		//Inicialización de los mocks
		ClienteService clienteServiceMock = mock(ClienteService.class);
		ValidadorFormato validadorFormatoMock = mock(ValidadorFormato.class);
		GestorDatos gestorDatosMock = mock(GestorDatos.class);

		//Clase anónima necesaria para inyectar dependencias hasta que funcione Spring
		GestorCliente gestorCliente = new GestorCliente() {
			{
				this.persistidorCliente = clienteServiceMock;
				this.validador = validadorFormatoMock;
				this.gestorDatos = gestorDatosMock;
			}
		};

		ArrayList<Estado> estados = new ArrayList<>();
		estados.add(new Estado(EstadoStr.ALTA));

		//Setear valores esperados a los mocks
		when(validadorFormatoMock.validarNombre(clienteM.getNombre())).thenReturn(resValNombre);
		when(validadorFormatoMock.validarApellido(clienteM.getApellido())).thenReturn(resValApellido);
		when(validadorFormatoMock.validarDocumento(clienteM.getTipoDocumento(), cliente.getNumeroDocumento())).thenReturn(resValDocumento);
		when(validadorFormatoMock.validarTelefono(clienteM.getTelefono())).thenReturn(resValTelefono);
		when(validadorFormatoMock.validarEmail(clienteM.getCorreo())).thenReturn(resValCorreo);
		when(clienteServiceMock.obtenerCliente(any())).thenReturn(resObtenerCliente);
		when(gestorDatosMock.obtenerEstados()).thenReturn(estados);
		doNothing().when(clienteServiceMock).modificarCliente(clienteM); //Para métodos void la sintaxis es distinta

		//Llamar al método a testear
		ResultadoModificarCliente resultadoModificarCliente = gestorCliente.modificarCliente(clienteM);

		//Comprobar resultados obtenidos, que se llaman a los métodos deseados y con los parámetros correctos
		assertEquals(resultadoModificarClienteEsperado, resultadoModificarCliente);
		if(modificar.equals(1)){
			assertEquals(EstadoStr.ALTA, clienteM.getEstado().getEstado());
		}
		verify(validadorFormatoMock).validarNombre(clienteM.getNombre());
		verify(validadorFormatoMock).validarApellido(clienteM.getApellido());
		verify(validadorFormatoMock).validarDocumento(clienteM.getTipoDocumento(), clienteM.getNumeroDocumento());
		verify(validadorFormatoMock).validarTelefono(clienteM.getTelefono());
		verify(validadorFormatoMock).validarEmail(clienteM.getCorreo());
		verify(clienteServiceMock, times(modificar)).modificarCliente(clienteM);
	}

	private static Cliente clienteE;

	protected Object[] parametersForTestEliminarCliente() {
		//Se carga cliente con datos básicos solo para evitar punteros nulos
		//Las validaciones se hacen en el validador, por lo que se setean luego en los mocks los valores esperados
		TipoDocumento doc = new TipoDocumento(TipoDocumentoStr.DNI);
		Estado est = new Estado(EstadoStr.ALTA);
		clienteE = new Cliente();
		InmuebleBuscado inm = new InmuebleBuscado(cliente, 4567.2, 345.9, 10, 2, 1, true, true, false, false, true, true, true, true, true, true, false);
		clienteE.setNombre("Juan")
				.setApellido("Pérez")
				.setNumeroDocumento("12345678")
				.setTipoDocumento(doc)
				.setEstado(est)
				.setInmuebleBuscado(inm);

		//Parámetros de JUnitParams
		return new Object[] {
				//resObtenerCliente,eliminar,resultadoEliminarClienteEsperado
				/*0*/ new Object[] { clienteE, 1, resultadoCorrectoEliminar },
		};
	}

	/**
	 * Test para probar el método eliminarCliente
	 *
	 * @param resObtenerCliente
	 * 			resultado devuelto por el mock persistidor al obtener cliente
	 * @param eliminar
	 * 			1 si se espera que se ejecute el modificar hacia capa de persistencia (ya que es baja lógica), 0 si no
	 * @param resultadoEliminarClienteEsperado
	 * 			resultado que se espera retorne el método a probar
	 * @throws Exception
	 */
	@Test
	@Parameters
	public void testEliminarCliente(Cliente resObtenerCliente, Integer eliminar, ResultadoEliminarCliente resultadoEliminarClienteEsperado) throws Exception {
		//Inicialización de los mocks
		ClienteService clienteServiceMock = mock(ClienteService.class);
		GestorDatos gestorDatosMock = mock(GestorDatos.class);

		//Clase anónima necesaria para inyectar dependencias hasta que funcione Spring
		GestorCliente gestorCliente = new GestorCliente() {
			{
				this.persistidorCliente = clienteServiceMock;
				this.gestorDatos = gestorDatosMock;
			}
		};

		ArrayList<Estado> estados = new ArrayList<>();
		estados.add(new Estado(EstadoStr.BAJA));

		//Setear valores esperados a los mocks
		when(clienteServiceMock.obtenerCliente(any())).thenReturn(resObtenerCliente);
		when(gestorDatosMock.obtenerEstados()).thenReturn(estados);
		doNothing().when(clienteServiceMock).modificarCliente(clienteE); //Para métodos void la sintaxis es distinta

		//Llamar al método a testear
		ResultadoEliminarCliente resultadoEliminarCliente = gestorCliente.eliminarCliente(clienteE);

		//Comprobar resultados obtenidos, que se llaman a los métodos deseados y con los parámetros correctos
		assertEquals(resultadoEliminarClienteEsperado, resultadoEliminarCliente);
		if(eliminar.equals(1)){
			assertEquals(EstadoStr.BAJA, clienteE.getEstado().getEstado());
		}
		verify(gestorDatosMock, times(eliminar)).obtenerEstados();
		verify(clienteServiceMock, times(eliminar)).modificarCliente(clienteE);
	}
}