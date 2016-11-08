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

import app.comun.ValidadorFormato;
import app.datos.clases.EstadoStr;
import app.datos.clases.FiltroPropietario;
import app.datos.entidades.DatosEdificio;
import app.datos.entidades.Estado;
import app.datos.entidades.Inmueble;
import app.datos.entidades.Propietario;
import app.datos.servicios.InmuebleService;
import app.excepciones.PersistenciaException;
import app.logica.resultados.ResultadoCrearInmueble;
import app.logica.resultados.ResultadoCrearInmueble.ErrorCrearInmueble;
import app.logica.resultados.ResultadoEliminarInmueble;
import app.logica.resultados.ResultadoEliminarInmueble.ErrorEliminarInmueble;
import app.logica.resultados.ResultadoModificarInmueble;
import app.logica.resultados.ResultadoModificarInmueble.ErrorModificarInmueble;

@Service
/**
 * Gestor que implementa la capa lógica del ABM Inmueble y funciones asociadas a un inmueble.
 */
public class GestorInmueble {

	@Resource
	protected InmuebleService persistidorInmueble;

	@Resource
	protected GestorDatos gestorDatos;

	@Resource
	protected GestorPropietario gestorPropietario;

	@Resource
	protected ValidadorFormato validador;

	/**
	 * Método para crear un inmueble. Primero se validan las reglas de negocia y luego se persiste.
	 * Pertenece a la taskcard 14 de la iteración 1 y a la historia 3
	 *
	 * @param inmueble
	 *            a guardar
	 * @return resultado de la operación
	 * @throws PersistenciaException
	 *             si falló al persistir
	 */
	public ResultadoCrearInmueble crearInmueble(Inmueble inmueble) throws PersistenciaException {
		ArrayList<ErrorCrearInmueble> errores = new ArrayList<>();

		if(inmueble.getFechaCarga() == null){
			errores.add(ErrorCrearInmueble.Fecha_Vacia);
		}

		if(inmueble.getPropietario() != null){
			Propietario propietario = gestorPropietario.obtenerPropietario(new FiltroPropietario(inmueble.getPropietario().getTipoDocumento().getTipo(), inmueble.getPropietario().getNumeroDocumento()));
			if(propietario == null){
				errores.add(ErrorCrearInmueble.Propietario_Inexistente);
			}
		}
		else{
			errores.add(ErrorCrearInmueble.Propietario_Vacio);
		}

		if(inmueble.getPrecio() != null && !validador.validarDoublePositivo(inmueble.getPrecio())){
			errores.add(ErrorCrearInmueble.Precio_Incorrecto);
		}

		if(inmueble.getFondo() != null && !validador.validarDoublePositivo(inmueble.getFondo())){
			errores.add(ErrorCrearInmueble.Fondo_Incorrecto);
		}

		if(inmueble.getFrente() != null && !validador.validarDoublePositivo(inmueble.getFrente())){
			errores.add(ErrorCrearInmueble.Frente_Incorrecto);
		}

		if(inmueble.getSuperficie() != null && !validador.validarDoublePositivo(inmueble.getSuperficie())){
			errores.add(ErrorCrearInmueble.Superficie_Incorrecta);
		}

		if(inmueble.getTipo() == null){
			errores.add(ErrorCrearInmueble.Tipo_Vacio);
		}

		if(!validador.validarDireccion(inmueble.getDireccion())){
			errores.add(ErrorCrearInmueble.Formato_Direccion_Incorrecto);
		}

		if(!validarDatosEdificio(inmueble.getDatosEdificio())){
			errores.add(ErrorCrearInmueble.Datos_Edificio_Incorrectos);
		}

		if(errores.isEmpty()){
			persistidorInmueble.guardarInmueble(inmueble);
		}

		return new ResultadoCrearInmueble(errores.toArray(new ErrorCrearInmueble[0]));
	}

