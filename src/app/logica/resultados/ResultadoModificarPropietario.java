package app.logica.resultados;

import app.logica.resultados.ResultadoModificarPropietario.ErrorResultadoModificarPropietario;

public class ResultadoModificarPropietario extends Resultado<ErrorResultadoModificarPropietario> {

	public ResultadoModificarPropietario(ErrorResultadoModificarPropietario... errores) {
		super(errores);
	}

	public enum ErrorResultadoModificarPropietario {

		Formato_Nombre_Incorrecto,
		Formato_Apellido_Incorrecto,
		Formato_Telefono_Incorrecto,
		Formato_Documento_Incorrecto,
		Formato_Email_Incorrecto,
		Formato_Direccion_Incorrecto,
		Ya_Existe_Propietario_Con_Mismo_Documento_y_Tipo
	}
}