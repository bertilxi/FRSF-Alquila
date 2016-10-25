package app.logica.gestores;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import app.datos.entidades.Localidad;
import app.datos.entidades.Provincia;
import app.datos.entidades.TipoDocumento;
import app.datos.entidades.TipoInmueble;
import app.datos.servicios.DatosService;
import app.excepciones.PersistenciaException;

@Service
public class GestorDatos {

	@Resource
	private DatosService persistidorDatos;

	public ArrayList<TipoDocumento> obtenerTiposDeDocumento() throws PersistenciaException{
		return persistidorDatos.obtenerTiposDeDocumento();
	}

	public ArrayList<TipoInmueble> obtenerTiposInmueble() throws PersistenciaException {
		return persistidorDatos.obtenerTiposDeInmueble();
	}

	public ArrayList<Provincia> obtenerProvincias() throws PersistenciaException {
		return persistidorDatos.obtenerProvincias();
	}

	public ArrayList<Localidad> obtenerLocalidadesDe(Provincia provincia) throws PersistenciaException {
		return persistidorDatos.obtenerLocalidadesDe(provincia);
	}
}
