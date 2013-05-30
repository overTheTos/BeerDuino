/*
 *  Copyright 2005, 2006, 2007 Alessandro Chiari.
 *
 *  This file is part of Hobbybrew.
 *
 *  Hobbybrew is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  Hobbybrew is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Hobbybrew; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
package it.vupo.beerduino.recipe.data;

/**
 * Classe per la gestione dei malti della ricetta (modifica da HobbyBrew)
 *
 * @author Alessandro Morniroli
 */
public class Malt extends DataAndFunction {

    /**
     * Costruttore di default
     */
    public Malt() {
        super();
    }

    /**
     * Costruttore secondario
     */
    public Malt(String name, int grams) {
        super.setName(name);
        super.setGrams(grams);
    }
}
