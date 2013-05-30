package it.vupo.beerduino.alarm;

import java.io.*; // for File
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.*; // allows you to use the sound classes

/**
 * Classe che suona una sirena di allarme alla fine di ogni step, o comunque
 * qualunque volta è necessario l'intervento umano
 *
 * @author SollAzzO
 */
public class Alarm {
    /**
     * File da eseguire (musichetta di allarme)
     */
    private File file;
    /**
     * Percorso del file waw da eseguire
     */
    private String path;
    /**
     * Clip per l'esecuzione del file audio
     */
    private Clip music;
    /**
     * Inputstream
     */
    private AudioInputStream stream;

    /**
     * Costruttore di default
     */
    public Alarm() {
        String temp = System.getProperty("user.dir");
        temp = temp + "/Images/";
        this.path = temp + "alarm.wav";
        this.file = new File(this.path);
        this.configure();
    }

    /**
     * Costruttore secondario
     *
     * @param path percorso del file da eseguire
     */
    public Alarm(String path) {
        String temp = System.getProperty("user.dir");
        temp = temp + "/Images/";
        this.path = temp + "alarm.wav";
        this.file = new File(this.path);
        this.configure();
    }

    /**
     * Configura l'esecuzione del file
     */
    public void configure() {
        music = null;
        try {
            music = AudioSystem.getClip();
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Alarm.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            this.stream = AudioSystem.getAudioInputStream(file);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(Alarm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Alarm.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            music = AudioSystem.getClip();
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Alarm.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            music.open(stream);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Alarm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Alarm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Esegui il file
     */
    public void playSound() {
        music.start();
        music.loop(Clip.LOOP_CONTINUOUSLY);
        //JOptionPane.showMessageDialog(null, "Next step", "Alert", JOptionPane.WARNING_MESSAGE);
        //music.stop();
    }

    /**
     * Stop il file
     */
    public void stopSound() {
        this.music.stop();
    }
}
