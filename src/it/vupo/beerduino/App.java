package it.vupo.beerduino;



import it.vupo.beerduino.configuration.AppConst;
import it.vupo.beerduino.configuration.Configuration;
import it.vupo.beerduino.configuration.GlobalSetting;
import it.vupo.beerduino.recipe.Load;
import it.vupo.beerduino.recipe.Recipe;
import it.vupo.beerduino.thermo.Thermo;
import it.vupo.beerduino.thread.Timers;
import it.vupo.beerduino.timer.TimerPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;
import org.jdesktop.layout.GroupLayout;







public class App extends JFrame implements ActionListener {

	private static final long serialVersionUID = -526930065395069010L;

	private SingleByteCommunication sbc;
	
	private static final Logger log = Logger.getLogger(App.class);

	private JPanel systemTimerPanel;
	
	//Configurazione
	private Configuration configuration;
    private String pathImages;
	
	//Ricetta
	private Recipe recipe;
	
	//Timer
    private Timers timer;
    private TimerPanel chrono;
    private JPanel timerPanel;
	
	//Termometro grafico di mash
    private Thermo mashThermo;
    private JCheckBox avvisiMash;
    
    //Parte tabella con step da promash
    private DefaultTableModel mashModel;
    private DefaultTableModel boilModel;
    private JPanel mashPanel;
    private JTable mashTable;
    private JPanel mashTablePanel;
    private JPanel boilPanel;
    private JTable boilTable;
    private JScrollPane jScrollPane2;
    private JScrollPane jScrollPane3;
    private JPanel thermosPanel;
   // private JButton newButton;
    private JButton loadButton;
    private JButton resetButton;
    
    //Pulsanti di Avvio/Stop
	private JButton playButton;
	private JButton stopButton;
	
	//Pulsanti sulla striscia sinistra
	private JButton offButton;
	private JButton greenButton;
	private JButton relayButton;
	private JButton almButton;
	
	public final void initUI() {

		//Inizializzazione dei buttons
		GlobalSetting.INSTANCE.setManualControl(false);
		
		//Serial va inizializzato 
//		this.serial.getTx().put("?atxOn$");
		
		configuration = new Configuration();
		
		//Main frame
		setSize(960, 680);
        setTitle(AppConst.APPLICATION_NAME);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
	
        
		// North region
		JMenuBar menubar = createMenuBar();
        setJMenuBar(menubar);

        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false);
        
       
        
