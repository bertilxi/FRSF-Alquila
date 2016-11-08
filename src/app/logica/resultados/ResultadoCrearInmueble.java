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
package app.logica.resultados;

import app.logica.resultados.ResultadoCrearInmueble.ErrorCrearInmueble;

public class ResultadoCrearInmueble extends Resultado<ErrorCrearInmueble> {

	public ResultadoCrearInmueble(ErrorCrearInmueble... errores) {
		super(errores);
	}

	public enum ErrorCrearInmueble {
		Fecha_Vacia,
		Propietario_Vacio,
		Propietario_Inexistente,
		Formato_Direccion_Incorrecto,
		Precio_Incorrecto,
		Fondo_Incorrecto,
		Frente_Incorrecto,
		Superficie_Incorrecta,
		Tipo_Vacio,
		Datos_Edificio_Incorrectos
	}

}
