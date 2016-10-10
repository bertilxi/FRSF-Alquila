package app.datos.entidades;

import java.util.ArrayList;

public class Vendedor {
    private Integer id;
    private String Nombre;
    private String Apellido;
    private String numeroDocumento;
    private String password;
    private String salt;

    //Relaciones
    private TipoDocumento tipoDocumento;

    //Opcionales
    private ArrayList<Venta> ventas;
}
