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
 * Classe per la gestione dei luppoli della ricetta (modifica da HobbyBrew)
 *
 * @author Alessandro Morniroli
 */
public class Hop extends DataAndFunction implements Comparable<Hop> {

    /**
     * AA%
     */
    private double aa;
    /**
     * Tipo luppolo
     */
    private String type;
    /**
     * Minutaggio
     */
    private int minutes;

    /**
     * Costruttore di default
     */
    public Hop() {
        super();
        this.aa = 0;
        this.type = null;
    }

    /**
     * Costruttore secondario
     *
     * @param name nome del luppolo
     * @param aa alfa acidi del luppolo
     * @param grams grammi di luppolo
     * @param type tipo di luppolo
     */
    public Hop(String name, double aa, int grams, String type) {
        super.setName(name);
        super.setGrams(grams);
        this.setMinutes(0);
        this.aa = aa;
        this.type = type;
    }

    /**
     * Costruttore secondario
     *
     * @param name nome del luppolo
     * @param min minutaggio dello step
     * @param aa alfa acidi del luppolo
     * @param grams grammi di luppolo
     * @param type tipo di luppolo
     */
    public Hop(String name, int min, double aa, int grams, String type) {
        super.setName(name);
        this.setMinutes(min);
        super.setGrams(grams);
        this.aa = aa;
        this.type = type;
    }

    /***********************
     *                     *
     * Metodi get() & set()*
     *                     *
     ***********************/
    public double getAa() {
        return aa;
    }

    public void setAa(double aa) {
        this.aa = aa;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int compareTo(Hop t) {
        return this.minutes + t.minutes;
    }
}
