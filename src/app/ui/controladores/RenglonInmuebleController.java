package app.ui.controladores;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import app.datos.entidades.Imagen;
import app.datos.entidades.Inmueble;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Controlador de la vista mostrar un inmueble y su foto
 */
public class RenglonInmuebleController extends OlimpoController {

	public static final String URLVista = "/app/ui/vistas/renglonInmueble.fxml";

	private Runnable eliminarInmueble;

	private Inmueble inmueble;

	@FXML
	private Label lbInmuebleId;
	
	@FXML
	private Label lblTipoInmueble;
	
	@FXML
	private Label lblPaisInmueble;

	@FXML
	private Label lblProvinciaInmueble;
	
	@FXML
	private Label lblLocalidadInmueble;
	
	@FXML
	private Label lblCalleAlturaInmueble;
	
	@FXML
	private Label lblBarrioInmueble;
	
	@FXML
	private Label lblPrecioInmueble;

	@FXML
	private ComboBox<Imagen> cbFotos;

	private Parent root;

	public RenglonInmuebleController(Inmueble inmueble) throws IOException {
		this.inmueble = inmueble;

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource(URLVista));
		loader.setControllerFactory(claseControlador -> {
			if(claseControlador != null && !claseControlador.isInstance(this)){
				throw new IllegalArgumentException("¡Instancia del controlador inválida, esperada una instancia de la clase '" + claseControlador.getName() + "'!");
			}

			return this;
		});

		root = loader.load();
	}

	public Parent getRoot() {
		return root;
	}

	public Inmueble getInmueble() {
		return inmueble;
	}

	public void setEliminarInmueble(Runnable eliminarInmueble) {
		this.eliminarInmueble = eliminarInmueble;
	}

	public Imagen getFotoSeleccionada() {
		return cbFotos.getValue();
	}

	@Override
	protected void inicializar(URL location, ResourceBundle resources) {
		lbInmuebleId.setText(String.format(lbInmuebleId.getText(), inmueble.getId()));
		lblTipoInmueble.setText(String.format(lblTipoInmueble.getText(), inmueble.getTipo().getTipo().toString()));
		lblPaisInmueble.setText(String.format(lblPaisInmueble.getText(), inmueble.getDireccion().getLocalidad().getProvincia().getPais().toString()));
		lblProvinciaInmueble.setText(String.format(lblProvinciaInmueble.getText(), inmueble.getDireccion().getLocalidad().getProvincia().toString()));
		lblLocalidadInmueble.setText(String.format(lblLocalidadInmueble.getText(), inmueble.getDireccion().getLocalidad().toString()));
		lblCalleAlturaInmueble.setText(String.format(lblCalleAlturaInmueble.getText(), inmueble.getDireccion().getCalle().toString(), inmueble.getDireccion().getNumero()));
		lblBarrioInmueble.setText(String.format(lblBarrioInmueble.getText(), inmueble.getDireccion().getBarrio().toString()));
		lblPrecioInmueble.setText(String.format(lblPrecioInmueble.getText(), inmueble.getPrecio()));
		
		if(inmueble.getFotos().isEmpty()){
			cbFotos.setVisible(false);
		}
		else{
			for(Imagen imagen: inmueble.getFotos()){
				cbFotos.getItems().add(imagen);
			}
			cbFotos.getSelectionModel().select(new Random().nextInt(cbFotos.getItems().size()));
			cbFotos.setCellFactory((p) -> {
				return generarListCell();
			});
			cbFotos.setButtonCell(generarListCell());
		}
	}

	private ListCell<Imagen> generarListCell(){
		return new ListCell<Imagen>() {
			@Override
			protected void updateItem(Imagen item, boolean empty) {
				super.updateItem(item, empty);

				if(item == null || empty){
					setGraphic(null);
				}
				else{
					byte[] imagenRaw = item.getArchivo();
					InputStream in = new ByteArrayInputStream(imagenRaw);
					ImageView imageView = new ImageView(new Image(in));
					imageView.setPreserveRatio(true);
					if(imageView.getImage().getHeight() > imageView.getImage().getWidth()){
						imageView.setFitHeight(100);
					}
					else{
						imageView.setFitWidth(100);
					}
					setGraphic(imageView);
				}
			}
		};
	}
	
	@FXML
	public void eliminarInmueble() {
		eliminarInmueble.run();
	}
}
