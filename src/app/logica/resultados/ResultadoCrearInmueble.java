package app.logica.resultados;

import app.logica.resultados.ResultadoCrearInmueble.ErrorCrearInmueble;

public class ResultadoCrearInmueble extends Resultado<ErrorCrearInmueble> {

	public ResultadoCrearInmueble(ErrorCrearInmueble... errores) {
		super(errores);
	}

	public enum ErrorCrearInmueble {

	}

}
