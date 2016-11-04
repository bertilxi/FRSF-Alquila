package app.ui;

import org.springframework.stereotype.Service;

import app.logica.CoordinadorJavaFX;
import app.ui.componentes.ventanas.PresentadorVentanas;
import app.ui.controladores.OlimpoController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

@Service
public class ScenographyChanger {

	private Stage stage;

	private PresentadorVentanas presentadorVentanas;

	private CoordinadorJavaFX coordinador;

	private Pane background;

	public ScenographyChanger(Stage stage, PresentadorVentanas presentadorVentanas, CoordinadorJavaFX coordinador, Pane background) {
		this.stage = stage;
		this.presentadorVentanas = presentadorVentanas;
		this.coordinador = coordinador;
		this.background = background;
	}

	public OlimpoController cambiarScenography(String URLVistaACambiar) {
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource(URLVistaACambiar));
			Pane newScenography = (Pane) loader.load();

			background.getChildren().clear();
			background.getChildren().add(newScenography);

			stage.sizeToScene();
			newScenography.minHeightProperty().bind(background.heightProperty());

			OlimpoController controller = loader.getController();

			controller.setScenographyChanger(this).setStage(stage).setCoordinador(coordinador).setPresentador(presentadorVentanas);

			return controller;
		} catch(Exception e){
			presentadorVentanas.presentarExcepcionInesperada(e, stage);
			return null;
		}
	}
}
