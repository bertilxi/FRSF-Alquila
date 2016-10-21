package app.logica.gestores;

import java.util.regex.Pattern;

import app.datos.entidades.Direccion;
import app.datos.entidades.TipoDocumento;

public class ValidadorFormato {

	private Pattern pat;

	public ValidadorFormato() {
		super();
	}

	public Boolean validarDocumento(TipoDocumento tipo, String numeroDocumento) {

		switch(tipo.getTipo()) {
		case DNI:
			pat = Pattern.compile("[0-9]{7,8}");
			break;
		case LC:
			pat = Pattern.compile("[0-9\\-]{0,20}");
			break;
		case LE:
			pat = Pattern.compile("[0-9\\-]{0,20}");
			break;
		case Pasaporte:
			pat = Pattern.compile("[0-9\\-]{0,20}");
			break;
		case CedulaExtranjera:
			pat = Pattern.compile("[0-9\\-]{0,20}");
			break;
		default:
			return false;
		}

		return pat.matcher(numeroDocumento).matches();
	}

	public Boolean validarNombre(String nombre) {
		pat = Pattern.compile("[a-zA-Z\\ ÁÉÍÓÚÜÑáéíóúüñ]{1,30}");

		return pat.matcher(nombre).matches();
	}

	public Boolean validarApellido(String apellido) {

		return validarNombre(apellido);
	}

	public Boolean validarEmail(String email) {
		pat = Pattern.compile("([0-9a-zA-Z]+(.[0-9a-zA-Z]+)*@[0-9a-zA-Z]+(.[0-9a-zA-Z]+)*(.[a-z]{2,4})){0,30}");

		return pat.matcher(email).matches();
	}

	public Boolean validarTelefono(String telefono) {
		pat = Pattern.compile("[0-9\\-]{0,20}");

		return pat.matcher(telefono).matches();
	}

	public Boolean validarDireccion(Direccion direccion) {

		if(direccion == null){
			return false;
		}

		pat = Pattern.compile("([0-9]*[1-9]+[0-9]*){0,30}");
		if(!pat.matcher(direccion.getNumero()).matches()){
			return false;
		}

		pat = Pattern.compile("[a-zA-Z0-9]{1,30}");
		if(direccion.getCalle() == null || !pat.matcher(direccion.getCalle().getNombre()).matches()){
			return false;
		}

		pat = Pattern.compile("[a-zA-Z0-9]{0,30}");
		if(direccion.getPiso() != null && !pat.matcher(direccion.getPiso()).matches()){
			return false;
		}

		pat = Pattern.compile("[a-zA-Z0-9]{0,30}");
		if(direccion.getDepartamento() != null && !pat.matcher(direccion.getDepartamento()).matches()){
			return false;
		}

		pat = Pattern.compile("[a-zA-Z0-9]{1,30}");
		if(direccion.getBarrio() != null && direccion.getBarrio().getNombre() != null && !pat.matcher(direccion.getBarrio().getNombre()).matches()){
			return false;
		}

		pat = Pattern.compile("[a-zA-Z0-9]{1,30}");
		if(direccion.getLocalidad() == null || pat.matcher(direccion.getLocalidad().getNombre()).matches()){
			return false;
		}

		return true;
	}
}
