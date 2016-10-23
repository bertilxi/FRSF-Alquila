package app.logica.gestores;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import app.datos.entidades.Cliente;
import app.logica.resultados.ResultadoCrearCliente;
import app.logica.resultados.ResultadoCrearCliente.ErrorCrearCliente;
import app.logica.resultados.ResultadoModificarCliente;
import app.logica.resultados.ResultadoModificarCliente.ErrorModificarCliente;

public class GestorClienteTest {

	// Para crearCliente
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

	@Mock
	private static GestorCliente gestorClienteMock;
	private static Cliente cliente1 = new Cliente();
	private static Cliente cliente2 = new Cliente();
	private static Cliente cliente3 = new Cliente();
	private static Cliente cliente4 = new Cliente();
	private static Cliente cliente5 = new Cliente();
	private static Cliente cliente6 = new Cliente();
	private static Cliente cliente7 = new Cliente();
	private static Cliente cliente8 = new Cliente();
	private static Cliente cliente9 = new Cliente();
	private static Cliente cliente10 = new Cliente();
	@Mock
	ResultadoCrearCliente resultadoCrearClienteMock;

	@BeforeClass
	public static void setUp() {

		cliente1.setId(0).setNombre().setApellido().setTipoDocumento().setNumeroDocumento().setTelefono();
		cliente2.setId(1).setNombre().setApellido().setTipoDocumento().setNumeroDocumento().setTelefono();
		cliente3.setId(2).setNombre().setApellido().setTipoDocumento().setNumeroDocumento().setTelefono();
		cliente4.setId(3).setNombre().setApellido().setTipoDocumento().setNumeroDocumento().setTelefono();
		cliente5.setId(4).setNombre().setApellido().setTipoDocumento().setNumeroDocumento().setTelefono();
		cliente6.setId(5).setNombre().setApellido().setTipoDocumento().setNumeroDocumento().setTelefono();
		cliente7.setId(6).setNombre().setApellido().setTipoDocumento().setNumeroDocumento().setTelefono();
		cliente8.setId(7).setNombre().setApellido().setTipoDocumento().setNumeroDocumento().setTelefono();
		cliente9.setId(8).setNombre().setApellido().setTipoDocumento().setNumeroDocumento().setTelefono();
		cliente10.setId(9).setNombre().setApellido().setTipoDocumento().setNumeroDocumento().setTelefono();

		Mockito.when(gestorClienteMock.crearCliente(cliente1)).thenReturn();
		Mockito.when(gestorClienteMock.crearCliente(cliente2)).thenReturn();
		Mockito.when(gestorClienteMock.crearCliente(cliente3)).thenReturn();
		Mockito.when(gestorClienteMock.crearCliente(cliente4)).thenReturn();
		Mockito.when(gestorClienteMock.modificarCliente(cliente5)).thenReturn();
		Mockito.when(gestorClienteMock.modificarCliente(cliente6)).thenReturn();
		Mockito.when(gestorClienteMock.modificarCliente(cliente7)).thenReturn();
		Mockito.when(gestorClienteMock.modificarCliente(cliente8)).thenReturn();
		Mockito.when(gestorClienteMock.modificarCliente(cliente9)).thenReturn();
		Mockito.when(gestorClienteMock.modificarCliente(cliente10)).thenReturn();

	}

	@Test
    public void crearCliente() throws Exception {

		// lo que deberia ser
		Mockito.when(gestorClienteMock.crearCliente(clienteMock)).thenReturn(resultadoCrearClienteMock);

		// lo que devuelve el metodo
		ResultadoCrearCliente resultadoCrearCliente = gestorClienteMock.crearCliente(clienteMock);

		Assert.assertEquals(resultadoCrearCliente, resultadoCrearClienteMock);

	}

}