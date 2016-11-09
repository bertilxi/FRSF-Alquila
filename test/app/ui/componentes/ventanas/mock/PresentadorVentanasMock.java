package app.ui.componentes.ventanas.mock;

import app.ui.componentes.ventanas.PresentadorVentanas;
import app.ui.componentes.ventanas.VentanaConfirmacion;
import app.ui.componentes.ventanas.VentanaError;
import app.ui.componentes.ventanas.VentanaErrorExcepcion;
import app.ui.componentes.ventanas.VentanaErrorExcepcionInesperada;
import app.ui.componentes.ventanas.VentanaEsperaBaseDeDatos;
import app.ui.componentes.ventanas.VentanaInformacion;
import javafx.stage.Window;

public class PresentadorVentanasMock extends PresentadorVentanas {

	Boolean acepta;

	public PresentadorVentanasMock() {

	}

	public PresentadorVentanasMock(Boolean acepta) {
		this.acepta = acepta;
	}

	@Override
	public VentanaConfirmacion presentarConfirmacion(String titulo, String mensaje, Window padre) {
		return new VentanaConfirmacion(titulo, mensaje) {
			@Override
			public Boolean acepta() {
				return acepta;
			}
		};
	}

	@Override
	public VentanaConfirmacion presentarConfirmacion(String titulo, String mensaje) {
		return new VentanaConfirmacion(titulo, mensaje) {
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

	@Override
	public VentanaEsperaBaseDeDatos presentarEsperaBaseDeDatos(Window w) {
		return null;
	}

	@Override
	public void presentarToast(String mensaje, Window padre) {

	}

	@Override
	public void presentarToast(String mensaje, Window padre, int ajusteHeight) {

	}
}
