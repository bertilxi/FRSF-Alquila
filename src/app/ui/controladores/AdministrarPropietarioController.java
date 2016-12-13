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
import java.util.ArrayList;
import java.util.ResourceBundle;

import app.datos.entidades.Propietario;
import app.excepciones.PersistenciaException;
import app.logica.resultados.ResultadoEliminarPropietario;
import app.logica.resultados.ResultadoEliminarPropietario.ErrorEliminarPropietario;
import app.ui.componentes.ventanas.VentanaConfirmacion;
import app.ui.controladores.resultado.ResultadoControlador;
import app.ui.controladores.resultado.ResultadoControlador.ErrorControlador;
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
	protected TableView<Propietario> tablaPropietarios;

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
	private Button botonVerVentas;
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
	 *            propietario seleccionado. Si no hay propietario seleccionado, es <code>null</code>
	 */
	private void habilitarBotones(Propietario propietario) {
		if(propietario == null){
			botonVer.setDisable(true);
			botonVerVentas.setDisable(true);
			botonModificar.setDisable(true);
			botonEliminar.setDisable(true);
		}
		else{
			botonVer.setDisable(false);
			botonVerVentas.setDisable(false);
			botonModificar.setDisable(false);
			botonEliminar.setDisable(false);
		}
	}

	/**
	 * Acción que se ejecuta al presionar el botón ver más
	 * Se pasa a la pantalla ver propietario
	 */
	@FXML
	private void handleVer() {
		if(tablaPropietarios.getSelectionModel().getSelectedItem() == null){
			return;
		}
		VerPropietarioController controlador = (VerPropietarioController) cambiarmeAScene(VerPropietarioController.URLVista);
		controlador.setPropietario(tablaPropietarios.getSelectionModel().getSelectedItem());
		controlador.setVendedorLogueado(vendedorLogueado);
	}

	/**
	 * Acción que se ejecuta al presionar el botón ver ventas
	 * Se pasa a la pantalla administrar ventas del propietario seleccionado
	 */
	@FXML
	private void handleVerVentas() {
		if(tablaPropietarios.getSelectionModel().getSelectedItem() == null){
			return;
		}
		AdministrarVentaController controlador = (AdministrarVentaController) cambiarmeAScene(AdministrarVentaController.URLVista);
		controlador.setPropietario(tablaPropietarios.getSelectionModel().getSelectedItem());
		controlador.setVendedorLogueado(vendedorLogueado);
	}

	/**
	 * Acción que se ejecuta al presionar el botón agregar
	 * Se pasa a la pantalla alta propietario
	 */
	@FXML
	private void handleAgregar() {
		AltaPropietarioController controlador = (AltaPropietarioController) cambiarmeAScene(AltaPropietarioController.URLVista);
		controlador.setVendedorLogueado(vendedorLogueado);
	}

	/**
	 * Acción que se ejecuta al presionar el botón modificar propietario
	 * Se pasa a la pantalla modificar propietario
	 */
	@FXML
	private void handleModificar() {
		if(tablaPropietarios.getSelectionModel().getSelectedItem() == null){
			return;
		}
		ModificarPropietarioController controlador = (ModificarPropietarioController) cambiarmeAScene(ModificarPropietarioController.URLVista);
		controlador.setPropietarioEnModificacion(tablaPropietarios.getSelectionModel().getSelectedItem());
		controlador.setVendedorLogueado(vendedorLogueado);
	}

	/**
	 * Acción que se ejecuta al presionar el botón eliminar
	 * Se muestra una ventana emergente para confirmar la operación
	 */
	@FXML
	protected ResultadoControlador handleEliminar() {
		ArrayList<ErrorControlador> erroresControlador = new ArrayList<>();

		if(tablaPropietarios.getSelectionModel().getSelectedItem() == null){
			return new ResultadoControlador(ErrorControlador.Campos_Vacios);
		}
		VentanaConfirmacion ventana = presentador.presentarConfirmacion("Eliminar propietario", "Está a punto de eliminar al propietario.\n ¿Está seguro que desea hacerlo?", this.stage);
		if(!ventana.acepta()){
			return new ResultadoControlador();
		}
		try{
			ResultadoEliminarPropietario resultado = coordinador.eliminarPropietario(tablaPropietarios.getSelectionModel().getSelectedItem());
			if(resultado.hayErrores()){
				StringBuilder stringErrores = new StringBuilder();
				for(ErrorEliminarPropietario err: resultado.getErrores()){
					switch(err) {

					}
				}
				presentador.presentarError("No se pudo eliminar el propietario", stringErrores.toString(), stage);

			}
			else{
				presentador.presentarToast("Se ha eliminado el propietario con éxito", stage);
			}
			tablaPropietarios.getItems().clear();
			tablaPropietarios.getItems().addAll(coordinador.obtenerPropietarios());
			return new ResultadoControlador(erroresControlador.toArray(new ErrorControlador[0]));
		} catch(PersistenciaException e){
			presentador.presentarExcepcion(e, stage);
			return new ResultadoControlador(ErrorControlador.Error_Persistencia);
		} catch(Exception e){
			presentador.presentarExcepcionInesperada(e, stage);
			return new ResultadoControlador(ErrorControlador.Error_Desconocido);
		}
	}
}
