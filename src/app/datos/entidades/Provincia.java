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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import app.comun.FormateadorString;

@NamedQuery(name = "obtenerProvinciasDe", query = "SELECT p FROM Provincia p WHERE pais=:pa ORDER BY p.nombre ASC")
@Entity
@Table(name = "provincia", uniqueConstraints = @UniqueConstraint(name = "provincia_nombre_idpais_uk", columnNames = { "nombre", "idpais" }))
/*
 * Entidad que modela una provincia.
 * Pertenece a la taskcard 12 de la iteración 1 y a la historia de usuario 3
 */
public class Provincia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; //ID

	@Column(name = "nombre", length = 50, nullable = false)
	private String nombre;

	//Relaciones
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "idpais", referencedColumnName = "id", foreignKey = @ForeignKey(name = "provincia_idpais_fk"), nullable = false)
	private Pais pais;

	public Provincia() {
		super();
		this.nombre = "";
	}

	public Provincia(String nombre, Pais pais) {
		super();
		this.nombre = nombre;
		this.pais = pais;
	}

	public Integer getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public Provincia setNombre(String nombre) {
		this.nombre = nombre;
		return this;
	}

	public Pais getPais() {
		return pais;
	}

	public Provincia setPais(Pais pais) {
		this.pais = pais;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((pais == null) ? 0 : pais.hashCode());
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
		Provincia other = (Provincia) obj;
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
		if(nombre == null){
			if(other.nombre != null){
				return false;
			}
		}
		else if(!nombre.equals(other.nombre)){
			return false;
		}
		if(pais == null){
			if(other.pais != null){
				return false;
			}
		}
		else if(!pais.equals(other.pais)){
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return new FormateadorString().primeraMayuscula(nombre);
	}

}
