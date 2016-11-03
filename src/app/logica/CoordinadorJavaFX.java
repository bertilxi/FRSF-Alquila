package app.logica;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import app.datos.clases.DatosLogin;
import app.datos.entidades.Cliente;
import app.datos.entidades.Localidad;
import app.datos.entidades.Pais;
import app.datos.entidades.Propietario;
import app.datos.entidades.Provincia;
import app.datos.entidades.TipoDocumento;
import app.datos.entidades.TipoInmueble;
import app.datos.entidades.Vendedor;
import app.excepciones.GestionException;
import app.excepciones.PersistenciaException;
import app.logica.gestores.GestorCliente;
import app.logica.gestores.GestorDatos;
import app.logica.gestores.GestorInmueble;
import app.logica.gestores.GestorPropietario;
import app.logica.gestores.GestorVendedor;
import app.logica.resultados.ResultadoAutenticacion;
import app.logica.resultados.ResultadoCrearCliente;
import app.logica.resultados.ResultadoCrearPropietario;
import app.logica.resultados.ResultadoCrearVendedor;
import app.logica.resultados.ResultadoEliminarCliente;
import app.logica.resultados.ResultadoEliminarPropietario;
import app.logica.resultados.ResultadoModificarCliente;
import app.logica.resultados.ResultadoModificarPropietario;
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

	public ResultadoCrearCliente crearCliente(Cliente cliente) throws PersistenciaException, GestionException {
		return gestorCliente.crearCliente(cliente);
	}

	public ResultadoModificarCliente modificarCliente(Cliente cliente) throws PersistenciaException {
		return gestorCliente.modificarCliente(cliente);
	}

	public ResultadoEliminarCliente eliminarCliente(Cliente cliente) throws PersistenciaException {
		return gestorCliente.eliminarCliente(cliente);
	}

	public ArrayList<Cliente> obtenerClientes() throws PersistenciaException {
		return gestorCliente.obtenerClientes();
	}

	public ResultadoCrearPropietario crearPropietario(Propietario propietario) throws PersistenciaException, GestionException {
		return gestorPropietario.crearPropietario(propietario);
	}

	public ResultadoModificarPropietario modificarPropietario(Propietario propietario) throws PersistenciaException {
		return gestorPropietario.modificarPropietario(propietario);
	}

	public ResultadoEliminarPropietario eliminarPropietario(Propietario propietario) throws PersistenciaException {
		return gestorPropietario.eliminarPropietario(propietario);
	}

	public ArrayList<Propietario> obtenerPropietarios() throws PersistenciaException {
		return gestorPropietario.obtenerPropietarios();
	}

	public ArrayList<TipoInmueble> obtenerTiposInmueble() throws PersistenciaException {
		return gestorDatos.obtenerTiposInmueble();
	}

	public ArrayList<Provincia> obtenerProvinciasDe(Pais pais) throws PersistenciaException {
		return gestorDatos.obtenerProvinciasDe(pais);
	}

	public ArrayList<Localidad> obtenerLocalidadesDe(Provincia prov) throws PersistenciaException {
		return gestorDatos.obtenerLocalidadesDe(prov);
	}

	public ArrayList<Pais> obtenerPaises() throws PersistenciaException {
		return gestorDatos.obtenerPaises();
	}

}