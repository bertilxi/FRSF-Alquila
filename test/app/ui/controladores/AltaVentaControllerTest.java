package app.ui.controladores;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;
import org.mockito.Mockito;

import app.datos.clases.TipoDocumentoStr;
import app.datos.entidades.Cliente;
import app.datos.entidades.Direccion;
import app.datos.entidades.Inmueble;
import app.datos.entidades.Propietario;
import app.datos.entidades.TipoDocumento;
import app.datos.entidades.Vendedor;
import app.excepciones.GenerarPDFException;
import app.excepciones.ImprimirPDFException;
import app.excepciones.SaveUpdateException;
import app.logica.CoordinadorJavaFX;
import app.logica.resultados.ResultadoCrearVenta;
import app.logica.resultados.ResultadoCrearVenta.ErrorCrearVenta;
import app.ui.componentes.ventanas.PresentadorVentanas;
import app.ui.componentes.ventanas.VentanaConfirmacion;
import app.ui.componentes.ventanas.VentanaError;
import app.ui.componentes.ventanas.VentanaErrorExcepcion;
import app.ui.componentes.ventanas.VentanaErrorExcepcionInesperada;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

/**
 * Test para el controlador de la vista Alta Venta
 */
@RunWith(JUnitParamsRunner.class)
public class AltaVentaControllerTest {

	@Test
	@Parameters
	public void testCrearVenta(Inmueble inmuebleAVender,
			Cliente clienteSeleccionado,
			Vendedor vendedorLogueado,
			String importe,
			String medioDePago,
			ResultadoCrearVenta resultadoCrearVentaEsperado,
			Integer llamaAPresentadorVentanasPresentarConfirmacion,
			boolean usuarioAceptaConfirmacion,
			Integer llamaACoordinadorImprimirPDF,
			boolean contraseñaCorrecta,
			Integer llamaAPresentadorVentanasPresentarError,
			Integer llamaAPresentadorVentanasPresentarExcepcionLogica,
			Integer llamaAPresentadorVentanasPresentarExcepcionImprimir,
			Integer llamaACrearVenta,
			Exception excepcionCapaLogica,
			Exception excepcionAlImprimir)
			throws Throwable {
		//Se crean los mocks necesarios
		CoordinadorJavaFX coordinadorMock = mock(CoordinadorJavaFX.class);
		PresentadorVentanas presentadorVentanasMock = mock(PresentadorVentanas.class);
		VentanaError ventanaErrorMock = mock(VentanaError.class);
		VentanaErrorExcepcion ventanaErrorExcepcionMock = mock(VentanaErrorExcepcion.class);
		VentanaErrorExcepcionInesperada ventanaErrorExcepcionInesperadaMock = mock(VentanaErrorExcepcionInesperada.class);
		VentanaConfirmacion ventanaConfirmacionMock = mock(VentanaConfirmacion.class);

		//Se setea lo que deben devolver los mocks cuando son invocados por la clase a probar
		when(presentadorVentanasMock.presentarError(any(), any(), any())).thenReturn(ventanaErrorMock);
		when(presentadorVentanasMock.presentarExcepcion(any(), any())).thenReturn(ventanaErrorExcepcionMock);
		when(presentadorVentanasMock.presentarExcepcionInesperada(any(), any())).thenReturn(ventanaErrorExcepcionInesperadaMock);
		when(ventanaConfirmacionMock.acepta()).thenReturn(usuarioAceptaConfirmacion);
		when(presentadorVentanasMock.presentarConfirmacion(any(), any(), any())).thenReturn(ventanaConfirmacionMock);

		if(excepcionCapaLogica != null){
			when(coordinadorMock.crearVenta(any())).thenThrow(excepcionCapaLogica);
		} else {
			when(coordinadorMock.crearVenta(any())).thenReturn(resultadoCrearVentaEsperado);
		}

		if(excepcionAlImprimir != null){
			Mockito.doThrow(excepcionAlImprimir).when(coordinadorMock).imprimirPDF(any());
		} else {
			doNothing().when(coordinadorMock).imprimirPDF(any());
		}

		ArrayList<Cliente> clientes = new ArrayList<>();
		clientes.add(clienteSeleccionado);
		when(coordinadorMock.obtenerClientes()).thenReturn(clientes);

		//Controlador a probar, se sobreescriben algunos métodos para setear los mocks y setear los datos que ingresaría el usuario en la vista
		AltaVentaController altaVentaController = new AltaVentaController() {
			@Override
			public void inicializar(URL location, ResourceBundle resources) {
				this.coordinador = coordinadorMock;
				this.presentador = presentadorVentanasMock;
				super.inicializar(location, resources);
			}

			@Override
			public void setTitulo(String titulo) {

			}

			@Override
			public void setInmueble(Inmueble inmueble) {
				this.inmueble = inmueble;
			}

			@Override
			protected boolean showConfirmarContraseñaDialog() {
				return contraseñaCorrecta;
			}

			@Override
			public void acceptAction() {
				this.textFieldImporte.setText(importe);
				this.textFieldMedioDePago.setText(medioDePago);
				this.comboBoxCliente.getSelectionModel().select(clienteSeleccionado);
				super.acceptAction();
			};

		};
		altaVentaController.setInmueble(inmuebleAVender);
		altaVentaController.setVendedorLogueado(vendedorLogueado);

		//Los controladores de las vistas deben correrse en un thread de JavaFX
		ControladorTest corredorTestEnJavaFXThread = new ControladorTest(AltaVentaController.URLVista, altaVentaController);
		Statement test = new Statement() {
			@Override
			public void evaluate() throws Throwable {
				//Método a probar
				altaVentaController.acceptAction();
				//Se hacen las verificaciones pertinentes para comprobar que el controlador se comporte adecuadamente
				Mockito.verify(coordinadorMock).obtenerClientes();
				Mockito.verify(coordinadorMock, times(llamaACrearVenta)).crearVenta(any());
				Mockito.verify(coordinadorMock,times(llamaACoordinadorImprimirPDF)).imprimirPDF(any());
				Mockito.verify(presentadorVentanasMock, times(llamaAPresentadorVentanasPresentarError)).presentarError(eq("Revise sus campos"), any(), any());
				Mockito.verify(presentadorVentanasMock, times(llamaAPresentadorVentanasPresentarExcepcionLogica)).presentarExcepcion(eq(excepcionCapaLogica), any());
				Mockito.verify(presentadorVentanasMock, times(llamaAPresentadorVentanasPresentarExcepcionImprimir)).presentarExcepcion(eq(excepcionAlImprimir), any());
				Mockito.verify(presentadorVentanasMock,times(llamaAPresentadorVentanasPresentarConfirmacion)).presentarConfirmacion(any(), any(), any());

			}
		};

		//Se corre el test en el hilo de JavaFX
		corredorTestEnJavaFXThread.apply(test, null).evaluate();
	}


