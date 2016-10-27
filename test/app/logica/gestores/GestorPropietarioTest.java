package app.logica.gestores;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import app.datos.clases.FiltroPropietario;
import app.datos.clases.TipoDocumentoStr;
import app.datos.entidades.Barrio;
import app.datos.entidades.Calle;
import app.datos.entidades.Direccion;
import app.datos.entidades.Localidad;
import app.datos.entidades.Propietario;
import app.datos.entidades.TipoDocumento;
import app.datos.servicios.PropietarioService;
import app.logica.ValidadorFormato;
import app.logica.resultados.ResultadoCrearPropietario;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ValidadorFormato.class)
public class GestorPropietarioTest {

	private static Propietario propietario;
	private static GestorPropietario gestorPropietario;
	private static PropietarioService propietarioService;
	private static FiltroPropietario filtro;

	@BeforeClass
	public static void setUp() {
		//Se carga propietario con datos básicos solo para evitar punteros nulos
		//Las validaciones se hacen en el validador, por lo que se setean luego en los mocks los valores esperados
		TipoDocumento doc = new TipoDocumento(1, TipoDocumentoStr.DNI);
		Localidad localidad = new Localidad(1, "sadadfa", null);
		Direccion dir = new Direccion(1, "1234", "1234", "a", new Calle(1, "hilka", localidad), new Barrio(1, "asdas", localidad), localidad);

		propietario = new Propietario(1, "Juan", "Perez", "38377777", "3424686868", "d.a@hotmail.com", doc, dir, null);
		filtro = new FiltroPropietario(propietario.getTipoDocumento().getTipo(), propietario.getNumeroDocumento());

		//Inicialización de los mocks
		propietarioService = PowerMockito.mock(PropietarioService.class);
		PowerMockito.mockStatic(ValidadorFormato.class);

		//Clase anónima necesaria para inyectar dependencias hasta que funcione Spring
		gestorPropietario = new GestorPropietario() {
			{
				this.persistidorPropietario = propietarioService;
			}
		};
	}

