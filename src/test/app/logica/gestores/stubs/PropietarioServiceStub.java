package test.app.logica.gestores.stubs;

import org.hibernate.SessionFactory;

import app.datos.clases.TipoDocumentoStr;
import app.datos.entidades.Propietario;
import app.datos.entidades.TipoDocumento;
import app.datos.servicios.implementacion.PropietarioServiceJPA;
import app.excepciones.PersistenciaException;

public class PropietarioServiceStub extends PropietarioServiceJPA{

	public PropietarioServiceStub(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public PropietarioServiceStub() {
		super(null);
	}
	
	@Override
	public void guardarPropietario(Propietario propietario) throws PersistenciaException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modificarPropietario(Propietario propietario) throws PersistenciaException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Propietario obtenerPropietario(TipoDocumento tipoDocumento, String documento) throws PersistenciaException {
		TipoDocumentoStr tipoExistente = new TipoDocumento().getTipo().DNI;
		String documentoExistente = "11111111";
	
		if(tipoDocumento.equals(tipoExistente)&&documento.equals(documentoExistente)) {
			return true;
		}
		else {
			return false;
		}
	}
}
