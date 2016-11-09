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
import app.logica.resultados.ResultadoEliminarCliente.ErrorEliminarCliente;
import app.logica.resultados.ResultadoModificarCliente;
import app.logica.resultados.ResultadoModificarCliente.ErrorModificarCliente;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
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
	private static final ResultadoModificarCliente resultadoModificarYaSePoseeMismoDocumento =
			new ResultadoModificarCliente(ErrorModificarCliente.Otro_Cliente_Posee_Mismo_Documento_Y_Tipo);

	//Para eliminar cliente
	private static final ResultadoEliminarCliente resultadoCorrectoEliminar = new ResultadoEliminarCliente();
	private static final ResultadoEliminarCliente resultadoEliminarNoExisteCliente =
			new ResultadoEliminarCliente(ErrorEliminarCliente.No_Existe_Cliente);


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
				.setInmuebleBuscado(inm);

		//Parámetros de JUnitParams
		return new Object[] {
				new Object[] { true, true, true, true, null, 1, resultadoCorrecto },
				new Object[] { false, true, true, true, null, 0, resultadoCrearNombreIncorrecto },
				new Object[] { true, false, true, true, null, 0, resultadoCrearApellidoIncorrecto },
				new Object[] { true, true, false, true, null, 0, resultadoCrearDocumentoIncorrecto },
				new Object[] { true, true, true, false, null, 0, resultadoCrearTelefonoIncorrecto },
				new Object[] { false, false, true, true, null, 0, new ResultadoCrearCliente(ErrorCrearCliente.Formato_Nombre_Incorrecto, ErrorCrearCliente.Formato_Apellido_Incorrecto) },
				new Object[] { true, true, true, true, cliente, 0, resultadoCrearYaExiste }
		};
	}

	@Test
	@Parameters
	public void testCrearCliente(Boolean resValNombre, Boolean resValApellido, Boolean resValDocumento, Boolean resValTelefono, Cliente resObtenerCliente, Integer guardar, ResultadoCrearCliente resultadoCrearClienteEsperado) throws Exception {
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
				new Object[] { true, true, true, true, clienteM, 1, resultadoCorrectoModificar },
				new Object[] { false, true, true, true, clienteM, 0, resultadoModificarNombreIncorrecto },
				new Object[] { true, false, true, true, clienteM, 0, resultadoModificarApellidoIncorrecto },
				new Object[] { true, true, false, true, clienteM, 0, resultadoModificarDocumentoIncorrecto },
				new Object[] { true, true, true, false, clienteM, 0, resultadoModificarTelefonoIncorrecto },
				new Object[] { false, false, true, true, clienteM, 0, new ResultadoModificarCliente(ErrorModificarCliente.Formato_Nombre_Incorrecto, ErrorModificarCliente.Formato_Apellido_Incorrecto) },
				new Object[] { true, true, true, true, clienteM2, 0, resultadoModificarYaSePoseeMismoDocumento }
		};
	}

	@Test
	@Parameters
	public void testModificarCliente(Boolean resValNombre, Boolean resValApellido, Boolean resValDocumento, Boolean resValTelefono, Cliente resObtenerCliente, Integer modificar, ResultadoModificarCliente resultadoModificarClienteEsperado) throws Exception {
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
				new Object[] { clienteE, 1, resultadoCorrectoEliminar },
				new Object[] { null, 0, resultadoEliminarNoExisteCliente },
		};
	}

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