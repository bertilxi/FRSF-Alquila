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
import java.util.ResourceBundle;

import app.datos.entidades.Cliente;
import app.datos.entidades.TipoDocumento;
import app.excepciones.EntidadExistenteConEstadoBajaException;
import app.excepciones.GestionException;
import app.excepciones.PersistenciaException;
import app.logica.resultados.ResultadoCrearCliente;
import app.logica.resultados.ResultadoCrearCliente.ErrorCrearCliente;
import app.ui.componentes.ventanas.VentanaConfirmacion;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * Controlador de la vista para dar de alta un cliente
 */
public class AltaClienteController extends OlimpoController {

	public static final String URLVista = "/app/ui/vistas/altaCliente.fxml";

	@FXML
	protected TextField textFieldNombre;

	@FXML
	protected TextField textFieldApellido;

	@FXML
	protected ComboBox<TipoDocumento> comboBoxTipoDocumento;

	@FXML
	protected TextField textFieldNumeroDocumento;

	@FXML
	protected TextField textFieldTelefono;

	private Cliente cliente;

	/**
	 * Setea los campos con los datos del cliente pasado por parámetro. Si no se pasa ninguno se lo crea
	 *
	 * @param cliente
	 * 			cliente del que se obtienen los datos. Si no hay cliente, es <code>null</code>
	 */
	public void setCliente(Cliente cliente) {
		if(cliente != null){
			this.cliente = cliente;
			textFieldNombre.setText(cliente.getNombre());
			textFieldApellido.setText(cliente.getApellido());
			textFieldTelefono.setText(cliente.getTelefono());
			textFieldNumeroDocumento.setText(cliente.getNumeroDocumento());
			comboBoxTipoDocumento.setValue(cliente.getTipoDocumento());
		}
		else{
			this.cliente = new Cliente();
		}
	}

	/**
	 * Acción que se ejecuta al apretar el botón aceptar.
	 *
	 * Valida que se hayan insertado datos, los carga al cliente y deriva la operación a capa lógica.
	 * Si la capa lógica retorna errores, se muestran al usuario.
	 */
	@FXML
	public void acceptAction() {

		StringBuilder error = new StringBuilder("");

		String nombre = textFieldNombre.getText().trim();
		String apellido = textFieldApellido.getText().trim();
		String numeroDocumento = textFieldNumeroDocumento.getText().trim();
		String telefono = textFieldTelefono.getText().trim();
		TipoDocumento tipoDoc = comboBoxTipoDocumento.getValue();

		if(nombre.isEmpty()){
			error.append("Inserte un nombre").append("\n");
		}
		if(apellido.isEmpty()){
			error.append("Inserte un apellido").append("\n");
		}
		if(tipoDoc == null){
			error.append("Elija un tipo de documento").append("\n");
		}
		if(numeroDocumento.isEmpty()){
			error.append("Inserte un numero de documento").append("\n");
		}
		if(telefono.isEmpty()){
			error.append("Inserte un telefono").append("\n");
		}

		if(cliente.getInmuebleBuscado() == null){
			error.append("Debe cargar un inmueble buscado al cliente").append("\n");
		}

		if(!error.toString().isEmpty()){
			presentador.presentarError("Revise sus campos", error.toString(), stage);
		}
		else{
			cliente.setNombre(nombre)
					.setApellido(apellido)
					.setTipoDocumento(tipoDoc)
					.setNumeroDocumento(numeroDocumento)
					.setTelefono(telefono);

			try{
				ResultadoCrearCliente resultado = coordinador.crearCliente(cliente);
				if(resultado.hayErrores()){
					StringBuilder stringErrores = new StringBuilder();
					for(ErrorCrearCliente err: resultado.getErrores()){
						switch(err) {
						case Formato_Nombre_Incorrecto:
							stringErrores.append("Formato de nombre incorrecto.\n");
							break;
						case Formato_Apellido_Incorrecto:
							stringErrores.append("Formato de apellido incorrecto.\n");
							break;
						case Formato_Telefono_Incorrecto:
							stringErrores.append("Formato de teléfono incorrecto.\n");
							break;
						case Formato_Documento_Incorrecto:
							stringErrores.append("Tipo y formato de documento incorrecto.\n");
							break;
						case Ya_Existe_Cliente:
							stringErrores.append("Ya existe un cliente con ese tipo y número de documento.\n");
							break;
						}
					}
					presentador.presentarError("No se pudo crear el cliente", stringErrores.toString(), stage);
				}
				else{
					cambiarmeAScene(AdministrarClienteController.URLVista);
				}
			} catch(GestionException e){
				if(e.getClass().equals(EntidadExistenteConEstadoBajaException.class)){
					VentanaConfirmacion ventana = presentador.presentarConfirmacion("El cliente ya existe", "El cliente ya existía anteriormente pero fué dado de baja.\n ¿Desea volver a darle de alta?", stage);
					if(ventana.acepta()){
						ModificarClienteController controlador = (ModificarClienteController) cambiarmeAScene(ModificarClienteController.URLVista);
						controlador.setClienteEnModificacion(cliente);
					}
				}
			} catch(PersistenciaException e){
				presentador.presentarExcepcion(e, stage);
			}
		}
	}

	/**
	 * Acción que se ejecuta al presionar el botón cargar inmueble.
	 * Se pasa a la pantalla de inmueble buscado
	 */
	@FXML
	private void cargarInmueble() {
		cliente.setNombre(textFieldNombre.getText().trim())
				.setApellido(textFieldApellido.getText().trim())
				.setTipoDocumento(comboBoxTipoDocumento.getValue())
				.setNumeroDocumento(textFieldNumeroDocumento.getText().trim())
				.setTelefono(textFieldTelefono.getText().trim());
		InmuebleBuscadoController controlador = (InmuebleBuscadoController) cambiarmeAScene(InmuebleBuscadoController.URLVista);
		controlador.setCliente(cliente);
	}

	/**
	 * Acción que se ejecuta al presionar el botón cancelar.
	 * Se vuelve a la pantalla administrar cliente.
	 */
	@FXML
	private void cancelAction() {
		cambiarmeAScene(AdministrarClienteController.URLVista);
	}

	@Override
	public void inicializar(URL location, ResourceBundle resources) {
		this.setTitulo("Nuevo cliente");

		try{
			comboBoxTipoDocumento.getItems().addAll(coordinador.obtenerTiposDeDocumento());
		} catch(PersistenciaException e){
			presentador.presentarExcepcion(e, stage);
		}
	}
}
