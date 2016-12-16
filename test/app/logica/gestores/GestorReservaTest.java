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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.mail.MessagingException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;

import app.datos.clases.EstadoInmuebleStr;
import app.datos.clases.EstadoStr;
import app.datos.clases.TipoDocumentoStr;
import app.datos.clases.TipoInmuebleStr;
import app.datos.entidades.Archivo;
import app.datos.entidades.Barrio;
import app.datos.entidades.Calle;
import app.datos.entidades.Cliente;
import app.datos.entidades.Direccion;
import app.datos.entidades.Estado;
import app.datos.entidades.EstadoInmueble;
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
import app.logica.resultados.ResultadoEliminarReserva;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

/**
 * Este test prueba los métodos de la clase GestorReserva
 */
@RunWith(JUnitParamsRunner.class)
public class GestorReservaTest {

	/**
	 * Método que devuelve los parámetros para probar el método crearReserva()
	 *
	 * @return parámetros de prueba
	 */
	protected Object[] parametersForTestCrearReserva() {
		Calendar calendarFechaMañana = Calendar.getInstance();

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

		EstadoInmueble estadoInmueble = new EstadoInmueble().setEstado(EstadoInmuebleStr.NO_VENDIDO);

		Inmueble inmueble = new Inmueble() {
			@Override
			public Integer getId() {
				return 12345;
			};

			@Override
			public Set<Reserva> getReservas() {
				return new HashSet<>();
			};
		}.setTipo(new TipoInmueble(TipoInmuebleStr.DEPARTAMENTO))
				.setDireccion(direccion)
				.setPropietario(propietario)
				.setEstadoInmueble(estadoInmueble);

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

					@Override
					public Set<Reserva> getReservas() {
						return new HashSet<>();
					};
				}
						.setTipo(new TipoInmueble(TipoInmuebleStr.DEPARTAMENTO))
						.setDireccion(direccion)
						.setEstadoInmueble(estadoInmueble))
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

					@Override
					public Set<Reserva> getReservas() {
						return new HashSet<>();
					};
				}
						.setTipo(new TipoInmueble(TipoInmuebleStr.DEPARTAMENTO))
						.setDireccion(direccion)
						.setPropietario(new Propietario()
								.setApellido("Rebechi"))
						.setEstadoInmueble(estadoInmueble))
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

					@Override
					public Set<Reserva> getReservas() {
						return new HashSet<>();
					};
				}
						.setTipo(new TipoInmueble(TipoInmuebleStr.DEPARTAMENTO))
						.setDireccion(direccion)
						.setPropietario(new Propietario()
								.setNombre("Esteban"))
						.setEstadoInmueble(estadoInmueble))
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

					@Override
					public Set<Reserva> getReservas() {
						return new HashSet<>();
					};
				}
						.setDireccion(direccion)
						.setPropietario(propietario)
						.setEstadoInmueble(estadoInmueble))
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

					@Override
					public Set<Reserva> getReservas() {
						return new HashSet<>();
					};
				}
						.setTipo(new TipoInmueble(TipoInmuebleStr.DEPARTAMENTO))
						.setPropietario(propietario)
						.setEstadoInmueble(estadoInmueble))
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

					@Override
					public Set<Reserva> getReservas() {
						return new HashSet<>();
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
						.setPropietario(propietario)
						.setEstadoInmueble(estadoInmueble))
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

					@Override
					public Set<Reserva> getReservas() {
						return new HashSet<>();
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
						.setPropietario(propietario)
						.setEstadoInmueble(estadoInmueble))
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

					@Override
					public Set<Reserva> getReservas() {
						return new HashSet<>();
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
						.setPropietario(propietario)
						.setEstadoInmueble(estadoInmueble))
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

					@Override
					public Set<Reserva> getReservas() {
						return new HashSet<>();
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
						.setPropietario(propietario)
						.setEstadoInmueble(estadoInmueble))
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

		calendarFechaMañana.setTime(fechahoy);
		calendarFechaMañana.add(Calendar.DATE, 1);
		Reserva reservaFechaInicioPosteriorAFechaFin = new Reserva()
				.setCliente(cliente)
				.setInmueble(inmueble)
				.setImporte(300000.0)
				.setFechaInicio(calendarFechaMañana.getTime())
				.setFechaFin(fechahoy);

		Reserva reservaImporteMenorIgualCero = new Reserva()
				.setCliente(cliente)
				.setInmueble(inmueble)
				.setImporte(-300000.0)
				.setFechaInicio(fechahoy)
				.setFechaFin(fechahoy);

		Reserva reservaInmuebleVendido = new Reserva()
				.setCliente(cliente)
				.setInmueble(new Inmueble() {
					@Override
					public Integer getId() {
						return 12345;
					};

					@Override
					public Set<Reserva> getReservas() {
						return new HashSet<>();
					};
				}.setTipo(new TipoInmueble(TipoInmuebleStr.DEPARTAMENTO))
						.setDireccion(direccion)
						.setPropietario(propietario)
						.setEstadoInmueble(new EstadoInmueble().setEstado(EstadoInmuebleStr.VENDIDO)))
				.setImporte(300000.0)
				.setFechaInicio(fechahoy)
				.setFechaFin(fechahoy);

		Reserva reservaConflictiva = new Reserva()
				.setCliente(cliente)
				.setImporte(300000.0)
				.setFechaInicio(fechahoy)
				.setFechaFin(calendarFechaMañana.getTime())
				.setEstado(new Estado(EstadoStr.ALTA));
		reservaConflictiva.setInmueble(new Inmueble() {
			@Override
			public Set<Reserva> getReservas() {
				Set<Reserva> reservas = new HashSet<>();
				reservas.add(reservaConflictiva);
				return reservas;
			}

			@Override
			public Integer getId() {
				return 12345;
			};
		}.setTipo(new TipoInmueble(TipoInmuebleStr.DEPARTAMENTO))
				.setDireccion(direccion)
				.setPropietario(propietario)
				.setEstadoInmueble(estadoInmueble));

		return new Object[] {
				//Casos de prueba
				//reserva, resultado, excepcion
				/* 0 */new Object[] { reservaCorrecta, new ResultadoCrearReserva(null), null }, //reserva correcta
				/* 1 */new Object[] { reservaSinCliente, new ResultadoCrearReserva(null, ErrorCrearReserva.Cliente_Vacío), null }, //reserva sin cliente
				/* 2 */new Object[] { reservaSinNombreCliente, new ResultadoCrearReserva(null, ErrorCrearReserva.Nombre_Cliente_Vacío), null }, //reserva sin nombre cliente
				/* 3 */new Object[] { reservaSinApellidoCliente, new ResultadoCrearReserva(null, ErrorCrearReserva.Apellido_Cliente_Vacío), null }, //reserva sin apellido cliente
				/* 4 */new Object[] { reservaSinTipoDocumentoCliente, new ResultadoCrearReserva(null, ErrorCrearReserva.TipoDocumento_Cliente_Vacío), null }, //reserva sin tipo documento en el cliente
				/* 5 */new Object[] { reservaSinNumeroDocumentoCliente, new ResultadoCrearReserva(null, ErrorCrearReserva.NúmeroDocumento_Cliente_Vacío), null }, //reserva sin numero de documento en el cliente
				/* 6 */new Object[] { reservaSinInmueble, new ResultadoCrearReserva(null, ErrorCrearReserva.Inmueble_Vacío), null }, //reserva sin inmueble
				/* 7 */new Object[] { reservaSinPropietario, new ResultadoCrearReserva(null, ErrorCrearReserva.Propietario_Vacío), null }, //reserva sin Propietario
				/* 8 */new Object[] { reservaSinNombrePropietario, new ResultadoCrearReserva(null, ErrorCrearReserva.Nombre_Propietario_Vacío), null }, //reserva sin nombre de Propietario
				/* 9 */new Object[] { reservaSinApellidoPropietario, new ResultadoCrearReserva(null, ErrorCrearReserva.Apellido_Propietario_Vacío), null }, //reserva sin apellido de Propietario
				/* 10 */new Object[] { reservaSinTipoInmueble, new ResultadoCrearReserva(null, ErrorCrearReserva.Tipo_Inmueble_Vacío), null }, //reserva sin tipo de Inmueble
				/* 11 */new Object[] { reservaSinDireccionInmueble, new ResultadoCrearReserva(null, ErrorCrearReserva.Dirección_Inmueble_Vacía), null }, //reserva sin direccion de inmueble
				/* 12 */new Object[] { reservaSinLocalidadInmueble, new ResultadoCrearReserva(null, ErrorCrearReserva.Localidad_Inmueble_Vacía), null }, //reserva sin localidad de inmueble
				/* 13 */new Object[] { reservaSinBarrioInmueble, new ResultadoCrearReserva(null, ErrorCrearReserva.Barrio_Inmueble_Vacío), null }, //reserva sin Barrio de inmueble
				/* 14 */new Object[] { reservaSinCalleInmueble, new ResultadoCrearReserva(null, ErrorCrearReserva.Calle_Inmueble_Vacía), null }, //reserva sin calle de inmueble
				/* 15 */new Object[] { reservaSinAlturaInmueble, new ResultadoCrearReserva(null, ErrorCrearReserva.Altura_Inmueble_Vacía), null }, //reserva sin altura de calle de inmueble
				/* 16 */new Object[] { reservaSinFechaInicio, new ResultadoCrearReserva(null, ErrorCrearReserva.FechaInicio_vacía), null }, //reserva sin fecha de inicio vacía
				/* 17 */new Object[] { reservaSinFechaFin, new ResultadoCrearReserva(null, ErrorCrearReserva.FechaFin_vacía), null }, //reserva sin fecha de fin vacía
				/* 18 */new Object[] { reservaFechaInicioPosteriorAFechaFin, new ResultadoCrearReserva(null, ErrorCrearReserva.Fecha_Inicio_Posterior_A_Fecha_Fin), null }, //reserva con fecha inicio posterior a fecha fin
				/* 19 */new Object[] { reservaConflictiva, new ResultadoCrearReserva(reservaConflictiva, ErrorCrearReserva.Existe_Otra_Reserva_Activa), null }, //existe otra reserva en el mismo rango de fechas
				/* 20 */new Object[] { reservaSinImporte, new ResultadoCrearReserva(null, ErrorCrearReserva.Importe_Vacío), null }, //reserva sin Importe
				/* 21 */new Object[] { reservaImporteMenorIgualCero, new ResultadoCrearReserva(null, ErrorCrearReserva.Importe_Menor_O_Igual_A_Cero), null }, //reserva con importe menor o igual a cero
				/* 22 */new Object[] { reservaInmuebleVendido, new ResultadoCrearReserva(null, ErrorCrearReserva.Inmueble_Vendido), null }, //reserva con inmueble vendido
				/* 23 */new Object[] { reservaCorrecta, null, new GenerarPDFException(new Exception()) }, //el gestorPDF tira una excepción
				/* 24 */new Object[] { reservaCorrecta, null, new ObjNotFoundException("", new Exception()) }, //el persistidor tira una excepción
				/* 25 */new Object[] { reservaCorrecta, null, new Exception() } //el persistidor tira una excepción inesperada
		};
	}

	/**
	 * Prueba el método crearReserva(), el cual corresponde con la taskcard 25 de la iteración 2 y a la historia 7
	 *
	 * @param reserva
	 *            reserva a crear
	 * @param resultado
	 *            resultado que se espera que devuelva el gestor
	 * @param excepcion
	 *            es la excepcion que debe lanzar el mock del persistidor o del gestorPDF, si la prueba involucra procesar una excepcion de dicho persistidor/gestor, debe ser nulo propietario para que
	 *            se use
	 * @throws Exception
	 */
	@Test
	@Parameters
	public void testCrearReserva(Reserva reserva, ResultadoCrearReserva resultado, Throwable excepcion) throws Exception {
		//Se crean los mocks de la prueba
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
		GestorEmail gestorEmailMock = new GestorEmail(true) {
			@Override
			public void enviarEmail(String destinatario, String asunto, String mensaje, Archivo archivo)
					throws IOException, MessagingException {

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

		//Se crea el gestor a probar, se sobreescriben algunos métodos para setear los mocks
		GestorReserva gestorReserva = new GestorReserva() {
			{
				this.gestorPDF = gestorPDFMock;
				this.persistidorReserva = persistidorReservaMock;
				this.gestorDatos = gestorDatosMock;
				this.gestorEmail = gestorEmailMock;
			}
		};

		//Creamos la prueba
		Statement test = new Statement() {
			@Override
			public void evaluate() throws Throwable {
				if(resultado != null){
					//Se hacen las verificaciones pertinentes para comprobar que el gestor se comporte adecuadamente
					assertEquals(resultado, gestorReserva.crearReserva(reserva));
				}
				else{
					try{
						gestorReserva.crearReserva(reserva);
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
	 * Método que devuelve los parámetros para probar el método eliminarReserva()
	 *
	 * @return parámetros de prueba
	 */
	protected Object[] parametersForTestEliminarReserva() {

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

			@Override
			public Set<Reserva> getReservas() {
				return new HashSet<>();
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

		return new Object[] {
				//Casos de prueba
				//reserva, resultado, excepcion
				/* 0 */new Object[] { reservaCorrecta, new ResultadoEliminarReserva(), null }, //reserva correcta
				/* 1 */new Object[] { reservaCorrecta, null, new ObjNotFoundException("", new Exception()) }, //el persistidor tira una excepción
				/* 2 */new Object[] { reservaCorrecta, null, new Exception() } //el persistidor tira una excepción inesperada
		};
	}

	/**
	 * Prueba el método eliminarReserva(), el cual corresponde con la taskcard 25 de la iteración 2 y a la historia 7
	 *
	 * @param reserva
	 *            reserva a eliminar
	 * @param resultado
	 *            resultado que se espera que devuelva el gestor
	 * @param excepcion
	 *            es la excepcion que debe lanzar el mock del persistidor, si la prueba involucra procesar una excepcion de dicho persistidor, debe ser nulo propietario para que
	 *            se use
	 * @throws Exception
	 */
	@Test
	@Parameters
	public void testEliminarReserva(Reserva reserva, ResultadoEliminarReserva resultado, Throwable excepcion) throws Exception {
		//Se crean los mocks de la prueba
		GestorDatos gestorDatosMock = new GestorDatos() {

			@Override
			public ArrayList<Estado> obtenerEstados() throws PersistenciaException {
				return new ArrayList<>();
			}
		};
		ReservaService persistidorReservaMock = new ReservaService() {

			@Override
			public void guardarReserva(Reserva reserva) throws PersistenciaException {

			}

			@Override
			public void modificarReserva(Reserva reserva) throws PersistenciaException {
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
			public ArrayList<Reserva> obtenerReservas(Cliente cliente) throws PersistenciaException {

				return null;
			}

			@Override
			public ArrayList<Reserva> obtenerReservas(Inmueble inmueble) throws PersistenciaException {

				return null;
			}

		};

		//Se crea el gestor a probar, se sobreescriben algunos métodos para setear los mocks
		GestorReserva gestorReserva = new GestorReserva() {
			{
				this.persistidorReserva = persistidorReservaMock;
				this.gestorDatos = gestorDatosMock;
			}
		};

		//Creamos la prueba
		Statement test = new Statement() {
			@Override
			public void evaluate() throws Throwable {
				if(resultado != null){
					//Se hacen las verificaciones pertinentes para comprobar que el gestor se comporte adecuadamente
					assertEquals(resultado, gestorReserva.eliminarReserva(reserva));
				}
				else{
					try{
						gestorReserva.eliminarReserva(reserva);
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
}
