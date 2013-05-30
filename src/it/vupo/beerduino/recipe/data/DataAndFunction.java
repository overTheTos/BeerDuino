package it.vupo.beerduino.recipe.data;

/**
 * Classe astratta di supporto alla classe Recipe
 *
 * @author Alessandro Morniroli
 */
public abstract class DataAndFunction {

    /**
     * Nome generico dell'ingrediente
     */
    private String name;
    /**
     * Grammi dell'ingrediente
     */
    private int grams;

    /**
     * Costruttore di default
     */
    public DataAndFunction() {
        this.name = null;
        this.grams = 0;
    }

    /***********************
     *                     *
     * Metodi get() & set()*
     *                     *
     ***********************/
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGrams() {
        return grams;
    }

    public void setGrams(int grams) {
        this.grams = grams;
    }
}
