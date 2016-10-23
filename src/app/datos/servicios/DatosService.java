package app.datos.servicios;

import java.util.ArrayList;

import app.datos.entidades.Localidad;
import app.datos.entidades.Provincia;
import app.datos.entidades.TipoDocumento;
import app.datos.entidades.TipoInmueble;
import app.excepciones.PersistenciaException;

public interface DatosService {

	public ArrayList<Localidad> obtenerLocalidadesDe(Provincia provincia) throws PersistenciaException;

	public ArrayList<Provincia> obtenerProvincias() throws PersistenciaException;

	public ArrayList<TipoDocumento> obtenerTiposDeDocumento() throws PersistenciaException;

	public ArrayList<TipoInmueble> obtenerTiposDeInmueble() throws PersistenciaException;

}
