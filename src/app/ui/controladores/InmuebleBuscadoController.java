/**
 * Copyright (C) 2016  Fernando Berti - Daniel Campodonico - Emiliano Gioria - Lucas Moretti - Esteban Rebechi - Andres Leonel Rico
 * This file is part of Olimpo.
 *
 * Olimpo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Olimpo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Olimpo.  If not, see <http://www.gnu.org/licenses/>.
 */
package app.ui.controladores;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import app.datos.entidades.Barrio;
import app.datos.entidades.Cliente;
import app.datos.entidades.InmuebleBuscado;
import app.datos.entidades.Localidad;
import app.datos.entidades.Pais;
import app.datos.entidades.Provincia;
import app.datos.entidades.TipoInmueble;
import app.excepciones.PersistenciaException;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class InmuebleBuscadoController extends OlimpoController {

	public static final String URLVista = "/app/ui/vistas/inmuebleBuscado.fxml";

	@FXML
	private ComboBox<Pais> comboBoxPais;
	@FXML
	private ComboBox<Provincia> comboBoxProvincia;
	@FXML
	private ComboBox<Localidad> comboBoxLocalidad;
	@FXML
	private ComboBox<Barrio> comboBoxBarrio;

	@FXML
	private TableView<Localidad> tablaLocalidades;
	@FXML
	private TableColumn<Localidad, String> columnaNombreLocalidad;

	@FXML
	private Button botonAgregarLocalidad;
	@FXML
	private Button botonQuitarLocalidad;

	@FXML
	private TableView<Barrio> tablaBarrios;
	@FXML
	private TableColumn<Barrio, String> columnaNombreBarrio;

	@FXML
	private Button botonAgregarBarrio;
	@FXML
	private Button botonQuitarBarrio;

	@FXML
	private CheckBox checkBoxLocal;
	@FXML
	private CheckBox checkBoxCasa;
	@FXML
	private CheckBox checkBoxDepartamento;
	@FXML
	private CheckBox checkBoxTerreno;
	@FXML
	private CheckBox checkBoxGalpon;
	@FXML
	private CheckBox checkBoxQuinta;

	@FXML
	private TextField textFieldSuperficie;
	@FXML
	private TextField textFieldAntiguedad;
	@FXML
	private TextField textFieldDormitorios;
	@FXML
	private TextField textFieldBaños;
	@FXML
	private TextField textFieldPrecio;

	@FXML
	private CheckBox checkBoxPropiedadHorizontal;
	@FXML
	private CheckBox checkBoxGarage;
	@FXML
	private CheckBox checkBoxPatio;
	@FXML
	private CheckBox checkBoxPiscina;
	@FXML
	private CheckBox checkBoxAguaCorriente;
	@FXML
	private CheckBox checkBoxCloaca;
	@FXML
	private CheckBox checkBoxGasNatural;
	@FXML
	private CheckBox checkBoxAguaCaliente;
	@FXML
	private CheckBox checkBoxTelefono;
	@FXML
	private CheckBox checkBoxLavadero;
	@FXML
	private CheckBox checkBoxPavimento;

	private Boolean alta;

	private Cliente cliente;

	private ArrayList<Pais> listaPaises;

	private ArrayList<Provincia> listaProvincias;

	private ArrayList<Localidad> listaLocalidades;

	private ArrayList<Localidad> listaLocalidadesSeleccionadas;

	private ArrayList<Barrio> listaBarrios;

	private ArrayList<Barrio> listaBarriosSeleccionados;

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
		InmuebleBuscado inmueble = cliente.getInmuebleBuscado();
		Platform.runLater(() -> {
			if(inmueble!=null) {
				alta = false;
				listaBarriosSeleccionados.addAll(inmueble.getBarrios());
				listaLocalidadesSeleccionadas.addAll(inmueble.getLocalidades());
				checkBoxAguaCaliente.setSelected(inmueble.getAguaCaliente());
				checkBoxAguaCorriente.setSelected(inmueble.getAguaCorriente());
				checkBoxCloaca.setSelected(inmueble.getCloacas());
				checkBoxGarage.setSelected(inmueble.getGaraje());
				checkBoxGasNatural.setSelected(inmueble.getGasNatural());
				checkBoxLavadero.setSelected(inmueble.getLavadero());
				checkBoxPatio.setSelected(inmueble.getPatio());
				checkBoxPavimento.setSelected(inmueble.getPavimento());
				checkBoxPiscina.setSelected(inmueble.getPiscina());
				checkBoxPropiedadHorizontal.setSelected(inmueble.getPropiedadHorizontal());
				checkBoxTelefono.setSelected(inmueble.getTelefono());
				textFieldAntiguedad.setText(inmueble.getAntiguedadMax().toString());
				textFieldBaños.setText(inmueble.getBañosMin().toString());
				textFieldDormitorios.setText(inmueble.getDormitoriosMin().toString());
				textFieldSuperficie.setText(inmueble.getSuperficieMin().toString());
				textFieldPrecio.setText(inmueble.getPrecioMax().toString());
				for(TipoInmueble tipo: inmueble.getTiposInmueblesBuscados()) {
					switch(tipo.getTipo()) {
					case LOCAL:
						checkBoxLocal.setSelected(true);
						break;
					case CASA:
						checkBoxCasa.setSelected(true);
						break;
					case DEPARTAMENTO:
						checkBoxDepartamento.setSelected(true);
						break;
					case GALPON:
						checkBoxGalpon.setSelected(true);
						break;
					case QUINTA:
						checkBoxQuinta.setSelected(true);
						break;
					case TERRENO:
						checkBoxTerreno.setSelected(true);
						break;
					}
				}
			} else {
				alta = true;
			}
			tablaBarrios.getItems().clear();
			tablaBarrios.getItems().addAll(listaBarriosSeleccionados);
			tablaLocalidades.getItems().clear();
			tablaLocalidades.getItems().addAll(listaLocalidadesSeleccionadas);
		});
	}

	@FXML
	private void acceptAction() {
		StringBuffer errores = new StringBuffer("");

		Pattern pat = Pattern.compile("[0-9]{1,30}");
		if(!pat.matcher(textFieldSuperficie.getText().trim()).matches()) {
			errores.append("Superficie incorrecta. Introduzca solo números");
		}
		if(!pat.matcher(textFieldPrecio.getText().trim()).matches()) {
			errores.append("Precio incorrecto. Introduzca solo números");
		}
		if(!pat.matcher(textFieldAntiguedad.getText().trim()).matches()) {
			errores.append("Antigüedad incorrecta. Introduzca solo números");
		}
		if(!pat.matcher(textFieldDormitorios.getText().trim()).matches()) {
			errores.append("Dormitorios incorrecto. Introduzca solo números");
		}
		if(!pat.matcher(textFieldBaños.getText().trim()).matches()) {
			errores.append("Baños incorrecto. Introduzca solo números");
		}

		if(!errores.toString().isEmpty()){
			presentador.presentarError("Revise sus campos", errores.toString(), stage);
		} else {

			Double supeficieMinima = Double.valueOf(textFieldSuperficie.getText().trim());
			Integer antiguedadMaxima = Integer.valueOf(textFieldAntiguedad.getText().trim());
			Integer dormitoriosMinimos = Integer.valueOf(textFieldDormitorios.getText().trim());
			Integer bañosMinimos = Integer.valueOf(textFieldBaños.getText().trim());
			Double precioMaximo = Double.valueOf(textFieldPrecio.getText().trim());

			Boolean propiedadHorizontal = checkBoxPropiedadHorizontal.isSelected();
			Boolean garage = checkBoxGarage.isSelected();
			Boolean patio = checkBoxPatio.isSelected();
			Boolean piscina = checkBoxPiscina.isSelected();
			Boolean aguaCorriente = checkBoxAguaCorriente.isSelected();
			Boolean cloaca = checkBoxCloaca.isSelected();
			Boolean gasNatural = checkBoxGasNatural.isSelected();
			Boolean aguaCaliente = checkBoxAguaCaliente.isSelected();
			Boolean telefono = checkBoxTelefono.isSelected();
			Boolean lavadero = checkBoxLavadero.isSelected();
			Boolean pavimento = checkBoxPavimento.isSelected();

			Boolean casa = checkBoxCasa.isSelected();
			Boolean departamento = checkBoxDepartamento.isSelected();
			Boolean local = checkBoxLocal.isSelected();
			Boolean galpon = checkBoxGalpon.isSelected();
			Boolean terreno = checkBoxTerreno.isSelected();
			Boolean quinta = checkBoxQuinta.isSelected();

			InmuebleBuscado inmuebleBuscado = cliente.getInmuebleBuscado();
			if(alta) {
				inmuebleBuscado = new InmuebleBuscado();
				cliente.setInmuebleBuscado(inmuebleBuscado);
				inmuebleBuscado.setCliente(cliente);
			}
			inmuebleBuscado.setSuperficieMin(supeficieMinima)
			.setAntiguedadMax(antiguedadMaxima)
			.setDormitoriosMin(dormitoriosMinimos)
			.setBañosMin(bañosMinimos)
			.setPrecioMax(precioMaximo)
			.setPropiedadHorizontal(propiedadHorizontal)
			.setGaraje(garage)
			.setPatio(patio)
			.setPiscina(piscina)
			.setAguaCaliente(aguaCaliente)
			.setAguaCorriente(aguaCorriente)
			.setCloacas(cloaca)
			.setGasNatural(gasNatural)
			.setTelefono(telefono)
			.setLavadero(lavadero)
			.setPavimento(pavimento);

			inmuebleBuscado.getLocalidades().addAll(listaLocalidadesSeleccionadas);
			inmuebleBuscado.getBarrios().addAll(listaBarriosSeleccionados);

			try {
				for(TipoInmueble tipo: coordinador.obtenerTiposInmueble()) {
					switch(tipo.getTipo()) {
					case CASA:
						if (casa) {
							inmuebleBuscado.getTiposInmueblesBuscados().add(tipo);
						}
						break;
					case DEPARTAMENTO:
						if (departamento) {
							inmuebleBuscado.getTiposInmueblesBuscados().add(tipo);
						}
						break;
					case GALPON:
						if (galpon) {
							inmuebleBuscado.getTiposInmueblesBuscados().add(tipo);
						}
						break;
					case LOCAL:
						if (local) {
							inmuebleBuscado.getTiposInmueblesBuscados().add(tipo);
						}
						break;
					case QUINTA:
						if (quinta) {
							inmuebleBuscado.getTiposInmueblesBuscados().add(tipo);
						}
						break;
					case TERRENO:
						if (terreno) {
							inmuebleBuscado.getTiposInmueblesBuscados().add(tipo);
						}
						break;
					}
				}
			} catch (PersistenciaException e) {
				presentador.presentarExcepcion(e, stage);
			}

			if (alta) {
				AltaClienteController controlador = (AltaClienteController) cambiarmeAScene(AltaClienteController.URLVista);
				controlador.setCliente(cliente);
			} else {
				cambiarmeAScene(ModificarClienteController.URLVista);
			}
		}
	}

	@FXML
	private void cancelAction(ActionEvent event) {
		if (alta) {
			cambiarmeAScene(AltaClienteController.URLVista);
		} else {
			cambiarmeAScene(ModificarClienteController.URLVista);
		}
	}

	@Override
	public void inicializar(URL location, ResourceBundle resources) {
		listaPaises = new ArrayList<>();
		listaProvincias = new ArrayList<>();
		listaLocalidades = new ArrayList<>();
		listaBarrios = new ArrayList<>();
		listaLocalidadesSeleccionadas = new ArrayList<>();
		listaBarriosSeleccionados = new ArrayList<>();

		try{
			listaPaises = coordinador.obtenerPaises();
		} catch(PersistenciaException e){
			presentador.presentarExcepcion(e, stage);
		}
		comboBoxPais.getItems().addAll(listaPaises);

		botonAgregarBarrio.setDisable(true);
		botonAgregarLocalidad.setDisable(true);
		botonQuitarBarrio.setDisable(true);
		botonQuitarLocalidad.setDisable(true);

		columnaNombreLocalidad.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
		columnaNombreBarrio.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));

		comboBoxPais.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> actualizarProvincias(newValue));
		comboBoxProvincia.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> actualizarLocalidades(newValue));
		comboBoxLocalidad.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> localidadSeleccionadaCombo(newValue));

		tablaLocalidades.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> localidadSeleccionadaTabla(newValue));

		comboBoxBarrio.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> BarrioSeleccionadoCombo(newValue));
		tablaBarrios.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> BarrioSeleccionadoTabla(newValue));

	}

	@FXML
	private void handleAgregarLocalidad() {
		if(comboBoxLocalidad.getSelectionModel().getSelectedItem()!=null && !listaLocalidadesSeleccionadas.contains(comboBoxLocalidad.getSelectionModel().getSelectedItem())) {
			listaLocalidadesSeleccionadas.add(comboBoxLocalidad.getSelectionModel().getSelectedItem());
			tablaLocalidades.getItems().clear();
			tablaLocalidades.getItems().addAll(listaLocalidadesSeleccionadas);
		}
	}

	@FXML
	private void handleQuitarLocalidad() {
		if(tablaLocalidades.getSelectionModel().getSelectedItem()!=null) {
			for(Barrio bar: tablaBarrios.getItems()) {
				if(bar.getLocalidad().equals(tablaLocalidades.getSelectionModel().getSelectedItem())) {
					listaBarriosSeleccionados.remove(bar);
				}
			}
			tablaBarrios.getItems().clear();
			tablaBarrios.getItems().addAll(listaBarriosSeleccionados);
			listaLocalidadesSeleccionadas.remove(tablaLocalidades.getSelectionModel().getSelectedItem());
			tablaLocalidades.getItems().clear();
			tablaLocalidades.getItems().addAll(listaLocalidadesSeleccionadas);
		}
	}

	@FXML
	private void handleAgregarBarrio() {
		if(comboBoxBarrio.getSelectionModel().getSelectedItem()!=null && !listaBarriosSeleccionados.contains(comboBoxBarrio.getSelectionModel().getSelectedItem())) {
			listaBarriosSeleccionados.add(comboBoxBarrio.getSelectionModel().getSelectedItem());
			tablaBarrios.getItems().clear();
			tablaBarrios.getItems().addAll(listaBarriosSeleccionados);
		}
	}

	@FXML
	private void handleQuitarBarrio() {
		if(tablaBarrios.getSelectionModel().getSelectedItem()!=null) {
			listaBarriosSeleccionados.remove(tablaBarrios.getSelectionModel().getSelectedItem());
			tablaBarrios.getItems().clear();
			tablaBarrios.getItems().addAll(listaBarriosSeleccionados);
		}
	}

	private void BarrioSeleccionadoTabla(Barrio bar) {
		if(bar==null) {
			botonQuitarBarrio.setDisable(true);
		} else {
			botonQuitarBarrio.setDisable(false);
		}
	}

	private void localidadSeleccionadaTabla(Localidad loc) {
		comboBoxBarrio.getItems().clear();
		if (loc==null) {
			botonQuitarLocalidad.setDisable(true);
		} else {
			botonQuitarLocalidad.setDisable(false);
			try{
				listaBarrios = coordinador.obtenerBarriosDe(loc);
			} catch(PersistenciaException e){
				presentador.presentarExcepcion(e, stage);
			}
			comboBoxBarrio.getItems().addAll(listaBarrios);
		}
	}

	private void BarrioSeleccionadoCombo(Barrio bar) {
		if(bar==null) {
			botonAgregarBarrio.setDisable(true);
		} else {
			botonAgregarBarrio.setDisable(false);
		}
	}

	private void localidadSeleccionadaCombo(Localidad loc) {
		if(loc==null) {
			botonAgregarLocalidad.setDisable(true);
		} else {
			botonAgregarLocalidad.setDisable(false);
		}
	}

	private void actualizarLocalidades(Provincia provincia) {
		try{
			listaLocalidades = coordinador.obtenerLocalidadesDe(provincia);
		} catch(PersistenciaException e){
			presentador.presentarExcepcion(e, stage);
		}
		comboBoxLocalidad.getItems().clear();
		comboBoxLocalidad.getItems().addAll(listaLocalidades);
	}

	private void actualizarProvincias(Pais pais) {
		try{
			listaProvincias = coordinador.obtenerProvinciasDe(pais);
		} catch(PersistenciaException e){
			presentador.presentarExcepcion(e, stage);
		}
		comboBoxProvincia.getItems().clear();
		comboBoxProvincia.getItems().addAll(listaProvincias);
	}
}
