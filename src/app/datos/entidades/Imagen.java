package app.datos.entidades;

import java.util.ArrayList;

import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class Imagen extends Archivo {

	//Relaciones

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "codinmueble", foreignKey = @ForeignKey(name = "imagen_codinmueble_fk"))
	private ArrayList<Inmueble> inmuebles;
}
