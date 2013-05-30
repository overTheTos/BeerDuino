package it.vupo.beerduino.thread;

import org.jfree.chart.plot.JThermometer;

/**
 * Classe per la gestione delle temperature nelle pentole
 *
 * @author Alessandro Morniroli
 */
public class Temperature {

    /**
     * Temperatura della pentola di mash
     */
    private double mashTemp;
    /**
     * Temperatura della pentola di sparge
     */
    private double spargeTemp;
    /**
     * Temperature della pentola di boil
     */
    private double boilTemp;
    /**
     * Oggetto termometro per la pentola di mash
     */
    private JThermometer thermoMash;
    /**
     * Oggetto termometro per la pentola di sparge
     */
    private JThermometer thermoSparge;
    /**
     * Oggetto termometro per la pentola di boil
     */
    private JThermometer thermoBoil;

    /**
     * Costruttore di default
     */
    public Temperature() {
        this.mashTemp = 0;
        this.spargeTemp = 0;
        this.boilTemp = 0;
        this.thermoMash = null;
        this.thermoSparge = null;
        this.thermoBoil = null;
    }

    /***********************
     *                     *
     * Metodi get() & set()*
     *                     *
     ***********************/
    public double getBoilTemp() {
        return boilTemp;
    }

    public void setBoilTemp(double boilTemp) {
        this.boilTemp = boilTemp;
    }

    public double getMashTemp() {
        return mashTemp;
    }

    public void setMashTemp(double mashTemp) {
        this.mashTemp = mashTemp;
    }

    public double getSpargeTemp() {
        return spargeTemp;
    }

    public void setSpargeTemp(double spargeTemp) {
        this.spargeTemp = spargeTemp;
    }

    public JThermometer getThermoBoil() {
        return thermoBoil;
    }

    public void setThermoBoil(JThermometer thermoBoil) {
        this.thermoBoil = thermoBoil;
    }

    public JThermometer getThermoMash() {
        return thermoMash;
    }

    public void setThermoMash(JThermometer thermoMash) {
        this.thermoMash = thermoMash;
    }

    public JThermometer getThermoSparge() {
        return thermoSparge;
    }

    public void setThermoSparge(JThermometer thermoSparge) {
        this.thermoSparge = thermoSparge;
    }
}
