package app.logica.gestores;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;
import org.mockito.Mockito;

import app.comun.ValidadorFormato;
import app.comun.Mock.ValidadorFormatoMock;
import app.datos.clases.FiltroInmueble;
import app.datos.clases.FiltroPropietario;
import app.datos.clases.TipoDocumentoStr;
import app.datos.entidades.Barrio;
import app.datos.entidades.Calle;
import app.datos.entidades.DatosEdificio;
import app.datos.entidades.Direccion;
import app.datos.entidades.Estado;
import app.datos.entidades.Inmueble;
import app.datos.entidades.Localidad;
import app.datos.entidades.Pais;
import app.datos.entidades.Propietario;
import app.datos.entidades.Provincia;
import app.datos.entidades.TipoDocumento;
import app.datos.entidades.TipoInmueble;
import app.datos.servicios.HistorialService;
import app.datos.servicios.InmuebleService;
import app.excepciones.ObjNotFoundException;
import app.excepciones.PersistenciaException;
import app.excepciones.SaveUpdateException;
import app.logica.resultados.ResultadoCrearInmueble;
import app.logica.resultados.ResultadoCrearInmueble.ErrorCrearInmueble;
import app.logica.resultados.ResultadoModificarInmueble;
import app.logica.resultados.ResultadoModificarInmueble.ErrorModificarInmueble;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

/**
 * Este test prueba los métodos de la clase GestorInmueble
 */
@RunWith(JUnitParamsRunner.class)
public class GestorInmuebleTest {

