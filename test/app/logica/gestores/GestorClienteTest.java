package app.logica.gestores;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import app.comun.ValidadorFormato;
import app.datos.clases.EstadoStr;
import app.datos.clases.FiltroCliente;
import app.datos.clases.TipoDocumentoStr;
import app.datos.entidades.Cliente;
import app.datos.entidades.Estado;
import app.datos.entidades.TipoDocumento;
import app.datos.servicios.ClienteService;
import app.logica.resultados.ResultadoCrearCliente;
import app.logica.resultados.ResultadoCrearCliente.ErrorCrearCliente;
import app.logica.resultados.ResultadoModificarCliente;
import app.logica.resultados.ResultadoModificarCliente.ErrorModificarCliente;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class GestorClienteTest {

	private static ValidadorFormato validadorMock;
	private static Cliente cliente;
	private static GestorCliente gestorCliente;
	private static ClienteService clienteService;
	private static FiltroCliente filtro;

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

	@BeforeClass
	public static void setUp() throws Exception {
		//Setear valores esperados a los mocks
		validadorMock = Mockito.mock(ValidadorFormato.class);
		Mockito.when(validadorMock.validarNombre(cliente.getNombre())).thenReturn(true);
		Mockito.when(validadorMock.validarApellido(cliente.getApellido())).thenReturn(true);
		Mockito.when(validadorMock.validarDocumento(cliente.getTipoDocumento(), cliente.getNumeroDocumento())).thenReturn(true);
		Mockito.when(validadorMock.validarTelefono(cliente.getTelefono())).thenReturn(true);
		Mockito.when(clienteService.obtenerCliente(filtro)).thenReturn(cliente);
		Mockito.doNothing().when(clienteService).guardarCliente(cliente); //Para métodos void la sintaxis es distinta

	}

	@Test
	@Parameters
	public void crearCliente(String nombre, String apellido, TipoDocumento tipoDocumento, String numeroDocumento,
			String telefono, Estado estado, Boolean shouldSuccess) throws Exception {

		clienteService = Mockito.mock(ClienteService.class);

		gestorCliente = new GestorCliente() {
			{
				this.persistidorCliente = clienteService;
				this.validador = validadorMock;
			}
		};

		cliente.setNombre(nombre).setApellido(apellido).setTipoDocumento(tipoDocumento)
				.setNumeroDocumento(numeroDocumento).setTelefono(telefono).setEstado(estado);

	}

	protected Object[] parametersForCrearCliente() {
		return new Object[] {
				// Prueba todos los datos correctos
				new Object[] { "Jose", "Perez", new TipoDocumento(TipoDocumentoStr.DNI), "30123456",
						"03424802234", new Estado(EstadoStr.ALTA), true },
				// Prueba nombre vacio
				new Object[] { null, "Perez", new TipoDocumento(TipoDocumentoStr.DNI), "30123456",
						"03424802234", new Estado(EstadoStr.ALTA), false },
				// Prueba apellido vaio
				new Object[] { "Jose", null, new TipoDocumento(TipoDocumentoStr.DNI), "30123456",
						"03424802234", new Estado(EstadoStr.ALTA), false },
				// Prueba tipo documento vacio
				new Object[] { "Jose", "Perez", null, "30123456",
						"03424802234", new Estado(EstadoStr.ALTA), false },
				// Prueba numero documento vacio
				new Object[] { "Jose", "Perez", new TipoDocumento(TipoDocumentoStr.DNI), null,
						"03424802234", new Estado(EstadoStr.ALTA), false },
				// Prueba estado vacio
				new Object[] { "Jose", "Perez", new TipoDocumento(TipoDocumentoStr.DNI), "30123456",
						"03424802234", new Estado(EstadoStr.ALTA), false },
				// prueba estado en baja
				new Object[] { "Jose", "Perez", new TipoDocumento(TipoDocumentoStr.DNI), "30123456",
						"03424802234", new Estado(EstadoStr.ALTA), false },
				// prueba estado en alta
				new Object[] { "Jose", "Perez", new TipoDocumento(TipoDocumentoStr.DNI), "30123456",
						"03424802234", new Estado(EstadoStr.ALTA), false }
		};
	}

}