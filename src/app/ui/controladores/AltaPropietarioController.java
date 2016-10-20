package app.ui.controladores;


import app.datos.clases.TipoDocumentoStr;
import app.datos.entidades.Calle;
import app.datos.entidades.Direccion;
import app.datos.entidades.Propietario;
import app.datos.entidades.TipoDocumento;
import javafx.application.Application;
import javafx.event.ActionEvent;
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
    private ComboBox<TipoDocumentoStr> comboBoxTipoDocumento;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);


    }

    @FXML
    public void onClickButtonAccept(){
        String nombre = textFieldNombre.toString().trim();
        String apellido = textFieldApellido.toString().trim();
        String numeroDocumento = textFieldNumeroDocumento.toString().trim();
        String calle = textFieldCalle.toString().trim();
        String alturaCalle = textFieldAlturaCalle.toString().trim();
        String piso = textFieldPiso.toString().trim();
        String departamento = textFieldDepartamento.toString().trim();
        String localidad = textFieldLocalidad.toString().trim();
        String provincia = textFieldProvincia.toString().trim();
        String telefono = textFieldTelefono.toString().trim();
        String correoElectronico = textFieldCorreoElectronico.toString().trim();

        //Direccion direccion = new Direccion(null, alturaCalle, new Calle(null,calle), )

        TipoDocumento tipoDocumento = new TipoDocumento(null,comboBoxTipoDocumento.getValue());
        Propietario propietario = new Propietario(null, nombre, apellido, numeroDocumento, calle, alturaCalle )
    }
}
