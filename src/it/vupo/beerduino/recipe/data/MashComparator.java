
package it.vupo.beerduino.recipe.data;

import java.util.Comparator;

/**
 * Classe per la comparazione dei minuti dei vari luppoli. Necessario per riordinare
 * in modo corretto l'ordine e degli step di boil.
 *
 * @author Alessandro Morniroli
 */
public class MashComparator implements Comparator {

    /**
     * Comparazione tra due interi
     * @param t1 oggetto malto da comparare
     * @param t2 oggetto malto da comparare
     * @return 1 se minuti di m1 maggiore di minuti di m2, 0 altrimenti
     */
    public int compare(Object t1, Object t2) {
        MashStep m1 = (MashStep) t1;
        MashStep m2 = (MashStep) t2;

        if(m1.getEndTemp() > m2.getEndTemp())
            return 1;
        else
            return 0;
    }

}
