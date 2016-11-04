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
import java.util.ResourceBundle;

import app.datos.entidades.InmuebleBuscado;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class InmuebleBuscadoController extends OlimpoController {

	@FXML
	private TextField textFieldSuperficie;
	@FXML
	private TextField textFieldAntiguedad;
	@FXML
	private TextField textFieldDormitorios;
	@FXML
	private TextField textFieldBaños;

	@FXML
	private CheckBox checkboxPropiedadHorizontal;
	@FXML
	private CheckBox checkboxGarage;
	@FXML
	private CheckBox checkboxPatio;
	@FXML
	private CheckBox checkboxPiscina;
	@FXML
	private CheckBox checkboxAguaCorriente;
	@FXML
	private CheckBox checkboxCloaca;
	@FXML
	private CheckBox checkboxGasNatural;
	@FXML
	private CheckBox checkboxAguaCaliente;
	@FXML
	private CheckBox checkboxTelefono;
	@FXML
	private CheckBox checkboxLavadero;
	@FXML
	private CheckBox checkboxPavimento;

	public void acceptAction() {
		Double supeficie = Double.valueOf(textFieldSuperficie.getText().trim());
		Integer antiguedad = Integer.valueOf(textFieldAntiguedad.getText().trim());
		Integer dormitorios = Integer.valueOf(textFieldDormitorios.getText().trim());
		Integer baños = Integer.valueOf(textFieldBaños.getText().trim());

		Boolean propiedadHorizontal = checkboxPropiedadHorizontal.isSelected();
		Boolean garage = checkboxGarage.isSelected();
		Boolean patio = checkboxPatio.isSelected();
		Boolean piscina = checkboxPiscina.isSelected();
		Boolean aguaCorriente = checkboxAguaCorriente.isSelected();
		Boolean cloaca = checkboxCloaca.isSelected();
		Boolean gasNatural = checkboxGasNatural.isSelected();
		Boolean aguaCaliente = checkboxAguaCaliente.isSelected();
		Boolean telefono = checkboxTelefono.isSelected();
		Boolean lavadero = checkboxLavadero.isSelected();
		Boolean pavimento = checkboxPavimento.isSelected();

		// TODO: falta precio y barrios

		InmuebleBuscado inmuebleBuscado = new InmuebleBuscado();
		inmuebleBuscado.setSuperficieMin(supeficie)
				.setAntiguedadMax(antiguedad)
				.setDormitoriosMin(dormitorios)
				.setBañosMin(baños)
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

	}

	public void cancelAction(ActionEvent event) {
		((Node) (event.getSource())).getScene().getWindow().hide();
	}

	@Override
	public void inicializar(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

}
