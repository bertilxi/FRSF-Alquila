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
import app.datos.entidades.Inmueble;
import app.datos.entidades.Localidad;
import app.datos.entidades.Orientacion;
import app.datos.entidades.Pais;
import app.datos.entidades.Propietario;
import app.datos.entidades.Provincia;
import app.datos.entidades.TipoInmueble;
import app.excepciones.ObjNotFoundException;
import app.excepciones.PersistenciaException;
import app.logica.CoordinadorJavaFX;
import app.logica.resultados.ResultadoCrearInmueble;
import app.logica.resultados.ResultadoCrearInmueble.ErrorCrearInmueble;
import app.logica.resultados.ResultadoModificarInmueble;
import app.logica.resultados.ResultadoModificarInmueble.ErrorModificarInmueble;
import app.ui.componentes.ventanas.PresentadorVentanas;
import app.ui.componentes.ventanas.mock.PresentadorVentanasMock;
import app.ui.controladores.resultado.ResultadoControlador;
import app.ui.controladores.resultado.ResultadoControlador.ErrorControlador;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

/**
 * Este test prueba los métodos de la clase NMVInmuebleController
 */
@RunWith(JUnitParamsRunner.class)
public class NMVInmuebleControllerTest {

	@Test
	@Parameters
	//
	/**
	 * Prueba el método crearInmueble(), el cual corresponde con la taskcard 13 de la iteración 1 y a la historia 3
	 *
	 * @param propietario
	 *            que se usará en el test
	 * @param direccion
	 *            que se usará en el test
	 * @param tipoInmueble
	 *            que se usará en el test
	 * @param precio
	 *            que se usará en el test
	 * @param orientacion
	 *            que se usará en el test
	 * @param frente
	 *            que se usará en el test
	 * @param fondo
	 *            que se usará en el test
	 * @param superficie
	 *            que se usará en el test
	 * @param propiedadHorizontal
	 *            que se usará en el test
	 * @param superficieEdificio
	 *            que se usará en el test
	 * @param antigüedadEdificio
	 *            que se usará en el test
	 * @param dormitorios
	 *            que se usarán en el test
	 * @param baños
	 *            que se usarán en el test
	 * @param garaje
	 *            que se usará en el test
	 * @param patio
	 *            que se usará en el test
	 * @param piscina
	 *            que se usará en el test
	 * @param aguaCorriente
	 *            que se usará en el test
	 * @param cloacas
	 *            que se usarán en el test
	 * @param gasNatural
	 *            que se usará en el test
	 * @param aguaCaliente
	 *            que se usará en el test
	 * @param teléfono
	 *            que se usará en el test
	 * @param lavadero
	 *            que se usará en el test
	 * @param pavimento
	 *            que se usará en el test
	 * @param observaciones
	 *            que se usarán en el test
	 * @param resultadoControlador
	 *            es lo que se espera que devuelva el metodo
	 * @param resultadoLogica
	 *            es lo que el mock de la lógica debe devolver en el test y que el controlador recibe
	 * @param excepcion
	 *            es la excepcion que debe lanzar el mock de la lógica, si la prueba involucra procesar una excepcion de dicha lógica, debe ser nulo resultadoLogica para que se use
	 * @throws Exception
	 */
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
		Pais pais = new Pais();
		Provincia provincia = new Provincia().setPais(pais);
		Localidad localidad = new Localidad().setProvincia(provincia);
		Barrio barrio = new Barrio().setLocalidad(localidad);
		Calle calle = new Calle().setLocalidad(localidad);
		String numero = "1234";
		String piso = "2";
		String departamento = "2";
		String otros = "";
		Direccion direccion = new Direccion().setBarrio(barrio).setCalle(calle).setDepartamento(departamento).setLocalidad(localidad).setNumero(numero).setOtros(otros).setPiso(piso);
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
		String observaciones = "";

		ResultadoControlador resultadoControladorCorrecto = new ResultadoControlador();
		ResultadoCrearInmueble resultadoLogicaCorrecto = new ResultadoCrearInmueble();

