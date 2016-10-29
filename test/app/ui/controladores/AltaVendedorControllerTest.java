package app.ui.controladores;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;
import org.mockito.Mockito;

import app.datos.clases.TipoDocumentoStr;
import app.datos.entidades.TipoDocumento;
import app.datos.entidades.Vendedor;
import app.excepciones.GestionException;
import app.excepciones.PersistenciaException;
import app.logica.gestores.GestorDatos;
import app.logica.gestores.GestorVendedor;
import app.logica.resultados.ResultadoCrearVendedor;
import app.logica.resultados.ResultadoCrearVendedor.ErrorCrearVendedor;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class AltaVendedorControllerTest {

	@Test
	@Parameters
	public void testCrearVendedor(String nombre, String apellido, TipoDocumento tipoDocumento, String numeroDocumento, String contraseña, String contraseña2, ResultadoCrearVendedor resultadoCrearVendedorEsperado, Throwable excepcion) throws Exception {

		GestorVendedor gestorVendedorMock = Mockito.mock(GestorVendedor.class);
		GestorDatos gestorDatosMock = Mockito.mock(GestorDatos.class);

		Vendedor vendedor = new Vendedor()
				.setNombre(nombre)
				.setApellido(apellido)
				.setTipoDocumento(tipoDocumento)
				.setNumeroDocumento(numeroDocumento)
				.setPassword(contraseña);

		ArrayList<TipoDocumento> tipos = new ArrayList<TipoDocumento>();
		tipos.add(0, tipoDocumento);

		Mockito.when(gestorVendedorMock.crearVendedor(vendedor)).thenReturn(resultadoCrearVendedorEsperado);
		Mockito.when(gestorDatosMock.obtenerTiposDeDocumento()).thenReturn(tipos);

		AltaVendedorController altaVendedorController = new AltaVendedorController() {
			@Override
			public ResultadoCrearVendedor acceptAction() throws PersistenciaException, GestionException {
				this.gestorVendedor = gestorVendedorMock;
				this.gestorDatos = gestorDatosMock;
				this.textFieldNombre.setText(nombre);
				this.textFieldApellido.setText(apellido);
				this.comboBoxTipoDocumento.getItems().clear();
				this.comboBoxTipoDocumento.getItems().add(tipoDocumento);
				this.comboBoxTipoDocumento.getSelectionModel().select(tipoDocumento);
				this.textFieldNumeroDocumento.setText(numeroDocumento);
				this.passwordFieldContraseña.setText(contraseña);
				this.passwordFieldRepiteContraseña.setText(contraseña);
				return super.acceptAction();
			};
		};

		ControladorTest corredorTestEnJavaFXThread = new ControladorTest(AltaVendedorController.URLVista, altaVendedorController);

		Statement test = new Statement() {
			@Override
			public void evaluate() throws Throwable {
				//Mockito.verify(gestorVendedorMock).crearVendedor(vendedor);
				assertEquals(resultadoCrearVendedorEsperado, altaVendedorController.acceptAction());
			}
		};

		try{
			corredorTestEnJavaFXThread.apply(test, null).evaluate();
		} catch(Throwable e){
			throw new Exception(e);
		}
	}

	protected Object[] parametersForTestCrearVendedor() {
		return new Object[] {
				new Object[] { "Juan", "Perez", (new TipoDocumento()).setTipo(TipoDocumentoStr.DNI), "12345678", "abc", "abc", resultadoCorrecto, null }, //prueba correcta
				new Object[] { "Juan", "Perez", (new TipoDocumento()).setTipo(TipoDocumentoStr.DNI), "12345678", "abc", "abc", resultadoCrearNombreIncorrecto, null }, //prueba nombre incorrecto
				new Object[] { "Juan", "Perez", (new TipoDocumento()).setTipo(TipoDocumentoStr.DNI), "12345678", "abc", "abc", resultadoCrearApellidoIncorrecto, null }, //prueba apellido incorrecto
				new Object[] { "Juan", "Perez", (new TipoDocumento()).setTipo(TipoDocumentoStr.DNI), "12345678", "abc", "abc", resultadoCrearDocumentoIncorrecto, null }, //prueba documento incorrecto
				new Object[] { "Juan", "Perez", (new TipoDocumento()).setTipo(TipoDocumentoStr.DNI), "12345678", "abc", "abc", resultadoCrearYaExiste, null }, //prueba ya existe vendedor
				new Object[] { "Juan", "Perez", (new TipoDocumento()).setTipo(TipoDocumentoStr.DNI), "12345678", "abc", "abc", new ResultadoCrearVendedor(ErrorCrearVendedor.Formato_Nombre_Incorrecto, ErrorCrearVendedor.Formato_Apellido_Incorrecto), null } //prueba ya existe vendedor
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