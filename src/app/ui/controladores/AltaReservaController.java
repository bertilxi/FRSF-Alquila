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

import app.datos.entidades.Cliente;
import app.datos.entidades.Inmueble;
import app.datos.entidades.Reserva;
import app.excepciones.PersistenciaException;
import app.logica.resultados.ResultadoCrearReserva;
import app.logica.resultados.ResultadoCrearReserva.ErrorCrearReserva;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.NumberStringConverter;

/**
 * Controlador de la vista que crea un reserva
 */
public class AltaReservaController extends OlimpoController {

	public static final String URLVista = "/app/ui/vistas/altaReserva.fxml";
	protected Inmueble inmueble;
	protected Cliente cliente;

	@FXML
	protected ComboBox<Cliente> comboBoxCliente;
	@FXML
	protected ComboBox<Inmueble> comboBoxInmueble;
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
		Cliente cliente = comboBoxCliente.getValue();
		Inmueble inmueble = comboBoxInmueble.getValue();

		if(cliente == null){
			error.append("Seleccione un cliente").append("\r\n");
		}

		if(inmueble == null){
			error.append("Seleccione un inmueble").append("\r\n");
		}

		if(importe.equals("")){
			error.append("Ingrese un importe").append("\r\n");
		}

		if(datePickerInicio.getValue() == null){
			error.append("Ingrese una fecha de inicio").append("\r\n");
		}

		if(datePickerFin.getValue() == null){
			error.append("Ingrese una fecha de fin").append("\r\n");
		}

		if(!error.toString().isEmpty()){
			presentador.presentarError("Revise sus campos", error.toString(), stage);
		}
		else{
			Date fechaInicio = Date.from(datePickerInicio.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
			Date fechaFin = Date.from(datePickerFin.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());

			Reserva reserva = new Reserva()
					.setImporte(Double.valueOf(importe))
					.setFechaFin(fechaFin)
					.setFechaInicio(fechaInicio)
					.setCliente(cliente)
					.setInmueble(inmueble);

			ResultadoCrearReserva resultadoCrearReserva = null;

			try{
				resultadoCrearReserva = coordinador.crearReserva(reserva);
				error.delete(0, error.length());

				if(resultadoCrearReserva.hayErrores()){
					for(ErrorCrearReserva e: resultadoCrearReserva.getErrores()){
						switch(e) {
						case Cliente_Vacío:
							error.append("No se ha seleccionado un cliente\r\n");
							break;
						case Nombre_Cliente_Vacío:
							error.append("El cliente seleccionado no tiene nombre\r\n");
							break;
						case Apellido_Cliente_Vacío:
							error.append("El cliente seleccionado no tiene un apellido\r\n");
							break;
						case NúmeroDocumento_Cliente_Vacío:
							error.append("El cliente seleccionado no tiene número de documento\r\n");
							break;
						case TipoDocumento_Cliente_Vacío:
							error.append("El cliente seleccionado no tiene un tipo de documento\r\n");
							break;
						case Inmueble_Vacío:
							error.append("No se ha seleccionado un inmueble\r\n");
							break;
						case Dirección_Inmueble_Vacía:
							error.append("El inmueble seleccionado no tiene una dirección\r\n");
							break;
						case Barrio_Inmueble_Vacío:
							error.append("El inmueble seleccionado no tiene un barrio\r\n");
							break;
						case Calle_Inmueble_Vacía:
							error.append("El inmueble seleccionado no tiene una calle\r\n");
							break;
						case Localidad_Inmueble_Vacía:
							error.append("El inmueble seleccionado no tiene una localidad\r\n");
							break;
						case Altura_Inmueble_Vacía:
							error.append("El inmueble seleccionado no tiene una altura en su dirección\r\n");
							break;
						case Tipo_Inmueble_Vacío:
							error.append("El inmueble seleccionado no tiene un tipo de inmueble\r\n");
							break;
						case Propietario_Vacío:
							error.append("El inmueble seleccionado no tiene propietario\r\n");
							break;
						case Nombre_Propietario_Vacío:
							error.append("El propietario del inmueble ingresado no tiene nombre\r\n");
							break;
						case Apellido_Propietario_Vacío:
							error.append("El propietario del inmueble seleccionado no tiene apellido\r\n");
							break;
						case Importe_Vacío:
							error.append("No se ha ingresado un importe\r\n");
							break;
						case Formato_Importe_Incorrecto:
							error.append("El formato del importe es incorrecto\r\n");
							break;
						case FechaFin_vacía:
							error.append("No se ha seleccionado una fecha de fin\r\n");
							break;
						case FechaInicio_vacía:
							error.append("No se ha seleccionado una fecha de inicio\r\n");
							break;
						case Ya_Existe_Reserva_Entre_Las_Fechas:
							error.append("Ya existe una reserva para este inmueble en ese periodo de tiempo\r\n");
							break;
						}
					}
					presentador.presentarError("Revise sus campos", error.toString(), stage);
				}
				else{
					presentador.presentarToast("Se ha realizado la reserva con éxito", stage);
					salir();
				}
			} catch(PersistenciaException e){
				presentador.presentarExcepcion(e, stage);
			} catch(Exception e){
				presentador.presentarExcepcionInesperada(e, stage);
			}
		}
	}

	/**
	 * Acción que se ejecuta al presionar el botón cancelar.
	 * Se vuelve a la pantalla desde la que se llamó a AltaReserva, administrarInmuebles.
	 */
	public void cancelAction(ActionEvent event) {
		salir();
	}

	@Override
	public void salir() {
		AdministrarReservaController controlador = (AdministrarReservaController) cambiarmeAScene(AdministrarReservaController.URLVista);
		if(cliente != null){
			controlador.setCliente(cliente);
		}
		else if(inmueble != null){
			controlador.setCliente(cliente);
		}
	}

	public void setInmueble(Inmueble inmueble) {
		this.inmueble = inmueble;
		if(inmueble != null){
			comboBoxInmueble.getSelectionModel().select(inmueble);
		}
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
		if(cliente != null){
			comboBoxCliente.getSelectionModel().select(cliente);
		}
	}

	@Override
	public void inicializar(URL location, ResourceBundle resources) {
		this.setTitulo("Nueva Reserva");
		try{
			comboBoxCliente.getItems().addAll(coordinador.obtenerClientes());
			comboBoxInmueble.getItems().addAll(coordinador.obtenerInmuebles());
		} catch(PersistenciaException e){
		}
		textFieldImporte.setTextFormatter(new TextFormatter<>(new NumberStringConverter(Locale.US, "###.##")));
	}
}
