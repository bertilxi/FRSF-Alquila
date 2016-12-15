package app.excepciones;

public class ImprimirPDFException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public ImprimirPDFException(Throwable e) {
		super("No se pudo imprimir el pdf deseado.", e);
	}

}
