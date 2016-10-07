package app.datos.entidades;

import java.util.ArrayList;

/**
 * Created by fer on 07/10/16.
 */
public class Inmueble {
    Integer id;
    String direccion;
    String tipo;
    Double Precio;
    StringBuffer caracteristicas;
    ArrayList<String> fotos;
    StringBuffer observaciones;
    ArrayList<Reserva> reservas;
}
