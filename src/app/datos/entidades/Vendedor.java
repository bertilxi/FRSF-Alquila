package app.datos.entidades;

import java.util.ArrayList;

public class Vendedor {

	private Integer id;
	private String nombre;
	private String apellido;
	private String numeroDocumento;
	private String password;
	private String salt;

    //Relaciones
    private TipoDocumento tipoDocumento;

    //Opcionales
    private ArrayList<Venta> ventas;
}
