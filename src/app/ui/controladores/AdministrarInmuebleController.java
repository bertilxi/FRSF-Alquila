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
package app.ui.controladores;

import java.net.URL;
import java.util.ResourceBundle;

import app.ui.controladores.resultado.ResultadoControlador;
import javafx.fxml.FXML;

/**
 * Controlador de la vista de administración de inmuebles que se encarga de manejar el listado y la eliminación de inmuebles
 * Pertenece a la taskcard 13 de la iteración 1 y a la historia 3
 */
public class AdministrarInmuebleController extends OlimpoController {

	public static final String URLVista = "/app/ui/vistas/administrarInmueble.fxml";

	@Override
	protected void inicializar(URL location, ResourceBundle resources) {
		setTitulo("Administrar inmuebles");
	}

	@FXML
	public void agregar() {
		cambiarmeAScene(NMVInmuebleController.URLVista);
	}

	/**
	 * Método que permite eliminar un inmueble
	 * Pertenece a la taskcard 13 de la iteración 1 y a la historia 3
	 *
	 * @return ResultadoControlador que resume lo que hizo el controlador
	 */
	@FXML
	public ResultadoControlador eliminarInmueble() {
		return null;
	}

}
