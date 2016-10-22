package app.logica.gestores;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import app.datos.entidades.Cliente;
import app.logica.resultados.ResultadoCrearCliente;

public class GestorClienteTest {

	@Mock
	private GestorCliente gestorClienteMock;

	@Mock
	private Cliente clienteMock;

	@Mock
	ResultadoCrearCliente resultadoCrearClienteMock;

	@BeforeClass
	public static void setUp() {

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