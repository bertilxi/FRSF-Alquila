package app.ui.controladores;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.junit.Test;
import org.junit.runners.model.Statement;
import org.mockito.Mockito;

import app.datos.clases.TipoDocumentoStr;
import app.datos.entidades.Cliente;
import app.datos.entidades.TipoDocumento;
import app.excepciones.GestionException;
import app.excepciones.PersistenciaException;
import app.logica.CoordinadorJavaFX;
import app.logica.resultados.ResultadoCrearCliente;
import junitparams.Parameters;

public class AltaClienteControllerTest {

	@Test
	@Parameters
	// TODO: probar con inmueble buscado
    // TODO: terminar y corregir
	public void testCrearClientetestCrearVendedor(String nombre,
			String apellido,
			TipoDocumento tipoDocumento,
			String numeroDocumento,
			String telefono,
			ResultadoCrearCliente resultadoCrearClienteEsperado,
			Integer llamaACrearCliente,
			Throwable excepcion) throws Exception {

		CoordinadorJavaFX coordinadorMock = Mockito.mock(CoordinadorJavaFX.class);

		Cliente cliente = new Cliente()
				.setNombre(nombre)
				.setApellido(apellido)
				.setTipoDocumento(tipoDocumento)
				.setNumeroDocumento(numeroDocumento)
				//                .setInmuebleBuscado()
				.setTelefono(telefono);

		ArrayList<TipoDocumento> tipos = new ArrayList<>();
		tipos.add(tipoDocumento);

		Mockito.when(coordinadorMock.crearCliente(cliente)).thenReturn(resultadoCrearClienteEsperado);
		Mockito.when(coordinadorMock.obtenerTiposDeDocumento()).thenReturn(tipos);

		AltaClienteController altaClienteController = new AltaClienteController() {
			@Override
			public void inicializar(URL location, ResourceBundle resources) {
				super.inicializar(location, resources);
				this.coordinador = coordinadorMock;
			}

			@Override
			public void acceptAction() throws PersistenciaException, GestionException {
				this.textFieldNombre.setText(nombre);
				this.textFieldApellido.setText(apellido);
				this.comboBoxTipoDocumento.getSelectionModel().select(tipoDocumento);
				this.textFieldNumeroDocumento.setText(numeroDocumento);
				this.textFieldTelefono.setText(telefono);

			};
		};

		ControladorTest corredorTestEnJavaFXThread =
				new ControladorTest(AltaClienteController.URLVista, altaClienteController);

		Statement test = new Statement() {
			@Override
			public void evaluate() throws Throwable {
				Mockito.verify(coordinadorMock).obtenerTiposDeDocumento();
				altaClienteController.acceptAction();
				Mockito.verify(coordinadorMock, Mockito.times(llamaACrearCliente)).crearCliente(Mockito.any());
			}
		};

		try{
			corredorTestEnJavaFXThread.apply(test, null);
		} catch(Throwable e){
			throw new Exception(e);
		}

	}

	protected Object[] parametersForTestCrearCliente() {
		return new Object[] {
				//prueba correcta
				new Object[] { "Juan", "Perez",
						(new TipoDocumento()).setTipo(TipoDocumentoStr.DNI),
						"12345678", "", resultadoCorrecto, 1, null },
				//prueba nombre incorrecto
				new Object[] { "Juan", "Perez",
						(new TipoDocumento()).setTipo(TipoDocumentoStr.DNI),
						"12345678", "", resultadoCrearNombreIncorrecto, 1, null },
				//prueba apellido incorrecto
				new Object[] { "Juan", "Perez",
						(new TipoDocumento()).setTipo(TipoDocumentoStr.DNI),
						"12345678", "", resultadoCrearApellidoIncorrecto, 1, null },
				//prueba documento incorrecto
				new Object[] { "Juan", "Perez",
						(new TipoDocumento()).setTipo(TipoDocumentoStr.DNI),
						"12345678", "", resultadoCrearDocumentoIncorrecto, 1, null },
				//prueba ya existe vendedor
				new Object[] { "Juan", "Perez",
						(new TipoDocumento()).setTipo(TipoDocumentoStr.DNI),
						"12345678", "", resultadoCrearYaExiste, 1, null },
				//prueba ya existe vendedor
				new Object[] { "Juan", "Perez",
						(new TipoDocumento()).setTipo(TipoDocumentoStr.DNI),
						"12345678", "",
						new ResultadoCrearCliente(ResultadoCrearCliente.ErrorCrearCliente.Formato_Nombre_Incorrecto,
								ResultadoCrearCliente.ErrorCrearCliente.Formato_Apellido_Incorrecto),
						1, null },
				//prueba nombre vacio
				new Object[] { "", "Perez",
						(new TipoDocumento()).setTipo(TipoDocumentoStr.DNI),
						"12345678", "abc", "abc", null, 0, null }
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