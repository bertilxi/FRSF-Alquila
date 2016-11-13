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
import app.datos.entidades.Cliente;
import app.datos.entidades.InmuebleBuscado;
import app.datos.entidades.TipoDocumento;
import app.excepciones.SaveUpdateException;
import app.logica.CoordinadorJavaFX;
import app.logica.resultados.ResultadoModificarCliente;
import app.logica.resultados.ResultadoModificarCliente.ErrorModificarCliente;
import app.ui.ScenographyChanger;
import app.ui.componentes.ventanas.PresentadorVentanas;
import app.ui.componentes.ventanas.VentanaConfirmacion;
import app.ui.componentes.ventanas.VentanaError;
import app.ui.componentes.ventanas.VentanaErrorExcepcion;
import app.ui.componentes.ventanas.VentanaErrorExcepcionInesperada;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class ModificarClienteControllerTest {

	private static Cliente cliente;

	@Test
	@Parameters
	public void testModificarCliente(String nombre,
			String apellido,
			TipoDocumento tipoDocumento,
			String numeroDocumento,
			String telefono,
			InmuebleBuscado inmueble,
			ResultadoModificarCliente resultadoModificarClienteEsperado,
			Integer llamaAPresentadorVentanasPresentarError,
			Integer llamaAPresentadorVentanasPresentarExcepcion,
			Exception excepcion) throws Throwable {

		CoordinadorJavaFX coordinadorMock = Mockito.mock(CoordinadorJavaFX.class);
		PresentadorVentanas presentadorVentanasMock = mock(PresentadorVentanas.class);
		VentanaError ventanaErrorMock = mock(VentanaError.class);
		VentanaErrorExcepcion ventanaErrorExcepcionMock = mock(VentanaErrorExcepcion.class);
		VentanaErrorExcepcionInesperada ventanaErrorExcepcionInesperadaMock = mock(VentanaErrorExcepcionInesperada.class);
		VentanaConfirmacion ventanaConfirmacionMock = mock(VentanaConfirmacion.class);
		ScenographyChanger scenographyChangerMock = mock(ScenographyChanger.class);
		ModificarClienteController modificarClienteControllerMock = mock(ModificarClienteController.class);

		when(presentadorVentanasMock.presentarError(any(), any(), any())).thenReturn(ventanaErrorMock);
		when(presentadorVentanasMock.presentarExcepcion(any(), any())).thenReturn(ventanaErrorExcepcionMock);
		when(presentadorVentanasMock.presentarExcepcionInesperada(any(), any())).thenReturn(ventanaErrorExcepcionInesperadaMock);
		when(presentadorVentanasMock.presentarConfirmacion(any(), any(), any())).thenReturn(ventanaConfirmacionMock);
		when(scenographyChangerMock.cambiarScenography(any(), any())).thenReturn(modificarClienteControllerMock);
		doNothing().when(modificarClienteControllerMock).setClienteEnModificacion(any());
		doNothing().when(presentadorVentanasMock).presentarToast(any(), any());

		cliente = new Cliente()
				.setNombre(nombre)
				.setApellido(apellido)
				.setTipoDocumento(tipoDocumento)
				.setNumeroDocumento(numeroDocumento)
				.setInmuebleBuscado(inmueble)
				.setTelefono(telefono);

		inmueble.setCliente(cliente);

		when(coordinadorMock.modificarCliente(cliente)).thenReturn(resultadoModificarClienteEsperado);
		if(excepcion != null){
			when(coordinadorMock.modificarCliente(cliente)).thenThrow(excepcion);
		}

		ArrayList<TipoDocumento> tipos = new ArrayList<>();
		tipos.add(tipoDocumento);
		Mockito.when(coordinadorMock.obtenerTiposDeDocumento()).thenReturn(tipos);

		ModificarClienteController modificarClienteController = new ModificarClienteController() {
			@Override
			public void inicializar(URL location, ResourceBundle resources) {
				this.coordinador = coordinadorMock;
				this.presentador = new PresentadorVentanas();
				this.presentador = presentadorVentanasMock;
				setScenographyChanger(scenographyChangerMock);
				super.inicializar(location, resources);
			}

			@Override
			public void acceptAction() {
				this.coordinador = coordinadorMock;
				this.textFieldNombre.setText(nombre);
				this.textFieldApellido.setText(apellido);
				this.comboBoxTipoDocumento.getSelectionModel().select(tipoDocumento);
				this.textFieldNumeroDocumento.setText(numeroDocumento);
				this.textFieldTelefono.setText(telefono);
				super.acceptAction();
			}

			@Override
			protected void setTitulo(String titulo) {

			}

			@Override
			public void setClienteEnModificacion(Cliente clienteEnModificacion) {
				this.clienteEnModificacion = clienteEnModificacion;
			}
		};
		modificarClienteController.setClienteEnModificacion(cliente);

		ControladorTest corredorTestEnJavaFXThread =
				new ControladorTest(ModificarClienteController.URLVista, modificarClienteController);

		Statement test = new Statement() {
			@Override
			public void evaluate() throws Throwable {
				modificarClienteController.acceptAction();

				Mockito.verify(coordinadorMock).obtenerTiposDeDocumento();
				Mockito.verify(presentadorVentanasMock, times(llamaAPresentadorVentanasPresentarError)).presentarError(eq("Revise sus campos"), any(), any());
				Mockito.verify(presentadorVentanasMock, times(llamaAPresentadorVentanasPresentarExcepcion)).presentarExcepcion(eq(excepcion), any());
			}
		};

		corredorTestEnJavaFXThread.apply(test, null).evaluate();


	}

	protected Object[] parametersForTestModificarCliente() {
		TipoDocumento doc = new TipoDocumento(TipoDocumentoStr.DNI);
		InmuebleBuscado inm = new InmuebleBuscado(null,1000000.00, 10.0, 10, 1, 1, true, true, false, false, true, true, true, true, true, true, false);
		return new Object[] {
				//prueba correcta
				new Object[] { "Juan", "Perez",doc,"12345678", "123-123", inm, resultadoCorrecto, 0, 0, null},
				//prueba nombre incorrecto
				new Object[] { "Juan", "Perez",doc,"12345678", "123-123", inm, resultadoModificarNombreIncorrecto, 1, 0, null},
				//prueba apellido incorrecto
				new Object[] { "Juan", "Perez",doc,"12345678", "123-123", inm, resultadoModificarApellidoIncorrecto, 1, 0, null},
				//prueba documento incorrecto
				new Object[] { "Juan", "Perez", doc,"12345678", "123-123", inm, resultadoModificarDocumentoIncorrecto, 1, 0, null},
				//prueba teléfono incorrecto
				new Object[] { "Juan", "Perez", doc,"12345678", "123-123", inm, resultadoModificarTelefonoIncorrecto, 1, 0, null},
				//prueba ya existe cliente
				new Object[] { "Juan", "Perez", doc,"12345678", "123-123", inm, resultadoModificarYaExiste, 1, 0, null},
				//prueba ya existe cliente
				new Object[] { "Juan", "Perez",doc,"12345678", "123-123", inm,new ResultadoModificarCliente(ErrorModificarCliente.Formato_Nombre_Incorrecto,ErrorModificarCliente.Formato_Apellido_Incorrecto),1, 0, null},
				//prueba nombre vacio
				new Object[] { "", "Perez", doc, "12345678", "123-123", inm, null, 1, 0, null},
				//prueba PersistenciaException
				new Object[] { "Juan", "Perez",doc,"12345678", "123-123", inm, resultadoCorrecto, 0, 1, new SaveUpdateException(new Throwable())}

		};
	}

	//Para modificarCliente
	private static final ResultadoModificarCliente resultadoCorrecto = new ResultadoModificarCliente();
	private static final ResultadoModificarCliente resultadoModificarNombreIncorrecto =
			new ResultadoModificarCliente(ResultadoModificarCliente.ErrorModificarCliente.Formato_Nombre_Incorrecto);
	private static final ResultadoModificarCliente resultadoModificarApellidoIncorrecto =
			new ResultadoModificarCliente(ResultadoModificarCliente.ErrorModificarCliente.Formato_Apellido_Incorrecto);
	private static final ResultadoModificarCliente resultadoModificarDocumentoIncorrecto =
			new ResultadoModificarCliente(ResultadoModificarCliente.ErrorModificarCliente.Formato_Documento_Incorrecto);
	private static final ResultadoModificarCliente resultadoModificarTelefonoIncorrecto =
			new ResultadoModificarCliente(ResultadoModificarCliente.ErrorModificarCliente.Formato_Telefono_Incorrecto);
	private static final ResultadoModificarCliente resultadoModificarYaExiste =
			new ResultadoModificarCliente(ResultadoModificarCliente.ErrorModificarCliente.Otro_Cliente_Posee_Mismo_Documento_Y_Tipo);

}