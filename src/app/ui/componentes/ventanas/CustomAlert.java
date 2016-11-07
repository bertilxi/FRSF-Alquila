package app.ui.componentes.ventanas;

import app.ui.componentes.StyleCSS;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public abstract class CustomAlert extends Alert {

	public CustomAlert(AlertType alertType, String contentText, ButtonType[] buttons) {
		super(alertType, contentText, buttons);
		this.getDialogPane().getStylesheets().add(new StyleCSS().getStyle());
	}

	public CustomAlert(AlertType alertType) {
		super(alertType);
		this.getDialogPane().getStylesheets().add(new StyleCSS().getStyle());
	}

}
