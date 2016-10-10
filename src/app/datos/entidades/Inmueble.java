package app.datos.entidades;

import java.util.ArrayList;
import java.util.Date;

public class Inmueble {
	private Integer id; //ID
	private String codigo;
	private String observaciones;
	private Date fechaCarga;
	private Double precio;
	private Double frente; // en metros
	private Double fondo; // en metros
	private Double superficie; // en metros cuadrados

	//Relaciones
	private TipoInmueble tipo;
	private Orientacion orientacion;
	private Direccion direccion;
	private Propietario propietario;
	private ArrayList<Imagen> fotos; //Relacion muchos a muchos

	//Opcionales
	private DatosEdificio datosEdificio;
	private ArrayList<Reserva> reservas;
}
