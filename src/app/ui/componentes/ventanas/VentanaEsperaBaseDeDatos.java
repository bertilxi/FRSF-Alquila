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
package app.ui.componentes.ventanas;

import app.ui.componentes.IconoAnimadoEspera;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class VentanaEsperaBaseDeDatos extends CustomAlert {

	protected VentanaEsperaBaseDeDatos() {
		this(null);
	}

	public VentanaEsperaBaseDeDatos(Window padre) {
		super(AlertType.NONE);
		this.initStyle(StageStyle.UNDECORATED);
		if(padre != null){
			this.initOwner(padre);
		}
		this.setGraphic(new ImageView(new IconoAnimadoEspera()));
		this.setContentText("Esperando a la base de datos...");
		this.setTitle("Esperando");
		((Label) this.getDialogPane().getChildren().get(1)).setAlignment(Pos.CENTER);
		((Label) this.getDialogPane().getChildren().get(1)).setFont(new Font(20));
	}
}
