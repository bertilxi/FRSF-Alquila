package app.datos.clases;

public class FiltroPropietario {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((documento == null) ? 0 : documento.hashCode());
		result = prime * result + ((tipoDocumento == null) ? 0 : tipoDocumento.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		FiltroPropietario other = (FiltroPropietario) obj;
		if(documento == null){
			if(other.documento != null)
				return false;
		}
		else if(!documento.equals(other.documento))
			return false;
		if(tipoDocumento != other.tipoDocumento)
			return false;
		return true;
	}

	private TipoDocumentoStr tipoDocumento;
	private String documento;

	public FiltroPropietario(TipoDocumentoStr tipoDocumento, String documento) {
		super();
		if(tipoDocumento == null || documento == null){
			throw new NullPointerException();
		}
		this.tipoDocumento = tipoDocumento;
		this.documento = documento;
	}

	public TipoDocumentoStr getTipoDocumento() {
		return tipoDocumento;
	}

	public String getDocumento() {
		return documento;
	}

}
