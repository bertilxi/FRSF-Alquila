package app.logica.resultados;

import app.logica.resultados.ResultadoCrearPropietario.ErrorResultadoCrearPropietario;

public class ResultadoCrearPropietario extends Resultado<ErrorResultadoCrearPropietario>{

	public ResultadoCrearPropietario(ErrorResultadoCrearPropietario... errores) {
		super(errores);
	}

	public enum ErrorResultadoCrearPropietario {

		Formato_Nombre_Incorrecto,
		Formato_Apellido_Incorrecto,
		Formato_Telefono_Incorrecto,
		Formato_Documento_Incorrecto,
		Formato_Email_Incorrecto,
		Formato_Direccion_Incorrecto,
		Ya_Existe_Propietario
	}

}
