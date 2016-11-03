package app.ui.controladores;

import java.net.URL;
import java.util.ResourceBundle;

import app.logica.CoordinadorJavaFX;
import app.ui.ScenographyChanger;
import app.ui.componentes.ventanas.PresentadorVentanas;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public abstract class OlimpoController implements Initializable {

	protected Stage stage;
	protected CoordinadorJavaFX coordinador;
	protected PresentadorVentanas presentador;
	private ScenographyChanger scenographyChanger;

	protected OlimpoController cambiarScene(String URLVistaACambiar) {
		return scenographyChanger.cambiarScenography(URLVistaACambiar);
	}

	public OlimpoController setScenographyChanger(ScenographyChanger scenographyChanger) {
		this.scenographyChanger = scenographyChanger;
		return this;
	}

	public OlimpoController setStage(Stage stage) {
		this.stage = stage;
		return this;
	}

	public OlimpoController setCoordinador(CoordinadorJavaFX coordinador) {
		this.coordinador = coordinador;
		return this;
	}

	public OlimpoController setPresentador(PresentadorVentanas presentador) {
		this.presentador = presentador;
		return this;
	}

	@Override
	public final void initialize(URL location, ResourceBundle resources) {
		Platform.runLater(() -> {
			inicializar(location, resources);
		});
	}

	protected abstract void inicializar(URL location, ResourceBundle resources);

	@FXML
	public void salir() {
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	protected void setTitulo(String titulo) {
		stage.setTitle(titulo);
	}
}
