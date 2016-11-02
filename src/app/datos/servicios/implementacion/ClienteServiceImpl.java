package app.datos.servicios.implementacion;

import java.util.ArrayList;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import app.datos.clases.FiltroCliente;
import app.datos.entidades.Cliente;
import app.datos.servicios.ClienteService;
import app.excepciones.PersistenciaException;

@Repository
public class ClienteServiceImpl implements ClienteService {

	private SessionFactory sessionFactory;

	@Autowired
	public ClienteServiceImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Override
	public void guardarCliente(Cliente cliente) throws PersistenciaException {
		// TODO Auto-generated method stub

	}

	@Override
	public void modificarCliente(Cliente cliente) throws PersistenciaException {
		// TODO Auto-generated method stub

	}

	@Override
	public Cliente obtenerCliente(FiltroCliente filtro) throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Cliente> listarClientes() throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

}
