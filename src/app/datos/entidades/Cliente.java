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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@NamedQueries(value = { @NamedQuery(name = "obtenerClientes", query = "SELECT c FROM Cliente c WHERE c.estado.estado = 'ALTA'"), @NamedQuery(name = "obtenerCliente", query = "SELECT c FROM Cliente c WHERE c.numeroDocumento = :documento AND c.tipoDocumento.tipo = :tipoDocumento") })
@Entity
@Table(name = "cliente", uniqueConstraints = @UniqueConstraint(name = "cliente_numerodocumento_idtipodocumento_uk", columnNames = { "numerodocumento", "idtipodocumento" }))
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id; //ID

	@Column(name = "nombre", length = 100, nullable = true)
	private String nombre;

	@Column(name = "apellido", length = 100, nullable = true)
	private String apellido;

	@Column(name = "numerodocumento", length = 20, nullable = true)
	private String numeroDocumento;

	@Column(name = "telefono", length = 20)
	private String telefono;

	//Relaciones

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idtipodocumento", referencedColumnName = "id", foreignKey = @ForeignKey(name = "cliente_idtipodocumento_fk"), nullable = true)
	private TipoDocumento tipoDocumento;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idestado", referencedColumnName = "id", foreignKey = @ForeignKey(name = "cliente_idestado_fk"), nullable = false)
	private Estado estado;

	@OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true, optional = false)
	private InmuebleBuscado inmuebleBuscado;

	public Cliente() {
		super();
	}

	public Cliente(String nombre, String apellido, String numeroDocumento, String telefono, Estado estado, TipoDocumento tipoDocumento, InmuebleBuscado inmuebleBuscado) {
		this();
		this.nombre = nombre;
		this.apellido = apellido;
		this.numeroDocumento = numeroDocumento;
		this.telefono = telefono;
		this.estado = estado;
		this.tipoDocumento = tipoDocumento;
		this.inmuebleBuscado = inmuebleBuscado;
	}

	public Integer getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public Cliente setNombre(String nombre) {
		this.nombre = nombre;
		return this;
	}

	public String getApellido() {
		return apellido;
	}

	public Cliente setApellido(String apellido) {
		this.apellido = apellido;
		return this;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public Cliente setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
		return this;
	}

	public String getTelefono() {
		return telefono;
	}

	public Cliente setTelefono(String telefono) {
		this.telefono = telefono;
		return this;
	}

	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	public Cliente setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
		return this;
	}

	public Estado getEstado() {
		return estado;
	}

	public Cliente setEstado(Estado estado) {
		this.estado = estado;
		return this;
	}

	public InmuebleBuscado getInmuebleBuscado() {
		return inmuebleBuscado;
	}

	public Cliente setInmuebleBuscado(InmuebleBuscado inmuebleBuscado) {
		this.inmuebleBuscado = inmuebleBuscado;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((apellido == null) ? 0 : apellido.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((numeroDocumento == null) ? 0 : numeroDocumento.hashCode());
		result = prime * result + ((telefono == null) ? 0 : telefono.hashCode());
		result = prime * result + ((tipoDocumento == null) ? 0 : tipoDocumento.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
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
		Cliente other = (Cliente) obj;
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
		if(apellido == null){
			if(other.apellido != null){
				return false;
			}
		}
		else if(!apellido.equals(other.apellido)){
			return false;
		}
		if(nombre == null){
			if(other.nombre != null){
				return false;
			}
		}
		else if(!nombre.equals(other.nombre)){
			return false;
		}
		if(telefono == null){
			if(other.telefono != null){
				return false;
			}
		}
		else if(!telefono.equals(other.telefono)){
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
}
