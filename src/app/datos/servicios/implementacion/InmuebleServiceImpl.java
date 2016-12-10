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

import javax.persistence.EntityNotFoundException;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import app.datos.clases.FiltroInmueble;
import app.datos.entidades.Inmueble;
import app.datos.servicios.InmuebleService;
import app.excepciones.ConsultaException;
import app.excepciones.ObjNotFoundException;
import app.excepciones.PersistenciaException;
import app.excepciones.SaveUpdateException;

/**
 * Implementación con hibernate de la interface que define los métodos de persistencia de un inmueble
 * Pertenece a la taskcard 15 de la iteración 1 y a la historia de usuario 3
 */

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
			throw new SaveUpdateException(e);
		}

	}

	@Override
	@Transactional(rollbackFor = PersistenciaException.class)
	public void modificarInmueble(Inmueble inmueble) throws PersistenciaException {
		Session session = getSessionFactory().getCurrentSession();
		try{
			session.update(inmueble);
		} catch(Exception e){
			throw new SaveUpdateException(e);
		}
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = PersistenciaException.class)
	public ArrayList<Inmueble> listarInmuebles() throws PersistenciaException {
		ArrayList<Inmueble> inmuebles = new ArrayList<>();
		Session session = getSessionFactory().getCurrentSession();
		try{
			for(Object o: session.getNamedQuery("obtenerInmuebles").list()){
				if(o instanceof Inmueble){
					inmuebles.add((Inmueble) o);
				}
			}
		} catch(Exception e){
			throw new ConsultaException(e);
		}
		return inmuebles;
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = PersistenciaException.class)
	public ArrayList<Inmueble> listarInmuebles(FiltroInmueble filtro) throws PersistenciaException {
		ArrayList<Inmueble> inmuebles = new ArrayList<>();
		Session session = getSessionFactory().getCurrentSession();
		try{
			Query query = session.createQuery(filtro.getConsultaDinamica());
			filtro.setParametros(query);
			for(Object o: query.list()){
				if(o instanceof Inmueble){
					inmuebles.add((Inmueble) o);
				}
			}
		} catch(Exception e){
			throw new ConsultaException(e);
		}
		return inmuebles;
	}

	@Override
	@Transactional(rollbackFor = PersistenciaException.class)
	public Inmueble obtenerInmueble(Integer id) throws PersistenciaException {
		Inmueble inmueble = null;
		Session session = getSessionFactory().getCurrentSession();
		try{
			inmueble = session.get(Inmueble.class, id);
		} catch(EntityNotFoundException e){
			throw new ObjNotFoundException("obtener", e);
		} catch(Exception e){
			throw new ConsultaException(e);
		}
		return inmueble;
	}

}
