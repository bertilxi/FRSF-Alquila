package app.datos.entidades;

public class Direccion {
    private Integer id; //ID
    private String numero;
    private String pisoDepartamentoOtros;

    //Relaciones
    private Calle calle;
    private Barrio barrio;
}
