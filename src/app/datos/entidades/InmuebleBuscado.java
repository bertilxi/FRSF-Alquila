package app.datos.entidades;

import java.util.ArrayList;

public class InmuebleBuscado {
    private Cliente cliente; //ID
    private Double precioMax;
    private Double superficieMin; // en metros cuadrados
    private Integer antiguedadMax; // en años
    private Integer dormitoriosMin;
    private Integer bañosMin;
    private Boolean garaje;
    private Boolean patio;
    private Boolean piscina;
    private Boolean propiedadHorizontal;
    private Boolean aguaCorriente;
    private Boolean cloacas;
    private Boolean gasNatural;
    private Boolean aguaCaliente;
    private Boolean lavadero;
    private Boolean pavimento;

    //Relaciones
    private Localidad localidad;
    private ArrayList<Barrio> barrios; //Relacion muchos a muchos
    private ArrayList<TipoInmueble> tiposInmueblesBuscados; //Relacion muchos a muchos
}
