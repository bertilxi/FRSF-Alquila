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
import app.datos.clases.FiltroPropietario;
import app.datos.entidades.Inmueble;
import app.datos.entidades.Propietario;
import app.datos.servicios.InmuebleService;
import app.excepciones.GestionException;
import app.excepciones.PersistenciaException;
import app.logica.resultados.ResultadoCrearInmueble;
import app.logica.resultados.ResultadoEliminarInmueble;
import app.logica.resultados.ResultadoModificarInmueble;
import app.logica.resultados.ResultadoModificarInmueble.ErrorModificarInmueble;

@Service
public class GestorInmueble {

	@Resource
	protected InmuebleService persistidorInmueble;

	@Resource
	protected GestorDatos gestorDatos;

	@Resource
	protected GestorPropietario gestorPropietario;

	@Resource
	protected ValidadorFormato validador;

	public ResultadoCrearInmueble crearInmueble(Inmueble inmbueble) throws PersistenciaException, GestionException {
		throw new NotYetImplementedException();
	}

	public ResultadoModificarInmueble modificarInmueble(Inmueble inmueble) throws PersistenciaException {
		ArrayList<ErrorModificarInmueble> errores = new ArrayList<>();

		if(inmueble.getFechaCarga() == null){
			errores.add(ErrorModificarInmueble.Fecha_Vacia);
		}

		if(inmueble.getPropietario() != null){
			Propietario propietario = gestorPropietario.obtenerPropietario(new FiltroPropietario(inmueble.getPropietario().getTipoDocumento().getTipo(), inmueble.getPropietario().getNumeroDocumento()));
			if(propietario == null){
				errores.add(ErrorModificarInmueble.Propietario_Inexistente);
			}
		}
		else{
			errores.add(ErrorModificarInmueble.Propietario_Inexistente);
		}

		if(inmueble.getPrecio() == null){
			errores.add(ErrorModificarInmueble.Precio_Vacio);
		}
		else{
			if(!validador.validarDoublePositivo(inmueble.getPrecio())){
				errores.add(ErrorModificarInmueble.Precio_Incorrecto);
			}
		}

		if(inmueble.getFondo() != null && !validador.validarDoublePositivo(inmueble.getFondo())){
			errores.add(ErrorModificarInmueble.Fondo_Incorrecto);
		}

		if(inmueble.getFrente() != null && !validador.validarDoublePositivo(inmueble.getFrente())){
			errores.add(ErrorModificarInmueble.Frente_Incorrecto);
		}

		if(inmueble.getSuperficie() != null && !validador.validarDoublePositivo(inmueble.getSuperficie())){
			errores.add(ErrorModificarInmueble.Superficie_Incorrecta);
		}

		if(inmueble.getTipo() == null){
			errores.add(ErrorModificarInmueble.Tipo_Vacio);
		}

		if(!validador.validarDireccion(inmueble.getDireccion())){
			errores.add(ErrorModificarInmueble.Formato_Direccion_Incorrecto);
		}

		if(!validador.validarDatosEdificio(inmueble.getDatosEdificio())){
			errores.add(ErrorModificarInmueble.Datos_Edificio_Incorrectos);
		}

		Inmueble inmuebleAuxiliar = persistidorInmueble.obtenerInmueble(inmueble.getId());
		if(inmuebleAuxiliar == null){
			errores.add(ErrorModificarInmueble.Inmueble_Inexistente);
		}

		if(errores.isEmpty()){
			persistidorInmueble.modificarInmueble(inmueble);
		}

		return new ResultadoModificarInmueble(errores.toArray(new ErrorModificarInmueble[0]));
	}

	public ResultadoEliminarInmueble eliminarInmueble(Inmueble propietario) throws PersistenciaException {
		throw new NotYetImplementedException();
	}

	public ArrayList<Inmueble> obtenerInmuebles() throws PersistenciaException {
		throw new NotYetImplementedException();
	}
}
