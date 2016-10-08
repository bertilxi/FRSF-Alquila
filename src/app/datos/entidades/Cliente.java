package app.datos.entidades;

import java.util.ArrayList;

public class Cliente {
	private Integer id; //ID
	private String nombre;
	private String apellido;
	private String numeroDocumento;
	private String telefono;

	//Relaciones
	private TipoDocumento tipoDocumento;

	//Opcionales
	private InmuebleBuscado buscado;
	private ArrayList<Venta> compras;
	private ArrayList<Reserva> reservas;
	private ArrayList<Catalogo> catalogos;

}
