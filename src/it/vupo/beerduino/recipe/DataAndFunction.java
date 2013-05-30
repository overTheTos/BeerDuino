package it.vupo.beerduino.recipe;



/**
 * Classe di supporto grafica per la creazione o il caricamente di una ricetta
 *
 * @author Alessandro Morniroli
 */
public class DataAndFunction extends javax.swing.JDialog {

    /**
     * Ricetta
     */
    private Recipe recipe;
    /**
     * Booleano per la conferma della ricetta
     */
    private Boolean check;

    /**
     * Costruttore di default
     *
     * @param parent jframe parent
     * @param modal true per farlo rimanere in primo piano, false altrimenti
     */
    public DataAndFunction(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.recipe = null;
        this.check = false;
    }

    /***********************
     *                     *
     * Metodi get() & set()*
     *                     *
     ***********************/
    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }
}
