package app.datos.entidades;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by fer on 07/10/16.
 */
public class Inmueble {
	Integer id;
	String codigo;
	Date fechaCarga;
	Propietario propietario;
	TipoInmueble tipo;
	Double precio;
	DatosEdificio datosEdificio;
	ArrayList<String> fotos;
	StringBuffer observaciones;
	ArrayList<Reserva> reservas;
	Direccion direccion;
	Orientacion orientacion;
	Medidas medidas;
}
