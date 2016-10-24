package app.datos.clases;

public class FiltroPropietario {

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
