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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
/**
 * Clase encargada de la conversion de fechas
 */
public class ConversorFechas {

	/**
	 * Convierte de Date a LocalDate
	 *
	 * @param fecha
	 *            a convertir
	 * @return la fecha convertida a LocalDate
	 */
	public LocalDate getLocalDate(Date fecha) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		LocalDate localDate = LocalDate.of(cal.get(Calendar.YEAR),
				cal.get(Calendar.MONTH) + 1,
				cal.get(Calendar.DAY_OF_MONTH));
		return localDate;
	}

	/**
	 * Convierte de LocalDate a Date
	 *
	 * @param fecha
	 *            a convertir
	 * @return la fecha convertida a Date
	 */
	public Date getDate(LocalDate fecha) {
		Instant instant = Instant.from(fecha.atStartOfDay(ZoneId.systemDefault()));
		Date date = Date.from(instant);
		return date;
	}

	/**
	 * Convierte de Date a un String con el formato dd/MM/yyyy
	 *
	 * @param fecha
	 *            a convertir
	 * @return la fecha convertida a String dd/MM/yyyy
	 */
	public String diaMesYAnioToString(Date fecha) {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		return formatter.format(fecha);
	}
}
