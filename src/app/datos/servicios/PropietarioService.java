
package app.datos.servicios;

import java.util.ArrayList;

import app.datos.clases.FiltroPropietario;
import app.datos.entidades.Propietario;
import app.excepciones.PersistenciaException;

public interface PropietarioService {

	public void guardarPropietario(Propietario propietario) throws PersistenciaException;

	public void modificarPropietario(Propietario propietario) throws PersistenciaException;

	public Propietario obtenerPropietario(FiltroPropietario filtro) throws PersistenciaException;

	public ArrayList<Propietario> listarPropietarios() throws PersistenciaException;
}