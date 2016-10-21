package app.datos.entidades;

import java.util.ArrayList;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "propietario", uniqueConstraints = @UniqueConstraint(name = "propietario_numerodocumento_idtipo_uk", columnNames = { "numerodocumento", "idtipo" }))
public class Propietario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; //ID

	@Column(name = "nombre", length = 30)
	private String nombre;

	@Column(name = "apellido", length = 30)
	private String apellido;

	@Column(name = "numerodocumento", length = 30)
	private String numeroDocumento;

	@Column(name = "telefono", length = 30)
	private String telefono;

	@Column(name = "email", length = 30)
	private String email;

	//Reclaciones
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idtipo", referencedColumnName = "id", foreignKey = @ForeignKey(name = "propietario_idtipo_fk"))
	private TipoDocumento tipoDocumento;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "iddireccion", referencedColumnName = "id", foreignKey = @ForeignKey(name = "propietario_iddireccion_fk"))
	private Direccion direccion;

	//Opcionales
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "propietario")
	@Transient
	private ArrayList<Inmueble> inmuebles;

	public Propietario() {
		super();
		this.inmuebles = new ArrayList<>();
	}

	public Propietario(Integer id, String nombre, String apellido, String numeroDocumento, String telefono,
			String email, TipoDocumento tipoDocumento, Direccion direccion, ArrayList<Inmueble> inmuebles) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.numeroDocumento = numeroDocumento;
		this.telefono = telefono;
		this.email = email;
		this.tipoDocumento = tipoDocumento;
		this.direccion = direccion;
		this.inmuebles = inmuebles;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	public ArrayList<Inmueble> getInmuebles() {
		return inmuebles;
	}

	public void setInmuebles(ArrayList<Inmueble> inmuebles) {
		this.inmuebles = inmuebles;
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
		Propietario other = (Propietario) obj;
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
