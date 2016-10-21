package app.logica.gestores;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import app.datos.entidades.Cliente;
import app.datos.servicios.ClienteService;
import app.excepciones.PersistenciaException;
import app.logica.resultados.ResultadoCrearCliente;
import app.logica.resultados.ResultadoModificarCliente;
import app.logica.resultados.ResultadoCrearCliente.ErrorResultadoCrearCliente;
import app.logica.resultados.ResultadoModificarCliente.ErrorResultadoModificarCliente;

@Service
public class GestorCliente {

	private ClienteService persistidorCliente;

	private ValidadorFormato validador;

	public ResultadoCrearCliente crearCliente(Cliente cliente) throws PersistenciaException {
		ArrayList<ErrorResultadoCrearCliente> errores = new ArrayList<ErrorResultadoCrearCliente>();

		if (!validador.validarNombre(cliente.getNombre())) {
			errores.add(ErrorResultadoCrearCliente.Formato_Nombre_Incorrecto);
		}

		if (!validador.validarApellido(cliente.getApellido())) {
			errores.add(ErrorResultadoCrearCliente.Formato_Apellido_Incorrecto);
		}

		if (!validador.validarTelefono(cliente.getTelefono())) {
			errores.add(ErrorResultadoCrearCliente.Formato_Telefono_Incorrecto);
		}

		if (!validador.validarDocumento(cliente.getTipoDocumento(), cliente.getNumeroDocumento())) {
			errores.add(ErrorResultadoCrearCliente.Formato_Documento_Incorrecto);
		}

		if(null!=persistidorCliente.obtenerCliente(cliente.getTipoDocumento(), cliente.getNumeroDocumento())) {
			errores.add(ErrorResultadoCrearCliente.Ya_Existe_Cliente);
		}

		if(errores.isEmpty()) {
			persistidorCliente.guardarCliente(cliente);
		}

		return new ResultadoCrearCliente(errores.toArray(new ErrorResultadoCrearCliente[0]));
	}

	public ResultadoModificarCliente modificarCliente(Cliente cliente) throws PersistenciaException {
		ArrayList<ErrorResultadoModificarCliente> errores = new ArrayList<ErrorResultadoModificarCliente>();

		if (!validador.validarNombre(cliente.getNombre())) {
			errores.add(ErrorResultadoModificarCliente.Formato_Nombre_Incorrecto);
		}

		if (!validador.validarApellido(cliente.getApellido())) {
			errores.add(ErrorResultadoModificarCliente.Formato_Apellido_Incorrecto);
		}

		if (!validador.validarTelefono(cliente.getTelefono())) {
			errores.add(ErrorResultadoModificarCliente.Formato_Telefono_Incorrecto);
		}

		if (!validador.validarDocumento(cliente.getTipoDocumento(), cliente.getNumeroDocumento())) {
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
