/**
 * Copyright (C) 2016  Fernando Berti - Daniel Campodonico - Emiliano Gioria - Lucas Moretti - Esteban Rebechi - Andres Leonel Rico
 * This file is part of Olimpo.
 *
 * Olimpo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Olimpo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Olimpo.  If not, see <http://www.gnu.org/licenses/>.
 */
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
	@Transactional(readOnly = true, rollbackFor = PersistenciaException.class)
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
	@Transactional(readOnly = true, rollbackFor = PersistenciaException.class)
	public ArrayList<Vendedor> listarVendedores() throws PersistenciaException {
		ArrayList<Vendedor> vendedores = new ArrayList<>();
		Session session = getSessionFactory().getCurrentSession();
		try{
			for(Object o: session.getNamedQuery("listarVendedores").list()){
				if(o instanceof Vendedor){
					vendedores.add((Vendedor) o);
				}
			}
		} catch(Exception e){
			throw new ConsultaException();
		}
		return vendedores;
	}

}
