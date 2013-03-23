package it.vupo.beerduino;

import it.vupo.beerduino.configuration.AppConst;
import it.vupo.beerduino.configuration.GlobalSetting;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

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

		JButton burnerButton = new JButton("Burner On");
		burnerButton.addActionListener(this);
		burnerButton.setActionCommand("burner");

		JButton acidButton = new JButton("Acid Rest");
		acidButton.addActionListener(this);
		acidButton.setActionCommand("acid");

		JButton proteinButton = new JButton("Protein Rest");
		proteinButton.addActionListener(this);
		proteinButton.setActionCommand("protein");

		JButton sacchaButton = new JButton("Saccha On");
		sacchaButton.addActionListener(this);
		sacchaButton.setActionCommand("saccha");

		JButton mashButton = new JButton("Mash Out");
		mashButton.addActionListener(this);
		mashButton.setActionCommand("mashout");

		JButton appnameButton = new JButton("App Name");
		appnameButton.addActionListener(this);
		appnameButton.setActionCommand("appname");

		// JButton reset = new JButton("Reset");
		panel.add(label);// Components Added using Flow Layout
		// panel.add(tf);
		panel.add(offButton);
		panel.add(greenButton);
		panel.add(relayButton);
		panel.add(almButton);
		panel.add(burnerButton);
		panel.add(acidButton);
		panel.add(proteinButton);
		panel.add(sacchaButton);
		panel.add(mashButton);
		panel.add(appnameButton);

		// panel.add(reset);
		// Text Area at the Center
		JPanel centerPanel = new JPanel();
		Dimension centerDimension = new Dimension(1024, 350);
		centerPanel.setPreferredSize(centerDimension);
		centerPanel.add(radioPanel);

		JTextField temperature = new JTextField(20);

		centerPanel.add(temperature);
		// Adding Components to the frame.
		frame.getContentPane().add(BorderLayout.NORTH, mb);
		frame.getContentPane().add(BorderLayout.CENTER, centerPanel);
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
		JMenuItem m11 = new JMenuItem("New...");
		m1.add(m11);
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
		if ("burner".equals(e.getActionCommand())) {
			sbc.sendSingleByte(AppConst.ARDUINO_WRITE_BURNER_ON);
		}
		if ("acid".equals(e.getActionCommand())) {
			sbc.sendSingleByte(AppConst.ARDUINO_WRITE_ACID_REST);
		}
		if ("protein".equals(e.getActionCommand())) {
			sbc.sendSingleByte(AppConst.ARDUINO_WRITE_PROTEIN_REST);
		}
		if ("saccha".equals(e.getActionCommand())) {
			sbc.sendSingleByte(AppConst.ARDUINO_WRITE_SACCHARIFICATION);
		}
		if ("mashout".equals(e.getActionCommand())) {
			sbc.sendSingleByte(AppConst.ARDUINO_WRITE_MASH_OUT);
		}
		if ("appname".equals(e.getActionCommand())) {
			sbc.sendSingleByte(AppConst.ARDUINO_WRITE_BEERDUINO);
		}
	}

}
