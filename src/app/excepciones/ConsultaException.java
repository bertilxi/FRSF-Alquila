package app.excepciones;

/**
 * Representa un error en una petici�n de datos a la base de datos
 *
 * @author Acosta - Gioria - Moretti - Rebechi
 *
 */
public class ConsultaException extends olimpo.excepciones.PersistenciaException {

	private static final long serialVersionUID = 1L;

	public ConsultaException() {
		super("Error inesperado interactuando con la base de datos.\nNo se pudo obtener los datos deseados");
	}
}
