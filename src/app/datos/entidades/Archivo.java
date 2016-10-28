package app.datos.entidades;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "archivo")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Archivo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; //ID

	@Column(name = "archivo")
	@Lob
	@Basic(fetch = FetchType.EAGER)
	private byte[] archivo; //Puede ir String ruta si al final no lo ponemos en la base de datos
}
