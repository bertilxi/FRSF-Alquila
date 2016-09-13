package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class SampleController implements Initializable{
	@FXML
	private TextField textField1;
	@FXML
	private TextField textField2;

	private static final String EMAIL_PATTERN =
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String PASSWORD_PATTERN =
			"(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{5,10}";

	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {

	}

	public Boolean handleClickBUtton1(){
		Boolean logged = true;
		String user = textField1.getText();
		String password = textField2.getText();

		if (!Pattern.matches(EMAIL_PATTERN, user)) {
			logged = false;
		}
		if (!Pattern.matches(PASSWORD_PATTERN, password)) {
			logged = false;
		}
		if(!logged){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Information Dialog");
			alert.setHeaderText(null);
			alert.setContentText("I have a great message for you!");
			alert.showAndWait();
		}



		return logged;

	}
}
