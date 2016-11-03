package app.excepciones;

/**
 * Representa un error en la gesti�n de datos
 *
 * @author Acosta - Gioria - Moretti - Rebechi
 */
public class GestionException extends Exception {

    private static final long serialVersionUID = 1L;

    public GestionException(String msg) {
        super(msg);
    }
}
