package app.excepciones;

/**
 * Representa un error buscando un elemento sobre la base de datos
 *
 * @author
 */
public class ObjNotFoundException extends PersistenciaException {

    private static final long serialVersionUID = 1L;

    public ObjNotFoundException(String accion) {
        super("Error inesperado interactuando con la base de datos.\nNo se pudo encontrar el elemento que se desea " + accion + ".");
    }
}
