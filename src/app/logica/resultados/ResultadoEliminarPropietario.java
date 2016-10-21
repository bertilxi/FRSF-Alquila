package app.logica.resultados;

import app.logica.resultados.ResultadoEliminarPropietario.ErrorResultadoEliminarPropietario;

public class ResultadoEliminarPropietario extends Resultado<ErrorResultadoEliminarPropietario> {

	public ResultadoEliminarPropietario(ErrorResultadoEliminarPropietario... errores) {
		super(errores);
	}

	public enum ErrorResultadoEliminarPropietario {

		No_Existe_Propietario_En_BD
	}
}
