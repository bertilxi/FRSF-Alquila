package app.logica;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import app.datos.clases.DatosLogin;
import app.datos.entidades.TipoDocumento;
import app.datos.entidades.Vendedor;
import app.excepciones.GestionException;
import app.excepciones.PersistenciaException;
import app.logica.gestores.GestorCliente;
import app.logica.gestores.GestorDatos;
import app.logica.gestores.GestorInmueble;
import app.logica.gestores.GestorPropietario;
import app.logica.gestores.GestorVendedor;
import app.logica.resultados.ResultadoAutenticacion;
import app.logica.resultados.ResultadoCrearVendedor;
import app.logica.resultados.ResultadoModificarVendedor;

@Service
public class CoordinadorJavaFX {

	@Resource
	GestorVendedor gestorVendedor;

	@Resource
	GestorCliente gestorCliente;

	@Resource
	GestorPropietario gestorPropietario;

	@Resource
	GestorInmueble gestorInmueble;

	@Resource
	GestorDatos gestorDatos;

	public ResultadoAutenticacion autenticarVendedor(DatosLogin login) throws PersistenciaException {
		return gestorVendedor.autenticarVendedor(login);
	}

	public ResultadoCrearVendedor crearVendedor(Vendedor vendedor) throws PersistenciaException, GestionException {
		return gestorVendedor.crearVendedor(vendedor);
	}

	public ArrayList<TipoDocumento> obtenerTiposDeDocumento() throws PersistenciaException {
		return gestorDatos.obtenerTiposDeDocumento();
	}

	public ResultadoModificarVendedor modificarVendedor(Vendedor vendedor) throws PersistenciaException, GestionException {
		return gestorVendedor.modificarVendedor(vendedor);
	}

	public ArrayList<Vendedor> obtenerVendedores() throws PersistenciaException {
		return gestorVendedor.obtenerVendedores();
	}

}