package it.vupo.beerduino;

import it.vupo.beerduino.configuration.AppConst;
import it.vupo.beerduino.configuration.GlobalSetting;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class App extends JFrame implements ActionListener {

	private SingleByteCommunication sbc;

	public static void main(String args[]) {
		new App();
	}

	App() {
		GlobalSetting.INSTANCE.setManualControl(false);
		JFrame frame = new JFrame(AppConst.APPLICATION_NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);

		// Creating the MenuBar and adding components
		JMenuBar mb = new JMenuBar();
		JMenu m1 = new JMenu("File");
		JMenu m2 = new JMenu("Help");
		mb.add(m1);
		mb.add(m2);
		JMenuItem m11 = new JMenuItem("New...");
		JMenuItem m12 = new JMenuItem("Exit");
		m1.add(m11);
		m1.add(m12);
		JMenuItem m21 = new JMenuItem("About BeerDuino...");
		m2.add(m21);
		
		// Creating the panel at bottom and adding components
		JPanel panel = new JPanel(); // the panel is not visible in output
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
		JTextField ta = new JTextField();
		// Adding Components to the frame.
		frame.getContentPane().add(BorderLayout.SOUTH, panel);
		frame.getContentPane().add(BorderLayout.NORTH, mb);
		frame.getContentPane().add(BorderLayout.CENTER, ta);
		frame.setVisible(true);

		// pack();
		sbc = new SingleByteCommunication();
		sbc.initialize();

		while (true) {
			ta.setText(String.valueOf(sbc.getTemperatura()));
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if ("off".equals(e.getActionCommand())) {
			GlobalSetting.INSTANCE.setManualControl(true);
			sbc.sendSingleByte(AppConst.ARDUINO_SHUTDOWN);
		}
		if ("green".equals(e.getActionCommand())) {
			GlobalSetting.INSTANCE.setManualControl(true);
			sbc.sendSingleByte(AppConst.ARDUINO_GREEN_LED_ON);
		} 
		if ("relay".equals(e.getActionCommand())) {
			GlobalSetting.INSTANCE.setManualControl(true);
			sbc.sendSingleByte(AppConst.ARDUINO_RELAY_AND_YELLOW_LED_ON);
		}
		if ("alarm".equals(e.getActionCommand())) {
			GlobalSetting.INSTANCE.setManualControl(true);
			sbc.sendSingleByte(AppConst.ARDUINO_ALARM_ON);
		} 
		if ("burner".equals(e.getActionCommand())) {
			GlobalSetting.INSTANCE.setManualControl(true);
			sbc.sendSingleByte(AppConst.ARDUINO_WRITE_BURNER_ON);
		}
		if ("acid".equals(e.getActionCommand())) {
			GlobalSetting.INSTANCE.setManualControl(true);
			sbc.sendSingleByte(AppConst.ARDUINO_WRITE_ACID_REST);
		}
		if ("protein".equals(e.getActionCommand())) {
			GlobalSetting.INSTANCE.setManualControl(true);
			sbc.sendSingleByte(AppConst.ARDUINO_WRITE_PROTEIN_REST);
		} 
		if ("saccha".equals(e.getActionCommand())) {
			GlobalSetting.INSTANCE.setManualControl(true);
			sbc.sendSingleByte(AppConst.ARDUINO_WRITE_SACCHARIFICATION);
		}
		if ("mashout".equals(e.getActionCommand())) {
			GlobalSetting.INSTANCE.setManualControl(true);
			sbc.sendSingleByte(AppConst.ARDUINO_WRITE_MASH_OUT);
		}
		if ("appname".equals(e.getActionCommand())) {
			GlobalSetting.INSTANCE.setManualControl(true);
			sbc.sendSingleByte(AppConst.ARDUINO_WRITE_BEERDUINO);
		}
		}

}
