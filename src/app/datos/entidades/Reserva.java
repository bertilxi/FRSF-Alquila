package app.datos.entidades;

import java.util.Date;

public class Reserva {
	private Integer id; //ID
	private Double importe;
	private Date tiempoVigencia;

	//Relaciones
	private Cliente cliente;
	private Inmueble inmueble;
}
