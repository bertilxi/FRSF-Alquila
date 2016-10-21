package app.datos.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="medidas_inmueble")
public class MedidasInmueble {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="frente")
	private Double frente;
	
	@Column(name="fondo")
	private Double fondo;
	
	@Column(name="superficie")
	private Double superficie;

	public MedidasInmueble() {
		super();
	}

	public MedidasInmueble(Integer id, Double frente, Double fondo, Double superficie) {
		super();
		this.id = id;
		this.frente = frente;
		this.fondo = fondo;
		this.superficie = superficie;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fondo == null) ? 0 : fondo.hashCode());
		result = prime * result + ((frente == null) ? 0 : frente.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((superficie == null) ? 0 : superficie.hashCode());
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
		MedidasInmueble other = (MedidasInmueble) obj;
		if (fondo == null) {
			if (other.fondo != null)
				return false;
		} else if (!fondo.equals(other.fondo))
			return false;
		if (frente == null) {
			if (other.frente != null)
				return false;
		} else if (!frente.equals(other.frente))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (superficie == null) {
			if (other.superficie != null)
				return false;
		} else if (!superficie.equals(other.superficie))
			return false;
		return true;
	}
}
