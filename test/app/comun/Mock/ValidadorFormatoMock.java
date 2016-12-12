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
package app.comun.Mock;

import app.comun.ValidadorFormato;
import app.datos.entidades.Direccion;
import app.datos.entidades.TipoDocumento;

public class ValidadorFormatoMock extends ValidadorFormato{
	
	@Override
	public Boolean validarDireccion(Direccion direccion) {
		return true;
	}
	@Override
	public Boolean validarDoublePositivo(Double numeroDouble) {
		return true;
	}
	@Override
	public Boolean validarApellido(String apellido) {
		return true;
	}
	@Override
	public Boolean validarDocumento(TipoDocumento tipo, String numeroDocumento) {
		return true;
	}
	@Override
	public Boolean validarEmail(String email) {
		return true;
	}
	@Override
	public Boolean validarEnteroPositivo(Integer numeroInteger) {
		return true;
	}
	@Override
	public Boolean validarLocalidad(String localidad) {
		return true;
	}
	@Override
	public Boolean validarNombre(String nombre) {
		return true;
	}
	@Override
	public Boolean validarTelefono(String telefono) {
		return true;
	}
}
