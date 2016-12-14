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
import app.datos.entidades.InmuebleBuscado;
import app.datos.entidades.Localidad;
import app.datos.entidades.TipoInmueble;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Controlador de la vista para ver los datos del inmueble buscado por un cliente
 */
public class VerInmuebleBuscadoController extends OlimpoController {
	public static final String URLVista = "/app/ui/vistas/verInmuebleBuscado.fxml";

	@FXML
	private TableView<Localidad> tablaLocalidades;
	@FXML
	private TableColumn<Localidad, String> columnaNombreLocalidad;

	@FXML
	private TableView<Barrio> tablaBarrios;
	@FXML
	private TableColumn<Barrio, String> columnaNombreBarrio;
	@FXML
	private TableColumn<Barrio, String> columnaNombreLocalidadDelBarrio;

	@FXML
	private Label labelLocal;
	@FXML
	private Label labelCasa;
	@FXML
	private Label labelDepartamento;
	@FXML
	private Label labelTerreno;
	@FXML
	private Label labelGalpon;
	@FXML
	private Label labelQuinta;

	@FXML
	private Label labelSuperficie;
	@FXML
	private Label labelAntiguedad;
	@FXML
	private Label labelDormitorios;
	@FXML
	private Label labelBaños;
	@FXML
	private Label labelPrecio;

	@FXML
	private Label labelPropiedadHorizontal;
	@FXML
	private Label labelGarage;
	@FXML
	private Label labelPatio;
	@FXML
	private Label labelPiscina;
	@FXML
	private Label labelAguaCorriente;
	@FXML
	private Label labelCloaca;
	@FXML
	private Label labelGasNatural;
	@FXML
	private Label labelAguaCaliente;
	@FXML
	private Label labelTelefono;
	@FXML
	private Label labelLavadero;
	@FXML
	private Label labelPavimento;

	/**
	 * Se setean los campos con los datos del inmueble
	 *
	 * @param inmueble
	 *            inmueble del que se obtienen los datos
	 */
	public void setInmueble(InmuebleBuscado inmueble) {
		Platform.runLater(() -> {
			labelAguaCaliente.setText((inmueble.getAguaCaliente()) ? ("Si") : ("No"));
			labelAguaCorriente.setText((inmueble.getAguaCorriente()) ? ("Si") : ("No"));
			labelCloaca.setText((inmueble.getCloacas()) ? ("Si") : ("No"));
			labelGarage.setText((inmueble.getGaraje()) ? ("Si") : ("No"));
			labelGasNatural.setText((inmueble.getGasNatural()) ? ("Si") : ("No"));
			labelLavadero.setText((inmueble.getLavadero()) ? ("Si") : ("No"));
			labelPatio.setText((inmueble.getPatio()) ? ("Si") : ("No"));
			labelPavimento.setText((inmueble.getPavimento()) ? ("Si") : ("No"));
			labelPiscina.setText((inmueble.getPiscina()) ? ("Si") : ("No"));
			labelPropiedadHorizontal.setText((inmueble.getPropiedadHorizontal()) ? ("Si") : ("No"));
			labelTelefono.setText((inmueble.getTelefono()) ? ("Si") : ("No"));
			labelAntiguedad.setText(inmueble.getAntiguedadMax().toString());
			labelBaños.setText(inmueble.getBañosMin().toString());
			labelDormitorios.setText(inmueble.getDormitoriosMin().toString());
			labelSuperficie.setText(inmueble.getSuperficieMin().toString());
			labelPrecio.setText(inmueble.getPrecioMax().toString());

			labelLocal.setText("No");
			labelCasa.setText("No");
			labelDepartamento.setText("No");
			labelGalpon.setText("No");
			labelQuinta.setText("No");
			labelTerreno.setText("No");

			for(TipoInmueble tipo: inmueble.getTiposInmueblesBuscados()){
				switch(tipo.getTipo()) {
				case LOCAL:
					labelLocal.setText("Si");
					break;
				case CASA:
					labelCasa.setText("Si");
					break;
				case DEPARTAMENTO:
					labelDepartamento.setText("Si");
					break;
				case GALPON:
					labelGalpon.setText("Si");
					break;
				case QUINTA:
					labelQuinta.setText("Si");
					break;
				case TERRENO:
					labelTerreno.setText("Si");
					break;
				}
			}
			tablaLocalidades.getItems().clear();
			tablaLocalidades.getItems().addAll(inmueble.getLocalidades());
			tablaBarrios.getItems().clear();
			tablaBarrios.getItems().addAll(inmueble.getBarrios());
		});
	}

	/**
	 * Acción que se ejecuta al presionar el botón atrás.
	 * Se vuelve a la pantalla administrar cliente.
	 */
	@FXML
	private void handleAtras() {
		cambiarmeAScene(AdministrarClienteController.URLVista);
	}

	@Override
	public void inicializar(URL location, ResourceBundle resources) {
		this.setTitulo("Ver inmueble buscado");
		columnaNombreLocalidad.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
		columnaNombreBarrio.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
		columnaNombreLocalidadDelBarrio.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLocalidad().getNombre()));
	}
}