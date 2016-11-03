package app.ui.controladores;

import javax.annotation.Resource;

import app.logica.CoordinadorJavaFX;
import app.ui.componentes.ventanas.PresentadorVentanas;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public abstract class OlimpoController implements Initializable {

	protected Stage stage;
	protected CoordinadorJavaFX coordinador;
	protected PresentadorVentanas presentador;
	protected CoordinadorVentanas coordinadorVentanas;

	@Resource
	protected CoordinadorJavaFX coordinadorJavaFX;

	public void setCoordinadorVentanas(CoordinadorVentanas coordinadorVentanas) {
		this.coordinadorVentanas = coordinadorVentanas;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void setCoordinador(CoordinadorJavaFX coordinador) {
		this.coordinador = coordinador;
	}

	public void setPresentador(PresentadorVentanas presentador) {
		this.presentador = presentador;
	}

	@FXML
	public void salir() {
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}
}
