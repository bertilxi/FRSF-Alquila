package app.logica.gestores;

import app.datos.entidades.Cliente;
import app.logica.gestores.GestorCliente;
import app.logica.resultados.ResultadoCrearCliente;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static app.logica.resultados.ResultadoCrearCliente.ErrorResultadoCrearCliente.Formato_Nombre_Incorrecto;


public class GestorClienteTest {

    @Mock
    private GestorCliente gestorClienteMock;

    @Mock
    private Cliente clienteMock;

    @Mock ResultadoCrearCliente resultadoCrearClienteMock;

    @BeforeClass
    public static void setUp(){

    }

    @Test
    public void crearCliente() throws Exception {

        // lo que deberia ser
        Mockito.when(gestorClienteMock.crearCliente(clienteMock)).thenReturn(resultadoCrearClienteMock);

        // lo que devuelve el metodo
        ResultadoCrearCliente resultadoCrearCliente = gestorClienteMock.crearCliente(clienteMock)

        Assert.assertEquals(resultadoCrearCliente,resultadoCrearClienteMock);


    }

}