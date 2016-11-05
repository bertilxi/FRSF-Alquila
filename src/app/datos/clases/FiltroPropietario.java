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

public class FiltroPropietario {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((documento == null) ? 0 : documento.hashCode());
		result = prime * result + ((tipoDocumento == null) ? 0 : tipoDocumento.hashCode());
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
		FiltroPropietario other = (FiltroPropietario) obj;
		if(documento == null){
			if(other.documento != null)
				return false;
		}
		else if(!documento.equals(other.documento))
			return false;
		if(tipoDocumento != other.tipoDocumento)
			return false;
		return true;
	}

	private TipoDocumentoStr tipoDocumento;
	private String documento;

	public FiltroPropietario(TipoDocumentoStr tipoDocumento, String documento) {
		super();
		if(tipoDocumento == null || documento == null){
			throw new NullPointerException();
		}
		this.tipoDocumento = tipoDocumento;
		this.documento = documento;
	}

	public TipoDocumentoStr getTipoDocumento() {
		return tipoDocumento;
	}

	public String getDocumento() {
		return documento;
	}

}
