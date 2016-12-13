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

import app.datos.entidades.PDF;
import app.datos.entidades.Reserva;
import app.logica.resultados.ResultadoCrearReserva.ErrorCrearReserva;

public class ResultadoCrearReserva extends Resultado<ErrorCrearReserva> {

	private Reserva reservaEnConflicto;

	private PDF pdfReserva;

	public enum ErrorCrearReserva {

		Cliente_Vacío,
		Nombre_Cliente_Vacío,
		Apellido_Cliente_Vacío,
		TipoDocumento_Cliente_Vacío,
		NúmeroDocumento_Cliente_Vacío,
		Propietario_Vacío,
		Nombre_Propietario_Vacío,
		Apellido_Propietario_Vacío,
		Inmueble_Vacío,
		Tipo_Inmueble_Vacío,
		Dirección_Inmueble_Vacía,
		Localidad_Inmueble_Vacía,
		Barrio_Inmueble_Vacío,
		Calle_Inmueble_Vacía,
		Altura_Inmueble_Vacía,
		FechaInicio_vacía,
		FechaFin_vacía,
		Fecha_Inicio_Posterior_A_Fecha_Fin,
		Existe_Otra_Reserva_Activa,
		Importe_Vacío,
		Importe_Menor_O_Igual_A_Cero,
	}

	public ResultadoCrearReserva(Reserva reservaEnConflicto, ErrorCrearReserva... errores) {
		super(errores);
		this.reservaEnConflicto = reservaEnConflicto;
	}

	public ResultadoCrearReserva(PDF pdfReserva) {
		super();
		this.pdfReserva = pdfReserva;
	}

	public Reserva getReservaEnConflicto() {
		return reservaEnConflicto;
	}

	public PDF getPdfReserva() {
		return pdfReserva;
	}
}
