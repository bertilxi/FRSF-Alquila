package app.ui.controladores;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;

import app.datos.clases.DatosLogin;
import app.excepciones.PersistenciaException;
import app.logica.CoordinadorJavaFX;
import app.logica.resultados.ResultadoAutenticacion;
import app.logica.resultados.ResultadoAutenticacion.ErrorAutenticacion;
import app.ui.controladores.resultado.ResultadoControlador;
import app.ui.controladores.resultado.ResultadoControlador.ErrorControlador;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class LoginControllerTest {

	@Test
	@Parameters
	public void testIngresar(String dni, String contra, ResultadoControlador resultadoVista, ResultadoAutenticacion resultadoLogica, Throwable excepcion) throws Exception {
		CoordinadorJavaFX coordinadorMock = new CoordinadorJavaFX() {
			@Override
			public ResultadoAutenticacion autenticarVendedor(DatosLogin login) throws PersistenciaException {
				if(resultadoLogica != null){
					return resultadoLogica;
				}
				if(excepcion instanceof PersistenciaException){
					throw (PersistenciaException) excepcion;
				}
				new Integer("asd");
				return null;
			}
		};

		LoginController loginController = new LoginController() {
			@Override
			public ResultadoControlador ingresar() {
				coordinador = coordinadorMock;
				desatendido = true;
				tfDNI.setText(dni);
				pfContra.setText(contra);
				return super.ingresar();
			};
		};

		ControladorTest corredorTestEnJavaFXThread = new ControladorTest(LoginController.URLVista, loginController);

		Statement test = new Statement() {
			@Override
			public void evaluate() throws Throwable {
				assertEquals(resultadoVista, loginController.ingresar());
			}
		};

		try{
			corredorTestEnJavaFXThread.apply(test, null).evaluate();
		} catch(Throwable e){
			throw new Exception(e);
		}
	}

	protected Object[] parametersForTestIngresar() {
		return new Object[] {
				new Object[] { "12345678", "pepe", new ResultadoControlador(), new ResultadoAutenticacion(), null }, //prueba ingreso correcto
				new Object[] { "", "pepe", new ResultadoControlador(ErrorControlador.Campos_Vacios), new ResultadoAutenticacion(ErrorAutenticacion.Datos_Incorrectos), null }, //prueba DNI vacio
				new Object[] { "12345678", "", new ResultadoControlador(ErrorControlador.Campos_Vacios), new ResultadoAutenticacion(ErrorAutenticacion.Datos_Incorrectos), null }, //prueba Contraseña vacia
				new Object[] { "12345678", "pepe", new ResultadoControlador(ErrorControlador.Datos_Incorrectos), new ResultadoAutenticacion(ErrorAutenticacion.Datos_Incorrectos), null }, //prueba un ingreso incorrecto
				new Object[] { "ñú", "pepe", new ResultadoControlador(ErrorControlador.Datos_Incorrectos), new ResultadoAutenticacion(ErrorAutenticacion.Datos_Incorrectos), null }, //prueba un ingreso incorrecto con caracteres UTF8
				new Object[] { "ñú", "pepe", new ResultadoControlador(ErrorControlador.Error_Persistencia), null, new PersistenciaException("Error de persistencia. Test.") }, //Prueba una excepcion de persistencia
				new Object[] { "ñú", "pepe", new ResultadoControlador(ErrorControlador.Error_Desconocido), null, new Exception() } //Prueba una excepcion desconocida
		};
	}

}