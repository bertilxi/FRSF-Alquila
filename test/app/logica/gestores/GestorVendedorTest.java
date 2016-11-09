package app.logica.gestores;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;
import org.mockito.Matchers;
import org.mockito.Mockito;

import app.comun.EncriptadorPassword;
import app.comun.ValidadorFormato;
import app.datos.clases.DatosLogin;
import app.datos.clases.EstadoStr;
import app.datos.clases.FiltroVendedor;
import app.datos.clases.TipoDocumentoStr;
import app.datos.entidades.Estado;
import app.datos.entidades.TipoDocumento;
import app.datos.entidades.Vendedor;
import app.datos.servicios.VendedorService;
import app.excepciones.ObjNotFoundException;
import app.excepciones.PersistenciaException;
import app.logica.resultados.ResultadoAutenticacion;
import app.logica.resultados.ResultadoAutenticacion.ErrorAutenticacion;
import app.logica.resultados.ResultadoCrearVendedor;
import app.logica.resultados.ResultadoCrearVendedor.ErrorCrearVendedor;
import app.logica.resultados.ResultadoEliminarVendedor;
import app.logica.resultados.ResultadoEliminarVendedor.ErrorEliminarVendedor;
import app.logica.resultados.ResultadoModificarVendedor;
import app.logica.resultados.ResultadoModificarVendedor.ErrorModificarVendedor;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
/**
 * Este test prueba los métodos de la clase GestorVendedor
 */
public class GestorVendedorTest {

	//Mock del encriptador a utilizar
	private static EncriptadorPassword encriptadorMock = Mockito.mock(EncriptadorPassword.class);

	@Test
	@Parameters
	/**
	 * Prueba el método autenticarVendedor(datos), el cual corresponde con la taskcard 1 de la iteración 1 y a la historia 1
	 *
	 * @param datos
	 *            que se usarán en el test
	 * @param resultadoLogica
	 *            es lo que se espera que devuelva el metodo
	 * @param vendedor
	 *            es lo que el mock de la capa de datos debe devolver en el test y que el controlador recibe
	 * @param excepcion
	 *            es la excepcion que debe lanzar el mock de la capa de datos, si la prueba involucra procesar una excepcion de dicha capa de datos, debe ser nulo vendedor para que se use
	 * @throws Exception
	 */
	public void testAutenticarVendedor(DatosLogin datos, ResultadoAutenticacion resultadoLogica, Vendedor vendedor, Throwable excepcion) throws Exception {
		//Creamos el soporte de la prueba
		VendedorService vendedorServiceMock = new VendedorService() {

			@Override
			public void guardarVendedor(Vendedor vendedor) throws PersistenciaException {
			}

			@Override
			public void modificarVendedor(Vendedor vendedor) throws PersistenciaException {
			}

			@Override
			public Vendedor obtenerVendedor(FiltroVendedor filtro) throws PersistenciaException {
				if(vendedor != null){
					return vendedor;
				}
				if(excepcion == null){
					return null;
				}
				if(excepcion instanceof PersistenciaException){
					throw (PersistenciaException) excepcion;
				}
				new Integer("asd");
				return null;
			}

			@Override
			public ArrayList<Vendedor> listarVendedores() throws PersistenciaException {
				return null;
			}
		};
		GestorVendedor gestorVendedor = new GestorVendedor() {
			{
				this.persistidorVendedor = vendedorServiceMock;
				this.encriptador = encriptadorMock;
			}
		};

		//Creamos la prueba
		Statement test = new Statement() {
			@Override
			public void evaluate() throws Throwable {
				if(resultadoLogica != null){
					assertEquals(resultadoLogica, gestorVendedor.autenticarVendedor(datos));
				}
				else{
					try{
						gestorVendedor.autenticarVendedor(datos);
						Assert.fail("Debería haber fallado!");
					} catch(PersistenciaException e){
						Assert.assertEquals((excepcion), e);
					} catch(Exception e){
						if(excepcion instanceof PersistenciaException){
							Assert.fail("Debería haber tirado una PersistenciaException y tiro otra Exception!");
						}
					}
				}
			}
		};

		//Ejecutamos la prueba
		try{
			test.evaluate();
		} catch(Throwable e){
			throw new Exception(e);
		}
	}

