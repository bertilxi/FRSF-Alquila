package app.datos.clases;

public enum TipoDocumentoStr {
	DNI, LC, LE, PASAPORTE, CEDULA_EXTRANJERA;

	@Override
	public String toString() {
		switch(this) {
		case DNI:
			return "DNI";
		case LC:
			return "LC";
		case LE:
			return "LE";
		case PASAPORTE:
			return "Pasaporte";
		case CEDULA_EXTRANJERA:
			return "Cédula Extranjera";
		}
		return null;
	}
}
