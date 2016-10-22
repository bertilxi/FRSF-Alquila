package app.logica.resultados;

import app.logica.resultados.ResultadoModificarPropietario.ErrorModificarPropietario;

public class ResultadoModificarPropietario extends Resultado<ErrorModificarPropietario> {

	public ResultadoModificarPropietario(ErrorModificarPropietario... errores) {
		super(errores);
	}

	public enum ErrorModificarPropietario {

		Formato_Nombre_Incorrecto,
		Formato_Apellido_Incorrecto,
		Formato_Telefono_Incorrecto,
		Formato_Documento_Incorrecto,
		Formato_Email_Incorrecto,
		Formato_Direccion_Incorrecto,
		Ya_Existe_Propietario
	}
}