	/**
	 * Método que se encarga de la modificación de los datos de un inmueble.
	 * Se validan todos los datos correspondientes al inmueble, corroborando los que son obligatorios.
	 * Se valida que exista el inmueble en la base de datos y en caso de que no haya errores, delega el guardado del objeto a la capa de acceso a datos.
	 *
	 * @param inmueble
	 *            inmueble a modificar.
	 *
	 * @return un resultado informando errores correspondientes en caso de que los haya.
	 *
	 * @throws PersistenciaException
	 *             Se lanza esta excepción al ocurrir un error interactuando con la capa de acceso a datos.
	 */
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
			errores.add(ErrorModificarInmueble.Propietario_Vacio);
		}

		if(inmueble.getPrecio() != null && !validador.validarDoublePositivo(inmueble.getPrecio())){
			errores.add(ErrorModificarInmueble.Precio_Incorrecto);
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

		if(!validarDatosEdificio(inmueble.getDatosEdificio())){
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

	/**
	 * Se encarga de validar que exista el inmueble a eliminar, se setea el estado en BAJA y,
	 * en caso de que no haya errores, delegar el guardado del objeto a la capa de acceso a datos.
	 *
	 * @param inmueble
	 *            inmueble a eliminar
	 * @return un resultado informando errores correspondientes en caso de que los haya
	 *
	 * @throws PersistenciaException
	 *             se lanza esta excepción al ocurrir un error interactuando con la capa de acceso a datos
	 */
	public ResultadoEliminarInmueble eliminarInmueble(Inmueble inmueble) throws PersistenciaException {
		ArrayList<ErrorEliminarInmueble> errores = new ArrayList<>();

		Inmueble inmuebleAuxiliar = persistidorInmueble.obtenerInmueble(inmueble.getId());

		if(null == inmuebleAuxiliar){
			errores.add(ErrorEliminarInmueble.No_Existe_Inmueble);
		}

		if(errores.isEmpty()){
			ArrayList<Estado> estados = gestorDatos.obtenerEstados();
			for(Estado e: estados){
				if(e.getEstado().equals(EstadoStr.BAJA)){
					inmueble.setEstado(e);
				}
			}
			persistidorInmueble.modificarInmueble(inmueble);
		}

		return new ResultadoEliminarInmueble(errores.toArray(new ErrorEliminarInmueble[0]));
	}

	/**
	 * Obtiene el listado de inmuebles solicitándola a la capa de acceso a datos
	 *
	 * @return el listado de inmuebles solicitados
	 *
	 * @throws PersistenciaException
	 *             se lanza esta excepción al ocurrir un error interactuando con la capa de acceso a datos
	 */
	public ArrayList<Inmueble> obtenerInmuebles() throws PersistenciaException {
		return persistidorInmueble.listarInmuebles();
	}

	private Boolean validarDatosEdificio(DatosEdificio datosEdificio) {
		if(datosEdificio == null){
			return false;
		}

		if(datosEdificio.getAguaCaliente() == null){
			return false;
		}
		if(datosEdificio.getAguaCorriente() == null){
			return false;
		}
		if(datosEdificio.getCloacas() == null){
			return false;
		}
		if(datosEdificio.getGaraje() == null){
			return false;
		}
		if(datosEdificio.getGasNatural() == null){
			return false;
		}
		if(datosEdificio.getLavadero() == null){
			return false;
		}
		if(datosEdificio.getPatio() == null){
			return false;
		}
		if(datosEdificio.getPavimento() == null){
			return false;
		}
		if(datosEdificio.getPiscina() == null){
			return false;
		}
		if(datosEdificio.getPropiedadHorizontal() == null){
			return false;
		}
		if(datosEdificio.getTelefono() == null){
			return false;
		}
		if(datosEdificio.getAntiguedad() != null && !validador.validarEnteroPositivo(datosEdificio.getAntiguedad())){
			return false;
		}
		if(datosEdificio.getBaños() != null && !validador.validarEnteroPositivo(datosEdificio.getBaños())){
			return false;
		}
		if(datosEdificio.getDormitorios() != null && !validador.validarEnteroPositivo(datosEdificio.getDormitorios())){
			return false;
		}
		if(datosEdificio.getSuperficie() != null && !validador.validarDoublePositivo(datosEdificio.getSuperficie())){
			return false;
		}

		return true;
	}
}