	/**
	 * Método que devuelve los parámetros para probar el método autenticarVendedor(datos)
	 *
	 * @return parámetros de prueba
	 */
	protected Object[] parametersForTestAutenticarVendedor() {
		Mockito.when(encriptadorMock.encriptar(Matchers.any(), Matchers.any())).thenReturn("1234");
		Mockito.when(encriptadorMock.generarSal()).thenReturn("1234");

		TipoDocumento tipoDocumento = new TipoDocumento().setTipo(TipoDocumentoStr.DNI);
		String numDoc = "12345678";
		String contrasenia = "pepe";
		String sal = encriptadorMock.generarSal();

		DatosLogin datosCorrectos = new DatosLogin(tipoDocumento, numDoc, contrasenia.toCharArray());
		Vendedor vendedorCorrecto = new Vendedor().setTipoDocumento(tipoDocumento).setNumeroDocumento(numDoc).setPassword(encriptadorMock.encriptar(contrasenia.toCharArray(), sal)).setSalt(sal);

		DatosLogin datosTipoDocumentoVacio = new DatosLogin(null, numDoc, contrasenia.toCharArray());

		DatosLogin datosNumeroDocumentoVacio = new DatosLogin(tipoDocumento, "", contrasenia.toCharArray());

		DatosLogin datosContraseniaVacia = new DatosLogin(null, numDoc, "".toCharArray());

		DatosLogin datosCaracteresRaros = new DatosLogin(tipoDocumento, "ñúïÒ", contrasenia.toCharArray());

		return new Object[] {
				new Object[] { datosCorrectos, new ResultadoAutenticacion(vendedorCorrecto), vendedorCorrecto, null }, //prueba ingreso correcto
				new Object[] { datosTipoDocumentoVacio, new ResultadoAutenticacion(null, ErrorAutenticacion.Datos_Incorrectos), vendedorCorrecto, null }, //prueba TipoDocumento vacio, ingreso incorrecto
				new Object[] { datosNumeroDocumentoVacio, new ResultadoAutenticacion(null, ErrorAutenticacion.Datos_Incorrectos), vendedorCorrecto, null }, //prueba numero de documento vacio, ingreso incorrecto
				new Object[] { datosContraseniaVacia, new ResultadoAutenticacion(null, ErrorAutenticacion.Datos_Incorrectos), vendedorCorrecto, null }, //prueba Contraseña vacia, ingreso incorrecto
				new Object[] { datosCorrectos, new ResultadoAutenticacion(null, ErrorAutenticacion.Datos_Incorrectos), null, null }, //prueba un ingreso incorrecto
				new Object[] { datosCaracteresRaros, new ResultadoAutenticacion(null, ErrorAutenticacion.Datos_Incorrectos), null, null }, //prueba un ingreso incorrecto con caracteres UTF8
				new Object[] { datosCorrectos, null, null, new ObjNotFoundException("Error de persistencia. Test.", new Exception()) }, //Prueba una excepcion de persistencia
				new Object[] { datosCorrectos, null, null, new Exception() } //Prueba una excepcion desconocida
		};
	}

	private static Vendedor vendedor;

	protected Object[] parametersForTestCrearVendedor() {
		//Se carga vendedor con datos básicos solo para evitar punteros nulos
		//Las validaciones se hacen en el validador, por lo que se setean luego en los mocks los valores esperados
		TipoDocumento doc = new TipoDocumento(TipoDocumentoStr.DNI);
		vendedor = new Vendedor();
		vendedor.setNombre("Juan")
				.setApellido("Pérez")
				.setNumeroDocumento("12345678")
				.setTipoDocumento(doc)
				.setSalt("abcd")
				.setPassword("1234");

		//Parámetros de JUnitParams
		return new Object[] {
				new Object[] { true, true, true, null, 1, resultadoCorrecto },
				new Object[] { false, true, true, null, 0, resultadoCrearNombreIncorrecto },
				new Object[] { true, false, true, null, 0, resultadoCrearApellidoIncorrecto },
				new Object[] { true, true, false, null, 0, resultadoCrearDocumentoIncorrecto },
				new Object[] { false, false, true, null, 0, new ResultadoCrearVendedor(ErrorCrearVendedor.Formato_Nombre_Incorrecto, ErrorCrearVendedor.Formato_Apellido_Incorrecto) },
				new Object[] { true, true, true, vendedor, 0, resultadoCrearYaExiste }
		};
	}

