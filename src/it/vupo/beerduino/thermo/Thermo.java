package it.vupo.beerduino.thermo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Point;
import javax.swing.JPanel;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.dial.DialBackground;
import org.jfree.chart.plot.dial.DialCap;
import org.jfree.chart.plot.dial.DialPlot;
import org.jfree.chart.plot.dial.DialTextAnnotation;
import org.jfree.chart.plot.dial.DialValueIndicator;
import org.jfree.chart.plot.dial.StandardDialFrame;
import org.jfree.chart.plot.dial.StandardDialScale;
import org.jfree.data.general.DefaultValueDataset;
import org.jfree.ui.GradientPaintTransformType;
import org.jfree.ui.StandardGradientPaintTransformer;

/**
 * Classe per la creazione grafica dei termometri da inserire all'interno di
 * un JPanel
 */
public class Thermo extends JPanel {
	
	/**
     * Dataset per la parte riguardante la temperatura (termometro)
     */
    private DefaultValueDataset temperature;
    /**
     * Dataset per la parte riguardante il volume delle pentole (indicatore)
     */
    private DefaultValueDataset volume;

    /**
     * Costruttore di default
     */
    public Thermo(String s) {
        this.temperature = null;
        this.volume = null;
        this.createDial(s);
    }

    /**
     * Costruisce il termometro
     */
    public void createDial(String s) {
        temperature = new DefaultValueDataset(0D);
        volume = new DefaultValueDataset(0D);
        DialPlot dialplot = new DialPlot();
        dialplot.setView(0.0D, 0.0D, 1.0D, 1.0D);
        dialplot.setDataset(0, temperature);
        dialplot.setDataset(1, volume);
        StandardDialFrame standarddialframe = new StandardDialFrame();
        standarddialframe.setBackgroundPaint(Color.lightGray);
        standarddialframe.setForegroundPaint(Color.darkGray);
        dialplot.setDialFrame(standarddialframe);
        GradientPaint gradientpaint = new GradientPaint(new Point(), new Color(255, 255, 255), new Point(), new Color(170, 170, 220));
        DialBackground dialbackground = new DialBackground(gradientpaint);
        dialbackground.setGradientPaintTransformer(new StandardGradientPaintTransformer(GradientPaintTransformType.VERTICAL));
        dialplot.setBackground(dialbackground);
        DialTextAnnotation dialtextannotation = new DialTextAnnotation("°C | L");
        dialtextannotation.setFont(new Font("Dialog", 1, 14));
        dialtextannotation.setRadius(0.69999999999999996D);
        dialplot.addLayer(dialtextannotation);
        DialValueIndicator dialvalueindicator = new DialValueIndicator(0);
        dialvalueindicator.setFont(new Font("Dialog", 0, 10));
        dialvalueindicator.setOutlinePaint(Color.black);
        dialvalueindicator.setRadius(0.59999999999999998D);
        dialvalueindicator.setAngle(-103D);
        dialplot.addLayer(dialvalueindicator);
        DialValueIndicator dialvalueindicator1 = new DialValueIndicator(1);
        dialvalueindicator1.setFont(new Font("Dialog", 0, 10));
        dialvalueindicator1.setOutlinePaint(Color.red);
        dialvalueindicator1.setRadius(0.59999999999999998D);
        dialvalueindicator1.setAngle(-77D);
        dialplot.addLayer(dialvalueindicator1);
        StandardDialScale standarddialscale = new StandardDialScale(-10D, 110d, -140D, -220D, 10D, 5);
        standarddialscale.setTickRadius(0.88D);
        standarddialscale.setTickLabelOffset(0.14999999999999999D);
        standarddialscale.setTickLabelFont(new Font("Dialog", 0, 14));
        dialplot.addScale(0, standarddialscale);
        StandardDialScale standarddialscale1 = new StandardDialScale(0.0D, 40D, +90D, -140D, 10D, 5);
        standarddialscale1.setTickRadius(0.5D);
        standarddialscale1.setTickLabelOffset(0.14999999999999999D);
        standarddialscale1.setTickLabelFont(new Font("Dialog", 0, 10));
        standarddialscale1.setMajorTickPaint(Color.red);
        standarddialscale1.setMinorTickPaint(Color.red);
        dialplot.addScale(1, standarddialscale1);
        dialplot.mapDatasetToScale(1, 1);
        org.jfree.chart.plot.dial.DialPointer.Pin pin = new org.jfree.chart.plot.dial.DialPointer.Pin(1);
        pin.setRadius(0.55000000000000004D);
        dialplot.addPointer(pin);
        org.jfree.chart.plot.dial.DialPointer.Pointer pointer = new org.jfree.chart.plot.dial.DialPointer.Pointer(0);
        dialplot.addPointer(pointer);
        DialCap dialcap = new DialCap();
        dialcap.setRadius(0.10000000000000001D);
        dialplot.setCap(dialcap);
        JFreeChart jfreechart = new JFreeChart(dialplot);
        jfreechart.setTitle(s);
        ChartPanel chartpanel = new ChartPanel(jfreechart);
        chartpanel.setPreferredSize(new Dimension(250, 250));
        add(chartpanel);
    }


    public DefaultValueDataset getTemperature() {
        return temperature;
    }

    public void setTemperature(DefaultValueDataset temperature) {
        this.temperature = temperature;
    }

    public DefaultValueDataset getVolume() {
        return volume;
    }

    public void setVolume(DefaultValueDataset volume) {
        this.volume = volume;
    }
}
