package app.datos.entidades;

import java.util.HashSet;
import java.util.Set;

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
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

@NamedQueries(value = { @NamedQuery(name = "obtenerVendedor", query = "SELECT v FROM Vendedor v WHERE numeroDocumento = :documento AND tipoDocumento.tipo = :tipoDocumento"), @NamedQuery(name = "listarVendedores", query = "SELECT v FROM Vendedor v WHERE v.estado.estado = 'ALTA'") })
@Entity
@Table(name = "vendedor", uniqueConstraints = @UniqueConstraint(name = "vendedor_numerodocumento_idtipo_uk", columnNames = { "numerodocumento", "idtipo" }))
public class Vendedor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; //ID

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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idtipo", referencedColumnName = "id", foreignKey = @ForeignKey(name = "vendedor_idtipo_fk"), nullable = false)
	private TipoDocumento tipoDocumento;

	//@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "vendedor")
	@Transient //TODO hacer despues
	private Set<Venta> ventas;

	// baja
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idestado", referencedColumnName = "id", foreignKey = @ForeignKey(name = "vendedor_idestado_fk"), nullable = false)
	private Estado estado;

	public Vendedor() {
		super();
		this.ventas = new HashSet<>();
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

		if(!getNumeroDocumento().equals(vendedor.getNumeroDocumento())){
			return false;
		}
		if(!getTipoDocumento().equals(vendedor.getTipoDocumento())){
			return false;
		}
		return getVentas() != null ? getVentas().equals(vendedor.getVentas()) : vendedor.getVentas() == null;

	}

	@Override
	public int hashCode() {
		int result = getId().hashCode();
		result = 31 * result + getNumeroDocumento().hashCode();
		result = 31 * result + getTipoDocumento().hashCode();
		return result;
	}
}
