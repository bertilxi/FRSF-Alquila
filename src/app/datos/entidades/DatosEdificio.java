package app.datos.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="datos_edificio")
public class DatosEdificio {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="superficie")
    private Double superficie; // en metros cuadrados
    
	@Column(name="antiguedad")
	private Integer antiguedad; // en años
	
	@Column(name="dormitorios")
    private Integer dormitorios;
	
	@Column(name="baños")
    private Integer baños;
	
	@Column(name="garaje")
    private Integer garaje;
	
	@Column(name="patio")
    private Integer patio;
	
	@Column(name="piscina")
    private Integer piscina;
	
	@Column(name="telefono")
    private Boolean telefono;
	
	@Column(name="propiedad_horizontal")
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

	public DatosEdificio() {
		super();
	}

	public DatosEdificio(Integer id, Double superficie, Integer antiguedad, Integer dormitorios, Integer baños,
			Integer garaje, Integer patio, Integer piscina, Boolean telefono, Boolean propiedadHorizontal,
			Boolean aguaCorriente, Boolean cloacas, Boolean gasNatural, Boolean aguaCaliente, Boolean lavadero,
			Boolean pavimento) {
		super();
		this.id = id;
		this.superficie = superficie;
		this.antiguedad = antiguedad;
		this.dormitorios = dormitorios;
		this.baños = baños;
		this.garaje = garaje;
		this.patio = patio;
		this.piscina = piscina;
		this.telefono = telefono;
		this.propiedadHorizontal = propiedadHorizontal;
		this.aguaCorriente = aguaCorriente;
		this.cloacas = cloacas;
		this.gasNatural = gasNatural;
		this.aguaCaliente = aguaCaliente;
		this.lavadero = lavadero;
		this.pavimento = pavimento;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getSuperficie() {
		return superficie;
	}

	public void setSuperficie(Double superficie) {
		this.superficie = superficie;
	}

	public Integer getAntiguedad() {
		return antiguedad;
	}

	public void setAntiguedad(Integer antiguedad) {
		this.antiguedad = antiguedad;
	}

	public Integer getDormitorios() {
		return dormitorios;
	}

	public void setDormitorios(Integer dormitorios) {
		this.dormitorios = dormitorios;
	}

	public Integer getBaños() {
		return baños;
	}

	public void setBaños(Integer baños) {
		this.baños = baños;
	}

	public Integer getGaraje() {
		return garaje;
	}

	public void setGaraje(Integer garaje) {
		this.garaje = garaje;
	}

	public Integer getPatio() {
		return patio;
	}

	public void setPatio(Integer patio) {
		this.patio = patio;
	}

	public Integer getPiscina() {
		return piscina;
	}

	public void setPiscina(Integer piscina) {
		this.piscina = piscina;
	}

	public Boolean getTelefono() {
		return telefono;
	}

	public void setTelefono(Boolean telefono) {
		this.telefono = telefono;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aguaCaliente == null) ? 0 : aguaCaliente.hashCode());
		result = prime * result + ((aguaCorriente == null) ? 0 : aguaCorriente.hashCode());
		result = prime * result + ((antiguedad == null) ? 0 : antiguedad.hashCode());
		result = prime * result + ((baños == null) ? 0 : baños.hashCode());
		result = prime * result + ((cloacas == null) ? 0 : cloacas.hashCode());
		result = prime * result + ((dormitorios == null) ? 0 : dormitorios.hashCode());
		result = prime * result + ((garaje == null) ? 0 : garaje.hashCode());
		result = prime * result + ((gasNatural == null) ? 0 : gasNatural.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lavadero == null) ? 0 : lavadero.hashCode());
		result = prime * result + ((patio == null) ? 0 : patio.hashCode());
		result = prime * result + ((pavimento == null) ? 0 : pavimento.hashCode());
		result = prime * result + ((piscina == null) ? 0 : piscina.hashCode());
		result = prime * result + ((propiedadHorizontal == null) ? 0 : propiedadHorizontal.hashCode());
		result = prime * result + ((superficie == null) ? 0 : superficie.hashCode());
		result = prime * result + ((telefono == null) ? 0 : telefono.hashCode());
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
		DatosEdificio other = (DatosEdificio) obj;
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
		if (antiguedad == null) {
			if (other.antiguedad != null)
				return false;
		} else if (!antiguedad.equals(other.antiguedad))
			return false;
		if (baños == null) {
			if (other.baños != null)
				return false;
		} else if (!baños.equals(other.baños))
			return false;
		if (cloacas == null) {
			if (other.cloacas != null)
				return false;
		} else if (!cloacas.equals(other.cloacas))
			return false;
		if (dormitorios == null) {
			if (other.dormitorios != null)
				return false;
		} else if (!dormitorios.equals(other.dormitorios))
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
		if (propiedadHorizontal == null) {
			if (other.propiedadHorizontal != null)
				return false;
		} else if (!propiedadHorizontal.equals(other.propiedadHorizontal))
			return false;
		if (superficie == null) {
			if (other.superficie != null)
				return false;
		} else if (!superficie.equals(other.superficie))
			return false;
		if (telefono == null) {
			if (other.telefono != null)
				return false;
		} else if (!telefono.equals(other.telefono))
			return false;
		return true;
	}
}
