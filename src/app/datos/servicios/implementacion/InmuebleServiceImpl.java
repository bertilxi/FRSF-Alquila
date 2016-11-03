package app.datos.servicios.implementacion;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import app.datos.entidades.Inmueble;
import app.datos.entidades.Propietario;
import app.datos.servicios.InmuebleService;
import app.excepciones.ConsultaException;
import app.excepciones.PersistenciaException;
import app.excepciones.SaveUpdateException;

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
	@Transactional(rollbackFor = PersistenciaException.class)
	public void guardarInmueble(Inmueble inmueble) throws PersistenciaException {
		Session session = getSessionFactory().getCurrentSession();
		try{
			session.save(inmueble);
		} catch(Exception e){
			throw new SaveUpdateException();
		}

	}

	@Override
	@Transactional(rollbackFor = PersistenciaException.class)
	public void modificarInmueble(Inmueble inmueble) throws PersistenciaException {
		Session session = getSessionFactory().getCurrentSession();
		try{
			session.update(inmueble);
		} catch(Exception e){
			throw new SaveUpdateException();
		}
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = PersistenciaException.class)
	public ArrayList<Inmueble> listarInmuebles() throws PersistenciaException {
		ArrayList<Inmueble> inmuebles = new ArrayList<>();
		Session session = getSessionFactory().getCurrentSession();
		try{
			for(Object o: session.getNamedQuery("obtenerInmuebles").list()){
				if(o instanceof Propietario){
					inmuebles.add((Inmueble) o);
				}
			}
		} catch(Exception e){
			throw new ConsultaException();
		}
		return inmuebles;
	}

}
