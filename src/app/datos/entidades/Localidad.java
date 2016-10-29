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
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@NamedQuery(name = "obtenerLocalidadesDe", query = "SELECT l FROM Localidad l WHERE provincia=:prov")
@Entity
@Table(name = "localidad", uniqueConstraints = @UniqueConstraint(name = "localidad_nombre_idprovincia_uk", columnNames = { "nombre", "idprovincia" }))
public class Localidad {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; //ID

	@Column(name = "nombre", length = 30, nullable = false)
	private String nombre;

	//Relaciones
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idprovincia", referencedColumnName = "id", foreignKey = @ForeignKey(name = "localidad_idprovincia_fk"), nullable = false)
	private Provincia provincia;

	public Localidad() {
		super();
	}

	public Localidad(String nombre, Provincia provincia) {
		super();
		this.nombre = nombre;
		this.provincia = provincia;
	}

	public Integer getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public Localidad setNombre(String nombre) {
		this.nombre = nombre;
		return this;
	}

	public Provincia getProvincia() {
		return provincia;
	}

	public Localidad setProvincia(Provincia provincia) {
		this.provincia = provincia;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((provincia == null) ? 0 : provincia.hashCode());
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
		Localidad other = (Localidad) obj;
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
		if(nombre == null){
			if(other.nombre != null){
				return false;
			}
		}
		else if(!nombre.equals(other.nombre)){
			return false;
		}
		if(provincia == null){
			if(other.provincia != null){
				return false;
			}
		}
		else if(!provincia.equals(other.provincia)){
			return false;
		}
		return true;
	}
}
