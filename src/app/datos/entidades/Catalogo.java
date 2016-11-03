package app.datos.entidades;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("unused")
@Entity
@Table(name = "catalogo")
public class Catalogo {
	private Cliente cliente; //ID
	private Date fechaEmision;

	//Relaciones
	private PDF catalogo;
}
