package app.logica.resultados;

import app.logica.resultados.ResultadoEliminarCliente.ErrorEliminarCliente;

public class ResultadoEliminarCliente extends Resultado<ErrorEliminarCliente> {

	public ResultadoEliminarCliente(ErrorEliminarCliente... errores) {
		super(errores);
	}

	public enum ErrorEliminarCliente {

		No_Existe_Cliente
	}

}
