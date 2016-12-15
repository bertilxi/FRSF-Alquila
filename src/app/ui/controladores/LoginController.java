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
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import app.datos.clases.DatosLogin;
import app.datos.entidades.TipoDocumento;
import app.excepciones.PersistenciaException;
import app.logica.resultados.ResultadoAutenticacion;
import app.logica.resultados.ResultadoAutenticacion.ErrorAutenticacion;
import app.ui.controladores.resultado.ResultadoControlador;
import app.ui.controladores.resultado.ResultadoControlador.ErrorControlador;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Controlador de la vista de login que se encarga de manejar el ingreso de un vendedor al sistema
 * Pertenece a la taskcard 1 de la iteración 1 y a la historia 1
 */
public class LoginController extends OlimpoController {

	public static final String URLVista = "/app/ui/vistas/login.fxml";

	@FXML
	protected TextField tfNumeroDocumento;

	@FXML
	protected PasswordField pfContra;

	@FXML
	protected ComboBox<TipoDocumento> cbTipoDocumento;

	@FXML
	public void registrar() {
		cambiarmeAScene(AltaVendedorController.URLVista, URLVista, true);
	}

	@FXML
	/**
	 * Método que permite a un vendedor entrar al sistema.
	 * Pertenece a la taskcard 1 de la iteración 1 y a la historia 1
	 *
	 * @return ResultadoControlador que resume lo que hizo el controlador
	 */
	public ResultadoControlador ingresar() {
		Set<ErrorControlador> erroresControlador = new HashSet<>();
		ResultadoAutenticacion resultado = null;
		Boolean hayErrores;
		DatosLogin datos;
		String errores = "";

		//Toma de datos de la vista
		TipoDocumento tipoDocumento = cbTipoDocumento.getValue();
		String dni = tfNumeroDocumento.getText().trim();
		char[] pass = pfContra.getText().toCharArray();

		if(tipoDocumento == null || dni.isEmpty() || pass.length < 1){
			presentador.presentarError("No se ha podido iniciar sesión", "Campos vacíos.", stage);
			return new ResultadoControlador(ErrorControlador.Campos_Vacios);
		}
		datos = new DatosLogin(tipoDocumento, dni, pass);

		//Inicio transacción al gestor
		try{
			resultado = coordinador.autenticarVendedor(datos);
		} catch(PersistenciaException e){
			presentador.presentarExcepcion(e, stage);
			return new ResultadoControlador(ErrorControlador.Error_Persistencia);
		} catch(Exception e){
			presentador.presentarExcepcionInesperada(e, stage);
			return new ResultadoControlador(ErrorControlador.Error_Desconocido);
		}

		//Tratamiento de errores
		hayErrores = resultado.hayErrores();
		if(hayErrores){
			for(ErrorAutenticacion r: resultado.getErrores()){
				switch(r) {
				case Datos_Incorrectos:
					errores += "Datos inválidos al iniciar sesión.\n";
					erroresControlador.add(ErrorControlador.Datos_Incorrectos);
					break;
				}
			}

			if(!errores.isEmpty()){
				presentador.presentarError("No se ha podido iniciar sesión", errores, stage);
			}
		}
		else{
			BaseController siguientePantalla = (BaseController) cambiarmeAScene(BaseController.URLVista, URLVista, true);
			siguientePantalla.formatearConVendedor(resultado.getVendedorLogueado());
		}
		return new ResultadoControlador(erroresControlador.toArray(new ErrorControlador[0]));
	}

	@Override
	public void inicializar(URL location, ResourceBundle resources) {
		setTitulo("Ingrese al sistema");
		try{
			cbTipoDocumento.getItems().addAll(coordinador.obtenerTiposDeDocumento());
		} catch(PersistenciaException e){
			presentador.presentarExcepcion(e, stage);
		} catch(Exception e){
			presentador.presentarExcepcionInesperada(e, stage);
		}

		stage.centerOnScreen();
	}

	@FXML
	private void handleKeyPressedLogin(KeyEvent e) {
		if(e.getEventType()==KeyEvent.KEY_PRESSED){
			if(e.getCode()==KeyCode.ENTER) {
				ingresar();
			}
    	}
	}

}
