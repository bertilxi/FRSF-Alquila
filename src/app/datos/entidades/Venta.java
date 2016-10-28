package app.datos.entidades;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "venta")
public class Venta {

	private Integer id; //ID
	private Double monto;
	private Date fecha;
	private PDF venta;

	//Relaciones
	private Vendedor vendedor;
	private Cliente cliente;
	private Propietario propietario;
	private Inmueble inmueble;

}
