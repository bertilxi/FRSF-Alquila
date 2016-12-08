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

import java.util.HashSet;
import java.util.Set;

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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import app.comun.FormateadorString;

@NamedQueries(value = { @NamedQuery(name = "obtenerPropietarios", query = "SELECT p FROM Propietario p WHERE p.estado.estado = 'ALTA'"),
		@NamedQuery(name = "obtenerPropietario", query = "SELECT p FROM Propietario p WHERE p.numeroDocumento = :documento AND p.tipoDocumento.tipo = :tipoDocumento") })
@Entity
@Table(name = "propietario", uniqueConstraints = @UniqueConstraint(name = "propietario_numerodocumento_idtipo_uk", columnNames = { "numerodocumento", "idtipo" }))
/**
 * Entidad que modela a un propietario
 */
public class Propietario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; //ID

	@Column(name = "nombre", length = 30)
	private String nombre;

	@Column(name = "apellido", length = 30)
	private String apellido;

	@Column(name = "numerodocumento", length = 30)
	private String numeroDocumento;

	@Column(name = "telefono", length = 30)
	private String telefono;

	@Column(name = "email", length = 30)
	private String email;

	//Reclaciones
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idtipo", referencedColumnName = "id", foreignKey = @ForeignKey(name = "propietario_idtipo_fk"))
	private TipoDocumento tipoDocumento;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "iddireccion", referencedColumnName = "id", foreignKey = @ForeignKey(name = "propietario_iddireccion_fk"))
	private Direccion direccion;

	//Opcionales
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "propietario")
	@Transient
	private Set<Inmueble> inmuebles;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idestado", referencedColumnName = "id", foreignKey = @ForeignKey(name = "propietario_idestado_fk"), nullable = false)
	private Estado estado;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "propietario")
	@Transient
	private Set<Venta> ventas;

	public Propietario() {
		super();
		this.inmuebles = new HashSet<>();
		this.ventas = new HashSet<>();
	}

	public Propietario(Integer id, String nombre, String apellido, String numeroDocumento, String telefono, String email, TipoDocumento tipoDocumento, Direccion direccion) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.numeroDocumento = numeroDocumento;
		this.telefono = telefono;
		this.email = email;
		this.tipoDocumento = tipoDocumento;
		this.direccion = direccion;
	}

	public Integer getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public Propietario setNombre(String nombre) {
		this.nombre = nombre;
		return this;
	}

	public String getApellido() {
		return apellido;
	}

	public Propietario setApellido(String apellido) {
		this.apellido = apellido;
		return this;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public Propietario setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
		return this;
	}

	public String getTelefono() {
		return telefono;
	}

	public Propietario setTelefono(String telefono) {
		this.telefono = telefono;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public Propietario setEmail(String email) {
		this.email = email;
		return this;
	}

	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	public Propietario setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
		return this;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public Propietario setDireccion(Direccion direccion) {
		this.direccion = direccion;
		return this;
	}

	public Set<Inmueble> getInmuebles() {
		return inmuebles;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Set<Venta> getVentas() {
		return ventas;
	}

	public Propietario setVentas(Set<Venta> ventas) {
		this.ventas = ventas;
		return this;
	}

	public Propietario setInmuebles(Set<Inmueble> inmuebles) {
		this.inmuebles = inmuebles;
		return this;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((apellido == null) ? 0 : apellido.hashCode());
		result = prime * result + ((direccion == null) ? 0 : direccion.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((numeroDocumento == null) ? 0 : numeroDocumento.hashCode());
		result = prime * result + ((tipoDocumento == null) ? 0 : tipoDocumento.hashCode());
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
		Propietario other = (Propietario) obj;
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
		if(numeroDocumento == null){
			if(other.numeroDocumento != null){
				return false;
			}
		}
		else if(!numeroDocumento.equals(other.numeroDocumento)){
			return false;
		}
		if(tipoDocumento == null){
			if(other.tipoDocumento != null){
				return false;
			}
		}
		else if(!tipoDocumento.equals(other.tipoDocumento)){
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		FormateadorString formateador = new FormateadorString();
		return formateador.primeraMayuscula(nombre) + " " + formateador.primeraMayuscula(apellido);
	}
}
