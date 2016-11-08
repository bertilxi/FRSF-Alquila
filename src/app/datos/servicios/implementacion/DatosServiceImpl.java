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

import app.datos.entidades.Barrio;
import app.datos.entidades.Calle;
import app.datos.entidades.Estado;
import app.datos.entidades.Localidad;
import app.datos.entidades.Orientacion;
import app.datos.entidades.Pais;
import app.datos.entidades.Provincia;
import app.datos.entidades.TipoDocumento;
import app.datos.entidades.TipoInmueble;
import app.datos.servicios.DatosService;
import app.excepciones.ConsultaException;
import app.excepciones.PersistenciaException;

@Repository
public class DatosServiceImpl implements DatosService {

	private SessionFactory sessionFactory;

	@Autowired
	public DatosServiceImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = PersistenciaException.class)
	public ArrayList<Localidad> obtenerLocalidadesDe(Provincia provincia) throws PersistenciaException {
		ArrayList<Localidad> localidades = new ArrayList<>();
		Session session = getSessionFactory().getCurrentSession();
		try{
			for(Object o: session.getNamedQuery("obtenerLocalidadesDe").setParameter("prov", provincia).list()){
				if(o instanceof Localidad){
					localidades.add((Localidad) o);
				}
			}
		} catch(Exception e){
			throw new ConsultaException(e);
		}
		return localidades;
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = PersistenciaException.class)
	public ArrayList<Provincia> obtenerProvinciasDe(Pais pais) throws PersistenciaException {
		ArrayList<Provincia> provincias = new ArrayList<>();
		Session session = getSessionFactory().getCurrentSession();
		try{
			for(Object o: session.getNamedQuery("obtenerProvinciasDe").setParameter("pa", pais).list()){
				if(o instanceof Provincia){
					provincias.add((Provincia) o);
				}
			}
		} catch(Exception e){
			throw new ConsultaException(e);
		}
		return provincias;
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = PersistenciaException.class)
	public ArrayList<Pais> obtenerPaises() throws PersistenciaException {
		ArrayList<Pais> paises = new ArrayList<>();
		Session session = getSessionFactory().getCurrentSession();
		try{
			for(Object o: session.getNamedQuery("obtenerPaises").list()){
				if(o instanceof Pais){
					paises.add((Pais) o);
				}
			}
		} catch(Exception e){
			throw new ConsultaException(e);
		}
		return paises;
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = PersistenciaException.class)
	public ArrayList<TipoDocumento> obtenerTiposDeDocumento() throws PersistenciaException {
		ArrayList<TipoDocumento> tipos = new ArrayList<>();
		Session session = getSessionFactory().getCurrentSession();
		try{
			for(Object o: session.getNamedQuery("obtenerTiposDeDocumento").list()){
				if(o instanceof TipoDocumento){
					tipos.add((TipoDocumento) o);
				}
			}
		} catch(Exception e){
			throw new ConsultaException(e);
		}
		return tipos;
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = PersistenciaException.class)
	public ArrayList<TipoInmueble> obtenerTiposDeInmueble() throws PersistenciaException {
		ArrayList<TipoInmueble> tipos = new ArrayList<>();
		Session session = getSessionFactory().getCurrentSession();
		try{
			for(Object o: session.getNamedQuery("obtenerTiposDeInmueble").list()){
				if(o instanceof TipoInmueble){
					tipos.add((TipoInmueble) o);
				}
			}
		} catch(Exception e){
			throw new ConsultaException(e);
		}
		return tipos;
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = PersistenciaException.class)
	public ArrayList<Estado> obtenerEstados() throws PersistenciaException {
		ArrayList<Estado> estados = new ArrayList<>();
		Session session = getSessionFactory().getCurrentSession();
		try{
			for(Object o: session.getNamedQuery("obtenerEstados").list()){
				if(o instanceof Estado){
					estados.add((Estado) o);
				}
			}
		} catch(Exception e){
			throw new ConsultaException(e);
		}
		return estados;
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = PersistenciaException.class)
	public ArrayList<Barrio> obtenerBarriosDe(Localidad localidad) throws PersistenciaException {
		ArrayList<Barrio> barrios = new ArrayList<>();
		Session session = getSessionFactory().getCurrentSession();
		try{
			for(Object o: session.getNamedQuery("obtenerBarriosDe").setParameter("loc", localidad).list()){
				if(o instanceof Barrio){
					barrios.add((Barrio) o);
				}
			}
		} catch(Exception e){
			throw new ConsultaException(e);
		}
		return barrios;
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = PersistenciaException.class)
	public ArrayList<Calle> obtenerCallesDe(Localidad localidad) throws PersistenciaException {
		ArrayList<Calle> calles = new ArrayList<>();
		Session session = getSessionFactory().getCurrentSession();
		try{
			for(Object o: session.getNamedQuery("obtenerCallesDe").setParameter("loc",localidad).list()){
				if(o instanceof Calle){
					calles.add((Calle) o);
				}
			}
		} catch(Exception e){
			throw new ConsultaException(e);
		}
		return calles;
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = PersistenciaException.class)
	public ArrayList<Orientacion> obtenerOrientaciones() throws PersistenciaException {
		ArrayList<Orientacion> orientaciones = new ArrayList<>();
		Session session = getSessionFactory().getCurrentSession();
		try{
			for(Object o: session.getNamedQuery("obtenerOrientaciones").list()){
				if(o instanceof Orientacion){
					orientaciones.add((Orientacion) o);
				}
			}
		} catch(Exception e){
			throw new ConsultaException(e);
		}
		return orientaciones;
	}

}
