package app.datos.entidades;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="inmueble_buscado")
public class InmuebleBuscado {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="precio_max")
    private Double precioMax;
	
	@Column(name="superficie_min")
    private Double superficieMin; // en metros cuadrados
    
	@Column(name="antiguedad_max")
	private Integer antiguedadMax; // en años
 
	@Column(name="dormitorios_min")
	private Integer dormitoriosMin;
	
	@Column(name="baños_min")
    private Integer bañosMin;
	
	@Column(name="garaje")
    private Boolean garaje;
	
	@Column(name="patio")
    private Boolean patio;
	
	@Column(name="piscina")
    private Boolean piscina;
	
	@Column(name="propiedad_horizonal")
    private Boolean propiedadHorizontal;
	
	@Column(name="agua_corriente")
    private Boolean aguaCorriente;
	
	@Column(name="cloacas")
    private Boolean cloacas;
	
	@Column(name="gas_natural")
    private Boolean gasNatural;
	
	@Column(name="agua_caliente")
    private Boolean aguaCaliente;

	@Column(name="lavadero")
    private Boolean lavadero;

	@Column(name="pavimento")
    private Boolean pavimento;

    //Relaciones
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "inmueble_buscado_localidad",joinColumns=@JoinColumn(name="idlocalidad"),inverseJoinColumns=@JoinColumn(name="idinmueblebuscado"))
    private ArrayList<Localidad> localidades;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "inmueble_buscado_barrio",joinColumns=@JoinColumn(name="idbarrio"),inverseJoinColumns=@JoinColumn(name="idinmueblebuscado"))
    private ArrayList<Barrio> barrios; //Relacion muchos a muchos 
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "inmueble_buscado_tipo_inmueble",joinColumns=@JoinColumn(name="idtipoinmueble"),inverseJoinColumns=@JoinColumn(name="idinmueblebuscado"))
    private ArrayList<TipoInmueble> tiposInmueblesBuscados; //Relacion muchos a muchos
	
	public InmuebleBuscado() {
		super();
		this.localidades = new ArrayList<Localidad>();
		this.barrios = new ArrayList<Barrio>();
		this.tiposInmueblesBuscados = new ArrayList<TipoInmueble>();
	}

	public InmuebleBuscado(Integer id, Double precioMax, Double superficieMin, Integer antiguedadMax,
			Integer dormitoriosMin, Integer bañosMin, Boolean garaje, Boolean patio, Boolean piscina,
			Boolean propiedadHorizontal, Boolean aguaCorriente, Boolean cloacas, Boolean gasNatural,
			Boolean aguaCaliente, Boolean lavadero, Boolean pavimento, ArrayList<Localidad> localidades,
			ArrayList<Barrio> barrios, ArrayList<TipoInmueble> tiposInmueblesBuscados) {
		super();
		this.id = id;
		this.precioMax = precioMax;
		this.superficieMin = superficieMin;
		this.antiguedadMax = antiguedadMax;
		this.dormitoriosMin = dormitoriosMin;
		this.bañosMin = bañosMin;
		this.garaje = garaje;
		this.patio = patio;
		this.piscina = piscina;
		this.propiedadHorizontal = propiedadHorizontal;
		this.aguaCorriente = aguaCorriente;
		this.cloacas = cloacas;
		this.gasNatural = gasNatural;
		this.aguaCaliente = aguaCaliente;
		this.lavadero = lavadero;
		this.pavimento = pavimento;
		this.localidades = localidades;
		this.barrios = barrios;
		this.tiposInmueblesBuscados = tiposInmueblesBuscados;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getPrecioMax() {
		return precioMax;
	}

	public void setPrecioMax(Double precioMax) {
		this.precioMax = precioMax;
	}

	public Double getSuperficieMin() {
		return superficieMin;
	}

	public void setSuperficieMin(Double superficieMin) {
		this.superficieMin = superficieMin;
	}

	public Integer getAntiguedadMax() {
		return antiguedadMax;
	}

	public void setAntiguedadMax(Integer antiguedadMax) {
		this.antiguedadMax = antiguedadMax;
	}

	public Integer getDormitoriosMin() {
		return dormitoriosMin;
	}

	public void setDormitoriosMin(Integer dormitoriosMin) {
		this.dormitoriosMin = dormitoriosMin;
	}

	public Integer getBañosMin() {
		return bañosMin;
	}

	public void setBañosMin(Integer bañosMin) {
		this.bañosMin = bañosMin;
	}

	public Boolean getGaraje() {
		return garaje;
	}

	public void setGaraje(Boolean garaje) {
		this.garaje = garaje;
	}

	public Boolean getPatio() {
		return patio;
	}

	public void setPatio(Boolean patio) {
		this.patio = patio;
	}

	public Boolean getPiscina() {
		return piscina;
	}

	public void setPiscina(Boolean piscina) {
		this.piscina = piscina;
	}

	public Boolean getPropiedadHorizontal() {
		return propiedadHorizontal;
	}

	public void setPropiedadHorizontal(Boolean propiedadHorizontal) {
		this.propiedadHorizontal = propiedadHorizontal;
	}

	public Boolean getAguaCorriente() {
		return aguaCorriente;
	}

	public void setAguaCorriente(Boolean aguaCorriente) {
		this.aguaCorriente = aguaCorriente;
	}

	public Boolean getCloacas() {
		return cloacas;
	}

	public void setCloacas(Boolean cloacas) {
		this.cloacas = cloacas;
	}

	public Boolean getGasNatural() {
		return gasNatural;
	}

	public void setGasNatural(Boolean gasNatural) {
		this.gasNatural = gasNatural;
	}

	public Boolean getAguaCaliente() {
		return aguaCaliente;
	}

	public void setAguaCaliente(Boolean aguaCaliente) {
		this.aguaCaliente = aguaCaliente;
	}

	public Boolean getLavadero() {
		return lavadero;
	}

	public void setLavadero(Boolean lavadero) {
		this.lavadero = lavadero;
	}

	public Boolean getPavimento() {
		return pavimento;
	}

	public void setPavimento(Boolean pavimento) {
		this.pavimento = pavimento;
	}

	public ArrayList<Localidad> getLocalidades() {
		return localidades;
	}

	public void setLocalidades(ArrayList<Localidad> localidades) {
		this.localidades = localidades;
	}

	public ArrayList<Barrio> getBarrios() {
		return barrios;
	}

	public void setBarrios(ArrayList<Barrio> barrios) {
		this.barrios = barrios;
	}

	public ArrayList<TipoInmueble> getTiposInmueblesBuscados() {
		return tiposInmueblesBuscados;
	}

	public void setTiposInmueblesBuscados(ArrayList<TipoInmueble> tiposInmueblesBuscados) {
		this.tiposInmueblesBuscados = tiposInmueblesBuscados;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aguaCaliente == null) ? 0 : aguaCaliente.hashCode());
		result = prime * result + ((aguaCorriente == null) ? 0 : aguaCorriente.hashCode());
		result = prime * result + ((antiguedadMax == null) ? 0 : antiguedadMax.hashCode());
		result = prime * result + ((barrios == null) ? 0 : barrios.hashCode());
		result = prime * result + ((bañosMin == null) ? 0 : bañosMin.hashCode());
		result = prime * result + ((cloacas == null) ? 0 : cloacas.hashCode());
		result = prime * result + ((dormitoriosMin == null) ? 0 : dormitoriosMin.hashCode());
		result = prime * result + ((garaje == null) ? 0 : garaje.hashCode());
		result = prime * result + ((gasNatural == null) ? 0 : gasNatural.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lavadero == null) ? 0 : lavadero.hashCode());
		result = prime * result + ((localidades == null) ? 0 : localidades.hashCode());
		result = prime * result + ((patio == null) ? 0 : patio.hashCode());
		result = prime * result + ((pavimento == null) ? 0 : pavimento.hashCode());
		result = prime * result + ((piscina == null) ? 0 : piscina.hashCode());
		result = prime * result + ((precioMax == null) ? 0 : precioMax.hashCode());
		result = prime * result + ((propiedadHorizontal == null) ? 0 : propiedadHorizontal.hashCode());
		result = prime * result + ((superficieMin == null) ? 0 : superficieMin.hashCode());
		result = prime * result + ((tiposInmueblesBuscados == null) ? 0 : tiposInmueblesBuscados.hashCode());
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
		InmuebleBuscado other = (InmuebleBuscado) obj;
		if (aguaCaliente == null) {
			if (other.aguaCaliente != null)
				return false;
		} else if (!aguaCaliente.equals(other.aguaCaliente))
			return false;
		if (aguaCorriente == null) {
			if (other.aguaCorriente != null)
				return false;
		} else if (!aguaCorriente.equals(other.aguaCorriente))
			return false;
		if (antiguedadMax == null) {
			if (other.antiguedadMax != null)
				return false;
		} else if (!antiguedadMax.equals(other.antiguedadMax))
			return false;
		if (barrios == null) {
			if (other.barrios != null)
				return false;
		} else if (!barrios.equals(other.barrios))
			return false;
		if (bañosMin == null) {
			if (other.bañosMin != null)
				return false;
		} else if (!bañosMin.equals(other.bañosMin))
			return false;
		if (cloacas == null) {
			if (other.cloacas != null)
				return false;
		} else if (!cloacas.equals(other.cloacas))
			return false;
		if (dormitoriosMin == null) {
			if (other.dormitoriosMin != null)
				return false;
		} else if (!dormitoriosMin.equals(other.dormitoriosMin))
			return false;
		if (garaje == null) {
			if (other.garaje != null)
				return false;
		} else if (!garaje.equals(other.garaje))
			return false;
		if (gasNatural == null) {
			if (other.gasNatural != null)
				return false;
		} else if (!gasNatural.equals(other.gasNatural))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lavadero == null) {
			if (other.lavadero != null)
				return false;
		} else if (!lavadero.equals(other.lavadero))
			return false;
		if (localidad == null) {
			if (other.localidades != null)
				return false;
		} else if (!localidades.equals(other.localidades))
			return false;
		if (patio == null) {
			if (other.patio != null)
				return false;
		} else if (!patio.equals(other.patio))
			return false;
		if (pavimento == null) {
			if (other.pavimento != null)
				return false;
		} else if (!pavimento.equals(other.pavimento))
			return false;
		if (piscina == null) {
			if (other.piscina != null)
				return false;
		} else if (!piscina.equals(other.piscina))
			return false;
		if (precioMax == null) {
			if (other.precioMax != null)
				return false;
		} else if (!precioMax.equals(other.precioMax))
			return false;
		if (propiedadHorizontal == null) {
			if (other.propiedadHorizontal != null)
				return false;
		} else if (!propiedadHorizontal.equals(other.propiedadHorizontal))
			return false;
		if (superficieMin == null) {
			if (other.superficieMin != null)
				return false;
		} else if (!superficieMin.equals(other.superficieMin))
			return false;
		if (tiposInmueblesBuscados == null) {
			if (other.tiposInmueblesBuscados != null)
				return false;
		} else if (!tiposInmueblesBuscados.equals(other.tiposInmueblesBuscados))
			return false;
		return true;
	}
}
