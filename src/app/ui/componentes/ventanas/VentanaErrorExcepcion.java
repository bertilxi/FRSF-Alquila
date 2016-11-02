package app.ui.componentes.ventanas;

import javafx.scene.control.Alert;
import javafx.stage.Window;

/**
 * Representa una ventana que muestra un mensaje de error
 *
 * @author Acosta - Gioria - Moretti - Rebechi
 */
public class VentanaErrorExcepcion extends Alert {

	/**
	 * Constructor. Genera parte de la ventana
	 */
	protected VentanaErrorExcepcion(AlertType alertType) {
		super(alertType);
	}

	/**
	 * Constructor. Genera la ventana
	 *
	 * @param mensaje
	 *            mensaje a mostrar en la ventana
	 */
	protected VentanaErrorExcepcion(String mensaje) {
		this(mensaje, null);
	}

	/**
	 * Constructor. Genera la ventana
	 *
	 * @param mensaje
	 *            mensaje a mostrar en la ventana
	 * @param padre
	 *            ventana en la que se mostrar� este di�logo
	 */
	protected VentanaErrorExcepcion(String mensaje, Window padre) {
		super(AlertType.ERROR);
		if(padre != null){
			this.initOwner(padre);
		}
		this.setContentText(mensaje);
		this.setHeaderText(null);
		this.setTitle("Error");
		this.showAndWait();
	}
}
