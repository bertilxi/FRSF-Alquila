package app.logica.resultados;

import app.logica.resultados.ResultadoCrearCliente.ErrorResultadoCrearCliente;

public class ResultadoCrearCliente extends Resultado<ErrorResultadoCrearCliente>{

	public ResultadoCrearCliente(ErrorResultadoCrearCliente... errores) {
		super(errores);
	}

	public enum ErrorResultadoCrearCliente {

		Formato_Nombre_Incorrecto,
		Formato_Apellido_Incorrecto,
		Formato_Telefono_Incorrecto,
		Formato_Documento_Incorrecto,
		Ya_Existe_Cliente
	}
}
