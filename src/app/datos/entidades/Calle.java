package app.datos.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "calle", uniqueConstraints = @UniqueConstraint(name = "calle_nombre_idlocalidad_uk", columnNames = { "nombre", "idlocalidad" }))
public class Calle {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; //ID

	@Column(name = "nombre", length = 30, nullable = false)
	private String nombre;

	//Relaciones
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idlocalidad", referencedColumnName = "id", foreignKey = @ForeignKey(name = "calle_idlocalidad_fk"), nullable = false)
	private Localidad localidad;

	public Calle() {
		super();
	}

	public Calle(String nombre, Localidad localidad) {
		super();
		this.nombre = nombre;
		this.localidad = localidad;
	}

	public Integer getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Localidad getLocalidad() {
		return localidad;
	}

	public void setLocalidad(Localidad localidad) {
		this.localidad = localidad;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((localidad == null) ? 0 : localidad.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
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
		Calle other = (Calle) obj;
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
		if(localidad == null){
			if(other.localidad != null){
				return false;
			}
		}
		else if(!localidad.equals(other.localidad)){
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
		return true;
	}
}
