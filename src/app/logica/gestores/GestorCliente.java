package app.logica.gestores;

import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import app.datos.entidades.Cliente;
import app.datos.servicios.ClienteService;
import app.excepciones.ObjNotFoundException;
import app.excepciones.PersistenciaException;
import app.logica.resultados.ResultadoCrearCliente;

@Service
public class GestorCliente {
	
	private ClienteService persistidorCliente;

	public ResultadoCrearCliente crearCliente(Cliente cliente) throws PersistenciaException {
		ResultadoCrearCliente resultado = new ResultadoCrearCliente();
		
		Pattern pat = Pattern.compile("[a-zA-Z\\ ÁÉÍÓÚÜÑáéíóúüñ]{1,100}");
		if (!pat.matcher(cliente.getNombre()).matches()) {
			//informar error en clase resultado
		}
		
		if (!pat.matcher(cliente.getApellido()).matches()) {
			//informar error en clase resultado
		}
		
		pat = Pattern.compile("[0-9\\-]{0,20}");
		if (!pat.matcher(cliente.getTelefono()).matches()) {
			//informar error en clase resultado
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
			//informar error en clase resultado
		}
		
		if(null!=persistidorCliente.obtenerCliente(cliente.getTipoDocumento(), cliente.getNumeroDocumento())) {
			//informar que ya existe un cliente con este tipo de documento y documento
		}
		
		return resultado;
	}
}
