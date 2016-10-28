package app.datos.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "inmueble")
public class Inmueble implements Serializable {

	private static final long serialVersionUID = 1L;

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

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "iddireccion", referencedColumnName = "id", foreignKey = @ForeignKey(name = "inmueble_iddireccion_fk"), nullable = false, unique = true)
	private Direccion direccion;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idpropietario", referencedColumnName = "id", foreignKey = @ForeignKey(name = "inmueble_idpropietario_fk"), nullable = false)
	private Propietario propietario;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "inmueble")
	@JoinColumn(name = "archivo", referencedColumnName = "inmuebleid_archivo") //TODO ver como queda en la bd
	private ArrayList<Imagen> fotos;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idestado", referencedColumnName = "id", foreignKey = @ForeignKey(name = "inmueble_idestado_fk"), nullable = false)
	private Estado estado;

	//Opcionales
	// private DatosEdificio datosEdificio;
	// private ArrayList<Reserva> reservas;

	public Inmueble() {
		super();
		fotos = new ArrayList<>();
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

	public ArrayList<Imagen> getFotos() {
		return fotos;
	}

	public Estado getEstado() {
		return estado;
	}

	public Inmueble setEstado(Estado estado) {
		this.estado = estado;
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
}
