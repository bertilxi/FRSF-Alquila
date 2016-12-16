package app.ui.controladores;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;
import org.mockito.Mockito;

import app.datos.entidades.Cliente;
import app.datos.entidades.Inmueble;
import app.datos.entidades.Propietario;
import app.datos.entidades.Vendedor;
import app.datos.entidades.Venta;
import app.ui.componentes.ventanas.PresentadorVentanas;
import app.ui.componentes.ventanas.VentanaError;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

/**
 * Este test prueba los métodos de la clase AdministrarVentaController
 */
@RunWith(JUnitParamsRunner.class)
public class AdministrarVentaControllerTest {

	/**
	 * Test para probar las transiciones correctas desde y hacia la pantalla y la muestra de las
	 * columnas correctas según el rol para el cual se desean conocer las ventas
	 *
	 * @param cliente
	 * 			cliente para el cual se desean listar las ventas
	 * @param propietario
	 * 			propietario para el cual se desean listar las ventas
	 * @param vendedor
	 * 			vendedor para el cual se desean listar las ventas
	 * @param presionaVerInmueble
	 * 			si el usuario presiona ver inmueble
	 * @param presionaVerDocumento
	 * 			si el usuario presiona ver documento
	 * @param llamaAPresentadorVentanasPresentarError
	 * 			1 si llama al método presentar error del presentador de ventanas
	 * @param excepcion
	 * 			excepción que se simula lanzar desde capa lógica
	 * @throws Throwable
	 */
	@Test
	@Parameters
	public void testRolesDeIngresoYEgreso(Cliente cliente,
			Propietario propietario,
			Vendedor vendedor,
			boolean presionaVerInmueble,
			boolean presionaVerDocumento,
			Integer llamaAPresentadorVentanasPresentarError,
			Exception excepcion) throws Throwable {

		PresentadorVentanas presentadorVentanasMock = mock(PresentadorVentanas.class);
		VentanaError ventanaErrorMock = mock(VentanaError.class);
		VerBasicosInmuebleController controladorMock = mock(VerBasicosInmuebleController.class);

		when(presentadorVentanasMock.presentarError(any(), any(), any())).thenReturn(ventanaErrorMock);
		if(cliente != null){
			Mockito.doNothing().when(controladorMock).setCliente(cliente);
		}
		if(propietario != null){
			Mockito.doNothing().when(controladorMock).setPropietario(propietario);
		}
		if(vendedor != null){
			Mockito.doNothing().when(controladorMock).setVendedor(vendedor);
		}
		Mockito.doNothing().when(controladorMock).setVendedorLogueado(any());
		Mockito.doNothing().when(controladorMock).setInmueble(any());

		AdministrarVentaController administrarVentaController = new AdministrarVentaController() {
			@Override
			public void inicializar(URL location, ResourceBundle resources) {
				this.presentador = presentadorVentanasMock;
				super.inicializar(location, resources);
			}

			@Override
			public void setTitulo(String titulo) {

			}

			@Override
			protected void handleVerDocumento() {
				tablaVentas.getSelectionModel().selectFirst();
				if(tablaVentas.getSelectionModel().getSelectedItem() != null){
					try{
						//se intenta abrir el pdf
						if(excepcion != null){
							throw excepcion;
						}
					} catch(Exception ex){
						presentador.presentarError("Error", "No se pudo abrir el archivo pdf", stage);
					}
				}
			}

			@Override
			protected void handleVerInmueble() {
				tablaVentas.getSelectionModel().selectFirst();
				super.handleVerInmueble();
			}

			@Override
			protected OlimpoController cambiarmeAScene(String url) {
				controladorMock.setVendedorLogueado(vendedorLogueado);
				return controladorMock;
			}

		};

		//Los controladores de las vistas deben correrse en un thread de JavaFX
		ControladorTest corredorTestEnJavaFXThread = new ControladorTest(AdministrarVentaController.URLVista, administrarVentaController);
		Statement correr_test = new Statement() {
			@Override
			public void evaluate() throws Throwable {
				if(cliente != null){
					administrarVentaController.setCliente(cliente);
				}
				if(propietario != null){
					administrarVentaController.setPropietario(propietario);
				}
				if(vendedor != null){
					administrarVentaController.setVendedor(vendedor);
				}
			}
		};
		Statement evaluar_test = new Statement() {
			@Override
			public void evaluate() throws Throwable {
				if(cliente != null){
					assertEquals(false, administrarVentaController.columnaCliente.isVisible());
					assertEquals(true, administrarVentaController.columnaPropietario.isVisible());
					assertEquals(true, administrarVentaController.columnaVendedor.isVisible());
				}
				if(propietario != null){
					assertEquals(true, administrarVentaController.columnaCliente.isVisible());
					assertEquals(false, administrarVentaController.columnaPropietario.isVisible());
					assertEquals(true, administrarVentaController.columnaVendedor.isVisible());
				}
				if(vendedor != null){
					assertEquals(true, administrarVentaController.columnaCliente.isVisible());
					assertEquals(true, administrarVentaController.columnaPropietario.isVisible());
					assertEquals(false, administrarVentaController.columnaVendedor.isVisible());
				}

				if(presionaVerDocumento){
					administrarVentaController.handleVerDocumento();
				}
				if(presionaVerInmueble){
					administrarVentaController.handleVerInmueble();
					Mockito.verify(controladorMock).setInmueble(any());
					Mockito.verify(controladorMock).setVendedorLogueado(any());
					if(cliente != null){
						Mockito.verify(controladorMock).setCliente(cliente);
					}
					if(propietario != null){
						Mockito.verify(controladorMock).setPropietario(propietario);
					}
					if(vendedor != null){
						Mockito.verify(controladorMock).setVendedor(vendedor);
					}

				}

				Mockito.verify(presentadorVentanasMock, times(llamaAPresentadorVentanasPresentarError)).presentarError(eq("Error"), eq("No se pudo abrir el archivo pdf"), any());
			}
		};

		//Se corre el test en el hilo de JavaFX
		corredorTestEnJavaFXThread.apply(correr_test, null).evaluate();
		corredorTestEnJavaFXThread.apply(evaluar_test, null).evaluate();
	}

