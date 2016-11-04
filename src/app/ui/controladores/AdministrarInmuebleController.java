package app.ui.controladores;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;

public class AdministrarInmuebleController extends OlimpoController {

	public static final String URLVista = "/app/ui/vistas/administrarInmueble.fxml";

	@Override
	protected void inicializar(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	@FXML
	public void agregar() {
		cambiarmeAScene(NMVInmuebleController.URLVista);
	}
}
