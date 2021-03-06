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

import app.comun.ConversorFechas;
import app.datos.clases.EstadoInmuebleStr;
import app.datos.entidades.Cliente;
import app.datos.entidades.Inmueble;
import app.datos.entidades.Reserva;
import app.excepciones.PersistenciaException;
import app.logica.resultados.ResultadoEliminarReserva;
import app.logica.resultados.ResultadoEliminarReserva.ErrorEliminarReserva;
import app.ui.ScenographyChanger;
import app.ui.componentes.ventanas.VentanaConfirmacion;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;

/**
 * Controlador de la vista que lista y administra las reservas
 * Permite la creación de una nueva reserva para un inmueble o un cliente (según de donde se invoque)
 * Permite ver el comprobante PDF de una reserva
 * Permite la eliminacion de una reserva
 */
public class AdministrarReservaController extends OlimpoController {

	public static final String URLVista = "/app/ui/vistas/administrarReserva.fxml";
	protected Cliente cliente;
	protected Inmueble inmueble;
	protected ConversorFechas conversorFechas = new ConversorFechas();

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

	@FXML
	private Pane fondo;

	@Override
	public void inicializar(URL location, ResourceBundle resources) {
		this.agregarScenographyChanger(fondo, new ScenographyChanger(stage, presentador, coordinador, fondo));
		setTitulo("Administrar reservas");
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
		//Se verifica que se haya seleccionado un inmueble y que este no esté vendido
		if(inmueble != null && inmueble.getEstadoInmueble().getEstado().equals(EstadoInmuebleStr.VENDIDO)){
			presentador.presentarError("Error al crear reserva", "El inmueble ya fue vendido", stage);
			return;
		}
		//Se llama a la pantalla alta reserva
		AltaReservaController controlador = (AltaReservaController) cambiarmeAScene(AltaReservaController.URLVista, URLVista);
		//Se le pasan a la pantalla los datos del cliente o inmueble según corresponda
		if(cliente != null){
			controlador.setCliente(cliente);
			controlador.setVendedorLogueado(vendedorLogueado);
		}
		else if(inmueble != null){
			controlador.setInmueble(inmueble);
			controlador.setVendedorLogueado(vendedorLogueado);
		}
	}

	/**
	 * Acción que se ejecuta al presionar el botón ver
	 * Se pasa a la pantalla de ver pdf
	 */
	public void verAction(ActionEvent event) {
		Reserva reserva = tablaReservas.getSelectionModel().getSelectedItem();
		//Se comprueba que se haya seleccionado una reserva
		if(reserva == null){
			return;
		}
		
		//Se llama a la pantalla que muestra los PDFs
		VerPDFController visorPDF = (VerPDFController) cambiarScene(fondo, VerPDFController.URLVista, (Pane) fondo.getChildren().get(0));
		visorPDF.cargarPDF(reserva.getArchivoPDF());
	}

	/**
	 * Acción que se ejecuta al presionar el botón eliminar
	 * Se muestra una ventana emergente para confirmar la operación
	 * Si acepta se da de baja la reserva
	 */
	public void eliminarAction(ActionEvent event) {
		Reserva reserva = tablaReservas.getSelectionModel().getSelectedItem();
		//Se comprueba que se haya seleccionado una reserva
		if(reserva == null){
			return;
		}
		//Se muestra una ventana para que el usuario confirme la operación
		VentanaConfirmacion ventana = presentador.presentarConfirmacion("Eliminar reserva", "Está a punto de eliminar una reserva.\n¿Desea continuar?", this.stage);
		if(ventana.acepta()){
			ResultadoEliminarReserva resultado = new ResultadoEliminarReserva();
			try{
				//Se da de baja la reserva
				resultado = coordinador.eliminarReserva(reserva);
			} catch(PersistenciaException e){
				presentador.presentarExcepcion(e, stage);
			}

			//Se muestran los errores devueltos por el método de la lógica
			if(resultado.hayErrores()){
				StringBuilder stringErrores = new StringBuilder();
				for(ErrorEliminarReserva err: resultado.getErrores()){
					switch(err) {
						//Por el momento no hay errores
					}
				}
				presentador.presentarError("Ha ocurrido un error", stringErrores.toString(), stage);
			}
			else{
				//Si no hay errores se muestra una notificación y se remueve la reserva de la lista
				tablaReservas.getItems().remove(reserva);
				presentador.presentarToast("Se ha eliminado la reserva con éxito", stage);
			}
		}
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
		if(cliente != null){
			try{
				tablaReservas.getItems().addAll(coordinador.obtenerReservas(cliente));
			} catch(PersistenciaException e){
				presentador.presentarExcepcion(e, stage);
			}
			columnaClienteOInmueble.setText("Inmueble");
			columnaClienteOInmueble.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getInmueble().getDireccion().toString()));
			columnaFechaInicio.setCellValueFactory(cellData -> new SimpleStringProperty(conversorFechas.diaMesYAnioToString(cellData.getValue().getFechaInicio())));
			columnaFechaFin.setCellValueFactory(cellData -> new SimpleStringProperty(conversorFechas.diaMesYAnioToString(cellData.getValue().getFechaFin())));
			columnaImporte.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getImporte().toString()));
		}
	}

	public void setInmueble(Inmueble inmueble) {
		this.inmueble = inmueble;
		if(inmueble != null){
			try{
				tablaReservas.getItems().addAll(coordinador.obtenerReservas(inmueble));
			} catch(PersistenciaException e){
				presentador.presentarExcepcion(e, stage);
			}
			columnaClienteOInmueble.setText("Cliente");
			columnaClienteOInmueble.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCliente().toString()));
			columnaFechaInicio.setCellValueFactory(cellData -> new SimpleStringProperty(conversorFechas.diaMesYAnioToString(cellData.getValue().getFechaInicio())));
			columnaFechaFin.setCellValueFactory(cellData -> new SimpleStringProperty(conversorFechas.diaMesYAnioToString(cellData.getValue().getFechaFin())));
			columnaImporte.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getImporte().toString()));
		}
	}
}
