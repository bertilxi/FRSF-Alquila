package app.datos.servicios.implementacion;

import java.util.ArrayList;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import app.datos.clases.FiltroVendedor;
import app.datos.entidades.Vendedor;
import app.datos.servicios.VendedorService;
import app.excepciones.ConsultaException;
import app.excepciones.PersistenciaException;
import app.excepciones.SaveUpdateException;

@Repository
public class VendedorServiceImpl implements VendedorService {

	private SessionFactory sessionFactory;

	@Autowired
	public VendedorServiceImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Override
	@Transactional(rollbackFor = PersistenciaException.class)
	public void guardarVendedor(Vendedor vendedor) throws PersistenciaException {
		Session session = getSessionFactory().getCurrentSession();
		try{
			session.save(vendedor);
		} catch(Exception e){
			throw new SaveUpdateException();
		}

	}

	@Override
	@Transactional(rollbackFor = PersistenciaException.class)
	public void modificarVendedor(Vendedor vendedor) throws PersistenciaException {
		Session session = getSessionFactory().getCurrentSession();
		try{
			session.update(vendedor);
		} catch(Exception e){
			throw new SaveUpdateException();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Vendedor obtenerVendedor(FiltroVendedor filtro) throws PersistenciaException {
		Vendedor vendedor = null;
		Session session = getSessionFactory().getCurrentSession();
		try{
			vendedor = (Vendedor) session.getNamedQuery("obtenerVendedor").setParameter("tipoDocumento", filtro.getTipoDocumento()).setParameter("documento", filtro.getDocumento()).uniqueResult();
		} catch(NoResultException e){
			return null;
		} catch(NonUniqueResultException e){
			return null;
		} catch(Exception e){
			throw new ConsultaException();
		}
		return vendedor;
	}

	@Override
	@Transactional(readOnly = true)
	public ArrayList<Vendedor> listarVendedores() throws PersistenciaException {
		ArrayList<Vendedor> vendedores = new ArrayList<Vendedor>();
		Session session = getSessionFactory().getCurrentSession();
		try{
			for(Object o: session.getNamedQuery("listarVendedores").list()){
				if(o instanceof Vendedor){
					vendedores.add((Vendedor) o);
				}
			}
		} catch(Exception e) {
			throw new ConsultaException();
		}
		return vendedores;
	}

}
