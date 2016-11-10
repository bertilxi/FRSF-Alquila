package app.datos.servicios.implementacion;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import app.datos.entidades.HistorialInmueble;
import app.datos.servicios.HistorialService;
import app.excepciones.PersistenciaException;
import app.excepciones.SaveUpdateException;

@Repository
public class HistorialServiceImpl implements HistorialService {

	private SessionFactory sessionFactory;

	@Autowired
	public HistorialServiceImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Override
	@Transactional(rollbackFor = PersistenciaException.class)
	public void guardarHistorialInmueble(HistorialInmueble historialInmueble) throws PersistenciaException {
		Session session = getSessionFactory().getCurrentSession();
		try{
			session.save(historialInmueble);
		} catch(Exception e){
			throw new SaveUpdateException(e);
		}
	}

}
