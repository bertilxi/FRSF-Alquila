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

import app.datos.clases.EstadoInmuebleStr;
import app.datos.entidades.Inmueble;
import app.excepciones.PersistenciaException;
import app.logica.resultados.ResultadoEliminarInmueble;
import app.logica.resultados.ResultadoEliminarInmueble.ErrorEliminarInmueble;
import app.ui.componentes.ventanas.VentanaConfirmacion;
import app.ui.controladores.resultado.ResultadoControlador;
import app.ui.controladores.resultado.ResultadoControlador.ErrorControlador;
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
	protected TableView<Inmueble> tablaInmuebles;

	@FXML
	private TableColumn<Inmueble, String> columnaTipoInmueble;

	@FXML
	private TableColumn<Inmueble, String> columnaUbicacionInmueble;

	@FXML
	private TableColumn<Inmueble, String> columnaPropietarioInmueble;

	@FXML
	private Button btVerMas;

	@FXML
	private Button btVerReservas;

	@FXML
	private Button btModificar;

	@FXML
	private Button btEliminar;

	@FXML
	private Button btVender;

	@Override
	protected void inicializar(URL location, ResourceBundle resources) {
		setTitulo("Administrar inmuebles");

		tablaInmuebles.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			Boolean noHayInmuebleSeleccionado = newValue == null;
			btVerMas.setDisable(noHayInmuebleSeleccionado);
			btModificar.setDisable(noHayInmuebleSeleccionado);
			btEliminar.setDisable(noHayInmuebleSeleccionado);
			btVender.setDisable(noHayInmuebleSeleccionado);
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

		try{
			tablaInmuebles.getItems().addAll(coordinador.obtenerInmuebles());
		} catch(PersistenciaException e){
			presentador.presentarExcepcion(e, stage);
		}
	}

	@FXML
	public void agregar() {
		cambiarmeAScene(NMVInmuebleController.URLVista, URLVista);
	}

	@FXML
	public void modificar() {
		Inmueble inmueble = tablaInmuebles.getSelectionModel().getSelectedItem();
		if(inmueble == null){
			return;
		}
		NMVInmuebleController nuevaPantalla = (NMVInmuebleController) cambiarmeAScene(NMVInmuebleController.URLVista, URLVista);
		nuevaPantalla.formatearModificarInmueble(inmueble);
	}

	@FXML
	public void verMas() {
		Inmueble inmueble = tablaInmuebles.getSelectionModel().getSelectedItem();
		if(inmueble == null){
			return;
		}
		NMVInmuebleController nuevaPantalla = (NMVInmuebleController) cambiarmeAScene(NMVInmuebleController.URLVista, URLVista);
		nuevaPantalla.formatearVerInmueble(inmueble);
	}

	/**
	 * Acción que se ejecuta al presionar el botón ver reservas
	 * Se pasa a la pantalla administrar reservas con el inmueble seleccionado
	 */
	@FXML
	private void verReservas() {
		if(tablaInmuebles.getSelectionModel().getSelectedItem() == null){
			return;
		}
		AdministrarReservaController controlador = (AdministrarReservaController) cambiarmeAScene(AdministrarReservaController.URLVista);
		controlador.setInmueble(tablaInmuebles.getSelectionModel().getSelectedItem());
	}

	/**
	 * Método que permite eliminar un inmueble
	 * Pertenece a la taskcard 13 de la iteración 1 y a la historia 3
	 *
	 * @return ResultadoControlador que resume lo que hizo el controlador
	 */
	@FXML
	public ResultadoControlador eliminarInmueble() {
		ArrayList<ErrorControlador> erroresControlador = new ArrayList<>();
		ResultadoEliminarInmueble resultado;
		StringBuffer erroresBfr = new StringBuffer();

		//Toma de datos de la vista
		Inmueble inmueble = tablaInmuebles.getSelectionModel().getSelectedItem();
		if(inmueble == null){
			return new ResultadoControlador(ErrorControlador.Campos_Vacios);
		}
		VentanaConfirmacion ventana = presentador.presentarConfirmacion("Eliminar inmueble", "Está a punto de eliminar a el inmueble.\n ¿Está seguro que desea hacerlo?", this.stage);
		if(!ventana.acepta()){
			return new ResultadoControlador();
		}
		try{
			resultado = coordinador.eliminarInmueble(inmueble);
		} catch(PersistenciaException e){
			presentador.presentarExcepcion(e, stage);
			return new ResultadoControlador(ErrorControlador.Error_Persistencia);
		} catch(Exception e){
			presentador.presentarExcepcionInesperada(e, stage);
			return new ResultadoControlador(ErrorControlador.Error_Desconocido);
		}

		if(resultado.hayErrores()){
			for(ErrorEliminarInmueble err: resultado.getErrores()){
				switch(err) {

				}
			}
			presentador.presentarError("No se pudo crear el inmueble", erroresBfr.toString(), stage);
		}
		else{
			presentador.presentarToast("Se ha eliminado el inmueble con éxito", stage);
			tablaInmuebles.getItems().remove(inmueble);
		}

		return new ResultadoControlador(erroresControlador.toArray(new ErrorControlador[0]));
	}

	/**
	 * Método que permite acceder a la pantalla de venta de un inmueble
	 * Pertenece a la taskcard 29 de la iteración 2 y a la historia 8
	 */
	@FXML
	public void venderInmueble() {
		if(tablaInmuebles.getSelectionModel().getSelectedItem().getEstadoInmueble().getEstado().equals(EstadoInmuebleStr.VENDIDO)) {
			presentador.presentarInformacion("Inmueble vendido", "No se puede realizar la venta ya que el inmueble ya se encuentra vendido", stage);
		} else {
			AltaVentaController controlador = (AltaVentaController) cambiarmeAScene(AltaVentaController.URLVista);
			controlador.setInmueble(tablaInmuebles.getSelectionModel().getSelectedItem());
		}
	}
}
