package app.logica.gestores;

import javax.annotation.Resource;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Service;

import app.datos.clases.DatosLogin;
import app.datos.servicios.VendedorService;
import app.excepciones.PersistenciaException;
import app.logica.resultados.ResultadoAutenticacion;
import app.logica.resultados.ResultadoCrearVendedor;
import app.logica.resultados.ResultadoEliminarVendedor;

@Service
public class GestorVendedor {

	@Resource
	protected VendedorService persistidorVendedor;

	public ResultadoAutenticacion autenticarVendedor(DatosLogin datos) throws PersistenciaException {
		throw new NotYetImplementedException();
	}

	public ResultadoCrearVendedor crearVendedor() throws PersistenciaException {
		throw new NotYetImplementedException();
	}

	public ResultadoEliminarVendedor eliminarVendedor() throws PersistenciaException {
		throw new NotYetImplementedException();
	}
}
