package app.datos.entidades;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "pdf")
@PrimaryKeyJoinColumn(name = "pdf")
public class PDF extends Archivo {

}