	protected Object[] parametersForTestCrearVenta() {
		TipoDocumento tipoDoc = new TipoDocumento(TipoDocumentoStr.DNI);
		Cliente cliente = new Cliente();
		cliente.setTipoDocumento(tipoDoc).setNumeroDocumento("11111111");
		Cliente clienteProp = new Cliente();
		clienteProp.setTipoDocumento(tipoDoc).setNumeroDocumento("44444444");
		Vendedor vendedor = new Vendedor();
		vendedor.setTipoDocumento(tipoDoc).setNumeroDocumento("99999999");
		Propietario propietario = new Propietario();
		propietario.setTipoDocumento(tipoDoc).setNumeroDocumento("44444444");
		Inmueble inmueble = new Inmueble().setDireccion(new Direccion()).setPropietario(propietario);
		Inmueble inmuebleP = new Inmueble().setDireccion(new Direccion());

		return new Object[] {
				/*0*/new Object[] { inmueble, cliente, vendedor, "1000000","contado", resultadoCorrecto, 1, true, 1, true, 0, 0, 0, 1, null, null }, //prueba correcta, usuario acepta imprimir pdf
				/*1*/new Object[] { inmueble, cliente, vendedor, "1000000","contado", resultadoCorrecto, 1, false, 0, true, 0, 0, 0, 1, null, null }, //prueba correcta, usuario no acepta imprimir pdf
				/*2*/new Object[] { inmueble, cliente, vendedor, "1000000","contado", null, 0, false, 0, false, 0, 0, 0, 0, null, null }, //Datos correctos pero contraseña incorrecta
				/*3*/new Object[] { inmueble, null, vendedor, "1000000","contado", null, 0, false, 0, false, 1, 0, 0, 0, null, null }, //no se elije cliente
				/*4*/new Object[] { inmueble, cliente, vendedor, "abcd","contado", null, 0, false, 0, false, 1, 0, 0, 0, null, null }, //importe incorrecto
				/*5*/new Object[] { inmueble, cliente, vendedor, "1000000","", null, 0, false, 0, false, 1, 0, 0, 0, null, null }, //no se ingresa medio de pago
				/*6*/new Object[] { inmueble, cliente, vendedor, "1000000","contado", resultadoCrearCliente_Vacío, 0, false, 0, true, 1, 0, 0, 1, null, null }, //capa lógica retorna cliente vacío
				/*7*/new Object[] { inmueble, cliente, null, "1000000","contado", resultadoCrearVendedor_Vacio, 0, false, 0, true, 1, 0, 0, 1, null, null }, //capa lógica retorna vendedor vacío
				/*8*/new Object[] { inmueble, cliente, vendedor, "1000000","contado", resultadoCrearImporte_Vacio, 0, false, 0, true, 1, 0, 0, 1, null, null }, //capa lógica retorna importe vacío
				/*9*/new Object[] { inmueble, cliente, vendedor, "1000000","contado", resultadoCrearMedio_De_Pago_Vacio, 0, false, 0, true, 1, 0, 0, 1, null, null }, //capa lógica retorna medio de pago vacío
				/*10*/new Object[] { inmueble, cliente, vendedor, "1000000","contado#%&/", resultadoCrearMedio_De_Pago_Incorrecto, 0, false, 0, true, 1, 0, 0, 1, null, null }, //formato de medio de pago incorrecto
				/*11*/new Object[] { inmuebleP, cliente, vendedor, "1000000","contado", null, 0, false, 0, true, 1, 0, 0, 0, null, null }, //inmueble sin propietario
				/*12*/new Object[] { inmueble, cliente, vendedor, "1000000","contado", resultadoCrearPropietario_Vacío, 0, false, 0, true, 1, 0, 0, 1, null, null }, //capa lógica retorna inmueble sin propietario
				/*13*/new Object[] { null, cliente, vendedor, "1000000","contado", null, 0, false, 0, true, 1, 0, 0, 0, null, null }, //inmueble vacío
				/*14*/new Object[] { inmueble, cliente, vendedor, "1000000","contado", resultadoCrearInmueble_Vacío, 0, false, 0, true, 1, 0, 0, 1, null, null }, //capa lógica retorna inmueble vacío
				/*15*/new Object[] { inmueble, cliente, vendedor, "1000000","contado", resultadoCrearInmueble_Ya_Vendido, 0, false, 0, true, 1, 0, 0, 1, null, null }, //el inmueble ya está vendido
				/*16*/new Object[] { inmueble, cliente, vendedor, "1000000","contado", resultadoCrearInmueble_Reservado_Por_Otro_Cliente, 0, false, 0, true, 1, 0, 0, 1, null, null }, //el inmueble ya está reservado
				/*17*/new Object[] { inmueble, clienteProp, vendedor, "1000000","contado", resultadoCrearCliente_Igual_A_Propietario, 0, false, 0, true, 1, 0, 0, 1, null, null }, //el cliente ya es el propietario del inmueble
				/*18*/new Object[] { inmueble, cliente, vendedor, "1000000","contado", null, 0, false, 0, true, 0, 1, 0, 1, new GenerarPDFException(new Throwable()), null }, //excepción al generar pdf
				/*19*/new Object[] { inmueble, cliente, vendedor, "1000000","contado", null, 0, false, 0, true, 0, 1, 0, 1, new SaveUpdateException(new Throwable()), null }, //excepción al persistir
				/*20*/new Object[] { inmueble, cliente, vendedor, "1000000","contado", resultadoCorrecto, 1, true, 1, true, 0, 0, 1, 1, null, new ImprimirPDFException(new Throwable()) }, //excepción al imprimir pdf


		};
	}

	//Resultados crearVenta
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