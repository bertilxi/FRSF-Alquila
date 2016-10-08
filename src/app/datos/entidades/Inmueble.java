package app.datos.entidades;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by fer on 07/10/16.
 */
public class Inmueble {
	private Integer id;
	private String codigo;
	private Date fechaCarga;
	private Propietario propietario;
	private TipoInmueble tipo;
	private Double precio;
	private DatosEdificio datosEdificio;
	private ArrayList<String> fotos;
	private StringBuffer observaciones;
	private ArrayList<Reserva> reservas;
	private Direccion direccion;
	private Orientacion orientacion;
	private Medidas medidas;
}
