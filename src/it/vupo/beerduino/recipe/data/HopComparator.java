package it.vupo.beerduino.recipe.data;

import java.util.Comparator;

/**
 * Classe per la comparazione dei minuti dei vari luppoli. Necessario per riordinare
 * in modo corretto l'ordine e degli step di boil.
 *
 * @author Alessandro Morniroli
 */
public class HopComparator implements Comparator {

    /**
     * Comparazione tra due interi
     * @param t1 oggetto luppolo da comparare
     * @param t2 oggetto luppolo da comparare
     * @return 1 se minuti di h1 minore di minuti di h2, 0 altrimenti
     */
    public int compare(Object t1, Object t2) {
        Hop h1 = (Hop) t1;
        Hop h2 = (Hop) t2;

        if(h1.getMinutes() < h2.getMinutes())
            return 1;
        else
            return 0;
    }
}
