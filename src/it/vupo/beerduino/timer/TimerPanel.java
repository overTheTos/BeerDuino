package it.vupo.beerduino.timer;

/**
 *  Classe per la creazione grafica del timer. Classe modificata per le mie
 * esigenze recuperata dal sito http://www.jug-muenster.de/swing-apollo-space-program-mission-timer-280/
 *
 * @author hansolo
 */
public class TimerPanel extends javax.swing.JPanel implements java.awt.event.MouseListener {

    private final java.awt.image.BufferedImage[] DIGIT_ARRAY = {
        createDigit(0),
        createDigit(1),
        createDigit(2),
        createDigit(3),
        createDigit(4),
        createDigit(5),
        createDigit(6),
        createDigit(7),
        createDigit(8),
        createDigit(9)
    };
    private final java.awt.image.BufferedImage DOTS_ON = createDots(true);
    private final java.awt.image.BufferedImage DOTS_OFF = createDots(false);
    private boolean dotsOn = true;
    private final java.awt.image.BufferedImage BACKGROUND_IMAGE = createBackground(450, 180);
    private final javax.swing.Timer TIMER = new javax.swing.Timer(500, new java.awt.event.ActionListener() {

        public void actionPerformed(java.awt.event.ActionEvent evt) {
        }
    });
    private int sec_right = 0;
    private int sec_left = 0;
    private int min_right = 0;
    private int min_left = 0;
    private int hour_right = 0;
    private int hour_mid = 0;
    private int hour_left = 0;

    public TimerPanel() {
        setPreferredSize(new java.awt.Dimension(450, 180));
        setSize(new java.awt.Dimension(450, 180));
        init();
    }

    private void init() {
        addMouseListener(this);
    }

    public void startTimer() {
        TIMER.start();
    }

    public void stopTimer() {
        TIMER.stop();
        dotsOn = true;
    }

