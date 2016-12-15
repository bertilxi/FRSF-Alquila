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
package app.datos.clases;

public enum TipoInmuebleStr {
    LOCAL //local-oficina
    , CASA //casa
    , DEPARTAMENTO //departamento
    , TERRENO //terreno
    , QUINTA //quinta,
    , GALPON; //galpón

    @Override
    public String toString() {
        switch (this) {
            case LOCAL:
                return "Local";
            case CASA:
                return "Casa";
            case DEPARTAMENTO:
                return "Departamento";
            case TERRENO:
                return "Terreno";
            case QUINTA:
                return "Quinta";
            case GALPON:
                return "Galpón";
        }
        return null;
    }
}
