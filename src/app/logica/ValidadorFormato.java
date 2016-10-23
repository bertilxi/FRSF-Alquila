package app.logica;

import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import app.datos.entidades.Direccion;
import app.datos.entidades.TipoDocumento;

@Service
public class ValidadorFormato {

	public static Boolean validarDocumento(TipoDocumento tipo, String numeroDocumento) {
		Pattern pat;

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

	public static Boolean validarNombre(String nombre) {
		Pattern pat = Pattern.compile("[a-zA-Z\\ ÁÉÍÓÚÜÑáéíóúüñ]{1,30}");
		return pat.matcher(nombre).matches();
	}

	public static Boolean validarApellido(String apellido) {
		return validarNombre(apellido);
	}

	public static Boolean validarEmail(String email) {
		Pattern pat = Pattern.compile("([0-9a-zA-Z]+(.[0-9a-zA-Z]+)*@[0-9a-zA-Z]+(.[0-9a-zA-Z]+)*(.[a-z]{2,4})){0,30}");

		return pat.matcher(email).matches();
	}

	public static Boolean validarTelefono(String telefono) {
		Pattern pat = Pattern.compile("[0-9\\-]{0,20}");

		return pat.matcher(telefono).matches();
	}

	public static Boolean validarDireccion(Direccion direccion) {
		Pattern pat;

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
