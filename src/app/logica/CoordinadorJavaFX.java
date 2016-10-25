package app.logica;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import app.datos.clases.DatosLogin;
import app.excepciones.PersistenciaException;
import app.logica.gestores.GestorVendedor;
import app.logica.resultados.ResultadoAutenticacion;

@Service
public class CoordinadorJavaFX {

	@Resource
	GestorVendedor gestorVendedor;

	public ResultadoAutenticacion autenticarVendedor(DatosLogin login) throws PersistenciaException {
		return gestorVendedor.autenticarVendedor(login);
	}
}
