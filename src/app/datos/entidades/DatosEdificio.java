/**
 * Copyright (C) 2016 Fernando Berti - Daniel Campodonico - Emiliano Gioria - Lucas Moretti - Esteban Rebechi - Andres Leonel Rico
 * This file is part of Olimpo.
 *
 * Olimpo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Olimpo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Olimpo. If not, see <http://www.gnu.org/licenses/>.
 */
package app.datos.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "datos_edificio")
public class DatosEdificio implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id; //ID

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idinmueble", referencedColumnName = "id", foreignKey = @ForeignKey(name = "datos_edificio_idinmueble_fk"), nullable = false)
	private Inmueble inmueble;

	@Column(name = "superficie")
	private Double superficie; // en metros cuadrados

	@Column(name = "antiguedad")
	private Integer antiguedad; // en años

	@Column(name = "dormitorios")
	private Integer dormitorios;

	@Column(name = "baños")
	private Integer baños;

	@Column(name = "garaje")
	private Boolean garaje;

	@Column(name = "patio")
	private Boolean patio;

	@Column(name = "piscina")
	private Boolean piscina;

	@Column(name = "telefono")
	private Boolean telefono;

	@Column(name = "propiedad_horizontal")
	private Boolean propiedadHorizontal;

	@Column(name = "agua_corriente")
	private Boolean aguaCorriente;

	@Column(name = "cloacas")
	private Boolean cloacas;

	@Column(name = "gas_natural")
	private Boolean gasNatural;

	@Column(name = "agua_caliente")
	private Boolean aguaCaliente;

	@Column(name = "lavadero")
	private Boolean lavadero;

	@Column(name = "pavimento")
	private Boolean pavimento;

	public DatosEdificio() {
		super();
	}

	public DatosEdificio(Inmueble inmueble, Double superficie, Integer antiguedad, Integer dormitorios, Integer baños,
			Boolean garaje, Boolean patio, Boolean piscina, Boolean telefono, Boolean propiedadHorizontal,
			Boolean aguaCorriente, Boolean cloacas, Boolean gasNatural, Boolean aguaCaliente, Boolean lavadero,
			Boolean pavimento) {
		super();
		this.inmueble = inmueble;
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

	public Inmueble getInmueble() {
		return inmueble;
	}

	public DatosEdificio setInmueble(Inmueble inmueble) {
		this.inmueble = inmueble;
		return this;
	}

	public Double getSuperficie() {
		return superficie;
	}

	public DatosEdificio setSuperficie(Double superficie) {
		this.superficie = superficie;
		return this;
	}

	public Integer getAntiguedad() {
		return antiguedad;
	}

	public DatosEdificio setAntiguedad(Integer antiguedad) {
		this.antiguedad = antiguedad;
		return this;
	}

	public Integer getDormitorios() {
		return dormitorios;
	}

	public DatosEdificio setDormitorios(Integer dormitorios) {
		this.dormitorios = dormitorios;
		return this;
	}

	public Integer getBaños() {
		return baños;
	}

	public DatosEdificio setBaños(Integer baños) {
		this.baños = baños;
		return this;
	}

	public Boolean getGaraje() {
		return garaje;
	}

	public DatosEdificio setGaraje(Boolean garaje) {
		this.garaje = garaje;
		return this;
	}

	public Boolean getPatio() {
		return patio;
	}

	public DatosEdificio setPatio(Boolean patio) {
		this.patio = patio;
		return this;
	}

	public Boolean getPiscina() {
		return piscina;
	}

	public DatosEdificio setPiscina(Boolean piscina) {
		this.piscina = piscina;
		return this;
	}

	public Boolean getTelefono() {
		return telefono;
	}

	public DatosEdificio setTelefono(Boolean telefono) {
		this.telefono = telefono;
		return this;
	}

	public Boolean getPropiedadHorizontal() {
		return propiedadHorizontal;
	}

	public DatosEdificio setPropiedadHorizontal(Boolean propiedadHorizontal) {
		this.propiedadHorizontal = propiedadHorizontal;
		return this;
	}

	public Boolean getAguaCorriente() {
		return aguaCorriente;
	}

	public DatosEdificio setAguaCorriente(Boolean aguaCorriente) {
		this.aguaCorriente = aguaCorriente;
		return this;
	}

	public Boolean getCloacas() {
		return cloacas;
	}

	public DatosEdificio setCloacas(Boolean cloacas) {
		this.cloacas = cloacas;
		return this;
	}

	public Boolean getGasNatural() {
		return gasNatural;
	}

	public DatosEdificio setGasNatural(Boolean gasNatural) {
		this.gasNatural = gasNatural;
		return this;
	}

	public Boolean getAguaCaliente() {
		return aguaCaliente;
	}

	public DatosEdificio setAguaCaliente(Boolean aguaCaliente) {
		this.aguaCaliente = aguaCaliente;
		return this;
	}

	public Boolean getLavadero() {
		return lavadero;
	}

	public DatosEdificio setLavadero(Boolean lavadero) {
		this.lavadero = lavadero;
		return this;
	}

	public Boolean getPavimento() {
		return pavimento;
	}

	public DatosEdificio setPavimento(Boolean pavimento) {
		this.pavimento = pavimento;
		return this;
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
		result = prime * result + ((inmueble == null) ? 0 : inmueble.hashCode());
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
		if(this == obj){
			return true;
		}
		if(obj == null){
			return false;
		}
		if(getClass() != obj.getClass()){
			return false;
		}
		DatosEdificio other = (DatosEdificio) obj;
		if(inmueble == null){
			if(other.inmueble != null){
				return false;
			}
		}
		else if(!inmueble.equals(other.inmueble)){
			return false;
		}
		else{
			return true;
		}
		if(aguaCaliente == null){
			if(other.aguaCaliente != null){
				return false;
			}
		}
		else if(!aguaCaliente.equals(other.aguaCaliente)){
			return false;
		}
		if(aguaCorriente == null){
			if(other.aguaCorriente != null){
				return false;
			}
		}
		else if(!aguaCorriente.equals(other.aguaCorriente)){
			return false;
		}
		if(antiguedad == null){
			if(other.antiguedad != null){
				return false;
			}
		}
		else if(!antiguedad.equals(other.antiguedad)){
			return false;
		}
		if(baños == null){
			if(other.baños != null){
				return false;
			}
		}
		else if(!baños.equals(other.baños)){
			return false;
		}
		if(cloacas == null){
			if(other.cloacas != null){
				return false;
			}
		}
		else if(!cloacas.equals(other.cloacas)){
			return false;
		}
		if(dormitorios == null){
			if(other.dormitorios != null){
				return false;
			}
		}
		else if(!dormitorios.equals(other.dormitorios)){
			return false;
		}
		if(garaje == null){
			if(other.garaje != null){
				return false;
			}
		}
		else if(!garaje.equals(other.garaje)){
			return false;
		}
		if(gasNatural == null){
			if(other.gasNatural != null){
				return false;
			}
		}
		else if(!gasNatural.equals(other.gasNatural)){
			return false;
		}
		if(lavadero == null){
			if(other.lavadero != null){
				return false;
			}
		}
		else if(!lavadero.equals(other.lavadero)){
			return false;
		}
		if(patio == null){
			if(other.patio != null){
				return false;
			}
		}
		else if(!patio.equals(other.patio)){
			return false;
		}
		if(pavimento == null){
			if(other.pavimento != null){
				return false;
			}
		}
		else if(!pavimento.equals(other.pavimento)){
			return false;
		}
		if(piscina == null){
			if(other.piscina != null){
				return false;
			}
		}
		else if(!piscina.equals(other.piscina)){
			return false;
		}
		if(propiedadHorizontal == null){
			if(other.propiedadHorizontal != null){
				return false;
			}
		}
		else if(!propiedadHorizontal.equals(other.propiedadHorizontal)){
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
		if(telefono == null){
			if(other.telefono != null){
				return false;
			}
		}
		else if(!telefono.equals(other.telefono)){
			return false;
		}
		return true;
	}
}
