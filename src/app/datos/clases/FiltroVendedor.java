package app.datos.clases;

public class FiltroVendedor {

	private TipoDocumentoStr tipoDocumento;
	private String documento;
	private String contra;

	public FiltroVendedor(TipoDocumentoStr tipoDocumento, String documento, String contra) {
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

	public String getContra() {
		return contra;
	}

}
