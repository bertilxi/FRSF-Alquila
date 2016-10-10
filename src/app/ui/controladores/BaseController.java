package app.ui.controladores;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controlador base que se encarga de manejar la barra de titulos y la barra lateral
 */
public class BaseController implements Initializable{

    private static double xOffset = 0;
    private static double yOffset = 0;

    @FXML
    private ToggleButton toggleButtonClientes;
    @FXML
    private ToggleButton toggleButtonInmuebles;
    @FXML
    private ToggleButton toggleButtonVendedores;
    @FXML
    private ToggleButton toggleButtonPropietarios;
    @FXML
    private ToggleButton toggleButtonAyuda;

    private ToggleGroup toggleGroupSidebar = new ToggleGroup();


    @FXML
    private HBox titlebar;
    private Stage stage;

    public void controlerPassing(Stage stage){
        this.stage = stage;
    }

    @FXML
    private void exitPlatform(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void minimizePlatform(ActionEvent event) {
        stage.setIconified(true);
    }

    @FXML
    private void maximizePlatform(ActionEvent event) {

        if (stage.isMaximized()) {
            stage.setMaximized(false);
        } else {
            stage.setMaximized(true);
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // como se pierden las propiedades del sistema por no tener barra de titulo
        // se implementan dos handlers que manejan el movimiento de arrastre de la ventana
        // se pierden mas propiedades que por el momento ignoro
        titlebar.setOnMousePressed(event -> {
            xOffset = stage.getX() - event.getScreenX();
            yOffset = stage.getY() - event.getScreenY();
        });

        titlebar.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() + xOffset);
            stage.setY(event.getScreenY() + yOffset);
        });

        toggleButtonAyuda.setToggleGroup(toggleGroupSidebar);
        toggleButtonClientes.setToggleGroup(toggleGroupSidebar);
        toggleButtonInmuebles.setToggleGroup(toggleGroupSidebar);
        toggleButtonPropietarios.setToggleGroup(toggleGroupSidebar);
        toggleButtonVendedores.setToggleGroup(toggleGroupSidebar);
        
    }
}
