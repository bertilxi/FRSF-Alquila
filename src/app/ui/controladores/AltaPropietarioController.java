package app.ui.controladores;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import app.datos.entidades.*;
import app.logica.resultados.ResultadoCrearPropietario;
import app.ui.componentes.VentanaError;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class AltaPropietarioController extends BaseController {

    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldApellido;
    @FXML
    private TextField textFieldNumeroDocumento;
    @FXML
    private TextField textFieldAlturaCalle;
    @FXML
    private TextField textFieldPiso;
    @FXML
    private TextField textFieldDepartamento;
    @FXML
    private TextField textFieldTelefono;
    @FXML
    private TextField textFieldCorreoElectronico;
    @FXML
    private ComboBox<TipoDocumento> comboBoxTipoDocumento;
    @FXML
    private ComboBox<Provincia> comboBoxProvincia;
    @FXML
    private ComboBox<Localidad> comboBoxLocalidad;
    @FXML
    private ComboBox<Calle> comboBoxCalle;
    @FXML
    private ComboBox<Barrio> comboBoxBarrio;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

    }

    public ResultadoCrearPropietario acceptAction() {

        StringBuilder error = new StringBuilder("");

        String nombre = textFieldNombre.toString().trim();
        String apellido = textFieldApellido.toString().trim();
        String numeroDocumento = textFieldNumeroDocumento.toString().trim();
        String alturaCalle = textFieldAlturaCalle.toString().trim();
        String piso = textFieldPiso.toString().trim();
        String departamento = textFieldDepartamento.toString().trim();
        String telefono = textFieldTelefono.toString().trim();
        String correoElectronico = textFieldCorreoElectronico.toString().trim();

        Provincia provincia = comboBoxProvincia.getValue();
        Localidad localidad = comboBoxLocalidad.getValue();
        TipoDocumento tipoDoc = comboBoxTipoDocumento.getValue();
        Barrio barrio = comboBoxBarrio.getValue();
        Calle calle = comboBoxCalle.getValue();

        if (nombre.isEmpty()) {
            error.append("Inserte un nombre").append("\r\n ");
        }
        if (apellido.isEmpty()) {
            error.append("Inserte un apellido").append("\r\n ");
        }
        if (numeroDocumento.isEmpty()) {
            error.append("Inserte un numero de documento").append("\r\n ");
        }
        if (alturaCalle.isEmpty()) {
            error.append("Inserte una altura").append("\r\n ");
        }
        if (telefono.isEmpty()) {
            error.append("Inserte un telefono").append("\r\n ");
        }

        if (!error.toString().isEmpty()) {
            VentanaError ventanaError = new VentanaError("Revise sus campos", error.toString());
        } else {

            Direccion direccion = new Direccion();
            direccion.setId(null)
                    .setNumero(alturaCalle)
                    .setCalle(calle)
                    .setBarrio(barrio)
                    .setPiso(piso)
                    .setDepartamento(departamento)
                    .setLocalidad(localidad);

            Propietario propietario = new Propietario();
            propietario.setId(null)
                    .setNombre(nombre)
                    .setApellido(apellido)
                    .setTipoDocumento(tipoDoc)
                    .setNumeroDocumento(numeroDocumento)
                    .setTelefono(telefono)
                    .setEmail(correoElectronico)
                    .setDireccion(direccion)
                    .setInmuebles(null);

        }
        return null;
    }
}
