package app.logica.resultados;

import app.logica.resultados.ResultadoEliminarInmueble.ErrorEliminarInmueble;

public class ResultadoEliminarInmueble extends Resultado<ErrorEliminarInmueble> {

	public ResultadoEliminarInmueble(ErrorEliminarInmueble... errores) {
		super(errores);
	}

	public enum ErrorEliminarInmueble {

	}

}
