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
package app.comun;

/**
 * Clase encargada de la conversion de strings
 */
public class FormateadorString {

	/**
	 * Retorna el string con la primera letra en mayúscula
	 * 
	 * @param entrada
	 *            string a convertir
	 * @return string de entrada con la primera letra en mayúscula
	 */
	public String primeraMayuscula(String entrada) {
		if(entrada == null){
			return null;
		}
		switch(entrada.length()) {
		// Los strings vacíos se retornan como están.
		case 0:
			entrada = "";
			break;
		// Los strings de un solo caracter se devuelven en mayúscula.
		case 1:
			entrada = entrada.toUpperCase();
			break;
		// Sino, mayúscula la primera letra, minúscula el resto.
		default:
			entrada = entrada.substring(0, 1).toUpperCase()
					+ entrada.substring(1).toLowerCase();
		}
		return entrada;
	}
}
