package app.datos.entidades;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "archivo")
public abstract class Archivo {
	private Integer id; //ID
	private byte[] archivo; //Puede ir String ruta si al final no lo ponemos en la base de datos
}
