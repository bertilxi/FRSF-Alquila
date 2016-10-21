package app.datos.entidades;

import app.datos.clases.TipoInmuebleStr;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tipo_inmueble")
public class TipoInmueble {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; //ID
	
	@Enumerated
    private TipoInmuebleStr tipo;

	//TODO Borrar si no se usa
    //Relaciones
    //private ArrayList<InmuebleBuscado> inmueblesBuscados; //Relacion muchos a muchos
	
	public TipoInmueble() {
		super();
	}
	
	public TipoInmueble(Integer id, TipoInmuebleStr tipo) {
		super();
		this.id = id;
		this.tipo = tipo;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TipoInmueble other = (TipoInmueble) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (tipo != other.tipo)
			return false;
		return true;
	}
}
