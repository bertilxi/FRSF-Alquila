package app.datos.entidades;

import app.datos.clases.TipoInmuebleStr;

import java.util.ArrayList;

public class TipoInmueble {
    private Integer id; //ID
    private TipoInmuebleStr tipo;

    //Relaciones
    private ArrayList<InmuebleBuscado> inmueblesBuscados; //Relacion muchos a muchos
}
