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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.imageio.ImageIO;

import app.comun.ConversorFechas;
import app.datos.clases.EstadoStr;
import app.datos.entidades.Barrio;
import app.datos.entidades.Calle;
import app.datos.entidades.DatosEdificio;
import app.datos.entidades.Direccion;
import app.datos.entidades.Estado;
import app.datos.entidades.Imagen;
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
import app.logica.resultados.ResultadoModificarInmueble;
import app.logica.resultados.ResultadoModificarInmueble.ErrorModificarInmueble;
import app.ui.controladores.resultado.ResultadoControlador;
import app.ui.controladores.resultado.ResultadoControlador.ErrorControlador;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;

/**
 * Controlador de la vista que crea, modifica o muestra un inmueble
 * Pertenece a la taskcard 13 de la iteración 1 y a la historia 3
 */
public class NMVInmuebleController extends OlimpoController {

	public static final String URLVista = "/app/ui/vistas/NMVInmueble.fxml";

	@FXML
	protected CheckBox cbAguaCaliente;

	@FXML
	protected CheckBox cbAguaCorriente;

	@FXML
	protected CheckBox cbCloaca;

	@FXML
	protected CheckBox cbGarage;

	@FXML
	protected CheckBox cbGasNatural;

	@FXML
	protected CheckBox cbLavadero;

	@FXML
	protected CheckBox cbPatio;

	@FXML
	protected CheckBox cbPavimento;

	@FXML
	protected CheckBox cbPiscina;

	@FXML
	protected CheckBox cbPropiedadHorizontal;

	@FXML
	protected CheckBox cbTelefono;

	@FXML
	protected ComboBox<Barrio> cbBarrio;

	@FXML
	protected ComboBox<Calle> cbCalle;

	@FXML
	protected ComboBox<Localidad> cbLocalidad;

	@FXML
	protected ComboBox<Orientacion> cbOrientacion;

	@FXML
	protected ComboBox<Pais> cbPais;

	@FXML
	protected ComboBox<Propietario> cbPropietario;

	@FXML
	protected ComboBox<Provincia> cbProvincia;

	@FXML
	protected ComboBox<TipoInmueble> cbTipoInmueble;

	@FXML
	protected TextArea taObservaciones;

	@FXML
	protected TextField tfAltura;

	@FXML
	protected TextField tfAntiguedad;

	@FXML
	protected TextField tfBaños;

	@FXML
	protected TextField tfCodigo;

	@FXML
	protected TextField tfDepartamento;

	@FXML
	protected TextField tfDormitorios;

	@FXML
	protected TextField tfFechaCarga;

	@FXML
	protected TextField tfFondo;

	@FXML
	protected TextField tfFrente;

	@FXML
	protected TextField tfOtros;

	@FXML
	protected TextField tfPiso;

	@FXML
	protected TextField tfPrecioVenta;

	@FXML
	protected TextField tfSuperficie;

	@FXML
	protected TextField tfSuperficieEdificio;

	@FXML
	private Pane padre;

	@FXML
	private Pane pantalla1;

	@FXML
	private Pane pantalla2;

	@FXML
	private Pane panelFotos;

	@FXML
	private GridPane gridFotos;

	@FXML
	private ScrollPane scrollFotos;

	@FXML
	private Map<ImageView, File> archivosImagenesNuevas = new HashMap<>();

	@FXML
	private ImageView imagenSeleccionada;

	@FXML
	private Button btQuitarFoto;

	@FXML
	private Button btAgregarFoto;

	@FXML
	private Button btAceptar;

	private StringProperty titulo1 = new SimpleStringProperty();

	private StringProperty titulo2 = new SimpleStringProperty();

	private Inmueble inmueble;

