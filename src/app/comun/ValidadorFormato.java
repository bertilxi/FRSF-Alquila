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
import javafx.scene.control.TextField;

@Service
public class ValidadorFormato {

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

	public Boolean validarNombre(String nombre) {
		Pattern pat = Pattern.compile("[a-zA-Z\\ ÁÉÍÓÚÜÑáéíóúüñ]{1,30}");
		return pat.matcher(nombre).matches();
	}

	public Boolean validarApellido(String apellido) {
		return validarNombre(apellido);
	}

	public Boolean validarEmail(String email) {
		return EmailValidator.getInstance().isValid(email) && email.length() <= 30;
	}

	public Boolean validarTelefono(String telefono) {
		Pattern pat = Pattern.compile("[0-9\\-]{0,20}");

		return pat.matcher(telefono).matches();
	}

	public Boolean validarDireccion(Direccion direccion) {
		Pattern pat;

		if(direccion == null){
			return false;
		}

		pat = Pattern.compile("([0-9]*[1-9]+[0-9]*){0,30}");
		if(!pat.matcher(direccion.getNumero()).matches()){
			return false;
		}

		pat = Pattern.compile("[a-zA-Z0-9]{1,50}");
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

		pat = Pattern.compile("[a-zA-Z0-9]{1,50}");
		if(direccion.getBarrio() != null && direccion.getBarrio().getNombre() != null && !pat.matcher(direccion.getBarrio().getNombre()).matches()){
			return false;
		}

		pat = Pattern.compile("[a-zA-Z0-9]{1,50}");
		if(direccion.getLocalidad() == null || !pat.matcher(direccion.getLocalidad().getNombre()).matches()){
			return false;
		}

		return true;
	}

	public Boolean validarLocalidad(String localidad) {
		return validarNombre(localidad);
	}

	public Boolean validarNombreVista(String nombre) {
		Pattern pat = Pattern.compile("[a-zA-Z\\ ÁÉÍÓÚÜÑáéíóúüñ]{0,30}");
		return pat.matcher(nombre).matches();
	}

	public Boolean validarNumeroDocumentoVista(String nombre) {
		Pattern pat = Pattern.compile("[0-9]{0,8}");
		return pat.matcher(nombre).matches();
	}

	public Boolean validarNumeroVista(String nombre) {
		Pattern pat = Pattern.compile("[0-9]{0,30}");
		return pat.matcher(nombre).matches();
	}

	public Boolean validarEmailVista(String correo) {
		Pattern pat = Pattern.compile("[([A-Za-z]{0,30})([\\\\@]{0,1})([A-Za-z]{0,30})([\\\\.]{0,1})([A-Za-z]{0,30})([\\\\.]{0,1})([A-Za-z]{0,30})]{0,30}");
		return pat.matcher(correo).matches();
	}

	public void setValidadorNombre(TextField textField) {
		textField.textProperty().addListener((observable, oldValue, newValue) -> {

			if(!validarNombreVista(newValue)){
				textField.setText(oldValue);
			}

		});
	}

	public void setValidadorNumeroDocumento(TextField textField) {
		textField.textProperty().addListener((observable, oldValue, newValue) -> {

			if(!validarNumeroDocumentoVista(newValue)){
				textField.setText(oldValue);
			}

		});
	}

	public void setValidadorEmail(TextField textField) {
		textField.textProperty().addListener((observable, oldValue, newValue) -> {

			if(!validarEmailVista(newValue)){
				textField.setText(oldValue);
			}

		});
	}

	public void setValidadorApellido(TextField textField) {
		setValidadorNombre(textField);
	}

	public void setValidadorTelefono(TextField textField) {
		textField.textProperty().addListener((observable, oldValue, newValue) -> {

			if(!validarNumeroVista(newValue)){
				textField.setText(oldValue);
			}

		});
	}

	public void setValidadorDireccion(TextField textField) {
		setValidadorNombre(textField);
	}

	public void setValidadorLocalidad(TextField textField) {
		setValidadorNombre(textField);
	}

}
