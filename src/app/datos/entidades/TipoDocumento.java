package app.datos.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import app.datos.clases.TipoDocumentoStr;

@NamedQuery(name = "obtenerTiposDeDocumento", query = "SELECT t FROM TipoDocumento t")
@Entity
@Table(name = "tipo_documento")
public class TipoDocumento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; //ID

	//TODO ver enum
	@Column(name = "tipo_documento_enum")
	@Enumerated(EnumType.STRING)
	private TipoDocumentoStr tipo;

	public TipoDocumento() {
		super();
	}

	public TipoDocumento(Integer id, TipoDocumentoStr tipo) {
		super();
		this.id = id;
		this.tipo = tipo;
	}

	public Integer getId() {
		return id;
	}

	public TipoDocumentoStr getTipo() {
		return tipo;
	}

	public TipoDocumento setTipo(TipoDocumentoStr tipo) {
		this.tipo = tipo;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj){
			return true;
		}
		if(obj == null){
			return false;
		}
		if(getClass() != obj.getClass()){
			return false;
		}
		TipoDocumento other = (TipoDocumento) obj;
		if(id == null){
			if(other.id != null){
				return false;
			}
		}
		else if(!id.equals(other.id)){
			return false;
		}
		if(tipo != other.tipo){
			return false;
		}
		return true;
	}
}
