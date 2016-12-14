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

import app.datos.entidades.Vendedor;
import app.logica.CoordinadorJavaFX;
import app.ui.ScenographyChanger;
import app.ui.componentes.ventanas.PresentadorVentanas;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public abstract class OlimpoController implements Initializable {

	protected Stage stage;
	protected CoordinadorJavaFX coordinador;
	protected PresentadorVentanas presentador;
	private ScenographyChanger parentScenographyChanger;
	private Map<Node, ScenographyChanger> myScenographyChangers = new HashMap<>();
	protected String URLVistaRetorno;

	protected Vendedor vendedorLogueado;

	public void setVendedorLogueado(Vendedor vendedorLogueado) {
		this.vendedorLogueado = vendedorLogueado;
	}

	protected Pane paneVistaRetorno;

	protected void agregarScenographyChanger(Node contexto, ScenographyChanger scenographyChanger) {
		myScenographyChangers.put(contexto, scenographyChanger);
	}

	protected OlimpoController cambiarScene(Node contexto, String URLVistaACambiar, Boolean useStageSize) {
		OlimpoController controlador = myScenographyChangers.get(contexto).cambiarScenography(URLVistaACambiar, useStageSize);
		controlador.setVendedorLogueado(vendedorLogueado);
		return controlador;
	}

	protected OlimpoController cambiarScene(Node contexto, String URLVistaACambiar) {
		return cambiarScene(contexto, URLVistaACambiar, false);
	}

	protected OlimpoController cambiarScene(Node contexto, String URLVistaACambiar, String URLVistaRetorno, Boolean useStageSize) {
		OlimpoController nuevoController = myScenographyChangers.get(contexto).cambiarScenography(URLVistaACambiar, useStageSize);
		nuevoController.URLVistaRetorno = URLVistaRetorno;
		nuevoController.setVendedorLogueado(vendedorLogueado);
		return nuevoController;
	}

	protected OlimpoController cambiarScene(Node contexto, String URLVistaACambiar, String URLVistaRetorno) {
		return cambiarScene(contexto, URLVistaACambiar, URLVistaRetorno, false);
	}

	protected OlimpoController cambiarmeAScene(String URLVistaACambiar, String URLVistaRetorno, Boolean useSceneSize) {
		OlimpoController nuevoController = parentScenographyChanger.cambiarScenography(URLVistaACambiar, useSceneSize);
		nuevoController.URLVistaRetorno = URLVistaRetorno;
		nuevoController.setVendedorLogueado(vendedorLogueado);
		return nuevoController;
	}

	protected OlimpoController cambiarmeAScene(String URLVistaACambiar, String URLVistaRetorno) {
		return cambiarmeAScene(URLVistaACambiar, URLVistaRetorno, false);
	}

	protected void cambiarmeAScene(Pane paneVistaRetorno, Boolean useSceneSize) {
		parentScenographyChanger.cambiarScenography(paneVistaRetorno, useSceneSize);
	}

	protected void cambiarmeAScene(Pane paneVistaRetorno) {
		cambiarmeAScene(paneVistaRetorno, false);
	}

	protected OlimpoController cambiarScene(Node contexto, String URLVistaACambiar, Pane paneVistaRetorno, Boolean useStageSize) {
		OlimpoController nuevoController = myScenographyChangers.get(contexto).cambiarScenography(URLVistaACambiar, useStageSize);
		nuevoController.paneVistaRetorno = paneVistaRetorno;
		nuevoController.setVendedorLogueado(vendedorLogueado);
		return nuevoController;
	}

	protected OlimpoController cambiarScene(Node contexto, String URLVistaACambiar, Pane paneVistaRetorno) {
		return cambiarScene(contexto, URLVistaACambiar, paneVistaRetorno, false);
	}

	protected OlimpoController cambiarmeAScene(String URLVistaACambiar, Pane paneVistaRetorno, Boolean useSceneSize) {
		OlimpoController nuevoController = parentScenographyChanger.cambiarScenography(URLVistaACambiar, useSceneSize);
		nuevoController.paneVistaRetorno = paneVistaRetorno;
		nuevoController.setVendedorLogueado(vendedorLogueado);
		return nuevoController;
	}

	protected OlimpoController cambiarmeAScene(String URLVistaACambiar, Pane paneVistaRetorno) {
		return cambiarmeAScene(URLVistaACambiar, paneVistaRetorno, false);
	}

	protected OlimpoController cambiarmeAScene(String URLVistaACambiar, Boolean useSceneSize) {
		OlimpoController controlador = parentScenographyChanger.cambiarScenography(URLVistaACambiar, useSceneSize);
		controlador.setVendedorLogueado(vendedorLogueado);
		return controlador;
	}

	protected OlimpoController cambiarmeAScene(String URLVistaACambiar) {
		return cambiarmeAScene(URLVistaACambiar, false);
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
		else if(paneVistaRetorno != null){
			cambiarmeAScene(paneVistaRetorno);
		}
		else{
			stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
		}
	}

	protected void setTitulo(String titulo) {
		stage.setTitle(titulo);
	}
}
