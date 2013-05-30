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

package it.vupo.beerduino.recipe;

import it.vupo.beerduino.recipe.data.Hop;
import it.vupo.beerduino.recipe.data.HopComparator;
import it.vupo.beerduino.recipe.data.Malt;
import it.vupo.beerduino.recipe.data.MashComparator;
import it.vupo.beerduino.recipe.data.MashStep;
import it.vupo.beerduino.recipe.data.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Classe di fondamentale importanza, per la creazione della ricetta (sia che si tratti di una ricetta creata
 * manualmente, sia di una importata). Gran parte del codice è stato preso dal progetto HobbyBrew, che permette tra le
 * altre cose di importare ricette da promash. Il codice è stato preso come spunto e modificato in base alle mie esigenze.
 * Senza questa classe, non sarebbe possibile gestire in modo automatizzato le varie fasi di mash e boil.
 * Necessita di alcune modifiche in quanto abbastanza complicata da comprendere
 *
 * @author Alessandro Morniroli
 */
public class Recipe extends Utils {

    /**
     * Lista contenente i luppoli
     */
    private List<Hop> hops;
    /**
     * Lista contenente gli step dei malti
     */
    private List<Malt> malts;
    /**
     * Lista contenente gli steps dei malti
     */
    private List<MashStep> mash;
    /**
     * Nome
     */
    private String name;
    /**
     * Minuti di bollitura
     */
    private int bollitura;
    /**
     * Numero di luppoli
     */
    private int numHop;
    /**
     * Numero di malti
     */
    private int numMalt;
    /**
     * Attributo necessario per la lettura della ricett
     */
    private int y;
    /**
     * Attributo necessario per la lettura della ricett
     */
    private int m;
    /**
     * Attributo necessario per la lettura della ricett
     */
    private boolean mashComplex;
    /**
     * Attributo necessario per la lettura della ricett
     */
    private int numStepMash;
    /**
     * Attributo necessario per la lettura della ricett
     */
    private int numExtra;
    /**
     * Path della ricetta promash
     */
    private String path;
    /**
     * Buffer di appoggio per la lettura del file
     */
    private byte[] b;
    /**
     * Step iodio
     */
    private int iodio;
    /**
     * Tempo di boil
     */
    private int boil;
    /**
     * Boolean per la conferma
     */
    private boolean check;

    /**
     * Costruttore di default
     */
    public Recipe() {
    }

    /**
     * Costruttore secondario
     */
    public Recipe(String path) throws Exception {
        this.path = path;
        b = super.buffer(path);
    }

    /**
     * Funzione che modifica correttamente gli step del mash, indicando solamente
     * il nome dello step, la temperatura e la sua durata
     */
    public void extractMash() {
        List<MashStep> temp = new ArrayList<MashStep>();
        int tmp = 0;
        for (int i = 0; i < this.mash.size(); i++) {
            if (this.mash.get(i).getEndTemp() > 0) {
                if (!this.mash.get(i).getNome().equals("Sparge")) {
                    temp.add(this.mash.get(i));
                    temp.get(tmp).setLength(temp.get(tmp).getLength());
                    tmp++;
                }
            }
        }
        Collections.sort(temp);
        this.setMash(temp); // istruzione che mi riscrive il mashStep
    }

    /**
     * Funzione init che permette di leggere la ricetta
     * @throws Exception
     */
    public void readRec(String path) throws Exception {
        this.path = path;
        this.b = super.buffer(path);
        setHops(new ArrayList<Hop>());
        setMalts(new ArrayList<Malt>());
        setMash(new ArrayList<MashStep>());
        setName(super.arr2String(b, 0));
        setBollitura((int) b[117]);
        this.readHop();
        this.readMalts();
        this.readExtra();
        //this.printHops();
        this.readArgs();
        this.readMash();
        this.setIodioStep();
        this.order();
        //this.printHops();

    }

    /**
     * Sfoglia la lista del mash alla ricerca dello step dello iodio (subito dopo
     * lo step di saccarificazione
     */
    public void setIodioStep() {
        for (int i = 0; i < this.mash.size(); i++) {
            if (this.mash.get(i).getNome() == "Saccharification rest") {
                this.mash.get(i).setIodio(true);
            }
        }
    }

