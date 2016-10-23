package app.logica.gestores;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import app.datos.entidades.Cliente;
import app.datos.servicios.ClienteService;
import app.excepciones.PersistenciaException;
import app.logica.ValidadorFormato;
import app.logica.resultados.ResultadoCrearCliente;
import app.logica.resultados.ResultadoCrearCliente.ErrorCrearCliente;
import app.logica.resultados.ResultadoModificarCliente;
import app.logica.resultados.ResultadoModificarCliente.ErrorModificarCliente;

@Service
public class GestorCliente {

	@Resource
	private ClienteService persistidorCliente;

	public ResultadoCrearCliente crearCliente(Cliente cliente) throws PersistenciaException {
		ArrayList<ErrorCrearCliente> errores = new ArrayList<>();

		if(!ValidadorFormato.validarNombre(cliente.getNombre())){
			errores.add(ErrorCrearCliente.Formato_Nombre_Incorrecto);
		}

		if(!ValidadorFormato.validarApellido(cliente.getApellido())){
			errores.add(ErrorCrearCliente.Formato_Apellido_Incorrecto);
		}

		if(!ValidadorFormato.validarTelefono(cliente.getTelefono())){
			errores.add(ErrorCrearCliente.Formato_Telefono_Incorrecto);
		}

		if(!ValidadorFormato.validarDocumento(cliente.getTipoDocumento(), cliente.getNumeroDocumento())){
			errores.add(ErrorCrearCliente.Formato_Documento_Incorrecto);
		}

		if(null != persistidorCliente.obtenerCliente(cliente.getTipoDocumento(), cliente.getNumeroDocumento())){
			errores.add(ErrorCrearCliente.Ya_Existe_Cliente);
		}

		if(errores.isEmpty()){
			persistidorCliente.guardarCliente(cliente);
		}

		return new ResultadoCrearCliente(errores.toArray(new ErrorCrearCliente[0]));
	}

	public ResultadoModificarCliente modificarCliente(Cliente cliente) throws PersistenciaException {
		ArrayList<ErrorModificarCliente> errores = new ArrayList<>();

		if(!ValidadorFormato.validarNombre(cliente.getNombre())){
			errores.add(ErrorModificarCliente.Formato_Nombre_Incorrecto);
		}

		if(!ValidadorFormato.validarApellido(cliente.getApellido())){
			errores.add(ErrorModificarCliente.Formato_Apellido_Incorrecto);
		}

		if(!ValidadorFormato.validarTelefono(cliente.getTelefono())){
			errores.add(ErrorModificarCliente.Formato_Telefono_Incorrecto);
		}

		if(!ValidadorFormato.validarDocumento(cliente.getTipoDocumento(), cliente.getNumeroDocumento())){
			errores.add(ErrorModificarCliente.Formato_Documento_Incorrecto);
		}

		Cliente clienteAuxiliar = persistidorCliente.obtenerCliente(cliente.getTipoDocumento(), cliente.getNumeroDocumento());
		if(null != clienteAuxiliar && !cliente.getId().equals(clienteAuxiliar.getId())){
			errores.add(ErrorModificarCliente.Otro_Cliente_Posee_Mismo_Documento_Y_Tipo);
		}

		if(errores.isEmpty()){
			persistidorCliente.modificarCliente(cliente);
		}

		return new ResultadoModificarCliente(errores.toArray(new ErrorModificarCliente[0]));
	}
}
