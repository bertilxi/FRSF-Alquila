package app.excepciones;

public class EntidadExistenteConEstadoBajaException extends GestionException{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public EntidadExistenteConEstadoBajaException() {
		super("La entidad que se intenta crear ya existe, pero está dada de baja.");
	}

}
