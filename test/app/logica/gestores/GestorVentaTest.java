package app.logica.gestores;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import app.comun.ValidadorFormato;
import app.datos.clases.EstadoInmuebleStr;
import app.datos.clases.TipoDocumentoStr;
import app.datos.entidades.Cliente;
import app.datos.entidades.EstadoInmueble;
import app.datos.entidades.Inmueble;
import app.datos.entidades.PDF;
import app.datos.entidades.Propietario;
import app.datos.entidades.Reserva;
import app.datos.entidades.TipoDocumento;
import app.datos.entidades.Vendedor;
import app.datos.entidades.Venta;
import app.datos.servicios.VentaService;
import app.excepciones.GenerarPDFException;
import app.excepciones.SaveUpdateException;
import app.logica.resultados.ResultadoCrearVenta;
import app.logica.resultados.ResultadoCrearVenta.ErrorCrearVenta;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

/**
 * Este test prueba los métodos de la clase GestorVenta
 */
@RunWith(JUnitParamsRunner.class)
public class GestorVentaTest {

	@Test
	@Parameters
	/**
	 * Prueba el método crearVenta, el cual corresponde con la taskcard 30 de la iteración 2 y a la historia 8
	 *
	 */
	public void testCrearVenta(Venta venta,
			ResultadoCrearVenta resultadoEsperado,
			boolean resultadoCorrecto,
			boolean medioDePagoValido,
			Exception excepcionPersistencia,
			Exception excepcionPDF) throws Throwable {

		GestorDatos gestorDatosMock = mock(GestorDatos.class);
		GestorPDF gestorPDFMock = mock(GestorPDF.class);
		VentaService persistidorVentaMock = mock(VentaService.class);
		ValidadorFormato validadorMock = mock(ValidadorFormato.class);

		if(excepcionPDF == null){
			Mockito.when(gestorPDFMock.generarPDF(venta)).thenReturn(new PDF());
		}
		else{
			Mockito.when(gestorPDFMock.generarPDF(venta)).thenThrow(excepcionPDF);
		}
		if(excepcionPersistencia == null){
			Mockito.doNothing().when(persistidorVentaMock).guardarVenta(venta);
		}
		else{
			Mockito.doThrow(excepcionPersistencia).when(persistidorVentaMock).guardarVenta(venta);
		}
		Mockito.when(validadorMock.validarMedioDePago(venta.getMedioDePago())).thenReturn(medioDePagoValido);

		ArrayList<EstadoInmueble> estadosInm = new ArrayList<>();
		estadosInm.add(new EstadoInmueble(EstadoInmuebleStr.VENDIDO));
		Mockito.when(gestorDatosMock.obtenerEstadosInmueble()).thenReturn(estadosInm);

		GestorVenta gestorVenta = new GestorVenta() {
			{
				this.persistidorVenta = persistidorVentaMock;
				this.gestorDatos = gestorDatosMock;
				this.gestorPDF = gestorPDFMock;
				this.validador = validadorMock;
			}
		};

		ResultadoCrearVenta resultadoCrearVenta;
		try{
			resultadoCrearVenta = gestorVenta.crearVenta(venta);
			assertEquals(resultadoEsperado.getErrores(), resultadoCrearVenta.getErrores());
		} catch(Exception e){
			if(excepcionPDF != null){
				assertEquals(excepcionPDF.getClass(), e.getClass());
			}
			if(excepcionPersistencia != null){
				assertEquals(excepcionPersistencia.getClass(), e.getClass());
			}
		}

		if(venta.getMedioDePago() != null) {
			Mockito.verify(validadorMock).validarMedioDePago(venta.getMedioDePago());
		}
		if(resultadoCorrecto){
			Mockito.verify(gestorDatosMock).obtenerEstadosInmueble();
			Mockito.verify(gestorPDFMock).generarPDF(venta);
			Mockito.verify(persistidorVentaMock).guardarVenta(venta);

			assertEquals(EstadoInmuebleStr.VENDIDO, venta.getInmueble().getEstadoInmueble().getEstado());
			assertEquals(PDF.class, venta.getArchivoPDF().getClass());
			assertEquals(Date.class, venta.getFecha().getClass());
		}
	}

