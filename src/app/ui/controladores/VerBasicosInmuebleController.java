package app.ui.controladores;

import java.net.URL;
import java.util.ResourceBundle;

import app.datos.entidades.Cliente;
import app.datos.entidades.Inmueble;
import app.datos.entidades.Propietario;
import app.datos.entidades.Vendedor;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Controlador de la vista para ver los datos del inmueble buscado por un cliente
 */
public class VerBasicosInmuebleController extends OlimpoController {
	public static final String URLVista = "/app/ui/vistas/verDatosBasicosInmueble.fxml";

	@FXML
	private Label labelCodigoInmueble;
	@FXML
	private Label labelTipoInmueble;
	@FXML
	private Label labelPais;
	@FXML
	private Label labelProvincia;
	@FXML
	private Label labelLocalidad;
	@FXML
	private Label labelBarrio;
	@FXML
	private Label labelCalle;
	@FXML
	private Label labelAltura;
	@FXML
	private Label labelPiso;
	@FXML
	private Label labelDepartamento;
	@FXML
	private Label labelOtros;

	private Cliente cliente;
	private Propietario propietario;
	private Vendedor vendedor;

	private TipoPersona persona;

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
		persona = TipoPersona.Cliente;
	}

	public void setPropietario(Propietario propietario) {
		this.propietario = propietario;
		persona = TipoPersona.Propietario;
	}

	public void setVendedor(Vendedor vendedor) {
		this.vendedor = vendedor;
		persona = TipoPersona.Vendedor;
	}

	/**
	 * Se setean los campos con los datos del inmueble
	 *
	 * @param inmueble
	 * 			inmueble del que se obtienen los datos
	 */
	public void setInmueble(Inmueble inmueble) {
		Platform.runLater(() -> {
			labelAltura.setText(inmueble.getDireccion().getNumero());
			labelBarrio.setText(inmueble.getDireccion().getBarrio().getNombre());
			labelCalle.setText(inmueble.getDireccion().getCalle().getNombre());
			labelCodigoInmueble.setText(inmueble.getId().toString());
			labelDepartamento.setText(inmueble.getDireccion().getDepartamento());
			labelLocalidad.setText(inmueble.getDireccion().getLocalidad().getNombre());
			labelOtros.setText(inmueble.getDireccion().getOtros());
			labelPais.setText(inmueble.getDireccion().getLocalidad().getProvincia().getPais().getNombre());
			labelPiso.setText(inmueble.getDireccion().getPiso());
			labelProvincia.setText(inmueble.getDireccion().getLocalidad().getProvincia().getNombre());
			labelTipoInmueble.setText(inmueble.getTipo().toString());
		});
	}

	/**
	 * Acción que se ejecuta al presionar el botón atrás.
	 * Se vuelve a la pantalla administrar cliente.
	 */
	@FXML
	private void handleAtras() {
		AdministrarVentaController controlador = (AdministrarVentaController) cambiarmeAScene(AdministrarVentaController.URLVista);
		controlador.setVendedorLogueado(vendedorLogueado);
		switch(persona) {
		case Cliente:
			controlador.setCliente(cliente);
			break;
		case Propietario:
			controlador.setPropietario(propietario);
			break;
		case Vendedor:
			controlador.setVendedor(vendedor);
			break;
		}
	}

	@Override
	public void inicializar(URL location, ResourceBundle resources) {
		this.setTitulo("Ver inmueble");
	}

	public enum TipoPersona {
		Cliente,
		Propietario,
		Vendedor
	}
}