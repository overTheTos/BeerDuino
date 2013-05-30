package it.vupo.beerduino.thread;

import it.vupo.beerduino.alarm.Alarm;
import it.vupo.beerduino.recipe.Recipe;
import it.vupo.beerduino.timer.TimerPanel;

import java.awt.Color;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 * Classe e thread più importante di tutto il sistema. E' il responsabile del corretto funzionamento
 * della gestione automatica delle fasi di mash, sparge e boil. Confronta le temperatura rilevate con quelle salvate nella ricetta,
 * e a seconda se sono minori o maggiori accende e spegne i relays che comandano le elettrovalvole. Oltre a ciò, elabora in modo
 * automatico gli step da eseguire, avvisando l'utente qualora fosse necessario un intervento umano. Si
 * preoccupa anche dell'avanzamento del timer del pannello grafico timerPanel. E' una classe complicata,
 * che necessita di essere risistemata per essere più comprensibile e più efficiente.
 *
 * @author Alessandro Morniroli
 */
public class Timers extends Thread {

    /**
     * Cifra secondi dx
     */
    private int sec_right;
    /**
     * Cifra secondi sx
     */
    private int sec_left;
    /**
     * Cifra minuti dx
     */
    private int min_right;
    /**
     * Cifra minuti sx
     */
    private int min_left;
    /**
     * Cifra ore dx
     */
    private int hour_right;
    /**
     * Cifra ore middle
     */
    private int hour_mid;
    /**
     * Cifra ore sx
     */
    private int hour_left;
    /**
     * Timer grafico
     */
    private TimerPanel chrono;
    /**
     * Ricetta
     */
    private Recipe recipe;
    /**
     * Array contenente i vari pulsanti del sistema di controllo
     */
    private JButton[] list;
    /**
     * Stringa contenente il percorso dove sono salvate le immagini dei pulsanti
     */
    private String pathImages;
    /**
     * Tabella mash
     */
    private JTable mashTable;
    /**
     * Tabella sparge
     */
    private JTable boilTable;
    /**
     * Isteresi termica
     */
    private double isteresi;
    /**
     * Array contenente le configurazioni di isteresi, temp boil e temp sparge
     */
    private int[] configuration;
    /**
     * Punti orologio
     */
    private boolean dotsOn;
    /**
     * Matrice contenente dati riguardanti la sessione di mash
     */
    private int[][] arrMash;
    /**
     * Array contenente dati riguardanti la sessione di boil
     */
    private int[] arrBoil;
    /**
     * Variabile di appoggio per contare gli sep
     */
    private int count;
    /**
     * Boolean che indica se il mash è terminato
     */
    private boolean mashFinished;
    /**
     * Boolean che indica se la fase di boil è terminata
     */
    private boolean boilFinished;
    /**
     * Attributo per la lettura delle temperature
     */
    private Temperature temperature;
    /**
     * Buffer di ricezione
     */
    private Buffer tx;
    /**
     * Tipo di timer (0 per il mash, 1 per lo sparge)
     */
    private int type;
    /**
     * JCheckBox per il controllo del pulsante avvisiMash
     */
    private JCheckBox avvisiMash;
    /**
     * JCheckBox per il controllo del pulsante autoSparge
     */
    private JCheckBox autoSparge;
    /**
     * JCheckBox per il controllo del pulsante avvisiBoil
     */
    private JCheckBox avvisiBoil;
    /**
     * Step relativoa al test dello iodio
     */
    private int iodio;
    /**
     * Attributo per eseguire il suono di avviso (sirena)
     */
    private Alarm alarm;

    /**
     * Costruttore di default
     */
    public Timers() {
    }

