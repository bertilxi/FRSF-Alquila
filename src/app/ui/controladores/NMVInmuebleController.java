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

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import app.datos.clases.EstadoStr;
import app.datos.entidades.Barrio;
import app.datos.entidades.Calle;
import app.datos.entidades.DatosEdificio;
import app.datos.entidades.Direccion;
import app.datos.entidades.Estado;
import app.datos.entidades.Inmueble;
import app.datos.entidades.Localidad;
import app.datos.entidades.Orientacion;
import app.datos.entidades.Pais;
import app.datos.entidades.Propietario;
import app.datos.entidades.Provincia;
import app.datos.entidades.TipoInmueble;
import app.excepciones.PersistenciaException;
import app.logica.resultados.ResultadoCrearInmueble;
import app.logica.resultados.ResultadoCrearInmueble.ErrorCrearInmueble;
import app.ui.controladores.resultado.ResultadoControlador;
import app.ui.controladores.resultado.ResultadoControlador.ErrorControlador;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

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

	@FXML
	private Pane panelFotos;

	@FXML
	private ImageView imagenSeleccionada;

	@FXML
	private Button btQuitarFoto;

	private StringProperty titulo1 = new SimpleStringProperty();

	private StringProperty titulo2 = new SimpleStringProperty();

	private Inmueble inmueble;

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

		try{
			cbPropietario.getItems().addAll(coordinador.obtenerPropietarios());
			cbTipoInmueble.getItems().addAll(coordinador.obtenerTiposInmueble());
			cbOrientacion.getItems().addAll(coordinador.obtenerOrientaciones());
			cbPais.getItems().addAll(coordinador.obtenerPaises());
		} catch(PersistenciaException e){
			presentador.presentarExcepcion(e, stage);
		}

		//se selecciona por defecto a argentina en el combo box país
		for(Pais p: cbPais.getItems()){
			if(p.getNombre().equals("Argentina")){
				cbPais.getSelectionModel().select(p);
				break;
			}
		}
		actualizarProvincias(cbPais.getSelectionModel().getSelectedItem());
		final TextFormatter<Integer> formatterInteger = new TextFormatter<>(new IntegerStringConverter());
		final TextFormatter<Double> formatterDouble = new TextFormatter<>(new DoubleStringConverter());
		tfAntiguedad.setTextFormatter(formatterInteger);
		tfBaños.setTextFormatter(formatterInteger);
		tfAltura.setTextFormatter(formatterInteger);
		tfDormitorios.setTextFormatter(formatterInteger);
		tfSuperficieEdificio.setTextFormatter(formatterDouble);
		tfFondo.setTextFormatter(formatterDouble);
		tfFrente.setTextFormatter(formatterDouble);
		tfSuperficie.setTextFormatter(formatterDouble);
		tfPrecioVenta.setTextFormatter(formatterDouble);

		cbPais.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> actualizarProvincias(newValue));
		cbProvincia.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> actualizarLocalidades(newValue));
		cbLocalidad.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> actualizarBarriosYCalles(newValue));

		//se setean los converters para cuando se ingrese un item no existente a través
		//del editor de texto de los comboBox editables
		cbPais.setConverter(new StringConverter<Pais>() {

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
				for(Pais pais: cbPais.getItems()){
					if(nombre.equals(pais.getNombre())){
						return pais;
					}
				}
				Pais pais = new Pais();
				pais.setNombre(nombre);
				return pais;
			}
		});

		cbProvincia.setConverter(new StringConverter<Provincia>() {

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
				for(Provincia prov: cbProvincia.getItems()){
					if(nombre.equals(prov.getNombre())){
						return prov;
					}
				}
				Provincia prov = new Provincia();
				prov.setNombre(nombre);
				prov.setPais(cbPais.getValue());
				return prov;
			}
		});

		cbLocalidad.setConverter(new StringConverter<Localidad>() {

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
				for(Localidad loc: cbLocalidad.getItems()){
					if(nombre.equals(loc.getNombre())){
						return loc;
					}
				}
				Localidad loc = new Localidad();
				loc.setNombre(nombre);
				loc.setProvincia(cbProvincia.getValue());
				return loc;
			}
		});

		cbBarrio.setConverter(new StringConverter<Barrio>() {

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
				for(Barrio bar: cbBarrio.getItems()){
					if(nombre.equals(bar.getNombre())){
						return bar;
					}
				}
				Barrio bar = new Barrio();
				bar.setNombre(nombre);
				bar.setLocalidad(cbLocalidad.getValue());
				return bar;
			}
		});

		cbCalle.setConverter(new StringConverter<Calle>() {

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
				for(Calle cal: cbCalle.getItems()){
					if(nombre.equals(cal.getNombre())){
						return cal;
					}
				}
				Calle cal = new Calle();
				cal.setNombre(nombre);
				cal.setLocalidad(cbLocalidad.getValue());
				return cal;
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
		cbLocalidad.getItems().clear();
		if(provincia != null && provincia.getId() != null){
			try{
				cbLocalidad.getItems().addAll(coordinador.obtenerLocalidadesDe(provincia));
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
		cbProvincia.getItems().clear();
		if(pais != null && pais.getId() != null){
			try{
				cbProvincia.getItems().addAll(coordinador.obtenerProvinciasDe(pais));
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
		cbBarrio.getItems().clear();
		cbCalle.getItems().clear();
		if(loc != null && loc.getId() != null){
			try{
				cbBarrio.getItems().addAll(coordinador.obtenerBarriosDe(loc));
				cbCalle.getItems().addAll(coordinador.obtenerCallesDe(loc));
			} catch(PersistenciaException e){
				presentador.presentarExcepcion(e, stage);
			}
		}
		cbBarrio.getEditor().clear();
		cbCalle.getEditor().clear();
	}

	@FXML
	public void agregarFoto() {
		File imagen = solicitarArchivo();
		if(imagen == null){
			return;
		}

		try{
			final ImageView imageView = new ImageView(imagen.toURI().toURL().toExternalForm());
			imageView.setPreserveRatio(true);
			imageView.setFitHeight(100);
			imageView.setOnMouseClicked((event) -> {
				cambiarImagen(imageView);
			});
			panelFotos.getChildren().add(imageView);
		} catch(MalformedURLException e){
			presentador.presentarExcepcionInesperada(e, stage);
		}
	}

	private void cambiarImagen(ImageView imageView) {
		if(imagenSeleccionada != null){
			imagenSeleccionada.setOpacity(1);
			if(imagenSeleccionada.equals(imageView)){
				imagenSeleccionada = null;
				btQuitarFoto.setDisable(true);
				return;
			}
		}
		btQuitarFoto.setDisable(false);
		imagenSeleccionada = imageView;
		imagenSeleccionada.setOpacity(0.5);
	}

	private File solicitarArchivo() {
		File retorno = null;
		String tipos = "(";
		ArrayList<String> tiposFiltro = new ArrayList<>();
		for(String formato: ImageIO.getReaderFormatNames()){
			tipos += "*." + formato + ";";
			tiposFiltro.add("*." + formato);
		}
		tipos = tipos.substring(0, tipos.length() - 1);
		tipos += ")";

		ExtensionFilter filtro = new ExtensionFilter("Archivo de imágen " + tipos, tiposFiltro);

		FileChooser archivoSeleccionado = new FileChooser();
		archivoSeleccionado.getExtensionFilters().add(filtro);

		retorno = archivoSeleccionado.showOpenDialog(stage);
		if(retorno != null){
			String nombreArchivo = retorno.toString();
			retorno = new File(nombreArchivo);
		}
		return retorno;
	}

	@FXML
	public void quitarFoto() {
		panelFotos.getChildren().remove(imagenSeleccionada);
		imagenSeleccionada = null;
		btQuitarFoto.setDisable(true);
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
		ArrayList<ErrorControlador> erroresControlador = new ArrayList<>();
		ResultadoCrearInmueble resultado;
		StringBuffer erroresBfr = new StringBuffer();
		Inmueble inmueble = new Inmueble();

		//Toma de datos de la vista
		DatosEdificio datos = new DatosEdificio()
				.setSuperficie(Double.parseDouble(tfSuperficieEdificio.getText()))
				.setAntiguedad(Integer.parseInt(tfAntiguedad.getText()))
				.setDormitorios(Integer.parseInt(tfDormitorios.getText()))
				.setBaños(Integer.parseInt(tfBaños.getText()))
				.setPropiedadHorizontal(cbPropiedadHorizontal.isSelected())
				.setGaraje(cbGarage.isSelected())
				.setPatio(cbPatio.isSelected())
				.setPiscina(cbPiscina.isSelected())
				.setAguaCorriente(cbAguaCorriente.isSelected())
				.setCloacas(cbCloaca.isSelected())
				.setGasNatural(cbGasNatural.isSelected())
				.setAguaCaliente(cbAguaCaliente.isSelected())
				.setTelefono(cbTelefono.isSelected())
				.setLavadero(cbLavadero.isSelected())
				.setPavimento(cbPavimento.isSelected())
				.setInmueble(inmueble);

		Localidad localidad = cbLocalidad.getValue();
		Barrio barrio = cbBarrio.getValue();
		Calle calle = cbCalle.getValue();
		Date fechaCarga = new Date();
		Orientacion orientacion = cbOrientacion.getValue();
		Propietario propietario = cbPropietario.getValue();
		TipoInmueble tipo = cbTipoInmueble.getValue();

		Direccion direccion = new Direccion()
				.setLocalidad(localidad)
				.setCalle(calle)
				.setNumero(tfAltura.getText())
				.setBarrio(barrio)
				.setDepartamento(tfDepartamento.getText())
				.setOtros(tfOtros.getText())
				.setPiso(tfPiso.getText());

		inmueble.setDatosEdificio(datos)
				.setFechaCarga(fechaCarga)
				.setEstado(new Estado(EstadoStr.ALTA))
				.setDireccion(direccion)
				.setTipo(tipo)
				.setOrientacion(orientacion)
				.setPropietario(propietario)
				.setPrecio(Double.parseDouble(tfPrecioVenta.getText()))
				.setFrente(Double.parseDouble(tfFrente.getText()))
				.setFondo(Double.parseDouble(tfFondo.getText()))
				.setSuperficie(Double.parseDouble(tfSuperficie.getText()))
				.setObservaciones(taObservaciones.getText());

		try{
			resultado = coordinador.crearInmueble(inmueble);
		} catch(PersistenciaException e){
			presentador.presentarExcepcion(e, stage);
			return new ResultadoControlador(ErrorControlador.Error_Persistencia);
		} catch(Exception e){
			presentador.presentarExcepcionInesperada(e, stage);
			return new ResultadoControlador(ErrorControlador.Error_Desconocido);
		}

		if(resultado.hayErrores()){
			for(ErrorCrearInmueble err: resultado.getErrores()){
				switch(err) {
				case Fecha_Vacia:
					erroresBfr.append("Fecha no ingresada.\n");
					break;
				case Fondo_Incorrecto:
					erroresBfr.append("Formato del campo Fondo incorrecto.\n");
					break;
				case Formato_Direccion_Incorrecto:
					erroresBfr.append("Formato de dirección incorrecto.\n");
					break;
				case Frente_Incorrecto:
					erroresBfr.append("Formato del campo Frente incorrecto.\n");
					break;
				case Precio_Incorrecto:
					erroresBfr.append("Formato de precio incorrecto.\n");
					break;
				case Propietario_Inexistente:
					erroresBfr.append("El propietario seleccionado no existe en el sistema.\n");
					break;
				case Propietario_Vacio:
					erroresBfr.append("Elija el propietario.\n");
					break;
				case Superficie_Incorrecta:
					erroresBfr.append("Formato superficie de Inmueble incorrecto.\n");
					break;
				case Tipo_Vacio:
					erroresBfr.append("Elija el tipo de Inmueble.\n");
					break;
				case Datos_Edificio_Incorrectos:
					erroresBfr.append("Formato de los datos de edificio incorrectos.\n");
					break;
				}
			}
			erroresControlador.add(ErrorControlador.Datos_Incorrectos);
			presentador.presentarError("No se pudo crear el inmueble", erroresBfr.toString(), stage);
		}
		else{
			cambiarmeAScene(AdministrarInmuebleController.URLVista);
			presentador.presentarToast("Se ha creado el vendedor con éxito", stage);
		}

		return new ResultadoControlador(erroresControlador.toArray(new ErrorControlador[0]));
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

	public void formatearModificarInmueble(Inmueble inmueble) {

	}

	public void formatearVerInmueble(Inmueble inmueble) {

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
