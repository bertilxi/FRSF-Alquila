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
package app.datos.servicios;

import java.util.ArrayList;

import app.datos.clases.FiltroPropietario;
import app.datos.entidades.Propietario;
import app.excepciones.PersistenciaException;

/**
 * Interface que define los métodos de persistencia de un propietario
 * Pertenece a la taskcard 10 de la iteración 1 y a la historia de usuario 2
 */

public interface PropietarioService {

	public void guardarPropietario(Propietario propietario) throws PersistenciaException;

	public void modificarPropietario(Propietario propietario) throws PersistenciaException;

	public Propietario obtenerPropietario(FiltroPropietario filtro) throws PersistenciaException;

	public ArrayList<Propietario> listarPropietarios() throws PersistenciaException;
}