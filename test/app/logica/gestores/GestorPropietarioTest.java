package app.logica.gestores;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;

import app.comun.ValidadorFormato;
import app.datos.clases.EstadoStr;
import app.datos.clases.FiltroPropietario;
import app.datos.clases.TipoDocumentoStr;
import app.datos.entidades.Barrio;
import app.datos.entidades.Calle;
import app.datos.entidades.Direccion;
import app.datos.entidades.Estado;
import app.datos.entidades.Localidad;
import app.datos.entidades.Propietario;
import app.datos.entidades.TipoDocumento;
import app.datos.servicios.PropietarioService;
import app.logica.resultados.ResultadoCrearPropietario;
import app.logica.resultados.ResultadoCrearPropietario.ErrorCrearPropietario;
import app.logica.resultados.ResultadoModificarPropietario;
import app.logica.resultados.ResultadoModificarPropietario.ErrorModificarPropietario;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class GestorPropietarioTest {

	private static Propietario propietario;
	private static FiltroPropietario filtro;

	protected Object[] parametersForTestCrearPropietario() {
		//Se carga propietario con datos básicos solo para evitar punteros nulos
		//Las validaciones se hacen en el validador, por lo que se setean luego en los mocks los valores esperados
		TipoDocumento doc = new TipoDocumento(TipoDocumentoStr.DNI);
		Localidad localidad = new Localidad("sadadfa", null);
		Direccion dir = new Direccion("1234", "1234", "a", new Calle("hilka", localidad), new Barrio("asdas", localidad), localidad);
		propietario = new Propietario(1, "Juan", "Perez", "38377777", "3424686868", "d.a@hotmail.com", doc, dir);
		propietario.setEstado(new Estado(EstadoStr.ALTA));
		filtro = new FiltroPropietario(propietario.getTipoDocumento().getTipo(), propietario.getNumeroDocumento());

		//Parámetros de JUnitParams
		return new Object[] {
				new Object[] { true, true, true, true, true, true, null, 1, resultadoCorrecto },
				new Object[] { false, true, true, true, true, true, null, 0, resultadoCrearNombreIncorrecto },
				new Object[] { true, false, true, true, true, true, null, 0, resultadoCrearApellidoIncorrecto },
				new Object[] { true, true, false, true, true, true, null, 0, resultadoCrearDocumentoIncorrecto },
				new Object[] { true, true, true, false, true, true, null, 0, resultadoCrearTelefonoIncorrecto },
				new Object[] { true, true, true, true, false, true, null, 0, resultadoCrearEmailIncorrecto },
				new Object[] { true, true, true, true, true, false, null, 0, resultadoCrearDireccionIncorrecto },
				new Object[] { false, false, true, true, true, true, null, 0, new ResultadoCrearPropietario(ErrorCrearPropietario.Formato_Nombre_Incorrecto, ErrorCrearPropietario.Formato_Apellido_Incorrecto) },
				new Object[] { true, true, true, true, true, true, propietario, 0, resultadoCrearYaExiste }
		};
	}

	@Test
	@Parameters
	public void testCrearPropietario(Boolean resValNombre, Boolean resValApellido, Boolean resValDocumento, Boolean resValTelefono, Boolean resValEmail, Boolean resValDireccion, Propietario resObtenerPropietario, Integer guardar, ResultadoCrearPropietario resultadoCrearPropietarioEsperado) throws Exception {
		//Inicialización de los mocks
		PropietarioService propietarioServiceMock = mock(PropietarioService.class);
		ValidadorFormato validadorFormatoMock = mock(ValidadorFormato.class);
		GestorDatos gestorDatosMock = mock(GestorDatos.class);

		//Clase anónima necesaria para inyectar dependencias hasta que funcione Spring
		GestorPropietario gestorPropietario = new GestorPropietario() {
			{
				this.persistidorPropietario = propietarioServiceMock;
				this.validador = validadorFormatoMock;
				this.gestorDatos = gestorDatosMock;
			}
		};

		ArrayList<Estado> estados = new ArrayList<>();
		estados.add(new Estado(EstadoStr.ALTA));

		//Setear valores esperados a los mocks
		when(validadorFormatoMock.validarNombre(propietario.getNombre())).thenReturn(resValNombre);
		when(validadorFormatoMock.validarApellido(propietario.getApellido())).thenReturn(resValApellido);
		when(validadorFormatoMock.validarDocumento(propietario.getTipoDocumento(), propietario.getNumeroDocumento())).thenReturn(resValDocumento);
		when(validadorFormatoMock.validarTelefono(propietario.getTelefono())).thenReturn(resValTelefono);
		when(validadorFormatoMock.validarEmail(propietario.getEmail())).thenReturn(resValEmail);
		when(validadorFormatoMock.validarDireccion(propietario.getDireccion())).thenReturn(resValDireccion);
		when(propietarioServiceMock.obtenerPropietario(filtro)).thenReturn(resObtenerPropietario);
		when(gestorDatosMock.obtenerEstados()).thenReturn(estados);
		doNothing().when(propietarioServiceMock).guardarPropietario(propietario); //Para métodos void la sintaxis es distinta

		//Llamar al método a testear
		ResultadoCrearPropietario resultadoCrearPropietario = gestorPropietario.crearPropietario(propietario);

		//Comprobar resultados obtenidos, que se llaman a los métodos deseados y con los parámetros correctos
		assertEquals(resultadoCrearPropietarioEsperado.getErrores(), resultadoCrearPropietario.getErrores());
		verify(validadorFormatoMock).validarNombre(propietario.getNombre());
		verify(validadorFormatoMock).validarApellido(propietario.getApellido());
		verify(validadorFormatoMock).validarDocumento(propietario.getTipoDocumento(), propietario.getNumeroDocumento());
		verify(validadorFormatoMock).validarTelefono(propietario.getTelefono());
		verify(validadorFormatoMock).validarEmail(propietario.getEmail());
		verify(validadorFormatoMock).validarDireccion(propietario.getDireccion());
		verify(propietarioServiceMock).obtenerPropietario(filtro);
		verify(gestorDatosMock, times(guardar)).obtenerEstados();
		verify(propietarioServiceMock, times(guardar)).guardarPropietario(propietario);
	}

	//Para crearPropietario
	private static final ResultadoCrearPropietario resultadoCorrecto = new ResultadoCrearPropietario();
	private static final ResultadoCrearPropietario resultadoCrearNombreIncorrecto =
			new ResultadoCrearPropietario(ErrorCrearPropietario.Formato_Nombre_Incorrecto);
	private static final ResultadoCrearPropietario resultadoCrearApellidoIncorrecto =
			new ResultadoCrearPropietario(ErrorCrearPropietario.Formato_Apellido_Incorrecto);
	private static final ResultadoCrearPropietario resultadoCrearDocumentoIncorrecto =
			new ResultadoCrearPropietario(ErrorCrearPropietario.Formato_Documento_Incorrecto);
	private static final ResultadoCrearPropietario resultadoCrearTelefonoIncorrecto =
			new ResultadoCrearPropietario(ErrorCrearPropietario.Formato_Telefono_Incorrecto);
	private static final ResultadoCrearPropietario resultadoCrearEmailIncorrecto =
			new ResultadoCrearPropietario(ErrorCrearPropietario.Formato_Email_Incorrecto);
	private static final ResultadoCrearPropietario resultadoCrearDireccionIncorrecto =
			new ResultadoCrearPropietario(ErrorCrearPropietario.Formato_Direccion_Incorrecto);
	private static final ResultadoCrearPropietario resultadoCrearYaExiste =
			new ResultadoCrearPropietario(ErrorCrearPropietario.Ya_Existe_Propietario);
	//Para modificarPropietario
	private static final ResultadoModificarPropietario resultadoModificarApellidoIncorrecto =
			new ResultadoModificarPropietario(ErrorModificarPropietario.Formato_Apellido_Incorrecto);
	private static final ResultadoModificarPropietario resultadoModificarDocumentoIncorrecto =
			new ResultadoModificarPropietario(ErrorModificarPropietario.Formato_Documento_Incorrecto);
	private static final ResultadoModificarPropietario resultadoModificarNombreIncorrecto =
			new ResultadoModificarPropietario(ErrorModificarPropietario.Formato_Nombre_Incorrecto);
	private static final ResultadoModificarPropietario resultadoModificarTelefonoIncorrecto =
			new ResultadoModificarPropietario(ErrorModificarPropietario.Formato_Telefono_Incorrecto);
	private static final ResultadoModificarPropietario resultadoModificarYaSePoseeMismoDocumento =
			new ResultadoModificarPropietario(ErrorModificarPropietario.Ya_Existe_Propietario);
}
