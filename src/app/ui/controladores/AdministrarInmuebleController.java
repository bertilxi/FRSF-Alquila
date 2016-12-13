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
import app.datos.clases.FiltroInmueble;
import app.datos.entidades.Barrio;
import app.datos.entidades.EstadoInmueble;
import app.datos.entidades.Inmueble;
import app.datos.entidades.Localidad;
import app.datos.entidades.Pais;
import app.datos.entidades.Provincia;
import app.datos.entidades.TipoInmueble;
import app.excepciones.PersistenciaException;
import app.logica.resultados.ResultadoEliminarInmueble;
import app.logica.resultados.ResultadoEliminarInmueble.ErrorEliminarInmueble;
import app.ui.componentes.ventanas.VentanaConfirmacion;
import app.ui.controladores.resultado.ResultadoControlador;
import app.ui.controladores.resultado.ResultadoControlador.ErrorControlador;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

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
	private Button btAgregar;

	@FXML
	private Button btModificar;

	@FXML
	private Button btEliminar;

	@FXML
	private Button btVender;

	@FXML
	private Button btAceptar;

	@FXML
	private Button btSalir;

	@FXML
	private VBox vboxBotones;

	@FXML
	private ComboBox<Pais> comboBoxPais;
	@FXML
	private ComboBox<Provincia> comboBoxProvincia;
	@FXML
	private ComboBox<Localidad> comboBoxLocalidad;
	@FXML
	private ComboBox<Barrio> comboBoxBarrio;
	@FXML
	private ComboBox<EstadoInmueble> comboBoxEstadoInmueble;
	@FXML
	private ComboBox<TipoInmueble> comboBoxTipoInmueble;
	@FXML
	private TextField textFieldCantidadDormitorios;
	@FXML
	private TextField textFieldPrecioMinimo;
	@FXML
	private TextField textFieldPrecioMaximo;


	private ArrayList<Inmueble> inmueblesNoMostrar;

	private ArrayList<Inmueble> inmueblesSeleccionados;

	private Runnable accionCierre;

	@Override
	protected void inicializar(URL location, ResourceBundle resources) {
		setTitulo("Administrar inmuebles");

		try{
			comboBoxPais.getItems().addAll(coordinador.obtenerPaises());
		} catch(PersistenciaException e){
			presentador.presentarExcepcion(e, stage);
		}

		comboBoxPais.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> actualizarProvincias(newValue));
		comboBoxProvincia.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> actualizarLocalidades(newValue));
		comboBoxLocalidad.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> actualizarBarrios(newValue));

		try{
			comboBoxEstadoInmueble.getItems().addAll(coordinador.obtenerEstadosInmueble());
		} catch(PersistenciaException e){
			presentador.presentarExcepcion(e, stage);
		}

		try{
			comboBoxTipoInmueble.getItems().addAll(coordinador.obtenerTiposInmueble());
		} catch(PersistenciaException e){
			presentador.presentarExcepcion(e, stage);
		}

		tablaInmuebles.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			Boolean noHayInmuebleSeleccionado = newValue == null;
			btVerMas.setDisable(noHayInmuebleSeleccionado);
			btVerReservas.setDisable(noHayInmuebleSeleccionado);
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

		tablaInmuebles.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<Inmueble>() {
			@Override
			public void onChanged(Change<? extends Inmueble> c) {
				btAceptar.setDisable(c.getList().isEmpty());
			}

		});
	}

	@FXML
	public void agregar() {
		NMVInmuebleController controlador = (NMVInmuebleController) cambiarmeAScene(NMVInmuebleController.URLVista, URLVista);
		controlador.setVendedorLogueado(vendedorLogueado);
	}

	@FXML
	public void modificar() {
		Inmueble inmueble = tablaInmuebles.getSelectionModel().getSelectedItem();
		if(inmueble == null){
			return;
		}
		NMVInmuebleController nuevaPantalla = (NMVInmuebleController) cambiarmeAScene(NMVInmuebleController.URLVista, URLVista);
		nuevaPantalla.formatearModificarInmueble(inmueble);
		nuevaPantalla.setVendedorLogueado(vendedorLogueado);
	}

	@FXML
	public void verMas() {
		Inmueble inmueble = tablaInmuebles.getSelectionModel().getSelectedItem();
		if(inmueble == null){
			return;
		}
		NMVInmuebleController nuevaPantalla = (NMVInmuebleController) cambiarmeAScene(NMVInmuebleController.URLVista, URLVista);
		nuevaPantalla.formatearVerInmueble(inmueble);
		nuevaPantalla.setVendedorLogueado(vendedorLogueado);
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
		controlador.setVendedorLogueado(vendedorLogueado);
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
		if(tablaInmuebles.getSelectionModel().getSelectedItem().getEstadoInmueble().getEstado().equals(EstadoInmuebleStr.VENDIDO)){
			presentador.presentarInformacion("Inmueble vendido", "No se puede realizar la venta ya que el inmueble ya se encuentra vendido", stage);
		}
		else{
			AltaVentaController controlador = (AltaVentaController) cambiarmeAScene(AltaVentaController.URLVista);
			controlador.setInmueble(tablaInmuebles.getSelectionModel().getSelectedItem());
			controlador.setVendedorLogueado(vendedorLogueado);
		}
	}

	@FXML
	public void aceptar() {
		inmueblesSeleccionados.addAll(tablaInmuebles.getSelectionModel().getSelectedItems());
		accionCierre.run();
		salir();
	}

	public void formatearObtenerInmuebles(ArrayList<Inmueble> inmuebles, ArrayList<Inmueble> inmueblesNuevos, Runnable accionCierre, Boolean multiple) {
		this.inmueblesNoMostrar = inmuebles;
		this.inmueblesSeleccionados = inmueblesNuevos;
		this.accionCierre = accionCierre;
		vboxBotones.getChildren().clear();
		vboxBotones.getChildren().add(btAceptar);
		vboxBotones.getChildren().add(btSalir);
		btAceptar.setVisible(true);
		btSalir.setVisible(true);
		btSalir.setDisable(false);
		Platform.runLater(() -> {
			tablaInmuebles.getItems().removeAll(inmueblesNoMostrar);
			if(multiple){
				tablaInmuebles.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			}
			else{
				tablaInmuebles.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
			}
		});
	}

	/**
	 * Cuando varía la seleccion del comboBox de países, se actualiza el comboBox de provincias.
	 * También se delega la tarea de vaciar el comboBox de localidades
	 *
	 * @param pais
	 * 			país que fué seleccionado en el comboBox. Si no hay nada seleccionado, es <code>null</code>
	 */
	private void actualizarProvincias(Pais pais) {
		comboBoxProvincia.getItems().clear();
		if(pais != null){
			try{
				comboBoxProvincia.getItems().addAll(coordinador.obtenerProvinciasDe(pais));
			} catch(PersistenciaException e){
				presentador.presentarExcepcion(e, stage);
			}
		}
		actualizarLocalidades(null);
	}

	/**
	 * Cuando varía la seleccion del comboBox de provincias, se actualiza el comboBox de localidades.
	 * También se delega la tarea de vaciar el comboBox de barrios
	 *
	 * @param provincia
	 * 			provincia que fué seleccionada en el comboBox. Si no hay nada seleccionado, es <code>null</code>
	 */
	private void actualizarLocalidades(Provincia provincia) {
		comboBoxLocalidad.getItems().clear();
		if(provincia != null){
			try{
				comboBoxLocalidad.getItems().addAll(coordinador.obtenerLocalidadesDe(provincia));
			} catch(PersistenciaException e){
				presentador.presentarExcepcion(e, stage);
			}
		}
		actualizarBarrios(null);
	}

	/**
	 * Cuando varía la seleccion del comboBox de localidades, se actualiza el comboBox de barrios.
	 *
	 * @param localidad
	 * 			provincia que fué seleccionada en el comboBox. Si no hay nada seleccionado, es <code>null</code>
	 */
	private void actualizarBarrios(Localidad localidad) {
		comboBoxBarrio.getItems().clear();
		if(localidad != null){
			try{
				comboBoxBarrio.getItems().addAll(coordinador.obtenerBarriosDe(localidad));
			} catch(PersistenciaException e){
				presentador.presentarExcepcion(e, stage);
			}
		}
	}

	/**
	 * Método que permite realizar una consulta de inmuebles
	 * Pertenece a la taskcard 20 de la iteración 2 y a la historia 4
	 */
	@FXML
	private void buscarAction() {
		StringBuilder errores = new StringBuilder("");

		Pais pais = comboBoxPais.getValue();
		Provincia provincia = comboBoxProvincia.getValue();
		Localidad localidad = comboBoxLocalidad.getValue();
		Barrio barrio = comboBoxBarrio.getValue();
		EstadoInmueble estadoInmueble = comboBoxEstadoInmueble.getValue();
		TipoInmueble tipoInmueble = comboBoxTipoInmueble.getValue();
		Integer cantidadDormitorios = null;
		Double precioMinimo = null;
		Double precioMaximo = null;

		boolean vacioCD = false, vacioPMa = false, vacioPMi = false;

		if(!textFieldCantidadDormitorios.getText().trim().isEmpty()) {
			try {
				cantidadDormitorios = Integer.valueOf(textFieldCantidadDormitorios.getText().trim());
			} catch (Exception e) {
				errores.append("Cantidad de dormitorios incorrecta. Introduzca solo números.\n");
			}
		} else {
			vacioCD = true;
		}

		if(!textFieldPrecioMinimo.getText().trim().isEmpty()) {
			try {
				precioMinimo = Double.valueOf(textFieldPrecioMinimo.getText().trim());
			} catch (Exception e) {
				errores.append("Precio mínimo incorrecto. Introduzca solo números y un punto para decimales.\n");
			}
		} else {
			vacioPMa = true;
		}

		if(!textFieldPrecioMaximo.getText().trim().isEmpty()) {
			try {
				precioMaximo = Double.valueOf(textFieldPrecioMaximo.getText().trim());
			} catch (Exception e) {
				errores.append("Precio máximo incorrecto. Introduzca solo números y un punto para decimales.\n");
			}
		} else {
			vacioPMi = true;
		}

		if(!errores.toString().isEmpty()){
			presentador.presentarError("Revise sus campos", errores.toString(), stage);
		} else {

			tablaInmuebles.getItems().clear();
			if(pais == null && provincia == null && localidad == null && barrio == null && estadoInmueble == null && tipoInmueble == null && vacioCD && vacioPMa && vacioPMi) {
				try {
					tablaInmuebles.getItems().addAll(coordinador.obtenerInmuebles());
				} catch (PersistenciaException e) {
					presentador.presentarExcepcion(e, stage);
				}
			} else {
				try {
					FiltroInmueble filtro = new FiltroInmueble.Builder()
							.barrio(barrio)
							.cantidadDormitorios(cantidadDormitorios)
							.estadoInmueble(estadoInmueble.getEstado())
							.localidad(localidad)
							.pais(pais)
							.precioMaximo(precioMaximo)
							.precioMinimo(precioMinimo)
							.provincia(provincia)
							.tipoInmueble(tipoInmueble.getTipo())
							.build();
					tablaInmuebles.getItems().addAll(coordinador.obtenerInmuebles(filtro));
				} catch (Exception e) {
					presentador.presentarExcepcion(e, stage);
				}
			}
		}
	}
}
