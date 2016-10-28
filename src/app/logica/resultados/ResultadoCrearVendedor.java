package app.logica.resultados;

import app.logica.resultados.ResultadoCrearVendedor.ErrorCrearVendedor;

public class ResultadoCrearVendedor extends Resultado<ErrorCrearVendedor>{

	public ResultadoCrearVendedor(ErrorCrearVendedor... errores) {
		super(errores);
	}

	public enum ErrorCrearVendedor {

		Formato_Nombre_Incorrecto,
		Formato_Apellido_Incorrecto,
		Formato_Documento_Incorrecto,
		Ya_Existe_Vendedor
	}
}