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
import javax.persistence.UniqueConstraint;

import app.comun.FormateadorString;

@NamedQueries(value = { @NamedQuery(name = "obtenerVendedor", query = "SELECT v FROM Vendedor v WHERE numeroDocumento = :documento AND tipoDocumento.tipo = :tipoDocumento"), @NamedQuery(name = "listarVendedores", query = "SELECT v FROM Vendedor v WHERE v.estado.estado = 'ALTA'") })
@Entity
@Table(name = "vendedor", uniqueConstraints = @UniqueConstraint(name = "vendedor_numerodocumento_idtipo_uk", columnNames = { "numerodocumento", "idtipo" }))
/**
 * Entidad que modela a un vendedor
 *  Task card 2 de la iteración 1, historia de usuario 1
 */
public class Vendedor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Integer id; //ID

	@Column(name = "nombre", length = 30, nullable = false)
	private String nombre;

	@Column(name = "apellido", length = 30, nullable = false)
	private String apellido;

	@Column(name = "numerodocumento", length = 30, nullable = false)
	private String numeroDocumento;

	@Column(name = "password", length = 100, nullable = false)
	private String password;

	@Column(name = "salt", nullable = false)
	private String salt;

	@Column(name = "root", nullable = false)
	private Boolean root;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idtipo", referencedColumnName = "id", foreignKey = @ForeignKey(name = "vendedor_idtipo_fk"), nullable = false)
	private TipoDocumento tipoDocumento;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "vendedor")
	private Set<Venta> ventas;

	//estado lógico que tiene la entidad
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idestado", referencedColumnName = "id", foreignKey = @ForeignKey(name = "vendedor_idestado_fk"), nullable = false)
	private Estado estado;

	public Vendedor() {
		super();
		this.ventas = new HashSet<>();
		this.root = false;
	}

	public Vendedor(Integer id, String nombre, String apellido, String numeroDocumento, String password, String salt, TipoDocumento tipoDocumento, Estado estado) {
		this();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.numeroDocumento = numeroDocumento;
		this.password = password;
		this.salt = salt;
		this.tipoDocumento = tipoDocumento;
		this.estado = estado;
	}

	public Integer getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public Vendedor setNombre(String nombre) {
		this.nombre = nombre;
		return this;
	}

	public String getApellido() {
		return apellido;
	}

	public Vendedor setApellido(String apellido) {
		this.apellido = apellido;
		return this;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public Vendedor setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public Vendedor setPassword(String password) {
		this.password = password;
		return this;
	}

	public String getSalt() {
		return salt;
	}

	public Vendedor setSalt(String salt) {
		this.salt = salt;
		return this;
	}

	public Boolean getRoot() {
		return root;
	}

	public Vendedor setRoot(Boolean root) {
		this.root = root;
		return this;
	}

	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	public Vendedor setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
		return this;
	}

	public Set<Venta> getVentas() {
		return ventas;
	}

	public Vendedor setEstado(Estado estado) {
		this.estado = estado;
		return this;
	}

	public Estado getEstado() {
		return estado;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o){
			return true;
		}
		if(!(o instanceof Vendedor)){
			return false;
		}

		Vendedor vendedor = (Vendedor) o;

		if(id == null){
			if(vendedor.getId() != null){
				return false;
			}
		}
		else if(!id.equals(vendedor.getId())){
			return false;
		}
		else{
			return true;
		}
		if(numeroDocumento == null){
			if(vendedor.numeroDocumento != null){
				return false;
			}
		}
		else if(!numeroDocumento.equals(vendedor.numeroDocumento)){
			return false;
		}
		if(tipoDocumento == null){
			if(vendedor.tipoDocumento != null){
				return false;
			}
		}
		else if(!tipoDocumento.equals(vendedor.tipoDocumento)){
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((numeroDocumento == null) ? 0 : numeroDocumento.hashCode());
		result = prime * result + ((tipoDocumento == null) ? 0 : tipoDocumento.hashCode());
		return result;
	}

	@Override
	public String toString() {
		FormateadorString formateador = new FormateadorString();
		return formateador.nombrePropio(nombre) + " " + formateador.nombrePropio(apellido);
	}
}
