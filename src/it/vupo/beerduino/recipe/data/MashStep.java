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

import it.vupo.beerduino.recipe.Recipe;

import org.jdom.Element;


/**
 * Classe modificata da Hobbybrew, necessaria per la creazione dei vari step di mash per
 * automatizzare il tutto
 * 
 * @author Alessandro Morniroli
 */
public class MashStep implements Comparable<MashStep> {

    /**
     * Attributo necessario per lettura della ricetta
     */
    private Integer startTemp;
    /**
     * Attributo necessario per lettura della ricetta
     */
    private Integer start;
    /**
     * Attributo necessario per lettura della ricetta (temperatura da tenere costante in fase di mash per
     * un determinato step)
     */
    private Integer endTemp;
    /**
     * Attributo necessario per la lettura della ricetta (durata in minuti di uno step)
     */
    private Integer length;
    /**
     * Attributo necessario per la lettura della ricetta
     */
    private Integer ramp;
    /**
     * Nome dello step
     */
    private String nome;
    /**
     * Ricetta
     */
    private Recipe recipe;
    /**
     * Indica se questo al termine di questo step è necessario eseguire il test dello iodio (dopo lo step saccharification)
     */
    private boolean iodio;

    /**
     * Costruttore di default
     */
    public MashStep() {
        setNome("Step non definito");
        this.startTemp = 0;
        this.start = 0;
        this.endTemp = 0;
        this.length = 0;
        this.ramp = 0;
        this.nome = "";
        this.iodio = false;
    }

    /**
     * Costruttore sceondario
     *
     * @param recipe ricetta dove salvare i dati
     */
    public MashStep(Recipe recipe) {
        this();
        this.setRecipe(recipe);
        this.iodio = false;
    }

    /***********************
     *                     *
     * Metodi get() & set()*
     *                     *
     ***********************/
    public Integer getStartTemp() {
        return this.startTemp;
    }

    public void setStartTemp(Integer startTemp) {
        this.startTemp = startTemp != null ? startTemp : new Integer(0);
    }

    public Integer getEndTemp() {
        return this.endTemp;
    }

    public void setEndTemp(Integer endTemp) {
        this.endTemp = endTemp != null ? endTemp : new Integer(0);
    }

    public Integer getLength() {
        return this.length;
    }

    public void setLength(Integer length) {
        this.length = length != null ? length : new Integer(1);
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getRamp() {
        return this.ramp;
    }

    public void setRamp(Integer ramp) {
        this.ramp = ramp != null ? ramp : new Integer(5);
    }

    public Integer getStart() {
        return this.start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public int compareTo(MashStep t) {
        return this.endTemp - t.endTemp;
    }

    public boolean isIodio() {
        return iodio;
    }

    public void setIodio(boolean iodio) {
        this.iodio = iodio;
    }
}
