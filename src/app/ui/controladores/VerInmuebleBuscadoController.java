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
			tablaBarrios.getItems().clear();
			tablaBarrios.getItems().addAll(inmueble.getBarrios());
			tablaLocalidades.getItems().clear();
			tablaLocalidades.getItems().addAll(inmueble.getLocalidades());
		});
	}

	@FXML
	private void HandleAtras() {
		cambiarmeAScene(AdministrarClienteController.URLVista);
	}

	@Override
	public void inicializar(URL location, ResourceBundle resources) {
		labelLocal.setText("No");
		labelCasa.setText("No");
		labelDepartamento.setText("No");
		labelGalpon.setText("No");
		labelQuinta.setText("No");
		labelTerreno.setText("No");

		columnaNombreLocalidad.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
		columnaNombreBarrio.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
		columnaNombreLocalidadDelBarrio.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLocalidad().getNombre()));
	}
}