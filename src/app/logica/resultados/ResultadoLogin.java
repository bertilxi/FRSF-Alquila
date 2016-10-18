package app.logica.resultados;

import app.logica.resultados.ResultadoLogin.ErrorResultadoLogin;;

public class ResultadoLogin extends Resultado<ErrorResultadoLogin> {

	public enum ErrorResultadoLogin {
		Datos_Incorrectos
	}

	public ResultadoLogin(ErrorResultadoLogin... errores) {
		super(errores);
	}
}
