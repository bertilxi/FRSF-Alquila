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
