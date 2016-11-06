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
import java.util.List;
import java.util.ResourceBundle;

import app.comun.EncriptadorPassword;
import app.datos.entidades.TipoDocumento;
import app.datos.entidades.Vendedor;
import app.excepciones.PersistenciaException;
import app.logica.resultados.ResultadoModificarVendedor;
import app.logica.resultados.ResultadoModificarVendedor.ErrorModificarVendedor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ModificarVendedorController extends OlimpoController {

	public static final String URLVista = "/app/ui/vistas/modificarVendedor.fxml";

	@FXML
	private TextField textFieldNombre;
	@FXML
	private TextField textFieldApellido;
	@FXML
	private TextField textFieldNumeroDocumento;
	@FXML
	private PasswordField passwordFieldContraseñaAntigua;
	@FXML
	private PasswordField passwordFieldContraseñaNueva;
	@FXML
	private PasswordField passwordFieldRepiteContraseña;
	@FXML
	private CheckBox checkBoxCambiarContraseña;
	@FXML
	private Label labelContraseñaAntigua;
	@FXML
	private Label labelContraseñaNueva;
	@FXML
	private Label labelRepiteContraseña;
	@FXML
	private ComboBox<TipoDocumento> comboBoxTipoDocumento;
	@FXML
	private Button acceptButton;
	@FXML
	private Button cancelButton;

	private Vendedor vendedor;

	private EncriptadorPassword encriptador = new EncriptadorPassword();

	public void acceptAction() {

		StringBuilder error = new StringBuilder("");

		String nombre = textFieldNombre.getText().trim();
		String apellido = textFieldApellido.getText().trim();
		String numeroDocumento = textFieldNumeroDocumento.getText().trim();
		String passwordAntigua = passwordFieldContraseñaAntigua.getText();
		String password1 = passwordFieldContraseñaNueva.getText();
		String password2 = passwordFieldRepiteContraseña.getText();
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

		if(checkBoxCambiarContraseña.isSelected()){
			if(passwordAntigua.isEmpty()){
				error.append("Ingrese su antigua contraseña\r\n");
			}
			if(!encriptador.encriptar(passwordAntigua.toCharArray(), vendedor.getSalt()).equals(vendedor.getPassword()) && !passwordAntigua.isEmpty()){
				error.append("Su contraseña antigua es incorrecta\r\n");
			}
			if(password1.isEmpty() && password2.isEmpty()){
				error.append("Ingrese su nueva contraseña").append("\r\n");
			}
			if(!password1.equals(password2)){
				error.append("Sus nuevas contraseñas no coinciden. Ingréselas nuevamente").append("\r\n");
			}
		}

		if(!error.toString().isEmpty()){
			presentador.presentarError("Revise sus campos", error.toString(), stage);
		}
		else{
			vendedor.setNombre(nombre)
					.setApellido(apellido)
					.setNumeroDocumento(numeroDocumento)
					.setTipoDocumento(tipoDoc);
			if(checkBoxCambiarContraseña.isSelected()){
				vendedor.setPassword(encriptador.encriptar(passwordFieldContraseñaNueva.getText().toCharArray(), vendedor.getSalt()));
			}

			ResultadoModificarVendedor resultadoModificarVendedor = null;

			try{
				resultadoModificarVendedor = coordinador.modificarVendedor(vendedor);
				error.delete(0, error.length());
				List<ErrorModificarVendedor> listaErrores = resultadoModificarVendedor.getErrores();
				if(listaErrores.contains(ErrorModificarVendedor.Formato_Nombre_Incorrecto)){
					error.append("Nombre Incorrecto").append("\r\n");
				}
				if(listaErrores.contains(ErrorModificarVendedor.Formato_Apellido_Incorrecto)){
					error.append("Apellido Incorrecto").append("\r\n");
				}
				if(listaErrores.contains(ErrorModificarVendedor.Formato_Documento_Incorrecto)){
					error.append("Documento Incorrecto").append("\r\n");
				}
				if(listaErrores.contains(ErrorModificarVendedor.Otro_Vendedor_Posee_Mismo_Documento_Y_Tipo)){
					error.append("Ya existe otro vendedor registrado con ese documento").append("\r\n");
				}

				if(!error.toString().isEmpty()){
					presentador.presentarError("Revise sus campos", error.toString(), stage);
				}
				else{
					cambiarmeAScene(AdministrarVendedorController.URLVista);
				}

			} catch(PersistenciaException e){
				presentador.presentarExcepcion(e, stage);
			} catch(Exception e){
				presentador.presentarExcepcionInesperada(e, stage);
			}
		}

	}

	public void cancelAction(ActionEvent event) {
		cambiarmeAScene(AdministrarVendedorController.URLVista);
	}

	public void checkBoxAction() {
		if(checkBoxCambiarContraseña.isSelected()){
			labelContraseñaAntigua.setDisable(false);
			labelContraseñaNueva.setDisable(false);
			labelRepiteContraseña.setDisable(false);
			passwordFieldContraseñaAntigua.setDisable(false);
			passwordFieldContraseñaNueva.setDisable(false);
			passwordFieldRepiteContraseña.setDisable(false);
		}
		else{
			labelContraseñaAntigua.setDisable(true);
			labelContraseñaNueva.setDisable(true);
			labelRepiteContraseña.setDisable(true);
			passwordFieldContraseñaAntigua.setDisable(true);
			passwordFieldContraseñaNueva.setDisable(true);
			passwordFieldRepiteContraseña.setDisable(true);
		}
	}

	@Override
	public void inicializar(URL location, ResourceBundle resources) {
		try{
			comboBoxTipoDocumento.getItems().addAll(coordinador.obtenerTiposDeDocumento());
		} catch(PersistenciaException e){
			presentador.presentarExcepcion(e, stage);
		}
		labelContraseñaAntigua.setDisable(true);
		labelContraseñaNueva.setDisable(true);
		labelRepiteContraseña.setDisable(true);
		passwordFieldContraseñaAntigua.setDisable(true);
		passwordFieldContraseñaNueva.setDisable(true);
		passwordFieldRepiteContraseña.setDisable(true);
		checkBoxCambiarContraseña.setSelected(false);
	}

	public void setVendedor(Vendedor vendedor) {
		this.setTitulo("Modificar vendedor");
		this.vendedor = vendedor;
		textFieldNombre.setText(vendedor.getNombre());
		textFieldApellido.setText(vendedor.getApellido());
		textFieldNumeroDocumento.setText(vendedor.getNumeroDocumento());
		comboBoxTipoDocumento.getSelectionModel().select(vendedor.getTipoDocumento());
	}
}
