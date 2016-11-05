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
package app.ui.controladores;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import app.datos.entidades.Cliente;
import app.excepciones.PersistenciaException;
import app.logica.resultados.ResultadoEliminarCliente;
import app.logica.resultados.ResultadoEliminarCliente.ErrorEliminarCliente;
import app.ui.componentes.ventanas.VentanaConfirmacion;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AdministrarClienteController extends OlimpoController {

	public static final String URLVista = "/app/ui/vistas/administrarCliente.fxml";

	@FXML
	private TableView<Cliente> tablaClientes;

	@FXML
	private TableColumn<Cliente, String> columnaNumeroDocumento;
	@FXML
	private TableColumn<Cliente, String> columnaNombre;
	@FXML
	private TableColumn<Cliente, String> columnaApellido;
	@FXML
	private TableColumn<Cliente, String> columnaTelefono;

	@FXML
	private Button botonAgregar;
	@FXML
	private Button botonModificar;
	@FXML
	private Button botonEliminar;

	private ArrayList<Cliente> listaClientes;

	@Override
	public void inicializar(URL location, ResourceBundle resources) {

		try{
			listaClientes = coordinador.obtenerClientes();
		} catch(PersistenciaException e){
			presentador.presentarExcepcion(e, stage);
		} catch(Exception e){
			presentador.presentarExcepcionInesperada(e, stage);
		}

		columnaNumeroDocumento.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumeroDocumento()));
		columnaNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
		columnaApellido.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApellido()));
		columnaTelefono.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefono()));

		tablaClientes.getItems().addAll(listaClientes);

		habilitarBotones(null);

		tablaClientes.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> habilitarBotones(newValue));
	}

	private void habilitarBotones(Cliente cliente) {
		if(cliente == null){
			botonModificar.setDisable(true);
			botonEliminar.setDisable(true);
		}
		else{
			botonModificar.setDisable(false);
			botonEliminar.setDisable(false);
		}
	}

	@FXML
	private void handleAgregar() {
		AltaClienteController controlador = (AltaClienteController) cambiarmeAScene(AltaClienteController.URLVista);
		controlador.setCliente(null);
	}

	@FXML
	private void handleModificar() {
		if(tablaClientes.getSelectionModel().getSelectedItem() == null){
			return;
		}
		ModificarClienteController controlador = (ModificarClienteController) cambiarmeAScene(ModificarClienteController.URLVista);
		controlador.setClienteEnModificacion(tablaClientes.getSelectionModel().getSelectedItem());
	}

	@FXML
	private void handleEliminar() {
		if(tablaClientes.getSelectionModel().getSelectedItem() == null){
			return;
		}
		VentanaConfirmacion ventana = presentador.presentarConfirmacion("Eliminar cliente", "Está a punto de eliminar al cliente.\n ¿Está seguro que desea hacerlo?", this.stage);
		if(ventana.acepta()){
			try{
				ResultadoEliminarCliente resultado = coordinador.eliminarCliente(tablaClientes.getSelectionModel().getSelectedItem());
				if(resultado.hayErrores()){
					StringBuilder stringErrores = new StringBuilder();
					for(ErrorEliminarCliente err: resultado.getErrores()){
						switch(err) {
						case No_Existe_Cliente:
							stringErrores.append("No existe el cliente que desea eliminar.\n");
							break;
						}
					}
					presentador.presentarError("No se pudo eliminar el cliente", stringErrores.toString(), stage);

				}
				tablaClientes.getItems().clear();
				tablaClientes.getItems().addAll(coordinador.obtenerClientes());
			} catch(PersistenciaException e){
				presentador.presentarExcepcion(e, stage);
			}
		}
	}
}
