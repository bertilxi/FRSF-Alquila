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

import app.comun.EncriptadorPassword;
import app.comun.ValidadorFormato;
import app.datos.clases.DatosLogin;
import app.datos.clases.EstadoStr;
import app.datos.clases.FiltroVendedor;
import app.datos.entidades.Estado;
import app.datos.entidades.Vendedor;
import app.datos.servicios.VendedorService;
import app.excepciones.EntidadExistenteConEstadoBajaException;
import app.excepciones.GestionException;
import app.excepciones.PersistenciaException;
import app.logica.resultados.ResultadoAutenticacion;
import app.logica.resultados.ResultadoAutenticacion.ErrorAutenticacion;
import app.logica.resultados.ResultadoCrearVendedor;
import app.logica.resultados.ResultadoCrearVendedor.ErrorCrearVendedor;
import app.logica.resultados.ResultadoEliminarVendedor;
import app.logica.resultados.ResultadoEliminarVendedor.ErrorEliminarVendedor;
import app.logica.resultados.ResultadoModificarVendedor;
import app.logica.resultados.ResultadoModificarVendedor.ErrorModificarVendedor;

@Service
public class GestorVendedor {

	@Resource
	protected VendedorService persistidorVendedor;

	@Resource
	protected GestorDatos gestorDatos;

	@Resource
	protected ValidadorFormato validador;

	@Resource
	protected EncriptadorPassword encriptador;

	public ResultadoAutenticacion autenticarVendedor(DatosLogin datos) throws PersistenciaException {
		ArrayList<ErrorAutenticacion> errores = new ArrayList<>();

		//Compruebo que los datos no sean nulos ni vacios
		if(datos.getContrasenia() == null || datos.getContrasenia().length < 1 || datos.getDNI() == null || datos.getDNI().length() < 1 || datos.getTipoDocumento() == null){
			errores.add(ErrorAutenticacion.Datos_Incorrectos);
		}
		else{
			Vendedor vendedorAuxiliar = persistidorVendedor.obtenerVendedor(new FiltroVendedor(datos.getTipoDocumento().getTipo(), datos.getDNI()));

			//Si lo encuentra comprueba que la contraseña ingresada coincida con la de la base de datos
			if(vendedorAuxiliar == null || vendedorAuxiliar.getPassword() == null ||
					!vendedorAuxiliar.getPassword().equals(encriptador.encriptar(datos.getContrasenia(), vendedorAuxiliar.getSalt()))){
				//Si no coincide falla
				errores.add(ErrorAutenticacion.Datos_Incorrectos);
			}
		}
		return new ResultadoAutenticacion(errores.toArray(new ErrorAutenticacion[0]));
	}

	public ResultadoCrearVendedor crearVendedor(Vendedor vendedor) throws PersistenciaException, GestionException {
		ArrayList<ErrorCrearVendedor> errores = new ArrayList<>();

		if(!validador.validarNombre(vendedor.getNombre())){
			errores.add(ErrorCrearVendedor.Formato_Nombre_Incorrecto);
		}

		if(!validador.validarApellido(vendedor.getApellido())){
			errores.add(ErrorCrearVendedor.Formato_Apellido_Incorrecto);
		}

		if(!validador.validarDocumento(vendedor.getTipoDocumento(), vendedor.getNumeroDocumento())){
			errores.add(ErrorCrearVendedor.Formato_Documento_Incorrecto);
		}

		Vendedor vendedorAuxiliar = persistidorVendedor.obtenerVendedor(new FiltroVendedor(vendedor.getTipoDocumento().getTipo(), vendedor.getNumeroDocumento()));
		if(null != vendedorAuxiliar){
			if(vendedorAuxiliar.getEstado().getEstado().equals(EstadoStr.ALTA)){
				errores.add(ErrorCrearVendedor.Ya_Existe_Vendedor);
			}
			else{
				throw new EntidadExistenteConEstadoBajaException();
			}
		}

		if(errores.isEmpty()){
			ArrayList<Estado> estados = gestorDatos.obtenerEstados();
			for(Estado e: estados){
				if(e.getEstado().equals(EstadoStr.ALTA)){
					vendedor.setEstado(e);
				}
			}
			persistidorVendedor.guardarVendedor(vendedor);
		}

		return new ResultadoCrearVendedor(errores.toArray(new ErrorCrearVendedor[0]));
	}

	public ResultadoModificarVendedor modificarVendedor(Vendedor vendedor) throws PersistenciaException {
		ArrayList<ErrorModificarVendedor> errores = new ArrayList<>();

		if(!validador.validarNombre(vendedor.getNombre())){
			errores.add(ErrorModificarVendedor.Formato_Nombre_Incorrecto);
		}

		if(!validador.validarApellido(vendedor.getApellido())){
			errores.add(ErrorModificarVendedor.Formato_Apellido_Incorrecto);
		}

		if(!validador.validarDocumento(vendedor.getTipoDocumento(), vendedor.getNumeroDocumento())){
			errores.add(ErrorModificarVendedor.Formato_Documento_Incorrecto);
		}

		Vendedor vendedorAuxiliar = persistidorVendedor.obtenerVendedor(new FiltroVendedor(vendedor.getTipoDocumento().getTipo(), vendedor.getNumeroDocumento()));
		if(null != vendedorAuxiliar && !vendedor.equals(vendedorAuxiliar)){
			errores.add(ErrorModificarVendedor.Otro_Vendedor_Posee_Mismo_Documento_Y_Tipo);
		}

		if(errores.isEmpty()){
			if(vendedor.getEstado().getEstado().equals(EstadoStr.BAJA)){
				ArrayList<Estado> estados = gestorDatos.obtenerEstados();
				for(Estado e: estados){
					if(e.getEstado().equals(EstadoStr.ALTA)){
						vendedor.setEstado(e);
					}
				}
			}
			persistidorVendedor.modificarVendedor(vendedor);
		}

		return new ResultadoModificarVendedor(errores.toArray(new ErrorModificarVendedor[0]));
	}

	public ResultadoEliminarVendedor eliminarVendedor(Vendedor vendedor) throws PersistenciaException {
		ArrayList<ErrorEliminarVendedor> errores = new ArrayList<>();

		Vendedor vendedorAuxiliar = persistidorVendedor.obtenerVendedor(new FiltroVendedor(vendedor.getTipoDocumento().getTipo(), vendedor.getNumeroDocumento()));

		if(null == vendedorAuxiliar){
			errores.add(ErrorEliminarVendedor.No_Existe_Vendedor);
		}

		if(errores.isEmpty()){
			ArrayList<Estado> estados = gestorDatos.obtenerEstados();
			for(Estado e: estados){
				if(e.getEstado().equals(EstadoStr.BAJA)){
					vendedor.setEstado(e);
				}
			}
			persistidorVendedor.modificarVendedor(vendedor);
		}

		return new ResultadoEliminarVendedor(errores.toArray(new ErrorEliminarVendedor[0]));
	}

	public ArrayList<Vendedor> obtenerVendedores() throws PersistenciaException {
		return persistidorVendedor.listarVendedores();
	}

	public Vendedor obtenerVendedor(Vendedor vendedor) throws PersistenciaException {
		return persistidorVendedor.obtenerVendedor(new FiltroVendedor(vendedor.getTipoDocumento().getTipo(), vendedor.getNumeroDocumento()));
	}
}
