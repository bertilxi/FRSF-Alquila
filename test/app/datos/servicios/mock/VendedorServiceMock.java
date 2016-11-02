package app.datos.servicios.mock;

import app.datos.clases.FiltroVendedor;
import app.datos.entidades.Vendedor;
import app.datos.servicios.VendedorService;
import app.excepciones.PersistenciaException;

public class VendedorServiceMock implements VendedorService {

	private Vendedor vendedor;
	private Throwable excepcion;

	public VendedorServiceMock(Vendedor vendedor, Throwable excepcion) {
		this.vendedor = vendedor;
		this.excepcion = excepcion;
	}

	@Override
	public void guardarVendedor(Vendedor vendedor) throws PersistenciaException {
	}

	@Override
	public void modificarVendedor(Vendedor vendedor) throws PersistenciaException {
	}

	@Override
	public Vendedor obtenerVendedor(FiltroVendedor filtro) throws PersistenciaException {
		if(vendedor != null){
			return vendedor;
		}
		if(excepcion == null){
			return null;
		}
		if(excepcion instanceof PersistenciaException){
			throw (PersistenciaException) excepcion;
		}
		new Integer("asd");
		return null;
	}
}
