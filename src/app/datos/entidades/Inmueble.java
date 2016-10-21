package app.datos.entidades;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class Inmueble {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id; //ID

	@Column(name = "codigo", length = 100)
	private String codigo;

	@Column(name = "observaciones", length = 300)
	private String observaciones;

	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_carga")
	private Date fechaCarga;

	@Column(name = "precio")
	private Double precio;

	@Column(name = "frente")
	private Double frente; // en metros

	@Column(name = "fondo")
	private Double fondo; // en metros

	@Column(name = "superficie")
	private Double superficie; // en metros cuadrados

	//Relaciones

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idtipo", referencedColumnName = "id", foreignKey = @ForeignKey(name = "inmueble_idtipo_fk"))
	private TipoInmueble tipo;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idorientacion", referencedColumnName = "id", foreignKey = @ForeignKey(name = "inmueble_idorientacion_fk"))
	private Orientacion orientacion;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "iddireccion", referencedColumnName = "id", foreignKey = @ForeignKey(name = "inmueble_iddireccion_fk"))
	private Direccion direccion;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idpropietario", referencedColumnName = "id", foreignKey = @ForeignKey(name = "inmueble_idpropietario_fk"))
	private Propietario propietario;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "tarea")
	private ArrayList<Imagen> fotos;

	//Opcionales
	// private DatosEdificio datosEdificio;
	// private ArrayList<Reserva> reservas;

	public Inmueble() {
		super();
		fotos = new ArrayList<>();
	}

	public Inmueble(Integer id, String codigo, String observaciones, Date fechaCarga, Double precio, Double frente, Double fondo, Double superficie) {
		this();
		this.id = id;
		this.codigo = codigo;
		this.observaciones = observaciones;
		this.fechaCarga = fechaCarga;
		this.precio = precio;
		this.frente = frente;
		this.fondo = fondo;
		this.superficie = superficie;
	}

	public Integer getId() {
		return id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Date getFechaCarga() {
		return fechaCarga;
	}

	public void setFechaCarga(Date fechaCarga) {
		this.fechaCarga = fechaCarga;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Double getFrente() {
		return frente;
	}

	public void setFrente(Double frente) {
		this.frente = frente;
	}

	public Double getFondo() {
		return fondo;
	}

	public void setFondo(Double fondo) {
		this.fondo = fondo;
	}

	public Double getSuperficie() {
		return superficie;
	}

	public void setSuperficie(Double superficie) {
		this.superficie = superficie;
	}

	public TipoInmueble getTipo() {
		return tipo;
	}

	public void setTipo(TipoInmueble tipo) {
		this.tipo = tipo;
	}

	public Orientacion getOrientacion() {
		return orientacion;
	}

	public void setOrientacion(Orientacion orientacion) {
		this.orientacion = orientacion;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	public Propietario getPropietario() {
		return propietario;
	}

	public void setPropietario(Propietario propietario) {
		this.propietario = propietario;
	}

	public ArrayList<Imagen> getFotos() {
		return fotos;
	}

	public void setFotos(ArrayList<Imagen> fotos) {
		this.fotos = fotos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((direccion == null) ? 0 : direccion.hashCode());
		result = prime * result + ((fechaCarga == null) ? 0 : fechaCarga.hashCode());
		result = prime * result + ((fondo == null) ? 0 : fondo.hashCode());
		result = prime * result + ((fotos == null) ? 0 : fotos.hashCode());
		result = prime * result + ((frente == null) ? 0 : frente.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((observaciones == null) ? 0 : observaciones.hashCode());
		result = prime * result + ((orientacion == null) ? 0 : orientacion.hashCode());
		result = prime * result + ((precio == null) ? 0 : precio.hashCode());
		result = prime * result + ((propietario == null) ? 0 : propietario.hashCode());
		result = prime * result + ((superficie == null) ? 0 : superficie.hashCode());
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
		Inmueble other = (Inmueble) obj;
		if(codigo == null){
			if(other.codigo != null){
				return false;
			}
		}
		else if(!codigo.equals(other.codigo)){
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
		if(fotos == null){
			if(other.fotos != null){
				return false;
			}
		}
		else if(!fotos.equals(other.fotos)){
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
		if(id == null){
			if(other.id != null){
				return false;
			}
		}
		else if(!id.equals(other.id)){
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
		if(orientacion == null){
			if(other.orientacion != null){
				return false;
			}
		}
		else if(!orientacion.equals(other.orientacion)){
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
		if(propietario == null){
			if(other.propietario != null){
				return false;
			}
		}
		else if(!propietario.equals(other.propietario)){
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
		if(tipo == null){
			if(other.tipo != null){
				return false;
			}
		}
		else if(!tipo.equals(other.tipo)){
			return false;
		}
		return true;
	}

}
