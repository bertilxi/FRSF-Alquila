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
import app.datos.clases.FiltroCliente;
import app.datos.entidades.Cliente;
import app.datos.entidades.Estado;
import app.datos.servicios.ClienteService;
import app.excepciones.EntidadExistenteConEstadoBajaException;
import app.excepciones.GestionException;
import app.excepciones.PersistenciaException;
import app.logica.resultados.ResultadoCrearCliente;
import app.logica.resultados.ResultadoCrearCliente.ErrorCrearCliente;
import app.logica.resultados.ResultadoEliminarCliente;
import app.logica.resultados.ResultadoEliminarCliente.ErrorEliminarCliente;
import app.logica.resultados.ResultadoModificarCliente;
import app.logica.resultados.ResultadoModificarCliente.ErrorModificarCliente;

@Service
public class GestorCliente {

	@Resource
	protected ClienteService persistidorCliente;

	@Resource
	protected GestorDatos gestorDatos;

	@Resource
	protected ValidadorFormato validador;

	public ResultadoCrearCliente crearCliente(Cliente cliente) throws PersistenciaException, GestionException {
		ArrayList<ErrorCrearCliente> errores = new ArrayList<>();

		if(!validador.validarNombre(cliente.getNombre())){
			errores.add(ErrorCrearCliente.Formato_Nombre_Incorrecto);
		}

		if(!validador.validarApellido(cliente.getApellido())){
			errores.add(ErrorCrearCliente.Formato_Apellido_Incorrecto);
		}

		if(!validador.validarTelefono(cliente.getTelefono())){
			errores.add(ErrorCrearCliente.Formato_Telefono_Incorrecto);
		}

		if(!validador.validarDocumento(cliente.getTipoDocumento(), cliente.getNumeroDocumento())){
			errores.add(ErrorCrearCliente.Formato_Documento_Incorrecto);
		}

		Cliente clienteAuxiliar = persistidorCliente.obtenerCliente(new FiltroCliente(cliente.getTipoDocumento().getTipo(),
				cliente.getNumeroDocumento()));

		if(null != clienteAuxiliar){
			if(clienteAuxiliar.getEstado().getEstado().equals(EstadoStr.ALTA)){
				errores.add(ErrorCrearCliente.Ya_Existe_Cliente);
			}
			else{
				throw new EntidadExistenteConEstadoBajaException();
			}
		}

		if(errores.isEmpty()){
			ArrayList<Estado> estados = gestorDatos.obtenerEstados();
			for(Estado e: estados){
				if(e.getEstado().equals(EstadoStr.ALTA)){
					cliente.setEstado(e);
				}
			}
			persistidorCliente.guardarCliente(cliente);
		}

		return new ResultadoCrearCliente(errores.toArray(new ErrorCrearCliente[0]));
	}

	public ResultadoModificarCliente modificarCliente(Cliente cliente) throws PersistenciaException {
		ArrayList<ErrorModificarCliente> errores = new ArrayList<>();

		if(!validador.validarNombre(cliente.getNombre())){
			errores.add(ErrorModificarCliente.Formato_Nombre_Incorrecto);
		}

		if(!validador.validarApellido(cliente.getApellido())){
			errores.add(ErrorModificarCliente.Formato_Apellido_Incorrecto);
		}

		if(!validador.validarTelefono(cliente.getTelefono())){
			errores.add(ErrorModificarCliente.Formato_Telefono_Incorrecto);
		}

		if(!validador.validarDocumento(cliente.getTipoDocumento(), cliente.getNumeroDocumento())){
			errores.add(ErrorModificarCliente.Formato_Documento_Incorrecto);
		}

		Cliente clienteAuxiliar = persistidorCliente.obtenerCliente(new FiltroCliente(cliente.getTipoDocumento().getTipo(),
				cliente.getNumeroDocumento()));

		if(null != clienteAuxiliar && !cliente.equals(clienteAuxiliar)){
			errores.add(ErrorModificarCliente.Otro_Cliente_Posee_Mismo_Documento_Y_Tipo);
		}

		if(errores.isEmpty()){
			if(cliente.getEstado().getEstado().equals(EstadoStr.BAJA)){
				ArrayList<Estado> estados = gestorDatos.obtenerEstados();
				for(Estado e: estados){
					if(e.getEstado().equals(EstadoStr.ALTA)){
						cliente.setEstado(e);
					}
				}
			}
			persistidorCliente.modificarCliente(cliente);
		}

		return new ResultadoModificarCliente(errores.toArray(new ErrorModificarCliente[0]));
	}

	public ResultadoEliminarCliente eliminarCliente(Cliente cliente) throws PersistenciaException {
		ArrayList<ErrorEliminarCliente> errores = new ArrayList<>();

		Cliente clienteAuxiliar = persistidorCliente.obtenerCliente(new FiltroCliente(cliente.getTipoDocumento().getTipo(), cliente.getNumeroDocumento()));

		if(null == clienteAuxiliar){
			errores.add(ErrorEliminarCliente.No_Existe_Cliente);
		}

		if(errores.isEmpty()){
			ArrayList<Estado> estados = gestorDatos.obtenerEstados();
			for(Estado e: estados){
				if(e.getEstado().equals(EstadoStr.BAJA)){
					cliente.setEstado(e);
				}
			}
			persistidorCliente.modificarCliente(cliente);
		}

		return new ResultadoEliminarCliente(errores.toArray(new ErrorEliminarCliente[0]));
	}

	public ArrayList<Cliente> obtenerClientes() throws PersistenciaException {
		return persistidorCliente.listarClientes();
	}
}
