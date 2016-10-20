package app.ui.controladores;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controlador base que se encarga de manejar la barra de titulos y la barra lateral
 */
public class BaseController extends WindowTitleController {


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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        toggleButtonAyuda.setToggleGroup(toggleGroupSidebar);
        toggleButtonClientes.setToggleGroup(toggleGroupSidebar);
        toggleButtonInmuebles.setToggleGroup(toggleGroupSidebar);
        toggleButtonPropietarios.setToggleGroup(toggleGroupSidebar);
        toggleButtonVendedores.setToggleGroup(toggleGroupSidebar);

    }
}