	@Test
	@Parameters
	public void testCrearInmueble(Inmueble inmueble, ResultadoCrearInmueble resultado, ValidadorFormato validadorMock, Propietario propietario, Throwable excepcion) throws Exception {
		GestorDatos gestorDatosMock = new GestorDatos() {

			@Override
			public ArrayList<Estado> obtenerEstados() throws PersistenciaException {
				return new ArrayList<>();
			}
		};
		GestorPropietario gestorPropietarioMock = new GestorPropietario() {

			@Override
			public Propietario obtenerPropietario(FiltroPropietario filtro) throws PersistenciaException {
				if(propietario != null){
					return propietario;
				}
				if(excepcion == null){
					return null;
				}
				if(excepcion instanceof PersistenciaException){
					throw (PersistenciaException) excepcion;
				}
				new Integer("asd");
				return null;
			}
		};
		InmuebleService persistidorInmuebleMock = new InmuebleService() {

			@Override
			public Inmueble obtenerInmueble(Integer id) throws PersistenciaException {
				return null;
			}

			@Override
			public void modificarInmueble(Inmueble inmueble) throws PersistenciaException {

			}

			@Override
			public ArrayList<Inmueble> listarInmuebles() throws PersistenciaException {
				return null;
			}

			@Override
			public void guardarInmueble(Inmueble inmueble) throws PersistenciaException {

			}

			@Override
			public ArrayList<Inmueble> listarInmuebles(FiltroInmueble filtro) throws PersistenciaException {
				return null;
			}
		};
		GestorInmueble gestorInmueble = new GestorInmueble() {
			{
				this.gestorPropietario = gestorPropietarioMock;
				this.persistidorInmueble = persistidorInmuebleMock;
				this.gestorDatos = gestorDatosMock;
				this.validador = validadorMock;
			}
		};

		//creamos la prueba
		Statement test = new Statement() {
			@Override
			public void evaluate() throws Throwable {
				if(resultado != null){
					assertEquals(resultado, gestorInmueble.crearInmueble(inmueble));
				}
				else{
					try{
						gestorInmueble.crearInmueble(inmueble);
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

	protected Object[] parametersForTestCrearInmueble() {
		DatosEdificio datosCorrectos = new DatosEdificio()
				.setAguaCaliente(true)
				.setAguaCorriente(true)
				.setCloacas(true)
				.setGaraje(true)
				.setGasNatural(true)
				.setLavadero(true)
				.setPatio(true)
				.setPavimento(true)
				.setPiscina(true)
				.setPropiedadHorizontal(true)
				.setTelefono(true);

		Propietario propietario = new Propietario()
				.setTipoDocumento(new TipoDocumento().setTipo(TipoDocumentoStr.DNI))
				.setNumeroDocumento("12345678");

		Localidad localidad = new Localidad().setProvincia(new Provincia().setPais(new Pais()));
		Direccion direccion = new Direccion().setCalle(new Calle().setLocalidad(localidad)).setLocalidad(localidad).setBarrio(new Barrio().setLocalidad(localidad));

		Inmueble inmuebleCorrecto = new Inmueble()
				.setDatosEdificio(datosCorrectos)
				.setFechaCarga(new Date())
				.setPropietario(propietario)
				.setTipo(new TipoInmueble())
				.setPrecio(20000.0)
				.setDireccion(direccion);

		Inmueble inmuebleSinFecha = new Inmueble()
				.setDatosEdificio(datosCorrectos)
				.setPropietario(propietario)
				.setTipo(new TipoInmueble())
				.setPrecio(20000.0)
				.setDireccion(direccion);

		Inmueble inmuebleSinPropietario = new Inmueble()
				.setDatosEdificio(datosCorrectos)
				.setFechaCarga(new Date())
				.setTipo(new TipoInmueble())
				.setPrecio(20000.0)
				.setDireccion(direccion);

		Inmueble inmuebleSinTipo = new Inmueble()
				.setDatosEdificio(datosCorrectos)
				.setFechaCarga(new Date())
				.setPropietario(propietario)
				.setPrecio(20000.0)
				.setDireccion(direccion);

		Inmueble inmuebleSinPrecio = new Inmueble()
				.setDatosEdificio(datosCorrectos)
				.setFechaCarga(new Date())
				.setPropietario(propietario)
				.setTipo(new TipoInmueble())
				.setDireccion(direccion);

		Inmueble inmueblePrecioIncorrecto = new Inmueble()
				.setDatosEdificio(datosCorrectos)
				.setFechaCarga(new Date())
				.setPropietario(propietario)
				.setTipo(new TipoInmueble())
				.setPrecio(-32.5)
				.setDireccion(direccion);

		Inmueble inmuebleFrenteIncorrecto = new Inmueble()
				.setDatosEdificio(datosCorrectos)
				.setFechaCarga(new Date())
				.setPropietario(propietario)
				.setTipo(new TipoInmueble())
				.setFrente(-34.0)
				.setPrecio(20000.0)
				.setDireccion(direccion);

		Inmueble inmuebleFondoIncorrecto = new Inmueble()
				.setDatosEdificio(datosCorrectos)
				.setFechaCarga(new Date())
				.setPropietario(propietario)
				.setTipo(new TipoInmueble())
				.setFondo(-4.9)
				.setPrecio(20000.0)
				.setDireccion(direccion);

		Inmueble inmuebleSuperficieIncorrecta = new Inmueble()
				.setDatosEdificio(datosCorrectos)
				.setFechaCarga(new Date())
				.setPropietario(propietario)
				.setTipo(new TipoInmueble())
				.setSuperficie(-9.993434)
				.setPrecio(20000.0)
				.setDireccion(direccion);

		Inmueble inmuebleDatosEdificioIncorrectos = new Inmueble()
				.setFechaCarga(new Date())
				.setPropietario(propietario)
				.setTipo(new TipoInmueble())
				.setDatosEdificio(new DatosEdificio())
				.setPrecio(20000.0)
				.setDireccion(direccion);

		ValidadorFormato validadorCorrecto = new ValidadorFormatoMock();
		ValidadorFormato validadorFormatoDireccionIncorrecto = new ValidadorFormatoMock() {
			@Override
			public Boolean validarDireccion(Direccion direccion) {
				return false;
			}
		};
		ValidadorFormato validadorDoubleIncorrecto = new ValidadorFormatoMock() {
			@Override
			public Boolean validarDoublePositivo(Double numeroDouble) {
				if(numeroDouble.equals(20000.0)){
					return true;
				}
				return false;
			}
		};

		return new Object[] {
				new Object[] { inmuebleCorrecto, new ResultadoCrearInmueble(), validadorCorrecto, propietario, null }, //inmueble correcto
				new Object[] { inmuebleSinFecha, new ResultadoCrearInmueble(ErrorCrearInmueble.Fecha_Vacia), validadorCorrecto, propietario, null }, //inmueble sin fecha de carga
				new Object[] { inmuebleSinPropietario, new ResultadoCrearInmueble(ErrorCrearInmueble.Propietario_Vacio), validadorCorrecto, propietario, null }, //inmueble sin propietario
				new Object[] { inmuebleSinTipo, new ResultadoCrearInmueble(ErrorCrearInmueble.Tipo_Vacio), validadorCorrecto, propietario, null }, //inmueble sin TipoInmueble
				new Object[] { inmuebleSinPrecio, new ResultadoCrearInmueble(ErrorCrearInmueble.Precio_Vacio), validadorCorrecto, propietario, null }, //inmueble sin precio
				new Object[] { inmuebleCorrecto, new ResultadoCrearInmueble(ErrorCrearInmueble.Formato_Direccion_Incorrecto), validadorFormatoDireccionIncorrecto, propietario, null }, //inmueble con formato de direccion incorrecta
				new Object[] { inmueblePrecioIncorrecto, new ResultadoCrearInmueble(ErrorCrearInmueble.Precio_Incorrecto), validadorDoubleIncorrecto, propietario, null }, //inmueble con formato de precio incorrecto
				new Object[] { inmuebleFrenteIncorrecto, new ResultadoCrearInmueble(ErrorCrearInmueble.Frente_Incorrecto), validadorDoubleIncorrecto, propietario, null }, //inmueble con formato de frente incorrecto
				new Object[] { inmuebleFondoIncorrecto, new ResultadoCrearInmueble(ErrorCrearInmueble.Fondo_Incorrecto), validadorDoubleIncorrecto, propietario, null }, //inmueble con formato de fondo incorrecto
				new Object[] { inmuebleSuperficieIncorrecta, new ResultadoCrearInmueble(ErrorCrearInmueble.Superficie_Incorrecta), validadorDoubleIncorrecto, propietario, null }, //inmueble con formato de superficie incorrecto
				new Object[] { inmuebleDatosEdificioIncorrectos, new ResultadoCrearInmueble(ErrorCrearInmueble.Datos_Edificio_Incorrectos), validadorCorrecto, propietario, null }, //inmueble con datosEdificio Incorrectos
				new Object[] { inmuebleCorrecto, new ResultadoCrearInmueble(ErrorCrearInmueble.Propietario_Inexistente), validadorCorrecto, null, null }, //propietario del inmueble no está persistido
				new Object[] { inmuebleCorrecto, null, validadorCorrecto, null, new ObjNotFoundException("", new Exception()) }, //el persistidor tira una PersistenciaException
				new Object[] { inmuebleCorrecto, null, validadorCorrecto, null, new Exception() } //el persistidor tira una excepción inesperada
		};
	}

	@Test
	@Parameters
	public void testModificarInmueble(Inmueble inmueble,
			Boolean resultadoValidarFondo,
			Boolean resultadoValidarFrente,
			Boolean resultadoValidarSuperficie,
			Boolean resultadoValidarDireccion,
			Boolean resultadoValidarDatosEdificio,
			Boolean resultadoValidarPrecio,
			Boolean retornaInmueble,
			Boolean retornaPropietario,
			ResultadoModificarInmueble resultado,
			Throwable excepcion) throws Exception {
		GestorPropietario gestorPropietarioMock = Mockito.mock(GestorPropietario.class);
		InmuebleService persistidorInmuebleMock = Mockito.mock(InmuebleService.class);
		HistorialService persistidorHistorialMock = Mockito.mock(HistorialService.class);
		ValidadorFormato validadorFormatoMock = Mockito.mock(ValidadorFormato.class);

		Mockito.when(validadorFormatoMock.validarDoublePositivo(inmueble.getFondo())).thenReturn(resultadoValidarFondo);
		Mockito.when(validadorFormatoMock.validarDoublePositivo(inmueble.getFrente())).thenReturn(resultadoValidarFrente);
		Mockito.when(validadorFormatoMock.validarDoublePositivo(inmueble.getSuperficie())).thenReturn(resultadoValidarSuperficie);
		Mockito.when(validadorFormatoMock.validarDireccion(inmueble.getDireccion())).thenReturn(resultadoValidarDireccion);
		Mockito.when(validadorFormatoMock.validarDoublePositivo(inmueble.getPrecio())).thenReturn(resultadoValidarPrecio);

		if(retornaPropietario){
			Mockito.when(gestorPropietarioMock.obtenerPropietario(any())).thenReturn(inmueble.getPropietario());
		}
		else{
			Mockito.when(gestorPropietarioMock.obtenerPropietario(any())).thenReturn(null);
		}

		if(retornaInmueble){
			Mockito.when(persistidorInmuebleMock.obtenerInmueble(inmueble.getId())).thenReturn(inmueble);
		}
		else{
			Mockito.when(persistidorInmuebleMock.obtenerInmueble(inmueble.getId())).thenReturn(null);
		}
		if(excepcion != null){
			Mockito.doThrow(excepcion).when(persistidorInmuebleMock).modificarInmueble(inmueble);
		}

		GestorInmueble gestorInmueble = new GestorInmueble() {
			{
				this.validador = validadorFormatoMock;
				this.gestorPropietario = gestorPropietarioMock;
				this.persistidorInmueble = persistidorInmuebleMock;
				this.persistidorHistorial = persistidorHistorialMock;
			}
		};

		//creamos la prueba
		Statement test = new Statement() {
			@Override
			public void evaluate() throws Throwable {
				if(excepcion == null){
					assertEquals(resultado, gestorInmueble.modificarInmueble(inmueble));
					assertEquals(resultadoValidarDatosEdificio, gestorInmueble.validarDatosEdificio(inmueble.getDatosEdificio()));
					if(!resultado.hayErrores()){
						Mockito.verify(persistidorInmuebleMock).modificarInmueble(any());
						Mockito.verify(persistidorHistorialMock).guardarHistorialInmueble(any());
					}

				}
				else{
					try{
						gestorInmueble.modificarInmueble(inmueble);
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

	protected Object[] parametersForTestModificarInmueble() {
		DatosEdificio datosCorrectos = new DatosEdificio()
				.setAguaCaliente(true)
				.setAguaCorriente(true)
				.setCloacas(true)
				.setGaraje(true)
				.setGasNatural(true)
				.setLavadero(true)
				.setPatio(true)
				.setPavimento(true)
				.setPiscina(true)
				.setPropiedadHorizontal(true)
				.setTelefono(true);

		Propietario propietario = new Propietario()
				.setTipoDocumento(new TipoDocumento().setTipo(TipoDocumentoStr.DNI))
				.setNumeroDocumento("12345678");

		Localidad localidad = new Localidad("sdf", new Provincia("sf", new Pais("sd")));
		Direccion direccion = new Direccion("12", null, null, new Calle("sdf", localidad), null, localidad);

		Inmueble inmuebleCorrecto = new Inmueble()
				.setDatosEdificio(datosCorrectos)
				.setFechaCarga(new Date())
				.setPropietario(propietario)
				.setTipo(new TipoInmueble())
				.setDireccion(direccion)
				.setPrecio(20000.0);

		Inmueble inmuebleSinFecha = new Inmueble()
				.setDatosEdificio(datosCorrectos)
				.setPropietario(propietario)
				.setTipo(new TipoInmueble())
				.setDireccion(direccion)
				.setPrecio(20000.0);

		Inmueble inmuebleSinPropietario = new Inmueble()
				.setDatosEdificio(datosCorrectos)
				.setFechaCarga(new Date())
				.setTipo(new TipoInmueble())
				.setDireccion(direccion)
				.setPrecio(20000.0);

		Inmueble inmuebleSinTipo = new Inmueble()
				.setDatosEdificio(datosCorrectos)
				.setFechaCarga(new Date())
				.setPropietario(propietario)
				.setDireccion(direccion)
				.setPrecio(20000.0);

		Inmueble inmuebleSinPrecio = new Inmueble()
				.setDatosEdificio(datosCorrectos)
				.setFechaCarga(new Date())
				.setPropietario(propietario)
				.setDireccion(direccion)
				.setTipo(new TipoInmueble());

		Inmueble inmuebleFrenteIncorrecto = new Inmueble()
				.setDatosEdificio(datosCorrectos)
				.setFechaCarga(new Date())
				.setPropietario(propietario)
				.setTipo(new TipoInmueble())
				.setFrente(-34.0)
				.setDireccion(direccion)
				.setPrecio(20000.0);

		Inmueble inmuebleFondoIncorrecto = new Inmueble()
				.setDatosEdificio(datosCorrectos)
				.setFechaCarga(new Date())
				.setPropietario(propietario)
				.setTipo(new TipoInmueble())
				.setFondo(-4.9)
				.setDireccion(direccion)
				.setPrecio(20000.0);

		Inmueble inmuebleSuperficieIncorrecta = new Inmueble()
				.setDatosEdificio(datosCorrectos)
				.setFechaCarga(new Date())
				.setPropietario(propietario)
				.setTipo(new TipoInmueble())
				.setSuperficie(-9.993434)
				.setDireccion(direccion)
				.setPrecio(20000.0);

		Inmueble inmuebleDatosEdificioIncorrectos = new Inmueble()
				.setFechaCarga(new Date())
				.setPropietario(propietario)
				.setTipo(new TipoInmueble())
				.setDireccion(direccion)
				.setPrecio(20000.0);

		return new Object[] {
				new Object[] { inmuebleCorrecto, true, true, true, true, true, true, true, true, resultadoModificarCorrecto, null }, //inmueble correcto
				new Object[] { inmuebleSinFecha, true, true, true, true, true, true, true, true, resultadoModificarFecha_Vacia, null }, //inmueble sin fecha de carga
				new Object[] { inmuebleSinPropietario, true, true, true, true, true, true, true, true, resultadoModificarPropietario_Vacio, null }, //inmueble sin propietario
				new Object[] { inmuebleSinTipo, true, true, true, true, true, true, true, true, resultadoModificarTipo_Vacio, null }, //inmueble sin TipoInmueble
				new Object[] { inmuebleSinPrecio, true, true, true, true, true, true, true, true, resultadoModificarPrecio_Vacio, null }, //inmueble sin precio
				new Object[] { inmuebleFondoIncorrecto, false, true, true, true, true, true, true, true, resultadoModificarFondo_Incorrecto, null }, //inmueble con formato de fondo incorrecto
				new Object[] { inmuebleFrenteIncorrecto, true, false, true, true, true, true, true, true, resultadoModificarFrente_Incorrecto, null }, //inmueble con formato de frente incorrecto
				new Object[] { inmuebleSuperficieIncorrecta, true, true, false, true, true, true, true, true, resultadoModificarSuperficie_Incorrecta, null }, //inmueble con formato de superficie incorrecto
				new Object[] { inmuebleCorrecto, true, true, true, false, true, true, true, true, resultadoModificarFormato_Direccion_Incorrecto, null }, //inmueble con formato de direccion incorrecta
				new Object[] { inmuebleDatosEdificioIncorrectos, true, true, true, true, false, true, true, true, resultadoModificarDatos_Edificio_Incorrectos, null }, //inmueble con datosEdificio Incorrectos
				new Object[] { inmuebleCorrecto, true, true, true, true, true, false, true, true, resultadoModificarPrecio_Incorrecto, null }, //inmueble con formato de precio incorrecto
				new Object[] { inmuebleCorrecto, true, true, true, true, true, true, true, false, resultadoModificarPropietario_Inexistente, null }, //propietario del inmueble no está persistido
				new Object[] { inmuebleCorrecto, true, true, true, true, true, true, false, true, resultadoModificarInmueble_Inexistente, null }, //Inmueble no está persistido
				new Object[] { inmuebleCorrecto, true, true, true, true, true, true, true, true, null, new ObjNotFoundException("", new Exception()) }, //el persistidor tira una PersistenciaException
				new Object[] { inmuebleCorrecto, true, true, true, true, true, true, true, true, null, new SaveUpdateException(new Exception()) }, //el persistidor tira una SaveUpdateException
		};
	}

	private static final ResultadoModificarInmueble resultadoModificarCorrecto = new ResultadoModificarInmueble();
	private static final ResultadoModificarInmueble resultadoModificarFecha_Vacia = new ResultadoModificarInmueble(ErrorModificarInmueble.Fecha_Vacia);
	private static final ResultadoModificarInmueble resultadoModificarPropietario_Vacio = new ResultadoModificarInmueble(ErrorModificarInmueble.Propietario_Vacio);
	private static final ResultadoModificarInmueble resultadoModificarPropietario_Inexistente = new ResultadoModificarInmueble(ErrorModificarInmueble.Propietario_Inexistente);
	private static final ResultadoModificarInmueble resultadoModificarFormato_Direccion_Incorrecto = new ResultadoModificarInmueble(ErrorModificarInmueble.Formato_Direccion_Incorrecto);
	private static final ResultadoModificarInmueble resultadoModificarPrecio_Vacio = new ResultadoModificarInmueble(ErrorModificarInmueble.Precio_Vacio);
	private static final ResultadoModificarInmueble resultadoModificarPrecio_Incorrecto = new ResultadoModificarInmueble(ErrorModificarInmueble.Precio_Incorrecto);
	private static final ResultadoModificarInmueble resultadoModificarFondo_Incorrecto = new ResultadoModificarInmueble(ErrorModificarInmueble.Fondo_Incorrecto);
	private static final ResultadoModificarInmueble resultadoModificarFrente_Incorrecto = new ResultadoModificarInmueble(ErrorModificarInmueble.Frente_Incorrecto);
	private static final ResultadoModificarInmueble resultadoModificarSuperficie_Incorrecta = new ResultadoModificarInmueble(ErrorModificarInmueble.Superficie_Incorrecta);
	private static final ResultadoModificarInmueble resultadoModificarTipo_Vacio = new ResultadoModificarInmueble(ErrorModificarInmueble.Tipo_Vacio);
	private static final ResultadoModificarInmueble resultadoModificarDatos_Edificio_Incorrectos = new ResultadoModificarInmueble(ErrorModificarInmueble.Datos_Edificio_Incorrectos);
	private static final ResultadoModificarInmueble resultadoModificarInmueble_Inexistente = new ResultadoModificarInmueble(ErrorModificarInmueble.Inmueble_Inexistente);
}