	@Test
	public void testCrearPropietario_correcto() throws Exception {
		//Setear valores esperados a los mocks
		PowerMockito.when(ValidadorFormato.validarNombre(propietario.getNombre())).thenReturn(true);
		PowerMockito.when(ValidadorFormato.validarApellido(propietario.getApellido())).thenReturn(true);
		PowerMockito.when(ValidadorFormato.validarDocumento(propietario.getTipoDocumento(), propietario.getNumeroDocumento())).thenReturn(true);
		PowerMockito.when(ValidadorFormato.validarTelefono(propietario.getTelefono())).thenReturn(true);
		PowerMockito.when(ValidadorFormato.validarEmail(propietario.getEmail())).thenReturn(true);
		PowerMockito.when(ValidadorFormato.validarDireccion(propietario.getDireccion())).thenReturn(true);
		PowerMockito.when(propietarioService.obtenerPropietario(filtro)).thenReturn(null);
		PowerMockito.doNothing().when(propietarioService).guardarPropietario(propietario); //Para métodos void la sintaxis es distinta
		ResultadoCrearPropietario resultadoCrearPropietarioEsperado = new ResultadoCrearPropietario();

		//Llamar al método a testear
		ResultadoCrearPropietario resultadoCrearPropietario = gestorPropietario.crearPropietario(propietario);

		//System.out.println(resultadoCrearPropietario.getErrores().toString() + " - " + resultadoCrearPropietarioEsperado.getErrores().toString());

		//Comprobar resultados obtenidos, que se llaman a los métodos deseados y con los parámetros correctos
		assertEquals(resultadoCrearPropietarioEsperado, resultadoCrearPropietario);
		PowerMockito.verifyStatic(); //ejecutar siempre verifyStatic antes del método estático a comprobar que se llama
		ValidadorFormato.validarNombre(propietario.getNombre());
		PowerMockito.verifyStatic();
		ValidadorFormato.validarApellido(propietario.getApellido());
		PowerMockito.verifyStatic();
		ValidadorFormato.validarDocumento(propietario.getTipoDocumento(), propietario.getNumeroDocumento());
		PowerMockito.verifyStatic();
		ValidadorFormato.validarTelefono(propietario.getTelefono());
		PowerMockito.verifyStatic();
		ValidadorFormato.validarEmail(propietario.getEmail());
		PowerMockito.verifyStatic();
		ValidadorFormato.validarDireccion(propietario.getDireccion());
		verify(propietarioService).obtenerPropietario(filtro);
		verify(propietarioService).guardarPropietario(propietario);
	}
	/*
	 * @Test
	 * public void crearPropietarioTestNombreIncorrecto() throws Exception {
	 * propietario.setNombre("Nombre Incorrecto por mas de treinta caracteres");
	 * ResultadoCrearPropietario resultadoCrearPropietarioEsperado = new ResultadoCrearPropietario(ErrorCrearPropietario.Formato_Nombre_Incorrecto);
	 * ResultadoCrearPropietario resultadoCrearPropietario = gestorPropietario.crearPropietario(propietario);
	 * assertEquals(resultadoCrearPropietarioEsperado, resultadoCrearPropietario);
	 * }
	 * 
	 * @Test
	 * public void crearPropietarioTestNombreIncorrecto2() throws Exception {
	 * propietario.setNombre("Nombre Incorrect0 por numer0s");
	 * ResultadoCrearPropietario resultadoCrearPropietarioEsperado = new ResultadoCrearPropietario(ErrorCrearPropietario.Formato_Nombre_Incorrecto);
	 * ResultadoCrearPropietario resultadoCrearPropietario = gestorPropietario.crearPropietario(propietario);
	 * assertEquals(resultadoCrearPropietarioEsperado, resultadoCrearPropietario);
	 * }
	 * 
	 * @Test
	 * public void crearPropietarioTestNombreNulo() throws Exception {
	 * propietario.setNombre(null);
	 * ResultadoCrearPropietario resultadoCrearPropietarioEsperado = new ResultadoCrearPropietario(ErrorCrearPropietario.Formato_Nombre_Incorrecto);
	 * ResultadoCrearPropietario resultadoCrearPropietario = gestorPropietario.crearPropietario(propietario);
	 * assertEquals(resultadoCrearPropietarioEsperado, resultadoCrearPropietario);
	 * }
	 * 
	 * @Test
	 * public void crearPropietarioTestNombreVacio() throws Exception {
	 * propietario.setNombre("");
	 * ResultadoCrearPropietario resultadoCrearPropietarioEsperado = new ResultadoCrearPropietario(ErrorCrearPropietario.Formato_Nombre_Incorrecto);
	 * ResultadoCrearPropietario resultadoCrearPropietario = gestorPropietario.crearPropietario(propietario);
	 * assertEquals(resultadoCrearPropietarioEsperado, resultadoCrearPropietario);
	 * }
	 * 
	 * @Test
	 * public void crearPropietarioTestApellidoIncorrecto() throws Exception {
	 * propietario.setApellido("Apellido Incorrecto por mas de treinta caracteres");
	 * ResultadoCrearPropietario resultadoCrearPropietarioEsperado = new ResultadoCrearPropietario(ErrorCrearPropietario.Formato_Apellido_Incorrecto);
	 * ResultadoCrearPropietario resultadoCrearPropietario = gestorPropietario.crearPropietario(propietario);
	 * assertEquals(resultadoCrearPropietarioEsperado, resultadoCrearPropietario);
	 * }
	 * 
	 * @Test
	 * public void crearPropietarioTestDNIIncorrectoLongitud() throws Exception {
	 * String documentoInvalido = "123456789";
	 * propietario.setNumeroDocumento(documentoInvalido);
	 * ResultadoCrearPropietario resultadoCrearPropietarioEsperado = new ResultadoCrearPropietario(ErrorCrearPropietario.Formato_Documento_Incorrecto);
	 * ResultadoCrearPropietario resultadoCrearPropietario = gestorPropietario.crearPropietario(propietario);
	 * assertEquals(resultadoCrearPropietarioEsperado, resultadoCrearPropietario);
	 * }
	 * 
	 * @Test
	 * public void crearPropietarioTestDNIIncorrectoExistente() throws Exception {
	 * String documentoExistente = "11111111";
	 * propietario.setNumeroDocumento(documentoExistente);
	 * ResultadoCrearPropietario resultadoCrearPropietarioEsperado = new ResultadoCrearPropietario(ErrorCrearPropietario.Ya_Existe_Propietario);
	 * ResultadoCrearPropietario resultadoCrearPropietario = gestorPropietario.crearPropietario(propietario);
	 * assertEquals(resultadoCrearPropietarioEsperado, resultadoCrearPropietario);
	 * }
	 * 
	 * //No coincide el tipo de documento con el numero
	 * 
	 * @Test
	 * public void crearPropietarioTestDNIInexistentePorTipo() throws Exception {
	 * String documentoExistente = "11111111";
	 * propietario.setTipoDocumento(new TipoDocumento(null, TipoDocumentoStr.LC));
	 * propietario.setNumeroDocumento(documentoExistente);
	 * ResultadoCrearPropietario resultadoCrearPropietarioEsperado = new ResultadoCrearPropietario();
	 * ResultadoCrearPropietario resultadoCrearPropietario = gestorPropietario.crearPropietario(propietario);
	 * assertEquals(resultadoCrearPropietarioEsperado, resultadoCrearPropietario);
	 * }
	 * 
	 * @Test
	 * public void crearPropietarioTestLCincorrecto() throws Exception {
	 * String documentoInvalido = "G1111111";
	 * propietario.setTipoDocumento(new TipoDocumento(null, TipoDocumentoStr.LC));
	 * propietario.setNumeroDocumento(documentoInvalido);
	 * ResultadoCrearPropietario resultadoCrearPropietarioEsperado = new ResultadoCrearPropietario(ErrorCrearPropietario.Formato_Documento_Incorrecto);
	 * ResultadoCrearPropietario resultadoCrearPropietario = gestorPropietario.crearPropietario(propietario);
	 * assertEquals(resultadoCrearPropietarioEsperado, resultadoCrearPropietario);
	 * }
	 */
}