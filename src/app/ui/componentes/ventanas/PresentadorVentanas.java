package app.ui.componentes.ventanas;

import javafx.stage.Window;

public class PresentadorVentanas {

	public VentanaConfirmacion presentarConfirmacion(String titulo, String mensaje, Window padre) {
		return new VentanaConfirmacion(titulo, mensaje, padre);
	}

	public VentanaConfirmacion presentarConfirmacion(String titulo, String mensaje) {
		return new VentanaConfirmacion(titulo, mensaje);
	}

	public VentanaError presentarError(String titulo, String mensaje, Window padre) {
		return new VentanaError(titulo, mensaje, padre);
	}

	public VentanaError presentarError(String titulo, String mensaje) {
		return new VentanaError(titulo, mensaje);
	}

	public VentanaInformacion presentarInformacion(String titulo, String mensaje, Window padre) {
		return new VentanaInformacion(titulo, mensaje, padre);
	}

	public VentanaInformacion presentarInformacion(String titulo, String mensaje) {
		return new VentanaInformacion(titulo, mensaje);
	}

	public VentanaErrorExcepcion presentarExcepcion(Exception e, Window w) {
		e.printStackTrace();
		return new VentanaErrorExcepcion(e.getMessage(), w);
	}

	public VentanaErrorExcepcion presentarExcepcion(Exception e) {
		e.printStackTrace();
		return new VentanaErrorExcepcion(e.getMessage());
	}

	public VentanaErrorExcepcionInesperada presentarExcepcionInesperada(Exception e, Window w) {
		System.err.println("Excepción inesperada!!");
		e.printStackTrace();
		return new VentanaErrorExcepcionInesperada(w);
	}

	public VentanaErrorExcepcionInesperada presentarExcepcionInesperada(Exception e) {
		System.err.println("Excepción inesperada!!");
		e.printStackTrace();
		return new VentanaErrorExcepcionInesperada();
	}
}
