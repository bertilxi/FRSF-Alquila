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
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

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

	@Override
	protected void inicializar(URL location, ResourceBundle resources) {
		padre.getChildren().clear();
		padre.getChildren().add(pantalla1);
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

	@FXML
	public void aceptar() {

	}

	@FXML
	public void siguiente() {
		padre.getChildren().clear();
		padre.getChildren().add(pantalla2);
	}

	@FXML
	public void atras() {
		padre.getChildren().clear();
		padre.getChildren().add(pantalla1);
	}
}