    /**
     * Costruttore secondario (sparge)
     * @param t oggetto temperature per la lettura delle temperature
     * @param tx buffer di trasmissione
     * @param list array contenente i pulsanti del sistema di controllo
     * @param type variabile che mi indica se sto creando un timer per lo sparge (1) o per il mash (0)
     * @param autoSparge JCheckBox per controllare il valore del pulsante autoSparge se è attivo o meno
     * @param configuration attributi di configurazione (isteresi, boil temp e sparge temp)
     * @param isteresi isteresi (ridondante, da sistemare)
     * @param path percorso della cartella contenente le immagini dei pulsanti
     */
    public Timers(Temperature t, Buffer tx, JButton[] list, int type, JCheckBox autoSparge, int[] configuration, double isteresi, String path) {
        this.temperature = t;
        this.tx = tx;
        this.type = type;
        this.list = list;
        this.autoSparge = autoSparge;
        this.configuration = configuration;
        this.isteresi = isteresi;
        this.alarm = new Alarm(this.pathImages);
        this.pathImages = path;
    }

    /**
     * Costruttore secondario (mash)
     * @param chrono timer per gli step di mash e boil
     * @param recipe ricetta
     * @param list array contenente i pulsanti del sistema di controllo
     * @param mashTable tabella di mash
     * @param boilTable tabella di boiò
     * @param t oggetto temperature per la lettura delle temperature
     * @param tx buffer di trasmissione
     * @param type variabile che mi indica se sto creando un timer per lo sparge (1) o per il mash (0)
     * @param avvisiMash JCheckBox per controllare il valore del pulsante avvisiMash se è attivo o meno
     * @param avvisiBoil JCheckBox per controllare il valore del pulsante avvisiBoil se è attivo o meno
     * @param configuration attributi di configurazione (isteresi, boil temp e sparge temp)
     * @param isteresi isteresi (ridondante, da sistemare)
     * @param path percorso della cartella contenente le immagini dei pulsanti
     */
    public Timers(TimerPanel chrono, Recipe recipe, JButton[] list, JTable mashTable, JTable boilTable, Temperature t, Buffer tx, int type, JCheckBox avvisiMash, /*JCheckBox avvisiBoil,*/ int[] configuration, double isteresi, String path) {
        this.chrono = chrono;
        this.recipe = recipe;
        this.count = 0;
        this.mashFinished = false;
        this.boilFinished = false;
        this.list = list;
        this.mashTable = mashTable;
        this.boilTable = boilTable;
        this.temperature = t;
        this.createSession();
        this.tx = tx;
        this.type = type;
        this.avvisiMash = avvisiMash;
        //this.avvisiBoil = avvisiBoil;
        this.configuration = configuration;
        this.isteresi = isteresi;
        this.pathImages = path;
        this.alarm = new Alarm(this.pathImages);
    }

    /**
     * Crea gli array contenenti i dati per le sessioni di mash e boil (minuti e temperature
     * nel caso del mash, solo minuti nel caso di boil)
     */
    public void createSession() {
        this.arrMash = new int[this.recipe.getMash().size()][2];
        for (int i = 0; i < arrMash.length; i++) {
            this.arrMash[i][0] = this.recipe.getMash().get(i).getLength();
            this.arrMash[i][1] = this.recipe.getMash().get(i).getEndTemp();
            if (this.recipe.getMash().get(i).isIodio()) {
                this.iodio = i;
            }
        }

        this.arrBoil = new int[this.recipe.getHops().size()];
        for (int i = 0; i < arrBoil.length; i++) {
            this.arrBoil[i] = this.recipe.getHops().get(i).getMinutes();
        }
    }

    /**
     * Setta il timer impostando correttamente le ore, i minuti e i secondi
     *
     * @param min minuti da convertire in ore, minuti e secondi
     */
    public void setTimer(int min) {
        int temp = min * 60;
        int ore = temp / 3600;
        hour_right = ore;

        int remainder = temp % 3600;
        int minuti = remainder / 60;
        int secondi = remainder % 60;
        sec_left = secondi / 10;
        sec_right = secondi % 10;
        min_left = minuti / 10;
        min_right = minuti % 10;
        hour_mid = 0;
        hour_left = 0;
        this.chrono.setSec_left(sec_left);
        this.chrono.setSec_right(sec_right);
        this.chrono.setMin_left(min_left);
        this.chrono.setMin_right(min_right);
        this.chrono.setHour_left(0);
        this.chrono.setHour_mid(0);
        this.chrono.setHour_right(hour_right);
    }

