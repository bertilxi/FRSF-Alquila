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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

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
	private Button btGenerarCatalogo;

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

	private ArrayList<Inmueble> inmueblesNoMostrar = new ArrayList<>();

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

		tablaInmuebles.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		tablaInmuebles.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			Boolean noHayInmuebleSeleccionado = newValue == null;
			btVerMas.setDisable(noHayInmuebleSeleccionado);
			btVerReservas.setDisable(noHayInmuebleSeleccionado);
			btModificar.setDisable(noHayInmuebleSeleccionado);
			btEliminar.setDisable(noHayInmuebleSeleccionado);
			btVender.setDisable(noHayInmuebleSeleccionado);
			btGenerarCatalogo.setDisable(noHayInmuebleSeleccionado);
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

		//se setean los converters para cuando se ingrese un item no existente a través
		//del editor de texto de los comboBox editables
		comboBoxPais.setConverter(new StringConverter<Pais>() {

			@Override
			public String toString(Pais object) {
				if(object == null){
					return null;
				}
				return object.toString();
			}

			@Override
			public Pais fromString(String nombre) {
				nombre = nombre.toLowerCase().trim();
				if(nombre.isEmpty()){
					return null;
				}
				for(Pais pais: comboBoxPais.getItems()){
					if(nombre.equals(pais.getNombre())){
						return pais;
					}
				}
				Pais pais = new Pais();
				pais.setNombre(nombre);
				return pais;
			}
		});

		comboBoxProvincia.setConverter(new StringConverter<Provincia>() {

			@Override
			public String toString(Provincia object) {
				if(object == null){
					return null;
				}
				return object.toString();
			}

			@Override
			public Provincia fromString(String nombre) {
				nombre = nombre.toLowerCase().trim();
				if(nombre.isEmpty()){
					return null;
				}
				for(Provincia prov: comboBoxProvincia.getItems()){
					if(nombre.equals(prov.getNombre())){
						return prov;
					}
				}
				Provincia prov = new Provincia();
				prov.setNombre(nombre);
				prov.setPais(comboBoxPais.getValue());
				return prov;
			}
		});

		comboBoxLocalidad.setConverter(new StringConverter<Localidad>() {

			@Override
			public String toString(Localidad object) {
				if(object == null){
					return null;
				}
				return object.toString();
			}

			@Override
			public Localidad fromString(String nombre) {
				nombre = nombre.toLowerCase().trim();
				if(nombre.isEmpty()){
					return null;
				}
				for(Localidad loc: comboBoxLocalidad.getItems()){
					if(nombre.equals(loc.getNombre())){
						return loc;
					}
				}
				Localidad loc = new Localidad();
				loc.setNombre(nombre);
				loc.setProvincia(comboBoxProvincia.getValue());
				return loc;
			}
		});

		comboBoxBarrio.setConverter(new StringConverter<Barrio>() {

			@Override
			public String toString(Barrio object) {
				if(object == null){
					return null;
				}
				return object.toString();
			}

			@Override
			public Barrio fromString(String nombre) {
				nombre = nombre.toLowerCase().trim();
				if(nombre.isEmpty()){
					return null;
				}
				for(Barrio bar: comboBoxBarrio.getItems()){
					if(nombre.equals(bar.getNombre())){
						return bar;
					}
				}
				Barrio bar = new Barrio();
				bar.setNombre(nombre);
				bar.setLocalidad(comboBoxLocalidad.getValue());
				return bar;
			}
		});

		//Cuando el foco sale de los comboBox que estaban siendo editados
		//el texto ingresado se convierte en un item y se lo selecciona
		comboBoxPais.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
				if(!newPropertyValue){
					comboBoxPais.getSelectionModel().select(comboBoxPais.getConverter().fromString(comboBoxPais.getEditor().getText()));

				}
			}
		});

		comboBoxProvincia.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
				if(!newPropertyValue){
					comboBoxProvincia.getSelectionModel().select(comboBoxProvincia.getConverter().fromString(comboBoxProvincia.getEditor().getText()));

				}
			}
		});

		comboBoxLocalidad.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
				if(!newPropertyValue){
					comboBoxLocalidad.getSelectionModel().select(comboBoxLocalidad.getConverter().fromString(comboBoxLocalidad.getEditor().getText()));

				}
			}
		});

		comboBoxBarrio.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
				if(!newPropertyValue){
					comboBoxBarrio.getSelectionModel().select(comboBoxBarrio.getConverter().fromString(comboBoxBarrio.getEditor().getText()));

				}
			}
		});
	}

	/**
	 * Método que se llama al hacer click en el botón agregar.
	 */
	@FXML
	public void agregar() {
		//Cambia a la pantalla de agregar inmueble
		cambiarmeAScene(NMVInmuebleController.URLVista, URLVista);
	}

	/**
	 * Método que se llama al hacer click en el botón modificar.
	 */
	@FXML
	public void modificar() {
		Inmueble inmueble = tablaInmuebles.getSelectionModel().getSelectedItem();
		if(inmueble == null || inmueble.getEstadoInmueble().getEstado().equals(EstadoInmuebleStr.VENDIDO)){
			return;
		}
		//Cambia a la pantalla de modificar inmueble
		NMVInmuebleController nuevaPantalla = (NMVInmuebleController) cambiarmeAScene(NMVInmuebleController.URLVista, URLVista);
		//Le seteamos el inmueble que queremos modificar
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
		AdministrarReservaController controlador = (AdministrarReservaController) cambiarmeAScene(AdministrarReservaController.URLVista, URLVista);
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
		//Inicialización de variables
		ArrayList<ErrorControlador> erroresControlador = new ArrayList<>();
		ResultadoEliminarInmueble resultado;
		StringBuffer erroresBfr = new StringBuffer();

		//Toma de datos de la vista
		Inmueble inmueble = tablaInmuebles.getSelectionModel().getSelectedItem();
		if(inmueble == null){
			return new ResultadoControlador(ErrorControlador.Campos_Vacios);
		}

		//Se pregunta si quiere eliminar el inmueble
		VentanaConfirmacion ventana = presentador.presentarConfirmacion("Eliminar inmueble", "Está a punto de eliminar a el inmueble.\n ¿Está seguro que desea hacerlo?", this.stage);
		if(!ventana.acepta()){
			return new ResultadoControlador();
		}

		try{
			//Se llama a la lógica para eliminar el inmueble y se recibe el resultado de las validaciones y datos extras de ser necesarios
			resultado = coordinador.eliminarInmueble(inmueble);
		} catch(PersistenciaException e){
			presentador.presentarExcepcion(e, stage);
			return new ResultadoControlador(ErrorControlador.Error_Persistencia);
		} catch(Exception e){
			presentador.presentarExcepcionInesperada(e, stage);
			return new ResultadoControlador(ErrorControlador.Error_Desconocido);
		}

		//Procesamiento de errores de la lógica
		if(resultado.hayErrores()){
			for(ErrorEliminarInmueble err: resultado.getErrores()){
				switch(err) {

				}
			}
			//Se muestran los errores
			presentador.presentarError("No se pudo crear el inmueble", erroresBfr.toString(), stage);
		}
		else{
			//Se muestra una notificación de que se eliminó correctamente el inmueble
			presentador.presentarToast("Se ha eliminado el inmueble con éxito", stage);
			//Se quita el inmueble de la vista
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
		}
	}

	@FXML
	/**
	 * Método que permite acceder a la pantalla de generar catálogo
	 */
	public void generarCatalogo() {
		ArrayList<Inmueble> inmuebles = new ArrayList<>(tablaInmuebles.getSelectionModel().getSelectedItems());
		ArrayList<Inmueble> inmueblesVendidos = new ArrayList<>();
		for(Inmueble inmueble: inmuebles){
			if(inmueble.getEstadoInmueble().getEstado().equals(EstadoInmuebleStr.VENDIDO)){
				inmueblesVendidos.add(inmueble);
			}
		}
		inmuebles.removeAll(inmueblesVendidos);
		if(inmuebles.isEmpty()){
			return;
		}
		AltaCatalogoController controlador = (AltaCatalogoController) cambiarmeAScene(AltaCatalogoController.URLVista, URLVista);
		controlador.setInmuebles(inmuebles);
	}

	@FXML
	public void aceptar() {
		inmueblesSeleccionados.addAll(tablaInmuebles.getSelectionModel().getSelectedItems());
		accionCierre.run();
		salir();
	}

	public void formatearObtenerInmueblesNoVendidos(ArrayList<Inmueble> inmuebles, ArrayList<Inmueble> inmueblesNuevos, Runnable accionCierre, Boolean multiple) {
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
			buscarAction();
			for(EstadoInmueble estadoInmueble: comboBoxEstadoInmueble.getItems()){
				if(estadoInmueble.getEstado().equals(EstadoInmuebleStr.NO_VENDIDO)){
					comboBoxEstadoInmueble.getSelectionModel().select(estadoInmueble);
					break;
				}
			}
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
	 * Cuando varía la seleccion del comboBox de provincias, se actualiza el comboBox de localidades.
	 * También se delega la tarea de vaciar el comboBox de barrios
	 *
	 * @param provincia
	 *            provincia que fué seleccionada en el comboBox. Si no hay nada seleccionado, es <code>null</code>
	 */
	private void actualizarLocalidades(Provincia provincia) {
		comboBoxLocalidad.setEditable(true);
		comboBoxLocalidad.getEditor().clear();
		if(provincia == null){
			comboBoxLocalidad.setEditable(false);
		}
		comboBoxLocalidad.getItems().clear();
		if(provincia != null && provincia.getId() != null){
			try{
				comboBoxLocalidad.getItems().addAll(coordinador.obtenerLocalidadesDe(provincia));
			} catch(PersistenciaException e){
				presentador.presentarExcepcion(e, stage);
			}
		}
		actualizarBarrios(null);
	}

	/**
	 * Cuando varía la seleccion del comboBox de países, se actualiza el comboBox de provincias.
	 * También se delega la tarea de vaciar el comboBox de localidades
	 *
	 * @param pais
	 *            país que fué seleccionado en el comboBox. Si no hay nada seleccionado, es <code>null</code>
	 */
	private void actualizarProvincias(Pais pais) {
		comboBoxProvincia.setEditable(true);
		comboBoxProvincia.getEditor().clear();
		if(pais == null){
			comboBoxProvincia.setEditable(false);
		}
		comboBoxProvincia.getItems().clear();
		if(pais != null && pais.getId() != null){
			try{
				comboBoxProvincia.getItems().addAll(coordinador.obtenerProvinciasDe(pais));
			} catch(PersistenciaException e){
				presentador.presentarExcepcion(e, stage);
			}
		}
		actualizarLocalidades(null);
	}

	/**
	 * Cuando varía la seleccion del comboBox de localidades, se actualiza el comboBox de barrios.
	 *
	 * @param loc
	 *            localidad que fué seleccionada en el comboBox. Si no hay nada seleccionado, es <code>null</code>
	 */
	private void actualizarBarrios(Localidad loc) {
		comboBoxBarrio.setEditable(true);
		comboBoxBarrio.getEditor().clear();
		if(loc == null){
			comboBoxBarrio.setEditable(false);
		}
		comboBoxBarrio.getItems().clear();
		if(loc != null && loc.getId() != null){
			try{
				comboBoxBarrio.getItems().addAll(coordinador.obtenerBarriosDe(loc));
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

		//Se obtienen los datos ingresados por el usuario y se verifican errores
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

		if(!textFieldCantidadDormitorios.getText().trim().isEmpty()){
			try{
				cantidadDormitorios = Integer.valueOf(textFieldCantidadDormitorios.getText().trim());
			} catch(Exception e){
				errores.append("Cantidad de dormitorios incorrecta. Introduzca solo números.\n");
			}
		}
		else{
			vacioCD = true;
		}

		if(!textFieldPrecioMinimo.getText().trim().isEmpty()){
			try{
				precioMinimo = Double.valueOf(textFieldPrecioMinimo.getText().trim());
			} catch(Exception e){
				errores.append("Precio mínimo incorrecto. Introduzca solo números y un punto para decimales.\n");
			}
		}
		else{
			vacioPMa = true;
		}

		if(!textFieldPrecioMaximo.getText().trim().isEmpty()){
			try{
				precioMaximo = Double.valueOf(textFieldPrecioMaximo.getText().trim());
			} catch(Exception e){
				errores.append("Precio máximo incorrecto. Introduzca solo números y un punto para decimales.\n");
			}
		}
		else{
			vacioPMi = true;
		}

		if(precioMaximo != null && precioMinimo != null && precioMaximo < precioMinimo){
			errores.append("El precio máximo es menor al precio mínimo.\n");
		}

		//si hay errores se muestra al usuario
		if(!errores.toString().isEmpty()){
			presentador.presentarError("Revise sus campos", errores.toString(), stage);
		}
		else{
			tablaInmuebles.getItems().clear();

			// si no se introdujo ningún filtro de búsqueda
			if(pais == null && provincia == null && localidad == null && barrio == null && estadoInmueble == null && tipoInmueble == null && vacioCD && vacioPMa && vacioPMi){
				try{
					tablaInmuebles.getItems().addAll(coordinador.obtenerInmuebles());
				} catch(PersistenciaException e){ //fallo en la capa de persistencia
					presentador.presentarExcepcion(e, stage);
				}
			}
			else{ // si se introdujo algún filtro de búsqueda
				try{
					FiltroInmueble filtro = new FiltroInmueble.Builder()
							.barrio(barrio)
							.cantidadDormitorios(cantidadDormitorios)
							.estadoInmueble(((estadoInmueble != null) ? estadoInmueble.getEstado() : (null)))
							.localidad(localidad)
							.pais(pais)
							.precioMaximo(precioMaximo)
							.precioMinimo(precioMinimo)
							.provincia(provincia)
							.tipoInmueble(((tipoInmueble != null) ? tipoInmueble.getTipo() : (null)))
							.build();
					tablaInmuebles.getItems().addAll(coordinador.obtenerInmuebles(filtro));
				} catch(PersistenciaException e) { // fallo en la capa de persistencia
					presentador.presentarExcepcion(e, stage);
				} catch(Exception e){ //alguna otra excepción inesperada
					presentador.presentarExcepcionInesperada(e, stage);
				}
			}
			tablaInmuebles.getItems().removeAll(inmueblesNoMostrar);
		}
	}
}
