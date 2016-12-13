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
package app.logica;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import app.datos.clases.CatalogoVista;
import app.datos.clases.DatosLogin;
import app.datos.clases.FiltroInmueble;
import app.datos.clases.FiltroPropietario;
import app.datos.entidades.Barrio;
import app.datos.entidades.Calle;
import app.datos.entidades.Cliente;
import app.datos.entidades.Inmueble;
import app.datos.entidades.Localidad;
import app.datos.entidades.Orientacion;
import app.datos.entidades.PDF;
import app.datos.entidades.Pais;
import app.datos.entidades.Propietario;
import app.datos.entidades.Provincia;
import app.datos.entidades.Reserva;
import app.datos.entidades.TipoDocumento;
import app.datos.entidades.TipoInmueble;
import app.datos.entidades.Vendedor;
import app.datos.entidades.Venta;
import app.excepciones.GestionException;
import app.excepciones.PersistenciaException;
import app.logica.gestores.GestorCatalogo;
import app.logica.gestores.GestorCliente;
import app.logica.gestores.GestorDatos;
import app.logica.gestores.GestorInmueble;
import app.logica.gestores.GestorPDF;
import app.logica.gestores.GestorPropietario;
import app.logica.gestores.GestorReserva;
import app.logica.gestores.GestorVendedor;
import app.logica.gestores.GestorVenta;
import app.logica.resultados.ResultadoAutenticacion;
import app.logica.resultados.ResultadoCrearCatalogo;
import app.logica.resultados.ResultadoCrearCliente;
import app.logica.resultados.ResultadoCrearInmueble;
import app.logica.resultados.ResultadoCrearPropietario;
import app.logica.resultados.ResultadoCrearReserva;
import app.logica.resultados.ResultadoCrearVendedor;
import app.logica.resultados.ResultadoCrearVenta;
import app.logica.resultados.ResultadoEliminarCliente;
import app.logica.resultados.ResultadoEliminarInmueble;
import app.logica.resultados.ResultadoEliminarPropietario;
import app.logica.resultados.ResultadoEliminarReserva;
import app.logica.resultados.ResultadoEliminarVendedor;
import app.logica.resultados.ResultadoModificarCliente;
import app.logica.resultados.ResultadoModificarInmueble;
import app.logica.resultados.ResultadoModificarPropietario;
import app.logica.resultados.ResultadoModificarVendedor;

@Service
public class CoordinadorJavaFX {

	@Resource
	GestorVendedor gestorVendedor;

	@Resource
	GestorCliente gestorCliente;

	@Resource
	GestorPropietario gestorPropietario;

	@Resource
	GestorInmueble gestorInmueble;

	@Resource
	GestorDatos gestorDatos;

	@Resource
	GestorReserva gestorReserva;

	@Resource
	GestorVenta gestorVenta;

	@Resource
	GestorCatalogo gestorCatalogo;

	@Resource
	GestorPDF gestorPDF;

	public ResultadoAutenticacion autenticarVendedor(DatosLogin login) throws PersistenciaException {
		return gestorVendedor.autenticarVendedor(login);
	}

	public ResultadoCrearVendedor crearVendedor(Vendedor vendedor) throws PersistenciaException, GestionException {
		return gestorVendedor.crearVendedor(vendedor);
	}

	public ArrayList<TipoDocumento> obtenerTiposDeDocumento() throws PersistenciaException {
		return gestorDatos.obtenerTiposDeDocumento();
	}

	public ResultadoModificarVendedor modificarVendedor(Vendedor vendedor) throws PersistenciaException, GestionException {
		return gestorVendedor.modificarVendedor(vendedor);
	}

	public ArrayList<Vendedor> obtenerVendedores() throws PersistenciaException {
		return gestorVendedor.obtenerVendedores();
	}

	public ResultadoCrearCliente crearCliente(Cliente cliente) throws PersistenciaException, GestionException {
		return gestorCliente.crearCliente(cliente);
	}

	public ResultadoModificarCliente modificarCliente(Cliente cliente) throws PersistenciaException {
		return gestorCliente.modificarCliente(cliente);
	}

	public ResultadoEliminarCliente eliminarCliente(Cliente cliente) throws PersistenciaException {
		return gestorCliente.eliminarCliente(cliente);
	}

