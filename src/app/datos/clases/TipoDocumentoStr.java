/**
 * Copyright (C) 2016  Fernando Berti - Daniel Campodonico - Emiliano Gioria - Lucas Moretti - Esteban Rebechi - Andres Leonel Rico
 * This file is part of Olimpo.
 *
 * Olimpo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Olimpo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Olimpo.  If not, see <http://www.gnu.org/licenses/>.
 */
package app.datos.clases;

public enum TipoDocumentoStr {
	DNI, LC, LE, PASAPORTE, CEDULA_EXTRANJERA;

	@Override
	public String toString() {
		switch(this) {
		case DNI:
			return "DNI";
		case LC:
			return "LC";
		case LE:
			return "LE";
		case PASAPORTE:
			return "Pasaporte";
		case CEDULA_EXTRANJERA:
			return "Cédula Extranjera";
		}
		return null;
	}
}
