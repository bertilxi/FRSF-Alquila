package app.ui.controladores;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;
import org.mockito.Mockito;

import app.comun.EncriptadorPassword;
import app.datos.clases.TipoDocumentoStr;
import app.datos.entidades.TipoDocumento;
import app.datos.entidades.Vendedor;
import app.excepciones.SaveUpdateException;
import app.logica.CoordinadorJavaFX;
import app.logica.resultados.ResultadoModificarVendedor;
import app.logica.resultados.ResultadoModificarVendedor.ErrorModificarVendedor;
import app.ui.componentes.ventanas.PresentadorVentanas;
import app.ui.componentes.ventanas.VentanaError;
import app.ui.componentes.ventanas.VentanaErrorExcepcion;
import app.ui.componentes.ventanas.VentanaErrorExcepcionInesperada;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

/**
 * Test para el controlador de la vista Modificar Vendedor
 */
@RunWith(JUnitParamsRunner.class)
public class ModificarVendedorControllerTest {

	@Test
	@Parameters
	public void testModificarVendedor(String nombre,
			String apellido,
			TipoDocumento tipoDocumento,
			String numeroDocumento,
			String resultadoEncriptar,
			String contraseñaAntigua,
			String contraseñaNueva,
			String contraseñaNueva2,
			ResultadoModificarVendedor resultadoModificarVendedorEsperado,
			Integer llamaAPresentadorVentanasPresentarError,
			Integer llamaAPresentadorVentanasPresentarExcepcion,
			Integer llamaAPresentadorVentanasPresentarExcepcionInesperada,
			Integer llamaAModificarVendedor,
			Exception excepcion,
			Integer llamaACambiarScene,
			Boolean checkBoxSeleccionado)
			throws Throwable {
		//Se crean los mocks necesarios
		CoordinadorJavaFX coordinadorMock = mock(CoordinadorJavaFX.class);
		PresentadorVentanas presentadorVentanasMock = mock(PresentadorVentanas.class);
		VentanaError ventanaErrorMock = mock(VentanaError.class);
		VentanaErrorExcepcion ventanaErrorExcepcionMock = mock(VentanaErrorExcepcion.class);
		VentanaErrorExcepcionInesperada ventanaErrorExcepcionInesperadaMock = mock(VentanaErrorExcepcionInesperada.class);
		EncriptadorPassword encriptadorPasswordMock = mock(EncriptadorPassword.class);

		//Se setea lo que deben devolver los mocks cuando son invocados por la clase a probar
		when(presentadorVentanasMock.presentarError(any(), any(), any())).thenReturn(ventanaErrorMock);
		when(presentadorVentanasMock.presentarExcepcion(any(), any())).thenReturn(ventanaErrorExcepcionMock);
		when(presentadorVentanasMock.presentarExcepcionInesperada(any(), any())).thenReturn(ventanaErrorExcepcionInesperadaMock);
		doNothing().when(presentadorVentanasMock).presentarToast(any(), any());
		when(encriptadorPasswordMock.encriptar(eq(contraseñaAntigua.toCharArray()), any())).thenReturn(resultadoEncriptar);

		Vendedor vendedor = new Vendedor()
				.setNombre(nombre)
				.setApellido(apellido)
				.setTipoDocumento(tipoDocumento)
				.setNumeroDocumento(numeroDocumento)
				.setPassword(contraseñaAntigua);
		when(coordinadorMock.modificarVendedor(vendedor)).thenReturn(resultadoModificarVendedorEsperado);
		if(excepcion != null){
			when(coordinadorMock.modificarVendedor(vendedor)).thenThrow(excepcion);
		}

		ArrayList<TipoDocumento> tipos = new ArrayList<>();
		tipos.add(tipoDocumento);
		when(coordinadorMock.obtenerTiposDeDocumento()).thenReturn(tipos);

		//Controlador a probar, se sobreescriben algunos métodos para setear los mocks y setear los datos que ingresaría el usuario en la vista
		ModificarVendedorController modificarVendedorController = new ModificarVendedorController() {
			@Override
			public void inicializar(URL location, ResourceBundle resources) {
				this.coordinador = coordinadorMock;
				this.presentador = presentadorVentanasMock;
				this.encriptador = encriptadorPasswordMock;
				this.setVendedor(vendedor);
				super.inicializar(location, resources);
			}

			@Override
			public void acceptAction() {
				this.textFieldNombre.setText(nombre);
				this.textFieldApellido.setText(apellido);
				this.comboBoxTipoDocumento.getSelectionModel().select(tipoDocumento);
				this.textFieldNumeroDocumento.setText(numeroDocumento);
				this.passwordFieldContraseñaAntigua.setText(contraseñaAntigua);
				this.passwordFieldContraseñaNueva.setText(contraseñaNueva);
				this.passwordFieldRepiteContraseña.setText(contraseñaNueva2);
				this.checkBoxCambiarContraseña.setSelected(checkBoxSeleccionado);
				super.acceptAction();
			};

			@Override
			protected void setTitulo(String titulo) {

			}
		};

		//Los controladores de las vistas deben correrse en un thread de JavaFX
		ControladorTest corredorTestEnJavaFXThread = new ControladorTest(ModificarVendedorController.URLVista, modificarVendedorController);
		Statement test = new Statement() {
			@Override
			public void evaluate() throws Throwable {
				//Método a probar
				modificarVendedorController.acceptAction();
				//Se hacen las verificaciones pertinentes para comprobar que el controlador se comporte adecuadamente
				Mockito.verify(coordinadorMock).obtenerTiposDeDocumento();
				Mockito.verify(coordinadorMock, times(llamaAModificarVendedor)).modificarVendedor(any());
				Mockito.verify(presentadorVentanasMock, times(llamaAPresentadorVentanasPresentarError)).presentarError(eq("Revise sus campos"), any(), any());
				Mockito.verify(presentadorVentanasMock, times(llamaAPresentadorVentanasPresentarExcepcion)).presentarExcepcion(eq(excepcion), any());
				Mockito.verify(presentadorVentanasMock, times(llamaAPresentadorVentanasPresentarExcepcionInesperada)).presentarExcepcionInesperada(eq(excepcion), any());
			}
		};

		//Se corre el test en el hilo de JavaFX
		corredorTestEnJavaFXThread.apply(test, null).evaluate();
	}

