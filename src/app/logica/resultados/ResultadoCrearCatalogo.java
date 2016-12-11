package app.logica.resultados;

import app.logica.resultados.ResultadoCrearCatalogo.ErrorCrearCatalogo;

public class ResultadoCrearCatalogo extends Resultado<ErrorCrearCatalogo> {

	public ResultadoCrearCatalogo(ErrorCrearCatalogo... errores) {
		super(errores);
	}

	public enum ErrorCrearCatalogo {
		Cliente_inexistente,
		Codigo_Inmueble_Inexistente,
		Tipo_Inmueble_Inexistente,
		Localidad_Inmueble_Inexistente,
		Direccion_Inmueble_Inexistente,
		Barrio_Inmueble_Inexistente,
		Precio_Inmueble_Inexistente
	}
}
