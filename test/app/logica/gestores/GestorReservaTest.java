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

import java.util.ArrayList;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;

import app.datos.clases.TipoDocumentoStr;
import app.datos.clases.TipoInmuebleStr;
import app.datos.entidades.Barrio;
import app.datos.entidades.Calle;
import app.datos.entidades.Cliente;
import app.datos.entidades.Direccion;
import app.datos.entidades.Estado;
import app.datos.entidades.Inmueble;
import app.datos.entidades.Localidad;
import app.datos.entidades.PDF;
import app.datos.entidades.Pais;
import app.datos.entidades.Propietario;
import app.datos.entidades.Provincia;
import app.datos.entidades.Reserva;
import app.datos.entidades.TipoDocumento;
import app.datos.entidades.TipoInmueble;
import app.datos.servicios.ReservaService;
import app.excepciones.GenerarPDFException;
import app.excepciones.GestionException;
import app.excepciones.ObjNotFoundException;
import app.excepciones.PersistenciaException;
import app.logica.resultados.ResultadoCrearReserva;
import app.logica.resultados.ResultadoCrearReserva.ErrorCrearReserva;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

/**
 * Este test prueba los métodos de la clase GestorReserva
 */
@RunWith(JUnitParamsRunner.class)
public class GestorReservaTest {

	//Prueba el método crearReserva(), el cual corresponde con la taskcard 25 de la iteración 2 y a la historia 7

	@Test
	@Parameters

