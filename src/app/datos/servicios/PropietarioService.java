package app.datos.servicios;

import app.datos.clases.FiltroPropietario;
import app.datos.entidades.Propietario;
import app.excepciones.PersistenciaException;

public interface PropietarioService {

	public void guardarPropietario(Propietario propietario) throws PersistenciaException;

	public void modificarPropietario(Propietario propietario) throws PersistenciaException;

	public Propietario obtenerPropietario(FiltroPropietario filtro) throws PersistenciaException;

}