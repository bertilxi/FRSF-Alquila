package app.ui.controladores;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import app.datos.clases.CatalogoVista;
import app.datos.entidades.Cliente;
import app.datos.entidades.Imagen;
import app.datos.entidades.Inmueble;
import app.datos.entidades.PDF;
import app.excepciones.GestionException;
import app.excepciones.PersistenciaException;
import app.logica.resultados.ResultadoCrearCatalogo;
import app.logica.resultados.ResultadoCrearCatalogo.ErrorCrearCatalogo;
import app.ui.ScenographyChanger;
import app.ui.controladores.resultado.ResultadoControlador;
import app.ui.controladores.resultado.ResultadoControlador.ErrorControlador;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;

/**
 * Controlador de la vista para dar de alta un catálogo
 */
public class AltaCatalogoController extends OlimpoController {

	public static final String URLVista = "/app/ui/vistas/altaCatalogo.fxml";

	@FXML
	protected Pane listaInmuebles;

	protected ArrayList<Inmueble> inmuebles = new ArrayList<>();

	@FXML
	protected ComboBox<Cliente> cbCliente;

	@FXML
	protected Pane fondo;

	@FXML
	protected Button btnGenerarCatalogo;

	protected Map<Node, RenglonInmuebleController> renglones = new HashMap<>();

	@Override
	protected void inicializar(URL location, ResourceBundle resources) {
		this.agregarScenographyChanger(fondo, new ScenographyChanger(stage, presentador, coordinador, fondo));
		this.setTitulo("Nuevo catalogo");

		btnGenerarCatalogo.setDisable(true);
		listaInmuebles.getChildren().addListener(new ListChangeListener<Node>() {
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends Node> c) {
				btnGenerarCatalogo.setDisable(c.getList().isEmpty() || cbCliente.getValue() == null);
			}
		});

		cbCliente.valueProperty().addListener(new ChangeListener<Cliente>() {
			@Override
			public void changed(ObservableValue<? extends Cliente> observable, Cliente oldValue, Cliente newValue) {
				btnGenerarCatalogo.setDisable(listaInmuebles.getChildren().isEmpty() || newValue == null);
			}
		});

