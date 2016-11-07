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

import app.datos.entidades.Propietario;
import app.excepciones.PersistenciaException;
import app.logica.resultados.ResultadoEliminarPropietario;
import app.logica.resultados.ResultadoEliminarPropietario.ErrorEliminarPropietario;
import app.ui.componentes.ventanas.VentanaConfirmacion;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Controlador de la vista que lista y administra los propietarios
 */
public class AdministrarPropietarioController extends OlimpoController {

	public static final String URLVista = "/app/ui/vistas/administrarPropietario.fxml";

	@FXML
	private TableView<Propietario> tablaPropietarios;

	@FXML
	private TableColumn<Propietario, String> columnaNumeroDocumento;
	@FXML
	private TableColumn<Propietario, String> columnaNombre;
	@FXML
	private TableColumn<Propietario, String> columnaApellido;
	@FXML
	private TableColumn<Propietario, String> columnaTelefono;

	@FXML
	private Button botonVer;
	@FXML
	private Button botonAgregar;
	@FXML
	private Button botonModificar;
	@FXML
	private Button botonEliminar;


	@Override
	public void inicializar(URL location, ResourceBundle resources) {
		setTitulo("Administrar propietarios");

		try{
			tablaPropietarios.getItems().addAll(coordinador.obtenerPropietarios());
		} catch(PersistenciaException e){
			presentador.presentarError("Error", "No se pudieron listar los propietarios", stage);
		}

		columnaNumeroDocumento.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumeroDocumento()));
		columnaNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
		columnaApellido.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApellido()));
		columnaTelefono.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefono()));

		habilitarBotones(null);

		tablaPropietarios.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> habilitarBotones(newValue));
	}

	/**
	 * Habilita o deshabilita botones según si hay un propietario seleccionado o no
	 *
	 * @param propietario
	 * 		propietario seleccionado. Si no hay propietario seleccionado, es <code>null</code>
	 */
	private void habilitarBotones(Propietario propietario) {
		if(propietario == null){
			botonVer.setDisable(true);
			botonModificar.setDisable(true);
			botonEliminar.setDisable(true);
		}
		else{
			botonVer.setDisable(false);
			botonModificar.setDisable(false);
			botonEliminar.setDisable(false);
		}
	}

	/**
	 * acción que se ejecuta al presionar el botón ver más
	 */
	@FXML
	private void handleVer() {
		if(tablaPropietarios.getSelectionModel().getSelectedItem() == null){
			return;
		}
		VerPropietarioController controlador = (VerPropietarioController) cambiarmeAScene(VerPropietarioController.URLVista);
		controlador.setPropietario(tablaPropietarios.getSelectionModel().getSelectedItem());
	}

	/**
	 * acción que se ejecuta al presionar el botón agregar
	 */
	@FXML
	private void handleAgregar() {
		cambiarmeAScene(AltaPropietarioController.URLVista);
	}

	@FXML
	private void handleModificar() {
		if(tablaPropietarios.getSelectionModel().getSelectedItem() == null){
			return;
		}
		ModificarPropietarioController controlador = (ModificarPropietarioController) cambiarmeAScene(ModificarPropietarioController.URLVista);
		controlador.setPropietarioEnModificacion(tablaPropietarios.getSelectionModel().getSelectedItem());
	}

	/**
	 * acción que se ejecuta al presionar el botón eliminar
	 */
	@FXML
	private void handleEliminar() {
		if(tablaPropietarios.getSelectionModel().getSelectedItem() == null){
			return;
		}
		VentanaConfirmacion ventana = presentador.presentarConfirmacion("Eliminar propietario", "Está a punto de eliminar al propietario.\n ¿Está seguro que desea hacerlo?", this.stage);
		if(ventana.acepta()){
			try{
				ResultadoEliminarPropietario resultado = coordinador.eliminarPropietario(tablaPropietarios.getSelectionModel().getSelectedItem());
				if(resultado.hayErrores()){
					StringBuilder stringErrores = new StringBuilder();
					for(ErrorEliminarPropietario err: resultado.getErrores()){
						switch(err) {
						case No_Existe_Propietario:
							stringErrores.append("No existe el propietario que desea eliminar.\n");
							break;
						}
					}
					presentador.presentarError("No se pudo eliminar el propietario", stringErrores.toString(), stage);

				}
				tablaPropietarios.getItems().clear();
				tablaPropietarios.getItems().addAll(coordinador.obtenerPropietarios());
			} catch(PersistenciaException e){
				presentador.presentarExcepcion(e, stage);
			}
		}
	}
}
