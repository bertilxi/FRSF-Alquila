package app.ui.controladores;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by fer on 10/10/16.
 */
public abstract class baseController implements Initializable{


    @FXML
    private ToggleButton closeButton;
    @FXML
    private ToggleButton maximizeButton;
    @FXML
    private ToggleButton minimizeButton;


    @FXML
    private void exitPlatform(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void minimizePlatform(ActionEvent event) {
        Stage stage = (Stage) minimizeButton.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void maximizePlatform(ActionEvent event) {
        Stage stage = (Stage) maximizeButton.getScene().getWindow();

        if (stage.isMaximized()) {
            stage.setMaximized(false);
        } else {
            stage.setMaximized(true);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
