package app.logica.gestores;

import org.springframework.stereotype.Service;

import app.datos.entidades.Reserva;
import app.excepciones.PersistenciaException;
import app.logica.resultados.ResultadoCrearReserva;
import app.logica.resultados.ResultadoEliminarReserva;

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
	public ResultadoCrearReserva crearReserva(Reserva reserva) throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultadoEliminarReserva eliminarReserva(Reserva reserva) {
		// TODO Auto-generated method stub
		return null;
	}

}
