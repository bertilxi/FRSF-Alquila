package app.logica.gestores;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;

import app.comun.ValidadorFormato;
import app.comun.Mock.ValidadorFormatoMock;
import app.datos.clases.FiltroPropietario;
import app.datos.clases.TipoDocumentoStr;
import app.datos.entidades.DatosEdificio;
import app.datos.entidades.Direccion;
import app.datos.entidades.Inmueble;
import app.datos.entidades.Propietario;
import app.datos.entidades.TipoDocumento;
import app.datos.entidades.TipoInmueble;
import app.datos.servicios.InmuebleService;
import app.excepciones.ObjNotFoundException;
import app.excepciones.PersistenciaException;
import app.logica.resultados.ResultadoCrearInmueble;
import app.logica.resultados.ResultadoCrearInmueble.ErrorCrearInmueble;
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
		};
		GestorInmueble gestorInmueble = new GestorInmueble() {
			{
				this.gestorPropietario = gestorPropietarioMock;
				this.persistidorInmueble = persistidorInmuebleMock;
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

		Inmueble inmuebleCorrecto = new Inmueble()
				.setDatosEdificio(datosCorrectos)
				.setFechaCarga(new Date())
				.setPropietario(propietario)
				.setTipo(new TipoInmueble())
				.setPrecio(20000.0);

		Inmueble inmuebleSinFecha = new Inmueble()
				.setDatosEdificio(datosCorrectos)
				.setPropietario(propietario)
				.setTipo(new TipoInmueble())
				.setPrecio(20000.0);

		Inmueble inmuebleSinPropietario = new Inmueble()
				.setDatosEdificio(datosCorrectos)
				.setFechaCarga(new Date())
				.setTipo(new TipoInmueble())
				.setPrecio(20000.0);

		Inmueble inmuebleSinTipo = new Inmueble()
				.setDatosEdificio(datosCorrectos)
				.setFechaCarga(new Date())
				.setPropietario(propietario)
				.setPrecio(20000.0);

		Inmueble inmuebleSinPrecio = new Inmueble()
				.setDatosEdificio(datosCorrectos)
				.setFechaCarga(new Date())
				.setPropietario(propietario)
				.setTipo(new TipoInmueble());

		Inmueble inmueblePrecioIncorrecto = new Inmueble()
				.setDatosEdificio(datosCorrectos)
				.setFechaCarga(new Date())
				.setPropietario(propietario)
				.setTipo(new TipoInmueble())
				.setPrecio(-32.5);

		Inmueble inmuebleFrenteIncorrecto = new Inmueble()
				.setDatosEdificio(datosCorrectos)
				.setFechaCarga(new Date())
				.setPropietario(propietario)
				.setTipo(new TipoInmueble())
				.setFrente(-34.0)
				.setPrecio(20000.0);

		Inmueble inmuebleFondoIncorrecto = new Inmueble()
				.setDatosEdificio(datosCorrectos)
				.setFechaCarga(new Date())
				.setPropietario(propietario)
				.setTipo(new TipoInmueble())
				.setFondo(-4.9)
				.setPrecio(20000.0);

		Inmueble inmuebleSuperficieIncorrecta = new Inmueble()
				.setDatosEdificio(datosCorrectos)
				.setFechaCarga(new Date())
				.setPropietario(propietario)
				.setTipo(new TipoInmueble())
				.setSuperficie(-9.993434)
				.setPrecio(20000.0);

		Inmueble inmuebleDatosEdificioIncorrectos = new Inmueble()
				.setFechaCarga(new Date())
				.setPropietario(propietario)
				.setTipo(new TipoInmueble())
				.setDatosEdificio(new DatosEdificio())
				.setPrecio(20000.0);

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

}
