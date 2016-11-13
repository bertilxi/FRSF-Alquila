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

import app.datos.clases.TipoInmuebleStr;
import app.datos.entidades.Barrio;
import app.datos.entidades.Cliente;
import app.datos.entidades.InmuebleBuscado;
import app.datos.entidades.Localidad;
import app.datos.entidades.Pais;
import app.datos.entidades.Provincia;
import app.datos.entidades.TipoInmueble;
import app.excepciones.SaveUpdateException;
import app.logica.CoordinadorJavaFX;
import app.ui.ScenographyChanger;
import app.ui.componentes.ventanas.PresentadorVentanas;
import app.ui.componentes.ventanas.VentanaError;
import app.ui.componentes.ventanas.VentanaErrorExcepcion;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class InmuebleBuscadoControllerTest {

	@Test
	@Parameters
	public void testCrearCliente(String superficie,
			String antiguedad,
			String dormitorios,
			String baños,
			String precio,
			ArrayList<Localidad> localidades,
			ArrayList<Barrio> barrios,
			Boolean local,
			Boolean casa,
			Boolean departamento,
			Boolean terreno,
			Boolean galpon,
			Boolean quinta,
			Boolean propiedadHorizontal,
			Boolean garage,
			Boolean patio,
			Boolean piscina,
			Boolean aguaCorriente,
			Boolean cloaca,
			Boolean gasNatural,
			Boolean aguaCaliente,
			Boolean telefono,
			Boolean lavadero,
			Boolean pavimento,
			Cliente clienteNuevo,
			Cliente clienteEnModificacion,
			Integer camposCorrectos,
			Integer llamaAPresentadorVentanasPresentarError,
			Integer llamaAPresentadorVentanasPresentarExcepcion,
			Exception excepcion) throws Throwable {

		CoordinadorJavaFX coordinadorMock = Mockito.mock(CoordinadorJavaFX.class);
		PresentadorVentanas presentadorVentanasMock = mock(PresentadorVentanas.class);
		VentanaError ventanaErrorMock = mock(VentanaError.class);
		VentanaErrorExcepcion ventanaErrorExcepcionMock = mock(VentanaErrorExcepcion.class);
		ScenographyChanger scenographyChangerMock = mock(ScenographyChanger.class);
		AltaClienteController altaClienteControllerMock = mock(AltaClienteController.class);
		ModificarClienteController modificarClienteControllerMock = mock(ModificarClienteController.class);

		when(coordinadorMock.obtenerPaises()).thenReturn(new ArrayList<Pais>());
		when(presentadorVentanasMock.presentarError(any(), any(), any())).thenReturn(ventanaErrorMock);
		when(presentadorVentanasMock.presentarExcepcion(any(), any())).thenReturn(ventanaErrorExcepcionMock);
		if (clienteNuevo != null) {
			when(scenographyChangerMock.cambiarScenography(any(), any())).thenReturn(altaClienteControllerMock);
		}
		if (clienteEnModificacion != null) {
			when(scenographyChangerMock.cambiarScenography(any(), any())).thenReturn(modificarClienteControllerMock);
		}
		doNothing().when(altaClienteControllerMock).setCliente(any());
		doNothing().when(modificarClienteControllerMock).setClienteEnModificacion(any());

		if(excepcion == null) {
			ArrayList<TipoInmueble> tipos = new ArrayList<>();
			tipos.add(new TipoInmueble(TipoInmuebleStr.CASA));
			tipos.add(new TipoInmueble(TipoInmuebleStr.DEPARTAMENTO));
			tipos.add(new TipoInmueble(TipoInmuebleStr.GALPON));
			tipos.add(new TipoInmueble(TipoInmuebleStr.LOCAL));
			tipos.add(new TipoInmueble(TipoInmuebleStr.QUINTA));
			tipos.add(new TipoInmueble(TipoInmuebleStr.TERRENO));
			when(coordinadorMock.obtenerTiposInmueble()).thenReturn(tipos);
		} else {
			when(coordinadorMock.obtenerTiposInmueble()).thenThrow(excepcion);
		}

		InmuebleBuscadoController inmuebleBuscadoController = new InmuebleBuscadoController() {
			@Override
			public void inicializar(URL location, ResourceBundle resources) {
				this.coordinador = coordinadorMock;
				this.presentador = presentadorVentanasMock;
				setScenographyChanger(scenographyChangerMock);
				super.inicializar(location, resources);
			}

			@Override
			public void acceptAction() {
				this.textFieldAntiguedad.setText(antiguedad);
				this.textFieldBaños.setText(baños);
				this.textFieldDormitorios.setText(dormitorios);
				this.textFieldPrecio.setText(precio);
				this.textFieldSuperficie.setText(superficie);
				this.listaBarriosSeleccionados.clear();
				this.listaBarriosSeleccionados.addAll(barrios);
				this.listaLocalidadesSeleccionadas.clear();
				this.listaLocalidadesSeleccionadas.addAll(localidades);
				this.checkBoxAguaCaliente.setSelected(aguaCaliente);
				this.checkBoxAguaCorriente.setSelected(aguaCorriente);
				this.checkBoxCasa.setSelected(casa);
				this.checkBoxCloaca.setSelected(cloaca);
				this.checkBoxDepartamento.setSelected(departamento);
				this.checkBoxGalpon.setSelected(galpon);
				this.checkBoxGarage.setSelected(garage);
				this.checkBoxGasNatural.setSelected(gasNatural);
				this.checkBoxLavadero.setSelected(lavadero);
				this.checkBoxLocal.setSelected(local);
				this.checkBoxPatio.setSelected(patio);
				this.checkBoxPavimento.setSelected(pavimento);
				this.checkBoxPiscina.setSelected(piscina);
				this.checkBoxPropiedadHorizontal.setSelected(propiedadHorizontal);
				this.checkBoxQuinta.setSelected(quinta);
				this.checkBoxTelefono.setSelected(telefono);
				this.checkBoxTerreno.setSelected(terreno);
				super.acceptAction();
			}

			@Override
			protected void setTitulo(String titulo) {

			}

			@Override
			public void setCliente(Cliente cliente) {
				this.cliente = cliente;
				InmuebleBuscado inmueble = cliente.getInmuebleBuscado();
				if(inmueble != null){
					this.alta = false;
				} else {
					this.alta = true;
				}
			}
		};

		ControladorTest corredorTestEnJavaFXThread =
				new ControladorTest(InmuebleBuscadoController.URLVista, inmuebleBuscadoController);

		Statement test = new Statement() {
			@Override
			public void evaluate() throws Throwable {
				if(clienteNuevo != null) {
					inmuebleBuscadoController.setCliente(clienteNuevo);
				}
				if(clienteEnModificacion != null) {
					inmuebleBuscadoController.setCliente(clienteEnModificacion);
				}

				inmuebleBuscadoController.acceptAction();

				if (clienteNuevo != null && excepcion == null) {
					Mockito.verify(altaClienteControllerMock,times(camposCorrectos)).setCliente(clienteNuevo);
				}
				if (clienteEnModificacion != null  && excepcion == null) {
					Mockito.verify(modificarClienteControllerMock,times(camposCorrectos)).setClienteEnModificacion(clienteEnModificacion);
				}
				Mockito.verify(coordinadorMock,times(camposCorrectos)).obtenerTiposInmueble();
				Mockito.verify(presentadorVentanasMock, times(llamaAPresentadorVentanasPresentarError)).presentarError(eq("Revise sus campos"), any(), any());
				Mockito.verify(presentadorVentanasMock, times(llamaAPresentadorVentanasPresentarExcepcion)).presentarExcepcion(eq(excepcion), any());
			}
		};

		corredorTestEnJavaFXThread.apply(test, null).evaluate();


	}

	protected Object[] parametersForTestCrearCliente() {
		ArrayList<Localidad> localidades = new ArrayList<>();
		localidades.add(new Localidad("Federal", new Provincia("Entre ríos", new Pais("Argentina"))));
		ArrayList<Barrio> barrios = new ArrayList<>();
		Cliente clienteNuevo = new Cliente();
		Cliente clienteEnModificacion = new Cliente();
		clienteEnModificacion.setInmuebleBuscado(new InmuebleBuscado());
		clienteEnModificacion.getInmuebleBuscado().setCliente(clienteEnModificacion);
		return new Object[] {
				//prueba correcta de inmueble nuevo
				new Object[] {"30.0", "10", "2", "1", "3000000.0", localidades, barrios, false, true, true, false, false, false, false, false, true, false, true, true, true, true, false, true, true, clienteNuevo, null, 1, 0, 0, null},
				//prueba campo de texto incorrecto de inmueble nuevo
				new Object[] {"30.0", "abc", "2", "1", "3000000.0", localidades, barrios, false, true, true, false, false, false, false, false, true, false, true, true, true, true, false, true, true, clienteNuevo, null, 0, 1, 0, null},
				//prueba campo texto vacio de inmueble nuevo
				new Object[] {"30.0", "10", "", "1", "3000000.0", localidades, barrios, false, true, true, false, false, false, false, false, true, false, true, true, true, true, false, true, true, clienteNuevo, null, 0, 1, 0, null},
				//prueba PersistenciaException de inmueble nuevo
				new Object[] {"30.0", "10", "2", "1", "3000000.0", localidades, barrios, false, true, true, false, false, false, false, false, true, false, true, true, true, true, false, true, true, clienteNuevo, null, 1, 0, 1, new SaveUpdateException(new Throwable())},
				//prueba correcta de inmueble en modificación
				new Object[] {"30.0", "10", "2", "1", "3000000.0", localidades, barrios, false, true, true, false, false, false, false, false, true, false, true, true, true, true, false, true, true, null, clienteEnModificacion, 1, 0, 0, null},
				//prueba campo de texto incorrecto de inmueble en modificación
				new Object[] {"30.0", "abc", "2", "1", "3000000.0", localidades, barrios, false, true, true, false, false, false, false, false, true, false, true, true, true, true, false, true, true, null, clienteEnModificacion, 0, 1, 0, null},
				//prueba campo texto vacio de inmueble en modificación
				new Object[] {"30.0", "10", "", "1", "3000000.0", localidades, barrios, false, true, true, false, false, false, false, false, true, false, true, true, true, true, false, true, true, null, clienteEnModificacion, 0, 1, 0, null},
				//prueba PersistenciaException de inmueble en modificación
				new Object[] {"30.0", "10", "2", "1", "3000000.0", localidades, barrios, false, true, true, false, false, false, false, false, true, false, true, true, true, true, false, true, true, null, clienteEnModificacion, 1, 0, 1, new SaveUpdateException(new Throwable())}

		};
	}

}