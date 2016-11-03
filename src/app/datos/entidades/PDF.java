package app.datos.entidades;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "pdf")
@PrimaryKeyJoinColumn(name = "archivoid")
public class PDF extends Archivo {

	public PDF() {
		super();
	}

	public PDF(byte[] archivo) {
		super(archivo);
	}
}
