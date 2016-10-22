package app.ui.controladores.resultado;

import app.ui.controladores.resultado.ResultadoControlador.ErrorControlador;

public class ResultadoControlador extends Resultado<ErrorControlador> {

	public ResultadoControlador(ErrorControlador... errores) {
		super(errores);
	}

	public enum ErrorControlador {
		Datos_Incorrectos, Campos_Vacios, Error_Persistencia, Error_Desconocido
	}
}