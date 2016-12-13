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

import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

/**
 * Clase encargada de la conversion de strings
 */
@Service
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

		//Si la entrada consiste solo de espacios en blanco, se devuelve una cadena vacía
		entrada.trim();
		if(entrada == ""){
			return "";
		}

		//Se convierte toda la cadena a minúsculas
		entrada.toLowerCase();

		//Se separan las palabras
		String[] partes = entrada.split(" ");
		StringBuffer salida = new StringBuffer();

		//Si la palabra es alguna de las siguientes, no se debe convertir a mayúscula su primer letra
		Pattern patron = Pattern.compile("a|de|del|el|en|la|las|lo|los|of|y");
		for(String parte: partes){
			if(patron.matcher(parte).matches()){
				salida.append(parte + " ");
			}
			else{
				salida.append(parte.substring(0, 1).toUpperCase() + parte.substring(1) + " ");
			}
		}

		//Se convierte la primera letra a mayúscula en caso de no haberlo hecho antes y se recortan los espacios al final
		return (salida.substring(0, 1).toUpperCase() + salida.substring(1)).trim();
	}
}
