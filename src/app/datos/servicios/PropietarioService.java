package app.datos.servicios;

import app.datos.entidades.Propietario;
import app.datos.entidades.TipoDocumento;
import app.excepciones.PersistenciaException;

public interface PropietarioService {

    public void guardarPropietario(Propietario propietario) throws PersistenciaException;

    public void modificarPropietario(Propietario propietario) throws PersistenciaException;

    public Propietario obtenerPropietario(TipoDocumento tipoDocumento, Integer documento) throws PersistenciaException;
}