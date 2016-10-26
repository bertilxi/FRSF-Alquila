package app.logica.gestores;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;
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
import app.ui.controladores.resultado.ResultadoControlador;
import app.ui.controladores.resultado.ResultadoControlador.ErrorControlador;
import junitparams.Parameters;

public class GestorVendedorTest {

	@Test
	@Parameters
	public void testIngresar(TipoDocumento tipoDocumento, String dni, String contra, ResultadoAutenticacion resultadoLogica, Vendedor vendedor, Throwable excepcion) throws Exception {
		VendedorService vendedorServiceMock = new VendedorService() {
			@Override
			public void guardarVendedor(Vendedor vendedor) throws PersistenciaException {

			}

			@Override
			public void modificarVendedor(Vendedor vendedor) throws PersistenciaException {
				// TODO Auto-generated method stub

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

		DatosLogin datos = new DatosLogin(tipoDocumento, dni, contra.toCharArray());

		Statement test = new Statement() {
			@Override
			public void evaluate() throws Throwable {
				if(excepcion != null){
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

	protected Object[] parametersForTestIngresar() {
		return new Object[] {
				new Object[] { (new TipoDocumento()).setTipo(TipoDocumentoStr.DNI), "12345678", "pepe", new ResultadoControlador(), new ResultadoAutenticacion(), null }, //prueba ingreso correcto
				new Object[] { null, "", "pepe", new ResultadoControlador(ErrorControlador.Campos_Vacios), new ResultadoAutenticacion(ErrorAutenticacion.Datos_Incorrectos), null }, //prueba TipoDocumento vacio
				new Object[] { (new TipoDocumento()).setTipo(TipoDocumentoStr.LC), "", "pepe", new ResultadoControlador(ErrorControlador.Campos_Vacios), new ResultadoAutenticacion(ErrorAutenticacion.Datos_Incorrectos), null }, //prueba numero de documento vacio
				new Object[] { (new TipoDocumento()).setTipo(TipoDocumentoStr.LE), "12345678", "", new ResultadoControlador(ErrorControlador.Campos_Vacios), new ResultadoAutenticacion(ErrorAutenticacion.Datos_Incorrectos), null }, //prueba Contraseña vacia
				new Object[] { (new TipoDocumento()).setTipo(TipoDocumentoStr.CedulaExtranjera), "12345678", "pepe", new ResultadoControlador(ErrorControlador.Datos_Incorrectos), new ResultadoAutenticacion(ErrorAutenticacion.Datos_Incorrectos), null }, //prueba un ingreso incorrecto
				new Object[] { (new TipoDocumento()).setTipo(TipoDocumentoStr.Pasaporte), "ñú", "pepe", new ResultadoControlador(ErrorControlador.Datos_Incorrectos), new ResultadoAutenticacion(ErrorAutenticacion.Datos_Incorrectos), null }, //prueba un ingreso incorrecto con caracteres UTF8
				new Object[] { (new TipoDocumento()).setTipo(TipoDocumentoStr.LC), "ñú", "pepe", new ResultadoControlador(ErrorControlador.Error_Persistencia), null, new PersistenciaException("Error de persistencia. Test.") }, //Prueba una excepcion de persistencia
				new Object[] { (new TipoDocumento()).setTipo(TipoDocumentoStr.DNI), "ñú", "pepe", new ResultadoControlador(ErrorControlador.Error_Desconocido), null, new Exception() } //Prueba una excepcion desconocida
		};
	}
}
