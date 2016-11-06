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
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import app.logica.CoordinadorJavaFX;
import app.ui.ScenographyChanger;
import app.ui.componentes.ventanas.PresentadorVentanas;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public abstract class OlimpoController implements Initializable {

	protected Stage stage;
	protected CoordinadorJavaFX coordinador;
	protected PresentadorVentanas presentador;
	private ScenographyChanger parentScenographyChanger;
	private Map<Node, ScenographyChanger> myScenographyChangers = new HashMap<>();
	protected String URLVistaRetorno;

	protected OlimpoController agregarScenographyChanger(Node contexto, ScenographyChanger scenographyChanger) {
		myScenographyChangers.put(contexto, scenographyChanger);
		return this;
	}

	protected OlimpoController cambiarScene(Node contexto, String URLVistaACambiar) {
		return myScenographyChangers.get(contexto).cambiarScenography(URLVistaACambiar);
	}

	protected OlimpoController cambiarmeAScene(String URLVistaACambiar) {
		return parentScenographyChanger.cambiarScenography(URLVistaACambiar);
	}

	protected OlimpoController cambiarmeAScene(String URLVistaACambiar, String URLVistaRetorno) {
		this.URLVistaRetorno = URLVistaRetorno;
		return parentScenographyChanger.cambiarScenography(URLVistaACambiar);
	}

	public OlimpoController setScenographyChanger(ScenographyChanger scenographyChanger) {
		this.parentScenographyChanger = scenographyChanger;
		return this;
	}

	public OlimpoController setStage(Stage stage) {
		this.stage = stage;
		return this;
	}

	public OlimpoController setCoordinador(CoordinadorJavaFX coordinador) {
		this.coordinador = coordinador;
		return this;
	}

	public OlimpoController setPresentador(PresentadorVentanas presentador) {
		this.presentador = presentador;
		return this;
	}

	@Override
	public final void initialize(URL location, ResourceBundle resources) {
		Platform.runLater(() -> {
			inicializar(location, resources);
		});
	}

	protected abstract void inicializar(URL location, ResourceBundle resources);

	@FXML
	public void salir() {
		if(URLVistaRetorno != null){
			cambiarmeAScene(URLVistaRetorno);
		}
		else{
			stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
		}
	}

	protected void setTitulo(String titulo) {
		stage.setTitle(titulo);
	}
}