    /**
     * Lettura dei luppoli
     */
    public void readHop() {
        setNumHop((int) b[85]);
        for (int i = 0; i < getNumHop(); i++) {
            Hop h = new Hop();
            h.setName(super.arr2String(b, 1157 + 635 * i));
            double tmp = super.arr2Double(b, 1157 + 635 * i + (1777 - 1157));
            h.setAa((double) ((int) (tmp * 10)) / 10);
            h.setMinutes((int) super.arr2Byte(b, 1157 + 635 * i + (1786 - 1157)));
            if (h.getMinutes() < 0) {
                h.setMinutes(0);
            }
            getHops().add(h);
        }
    }

    /**
     * Lettura dei malti
     */
    public void readMalts() {
        setNumMalt((int) b[89]);
        for (int i = 0; i < getNumMalt(); i++) {
            Malt m = new Malt();
            m.setName(super.arr2String(b, 1157 + 635 * getNumHop() + 529 * i));
            getMalts().add(m);
        }
    }

    /**
     * Lettura di qualcosa (non so bene, il codice è preso da HobbyBrew)
     */
    public void readArgs() {
        setM(1157 + 635 * getNumHop() + 529 * getNumMalt() + 589 * getNumExtra() + 757 + 4028 + 4028);
        setMashComplex(b[b.length - 1] == 1);
        setNumStepMash((int) b[getM() + 255]);
        setY(1157 + 635 * getNumHop() + 529 * getNumMalt() + 589 * getNumExtra());
    }

    /**
     * Lettura di eventuali extra (coriandolo, etc)
     */
    public void readExtra() {
        setNumExtra((int) b[93]);
        for (int i = 0; i < getNumExtra(); i++) {
            Hop h = new Hop();
            h.setName(super.arr2String(b, 1157 + 635 * getNumHop() + 529 * getNumMalt() + 589 * i));
            h.setAa(0.0);
            h.setMinutes((int) super.arr2Byte(b, 1157 + 635 * getNumHop() + 529 * getNumMalt() + 589 * i + 56));
            getHops().add(h);
        }
    }

    /**
     * Lettura del mash
     */
    public void readMash() {
        if (!isMashComplex()) {
            //System.out.println("multistep="+Utils.arr2Byte(b,getY()+695));
            setNumStepMash(super.arr2Byte(b, getY() + 695));
            boolean multistep = (super.arr2Byte(b, getY() + 695) == 3);
            int i = 704;
            int minute = 0, T = (int) super.F2C((int) b[getM() + 259]);

            String D[] = new String[]{"Acid rest", "Protein rest", "Intermediate rest", "Saccharification rest", "Mash out", "Sparge"};
            for (int j = 0; j < 6; j++) {
                if (multistep || j >= 3) {
                    MashStep step = new MashStep();
                    step.setStart(minute);
                    step.setStartTemp(T);
                    //System.out.println("Temperatura iniziale: " + T);
                    step.setEndTemp((int) Math.round(super.F2C((int) 0xFF & super.arr2Byte(b, getY() + i))));
                    //System.out.println("Temperatura finale: " + (int)
                    //	    Math.round(Utils.F2C((int)0xFF&Utils.arr2Byte(b,getY()+i))));
                    int L = (int) 0xFF & super.arr2Byte(b, getY() + i + 4);
                    //System.out.println("L " + L);
                    if (L > 5) {
                        step.setLength(L);
                        step.setRamp(5);
                    } else {
                        step.setLength(L);
                        step.setRamp(0);
                    }
                    if (step.getStartTemp() <= 0 || L <= 5) {
                        step.setStartTemp(step.getEndTemp());
                    }
                    step.setNome(D[j]);

                    //if(L>0) getInfusionSteps().add(step);
                    minute += 5 + step.getLength();
                    //System.out.println("Minuti: " + minute);
                    getMash().add(step);
                    T = step.getEndTemp();
                }
                i += 8;
            }
        }
    }

    /**
     * Stampa a video la ricetta completa
     */
    public void print() {
        System.out.println("Nome: " + getName() + " ");
        System.out.println("Boiltime: " + getBollitura() + " ");
        this.printMalts();
        this.printHops();
        System.out.println("NumStep: " + getNumStepMash() + " ");
        this.printMash();
    }

