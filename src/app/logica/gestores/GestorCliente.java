package app.logica.gestores;

import java.util.ArrayList;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import app.datos.entidades.Cliente;
import app.datos.servicios.ClienteService;
import app.excepciones.PersistenciaException;
import app.logica.resultados.ResultadoCrearCliente;
import app.logica.resultados.ResultadoCrearCliente.ErrorResultadoCrearCliente;

@Service
public class GestorCliente {

	private ClienteService persistidorCliente;

	public ResultadoCrearCliente crearCliente(Cliente cliente) throws PersistenciaException {
		ArrayList<ErrorResultadoCrearCliente> errores = new ArrayList<ErrorResultadoCrearCliente>();


		Pattern pat = Pattern.compile("[a-zA-Z\\ ÁÉÍÓÚÜÑáéíóúüñ]{1,100}");
		if (!pat.matcher(cliente.getNombre()).matches()) {
			errores.add(ErrorResultadoCrearCliente.Formato_Nombre_Incorrecto);
		}

		if (!pat.matcher(cliente.getApellido()).matches()) {
			errores.add(ErrorResultadoCrearCliente.Formato_Apellido_Incorrecto);
		}

		pat = Pattern.compile("[0-9\\-]{0,20}");
		if (!pat.matcher(cliente.getTelefono()).matches()) {
			errores.add(ErrorResultadoCrearCliente.Formato_Telefono_Incorrecto);
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
}