package app.logica.resultados;

import app.logica.resultados.ResultadoModificarCliente.ErrorModificarCliente;

public class ResultadoModificarCliente extends Resultado<ErrorModificarCliente>{

	public ResultadoModificarCliente(ErrorModificarCliente... errores) {
		super(errores);
	}

	public enum ErrorModificarCliente {

		Formato_Nombre_Incorrecto,
		Formato_Apellido_Incorrecto,
		Formato_Telefono_Incorrecto,
		Formato_Documento_Incorrecto,
		Otro_Cliente_Posee_Mismo_Documento_Y_Tipo
	}
}