	public ArrayList<Cliente> obtenerClientes() throws PersistenciaException {
		return gestorCliente.obtenerClientes();
	}

	public ResultadoCrearPropietario crearPropietario(Propietario propietario) throws PersistenciaException, GestionException {
		return gestorPropietario.crearPropietario(propietario);
	}

	public ResultadoModificarPropietario modificarPropietario(Propietario propietario) throws PersistenciaException {
		return gestorPropietario.modificarPropietario(propietario);
	}

	public ResultadoEliminarPropietario eliminarPropietario(Propietario propietario) throws PersistenciaException {
		return gestorPropietario.eliminarPropietario(propietario);
	}

	public ArrayList<Propietario> obtenerPropietarios() throws PersistenciaException {
		return gestorPropietario.obtenerPropietarios();
	}

	public ResultadoCrearInmueble crearInmueble(Inmueble inmueble) throws PersistenciaException {
		return gestorInmueble.crearInmueble(inmueble);
	}

	public ResultadoModificarInmueble modificarInmueble(Inmueble inmbueble) throws PersistenciaException {
		return gestorInmueble.modificarInmueble(inmbueble);
	}

	public ResultadoEliminarInmueble eliminarInmueble(Inmueble inmbueble) throws PersistenciaException {
		return gestorInmueble.eliminarInmueble(inmbueble);
	}

	public ArrayList<Inmueble> obtenerInmuebles() throws PersistenciaException {
		return gestorInmueble.obtenerInmuebles();
	}

	public ArrayList<Inmueble> obtenerInmuebles(FiltroInmueble filtro) throws PersistenciaException {
		return gestorInmueble.obtenerInmuebles(filtro);
	}

	public ArrayList<TipoInmueble> obtenerTiposInmueble() throws PersistenciaException {
		return gestorDatos.obtenerTiposInmueble();
	}

	public ArrayList<Provincia> obtenerProvinciasDe(Pais pais) throws PersistenciaException {
		return gestorDatos.obtenerProvinciasDe(pais);
	}

	public ArrayList<Localidad> obtenerLocalidadesDe(Provincia prov) throws PersistenciaException {
		return gestorDatos.obtenerLocalidadesDe(prov);
	}

	public ArrayList<Pais> obtenerPaises() throws PersistenciaException {
		return gestorDatos.obtenerPaises();
	}

	public ResultadoEliminarVendedor eliminarVendedor(Vendedor vendedor) throws PersistenciaException {
		return gestorVendedor.eliminarVendedor(vendedor);
	}

	public Vendedor obtenerVendedor(Vendedor vendedor) throws PersistenciaException {
		return gestorVendedor.obtenerVendedor(vendedor);
	}

	public ArrayList<Barrio> obtenerBarriosDe(Localidad localidad) throws PersistenciaException {
		return gestorDatos.obtenerBarriosDe(localidad);
	}

	public ArrayList<Calle> obtenerCallesDe(Localidad localidad) throws PersistenciaException {
		return gestorDatos.obtenerCallesDe(localidad);
	}

	public Propietario obtenerPropietario(FiltroPropietario filtro) throws PersistenciaException {
		return gestorPropietario.obtenerPropietario(filtro);
	}

	public ArrayList<Orientacion> obtenerOrientaciones() throws PersistenciaException {
		return gestorDatos.obtenerOrientaciones();
	}

	public ResultadoCrearReserva crearReserva(Reserva reserva) throws PersistenciaException, GestionException {
		return gestorReserva.crearReserva(reserva);
	}

	public ResultadoEliminarReserva eliminarReserva(Reserva reserva) throws PersistenciaException {
		return gestorReserva.eliminarReserva(reserva);
	}

	public ResultadoCrearVenta crearVenta(Venta venta) throws PersistenciaException, GestionException {
		return gestorVenta.crearVenta(venta);
	}

	public ResultadoCrearCatalogo crearCatalogo(CatalogoVista catalogoVista) throws PersistenciaException, GestionException {
		return gestorCatalogo.crearCatalogo(catalogoVista);
	}

	public void imprimirPDF(PDF pdf) throws GestionException {
		gestorPDF.imprimirPDF(pdf);
	}
}