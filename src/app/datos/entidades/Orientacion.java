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
package app.datos.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import app.datos.clases.OrientacionStr;

@NamedQuery(name = "obtenerOrientaciones", query = "SELECT o FROM Orientacion o")
@Entity
@Table(name = "orientacion")
/*
 * Entidad que modela la orientacion física que tiene un inmueble.
 * Pertenece a la taskcard 12 de la iteración 1 y a la historia de usuario 3
 */
public class Orientacion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; //ID

	@Column(name = "orientacion_enum", length = 10)
	@Enumerated(EnumType.STRING)
	private OrientacionStr orientacion;

	public Orientacion() {
		super();
	}

	public Orientacion(OrientacionStr orientacion) {
		super();
		this.orientacion = orientacion;
	}

	public Integer getId() {
		return id;
	}

	public OrientacionStr getOrientacion() {
		return orientacion;
	}

	public Orientacion setOrientacion(OrientacionStr orientacion) {
		this.orientacion = orientacion;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((orientacion == null) ? 0 : orientacion.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj){
			return true;
		}
		if(obj == null){
			return false;
		}
		if(getClass() != obj.getClass()){
			return false;
		}
		Orientacion other = (Orientacion) obj;
		if(id == null){
			if(other.id != null){
				return false;
			}
		}
		else if(!id.equals(other.id)){
			return false;
		}
		else{
			return true;
		}
		if(orientacion != other.orientacion){
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return orientacion.toString();
	}
}