        ImageIcon playImage = new ImageIcon("images/play.png");
        playButton = new JButton(playImage);
        playButton.setBorder(new EmptyBorder(0, 3, 0, 3));
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                playActionPerformed(evt);
            }
        });
        toolbar.add(playButton);
        
        ImageIcon stopImage = new ImageIcon("images/stop.png");
        stopButton = new JButton(stopImage);
        stopButton.setBorder(new EmptyBorder(0, 3, 0, 3));
        playButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                //TODO:implementare action stop
            	//stopActionPerformed(evt);
            }
        });
        toolbar.add(stopButton);
        
        //Radio Manual/Auto control panel
  		JPanel radioPanel = createControlRadioButtons();
  		Dimension centerDimension = new Dimension(100, 20);
  		radioPanel.setPreferredSize(centerDimension);
  		
  		toolbar.add(radioPanel);
        
        add(toolbar, BorderLayout.NORTH);
        
        
        //West Region
        JToolBar vertical = new JToolBar(JToolBar.VERTICAL);
        vertical.setFloatable(false);
        vertical.setMargin(new Insets(10, 5, 5, 5));

        ImageIcon offImage = new ImageIcon("images/power.png");
		offButton = new JButton(offImage);
		offButton.addActionListener(this);
		offButton.setActionCommand("off");
		offButton.setBorder(new EmptyBorder(3, 0, 3, 0));

		ImageIcon greenImage = new ImageIcon("images/green.png");
		greenButton = new JButton(greenImage);
		greenButton.addActionListener(this);
		greenButton.setActionCommand("green");
		greenButton.setBorder(new EmptyBorder(3, 0, 3, 0));

		ImageIcon relaynImage = new ImageIcon("images/burn.png");
		relayButton = new JButton(relaynImage);
		relayButton.addActionListener(this);
		relayButton.setActionCommand("relay");
		relayButton.setBorder(new EmptyBorder(3, 0, 3, 0));

		ImageIcon almImage = new ImageIcon("images/alarm.png");
		almButton = new JButton(almImage);
		almButton.addActionListener(this);
		almButton.setActionCommand("alarm");
		almButton.setBorder(new EmptyBorder(3, 0, 3, 0));

		offButton.setEnabled(false);
		greenButton.setEnabled(false);
		relayButton.setEnabled(false);
		almButton.setEnabled(false);
		
		vertical.add(offButton);
        vertical.add(greenButton);
        vertical.add(relayButton);
        vertical.add(almButton);

        add(vertical, BorderLayout.WEST);
        
        
        //Center Region
        JPanel centerPanel = new JPanel();
        	
        mashTablePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Mash", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 0, 10))); // NOI18N
        mashTablePanel.setPreferredSize(new java.awt.Dimension(377, 320));

        mashTable.setFont(new java.awt.Font("Lucida Grande", 0, 10));
        mashTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Step", "°C", "Mins"
            }
        ));
        mashTable.setPreferredSize(null);
        jScrollPane2.setViewportView(mashTable);

        boilTable.setFont(new java.awt.Font("Lucida Grande", 0, 10));
        boilTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Step", "Mins"
            }
        ));
        boilTable.setPreferredSize(null);
        jScrollPane3.setViewportView(boilTable);

        loadButton.setFont(new Font("Lucida Grande", 3, 10));
        loadButton.setText("Load");
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                loadButtonActionPerformed(evt);
            }
        });

        resetButton.setFont(new Font("Lucida Grande", 3, 10));
        resetButton.setText("Reset");
        resetButton.setEnabled(false);
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                resetButtonActionPerformed(evt);
            }
        });

        GroupLayout mashTablePanelLayout = new GroupLayout(mashTablePanel);
        mashTablePanel.setLayout(mashTablePanelLayout);
        mashTablePanelLayout.setHorizontalGroup(
            mashTablePanelLayout.createParallelGroup(GroupLayout.LEADING)
            .add(mashTablePanelLayout.createSequentialGroup()
                .addContainerGap(112, Short.MAX_VALUE)
                .add(resetButton)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(loadButton)
                .add(20, 20, 20))
            .add(mashTablePanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(mashTablePanelLayout.createParallelGroup(GroupLayout.LEADING)
                    .add(jScrollPane2, GroupLayout.PREFERRED_SIZE, 325, GroupLayout.PREFERRED_SIZE)
                    .add(jScrollPane3, GroupLayout.PREFERRED_SIZE, 325, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mashTablePanelLayout.linkSize(new java.awt.Component[] {jScrollPane2, jScrollPane3}, GroupLayout.HORIZONTAL);

        mashTablePanelLayout.setVerticalGroup(
            mashTablePanelLayout.createParallelGroup(GroupLayout.LEADING)
            .add(mashTablePanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane2, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane3, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(mashTablePanelLayout.createParallelGroup(GroupLayout.BASELINE)
                    .add(loadButton)
                    .add(resetButton))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        mashTablePanelLayout.linkSize(new java.awt.Component[] {jScrollPane2, jScrollPane3}, GroupLayout.VERTICAL);

        thermosPanel.add(mashTablePanel);

        //Orologio
        timerPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Timer", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 0, 10))); // NOI18N
        timerPanel.setPreferredSize(new java.awt.Dimension(500, 210));

        
 		Box detailVerticalBox = Box.createVerticalBox();
		detailVerticalBox.add(thermosPanel);
		detailVerticalBox.add(timerPanel);
		
		Box summaryVerticalBox = Box.createVerticalBox();
		summaryVerticalBox.add(new JTextArea());
		
		centerPanel.add(detailVerticalBox);
		centerPanel.add(summaryVerticalBox);	
        
        add(centerPanel, BorderLayout.CENTER);
        

        
        //South Region
        JTextField temperature = new JTextField(10);
        JLabel statusbar = new JLabel(" Current temperature: " + temperature);
        statusbar.setPreferredSize(new Dimension(-1, 22));
        statusbar.setBorder(LineBorder.createGrayLineBorder());
        
        add(statusbar, BorderLayout.SOUTH);
        
		
		//http://zetcode.com/tutorials/javaswingtutorial/swinglayoutmanagement/

		// Automatic temperature control
//		sbc = new SingleByteCommunication();
//		sbc.initialize();
//
//		while (true) {
//			temperature.setText(String.valueOf(sbc.getTemperatura()));
//		}
		
	}
	
    /**
     * Azione associata al pulsante play
     *
     * @param evt evento del mouse (click)
     */
    private void playActionPerformed(ActionEvent evt) {
        if (!this.playButton.isSelected()) {
            this.playButton.setIcon(new ImageIcon(this.pathImages + "pause.png"));
            this.playButton.setSelected(true);
            if (this.timer.isAlive()) {
                this.timer.resume();
            } else {
                this.timer.start();
            }
        } else {
            this.playButton.setIcon(new ImageIcon(this.pathImages + "play.png"));
            this.playButton.setSelected(false);
            this.timer.suspend();
        }
    }
	
	 /**
     * Creazione dei modelli delle tabelle di mash e boil, necessarie per la visualizzazione
     * dello step attuale
     */
    public void createTableModel() {
        this.mashModel = new DefaultTableModel();
        this.mashModel.addColumn("Step");
        this.mashModel.addColumn("°C");
        this.mashModel.addColumn("Minutes");
        this.mashTable.setModel(this.mashModel);
        this.mashTable.setAutoCreateRowSorter(true);
        this.boilModel = new DefaultTableModel();
        this.boilModel.addColumn("Step");
        this.boilModel.addColumn("Minutes");
        this.boilTable.setModel(this.boilModel);
        this.boilTable.setAutoCreateRowSorter(true);

    }
	public App() {
		//Inizializzazione termometro mash
		initAll();
		initPaths();
        initUI();
        addThermoPanels();
        createTableModel();
    }

    private void addThermoPanels() {
    	mashPanel.add(this.mashThermo);
	}

	private void initAll() {
    	mashThermo = new Thermo("Mash");
    	
    	mashTable = new JTable();
        mashPanel = new JPanel();
        mashTablePanel = new JPanel();
        boilTable = new JTable();
        boilPanel = new JPanel();
        jScrollPane2 = new JScrollPane();
        jScrollPane3= new JScrollPane();
        loadButton = new JButton();
        resetButton = new JButton();
        thermosPanel = new JPanel();
        timerPanel = new JPanel();
        avvisiMash = new JCheckBox();
        
        recipe = new Recipe();
     	
        this.chrono = new TimerPanel();
        this.timer = null;
        this.timerPanel.add(chrono);
        
        systemTimerPanel = new JPanel();
	}
	
    /**
     * Inizializzazione percorso cartelle di appoggio (immagini etc)
     */
    public void initPaths() {
        String temp = System.getProperty("user.dir");
        temp = temp + "/images/";
        this.pathImages = temp;
    }
    
    /**
     * Creazione timer
     */
    public void initChrono() {
        this.chrono = new TimerPanel();
        this.timer = null;
        this.timerPanel.add(chrono);
    }
	
    /**
     * Inserisce il riassunto della ricetta all'interno delle tabelle
     */
    public void fixTable() {
        if (this.recipe.isCheck()) {
            for (int i = 0; i < this.recipe.getMash().size(); i++) {
                this.mashModel.addRow(new Object[]{
                            this.recipe.getMash().get(i).getNome(),
                            this.recipe.getMash().get(i).getEndTemp(),
                            this.recipe.getMash().get(i).getLength()
                        });
            }

            for (int i = 0; i < this.recipe.getHops().size(); i++) {
                this.boilModel.addRow(new Object[]{
                            this.recipe.getHops().get(i).getName(),
                            this.recipe.getHops().get(i).getMinutes()
                        });
            }
        }
    }
    
	/**
     * Azione relativa al pulsante load
     *
     * @param evt evento del mouse (click)
     */
    private void loadButtonActionPerformed(ActionEvent evt) {
        try {
            new Load(this.recipe);
        } catch (Exception ex) {
            this.log.error(ex);
        }
        if (this.recipe.isCheck()) {
            this.fixTable();
            int[] temp = {this.configuration.getSparegTemp(), this.configuration.getBoilTemp()};
            //Presa solo riga connessione seriale (connectionType = 0)
            this.timer = new Timers(chrono, recipe, /*list,*/ mashTable, boilTable, /*this.serial.getExecutor().getTemperature(), this.serial.getTx(),*/ 0, this.avvisiMash, /*this.avvisiBoil,*/ temp, this.configuration.getIsteresi(), this.pathImages);
            this.timer.setTimer(this.recipe.getMash().get(0).getLength().intValue());
            this.chrono.setSec_left(this.timer.getSec_left());
            this.chrono.setSec_right(this.timer.getSec_right());
            this.chrono.setMin_left(this.timer.getMin_left());
            this.chrono.setMin_right(this.timer.getMin_right());
            this.chrono.setHour_left(this.timer.getHour_left());
            this.chrono.setHour_mid(this.timer.getHour_mid());
            this.chrono.setHour_right(this.timer.getHour_right());
            this.playButton.setEnabled(true);
            this.loadButton.setEnabled(false);
            this.resetButton.setEnabled(true);
        }
    }
    
    /**
     * Azione relativa al pulsante reset
     *
     * @param evt evento del mouse (click)
     */
    private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int count = this.mashModel.getRowCount();
        for (int i = 0; i < count; i++) {
            this.mashModel.removeRow(0);
        }
        count = this.boilTable.getRowCount();
        for (int i = 0; i < count; i++) {
            this.boilModel.removeRow(0);
        }
        this.resetChrono();
        this.playButton.setEnabled(false);
        this.loadButton.setEnabled(true);
    }
    
    /**
     * Setta a 0 il cronometro
     */
    public void resetChrono() {
        this.chrono.setSec_left(0);
        this.chrono.setSec_right(0);
        this.chrono.setMin_left(0);
        this.chrono.setMin_right(0);
        this.chrono.setHour_left(0);
        this.chrono.setHour_mid(0);
        this.chrono.setHour_right(0);
        this.chrono.repaint();
    }

	public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                App ex = new App();
                ex.setVisible(true);
            }
        });
    }

	/**
	 * Radio buttons control creation
	 * @return Radio buttons panel
	 */
	private JPanel createControlRadioButtons() {
		// Create the radio buttons.
		String autoString = "Automatic Control";
		JRadioButton autoControlButton = new JRadioButton(autoString);
		autoControlButton.setMnemonic(KeyEvent.VK_A);
		autoControlButton.setActionCommand(autoString);
		autoControlButton.setSelected(true);

		String manualString = "Manual Control";
		JRadioButton manualControlButton = new JRadioButton(manualString);
		manualControlButton.setMnemonic(KeyEvent.VK_B);
		manualControlButton.setActionCommand(manualString);

		// Group the radio buttons.
		ButtonGroup beerDuinoControlGroup = new ButtonGroup();
		beerDuinoControlGroup.add(autoControlButton);
		beerDuinoControlGroup.add(manualControlButton);

		// Register a listener for the radio buttons.
		autoControlButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GlobalSetting.INSTANCE.setManualControl(false);
				offButton.setEnabled(false);
				greenButton.setEnabled(false);
				relayButton.setEnabled(false);
				almButton.setEnabled(false);
				timerPanel.setEnabled(false);
				thermosPanel.setEnabled(false);
				JOptionPane.showMessageDialog(App.this,
						"Automatic Control Enabled", "Control",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		manualControlButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GlobalSetting.INSTANCE.setManualControl(true);
				offButton.setEnabled(true);
				greenButton.setEnabled(true);
				relayButton.setEnabled(true);
				almButton.setEnabled(true);
				timerPanel.setEnabled(true);
				thermosPanel.setEnabled(true);
				JOptionPane.showMessageDialog(App.this,
						"Manual Control Enabled", "Control",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});

		JPanel radioPanel = new JPanel(new GridLayout(1, 1));
		radioPanel.add(autoControlButton);
		autoControlButton.setHorizontalAlignment(0);
		radioPanel.add(manualControlButton);
		manualControlButton.setHorizontalAlignment(0);
		return radioPanel;
	}

	/**
	 * Menu-bar creation
	 * 
	 * @return The created Menu-bar
	 */
	private JMenuBar createMenuBar() {

		JMenuBar mb = new JMenuBar();
		JMenu m1 = new JMenu("File");
		JMenu m2 = new JMenu("Help");
		mb.add(m1);
		mb.add(m2);

		// Exit
		JMenuItem m12 = new JMenuItem("Exit");
		m12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		m1.add(m12);
		// About...
		JMenuItem m21 = new JMenuItem("About BeerDuino...");
		m21.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane
						.showMessageDialog(
								App.this,
								"BeerDuino - Beer Mesh Temperature Controller. Developed by V3ryU1traPr0ff1c3©. All rights reserved.",
								"About", JOptionPane.PLAIN_MESSAGE);
			}
		});
		m2.add(m21);
		return mb;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if ("off".equals(e.getActionCommand())) {
			sbc.sendSingleByte(AppConst.ARDUINO_SHUTDOWN);
		}
		if ("green".equals(e.getActionCommand())) {
			sbc.sendSingleByte(AppConst.ARDUINO_GREEN_LED_ON);
		}
		if ("relay".equals(e.getActionCommand())) {
			sbc.sendSingleByte(AppConst.ARDUINO_RELAY_AND_YELLOW_LED_ON);
		}
		if ("alarm".equals(e.getActionCommand())) {
			sbc.sendSingleByte(AppConst.ARDUINO_ALARM_ON);
		}
	}

}
