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

import org.springframework.stereotype.Service;

import app.datos.clases.ReservaVista;
import app.datos.entidades.Reserva;
import app.excepciones.PersistenciaException;
import app.logica.resultados.ResultadoCrearReserva;
import app.logica.resultados.ResultadoEliminarReserva;

@Service
/**
 * Gestor que implementa la capa lógica del ABM Reserva y funciones asociadas a una reserva.
 */
public class GestorReserva {

	/**
	 * Método para crear una reserva. Primero se validan las reglas de negocia y luego se persiste.
	 * Pertenece a la taskcard 25 de la iteración 2 y a la historia 7
	 *
	 * @param reserva
	 *            a guardar
	 * @return resultado de la operación
	 * @throws PersistenciaException
	 *             si falló al persistir
	 */
	public ResultadoCrearReserva crearReserva(ReservaVista reserva) throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultadoEliminarReserva eliminarReserva(Reserva reserva) {
		// TODO Auto-generated method stub
		return null;
	}

}