    /**
     * Metodo run del thread. E' presente uno switch case, che mi indica quali funzioni svolgere
     * in base al fatto che l'attributo type sia 0 (funzioni relative alle fasi di mash) oppure 1 (funzioni
     * relative alle fasi di sparge)
     */
    public void run() {

        switch (this.type) {
            case 0:
                boolean chk = false;
                while (!boilFinished) {
                    while (!chk && !mashFinished) {
                        System.out.println("aspetto");
                        chk = this.checkMashTemperature();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Timers.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    this.checkMashTemperature();
                    this.chrono.setDotsOn(!this.chrono.isDotsOn());
                    if (this.chrono.isDotsOn()) {
                        this.chrono.setSec_right(this.chrono.getSec_right() - 1);
                    }

                    if (this.chrono.getSec_right() == -1) {
                        this.chrono.setSec_right(9);
                        this.chrono.setSec_left(this.chrono.getSec_left() - 1);
                    }

                    if (this.chrono.getSec_left() == -1) {
                        this.chrono.setSec_left(5);
                        this.chrono.setMin_right(this.chrono.getMin_right() - 1);
                    }

                    if (this.chrono.getMin_right() == -1) {
                        this.chrono.setMin_right(9);
                        this.chrono.setMin_left(this.chrono.getMin_left() - 1);
                    }

                    if (this.chrono.getMin_left() == -1) {
                        this.chrono.setMin_left(5);
                        this.chrono.setHour_right(this.chrono.getHour_right() - 1);
                    }

                    if (this.chrono.getHour_left() == -1) {
                        this.chrono.setHour_left(0);
                        this.chrono.setHour_mid(this.chrono.getHour_mid() - 1);
                    }

                    if (this.chrono.getHour_mid() == -1) {
                        this.chrono.setHour_mid(0);
                        this.chrono.setHour_right(this.chrono.getHour_left() - 1);
                    }

                    if (this.chrono.getHour_right() == -1) {
                        this.setTimer(0);
                        String tmp = "";
                        if (!this.mashFinished && this.count == iodio) {
                            this.alarm = new Alarm(this.pathImages);
                            this.alarm.playSound();
                            tmp = JOptionPane.showInputDialog("Test IODIO: inserisci i minuti da aggiungere (null se si desidera continuare)");
                            this.alarm.stopSound();
                        } else {
                            if (!this.mashFinished && this.avvisiMash.isSelected()) {
                                this.alarm = new Alarm(this.pathImages);
                                this.alarm.playSound();
                                tmp = JOptionPane.showInputDialog("Inserisci i minuti da aggiungere (null se si desidera continuare)");
                                this.alarm.stopSound();
                            }
                        }
                        if (!tmp.equalsIgnoreCase("")) {
                            this.setTimer(Integer.parseInt(tmp));
                        } else {
                            this.chrono.setHour_right(0);
                            chk = false;
                            this.count++;
                            if (!this.mashFinished) {
                                this.mashTable.setSelectionBackground(Color.GREEN);
                                this.mashTable.setRowSelectionInterval(0, count - 1);
                                if (count < this.arrMash.length) {
                                    this.setTimer(this.arrMash[count][0]);
                                } else {
                                    this.mashFinished = true;
                                    this.count = 0;
                                    this.setTimer(this.recipe.getBollitura());
                                    //this.setTimer(this.arrBoil[count]);
                                    if (this.list[1].isSelected()) {
                                        this.changeButtonState(this.list[1]);
                                    }
                                    this.list[5].setIcon(new ImageIcon(this.pathImages + "play.png"));
            this.list[5].setBorder(null);
            this.list[5].setSelected(false);
            this.suspend();
  
                                    this.tx.put("?mashOff$");
                                    while (!chk) {
                                        chk = this.checkBoilTemperature();
                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException ex) {
                                            Logger.getLogger(Timers.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    }


                                    if (this.avvisiBoil.isSelected()) {
                                        this.alarm = new Alarm(this.pathImages);
                                        this.alarm.playSound();
                                        JOptionPane.showMessageDialog(new JFrame(),
                                                "Inserisci " + this.recipe.getHops().get(count).getName(),
                                                "Insert...",
                                                JOptionPane.WARNING_MESSAGE);
                                        this.alarm.stopSound();
                                    }
                                    count++;

                                }
                            }
                        }
                    }

                    if (this.mashFinished) {
                        //int tmp = this.getTimer();
                        if (count < this.arrBoil.length) {

                            if (this.checkTime()) {
                                //if (tmp == this.arrBoil[count] - 1) {
                                if (this.avvisiBoil.isSelected()) {
                                    this.alarm = new Alarm(this.pathImages);
                                    this.alarm.playSound();
                                    JOptionPane.showMessageDialog(new JFrame(),
                                            "Inserisci " + this.recipe.getHops().get(count).getName(),
                                            "Insert...",
                                            JOptionPane.WARNING_MESSAGE);
                                    this.alarm.stopSound();
                                }
                                this.boilTable.setSelectionBackground(Color.GREEN);
                                this.boilTable.setRowSelectionInterval(0, count - 1);
                                count++;
                            }
                        } else {
                            if (this.checkNumbers()) {
                                this.boilTable.setSelectionBackground(Color.GREEN);
                                this.boilTable.setRowSelectionInterval(0, count - 1);
                                this.boilFinished = true;
                                this.alarm = new Alarm(this.pathImages);
                                this.alarm.playSound();
                                JOptionPane.showMessageDialog(new JFrame(),
                                        "No more steps",
                                        "Sessione terminata",
                                        JOptionPane.WARNING_MESSAGE);
                                this.alarm.stopSound();
                                this.list[5].setEnabled(false);
                                this.tx.put("?boilOff$");
                                if (this.list[3].isSelected()) {
                                    this.changeButtonState(this.list[3]);
                                }
                            }
                        }
                    }
                    this.chrono.repaint();

                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Timers.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                break;

            case 1:
                while (this.autoSparge.isSelected()) {
                    this.checkSpargeTemperature();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Timers.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
        }
    }

    /**
     * Controlla se tutte le cifre del timer sono uguali a 0
     *
     * @return true se le cifre del timer sono uguali a 0, false altrimenti
     */
    public boolean checkNumbers() {
        int sec = this.chrono.getSec_left() + this.chrono.getSec_right();
        int hour = this.chrono.getHour_left() * 100 + this.chrono.getHour_mid() * 10 + this.chrono.getHour_right();
        int min = this.chrono.getMin_left() + this.chrono.getMin_right();

        if (sec == 0 && min == 0 && hour == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Controlla che il tempo indicato dal timer sia uguale ad uno specifico tempo di uno step di
     * boil (necessario per sapere quando avvisare l'utente di qualche intervento umano)
     *
     * @return true se è uguale al tempo di uno specifico step di boil, false altrimenti
     */
    public boolean checkTime() {
        if ((this.chrono.getSec_left() + this.chrono.getSec_right() == 0)) {
            int hour = this.chrono.getHour_left() * 100 + this.chrono.getHour_mid() * 10 + this.chrono.getHour_right();
            //hour = hour * 60;
            int minutes = (this.chrono.getMin_left() * 10 + this.chrono.getMin_right()) + hour * 60;

            if (minutes == this.arrBoil[count]) {
                return true;
            }
        }
        return false;
    }

    /**
     * Controlla il valore della pentola di mash, confrontandolo con quello indicato dalla ricetta
     *
     * @return false se il valore è minore, true altrimenti
     */
    public boolean checkMashTemperature() {
        if (!this.mashFinished) {
            if (this.temperature.getMashTemp() < (this.arrMash[count][1] - this.isteresi)) {
                if (!this.list[1].isSelected()) {
                    this.changeButtonState(this.list[1]);
                    this.tx.put("?mashOn$");
                }
                return false;
            } else {
                if (this.temperature.getMashTemp() >= (this.arrMash[count][1] - this.isteresi)) {
                    if (this.list[1].isSelected()) {
                        this.changeButtonState(this.list[1]);
                        this.tx.put("?mashOff$");
                        return true;
                    } else {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Controlla il valore della temperatura della pentola di sparge, accendendo o spegnendo il relay che
     * controlla la sua elettrovalvola qualora la temperatura sia minore o maggiore di quella impostata
     * nelle configurazioni
     */
    public void checkSpargeTemperature() {
        if (this.temperature.getSpargeTemp() < (this.configuration[0] - this.isteresi) && !this.list[2].isSelected()) {
            this.changeButtonState(this.list[2]);
            this.tx.put("?spargeOn$");
        } else {
            if (this.temperature.getSpargeTemp() >= (this.configuration[0] - this.isteresi) && this.list[2].isSelected()) {
                this.changeButtonState(this.list[2]);
                this.tx.put("?spargeOff$");
            }
        }
    }

    /**
     * Controlla il valore della temperatura della pentola di boil, accendendo o spegnendo il relay che
     * controlla la sua elettrovalvola qualora la temperatura sia minore o maggiore di quella impostata
     * nelle configurazioni
     */
    public boolean checkBoilTemperature() {
        if (this.temperature.getBoilTemp() < (this.configuration[1] - this.isteresi)) {
            if (!this.list[3].isSelected()) {
                this.changeButtonState(this.list[3]);
                this.tx.put("?boilOn$");
            }
            return false;

        } else {
            if (!this.list[3].isSelected()) {
                this.changeButtonState(this.list[3]);
                this.tx.put("?boilOn$");
            }
            return true;
        }
    }

    /**
     * Funzione per cambiare lo stato di un pulsante (e la relativa immagine)
     * @param button
     */
    public void changeButtonState(JButton button) {
        if (button.isSelected()) {
            button.setIcon(new ImageIcon(this.pathImages + "offOn.png"));
            button.setBorder(null);
            button.setSelected(false);
        } else {
            button.setIcon(new ImageIcon(this.pathImages + "OnOff.png"));
            button.setBorder(null);
            button.setSelected(true);
        }
    }

    /***********************
     *                     *
     * Metodi get() & set()*
     *                     *
     ***********************/
    public boolean isDotsOn() {
        return dotsOn;
    }

    public void setDotsOn(boolean dotsOn) {
        this.dotsOn = dotsOn;
    }

    public int getHour_left() {
        return hour_left;
    }

    public void setHour_left(int hour_left) {
        this.hour_left = hour_left;
    }

    public int getHour_mid() {
        return hour_mid;
    }

    public void setHour_mid(int hour_mid) {
        this.hour_mid = hour_mid;
    }

    public int getHour_right() {
        return hour_right;
    }

    public void setHour_right(int hour_right) {
        this.hour_right = hour_right;
    }

    public int getMin_left() {
        return min_left;
    }

    public void setMin_left(int min_left) {
        this.min_left = min_left;
    }

    public int getMin_right() {
        return min_right;
    }

    public void setMin_right(int min_right) {
        this.min_right = min_right;
    }

    public int getSec_left() {
        return sec_left;
    }

    public void setSec_left(int sec_left) {
        this.sec_left = sec_left;
    }

    public int getSec_right() {
        return sec_right;
    }

    public void setSec_right(int sec_right) {
        this.sec_right = sec_right;
    }
}
