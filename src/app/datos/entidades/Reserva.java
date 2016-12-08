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

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "reserva")
public class Reserva {
	private Integer id; //ID
	private Double importe;
	private Date fechaInicio;
	private Date fechaFin;
	private PDF archivoPDF;

	//Relaciones
	private Cliente cliente;
	private Inmueble inmueble;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idestado", referencedColumnName = "id", foreignKey = @ForeignKey(name = "reserva_idestado_fk"), nullable = false)
	private Estado estado;

	public Integer getId() {
		return id;
	}

	public Reserva setId(Integer id) {
		this.id = id;
		return this;
	}

	public Double getImporte() {
		return importe;
	}

	public Reserva setImporte(Double importe) {
		this.importe = importe;
		return this;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public Reserva setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
		return this;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public Reserva setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
		return this;
	}

	public PDF getArchivoPDF() {
		return archivoPDF;
	}

	public Reserva setArchivoPDF(PDF archivoPDF) {
		this.archivoPDF = archivoPDF;
		return this;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public Reserva setCliente(Cliente cliente) {
		this.cliente = cliente;
		return this;
	}

	public Inmueble getInmueble() {
		return inmueble;
	}

	public Reserva setInmueble(Inmueble inmueble) {
		this.inmueble = inmueble;
		return this;
	}

	public Estado getEstado() {
		return estado;
	}

	public Reserva setEstado(Estado estado) {
		this.estado = estado;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((archivoPDF == null) ? 0 : archivoPDF.hashCode());
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result + ((fechaFin == null) ? 0 : fechaFin.hashCode());
		result = prime * result + ((fechaInicio == null) ? 0 : fechaInicio.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((importe == null) ? 0 : importe.hashCode());
		result = prime * result + ((inmueble == null) ? 0 : inmueble.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		Reserva other = (Reserva) obj;
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
			if(other.archivoPDF != null)
				return false;
		}
		else if(!archivoPDF.equals(other.archivoPDF))
			return false;
		if(cliente == null){
			if(other.cliente != null)
				return false;
		}
		else if(!cliente.equals(other.cliente))
			return false;
		if(estado == null){
			if(other.estado != null)
				return false;
		}
		else if(!estado.equals(other.estado))
			return false;
		if(fechaFin == null){
			if(other.fechaFin != null)
				return false;
		}
		else if(!fechaFin.equals(other.fechaFin))
			return false;
		if(fechaInicio == null){
			if(other.fechaInicio != null)
				return false;
		}
		else if(!fechaInicio.equals(other.fechaInicio))
			return false;
		if(importe == null){
			if(other.importe != null)
				return false;
		}
		else if(!importe.equals(other.importe))
			return false;
		if(inmueble == null){
			if(other.inmueble != null)
				return false;
		}
		else if(!inmueble.equals(other.inmueble))
			return false;
		return true;
	}

}
