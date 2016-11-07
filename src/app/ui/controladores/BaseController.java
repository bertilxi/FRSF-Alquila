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

import app.datos.entidades.Vendedor;
import app.ui.ScenographyChanger;
import javafx.application.Platform;
import javafx.collections.ListChangeListener.Change;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * Controlador base de la vista principal para la administración que se encarga de manejar el cambio entre funcionalidades
 * Pertenece a la taskcard 4 de la iteración 1 y a la historia 1
 */
public class BaseController extends OlimpoController {

	public static final String URLVista = "/app/ui/vistas/base.fxml";

	private String ventanaInicio = InicioController.URLVista;

	@FXML
	private ToggleButton toggleButtonDatosVendedor;

	@FXML
	private ToggleButton toggleButtonClientes;

	@FXML
	private ToggleButton toggleButtonInmuebles;

	@FXML
	private ToggleButton toggleButtonPropietarios;

	@FXML
	private ToggleButton toggleButtonVendedores;

	@FXML
	private ToggleButton toggleButtonSalir;

	private ToggleGroup toggleGroupSidebar = new ToggleGroup();

	@FXML
	private Pane background;

	@FXML
	private Pane panelGeneral;

	@FXML
	private Pane panelVendedores;

	private Vendedor vendedorLogueado;

	@Override
	public void inicializar(URL location, ResourceBundle resources) {
		//Primera pantalla a mostrar
		this.agregarScenographyChanger(background, new ScenographyChanger(stage, presentador, coordinador, background));
		cambiarScene(background, ventanaInicio);

		toggleButtonDatosVendedor.setToggleGroup(toggleGroupSidebar);
		toggleButtonClientes.setToggleGroup(toggleGroupSidebar);
		toggleButtonInmuebles.setToggleGroup(toggleGroupSidebar);
		toggleButtonPropietarios.setToggleGroup(toggleGroupSidebar);
		toggleButtonVendedores.setToggleGroup(toggleGroupSidebar);
		toggleButtonSalir.setToggleGroup(toggleGroupSidebar);
		addAlwaysOneSelectedSupport(toggleGroupSidebar);
	}

	private void addAlwaysOneSelectedSupport(final ToggleGroup toggleGroup) {
		toggleGroup.getToggles().addListener((Change<? extends Toggle> c) -> {
			while(c.next()){
				for(final Toggle addedToggle: c.getAddedSubList()){
					addConsumeMouseEventfilter(addedToggle);
				}
			}
		});
		toggleGroup.getToggles().forEach(t -> {
			addConsumeMouseEventfilter(t);
		});
	}

	private void addConsumeMouseEventfilter(Toggle toggle) {
		EventHandler<MouseEvent> consumeMouseEventfilter = (MouseEvent mouseEvent) -> {
			if(((Toggle) mouseEvent.getSource()).isSelected()){
				mouseEvent.consume();
			}
		};

		((ToggleButton) toggle).addEventFilter(MouseEvent.MOUSE_PRESSED, consumeMouseEventfilter);
		((ToggleButton) toggle).addEventFilter(MouseEvent.MOUSE_RELEASED, consumeMouseEventfilter);
		((ToggleButton) toggle).addEventFilter(MouseEvent.MOUSE_CLICKED, consumeMouseEventfilter);
	}

	@FXML
	public void verMisDatos(Event event) {
		ModificarVendedorController nuevaPantalla = (ModificarVendedorController) cambiarScene(background, ModificarVendedorController.URLVista, ventanaInicio);
		nuevaPantalla.setVendedor(vendedorLogueado);
	}

	@FXML
	public void verClientes(Event event) {
		cambiarScene(background, AdministrarClienteController.URLVista);
	}

	@FXML
	public void verInmuebles() {
		cambiarScene(background, AdministrarInmuebleController.URLVista);
	}

	@FXML
	public void verVendedores() {
		cambiarScene(background, AdministrarVendedorController.URLVista);
	}

	@FXML
	public void verPropietarios() {
		cambiarScene(background, AdministrarPropietarioController.URLVista);
	}

	public void formatearConVendedor(Vendedor vendedor) {
		this.vendedorLogueado = vendedor;
		Platform.runLater(() -> {
			if(!vendedor.getRoot()){
				panelGeneral.getChildren().remove(panelVendedores);
			}
		});
	}
}
