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
import app.excepciones.EntidadExistenteConEstadoBajaException;
import app.excepciones.SaveUpdateException;
import app.logica.CoordinadorJavaFX;
import app.logica.resultados.ResultadoCrearCliente;
import app.logica.resultados.ResultadoCrearCliente.ErrorCrearCliente;
import app.ui.ScenographyChanger;
import app.ui.componentes.ventanas.PresentadorVentanas;
import app.ui.componentes.ventanas.VentanaConfirmacion;
import app.ui.componentes.ventanas.VentanaError;
import app.ui.componentes.ventanas.VentanaErrorExcepcion;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class AltaClienteControllerTest {

	private static Cliente cliente;

	@Test
	@Parameters
	public void testCrearCliente(String nombre,
			String apellido,
			TipoDocumento tipoDocumento,
			String numeroDocumento,
			String telefono,
			InmuebleBuscado inmueble,
			ResultadoCrearCliente resultadoCrearClienteEsperado,
			Integer llamaAPresentadorVentanasPresentarError,
			Integer llamaAPresentadorVentanasPresentarExcepcion,
			Integer llamaACrearCliente,
			Exception excepcion,
			Boolean aceptarVentanaConfirmacion,
			Integer llamaACambiarScene) throws Throwable {

		CoordinadorJavaFX coordinadorMock = Mockito.mock(CoordinadorJavaFX.class);
		PresentadorVentanas presentadorVentanasMock = mock(PresentadorVentanas.class);
		VentanaError ventanaErrorMock = mock(VentanaError.class);
		VentanaErrorExcepcion ventanaErrorExcepcionMock = mock(VentanaErrorExcepcion.class);
		VentanaConfirmacion ventanaConfirmacionMock = mock(VentanaConfirmacion.class);
		ScenographyChanger scenographyChangerMock = mock(ScenographyChanger.class);
		ModificarClienteController modificarClienteControllerMock = mock(ModificarClienteController.class);

		when(presentadorVentanasMock.presentarError(any(), any(), any())).thenReturn(ventanaErrorMock);
		when(presentadorVentanasMock.presentarExcepcion(any(), any())).thenReturn(ventanaErrorExcepcionMock);
		when(presentadorVentanasMock.presentarConfirmacion(any(), any(), any())).thenReturn(ventanaConfirmacionMock);
		when(ventanaConfirmacionMock.acepta()).thenReturn(aceptarVentanaConfirmacion);
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

		when(coordinadorMock.crearCliente(cliente)).thenReturn(resultadoCrearClienteEsperado);
		if(excepcion != null){
			when(coordinadorMock.crearCliente(cliente)).thenThrow(excepcion);
		}

		ArrayList<TipoDocumento> tipos = new ArrayList<>();
		tipos.add(tipoDocumento);
		Mockito.when(coordinadorMock.obtenerTiposDeDocumento()).thenReturn(tipos);

		AltaClienteController altaClienteController = new AltaClienteController() {
			@Override
			public void inicializar(URL location, ResourceBundle resources) {
				this.coordinador = coordinadorMock;
				this.presentador = presentadorVentanasMock;
				setScenographyChanger(scenographyChangerMock);
				super.inicializar(location, resources);
			}

			@Override
			public void acceptAction() {
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
			public void setCliente(Cliente cliente) {
				if(cliente != null){
					this.cliente = cliente;
				}
				else{
					this.cliente = new Cliente();
				}
			}
		};
		altaClienteController.setCliente(cliente);

		ControladorTest corredorTestEnJavaFXThread =
				new ControladorTest(AltaClienteController.URLVista, altaClienteController);

		Statement test = new Statement() {
			@Override
			public void evaluate() throws Throwable {
				altaClienteController.acceptAction();

				Mockito.verify(coordinadorMock).obtenerTiposDeDocumento();
				Mockito.verify(coordinadorMock, Mockito.times(llamaACrearCliente)).crearCliente(Mockito.any());
				Mockito.verify(presentadorVentanasMock, times(llamaAPresentadorVentanasPresentarError)).presentarError(eq("Revise sus campos"), any(), any());
				Mockito.verify(presentadorVentanasMock, times(llamaAPresentadorVentanasPresentarExcepcion)).presentarExcepcion(eq(excepcion), any());
				Mockito.verify(scenographyChangerMock, times(llamaACambiarScene)).cambiarScenography(ModificarClienteController.URLVista, false);
				Mockito.verify(modificarClienteControllerMock, times(llamaACambiarScene)).setClienteEnModificacion(cliente);
			}
		};

		corredorTestEnJavaFXThread.apply(test, null).evaluate();


	}

	protected Object[] parametersForTestCrearCliente() {
		TipoDocumento doc = new TipoDocumento(TipoDocumentoStr.DNI);
		InmuebleBuscado inm = new InmuebleBuscado(null,1000000.00, 10.0, 10, 1, 1, true, true, false, false, true, true, true, true, true, true, false);
		return new Object[] {
				//prueba correcta
				new Object[] { "Juan", "Perez",doc,"12345678", "123-123", inm, resultadoCorrecto, 0, 0, 1, null, true, 0  },
				//prueba nombre incorrecto
				new Object[] { "Juan", "Perez",doc,"12345678", "123-123", inm, resultadoCrearNombreIncorrecto, 1, 0, 1, null, true, 0 },
				//prueba apellido incorrecto
				new Object[] { "Juan", "Perez",doc,"12345678", "123-123", inm, resultadoCrearApellidoIncorrecto, 1, 0, 1, null, true, 0 },
				//prueba documento incorrecto
				new Object[] { "Juan", "Perez", doc,"12345678", "123-123", inm, resultadoCrearDocumentoIncorrecto, 1, 0, 1, null, true, 0 },
				//prueba teléfono incorrecto
				new Object[] { "Juan", "Perez", doc,"12345678", "123-123", inm, resultadoCrearTelefonoIncorrecto, 1, 0, 1, null, true, 0 },
				//prueba ya existe cliente
				new Object[] { "Juan", "Perez", doc,"12345678", "123-123", inm, resultadoCrearYaExiste, 1, 0, 1, null, true, 0 },
				//prueba ya existe cliente
				new Object[] { "Juan", "Perez",doc,"12345678", "123-123", inm,new ResultadoCrearCliente(ErrorCrearCliente.Formato_Nombre_Incorrecto,ErrorCrearCliente.Formato_Apellido_Incorrecto),1, 0, 1, null, true, 0 },
				//prueba nombre vacio
				new Object[] { "", "Perez", doc, "12345678", "123-123", inm, null, 1, 0, 0, null, true, 0 },
				//prueba cliente Existente y acepta
				new Object[] { "Juan", "Perez",doc,"12345678", "123-123", inm, resultadoCorrecto, 0, 0, 1, new EntidadExistenteConEstadoBajaException(), true, 1 },
				//prueba cliente Existente y cancela
				new Object[] { "Juan", "Perez",doc,"12345678", "123-123", inm, resultadoCorrecto, 0, 0, 1, new EntidadExistenteConEstadoBajaException(), false, 0 },
				//prueba PersistenciaException
				new Object[] { "Juan", "Perez",doc,"12345678", "123-123", inm, resultadoCorrecto, 0, 1, 1, new SaveUpdateException(new Throwable()), false, 0 }

		};
	}

	//Para crearCliente
	private static final ResultadoCrearCliente resultadoCorrecto = new ResultadoCrearCliente();
	private static final ResultadoCrearCliente resultadoCrearNombreIncorrecto =
			new ResultadoCrearCliente(ResultadoCrearCliente.ErrorCrearCliente.Formato_Nombre_Incorrecto);
	private static final ResultadoCrearCliente resultadoCrearApellidoIncorrecto =
			new ResultadoCrearCliente(ResultadoCrearCliente.ErrorCrearCliente.Formato_Apellido_Incorrecto);
	private static final ResultadoCrearCliente resultadoCrearDocumentoIncorrecto =
			new ResultadoCrearCliente(ResultadoCrearCliente.ErrorCrearCliente.Formato_Documento_Incorrecto);
	private static final ResultadoCrearCliente resultadoCrearTelefonoIncorrecto =
			new ResultadoCrearCliente(ResultadoCrearCliente.ErrorCrearCliente.Formato_Telefono_Incorrecto);
	private static final ResultadoCrearCliente resultadoCrearYaExiste =
			new ResultadoCrearCliente(ResultadoCrearCliente.ErrorCrearCliente.Ya_Existe_Cliente);

}