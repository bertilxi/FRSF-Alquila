package app.ui.controladores;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;

import app.datos.clases.OrientacionStr;
import app.datos.clases.TipoInmuebleStr;
import app.datos.entidades.Barrio;
import app.datos.entidades.Calle;
import app.datos.entidades.Direccion;
import app.datos.entidades.Imagen;
import app.datos.entidades.Inmueble;
import app.datos.entidades.Localidad;
import app.datos.entidades.Orientacion;
import app.datos.entidades.Pais;
import app.datos.entidades.Propietario;
import app.datos.entidades.Provincia;
import app.datos.entidades.TipoInmueble;
import app.excepciones.PersistenciaException;
import app.logica.CoordinadorJavaFX;
import app.logica.resultados.ResultadoCrearInmueble;
import app.logica.resultados.ResultadoModificarInmueble;
import app.ui.componentes.ventanas.PresentadorVentanas;
import app.ui.componentes.ventanas.mock.PresentadorVentanasMock;
import app.ui.controladores.resultado.ResultadoControlador;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

/**
 * Este test prueba los métodos de la clase NMVInmuebleController
 */
@RunWith(JUnitParamsRunner.class)
public class NMVInmuebleControllerTest {

	@Test
	@Parameters
	//Pertenece a la taskcard 13 de la iteración 1 y a la historia 3
	public void testCrearInmueble(Propietario propietario,
			Direccion direccion,
			TipoInmueble tipoInmueble,
			Double precio,
			Orientacion orientacion,
			Double frente,
			Double fondo,
			Double superficie,
			Boolean propiedadHorizontal,
			Double superficieEdificio,
			Integer antigüedadEdificio,
			Integer dormitorios,
			Integer baños,
			Boolean garaje,
			Boolean patio,
			Boolean piscina,
			Boolean aguaCorriente,
			Boolean cloacas,
			Boolean gasNatural,
			Boolean aguaCaliente,
			Boolean teléfono,
			Boolean lavadero,
			Boolean pavimento,
			ArrayList<Imagen> fotos,
			String observaciones,
			ResultadoControlador resultadoControlador,
			ResultadoCrearInmueble resultadoLogica,
			Throwable excepcion) throws Exception {
		CoordinadorJavaFX coordinadorMock = new CoordinadorJavaFX() {
			@Override
			public ResultadoCrearInmueble crearInmueble(Inmueble inmueble) throws PersistenciaException {
				if(resultadoLogica != null){
					return resultadoLogica;
				}
				if(excepcion instanceof PersistenciaException){
					throw (PersistenciaException) excepcion;
				}
				new Integer("asd");
				return null;
			}

			@Override
			public ArrayList<Pais> obtenerPaises() throws PersistenciaException {
				ArrayList<Pais> objetos = new ArrayList<>();
				objetos.add(direccion.getLocalidad().getProvincia().getPais());
				return objetos;
			}

			@Override
			public ArrayList<Provincia> obtenerProvinciasDe(Pais pais) throws PersistenciaException {
				ArrayList<Provincia> objetos = new ArrayList<>();
				objetos.add(direccion.getLocalidad().getProvincia());
				return objetos;
			}

			@Override
			public ArrayList<Localidad> obtenerLocalidadesDe(Provincia prov) throws PersistenciaException {
				ArrayList<Localidad> objetos = new ArrayList<>();
				objetos.add(direccion.getLocalidad());
				return objetos;
			}

			@Override
			public ArrayList<Calle> obtenerCallesDe(Localidad localidad) throws PersistenciaException {
				ArrayList<Calle> objetos = new ArrayList<>();
				objetos.add(direccion.getCalle());
				return objetos;
			}

			@Override
			public ArrayList<Barrio> obtenerBarriosDe(Localidad localidad) throws PersistenciaException {
				ArrayList<Barrio> objetos = new ArrayList<>();
				objetos.add(direccion.getBarrio());
				return objetos;
			}

			@Override
			public ArrayList<Propietario> obtenerPropietarios() throws PersistenciaException {
				ArrayList<Propietario> objetos = new ArrayList<>();
				objetos.add(propietario);
				return objetos;
			}

			@Override
			public ArrayList<TipoInmueble> obtenerTiposInmueble() throws PersistenciaException {
				ArrayList<TipoInmueble> objetos = new ArrayList<>();
				objetos.add(tipoInmueble);
				return objetos;
			}

			@Override
			public ArrayList<Orientacion> obtenerOrientaciones() throws PersistenciaException {
				ArrayList<Orientacion> objetos = new ArrayList<>();
				objetos.add(orientacion);
				return objetos;
			}
		};
		PresentadorVentanas presentadorMock = new PresentadorVentanasMock();

		NMVInmuebleController nMVInmuebleController = new NMVInmuebleController() {
			@Override
			public ResultadoControlador aceptar() {
				cbAguaCaliente.setSelected(aguaCaliente);
				cbAguaCorriente.setSelected(aguaCorriente);
				cbCloaca.setSelected(cloacas);
				cbGarage.setSelected(garaje);
				cbGasNatural.setSelected(gasNatural);
				cbLavadero.setSelected(lavadero);
				cbPatio.setSelected(patio);
				cbPavimento.setSelected(pavimento);
				cbPiscina.setSelected(piscina);
				cbPropiedadHorizontal.setSelected(propiedadHorizontal);
				cbTelefono.setSelected(teléfono);

				cbPais.getSelectionModel().select(null);
				cbPais.getSelectionModel().select(direccion.getLocalidad().getProvincia().getPais());
				cbProvincia.getSelectionModel().select(direccion.getLocalidad().getProvincia());
				cbLocalidad.getSelectionModel().select(direccion.getLocalidad());
				cbBarrio.getSelectionModel().select(direccion.getBarrio());
				cbCalle.getSelectionModel().select(direccion.getCalle());
				cbOrientacion.getSelectionModel().select(orientacion);
				cbPropietario.getSelectionModel().select(propietario);
				cbTipoInmueble.getSelectionModel().select(tipoInmueble);

				taObservaciones.setText(observaciones);

				tfAltura.setText(direccion.getNumero());
				tfAntiguedad.setText(antigüedadEdificio.toString());
				tfBaños.setText(baños.toString());
				tfCodigo.setText(new Integer(1).toString());
				tfDepartamento.setText(direccion.getDepartamento());
				tfDormitorios.setText(dormitorios.toString());
				tfFechaCarga.setText(new Date().toString());
				tfFondo.setText(fondo.toString());
				tfFrente.setText(frente.toString());
				tfOtros.setText(direccion.getOtros());
				tfPiso.setText(direccion.getPiso());
				tfPrecioVenta.setText(precio.toString());
				tfSuperficie.setText(superficie.toString());
				tfSuperficieEdificio.setText(superficieEdificio.toString());

				return super.aceptar();
			}

			@Override
			public void salir() {

			}
		};
		nMVInmuebleController.setCoordinador(coordinadorMock);
		nMVInmuebleController.setPresentador(presentadorMock);

		ControladorTest corredorTestEnJavaFXThread = new ControladorTest(NMVInmuebleController.URLVista, nMVInmuebleController);

		nMVInmuebleController.setStage(corredorTestEnJavaFXThread.getStagePrueba());

		Statement test = new Statement() {
			@Override
			public void evaluate() throws Throwable {
				assertEquals(resultadoControlador, nMVInmuebleController.aceptar());
			}
		};

		try{
			corredorTestEnJavaFXThread.apply(test, null).evaluate();
		} catch(Throwable e){
			throw new Exception(e);
		}
	}

