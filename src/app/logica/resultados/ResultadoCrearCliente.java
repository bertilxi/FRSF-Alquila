package app.logica.resultados;

import app.logica.resultados.ResultadoCrearCliente.ErrorCrearCliente;

public class ResultadoCrearCliente extends Resultado<ErrorCrearCliente>{

	public ResultadoCrearCliente(ErrorCrearCliente... errores) {
		super(errores);
	}

	public enum ErrorCrearCliente {

		Formato_Nombre_Incorrecto,
		Formato_Apellido_Incorrecto,
		Formato_Telefono_Incorrecto,
		Formato_Documento_Incorrecto,
		Ya_Existe_Cliente
	}

}
