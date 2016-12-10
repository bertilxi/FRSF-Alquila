/**
 * Copyright (C) 2016 Fernando Berti - Daniel Campodonico - Emiliano Gioria - Lucas Moretti - Esteban Rebechi - Andres Leonel Rico
 * This file is part of Olimpo.
 *
 * Olimpo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Olimpo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Olimpo. If not, see <http://www.gnu.org/licenses/>.
 */
package app.ui.controladores;

import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import app.datos.clases.ReservaVista;
import app.datos.entidades.Cliente;
import app.datos.entidades.Inmueble;
import app.excepciones.PersistenciaException;
import app.logica.resultados.ResultadoCrearReserva;
import app.logica.resultados.ResultadoCrearReserva.ErrorCrearReserva;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.NumberStringConverter;

/**
 * Controlador de la vista que crea un reserva
 */
public class AltaReservaController extends OlimpoController {

	public static final String URLVista = "/app/ui/vistas/altaReserva.fxml";
	protected Inmueble inmueble;

	@FXML
	protected Label labelDetalleInmueble;
	@FXML
	protected ComboBox<Cliente> comboBoxCliente;
	@FXML
	protected TextField textFieldImporte;
	@FXML
	protected DatePicker datePickerInicio;
	@FXML
	protected DatePicker datePickerFin;
	@FXML
	private Button acceptButton;
	@FXML
	private Button cancelButton;

	/**
	 * Acción que se ejecuta al apretar el botón aceptar.
	 *
	 * Valida que se hayan insertado datos, los carga a una reserva y deriva la operación a capa lógica.
	 * Si la capa lógica retorna errores, éstos se muestran al usuario.
	 */
	public void acceptAction() {

		StringBuilder error = new StringBuilder("");

		String importe = textFieldImporte.getText().trim();
		Date fechaInicio = Date.from(datePickerInicio.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
		Date fechaFin = Date.from(datePickerFin.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
		Cliente cliente = comboBoxCliente.getValue();

		if(cliente == null){
			error.append("Seleccione un cliente").append("\r\n ");
		}

		if(importe.equals("")){
			error.append("Ingrese un importe").append("\r\n ");
		}

		/*
		 * if(fecha==null)){
		 * error.append("Por favor, seleccione un cliente.").append("\r\n ");
		 * }
		 * if(cliente==null)){
		 * error.append("Por favor, seleccione un cliente.").append("\r\n ");
		 * }
		 */

		if(!error.toString().isEmpty()){
			presentador.presentarError("Revise sus campos", error.toString(), stage);
		}
		else{
			ReservaVista reserva = new ReservaVista(cliente, inmueble, Double.valueOf(importe), fechaInicio, fechaFin);

			ResultadoCrearReserva resultadoCrearReserva = null;

			try{
				resultadoCrearReserva = coordinador.crearReserva(reserva);
				error.delete(0, error.length());

				if(resultadoCrearReserva.hayErrores()){
					for(ErrorCrearReserva e: resultadoCrearReserva.getErrores()){
						switch(e) {
						case Formato_Importe_Incorrecto:
							error.append("El formato del importe es incorrecto");
							break;
						case Ya_Existe_Reserva_Entre_Las_Fechas:
							error.append("Ya existe una reserva para este inmueble en ese periodo de tiempo");
							break;
						}
					}
					presentador.presentarError("Revise sus campos", error.toString(), stage);
				}
				else{
					presentador.presentarToast("Se ha realizado la reserva con éxito", stage);
					cambiarmeAScene(AdministrarInmuebleController.URLVista);
				}
			} catch(PersistenciaException e){
				presentador.presentarExcepcion(e, stage);
			} catch(Exception e){
				presentador.presentarExcepcionInesperada(e, stage); //falta el stage
			}
		}
	}

	/**
	 * Acción que se ejecuta al presionar el botón cancelar.
	 * Se vuelve a la pantalla desde la que se llamó a AltaReserva, administrarInmuebles.
	 */
	public void cancelAction(ActionEvent event) {
		cambiarmeAScene(AdministrarInmuebleController.URLVista);
	}

	public void setInmueble(Inmueble inmueble) {
		this.inmueble = inmueble;
	}

	@Override
	public void inicializar(URL location, ResourceBundle resources) {
		//this.setTitulo("Nueva Reserva");
		try{
			comboBoxCliente.getItems().addAll(coordinador.obtenerClientes());
			//	labelDetalleInmueble.setText(inmueble.getDireccion().toString());
		} catch(PersistenciaException e){
		}
		textFieldImporte.setTextFormatter(new TextFormatter<>(new NumberStringConverter(Locale.US, "###############.##")));
	}
}
