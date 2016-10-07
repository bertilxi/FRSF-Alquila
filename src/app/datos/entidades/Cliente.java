package app.datos.entidades;

import java.util.ArrayList;

/**
 * Created by fer on 07/10/16.
 */
public class Cliente {
	private Integer id;
	private String nombre;
	private String apellido;
	private TipoDocumento tipoDocumento;
	private Integer numeroDocumento;
	private String telefono;
	private Propietario propietario;
	private InmuebleBuscado buscado;
	private ArrayList<Venta> compras;
	private ArrayList<Reserva> reservas;
	private ArrayList<Catalogo> catalogos;

}
