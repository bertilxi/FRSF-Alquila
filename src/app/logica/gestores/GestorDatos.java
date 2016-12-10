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
package app.logica.gestores;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

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
import app.excepciones.PersistenciaException;

@Service
public class GestorDatos {

	@Resource
	private DatosService persistidorDatos;

	public ArrayList<TipoDocumento> obtenerTiposDeDocumento() throws PersistenciaException {
		return persistidorDatos.obtenerTiposDeDocumento();
	}

	public ArrayList<TipoInmueble> obtenerTiposInmueble() throws PersistenciaException {
		return persistidorDatos.obtenerTiposDeInmueble();
	}

	public ArrayList<Provincia> obtenerProvinciasDe(Pais pais) throws PersistenciaException {
		ArrayList<Provincia> lista = persistidorDatos.obtenerProvinciasDe(pais);
		return lista;
	}

	public ArrayList<Localidad> obtenerLocalidadesDe(Provincia prov) throws PersistenciaException {
		ArrayList<Localidad> lista = persistidorDatos.obtenerLocalidadesDe(prov);
		return lista;
	}

	public ArrayList<Pais> obtenerPaises() throws PersistenciaException {
		ArrayList<Pais> lista = persistidorDatos.obtenerPaises();
		return lista;
	}

	public ArrayList<Estado> obtenerEstados() throws PersistenciaException {
		return persistidorDatos.obtenerEstados();
	}

	public ArrayList<Barrio> obtenerBarriosDe(Localidad localidad) throws PersistenciaException {
		ArrayList<Barrio> lista = persistidorDatos.obtenerBarriosDe(localidad);
		return lista;
	}

	public ArrayList<Calle> obtenerCallesDe(Localidad localidad) throws PersistenciaException {
		ArrayList<Calle> lista = persistidorDatos.obtenerCallesDe(localidad);
		return lista;
	}

	public ArrayList<Orientacion> obtenerOrientaciones() throws PersistenciaException {
		return persistidorDatos.obtenerOrientaciones();
	}
}
