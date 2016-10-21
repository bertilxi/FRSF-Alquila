package app.datos.servicios;

import java.util.ArrayList;

import app.datos.entidades.Localidad;
import app.datos.entidades.Provincia;

public interface ServiceDatos {

	public ArrayList<Localidad> obtenerLocalidadesDe(Provincia provincia);
	public ArrayList<Provincia> obtenerProvincias();
}
