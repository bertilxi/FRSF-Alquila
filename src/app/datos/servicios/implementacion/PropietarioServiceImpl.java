/**
 * Copyright (C) 2016 Fernando Berti - Daniel Campodonico - Emiliano Gioria - Lucas Moretti - Esteban Rebechi - Andres Leonel Rico
 * This file is part of Olimpo.
 *
 * Olimpo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Olimpo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Olimpo. If not, see <http://www.gnu.org/licenses/>.
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

import app.datos.clases.FiltroPropietario;
import app.datos.entidades.Propietario;
import app.datos.servicios.PropietarioService;
import app.excepciones.ConsultaException;
import app.excepciones.PersistenciaException;
import app.excepciones.SaveUpdateException;

/**
 * Implementación con hibernate de la interface que define los métodos de persistencia de un propietario
 * Pertenece a la taskcard 10 de la iteración 1 y a la historia de usuario 2
 */
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
			throw new SaveUpdateException(e);
		}
	}

	@Override
	@Transactional(rollbackFor = PersistenciaException.class)
	public void modificarPropietario(Propietario propietario) throws PersistenciaException {
		Session session = getSessionFactory().getCurrentSession();
		try{
			session.update(propietario);
		} catch(Exception e){
			throw new SaveUpdateException(e);
		}
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = PersistenciaException.class)
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
			throw new ConsultaException(e);
		}
		return propietario;
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = PersistenciaException.class)
	public ArrayList<Propietario> listarPropietarios() throws PersistenciaException {
		ArrayList<Propietario> propietarios = new ArrayList<>();
		Session session = getSessionFactory().getCurrentSession();
		try{
			for(Object o: session.getNamedQuery("obtenerPropietarios").list()){
				if(o instanceof Propietario){
					propietarios.add((Propietario) o);
				}
			}
		} catch(Exception e){
			throw new ConsultaException(e);
		}
		return propietarios;
	}

}
