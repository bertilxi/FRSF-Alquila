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
import app.excepciones.PersistenciaException;
import app.logica.resultados.ResultadoEliminarVendedor;
import app.logica.resultados.ResultadoEliminarVendedor.ErrorEliminarVendedor;
import app.ui.componentes.ventanas.VentanaConfirmacion;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Controlador de la vista que lista y administra los vendedores
 * Permite la creación, modificación y eliminación de vendedores
 */
public class AdministrarVendedorController extends OlimpoController {

	public static final String URLVista = "/app/ui/vistas/administrarVendedor.fxml";

	@FXML
	protected TableView<Vendedor> tablaVendedores;
	@FXML
	private TableColumn<Vendedor, String> columnaNumeroDocumento;
	@FXML
	private TableColumn<Vendedor, String> columnaNombre;
	@FXML
	private TableColumn<Vendedor, String> columnaApellido;

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
		setTitulo("Administrar vendedores");
		try{
			tablaVendedores.getItems().addAll(coordinador.obtenerVendedores());
		} catch(PersistenciaException e){
			presentador.presentarExcepcion(e, stage);
		} catch(Exception e){
			presentador.presentarExcepcion(e, stage);
		}

		columnaNumeroDocumento.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumeroDocumento()));
		columnaNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
		columnaApellido.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApellido()));

		habilitarBotones(null);

		tablaVendedores.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> habilitarBotones(newValue));
	}

	/**
	 * Habilita o deshabilita botones según si hay un vendedor seleccionado o no
	 *
	 * @param vendedor
	 *            vendedor seleccionado. Si no hay vendedor seleccionado, es <code>null</code>
	 */
	private void habilitarBotones(Vendedor vendedor) {
		if(vendedor == null){
			botonVerVentas.setDisable(true);
			botonModificar.setDisable(true);
			botonEliminar.setDisable(true);
		}
		else{
			botonVerVentas.setDisable(false);
			botonModificar.setDisable(false);
			botonEliminar.setDisable(false);
		}
	}

	/**
	 * Acción que se ejecuta al presionar el botón ver ventas
	 * Se pasa a la pantalla administrar ventas con el vendedor seleccionado
	 */
	@FXML
	private void handleVerVentas() {
		if(tablaVendedores.getSelectionModel().getSelectedItem() == null){
			return;
		}
		AdministrarVentaController controlador = (AdministrarVentaController) cambiarmeAScene(AdministrarVentaController.URLVista);
		controlador.setVendedor(tablaVendedores.getSelectionModel().getSelectedItem());
		controlador.setVendedorLogueado(vendedorLogueado);
	}

	/**
	 * Acción que se ejecuta al presionar el botón agregar
	 * Se pasa a la pantalla alta vendedor
	 */
	public void agregarAction(ActionEvent event) {
		AltaVendedorController controlador = (AltaVendedorController) cambiarmeAScene(AltaVendedorController.URLVista, URLVista);
		controlador.setVendedorLogueado(vendedorLogueado);
	}

	/**
	 * Acción que se ejecuta al presionar el botón modificar
	 * Se pasa a la pantalla modificar vendedor
	 */
	public void modificarAction(ActionEvent event) {
		Vendedor vendedor = tablaVendedores.getSelectionModel().getSelectedItem();
		if(vendedor == null){
			return;
		}
		ModificarVendedorController controlador = (ModificarVendedorController) cambiarmeAScene(ModificarVendedorController.URLVista, URLVista);
		controlador.setVendedor(vendedor);
		controlador.setVendedorLogueado(vendedorLogueado);
	}

	/**
	 * Acción que se ejecuta al presionar el botón eliminar
	 * Se muestra una ventana emergente para confirmar la operación
	 */
	public void eliminarAction(ActionEvent event) {
		Vendedor vendedor = tablaVendedores.getSelectionModel().getSelectedItem();
		if(vendedor == null){
			return;
		}
		VentanaConfirmacion ventana = presentador.presentarConfirmacion("Eliminar vendedor", "Está a punto de eliminar al vendedor.\n¿Desea continuar?", this.stage);
		if(ventana.acepta()){
			try{
				ResultadoEliminarVendedor resultado = coordinador.eliminarVendedor(vendedor);
				if(resultado.hayErrores()){
					StringBuilder stringErrores = new StringBuilder();
					for(ErrorEliminarVendedor err: resultado.getErrores()){
						switch(err) {

						}
					}
					presentador.presentarError("No se pudo eliminar el vendedor", stringErrores.toString(), stage);

				}
				else{
					tablaVendedores.getItems().remove(vendedor);
					presentador.presentarToast("Se ha eliminado al vendedor " + vendedor.getNombre() + " con éxito", stage);
				}
			} catch(PersistenciaException e){
				presentador.presentarExcepcion(e, stage);
			}
		}
	}
}
