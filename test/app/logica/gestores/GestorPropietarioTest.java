package app.logica.gestores;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;

import app.datos.clases.FiltroPropietario;
import app.datos.clases.TipoDocumentoStr;
import app.datos.entidades.Barrio;
import app.datos.entidades.Calle;
import app.datos.entidades.Direccion;
import app.datos.entidades.Localidad;
import app.datos.entidades.Propietario;
import app.datos.entidades.Provincia;
import app.datos.entidades.TipoDocumento;
import app.datos.servicios.PropietarioService;
import app.excepciones.PersistenciaException;
import app.logica.ValidadorFormato;
import app.logica.resultados.ResultadoAutenticacion;
import app.logica.resultados.ResultadoAutenticacion.ErrorAutenticacion;
import app.logica.resultados.ResultadoCrearPropietario;
import app.ui.controladores.resultado.ResultadoControlador;
import app.ui.controladores.resultado.ResultadoControlador.ErrorControlador;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
@PrepareForTest({ ValidadorFormato.class, Localidad.class, Provincia.class, Calle.class, Barrio.class, Direccion.class })
public class GestorPropietarioTest {

	@Rule
	public PowerMockRule rule = new PowerMockRule();
	private static Propietario propietario;
	private static GestorPropietario gestorPropietario;
	private static PropietarioService propietarioService;
	private static FiltroPropietario filtro;

	@Test
	@Parameters
	public void testCrearPropietario_correcto(TipoDocumento tipoDocumento, String numDoc, String contra, ResultadoControlador resultadoVista, ResultadoAutenticacion resultadoLogica, Throwable excepcion) throws Exception {
		PowerMockito.mockStatic(ValidadorFormato.class);
		PowerMockito.when(ValidadorFormato.validarDocumento(tipoDocumento, numDoc)).thenReturn(true).thenReturn(false);

		Assert.assertTrue(ValidadorFormato.validarDocumento(tipoDocumento, numDoc));
		Assert.assertFalse(ValidadorFormato.validarDocumento(tipoDocumento, numDoc));

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
		ValidadorFormato.validarApellido(propietario.getApellido());
		ValidadorFormato.validarDocumento(propietario.getTipoDocumento(), propietario.getNumeroDocumento());
		ValidadorFormato.validarTelefono(propietario.getTelefono());
		ValidadorFormato.validarEmail(propietario.getEmail());
		ValidadorFormato.validarDireccion(propietario.getDireccion());
		Mockito.verify(propietarioService).obtenerPropietario(filtro);
		Mockito.verify(propietarioService).guardarPropietario(propietario);
		PowerMockito.verifyStatic();
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

	protected Object[] parametersForTestCrearPropietario_correcto() {
		return new Object[] {
				new Object[] { (new TipoDocumento()).setTipo(TipoDocumentoStr.DNI), "12345678", "pepe", new ResultadoControlador(), new ResultadoAutenticacion(), null }, //prueba ingreso correcto
				new Object[] { null, "", "pepe", new ResultadoControlador(ErrorControlador.Campos_Vacios), new ResultadoAutenticacion(ErrorAutenticacion.Datos_Incorrectos), null }, //prueba TipoDocumento vacio
				new Object[] { (new TipoDocumento()).setTipo(TipoDocumentoStr.LC), "", "pepe", new ResultadoControlador(ErrorControlador.Campos_Vacios), new ResultadoAutenticacion(ErrorAutenticacion.Datos_Incorrectos), null }, //prueba numero de documento vacio
				new Object[] { (new TipoDocumento()).setTipo(TipoDocumentoStr.LE), "12345678", "", new ResultadoControlador(ErrorControlador.Campos_Vacios), new ResultadoAutenticacion(ErrorAutenticacion.Datos_Incorrectos), null }, //prueba Contraseña vacia
				new Object[] { (new TipoDocumento()).setTipo(TipoDocumentoStr.CedulaExtranjera), "12345678", "pepe", new ResultadoControlador(ErrorControlador.Datos_Incorrectos), new ResultadoAutenticacion(ErrorAutenticacion.Datos_Incorrectos), null }, //prueba un ingreso incorrecto
				new Object[] { (new TipoDocumento()).setTipo(TipoDocumentoStr.Pasaporte), "ñú", "pepe", new ResultadoControlador(ErrorControlador.Datos_Incorrectos), new ResultadoAutenticacion(ErrorAutenticacion.Datos_Incorrectos), null }, //prueba un ingreso incorrecto con caracteres UTF8
				new Object[] { (new TipoDocumento()).setTipo(TipoDocumentoStr.LC), "ñú", "pepe", new ResultadoControlador(ErrorControlador.Error_Persistencia), null, new PersistenciaException("Error de persistencia. Test.") }, //Prueba una excepcion de persistencia
				new Object[] { (new TipoDocumento()).setTipo(TipoDocumentoStr.DNI), "ñú", "pepe", new ResultadoControlador(ErrorControlador.Error_Desconocido), null, new Exception() } //Prueba una excepcion desconocida
		};
	}
}
