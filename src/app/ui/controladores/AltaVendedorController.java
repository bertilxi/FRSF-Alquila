package app.ui.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;


public class AltaVendedorController extends BaseController {


    @FXML
    TextField textFieldNombre;

    @FXML
    TextField textFieldApellido;
    @FXML
    TextField textFieldNumeroDocumento;
    @FXML
    TextField textFieldContraseña;
    @FXML
    TextField textFieldRepiteContraseña;
    @FXML
    ComboBox comboBoxTipoDocumento;
}
