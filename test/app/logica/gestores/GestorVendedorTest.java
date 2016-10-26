package app.logica.gestores;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;

import app.datos.clases.DatosLogin;
import app.datos.clases.FiltroVendedor;
import app.datos.clases.TipoDocumentoStr;
import app.datos.entidades.TipoDocumento;
import app.datos.entidades.Vendedor;
import app.datos.servicios.VendedorService;
import app.excepciones.PersistenciaException;
import app.logica.resultados.ResultadoAutenticacion;
import app.logica.resultados.ResultadoAutenticacion.ErrorAutenticacion;
import app.ui.Contra;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class GestorVendedorTest {

	@Test
	@Parameters
	public void testAutenticarVendedor(TipoDocumento tipoDocumento, String numDoc, String contra, ResultadoAutenticacion resultadoLogica, Throwable excepcion) throws Exception {
		String sal = Contra.generarSal();
		Vendedor vendedor = new Vendedor().setTipoDocumento(tipoDocumento).setNumeroDocumento(numDoc).setPassword(Contra.encriptarMD5(contra.toCharArray(), sal)).setSalt(sal);
		VendedorService vendedorServiceMock = new VendedorService() {
			@Override
			public void guardarVendedor(Vendedor vendedor) throws PersistenciaException {
			}

			@Override
			public void modificarVendedor(Vendedor vendedor) throws PersistenciaException {
			}

			@Override
			public Vendedor obtenerVendedor(FiltroVendedor filtro) throws PersistenciaException {
				if(vendedor != null){
					return vendedor;
				}
				if(excepcion instanceof PersistenciaException){
					throw (PersistenciaException) excepcion;
				}
				new Integer("asd");
				return null;
			}
		};

		GestorVendedor gestorVendedor = new GestorVendedor() {
			{
				this.persistidorVendedor = vendedorServiceMock;
			}
		};

		DatosLogin datos = new DatosLogin(tipoDocumento, numDoc, contra.toCharArray());

		Statement test = new Statement() {
			@Override
			public void evaluate() throws Throwable {
				System.out.println(excepcion);
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

		try{
			test.evaluate();
		} catch(Throwable e){
			throw new Exception(e);
		}
	}

	protected Object[] parametersForTestAutenticarVendedor() {
		return new Object[] {
				new Object[] { (new TipoDocumento()).setTipo(TipoDocumentoStr.DNI), "12345678", "pepe", new ResultadoAutenticacion(), null }, //prueba ingreso correcto
				new Object[] { null, "", "pepe", new ResultadoAutenticacion(ErrorAutenticacion.Datos_Incorrectos), null }, //prueba TipoDocumento vacio, ingreso incorrecto
				new Object[] { (new TipoDocumento()).setTipo(TipoDocumentoStr.LC), "", "pepe", new ResultadoAutenticacion(ErrorAutenticacion.Datos_Incorrectos), null }, //prueba numero de documento vacio, ingreso incorrecto
				new Object[] { (new TipoDocumento()).setTipo(TipoDocumentoStr.LE), "12345678", "", new ResultadoAutenticacion(ErrorAutenticacion.Datos_Incorrectos), null }, //prueba Contraseña vacia, ingreso incorrecto
				new Object[] { (new TipoDocumento()).setTipo(TipoDocumentoStr.CedulaExtranjera), "12345678", "pepe", new ResultadoAutenticacion(ErrorAutenticacion.Datos_Incorrectos), null }, //prueba un ingreso incorrecto
				new Object[] { (new TipoDocumento()).setTipo(TipoDocumentoStr.Pasaporte), "ñú", "pepe", new ResultadoAutenticacion(ErrorAutenticacion.Datos_Incorrectos), null }, //prueba un ingreso incorrecto con caracteres UTF8
				new Object[] { (new TipoDocumento()).setTipo(TipoDocumentoStr.LC), "ñú", "pepe", null, new PersistenciaException("Error de persistencia. Test.") }, //Prueba una excepcion de persistencia
				new Object[] { (new TipoDocumento()).setTipo(TipoDocumentoStr.DNI), "ñú", "pepe", null, new Exception() } //Prueba una excepcion desconocida
		};
	}
}
