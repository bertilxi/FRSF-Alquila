package app.logica.gestores;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;
import org.mockito.Matchers;
import org.mockito.Mockito;

import app.comun.EncriptadorPassword;
import app.datos.clases.DatosLogin;
import app.datos.clases.TipoDocumentoStr;
import app.datos.entidades.TipoDocumento;
import app.datos.entidades.Vendedor;
import app.datos.servicios.VendedorService;
import app.datos.servicios.mock.VendedorServiceMock;
import app.excepciones.PersistenciaException;
import app.logica.resultados.ResultadoAutenticacion;
import app.logica.resultados.ResultadoAutenticacion.ErrorAutenticacion;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class GestorVendedorTest {

	private EncriptadorPassword encriptadorMock = Mockito.mock(EncriptadorPassword.class);

	@Test
	@Parameters
	public void testAutenticarVendedor(DatosLogin datos, ResultadoAutenticacion resultadoLogica, Vendedor vendedor, Throwable excepcion) throws Exception {
		//Creamos el soporte de la prueba
		VendedorService vendedorServiceMock = new VendedorServiceMock(vendedor, excepcion);
		GestorVendedor gestorVendedor = new GestorVendedor() {
			{
				this.persistidorVendedor = vendedorServiceMock;
				this.encriptador = encriptadorMock;
			}
		};

		//Creamos la prueba
		Statement test = new Statement() {
			@Override
			public void evaluate() throws Throwable {
				if(resultadoLogica != null){
					assertEquals(resultadoLogica, gestorVendedor.autenticarVendedor(datos));
				}
				else{
					try{
						gestorVendedor.autenticarVendedor(datos);
						Assert.fail("Debería haber fallado!");
					} catch(PersistenciaException e){
						Assert.assertEquals((excepcion), e);
					} catch(Exception e){
						if(excepcion instanceof PersistenciaException){
							Assert.fail("Debería haber tirado una PersistenciaException y tiro otra Exception!");
						}
					}
				}
			}
		};

		//Ejecutamos la prueba
		try{
			test.evaluate();
		} catch(Throwable e){
			throw new Exception(e);
		}
	}

	protected Object[] parametersForTestAutenticarVendedor() {
		Mockito.when(encriptadorMock.encriptar(Matchers.any(), Matchers.any())).thenReturn("1234");
		Mockito.when(encriptadorMock.generarSal()).thenReturn("1234");

		TipoDocumento tipoDocumento = new TipoDocumento().setTipo(TipoDocumentoStr.DNI);
		String numDoc = "12345678";
		String contrasenia = "pepe";
		String sal = encriptadorMock.generarSal();

		DatosLogin datosCorrectos = new DatosLogin(tipoDocumento, numDoc, contrasenia.toCharArray());
		Vendedor vendedorCorrecto = new Vendedor().setTipoDocumento(tipoDocumento).setNumeroDocumento(numDoc).setPassword(encriptadorMock.encriptar(contrasenia.toCharArray(), sal)).setSalt(sal);

		DatosLogin datosTipoDocumentoVacio = new DatosLogin(null, numDoc, contrasenia.toCharArray());

		DatosLogin datosNumeroDocumentoVacio = new DatosLogin(tipoDocumento, "", contrasenia.toCharArray());

		DatosLogin datosContraseniaVacia = new DatosLogin(null, numDoc, "".toCharArray());

		DatosLogin datosCaracteresRaros = new DatosLogin(tipoDocumento, "ñúïÒ", contrasenia.toCharArray());

		return new Object[] {
				new Object[] { datosCorrectos, new ResultadoAutenticacion(), vendedorCorrecto, null }, //prueba ingreso correcto
				new Object[] { datosTipoDocumentoVacio, new ResultadoAutenticacion(ErrorAutenticacion.Datos_Incorrectos), vendedorCorrecto, null }, //prueba TipoDocumento vacio, ingreso incorrecto
				new Object[] { datosNumeroDocumentoVacio, new ResultadoAutenticacion(ErrorAutenticacion.Datos_Incorrectos), vendedorCorrecto, null }, //prueba numero de documento vacio, ingreso incorrecto
				new Object[] { datosContraseniaVacia, new ResultadoAutenticacion(ErrorAutenticacion.Datos_Incorrectos), vendedorCorrecto, null }, //prueba Contraseña vacia, ingreso incorrecto
				new Object[] { datosCorrectos, new ResultadoAutenticacion(ErrorAutenticacion.Datos_Incorrectos), null, null }, //prueba un ingreso incorrecto
				new Object[] { datosCaracteresRaros, new ResultadoAutenticacion(ErrorAutenticacion.Datos_Incorrectos), null, null }, //prueba un ingreso incorrecto con caracteres UTF8
				new Object[] { datosCorrectos, null, null, new PersistenciaException("Error de persistencia. Test.") }, //Prueba una excepcion de persistencia
				new Object[] { datosCorrectos, null, null, new Exception() } //Prueba una excepcion desconocida
		};
	}
}
