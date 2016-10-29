package app.datos.servicios.implementacion;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import app.datos.clases.FiltroPropietario;
import app.datos.entidades.Propietario;
import app.datos.servicios.PropietarioService;
import app.excepciones.PersistenciaException;

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
	public void guardarPropietario(Propietario propietario) throws PersistenciaException {
		// TODO Auto-generated method stub

	}

	@Override
	public void modificarPropietario(Propietario propietario) throws PersistenciaException {
		// TODO Auto-generated method stub

	}

	@Override
	public Propietario obtenerPropietario(FiltroPropietario filtro) throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eliminarPropietario(Propietario propietario) throws PersistenciaException {

	}

}
