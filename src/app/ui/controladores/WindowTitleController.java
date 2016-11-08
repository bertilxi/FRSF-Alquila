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

import app.ui.ScenographyChanger;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class WindowTitleController extends OlimpoController {

	public static final String URLVista = "/app/ui/vistas/windowTitle.fxml";

	@FXML
	private Pane background;

	@Override
	public void inicializar(URL location, ResourceBundle resources) {
		//Primera pantalla a mostrar
		this.agregarScenographyChanger(background, new ScenographyChanger(stage, presentador, coordinador, background));
		cambiarScene(background, LoginController.URLVista, true);
	}
}
