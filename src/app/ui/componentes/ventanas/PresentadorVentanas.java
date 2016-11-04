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
package app.ui.componentes.ventanas;

import javafx.stage.Window;

public class PresentadorVentanas {

	public VentanaConfirmacion presentarConfirmacion(String titulo, String mensaje, Window padre) {
		return new VentanaConfirmacion(titulo, mensaje, padre);
	}

	public VentanaConfirmacion presentarConfirmacion(String titulo, String mensaje) {
		return new VentanaConfirmacion(titulo, mensaje);
	}

	public VentanaError presentarError(String titulo, String mensaje, Window padre) {
		return new VentanaError(titulo, mensaje, padre);
	}

	public VentanaError presentarError(String titulo, String mensaje) {
		return new VentanaError(titulo, mensaje);
	}

	public VentanaInformacion presentarInformacion(String titulo, String mensaje, Window padre) {
		return new VentanaInformacion(titulo, mensaje, padre);
	}

	public VentanaInformacion presentarInformacion(String titulo, String mensaje) {
		return new VentanaInformacion(titulo, mensaje);
	}

	public VentanaErrorExcepcion presentarExcepcion(Exception e, Window w) {
		e.printStackTrace();
		return new VentanaErrorExcepcion(e.getMessage(), w);
	}

	public VentanaErrorExcepcion presentarExcepcion(Exception e) {
		e.printStackTrace();
		return new VentanaErrorExcepcion(e.getMessage());
	}

	public VentanaErrorExcepcionInesperada presentarExcepcionInesperada(Exception e, Window w) {
		System.err.println("Excepción inesperada!!");
		e.printStackTrace();
		return new VentanaErrorExcepcionInesperada(w);
	}

	public VentanaErrorExcepcionInesperada presentarExcepcionInesperada(Exception e) {
		System.err.println("Excepción inesperada!!");
		e.printStackTrace();
		return new VentanaErrorExcepcionInesperada();
	}
}
