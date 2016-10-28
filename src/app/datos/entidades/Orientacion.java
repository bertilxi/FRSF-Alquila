package app.datos.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import app.datos.clases.OrientacionStr;

@Entity
@Table(name = "orientacion")
public class Orientacion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; //ID

	@Column(name = "orientacion_enum")
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

	public void setOrientacion(OrientacionStr orientacion) {
		this.orientacion = orientacion;
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
}
