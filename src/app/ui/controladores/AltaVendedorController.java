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

	private String nombre;
	private String apellido;
	private String numeroDocumento;
	private String password1;
	private String password2;
	private TipoDocumento tipoDoc;

	/**
	 * Acción que se ejecuta al apretar el botón aceptar.
	 *
	 * Valida que se hayan insertado datos, los carga al vendedor y deriva la operación a capa lógica.
	 * Si la capa lógica retorna errores, éstos se muestran al usuario.
	 */
	public void acceptAction() {
		//Se toman los datos ingresados por el usuario
		cargarDatos();

		//Se validan los datos ingresados por el usuario
		StringBuilder error = new StringBuilder("");
		validarVistaAltaVendedor(error);

		//Si hay errores, se los muestra en pantalla, si no, se llama a crear un vendedor en la capa lógica
		if(!error.toString().isEmpty()){
			presentador.presentarError("Revise sus campos", error.toString(), stage);
		}
		else{
			crearVendedor();
		}
	}

	/**
	 * Método que encapsula el llamado de crear un vendedor a la capa lógica y maneja los errores devueltos
	 */
	private void crearVendedor() {
		//Se crea el vendedor a pasarle a la capa lógica
		Vendedor vendedor = new Vendedor();
		vendedor.setNombre(nombre)
				.setApellido(apellido)
				.setNumeroDocumento(numeroDocumento)
				.setTipoDocumento(tipoDoc)
				.setSalt(encriptador.generarSal())
				.setPassword(encriptador.encriptar(password1.toCharArray(), vendedor.getSalt()));

		ResultadoCrearVendedor resultadoCrearVendedor = null;

		try{
			//Se llama a crear un vendedor en la capa lógica
			resultadoCrearVendedor = coordinador.crearVendedor(vendedor);
			StringBuilder error = new StringBuilder("");
			List<ErrorCrearVendedor> listaErrores = resultadoCrearVendedor.getErrores();

			//Se crea un mensaje apropiado a mostrar según el error recibido
			parsearErroresLogica(error, listaErrores);

			//Si hay errores, se los muestra en pantalla, si no, se presenta una notificación indicando el éxito de la operación
			if(!error.toString().isEmpty()){
				presentador.presentarError("Revise sus campos", error.toString(), stage);
			}
			else{
				presentador.presentarToast("Se ha creado el vendedor con éxito", stage);
				salir();
			}
			//Se manejan las excepciones que puede devolver la lógica
		} catch(PersistenciaException e){
			presentador.presentarExcepcion(e, stage);
		} catch(EntidadExistenteConEstadoBajaException e){
			//Si el vendedor ya existía pero fue dado de baja se debe mostrar una ventana preguntando al usuario si desea darlo de alta
			manejarVendedorExistenteBaja(vendedor);
		} catch(Exception e){
			presentador.presentarExcepcionInesperada(e, stage); //falta el stage
		}
	}

	/**
	 * Método que se llama al querer dar de alta un vendedor que fue dado de baja previamente
	 * Si el usuario confirma la operación se llama a la pantalla modificar vendedor
	 *
	 * @param vendedor
	 */
	private void manejarVendedorExistenteBaja(Vendedor vendedor) {
		VentanaConfirmacion ventana = presentador.presentarConfirmacion("El vendedor ya existe", "El vendedor está dado de baja. Si continúa podrá darlo de alta nuevamente. ¿Desea continuar?", stage);
		if(ventana.acepta()){
			//Si el usuario acepta se llama a la pantalla modificar vendedor
			try{
				vendedor = coordinador.obtenerVendedor(vendedor);
			} catch(PersistenciaException e1){
				presentador.presentarExcepcion(e1, stage);
			}
			ModificarVendedorController controlador = (ModificarVendedorController) cambiarmeAScene(ModificarVendedorController.URLVista, URLVistaRetorno);
			controlador.setVendedor(vendedor);
			controlador.setAltaVendedor();
		}
	}

	/**
	 * Se convierten los errores devueltos por la capa lógica a errores a mostrar al usuario
	 *
	 * @param error
	 *            Texto a mostrar al usuario
	 * @param listaErrores
	 *            Lista de errores de la capa lógica
	 */
	private void parsearErroresLogica(StringBuilder error, List<ErrorCrearVendedor> listaErrores) {
		for(ErrorCrearVendedor errorCrearVendedor: listaErrores){
			switch(errorCrearVendedor) {
			case Formato_Apellido_Incorrecto:
				error.append("Apellido Incorrecto").append("\r\n");
				break;
			case Formato_Documento_Incorrecto:
				error.append("Documento Incorrecto").append("\r\n");
				break;
			case Formato_Nombre_Incorrecto:
				error.append("Nombre Incorrecto").append("\r\n");
				break;
			case Ya_Existe_Vendedor:
				error.append("Ya existe un vendedor registrado con ese documento").append("\r\n");
				break;
			}
		}
	}

	/**
	 * Se valida que los campos ingresados por el usuario sean correctos
	 *
	 * @param error
	 *            Texto a mostrar en caso de que haya algún error
	 */
	private void validarVistaAltaVendedor(StringBuilder error) {
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
	}

	/**
	 * Se cargan los datos de la vista a variables para su posterior utilización
	 */
	private void cargarDatos() {
		nombre = textFieldNombre.getText().trim();
		apellido = textFieldApellido.getText().trim();
		numeroDocumento = textFieldNumeroDocumento.getText().trim();
		password1 = passwordFieldContraseña.getText();
		password2 = passwordFieldRepiteContraseña.getText();
		tipoDoc = comboBoxTipoDocumento.getValue();
	}

	/**
	 * Acción que se ejecuta al presionar el botón cancelar.
	 * Se vuelve a la pantalla desde la que se llamó a AltaVendedor.
	 */
	public void cancelAction(ActionEvent event) {
		salir();
	}

	@Override
	public void salir() {
		if(URLVistaRetorno != null){
			if(URLVistaRetorno.equals(LoginController.URLVista)){
				cambiarmeAScene(URLVistaRetorno, true);
			}
			else{
				cambiarmeAScene(URLVistaRetorno);
			}
		}
		else{
			super.salir();
		}
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
