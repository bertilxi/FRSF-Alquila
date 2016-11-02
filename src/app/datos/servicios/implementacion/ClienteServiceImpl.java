package app.datos.servicios.implementacion;

import java.util.ArrayList;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import app.datos.clases.FiltroCliente;
import app.datos.entidades.Cliente;
import app.datos.servicios.ClienteService;
import app.excepciones.ConsultaException;
import app.excepciones.PersistenciaException;
import app.excepciones.SaveUpdateException;

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
		Session session = getSessionFactory().getCurrentSession();
		try{
			session.save(cliente);
		} catch(Exception e){
			throw new SaveUpdateException();
		}
	}

	@Override
	public void modificarCliente(Cliente cliente) throws PersistenciaException {
		Session session = getSessionFactory().getCurrentSession();
		try{
			session.update(cliente);
		} catch(Exception e){
			throw new SaveUpdateException();
		}
	}

	@Override
	public Cliente obtenerCliente(FiltroCliente filtro) throws PersistenciaException {
		Cliente cliente;
		Session session = getSessionFactory().getCurrentSession();
		try{
			cliente = (Cliente) session.getNamedQuery("obtenerCliente").setParameter("tipoDocumento", filtro.getTipoDocumento()).setParameter("documento", filtro.getDocumento()).uniqueResult();
		} catch(NoResultException e){
			return null;
		} catch(NonUniqueResultException e){
			return null;
		} catch(Exception e){
			throw new ConsultaException();
		}
		return cliente;
	}

	@Override
	public ArrayList<Cliente> listarClientes() throws PersistenciaException {
		ArrayList<Cliente> clientes = new ArrayList<>();
		Session session = getSessionFactory().getCurrentSession();
		try{
			for(Object o: session.getNamedQuery("obtenerClientes").list()){
				if(o instanceof Cliente){
					clientes.add((Cliente) o);
				}
			}
		} catch(NoResultException e){
			return null;
		} catch(NonUniqueResultException e){
			return null;
		} catch(Exception e){
			throw new ConsultaException();
		}
		return clientes;
	}

}
