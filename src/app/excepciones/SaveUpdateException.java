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
package app.excepciones;

/**
 * Representa un error en el guardado o modificaci�n de datos sobe la base de datos
 *
 *
 */
public class SaveUpdateException extends PersistenciaException {

	private static final long serialVersionUID = 1L;

	public SaveUpdateException(Throwable e) {
		super("Error inesperado interactuando con la base de datos.\nNo se pudieron guardar los datos deseados.", e);
	}
}
