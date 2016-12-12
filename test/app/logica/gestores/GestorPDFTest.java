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
package app.logica.gestores;

import java.io.FileOutputStream;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import org.junit.Test;

import app.comun.ConversorFechas;
import app.comun.FormateadorString;
import app.datos.clases.ReservaVista;
import app.datos.clases.TipoDocumentoStr;
import app.datos.clases.TipoInmuebleStr;
import app.datos.entidades.Barrio;
import app.datos.entidades.Calle;
import app.datos.entidades.Cliente;
import app.datos.entidades.Direccion;
import app.datos.entidades.Inmueble;
import app.datos.entidades.Localidad;
import app.datos.entidades.PDF;
import app.datos.entidades.Pais;
import app.datos.entidades.Propietario;
import app.datos.entidades.Provincia;
import app.datos.entidades.TipoDocumento;
import app.datos.entidades.TipoInmueble;
import app.ui.controladores.ControladorTest;
import app.ui.controladores.LoginController;

public class GestorPDFTest {

	@Test
	public void test() throws Exception {
		new ControladorTest(LoginController.URLVista, new LoginController() {
			@Override
			protected void setTitulo(String titulo) {
			}

			@Override
			public void inicializar(URL location, ResourceBundle resources) {
			}
		});
		GestorPDF gestor = new GestorPDF() {
			{
				formateador = new FormateadorString();
				conversorFechas = new ConversorFechas();
			}
		};
		Cliente cliente = new Cliente().setNombre("Pablo")
				.setApellido("Van Derdonckt")
				.setTipoDocumento(new TipoDocumento().setTipo(TipoDocumentoStr.DNI))
				.setNumeroDocumento("36696969");
		Propietario propietario = new Propietario().setNombre("Esteban")
				.setApellido("Rebechi");
		Localidad localidad = new Localidad().setProvincia(new Provincia().setPais(new Pais())).setNombre("Ceres");
		Direccion direccion = new Direccion().setCalle(new Calle().setLocalidad(localidad).setNombre("Azquenaga")).setLocalidad(localidad).setBarrio(new Barrio().setLocalidad(localidad).setNombre("Vicente Zaspe"))
				.setNumero("3434")
				.setPiso("6")
				.setDepartamento("6B")
				.setOtros("otros");

		Inmueble inmueble = new Inmueble() {
			@Override
			public Integer getId() {
				return 12345;
			};
		}.setTipo(new TipoInmueble(TipoInmuebleStr.DEPARTAMENTO))
				.setDireccion(direccion)
				.setPropietario(propietario);
		Date fechahoy = new Date();
		ReservaVista reserva = new ReservaVista(cliente, inmueble, 300000.50, fechahoy, fechahoy);
		PDF pdf = gestor.generarPDF(reserva);
		FileOutputStream fos = new FileOutputStream("borrarRico.pdf");
		fos.write(pdf.getArchivo());
		fos.close();
	}
}