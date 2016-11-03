package app.datos.servicios;

import java.util.ArrayList;

import app.datos.entidades.Inmueble;
import app.excepciones.PersistenciaException;

public interface InmuebleService {

	public void guardarInmueble(Inmueble inmueble) throws PersistenciaException;

	public void modificarInmueble(Inmueble inmueble) throws PersistenciaException;

	ArrayList<Inmueble> listarInmuebles() throws PersistenciaException;

}