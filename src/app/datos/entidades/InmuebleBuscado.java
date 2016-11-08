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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "inmueble_buscado")
/*
 * Entidad que modela la busqueda de un inmueble, según las características que posee.
 * Pertenece a la taskcard 12 de la iteración 1 y a la historia de usuario 3
 */
public class InmuebleBuscado implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id; //ID

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idcliente", referencedColumnName = "id", foreignKey = @ForeignKey(name = "inmueble_buscado_idcliente_fk"), nullable = false)
	private Cliente cliente;

	@Column(name = "precio_max")
	private Double precioMax;

	@Column(name = "superficie_min")
	private Double superficieMin; // en metros cuadrados

	@Column(name = "antiguedad_max")
	private Integer antiguedadMax; // en años

	@Column(name = "dormitorios_min")
	private Integer dormitoriosMin;

	@Column(name = "baños_min")
	private Integer bañosMin;

	@Column(name = "garaje")
	private Boolean garaje;

	@Column(name = "patio")
	private Boolean patio;

	@Column(name = "piscina")
	private Boolean piscina;

	@Column(name = "propiedad_horizonal")
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

	@Column(name = "telefono")
	private Boolean telefono;

	//Relaciones

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "inmueble_buscado_localidad", joinColumns = @JoinColumn(name = "idlocalidad"), foreignKey = @ForeignKey(name = "inmueble_buscado_localidad_idlocalidadfk"), inverseJoinColumns = @JoinColumn(name = "idinmueblebuscado"), inverseForeignKey = @ForeignKey(name = "inmueble_buscado_inmueble_idinmueblefk"))
	private Set<Localidad> localidades;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "inmueble_buscado_barrio", joinColumns = @JoinColumn(name = "idbarrio"), foreignKey = @ForeignKey(name = "inmueble_buscado_barrio_idbarriofk"), inverseJoinColumns = @JoinColumn(name = "idinmueblebuscado"), inverseForeignKey = @ForeignKey(name = "inmueble_buscado_barrio_idinmueblefk"))
	private Set<Barrio> barrios;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "inmueble_buscado_tipo_inmueble", joinColumns = @JoinColumn(name = "idtipoinmueble"), foreignKey = @ForeignKey(name = "inmueble_buscado_tipo_idtipofk"), inverseJoinColumns = @JoinColumn(name = "idinmueblebuscado"), inverseForeignKey = @ForeignKey(name = "inmueble_buscado_inmueble_idinmueblefk"))
	private Set<TipoInmueble> tiposInmueblesBuscados;

	public InmuebleBuscado() {
		super();
		this.localidades = new HashSet<>();
		this.barrios = new HashSet<>();
		this.tiposInmueblesBuscados = new HashSet<>();
	}

	public InmuebleBuscado(Cliente cliente, Double precioMax, Double superficieMin, Integer antiguedadMax,
			Integer dormitoriosMin, Integer bañosMin, Boolean garaje, Boolean patio, Boolean piscina,
			Boolean propiedadHorizontal, Boolean aguaCorriente, Boolean cloacas, Boolean gasNatural,
			Boolean aguaCaliente, Boolean lavadero, Boolean pavimento, Boolean telefono) {
		super();
		this.cliente = cliente;
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
		this.telefono = telefono;
	}

	public Integer getId() {
		return id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public InmuebleBuscado setCliente(Cliente cliente) {
		this.cliente = cliente;
		return this;
	}

	public Double getPrecioMax() {
		return precioMax;
	}

	public InmuebleBuscado setPrecioMax(Double precioMax) {
		this.precioMax = precioMax;
		return this;
	}

	public Double getSuperficieMin() {
		return superficieMin;
	}

	public InmuebleBuscado setSuperficieMin(Double superficieMin) {
		this.superficieMin = superficieMin;
		return this;
	}

	public Integer getAntiguedadMax() {
		return antiguedadMax;
	}

	public InmuebleBuscado setAntiguedadMax(Integer antiguedadMax) {
		this.antiguedadMax = antiguedadMax;
		return this;
	}

	public Integer getDormitoriosMin() {
		return dormitoriosMin;
	}

	public InmuebleBuscado setDormitoriosMin(Integer dormitoriosMin) {
		this.dormitoriosMin = dormitoriosMin;
		return this;
	}

	public Integer getBañosMin() {
		return bañosMin;
	}

	public InmuebleBuscado setBañosMin(Integer bañosMin) {
		this.bañosMin = bañosMin;
		return this;
	}

	public Boolean getGaraje() {
		return garaje;
	}

	public InmuebleBuscado setGaraje(Boolean garaje) {
		this.garaje = garaje;
		return this;
	}

	public Boolean getPatio() {
		return patio;
	}

	public InmuebleBuscado setPatio(Boolean patio) {
		this.patio = patio;
		return this;
	}

	public Boolean getPiscina() {
		return piscina;
	}

	public InmuebleBuscado setPiscina(Boolean piscina) {
		this.piscina = piscina;
		return this;
	}

	public Boolean getPropiedadHorizontal() {
		return propiedadHorizontal;

	}

	public InmuebleBuscado setPropiedadHorizontal(Boolean propiedadHorizontal) {
		this.propiedadHorizontal = propiedadHorizontal;
		return this;
	}

	public Boolean getAguaCorriente() {
		return aguaCorriente;
	}

	public InmuebleBuscado setAguaCorriente(Boolean aguaCorriente) {
		this.aguaCorriente = aguaCorriente;
		return this;
	}

	public Boolean getCloacas() {
		return cloacas;
	}

	public InmuebleBuscado setCloacas(Boolean cloacas) {
		this.cloacas = cloacas;
		return this;
	}

	public Boolean getGasNatural() {
		return gasNatural;
	}

	public InmuebleBuscado setGasNatural(Boolean gasNatural) {
		this.gasNatural = gasNatural;
		return this;
	}

	public Boolean getAguaCaliente() {
		return aguaCaliente;
	}

	public InmuebleBuscado setAguaCaliente(Boolean aguaCaliente) {
		this.aguaCaliente = aguaCaliente;
		return this;
	}

	public Boolean getLavadero() {
		return lavadero;
	}

	public InmuebleBuscado setLavadero(Boolean lavadero) {
		this.lavadero = lavadero;
		return this;
	}

	public Boolean getPavimento() {
		return pavimento;
	}

	public InmuebleBuscado setPavimento(Boolean pavimento) {
		this.pavimento = pavimento;
		return this;
	}

	public Boolean getTelefono() {
		return telefono;
	}

	public InmuebleBuscado setTelefono(Boolean telefono) {
		this.telefono = telefono;
		return this;
	}

	public Set<Localidad> getLocalidades() {
		return localidades;
	}

	public Set<Barrio> getBarrios() {
		return barrios;
	}

	public Set<TipoInmueble> getTiposInmueblesBuscados() {
		return tiposInmueblesBuscados;
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
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
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
		if(this == obj){
			return true;
		}
		if(obj == null){
			return false;
		}
		if(getClass() != obj.getClass()){
			return false;
		}
		InmuebleBuscado other = (InmuebleBuscado) obj;
		if(cliente == null){
			if(other.cliente != null){
				return false;
			}
		}
		else if(!cliente.equals(other.cliente)){
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
		if(antiguedadMax == null){
			if(other.antiguedadMax != null){
				return false;
			}
		}
		else if(!antiguedadMax.equals(other.antiguedadMax)){
			return false;
		}
		if(barrios == null){
			if(other.barrios != null){
				return false;
			}
		}
		else if(!barrios.equals(other.barrios)){
			return false;
		}
		if(bañosMin == null){
			if(other.bañosMin != null){
				return false;
			}
		}
		else if(!bañosMin.equals(other.bañosMin)){
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
		if(dormitoriosMin == null){
			if(other.dormitoriosMin != null){
				return false;
			}
		}
		else if(!dormitoriosMin.equals(other.dormitoriosMin)){
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
		if(localidades == null){
			if(other.localidades != null){
				return false;
			}
		}
		else if(!localidades.equals(other.localidades)){
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
		if(precioMax == null){
			if(other.precioMax != null){
				return false;
			}
		}
		else if(!precioMax.equals(other.precioMax)){
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
		if(superficieMin == null){
			if(other.superficieMin != null){
				return false;
			}
		}
		else if(!superficieMin.equals(other.superficieMin)){
			return false;
		}
		if(tiposInmueblesBuscados == null){
			if(other.tiposInmueblesBuscados != null){
				return false;
			}
		}
		else if(!tiposInmueblesBuscados.equals(other.tiposInmueblesBuscados)){
			return false;
		}
		return true;
	}
}
