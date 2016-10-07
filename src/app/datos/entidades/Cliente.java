package app.datos.entidades;

import java.util.ArrayList;

/**
 * Created by fer on 07/10/16.
 */
public class Cliente {
    Integer id;
    String nombre;
    String apellido;
    TipoDocumento tipoDocumento;
    Integer numeroDocumento;
    String telefono;
    Propietario propietario;
    InmuebleBuscado buscado;
    ArrayList<Venta> compras;
    ArrayList<Reserva> reservas;
    ArrayList<Catalogo> catalogos;

}
