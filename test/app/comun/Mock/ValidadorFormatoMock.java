package app.comun.Mock;

import app.comun.ValidadorFormato;
import app.datos.entidades.Direccion;
import app.datos.entidades.TipoDocumento;

public class ValidadorFormatoMock extends ValidadorFormato{
	
	@Override
	public Boolean validarDireccion(Direccion direccion) {
		return true;
	}
	@Override
	public Boolean validarDoublePositivo(Double numeroDouble) {
		return true;
	}
	@Override
	public Boolean validarApellido(String apellido) {
		return true;
	}
	@Override
	public Boolean validarDocumento(TipoDocumento tipo, String numeroDocumento) {
		return true;
	}
	@Override
	public Boolean validarEmail(String email) {
		return true;
	}
	@Override
	public Boolean validarEnteroPositivo(Integer numeroInteger) {
		return true;
	}
	@Override
	public Boolean validarLocalidad(String localidad) {
		return true;
	}
	@Override
	public Boolean validarNombre(String nombre) {
		return true;
	}
	@Override
	public Boolean validarTelefono(String telefono) {
		return true;
	}
}
