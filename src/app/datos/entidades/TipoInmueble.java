package app.datos.entidades;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import app.datos.clases.TipoInmuebleStr;

@NamedQuery(name = "obtenerTiposDeInmueble", query = "SELECT t FROM TipoInmueble t")
@Entity
@Table(name = "tipo_inmueble")
public class TipoInmueble {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; //ID

	@Enumerated
	private TipoInmuebleStr tipo;

	public TipoInmueble() {
		super();
	}

	public TipoInmueble(TipoInmuebleStr tipo) {
		super();
		this.tipo = tipo;
	}

	public Integer getId() {
		return id;
	}

	public TipoInmuebleStr getTipo() {
		return tipo;
	}

	public void setTipo(TipoInmuebleStr tipo) {
		this.tipo = tipo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
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
		TipoInmueble other = (TipoInmueble) obj;
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
		if(tipo != other.tipo){
			return false;
		}
		return true;
	}
}
