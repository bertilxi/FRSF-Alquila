package app.datos.entidades;

import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class Imagen extends Archivo {

	//Relaciones

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "codinmueble", foreignKey = @ForeignKey(name = "imagen_codinmueble_fk"))
	private Inmueble inmueble;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "codinmueble", foreignKey = @ForeignKey(name = "imagen_codhistorialinmueble_fk"))
	private HistorialInmueble historialInmueble;
}
