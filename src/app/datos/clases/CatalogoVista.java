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

import java.util.Map;

import app.datos.entidades.Cliente;
import app.datos.entidades.Imagen;
import app.datos.entidades.Inmueble;

public class CatalogoVista {

	private Cliente cliente;
	private Map<Inmueble, Imagen> fotos;

	public CatalogoVista() {
		super();
	}

	public CatalogoVista(Cliente cliente, Map<Inmueble, Imagen> fotos) {
		super();
		this.cliente = cliente;
		this.fotos = fotos;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public Map<Inmueble, Imagen> getFotos() {
		return fotos;
	}
}
