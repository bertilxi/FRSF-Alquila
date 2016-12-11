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
		new ControladorTest(LoginController.URLVista, new LoginController(){
			@Override
			protected void setTitulo(String titulo) {
			}
			@Override
			public void inicializar(URL location, ResourceBundle resources) {
			}
		});
		GestorPDF gestor = new GestorPDF(){
			{formateador = new FormateadorString();
			conversorFechas = new ConversorFechas();}
		};
		Cliente cliente = new Cliente().setNombre("Pablo")
				.setApellido("Van Derdonckt")
				.setTipoDocumento(new TipoDocumento().setTipo(TipoDocumentoStr.DNI))
				.setNumeroDocumento("36696969");
		Propietario propietario = new Propietario().setNombre("Emiliano")
				.setApellido("Gioria");
		Localidad localidad = new Localidad().setProvincia(new Provincia().setPais(new Pais())).setNombre("Ceres");
		Direccion direccion = new Direccion().setCalle(new Calle().setLocalidad(localidad).setNombre("Azquenaga")).setLocalidad(localidad).setBarrio(new Barrio().setLocalidad(localidad).setNombre("Vicente Zaspe"))
				.setNumero("3434")
				.setPiso("6")
				.setDepartamento("6B")
				.setOtros("otros");
		
		Inmueble inmueble = new Inmueble(){
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