	/**
	 * Parámetros para el test
	 *
	 * @return
	 * 		Objeto con:
	 *         nombre, apellido, tipo de documento, número de documento, resultado de encriptar la contraseña, contraseña antigua,
	 *         nueva contraseña, repite contraseña, resultado al crear vendedor devuelto por el mock del coordinador,
	 *         veces que se debería llamar a mostrar un error, veces que se debería llamar a mostrar una excepción,
	 *         veces que se debería llamar a mostrar una excepción inesperada, veces que se debería llamar a modificar un vendedor,
	 *         excepción lanzada por el mock del coordinador, veces que se debería llamar a cambiar scene
	 */
	protected Object[] parametersForTestModificarVendedor() {
		TipoDocumento doc = new TipoDocumento(TipoDocumentoStr.DNI);
		return new Object[] {
				new Object[] { "Juan", "Perez", doc, "12345678", "abc", "abc", "abc", "abc", resultadoCorrecto, 0, 0, 0, 1, null, 0, false }, //prueba correcta
				new Object[] { "Juan", "Perez", doc, "12345678", "ac", "abc", "abc", "abc", resultadoCorrecto, 1, 0, 0, 0, null, 0, true }, //prueba contraseña antigua incorrecta
				new Object[] { "Juan", "Perez", doc, "12345678", "abc", "abc", "abc", "abdc", resultadoCorrecto, 1, 0, 0, 0, null, 0, true }, //prueba contraseñas nuevas no coinciden
				new Object[] { "Juan", "Perez", doc, "12345678", "abc", "abc", "abc", "abc", resultadoModificarNombreIncorrecto, 1, 0, 0, 1, null, 0, false }, //prueba nombre incorrecto
				new Object[] { "Juan", "Perez", doc, "12345678", "abc", "abc", "abc", "abc", resultadoModificarApellidoIncorrecto, 1, 0, 0, 1, null, 0, false }, //prueba apellido incorrecto
				new Object[] { "Juan", "Perez", doc, "12345678", "abc", "abc", "abc", "abc", resultadoModificarDocumentoIncorrecto, 1, 0, 0, 1, null, 0, false }, //prueba documento incorrecto
				new Object[] { "Juan", "Perez", doc, "12345678", "abc", "abc", "abc", "abc", resultadoCrearYaExiste, 1, 0, 0, 1, null, 0, false }, //prueba ya existe vendedor
				new Object[] { "Juan", "Perez", doc, "12345678", "abc", "abc", "abc", "abc", new ResultadoModificarVendedor(ErrorModificarVendedor.Formato_Nombre_Incorrecto, ErrorModificarVendedor.Formato_Apellido_Incorrecto), 1, 0, 0, 1, null, 0, false }, //prueba nombre y apellido incorrectos
				new Object[] { "", "Perez", doc, "12345678", "abc", "abc", "abc", "abc", null, 1, 0, 0, 0, null, 0, false }, //prueba nombre vacio
				new Object[] { "Juan", "Perez", doc, "12345678", "abc", "abc", "abc", "abc", resultadoCorrecto, 0, 1, 0, 1, new SaveUpdateException(new Throwable()), 0, false }, //prueba PersistenciaException
		};
	}

	//Resultados modificarVendedor
	private static final ResultadoModificarVendedor resultadoCorrecto = new ResultadoModificarVendedor();
	private static final ResultadoModificarVendedor resultadoModificarNombreIncorrecto =
			new ResultadoModificarVendedor(ErrorModificarVendedor.Formato_Nombre_Incorrecto);
	private static final ResultadoModificarVendedor resultadoModificarApellidoIncorrecto =
			new ResultadoModificarVendedor(ErrorModificarVendedor.Formato_Apellido_Incorrecto);
	private static final ResultadoModificarVendedor resultadoModificarDocumentoIncorrecto =
			new ResultadoModificarVendedor(ErrorModificarVendedor.Formato_Documento_Incorrecto);
	private static final ResultadoModificarVendedor resultadoCrearYaExiste =
			new ResultadoModificarVendedor(ErrorModificarVendedor.Otro_Vendedor_Posee_Mismo_Documento_Y_Tipo);
}