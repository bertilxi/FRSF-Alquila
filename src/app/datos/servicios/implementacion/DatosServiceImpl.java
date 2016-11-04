package app.datos.servicios.implementacion;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import app.datos.entidades.Estado;
import app.datos.entidades.Localidad;
import app.datos.entidades.Pais;
import app.datos.entidades.Provincia;
import app.datos.entidades.TipoDocumento;
import app.datos.entidades.TipoInmueble;
import app.datos.servicios.DatosService;
import app.excepciones.ConsultaException;
import app.excepciones.PersistenciaException;

@Repository
public class DatosServiceImpl implements DatosService {

	private SessionFactory sessionFactory;

	@Autowired
	public DatosServiceImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = PersistenciaException.class)
	public ArrayList<Localidad> obtenerLocalidadesDe(Provincia provincia) throws PersistenciaException {
		ArrayList<Localidad> localidades = new ArrayList<>();
		Session session = getSessionFactory().getCurrentSession();
		try{
			for(Object o: session.getNamedQuery("obtenerLocalidadesDe").setParameter("prov", provincia).list()){
				if(o instanceof Localidad){
					localidades.add((Localidad) o);
				}
			}
		} catch(Exception e){
			throw new ConsultaException();
		}
		return localidades;
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = PersistenciaException.class)
	public ArrayList<Provincia> obtenerProvinciasDe(Pais pais) throws PersistenciaException {
		ArrayList<Provincia> provincias = new ArrayList<>();
		Session session = getSessionFactory().getCurrentSession();
		try{
			for(Object o: session.getNamedQuery("obtenerProvinciasDe").setParameter("pa", pais).list()){
				if(o instanceof Provincia){
					provincias.add((Provincia) o);
				}
			}
		} catch(Exception e){
			throw new ConsultaException();
		}
		return provincias;
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = PersistenciaException.class)
	public ArrayList<Pais> obtenerPaises() throws PersistenciaException {
		ArrayList<Pais> paises = new ArrayList<>();
		Session session = getSessionFactory().getCurrentSession();
		try{
			for(Object o: session.getNamedQuery("obtenerPaises").list()){
				if(o instanceof Pais){
					paises.add((Pais) o);
				}
			}
		} catch(Exception e){
			throw new ConsultaException();
		}
		return paises;
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = PersistenciaException.class)
	public ArrayList<TipoDocumento> obtenerTiposDeDocumento() throws PersistenciaException {
		ArrayList<TipoDocumento> tipos = new ArrayList<>();
		Session session = getSessionFactory().getCurrentSession();
		try{
			for(Object o: session.getNamedQuery("obtenerTiposDeDocumento").list()){
				if(o instanceof TipoDocumento){
					tipos.add((TipoDocumento) o);
				}
			}
		} catch(Exception e){
			throw new ConsultaException();
		}
		return tipos;
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = PersistenciaException.class)
	public ArrayList<TipoInmueble> obtenerTiposDeInmueble() throws PersistenciaException {
		ArrayList<TipoInmueble> tipos = new ArrayList<>();
		Session session = getSessionFactory().getCurrentSession();
		try{
			for(Object o: session.getNamedQuery("obtenerTiposDeInmueble").list()){
				if(o instanceof TipoInmueble){
					tipos.add((TipoInmueble) o);
				}
			}
		} catch(Exception e){
			throw new ConsultaException();
		}
		return tipos;
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = PersistenciaException.class)
	public ArrayList<Estado> obtenerEstados() throws PersistenciaException {
		ArrayList<Estado> estados = new ArrayList<>();
		Session session = getSessionFactory().getCurrentSession();
		try{
			for(Object o: session.getNamedQuery("obtenerEstados").list()){
				if(o instanceof Estado){
					estados.add((Estado) o);
				}
			}
		} catch(Exception e){
			throw new ConsultaException();
		}
		return estados;
	}

}