		try{
			cbCliente.getItems().addAll(coordinador.obtenerClientes());
		} catch(PersistenciaException e){
			presentador.presentarExcepcion(e, stage);
		}
	}

	/**
	 * Método que se ejecuta al presionar el botón para agregar inmuebles
	 */
	@FXML
	public void agregarInmueble() {
		final ArrayList<Inmueble> inmueblesNuevos = new ArrayList<>();
		//Se va a la vista de consulta inmueble y se agregan a la vista al volver
		AdministrarInmuebleController vistaInmuebles = (AdministrarInmuebleController) this.cambiarScene(fondo, AdministrarInmuebleController.URLVista, (Pane) fondo.getChildren().get(0));
		vistaInmuebles.formatearObtenerInmueblesNoVendidos(inmuebles, inmueblesNuevos, () -> {
			agregarInmuebles(inmueblesNuevos);
		}, true);
	}

	/**
	 * Método que agrega inmuebles a la vista
	 *
	 * @param inmueblesNuevos
	 *            inmuebles a agregar a la vista
	 */
	private void agregarInmuebles(ArrayList<Inmueble> inmueblesNuevos) {
		try{
			for(Inmueble inmueble: inmueblesNuevos){
				//Se agrega un renglón a la lista de inmuebles
				RenglonInmuebleController renglonController = new RenglonInmuebleController(inmueble);

				//Se setea lo que va a hacer el botón eliminarInmueble del renglón
				renglonController.setEliminarInmueble(() -> {

					//Se quita al inmueble de la vista
					listaInmuebles.getChildren().remove(renglonController.getRoot());
					renglones.remove(renglonController.getRoot());
					inmuebles.remove(inmueble);
				});

				//Se agrega el inmueble a la vista
				listaInmuebles.getChildren().add(renglonController.getRoot());
				renglones.put(renglonController.getRoot(), renglonController);
				inmuebles.add(inmueble);
			}
		} catch(Exception e){
			presentador.presentarExcepcionInesperada(e);
		}
	}

	/**
	 * Acción que se ejecuta al apretar el botón generar catalogo.
	 *
	 * Toma datos de la vista, los carga al catálogo y deriva la operación a capa lógica.
	 * Si la capa lógica retorna errores, se muestran al usuario.
	 *
	 * @return ResultadoControlador que resume lo que hizo el controlador
	 */
	@FXML
	public ResultadoControlador generarCatalogo() {
		//Inicialización de variables
		ResultadoCrearCatalogo resultado;
		StringBuffer erroresBfr = new StringBuffer();
		CatalogoVista catalogo = null;

		//Toma de datos de la vista
		Map<Inmueble, Imagen> fotos = new HashMap<>();
		for(Node n: listaInmuebles.getChildren()){
			if(renglones.get(n) != null){
				RenglonInmuebleController renglon = renglones.get(n);
				fotos.put(renglon.getInmueble(), renglon.getFotoSeleccionada());
			}
		}

		//Se cargan los datos de la vista al catálogo a crear
		catalogo = new CatalogoVista(cbCliente.getValue(), fotos);

		//Inicio transacciones al gestor
		try{
			//Se llama a la lógica para crear el catálogo y se recibe el resultado de las validaciones y datos extras de ser necesarios
			resultado = coordinador.crearCatalogo(catalogo);
		} catch(PersistenciaException | GestionException e){
			presentador.presentarExcepcion(e, stage);
			return new ResultadoControlador(ErrorControlador.Error_Persistencia);
		} catch(Exception e){
			presentador.presentarExcepcionInesperada(e, stage);
			return new ResultadoControlador(ErrorControlador.Error_Desconocido);
		}

		//Procesamiento de errores de la lógica
		if(resultado.hayErrores()){
			for(ErrorCrearCatalogo e: resultado.getErrores()){
				switch(e) {
				case Barrio_Inmueble_Inexistente:
					erroresBfr.append("El barrio del inmueble está vacío.\n");
					break;
				case Cliente_inexistente:
					erroresBfr.append("No se seleccionó un cliente.\n");
					break;
				case Codigo_Inmueble_Inexistente:
					erroresBfr.append("El código del inmueble está vacío.\n");
					break;
				case Direccion_Inmueble_Inexistente:
					erroresBfr.append("La dirección del inmueble está vacío.\n");
					break;
				case Localidad_Inmueble_Inexistente:
					erroresBfr.append("La localidad del inmueble está vacío.\n");
					break;
				case Precio_Inmueble_Inexistente:
					erroresBfr.append("El precio del inmueble está vacío.\n");
					break;
				case Tipo_Inmueble_Inexistente:
					erroresBfr.append("El tipo del inmueble está vacío.\n");
					break;
				}
			}

			String errores = erroresBfr.toString();
			if(!errores.isEmpty()){
				//Se muestran los errores
				presentador.presentarError("Error al crear el catálogo", errores, stage);
			}
			//Se retorna error
			return new ResultadoControlador(ErrorControlador.Campos_Vacios);
		}
		else{
			//Se muestra una notificación de que se creó correctamente el catálogo
			presentador.presentarToast("Se ha creado el catálogo con éxito", stage);
			//Se muestra el catálogo
			mostrarPDF(resultado.getCatalogoPDF());
			//Se retorna que no hubo errores
			return new ResultadoControlador();
		}
	}

	protected void mostrarPDF(PDF pdf) {
		String irA = null;
		if(URLVistaRetorno != null){
			irA = URLVistaRetorno;
		}
		else{
			irA = AdministrarClienteController.URLVista;
		}
		VerPDFController visorPDF = (VerPDFController) cambiarmeAScene(VerPDFController.URLVista, irA);
		visorPDF.cargarPDF(pdf);
	}

	public void setCliente(Cliente cliente) {
		Platform.runLater(() -> {
			cbCliente.getSelectionModel().select(cliente);
		});
		cbCliente.setDisable(true);
	}

	public void setInmuebles(ArrayList<Inmueble> inmuebles) {
		agregarInmuebles(inmuebles);
	}
}
