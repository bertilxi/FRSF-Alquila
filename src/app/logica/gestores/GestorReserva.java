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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import app.datos.clases.EstadoStr;
import app.datos.clases.ReservaVista;
import app.datos.entidades.Estado;
import app.datos.entidades.PDF;
import app.datos.entidades.Reserva;
import app.datos.servicios.ReservaService;
import app.excepciones.GestionException;
import app.excepciones.PersistenciaException;
import app.logica.resultados.ResultadoCrearReserva;
import app.logica.resultados.ResultadoCrearReserva.ErrorCrearReserva;
import app.logica.resultados.ResultadoEliminarReserva;

@Service
/**
 * Gestor que implementa la capa lógica del ABM Reserva y funciones asociadas a una reserva.
 */
public class GestorReserva {

	@Resource
	protected ReservaService persistidorReserva;
	
	@Resource
	protected GestorInmueble gestorInmueble;
	
	@Resource
	protected GestorDatos gestorDatos;
 	
	@Resource
	protected GestorPDF gestorPDF;
	
	/**
	 * Método para crear una reserva. Primero se validan las reglas de negocia y luego se persiste.
	 * Pertenece a la taskcard 25 de la iteración 2 y a la historia 7
	 *
	 * @param reservaVista
	 *            a guardar
	 * @return resultado de la operación
	 * @throws PersistenciaException
	 *             si falló al persistir
	 * @throws GestionException 
	 */
	public ResultadoCrearReserva crearReserva(ReservaVista reservaVista) throws PersistenciaException, GestionException {
		Set<ErrorCrearReserva> errores = new HashSet<>();
		
		if(reservaVista.getCliente() == null){
			errores.add(ErrorCrearReserva.Cliente_Vacío);
		}
		else{
			if(reservaVista.getCliente().getNombre() == null){
				errores.add(ErrorCrearReserva.Nombre_Cliente_Vacío);
			}
			if(reservaVista.getCliente().getApellido() == null){
				errores.add(ErrorCrearReserva.Apellido_Cliente_Vacío);
			}
			if(reservaVista.getCliente().getTipoDocumento() == null || reservaVista.getCliente().getTipoDocumento().getTipo() == null){
				errores.add(ErrorCrearReserva.TipoDocumento_Cliente_Vacío);
			}
			if(reservaVista.getCliente().getNumeroDocumento() == null){
				errores.add(ErrorCrearReserva.NúmeroDocumento_Cliente_Vacío);
			}
		}
		if(reservaVista.getInmueble() == null){
			errores.add(ErrorCrearReserva.Inmueble_Vacío);
		}
		else{
			if(reservaVista.getInmueble().getPropietario() == null){
				errores.add(ErrorCrearReserva.Propietario_Vacío);
			}
			else{
				if(reservaVista.getInmueble().getPropietario().getNombre() == null){
					errores.add(ErrorCrearReserva.Nombre_Propietario_Vacío);
				}
				if(reservaVista.getInmueble().getPropietario().getApellido() == null){
					errores.add(ErrorCrearReserva.Apellido_Propietario_Vacío);
				}
			}
			if(reservaVista.getInmueble().getTipo() == null || reservaVista.getInmueble().getTipo().getTipo() == null){
				errores.add(ErrorCrearReserva.Tipo_Inmueble_Vacío);
			}
			if(reservaVista.getInmueble().getDireccion() == null){
				errores.add(ErrorCrearReserva.Dirección_Inmueble_Vacía);
			}
			else{
				if(reservaVista.getInmueble().getDireccion().getLocalidad() == null){
					errores.add(ErrorCrearReserva.Localidad_Inmueble_Vacía);
				}
				if(reservaVista.getInmueble().getDireccion().getBarrio() == null){
					errores.add(ErrorCrearReserva.Barrio_Inmueble_Vacío);
				}
				if(reservaVista.getInmueble().getDireccion().getCalle() == null){
					errores.add(ErrorCrearReserva.Calle_Inmueble_Vacía);
				}
				if(reservaVista.getInmueble().getDireccion().getNumero() == null){
					errores.add(ErrorCrearReserva.Altura_Inmueble_Vacía);
				}
			}
		}
		if(reservaVista.getFechaInicio() == null){
			errores.add(ErrorCrearReserva.FechaInicio_vacía);
		}
		if(reservaVista.getFechaFin() == null){
			errores.add(ErrorCrearReserva.FechaFin_vacía);
		}
		if(reservaVista.getImporte() == null){
			errores.add(ErrorCrearReserva.Importe_vacío);
		}
		
		if(errores.isEmpty()){
			PDF pdf = gestorPDF.generarPDF(reservaVista);
			
			Reserva reserva = new Reserva()
					.setCliente(reservaVista.getCliente())
					.setFechaInicio(reservaVista.getFechaInicio())
					.setFechaFin(reservaVista.getFechaFin())
					.setInmueble(reservaVista.getInmueble())
					.setArchivoPDF(pdf);
			
			persistidorReserva.guardarReserva(reserva);
		}

		return new ResultadoCrearReserva(errores.toArray(new ErrorCrearReserva[0]));
	}

	public ResultadoEliminarReserva eliminarReserva(Reserva reserva) throws PersistenciaException {
		
		ArrayList<Estado> estados = gestorDatos.obtenerEstados();
		Estado estadoBaja = null;
		for(Estado estado: estados){
			if(estado.getEstado().equals(EstadoStr.BAJA)){
				estadoBaja = estado;
			}
		}
		reserva.setEstado(estadoBaja);
		persistidorReserva.modificarReserva(reserva);
		
		return new ResultadoEliminarReserva();
	}

}
