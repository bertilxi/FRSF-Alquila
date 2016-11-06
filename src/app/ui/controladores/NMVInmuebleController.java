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
import app.datos.entidades.Orientacion;
import app.datos.entidades.Pais;
import app.datos.entidades.Propietario;
import app.datos.entidades.Provincia;
import app.datos.entidades.TipoInmueble;
import app.ui.controladores.resultado.ResultadoControlador;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 * Controlador de la vista que crea, modifica o muestra un inmueble
 * Pertenece a la taskcard 13 de la iteración 1 y a la historia 3
 */
public class NMVInmuebleController extends OlimpoController {

	public static final String URLVista = "/app/ui/vistas/NMVInmueble.fxml";

	@FXML
	private CheckBox cbAguaCaliente;

	@FXML
	private CheckBox cbAguaCorriente;

	@FXML
	private CheckBox cbCloaca;

	@FXML
	private CheckBox cbGarage;

	@FXML
	private CheckBox cbGasNatural;

	@FXML
	private CheckBox cbLavadero;

	@FXML
	private CheckBox cbPatio;

	@FXML
	private CheckBox cbPavimento;

	@FXML
	private CheckBox cbPiscina;

	@FXML
	private CheckBox cbPropiedadHorizontal;

	@FXML
	private CheckBox cbTelefono;

	@FXML
	private ComboBox<Barrio> cbBarrio;

	@FXML
	private ComboBox<Calle> cbCalle;

	@FXML
	private ComboBox<Localidad> cbLocalidad;

	@FXML
	private ComboBox<Orientacion> cbOrientacion;

	@FXML
	private ComboBox<Pais> cbPais;

	@FXML
	private ComboBox<Propietario> cbPropietario;

	@FXML
	private ComboBox<Provincia> cbProvincia;

	@FXML
	private ComboBox<TipoInmueble> cbTipoInmueble;

	@FXML
	private TextArea taObservaciones;

	@FXML
	private TextField tfAltura;

	@FXML
	private TextField tfAntiguedad;

	@FXML
	private TextField tfBaños;

	@FXML
	private TextField tfCodigo;

	@FXML
	private TextField tfDepartamento;

	@FXML
	private TextField tfDormitorios;

	@FXML
	private TextField tfFechaCarga;

	@FXML
	private TextField tfFondo;

	@FXML
	private TextField tfFrente;

	@FXML
	private TextField tfOtros;

	@FXML
	private TextField tfPiso;

	@FXML
	private TextField tfPrecioVenta;

	@FXML
	private TextField tfSuperficie;

	@FXML
	private TextField tfSuperficieEdificio;

	@FXML
	private Pane padre;

	@FXML
	private Pane pantalla1;

	@FXML
	private Pane pantalla2;

	private StringProperty titulo1 = new SimpleStringProperty();

	private StringProperty titulo2 = new SimpleStringProperty();

	@Override
	protected void inicializar(URL location, ResourceBundle resources) {
		titulo1.addListener((observable, oldValue, newValue) -> {
			setTitulo(newValue + " - " + titulo2.get());
		});
		titulo2.addListener((observable, oldValue, newValue) -> {
			setTitulo(titulo1.get() + " - " + newValue);
		});
		titulo1.set("Nuevo inmueble");
		atras();
	}

	@FXML
	public void agregarFoto() {

	}

	@FXML
	public void quitarFoto() {

	}

	@FXML
	public void cancelar() {
		salir();
	}

	@Override
	public void salir() {
		if(URLVistaRetorno != null){
			cambiarmeAScene(URLVistaRetorno);
		}
		else{
			cambiarmeAScene(AdministrarInmuebleController.URLVista);
		}
	}

	@FXML
	/**
	 * Método que permite guardar los cambios hechos en la vista
	 * Pertenece a la taskcard 13 de la iteración 1 y a la historia 3
	 *
	 * @return ResultadoControlador que resume lo que hizo el controlador
	 */
	public ResultadoControlador aceptar() {
		crearInmueble();
		modificarInmueble();
		return null;
	}

	/**
	 * Método que permite crear un inmueble
	 * Pertenece a la taskcard 13 de la iteración 1 y a la historia 3
	 *
	 * @return ResultadoControlador que resume lo que hizo el controlador
	 */
	private ResultadoControlador crearInmueble() {
		return null;
	}

	/**
	 * Método que permite modificar un inmueble
	 * Pertenece a la taskcard 13 de la iteración 1 y a la historia 3
	 *
	 * @return ResultadoControlador que resume lo que hizo el controlador
	 */
	private ResultadoControlador modificarInmueble() {
		return null;
	}

	@FXML
	public void atras() {
		padre.getChildren().clear();
		padre.getChildren().add(pantalla1);
		titulo2.set("Datos inmueble");
	}

	@FXML
	public void siguiente() {
		padre.getChildren().clear();
		padre.getChildren().add(pantalla2);
		titulo2.set("Datos edificio");
	}
}
