/**
 * Copyright (C) 2016 Fernando Berti - Daniel Campodonico - Emiliano Gioria - Lucas Moretti - Esteban Rebechi - Andres Leonel Rico
 * This file is part of Olimpo.
 *
 * Olimpo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Olimpo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Olimpo. If not, see <http://www.gnu.org/licenses/>.
 */
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
import app.datos.entidades.EstadoInmueble;
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
	/**
	 * Prueba el método crearInmueble(), el cual corresponde con la taskcard 14 de la iteración 1 y a la historia 3
	 *
	 * @param inmueble
	 *            inmueble a crear
	 * @param resultado
	 *            resultado que se espera que devuelva el gestor
	 * @param validadorMock
	 *            clase vacía que utiliza el gestor para validar
	 * @param propietario
	 *            propietario "en la base de datos" a comparar con el propietario del inmueble a guardar para verificar que exista en la base de datos
	 * @param excepcion
	 *            es la excepcion que debe lanzar el mock del persistidor, si la prueba involucra procesar una excepcion de dicho persistidor, debe ser nulo propietario para que se use
	 * @throws Exception
	 */
	public void testCrearInmueble(Inmueble inmueble, ResultadoCrearInmueble resultado, ValidadorFormato validadorMock, Propietario propietario, Throwable excepcion) throws Exception {
		GestorDatos gestorDatosMock = new GestorDatos() {

			@Override
			public ArrayList<Estado> obtenerEstados() throws PersistenciaException {
				return new ArrayList<>();
			}

			@Override
			public ArrayList<EstadoInmueble> obtenerEstadosInmueble() throws PersistenciaException {
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

	/**
	 * Método que devuelve los parámetros para probar el método crearInmueble()
	 *
	 * @return parámetros de prueba
	 */
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
				//Casos de prueba
				//inmueble, resultado, validadorMock, propietario, excepcion
				/* 0 */ new Object[] { inmuebleCorrecto, new ResultadoCrearInmueble(), validadorCorrecto, propietario, null }, //inmueble correcto
				/* 1 */ new Object[] { inmuebleSinFecha, new ResultadoCrearInmueble(ErrorCrearInmueble.Fecha_Vacia), validadorCorrecto, propietario, null }, //inmueble sin fecha de carga
				/* 2 */ new Object[] { inmuebleSinPropietario, new ResultadoCrearInmueble(ErrorCrearInmueble.Propietario_Vacio), validadorCorrecto, propietario, null }, //inmueble sin propietario
				/* 3 */ new Object[] { inmuebleSinTipo, new ResultadoCrearInmueble(ErrorCrearInmueble.Tipo_Vacio), validadorCorrecto, propietario, null }, //inmueble sin TipoInmueble
				/* 4 */ new Object[] { inmuebleSinPrecio, new ResultadoCrearInmueble(ErrorCrearInmueble.Precio_Vacio), validadorCorrecto, propietario, null }, //inmueble sin precio
				/* 5 */ new Object[] { inmuebleCorrecto, new ResultadoCrearInmueble(ErrorCrearInmueble.Formato_Direccion_Incorrecto), validadorFormatoDireccionIncorrecto, propietario, null }, //inmueble con formato de direccion incorrecta
				/* 6 */ new Object[] { inmueblePrecioIncorrecto, new ResultadoCrearInmueble(ErrorCrearInmueble.Precio_Incorrecto), validadorDoubleIncorrecto, propietario, null }, //inmueble con formato de precio incorrecto
				/* 7 */ new Object[] { inmuebleFrenteIncorrecto, new ResultadoCrearInmueble(ErrorCrearInmueble.Frente_Incorrecto), validadorDoubleIncorrecto, propietario, null }, //inmueble con formato de frente incorrecto
				/* 8 */ new Object[] { inmuebleFondoIncorrecto, new ResultadoCrearInmueble(ErrorCrearInmueble.Fondo_Incorrecto), validadorDoubleIncorrecto, propietario, null }, //inmueble con formato de fondo incorrecto
				/* 9 */ new Object[] { inmuebleSuperficieIncorrecta, new ResultadoCrearInmueble(ErrorCrearInmueble.Superficie_Incorrecta), validadorDoubleIncorrecto, propietario, null }, //inmueble con formato de superficie incorrecto
				/* 10 */ new Object[] { inmuebleDatosEdificioIncorrectos, new ResultadoCrearInmueble(ErrorCrearInmueble.Datos_Edificio_Incorrectos), validadorCorrecto, propietario, null }, //inmueble con datosEdificio Incorrectos
				/* 11 */ new Object[] { inmuebleCorrecto, new ResultadoCrearInmueble(ErrorCrearInmueble.Propietario_Inexistente), validadorCorrecto, null, null }, //propietario del inmueble no está persistido
				/* 12 */ new Object[] { inmuebleCorrecto, null, validadorCorrecto, null, new ObjNotFoundException("", new Exception()) }, //el persistidor tira una PersistenciaException
				/* 13 */ new Object[] { inmuebleCorrecto, null, validadorCorrecto, null, new Exception() } //el persistidor tira una excepción inesperada
		};
	}

	/**
	 * Prueba el método modificarInmueble(), el cual corresponde con la taskcard 14 de la iteración 1 y a la historia 3
	 *
	 * @param inmueble
	 *          inmueble a modificar
	 * @param resultadoValidarFondo
	 * 			resultado devuelto por el mock validador al validar el fondo del inmueble
	 * @param resultadoValidarFrente
	 * 			resultado devuelto por el mock validador al validar el frente del inmueble
	 * @param resultadoValidarSuperficie
	 * 			resultado devuelto por el mock validador al validar la superficie del inmueble
	 * @param resultadoValidarDireccion
	 * 			resultado devuelto por el mock validador al validar la dirección del inmueble
	 * @param resultadoValidarDatosEdificioEsperado
	 * 			resultado devuelto por el mock validador al validar el fondo del inmueble
	 * @param resultadoValidarPrecio
	 * 			resultado devuelto por el mock validador al validar el precio del inmueble
	 * @param retornaInmueble
	 * 			indica si el persistidor devuelve un inmueble
	 * @param retornaPropietario
	 * 			indica si el persistidor devuelve un propietario
	 * @param resultadoEsperado
	 *          resultado que se espera que devuelva el gestor
	 * @param propietario
	 *            propietario "en la base de datos" a comparar con el propietario del inmueble a guardar para verificar que exista en la base de datos
	 * @param excepcion
	 *            es la excepcion que debe lanzar el mock del persistidor, si la prueba involucra procesar una excepcion de dicho persistidor, debe ser nulo propietario para que se use
	 * @throws Exception
	 */
	@Test
	@Parameters
	public void testModificarInmueble(Inmueble inmueble, Boolean resultadoValidarFondo, Boolean resultadoValidarFrente, Boolean resultadoValidarSuperficie, Boolean resultadoValidarDireccion, Boolean resultadoValidarDatosEdificioEsperado, Boolean resultadoValidarPrecio, Boolean retornaInmueble, Boolean retornaPropietario, ResultadoModificarInmueble resultadoEsperado, Throwable excepcion) throws Exception {
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
					assertEquals(resultadoEsperado, gestorInmueble.modificarInmueble(inmueble));
					assertEquals(resultadoValidarDatosEdificioEsperado, gestorInmueble.validarDatosEdificio(inmueble.getDatosEdificio()));
					if(!resultadoEsperado.hayErrores()){
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
				//Casos de prueba
				// inmueble, resultadoValidarFondo, resultadoValidarFrente, resultadoValidarSuperficie, resultadoValidarDireccion, resultadoValidarDatosEdificio, resultadoValidarPrecio, retornaInmueble, retornaPropietario, resultado, excepcion
				/* 0 */ new Object[] { inmuebleCorrecto, true, true, true, true, true, true, true, true, resultadoModificarCorrecto, null }, //inmueble correcto
				/* 1 */ new Object[] { inmuebleSinFecha, true, true, true, true, true, true, true, true, resultadoModificarFecha_Vacia, null }, //inmueble sin fecha de carga
				/* 2 */ new Object[] { inmuebleSinPropietario, true, true, true, true, true, true, true, true, resultadoModificarPropietario_Vacio, null }, //inmueble sin propietario
				/* 3 */ new Object[] { inmuebleSinTipo, true, true, true, true, true, true, true, true, resultadoModificarTipo_Vacio, null }, //inmueble sin TipoInmueble
				/* 4 */ new Object[] { inmuebleSinPrecio, true, true, true, true, true, true, true, true, resultadoModificarPrecio_Vacio, null }, //inmueble sin precio
				/* 5 */ new Object[] { inmuebleFondoIncorrecto, false, true, true, true, true, true, true, true, resultadoModificarFondo_Incorrecto, null }, //inmueble con formato de fondo incorrecto
				/* 6 */ new Object[] { inmuebleFrenteIncorrecto, true, false, true, true, true, true, true, true, resultadoModificarFrente_Incorrecto, null }, //inmueble con formato de frente incorrecto
				/* 7 */ new Object[] { inmuebleSuperficieIncorrecta, true, true, false, true, true, true, true, true, resultadoModificarSuperficie_Incorrecta, null }, //inmueble con formato de superficie incorrecto
				/* 8 */ new Object[] { inmuebleCorrecto, true, true, true, false, true, true, true, true, resultadoModificarFormato_Direccion_Incorrecto, null }, //inmueble con formato de direccion incorrecta
				/* 9 */ new Object[] { inmuebleDatosEdificioIncorrectos, true, true, true, true, false, true, true, true, resultadoModificarDatos_Edificio_Incorrectos, null }, //inmueble con datosEdificio Incorrectos
				/* 10 */ new Object[] { inmuebleCorrecto, true, true, true, true, true, false, true, true, resultadoModificarPrecio_Incorrecto, null }, //inmueble con formato de precio incorrecto
				/* 11 */ new Object[] { inmuebleCorrecto, true, true, true, true, true, true, true, false, resultadoModificarPropietario_Inexistente, null }, //propietario del inmueble no está persistido
				/* 12 */ new Object[] { inmuebleCorrecto, true, true, true, true, true, true, false, true, resultadoModificarInmueble_Inexistente, null }, //Inmueble no está persistido
				/* 13 */ new Object[] { inmuebleCorrecto, true, true, true, true, true, true, true, true, null, new ObjNotFoundException("", new Exception()) }, //el persistidor tira una PersistenciaException
				/* 14 */ new Object[] { inmuebleCorrecto, true, true, true, true, true, true, true, true, null, new SaveUpdateException(new Exception()) }, //el persistidor tira una SaveUpdateException
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