	/**
	 * Método que devuelve los parámetros para probar el método crearInmueble()
	 *
	 * @return parámetros de prueba
	 */
	protected Object[] parametersForTestCrearInmueble() {
		Propietario propietario = new Propietario();
		Provincia provincia = new Provincia();
		Localidad localidad = new Localidad();
		Barrio barrio = new Barrio();
		Calle calle = new Calle();
		String numero = "1234";
		String piso = "2";
		String departamento = "2";
		String otros = "";
		TipoInmueble tipoInmueble = new TipoInmueble(TipoInmuebleStr.DEPARTAMENTO);
		Double precio = 2.5;
		Orientacion orientacion = new Orientacion(OrientacionStr.NORTE);
		Double frente = 3.0;
		Double fondo = 2.0;
		Double superficie = 6.0;
		Boolean propiedadHorizontal = true;
		Double superficieEdificio = 6.0;
		Integer antigüedadEdificio = 10;
		Integer dormitorios = 3;
		Integer baños = 5;
		Boolean garaje = true;
		Boolean patio = false;
		Boolean piscina = false;
		Boolean aguaCorriente = true;
		Boolean cloacas = true;
		Boolean gasNatural = true;
		Boolean aguaCaliente = true;
		Boolean teléfono = true;
		Boolean lavadero = true;
		Boolean pavimento = false;
		ArrayList<Imagen> fotos = new ArrayList<>();
		String observaciones = "";
		ResultadoControlador resultadoControlador = new ResultadoControlador();
		ResultadoCrearInmueble resultadoLogica = new ResultadoCrearInmueble();
		Throwable excepcion = new Exception();

		return new Object[] {
				new Object[] { propietario, provincia, localidad, barrio, calle, numero, piso, departamento, otros, tipoInmueble, precio, orientacion, frente, fondo, superficie,
						propiedadHorizontal, superficieEdificio, antigüedadEdificio, dormitorios, baños, garaje, patio, piscina, aguaCorriente, cloacas, gasNatural, aguaCaliente,
						teléfono, lavadero, pavimento, fotos, observaciones, resultadoControlador, resultadoLogica, excepcion }
		};
	}

