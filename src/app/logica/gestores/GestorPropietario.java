package app.logica.gestores;

import java.util.ArrayList;
import java.util.regex.Pattern;

import app.datos.entidades.Cliente;
import app.datos.entidades.Propietario;
import app.datos.servicios.ClienteService;
import app.datos.servicios.PropietarioService;
import app.excepciones.PersistenciaException;
import app.logica.resultados.ResultadoCrearCliente;
import app.logica.resultados.ResultadoModificarCliente;
import app.logica.resultados.ResultadoModificarPropietario;
import app.logica.resultados.ResultadoCrearCliente.ErrorResultadoCrearCliente;
import app.logica.resultados.ResultadoCrearPropietario;
import app.logica.resultados.ResultadoCrearPropietario.ErrorResultadoCrearPropietario;
import app.logica.resultados.ResultadoModificarCliente.ErrorResultadoModificarCliente;

public class GestorPropietario {

	private PropietarioService persistidorPropietario;
	private ValidadorFormato validador;
	
	public GestorPropietario(PropietarioService persistidorPropietario, ValidadorFormato validador) {
		super();
		this.persistidorPropietario = persistidorPropietario;
		this.validador = validador;
	}
	
	public ResultadoCrearPropietario crearPropietario(Propietario propietario) throws PersistenciaException {
		ArrayList<ErrorResultadoCrearPropietario> errores = new ArrayList<ErrorResultadoCrearPropietario>();
		
		if(!validador.validarNombre(propietario.getNombre())) {
			errores.add(ErrorResultadoCrearPropietario.Formato_Nombre_Incorrecto);
		}
		
		if(!validador.validarApellido(propietario.getApellido())) {
			errores.add(ErrorResultadoCrearPropietario.Formato_Apellido_Incorrecto);
		}

		if(!validador.validarDocumento(propietario.getTipoDocumento(),propietario.getNumeroDocumento())) {
			errores.add(ErrorResultadoCrearPropietario.Formato_Documento_Incorrecto);
		}
		
		if(!validador.validarTelefono(propietario.getTelefono())) {
			errores.add(ErrorResultadoCrearPropietario.Formato_Telefono_Incorrecto);
		}
		
		if(!validador.validarEmail(propietario.getEmail())) {
			errores.add(ErrorResultadoCrearPropietario.Formato_Email_Incorrecto);
		}
		
		if(!validador.validarDireccion(propietario.getDireccion())) {
			errores.add(ErrorResultadoCrearPropietario.Formato_Direccion_Incorrecto);
		}
		
		if(persistidorPropietario.obtenerPropietario(propietario.getTipoDocumento(), propietario.getNumeroDocumento())!=null) {
			errores.add(ErrorResultadoCrearPropietario.Ya_Existe_Propietario);
		}

		if(errores.isEmpty()) {
			persistidorPropietario.guardarPropietario(propietario);
		}

		return new ResultadoCrearPropietario(errores.toArray(new ErrorResultadoCrearPropietario[0]));
	}

	public ResultadoModificarPropietario modificarPropietario(Cliente cliente) throws PersistenciaException {
		ArrayList<ErrorResultadoModificarCliente> errores = new ArrayList<ErrorResultadoModificarCliente>();


		Pattern pat = Pattern.compile("[a-zA-Z\\ ÁÉÍÓÚÜÑáéíóúüñ]{1,100}");
		if (!pat.matcher(cliente.getNombre()).matches()) {
			errores.add(ErrorResultadoModificarCliente.Formato_Nombre_Incorrecto);
		}

		if (!pat.matcher(cliente.getApellido()).matches()) {
			errores.add(ErrorResultadoModificarCliente.Formato_Apellido_Incorrecto);
		}

		pat = Pattern.compile("[0-9\\-]{0,20}");
		if (!pat.matcher(cliente.getTelefono()).matches()) {
			errores.add(ErrorResultadoModificarCliente.Formato_Telefono_Incorrecto);
		}

		switch (cliente.getTipoDocumento().getTipo()) {
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
		}
		if (!pat.matcher(cliente.getNumeroDocumento()).matches()) {
			errores.add(ErrorResultadoModificarCliente.Formato_Documento_Incorrecto);
		}

		Cliente clienteAuxiliar = persistidorCliente.obtenerCliente(cliente.getTipoDocumento(), cliente.getNumeroDocumento());
		if(null!=clienteAuxiliar && !cliente.getId().equals(clienteAuxiliar.getId())) {
			errores.add(ErrorResultadoModificarCliente.Otro_Cliente_Posee_Mismo_Documento_Y_Tipo);
		}

		if(errores.isEmpty()) {
			persistidorCliente.modificarCliente(cliente);
		}

		return new ResultadoModificarCliente(errores.toArray(new ErrorResultadoModificarCliente[0]));
	}

	
}
