package app.datos.servicios;

import app.datos.entidades.TipoDocumento;
import app.datos.entidades.Vendedor;
import app.excepciones.PersistenciaException;

public interface VendedorService {

    public void guardarVendedor(Vendedor vendedor) throws PersistenciaException;

    public void modificarVendedor(Vendedor vendedor) throws PersistenciaException;

    public Vendedor obtenerVendedor(TipoDocumento tipoDocumento, Integer documento) throws PersistenciaException;

}
