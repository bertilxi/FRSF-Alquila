package app.datos.servicios;

import app.datos.entidades.Vendedor;
import app.excepciones.PersistenciaException;

public interface VendedorService {

	void guardarVendedor(Vendedor vendedor) throws PersistenciaException;
	void modificarVendedor(Vendedor vendedor) throws PersistenciaException;
	Vendedor obtenerVendedor(Integer dni)  throws PersistenciaException;
}
