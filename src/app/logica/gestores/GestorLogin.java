package app.logica.gestores;

import app.datos.clases.DatosLogin;
import app.excepciones.PersistenciaException;
import app.logica.resultados.ResultadoLogin;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Service;

@Service
public class GestorLogin {

    public ResultadoLogin loguearVendedor(DatosLogin login) throws PersistenciaException {
        throw new NotYetImplementedException();
    }

}
