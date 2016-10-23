package app.datos.servicios;

import app.datos.entidades.Propietario;
import app.datos.entidades.TipoDocumento;
import app.datos.entidades.Vendedor;
import app.excepciones.PersistenciaException;

public interface PropietarioService {

	public void guardarPropietario(Propietario propietario) throws PersistenciaException;

	public void modificarPropietario(Propietario propietario) throws PersistenciaException;

	public Propietario obtenerPropietario(TipoDocumento tipoDocumento, String documento) throws PersistenciaException;

	public void guardarVendedor(Vendedor vendedor) throws PersistenciaException;

	public void modificarVendedor(Vendedor vendedor) throws PersistenciaException;

	public Vendedor obtenerVendedor(TipoDocumento tipoDocumento, Integer documento) throws PersistenciaException;
}