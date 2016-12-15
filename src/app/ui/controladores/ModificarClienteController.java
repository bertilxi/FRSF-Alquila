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
import app.excepciones.PersistenciaException;
import app.logica.resultados.ResultadoModificarCliente;
import app.logica.resultados.ResultadoModificarCliente.ErrorModificarCliente;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * Controlador de la vista para modificar un cliente
 *
 * Task card 17 de la iteración 1, historia de usuario 2
 */
//Modificada en TaskCard 27 de la iteración 2
public class ModificarClienteController extends OlimpoController {

	public static final String URLVista = "/app/ui/vistas/modificarCliente.fxml";

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

	@FXML
	protected TextField textFieldCorreo;

	protected Cliente clienteEnModificacion;

	/**
	 * Setea los campos con los datos del cliente pasado por parámetro.
	 *
	 * @param clienteEnModificacion
	 *            cliente del que se obtienen los datos.
	 */
	public void setClienteEnModificacion(Cliente clienteEnModificacion) {
		//se setean los datos del cliente en modificación
		this.clienteEnModificacion = clienteEnModificacion;
		textFieldNombre.setText(clienteEnModificacion.getNombre());
		textFieldApellido.setText(clienteEnModificacion.getApellido());
		textFieldTelefono.setText(clienteEnModificacion.getTelefono());
		textFieldNumeroDocumento.setText(clienteEnModificacion.getNumeroDocumento());
		comboBoxTipoDocumento.setValue(clienteEnModificacion.getTipoDocumento());
		textFieldCorreo.setText(clienteEnModificacion.getCorreo());
	}

	/**
	 * Acción que se ejecuta al apretar el botón aceptar.
	 *
	 * Valida que se hayan insertado datos, los carga al cliente y deriva la operación a capa lógica.
	 * Si la capa lógica retorna errores, se muestran al usuario.
	 */
	@FXML
	protected void acceptAction() {

		StringBuilder error = new StringBuilder("");

		//obtengo datos introducidos por el usuario
		String nombre = textFieldNombre.getText().trim();
		String apellido = textFieldApellido.getText().trim();
		String numeroDocumento = textFieldNumeroDocumento.getText().trim();
		String telefono = textFieldTelefono.getText().trim();
		String correo = textFieldCorreo.getText().trim();
		TipoDocumento tipoDoc = comboBoxTipoDocumento.getValue();

		//verifico que no estén vacíos
		if(nombre.isEmpty()){
			error.append("Inserte un nombre").append("\r\n");
		}
		if(apellido.isEmpty()){
			error.append("Inserte un apellido").append("\r\n");
		}
		if(tipoDoc == null){
			error.append("Elija un tipo de documento").append("\r\n");
		}
		if(numeroDocumento.isEmpty()){
			error.append("Inserte un numero de documento").append("\r\n");
		}
		if(telefono.isEmpty()){
			error.append("Inserte un telefono").append("\r\n");
		}

		if(correo.isEmpty()){
			error.append("Inserte una dirección de correo electrónico").append("\n");
		}

		if(!error.toString().isEmpty()){  //si hay algún error lo muestro al usuario
			presentador.presentarError("Revise sus campos", error.toString(), stage);
		}
		else{
			//Si no hay errores se modifican las entidades con los datos introducidos
			clienteEnModificacion.setNombre(nombre)
					.setApellido(apellido)
					.setTipoDocumento(tipoDoc)
					.setNumeroDocumento(numeroDocumento)
					.setTelefono(telefono)
					.setCorreo(correo);

			try{
				//relevo la operación a capa lógica
				ResultadoModificarCliente resultado = coordinador.modificarCliente(clienteEnModificacion);
				if(resultado.hayErrores()){
					// si hay algún error se muestra al usuario
					StringBuilder stringErrores = new StringBuilder();
					for(ErrorModificarCliente err: resultado.getErrores()){
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
						case Formato_Correo_Incorrecto:
							stringErrores.append("Formato de correo electrónico incorrecto.\n");
							break;
						case Formato_Documento_Incorrecto:
							stringErrores.append("Tipo y formato de documento incorrecto.\n");
							break;
						case Otro_Cliente_Posee_Mismo_Documento_Y_Tipo:
							stringErrores.append("Otro cliente ya posee ese tipo y número de documento.\n");
							break;
						}
					}
					presentador.presentarError("Revise sus campos", stringErrores.toString(), stage);
				}
				else{
					//si no hay errores se muestra notificación y se vuelve a la pantalla de listar clientes
					presentador.presentarToast("Se ha modificado el cliente con éxito", stage);
					cambiarmeAScene(AdministrarClienteController.URLVista);
				}
			} catch(PersistenciaException e){ //excepción originada en la capa de persistencia
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
		InmuebleBuscadoController controlador = (InmuebleBuscadoController) cambiarmeAScene(InmuebleBuscadoController.URLVista);
		controlador.setCliente(clienteEnModificacion);
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
		this.setTitulo("Modificar cliente");

		try{
			comboBoxTipoDocumento.getItems().addAll(coordinador.obtenerTiposDeDocumento());
		} catch(PersistenciaException e){
			presentador.presentarExcepcion(e, stage);
		}
	}
}