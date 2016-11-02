package app.ui.componentes.ventanas;

import javafx.stage.Window;

public class PresentadorVentanasMock extends PresentadorVentanas {

	Boolean acepta;

	public PresentadorVentanasMock() {
		// TODO Auto-generated constructor stub
	}

	public PresentadorVentanasMock(Boolean acepta) {
		this.acepta = acepta;
	}

	@Override
	public VentanaConfirmacion presentarConfirmacion(String titulo, String mensaje, Window padre) {
		return new VentanaConfirmacion(titulo, mensaje, padre) {
			{

			}

			@Override
			public Boolean acepta() {
				return acepta;
			}
		};
	}

	@Override
	public VentanaConfirmacion presentarConfirmacion(String titulo, String mensaje) {
		return new VentanaConfirmacion(titulo, mensaje) {
			{

			}

			@Override
			public Boolean acepta() {
				return acepta;
			}
		};
	}

	@Override
	public VentanaError presentarError(String titulo, String mensaje, Window padre) {
		return null;
	}

	@Override
	public VentanaError presentarError(String titulo, String mensaje) {
		return null;
	}

	@Override
	public VentanaInformacion presentarInformacion(String titulo, String mensaje, Window padre) {
		return null;
	}

	@Override
	public VentanaInformacion presentarInformacion(String titulo, String mensaje) {
		return null;
	}

	@Override
	public VentanaErrorExcepcion presentarExcepcion(Exception e, Window w) {
		return null;
	}

	@Override
	public VentanaErrorExcepcion presentarExcepcion(Exception e) {
		return null;
	}

	@Override
	public VentanaErrorExcepcionInesperada presentarExcepcionInesperada(Exception e, Window w) {
		return null;
	}

	@Override
	public VentanaErrorExcepcionInesperada presentarExcepcionInesperada(Exception e) {
		return null;
	}
}
