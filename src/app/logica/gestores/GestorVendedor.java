package app.logica.gestores;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Service;

import app.datos.clases.DatosLogin;
import app.datos.clases.EstadoStr;
import app.datos.clases.FiltroVendedor;
import app.datos.entidades.Estado;
import app.datos.entidades.Vendedor;
import app.datos.servicios.DatosService;
import app.datos.servicios.VendedorService;
import app.excepciones.EntidadExistenteConEstadoBajaException;
import app.excepciones.GestionException;
import app.excepciones.PersistenciaException;
import app.logica.ValidadorFormato;
import app.logica.resultados.ResultadoAutenticacion;
import app.logica.resultados.ResultadoCrearVendedor;
import app.logica.resultados.ResultadoCrearVendedor.ErrorCrearVendedor;
import app.logica.resultados.ResultadoEliminarVendedor;
import app.logica.resultados.ResultadoModificarVendedor;
import app.logica.resultados.ResultadoModificarVendedor.ErrorModificarVendedor;

@Service
public class GestorVendedor {

	@Resource
	protected VendedorService persistidorVendedor;

	@Resource DatosService persistidorDatos;

	public ResultadoAutenticacion autenticarVendedor(DatosLogin datos) throws PersistenciaException {
		throw new NotYetImplementedException();
	}

	public ResultadoCrearVendedor crearVendedor(Vendedor vendedor) throws PersistenciaException, GestionException {
		ArrayList<ErrorCrearVendedor> errores = new ArrayList<>();

		if(!ValidadorFormato.validarNombre(vendedor.getNombre())){
			errores.add(ErrorCrearVendedor.Formato_Nombre_Incorrecto);
		}

		if(!ValidadorFormato.validarApellido(vendedor.getApellido())){
			errores.add(ErrorCrearVendedor.Formato_Apellido_Incorrecto);
		}

		if(!ValidadorFormato.validarDocumento(vendedor.getTipoDocumento(), vendedor.getNumeroDocumento())){
			errores.add(ErrorCrearVendedor.Formato_Documento_Incorrecto);
		}

		Vendedor vendedorAuxiliar = persistidorVendedor.obtenerVendedor(new FiltroVendedor(vendedor.getTipoDocumento().getTipo(), vendedor.getNumeroDocumento()));
		if(null != vendedorAuxiliar){
			if(vendedorAuxiliar.getEstado().getEstado().equals(EstadoStr.ALTA)){
				errores.add(ErrorCrearVendedor.Ya_Existe_Vendedor);
			}
			else{
				throw new EntidadExistenteConEstadoBajaException();
			}
		}

		if(errores.isEmpty()){
			ArrayList<Estado> estados = persistidorDatos.obtenerEstados();
			for(Estado e: estados) {
				if(e.getEstado().equals(EstadoStr.ALTA)) {
					vendedor.setEstado(e);
				}
			}
			persistidorVendedor.guardarVendedor(vendedor);
		}

		return new ResultadoCrearVendedor(errores.toArray(new ErrorCrearVendedor[0]));
	}

	public ResultadoModificarVendedor modificarVendedor(Vendedor vendedor) throws PersistenciaException {
		ArrayList<ErrorModificarVendedor> errores = new ArrayList<>();

		if(!ValidadorFormato.validarNombre(vendedor.getNombre())){
			errores.add(ErrorModificarVendedor.Formato_Nombre_Incorrecto);
		}

		if(!ValidadorFormato.validarApellido(vendedor.getApellido())){
			errores.add(ErrorModificarVendedor.Formato_Apellido_Incorrecto);
		}

		if(!ValidadorFormato.validarDocumento(vendedor.getTipoDocumento(), vendedor.getNumeroDocumento())){
			errores.add(ErrorModificarVendedor.Formato_Documento_Incorrecto);
		}

		Vendedor vendedorAuxiliar = persistidorVendedor.obtenerVendedor(new FiltroVendedor(vendedor.getTipoDocumento().getTipo(), vendedor.getNumeroDocumento()));
		if(null != vendedorAuxiliar && !vendedor.equals(vendedorAuxiliar)){
			errores.add(ErrorModificarVendedor.Otro_Vendedor_Posee_Mismo_Documento_Y_Tipo);
		}

		if(errores.isEmpty()){
			persistidorVendedor.modificarVendedor(vendedor);
		}

		return new ResultadoModificarVendedor(errores.toArray(new ErrorModificarVendedor[0]));
	}

	public ResultadoEliminarVendedor eliminarVendedor() throws PersistenciaException {
		throw new NotYetImplementedException();
	}
}
