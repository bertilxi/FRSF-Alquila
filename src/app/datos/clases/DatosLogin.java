package app.datos.clases;

import app.datos.entidades.TipoDocumento;

public class DatosLogin {

	private TipoDocumento tipoDocumento;

	private String dni;

	private char[] contrasenia;

	public DatosLogin(TipoDocumento tipoDocumento, String dni, char[] contrasenia) {
		this.tipoDocumento = tipoDocumento;
		this.dni = dni;
		this.contrasenia = contrasenia;
	}

	public String getDNI() {
		return dni;
	}

	public char[] getContrasenia() {
		return contrasenia;
	}

	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}
}