	@Test
	@Parameters
	public void testCrearVendedor(Boolean resValNombre, Boolean resValApellido, Boolean resValDocumento, Vendedor resObtenerVendedor, Integer guardar, ResultadoCrearVendedor resultadoCrearVendedorEsperado) throws Exception {
		//Inicialización de los mocks
		VendedorService vendedorServiceMock = mock(VendedorService.class);
		ValidadorFormato validadorFormatoMock = mock(ValidadorFormato.class);
		GestorDatos gestorDatosMock = mock(GestorDatos.class);

		//Clase anónima necesaria para inyectar dependencias hasta que funcione Spring
		GestorVendedor gestorVendedor = new GestorVendedor() {
			{
				this.persistidorVendedor = vendedorServiceMock;
				this.validador = validadorFormatoMock;
				this.gestorDatos = gestorDatosMock;
			}
		};

		ArrayList<Estado> estados = new ArrayList<>();
		estados.add(new Estado(EstadoStr.ALTA));

		//Setear valores esperados a los mocks
		when(validadorFormatoMock.validarNombre(vendedor.getNombre())).thenReturn(resValNombre);
		when(validadorFormatoMock.validarApellido(vendedor.getApellido())).thenReturn(resValApellido);
		when(validadorFormatoMock.validarDocumento(vendedor.getTipoDocumento(), vendedor.getNumeroDocumento())).thenReturn(resValDocumento);
		when(vendedorServiceMock.obtenerVendedor(any())).thenReturn(resObtenerVendedor);
		when(gestorDatosMock.obtenerEstados()).thenReturn(estados);
		doNothing().when(vendedorServiceMock).guardarVendedor(vendedor); //Para métodos void la sintaxis es distinta

		//Llamar al método a testear
		ResultadoCrearVendedor resultadoCrearVendedor = gestorVendedor.crearVendedor(vendedor);

		//Comprobar resultados obtenidos, que se llaman a los métodos deseados y con los parámetros correctos
		assertEquals(resultadoCrearVendedorEsperado, resultadoCrearVendedor);
		if(guardar.equals(1)){
			assertEquals(EstadoStr.ALTA, vendedor.getEstado().getEstado());
		}
		verify(validadorFormatoMock).validarNombre(vendedor.getNombre());
		verify(validadorFormatoMock).validarApellido(vendedor.getApellido());
		verify(validadorFormatoMock).validarDocumento(vendedor.getTipoDocumento(), vendedor.getNumeroDocumento());
		verify(gestorDatosMock, times(guardar)).obtenerEstados();
		verify(vendedorServiceMock, times(guardar)).guardarVendedor(vendedor);
	}

	//Para crearPropietario
	private static final ResultadoCrearVendedor resultadoCorrecto = new ResultadoCrearVendedor();
	private static final ResultadoCrearVendedor resultadoCrearNombreIncorrecto =
			new ResultadoCrearVendedor(ErrorCrearVendedor.Formato_Nombre_Incorrecto);
	private static final ResultadoCrearVendedor resultadoCrearApellidoIncorrecto =
			new ResultadoCrearVendedor(ErrorCrearVendedor.Formato_Apellido_Incorrecto);
	private static final ResultadoCrearVendedor resultadoCrearDocumentoIncorrecto =
			new ResultadoCrearVendedor(ErrorCrearVendedor.Formato_Documento_Incorrecto);
	private static final ResultadoCrearVendedor resultadoCrearYaExiste =
			new ResultadoCrearVendedor(ErrorCrearVendedor.Ya_Existe_Vendedor);

	private static Vendedor vendedorM;
	private static Vendedor vendedorM2;

