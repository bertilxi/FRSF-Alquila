package app.datos.servicios.implementacion;

import java.util.ArrayList;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import app.datos.clases.FiltroPropietario;
import app.datos.entidades.Propietario;
import app.datos.servicios.PropietarioService;
import app.excepciones.ConsultaException;
import app.excepciones.PersistenciaException;
import app.excepciones.SaveUpdateException;

@Repository
public class PropietarioServiceImpl implements PropietarioService {

	private SessionFactory sessionFactory;

	@Autowired
	public PropietarioServiceImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Override
	@Transactional(rollbackFor = PersistenciaException.class)
	public void guardarPropietario(Propietario propietario) throws PersistenciaException {
		Session session = getSessionFactory().getCurrentSession();
		try{
			session.save(propietario);
		} catch(Exception e){
			throw new SaveUpdateException();
		}
	}

	@Override
	@Transactional(rollbackFor = PersistenciaException.class)
	public void modificarPropietario(Propietario propietario) throws PersistenciaException {
		Session session = getSessionFactory().getCurrentSession();
		try{
			session.update(propietario);
		} catch(Exception e){
			throw new SaveUpdateException();
		}
	}

	@Override
	public Propietario obtenerPropietario(FiltroPropietario filtro) throws PersistenciaException {
		Propietario propietario = null;
		Session session = getSessionFactory().getCurrentSession();
		try{
			propietario = (Propietario) session.getNamedQuery("obtenerPropietario").setParameter("tipoDocumento", filtro.getTipoDocumento()).setParameter("documento", filtro.getDocumento()).uniqueResult();
		} catch(NoResultException e){
			return null;
		} catch(NonUniqueResultException e){
			return null;
		} catch(Exception e){
			throw new ConsultaException();
		}
		return propietario;
	}

	@Override
	public void eliminarPropietario(Propietario propietario) throws PersistenciaException {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<Propietario> listarPropietarios() throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

}
