package app.ui.controladores;

import java.net.URL;
import java.util.ResourceBundle;

public class InicioController extends OlimpoController {

	public static final String URLVista = "/app/ui/vistas/inicio.fxml";

	@Override
	protected void inicializar(URL location, ResourceBundle resources) {
		this.setTitulo("Bienvenido");
	}

}
