package it.vupo.beerduino;

import it.vupo.beerduino.configuration.AppConst;
import it.vupo.beerduino.configuration.GlobalSetting;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
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
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;

import sun.awt.VerticalBagLayout;

public class App extends JFrame implements ActionListener {

	private SingleByteCommunication sbc;

	public static void main(String args[]) {
		new App();
	}

	App() {
		// Start with automatic temperature control
		GlobalSetting.INSTANCE.setManualControl(false);

		JFrame frame = new JFrame(AppConst.APPLICATION_NAME);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setSize(1024, 768);

		// Creating the MenuBar and adding components
		JMenuBar mb = createMenuBar();

		// Creating the panel at bottom and adding components
		JPanel panel = new JPanel(); // the panel is not visible in output

		// In initialization code:

		// Put the radio buttons in a column in a panel.
		JPanel radioPanel = createControlRadioButtons();
		
		JLabel label = new JLabel("Actions");
		// JTextField tf = new JTextField(10);// accepts upto 10 characters

		JButton offButton = new JButton("OFF");
		offButton.addActionListener(this);
		offButton.setActionCommand("off");

		JButton greenButton = new JButton("Green Led");
		greenButton.addActionListener(this);
		greenButton.setActionCommand("green");

		JButton relayButton = new JButton("Relay On");
		relayButton.addActionListener(this);
		relayButton.setActionCommand("relay");

		JButton almButton = new JButton("Alarm");
		almButton.addActionListener(this);
		almButton.setActionCommand("alarm");

		// JButton reset = new JButton("Reset");
		panel.add(label);// Components Added using Flow Layout
		// panel.add(tf);
		panel.add(offButton);
		panel.add(greenButton);
		panel.add(relayButton);
		panel.add(almButton);
		
		Dimension centerDimension = new Dimension(1024, 350);
		radioPanel.setPreferredSize(centerDimension);
		
		JTextField temperature = new JTextField(20);
		
		JTable table = createTable();
		JScrollPane tableScrollPane = new JScrollPane(table);
		
		Box detailVerticalBox = Box.createVerticalBox();
		detailVerticalBox.add(radioPanel);
		detailVerticalBox.add(tableScrollPane);
		
		Box summaryVerticalBox = Box.createVerticalBox();
		summaryVerticalBox.add(temperature);
		
		Box horizontalBox = Box.createHorizontalBox();
		horizontalBox.add(detailVerticalBox);
		horizontalBox.add(summaryVerticalBox);
		
		// Adding Components to the frame.
		frame.getContentPane().add(BorderLayout.NORTH, mb);
		frame.getContentPane().add(BorderLayout.CENTER, horizontalBox);
		frame.getContentPane().add(BorderLayout.SOUTH, panel);
		frame.setVisible(true);

		// pack();
		sbc = new SingleByteCommunication();
		sbc.initialize();

		while (true) {
			temperature.setText(String.valueOf(sbc.getTemperatura()));
		}
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

	private Object[][] data;
	
	public JTable createTable(){
		
		String[] columnNames = {"Current Step",
				                "Step Name",
				                "Step Time",
				                "Rest Time",
				                "Rest Start Temp",
				                "Rest Stop Temp",
				                "Step Elapsed Time"};
		
//		data = {
//		        {false, "Acid Rest", 0, 15, 35, 35, 0},
//		        {false, "Protein Rest", 7, 20, 42, 42, 0},
//		        {true, "Saccharification", 24, 60, 66, 66, 0},
//		        {false, "Mash Out", 12, 15, 78, 78, 0}
//		        };
		
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
        
        
        //TODO: FINIRE BUTTON 
        JButton addRow = new JButton();
        addRow.setText("Add");
        addRow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				createAndShowGUI(); 
			}
		});
        
        return table;
	}
	
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("InputForm");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         
        //Create and set up the content pane.
        JComponent newContentPane = new InputForm();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
         
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}
