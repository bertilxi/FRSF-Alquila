package app.datos.entidades;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "imagen")
@PrimaryKeyJoinColumn(name = "archivoid")
public class Imagen extends Archivo {

	public Imagen() {
		super();
	}

	public Imagen(byte[] archivo) {
		super(archivo);
	}
}
