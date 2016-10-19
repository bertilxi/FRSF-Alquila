package app.logica.gestores;

import app.datos.clases.DatosLogin;
import app.datos.servicios.VendedorService;
import app.excepciones.PersistenciaException;
import app.logica.resultados.ResultadoAutenticacion;
import app.logica.resultados.ResultadoCrearVendedor;
import app.logica.resultados.ResultadoEliminarVendedor;
import app.logica.resultados.ResultadoLogin;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Service;

@Service
public class GestorVendedor {


    private VendedorService persistidorVendedor;

/*
    public ResultadoAutenticacion autenticarVendedor(DatosLogin datos) throws PersistenciaException {

    }

    public ResultadoCrearVendedor crearVendedor () throws PersistenciaException {

    }

    public ResultadoEliminarVendedor eliminarVendedor() throws PersistenciaException {

    }*/
}
