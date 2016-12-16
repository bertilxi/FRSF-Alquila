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
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "venta")
/**
 * Entidad que modela una venta
 * Pertenece a la taskcard 32 de la iteración 2
 */
public class Venta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id; //ID

	@Column(name = "importe", nullable = false)
	private Double importe;

	@Column(name = "medio_de_pago", nullable = false)
	private String medioDePago;

	@Column(name = "fecha", nullable = false)
	private Date fecha;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, optional = false)
	@JoinColumn(name = "idarchivo", foreignKey = @ForeignKey(name = "venta_idarchivo_fk"), nullable = false)
	private PDF archivoPDF;

	//Relaciones
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idvendedor", referencedColumnName = "id", foreignKey = @ForeignKey(name = "venta_idvendedor_fk"), nullable = false)
	private Vendedor vendedor;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idcliente", referencedColumnName = "id", foreignKey = @ForeignKey(name = "venta_idcliente_fk"), nullable = false)
	private Cliente cliente;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idpropietario", referencedColumnName = "id", foreignKey = @ForeignKey(name = "venta_idpropietario_fk"), nullable = false)
	private Propietario propietario;

	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "idinmueble", referencedColumnName = "id", foreignKey = @ForeignKey(name = "venta_idinmueble_fk"), nullable = false)
	private Inmueble inmueble;

	public Venta() {
		super();
	}

	public Venta(Double importe, String medioDePago, Date fecha, PDF archivoPDF, Vendedor vendedor, Cliente cliente, Propietario propietario, Inmueble inmueble) {
		super();
		this.importe = importe;
		this.medioDePago = medioDePago;
		this.fecha = fecha;
		this.archivoPDF = archivoPDF;
		this.vendedor = vendedor;
		this.cliente = cliente;
		this.propietario = propietario;
		this.inmueble = inmueble;
	}

	public Integer getId() {
		return id;
	}

	public Double getImporte() {
		return importe;
	}

	public Venta setImporte(Double importe) {
		this.importe = importe;
		return this;
	}

	public String getMedioDePago() {
		return medioDePago;
	}

	public Venta setMedioDePago(String medioDePago) {
		this.medioDePago = medioDePago;
		return this;
	}

	public Date getFecha() {
		return fecha;
	}

	public Venta setFecha(Date fecha) {
		this.fecha = fecha;
		return this;
	}

	public PDF getArchivoPDF() {
		return archivoPDF;
	}

	public Venta setArchivoPDF(PDF archivoPDF) {
		this.archivoPDF = archivoPDF;
		return this;
	}

	public Vendedor getVendedor() {
		return vendedor;
	}

	public Venta setVendedor(Vendedor vendedor) {
		this.vendedor = vendedor;
		return this;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public Venta setCliente(Cliente cliente) {
		this.cliente = cliente;
		return this;
	}

	public Propietario getPropietario() {
		return propietario;
	}

	public Venta setPropietario(Propietario propietario) {
		this.propietario = propietario;
		return this;
	}

	public Inmueble getInmueble() {
		return inmueble;
	}

	public Venta setInmueble(Inmueble inmueble) {
		this.inmueble = inmueble;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((archivoPDF == null) ? 0 : archivoPDF.hashCode());
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((importe == null) ? 0 : importe.hashCode());
		result = prime * result + ((inmueble == null) ? 0 : inmueble.hashCode());
		result = prime * result + ((medioDePago == null) ? 0 : medioDePago.hashCode());
		result = prime * result + ((propietario == null) ? 0 : propietario.hashCode());
		result = prime * result + ((vendedor == null) ? 0 : vendedor.hashCode());
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
		Venta other = (Venta) obj;
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
		if(archivoPDF == null){
			if(other.archivoPDF != null){
				return false;
			}
		}
		else if(!archivoPDF.equals(other.archivoPDF)){
			return false;
		}
		if(cliente == null){
			if(other.cliente != null){
				return false;
			}
		}
		else if(!cliente.equals(other.cliente)){
			return false;
		}
		if(fecha == null){
			if(other.fecha != null){
				return false;
			}
		}
		else if(!fecha.equals(other.fecha)){
			return false;
		}
		if(importe == null){
			if(other.importe != null){
				return false;
			}
		}
		else if(!importe.equals(other.importe)){
			return false;
		}
		if(inmueble == null){
			if(other.inmueble != null){
				return false;
			}
		}
		else if(!inmueble.equals(other.inmueble)){
			return false;
		}
		if(medioDePago == null){
			if(other.medioDePago != null){
				return false;
			}
		}
		else if(!medioDePago.equals(other.medioDePago)){
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
		if(vendedor == null){
			if(other.vendedor != null){
				return false;
			}
		}
		else if(!vendedor.equals(other.vendedor)){
			return false;
		}
		return true;
	}
}
