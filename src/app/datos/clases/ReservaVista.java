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
package app.datos.clases;

import java.util.Date;

import app.datos.entidades.Cliente;
import app.datos.entidades.Inmueble;

public class ReservaVista {

	private Cliente cliente;
	private Inmueble inmuble;
	private Double importe;
	private Date fechaInicio;
	private Date fechaFin;

	public ReservaVista(Cliente cliente, Inmueble inmuble, Double importe, Date fechaInicio, Date fechaFin) {
		super();
		this.cliente = cliente;
		this.inmuble = inmuble;
		this.importe = importe;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public Inmueble getInmuble() {
		return inmuble;
	}

	public Double getImporte() {
		return importe;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}
}
