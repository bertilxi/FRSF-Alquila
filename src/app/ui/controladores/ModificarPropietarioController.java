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

import app.datos.entidades.Barrio;
import app.datos.entidades.Calle;
import app.datos.entidades.Localidad;
import app.datos.entidades.Pais;
import app.datos.entidades.Propietario;
import app.datos.entidades.Provincia;
import app.datos.entidades.TipoDocumento;
import app.excepciones.PersistenciaException;
import app.logica.resultados.ResultadoModificarPropietario;
import app.logica.resultados.ResultadoModificarPropietario.ErrorModificarPropietario;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

/**
 * Controlador de la vista para modificar un propietario
 */
public class ModificarPropietarioController extends OlimpoController {

	public static final String URLVista = "/app/ui/vistas/modificarPropietario.fxml";

	@FXML
	private TextField textFieldNombre;
	@FXML
	private TextField textFieldApellido;
	@FXML
	private TextField textFieldNumeroDocumento;
	@FXML
	private TextField textFieldAlturaCalle;
	@FXML
	private TextField textFieldPiso;
	@FXML
	private TextField textFieldDepartamento;
	@FXML
	private TextField textFieldTelefono;
	@FXML
	private TextField textFieldCorreoElectronico;
	@FXML
	private ComboBox<TipoDocumento> comboBoxTipoDocumento;
	@FXML
	private ComboBox<Pais> comboBoxPais;
	@FXML
	private ComboBox<Provincia> comboBoxProvincia;
	@FXML
	private ComboBox<Localidad> comboBoxLocalidad;
	@FXML
	private ComboBox<Calle> comboBoxCalle;
	@FXML
	private ComboBox<Barrio> comboBoxBarrio;

	private Propietario propietarioEnModificacion;

	/**
	 * Setea los campos con los datos del propietario pasado por parámetro.
	 *
	 * @param propietarioEnModificacion
	 * 			propietario del que se obtienen los datos.
	 */
	public void setPropietarioEnModificacion(Propietario propietarioEnModificacion) {
		Platform.runLater(() -> {
			this.propietarioEnModificacion = propietarioEnModificacion;
			textFieldNombre.setText(propietarioEnModificacion.getNombre());
			textFieldApellido.setText(propietarioEnModificacion.getApellido());
			textFieldNumeroDocumento.setText(propietarioEnModificacion.getNumeroDocumento());
			textFieldAlturaCalle.setText(propietarioEnModificacion.getDireccion().getNumero());
			textFieldPiso.setText(propietarioEnModificacion.getDireccion().getPiso());
			textFieldDepartamento.setText(propietarioEnModificacion.getDireccion().getDepartamento());
			textFieldCorreoElectronico.setText(propietarioEnModificacion.getEmail());
			textFieldTelefono.setText(propietarioEnModificacion.getTelefono());
			comboBoxPais.setValue(propietarioEnModificacion.getDireccion().getLocalidad().getProvincia().getPais());
			comboBoxProvincia.setValue(propietarioEnModificacion.getDireccion().getLocalidad().getProvincia());
			comboBoxLocalidad.setValue(propietarioEnModificacion.getDireccion().getLocalidad());
			comboBoxBarrio.setValue(propietarioEnModificacion.getDireccion().getBarrio());
			comboBoxCalle.setValue(propietarioEnModificacion.getDireccion().getCalle());
			comboBoxTipoDocumento.setValue(propietarioEnModificacion.getTipoDocumento());
		});
	}

