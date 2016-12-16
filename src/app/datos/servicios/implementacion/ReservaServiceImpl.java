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

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import app.datos.entidades.Cliente;
import app.datos.entidades.Inmueble;
import app.datos.entidades.Reserva;
import app.datos.servicios.ReservaService;
import app.excepciones.ConsultaException;
import app.excepciones.PersistenciaException;
import app.excepciones.SaveUpdateException;

/**
 * Implementación con hibernate de la interface que define los métodos de persistencia de una reserva
 * Pertenece a la taskcard 28 de la iteración 2 y a la historia 7
 */
@Repository
public class ReservaServiceImpl implements ReservaService {

	private SessionFactory sessionFactory;

	@Autowired
	public ReservaServiceImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/* 
	 * Método para guardar en la base de datos una reserva
	 */
	@Override
	@Transactional(rollbackFor = PersistenciaException.class) //si falla hace rollback de la transacción
	public void guardarReserva(Reserva reserva) throws PersistenciaException {
		Session session = getSessionFactory().getCurrentSession();
		try{
			session.save(reserva);
		} catch(Exception e){
			throw new SaveUpdateException(e);
		}

	}

	/* 
	 * Método para modificar en la base de datos una reserva
	 */
	@Override
	@Transactional(rollbackFor = PersistenciaException.class) //si falla hace rollback de la transacción
	public void modificarReserva(Reserva reserva) throws PersistenciaException {
		Session session = getSessionFactory().getCurrentSession();
		try{
			session.update(reserva);
		} catch(Exception e){
			throw new SaveUpdateException(e);
		}

	}

	/* 
	 * Método para obtener todas las reservas de un cliente
	 */
	@Override
	@Transactional(rollbackFor = PersistenciaException.class) //si falla hace rollback de la transacción
	public ArrayList<Reserva> obtenerReservas(Cliente cliente) throws PersistenciaException {
		ArrayList<Reserva> reservas = new ArrayList<>();
		Session session = getSessionFactory().getCurrentSession();
		try{
			//named query ubicada en entidad reserva
			for(Object o: session.getNamedQuery("obtenerReservasCliente").setParameter("cliente", cliente).list()){
				if(o instanceof Reserva){
					reservas.add((Reserva) o);
				}
			}
		} catch(Exception e){
			throw new ConsultaException(e);
		}
		return reservas;
	}

	/* 
	 * Método para obtener todas las reservas de un inmueble
	 */
	@Override
	@Transactional(rollbackFor = PersistenciaException.class) //si falla hace rollback de la transacción
	public ArrayList<Reserva> obtenerReservas(Inmueble inmueble) throws PersistenciaException {
		ArrayList<Reserva> reservas = new ArrayList<>();
		Session session = getSessionFactory().getCurrentSession();
		try{
			//named query ubicada en entidad reserva
			for(Object o: session.getNamedQuery("obtenerReservasInmueble").setParameter("inmueble", inmueble).list()){
				if(o instanceof Reserva){
					reservas.add((Reserva) o);
				}
			}
		} catch(Exception e){
			throw new ConsultaException(e);
		}
		return reservas;
	}
}
