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
import app.excepciones.ObjNotFoundException;
import app.excepciones.PersistenciaException;
import app.logica.CoordinadorJavaFX;
import app.logica.resultados.ResultadoCrearInmueble;
import app.logica.resultados.ResultadoCrearInmueble.ErrorCrearInmueble;
import app.logica.resultados.ResultadoModificarInmueble;
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
