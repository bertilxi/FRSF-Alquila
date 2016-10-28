package app.datos.entidades;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "reserva")
public class Reserva {
	private Integer id; //ID
	private Double importe;
	private Date tiempoVigencia;
	private PDF reserva;

	//Relaciones
	private Cliente cliente;
	private Inmueble inmueble;
}
