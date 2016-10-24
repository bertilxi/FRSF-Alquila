package app.datos.servicios;

import app.datos.clases.FiltroCliente;
import app.datos.entidades.Cliente;
import app.excepciones.PersistenciaException;

public interface ClienteService {

	public void guardarCliente(Cliente cliente) throws PersistenciaException;

	public void modificarCliente(Cliente cliente) throws PersistenciaException;

	public Cliente obtenerCliente(FiltroCliente filtro) throws PersistenciaException;
}
