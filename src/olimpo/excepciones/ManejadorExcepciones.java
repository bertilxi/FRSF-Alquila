package olimpo.excepciones;

import olimpo.ui.componentes.VentanaErrorExcepcion;
import javafx.stage.Window;

public abstract class ManejadorExcepciones {
	public static void presentarExcepcion(Exception e, Window w) {
		e.printStackTrace();
		new VentanaErrorExcepcion(e.getMessage(), w);
	}
}
