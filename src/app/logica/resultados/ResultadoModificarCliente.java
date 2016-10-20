package app.logica.resultados;

import app.logica.resultados.ResultadoModificarCliente.ErrorResultadoModificarCliente;

public class ResultadoModificarCliente extends Resultado<ErrorResultadoModificarCliente>{

	public ResultadoModificarCliente(ErrorResultadoModificarCliente... errores) {
		super(errores);
	}

	public enum ErrorResultadoModificarCliente {

		Formato_Nombre_Incorrecto,
		Formato_Apellido_Incorrecto,
		Formato_Telefono_Incorrecto,
		Formato_Documento_Incorrecto,
		Otro_Cliente_Posee_Mismo_Documento_Y_Tipo
	}
}