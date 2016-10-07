package app.datos.entidades;

import java.util.ArrayList;

/**
 * Created by fer on 07/10/16.
 */
public class Inmueble {
    Integer id;
    String direccion;
    TipoInmueble tipo;
    Double Precio;
    // caracteristicas secundarias del inmueble
    CaracteristicasInmuebles caracteristicas;
    ArrayList<String> fotos;
    StringBuffer observaciones;
    ArrayList<Reserva> reservas;
}
