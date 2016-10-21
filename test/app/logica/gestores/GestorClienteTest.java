package test.app.logica.gestores;

import app.datos.entidades.Cliente;
import app.logica.gestores.GestorCliente;
import app.logica.resultados.ResultadoCrearCliente;
import org.junit.Assert;
import org.junit.Test;

import static app.logica.resultados.ResultadoCrearCliente.ErrorResultadoCrearCliente.Formato_Nombre_Incorrecto;


public class GestorClienteTest {
    @Test
    public void crearCliente() throws Exception {

        GestorCliente gestorCliente = new GestorCliente();

        Cliente cliente = new Cliente(0, "", "", "", "");
        ResultadoCrearCliente resultadoCrearClienteOK = new ResultadoCrearCliente(Formato_Nombre_Incorrecto);


        ResultadoCrearCliente resultadoCrearCliente = gestorCliente.crearCliente(cliente);

    }

}