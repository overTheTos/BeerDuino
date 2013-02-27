package it.vupo;

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
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class App extends JFrame implements ActionListener {

	private SingleByteCommunication sbc;

	public static void main(String args[]) {
		new App();
	}

	App() {
		JFrame frame = new JFrame("BEER-DUINO");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 400);

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
		
		JButton almButton = new JButton("Alarm");
		almButton.addActionListener(this);
		almButton.setActionCommand("alarm");
		
		JButton warnButton = new JButton("Relay On");
		warnButton.addActionListener(this);
		warnButton.setActionCommand("relay");
		
		// JButton reset = new JButton("Reset");
		panel.add(label);// Components Added using Flow Layout
		// panel.add(tf);
		panel.add(almButton);
		panel.add(warnButton);
		
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
		if ("alarm".equals(e.getActionCommand())) {
			sbc.sendSingleByte((byte) '3');
		} 
		if ("relay".equals(e.getActionCommand())) {
			sbc.sendSingleByte((byte) '2');
		}

	}

}
