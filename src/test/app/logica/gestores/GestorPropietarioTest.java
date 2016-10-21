package test.app.logica.gestores;

import app.datos.entidades.Cliente;
import app.datos.entidades.Direccion;
import app.datos.entidades.Inmueble;
import app.datos.entidades.Propietario;
import app.datos.entidades.TipoDocumento;
import app.logica.gestores.GestorCliente;
import app.logica.gestores.GestorPropietario;
import app.logica.resultados.ResultadoCrearCliente;
import test.app.logica.gestores.stubs.PropietarioServiceStub;
import app.logica.resultados.ResultadoCrearPropietario;
import app.logica.resultados.ResultadoCrearPropietario.ErrorResultadoCrearPropietario;

import org.junit.Assert;
import org.junit.Test;

import static app.logica.resultados.ResultadoCrearCliente.ErrorResultadoCrearCliente.Formato_Nombre_Incorrecto;
import static org.junit.Assert.*;

import java.util.ArrayList;


public class GestorPropietarioTest {
	
	private Propietario propietario;
	private GestorPropietario gestorPropietario;
	
	private void setUp() {
		TipoDocumento doc = new TipoDocumento();
		Direccion dir = new Direccion();
		ArrayList<Inmueble> inmuebles = new ArrayList<Inmueble>();
		propietario = new Propietario(1, "Juan", "Perez", "38377777", "3424686868", "d.a@hotmail.com", doc, dir, inmuebles );
		
		gestorPropietario = new GestorPropietario(new PropietarioServiceStub());
	}
	
    @Test
    public void crearPropietarioTestCorrecto() throws Exception {
        
        ResultadoCrearPropietario resultadoCrearPropietarioOK = new ResultadoCrearPropietario();
        ResultadoCrearPropietario resultadoCrearPropietario = gestorPropietario.crearPropietario(propietario);
        assertEquals(resultadoCrearPropietarioOK,resultadoCrearPropietario);
    }
    
    @Test
    public void crearPropietarioTestNombreIncorrecto() throws Exception {
        propietario.setNombre("Nombre Incorrecto por mas de treinta caracteres");
        ResultadoCrearPropietario resultadoCrearPropietarioNombreIncorrecto = new ResultadoCrearPropietario(ErrorResultadoCrearPropietario.Formato_Nombre_Incorrecto);
        ResultadoCrearPropietario resultadoCrearPropietario = gestorPropietario.crearPropietario(propietario);
        assertEquals(resultadoCrearPropietarioNombreIncorrecto,resultadoCrearPropietario);
    }
    
    @Test
    public void crearPropietarioTestNombreIncorrecto2() throws Exception {
        propietario.setNombre("Nombre Incorrect0 por numer0s");
        ResultadoCrearPropietario resultadoCrearPropietarioNombreIncorrecto = new ResultadoCrearPropietario(ErrorResultadoCrearPropietario.Formato_Nombre_Incorrecto);
        ResultadoCrearPropietario resultadoCrearPropietario = gestorPropietario.crearPropietario(propietario);
        assertEquals(resultadoCrearPropietarioNombreIncorrecto,resultadoCrearPropietario);
    }
    
    @Test
    public void crearPropietarioTestNombreNulo() throws Exception {
        propietario.setNombre(null);
        ResultadoCrearPropietario resultadoCrearPropietarioNombreIncorrecto = new ResultadoCrearPropietario(ErrorResultadoCrearPropietario.Formato_Nombre_Incorrecto);
        ResultadoCrearPropietario resultadoCrearPropietario = gestorPropietario.crearPropietario(propietario);
        assertEquals(resultadoCrearPropietarioNombreIncorrecto,resultadoCrearPropietario);
    }
    
    @Test
    public void crearPropietarioTestNombreVacio() throws Exception {
        propietario.setNombre("");
        ResultadoCrearPropietario resultadoCrearPropietarioNombreIncorrecto = new ResultadoCrearPropietario(ErrorResultadoCrearPropietario.Formato_Nombre_Incorrecto);
        ResultadoCrearPropietario resultadoCrearPropietario = gestorPropietario.crearPropietario(propietario);
        assertEquals(resultadoCrearPropietarioNombreIncorrecto,resultadoCrearPropietario);
    }
    
    @Test
    public void crearPropietarioTestApellidoIncorrecto() throws Exception {
        propietario.setApellido("Apellido Incorrecto por mas de treinta caracteres");
        ResultadoCrearPropietario resultadoCrearPropietarioApellidoIncorrecto = new ResultadoCrearPropietario(ErrorResultadoCrearPropietario.Formato_Apellido_Incorrecto);
        ResultadoCrearPropietario resultadoCrearPropietario = gestorPropietario.crearPropietario(propietario);
        assertEquals(resultadoCrearPropietarioApellidoIncorrecto,resultadoCrearPropietario);
    }
    
    @Test
    public void crearPropietarioTestDocumentoIncorrecto() throws Exception {
        String documentoValido = "12345678";
    	propietario.setNumeroDocumento(documentoValido);
        ResultadoCrearPropietario resultadoCrearPropietarioApellidoIncorrecto = new ResultadoCrearPropietario(ErrorResultadoCrearPropietario.Formato_Apellido_Incorrecto);
        ResultadoCrearPropietario resultadoCrearPropietario = gestorPropietario.crearPropietario(propietario);
        assertEquals(resultadoCrearPropietarioApellidoIncorrecto,resultadoCrearPropietario);
    }
    
    
    

}