	private ConversorFechas conversorFechas = new ConversorFechas();

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
			if(p.getNombre().equals("argentina")){
				cbPais.getSelectionModel().select(p);
				break;
			}
		}
		//se selecciona por defecto a santa fe en el combo box provincia
		for(Provincia pr: cbProvincia.getItems()){
			if(pr.getNombre().equals("santa fe")){
				cbProvincia.getSelectionModel().select(pr);
				break;
			}
		}
		actualizarProvincias(cbPais.getSelectionModel().getSelectedItem());
		tfAntiguedad.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
		tfBaños.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
		tfDormitorios.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
		tfSuperficieEdificio.setTextFormatter(new TextFormatter<>(new NumberStringConverter(Locale.US, "###.##")));
		tfFondo.setTextFormatter(new TextFormatter<>(new NumberStringConverter(Locale.US, "###.##")));
		tfFrente.setTextFormatter(new TextFormatter<>(new NumberStringConverter(Locale.US, "###.##")));
		tfSuperficie.setTextFormatter(new TextFormatter<>(new NumberStringConverter(Locale.US, "###.##")));
		tfPrecioVenta.setTextFormatter(new TextFormatter<>(new NumberStringConverter(Locale.US, "###.##")));

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
				nombre = nombre.toLowerCase().trim();
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
				nombre = nombre.toLowerCase().trim();
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
				nombre = nombre.toLowerCase().trim();
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
				nombre = nombre.toLowerCase().trim();
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
				nombre = nombre.toLowerCase().trim();
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

		//Cuando el foco sale de los comboBox que estaban siendo editados
		//el texto ingresado se convierte en un item y se lo selecciona
		cbPais.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
			if(!newPropertyValue){
				cbPais.getSelectionModel().select(cbPais.getConverter().fromString(cbPais.getEditor().getText()));

			}
		});

		cbProvincia.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
			if(!newPropertyValue){
				cbProvincia.getSelectionModel().select(cbProvincia.getConverter().fromString(cbProvincia.getEditor().getText()));

			}
		});

		cbLocalidad.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
			if(!newPropertyValue){
				cbLocalidad.getSelectionModel().select(cbLocalidad.getConverter().fromString(cbLocalidad.getEditor().getText()));

			}
		});

		cbBarrio.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
			if(!newPropertyValue){
				cbBarrio.getSelectionModel().select(cbBarrio.getConverter().fromString(cbBarrio.getEditor().getText()));

			}
		});

		cbCalle.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
			if(!newPropertyValue){
				cbCalle.getSelectionModel().select(cbCalle.getConverter().fromString(cbCalle.getEditor().getText()));

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
				seleccionarImagen(imageView);
			});
			panelFotos.getChildren().add(imageView);
			archivosImagenesNuevas.put(imageView, imagen);
		} catch(MalformedURLException e){
			presentador.presentarExcepcionInesperada(e, stage);
		}
	}

	private void seleccionarImagen(ImageView imageView) {
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
		ResultadoControlador resultado;
		if(inmueble == null){
			resultado = crearInmueble();
		}
		else{
			resultado = modificarInmueble();
		}
		if(!resultado.hayErrores()){
			salir();
		}
		return resultado;
	}

	/**
	 * Método que permite crear un inmueble
	 * Pertenece a la taskcard 13 de la iteración 1 y a la historia 3
	 *
	 * @return ResultadoControlador que resume lo que hizo el controlador
	 */
	private ResultadoControlador crearInmueble() {
		Set<ErrorControlador> erroresControlador = new HashSet<>();
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
				.setNumero(tfAltura.getText().toLowerCase().trim())
				.setBarrio(barrio)
				.setDepartamento(tfDepartamento.getText().toLowerCase().trim())
				.setOtros(tfOtros.getText().toLowerCase().trim())
				.setPiso(tfPiso.getText().toLowerCase().trim());

		//Guardar fotos
		ArrayList<Imagen> fotos = new ArrayList<>();
		for(Node nodo: panelFotos.getChildren()){
			if(nodo instanceof ImageView){
				ImageView imagen = (ImageView) nodo;
				File file = archivosImagenesNuevas.get(imagen);
				byte[] bFile = new byte[(int) file.length()];

				try{
					FileInputStream fileInputStream = new FileInputStream(file);
					//convert file into array of bytes
					fileInputStream.read(bFile);
					fileInputStream.close();
				} catch(Exception e){
					presentador.presentarExcepcion(e, stage);
					return new ResultadoControlador(ErrorControlador.Error_Desconocido);
				}
				fotos.add((Imagen) new Imagen().setArchivo(bFile));
			}
		}

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
				.setObservaciones(taObservaciones.getText())
				.getFotos().addAll(fotos);

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
					erroresControlador.add(ErrorControlador.Campos_Vacios);
					break;
				case Fondo_Incorrecto:
					erroresBfr.append("Formato del campo Fondo incorrecto.\n");
					erroresControlador.add(ErrorControlador.Datos_Incorrectos);
					break;
				case Formato_Direccion_Incorrecto:
					erroresBfr.append("Formato de dirección incorrecto.\n");
					erroresControlador.add(ErrorControlador.Datos_Incorrectos);
					break;
				case Frente_Incorrecto:
					erroresBfr.append("Formato del campo Frente incorrecto.\n");
					erroresControlador.add(ErrorControlador.Datos_Incorrectos);
					break;
				case Precio_Vacio:
					erroresBfr.append("Precio no ingresado.\n");
					erroresControlador.add(ErrorControlador.Campos_Vacios);
					break;
				case Precio_Incorrecto:
					erroresBfr.append("Formato de precio incorrecto.\n");
					erroresControlador.add(ErrorControlador.Datos_Incorrectos);
					break;
				case Propietario_Inexistente:
					erroresBfr.append("El propietario seleccionado no existe en el sistema.\n");
					erroresControlador.add(ErrorControlador.Entidad_No_Encontrada);
					break;
				case Propietario_Vacio:
					erroresBfr.append("Elija el propietario.\n");
					erroresControlador.add(ErrorControlador.Campos_Vacios);
					break;
				case Superficie_Incorrecta:
					erroresBfr.append("Formato superficie de Inmueble incorrecto.\n");
					erroresControlador.add(ErrorControlador.Datos_Incorrectos);
					break;
				case Tipo_Vacio:
					erroresBfr.append("Elija el tipo de Inmueble.\n");
					erroresControlador.add(ErrorControlador.Campos_Vacios);
					break;
				case Datos_Edificio_Incorrectos:
					erroresBfr.append("Formato de los datos de edificio incorrectos.\n");
					erroresControlador.add(ErrorControlador.Datos_Incorrectos);
					break;
				}
			}
			presentador.presentarError("No se pudo crear el inmueble", erroresBfr.toString(), stage);
		}
		else{
			presentador.presentarToast("Se ha creado el inmueble con éxito", stage);
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
		ArrayList<ErrorControlador> erroresControlador = new ArrayList<>();
		ResultadoModificarInmueble resultado;
		StringBuffer erroresBfr = new StringBuffer();

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
		Orientacion orientacion = cbOrientacion.getValue();
		Propietario propietario = cbPropietario.getValue();
		TipoInmueble tipo = cbTipoInmueble.getValue();

		Direccion direccion = new Direccion()
				.setLocalidad(localidad)
				.setCalle(calle)
				.setNumero(tfAltura.getText().toLowerCase().trim())
				.setBarrio(barrio)
				.setDepartamento(tfDepartamento.getText().toLowerCase().trim())
				.setOtros(tfOtros.getText().toLowerCase().trim())
				.setPiso(tfPiso.getText().toLowerCase().trim());

		//Guardar fotos
		ArrayList<Imagen> fotos = new ArrayList<>();
		for(Node nodo: panelFotos.getChildren()){
			if(nodo instanceof ImageView){
				ImageView imagen = (ImageView) nodo;
				File file = archivosImagenesNuevas.get(imagen);
				byte[] bFile = new byte[(int) file.length()];

				try{
					FileInputStream fileInputStream = new FileInputStream(file);
					//convert file into array of bytes
					fileInputStream.read(bFile);
					fileInputStream.close();
				} catch(Exception e){
					presentador.presentarExcepcion(e, stage);
					return new ResultadoControlador(ErrorControlador.Error_Desconocido);
				}
				fotos.add((Imagen) new Imagen().setArchivo(bFile));
			}
		}

		inmueble.setDatosEdificio(datos)
				.setEstado(new Estado(EstadoStr.ALTA))
				.setDireccion(direccion)
				.setTipo(tipo)
				.setOrientacion(orientacion)
				.setPropietario(propietario)
				.setPrecio(Double.parseDouble(tfPrecioVenta.getText()))
				.setFrente(Double.parseDouble(tfFrente.getText()))
				.setFondo(Double.parseDouble(tfFondo.getText()))
				.setSuperficie(Double.parseDouble(tfSuperficie.getText()))
				.setObservaciones(taObservaciones.getText())
				.getFotos().addAll(fotos);

		try{
			resultado = coordinador.modificarInmueble(inmueble);
		} catch(PersistenciaException e){
			presentador.presentarExcepcion(e, stage);
			return new ResultadoControlador(ErrorControlador.Error_Persistencia);
		} catch(Exception e){
			presentador.presentarExcepcionInesperada(e, stage);
			return new ResultadoControlador(ErrorControlador.Error_Desconocido);
		}

		if(resultado.hayErrores()){
			for(ErrorModificarInmueble err: resultado.getErrores()){
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
				case Precio_Vacio:
					erroresBfr.append("Precio no ingresado.\n");
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
				case Inmueble_Inexistente:
					erroresBfr.append("El inmueble ya no existe en el sistema");
					break;
				}
			}
			erroresControlador.add(ErrorControlador.Datos_Incorrectos);
			presentador.presentarError("No se pudo modificar el inmueble", erroresBfr.toString(), stage);
		}
		else{
			presentador.presentarToast("Se ha modificado el inmueble con éxito", stage);
		}

		return new ResultadoControlador(erroresControlador.toArray(new ErrorControlador[0]));
	}

	public void formatearModificarInmueble(Inmueble inmueble) {
		this.inmueble = inmueble;
		Platform.runLater(() -> {
			titulo1.set("Modificar inmueble");
			cargarDatos();
		});
	}

	public void formatearVerInmueble(Inmueble inmueble) {
		this.inmueble = inmueble;
		Platform.runLater(() -> {
			titulo1.set("Ver inmueble");
			cargarDatos();
			deshabilitarCampos();
		});
	}

	private void deshabilitarCampos() {
		cbAguaCaliente.setDisable(true);
		cbAguaCorriente.setDisable(true);
		cbCloaca.setDisable(true);
		cbGarage.setDisable(true);
		cbGasNatural.setDisable(true);
		cbLavadero.setDisable(true);
		cbPatio.setDisable(true);
		cbPavimento.setDisable(true);
		cbPiscina.setDisable(true);
		cbPropiedadHorizontal.setDisable(true);
		cbTelefono.setDisable(true);

		cbBarrio.setDisable(true);
		cbCalle.setDisable(true);
		cbLocalidad.setDisable(true);
		cbOrientacion.setDisable(true);
		cbPais.setDisable(true);
		cbPropietario.setDisable(true);
		cbProvincia.setDisable(true);
		cbTipoInmueble.setDisable(true);

		taObservaciones.setEditable(false);

		tfAltura.setEditable(false);
		tfAntiguedad.setEditable(false);
		tfBaños.setEditable(false);
		tfCodigo.setEditable(false);
		tfDepartamento.setEditable(false);
		tfDormitorios.setEditable(false);
		tfFechaCarga.setEditable(false);
		tfFondo.setEditable(false);
		tfFrente.setEditable(false);
		tfOtros.setEditable(false);
		tfPiso.setEditable(false);
		tfPrecioVenta.setEditable(false);
		tfSuperficie.setEditable(false);
		tfSuperficieEdificio.setEditable(false);

		btAceptar.setVisible(false);
		btAgregarFoto.setVisible(false);
		btQuitarFoto.setVisible(false);

		GridPane.setColumnSpan(scrollFotos, GridPane.getColumnSpan(scrollFotos) + 1);
	}

	private void cargarDatos() {
		cbAguaCaliente.setSelected(inmueble.getDatosEdificio().getAguaCaliente());
		cbAguaCorriente.setSelected(inmueble.getDatosEdificio().getAguaCorriente());
		cbCloaca.setSelected(inmueble.getDatosEdificio().getCloacas());
		cbGarage.setSelected(inmueble.getDatosEdificio().getGaraje());
		cbGasNatural.setSelected(inmueble.getDatosEdificio().getGasNatural());
		cbLavadero.setSelected(inmueble.getDatosEdificio().getLavadero());
		cbPatio.setSelected(inmueble.getDatosEdificio().getPatio());
		cbPavimento.setSelected(inmueble.getDatosEdificio().getPavimento());
		cbPiscina.setSelected(inmueble.getDatosEdificio().getPiscina());
		cbPropiedadHorizontal.setSelected(inmueble.getDatosEdificio().getPropiedadHorizontal());
		cbTelefono.setSelected(inmueble.getDatosEdificio().getTelefono());

		cbPais.getSelectionModel().select(null);
		cbPais.getSelectionModel().select(inmueble.getDireccion().getLocalidad().getProvincia().getPais());
		cbProvincia.getSelectionModel().select(inmueble.getDireccion().getLocalidad().getProvincia());
		cbLocalidad.getSelectionModel().select(inmueble.getDireccion().getLocalidad());
		cbBarrio.getSelectionModel().select(inmueble.getDireccion().getBarrio());
		cbCalle.getSelectionModel().select(inmueble.getDireccion().getCalle());
		cbOrientacion.getSelectionModel().select(inmueble.getOrientacion());
		cbPropietario.getSelectionModel().select(inmueble.getPropietario());
		cbTipoInmueble.getSelectionModel().select(inmueble.getTipo());

		taObservaciones.setText(inmueble.getObservaciones());

		tfAltura.setText(inmueble.getDireccion().getNumero());
		tfAntiguedad.setText(inmueble.getDatosEdificio().getAntiguedad().toString());
		tfBaños.setText(inmueble.getDatosEdificio().getBaños().toString());
		tfCodigo.setText(inmueble.getId().toString());
		tfDepartamento.setText(inmueble.getDireccion().getDepartamento());
		tfDormitorios.setText(inmueble.getDatosEdificio().getDormitorios().toString());
		tfFechaCarga.setText(conversorFechas.diaMesYAnioToString(inmueble.getFechaCarga()));
		tfFondo.setText(inmueble.getFondo().toString());
		tfFrente.setText(inmueble.getFrente().toString());
		tfOtros.setText(inmueble.getDireccion().getOtros());
		tfPiso.setText(inmueble.getDireccion().getPiso());
		tfPrecioVenta.setText(inmueble.getPrecio().toString());
		tfSuperficie.setText(inmueble.getSuperficie().toString());
		tfSuperficieEdificio.setText(inmueble.getDatosEdificio().getSuperficie().toString());

		for(Imagen imagen: inmueble.getFotos()){
			byte[] imagenRaw = imagen.getArchivo();
			InputStream in = new ByteArrayInputStream(imagenRaw);
			final ImageView imageView = new ImageView(new Image(in));
			imageView.setPreserveRatio(true);
			imageView.setFitHeight(100);
			imageView.setOnMouseClicked((event) -> {
				seleccionarImagen(imageView);
			});
			panelFotos.getChildren().add(imageView);
		}
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
