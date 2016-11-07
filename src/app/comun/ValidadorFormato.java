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

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;

import app.datos.entidades.Direccion;
import app.datos.entidades.TipoDocumento;

@Service
/**
 * Clase encargada de validar formatos de tipos de datos
 */
public class ValidadorFormato {

	/**
	 * Valida si un documento tiene el formato adecuado
	 *
	 * @param tipo
	 *            tipo de documento a validar
	 * @param numeroDocumento
	 *            numero de documento a validar
	 * @return si la combinación es valida
	 */
	public Boolean validarDocumento(TipoDocumento tipo, String numeroDocumento) {
		Pattern pat;

		if(tipo == null){
			return false;
		}

		switch(tipo.getTipo()) {
		case DNI:
			pat = Pattern.compile("[0-9]{7,8}");
			break;
		case LC:
			pat = Pattern.compile("[0-9\\-]{0,20}");
			break;
		case LE:
			pat = Pattern.compile("[0-9\\-]{0,20}");
			break;
		case PASAPORTE:
			pat = Pattern.compile("[0-9\\-]{0,20}");
			break;
		case CEDULA_EXTRANJERA:
			pat = Pattern.compile("[0-9\\-]{0,20}");
			break;
		default:
			return false;
		}

		return pat.matcher(numeroDocumento).matches();
	}

	/**
	 * Valida si un nombre tiene el formato adecuado
	 *
	 * @param nombre
	 *            nombre a validar
	 * @return si es valido
	 */
	public Boolean validarNombre(String nombre) {
		if(nombre == null){
			return false;
		}

		Pattern pat = Pattern.compile("[a-zA-Z\\ ÁÉÍÓÚÜÑáéíóúüñ]{1,30}");
		return pat.matcher(nombre).matches();
	}

	/**
	 * Valida si un apellido tiene el formato adecuado
	 *
	 * @param apellido
	 *            apellido a validar
	 * @return si es valido
	 */
	public Boolean validarApellido(String apellido) {
		return validarNombre(apellido);
	}

	/**
	 * Valida si un email tiene el formato adecuado
	 *
	 * @param email
	 *            email a validar
	 * @return si es valido
	 */
	public Boolean validarEmail(String email) {
		if(email == null){
			return false;
		}

		return EmailValidator.getInstance().isValid(email) && email.length() <= 30;
	}

	/**
	 * Valida si un telefono tiene el formato adecuado
	 *
	 * @param telefono
	 *            telefono a validar
	 * @return si es valido
	 */
	public Boolean validarTelefono(String telefono) {
		if(telefono == null){
			return false;
		}

		Pattern pat = Pattern.compile("[0-9\\-]{0,20}");
		return pat.matcher(telefono).matches();
	}

	/**
	 * Valida si una direccion tiene el formato adecuado
	 *
	 * @param direccion
	 *            direccion a validar
	 * @return si es valida
	 */
	public Boolean validarDireccion(Direccion direccion) {
		if(direccion == null){
			return false;
		}

		Pattern pat;

		pat = Pattern.compile("([0-9]*[1-9]+[0-9]*){0,30}");
		if(direccion.getNumero() == null || !pat.matcher(direccion.getNumero()).matches()){
			return false;
		}

		pat = Pattern.compile("[a-zA-Z0-9áéíóúüñÁÉÍÚÓÜÑ\\ ]{1,50}");
		if(direccion.getCalle() == null || !pat.matcher(direccion.getCalle().getNombre()).matches()){
			return false;
		}

		pat = Pattern.compile("[a-zA-Z0-9]{0,30}");
		if(direccion.getPiso() != null && !pat.matcher(direccion.getPiso()).matches()){
			return false;
		}

		pat = Pattern.compile("[a-zA-Z0-9]{0,30}");
		if(direccion.getDepartamento() != null && !pat.matcher(direccion.getDepartamento()).matches()){
			return false;
		}

		pat = Pattern.compile("[a-zA-Z0-9áéíóúüñÁÉÍÚÓÜÑ\\ ]{1,50}");
		if(direccion.getBarrio() != null && direccion.getBarrio().getNombre() != null && !pat.matcher(direccion.getBarrio().getNombre()).matches()){
			return false;
		}

		pat = Pattern.compile("[a-zA-Z0-9áéíóúüñÁÉÍÚÓÜÑ\\ ]{1,50}");
		if(direccion.getLocalidad() == null || !pat.matcher(direccion.getLocalidad().getNombre()).matches()){
			return false;
		}
		else{
			if(direccion.getLocalidad().getProvincia() == null || !pat.matcher(direccion.getLocalidad().getProvincia().getNombre()).matches()){
				return false;
			}
			else if(direccion.getLocalidad().getProvincia().getPais() == null || !pat.matcher(direccion.getLocalidad().getProvincia().getPais().getNombre()).matches()){
				return false;
			}
		}

		return true;
	}

	/**
	 * Valida si una localidad tiene el formato adecuado
	 *
	 * @param localidad
	 *            localidad a validar
	 * @return si es valida
	 */
	public Boolean validarLocalidad(String localidad) {
		if(localidad == null){
			return false;
		}

		Pattern pat = Pattern.compile("[a-zA-Z\\ ÁÉÍÓÚÜÑáéíóúüñ]{1,50}");
		return pat.matcher(localidad).matches();
	}
}
