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

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import app.datos.entidades.Venta;
import app.datos.servicios.VentaService;
import app.excepciones.PersistenciaException;
import app.excepciones.SaveUpdateException;

/**
 * Implementación con hibernate de la interface que define los métodos de persistencia de una venta
 * Pertenece a la taskcard 32 de la iteración 2 y a la historia 8
 */
@Repository
public class VentaServiceImpl implements VentaService {

	private SessionFactory sessionFactory;

	@Autowired
	public VentaServiceImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	/* 
	 * Método para guardar en la base de datos una venta
	 */
	@Override
	@Transactional(rollbackFor = PersistenciaException.class)//si falla hace rollback de la transacción
	public void guardarVenta(Venta venta) throws PersistenciaException {
		Session session = getSessionFactory().getCurrentSession();
		try{
			session.save(venta);
		} catch(Exception e){
			throw new SaveUpdateException(e);
		}
		
	}
}
