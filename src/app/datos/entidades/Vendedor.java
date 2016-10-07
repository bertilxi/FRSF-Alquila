package app.datos.entidades;

import java.util.ArrayList;

/**
 * Created by fer on 07/10/16.
 */

public class Vendedor {
	private Integer id;
	private String Nombre;
	private String Apellido;
	private TipoDocumento tipoDocumento;
	private Integer numeroDocumento;
	private String password;
	private String salt;
	private ArrayList<Venta> ventas;
}
