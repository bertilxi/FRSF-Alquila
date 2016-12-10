package app.datos.servicios.implementacion;

import org.hibernate.SessionFactory;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import app.datos.clases.EstadoInmuebleStr;
import app.datos.clases.FiltroInmueble;
import app.datos.clases.TipoInmuebleStr;
import app.datos.entidades.Barrio;
import app.datos.entidades.Localidad;
import app.datos.entidades.Pais;
import app.datos.entidades.Provincia;
import app.datos.servicios.InmuebleService;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
/**
 * Este test prueba los métodos de la clase InmuebleServiceImpl
 */
public class InmuebleServiceImplTest {

	public static final InmuebleService persistidorInmuble;
	public static final ApplicationContext appContext;
	public static final SessionFactory sessionFact;

	static{
		//Ocultar logs
		java.util.Enumeration<String> loggers = java.util.logging.LogManager.getLogManager().getLoggerNames();
		while(loggers.hasMoreElements()){
			String log = loggers.nextElement();
			java.util.logging.Logger.getLogger(log).setLevel(java.util.logging.Level.WARNING);
		}

		System.out.println("Abriendo Base de datos");
		appContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		persistidorInmuble = appContext.getBean(InmuebleService.class);
		sessionFact = (SessionFactory) appContext.getBean("sessionFactory");
		System.out.println("Base de datos abierta");
	}

	@AfterClass
	public static void cerrarBaseDeDatos() {
		sessionFact.close();
	}

	@Test
	@Parameters
	/**
	 * Prueba las querys que genera el filtro que se le pasa a listarInmuebles(FiltroInmueble filtro) para verificar que sean válidas.
	 * Corresponde con la taskcard 21 de la iteración 2 y a la historia 4
	 *
	 * @param filtro
	 *            filtro a probar sus querys
	 * @throws Exception
	 */
	public void testListarInmuebles(FiltroInmueble filtro) throws Exception {
		System.out.println("Inicio test");
		try{
			persistidorInmuble.listarInmuebles(filtro);
		} catch(Exception e){
			e.printStackTrace();
			System.out.println("Fin test");
			Assert.fail("No debería haber fallado");
		}
		System.out.println("Fin test");
	}

	/**
	 * Método que devuelve los parámetros para probar el método listarInmuebles(FiltroInmueble filtro)
	 *
	 * @return parámetros de prueba
	 */
	protected Object[] parametersForTestListarInmuebles() {
		FiltroInmueble filtroCompleto = new FiltroInmueble.Builder()
				.barrio(new Barrio().setNombre(""))
				.cantidadDormitorios(0)
				.estadoInmueble(EstadoInmuebleStr.VENDIDO)
				.localidad(new Localidad().setNombre(""))
				.pais(new Pais().setNombre(""))
				.precioMaximo(100.0)
				.precioMinimo(1.0)
				.provincia(new Provincia().setNombre(""))
				.tipoInmueble(TipoInmuebleStr.CASA)
				.build();
		FiltroInmueble filtroSinEstadoInmueble = new FiltroInmueble.Builder()
				.barrio(new Barrio().setNombre(""))
				.cantidadDormitorios(0)
				.localidad(new Localidad().setNombre(""))
				.pais(new Pais().setNombre(""))
				.precioMaximo(100.0)
				.precioMinimo(1.0)
				.provincia(new Provincia().setNombre(""))
				.tipoInmueble(TipoInmuebleStr.CASA)
				.build();
		FiltroInmueble filtroVacio = new FiltroInmueble.Builder().build();
		//Parámetros de JUnitParams
		return new Object[] {
				new Object[] { filtroCompleto },
				new Object[] { filtroSinEstadoInmueble },
				new Object[] { filtroVacio }
		};
	}
}