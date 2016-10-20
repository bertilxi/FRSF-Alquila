package app.ui.controladores;


import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.application.Application.launch;

public class AltaPropietarioController extends BaseController{
    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldApellido;
    @FXML
    private TextField textFieldNumeroDocumento;
    @FXML
    private TextField textFieldContraseña;
    @FXML
    private TextField textFieldRepetirContraseña;
    @FXML
    private TextField textFieldCalle;
    @FXML
    private TextField textFieldAlturaCalle;
    @FXML
    private TextField textFieldPiso;
    @FXML
    private TextField textFieldDepartamento;
    @FXML
    private TextField textFieldLocalidad;
    @FXML
    private TextField textFieldProvincia;
    @FXML
    private TextField textFieldTelefono;
    @FXML
    private TextField textFieldCorreoElectronico;

    @FXML
    private ComboBox comboBoxTipoDocumento;


}
