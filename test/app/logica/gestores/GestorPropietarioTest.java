package app.logica.gestores;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import app.datos.clases.FiltroPropietario;
import app.datos.clases.TipoDocumentoStr;
import app.datos.entidades.Barrio;
import app.datos.entidades.Calle;
import app.datos.entidades.Direccion;
import app.datos.entidades.Inmueble;
import app.datos.entidades.Localidad;
import app.datos.entidades.Propietario;
import app.datos.entidades.TipoDocumento;
import app.datos.servicios.PropietarioService;
import app.logica.resultados.ResultadoCrearPropietario;
import app.logica.resultados.ResultadoCrearPropietario.ErrorCrearPropietario;

public class GestorPropietarioTest {

	private Propietario propietario;

	private GestorPropietario gestorPropietario;

	@Mock
	private static PropietarioService propietarioService;

	@Before
	public void setUp() {
		TipoDocumento doc = new TipoDocumento(1, TipoDocumentoStr.DNI);
		Localidad localidad = new Localidad(1, "sadadfa", null);
		Direccion dir = new Direccion(1, "1234", "1234", "a", new Calle(1, "hilka", localidad), new Barrio(1, "asdas", localidad), localidad);
		ArrayList<Inmueble> inmuebles = new ArrayList<>();
		propietario = new Propietario(1, "Juan", "Perez", "38377777", "3424686868", "d.a@hotmail.com", doc, dir, inmuebles);

		gestorPropietario = new GestorPropietario() {
			{
				this.persistidorPropietario = propietarioService;
			}
		};
		propietarioService = Mockito.mock(PropietarioService.class);
	}

	@Test
	public void crearPropietarioTestCorrecto() throws Exception {

		ResultadoCrearPropietario resultadoCrearPropietarioEsperado = new ResultadoCrearPropietario();
		Mockito.when(propietarioService.obtenerPropietario(new FiltroPropietario(propietario.getTipoDocumento().getTipo(), propietario.getNumeroDocumento()))).thenReturn(null);
		ResultadoCrearPropietario resultadoCrearPropietario = gestorPropietario.crearPropietario(propietario);
		//System.out.println(resultadoCrearPropietario.getErrores().toString() + " - " + resultadoCrearPropietarioEsperado.getErrores().toString());
		assertEquals(resultadoCrearPropietarioEsperado, resultadoCrearPropietario);
	}

	@Test
	public void crearPropietarioTestNombreIncorrecto() throws Exception {
		propietario.setNombre("Nombre Incorrecto por mas de treinta caracteres");
		ResultadoCrearPropietario resultadoCrearPropietarioEsperado = new ResultadoCrearPropietario(ErrorCrearPropietario.Formato_Nombre_Incorrecto);
		ResultadoCrearPropietario resultadoCrearPropietario = gestorPropietario.crearPropietario(propietario);
		assertEquals(resultadoCrearPropietarioEsperado, resultadoCrearPropietario);
	}

	@Test
	public void crearPropietarioTestNombreIncorrecto2() throws Exception {
		propietario.setNombre("Nombre Incorrect0 por numer0s");
		ResultadoCrearPropietario resultadoCrearPropietarioEsperado = new ResultadoCrearPropietario(ErrorCrearPropietario.Formato_Nombre_Incorrecto);
		ResultadoCrearPropietario resultadoCrearPropietario = gestorPropietario.crearPropietario(propietario);
		assertEquals(resultadoCrearPropietarioEsperado, resultadoCrearPropietario);
	}

	@Test
	public void crearPropietarioTestNombreNulo() throws Exception {
		propietario.setNombre(null);
		ResultadoCrearPropietario resultadoCrearPropietarioEsperado = new ResultadoCrearPropietario(ErrorCrearPropietario.Formato_Nombre_Incorrecto);
		ResultadoCrearPropietario resultadoCrearPropietario = gestorPropietario.crearPropietario(propietario);
		assertEquals(resultadoCrearPropietarioEsperado, resultadoCrearPropietario);
	}

	@Test
	public void crearPropietarioTestNombreVacio() throws Exception {
		propietario.setNombre("");
		ResultadoCrearPropietario resultadoCrearPropietarioEsperado = new ResultadoCrearPropietario(ErrorCrearPropietario.Formato_Nombre_Incorrecto);
		ResultadoCrearPropietario resultadoCrearPropietario = gestorPropietario.crearPropietario(propietario);
		assertEquals(resultadoCrearPropietarioEsperado, resultadoCrearPropietario);
	}

	@Test
	public void crearPropietarioTestApellidoIncorrecto() throws Exception {
		propietario.setApellido("Apellido Incorrecto por mas de treinta caracteres");
		ResultadoCrearPropietario resultadoCrearPropietarioEsperado = new ResultadoCrearPropietario(ErrorCrearPropietario.Formato_Apellido_Incorrecto);
		ResultadoCrearPropietario resultadoCrearPropietario = gestorPropietario.crearPropietario(propietario);
		assertEquals(resultadoCrearPropietarioEsperado, resultadoCrearPropietario);
	}

	@Test
	public void crearPropietarioTestDNIIncorrectoLongitud() throws Exception {
		String documentoInvalido = "123456789";
		propietario.setNumeroDocumento(documentoInvalido);
		ResultadoCrearPropietario resultadoCrearPropietarioEsperado = new ResultadoCrearPropietario(ErrorCrearPropietario.Formato_Documento_Incorrecto);
		ResultadoCrearPropietario resultadoCrearPropietario = gestorPropietario.crearPropietario(propietario);
		assertEquals(resultadoCrearPropietarioEsperado, resultadoCrearPropietario);
	}

	@Test
	public void crearPropietarioTestDNIIncorrectoExistente() throws Exception {
		String documentoExistente = "11111111";
		propietario.setNumeroDocumento(documentoExistente);
		ResultadoCrearPropietario resultadoCrearPropietarioEsperado = new ResultadoCrearPropietario(ErrorCrearPropietario.Ya_Existe_Propietario);
		ResultadoCrearPropietario resultadoCrearPropietario = gestorPropietario.crearPropietario(propietario);
		assertEquals(resultadoCrearPropietarioEsperado, resultadoCrearPropietario);
	}

	//No coincide el tipo de documento con el numero
	@Test
	public void crearPropietarioTestDNIInexistentePorTipo() throws Exception {
		String documentoExistente = "11111111";
		propietario.setTipoDocumento(new TipoDocumento(null, TipoDocumentoStr.LC));
		propietario.setNumeroDocumento(documentoExistente);
		ResultadoCrearPropietario resultadoCrearPropietarioEsperado = new ResultadoCrearPropietario();
		ResultadoCrearPropietario resultadoCrearPropietario = gestorPropietario.crearPropietario(propietario);
		assertEquals(resultadoCrearPropietarioEsperado, resultadoCrearPropietario);
	}

	@Test
	public void crearPropietarioTestLCincorrecto() throws Exception {
		String documentoInvalido = "G1111111";
		propietario.setTipoDocumento(new TipoDocumento(null, TipoDocumentoStr.LC));
		propietario.setNumeroDocumento(documentoInvalido);
		ResultadoCrearPropietario resultadoCrearPropietarioEsperado = new ResultadoCrearPropietario(ErrorCrearPropietario.Formato_Documento_Incorrecto);
		ResultadoCrearPropietario resultadoCrearPropietario = gestorPropietario.crearPropietario(propietario);
		assertEquals(resultadoCrearPropietarioEsperado, resultadoCrearPropietario);
	}

}