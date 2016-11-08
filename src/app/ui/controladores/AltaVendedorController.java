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
import app.excepciones.EntidadExistenteConEstadoBajaException;
import app.excepciones.GestionException;
import app.excepciones.PersistenciaException;
import app.logica.resultados.ResultadoCrearVendedor;
import app.logica.resultados.ResultadoCrearVendedor.ErrorCrearVendedor;
import app.ui.componentes.ventanas.VentanaConfirmacion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Controlador de la vista que crea un vendedor
 */
public class AltaVendedorController extends OlimpoController {

	public static final String URLVista = "/app/ui/vistas/altaVendedor.fxml";

	@FXML
	protected TextField textFieldNombre;
	@FXML
	protected TextField textFieldApellido;
	@FXML
	protected TextField textFieldNumeroDocumento;
	@FXML
	protected PasswordField passwordFieldContraseña;
	@FXML
	protected PasswordField passwordFieldRepiteContraseña;
	@FXML
	protected ComboBox<TipoDocumento> comboBoxTipoDocumento;
	@FXML
	private Button acceptButton;
	@FXML
	private Button cancelButton;

	private EncriptadorPassword encriptador = new EncriptadorPassword();

	/**
	 * Acción que se ejecuta al apretar el botón aceptar.
	 *
	 * Valida que se hayan insertado datos, los carga al vendedor y deriva la operación a capa lógica.
	 * Si la capa lógica retorna errores, éstos se muestran al usuario.
	 */
	public void acceptAction() throws PersistenciaException, GestionException {

		StringBuilder error = new StringBuilder("");

		String nombre = textFieldNombre.getText().trim();
		String apellido = textFieldApellido.getText().trim();
		String numeroDocumento = textFieldNumeroDocumento.getText().trim();
		String password1 = passwordFieldContraseña.getText();
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

		if(password1.isEmpty() && password2.isEmpty()){
			error.append("Inserte su contraseña").append("\r\n");
		}

		if(!password1.isEmpty() && password2.isEmpty()){
			error.append("Inserte su contraseña nuevamente").append("\r\n");
		}

		if(!password1.equals(password2)){
			error.append("Sus contraseñas no coinciden, Ingreselas nuevamente").append("\r\n ");
		}

		if(!error.toString().isEmpty()){
			presentador.presentarError("Revise sus campos", error.toString(), stage);
		}
		else{
			Vendedor vendedor = new Vendedor();
			vendedor.setNombre(nombre)
					.setApellido(apellido)
					.setNumeroDocumento(numeroDocumento)
					.setTipoDocumento(tipoDoc)
					.setSalt(encriptador.generarSal())
					.setPassword(encriptador.encriptar(password1.toCharArray(), vendedor.getSalt()));

			ResultadoCrearVendedor resultadoCrearVendedor = null;

			try{
				resultadoCrearVendedor = coordinador.crearVendedor(vendedor);
				error.delete(0, error.length());
				List<ErrorCrearVendedor> listaErrores = resultadoCrearVendedor.getErrores();
				if(listaErrores.contains(ErrorCrearVendedor.Formato_Nombre_Incorrecto)){
					error.append("Nombre Incorrecto").append("\r\n");
				}
				if(listaErrores.contains(ErrorCrearVendedor.Formato_Apellido_Incorrecto)){
					error.append("Apellido Incorrecto").append("\r\n");
				}
				if(listaErrores.contains(ErrorCrearVendedor.Formato_Documento_Incorrecto)){
					error.append("Documento Incorrecto").append("\r\n");
				}
				if(listaErrores.contains(ErrorCrearVendedor.Ya_Existe_Vendedor)){
					error.append("Ya existe un vendedor registrado con ese documento").append("\r\n");
				}

				if(!error.toString().isEmpty()){
					presentador.presentarError("Revise sus campos", error.toString(), stage);
				}
				else{
					presentador.presentarToast("Se ha creado un vendedor", stage);
					cambiarmeAScene(URLVistaRetorno);
				}

			} catch(PersistenciaException e){
				presentador.presentarExcepcion(e, stage);
			} catch(GestionException e){
				if(e.getClass().equals(EntidadExistenteConEstadoBajaException.class)){
					VentanaConfirmacion ventana = presentador.presentarConfirmacion("El vendedor ya existe", "El vendedor ya existía anteriormente pero fue dado de baja. ¿Desea continuar?", stage);
					if(ventana.acepta()){
						vendedor = coordinador.obtenerVendedor(vendedor);
						ModificarVendedorController modificarVendedorController = (ModificarVendedorController) cambiarmeAScene(ModificarVendedorController.URLVista, AdministrarVendedorController.URLVista);
						modificarVendedorController.setVendedor(vendedor);
					}
				}
			} catch(Exception e){
				presentador.presentarExcepcionInesperada(e, stage); //falta el stage
			}
		}
	}

	/**
	 * Acción que se ejecuta al presionar el botón cancelar.
	 * Se vuelve a la pantalla desde la que se llamó a AltaVendedor.
	 */
	public void cancelAction(ActionEvent event) {
		cambiarmeAScene(URLVistaRetorno);
	}

	@Override
	public void inicializar(URL location, ResourceBundle resources) {
		this.setTitulo("Nuevo vendedor");
		try{
			comboBoxTipoDocumento.getItems().addAll(coordinador.obtenerTiposDeDocumento());
		} catch(PersistenciaException e){
		}
	}
}
