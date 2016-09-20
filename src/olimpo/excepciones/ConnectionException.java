package olimpo.excepciones;

/**
 * Representa un error de conexi�n con la base de datos
 *
 * @author Acosta - Gioria - Moretti - Rebechi
 *
 */
public class ConnectionException extends olimpo.excepciones.PersistenciaException {

	private static final long serialVersionUID = 1L;

	public ConnectionException() {
		super("No se pudo conectar con la base de datos.");
	}
}
