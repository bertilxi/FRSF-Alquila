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

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Service;

import app.comun.ValidadorFormato;
import app.datos.entidades.Inmueble;
import app.datos.servicios.InmuebleService;
import app.excepciones.GestionException;
import app.excepciones.PersistenciaException;
import app.logica.resultados.ResultadoCrearInmueble;
import app.logica.resultados.ResultadoEliminarInmueble;
import app.logica.resultados.ResultadoModificarInmueble;

@Service
public class GestorInmueble {

	@Resource
	protected InmuebleService persistidorInmueble;

	@Resource
	protected GestorDatos gestorDatos;

	@Resource
	protected ValidadorFormato validador;

	public ResultadoCrearInmueble crearInmueble(Inmueble inmbueble) throws PersistenciaException, GestionException {
		throw new NotYetImplementedException();
	}

	public ResultadoModificarInmueble modificarInmueble(Inmueble propietario) throws PersistenciaException {
		throw new NotYetImplementedException();
	}

	public ResultadoEliminarInmueble eliminarInmueble(Inmueble propietario) throws PersistenciaException {
		throw new NotYetImplementedException();
	}

	public ArrayList<Inmueble> obtenerInmuebles() throws PersistenciaException {
		throw new NotYetImplementedException();
	}
}
