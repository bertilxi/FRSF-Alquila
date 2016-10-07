package app.datos.entidades;

import java.util.ArrayList;

/**
 * Created by fer on 07/10/16.
 */
public class Propietario {
	private Integer id;
	private String nombre;
	private String Apellido;
	private TipoDocumento tipoDocumento;
	private Integer numeroDocumento;
	private String direccion;
	private String telefono;
	private String email;
	private ArrayList<Inmueble> inmuebles;

}