	public void testCrearReserva(Reserva Reserva, ResultadoCrearReserva resultado, Throwable excepcion) throws Exception {
		GestorDatos gestorDatosMock = new GestorDatos() {

			@Override
			public ArrayList<Estado> obtenerEstados() throws PersistenciaException {
				return new ArrayList<>();
			}
		};
		GestorPDF gestorPDFMock = new GestorPDF() {

			@Override
			public PDF generarPDF(Reserva reserva) throws GestionException {
				if(excepcion != null && excepcion instanceof GestionException){
					throw (GestionException) excepcion;
				}
				return new PDF();
			}
		};
		ReservaService persistidorReservaMock = new ReservaService() {

			@Override
			public void guardarReserva(Reserva reserva) throws PersistenciaException {
				if(excepcion != null){
					if(excepcion instanceof PersistenciaException){
						throw (PersistenciaException) excepcion;
					}
					else if(!(excepcion instanceof GestionException)){
						new Integer("asd");
					}
				}
			}

			@Override
			public void modificarReserva(Reserva reserva) throws PersistenciaException {

			}

			@Override
			public ArrayList<Reserva> obtenerReservas(Cliente cliente) throws PersistenciaException {

				return null;
			}

			@Override
			public ArrayList<Reserva> obtenerReservas(Inmueble inmueble) throws PersistenciaException {

				return null;
			}

		};
		GestorReserva gestorReserva = new GestorReserva() {
			{
				this.gestorPDF = gestorPDFMock;
				this.persistidorReserva = persistidorReservaMock;
				this.gestorDatos = gestorDatosMock;
			}
		};

		//creamos la prueba
		Statement test = new Statement() {
			@Override
			public void evaluate() throws Throwable {
				if(resultado != null){
					assertEquals(resultado, gestorReserva.crearReserva(Reserva));
				}
				else{
					try{
						gestorReserva.crearReserva(Reserva);
						Assert.fail("Debería haber fallado!");
					} catch(PersistenciaException | GestionException e){
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
	 * Método que devuelve los parámetros para probar el método crearReserva()
	 *
	 * @return parámetros de prueba
	 */
	protected Object[] parametersForTestCrearReserva() {

		Propietario propietario = new Propietario()
				.setNombre("Esteban")
				.setApellido("Rebechi");

		Localidad localidad = new Localidad()
				.setProvincia(new Provincia()
						.setPais(new Pais()))
				.setNombre("Ceres");
		Direccion direccion = new Direccion()
				.setCalle(new Calle()
						.setNombre("Azquenaga"))
				.setLocalidad(localidad)
				.setBarrio(new Barrio()
						.setNombre("Vicente Zaspe"))
				.setNumero("3434")
				.setPiso("6")
				.setDepartamento("6B")
				.setOtros("Ala izquierda");

		Cliente cliente = new Cliente()
				.setNombre("Pablo")
				.setApellido("Van Derdonckt")
				.setTipoDocumento(new TipoDocumento().setTipo(TipoDocumentoStr.DNI))
				.setNumeroDocumento("36696969");

		Inmueble inmueble = new Inmueble() {
			@Override
			public Integer getId() {
				return 12345;
			};
		}.setTipo(new TipoInmueble(TipoInmuebleStr.DEPARTAMENTO))
				.setDireccion(direccion)
				.setPropietario(propietario);

		Date fechahoy = new Date();

		Reserva reservaCorrecta = new Reserva()
				.setCliente(cliente)
				.setInmueble(inmueble)
				.setImporte(300000.0)
				.setFechaInicio(fechahoy)
				.setFechaFin(fechahoy);
		Reserva reservaSinCliente = new Reserva()
				.setInmueble(inmueble)
				.setImporte(300000.0)
				.setFechaInicio(fechahoy)
				.setFechaFin(fechahoy);
		Reserva reservaSinNombreCliente = new Reserva()
				.setCliente(new Cliente()
						.setApellido("Van Derdonckt")
						.setTipoDocumento(new TipoDocumento().setTipo(TipoDocumentoStr.DNI))
						.setNumeroDocumento("36696969"))
				.setInmueble(inmueble)
				.setImporte(300000.0)
				.setFechaInicio(fechahoy)
				.setFechaFin(fechahoy);
		Reserva reservaSinApellidoCliente = new Reserva()
				.setCliente(new Cliente()
						.setNombre("Pablo")
						.setTipoDocumento(new TipoDocumento().setTipo(TipoDocumentoStr.DNI))
						.setNumeroDocumento("36696969"))
				.setInmueble(inmueble)
				.setImporte(300000.0)
				.setFechaInicio(fechahoy)
				.setFechaFin(fechahoy);
		Reserva reservaSinTipoDocumentoCliente = new Reserva()
				.setCliente(new Cliente()
						.setNombre("Pablo")
						.setApellido("Van Derdonckt")
						.setNumeroDocumento("36696969"))
				.setInmueble(inmueble)
				.setImporte(300000.0)
				.setFechaInicio(fechahoy)
				.setFechaFin(fechahoy);
		Reserva reservaSinNumeroDocumentoCliente = new Reserva()
				.setCliente(new Cliente()
						.setNombre("Pablo")
						.setApellido("Van Derdonckt")
						.setTipoDocumento(new TipoDocumento().setTipo(TipoDocumentoStr.DNI)))
				.setInmueble(inmueble)
				.setImporte(300000.0)
				.setFechaInicio(fechahoy)
				.setFechaFin(fechahoy);
		Reserva reservaSinInmueble = new Reserva()
				.setCliente(cliente)
				.setImporte(300000.0)
				.setFechaInicio(fechahoy)
				.setFechaFin(fechahoy);
		Reserva reservaSinPropietario = new Reserva()
				.setCliente(cliente)
				.setInmueble(new Inmueble() {
					@Override
					public Integer getId() {
						return 12345;
					};
				}
						.setTipo(new TipoInmueble(TipoInmuebleStr.DEPARTAMENTO))
						.setDireccion(direccion))
				.setImporte(300000.0)
				.setFechaInicio(fechahoy)
				.setFechaFin(fechahoy);
		Reserva reservaSinNombrePropietario = new Reserva()
				.setCliente(cliente)
				.setInmueble(new Inmueble() {
					@Override
					public Integer getId() {
						return 12345;
					};
				}
						.setTipo(new TipoInmueble(TipoInmuebleStr.DEPARTAMENTO))
						.setDireccion(direccion)
						.setPropietario(new Propietario()
								.setApellido("Rebechi")))
				.setImporte(300000.0)
				.setFechaInicio(fechahoy)
				.setFechaFin(fechahoy);
		Reserva reservaSinApellidoPropietario = new Reserva()
				.setCliente(cliente)
				.setInmueble(new Inmueble() {
					@Override
					public Integer getId() {
						return 12345;
					};
				}
						.setTipo(new TipoInmueble(TipoInmuebleStr.DEPARTAMENTO))
						.setDireccion(direccion)
						.setPropietario(new Propietario()
								.setNombre("Esteban")))
				.setImporte(300000.0)
				.setFechaInicio(fechahoy)
				.setFechaFin(fechahoy);
		Reserva reservaSinTipoInmueble = new Reserva()
				.setCliente(cliente)
				.setInmueble(new Inmueble() {
					@Override
					public Integer getId() {
						return 12345;
					};
				}
						.setDireccion(direccion)
						.setPropietario(propietario))
				.setImporte(300000.0)
				.setFechaInicio(fechahoy)
				.setFechaFin(fechahoy);
		Reserva reservaSinDireccionInmueble = new Reserva()
				.setCliente(cliente)
				.setInmueble(new Inmueble() {
					@Override
					public Integer getId() {
						return 12345;
					};
				}
						.setTipo(new TipoInmueble(TipoInmuebleStr.DEPARTAMENTO))
						.setPropietario(propietario))
				.setImporte(300000.0)
				.setFechaInicio(fechahoy)
				.setFechaFin(fechahoy);
		Reserva reservaSinLocalidadInmueble = new Reserva()
				.setCliente(cliente)
				.setInmueble(new Inmueble() {
					@Override
					public Integer getId() {
						return 12345;
					};
				}.setTipo(new TipoInmueble(TipoInmuebleStr.DEPARTAMENTO))
						.setDireccion(new Direccion()
								.setCalle(new Calle()
										.setNombre("Azquenaga"))
								.setBarrio(new Barrio()
										.setNombre("Vicente Zaspe"))
								.setNumero("3434")
								.setPiso("6")
								.setDepartamento("6B")
								.setOtros("Ala izquierda"))
						.setPropietario(propietario))
				.setImporte(300000.0)
				.setFechaInicio(fechahoy)
				.setFechaFin(fechahoy);
		Reserva reservaSinBarrioInmueble = new Reserva()
				.setCliente(cliente)
				.setInmueble(new Inmueble() {
					@Override
					public Integer getId() {
						return 12345;
					};
				}.setTipo(new TipoInmueble(TipoInmuebleStr.DEPARTAMENTO))
						.setDireccion(new Direccion()
								.setCalle(new Calle()
										.setNombre("Azquenaga"))
								.setLocalidad(localidad)
								.setNumero("3434")
								.setPiso("6")
								.setDepartamento("6B")
								.setOtros("Ala izquierda"))
						.setPropietario(propietario))
				.setImporte(300000.0)
				.setFechaInicio(fechahoy)
				.setFechaFin(fechahoy);
		Reserva reservaSinCalleInmueble = new Reserva()
				.setCliente(cliente)
				.setInmueble(new Inmueble() {
					@Override
					public Integer getId() {
						return 12345;
					};
				}.setTipo(new TipoInmueble(TipoInmuebleStr.DEPARTAMENTO))
						.setDireccion(new Direccion()
								.setLocalidad(localidad)
								.setBarrio(new Barrio()
										.setNombre("Vicente Zaspe"))
								.setNumero("3434")
								.setPiso("6")
								.setDepartamento("6B")
								.setOtros("Ala izquierda"))
						.setPropietario(propietario))
				.setImporte(300000.0)
				.setFechaInicio(fechahoy)
				.setFechaFin(fechahoy);
		Reserva reservaSinAlturaInmueble = new Reserva()
				.setCliente(cliente)
				.setInmueble(new Inmueble() {
					@Override
					public Integer getId() {
						return 12345;
					};
				}.setTipo(new TipoInmueble(TipoInmuebleStr.DEPARTAMENTO))
						.setDireccion(new Direccion()
								.setCalle(new Calle()
										.setNombre("Azquenaga"))
								.setLocalidad(localidad)
								.setBarrio(new Barrio()
										.setNombre("Vicente Zaspe"))
								.setPiso("6")
								.setDepartamento("6B")
								.setOtros("Ala izquierda"))
						.setPropietario(propietario))
				.setImporte(300000.0)
				.setFechaInicio(fechahoy)
				.setFechaFin(fechahoy);
		Reserva reservaSinFechaInicio = new Reserva()
				.setCliente(cliente)
				.setInmueble(inmueble)
				.setImporte(300000.0)
				.setFechaFin(fechahoy);
		Reserva reservaSinFechaFin = new Reserva()
				.setCliente(cliente)
				.setInmueble(inmueble)
				.setImporte(300000.0)
				.setFechaInicio(fechahoy);
		Reserva reservaSinImporte = new Reserva()
				.setCliente(cliente)
				.setInmueble(inmueble)
				.setFechaInicio(fechahoy)
				.setFechaFin(fechahoy);

		return new Object[] {
				new Object[] { reservaCorrecta, new ResultadoCrearReserva(), null }, //reserva correcta
				new Object[] { reservaSinCliente, new ResultadoCrearReserva(ErrorCrearReserva.Cliente_Vacío), null }, //reserva sin cliente
				new Object[] { reservaSinNombreCliente, new ResultadoCrearReserva(ErrorCrearReserva.Nombre_Cliente_Vacío), null }, //reserva sin nombre cliente
				new Object[] { reservaSinApellidoCliente, new ResultadoCrearReserva(ErrorCrearReserva.Apellido_Cliente_Vacío), null }, //reserva sin apellido cliente
				new Object[] { reservaSinTipoDocumentoCliente, new ResultadoCrearReserva(ErrorCrearReserva.TipoDocumento_Cliente_Vacío), null }, //reserva sin tipo documento en el cliente
				new Object[] { reservaSinNumeroDocumentoCliente, new ResultadoCrearReserva(ErrorCrearReserva.NúmeroDocumento_Cliente_Vacío), null }, //reserva sin numero de documento en el cliente
				new Object[] { reservaSinInmueble, new ResultadoCrearReserva(ErrorCrearReserva.Inmueble_Vacío), null }, //reserva sin inmueble
				new Object[] { reservaSinPropietario, new ResultadoCrearReserva(ErrorCrearReserva.Propietario_Vacío), null }, //reserva sin Propietario
				new Object[] { reservaSinNombrePropietario, new ResultadoCrearReserva(ErrorCrearReserva.Nombre_Propietario_Vacío), null }, //reserva sin nombre de Propietario
				new Object[] { reservaSinApellidoPropietario, new ResultadoCrearReserva(ErrorCrearReserva.Apellido_Propietario_Vacío), null }, //reserva sin apellido de Propietario
				new Object[] { reservaSinTipoInmueble, new ResultadoCrearReserva(ErrorCrearReserva.Tipo_Inmueble_Vacío), null }, //reserva sin tipo de Inmueble
				new Object[] { reservaSinDireccionInmueble, new ResultadoCrearReserva(ErrorCrearReserva.Dirección_Inmueble_Vacía), null }, //reserva sin direccion de inmueble
				new Object[] { reservaSinLocalidadInmueble, new ResultadoCrearReserva(ErrorCrearReserva.Localidad_Inmueble_Vacía), null }, //reserva sin localidad de inmueble
				new Object[] { reservaSinBarrioInmueble, new ResultadoCrearReserva(ErrorCrearReserva.Barrio_Inmueble_Vacío), null }, //reserva sin Barrio de inmueble
				new Object[] { reservaSinCalleInmueble, new ResultadoCrearReserva(ErrorCrearReserva.Calle_Inmueble_Vacía), null }, //reserva sin calle de inmueble
				new Object[] { reservaSinAlturaInmueble, new ResultadoCrearReserva(ErrorCrearReserva.Altura_Inmueble_Vacía), null }, //reserva sin altura de calle de inmueble
				new Object[] { reservaSinFechaInicio, new ResultadoCrearReserva(ErrorCrearReserva.FechaInicio_vacía), null }, //reserva sin fecha de inicio vacía
				new Object[] { reservaSinFechaFin, new ResultadoCrearReserva(ErrorCrearReserva.FechaFin_vacía), null }, //reserva sin fecha de fin vacía
				new Object[] { reservaSinImporte, new ResultadoCrearReserva(ErrorCrearReserva.Importe_vacío), null }, //reserva sin Importe
				new Object[] { reservaCorrecta, null, new GenerarPDFException(new Exception()) }, //el gestorPDF tira una excepción
				new Object[] { reservaCorrecta, null, new ObjNotFoundException("", new Exception()) }, //el persistidor tira una excepción
				new Object[] { reservaCorrecta, null, new Exception() } //el persistidor tira una excepción inesperada
		};
	}
}
