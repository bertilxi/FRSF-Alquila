package app.logica.resultados;

import app.logica.resultados.ResultadoAutenticacion.ErrorAutenticacion;

public class ResultadoAutenticacion extends Resultado<ErrorAutenticacion> {

	public ResultadoAutenticacion(ErrorAutenticacion... errores) {
		super(errores);
	}

	public enum ErrorAutenticacion {
		Datos_Incorrectos
	}
}
