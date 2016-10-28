package app.logica.resultados;

import java.util.ArrayList;
import java.util.Arrays;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((errores == null) ? 0 : errores.hashCode());
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
		Resultado<?> other = (Resultado<?>) obj;
		if(errores == null){
			if(other.errores != null){
				return false;
			}
		}
		else if(!errores.equals(other.errores)){
			return false;
		}
		return true;
	}
}