		ResultadoControlador resultadoControladorDatosIncorrectos = new ResultadoControlador(ErrorControlador.Datos_Incorrectos);
		ResultadoControlador resultadoControladorCamposVacios = new ResultadoControlador(ErrorControlador.Campos_Vacios);
		ResultadoControlador resultadoControladorEntidadNoEncontrada = new ResultadoControlador(ErrorControlador.Entidad_No_Encontrada);
		ResultadoControlador resultadoControladorDatosIncorrectosYCamposVacios = new ResultadoControlador(ErrorControlador.Campos_Vacios, ErrorControlador.Datos_Incorrectos);
		ResultadoControlador resultadoControladorErrorPersistencia = new ResultadoControlador(ErrorControlador.Error_Persistencia);
		ResultadoControlador resultadoControladorErrorDesconocido = new ResultadoControlador(ErrorControlador.Error_Desconocido);

		ResultadoCrearInmueble resultadoLogicaDatosEdificioIncorrectos = new ResultadoCrearInmueble(ErrorCrearInmueble.Datos_Edificio_Incorrectos);
		ResultadoCrearInmueble resultadoLogicaFechaVacia = new ResultadoCrearInmueble(ErrorCrearInmueble.Fecha_Vacia);
		ResultadoCrearInmueble resultadoLogicaFondoIncorrecto = new ResultadoCrearInmueble(ErrorCrearInmueble.Fondo_Incorrecto);
		ResultadoCrearInmueble resultadoLogicaFormatoDireccionIncorrecto = new ResultadoCrearInmueble(ErrorCrearInmueble.Formato_Direccion_Incorrecto);
		ResultadoCrearInmueble resultadoLogicaFrenteIncorrecto = new ResultadoCrearInmueble(ErrorCrearInmueble.Frente_Incorrecto);
		ResultadoCrearInmueble resultadoLogicaPrecioIncorrecto = new ResultadoCrearInmueble(ErrorCrearInmueble.Precio_Incorrecto);
		ResultadoCrearInmueble resultadoLogicaPrecioVacio = new ResultadoCrearInmueble(ErrorCrearInmueble.Precio_Vacio);
		ResultadoCrearInmueble resultadoLogicaPropietarioInexistente = new ResultadoCrearInmueble(ErrorCrearInmueble.Propietario_Inexistente);
		ResultadoCrearInmueble resultadoLogicaPropietarioVacio = new ResultadoCrearInmueble(ErrorCrearInmueble.Propietario_Vacio);
		ResultadoCrearInmueble resultadoLogicaSuperficieIncorrecta = new ResultadoCrearInmueble(ErrorCrearInmueble.Superficie_Incorrecta);
		ResultadoCrearInmueble resultadoLogicaTipoVacio = new ResultadoCrearInmueble(ErrorCrearInmueble.Tipo_Vacio);
		ResultadoCrearInmueble resultadoLogicaTipoVacioYSuperficieIncorrecta = new ResultadoCrearInmueble(ErrorCrearInmueble.Tipo_Vacio, ErrorCrearInmueble.Superficie_Incorrecta);

		Throwable excepcionPersistencia = new ObjNotFoundException("", new Exception());
		Throwable excepcionInesperada = new Exception();

