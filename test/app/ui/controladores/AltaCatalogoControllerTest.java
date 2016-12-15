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
package app.ui.controladores;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;

import app.datos.clases.CatalogoVista;
import app.datos.entidades.Cliente;
import app.datos.entidades.Imagen;
import app.datos.entidades.Inmueble;
import app.datos.entidades.PDF;
import app.excepciones.ObjNotFoundException;
import app.excepciones.PersistenciaException;
import app.logica.CoordinadorJavaFX;
import app.logica.resultados.ResultadoCrearCatalogo;
import app.logica.resultados.ResultadoCrearCatalogo.ErrorCrearCatalogo;
import app.ui.componentes.ventanas.PresentadorVentanas;
import app.ui.componentes.ventanas.mock.PresentadorVentanasMock;
import app.ui.controladores.resultado.ResultadoControlador;
import app.ui.controladores.resultado.ResultadoControlador.ErrorControlador;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

/**
 * Este test prueba los métodos de la clase AltaCatalogoController
 */
@RunWith(JUnitParamsRunner.class)
public class AltaCatalogoControllerTest {

	@Test
	@Parameters
	/**
	 * Prueba el método generarCatalogo(), el cual corresponde con la taskcard 22 de la iteración 2 y a la historia 5
	 * 
	 * @param inmueblesEImagen
	 * 				son pares de inmueble e imagen seleccionados por el usuario
	 * @param cliente
	 * 				es el cliente seleccionado por el usuario
	 * @param resultadoControlador
	 * 				es el resultado que se espera de llamar el metodo de AltaCatalogoController
	 * @param resultadoLogica
	 * 				es el resultado que el controlador recibirá del coordinador
	 * @param excepcion
	 * 				es la excepción que el coordinador debe tirar
	 * @throws Exception
	 */
	public void testCrearInmueble(
			Map<Inmueble, Imagen> inmueblesEImagen,
			Cliente cliente,
			ResultadoControlador resultadoControlador,
			ResultadoCrearCatalogo resultadoLogica,
			Throwable excepcion) throws Exception {

		CoordinadorJavaFX coordinadorMock = new CoordinadorJavaFX() {
			@Override
			public ResultadoCrearCatalogo crearCatalogo(CatalogoVista catalogoVista) throws PersistenciaException {
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
			public ArrayList<Cliente> obtenerClientes() throws PersistenciaException {
				ArrayList<Cliente> clientes = new ArrayList<>();
				clientes.add(cliente);
				return clientes;
			}
		};
		PresentadorVentanas presentadorMock = new PresentadorVentanasMock();

		AltaCatalogoController altaCatalogoController = new AltaCatalogoController() {
			@Override
			public ResultadoControlador generarCatalogo() {
				cbCliente.getSelectionModel().select(cliente);
				try{
					for(Inmueble inmueble: inmueblesEImagen.keySet()){
						RenglonInmuebleController renglonController = new RenglonInmuebleController(inmueble){
							@Override
							public Imagen getFotoSeleccionada() {
								return inmueblesEImagen.get(inmueble);
							}
							
							@Override
							protected void inicializar(URL location, ResourceBundle resources) {

							}
						};
						listaInmuebles.getChildren().add(renglonController.getRoot());
						renglones.put(renglonController.getRoot(), renglonController);
					}
				} catch(IOException e){
					
				}
				
				return super.generarCatalogo();
			}
			
			@Override
			protected void inicializar(URL location, ResourceBundle resources) {

			}
			
			@Override
			protected void mostrarPDF(PDF pdf) {
				
			}
			
			@Override
			public void salir() {
				
			}
		};
		
		altaCatalogoController.setCoordinador(coordinadorMock);
		altaCatalogoController.setPresentador(presentadorMock);

		ControladorTest corredorTestEnJavaFXThread = new ControladorTest(AltaCatalogoController.URLVista, altaCatalogoController);

		altaCatalogoController.setStage(corredorTestEnJavaFXThread.getStagePrueba());

		Statement test = new Statement() {
			@Override
			public void evaluate() throws Throwable {
				assertEquals(resultadoControlador, altaCatalogoController.generarCatalogo());
			}
		};

		try{
			corredorTestEnJavaFXThread.apply(test, null).evaluate();
		} catch(Throwable e){
			throw new Exception(e);
		}
	}

	/**
	 * Método que devuelve los parámetros para probar el método generarCatalogo()
	 *
	 * @return parámetros de prueba
	 */
	protected Object[] parametersForTestCrearInmueble() {
		 
		Map<Inmueble,Imagen> inmueblesEImagenes = new HashMap<>();
		inmueblesEImagenes.put(new Inmueble(){
			@Override
			public String toString() {				
				return "Inmueble 1";
			}
		}, new Imagen());
		inmueblesEImagenes.put(new Inmueble(){
			@Override
			public String toString() {
				return "Inmueble 2";
			}
		}, new Imagen());
		
		Cliente cliente = new Cliente();

		ResultadoControlador resultadoControladorCorrecto = new ResultadoControlador();
		ResultadoCrearCatalogo resultadoLogicaCorrecto = new ResultadoCrearCatalogo(null);

		ResultadoControlador resultadoControladorCamposVacios = new ResultadoControlador(ErrorControlador.Campos_Vacios);
		ResultadoControlador resultadoControladorErrorPersistencia = new ResultadoControlador(ErrorControlador.Error_Persistencia);
		ResultadoControlador resultadoControladorErrorDesconocido = new ResultadoControlador(ErrorControlador.Error_Desconocido);

		ResultadoCrearCatalogo resultadoLogicaClienteInexistente = new ResultadoCrearCatalogo(null, ErrorCrearCatalogo.Cliente_inexistente);
		ResultadoCrearCatalogo resultadoLogicaCodigoInmuebleInexistente = new ResultadoCrearCatalogo(null, ErrorCrearCatalogo.Codigo_Inmueble_Inexistente);
		ResultadoCrearCatalogo resultadoLogicaTipoInmuebleInexistente = new ResultadoCrearCatalogo(null, ErrorCrearCatalogo.Tipo_Inmueble_Inexistente);
		ResultadoCrearCatalogo resultadoLogicaLocalidadInmuebleInexistente = new ResultadoCrearCatalogo(null, ErrorCrearCatalogo.Localidad_Inmueble_Inexistente);
		ResultadoCrearCatalogo resultadoLogicaDireccionInmuebleInexistente = new ResultadoCrearCatalogo(null, ErrorCrearCatalogo.Direccion_Inmueble_Inexistente);
		ResultadoCrearCatalogo resultadoLogicaBarrioInmuebleInexistente = new ResultadoCrearCatalogo(null, ErrorCrearCatalogo.Barrio_Inmueble_Inexistente);
		ResultadoCrearCatalogo resultadoLogicaPrecioInmuebleInexistente = new ResultadoCrearCatalogo(null, ErrorCrearCatalogo.Precio_Inmueble_Inexistente);
		ResultadoCrearCatalogo resultadoLogicaBarrioYPrecioInexistentes = new ResultadoCrearCatalogo(null, ErrorCrearCatalogo.Barrio_Inmueble_Inexistente, ErrorCrearCatalogo.Precio_Inmueble_Inexistente);

		Throwable excepcionPersistencia = new ObjNotFoundException("", new Exception());
		Throwable excepcionInesperada = new Exception();

		return new Object[] {
				
				new Object[] { inmueblesEImagenes, cliente, resultadoControladorCorrecto, resultadoLogicaCorrecto, null }, //test todo anda bien
				new Object[] { inmueblesEImagenes, cliente, resultadoControladorCamposVacios, resultadoLogicaClienteInexistente, null }, //test cliente vacío
				new Object[] { inmueblesEImagenes, cliente, resultadoControladorCamposVacios, resultadoLogicaCodigoInmuebleInexistente, null }, //test codigo de inmueble vacío
				new Object[] { inmueblesEImagenes, cliente, resultadoControladorCamposVacios, resultadoLogicaTipoInmuebleInexistente, null }, //test tipo de inmueble vacío
				new Object[] { inmueblesEImagenes, cliente, resultadoControladorCamposVacios, resultadoLogicaLocalidadInmuebleInexistente, null }, //test localidad del inmueble vacía
				new Object[] { inmueblesEImagenes, cliente, resultadoControladorCamposVacios, resultadoLogicaDireccionInmuebleInexistente, null }, //test direccion del inmueble vacía
				new Object[] { inmueblesEImagenes, cliente, resultadoControladorCamposVacios, resultadoLogicaBarrioInmuebleInexistente, null }, //test barrio del inmueble vacío
				new Object[] { inmueblesEImagenes, cliente, resultadoControladorCamposVacios, resultadoLogicaPrecioInmuebleInexistente, null }, //test precio del inmueble vacío
				new Object[] { inmueblesEImagenes, cliente, resultadoControladorCamposVacios, resultadoLogicaBarrioYPrecioInexistentes, null }, //test barrio y precio del inmueble vacíos
				new Object[] { inmueblesEImagenes, cliente, resultadoControladorErrorPersistencia, null, excepcionPersistencia }, //test excepción de persistencia
				new Object[] { inmueblesEImagenes, cliente, resultadoControladorErrorDesconocido, null, excepcionInesperada }, //test escepción inesperada
		};
	}
}