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
package app.ui.componentes.ventanas.mock;

import app.ui.componentes.ventanas.PresentadorVentanas;
import app.ui.componentes.ventanas.VentanaConfirmacion;
import app.ui.componentes.ventanas.VentanaError;
import app.ui.componentes.ventanas.VentanaErrorExcepcion;
import app.ui.componentes.ventanas.VentanaErrorExcepcionInesperada;
import app.ui.componentes.ventanas.VentanaEsperaBaseDeDatos;
import app.ui.componentes.ventanas.VentanaInformacion;
import javafx.stage.Window;

public class PresentadorVentanasMock extends PresentadorVentanas {

	Boolean acepta;

	public PresentadorVentanasMock() {

	}

	public PresentadorVentanasMock(Boolean acepta) {
		this.acepta = acepta;
	}

	@Override
	public VentanaConfirmacion presentarConfirmacion(String titulo, String mensaje, Window padre) {
		return new VentanaConfirmacion(titulo, mensaje) {
			@Override
			public Boolean acepta() {
				return acepta;
			}
		};
	}

	@Override
	public VentanaConfirmacion presentarConfirmacion(String titulo, String mensaje) {
		return new VentanaConfirmacion(titulo, mensaje) {
			@Override
			public Boolean acepta() {
				return acepta;
			}
		};
	}

	@Override
	public VentanaError presentarError(String titulo, String mensaje, Window padre) {
		return null;
	}

	@Override
	public VentanaError presentarError(String titulo, String mensaje) {
		return null;
	}

	@Override
	public VentanaInformacion presentarInformacion(String titulo, String mensaje, Window padre) {
		return null;
	}

	@Override
	public VentanaInformacion presentarInformacion(String titulo, String mensaje) {
		return null;
	}

	@Override
	public VentanaErrorExcepcion presentarExcepcion(Exception e, Window w) {
		return null;
	}

	@Override
	public VentanaErrorExcepcion presentarExcepcion(Exception e) {
		return null;
	}

	@Override
	public VentanaErrorExcepcionInesperada presentarExcepcionInesperada(Exception e, Window w) {
		return null;
	}

	@Override
	public VentanaErrorExcepcionInesperada presentarExcepcionInesperada(Exception e) {
		return null;
	}

	@Override
	public VentanaEsperaBaseDeDatos presentarEsperaBaseDeDatos(Window w) {
		return null;
	}

	@Override
	public void presentarToast(String mensaje, Window padre) {

	}

	@Override
	public void presentarToast(String mensaje, Window padre, int ajusteHeight) {

	}
}
