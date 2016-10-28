package app.datos.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import app.datos.clases.EstadoStr;

@Entity
@Table(name = "estado")
public class Estado {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; //ID

	@Column(name = "estado", unique = true)
	@Enumerated(EnumType.STRING)
	private EstadoStr estado;

	public Estado() {
		super();
	}

	public Estado(EstadoStr estado) {
		super();
		this.estado = estado;
	}

	public Integer getId() {
		return id;
	}

	public EstadoStr getEstado() {
		return estado;
	}

	public Estado setEstado(EstadoStr estado) {
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
		Estado other = (Estado) obj;
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
}
