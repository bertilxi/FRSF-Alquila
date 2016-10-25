package app.ui.controladores;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import app.datos.clases.DatosLogin;
import app.excepciones.PersistenciaException;
import app.logica.CoordinadorJavaFX;
import app.logica.resultados.ResultadoAutenticacion;
import app.logica.resultados.ResultadoAutenticacion.ErrorAutenticacion;
import app.ui.controladores.resultado.ResultadoControlador;
import app.ui.controladores.resultado.ResultadoControlador.ErrorControlador;

public class LoginControllerTest {

	private static CoordinadorJavaFX coordinadorMock;

	private String dni;

	private String contra;

	private LoginController loginController = new LoginController() {
		@Override
		public ResultadoControlador ingresar() {
			coordinador = coordinadorMock;
			desatendido = true;
			tfDNI.setText(dni);
			pfContra.setText(contra);
			return super.ingresar();
		};
	};

	private void correrTestIngresar(String dni, String contra, ResultadoControlador resultadoVista, ResultadoAutenticacion resultadoLogica, Throwable excepcion) throws Exception {
		this.dni = dni;
		this.contra = contra;

		if(resultadoLogica != null){
			coordinadorMock = new CoordinadorJavaFX() {
				@Override
				public ResultadoAutenticacion autenticarVendedor(DatosLogin login) throws PersistenciaException {
					return resultadoLogica;
				}
			};
		}
		if(excepcion != null){
			coordinadorMock = new CoordinadorJavaFX() {
				@Override
				public ResultadoAutenticacion autenticarVendedor(DatosLogin login) throws PersistenciaException {
					if(excepcion instanceof PersistenciaException){
						throw (PersistenciaException) excepcion;
					}
					new Integer("asd");
					return null;
				}
			};
		}

		ContoladorTest test = new ContoladorTest(LoginController.URLVista, loginController);

		Statement regla = new Statement() {
			@Override
			public void evaluate() throws Throwable {
				assertEquals(resultadoVista, loginController.ingresar());
			}
		};

		try{
			test.apply(regla, Description.createSuiteDescription("Prueba del metodo ingresar de la clase LoginController")).evaluate();
		} catch(Throwable e){
			throw new Exception(e);
		}
	}

	@Test
	public void ingresarCorrecto() throws Exception {
		correrTestIngresar("12345678", "pepe", new ResultadoControlador(), new ResultadoAutenticacion(), null);
	}

	@Test
	public void ingresarDNIVacio() throws Exception {
		correrTestIngresar("", "pepe", new ResultadoControlador(ErrorControlador.Campos_Vacios), new ResultadoAutenticacion(ErrorAutenticacion.Datos_Incorrectos), null);
	}

	@Test
	public void ingresarContraVacia() throws Exception {
		correrTestIngresar("12345678", "", new ResultadoControlador(ErrorControlador.Campos_Vacios), new ResultadoAutenticacion(ErrorAutenticacion.Datos_Incorrectos), null);
	}

	@Test
	public void ingresarDatosIncorrectos() throws Exception {
		correrTestIngresar("12345678", "pepe", new ResultadoControlador(ErrorControlador.Datos_Incorrectos), new ResultadoAutenticacion(ErrorAutenticacion.Datos_Incorrectos), null);
	}

	@Test
	public void ingresarDatosIncorrectosCaracteresUTF8() throws Exception {
		correrTestIngresar("ñú", "pepe", new ResultadoControlador(ErrorControlador.Datos_Incorrectos), new ResultadoAutenticacion(ErrorAutenticacion.Datos_Incorrectos), null);
	}

	@Test
	public void ingresarExcepcionPersistencia() throws Exception {
		correrTestIngresar("ñú", "pepe", new ResultadoControlador(ErrorControlador.Error_Persistencia), null, new PersistenciaException("Error de persistencia. Test."));
	}

	@Test
	public void ingresarExcepcionDesconocida() throws Exception {
		correrTestIngresar("ñú", "pepe", new ResultadoControlador(ErrorControlador.Error_Desconocido), null, new Exception());
	}

}