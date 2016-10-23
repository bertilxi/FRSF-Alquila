/**
 *
 */
package datos.entidades;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import app.datos.entidades.Direccion;
import app.datos.entidades.Inmueble;
import app.datos.entidades.Propietario;
import app.datos.entidades.TipoDocumento;

public class PropietarioTest {

	@Test
	public void equalsTestIguales() {
		TipoDocumento doc = new TipoDocumento();
		Direccion dir = new Direccion();
		ArrayList<Inmueble> inmuebles = new ArrayList<>();
		Propietario a = new Propietario(1, "Juan", "Perez", "38377777", "3424686868", "d.a@hotmail.com", doc, dir, inmuebles);
		Propietario b = new Propietario(1, "Juan", "Perez", "38377777", "3424686868", "d.a@hotmail.com", doc, dir, inmuebles);

		assertTrue(a.equals(b));
	}

	@Test
	public void equalsTestNombreDistinto() {
		TipoDocumento doc = new TipoDocumento();
		Direccion dir = new Direccion();
		ArrayList<Inmueble> inmuebles = new ArrayList<>();
		Propietario a = new Propietario(1, "Pablo", "Perez", "38377777", "3424686868", "d.a@hotmail.com", doc, dir, inmuebles);
		Propietario b = new Propietario(1, "Juan", "Perez", "38377777", "3424686868", "d.a@hotmail.com", doc, dir, inmuebles);

		assertFalse(a.equals(b));
	}

	@Test
	public void equalsTestMailDistinto() {
		TipoDocumento doc = new TipoDocumento();
		Direccion dir = new Direccion();
		ArrayList<Inmueble> inmuebles = new ArrayList<>();
		Propietario a = new Propietario(1, "Juan", "Perez", "38377777", "3424686868", "d.a@hotmail.com", doc, dir, inmuebles);
		Propietario b = new Propietario(1, "Juan", "Perez", "38377777", "3424686868", "a@hotmail.com", doc, dir, inmuebles);

		assertFalse(a.equals(b));
	}

	@Test
	public void equalsTestPunteroNulo() {
		TipoDocumento doc = new TipoDocumento();
		Direccion dir = new Direccion();
		ArrayList<Inmueble> inmuebles = new ArrayList<>();
		Propietario a = new Propietario(1, "Pablo", "Perez", "38377777", "3424686868", "d.a@hotmail.com", doc, dir, inmuebles);
		Propietario b = null;
		assertFalse(a.equals(b));
	}
}
