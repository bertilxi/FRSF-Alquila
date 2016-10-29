package app.datos.servicios.implementacion;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import app.datos.entidades.Inmueble;
import app.datos.servicios.InmuebleService;
import app.excepciones.PersistenciaException;

@Repository
public class InmuebleServiceImpl implements InmuebleService {

	private SessionFactory sessionFactory;

	@Autowired
	public InmuebleServiceImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Override
	public void guardarInmueble(Inmueble inmueble) throws PersistenciaException {
		// TODO Auto-generated method stub

	}

	@Override
	public void modificarInmueble(Inmueble inmueble) throws PersistenciaException {
		// TODO Auto-generated method stub

	}

}
