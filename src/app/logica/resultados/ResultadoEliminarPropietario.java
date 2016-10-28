package app.logica.resultados;

import app.logica.resultados.ResultadoEliminarPropietario.ErrorEliminarPropietario;

public class ResultadoEliminarPropietario extends Resultado<ErrorEliminarPropietario> {

	public ResultadoEliminarPropietario(ErrorEliminarPropietario... errores) {
		super(errores);
	}

	public enum ErrorEliminarPropietario {

		Formato_Documento_Incorrecto,
		No_Existe_Propietario
	}

}
