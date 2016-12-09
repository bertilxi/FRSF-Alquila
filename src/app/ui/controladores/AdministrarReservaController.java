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

import app.datos.entidades.Cliente;
import app.datos.entidades.Inmueble;
import app.datos.entidades.Reserva;
import app.excepciones.PersistenciaException;
import app.logica.resultados.ResultadoEliminarReserva;
import app.logica.resultados.ResultadoEliminarReserva.ErrorEliminarReserva;
import app.ui.componentes.ventanas.VentanaConfirmacion;
import app.ui.controladores.resultado.ResultadoControlador;
import app.ui.controladores.resultado.ResultadoControlador.ErrorControlador;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Controlador de la vista que lista y administra las reservas
 * Permite la creación de una nueva reserva para un inmueble o un cliente (según de donde se invoque)
 * Permite ver el comprobante PDF de una reserva
 * Permite la eliminacion de una reserva
 */
public class AdministrarReservaController extends OlimpoController {

	public static final String URLVista = "/app/ui/vistas/administrarReserva.fxml";

	@FXML
	protected TableView<Reserva> tablaReservas;
	@FXML
	private TableColumn<Reserva, String> columnaClienteOInmueble;
	@FXML
	private TableColumn<Reserva, String> columnaImporte;
	@FXML
	private TableColumn<Reserva, String> columnaFechaInicio;
	@FXML
	private TableColumn<Reserva, String> columnaFechaFin;

	@FXML
	private Button botonNuevo;
	@FXML
	private Button botonVerPDF;
	@FXML
	private Button botonEliminar;

	protected Cliente cliente;
	protected Inmueble inmueble;

	@Override
	public void inicializar(URL location, ResourceBundle resources) {
		setTitulo("Administrar reservas");
		if(cliente != null){
			tablaReservas.getItems().addAll(cliente.getReservas());
			columnaClienteOInmueble.setText("Inmueble");
			columnaClienteOInmueble.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getInmueble().getDireccion().toString()));
		}
		else if(inmueble != null){
			tablaReservas.getItems().addAll(inmueble.getReservas());
			columnaClienteOInmueble.setText("Cliente");
			columnaClienteOInmueble.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCliente().toString()));
		}

		columnaImporte.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getImporte().toString()));
		columnaFechaInicio.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFechaInicio().toString()));
		columnaFechaFin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFechaFin().toString()));

		habilitarBotones(null);

		tablaReservas.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> habilitarBotones(newValue));
	}

	/**
	 * Habilita o deshabilita botones según si hay una reserva seleccionado o no
	 *
	 * @param reserva
	 *            reserva seleccionada. Si no hay reserva seleccionado, es <code>null</code>
	 */
	private void habilitarBotones(Reserva reserva) {
		if(reserva == null){
			botonVerPDF.setDisable(true);
			botonEliminar.setDisable(true);
		}
		else{
			botonVerPDF.setDisable(false);
			botonEliminar.setDisable(false);
		}
	}

	/**
	 * Acción que se ejecuta al presionar el botón nuevo
	 * Se pasa a la pantalla alta reserva
	 */
	public void nuevoAction(ActionEvent event) {
		cambiarmeAScene(AltaReservaController.URLVista, URLVista);
	}

	/**
	 * Acción que se ejecuta al presionar el botón modificar
	 * Se pasa a la pantalla modificar reserva
	 */
	public void modificarAction(ActionEvent event) {
		Reserva reserva = tablaReservas.getSelectionModel().getSelectedItem();
		if(reserva == null){
			return;
		}
		ModificarReservaController modificarReservaController = (ModificarReservaController) cambiarmeAScene(ModificarReservaController.URLVista, URLVista);
		modificarReservaController.setReserva(reserva);
	}

	/**
	 * Acción que se ejecuta al presionar el botón eliminar
	 * Se muestra una ventana emergente para confirmar la operación
	 */
	public ResultadoControlador eliminarAction(ActionEvent event) {
		Reserva reserva = tablaReservas.getSelectionModel().getSelectedItem();
		if(reserva == null){
			return new ResultadoControlador();
		}
		VentanaConfirmacion ventana = presentador.presentarConfirmacion("Eliminar reserva", "Está a punto de eliminar al reserva.\n¿Desea continuar?", this.stage);
		if(ventana.acepta()){
			return new ResultadoControlador();
		}
		ResultadoEliminarReserva resultado;
		try{
			resultado = coordinador.eliminarReserva(reserva);
		} catch(PersistenciaException e){
			presentador.presentarExcepcion(e, stage);
			return new ResultadoControlador(ErrorControlador.Error_Persistencia);
		}

		if(resultado.hayErrores()){
			StringBuilder stringErrores = new StringBuilder();
			for(ErrorEliminarReserva err: resultado.getErrores()){
				switch(err) {
				case No_Existe_Reserva:
					stringErrores.append("No existe el reserva que desea eliminar.\n");
					break;
				}
			}
			presentador.presentarError("No se pudo eliminar el reserva", stringErrores.toString(), stage);
		}
		else{
			tablaReservas.getItems().remove(reserva);
			presentador.presentarToast("Se ha eliminado al reserva " + reserva.toString() + " con éxito", stage);
		}
		return new ResultadoControlador();
	}
}
