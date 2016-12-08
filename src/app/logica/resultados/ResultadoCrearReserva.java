package app.logica.resultados;

import app.logica.resultados.ResultadoCrearReserva.ErrorCrearReserva;

public class ResultadoCrearReserva extends Resultado<ErrorCrearReserva> {

	public ResultadoCrearReserva(ErrorCrearReserva... errores) {
		super(errores);
	}

	public enum ErrorCrearReserva {

		Formato_Importe_Incorrecto,
		Ya_Existe_Reserva_Entre_Las_Fechas
	}
}
