package it.vupo.beerduino.recipe;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;


/**
 * Classe per il caricamento di una ricetta (di promash). Viene creato un pannello grafico
 * in cui è possibile recupeare il percorso della ricetta da caricare
 *
 * @author Alessandro Morniroli
 */
public class Load extends JFrame {

    /**
     * Costruttore di default
     * @param recipe ricetta da caricare (il risultato del caricamento viene salvato in questo attributo)
     * 
     * @throws Exception
     */
    public Load(Recipe recipe) throws Exception {
        JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(Load.this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            recipe.readRec(fc.getSelectedFile().toString());
            new Confirm(new JFrame(), true, recipe).setVisible(true);
        } else {
            recipe.setCheck(false);
        }
    }
}
