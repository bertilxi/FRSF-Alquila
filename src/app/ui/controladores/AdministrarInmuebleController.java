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

import app.datos.entidades.Inmueble;
import app.ui.controladores.resultado.ResultadoControlador;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Controlador de la vista de administración de inmuebles que se encarga de manejar el listado y la eliminación de inmuebles
 * Pertenece a la taskcard 13 de la iteración 1 y a la historia 3
 */
public class AdministrarInmuebleController extends OlimpoController {

	public static final String URLVista = "/app/ui/vistas/administrarInmueble.fxml";

	@FXML
	private TableView<Inmueble> tablaInmuebles;

	@FXML
	private TableColumn<Inmueble, String> columnaTipoInmueble;

	@FXML
	private TableColumn<Inmueble, String> columnaUbicacionInmueble;

	@FXML
	private TableColumn<Inmueble, String> columnaPropietarioInmueble;

	@FXML
	private Button btVerMas;

	@FXML
	private Button btModificar;

	@FXML
	private Button btEliminar;

	@Override
	protected void inicializar(URL location, ResourceBundle resources) {
		setTitulo("Administrar inmuebles");

		tablaInmuebles.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			Boolean noHayInmuebleSeleccionado = newValue == null;
			btVerMas.setDisable(noHayInmuebleSeleccionado);
			btModificar.setDisable(noHayInmuebleSeleccionado);
			btEliminar.setDisable(noHayInmuebleSeleccionado);
		});

		columnaTipoInmueble.setCellValueFactory(param -> {
			if(param.getValue() != null){
				if(param.getValue().getTipo() != null){
					return new SimpleStringProperty(param.getValue().getTipo().toString());
				}
			}
			return new SimpleStringProperty("<Sin tipo>");
		});
		columnaUbicacionInmueble.setCellValueFactory(param -> {
			if(param.getValue() != null){
				if(param.getValue().getDireccion() != null){
					return new SimpleStringProperty(param.getValue().getDireccion().toString());
				}
			}
			return new SimpleStringProperty("<Sin ubicación>");
		});
		columnaPropietarioInmueble.setCellValueFactory(param -> {
			if(param.getValue() != null){
				if(param.getValue().getPropietario() != null){
					return new SimpleStringProperty(param.getValue().getPropietario().toString());
				}
			}
			return new SimpleStringProperty("<Sin propietario>");
		});
	}

	@FXML
	public void agregar() {
		NMVInmuebleController nuevaPantalla = (NMVInmuebleController) cambiarmeAScene(NMVInmuebleController.URLVista);
	}

	@FXML
	public void modificar() {
		Inmueble inmueble = tablaInmuebles.getSelectionModel().getSelectedItem();
		if(inmueble == null){
			return;
		}
		NMVInmuebleController nuevaPantalla = (NMVInmuebleController) cambiarmeAScene(NMVInmuebleController.URLVista);
		nuevaPantalla.formatearModificarInmueble(inmueble);
	}

	@FXML
	public void verMas() {
		Inmueble inmueble = tablaInmuebles.getSelectionModel().getSelectedItem();
		if(inmueble == null){
			return;
		}
		NMVInmuebleController nuevaPantalla = (NMVInmuebleController) cambiarmeAScene(NMVInmuebleController.URLVista);
		nuevaPantalla.formatearVerInmueble(inmueble);
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
