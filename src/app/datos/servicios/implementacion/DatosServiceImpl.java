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

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public ArrayList<Localidad> obtenerLocalidadesDe(Provincia provincia) throws PersistenciaException {
		ArrayList<Localidad> localidades = new ArrayList<>();
		Session session = getSessionFactory().getCurrentSession();
		try{
			localidades = (ArrayList<Localidad>) session.getNamedQuery("obtenerLocalidadesDe").setParameter("prov", provincia).list();
		} catch(Exception e){
			throw new ConsultaException();
		}
		return localidades;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public ArrayList<Provincia> obtenerProvinciasDe(Pais pais) throws PersistenciaException {
		ArrayList<Provincia> provincias = new ArrayList<>();
		Session session = getSessionFactory().getCurrentSession();
		try{
			provincias = (ArrayList<Provincia>) session.getNamedQuery("obtenerProvinciasDe").setParameter("pa", pais).list();
		} catch(Exception e){
			throw new ConsultaException();
		}
		return provincias;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public ArrayList<Pais> obtenerPaises() throws PersistenciaException {
		ArrayList<Pais> paises = new ArrayList<>();
		Session session = getSessionFactory().getCurrentSession();
		try{
			paises = (ArrayList<Pais>) session.getNamedQuery("obtenerPaises").list();
		} catch(Exception e){
			throw new ConsultaException();
		}
		return paises;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public ArrayList<TipoDocumento> obtenerTiposDeDocumento() throws PersistenciaException {
		ArrayList<TipoDocumento> tipos = new ArrayList<>();
		Session session = getSessionFactory().getCurrentSession();
		try{
			tipos = (ArrayList<TipoDocumento>) session.getNamedQuery("obtenerTiposDeDocumento").list();
		} catch(Exception e){
			throw new ConsultaException();
		}
		return tipos;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public ArrayList<TipoInmueble> obtenerTiposDeInmueble() throws PersistenciaException {
		ArrayList<TipoInmueble> tipos = new ArrayList<>();
		Session session = getSessionFactory().getCurrentSession();
		try{
			tipos = (ArrayList<TipoInmueble>) session.getNamedQuery("obtenerTiposDeInmueble").list();
		} catch(Exception e){
			throw new ConsultaException();
		}
		return tipos;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public ArrayList<Estado> obtenerEstados() throws PersistenciaException {
		ArrayList<Estado> estados = new ArrayList<>();
		Session session = getSessionFactory().getCurrentSession();
		try{
			estados = (ArrayList<Estado>) session.getNamedQuery("obtenerEstados").list();
		} catch(Exception e){
			throw new ConsultaException();
		}
		return estados;
	}

}
