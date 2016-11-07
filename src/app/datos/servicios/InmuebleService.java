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

import app.datos.entidades.Inmueble;
import app.excepciones.PersistenciaException;

public interface InmuebleService {

	public void guardarInmueble(Inmueble inmueble) throws PersistenciaException;

	public void modificarInmueble(Inmueble inmueble) throws PersistenciaException;

	public ArrayList<Inmueble> listarInmuebles() throws PersistenciaException;

	public Inmueble obtenerInmueble(Integer id) throws PersistenciaException;

}