	protected Object[] parametersForTestCrearVenta() {
		TipoDocumento tipoDoc = new TipoDocumento(TipoDocumentoStr.DNI);
		Propietario p = new Propietario();
		p.setTipoDocumento(tipoDoc).setNumeroDocumento("12345678");
		Vendedor v = new Vendedor();
		Cliente c = new Cliente();
		c.setTipoDocumento(tipoDoc).setNumeroDocumento("87654321");
		Cliente cp = new Cliente();
		cp.setTipoDocumento(tipoDoc).setNumeroDocumento("12345678");
		Inmueble isi = new Inmueble();
		isi.setEstadoInmueble(new EstadoInmueble(EstadoInmuebleStr.VENDIDO));
		Reserva rOtro = new Reserva();
		rOtro.setCliente(cp).setFechaInicio(new Date(System.currentTimeMillis() - 1000000000)).setFechaFin(new Date(System.currentTimeMillis() + 100000000));
		Reserva rMismo = new Reserva();
		rMismo.setCliente(c).setFechaInicio(new Date(System.currentTimeMillis() - 1000000000)).setFechaFin(new Date(System.currentTimeMillis() + 100000000));
		HashSet<Reserva> hROtro = new HashSet<>();
		hROtro.add(rOtro);
		HashSet<Reserva> hRMismo = new HashSet<>();
		hROtro.add(rMismo);
		Inmueble inoMismo = new Inmueble();
		inoMismo.setEstadoInmueble(new EstadoInmueble(EstadoInmuebleStr.NO_VENDIDO)).setReservas(hRMismo);
		Inmueble inoOtro = new Inmueble();
		inoOtro.setEstadoInmueble(new EstadoInmueble(EstadoInmuebleStr.NO_VENDIDO)).setReservas(hROtro);


		//Parámetros de JUnitParams
		return new Object[] {
				//venta,resultadoEsperado,resultadoCorrecto,medioDePagoValido,excepcionPersistencia,excepcionPDF
				/* 0 */new Object[] { new Venta().setCliente(c).setImporte(10.0).setInmueble(new Inmueble().setEstadoInmueble(new EstadoInmueble(EstadoInmuebleStr.NO_VENDIDO))).setMedioDePago("contado").setPropietario(p).setVendedor(v), resultadoCorrecto, true, true, null, null }, //resultado correcto
				/* 1 */new Object[] { new Venta().setCliente(null).setImporte(10.0).setInmueble(new Inmueble().setEstadoInmueble(new EstadoInmueble(EstadoInmuebleStr.NO_VENDIDO))).setMedioDePago("contado").setPropietario(p).setVendedor(v), resultadoCrearCliente_Vacío, false, true, null, null }, //la venta no tiene cliente
				/* 2 */new Object[] { new Venta().setCliente(c).setImporte(10.0).setInmueble(new Inmueble().setEstadoInmueble(new EstadoInmueble(EstadoInmuebleStr.NO_VENDIDO))).setMedioDePago("contado").setPropietario(null).setVendedor(v), resultadoCrearPropietario_Vacío, false, true, null, null }, //la venta no tiene propietario
				/* 3 */new Object[] { new Venta().setCliente(c).setImporte(10.0).setInmueble(null).setMedioDePago("contado").setPropietario(p).setVendedor(v), resultadoCrearInmueble_Vacío, false, true, null, null }, //la venta no tiene inmueble
				/* 4 */new Object[] { new Venta().setCliente(c).setImporte(10.0).setInmueble(new Inmueble().setEstadoInmueble(new EstadoInmueble(EstadoInmuebleStr.NO_VENDIDO))).setMedioDePago("contado").setPropietario(p).setVendedor(null), resultadoCrearVendedor_Vacio, false, true, null, null }, //la venta no tiene vendedor
				/* 5 */new Object[] { new Venta().setCliente(c).setImporte(null).setInmueble(new Inmueble().setEstadoInmueble(new EstadoInmueble(EstadoInmuebleStr.NO_VENDIDO))).setMedioDePago("contado").setPropietario(p).setVendedor(v), resultadoCrearImporte_Vacio, false, true, null, null }, //la venta no tiene importe
				/* 6 */new Object[] { new Venta().setCliente(c).setImporte(10.0).setInmueble(new Inmueble().setEstadoInmueble(new EstadoInmueble(EstadoInmuebleStr.NO_VENDIDO))).setMedioDePago(null).setPropietario(p).setVendedor(v), resultadoCrearMedio_De_Pago_Vacio, false, true, null, null }, //la venta no tiene medio de pago
				/* 7 */new Object[] { new Venta().setCliente(c).setImporte(10.0).setInmueble(new Inmueble().setEstadoInmueble(new EstadoInmueble(EstadoInmuebleStr.NO_VENDIDO))).setMedioDePago("#$%&").setPropietario(p).setVendedor(v), resultadoCrearMedio_De_Pago_Incorrecto, false, false, null, null }, //formato de medio de pago incorrecto
				/* 8 */new Object[] { new Venta().setCliente(cp).setImporte(10.0).setInmueble(new Inmueble().setEstadoInmueble(new EstadoInmueble(EstadoInmuebleStr.NO_VENDIDO))).setMedioDePago("contado").setPropietario(p).setVendedor(v), resultadoCrearCliente_Igual_A_Propietario, false, true, null, null }, //el cliente ya es el propietario
				/* 9 */new Object[] { new Venta().setCliente(c).setImporte(10.0).setInmueble(isi).setMedioDePago("contado").setPropietario(p).setVendedor(v), resultadoCrearInmueble_Ya_Vendido, false, true, null, null }, //el inmueble ya se encuentra vendido
				/* 10 */new Object[] { new Venta().setCliente(c).setImporte(10.0).setInmueble(inoOtro).setMedioDePago("contado").setPropietario(p).setVendedor(v), resultadoCrearInmueble_Reservado_Por_Otro_Cliente, false, true, null, null }, //el inmueble ya se encuentra reservado por otro cliente
				/* 11 */new Object[] { new Venta().setCliente(c).setImporte(10.0).setInmueble(inoMismo).setMedioDePago("contado").setPropietario(p).setVendedor(v), resultadoCorrecto, true, true, null, null }, //resultado correcto, el inmueble está reservado pero por el mismo cliente que desea comprarlo
				/* 12 */new Object[] { new Venta().setCliente(c).setImporte(10.0).setInmueble(new Inmueble().setEstadoInmueble(new EstadoInmueble(EstadoInmuebleStr.NO_VENDIDO))).setMedioDePago("contado").setPropietario(p).setVendedor(v), null, false, true, new SaveUpdateException(new Throwable()), null }, //excpecion al persistir
				/* 13 */new Object[] { new Venta().setCliente(c).setImporte(10.0).setInmueble(new Inmueble().setEstadoInmueble(new EstadoInmueble(EstadoInmuebleStr.NO_VENDIDO))).setMedioDePago("contado").setPropietario(p).setVendedor(v), null, false, true, null, new GenerarPDFException(new Throwable()) }, //excpecion al generar PDF

		};
	}

