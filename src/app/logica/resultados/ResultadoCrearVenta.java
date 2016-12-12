package app.logica.resultados;

import app.logica.resultados.ResultadoCrearVenta.ErrorCrearVenta;

public class ResultadoCrearVenta extends Resultado<ErrorCrearVenta> {

	public ResultadoCrearVenta(ErrorCrearVenta... errores) {
		super(errores);
	}

	public enum ErrorCrearVenta {

		Cliente_Igual_A_Propietario,
		Inmueble_Reservado_Por_Otro_Cliente,
		Formato_Medio_De_Pago_Incorrecto,
		Vendedor_Vacío,
		Cliente_Vacío,
		Propietario_Vacío,
		Inmueble_Vacío,
		Importe_vacío,
		Medio_De_Pago_Vacío
	}
}
