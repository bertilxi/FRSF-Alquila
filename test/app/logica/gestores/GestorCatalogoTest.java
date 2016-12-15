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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import app.datos.clases.CatalogoVista;
import app.datos.clases.TipoInmuebleStr;
import app.datos.entidades.Barrio;
import app.datos.entidades.Cliente;
import app.datos.entidades.Direccion;
import app.datos.entidades.Imagen;
import app.datos.entidades.Inmueble;
import app.datos.entidades.Localidad;
import app.datos.entidades.PDF;
import app.datos.entidades.TipoInmueble;
import app.excepciones.GenerarPDFException;
import app.excepciones.GestionException;
import app.logica.resultados.ResultadoCrearCatalogo;
import app.logica.resultados.ResultadoCrearCatalogo.ErrorCrearCatalogo;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class GestorCatalogoTest {

	protected Object[] parametersForTestCrearCatalogo() {
		//Parámetros de JUnitParams
		return new Object[] {
				//Casos de prueba
				//cliente, idSet, tipoInmueble, direccion, localidad, barrio, precio, resultadoCrearCatalogoEsperado, excepcion, excepcionEsperada, llamaACrearPDF
				/* 0 */ new Object[] { new Cliente(), 1, new TipoInmueble(TipoInmuebleStr.CASA), new Direccion(), new Localidad(), new Barrio(), 1.0, resultadoCrearCorrecto, null, null, 1 }, //Test correcto
				/* 1 */ new Object[] { null, 1, new TipoInmueble(TipoInmuebleStr.CASA), new Direccion(), new Localidad(), new Barrio(), 1.0, resultadoCrearCliente_inexistente, null, null, 0 }, //Cliente vacío
				/* 2 */ new Object[] { new Cliente(), null, new TipoInmueble(TipoInmuebleStr.CASA), new Direccion(), new Localidad(), new Barrio(), 1.0, resultadoCrearCodigo_Inmueble_Inexistente, null, null, 0 }, //Código de inmueble vacío
				/* 3 */ new Object[] { new Cliente(), 1, null, new Direccion(), new Localidad(), new Barrio(), 1.0, resultadoCrearTipo_Inmueble_Inexistente, null, null, 0 }, //Tipo de inmueble vacío
				/* 4 */ new Object[] { new Cliente(), 1, new TipoInmueble(TipoInmuebleStr.CASA), null, new Localidad(), new Barrio(), 1.0, resultadoCrearDireccion_Inmueble_Inexistente, null, null, 0 }, //Dirección de inmueble vacía
				/* 5 */ new Object[] { new Cliente(), 1, new TipoInmueble(TipoInmuebleStr.CASA), new Direccion(), null, new Barrio(), 1.0, resultadoCrearLocalidad_Inmueble_Inexistente, null, null, 0 }, //Localidad de inmueble vacía
				/* 6 */ new Object[] { new Cliente(), 1, new TipoInmueble(TipoInmuebleStr.CASA), new Direccion(), new Localidad(), null, 1.0, resultadoCrearBarrio_Inmueble_Inexistente, null, null, 0 }, //Barrio de inmueble vacío
				/* 7 */ new Object[] { new Cliente(), 1, new TipoInmueble(TipoInmuebleStr.CASA), new Direccion(), new Localidad(), new Barrio(), null, resultadoCrearPrecio_Inmueble_Inexistente, null, null, 0 }, //Barrio de inmueble vacío
				/* 8 */ new Object[] { new Cliente(), 1, new TipoInmueble(TipoInmuebleStr.CASA), new Direccion(), new Localidad(), new Barrio(), 1.0, null, new GenerarPDFException(new Throwable()), new GenerarPDFException(new Throwable()), 1 } //El gestorPDF tira una excepción
		};
	}

	/**
	 * Prueba el método crearCatalogo(), el cual corresponde con la taskcard 23 de la iteración 2
	 * 
	 * @param cliente
	 * 			cliente al que generarle el catálogo
	 * @param idSet
	 * 			código del inmueble para el catálogo
	 * @param tipoInmueble
	 * 			tipo del inmueble para el catálogo
	 * @param direccion
	 * 			dirección del inmueble para el catálogo
	 * @param localidad
	 * 			localidad del inmueble para el catálogo
	 * @param barrio
	 * 			barrio del inmueble para el catálogo
	 * @param precio
	 * 			precio del inmueble para el catálogo
	 * @param resultadoCrearCatalogoEsperado
	 * 			resultado esperado del test
	 * @param excepcion
	 * 			excepcion arrojada por el gestorPDF
	 * @param excepcionEsperada
	 * 			excepción esperada que sea lanzada por el método
	 * @param llamaACrearPDF
	 * 			indica si se debe llamar a crear el pdf
	 * @throws Exception
	 */
	@Test
	@Parameters
	public void testCrearCatalogo(Cliente cliente, Integer idSet, TipoInmueble tipoInmueble, Direccion direccion, Localidad localidad, Barrio barrio, Double precio, ResultadoCrearCatalogo resultadoCrearCatalogoEsperado, GestionException excepcion, GestionException excepcionEsperada, Integer llamaACrearPDF) throws Exception {
		//Inicialización de los mocks y el catálogo que se usará para probar
		GestorPDF gestorPDFMock = mock(GestorPDF.class);
		if(direccion != null){
			direccion.setLocalidad(localidad).setBarrio(barrio);
		}
		Inmueble inmueble = new Inmueble() {
			@Override
			public Integer getId() {
				return idSet;
			}
		};
		inmueble.setTipo(tipoInmueble)
				.setDireccion(direccion)
				.setPrecio(precio);
		HashMap<Inmueble, Imagen> inmuebles = new HashMap<>();
		inmuebles.put(inmueble, null);
		CatalogoVista catalogoVista = new CatalogoVista(cliente, inmuebles);

		//Clase anónima necesaria para inyectar dependencias
		GestorCatalogo gestorCatalogo = new GestorCatalogo() {
			{
				this.gestorPDF = gestorPDFMock;
			}
		};

		//Setear valores esperados a los mocks
		if(excepcion != null){
			when(gestorPDFMock.generarPDF(any(CatalogoVista.class))).thenThrow(excepcion);
		}
		else{
			when(gestorPDFMock.generarPDF(any(CatalogoVista.class))).thenReturn(new PDF());
		}

		//Llamar al método a testear y comprobar resultados obtenidos, que se llaman a los métodos deseados y con los parámetros correctos
		if(excepcionEsperada == null){
			assertEquals(resultadoCrearCatalogoEsperado.getErrores(), gestorCatalogo.crearCatalogo(catalogoVista).getErrores());
		}
		else{
			try{
				gestorCatalogo.crearCatalogo(catalogoVista);
				Assert.fail("Debería haber fallado!");
			} catch(Exception e){
				assertEquals(excepcionEsperada.getClass(), e.getClass());
			}
		}

		verify(gestorPDFMock, times(llamaACrearPDF)).generarPDF(catalogoVista);
	}

	//Para crearCatalogo
	private static final ResultadoCrearCatalogo resultadoCrearCorrecto = new ResultadoCrearCatalogo(null);
	private static final ResultadoCrearCatalogo resultadoCrearCliente_inexistente =
			new ResultadoCrearCatalogo(null, ErrorCrearCatalogo.Cliente_inexistente);
	private static final ResultadoCrearCatalogo resultadoCrearCodigo_Inmueble_Inexistente =
			new ResultadoCrearCatalogo(null, ErrorCrearCatalogo.Codigo_Inmueble_Inexistente);
	private static final ResultadoCrearCatalogo resultadoCrearTipo_Inmueble_Inexistente =
			new ResultadoCrearCatalogo(null, ErrorCrearCatalogo.Tipo_Inmueble_Inexistente);
	private static final ResultadoCrearCatalogo resultadoCrearLocalidad_Inmueble_Inexistente =
			new ResultadoCrearCatalogo(null, ErrorCrearCatalogo.Localidad_Inmueble_Inexistente);
	private static final ResultadoCrearCatalogo resultadoCrearDireccion_Inmueble_Inexistente =
			new ResultadoCrearCatalogo(null, ErrorCrearCatalogo.Direccion_Inmueble_Inexistente);
	private static final ResultadoCrearCatalogo resultadoCrearBarrio_Inmueble_Inexistente =
			new ResultadoCrearCatalogo(null, ErrorCrearCatalogo.Barrio_Inmueble_Inexistente);
	private static final ResultadoCrearCatalogo resultadoCrearPrecio_Inmueble_Inexistente =
			new ResultadoCrearCatalogo(null, ErrorCrearCatalogo.Precio_Inmueble_Inexistente);
}