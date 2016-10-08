package app.datos.servicios;

import app.datos.entidades.Cliente;
import app.datos.entidades.TipoDocumento;
import app.excepciones.PersistenciaException;

public interface ClienteService {

	public void guardarCliente(Cliente cliente) throws PersistenciaException;
	public void modificarCliente(Cliente cliente) throws PersistenciaException;
	public Cliente obtenerCliente(TipoDocumento tipoDocumento,Integer documento) throws PersistenciaException;
}
