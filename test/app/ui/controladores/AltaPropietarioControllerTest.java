/**
 * Copyright (C) 2016 Fernando Berti - Daniel Campodonico - Emiliano Gioria - Lucas Moretti - Esteban Rebechi - Andres Leonel Rico
 * This file is part of Olimpo.
 *
 * Olimpo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Olimpo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Olimpo. If not, see <http://www.gnu.org/licenses/>.
 */
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
import app.datos.entidades.Barrio;
import app.datos.entidades.Calle;
import app.datos.entidades.Direccion;
import app.datos.entidades.Localidad;
import app.datos.entidades.Pais;
import app.datos.entidades.Propietario;
import app.datos.entidades.Provincia;
import app.datos.entidades.TipoDocumento;
import app.excepciones.EntidadExistenteConEstadoBajaException;
import app.excepciones.SaveUpdateException;
import app.logica.CoordinadorJavaFX;
import app.logica.resultados.ResultadoCrearPropietario;
import app.logica.resultados.ResultadoCrearPropietario.ErrorCrearPropietario;
import app.ui.ScenographyChanger;
import app.ui.componentes.ventanas.PresentadorVentanas;
import app.ui.componentes.ventanas.VentanaConfirmacion;
import app.ui.componentes.ventanas.VentanaError;
import app.ui.componentes.ventanas.VentanaErrorExcepcion;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class AltaPropietarioControllerTest {

	private static Propietario propietario;

	@Test
	@Parameters
	public void testCrearPropietario(String nombre,
			String apellido,
			TipoDocumento tipoDocumento,
			String numeroDocumento,
			String telefono,
			String email,
			Direccion direccion,
			ResultadoCrearPropietario resultadoCrearPropietarioEsperado,
			Integer llamaAPresentadorVentanasPresentarError,
			Integer llamaAPresentadorVentanasPresentarExcepcion,
			Integer llamaACrearPropietario,
			Exception excepcion,
			Boolean aceptarVentanaConfirmacion,
			Integer llamaACambiarScene) throws Throwable {

		CoordinadorJavaFX coordinadorMock = Mockito.mock(CoordinadorJavaFX.class);
		PresentadorVentanas presentadorVentanasMock = mock(PresentadorVentanas.class);
		VentanaError ventanaErrorMock = mock(VentanaError.class);
		VentanaErrorExcepcion ventanaErrorExcepcionMock = mock(VentanaErrorExcepcion.class);
		VentanaConfirmacion ventanaConfirmacionMock = mock(VentanaConfirmacion.class);
		ScenographyChanger scenographyChangerMock = mock(ScenographyChanger.class);
		ModificarPropietarioController modificarPropietarioControllerMock = mock(ModificarPropietarioController.class);

		when(presentadorVentanasMock.presentarError(any(), any(), any())).thenReturn(ventanaErrorMock);
		when(presentadorVentanasMock.presentarExcepcion(any(), any())).thenReturn(ventanaErrorExcepcionMock);
		when(presentadorVentanasMock.presentarConfirmacion(any(), any(), any())).thenReturn(ventanaConfirmacionMock);
		when(ventanaConfirmacionMock.acepta()).thenReturn(aceptarVentanaConfirmacion);
		when(scenographyChangerMock.cambiarScenography(any(), any())).thenReturn(modificarPropietarioControllerMock);
		doNothing().when(modificarPropietarioControllerMock).setPropietarioEnModificacion(any());
		doNothing().when(presentadorVentanasMock).presentarToast(any(), any());

		propietario = new Propietario()
				.setNombre(nombre)
				.setApellido(apellido)
				.setTipoDocumento(tipoDocumento)
				.setNumeroDocumento(numeroDocumento)
				.setTelefono(telefono)
				.setEmail(email)
				.setDireccion(direccion);


		when(coordinadorMock.crearPropietario(propietario)).thenReturn(resultadoCrearPropietarioEsperado);
		if(excepcion != null){
			when(coordinadorMock.crearPropietario(propietario)).thenThrow(excepcion);
		}

		ArrayList<TipoDocumento> tipos = new ArrayList<>();
		tipos.add(tipoDocumento);
		Mockito.when(coordinadorMock.obtenerTiposDeDocumento()).thenReturn(tipos);

		AltaPropietarioController altaPropietarioController = new AltaPropietarioController() {
			@Override
			public void inicializar(URL location, ResourceBundle resources) {
				this.coordinador = coordinadorMock;
				this.presentador = presentadorVentanasMock;
				setScenographyChanger(scenographyChangerMock);
				super.inicializar(location, resources);
			}

			@Override
			public void acceptAction() {
				this.textFieldNombre.setText(nombre);
				this.textFieldApellido.setText(apellido);
				this.comboBoxTipoDocumento.getSelectionModel().select(tipoDocumento);
				this.textFieldNumeroDocumento.setText(numeroDocumento);
				this.textFieldTelefono.setText(telefono);
				this.textFieldCorreoElectronico.setText(email);
				this.comboBoxPais.getSelectionModel().select(direccion.getLocalidad().getProvincia().getPais());
				this.comboBoxProvincia.getSelectionModel().select(direccion.getLocalidad().getProvincia());
				this.comboBoxLocalidad.getSelectionModel().select(direccion.getLocalidad());
				this.comboBoxBarrio.getSelectionModel().select(direccion.getBarrio());
				this.comboBoxCalle.getSelectionModel().select(direccion.getCalle());
				this.textFieldAlturaCalle.setText(direccion.getNumero());
				this.textFieldDepartamento.setText(direccion.getDepartamento());
				this.textFieldPiso.setText(direccion.getPiso());
				this.textFieldOtros.setText(direccion.getOtros());
				super.acceptAction();
			}

			@Override
			protected void setTitulo(String titulo) {

			}
		};

		ControladorTest corredorTestEnJavaFXThread =
				new ControladorTest(AltaPropietarioController.URLVista, altaPropietarioController);

		Statement test = new Statement() {
			@Override
			public void evaluate() throws Throwable {
				altaPropietarioController.acceptAction();

				Mockito.verify(coordinadorMock).obtenerTiposDeDocumento();
				Mockito.verify(coordinadorMock, Mockito.times(llamaACrearPropietario)).crearPropietario(Mockito.any());
				Mockito.verify(presentadorVentanasMock, times(llamaAPresentadorVentanasPresentarError)).presentarError(eq("Revise sus campos"), any(), any());
				Mockito.verify(presentadorVentanasMock, times(llamaAPresentadorVentanasPresentarExcepcion)).presentarExcepcion(eq(excepcion), any());
				Mockito.verify(scenographyChangerMock, times(llamaACambiarScene)).cambiarScenography(ModificarPropietarioController.URLVista, false);
				Mockito.verify(modificarPropietarioControllerMock, times(llamaACambiarScene)).setPropietarioEnModificacion(propietario);
			}
		};

		corredorTestEnJavaFXThread.apply(test, null).evaluate();


	}

	protected Object[] parametersForTestCrearPropietario() {
		TipoDocumento doc = new TipoDocumento(TipoDocumentoStr.DNI);
		Pais pais = new Pais("Argentina");
		Provincia provincia = new Provincia("Santa fé", pais);
		Localidad localidad = new Localidad("Capital", provincia);
		Direccion dir = new Direccion("1865", "1", "B", new Calle("San martín", localidad), new Barrio("Centro", localidad), localidad, "");
		return new Object[] {
				//prueba correcta
				new Object[] { "Juan", "Perez",doc,"12345678", "123-123", "juanperez@hotmail.com", dir, resultadoCorrecto, 0, 0, 1, null, true, 0  },
				//prueba nombre incorrecto
				new Object[] { "Juan", "Perez",doc,"12345678", "123-123", "juanperez@hotmail.com", dir, resultadoCrearNombreIncorrecto, 1, 0, 1, null, true, 0 },
				//prueba apellido incorrecto
				new Object[] { "Juan", "Perez",doc,"12345678", "123-123", "juanperez@hotmail.com", dir, resultadoCrearApellidoIncorrecto, 1, 0, 1, null, true, 0 },
				//prueba documento incorrecto
				new Object[] { "Juan", "Perez", doc,"12345678", "123-123", "juanperez@hotmail.com", dir, resultadoCrearDocumentoIncorrecto, 1, 0, 1, null, true, 0 },
				//prueba teléfono incorrecto
				new Object[] { "Juan", "Perez", doc,"12345678", "123-123", "juanperez@hotmail.com", dir, resultadoCrearTelefonoIncorrecto, 1, 0, 1, null, true, 0 },
				//prueba email incorrecto
				new Object[] { "Juan", "Perez", doc,"12345678", "123-123", "juanperez@hotmail.com", dir, resultadoCrearEmailIncorrecto, 1, 0, 1, null, true, 0 },
				//prueba direccion incorrecta
				new Object[] { "Juan", "Perez", doc,"12345678", "123-123", "juanperez@hotmail.com", dir, resultadoCrearDireccionIncorrecta, 1, 0, 1, null, true, 0 },
				//prueba ya existe propietario
				new Object[] { "Juan", "Perez", doc,"12345678", "123-123", "juanperez@hotmail.com", dir, resultadoCrearYaExiste, 1, 0, 1, null, true, 0 },
				//prueba ya existe propietario
				new Object[] { "Juan", "Perez",doc,"12345678", "123-123", "juanperez@hotmail.com", dir,new ResultadoCrearPropietario(ErrorCrearPropietario.Formato_Nombre_Incorrecto,ErrorCrearPropietario.Formato_Apellido_Incorrecto),1, 0, 1, null, true, 0 },
				//prueba nombre vacio
				new Object[] { "", "Perez", doc, "12345678", "123-123", "juanperez@hotmail.com", dir, null, 1, 0, 0, null, true, 0 },
				//prueba propietario Existente y acepta
				new Object[] { "Juan", "Perez",doc,"12345678", "123-123", "juanperez@hotmail.com", dir, resultadoCorrecto, 0, 0, 1, new EntidadExistenteConEstadoBajaException(), true, 1 },
				//prueba propietario Existente y cancela
				new Object[] { "Juan", "Perez",doc,"12345678", "123-123", "juanperez@hotmail.com", dir, resultadoCorrecto, 0, 0, 1, new EntidadExistenteConEstadoBajaException(), false, 0 },
				//prueba PersistenciaException
				new Object[] { "Juan", "Perez",doc,"12345678", "123-123", "juanperez@hotmail.com", dir, resultadoCorrecto, 0, 1, 1, new SaveUpdateException(new Throwable()), false, 0 }

		};
	}

	//Para crearPropietario
	private static final ResultadoCrearPropietario resultadoCorrecto = new ResultadoCrearPropietario();
	private static final ResultadoCrearPropietario resultadoCrearNombreIncorrecto =
			new ResultadoCrearPropietario(ResultadoCrearPropietario.ErrorCrearPropietario.Formato_Nombre_Incorrecto);
	private static final ResultadoCrearPropietario resultadoCrearApellidoIncorrecto =
			new ResultadoCrearPropietario(ResultadoCrearPropietario.ErrorCrearPropietario.Formato_Apellido_Incorrecto);
	private static final ResultadoCrearPropietario resultadoCrearDocumentoIncorrecto =
			new ResultadoCrearPropietario(ResultadoCrearPropietario.ErrorCrearPropietario.Formato_Documento_Incorrecto);
	private static final ResultadoCrearPropietario resultadoCrearTelefonoIncorrecto =
			new ResultadoCrearPropietario(ResultadoCrearPropietario.ErrorCrearPropietario.Formato_Telefono_Incorrecto);
	private static final ResultadoCrearPropietario resultadoCrearEmailIncorrecto =
			new ResultadoCrearPropietario(ResultadoCrearPropietario.ErrorCrearPropietario.Formato_Email_Incorrecto);
	private static final ResultadoCrearPropietario resultadoCrearDireccionIncorrecta =
			new ResultadoCrearPropietario(ResultadoCrearPropietario.ErrorCrearPropietario.Formato_Direccion_Incorrecto);
	private static final ResultadoCrearPropietario resultadoCrearYaExiste =
			new ResultadoCrearPropietario(ResultadoCrearPropietario.ErrorCrearPropietario.Ya_Existe_Propietario);

}