	protected Object[] parametersForTestModificarVendedor() {
		//Se carga vendedor con datos básicos solo para evitar punteros nulos
		//Las validaciones se hacen en el validador, por lo que se setean luego en los mocks los valores esperados
		TipoDocumento doc = new TipoDocumento(TipoDocumentoStr.DNI);
		Estado est = new Estado(EstadoStr.ALTA);
		vendedorM = new Vendedor() {

			public Vendedor setId(Integer id) {
				this.id = id;
				return this;
			}

			{
				this.setId(1)
						.setNombre("Juan")
						.setApellido("Pérez")
						.setNumeroDocumento("12345678")
						.setTipoDocumento(doc)
						.setSalt("abcd")
						.setPassword("1234")
						.setEstado(est);
			}
		};

		vendedorM2 = new Vendedor() {

			public Vendedor setId(Integer id) {
				this.id = id;
				return this;
			}

			{
				this.setId(2)
						.setNombre("Juan")
						.setApellido("Pérez")
						.setNumeroDocumento("12345678")
						.setTipoDocumento(doc)
						.setSalt("abcd")
						.setPassword("1234");
			}
		};

		//Parámetros de JUnitParams
		return new Object[] {
				new Object[] { true, true, true, vendedorM, 1, resultadoCorrectoModificar },
				new Object[] { false, true, true, vendedorM, 0, resultadoModificarNombreIncorrecto },
				new Object[] { true, false, true, vendedorM, 0, resultadoModificarApellidoIncorrecto },
				new Object[] { true, true, false, vendedorM, 0, resultadoModificarDocumentoIncorrecto },
				new Object[] { false, false, true, vendedorM, 0, new ResultadoModificarVendedor(ErrorModificarVendedor.Formato_Nombre_Incorrecto, ErrorModificarVendedor.Formato_Apellido_Incorrecto) },
				new Object[] { true, true, true, vendedorM2, 0, resultadoModificarYaExiste }
		};
	}

	@Test
	@Parameters
	public void testModificarVendedor(Boolean resValNombre, Boolean resValApellido, Boolean resValDocumento, Vendedor resObtenerVendedor, Integer modificar, ResultadoModificarVendedor resultadoModificarVendedorEsperado) throws Exception {
		//Inicialización de los mocks
		VendedorService vendedorServiceMock = mock(VendedorService.class);
		ValidadorFormato validadorFormatoMock = mock(ValidadorFormato.class);
		GestorDatos gestorDatosMock = mock(GestorDatos.class);

		//Clase anónima necesaria para inyectar dependencias hasta que funcione Spring
		GestorVendedor gestorVendedor = new GestorVendedor() {
			{
				this.persistidorVendedor = vendedorServiceMock;
				this.validador = validadorFormatoMock;
				this.gestorDatos = gestorDatosMock;
			}
		};

		ArrayList<Estado> estados = new ArrayList<>();
		estados.add(new Estado(EstadoStr.ALTA));

		//Setear valores esperados a los mocks
		when(validadorFormatoMock.validarNombre(vendedorM.getNombre())).thenReturn(resValNombre);
		when(validadorFormatoMock.validarApellido(vendedorM.getApellido())).thenReturn(resValApellido);
		when(validadorFormatoMock.validarDocumento(vendedorM.getTipoDocumento(), vendedor.getNumeroDocumento())).thenReturn(resValDocumento);
		when(vendedorServiceMock.obtenerVendedor(any())).thenReturn(resObtenerVendedor);
		when(gestorDatosMock.obtenerEstados()).thenReturn(estados);
		doNothing().when(vendedorServiceMock).modificarVendedor(vendedorM); //Para métodos void la sintaxis es distinta

		//Llamar al método a testear
		ResultadoModificarVendedor resultadoModificarVendedor = gestorVendedor.modificarVendedor(vendedorM);

		//Comprobar resultados obtenidos, que se llaman a los métodos deseados y con los parámetros correctos
		assertEquals(resultadoModificarVendedorEsperado, resultadoModificarVendedor);
		if(modificar.equals(1)){
			assertEquals(EstadoStr.ALTA, vendedorM.getEstado().getEstado());
		}
		verify(validadorFormatoMock).validarNombre(vendedorM.getNombre());
		verify(validadorFormatoMock).validarApellido(vendedorM.getApellido());
		verify(validadorFormatoMock).validarDocumento(vendedorM.getTipoDocumento(), vendedorM.getNumeroDocumento());
		verify(vendedorServiceMock, times(modificar)).modificarVendedor(vendedorM);
	}

