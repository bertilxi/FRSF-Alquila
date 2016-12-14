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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import app.comun.ValidadorFormato;
import app.datos.clases.EstadoStr;
import app.datos.clases.TipoDocumentoStr;
import app.datos.entidades.Barrio;
import app.datos.entidades.Calle;
import app.datos.entidades.Direccion;
import app.datos.entidades.Estado;
import app.datos.entidades.Localidad;
import app.datos.entidades.Propietario;
import app.datos.entidades.TipoDocumento;
import app.datos.servicios.PropietarioService;
import app.excepciones.EntidadExistenteConEstadoBajaException;
import app.excepciones.SaveUpdateException;
import app.logica.resultados.ResultadoCrearPropietario;
import app.logica.resultados.ResultadoCrearPropietario.ErrorCrearPropietario;
import app.logica.resultados.ResultadoEliminarPropietario;
import app.logica.resultados.ResultadoModificarPropietario;
import app.logica.resultados.ResultadoModificarPropietario.ErrorModificarPropietario;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class GestorPropietarioTest {

	private static Propietario propietario;

	protected Object[] parametersForTestCrearPropietario() {
		//Se carga propietario con datos básicos solo para evitar punteros nulos
		//Las validaciones se hacen en el validador, por lo que se setean luego en los mocks los valores esperados
		TipoDocumento doc = new TipoDocumento(TipoDocumentoStr.DNI);
		Localidad localidad = new Localidad("sadadfa", null);
		Direccion dir = new Direccion("1234", "1234", "a", new Calle("hilka", localidad), new Barrio("asdas", localidad), localidad);
		propietario = new Propietario(1, "Juan", "Perez", "38377777", "3424686868", "d.a@hotmail.com", doc, dir);
		propietario.setEstado(new Estado(EstadoStr.ALTA));

		Propietario propietario2 = new Propietario(1, "Juan", "Perez", "38377777", "3424686868", "d.a@hotmail.com", doc, dir);
		propietario2.setEstado(new Estado(EstadoStr.BAJA));

		//Parámetros de JUnitParams
		return new Object[] {
				new Object[] { true, true, true, true, true, true, null, 1, resultadoCrearCorrecto, null, null }, //Test correcto
				new Object[] { false, true, true, true, true, true, null, 0, resultadoCrearNombreIncorrecto, null, null }, //Nombre Incorrecto
				new Object[] { true, false, true, true, true, true, null, 0, resultadoCrearApellidoIncorrecto, null, null }, //Apellido Incorrecto
				new Object[] { true, true, false, true, true, true, null, 0, resultadoCrearDocumentoIncorrecto, null, null }, //Documento Incorrecto
				new Object[] { true, true, true, false, true, true, null, 0, resultadoCrearTelefonoIncorrecto, null, null }, //Teléfono Incorrecto
				new Object[] { true, true, true, true, false, true, null, 0, resultadoCrearEmailIncorrecto, null, null }, //Email Incorrecto
				new Object[] { true, true, true, true, true, false, null, 0, resultadoCrearDireccionIncorrecto, null, null }, //Dirección incorrecta
				new Object[] { false, false, true, true, true, true, null, 0, new ResultadoCrearPropietario(ErrorCrearPropietario.Formato_Nombre_Incorrecto, ErrorCrearPropietario.Formato_Apellido_Incorrecto), null, null }, //Nombre y apellido incorrectos
				new Object[] { true, true, true, true, true, true, propietario, 0, resultadoCrearYaExiste, null, null }, //Ya existe un propietario con el mismo documento
				new Object[] { true, true, true, true, true, true, null, 1, null, new SaveUpdateException(new Exception()), new SaveUpdateException(new Exception()) }, //La base de datos tira una excepción
				new Object[] { true, true, true, true, true, true, propietario2, 0, null, null, new EntidadExistenteConEstadoBajaException() } //El propietario ya existe pero con estado BAJA
		};
	}

	@Test
	@Parameters
	public void testCrearPropietario(Boolean resValNombre,
			Boolean resValApellido,
			Boolean resValDocumento,
			Boolean resValTelefono,
			Boolean resValEmail,
			Boolean resValDireccion,
			Propietario resObtenerPropietario,
			Integer guardar,
			ResultadoCrearPropietario resultadoCrearPropietarioEsperado,
			Throwable excepcion,
			Throwable excepcionEsperada) throws Exception {

		//Inicialización de los mocks
		PropietarioService propietarioServiceMock = mock(PropietarioService.class);
		ValidadorFormato validadorFormatoMock = mock(ValidadorFormato.class);
		GestorDatos gestorDatosMock = mock(GestorDatos.class);

		//Clase anónima necesaria para inyectar dependencias
		GestorPropietario gestorPropietario = new GestorPropietario() {
			{
				this.persistidorPropietario = propietarioServiceMock;
				this.validador = validadorFormatoMock;
				this.gestorDatos = gestorDatosMock;
			}
		};

		ArrayList<Estado> estados = new ArrayList<>();
		estados.add(new Estado(EstadoStr.BAJA));
		estados.add(new Estado(EstadoStr.ALTA));

		//Setear valores esperados a los mocks
		when(validadorFormatoMock.validarNombre(propietario.getNombre())).thenReturn(resValNombre);
		when(validadorFormatoMock.validarApellido(propietario.getApellido())).thenReturn(resValApellido);
		when(validadorFormatoMock.validarDocumento(propietario.getTipoDocumento(), propietario.getNumeroDocumento())).thenReturn(resValDocumento);
		when(validadorFormatoMock.validarTelefono(propietario.getTelefono())).thenReturn(resValTelefono);
		when(validadorFormatoMock.validarEmail(propietario.getEmail())).thenReturn(resValEmail);
		when(validadorFormatoMock.validarDireccion(propietario.getDireccion())).thenReturn(resValDireccion);
		when(propietarioServiceMock.obtenerPropietario(any())).thenReturn(resObtenerPropietario);
		when(gestorDatosMock.obtenerEstados()).thenReturn(estados);

		//Setear la excepcion devuelta por la base de datos, si corresponde
		if(excepcion != null){
			doThrow(excepcion).when(propietarioServiceMock).guardarPropietario(propietario);
		}
		else{
			doNothing().when(propietarioServiceMock).guardarPropietario(propietario);
		}

		//Llamar al método a testear y comprobar resultados obtenidos, que se llaman a los métodos deseados y con los parámetros correctos
		if(excepcionEsperada == null){
			assertEquals(resultadoCrearPropietarioEsperado.getErrores(), gestorPropietario.crearPropietario(propietario).getErrores());
		}
		else{
			try{
				gestorPropietario.crearPropietario(propietario);
				Assert.fail("Debería haber fallado!");
			} catch(Exception e){
				assertEquals(excepcionEsperada.getClass(), e.getClass());
			}
		}
		verify(validadorFormatoMock).validarNombre(propietario.getNombre());
		verify(validadorFormatoMock).validarApellido(propietario.getApellido());
		verify(validadorFormatoMock).validarDocumento(propietario.getTipoDocumento(), propietario.getNumeroDocumento());
		verify(validadorFormatoMock).validarTelefono(propietario.getTelefono());
		verify(validadorFormatoMock).validarEmail(propietario.getEmail());
		verify(validadorFormatoMock).validarDireccion(propietario.getDireccion());
		verify(gestorDatosMock, times(guardar)).obtenerEstados();
		verify(propietarioServiceMock, times(guardar)).guardarPropietario(propietario);
	}

	protected Object[] parametersForTestModificarPropietario() {
		TipoDocumento doc = new TipoDocumento(TipoDocumentoStr.DNI);
		Localidad localidad = new Localidad("sadadfa", null);
		Direccion dir = new Direccion("1234", "1234", "a", new Calle("hilka", localidad), new Barrio("asdas", localidad), localidad);
		propietario = new Propietario(1, "Juan", "Perez", "38377777", "3424686868", "d.a@hotmail.com", doc, dir);
		propietario.setEstado(new Estado(EstadoStr.ALTA));

		Propietario propietario2 = new Propietario(2, "José", "Perez2", "38377777", "3424686868", "j.c@hotmail.com", doc, dir);
		propietario2.setEstado(new Estado(EstadoStr.ALTA));

		//Parámetros de JUnitParams
		return new Object[] {
				new Object[] { true, true, true, true, true, true, null, 1, resultadoModificarCorrecto, null, null }, //Test correcto (se modifica el documento)
				new Object[] { false, true, true, true, true, true, null, 0, resultadoModificarNombreIncorrecto, null, null }, //Nombre Incorrecto
				new Object[] { true, false, true, true, true, true, null, 0, resultadoModificarApellidoIncorrecto, null, null }, //Apellido Incorrecto
				new Object[] { true, true, false, true, true, true, null, 0, resultadoModificarDocumentoIncorrecto, null, null }, //Documento Incorrecto
				new Object[] { true, true, true, false, true, true, null, 0, resultadoModificarTelefonoIncorrecto, null, null }, //Teléfono Incorrecto
				new Object[] { true, true, true, true, false, true, null, 0, resultadoModificarEmailIncorrecto, null, null }, //Email Incorrecto
				new Object[] { true, true, true, true, true, false, null, 0, resultadoModificarDireccionIncorrecto, null, null }, //Dirección incorrecta
				new Object[] { false, false, true, true, true, true, null, 0, new ResultadoModificarPropietario(ErrorModificarPropietario.Formato_Nombre_Incorrecto, ErrorModificarPropietario.Formato_Apellido_Incorrecto), null, null }, //Nombre y apellido incorrectos
				new Object[] { true, true, true, true, true, true, propietario2, 0, resultadoModificarYaSePoseeMismoDocumento, null, null }, //Ya existe un propietario con el mismo documento
				new Object[] { true, true, true, true, true, true, propietario, 1, resultadoModificarCorrecto, null, null }, //Test correcto, no se cambió el documento
				new Object[] { true, true, true, true, true, true, null, 1, null, new SaveUpdateException(new Exception()), new SaveUpdateException(new Exception()) } //La base de datos tira una excepción
		};
	}

	@Test
	@Parameters
	public void testModificarPropietario(Boolean resValNombre,
			Boolean resValApellido,
			Boolean resValDocumento,
			Boolean resValTelefono,
			Boolean resValEmail,
			Boolean resValDireccion,
			Propietario resObtenerPropietario,
			Integer modificar,
			ResultadoModificarPropietario resultadoModificarPropietarioEsperado,
			Throwable excepcion,
			Throwable excepcionEsperada) throws Exception {
		//Inicialización de los mocks
		PropietarioService propietarioServiceMock = mock(PropietarioService.class);
		ValidadorFormato validadorFormatoMock = mock(ValidadorFormato.class);
		GestorDatos gestorDatosMock = mock(GestorDatos.class);

		//Clase anónima necesaria para inyectar dependencias
		GestorPropietario gestorPropietario = new GestorPropietario() {
			{
				this.persistidorPropietario = propietarioServiceMock;
				this.validador = validadorFormatoMock;
				this.gestorDatos = gestorDatosMock;
			}
		};

		ArrayList<Estado> estados = new ArrayList<>();
		estados.add(new Estado(EstadoStr.BAJA));
		estados.add(new Estado(EstadoStr.ALTA));

		//Setear valores esperados a los mocks
		when(validadorFormatoMock.validarNombre(propietario.getNombre())).thenReturn(resValNombre);
		when(validadorFormatoMock.validarApellido(propietario.getApellido())).thenReturn(resValApellido);
		when(validadorFormatoMock.validarDocumento(propietario.getTipoDocumento(), propietario.getNumeroDocumento())).thenReturn(resValDocumento);
		when(validadorFormatoMock.validarTelefono(propietario.getTelefono())).thenReturn(resValTelefono);
		when(validadorFormatoMock.validarEmail(propietario.getEmail())).thenReturn(resValEmail);
		when(validadorFormatoMock.validarDireccion(propietario.getDireccion())).thenReturn(resValDireccion);
		when(propietarioServiceMock.obtenerPropietario(any())).thenReturn(resObtenerPropietario);
		when(gestorDatosMock.obtenerEstados()).thenReturn(estados);

		//Setear la excepcion devuelta por la base de datos, si corresponde
		if(excepcion != null){
			doThrow(excepcion).when(propietarioServiceMock).modificarPropietario(propietario);
		}
		else{
			doNothing().when(propietarioServiceMock).modificarPropietario(propietario);
		}

		//Llamar al método a testear y comprobar resultados obtenidos, que se llaman a los métodos deseados y con los parámetros correctos
		if(excepcionEsperada == null){
			assertEquals(resultadoModificarPropietarioEsperado.getErrores(), gestorPropietario.modificarPropietario(propietario).getErrores());
		}
		else{
			try{
				gestorPropietario.modificarPropietario(propietario);
				Assert.fail("Debería haber fallado!");
			} catch(Exception e){
				assertEquals(excepcionEsperada.getClass(), e.getClass());
			}
		}
		verify(validadorFormatoMock).validarNombre(propietario.getNombre());
		verify(validadorFormatoMock).validarApellido(propietario.getApellido());
		verify(validadorFormatoMock).validarDocumento(propietario.getTipoDocumento(), propietario.getNumeroDocumento());
		verify(validadorFormatoMock).validarTelefono(propietario.getTelefono());
		verify(validadorFormatoMock).validarEmail(propietario.getEmail());
		verify(validadorFormatoMock).validarDireccion(propietario.getDireccion());
		verify(propietarioServiceMock).obtenerPropietario(any());
		verify(gestorDatosMock, times(modificar)).obtenerEstados();
		verify(propietarioServiceMock, times(modificar)).modificarPropietario(propietario);
	}

	protected Object[] parametersForTestEliminarPropietario() {
		TipoDocumento doc = new TipoDocumento(TipoDocumentoStr.DNI);
		Localidad localidad = new Localidad("sadadfa", null);
		Direccion dir = new Direccion("1234", "1234", "a", new Calle("hilka", localidad), new Barrio("asdas", localidad), localidad);
		propietario = new Propietario(1, "Juan", "Perez", "38377777", "3424686868", "d.a@hotmail.com", doc, dir);
		propietario.setEstado(new Estado(EstadoStr.ALTA));

		Propietario propietario2 = new Propietario(2, "José", "Perez2", "38377777", "3424686868", "j.c@hotmail.com", doc, dir);
		propietario2.setEstado(new Estado(EstadoStr.ALTA));

		//Parámetros de JUnitParams
		return new Object[] {
				new Object[] { propietario, 1, resultadoEliminarCorrecto, null, null }, //Test correcto
				new Object[] { propietario, 1, null, new SaveUpdateException(new Exception()), new SaveUpdateException(new Exception()) } //La base de datos tira una excepción
		};
	}

	@Test
	@Parameters
	public void testEliminarPropietario(Propietario resObtenerPropietario,
			Integer eliminar,
			ResultadoEliminarPropietario resultadoEliminarPropietarioEsperado,
			Throwable excepcion,
			Throwable excepcionEsperada) throws Exception {
		//Inicialización de los mocks
		PropietarioService propietarioServiceMock = mock(PropietarioService.class);
		GestorDatos gestorDatosMock = mock(GestorDatos.class);

		//Clase anónima necesaria para inyectar dependencias
		GestorPropietario gestorPropietario = new GestorPropietario() {
			{
				this.persistidorPropietario = propietarioServiceMock;
				this.gestorDatos = gestorDatosMock;
			}
		};

		ArrayList<Estado> estados = new ArrayList<>();
		estados.add(new Estado(EstadoStr.ALTA));
		estados.add(new Estado(EstadoStr.BAJA));

		//Setear valores esperados a los mocks
		when(gestorDatosMock.obtenerEstados()).thenReturn(estados);

		//Setear la excepcion devuelta por la base de datos, si corresponde
		if(excepcion != null){
			doThrow(excepcion).when(propietarioServiceMock).modificarPropietario(propietario);
		}
		else{
			doNothing().when(propietarioServiceMock).modificarPropietario(propietario);
		}

		//Llamar al método a testear y comprobar resultados obtenidos, que se llaman a los métodos deseados y con los parámetros correctos
		if(excepcionEsperada == null){
			assertEquals(resultadoEliminarPropietarioEsperado.getErrores(), gestorPropietario.eliminarPropietario(propietario).getErrores());
		}
		else{
			try{
				gestorPropietario.eliminarPropietario(propietario);
				Assert.fail("Debería haber fallado!");
			} catch(Exception e){
				assertEquals(excepcionEsperada.getClass(), e.getClass());
			}
		}
		if(eliminar != 0){
			assertEquals(EstadoStr.BAJA, propietario.getEstado().getEstado());
		}
		verify(gestorDatosMock, times(eliminar)).obtenerEstados();
		verify(propietarioServiceMock, times(eliminar)).modificarPropietario(propietario);
	}

	//Para crearPropietario
	private static final ResultadoCrearPropietario resultadoCrearCorrecto = new ResultadoCrearPropietario();
	private static final ResultadoCrearPropietario resultadoCrearNombreIncorrecto =
			new ResultadoCrearPropietario(ErrorCrearPropietario.Formato_Nombre_Incorrecto);
	private static final ResultadoCrearPropietario resultadoCrearApellidoIncorrecto =
			new ResultadoCrearPropietario(ErrorCrearPropietario.Formato_Apellido_Incorrecto);
	private static final ResultadoCrearPropietario resultadoCrearDocumentoIncorrecto =
			new ResultadoCrearPropietario(ErrorCrearPropietario.Formato_Documento_Incorrecto);
	private static final ResultadoCrearPropietario resultadoCrearTelefonoIncorrecto =
			new ResultadoCrearPropietario(ErrorCrearPropietario.Formato_Telefono_Incorrecto);
	private static final ResultadoCrearPropietario resultadoCrearEmailIncorrecto =
			new ResultadoCrearPropietario(ErrorCrearPropietario.Formato_Email_Incorrecto);
	private static final ResultadoCrearPropietario resultadoCrearDireccionIncorrecto =
			new ResultadoCrearPropietario(ErrorCrearPropietario.Formato_Direccion_Incorrecto);
	private static final ResultadoCrearPropietario resultadoCrearYaExiste =
			new ResultadoCrearPropietario(ErrorCrearPropietario.Ya_Existe_Propietario);

	//Para modificarPropietario
	private static final ResultadoModificarPropietario resultadoModificarCorrecto = new ResultadoModificarPropietario();
	private static final ResultadoModificarPropietario resultadoModificarApellidoIncorrecto =
			new ResultadoModificarPropietario(ErrorModificarPropietario.Formato_Apellido_Incorrecto);
	private static final ResultadoModificarPropietario resultadoModificarDocumentoIncorrecto =
			new ResultadoModificarPropietario(ErrorModificarPropietario.Formato_Documento_Incorrecto);
	private static final ResultadoModificarPropietario resultadoModificarNombreIncorrecto =
			new ResultadoModificarPropietario(ErrorModificarPropietario.Formato_Nombre_Incorrecto);
	private static final ResultadoModificarPropietario resultadoModificarTelefonoIncorrecto =
			new ResultadoModificarPropietario(ErrorModificarPropietario.Formato_Telefono_Incorrecto);
	private static final ResultadoModificarPropietario resultadoModificarYaSePoseeMismoDocumento =
			new ResultadoModificarPropietario(ErrorModificarPropietario.Ya_Existe_Propietario);
	private static final ResultadoModificarPropietario resultadoModificarEmailIncorrecto =
			new ResultadoModificarPropietario(ErrorModificarPropietario.Formato_Email_Incorrecto);
	private static final ResultadoModificarPropietario resultadoModificarDireccionIncorrecto =
			new ResultadoModificarPropietario(ErrorModificarPropietario.Formato_Direccion_Incorrecto);

	//Para eliminarPropietario
	private static final ResultadoEliminarPropietario resultadoEliminarCorrecto = new ResultadoEliminarPropietario();
}
