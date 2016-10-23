package app.datos.servicios;

import app.datos.clases.TipoDocumentoStr;

public class FiltroCliente {

	private TipoDocumentoStr tipoDocumento;
	private String documento;

	public FiltroCliente(TipoDocumentoStr tipoDocumento, String documento) {
		super();
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
