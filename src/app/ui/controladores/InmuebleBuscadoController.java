package app.ui.controladores;

import java.net.URL;
import java.util.ResourceBundle;

import app.datos.entidades.InmuebleBuscado;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class InmuebleBuscadoController extends OlimpoController {

	@FXML
	private TextField textFieldSuperficie;
	@FXML
	private TextField textFieldAntiguedad;
	@FXML
	private TextField textFieldDormitorios;
	@FXML
	private TextField textFieldBaños;

	@FXML
	private CheckBox checkboxPropiedadHorizontal;
	@FXML
	private CheckBox checkboxGarage;
	@FXML
	private CheckBox checkboxPatio;
	@FXML
	private CheckBox checkboxPiscina;
	@FXML
	private CheckBox checkboxAguaCorriente;
	@FXML
	private CheckBox checkboxCloaca;
	@FXML
	private CheckBox checkboxGasNatural;
	@FXML
	private CheckBox checkboxAguaCaliente;
	@FXML
	private CheckBox checkboxTelefono;
	@FXML
	private CheckBox checkboxLavadero;
	@FXML
	private CheckBox checkboxPavimento;

	public void acceptAction() {
		Double supeficie = Double.valueOf(textFieldSuperficie.getText().trim());
		Integer antiguedad = Integer.valueOf(textFieldAntiguedad.getText().trim());
		Integer dormitorios = Integer.valueOf(textFieldDormitorios.getText().trim());
		Integer baños = Integer.valueOf(textFieldBaños.getText().trim());

		Boolean propiedadHorizontal = checkboxPropiedadHorizontal.isSelected();
		Boolean garage = checkboxGarage.isSelected();
		Boolean patio = checkboxPatio.isSelected();
		Boolean piscina = checkboxPiscina.isSelected();
		Boolean aguaCorriente = checkboxAguaCorriente.isSelected();
		Boolean cloaca = checkboxCloaca.isSelected();
		Boolean gasNatural = checkboxGasNatural.isSelected();
		Boolean aguaCaliente = checkboxAguaCaliente.isSelected();
		Boolean telefono = checkboxTelefono.isSelected();
		Boolean lavadero = checkboxLavadero.isSelected();
		Boolean pavimento = checkboxPavimento.isSelected();

		// TODO: falta precio y barrios

		InmuebleBuscado inmuebleBuscado = new InmuebleBuscado();
		inmuebleBuscado.setSuperficieMin(supeficie)
				.setAntiguedadMax(antiguedad)
				.setDormitoriosMin(dormitorios)
				.setBañosMin(baños)
				.setPropiedadHorizontal(propiedadHorizontal)
				.setGaraje(garage)
				.setPatio(patio)
				.setPiscina(piscina)
				.setAguaCaliente(aguaCaliente)
				.setAguaCorriente(aguaCorriente)
				.setCloacas(cloaca)
				.setGasNatural(gasNatural)
				.setTelefono(telefono)
				.setLavadero(lavadero)
				.setPavimento(pavimento);

	}

	public void cancelAction(ActionEvent event) {
		((Node) (event.getSource())).getScene().getWindow().hide();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

}
