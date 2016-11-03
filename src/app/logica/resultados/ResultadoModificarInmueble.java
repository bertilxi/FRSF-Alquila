package app.logica.resultados;

import app.logica.resultados.ResultadoModificarInmueble.ErrorModificarInmueble;

public class ResultadoModificarInmueble extends Resultado<ErrorModificarInmueble> {

	public ResultadoModificarInmueble(ErrorModificarInmueble... errores) {
		super(errores);
	}

	public enum ErrorModificarInmueble {

	}
}