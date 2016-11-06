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

public class ModificarClienteController extends OlimpoController {

	public static final String URLVista = "/app/ui/vistas/modificarCliente.fxml";

	@FXML
	private TextField textFieldNombre;

	@FXML
	private TextField textFieldApellido;

	@FXML
	private ComboBox<TipoDocumento> comboBoxTipoDocumento;

	@FXML
	private TextField textFieldNumeroDocumento;

	@FXML
	private TextField textFieldTelefono;

	private Cliente clienteEnModificacion;

	public void setClienteEnModificacion(Cliente clienteEnModificacion) {
		this.clienteEnModificacion = clienteEnModificacion;
		textFieldNombre.setText(clienteEnModificacion.getNombre());
		textFieldApellido.setText(clienteEnModificacion.getApellido());
		textFieldTelefono.setText(clienteEnModificacion.getTelefono());
		textFieldNumeroDocumento.setText(clienteEnModificacion.getNumeroDocumento());
		comboBoxTipoDocumento.setValue(clienteEnModificacion.getTipoDocumento());
	}

	@FXML
	private void acceptAction() {

		StringBuilder error = new StringBuilder("");

		String nombre = textFieldNombre.getText().trim();
		String apellido = textFieldApellido.getText().trim();
		String numeroDocumento = textFieldNumeroDocumento.getText().trim();
		String telefono = textFieldTelefono.getText().trim();
		TipoDocumento tipoDoc = comboBoxTipoDocumento.getValue();

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

		if(!error.toString().isEmpty()){
			presentador.presentarError("Revise sus campos", error.toString(), stage);
		}
		else{
			clienteEnModificacion.setNombre(nombre)
					.setApellido(apellido)
					.setTipoDocumento(tipoDoc)
					.setNumeroDocumento(numeroDocumento)
					.setTelefono(telefono);

			try{
				ResultadoModificarCliente resultado = coordinador.modificarCliente(clienteEnModificacion);
				if(resultado.hayErrores()){
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
						case Formato_Documento_Incorrecto:
							stringErrores.append("Tipo y formato de documento incorrecto.\n");
							break;
						case Otro_Cliente_Posee_Mismo_Documento_Y_Tipo:
							stringErrores.append("Otro cliente ya posee ese tipo y número de documento.\n");
							break;
						}
					}
					presentador.presentarError("No se pudo modificar el cliente", stringErrores.toString(), stage);
				}
				else{
					cambiarmeAScene(AdministrarClienteController.URLVista);
				}
			} catch(PersistenciaException e){
				presentador.presentarExcepcion(e, stage);
			}
		}
	}

	@FXML
	private void cargarInmueble() {
		InmuebleBuscadoController controlador = (InmuebleBuscadoController) cambiarmeAScene(InmuebleBuscadoController.URLVista);
		controlador.setCliente(clienteEnModificacion);
	}

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