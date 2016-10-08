package app.datos.entidades;

import java.util.ArrayList;

public class Propietario {
	private Integer id; //ID
	private String nombre;
	private String Apellido;
	private String numeroDocumento;
	private String telefono;
	private String email;

	//Reclaciones
	private TipoDocumento tipoDocumento;
	private Direccion direccion;

	//Opcionales
	private ArrayList<Inmueble> inmuebles;
}
