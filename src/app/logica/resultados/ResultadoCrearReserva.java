package app.logica.resultados;

import app.logica.resultados.ResultadoCrearReserva.ErrorCrearReserva;

public class ResultadoCrearReserva extends Resultado<ErrorCrearReserva> {

	public ResultadoCrearReserva(ErrorCrearReserva... errores) {
		super(errores);
	}

	public enum ErrorCrearReserva {

		Formato_Importe_Incorrecto,
		Ya_Existe_Reserva_Entre_Las_Fechas,
		Cliente_Vacío,
		Nombre_Cliente_Vacío,
		Apellido_Cliente_Vacío,
		TipoDocumento_Cliente_Vacío,
		NúmeroDocumento_Cliente_Vacío,
		Propietario_Vacío,
		Nombre_Propietario_Vacío,
		Apellido_Propietario_Vacío,
		Inmueble_Vacío,
		Tipo_Inmueble_Vacío,
		Dirección_Inmueble_Vacía,
		Localidad_Inmueble_Vacía,
		Barrio_Inmueble_Vacío,
		Calle_Inmueble_Vacía,
		Altura_Inmueble_Vacía,
		FechaInicio_vacía,
		FechaFin_vacía,
		Importe_vacío
	}
}
