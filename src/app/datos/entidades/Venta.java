package app.datos.entidades;

import java.util.Date;

public class Venta {

	private Integer id; //ID
	private Double monto;
	private Date fecha;

	//Relaciones
	private Vendedor vendedor;
	private Cliente cliente;
	private Propietario propietario;
	private Inmueble inmueble;

}
