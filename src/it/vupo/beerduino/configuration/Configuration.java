package it.vupo.beerduino.configuration;

/**
 * Classe per la modifica di alcuni parametri di base da configurare, quali la isteresi termica,
 * la temperatura di sparge desiderata da tenere costante, e la temperatura di boil da raggiungere prima di
 * far partire il timer. Se non si imposta niente all'inizio della cotta, i valori di default sono 0 per la isteresi,
 * 90 per la temperatura di boil e 78 per quella di sparge
 *
 * @author Alessandro Morniroli
 */
public class Configuration {

    /**
     * Attributo per l'isteresi termica
     */
    private double isteresi;
    /**
     * Attributo per la temperatura di boil da raggiungere prima di far partire il timer
     */
    private int boilTemp;
    /**
     * Attributo per la temperatura di sparge da tenere costante
     */
    private int sparegTemp;

    /**
     * Costruttore default
     */
    public Configuration() {
        this.isteresi = 0;
        this.boilTemp = 90;
        this.sparegTemp = 78;
    }

    /***********************
     *                     *
     * Metodi get() & set()*
     *                     *
     ***********************/
    public int getBoilTemp() {
        return boilTemp;
    }

    public void setBoilTemp(int boilTemp) {
        this.boilTemp = boilTemp;
    }

    public int getSparegTemp() {
        return sparegTemp;
    }

    public void setSparegTemp(int sparegTemp) {
        this.sparegTemp = sparegTemp;
    }

    public double getIsteresi() {
        return isteresi;
    }

    public void setIsteresi(double isteresi) {
        this.isteresi = isteresi;
    }
}
