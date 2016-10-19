package app.excepciones;

import app.ui.componentes.VentanaErrorExcepcion;
import app.ui.componentes.VentanaErrorExcepcionInesperada;
import javafx.stage.Window;

public abstract class ManejadorExcepciones {
    public static void presentarExcepcion(Exception e, Window w) {
        e.printStackTrace();
        new VentanaErrorExcepcion(e.getMessage(), w);
    }

    public static void presentarExcepcionInesperada(Exception e, Window w) {
        System.err.println("Excepción inesperada!!");
        e.printStackTrace();
        new VentanaErrorExcepcionInesperada(w);
    }
}
