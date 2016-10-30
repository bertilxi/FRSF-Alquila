package app.logica.gestores;

import app.comun.ValidadorFormato;
import app.datos.clases.EstadoStr;
import app.datos.clases.TipoDocumentoStr;
import app.datos.entidades.Estado;
import app.datos.entidades.TipoDocumento;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;

import app.datos.clases.FiltroCliente;
import app.datos.entidades.Cliente;
import app.datos.servicios.ClienteService;
import app.logica.resultados.ResultadoCrearCliente;
import app.logica.resultados.ResultadoCrearCliente.ErrorCrearCliente;
import app.logica.resultados.ResultadoModificarCliente;
import app.logica.resultados.ResultadoModificarCliente.ErrorModificarCliente;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
@PrepareForTest({ ValidadorFormato.class, })
public class GestorClienteTest {

	@Rule
	public PowerMockRule rule = new PowerMockRule();
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
	public static void setUp() {
        //Setear valores esperados a los mocks
        PowerMockito.when(ValidadorFormato.validarNombre(cliente.getNombre())).thenReturn(resValNombre);
        PowerMockito.when(ValidadorFormato.validarApellido(cliente.getApellido())).thenReturn(resValApellido);
        PowerMockito.when(ValidadorFormato.validarDocumento(cliente.getTipoDocumento(), cliente.getNumeroDocumento())).thenReturn(resValDocumento);
        PowerMockito.when(ValidadorFormato.validarTelefono(cliente.getTelefono())).thenReturn(resValTelefono);
        PowerMockito.when(ValidadorFormato.validarEmail(cliente.getEmail())).thenReturn(resValEmail);
        PowerMockito.when(ValidadorFormato.validarDireccion(cliente.getDireccion())).thenReturn(resValDireccion);
        PowerMockito.when(clienteService.obtenercliente(filtro)).thenReturn(resObtenercliente);
        PowerMockito.doNothing().when(clienteService).guardarcliente(cliente); //Para métodos void la sintaxis es distinta

    }

	@Test
	@Parameters(method = "clientesValues")
	public void crearCliente(String nombre, String apellido, TipoDocumento tipoDocumento, String numeroDocumento,
			String telefono, Estado estado, Boolean shouldSuccess) throws Exception {

		clienteService = PowerMockito.mock(ClienteService.class);
		PowerMockito.mockStatic(ValidadorFormato.class);

		gestorCliente = new GestorCliente() {
			{
				this.persistidorCliente = clienteService;
			}
		};

		cliente.setId(null).setNombre(nombre).setApellido(apellido).setTipoDocumento(tipoDocumento)
				.setNumeroDocumento(numeroDocumento).setTelefono(telefono).setEstado(estado);

	}

	private Object[] clientesValues() {
		return new Object[] {
				// Prueba todos los datos correctos
				new Object[] { "Jose", "Perez", new TipoDocumento(null, TipoDocumentoStr.DNI), "30123456",
                        "03424802234", new Estado(null, EstadoStr.ALTA), true },
				// Prueba nombre vacio
				new Object[] { null, "Perez", new TipoDocumento(null, TipoDocumentoStr.DNI), "30123456",
                        "03424802234", new Estado(null, EstadoStr.ALTA), false },
				// Prueba apellido vaio
				new Object[] { "Jose", null, new TipoDocumento(null, TipoDocumentoStr.DNI), "30123456",
                        "03424802234", new Estado(null, EstadoStr.ALTA), false },
                // Prueba tipo documento vacio
                new Object[] { "Jose", "Perez", null, "30123456",
                        "03424802234", new Estado(null, EstadoStr.ALTA), false },
                // Prueba numero documento vacio
                new Object[] { "Jose", "Perez", new TipoDocumento(null, TipoDocumentoStr.DNI), null,
                        "03424802234", new Estado(null, EstadoStr.ALTA), false },
				// Prueba estado vacio
				new Object[] { "Jose", "Perez", new TipoDocumento(null, TipoDocumentoStr.DNI), "30123456",
                        "03424802234", new Estado(null, EstadoStr.ALTA), false },
				// prueba estado en baja
				new Object[] { "Jose", "Perez", new TipoDocumento(null, TipoDocumentoStr.DNI), "30123456",
                        "03424802234", new Estado(null, EstadoStr.ALTA), false },
				// prueba estado en alta
				new Object[] { "Jose", "Perez", new TipoDocumento(null, TipoDocumentoStr.DNI), "30123456",
                        "03424802234", new Estado(null, EstadoStr.ALTA), false }
		};
	}

}