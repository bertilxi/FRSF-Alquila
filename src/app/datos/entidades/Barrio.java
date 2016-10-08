package app.datos.entidades;

import java.util.ArrayList;

public class Barrio {
	private Integer id; //ID
	private String nombre;

	//Relaciones
	private Localidad localidad;
	private ArrayList<InmuebleBuscado> inmueblesBuscados; //Relacion muchos a muchos
}
