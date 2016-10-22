package app.datos.servicios;

import java.util.ArrayList;

import app.datos.entidades.Localidad;
import app.datos.entidades.Provincia;
import app.datos.entidades.TipoDocumento;
import app.datos.entidades.TipoInmueble;

public interface ServiceDatos {

	public ArrayList<Localidad> obtenerLocalidadesDe(Provincia provincia);
	public ArrayList<Provincia> obtenerProvincias();
	public ArrayList<TipoDocumento> obtenerTiposDeDocumento();
	public ArrayList<TipoInmueble> obtenerTiposDeInmueble();
}