	//Para modificarPropietario
	private static final ResultadoModificarVendedor resultadoCorrectoModificar = new ResultadoModificarVendedor();
	private static final ResultadoModificarVendedor resultadoModificarNombreIncorrecto =
			new ResultadoModificarVendedor(ErrorModificarVendedor.Formato_Nombre_Incorrecto);
	private static final ResultadoModificarVendedor resultadoModificarApellidoIncorrecto =
			new ResultadoModificarVendedor(ErrorModificarVendedor.Formato_Apellido_Incorrecto);
	private static final ResultadoModificarVendedor resultadoModificarDocumentoIncorrecto =
			new ResultadoModificarVendedor(ErrorModificarVendedor.Formato_Documento_Incorrecto);
	private static final ResultadoModificarVendedor resultadoModificarYaExiste =
			new ResultadoModificarVendedor(ErrorModificarVendedor.Otro_Vendedor_Posee_Mismo_Documento_Y_Tipo);

	private static Vendedor vendedorE;

	protected Object[] parametersForTestEliminarVendedor() {
		//Se carga vendedor con datos básicos solo para evitar punteros nulos
		//Las validaciones se hacen en el validador, por lo que se setean luego en los mocks los valores esperados
		TipoDocumento doc = new TipoDocumento(TipoDocumentoStr.DNI);
		Estado est = new Estado(EstadoStr.ALTA);
		vendedorE = new Vendedor();

		vendedorE.setNombre("Juan")
				.setApellido("Pérez")
				.setNumeroDocumento("12345678")
				.setTipoDocumento(doc)
				.setSalt("abcd")
				.setPassword("1234")
				.setEstado(est);

		//Parámetros de JUnitParams
		return new Object[] {
				new Object[] { vendedorE, 1, resultadoCorrectoEliminar },
				new Object[] { null, 0, resultadoEliminarNoExisteVendedor },
		};
	}

	@Test
	@Parameters
	public void testEliminarVendedor(Vendedor resObtenerVendedor, Integer eliminar, ResultadoEliminarVendedor resultadoEliminarVendedorEsperado) throws Exception {
		//Inicialización de los mocks
		VendedorService vendedorServiceMock = mock(VendedorService.class);
		GestorDatos gestorDatosMock = mock(GestorDatos.class);

		//Clase anónima necesaria para inyectar dependencias hasta que funcione Spring
		GestorVendedor gestorVendedor = new GestorVendedor() {
			{
				this.persistidorVendedor = vendedorServiceMock;
				this.gestorDatos = gestorDatosMock;
			}
		};

		ArrayList<Estado> estados = new ArrayList<>();
		estados.add(new Estado(EstadoStr.BAJA));

		//Setear valores esperados a los mocks
		when(vendedorServiceMock.obtenerVendedor(any())).thenReturn(resObtenerVendedor);
		when(gestorDatosMock.obtenerEstados()).thenReturn(estados);
		doNothing().when(vendedorServiceMock).modificarVendedor(vendedorE); //Para métodos void la sintaxis es distinta

		//Llamar al método a testear
		ResultadoEliminarVendedor resultadoEliminarVendedor = gestorVendedor.eliminarVendedor(vendedorE);

		//Comprobar resultados obtenidos, que se llaman a los métodos deseados y con los parámetros correctos
		assertEquals(resultadoEliminarVendedorEsperado, resultadoEliminarVendedor);
		if(eliminar.equals(1)){
			assertEquals(EstadoStr.BAJA, vendedorE.getEstado().getEstado());
		}
		verify(gestorDatosMock, times(eliminar)).obtenerEstados();
		verify(vendedorServiceMock, times(eliminar)).modificarVendedor(vendedorE);
	}

	//Para modificarPropietario
	private static final ResultadoEliminarVendedor resultadoCorrectoEliminar = new ResultadoEliminarVendedor();
	private static final ResultadoEliminarVendedor resultadoEliminarNoExisteVendedor =
			new ResultadoEliminarVendedor(ErrorEliminarVendedor.No_Existe_Vendedor);

}
