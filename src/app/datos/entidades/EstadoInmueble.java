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

import app.datos.clases.EstadoInmuebleStr;

/**
 * Entidad que modela los estados por los cuales pueden pasar un inmueble.
 * Pertenece a la taskcard 33 de la iteración 2 y a la historia de usuario 8
 */
@NamedQuery(name = "obtenerEstadosInmueble", query = "SELECT e FROM EstadoInmueble e")
@Entity
@Table(name = "estadoinmueble")
public class EstadoInmueble {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; //ID

	@Column(name = "estado", length = 20, unique = true)
	@Enumerated(EnumType.STRING)
	private EstadoInmuebleStr estado;

	public EstadoInmueble() {
		super();
	}

	public EstadoInmueble(EstadoInmuebleStr estado) {
		super();
		this.estado = estado;
	}

	public Integer getId() {
		return id;
	}

	public EstadoInmuebleStr getEstado() {
		return estado;
	}

	public EstadoInmueble setEstado(EstadoInmuebleStr estado) {
		this.estado = estado;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		EstadoInmueble other = (EstadoInmueble) obj;
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
		if(estado != other.estado){
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return estado.toString();
	}
}
