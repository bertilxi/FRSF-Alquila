package app.logica.gestores;

import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import app.datos.entidades.Cliente;
import app.datos.entidades.Propietario;
import app.datos.servicios.ClienteService;
import app.datos.servicios.PropietarioService;
import app.excepciones.PersistenciaException;
import app.logica.resultados.ResultadoCrearPropietario;
import app.logica.resultados.ResultadoCrearPropietario.ErrorCrearPropietario;
import app.logica.resultados.ResultadoModificarCliente.ErrorModificarCliente;
import app.logica.resultados.ResultadoModificarPropietario;
import app.logica.resultados.ResultadoModificarPropietario.ErrorModificarPropietario;
import app.logica.validadores.ValidadorFormato;

@Service
public class GestorPropietario {

	@Resource
	private PropietarioService persistidorPropietario;

	@Resource
	private ClienteService persistidorCliente;

	@Resource
	private ValidadorFormato validador;

	public ResultadoCrearPropietario crearPropietario(Propietario propietario) throws PersistenciaException {
		ArrayList<ErrorCrearPropietario> errores = new ArrayList<>();

		if(!validador.validarNombre(propietario.getNombre())){
			errores.add(ErrorCrearPropietario.Formato_Nombre_Incorrecto);
		}

		if(!validador.validarApellido(propietario.getApellido())){
			errores.add(ErrorCrearPropietario.Formato_Apellido_Incorrecto);
		}

		if(!validador.validarDocumento(propietario.getTipoDocumento(), propietario.getNumeroDocumento())){
			errores.add(ErrorCrearPropietario.Formato_Documento_Incorrecto);
		}

		if(!validador.validarTelefono(propietario.getTelefono())){
			errores.add(ErrorCrearPropietario.Formato_Telefono_Incorrecto);
		}

		if(!validador.validarEmail(propietario.getEmail())){
			errores.add(ErrorCrearPropietario.Formato_Email_Incorrecto);
		}

		if(!validador.validarDireccion(propietario.getDireccion())){
			errores.add(ErrorCrearPropietario.Formato_Direccion_Incorrecto);
		}

		if(persistidorPropietario.obtenerPropietario(propietario.getTipoDocumento(), propietario.getNumeroDocumento()) != null){
			errores.add(ErrorCrearPropietario.Ya_Existe_Propietario);
		}

		if(errores.isEmpty()){
			persistidorPropietario.guardarPropietario(propietario);
		}

		return new ResultadoCrearPropietario(errores.toArray(new ErrorCrearPropietario[0]));
	}

	public ResultadoModificarPropietario modificarPropietario(Cliente cliente) throws PersistenciaException {
		ArrayList<ErrorModificarCliente> errores = new ArrayList<>();

		Pattern pat = Pattern.compile("[a-zA-Z\\ ÁÉÍÓÚÜÑáéíóúüñ]{1,100}");
		if(!pat.matcher(cliente.getNombre()).matches()){
			errores.add(ErrorModificarCliente.Formato_Nombre_Incorrecto);
		}

		if(!pat.matcher(cliente.getApellido()).matches()){
			errores.add(ErrorModificarCliente.Formato_Apellido_Incorrecto);
		}

		pat = Pattern.compile("[0-9\\-]{0,20}");
		if(!pat.matcher(cliente.getTelefono()).matches()){
			errores.add(ErrorModificarCliente.Formato_Telefono_Incorrecto);
		}

		switch(cliente.getTipoDocumento().getTipo()) {
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
		if(!pat.matcher(cliente.getNumeroDocumento()).matches()){
			errores.add(ErrorModificarCliente.Formato_Documento_Incorrecto);
		}

		Cliente clienteAuxiliar = persistidorCliente.obtenerCliente(cliente.getTipoDocumento(), cliente.getNumeroDocumento());
		if(null != clienteAuxiliar && !cliente.getId().equals(clienteAuxiliar.getId())){
			errores.add(ErrorModificarCliente.Otro_Cliente_Posee_Mismo_Documento_Y_Tipo);
		}

		if(errores.isEmpty()){
			persistidorCliente.modificarCliente(cliente);
		}

		return new ResultadoModificarPropietario(errores.toArray(new ErrorModificarPropietario[0]));
	}

}
