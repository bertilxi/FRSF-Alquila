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

import app.datos.clases.TipoDocumentoStr;
import app.datos.entidades.TipoDocumento;
import app.datos.entidades.Vendedor;
import app.excepciones.EntidadExistenteConEstadoBajaException;
import app.excepciones.GestionException;
import app.excepciones.PersistenciaException;
import app.excepciones.SaveUpdateException;
import app.logica.CoordinadorJavaFX;
import app.logica.resultados.ResultadoCrearVendedor;
import app.logica.resultados.ResultadoCrearVendedor.ErrorCrearVendedor;
import app.ui.componentes.ventanas.PresentadorVentanas;
import app.ui.componentes.ventanas.VentanaConfirmacion;
import app.ui.componentes.ventanas.VentanaError;
import app.ui.componentes.ventanas.VentanaErrorExcepcion;
import app.ui.componentes.ventanas.VentanaErrorExcepcionInesperada;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class AltaVendedorControllerTest {

	@Test
	@Parameters
	public void testCrearVendedor(String nombre,
			String apellido,
			TipoDocumento tipoDocumento,
			String numeroDocumento,
			String contraseña,
			String contraseña2,
			ResultadoCrearVendedor resultadoCrearVendedorEsperado,
			Integer llamaAPresentadorVentanasPresentarError,
			Integer llamaAPresentadorVentanasPresentarExcepcion,
			Integer llamaAPresentadorVentanasPresentarExcepcionInesperada,
			Integer llamaACrearVendedor,
			Exception excepcion,
			Boolean aceptarVentanaConfirmacion,
			Integer llamaACambiarScene) throws Throwable {

		CoordinadorJavaFX coordinadorMock = mock(CoordinadorJavaFX.class);
		PresentadorVentanas presentadorVentanasMock = mock(PresentadorVentanas.class);
		VentanaError ventanaErrorMock = mock(VentanaError.class);
		VentanaErrorExcepcion ventanaErrorExcepcionMock = mock(VentanaErrorExcepcion.class);
		VentanaErrorExcepcionInesperada ventanaErrorExcepcionInesperadaMock = mock(VentanaErrorExcepcionInesperada.class);
		VentanaConfirmacion ventanaConfirmacionMock = mock(VentanaConfirmacion.class);

		Vendedor vendedor = new Vendedor()
				.setNombre(nombre)
				.setApellido(apellido)
				.setTipoDocumento(tipoDocumento)
				.setNumeroDocumento(numeroDocumento)
				.setPassword(contraseña);

		ArrayList<TipoDocumento> tipos = new ArrayList<>();
		tipos.add(tipoDocumento);

		when(presentadorVentanasMock.presentarError(any(), any(), any())).thenReturn(ventanaErrorMock);
		when(presentadorVentanasMock.presentarExcepcion(any(), any())).thenReturn(ventanaErrorExcepcionMock);
		when(presentadorVentanasMock.presentarExcepcionInesperada(any(), any())).thenReturn(ventanaErrorExcepcionInesperadaMock);
		when(presentadorVentanasMock.presentarConfirmacion(any(), any(), any())).thenReturn(ventanaConfirmacionMock);
		when(ventanaConfirmacionMock.acepta()).thenReturn(aceptarVentanaConfirmacion);
		doNothing().when(presentadorVentanasMock).presentarToast(any(), any());
		when(coordinadorMock.crearVendedor(vendedor)).thenReturn(resultadoCrearVendedorEsperado);
		if(excepcion != null){
			when(coordinadorMock.crearVendedor(vendedor)).thenThrow(excepcion);
		}
		when(coordinadorMock.obtenerTiposDeDocumento()).thenReturn(tipos);

		AltaVendedorController altaVendedorController = new AltaVendedorController() {
			@Override
			public void inicializar(URL location, ResourceBundle resources) {
				this.coordinador = coordinadorMock;
				this.presentador = new PresentadorVentanas();
				this.presentador = presentadorVentanasMock;
				super.inicializar(location, resources);
			}

			@Override
			public void acceptAction() throws PersistenciaException, GestionException {
				this.coordinador = coordinadorMock;
				this.textFieldNombre.setText(nombre);
				this.textFieldApellido.setText(apellido);
				this.comboBoxTipoDocumento.getSelectionModel().select(tipoDocumento);
				this.textFieldNumeroDocumento.setText(numeroDocumento);
				this.passwordFieldContraseña.setText(contraseña);
				this.passwordFieldRepiteContraseña.setText(contraseña);
				super.acceptAction();
			};

			@Override
			protected void revivirVendedor(Vendedor vendedor) {

			}

			@Override
			protected void setTitulo(String titulo) {

			}
		};

		ControladorTest corredorTestEnJavaFXThread = new ControladorTest(AltaVendedorController.URLVista, altaVendedorController);
		Statement test = new Statement() {
			@Override
			public void evaluate() throws Throwable {
				altaVendedorController.acceptAction();
				Mockito.verify(coordinadorMock).obtenerTiposDeDocumento();
				Mockito.verify(coordinadorMock, times(llamaACrearVendedor)).crearVendedor(any());
				Mockito.verify(presentadorVentanasMock, times(llamaAPresentadorVentanasPresentarError)).presentarError(eq("Revise sus campos"), any(), any());
				Mockito.verify(presentadorVentanasMock, times(llamaAPresentadorVentanasPresentarExcepcion)).presentarExcepcion(eq(excepcion), any());
				Mockito.verify(presentadorVentanasMock, times(llamaAPresentadorVentanasPresentarExcepcionInesperada)).presentarExcepcionInesperada(eq(excepcion), any());
			}
		};

		corredorTestEnJavaFXThread.apply(test, null).evaluate();
	}

	protected Object[] parametersForTestCrearVendedor() {
		TipoDocumento doc = new TipoDocumento(TipoDocumentoStr.DNI);
		return new Object[] {
				new Object[] { "Juan", "Perez", doc, "12345678", "abc", "abc", resultadoCorrecto, 0, 0, 0, 1, null, true, 0 }, //prueba correcta
				new Object[] { "Juan", "Perez", doc, "12345678", "abc", "abc", resultadoCrearNombreIncorrecto, 1, 0, 0, 1, null, true, 0 }, //prueba nombre incorrecto
				new Object[] { "Juan", "Perez", doc, "12345678", "abc", "abc", resultadoCrearApellidoIncorrecto, 1, 0, 0, 1, null, true, 0 }, //prueba apellido incorrecto
				new Object[] { "Juan", "Perez", doc, "12345678", "abc", "abc", resultadoCrearDocumentoIncorrecto, 1, 0, 0, 1, null, true, 0 }, //prueba documento incorrecto
				new Object[] { "Juan", "Perez", doc, "12345678", "abc", "abc", resultadoCrearYaExiste, 1, 0, 0, 1, null, true, 0 }, //prueba ya existe vendedor
				new Object[] { "Juan", "Perez", doc, "12345678", "abc", "abc", new ResultadoCrearVendedor(ErrorCrearVendedor.Formato_Nombre_Incorrecto, ErrorCrearVendedor.Formato_Apellido_Incorrecto), 1, 0, 0, 1, null, true, 0 }, //prueba nombre y apellido incorrectos
				new Object[] { "", "Perez", doc, "12345678", "abc", "abc", null, 1, 0, 0, 0, null, true, 0 }, //prueba nombre vacio
				new Object[] { "Juan", "Perez", doc, "12345678", "abc", "abc", resultadoCorrecto, 0, 0, 0, 1, new EntidadExistenteConEstadoBajaException(), true, 1 }, //prueba Vendedor Existente y acepta
				new Object[] { "Juan", "Perez", doc, "12345678", "abc", "abc", resultadoCorrecto, 0, 0, 0, 1, new EntidadExistenteConEstadoBajaException(), false, 0 }, //prueba Vendedor Existente y cancela
				new Object[] { "Juan", "Perez", doc, "12345678", "abc", "abc", resultadoCorrecto, 0, 1, 0, 1, new SaveUpdateException(new Throwable()), false, 0 }, //prueba PersistenciaException
		};
	}

	//Para crearVendedor
	private static final ResultadoCrearVendedor resultadoCorrecto = new ResultadoCrearVendedor();
	private static final ResultadoCrearVendedor resultadoCrearNombreIncorrecto =
			new ResultadoCrearVendedor(ErrorCrearVendedor.Formato_Nombre_Incorrecto);
	private static final ResultadoCrearVendedor resultadoCrearApellidoIncorrecto =
			new ResultadoCrearVendedor(ErrorCrearVendedor.Formato_Apellido_Incorrecto);
	private static final ResultadoCrearVendedor resultadoCrearDocumentoIncorrecto =
			new ResultadoCrearVendedor(ErrorCrearVendedor.Formato_Documento_Incorrecto);
	private static final ResultadoCrearVendedor resultadoCrearYaExiste =
			new ResultadoCrearVendedor(ErrorCrearVendedor.Ya_Existe_Vendedor);
}