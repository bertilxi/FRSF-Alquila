package app.logica.gestores;

import java.util.ArrayList;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import app.comun.ValidadorFormato;
import app.datos.clases.EstadoInmuebleStr;
import app.datos.entidades.EstadoInmueble;
import app.datos.entidades.Reserva;
import app.datos.entidades.Venta;
import app.datos.servicios.VentaService;
import app.excepciones.PersistenciaException;
import app.logica.resultados.ResultadoCrearVenta;
import app.logica.resultados.ResultadoCrearVenta.ErrorCrearVenta;

/**
 * Gestor que implementa la capa lógica del ABM Venta y funciones asociadas a una venta.
 */
@Service
public class GestorVenta {

	@Resource
	protected VentaService persistidorVenta;

	@Resource
	protected GestorDatos gestorDatos;

	@Resource
	protected GestorPDF gestorPDF;

	@Resource
	protected ValidadorFormato validador;

	/**
	 * Método para crear una venta. Primero se validan las reglas de negocio y luego se persiste.
	 * Pertenece a la taskcard 30 de la iteración 2 y a la historia 8
	 *
	 * @param venta
	 * 			venta que se quiere crear
	 * @return resultado de la operación
	 * @throws PersistenciaException
	 * 			si falló al persistir
	 */
	public ResultadoCrearVenta crearVenta(Venta venta) throws PersistenciaException {
		ArrayList<ErrorCrearVenta> errores = new ArrayList<>();

		if(venta.getCliente() == null){
			errores.add(ErrorCrearVenta.Cliente_Vacío);
		}

		if(venta.getVendedor() == null){
			errores.add(ErrorCrearVenta.Vendedor_Vacío);
		}

		if(venta.getInmueble() == null){
			errores.add(ErrorCrearVenta.Inmueble_Vacío);
		}

		if(venta.getPropietario() == null){
			errores.add(ErrorCrearVenta.Propietario_Vacío);
		}

		if(venta.getImporte() == null){
			errores.add(ErrorCrearVenta.Importe_vacío);
		}

		if(venta.getMedioDePago().isEmpty() || venta.getMedioDePago() == null){
			errores.add(ErrorCrearVenta.Medio_De_Pago_Vacío);
		} else if(!validador.validarMedioDePago(venta.getMedioDePago())) {
			errores.add(ErrorCrearVenta.Formato_Medio_De_Pago_Incorrecto);
		}

		if(venta.getCliente().getTipoDocumento().equals(venta.getPropietario().getTipoDocumento()) && venta.getCliente().getNumeroDocumento().equals(venta.getPropietario().getNumeroDocumento())) {
			errores.add(ErrorCrearVenta.Cliente_Igual_A_Propietario);
		}

		Date fechaHoy = new Date(System.currentTimeMillis());
		for(Reserva r: venta.getInmueble().getReservas()) {
			if (fechaHoy.after(r.getFechaInicio()) && fechaHoy.before(r.getFechaFin()) && !r.getCliente().equals(venta.getCliente())) {
				errores.add(ErrorCrearVenta.Inmueble_Reservado_Por_Otro_Cliente);
				break;
			}
		}

		if(errores.isEmpty()){
			ArrayList<EstadoInmueble> estadosInm = gestorDatos.obtenerEstadosInmueble();
			for(EstadoInmueble ei: estadosInm){
				if(ei.getEstado().equals(EstadoInmuebleStr.NO_VENDIDO)){
					venta.getInmueble().setEstadoInmueble(ei);
					break;
				}
			}
			venta.setFecha(fechaHoy);
			venta.setArchivoPDF(gestorPDF.generarPDF(venta));
			persistidorVenta.guardarVenta(venta);
		}

		return new ResultadoCrearVenta(errores.toArray(new ErrorCrearVenta[0]));
	}
}