	@Test
	@Parameters
	//Pertenece a la taskcard 13 de la iteración 1 y a la historia 3
	public void testModificarInmueble(Propietario propietario,
			Provincia provincia,
			Localidad localidad,
			Barrio barrio,
			Calle calle,
			String numero,
			String piso,
			String departamento,
			String otros,
			TipoInmueble tipoInmueble,
			Double precio,
			Orientacion orientacion,
			Double frente,
			Double fondo,
			Double superficie,
			Boolean propiedadHorizontal,
			Double superficieEdificio,
			Integer antigüedadEdificio,
			Integer dormitorios,
			Integer baños,
			Boolean garaje,
			Boolean patio,
			Boolean piscina,
			Boolean aguaCorriente,
			Boolean cloacas,
			Boolean gasNatural,
			Boolean aguaCaliente,
			Boolean teléfono,
			Boolean lavadero,
			Boolean pavimento,
			ArrayList<Imagen> fotos,
			String observaciones,
			ResultadoControlador resultadoControlador,
			ResultadoModificarInmueble resultadoLogica,
			Throwable excepcion) throws Exception {

	}

	/**
	 * Método que devuelve los parámetros para probar el método modificarInmueble()
	 *
	 * @return parámetros de prueba
	 */
	protected Object[] parametersForTestModificarInmueble() {
		Propietario propietario = new Propietario();
		Provincia provincia = new Provincia();
		Localidad localidad = new Localidad();
		Barrio barrio = new Barrio();
		Calle calle = new Calle();
		String numero = "1234";
		String piso = "2";
		String departamento = "2";
		String otros = "";
		TipoInmueble tipoInmueble = new TipoInmueble(TipoInmuebleStr.DEPARTAMENTO);
		Double precio = 2.5;
		Orientacion orientacion = new Orientacion(OrientacionStr.NORTE);
		Double frente = 3.0;
		Double fondo = 2.0;
		Double superficie = 6.0;
		Boolean propiedadHorizontal = true;
		Double superficieEdificio = 6.0;
		Integer antigüedadEdificio = 10;
		Integer dormitorios = 3;
		Integer baños = 5;
		Boolean garaje = true;
		Boolean patio = false;
		Boolean piscina = false;
		Boolean aguaCorriente = true;
		Boolean cloacas = true;
		Boolean gasNatural = true;
		Boolean aguaCaliente = true;
		Boolean teléfono = true;
		Boolean lavadero = true;
		Boolean pavimento = false;
		ArrayList<Imagen> fotos = new ArrayList<>();
		String observaciones = "";
		ResultadoControlador resultadoControlador = new ResultadoControlador();
		ResultadoModificarInmueble resultadoLogica = new ResultadoModificarInmueble();
		Throwable excepcion = new Exception();

		return new Object[] {
				new Object[] { propietario, provincia, localidad, barrio, calle, numero, piso, departamento, otros, tipoInmueble, precio, orientacion, frente, fondo, superficie,
						propiedadHorizontal, superficieEdificio, antigüedadEdificio, dormitorios, baños, garaje, patio, piscina, aguaCorriente, cloacas, gasNatural, aguaCaliente,
						teléfono, lavadero, pavimento, fotos, observaciones, resultadoControlador, resultadoLogica, excepcion }
		};
	}
}
