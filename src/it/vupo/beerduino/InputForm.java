package it.vupo.beerduino;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InputForm extends JPanel{
	
	private static final long serialVersionUID = -5584764145670638738L;
	
	private static int DEFAULT_VALUE = 0;
	private NumberFormat numberFormat;
	
	private FormVerifier formVerifier = new FormVerifier();
	
	private JLabel stepNameLabel = new JLabel();
	private JLabel stepTimeLabel = new JLabel();
	private JLabel restTimeLabel = new JLabel();
	private JLabel restStartTempLabel = new JLabel();
	private JLabel restStopTempLabel = new JLabel();
	
	private JTextField stepNameField = new JTextField(20);
	private JTextField stepTimeField = new JTextField(5); 
	private JTextField restTimeField = new JTextField(5); 
	private JTextField restStartTempField = new JTextField(5); 
	private JTextField restStopTempField = new JTextField(5); 
	
	private JButton confirmBtn = new JButton();
	private JButton exitBtn = new JButton();
	
	public InputForm(){
		super(new BorderLayout());
        setUpFormats();
        
		//Labels
		stepNameLabel.setText("Step Name:");
		stepTimeLabel.setText("Step Time:");
		restTimeLabel.setText("Rest Time:");
		restStartTempLabel.setText("Rest Start Temp:");
		restStopTempLabel.setText("Rest Stop Temp:");
		
		//Fields
		stepTimeField.setInputVerifier(formVerifier);
		restTimeField.setInputVerifier(formVerifier);
		restStartTempField.setInputVerifier(formVerifier);
		restStopTempField.setInputVerifier(formVerifier);
		
		//Action listeners
		stepTimeField.addActionListener(formVerifier);
		restTimeField.addActionListener(formVerifier);
		restStartTempField.addActionListener(formVerifier);
		restStopTempField.addActionListener(formVerifier);
		
		//Link labels to fields
		stepNameLabel.setLabelFor(stepNameField);
		stepTimeLabel.setLabelFor(stepTimeField);
		restTimeLabel.setLabelFor(restTimeField);
		restStartTempLabel.setLabelFor(restStartTempField);
		restStopTempLabel.setLabelFor(restStopTempField);
		
		//Buttons
		confirmBtn.setText("Ok");
		confirmBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Object[] dataInsert = { 
				false,
				stepNameField.getText(),
				stepTimeField.getText(),
				restTimeField.getText(),
				restStartTempField.getText(),
				restStopTempField.getText(),
				0};
				
				System.out.println("Current step: "+ dataInsert[0]);
				System.out.println("Step name: "+ dataInsert[1]);
				System.out.println("Step time: "+ dataInsert[2]);
				System.out.println("Rest time: "+ dataInsert[3]);
				System.out.println("Rest start temp: "+ dataInsert[4]);
				System.out.println("Rest stop temp: "+ dataInsert[5]);
				System.out.println("Step elapsed time: "+ dataInsert[6]);
				
				
			}
		});
		
		exitBtn.setText("Exit");
		exitBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
								
			}
		});
		
		
		JPanel labelPane = new JPanel(new GridLayout(0,1));
	    labelPane.add(stepNameLabel);
	    labelPane.add(stepTimeLabel);
	    labelPane.add(restTimeLabel);
	    labelPane.add(restStartTempLabel);
	    labelPane.add(restStopTempLabel);
	    
	    JPanel fieldPane = new JPanel(new GridLayout(0,1));
	    fieldPane.add(stepNameField);
	    fieldPane.add(stepTimeField);
	    fieldPane.add(restTimeField);
	    fieldPane.add(restStartTempField);
	    fieldPane.add(restStopTempField);
	    
	    JPanel btnPane = new JPanel(new GridLayout(0,1));
	    btnPane.add(confirmBtn);
	    btnPane.add(exitBtn);
	    
	    //Put the panels in this panel, labels on left,
	    //text fields on right.
	    setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
	    add(labelPane, BorderLayout.CENTER);
	    add(fieldPane, BorderLayout.LINE_END);
	    add(btnPane, BorderLayout.SOUTH);
	}
	
	private void setUpFormats() {
        numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setParseIntegerOnly(true);
	}
	
    class FormVerifier extends InputVerifier
    implements ActionListener {

    	int MIN_TEMP = 1;
    	int MAX_TEMP = 90;
    	int MIN_TIME = 0;
    	int MAX_TIME = 999;
    	
    	public boolean shouldYieldFocus(JComponent input) {
            boolean inputOK = verify(input);
            makeItPretty(input);
             
            if (inputOK) {
                return true;
            } else {
                Toolkit.getDefaultToolkit().beep();
                return false;
            }
        }
    	
    	@Override
        public boolean verify(JComponent input) {
            return checkField(input, false);
        }
         
        protected void makeItPretty(JComponent input) {
            checkField(input, true);
        }
         
        protected boolean checkField(JComponent input, boolean changeIt) {
            if (input == stepTimeField) {
                return checkTimeField(changeIt, stepTimeField);
            } else if (input == restTimeField) {
                return checkTimeField(changeIt, restTimeField);
            } else if (input == restStartTempField) {
                return checkTempField(changeIt, restStartTempField);
            } else if (input == restStopTempField) {
                return checkTempField(changeIt, restStopTempField);
            } else {
                return true;
            }
        }
        
        protected boolean checkTempField(boolean change, JTextField field) {
            boolean wasValid = true;
            int defaultValue = DEFAULT_VALUE;
            
            //Parse the value.
            try {
            	defaultValue = numberFormat.parse(field.getText()).intValue();
            } catch (ParseException pe) {
                wasValid = false;
                pe.printStackTrace();
            }
             
            //Value was invalid.
            if ((defaultValue < MIN_TEMP) || (defaultValue > MAX_TEMP)) {
                wasValid = false;
                if (change) {
                    if (defaultValue < MIN_TEMP) {
                    	defaultValue = MIN_TEMP;
                    } else { // numPeriods is greater than MAX_PERIOD
                    	defaultValue = MAX_TEMP;
                    }
                }
            }
             
            //Whether value was valid or not, format it nicely.
            if (change) {
            	field.setText(numberFormat.format(defaultValue));
            	field.selectAll();
            }
             
            return wasValid;
        }
        
        protected boolean checkTimeField(boolean change, JTextField field) {
            boolean wasValid = true;
            int defaultValue = DEFAULT_VALUE;
            
            //Parse the value.
            try {
            	defaultValue = numberFormat.parse(field.getText()).intValue();
            } catch (ParseException pe) {
                wasValid = false;
                pe.printStackTrace();
            }
             
            //Value was invalid.
            if ((defaultValue < MIN_TIME) || (defaultValue > MAX_TIME)) {
                wasValid = false;
                if (change) {
                    if (defaultValue < MIN_TIME) {
                    	defaultValue = MIN_TIME;
                    } else { // numPeriods is greater than MAX_PERIOD
                    	defaultValue = MAX_TIME;
                    }
                }
            }
             
            //Whether value was valid or not, format it nicely.
            if (change) {
            	field.setText(numberFormat.format(defaultValue));
            	field.selectAll();
            }
             
            return wasValid;
        }
        
        @Override 
        public void actionPerformed(ActionEvent e) {
            JTextField source = (JTextField)e.getSource();
            shouldYieldFocus(source); //ignore return value
            source.selectAll();
        }
    }
    

}
