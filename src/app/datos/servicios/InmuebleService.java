package app.datos.servicios;

import app.datos.entidades.Inmueble;
import app.excepciones.PersistenciaException;

public interface InmuebleService {

    public void guardarInmueble(Inmueble inmueble) throws PersistenciaException;

    public void modificarInmueble(Inmueble inmueble) throws PersistenciaException;
}