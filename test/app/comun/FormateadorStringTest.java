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
package app.comun;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class FormateadorStringTest {

	@Test
	@Parameters({ "Juan,Juan",
			"Juan Pablo,Juan Pablo",
			",",
			"abc,Abc",
			"abc de abc,Abc de Abc",
			"a,A",
			" ,",
			"a a,A a",
			"Sadf 123 - Piso -  Dpto. - ,Sadf 123 - Piso - Dpto. -" })
	public void nombrePropioTest(String nombre, String esperado) {
		assertEquals(esperado, new FormateadorString().nombrePropio(nombre));
	}
}
