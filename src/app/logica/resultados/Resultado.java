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
		return new ArrayList<T>(errores);
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
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		Resultado other = (Resultado) obj;
		ArrayList<T> o;
		if(errores == null){
			if(other.errores != null)
				return false;

		}
		else{
			o = new ArrayList<T>(other.errores);
			if(errores.size() != o.size()){
				return false;
			}
			for(int i = 0; i < o.size(); i++){
				if(!errores.get(i).equals(o.get(i))){
					return false;
				}
			}
		}
		return true;
	}
}
