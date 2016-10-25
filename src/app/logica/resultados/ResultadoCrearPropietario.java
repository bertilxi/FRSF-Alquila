package app.logica.resultados;

import app.logica.resultados.ResultadoCrearPropietario.ErrorCrearPropietario;

public class ResultadoCrearPropietario extends Resultado<ErrorCrearPropietario> {

	public ResultadoCrearPropietario(ErrorCrearPropietario... errores) {
		super(errores);
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	public enum ErrorCrearPropietario {

		Formato_Nombre_Incorrecto,
		Formato_Apellido_Incorrecto,
		Formato_Telefono_Incorrecto,
		Formato_Documento_Incorrecto,
		Formato_Email_Incorrecto,
		Formato_Direccion_Incorrecto,
		Ya_Existe_Propietario
	}

}
