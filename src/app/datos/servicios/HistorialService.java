package app.datos.servicios;

import app.datos.entidades.HistorialInmueble;
import app.excepciones.PersistenciaException;

public interface HistorialService {

	public void guardarHistorialInmueble(HistorialInmueble historialInmueble) throws PersistenciaException;

}
