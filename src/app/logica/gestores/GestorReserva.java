package app.logica.gestores;

import org.springframework.stereotype.Service;

import app.datos.clases.ReservaVista;
import app.excepciones.PersistenciaException;
import app.logica.resultados.ResultadoCrearReserva;

@Service
/**
 * Gestor que implementa la capa lógica del ABM Reserva y funciones asociadas a una reserva.
 */
public class GestorReserva {

	/**
	 * Método para crear una reserva. Primero se validan las reglas de negocia y luego se persiste.
	 * Pertenece a la taskcard 25 de la iteración 2 y a la historia 7
	 *
	 * @param reserva
	 *            a guardar
	 * @return resultado de la operación
	 * @throws PersistenciaException
	 *             si falló al persistir
	 */
	public ResultadoCrearReserva crearReserva(ReservaVista reserva) throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

}
