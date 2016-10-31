package app.logica.resultados;

import app.logica.resultados.ResultadoEliminarVendedor.ErrorEliminarVendedor;

public class ResultadoEliminarVendedor extends Resultado<ErrorEliminarVendedor> {

	public ResultadoEliminarVendedor(ErrorEliminarVendedor... errores) {
		super(errores);
	}

	public enum ErrorEliminarVendedor {

		No_Existe_Vendedor
	}

}
