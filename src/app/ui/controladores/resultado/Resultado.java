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
package app.ui.controladores.resultado;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public abstract class Resultado<T> {

	List<T> errores;

	@SafeVarargs
	public Resultado(T... errores) {
		this.errores = Arrays.asList(errores);
	}

	public Boolean hayErrores() {
		return !errores.isEmpty();
	}

	public List<T> getErrores() {
		return new ArrayList<>(errores);
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
		Resultado<?> other = (Resultado<?>) obj;
		if(errores == null){
			if(other.errores != null){
				return false;
			}
		}
		else if(other.errores != null && !new HashSet<>(errores).equals(new HashSet<>(other.errores))){
			return false;
		}
		return true;
	}

}