	/**
	 * Acción que se ejecuta al apretar el botón aceptar.
	 *
	 * Valida que se hayan insertado datos, los carga al propietario y deriva la operación a capa lógica.
	 * Si la capa lógica retorna errores, se muestran al usuario.
	 */
	@FXML
	public void acceptAction() {

		StringBuilder error = new StringBuilder("");

		String nombre = textFieldNombre.getText().trim();
		String apellido = textFieldApellido.getText().trim();
		String numeroDocumento = textFieldNumeroDocumento.getText().trim();
		String alturaCalle = textFieldAlturaCalle.getText().trim();
		String piso = textFieldPiso.getText().trim();
		String departamento = textFieldDepartamento.getText().trim();
		String telefono = textFieldTelefono.getText().trim();
		String correoElectronico = textFieldCorreoElectronico.getText().trim();

		Localidad localidad = comboBoxLocalidad.getValue();
		TipoDocumento tipoDoc = comboBoxTipoDocumento.getValue();
		Barrio barrio = comboBoxBarrio.getValue();
		Calle calle = comboBoxCalle.getValue();

		if(nombre.isEmpty()){
			error.append("Inserte un nombre").append("\n");
		}
		if(apellido.isEmpty()){
			error.append("Inserte un apellido").append("\n ");
		}
		if(numeroDocumento.isEmpty()){
			error.append("Inserte un numero de documento").append("\n ");
		}
		if(alturaCalle.isEmpty()){
			error.append("Inserte una altura").append("\n ");
		}
		if(telefono.isEmpty()){
			error.append("Inserte un telefono").append("\n ");
		}
		if(correoElectronico.isEmpty()){
			error.append("Inserte un correo electrónico").append("\n ");
		}
		if(calle == null){
			error.append("Elija una calle").append("\n");
		}
		if(barrio == null){
			error.append("Elija un barrio").append("\n");
		}
		if(tipoDoc == null){
			error.append("Elija un tipo de documento").append("\n");
		}
		if(localidad == null){
			error.append("Elija una localidad").append("\n");
		}

		if(!error.toString().isEmpty()){
			presentador.presentarError("Revise sus campos", error.toString(), stage);
		}
		else{

			propietarioEnModificacion.getDireccion().setNumero(alturaCalle)
					.setCalle(calle)
					.setBarrio(barrio)
					.setPiso(piso)
					.setDepartamento(departamento)
					.setLocalidad(localidad);

			propietarioEnModificacion.setNombre(nombre)
					.setApellido(apellido)
					.setTipoDocumento(tipoDoc)
					.setNumeroDocumento(numeroDocumento)
					.setTelefono(telefono)
					.setEmail(correoElectronico);

			try{
				ResultadoModificarPropietario resultado = coordinador.modificarPropietario(propietarioEnModificacion);
				if(resultado.hayErrores()){
					StringBuilder stringErrores = new StringBuilder();
					for(ErrorModificarPropietario err: resultado.getErrores()){
						switch(err) {
						case Formato_Nombre_Incorrecto:
							stringErrores.append("Formato de nombre incorrecto.\n");
							break;
						case Formato_Apellido_Incorrecto:
							stringErrores.append("Formato de apellido incorrecto.\n");
							break;
						case Formato_Telefono_Incorrecto:
							stringErrores.append("Formato de teléfono incorrecto.\n");
							break;
						case Formato_Documento_Incorrecto:
							stringErrores.append("Tipo y formato de documento incorrecto.\n");
							break;
						case Formato_Email_Incorrecto:
							stringErrores.append("Formato de email incorrecto.\n");
							break;
						case Formato_Direccion_Incorrecto:
							stringErrores.append("Formato de dirección incorrecto.\n");
							break;
						case Ya_Existe_Propietario:
							stringErrores.append("Otro cliente posee ese tipo y número de documento.\n");
							break;
						}
					}
					presentador.presentarError("No se pudo modificar el propietario", stringErrores.toString(), stage);
				}
				else{
					cambiarmeAScene(AdministrarPropietarioController.URLVista);
				}
			} catch(PersistenciaException e){
				presentador.presentarExcepcion(e, stage);
			}
		}
	}

	/**
	 * Acción que se ejecuta al presionar el botón cancelar.
	 * Se vuelve a la pantalla administrar propietario.
	 */
	@FXML
	public void cancelAction() {
		cambiarmeAScene(AdministrarPropietarioController.URLVista);
	}

	@Override
	public void inicializar(URL location, ResourceBundle resources) {
		this.setTitulo("Modificar propietario");

		try{
			comboBoxTipoDocumento.getItems().addAll(coordinador.obtenerTiposDeDocumento());
		} catch(PersistenciaException e){
			presentador.presentarExcepcion(e, stage);
		}
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
				(observable, oldValue, newValue) -> actualizarBarriosYCalles(newValue));

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
				nombre = nombre.trim();
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
				nombre = nombre.trim();
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
				nombre = nombre.trim();
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
				nombre = nombre.trim();
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

		comboBoxCalle.setConverter(new StringConverter<Calle>() {

			@Override
			public String toString(Calle object) {
				if(object == null){
					return null;
				}
				return object.toString();
			}

			@Override
			public Calle fromString(String nombre) {
				nombre = nombre.trim();
				if(nombre.isEmpty()){
					return null;
				}
				for(Calle cal: comboBoxCalle.getItems()){
					if(nombre.equals(cal.getNombre())){
						return cal;
					}
				}
				Calle cal = new Calle();
				cal.setNombre(nombre);
				cal.setLocalidad(comboBoxLocalidad.getValue());
				return cal;
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

		comboBoxCalle.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
				if(!newPropertyValue){
					comboBoxCalle.getSelectionModel().select(comboBoxCalle.getConverter().fromString(comboBoxCalle.getEditor().getText()));

				}
			}
		});
	}

	/**
	 * Cuando varía la seleccion del comboBox de provincias, se actualiza el comboBox de localidades.
	 * También se delega la tarea de vaciar los comboBox de barrios y calles
	 *
	 * @param provincia
	 * 			provincia que fué seleccionada en el comboBox. Si no hay nada seleccionado, es <code>null</code>
	 */
	private void actualizarLocalidades(Provincia provincia) {
		comboBoxLocalidad.getItems().clear();
		if(provincia != null && provincia.getId() != null){
			try{
				comboBoxLocalidad.getItems().addAll(coordinador.obtenerLocalidadesDe(provincia));
			} catch(PersistenciaException e){
				presentador.presentarExcepcion(e, stage);
			}
		}
		actualizarBarriosYCalles(null);
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
	 * Cuando varía la seleccion del comboBox de localidades, se actualizan los comboBox de barrios y calles.
	 *
	 * @param loc
	 * 			localidad que fué seleccionada en el comboBox. Si no hay nada seleccionado, es <code>null</code>
	 */
	private void actualizarBarriosYCalles(Localidad loc) {
		comboBoxBarrio.getItems().clear();
		comboBoxCalle.getItems().clear();
		if(loc != null && loc.getId() != null){
			try{
				comboBoxBarrio.getItems().addAll(coordinador.obtenerBarriosDe(loc));
				comboBoxCalle.getItems().addAll(coordinador.obtenerCallesDe(loc));
			} catch(PersistenciaException e){
				presentador.presentarExcepcion(e, stage);
			}
		}
		comboBoxBarrio.getEditor().clear();
		comboBoxCalle.getEditor().clear();
	}
}
