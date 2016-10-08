package app.datos.entidades;

import java.util.ArrayList;

import app.datos.clases.TipoInmuebleStr;

public class TipoInmueble {
	private Integer id;
	private TipoInmuebleStr tipo;

	//Relaciones
	private ArrayList<InmuebleBuscado> inmueblesBuscados; //Relacion muchos a muchos
}