	private static final ResultadoCrearVenta resultadoCorrecto = new ResultadoCrearVenta();
	private static final ResultadoCrearVenta resultadoCrearCliente_Vacío = new ResultadoCrearVenta(ErrorCrearVenta.Cliente_Vacío);
	private static final ResultadoCrearVenta resultadoCrearPropietario_Vacío = new ResultadoCrearVenta(ErrorCrearVenta.Propietario_Vacío);
	private static final ResultadoCrearVenta resultadoCrearInmueble_Vacío = new ResultadoCrearVenta(ErrorCrearVenta.Inmueble_Vacío);
	private static final ResultadoCrearVenta resultadoCrearVendedor_Vacio = new ResultadoCrearVenta(ErrorCrearVenta.Vendedor_Vacío);
	private static final ResultadoCrearVenta resultadoCrearImporte_Vacio = new ResultadoCrearVenta(ErrorCrearVenta.Importe_vacío);
	private static final ResultadoCrearVenta resultadoCrearMedio_De_Pago_Vacio = new ResultadoCrearVenta(ErrorCrearVenta.Medio_De_Pago_Vacío);
	private static final ResultadoCrearVenta resultadoCrearMedio_De_Pago_Incorrecto = new ResultadoCrearVenta(ErrorCrearVenta.Formato_Medio_De_Pago_Incorrecto);
	private static final ResultadoCrearVenta resultadoCrearInmueble_Reservado_Por_Otro_Cliente = new ResultadoCrearVenta(ErrorCrearVenta.Inmueble_Reservado_Por_Otro_Cliente);
	private static final ResultadoCrearVenta resultadoCrearCliente_Igual_A_Propietario = new ResultadoCrearVenta(ErrorCrearVenta.Cliente_Igual_A_Propietario);
	private static final ResultadoCrearVenta resultadoCrearInmueble_Ya_Vendido = new ResultadoCrearVenta(ErrorCrearVenta.Inmueble_Ya_Vendido);

}