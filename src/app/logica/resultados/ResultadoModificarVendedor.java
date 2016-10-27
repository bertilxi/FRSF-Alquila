package app.logica.resultados;

import app.logica.resultados.ResultadoModificarVendedor.ErrorModificarVendedor;

public class ResultadoModificarVendedor extends Resultado<ErrorModificarVendedor>{

	public ResultadoModificarVendedor(ErrorModificarVendedor... errores) {
		super(errores);
	}

	public enum ErrorModificarVendedor {

		Formato_Nombre_Incorrecto,
		Formato_Apellido_Incorrecto,
		Formato_Documento_Incorrecto,
		Otro_Vendedor_Posee_Mismo_Documento_Y_Tipo
	}
}