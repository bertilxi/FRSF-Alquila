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
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import app.datos.entidades.Cliente;
import app.datos.entidades.Propietario;
import app.datos.entidades.Vendedor;
import app.datos.entidades.Venta;
import app.ui.ScenographyChanger;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;

public class AdministrarVentaController extends OlimpoController {

	public static final String URLVista = "/app/ui/vistas/administrarVenta.fxml";

	@FXML
	protected Label labelPersona;

	@FXML
	protected TableView<Venta> tablaVentas;

	@FXML
	protected TableColumn<Venta, Object> columnaCliente;
	@FXML
	private TableColumn<Venta, String> columnaFecha;
	@FXML
	private TableColumn<Venta, String> columnaNombreCliente;
	@FXML
	private TableColumn<Venta, String> columnaApellidoCliente;
	@FXML
	protected TableColumn<Venta, Object> columnaPropietario;
	@FXML
	private TableColumn<Venta, String> columnaNombrePropietario;
	@FXML
	private TableColumn<Venta, String> columnaApellidoPropietario;
	@FXML
	protected TableColumn<Venta, Object> columnaVendedor;
	@FXML
	private TableColumn<Venta, String> columnaNombreVendedor;
	@FXML
	private TableColumn<Venta, String> columnaApellidoVendedor;

	@FXML
	private Button botonVerInmueble;

	@FXML
	private Button botonVerDocumento;

	@FXML
	private Pane fondo;

	protected TipoPersona tipoPersona;

	public void setCliente(Cliente persona) {
		Platform.runLater(() -> {
			if(persona != null){
				labelPersona.setText("Cliente: " + persona.getApellido() + ", " + persona.getNombre());
				tablaVentas.getItems().addAll(persona.getVentas());
			}
			columnaCliente.setVisible(false);
			tipoPersona = TipoPersona.Cliente;
		});
	}

	public void setPropietario(Propietario persona) {
		Platform.runLater(() -> {
			if(persona != null){
				labelPersona.setText("Propietario: " + persona.getApellido() + ", " + persona.getNombre());
				tablaVentas.getItems().addAll(persona.getVentas());
			}
			columnaPropietario.setVisible(false);
			tipoPersona = TipoPersona.Propietario;
		});
	}

	public void setVendedor(Vendedor persona) {
		Platform.runLater(() -> {
			if(persona != null){
				labelPersona.setText("Vendedor: " + persona.getApellido() + ", " + persona.getNombre());
				tablaVentas.getItems().addAll(persona.getVentas());
			}
			columnaVendedor.setVisible(false);
			tipoPersona = TipoPersona.Vendedor;
		});
	}

	@Override
	protected void inicializar(URL location, ResourceBundle resources) {
		this.agregarScenographyChanger(fondo, new ScenographyChanger(stage, presentador, coordinador, fondo));
		this.setTitulo("Ventas");

		columnaFecha.setCellValueFactory(cellData -> new SimpleStringProperty(((cellData.getValue().getFecha() != null) ? (new SimpleDateFormat("dd/MM/yyyy").format(cellData.getValue().getFecha())) : (null))));
		columnaNombreCliente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCliente().getNombre()));
		columnaApellidoCliente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCliente().getApellido()));
		columnaNombrePropietario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPropietario().getNombre()));
		columnaApellidoPropietario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPropietario().getApellido()));
		columnaNombreVendedor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVendedor().getNombre()));
		columnaApellidoVendedor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVendedor().getApellido()));

		habilitarBotones(null);

		tablaVentas.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> habilitarBotones(newValue));
	}

	/**
	 * Habilita o deshabilita botones según si hay una venta seleccionada o no
	 *
	 * @param venta
	 *            venta seleccionada. Si no hay venta seleccionada, es <code>null</code>
	 */
	private void habilitarBotones(Venta venta) {
		if(venta == null){
			botonVerDocumento.setDisable(true);
			botonVerInmueble.setDisable(true);
		}
		else{
			botonVerDocumento.setDisable(false);
			botonVerInmueble.setDisable(false);
		}
	}

	/**
	 * Acción que se ejecuta al presionar el botón ver inmueble
	 */
	@FXML
	protected void handleVerInmueble() {
		if(tablaVentas.getSelectionModel().getSelectedItem() != null){
			VerBasicosInmuebleController controlador = (VerBasicosInmuebleController) cambiarmeAScene(VerBasicosInmuebleController.URLVista);
			controlador.setInmueble(tablaVentas.getSelectionModel().getSelectedItem().getInmueble());
			controlador.setVendedorLogueado(vendedorLogueado);
			switch(tipoPersona) {
			case Cliente:
				controlador.setCliente(tablaVentas.getSelectionModel().getSelectedItem().getCliente());
				break;
			case Propietario:
				controlador.setPropietario(tablaVentas.getSelectionModel().getSelectedItem().getPropietario());
				break;
			case Vendedor:
				controlador.setVendedor(tablaVentas.getSelectionModel().getSelectedItem().getVendedor());
				break;
			}
		}
	}

	/**
	 * Acción que se ejecuta al presionar el botón ver documento
	 */
	@FXML
	protected void handleVerDocumento() {
		Venta venta = tablaVentas.getSelectionModel().getSelectedItem();
		if(venta == null){
			return;
		}

		VerPDFController visorPDF = (VerPDFController) cambiarScene(fondo, VerPDFController.URLVista, (Pane) fondo.getChildren().get(0));
		visorPDF.cargarPDF(venta.getArchivoPDF());
		visorPDF.setVendedorLogueado(vendedorLogueado);
	}

	private enum TipoPersona {
		Cliente,
		Propietario,
		Vendedor
	}
}
