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

import java.util.Date;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQuery(name = "obtenerInmuebles", query = "SELECT i FROM Inmueble i WHERE i.estado.estado = 'ALTA'")
@Table(name = "inmueble")
/**
 * Entidad que modela un inmueble
 * Pertenece a la taskcard 11 de la iteración 1 y a la historia 3
 */
public class Inmueble {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id; //ID

	@Column(name = "observaciones", length = 300)
	private String observaciones;

	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_carga", nullable = false)
	private Date fechaCarga;

	@Column(name = "precio", nullable = false)
	private Double precio;

	@Column(name = "frente")
	private Double frente; // en metros

	@Column(name = "fondo")
	private Double fondo; // en metros

	@Column(name = "superficie")
	private Double superficie; // en metros cuadrados

	//Relaciones

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idtipo", referencedColumnName = "id", foreignKey = @ForeignKey(name = "inmueble_idtipo_fk"), nullable = false)
	private TipoInmueble tipo;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idorientacion", referencedColumnName = "id", foreignKey = @ForeignKey(name = "inmueble_idorientacion_fk"))
	private Orientacion orientacion;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "iddireccion", referencedColumnName = "id", foreignKey = @ForeignKey(name = "inmueble_iddireccion_fk"), nullable = false, unique = true)
	private Direccion direccion;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idpropietario", referencedColumnName = "id", foreignKey = @ForeignKey(name = "inmueble_idpropietario_fk"), nullable = false)
	private Propietario propietario;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "inmueble_imagen", joinColumns = @JoinColumn(name = "idinmueble"), foreignKey = @ForeignKey(name = "inmueble_imagen_idinmueble_fk"), inverseJoinColumns = @JoinColumn(name = "idimagen"), inverseForeignKey = @ForeignKey(name = "inmueble_imagen_idimagen_fk"))
	private Set<Imagen> fotos;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idestado", referencedColumnName = "id", foreignKey = @ForeignKey(name = "inmueble_idestado_fk"), nullable = false)
	private Estado estado;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idestadoinmueble", referencedColumnName = "id", foreignKey = @ForeignKey(name = "inmueble_idestadoinmueble_fk"), nullable = false)
	private EstadoInmueble estadoInmueble; //Establece si el inmueble fue vendido o no

	@OneToOne(mappedBy = "inmueble", cascade = CascadeType.ALL, orphanRemoval = true, optional = false)
	private DatosEdificio datosEdificio;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "inmueble")
	private Venta venta;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "inmueble")
	private Set<Reserva> reservas;

	public Inmueble() {
		super();
		fotos = new HashSet<>();
		reservas = new HashSet<>();
	}

	public Inmueble(String observaciones, Date fechaCarga, Double precio, Double frente, Double fondo, Double superficie, TipoInmueble tipo, Direccion direccion, Propietario propietario, Estado estado) {
		this();
		this.observaciones = observaciones;
		this.fechaCarga = fechaCarga;
		this.precio = precio;
		this.frente = frente;
		this.fondo = fondo;
		this.superficie = superficie;
		this.tipo = tipo;
		this.direccion = direccion;
		this.propietario = propietario;
		this.estado = estado;
	}

	public Integer getId() {
		return id;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public Inmueble setObservaciones(String observaciones) {
		this.observaciones = observaciones;
		return this;
	}

	public Date getFechaCarga() {
		return fechaCarga;
	}

	public Inmueble setFechaCarga(Date fechaCarga) {
		this.fechaCarga = fechaCarga;
		return this;
	}

	public Double getPrecio() {
		return precio;
	}

	public Inmueble setPrecio(Double precio) {
		this.precio = precio;
		return this;
	}

	public Double getFrente() {
		return frente;
	}

	public Inmueble setFrente(Double frente) {
		this.frente = frente;
		return this;
	}

	public Double getFondo() {
		return fondo;
	}

	public Inmueble setFondo(Double fondo) {
		this.fondo = fondo;
		return this;
	}

	public Double getSuperficie() {
		return superficie;
	}

	public Inmueble setSuperficie(Double superficie) {
		this.superficie = superficie;
		return this;
	}

	public TipoInmueble getTipo() {
		return tipo;
	}

	public Inmueble setTipo(TipoInmueble tipo) {
		this.tipo = tipo;
		return this;
	}

	public Orientacion getOrientacion() {
		return orientacion;
	}

	public Inmueble setOrientacion(Orientacion orientacion) {
		this.orientacion = orientacion;
		return this;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public Inmueble setDireccion(Direccion direccion) {
		this.direccion = direccion;
		return this;
	}

	public Propietario getPropietario() {
		return propietario;
	}

	public Inmueble setPropietario(Propietario propietario) {
		this.propietario = propietario;
		return this;
	}

	public Set<Imagen> getFotos() {
		return fotos;
	}

	public Estado getEstado() {
		return estado;
	}

	public Inmueble setEstado(Estado estado) {
		this.estado = estado;
		return this;
	}

	public EstadoInmueble getEstadoInmueble() {
		return estadoInmueble;
	}

	public Inmueble setEstadoInmueble(EstadoInmueble estadoInmueble) {
		this.estadoInmueble = estadoInmueble;
		return this;
	}

	public DatosEdificio getDatosEdificio() {
		return datosEdificio;
	}

	public Inmueble setDatosEdificio(DatosEdificio datosEdificio) {
		this.datosEdificio = datosEdificio;
		return this;
	}

	public Set<Reserva> getReservas() {
		return reservas;
	}

	public Inmueble setReservas(Set<Reserva> reservas) {
		this.reservas = reservas;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((direccion == null) ? 0 : direccion.hashCode());
		result = prime * result + ((fechaCarga == null) ? 0 : fechaCarga.hashCode());
		result = prime * result + ((fondo == null) ? 0 : fondo.hashCode());
		result = prime * result + ((frente == null) ? 0 : frente.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((observaciones == null) ? 0 : observaciones.hashCode());
		result = prime * result + ((precio == null) ? 0 : precio.hashCode());
		result = prime * result + ((superficie == null) ? 0 : superficie.hashCode());
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
		Inmueble other = (Inmueble) obj;
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
		if(fechaCarga == null){
			if(other.fechaCarga != null){
				return false;
			}
		}
		else if(!fechaCarga.equals(other.fechaCarga)){
			return false;
		}
		if(fondo == null){
			if(other.fondo != null){
				return false;
			}
		}
		else if(!fondo.equals(other.fondo)){
			return false;
		}
		if(frente == null){
			if(other.frente != null){
				return false;
			}
		}
		else if(!frente.equals(other.frente)){
			return false;
		}
		if(observaciones == null){
			if(other.observaciones != null){
				return false;
			}
		}
		else if(!observaciones.equals(other.observaciones)){
			return false;
		}
		if(precio == null){
			if(other.precio != null){
				return false;
			}
		}
		else if(!precio.equals(other.precio)){
			return false;
		}
		if(superficie == null){
			if(other.superficie != null){
				return false;
			}
		}
		else if(!superficie.equals(other.superficie)){
			return false;
		}
		if(direccion == null){
			if(other.direccion != null){
				return false;
			}
		}
		else if(!direccion.equals(other.direccion)){
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		if(direccion != null){
			return direccion.toString();
		}
		if(id != null){
			return "Inmueble N° " + id.toString();
		}
		return super.toString();
	}
}
