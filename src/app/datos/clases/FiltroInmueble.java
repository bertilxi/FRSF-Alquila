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
package app.datos.clases;

import org.hibernate.Query;

import app.datos.entidades.Barrio;
import app.datos.entidades.Localidad;
import app.datos.entidades.Pais;
import app.datos.entidades.Provincia;

public class FiltroInmueble {

	private String consulta;
	private String nombreEntidad = "Inmu";
	private String pais;
	private String provincia;
	private String localidad;
	private String barrio;
	private TipoInmuebleStr tipoInmueble;
	private Integer cantidadDormitorios;
	private Double precioMaximo;
	private Double precioMinimo;
	private EstadoInmuebleStr estadoInmueble;

	private FiltroInmueble() {
		super();
	}

	private FiltroInmueble(FiltroInmueble filtro) {
		this();
		this.consulta = filtro.consulta;
		this.nombreEntidad = filtro.nombreEntidad;
		this.pais = filtro.pais;
		this.provincia = filtro.provincia;
		this.localidad = filtro.localidad;
		this.barrio = filtro.barrio;
		this.tipoInmueble = filtro.tipoInmueble;
		this.cantidadDormitorios = filtro.cantidadDormitorios;
		this.precioMaximo = filtro.precioMaximo;
		this.precioMinimo = filtro.precioMinimo;
		this.estadoInmueble = filtro.estadoInmueble;
	}

	public static class Builder {

		private FiltroInmueble filtroInmueble;

		public Builder() {
			super();
			filtroInmueble = new FiltroInmueble();
		}

		public Builder pais(Pais pais) {
			if(pais != null) {
				filtroInmueble.pais = pais.getNombre();
			}
			return this;
		}

		public Builder provincia(Provincia provincia) {
			if(provincia != null) {
				filtroInmueble.provincia = provincia.getNombre();
			}
			return this;
		}

		public Builder localidad(Localidad localidad) {
			if(localidad != null) {
				filtroInmueble.localidad = localidad.getNombre();
			}
			return this;
		}

		public Builder barrio(Barrio barrio) {
			if(barrio != null) {
				filtroInmueble.barrio = barrio.getNombre();
			}
			return this;
		}

		public Builder tipoInmueble(TipoInmuebleStr tipoInmueble) {
			filtroInmueble.tipoInmueble = tipoInmueble;
			return this;
		}

		public Builder cantidadDormitorios(Integer cantidadDormitorios) {
			filtroInmueble.cantidadDormitorios = cantidadDormitorios;
			return this;
		}

		public Builder precioMaximo(Double precioMaximo) {
			filtroInmueble.precioMaximo = precioMaximo;
			return this;
		}

		public Builder precioMinimo(Double precioMinimo) {
			filtroInmueble.precioMinimo = precioMinimo;
			return this;
		}

		public Builder estadoInmueble(EstadoInmuebleStr estadoInmueble) {
			filtroInmueble.estadoInmueble = estadoInmueble;
			return this;
		}

		public FiltroInmueble build() {
			filtroInmueble.setConsulta();
			return new FiltroInmueble(filtroInmueble);
		}
	}

	private void setConsulta() {
		consulta = this.getSelect() + this.getFrom() + this.getWhere() + this.getOrderBy();
	}

	private String getSelect() {
		String select = "SELECT " + nombreEntidad;
		return select;
	}

	private String getFrom() {
		String from = " FROM Inmueble " + nombreEntidad;
		return from;
	}

	private String getWhere() {
		String where =
				((pais != null) ? (nombreEntidad + ".direccion.localidad.provincia.pais.nombre LIKE :pai AND ") : ("")) +
						((provincia != null) ? (nombreEntidad + ".direccion.localidad.provincia.nombre LIKE :pro AND ") : ("")) +
						((localidad != null) ? (nombreEntidad + ".direccion.localidad.nombre LIKE :loc AND ") : ("")) +
						((barrio != null) ? (nombreEntidad + ".direccion.barrio.nombre LIKE :bar AND ") : ("")) +
						((tipoInmueble != null) ? (nombreEntidad + ".tipo.tipo = :tii AND ") : ("")) +
						((cantidadDormitorios != null) ? (nombreEntidad + ".datosEdificio.dormitorios >= :cad AND ") : ("")) +
						((precioMaximo != null) ? (nombreEntidad + ".precio <= :pma AND ") : ("")) +
						((precioMinimo != null) ? (nombreEntidad + ".precio >= :pmi AND ") : ("")) +
						((estadoInmueble != null) ? (nombreEntidad + ".estadoInmueble.estado = :esi AND ") : ("")); //TODO cambiar cuando exista

		if(!where.isEmpty()){
			where = " WHERE " + where;
			where = where.substring(0, where.length() - 4);
		}
		return where;
	}

	private String getOrderBy() {
		String orderBy = " ORDER BY " + nombreEntidad + ".fechaCarga ASC";
		return orderBy;
	}

	public Query setParametros(Query query) {
		if(pais != null){
			query.setParameter("pai", "%" + pais + "%");
		}
		if(provincia != null){
			query.setParameter("pro", "%" + provincia + "%");
		}
		if(localidad != null){
			query.setParameter("loc", "%" + localidad + "%");
		}
		if(barrio != null){
			query.setParameter("bar", "%" + barrio + "%");
		}
		if(tipoInmueble != null){
			query.setParameter("tii", tipoInmueble);
		}
		if(cantidadDormitorios != null){
			query.setParameter("cad", cantidadDormitorios);
		}
		if(precioMaximo != null){
			query.setParameter("pma", precioMaximo);
		}
		if(precioMinimo != null){
			query.setParameter("pmi", precioMinimo);
		}
		if(estadoInmueble != null){
			query.setParameter("esi", estadoInmueble);
		}
		return query;
	}

	public String getConsultaDinamica() {
		return consulta;
	}
}
