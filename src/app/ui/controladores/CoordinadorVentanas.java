package app.ui.controladores;

import org.springframework.stereotype.Service;

import app.logica.CoordinadorJavaFX;
import app.ui.componentes.ventanas.PresentadorVentanas;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

@Service
public class CoordinadorVentanas {

	private Stage stage;

	private PresentadorVentanas presentadorVentanas;

	private CoordinadorJavaFX coordinador;

	private Pane rootLayout;

	public CoordinadorVentanas(Stage stage, PresentadorVentanas presentadorVentanas, CoordinadorJavaFX coordinador, Pane rootLayout) {
		this.stage = stage;
		this.presentadorVentanas = presentadorVentanas;
		this.coordinador = coordinador;
		this.rootLayout = rootLayout;
	}

	public OlimpoController cambiarScene(String URLVistaACambiar) {
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource(URLVistaACambiar));
			Pane nuevaVista = (Pane) loader.load();

			rootLayout.getChildren().clear();
			rootLayout.getChildren().add(nuevaVista);

			OlimpoController controller = loader.getController();
			controller.setCoordinadorVentanas(this);
			controller.setStage(stage);
			controller.setCoordinador(coordinador);
			controller.setPresentador(presentadorVentanas);
			return controller;
		} catch(Exception e){
			presentadorVentanas.presentarExcepcionInesperada(e, stage);
			return null;
		}
	}

	public void setRootLayout(Parent root) {
		this.rootLayout = (BorderPane) root;
	}
}
