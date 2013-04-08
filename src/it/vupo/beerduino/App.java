package it.vupo.beerduino;

import it.vupo.beerduino.configuration.AppConst;
import it.vupo.beerduino.configuration.GlobalSetting;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
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
import javax.swing.table.DefaultTableCellRenderer;

public class App extends JFrame implements ActionListener {

	private SingleByteCommunication sbc;

	public App() {

        initUI();
    }
	
	public final void initUI() {

		//Main frame
		GlobalSetting.INSTANCE.setManualControl(false);
//		JFrame frame = new JFrame(AppConst.APPLICATION_NAME);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setSize(800, 600);
		
		
		setSize(800, 600);
        setTitle(AppConst.APPLICATION_NAME);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
		
		// North region
		JMenuBar menubar = createMenuBar();
        setJMenuBar(menubar);

        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false);

        ImageIcon addImage = new ImageIcon("images/add.png");
        JButton addButton = new JButton(addImage);
        addButton.setBorder(new EmptyBorder(0, 3, 0, 3));
        toolbar.add(addButton);
        
        ImageIcon removeImage = new ImageIcon("images/remove.png");
        JButton removeButton = new JButton(removeImage);
        removeButton.setBorder(new EmptyBorder(0, 3, 0, 3));
        toolbar.add(removeButton);
        
        ImageIcon playImage = new ImageIcon("images/play.png");
        JButton playButton = new JButton(playImage);
        playButton.setBorder(new EmptyBorder(0, 3, 0, 3));
        toolbar.add(playButton);
        
        ImageIcon stopImage = new ImageIcon("images/stop.png");
        JButton stopButton = new JButton(stopImage);
        addButton.setBorder(new EmptyBorder(0, 3, 0, 3));
        toolbar.add(stopButton);
        
        add(toolbar, BorderLayout.NORTH);
        
        
        //West Region
        JToolBar vertical = new JToolBar(JToolBar.VERTICAL);
        vertical.setFloatable(false);
        vertical.setMargin(new Insets(10, 5, 5, 5));

        ImageIcon offImage = new ImageIcon("images/power.png");
		JButton offButton = new JButton(offImage);
		offButton.addActionListener(this);
		offButton.setActionCommand("off");
		offButton.setBorder(new EmptyBorder(3, 0, 3, 0));

		ImageIcon greenImage = new ImageIcon("images/green.png");
		JButton greenButton = new JButton(greenImage);
		greenButton.addActionListener(this);
		greenButton.setActionCommand("green");
		greenButton.setBorder(new EmptyBorder(3, 0, 3, 0));

		ImageIcon relaynImage = new ImageIcon("images/burn.png");
		JButton relayButton = new JButton(relaynImage);
		relayButton.addActionListener(this);
		relayButton.setActionCommand("relay");
		relayButton.setBorder(new EmptyBorder(3, 0, 3, 0));

		ImageIcon almImage = new ImageIcon("images/alarm.png");
		JButton almButton = new JButton(almImage);
		almButton.addActionListener(this);
		almButton.setActionCommand("alarm");
		almButton.setBorder(new EmptyBorder(3, 0, 3, 0));

		vertical.add(offButton);
        vertical.add(greenButton);
        vertical.add(relayButton);
        vertical.add(almButton);

        add(vertical, BorderLayout.WEST);
        
        
        //Center Region
        JPanel centerPanel = new JPanel();
        	
        	// Radio Manual/Auto control panel
     		JPanel radioPanel = createControlRadioButtons();
     		Dimension centerDimension = new Dimension(100, 500);
     		radioPanel.setPreferredSize(centerDimension);
     		
     		//Table mash steps
     		Box table = createTable();
     		
 		Box detailVerticalBox = Box.createVerticalBox();
		detailVerticalBox.add(radioPanel);
		detailVerticalBox.add(table);
		
		Box summaryVerticalBox = Box.createVerticalBox();
		summaryVerticalBox.add(new JTextArea());
		
		//Box horizontalBox = Box.createHorizontalBox();
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

		
		
        //frame.setVisible(true);
		
		
		
		
		
		// Adding Components to the frame.
//		frame.getContentPane().add(BorderLayout.NORTH, mb);
//		frame.getContentPane().add(BorderLayout.CENTER, horizontalBox);
//		frame.getContentPane().add(BorderLayout.SOUTH, panel);
//		frame.setVisible(true);
		

		// Automatic temperature control
		sbc = new SingleByteCommunication();
		sbc.initialize();

		while (true) {
			temperature.setText(String.valueOf(sbc.getTemperatura()));
		}
		
	}
	
	public static void main(String args[]) {
		//new App();
		
		SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                App application = new App();
                application.setVisible(true);
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
				JOptionPane.showMessageDialog(App.this,
						"Automatic Control Enabled", "Control",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		manualControlButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GlobalSetting.INSTANCE.setManualControl(true);
				JOptionPane.showMessageDialog(App.this,
						"Manual Control Enabled", "Control",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});

		JPanel radioPanel = new JPanel(new GridLayout(0, 1));
		radioPanel.add(autoControlButton);
		radioPanel.add(manualControlButton);
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

	
	
	public Box createTable(){
		
		String[] columnNames = {"Current Step",
				                "Step Name",
				                "Step Time",
				                "Rest Time",
				                "Rest Start Temp",
				                "Rest Stop Temp",
				                "Step Elapsed Time"};
		
		Object[][] data = {{false, "Acid Rest", 0, 15, 35, 35, 0},
		        {false, "Protein Rest", 7, 20, 42, 42, 0},
		        {true, "Saccharification", 24, 60, 66, 66, 0},
		        {false, "Mash Out", 12, 15, 78, 78, 0}
		        };
		
		final JTable table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment( JLabel.RIGHT );
        table.getColumnModel().getColumn(2).setCellRenderer( rightRenderer );
        table.getColumnModel().getColumn(3).setCellRenderer( rightRenderer );
        table.getColumnModel().getColumn(4).setCellRenderer( rightRenderer );
        table.getColumnModel().getColumn(5).setCellRenderer( rightRenderer );
        table.getColumnModel().getColumn(6).setCellRenderer( rightRenderer );
        
        
        JButton addRow = new JButton();
        addRow.setText("Add Step");
        addRow.setToolTipText("Add a step to the mash");
        addRow.setVerticalTextPosition(AbstractButton.CENTER);
        addRow.setHorizontalTextPosition(AbstractButton.LEADING);
        addRow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				createAndShowGUI(); 
			}
		});
        
        JButton removeRow = new JButton();
        removeRow.setText("Remove Step");
        removeRow.setToolTipText("Remove a step to the mash");
        removeRow.setVerticalTextPosition(AbstractButton.CENTER);
        removeRow.setHorizontalTextPosition(AbstractButton.LEADING);
        removeRow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO: action
			}
		});
        
        JScrollPane pane = new JScrollPane(table);
        
        Box hBox = Box.createHorizontalBox();
        hBox.add(addRow);
        hBox.add(Box.createRigidArea(new Dimension(5, 0)));
        hBox.add(removeRow);
        
        Box box = Box.createVerticalBox();
        box.add(pane);
        box.add(hBox);
        
        
                
        return box;
	}
	
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("InputForm");
         
        //Create and set up the content pane.
        JComponent newContentPane = new InputForm();
        newContentPane.setOpaque(true); 
        //content panes must be opaque
        frame.setContentPane(newContentPane);
         
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}