	protected Object[] parametersForTestRolesDeIngresoYEgreso() {
		Cliente cliente = new Cliente();
		cliente.setNombre("Juan").setApellido("Pérez");
		Vendedor vendedor = new Vendedor();
		vendedor.setNombre("Juan").setApellido("Pérez");
		Propietario propietario = new Propietario();
		propietario.setNombre("Juan").setApellido("Pérez");
		Venta venta = new Venta();
		venta.setCliente(cliente).setPropietario(propietario).setVendedor(vendedor);
		venta.setFecha(new Date()).setInmueble(new Inmueble());
		cliente.getVentas().add(venta);
		propietario.getVentas().add(venta);
		vendedor.getVentas().add(venta);

		return new Object[] {
				//cliente,propietario,vendedor,presionaVerInmueble,presionaVerDocumento,llamaAPresentadorVentanasPresentarError,excepcion
				/* 0 */new Object[] { cliente, null, null, true, false, 0, null }, // se accede para un cliente, se presiona ver inmueble
				/* 1 */new Object[] { cliente, null, null, false, true, 0, null }, // se accede para un cliente, se presiona ver documento
				/* 2 */new Object[] { cliente, null, null, false, true, 1, new IOException() }, // se accede para un cliente, se presiona ver documento y ocurre excepcion al abrir pdf
				/* 3 */new Object[] { null, propietario, null, true, false, 0, null }, // se accede para un propietario, se presiona ver inmueble
				/* 4 */new Object[] { null, propietario, null, false, true, 0, null }, // se accede para un propietario, se presiona ver documento
				/* 5 */new Object[] { null, propietario, null, false, true, 1, new IOException() }, // se accede para un propietario, se presiona ver documento y ocurre excepcion al abrir pdf
				/* 6 */new Object[] { null, null, vendedor, true, false, 0, null }, // se accede para un vendedor, se presiona ver inmueble
				/* 7 */new Object[] { null, null, vendedor, false, true, 0, null }, // se accede para un vendedor, se presiona ver documento
				/* 8 */new Object[] { null, null, vendedor, false, true, 1, new IOException() }, // se accede para un vendedor, se presiona ver documento y ocurre excepcion al abrir pdf

		};
	}
}