		return new Object[] {
				new Object[] { propietario, direccion, tipoInmueble, precio, orientacion, frente, fondo, superficie, propiedadHorizontal, superficieEdificio,
						antigüedadEdificio, dormitorios, baños, garaje, patio, piscina, aguaCorriente, cloacas, gasNatural, aguaCaliente,
						teléfono, lavadero, pavimento, observaciones, resultadoControladorCorrecto, resultadoLogicaCorrecto, null }, //test Todo correcto

				new Object[] { propietario, direccion, tipoInmueble, precio, orientacion, frente, fondo, superficie, propiedadHorizontal, superficieEdificio,
						antigüedadEdificio, dormitorios, baños, garaje, patio, piscina, aguaCorriente, cloacas, gasNatural, aguaCaliente,
						teléfono, lavadero, pavimento, observaciones, resultadoControladorDatosIncorrectos, resultadoLogicaDatosEdificioIncorrectos, null }, //test Datos de edificio incorrectos

				new Object[] { propietario, direccion, tipoInmueble, precio, orientacion, frente, fondo, superficie, propiedadHorizontal, superficieEdificio,
						antigüedadEdificio, dormitorios, baños, garaje, patio, piscina, aguaCorriente, cloacas, gasNatural, aguaCaliente,
						teléfono, lavadero, pavimento, observaciones, resultadoControladorCamposVacios, resultadoLogicaFechaVacia, null }, //test fecha vacía

				new Object[] { propietario, direccion, tipoInmueble, precio, orientacion, frente, fondo, superficie, propiedadHorizontal, superficieEdificio,
						antigüedadEdificio, dormitorios, baños, garaje, patio, piscina, aguaCorriente, cloacas, gasNatural, aguaCaliente,
						teléfono, lavadero, pavimento, observaciones, resultadoControladorDatosIncorrectos, resultadoLogicaFondoIncorrecto, null }, //test formato de fondo incorrecto

				new Object[] { propietario, direccion, tipoInmueble, precio, orientacion, frente, fondo, superficie, propiedadHorizontal, superficieEdificio,
						antigüedadEdificio, dormitorios, baños, garaje, patio, piscina, aguaCorriente, cloacas, gasNatural, aguaCaliente,
						teléfono, lavadero, pavimento, observaciones, resultadoControladorDatosIncorrectos, resultadoLogicaFormatoDireccionIncorrecto, null }, //test formato de dirección incorrecto

				new Object[] { propietario, direccion, tipoInmueble, precio, orientacion, frente, fondo, superficie, propiedadHorizontal, superficieEdificio,
						antigüedadEdificio, dormitorios, baños, garaje, patio, piscina, aguaCorriente, cloacas, gasNatural, aguaCaliente,
						teléfono, lavadero, pavimento, observaciones, resultadoControladorDatosIncorrectos, resultadoLogicaFrenteIncorrecto, null }, //test formato de frente incorrecto

				new Object[] { propietario, direccion, tipoInmueble, precio, orientacion, frente, fondo, superficie, propiedadHorizontal, superficieEdificio,
						antigüedadEdificio, dormitorios, baños, garaje, patio, piscina, aguaCorriente, cloacas, gasNatural, aguaCaliente,
						teléfono, lavadero, pavimento, observaciones, resultadoControladorDatosIncorrectos, resultadoLogicaPrecioIncorrecto, null }, //test formato de precio incorrecto

				new Object[] { propietario, direccion, tipoInmueble, precio, orientacion, frente, fondo, superficie, propiedadHorizontal, superficieEdificio,
						antigüedadEdificio, dormitorios, baños, garaje, patio, piscina, aguaCorriente, cloacas, gasNatural, aguaCaliente,
						teléfono, lavadero, pavimento, observaciones, resultadoControladorCamposVacios, resultadoLogicaPrecioVacio, null }, //test precio vacío

				new Object[] { propietario, direccion, tipoInmueble, precio, orientacion, frente, fondo, superficie, propiedadHorizontal, superficieEdificio,
						antigüedadEdificio, dormitorios, baños, garaje, patio, piscina, aguaCorriente, cloacas, gasNatural, aguaCaliente,
						teléfono, lavadero, pavimento, observaciones, resultadoControladorEntidadNoEncontrada, resultadoLogicaPropietarioInexistente, null }, //test Propietario seleccionado inexistente en el sistema

				new Object[] { propietario, direccion, tipoInmueble, precio, orientacion, frente, fondo, superficie, propiedadHorizontal, superficieEdificio,
						antigüedadEdificio, dormitorios, baños, garaje, patio, piscina, aguaCorriente, cloacas, gasNatural, aguaCaliente,
						teléfono, lavadero, pavimento, observaciones, resultadoControladorCamposVacios, resultadoLogicaPropietarioVacio, null }, //test propietario vacío

				new Object[] { propietario, direccion, tipoInmueble, precio, orientacion, frente, fondo, superficie, propiedadHorizontal, superficieEdificio,
						antigüedadEdificio, dormitorios, baños, garaje, patio, piscina, aguaCorriente, cloacas, gasNatural, aguaCaliente,
						teléfono, lavadero, pavimento, observaciones, resultadoControladorDatosIncorrectos, resultadoLogicaSuperficieIncorrecta, null }, //test formato de superficie de inmueble incorrecto

				new Object[] { propietario, direccion, tipoInmueble, precio, orientacion, frente, fondo, superficie, propiedadHorizontal, superficieEdificio,
						antigüedadEdificio, dormitorios, baños, garaje, patio, piscina, aguaCorriente, cloacas, gasNatural, aguaCaliente,
						teléfono, lavadero, pavimento, observaciones, resultadoControladorCamposVacios, resultadoLogicaTipoVacio, null }, //test tipo de inmueble vacío

				new Object[] { propietario, direccion, tipoInmueble, precio, orientacion, frente, fondo, superficie, propiedadHorizontal, superficieEdificio,
						antigüedadEdificio, dormitorios, baños, garaje, patio, piscina, aguaCorriente, cloacas, gasNatural, aguaCaliente,
						teléfono, lavadero, pavimento, observaciones, resultadoControladorDatosIncorrectosYCamposVacios, resultadoLogicaTipoVacioYSuperficieIncorrecta, null }, //test tipo de inmueble vacío y formato de superficie de inmueble incorrecto

				new Object[] { propietario, direccion, tipoInmueble, precio, orientacion, frente, fondo, superficie, propiedadHorizontal, superficieEdificio,
						antigüedadEdificio, dormitorios, baños, garaje, patio, piscina, aguaCorriente, cloacas, gasNatural, aguaCaliente,
						teléfono, lavadero, pavimento, observaciones, resultadoControladorErrorPersistencia, null, excepcionPersistencia }, //test excepción de persistencia

				new Object[] { propietario, direccion, tipoInmueble, precio, orientacion, frente, fondo, superficie, propiedadHorizontal, superficieEdificio,
						antigüedadEdificio, dormitorios, baños, garaje, patio, piscina, aguaCorriente, cloacas, gasNatural, aguaCaliente,
						teléfono, lavadero, pavimento, observaciones, resultadoControladorErrorDesconocido, null, excepcionInesperada } //test escepción inesperada
		};
	}

	@Test
	@Parameters
	/**
	 * Prueba el método modificarInmueble(), el cual corresponde con la taskcard 13 de la iteración 1 y a la historia 3
	 *
	 * @param propietario
	 *            que se usará en el test
	 * @param direccion
	 *            que se usará en el test
	 * @param tipoInmueble
	 *            que se usará en el test
	 * @param precio
	 *            que se usará en el test
	 * @param orientacion
	 *            que se usará en el test
	 * @param frente
	 *            que se usará en el test
	 * @param fondo
	 *            que se usará en el test
	 * @param superficie
	 *            que se usará en el test
	 * @param propiedadHorizontal
	 *            que se usará en el test
	 * @param superficieEdificio
	 *            que se usará en el test
	 * @param antigüedadEdificio
	 *            que se usará en el test
	 * @param dormitorios
	 *            que se usarán en el test
	 * @param baños
	 *            que se usarán en el test
	 * @param garaje
	 *            que se usará en el test
	 * @param patio
	 *            que se usará en el test
	 * @param piscina
	 *            que se usará en el test
	 * @param aguaCorriente
	 *            que se usará en el test
	 * @param cloacas
	 *            que se usarán en el test
	 * @param gasNatural
	 *            que se usará en el test
	 * @param aguaCaliente
	 *            que se usará en el test
	 * @param teléfono
	 *            que se usará en el test
	 * @param lavadero
	 *            que se usará en el test
	 * @param pavimento
	 *            que se usará en el test
	 * @param observaciones
	 *            que se usarán en el test
	 * @param resultadoControlador
	 *            es lo que se espera que devuelva el metodo
	 * @param resultadoLogica
	 *            es lo que el mock de la lógica debe devolver en el test y que el controlador recibe
	 * @param excepcion
	 *            es la excepcion que debe lanzar el mock de la lógica, si la prueba involucra procesar una excepcion de dicha lógica, debe ser nulo resultadoLogica para que se use
	 * @throws Exception
	 */
	public void testModificarInmueble(Propietario propietario,
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
			String observaciones,
			ResultadoControlador resultadoControlador,
			ResultadoModificarInmueble resultadoLogica,
			Throwable excepcion) throws Exception {

		CoordinadorJavaFX coordinadorMock = new CoordinadorJavaFX() {
			@Override
			public ResultadoModificarInmueble modificarInmueble(Inmueble inmueble) throws PersistenciaException {
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
			{
				this.inmueble = new Inmueble();
			}

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
	 * Método que devuelve los parámetros para probar el método modificarInmueble()
	 *
	 * @return parámetros de prueba
	 */
	protected Object[] parametersForTestModificarInmueble() {
		Propietario propietario = new Propietario();
		Pais pais = new Pais();
		Provincia provincia = new Provincia().setPais(pais);
		Localidad localidad = new Localidad().setProvincia(provincia);
		Barrio barrio = new Barrio().setLocalidad(localidad);
		Calle calle = new Calle().setLocalidad(localidad);
		String numero = "1234";
		String piso = "2";
		String departamento = "2";
		String otros = "";
		Direccion direccion = new Direccion().setBarrio(barrio).setCalle(calle).setDepartamento(departamento).setLocalidad(localidad).setNumero(numero).setOtros(otros).setPiso(piso);
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
		String observaciones = "";

		ResultadoControlador resultadoControladorCorrecto = new ResultadoControlador();
		ResultadoControlador resultadoControladorDatosIncorrectos = new ResultadoControlador(ErrorControlador.Datos_Incorrectos);
		ResultadoControlador resultadoControladorCamposVacios = new ResultadoControlador(ErrorControlador.Campos_Vacios);
		ResultadoControlador resultadoControladorEntidadNoEncontrada = new ResultadoControlador(ErrorControlador.Entidad_No_Encontrada);
		ResultadoControlador resultadoControladorDatosIncorrectosYCamposVacios = new ResultadoControlador(ErrorControlador.Campos_Vacios, ErrorControlador.Datos_Incorrectos);
		ResultadoControlador resultadoControladorErrorPersistencia = new ResultadoControlador(ErrorControlador.Error_Persistencia);
		ResultadoControlador resultadoControladorErrorDesconocido = new ResultadoControlador(ErrorControlador.Error_Desconocido);

		ResultadoModificarInmueble resultadoLogicaCorrecto = new ResultadoModificarInmueble();
		ResultadoModificarInmueble resultadoLogicaDatosEdificioIncorrectos = new ResultadoModificarInmueble(ErrorModificarInmueble.Datos_Edificio_Incorrectos);
		ResultadoModificarInmueble resultadoLogicaFechaVacia = new ResultadoModificarInmueble(ErrorModificarInmueble.Fecha_Vacia);
		ResultadoModificarInmueble resultadoLogicaFondoIncorrecto = new ResultadoModificarInmueble(ErrorModificarInmueble.Fondo_Incorrecto);
		ResultadoModificarInmueble resultadoLogicaFormatoDireccionIncorrecto = new ResultadoModificarInmueble(ErrorModificarInmueble.Formato_Direccion_Incorrecto);
		ResultadoModificarInmueble resultadoLogicaFrenteIncorrecto = new ResultadoModificarInmueble(ErrorModificarInmueble.Frente_Incorrecto);
		ResultadoModificarInmueble resultadoLogicaPrecioIncorrecto = new ResultadoModificarInmueble(ErrorModificarInmueble.Precio_Incorrecto);
		ResultadoModificarInmueble resultadoLogicaPrecioVacio = new ResultadoModificarInmueble(ErrorModificarInmueble.Precio_Vacio);
		ResultadoModificarInmueble resultadoLogicaPropietarioInexistente = new ResultadoModificarInmueble(ErrorModificarInmueble.Propietario_Inexistente);
		ResultadoModificarInmueble resultadoLogicaPropietarioVacio = new ResultadoModificarInmueble(ErrorModificarInmueble.Propietario_Vacio);
		ResultadoModificarInmueble resultadoLogicaSuperficieIncorrecta = new ResultadoModificarInmueble(ErrorModificarInmueble.Superficie_Incorrecta);
		ResultadoModificarInmueble resultadoLogicaTipoVacio = new ResultadoModificarInmueble(ErrorModificarInmueble.Tipo_Vacio);
		ResultadoModificarInmueble resultadoLogicaInmuebleInexistente = new ResultadoModificarInmueble(ErrorModificarInmueble.Inmueble_Inexistente);
		ResultadoModificarInmueble resultadoLogicaTipoVacioYSuperficieIncorrecta = new ResultadoModificarInmueble(ErrorModificarInmueble.Tipo_Vacio, ErrorModificarInmueble.Superficie_Incorrecta);

		Throwable excepcionPersistencia = new ObjNotFoundException("", new Exception());
		Throwable excepcionInesperada = new Exception();

		return new Object[] {
				new Object[] { propietario, direccion, tipoInmueble, precio, orientacion, frente, fondo, superficie, propiedadHorizontal, superficieEdificio,
						antigüedadEdificio, dormitorios, baños, garaje, patio, piscina, aguaCorriente, cloacas, gasNatural, aguaCaliente,
						teléfono, lavadero, pavimento, observaciones, resultadoControladorCorrecto, resultadoLogicaCorrecto, null }, //test Todo correcto

				new Object[] { propietario, direccion, tipoInmueble, precio, orientacion, frente, fondo, superficie, propiedadHorizontal, superficieEdificio,
						antigüedadEdificio, dormitorios, baños, garaje, patio, piscina, aguaCorriente, cloacas, gasNatural, aguaCaliente,
						teléfono, lavadero, pavimento, observaciones, resultadoControladorDatosIncorrectos, resultadoLogicaDatosEdificioIncorrectos, null }, //test Datos de edificio incorrectos

				new Object[] { propietario, direccion, tipoInmueble, precio, orientacion, frente, fondo, superficie, propiedadHorizontal, superficieEdificio,
						antigüedadEdificio, dormitorios, baños, garaje, patio, piscina, aguaCorriente, cloacas, gasNatural, aguaCaliente,
						teléfono, lavadero, pavimento, observaciones, resultadoControladorCamposVacios, resultadoLogicaFechaVacia, null }, //test fecha vacía

				new Object[] { propietario, direccion, tipoInmueble, precio, orientacion, frente, fondo, superficie, propiedadHorizontal, superficieEdificio,
						antigüedadEdificio, dormitorios, baños, garaje, patio, piscina, aguaCorriente, cloacas, gasNatural, aguaCaliente,
						teléfono, lavadero, pavimento, observaciones, resultadoControladorDatosIncorrectos, resultadoLogicaFondoIncorrecto, null }, //test formato de fondo incorrecto

				new Object[] { propietario, direccion, tipoInmueble, precio, orientacion, frente, fondo, superficie, propiedadHorizontal, superficieEdificio,
						antigüedadEdificio, dormitorios, baños, garaje, patio, piscina, aguaCorriente, cloacas, gasNatural, aguaCaliente,
						teléfono, lavadero, pavimento, observaciones, resultadoControladorDatosIncorrectos, resultadoLogicaFormatoDireccionIncorrecto, null }, //test formato de dirección incorrecto

				new Object[] { propietario, direccion, tipoInmueble, precio, orientacion, frente, fondo, superficie, propiedadHorizontal, superficieEdificio,
						antigüedadEdificio, dormitorios, baños, garaje, patio, piscina, aguaCorriente, cloacas, gasNatural, aguaCaliente,
						teléfono, lavadero, pavimento, observaciones, resultadoControladorDatosIncorrectos, resultadoLogicaFrenteIncorrecto, null }, //test formato de frente incorrecto

				new Object[] { propietario, direccion, tipoInmueble, precio, orientacion, frente, fondo, superficie, propiedadHorizontal, superficieEdificio,
						antigüedadEdificio, dormitorios, baños, garaje, patio, piscina, aguaCorriente, cloacas, gasNatural, aguaCaliente,
						teléfono, lavadero, pavimento, observaciones, resultadoControladorDatosIncorrectos, resultadoLogicaPrecioIncorrecto, null }, //test formato de precio incorrecto

				new Object[] { propietario, direccion, tipoInmueble, precio, orientacion, frente, fondo, superficie, propiedadHorizontal, superficieEdificio,
						antigüedadEdificio, dormitorios, baños, garaje, patio, piscina, aguaCorriente, cloacas, gasNatural, aguaCaliente,
						teléfono, lavadero, pavimento, observaciones, resultadoControladorCamposVacios, resultadoLogicaPrecioVacio, null }, //test precio vacío

				new Object[] { propietario, direccion, tipoInmueble, precio, orientacion, frente, fondo, superficie, propiedadHorizontal, superficieEdificio,
						antigüedadEdificio, dormitorios, baños, garaje, patio, piscina, aguaCorriente, cloacas, gasNatural, aguaCaliente,
						teléfono, lavadero, pavimento, observaciones, resultadoControladorEntidadNoEncontrada, resultadoLogicaPropietarioInexistente, null }, //test Propietario seleccionado inexistente en el sistema

				new Object[] { propietario, direccion, tipoInmueble, precio, orientacion, frente, fondo, superficie, propiedadHorizontal, superficieEdificio,
						antigüedadEdificio, dormitorios, baños, garaje, patio, piscina, aguaCorriente, cloacas, gasNatural, aguaCaliente,
						teléfono, lavadero, pavimento, observaciones, resultadoControladorCamposVacios, resultadoLogicaPropietarioVacio, null }, //test propietario vacío

				new Object[] { propietario, direccion, tipoInmueble, precio, orientacion, frente, fondo, superficie, propiedadHorizontal, superficieEdificio,
						antigüedadEdificio, dormitorios, baños, garaje, patio, piscina, aguaCorriente, cloacas, gasNatural, aguaCaliente,
						teléfono, lavadero, pavimento, observaciones, resultadoControladorDatosIncorrectos, resultadoLogicaSuperficieIncorrecta, null }, //test formato de superficie de inmueble incorrecto

				new Object[] { propietario, direccion, tipoInmueble, precio, orientacion, frente, fondo, superficie, propiedadHorizontal, superficieEdificio,
						antigüedadEdificio, dormitorios, baños, garaje, patio, piscina, aguaCorriente, cloacas, gasNatural, aguaCaliente,
						teléfono, lavadero, pavimento, observaciones, resultadoControladorCamposVacios, resultadoLogicaTipoVacio, null }, //test tipo de inmueble vacío

				new Object[] { propietario, direccion, tipoInmueble, precio, orientacion, frente, fondo, superficie, propiedadHorizontal, superficieEdificio,
						antigüedadEdificio, dormitorios, baños, garaje, patio, piscina, aguaCorriente, cloacas, gasNatural, aguaCaliente,
						teléfono, lavadero, pavimento, observaciones, resultadoControladorEntidadNoEncontrada, resultadoLogicaInmuebleInexistente, null }, //test inmueble eleccionado inexistente en el sistema

				new Object[] { propietario, direccion, tipoInmueble, precio, orientacion, frente, fondo, superficie, propiedadHorizontal, superficieEdificio,
						antigüedadEdificio, dormitorios, baños, garaje, patio, piscina, aguaCorriente, cloacas, gasNatural, aguaCaliente,
						teléfono, lavadero, pavimento, observaciones, resultadoControladorDatosIncorrectosYCamposVacios, resultadoLogicaTipoVacioYSuperficieIncorrecta, null }, //test tipo de inmueble vacío y formato de superficie de inmueble incorrecto

				new Object[] { propietario, direccion, tipoInmueble, precio, orientacion, frente, fondo, superficie, propiedadHorizontal, superficieEdificio,
						antigüedadEdificio, dormitorios, baños, garaje, patio, piscina, aguaCorriente, cloacas, gasNatural, aguaCaliente,
						teléfono, lavadero, pavimento, observaciones, resultadoControladorErrorPersistencia, null, excepcionPersistencia }, //test excepción de persistencia

				new Object[] { propietario, direccion, tipoInmueble, precio, orientacion, frente, fondo, superficie, propiedadHorizontal, superficieEdificio,
						antigüedadEdificio, dormitorios, baños, garaje, patio, piscina, aguaCorriente, cloacas, gasNatural, aguaCaliente,
						teléfono, lavadero, pavimento, observaciones, resultadoControladorErrorDesconocido, null, excepcionInesperada } //test escepción inesperada
		};
	}
}
