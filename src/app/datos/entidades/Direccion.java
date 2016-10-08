package app.datos.entidades;

public class Direccion {
	private Integer id; //ID
	private String calle;
	private String numero;
	private String piso;
	private String departamento;
	private String barrio;

	//Relaciones
	private Localidad localidad;
}
