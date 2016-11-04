package app.ui.controladores;

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
import app.excepciones.GestionException;
import app.excepciones.PersistenciaException;
import app.logica.CoordinadorJavaFX;
import app.logica.resultados.ResultadoCrearVendedor;
import app.logica.resultados.ResultadoCrearVendedor.ErrorCrearVendedor;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class AltaVendedorControllerTest {

	@Test
	@Parameters
	public void testCrearVendedor(String nombre, String apellido, TipoDocumento tipoDocumento, String numeroDocumento, String contraseña, String contraseña2, ResultadoCrearVendedor resultadoCrearVendedorEsperado, Integer llamaACrearVendedor, Throwable excepcion) throws Exception {

		CoordinadorJavaFX coordinadorMock = Mockito.mock(CoordinadorJavaFX.class);

		Vendedor vendedor = new Vendedor()
				.setNombre(nombre)
				.setApellido(apellido)
				.setTipoDocumento(tipoDocumento)
				.setNumeroDocumento(numeroDocumento)
				.setPassword(contraseña);

		ArrayList<TipoDocumento> tipos = new ArrayList<>();
		tipos.add(tipoDocumento);

		Mockito.when(coordinadorMock.crearVendedor(vendedor)).thenReturn(resultadoCrearVendedorEsperado);
		Mockito.when(coordinadorMock.obtenerTiposDeDocumento()).thenReturn(tipos);

		AltaVendedorController altaVendedorController = new AltaVendedorController() {
			@Override
			public void inicializar(URL location, ResourceBundle resources) {
				this.coordinador = coordinadorMock;
				super.inicializar(location, resources);
			}

			@Override
			public void acceptAction() throws PersistenciaException, GestionException {
				this.textFieldNombre.setText(nombre);
				this.textFieldApellido.setText(apellido);
				this.comboBoxTipoDocumento.getSelectionModel().select(tipoDocumento);
				this.textFieldNumeroDocumento.setText(numeroDocumento);
				this.passwordFieldContraseña.setText(contraseña);
				this.passwordFieldRepiteContraseña.setText(contraseña);
			};
		};

		ControladorTest corredorTestEnJavaFXThread = new ControladorTest(AltaVendedorController.URLVista, altaVendedorController);

		Statement test = new Statement() {
			@Override
			public void evaluate() throws Throwable {
				Mockito.verify(coordinadorMock).obtenerTiposDeDocumento();
				altaVendedorController.acceptAction();
				Mockito.verify(coordinadorMock, Mockito.times(llamaACrearVendedor)).crearVendedor(Mockito.any());
			}
		};

		try{
			corredorTestEnJavaFXThread.apply(test, null);
		} catch(Throwable e){
			throw new Exception(e);
		}
	}

	protected Object[] parametersForTestCrearVendedor() {
		return new Object[] {
				new Object[] { "Juan", "Perez", (new TipoDocumento()).setTipo(TipoDocumentoStr.DNI), "12345678", "abc", "abc", resultadoCorrecto, 1, null }, //prueba correcta
				new Object[] { "Juan", "Perez", (new TipoDocumento()).setTipo(TipoDocumentoStr.DNI), "12345678", "abc", "abc", resultadoCrearNombreIncorrecto, 1, null }, //prueba nombre incorrecto
				new Object[] { "Juan", "Perez", (new TipoDocumento()).setTipo(TipoDocumentoStr.DNI), "12345678", "abc", "abc", resultadoCrearApellidoIncorrecto, 1, null }, //prueba apellido incorrecto
				new Object[] { "Juan", "Perez", (new TipoDocumento()).setTipo(TipoDocumentoStr.DNI), "12345678", "abc", "abc", resultadoCrearDocumentoIncorrecto, 1, null }, //prueba documento incorrecto
				new Object[] { "Juan", "Perez", (new TipoDocumento()).setTipo(TipoDocumentoStr.DNI), "12345678", "abc", "abc", resultadoCrearYaExiste, 1, null }, //prueba ya existe vendedor
				new Object[] { "Juan", "Perez", (new TipoDocumento()).setTipo(TipoDocumentoStr.DNI), "12345678", "abc", "abc", new ResultadoCrearVendedor(ErrorCrearVendedor.Formato_Nombre_Incorrecto, ErrorCrearVendedor.Formato_Apellido_Incorrecto), 1, null }, //prueba ya existe vendedor
				new Object[] { "", "Perez", (new TipoDocumento()).setTipo(TipoDocumentoStr.DNI), "12345678", "abc", "abc", null, 0, null } //prueba nombre vacio
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