    /**
     * Stampa a video i luppoli
     */
    public void printHops() {
        System.out.println("HOPS:");
        for (int i = 0; i < getHops().size(); i++) {
            System.out.print("Name: " + getHops().get(i).getName() + " ");
            System.out.print("AA%: " + getHops().get(i).getAa() + " ");
            System.out.print("Minutes: " + getHops().get(i).getMinutes());
            System.out.println();
        }
    }

    /**
     * Stampa a video i malti
     */
    public void printMalts() {
        System.out.println("MALTS:");
        for (int i = 0; i < getMalts().size(); i++) {
            System.out.print("Name: " + getMalts().get(i).getName() + " ");
            System.out.println();
        }
    }

    /**
     * Stampa a video le fasi di mash
     */
    public void printMash() {
        this.extractMash();
        System.out.println("MASH:");
        for (int i = 0; i < getMash().size(); i++) {
            System.out.print("Name: " + getMash().get(i).getNome() + " ");
            System.out.print("Temp: " + getMash().get(i).getEndTemp() + " ");
            System.out.print("Minute: " + (getMash().get(i).getLength()) + " ");
            System.out.println();
        }
    }

    /**
     * Ordina la ricetta (con le temperature per il mash e con i minuti per i luppoli e spezie
     */
    public void order() {
        for (int i = 0; i < this.mash.size(); i++) {
            if (this.mash.get(i).getEndTemp() < 0
                    || this.mash.get(i).getNome() == "Sparge"
                    || this.mash.get(i).getLength() < 0) {
                this.mash.remove(i);
            }
        }

        Collections.sort(this.mash, new MashComparator());
        Collections.sort(this.hops, new HopComparator());
        this.fixBoil();
    }

    /**
     * Sistema la lista della bollitura. Qualora luppoli o spezie hanno lo stesso tempo di bollitura,
     * viene creato solamente uno step e non due.
     */
    public void fixBoil() {
        for (int i = 0; i < this.hops.size(); i++) {
            if (i + 1 < this.hops.size() && this.hops.get(i).getMinutes() == this.hops.get(i + 1).getMinutes()) {
                Hop temp = new Hop();
                temp.setName(this.hops.get(i).getName() + "-" + this.hops.get(i + 1).getName());
                temp.setMinutes(this.hops.get(i).getMinutes());
                this.hops.remove(i);
                this.hops.remove(i);
                this.hops.add(i, temp);
            }
        }
    }

    /***********************
     *                     *
     * Metodi get() & set()*
     *                     *
     ***********************/
    public int getBollitura() {
        return bollitura;
    }

    public void setBollitura(int bollitura) {
        this.bollitura = bollitura;
    }

    public List<Hop> getHops() {
        return hops;
    }

    public void setHops(List<Hop> hops) {
        this.hops = hops;
    }

    public List<Malt> getMalts() {
        return malts;
    }

    public void setMalts(List<Malt> malts) {
        this.malts = malts;
    }

    public List<MashStep> getMash() {
        return mash;
    }

    public void setMash(List<MashStep> mash) {
        this.mash = mash;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumHop() {
        return numHop;
    }

    public void setNumHop(int numHop) {
        this.numHop = numHop;
    }

    public int getNumMalt() {
        return numMalt;
    }

    public void setNumMalt(int numMalt) {
        this.numMalt = numMalt;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public boolean isMashComplex() {
        return mashComplex;
    }

    public void setMashComplex(boolean mashComplex) {
        this.mashComplex = mashComplex;
    }

    public int getNumStepMash() {
        return numStepMash;
    }

    public void setNumStepMash(int numStepMash) {
        this.numStepMash = numStepMash;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getNumExtra() {
        return numExtra;
    }

    public void setNumExtra(int numExtra) {
        this.numExtra = numExtra;
    }

    public byte[] getB() {
        return b;
    }

    public void setB(byte[] b) {
        this.b = b;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getIodio() {
        return iodio;
    }

    public void setIodio(int iodio) {
        this.iodio = iodio;
    }

    public int getBoil() {
        return boil;
    }

    public void setBoil(int boil) {
        this.boil = boil;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
