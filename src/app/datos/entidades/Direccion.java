package app.datos.entidades;

public class Direccion {
	private Integer id; //ID
	private String numero;
	private String piso;
	private String departamento;

	//Relaciones
	private Calle calle;
	private Barrio barrio;
}
