package app.logica.gestores;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import app.datos.entidades.Localidad;
import app.datos.entidades.Pais;
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

	public ArrayList<Provincia> obtenerProvinciasDe(Pais pais) throws PersistenciaException {
		return persistidorDatos.obtenerProvinciasDe(pais);
	}

	public ArrayList<Localidad> obtenerLocalidadesDe(Provincia prov) throws PersistenciaException {
		return persistidorDatos.obtenerLocalidadesDe(prov);
	}

	public ArrayList<Pais> obtenerPaises() throws PersistenciaException {
		return persistidorDatos.obtenerPaises();
	}
}
