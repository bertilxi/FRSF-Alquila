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
import app.datos.entidades.Direccion;
import app.datos.entidades.Localidad;
import app.datos.entidades.Pais;
import app.datos.entidades.Propietario;
import app.datos.entidades.Provincia;
import app.datos.entidades.TipoDocumento;
import app.excepciones.EntidadExistenteConEstadoBajaException;
import app.excepciones.GestionException;
import app.excepciones.PersistenciaException;
import app.logica.resultados.ResultadoCrearPropietario;
import app.logica.resultados.ResultadoCrearPropietario.ErrorCrearPropietario;
import app.ui.componentes.ventanas.VentanaConfirmacion;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

/**
 * Controlador de la vista para dar de alta un propietario
 *
 * Task card 8 de la iteración 1, historia de usuario 2
 */
public class AltaPropietarioController extends OlimpoController {

	public static final String URLVista = "/app/ui/vistas/altaPropietario.fxml";

	@FXML
	protected TextField textFieldNombre;
	@FXML
	protected TextField textFieldApellido;
	@FXML
	protected TextField textFieldNumeroDocumento;
	@FXML
	protected TextField textFieldAlturaCalle;
	@FXML
	protected TextField textFieldPiso;
	@FXML
	protected TextField textFieldDepartamento;
	@FXML
	protected TextField textFieldTelefono;
	@FXML
	protected TextField textFieldCorreoElectronico;
	@FXML
	protected TextField textFieldOtros;
	@FXML
	protected ComboBox<TipoDocumento> comboBoxTipoDocumento;
	@FXML
	protected ComboBox<Pais> comboBoxPais;
	@FXML
	protected ComboBox<Provincia> comboBoxProvincia;
	@FXML
	protected ComboBox<Localidad> comboBoxLocalidad;
	@FXML
	protected ComboBox<Calle> comboBoxCalle;
	@FXML
	protected ComboBox<Barrio> comboBoxBarrio;

	/**
	 * Acción que se ejecuta al apretar el botón aceptar.
	 *
	 * Valida que se hayan insertado datos, los carga al propietario y deriva la operación a capa lógica.
	 * Si la capa lógica retorna errores, se muestran al usuario.
	 */
	@FXML
	public void acceptAction() {

		StringBuilder error = new StringBuilder("");

		//obtengo datos introducidos por el usuario
		String nombre = textFieldNombre.getText().trim();
		String apellido = textFieldApellido.getText().trim();
		String numeroDocumento = textFieldNumeroDocumento.getText().trim();
		String alturaCalle = textFieldAlturaCalle.getText().trim();
		String piso = textFieldPiso.getText().trim();
		String departamento = textFieldDepartamento.getText().trim();
		String telefono = textFieldTelefono.getText().trim();
		String correoElectronico = textFieldCorreoElectronico.getText().trim();
		String otros = textFieldOtros.getText().trim();

		Localidad localidad = comboBoxLocalidad.getValue();
		TipoDocumento tipoDoc = comboBoxTipoDocumento.getValue();
		Barrio barrio = comboBoxBarrio.getValue();
		Calle calle = comboBoxCalle.getValue();

		//verifico que no estén vacíos
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

		if(!error.toString().isEmpty()){ //si hay algún error lo muestro al usuario
			presentador.presentarError("Revise sus campos", error.toString(), stage);
		}
		else{
			//Si no hay errores se crean las entidades con los datos introducidos
			Direccion direccion = new Direccion();
			direccion.setNumero(alturaCalle)
					.setCalle(calle)
					.setBarrio(barrio)
					.setPiso(piso)
					.setDepartamento(departamento)
					.setLocalidad(localidad)
					.setOtros(otros);

			Propietario propietario = new Propietario();
			propietario.setNombre(nombre)
					.setApellido(apellido)
					.setTipoDocumento(tipoDoc)
					.setNumeroDocumento(numeroDocumento)
					.setTelefono(telefono)
					.setEmail(correoElectronico)
					.setDireccion(direccion);

			try{ //relevo la operación a capa lógica
				ResultadoCrearPropietario resultado = coordinador.crearPropietario(propietario);
				if(resultado.hayErrores()){ // si hay algún error se muestra al usuario
					StringBuilder stringErrores = new StringBuilder();
					for(ErrorCrearPropietario err: resultado.getErrores()){
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
							stringErrores.append("Ya existe un cliente con ese tipo y número de documento.\n");
							break;
						}
					}
					presentador.presentarError("Revise sus campos", stringErrores.toString(), stage);
				}
				else{ //si no hay errores se muestra notificación y se vuelve a la pantalla de listar propietarios
					presentador.presentarToast("Se ha creado el propietario con éxito", stage);
					cambiarmeAScene(AdministrarPropietarioController.URLVista);
				}
			} catch(GestionException e){ //excepción originada en gestor
				if(e.getClass().equals(EntidadExistenteConEstadoBajaException.class)){
					//el propietario existe pero fué dado de baja
					VentanaConfirmacion ventana = presentador.presentarConfirmacion("El propietario ya existe", "El propietario ya existía anteriormente pero fué dado de baja.\n ¿Desea volver a darle de alta?", stage);
					if(ventana.acepta()){
						//usuario acepta volver a darle de alta. Se pasa a la pantalla de modificar propietario
						ModificarPropietarioController controlador = (ModificarPropietarioController) cambiarmeAScene(ModificarPropietarioController.URLVista);
						controlador.setPropietarioEnModificacion(propietario);
					}
				}
			} catch(PersistenciaException e){ //excepción originada en la capa de persistencia
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
		this.setTitulo("Nuevo propietario");

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

		//se selecciona por defecto a argentina en el combo box país
		for(Pais p: comboBoxPais.getItems()){
			if(p.getNombre().equalsIgnoreCase("argentina")){
				comboBoxPais.getSelectionModel().select(p);
				break;
			}
		}
		actualizarProvincias(comboBoxPais.getSelectionModel().getSelectedItem());

		//cada vez que cambia el item seleccionado
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
				nombre = nombre.toLowerCase().trim();
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
	 *            provincia que fué seleccionada en el comboBox. Si no hay nada seleccionado, es <code>null</code>
	 */
	private void actualizarLocalidades(Provincia provincia) {
		comboBoxLocalidad.setEditable(true);
		comboBoxLocalidad.getEditor().clear();
		if(provincia == null) {
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
		actualizarBarriosYCalles(null);
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
		if(pais == null) {
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
	 * Cuando varía la seleccion del comboBox de localidades, se actualizan los comboBox de barrios y calles.
	 *
	 * @param loc
	 *            localidad que fué seleccionada en el comboBox. Si no hay nada seleccionado, es <code>null</code>
	 */
	private void actualizarBarriosYCalles(Localidad loc) {
		comboBoxBarrio.setEditable(true);
		comboBoxCalle.setEditable(true);
		comboBoxBarrio.getEditor().clear();
		comboBoxCalle.getEditor().clear();
		if(loc == null) {
			comboBoxBarrio.setEditable(false);
			comboBoxCalle.setEditable(false);
		}
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
	}
}
