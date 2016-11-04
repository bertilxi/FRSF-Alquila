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
package app.ui;

import org.springframework.stereotype.Service;

import app.logica.CoordinadorJavaFX;
import app.ui.componentes.ventanas.PresentadorVentanas;
import app.ui.controladores.OlimpoController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

@Service
public class ScenographyChanger {

	private Stage stage;

	private PresentadorVentanas presentadorVentanas;

	private CoordinadorJavaFX coordinador;

	private Pane background;

	public ScenographyChanger(Stage stage, PresentadorVentanas presentadorVentanas, CoordinadorJavaFX coordinador, Pane background) {
		this.stage = stage;
		this.presentadorVentanas = presentadorVentanas;
		this.coordinador = coordinador;
		this.background = background;
	}

	public OlimpoController cambiarScenography(String URLVistaACambiar) {
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource(URLVistaACambiar));
			Pane newScenography = (Pane) loader.load();

			background.getChildren().clear();
			background.getChildren().add(newScenography);

			newScenography.autosize();
			background.autosize();
			stage.sizeToScene();

			OlimpoController controller = loader.getController();

			controller.setScenographyChanger(this).setStage(stage).setCoordinador(coordinador).setPresentador(presentadorVentanas);

			return controller;
		} catch(Exception e){
			presentadorVentanas.presentarExcepcionInesperada(e, stage);
			return null;
		}
	}
}
