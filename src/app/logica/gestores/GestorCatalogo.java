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
package app.logica.gestores;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import app.datos.clases.CatalogoVista;
import app.datos.entidades.PDF;
import app.excepciones.GestionException;
import app.excepciones.PersistenciaException;
import app.logica.resultados.ResultadoCrearCatalogo;
import app.logica.resultados.ResultadoCrearCatalogo.ErrorCrearCatalogo;

/**
 * Clase que se encarga de generar un catalogo
 */
@Service
public class GestorCatalogo {

	@Resource
	GestorPDF gestorPDF;

	/**
	 * Se encarga de validar los datos de un catalogoVista a crear y, en caso de que no haya errores,
	 * delegar la generación del archivo pdf al gestorPDF
	 *	 *
	 * @param catalogoVista
	 *           clase con los datos necesarios para generar un catálogo
	 * @return un resultado informando errores correspondientes en caso de que los haya
	 *
	 * @throws GestionException
	 *             se lanza una excepción GenerarPDFException si ocurre un error al generar el PDF
	 */
	public ResultadoCrearCatalogo crearCatalogo(CatalogoVista catalogoVista) throws GestionException {
		ArrayList<ErrorCrearCatalogo> errores = new ArrayList<>();
		PDF catalogoPDF = null;

		if(catalogoVista.getCliente() == null){
			errores.add(ErrorCrearCatalogo.Cliente_inexistente);
		}

		catalogoVista.getFotos().forEach((i, f) -> {
			if(i.getId() == null){
				errores.add(ErrorCrearCatalogo.Codigo_Inmueble_Inexistente);
			}

			if(i.getTipo() == null){
				errores.add(ErrorCrearCatalogo.Tipo_Inmueble_Inexistente);
			}

			if(i.getDireccion() == null){
				errores.add(ErrorCrearCatalogo.Direccion_Inmueble_Inexistente);
			}

			if(i.getDireccion() != null && i.getDireccion().getLocalidad() == null){
				errores.add(ErrorCrearCatalogo.Localidad_Inmueble_Inexistente);
			}

			if(i.getDireccion() != null && i.getDireccion().getBarrio() == null){
				errores.add(ErrorCrearCatalogo.Barrio_Inmueble_Inexistente);
			}

			if(i.getPrecio() == null){
				errores.add(ErrorCrearCatalogo.Precio_Inmueble_Inexistente);
			}
		});

		if(errores.isEmpty()){
			catalogoPDF = gestorPDF.generarPDF(catalogoVista);
		}

		return new ResultadoCrearCatalogo(catalogoPDF, errores.toArray(new ErrorCrearCatalogo[0]));
	}
}