    public void resetTimer() {
        TIMER.stop();
        sec_right = 0;
        sec_left = 0;
        min_right = 0;
        min_left = 0;
        hour_right = 0;
        hour_mid = 0;
        hour_left = 0;
        repaint();
        dotsOn = true;
    }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);

        java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(java.awt.RenderingHints.KEY_ALPHA_INTERPOLATION, java.awt.RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2.setRenderingHint(java.awt.RenderingHints.KEY_COLOR_RENDERING, java.awt.RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2.setRenderingHint(java.awt.RenderingHints.KEY_STROKE_CONTROL, java.awt.RenderingHints.VALUE_STROKE_PURE);
        g2.setRenderingHint(java.awt.RenderingHints.KEY_DITHERING, java.awt.RenderingHints.VALUE_DITHER_ENABLE);
        g2.setRenderingHint(java.awt.RenderingHints.KEY_RENDERING, java.awt.RenderingHints.VALUE_RENDER_QUALITY);

        g2.drawImage(BACKGROUND_IMAGE, 0, 0, this);

        g2.drawImage(DIGIT_ARRAY[hour_left], 38, 90, this);
        g2.drawImage(DIGIT_ARRAY[hour_mid], 84, 90, this);
        g2.drawImage(DIGIT_ARRAY[hour_right], 130, 90, this);

        if (dotsOn) {
            g2.drawImage(DOTS_ON, 172, 90, this);
            g2.drawImage(DOTS_ON, 301, 90, this);
        } else {
            g2.drawImage(DOTS_OFF, 172, 90, this);
            g2.drawImage(DOTS_OFF, 301, 90, this);
        }

        g2.drawImage(DIGIT_ARRAY[min_left], 213, 90, this);
        g2.drawImage(DIGIT_ARRAY[min_right], 259, 90, this);

        g2.drawImage(DIGIT_ARRAY[sec_left], 319, 90, this);
        g2.drawImage(DIGIT_ARRAY[sec_right], 365, 90, this);

        g2.dispose();
    }

    private java.awt.image.BufferedImage createBackground(int width, int height) {
        java.awt.GraphicsConfiguration gfxConf = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        java.awt.image.BufferedImage IMAGE = gfxConf.createCompatibleImage(width, height, java.awt.Transparency.TRANSLUCENT);

        java.awt.Graphics2D g2 = (java.awt.Graphics2D) IMAGE.getGraphics();
        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(java.awt.RenderingHints.KEY_STROKE_CONTROL, java.awt.RenderingHints.VALUE_STROKE_PURE);
        g2.setRenderingHint(java.awt.RenderingHints.KEY_ALPHA_INTERPOLATION, java.awt.RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2.setRenderingHint(java.awt.RenderingHints.KEY_COLOR_RENDERING, java.awt.RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2.setRenderingHint(java.awt.RenderingHints.KEY_DITHERING, java.awt.RenderingHints.VALUE_DITHER_ENABLE);
        g2.setRenderingHint(java.awt.RenderingHints.KEY_RENDERING, java.awt.RenderingHints.VALUE_RENDER_QUALITY);

        final java.awt.geom.Point2D START_BACKGROUND = new java.awt.geom.Point2D.Float(0, 0);
        final java.awt.geom.Point2D STOP_BACKGROUND = new java.awt.geom.Point2D.Float(0, height);

        final float[] FRACTIONS = {
            0.0f,
            1.0f
        };

        final java.awt.Color[] COLORS_BACKGROUND = {
            new java.awt.Color(0xEEEEEE),
            new java.awt.Color(0xEEEEEE)
        };

        final java.awt.geom.Point2D START_HIGHLIGHT = new java.awt.geom.Point2D.Float(0, 79);
        final java.awt.geom.Point2D STOP_HIGHLIGHT = new java.awt.geom.Point2D.Float(0, 166);

        final float[] FRACTIONS_HIGHLIGHT = {
            0.0f,
            0.9f,
            1.0f
        };

        final java.awt.Color[] COLORS_HIGHLIGHT = {
            new java.awt.Color(0x000000),
            new java.awt.Color(0x000000),
            new java.awt.Color(0x000000)
        };

        final java.awt.geom.Point2D START = new java.awt.geom.Point2D.Float(0, 80);
        final java.awt.geom.Point2D STOP = new java.awt.geom.Point2D.Float(0, 165);

        final java.awt.Color[] COLORS_RIGHT = {
            new java.awt.Color(0xEEEEEE),
            new java.awt.Color(0xEEEEEE)
        };

        final java.awt.Color[] COLORS_LEFT = {
            new java.awt.Color(0xEEEEEE),
            new java.awt.Color(0xEEEEEE)
        };


        final java.awt.LinearGradientPaint GRADIENT_BACKGROUND = new java.awt.LinearGradientPaint(START_BACKGROUND, STOP_BACKGROUND, FRACTIONS, COLORS_BACKGROUND);
        g2.setPaint(GRADIENT_BACKGROUND);
        g2.fillRect(0, 0, width, height);

        final java.awt.LinearGradientPaint GRADIENT_HIGHLIGHT = new java.awt.LinearGradientPaint(START_HIGHLIGHT, STOP_HIGHLIGHT, FRACTIONS_HIGHLIGHT, COLORS_HIGHLIGHT);
        g2.setPaint(GRADIENT_HIGHLIGHT);
        g2.fillRect(17, 79, 176, 87);
        g2.fillRect(193, 79, 239, 87);

        final java.awt.LinearGradientPaint GRADIENT_LEFT = new java.awt.LinearGradientPaint(START, STOP, FRACTIONS, COLORS_LEFT);
        g2.setPaint(GRADIENT_LEFT);
        g2.fillRect(18, 80, 175, 85);

        final java.awt.LinearGradientPaint GRADIENT_RIGHT = new java.awt.LinearGradientPaint(START, STOP, FRACTIONS, COLORS_RIGHT);
        g2.setPaint(GRADIENT_RIGHT);
        g2.fillRect(193, 80, 238, 85);

        g2.setColor(new java.awt.Color(0x000000));
        g2.drawLine(193, 81, 193, 164);
        g2.setColor(new java.awt.Color(0x000000));
        g2.drawLine(194, 81, 194, 164);

        final java.awt.BasicStroke STROKE = new java.awt.BasicStroke(4.0f, java.awt.BasicStroke.CAP_SQUARE, java.awt.BasicStroke.JOIN_BEVEL);
        g2.setStroke(STROKE);
        g2.setColor(new java.awt.Color(0x000000)); // NEXT STEP E ANNESSI
        g2.drawLine(19, 30, 19, 36);
        g2.drawLine(19, 30, 130, 30);
        g2.drawLine(318, 30, 429, 30);
        g2.drawLine(429, 30, 429, 36);

        g2.setFont(new java.awt.Font("Arial", 1, 22));
        g2.drawString("      Next Step", 140, 37);
        g2.setFont(new java.awt.Font("Arial", 1, 16));
        g2.drawString("HOURS", 75, 65);
        g2.drawString("MIN", 243, 65);
        g2.drawString("SEC", 355, 65);

        g2.dispose();

        return IMAGE;
    }

    private java.awt.image.BufferedImage createDigit(int digit) {
        java.awt.GraphicsConfiguration gfxConf = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        java.awt.image.BufferedImage IMAGE = gfxConf.createCompatibleImage(46, 65, java.awt.Transparency.TRANSLUCENT);

        final java.awt.Color COLOR_ON = new java.awt.Color(0x000000);
        final java.awt.Color FRAME_COLOR_ON = new java.awt.Color(0x000000);
        final java.awt.Color COLOR_OFF = new java.awt.Color(0xEEEEEE);
        final java.awt.Color FRAME_COLOR_OFF = new java.awt.Color(0xEEEEEE);
        final java.awt.BasicStroke FRAME_STROKE = new java.awt.BasicStroke(1.0f, java.awt.BasicStroke.CAP_ROUND, java.awt.BasicStroke.JOIN_ROUND);

        java.awt.Graphics2D g2 = (java.awt.Graphics2D) IMAGE.getGraphics();
        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(java.awt.RenderingHints.KEY_STROKE_CONTROL, java.awt.RenderingHints.VALUE_STROKE_PURE);
        g2.setRenderingHint(java.awt.RenderingHints.KEY_ALPHA_INTERPOLATION, java.awt.RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2.setRenderingHint(java.awt.RenderingHints.KEY_COLOR_RENDERING, java.awt.RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2.setRenderingHint(java.awt.RenderingHints.KEY_DITHERING, java.awt.RenderingHints.VALUE_DITHER_ENABLE);
        g2.setRenderingHint(java.awt.RenderingHints.KEY_RENDERING, java.awt.RenderingHints.VALUE_RENDER_QUALITY);


        // A
        java.awt.geom.GeneralPath segment_a = new java.awt.geom.GeneralPath();
        segment_a.moveTo(17, 0);
        segment_a.lineTo(38, 0);
        segment_a.lineTo(37, 8);
        segment_a.lineTo(16, 8);
        segment_a.closePath();

        // B
        java.awt.geom.GeneralPath segment_b = new java.awt.geom.GeneralPath();
        segment_b.moveTo(39, 0);
        segment_b.lineTo(41, 0);
        segment_b.quadTo(45, 0, 45, 5);
        segment_b.lineTo(41, 32);
        segment_b.lineTo(38, 32);
        segment_b.lineTo(35, 28);
        segment_b.closePath();

        // C
        java.awt.geom.GeneralPath segment_c = new java.awt.geom.GeneralPath();
        segment_c.moveTo(37, 33);
        segment_c.lineTo(41, 33);
        segment_c.lineTo(37, 60);
        segment_c.quadTo(36, 65, 32, 65);
        segment_c.lineTo(30, 65);
        segment_c.lineTo(34, 37);
        segment_c.closePath();

        // D
        java.awt.geom.GeneralPath segment_d = new java.awt.geom.GeneralPath();
        segment_d.moveTo(9, 57);
        segment_d.lineTo(30, 57);
        segment_d.lineTo(29, 65);
        segment_d.lineTo(8, 65);
        segment_d.closePath();

        // E
        java.awt.geom.GeneralPath segment_e = new java.awt.geom.GeneralPath();
        segment_e.moveTo(4, 33);
        segment_e.lineTo(8, 33);
        segment_e.lineTo(11, 37);
        segment_e.lineTo(7, 65);
        segment_e.lineTo(4, 65);
        segment_e.quadTo(0, 65, 0, 60);
        segment_e.closePath();

        // F
        java.awt.geom.GeneralPath segment_f = new java.awt.geom.GeneralPath();
        segment_f.moveTo(8, 5);
        segment_f.quadTo(8, 0, 13, 0);
        segment_f.lineTo(16, 0);
        segment_f.lineTo(12, 28);
        segment_f.lineTo(8, 32);
        segment_f.lineTo(4, 32);
        segment_f.closePath();

        // G
        java.awt.geom.GeneralPath segment_g = new java.awt.geom.GeneralPath();
        segment_g.moveTo(14, 29);
        segment_g.lineTo(34, 29);
        segment_g.lineTo(36, 33);
        segment_g.lineTo(32, 37);
        segment_g.lineTo(13, 37);
        segment_g.lineTo(9, 33);
        segment_g.closePath();

        g2.setStroke(FRAME_STROKE);

        switch (digit) {
            case 0:
                g2.setColor(COLOR_ON);
                g2.fill(segment_a);
                g2.fill(segment_b);
                g2.fill(segment_c);
                g2.fill(segment_d);
                g2.fill(segment_e);
                g2.fill(segment_f);
                g2.setColor(COLOR_OFF);
                g2.fill(segment_g);
                g2.setColor(FRAME_COLOR_ON);
                g2.draw(segment_a);
                g2.draw(segment_b);
                g2.draw(segment_c);
                g2.draw(segment_d);
                g2.draw(segment_e);
                g2.draw(segment_f);
                g2.setColor(FRAME_COLOR_OFF);
                g2.draw(segment_g);
                break;
            case 1:
                g2.setColor(COLOR_ON);
                g2.fill(segment_b);
                g2.fill(segment_c);
                g2.setColor(COLOR_OFF);
                g2.fill(segment_a);
                g2.fill(segment_d);
                g2.fill(segment_e);
                g2.fill(segment_f);
                g2.fill(segment_g);
                g2.setColor(FRAME_COLOR_ON);
                g2.draw(segment_b);
                g2.draw(segment_c);
                g2.setColor(FRAME_COLOR_OFF);
                g2.draw(segment_a);
                g2.draw(segment_d);
                g2.draw(segment_e);
                g2.draw(segment_f);
                g2.draw(segment_g);
                break;
            case 2:
                g2.setColor(COLOR_ON);
                g2.fill(segment_a);
                g2.fill(segment_b);
                g2.fill(segment_d);
                g2.fill(segment_e);
                g2.fill(segment_g);
                g2.setColor(COLOR_OFF);
                g2.fill(segment_c);
                g2.fill(segment_f);
                g2.setColor(FRAME_COLOR_ON);
                g2.draw(segment_a);
                g2.draw(segment_b);
                g2.draw(segment_d);
                g2.draw(segment_e);
                g2.draw(segment_g);
                g2.setColor(FRAME_COLOR_OFF);
                g2.draw(segment_c);
                g2.draw(segment_f);
                break;
            case 3:
                g2.setColor(COLOR_ON);
                g2.fill(segment_a);
                g2.fill(segment_b);
                g2.fill(segment_c);
                g2.fill(segment_d);
                g2.fill(segment_g);
                g2.setColor(COLOR_OFF);
                g2.fill(segment_e);
                g2.fill(segment_f);
                g2.setColor(FRAME_COLOR_ON);
                g2.draw(segment_a);
                g2.draw(segment_b);
                g2.draw(segment_c);
                g2.draw(segment_d);
                g2.draw(segment_g);
                g2.setColor(FRAME_COLOR_OFF);
                g2.draw(segment_e);
                g2.draw(segment_f);
                break;
            case 4:
                g2.setColor(COLOR_ON);
                g2.fill(segment_b);
                g2.fill(segment_c);
                g2.fill(segment_f);
                g2.fill(segment_g);
                g2.setColor(COLOR_OFF);
                g2.fill(segment_a);
                g2.fill(segment_d);
                g2.fill(segment_e);
                g2.setColor(FRAME_COLOR_ON);
                g2.draw(segment_b);
                g2.draw(segment_c);
                g2.draw(segment_f);
                g2.draw(segment_g);
                g2.setColor(FRAME_COLOR_OFF);
                g2.draw(segment_a);
                g2.draw(segment_d);
                g2.draw(segment_e);
                break;
            case 5:
                g2.setColor(COLOR_ON);
                g2.fill(segment_a);
                g2.fill(segment_c);
                g2.fill(segment_d);
                g2.fill(segment_f);
                g2.fill(segment_g);
                g2.setColor(COLOR_OFF);
                g2.fill(segment_b);
                g2.fill(segment_e);
                g2.setColor(FRAME_COLOR_ON);
                g2.draw(segment_a);
                g2.draw(segment_c);
                g2.draw(segment_d);
                g2.draw(segment_f);
                g2.draw(segment_g);
                g2.setColor(FRAME_COLOR_OFF);
                g2.draw(segment_b);
                g2.draw(segment_e);
                break;
            case 6:
                g2.setColor(COLOR_ON);
                g2.fill(segment_a);
                g2.fill(segment_c);
                g2.fill(segment_d);
                g2.fill(segment_e);
                g2.fill(segment_f);
                g2.fill(segment_g);
                g2.setColor(COLOR_OFF);
                g2.fill(segment_b);
                g2.setColor(FRAME_COLOR_ON);
                g2.draw(segment_a);
                g2.draw(segment_c);
                g2.draw(segment_d);
                g2.draw(segment_e);
                g2.draw(segment_f);
                g2.draw(segment_g);
                g2.setColor(FRAME_COLOR_OFF);
                g2.draw(segment_b);
                break;
            case 7:
                g2.setColor(COLOR_ON);
                g2.fill(segment_a);
                g2.fill(segment_b);
                g2.fill(segment_c);
                g2.setColor(COLOR_OFF);
                g2.fill(segment_d);
                g2.fill(segment_e);
                g2.fill(segment_f);
                g2.fill(segment_g);
                g2.setColor(FRAME_COLOR_ON);
                g2.draw(segment_a);
                g2.draw(segment_b);
                g2.draw(segment_c);
                g2.setColor(FRAME_COLOR_OFF);
                g2.draw(segment_d);
                g2.draw(segment_e);
                g2.draw(segment_f);
                g2.draw(segment_g);
                break;
            case 8:
                g2.setColor(COLOR_ON);
                g2.fill(segment_a);
                g2.fill(segment_b);
                g2.fill(segment_c);
                g2.fill(segment_d);
                g2.fill(segment_e);
                g2.fill(segment_f);
                g2.fill(segment_g);
                g2.setColor(FRAME_COLOR_ON);
                g2.draw(segment_a);
                g2.draw(segment_b);
                g2.draw(segment_c);
                g2.draw(segment_d);
                g2.draw(segment_e);
                g2.draw(segment_f);
                g2.draw(segment_g);
                break;
            case 9:
                g2.setColor(COLOR_ON);
                g2.fill(segment_a);
                g2.fill(segment_b);
                g2.fill(segment_c);
                g2.fill(segment_d);
                g2.fill(segment_f);
                g2.fill(segment_g);
                g2.setColor(COLOR_OFF);
                g2.fill(segment_e);
                g2.setColor(FRAME_COLOR_ON);
                g2.draw(segment_a);
                g2.draw(segment_b);
                g2.draw(segment_c);
                g2.draw(segment_d);
                g2.draw(segment_f);
                g2.draw(segment_g);
                g2.setColor(FRAME_COLOR_OFF);
                g2.draw(segment_e);
                break;
            default:
                g2.setColor(COLOR_OFF);
                g2.fill(segment_a);
                g2.fill(segment_b);
                g2.fill(segment_c);
                g2.fill(segment_d);
                g2.fill(segment_e);
                g2.fill(segment_f);
                g2.fill(segment_g);
                g2.setColor(FRAME_COLOR_OFF);
                g2.draw(segment_a);
                g2.draw(segment_b);
                g2.draw(segment_c);
                g2.draw(segment_d);
                g2.draw(segment_e);
                g2.draw(segment_f);
                g2.draw(segment_g);
                break;
        }

        g2.dispose();

        return IMAGE;
    }

    private java.awt.image.BufferedImage createDots(boolean on) {
        java.awt.GraphicsConfiguration gfxConf = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        java.awt.image.BufferedImage IMAGE = gfxConf.createCompatibleImage(23, 65, java.awt.Transparency.TRANSLUCENT);

        final java.awt.Color COLOR_ON = new java.awt.Color(0x000000);
        final java.awt.Color FRAME_COLOR_ON = new java.awt.Color(0x000000);
        final java.awt.Color COLOR_OFF = new java.awt.Color(0x000000);
        final java.awt.Color FRAME_COLOR_OFF = new java.awt.Color(0x000000);
        final java.awt.BasicStroke FRAME_STROKE = new java.awt.BasicStroke(1.0f, java.awt.BasicStroke.CAP_ROUND, java.awt.BasicStroke.JOIN_ROUND);

        java.awt.Graphics2D g2 = (java.awt.Graphics2D) IMAGE.getGraphics();
        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(java.awt.RenderingHints.KEY_STROKE_CONTROL, java.awt.RenderingHints.VALUE_STROKE_PURE);
        g2.setRenderingHint(java.awt.RenderingHints.KEY_ALPHA_INTERPOLATION, java.awt.RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2.setRenderingHint(java.awt.RenderingHints.KEY_COLOR_RENDERING, java.awt.RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2.setRenderingHint(java.awt.RenderingHints.KEY_DITHERING, java.awt.RenderingHints.VALUE_DITHER_ENABLE);
        g2.setRenderingHint(java.awt.RenderingHints.KEY_RENDERING, java.awt.RenderingHints.VALUE_RENDER_QUALITY);

        g2.setStroke(FRAME_STROKE);

        if (on) {
            g2.setColor(COLOR_ON);
            g2.fillOval(8, 20, 7, 7);
            g2.fillOval(5, 39, 7, 7);

            g2.setColor(FRAME_COLOR_ON);
            g2.drawOval(8, 20, 7, 7);
            g2.drawOval(5, 39, 7, 7);
        } else {
            g2.setColor(COLOR_OFF);
            g2.fillOval(8, 20, 7, 7);
            g2.fillOval(5, 39, 7, 7);

            g2.setColor(FRAME_COLOR_OFF);
            g2.drawOval(8, 20, 7, 7);
            g2.drawOval(5, 39, 7, 7);
        }

        g2.dispose();

        return IMAGE;
    }

    @Override
    public java.awt.Dimension getSize() {
        return new java.awt.Dimension(450, 180);
    }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent event) {
        if (event.getButton() == java.awt.event.MouseEvent.BUTTON1) {
            if (TIMER.isRunning()) {
                stopTimer();
            } else {
                startTimer();
            }
        }

        if (event.getClickCount() == 2) {
            resetTimer();
        }
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent event) {
    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent event) {
    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent event) {
    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent event) {
    }

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
