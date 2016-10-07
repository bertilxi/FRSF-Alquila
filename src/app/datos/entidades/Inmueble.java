package app.datos.entidades;

import java.util.ArrayList;

/**
 * Created by fer on 07/10/16.
 */
public class Inmueble {
    private Integer id;
    private String direccion;
    private TipoInmueble tipo;
    private Double Precio;
    // caracteristicas secundarias del inmueble
    private CaracteristicasInmuebles caracteristicas;
    private ArrayList<String> fotos;
    private StringBuffer observaciones;
    private ArrayList<Reserva> reservas;
}
