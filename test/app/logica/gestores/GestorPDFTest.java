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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

import org.junit.Test;

import app.comun.ConversorFechas;
import app.comun.FormateadorString;
import app.datos.clases.CatalogoVista;
import app.datos.clases.TipoDocumentoStr;
import app.datos.clases.TipoInmuebleStr;
import app.datos.entidades.Barrio;
import app.datos.entidades.Calle;
import app.datos.entidades.Cliente;
import app.datos.entidades.DatosEdificio;
import app.datos.entidades.Direccion;
import app.datos.entidades.Imagen;
import app.datos.entidades.Inmueble;
import app.datos.entidades.Localidad;
import app.datos.entidades.PDF;
import app.datos.entidades.Pais;
import app.datos.entidades.Propietario;
import app.datos.entidades.Provincia;
import app.datos.entidades.Reserva;
import app.datos.entidades.TipoDocumento;
import app.datos.entidades.TipoInmueble;
import app.datos.entidades.Venta;
import app.ui.controladores.ControladorTest;
import app.ui.controladores.LoginController;

public class GestorPDFTest {

	@Test
	/**
	 * Prueba el método generarPDF(Reserva reserva), el cual corresponde con la taskcard 25 de la iteración 2 y a la historia 7
	 * El test es en parte manual ya que genera un archivo pdf que debe comprobarse si es correcto manualmente.
	 *
	 * @throws Exception
	 */
	public void testGenerarPDFReserva() throws Exception {
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
		Reserva reserva = new Reserva().setCliente(cliente).setInmueble(inmueble).setImporte(300000.50).setFechaInicio(fechahoy).setFechaFin(fechahoy);

		PDF pdf = gestor.generarPDF(reserva);
		FileOutputStream fos = new FileOutputStream("testReserva.pdf");
		fos.write(pdf.getArchivo());
		fos.close();
	}

	@Test
	public void testGenerarPDFCatalogo() throws Exception {
		Direccion direccion = new Direccion()
				.setLocalidad(new Localidad().setNombre("Localidad 1").setProvincia(new Provincia().setNombre("Provincia 1").setPais(new Pais().setNombre("Pais 1"))))
				.setBarrio(new Barrio().setNombre("Barrio 1"))
				.setCalle(new Calle().setNombre("Calle 1"))
				.setNumero("123")
				.setPiso("Piso 1")
				.setOtros("Otros 1");
		File file = new File("src/res/img/icono-256.png");
		byte[] bytes = new byte[(int) file.length()];
		FileInputStream fileInputStream = new FileInputStream(file);
		fileInputStream.read(bytes);
		fileInputStream.close();
		Imagen imagen = (Imagen) new Imagen().setArchivo(bytes);

		Inmueble inmueble1 = new Inmueble() {
			@Override
			public Integer getId() {
				return 1;
			}
		};
		inmueble1.setDireccion(direccion).setTipo(new TipoInmueble(TipoInmuebleStr.CASA)).setDatosEdificio(new DatosEdificio()).setPrecio(10.0);
		Inmueble inmueble2 = new Inmueble() {
			@Override
			public Integer getId() {
				return 2;
			}
		};
		inmueble2.setDireccion(direccion).setTipo(new TipoInmueble(TipoInmuebleStr.CASA)).setDatosEdificio(new DatosEdificio()).setPrecio(10.0);
		Inmueble inmueble3 = new Inmueble() {
			@Override
			public Integer getId() {
				return 3;
			}
		};
		inmueble3.setDireccion(direccion).setTipo(new TipoInmueble(TipoInmuebleStr.CASA)).setDatosEdificio(new DatosEdificio()).setPrecio(10.0);
		Inmueble inmueble4 = new Inmueble() {
			@Override
			public Integer getId() {
				return 4;
			}
		};
		inmueble4.setDireccion(direccion).setTipo(new TipoInmueble(TipoInmuebleStr.CASA)).setDatosEdificio(new DatosEdificio()).setPrecio(10.0);
		Inmueble inmueble5 = new Inmueble() {
			@Override
			public Integer getId() {
				return 5;
			}
		};
		inmueble5.setDireccion(direccion).setTipo(new TipoInmueble(TipoInmuebleStr.CASA)).setDatosEdificio(new DatosEdificio()).setPrecio(10.0);
		Inmueble inmueble6 = new Inmueble() {
			@Override
			public Integer getId() {
				return 6;
			}
		};
		inmueble6.setDireccion(direccion).setTipo(new TipoInmueble(TipoInmuebleStr.CASA)).setDatosEdificio(new DatosEdificio()).setPrecio(10.0);

		HashMap<Inmueble, Imagen> mapa = new HashMap<>();
		mapa.put(inmueble1, imagen);
		mapa.put(inmueble2, imagen);
		mapa.put(inmueble3, imagen);
		mapa.put(inmueble4, imagen);
		mapa.put(inmueble5, imagen);
		mapa.put(inmueble6, imagen);

		CatalogoVista catalogoVista = new CatalogoVista(new Cliente(), mapa);

		GestorPDF gestor = new GestorPDF() {
			{
				formateador = new FormateadorString();
				conversorFechas = new ConversorFechas();
			}
		};

		PDF pdf = gestor.generarPDF(catalogoVista);
		FileOutputStream fos = new FileOutputStream("testCatalogo.pdf");
		fos.write(pdf.getArchivo());
		fos.close();
	}

	@Test
	/**
	 * Prueba el método generarPDF(Venta venta), el cual corresponde con la taskcard 30 de la iteración 2 y a la historia 8
	 * El test es en parte manual ya que genera un archivo pdf que debe comprobarse si es correcto manualmente.
	 *
	 * @throws Exception
	 */
	public void testGenerarPDFVenta() throws Exception {
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
		Cliente cliente = new Cliente().setNombre("Jaquelina")
				.setApellido("Acosta")
				.setTipoDocumento(new TipoDocumento().setTipo(TipoDocumentoStr.DNI))
				.setNumeroDocumento("36696969");
		Propietario propietario = new Propietario().setNombre("Ignacio")
				.setApellido("Falchini")
				.setTipoDocumento(new TipoDocumento().setTipo(TipoDocumentoStr.DNI))
				.setNumeroDocumento("12345678");
		Localidad localidad = new Localidad().setProvincia(new Provincia().setPais(new Pais())).setNombre("Federal");
		Direccion direccion = new Direccion().setCalle(new Calle().setLocalidad(localidad).setNombre("Donovan")).setLocalidad(localidad).setBarrio(new Barrio().setLocalidad(localidad).setNombre("Centro"))
				.setNumero("635")
				.setPiso("6")
				.setDepartamento("B")
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
		Venta venta = new Venta().setCliente(cliente)
				.setPropietario(propietario)
				.setInmueble(inmueble)
				.setFecha(fechahoy)
				.setImporte(1000000.0)
				.setMedioDePago("contado");

		PDF pdf = gestor.generarPDF(venta);
		FileOutputStream fos = new FileOutputStream("testVenta.pdf");
		fos.write(pdf.getArchivo());
		fos.close();
	}
}