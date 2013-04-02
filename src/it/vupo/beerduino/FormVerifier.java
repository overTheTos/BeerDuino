package it.vupo.beerduino;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.InputVerifier;
import javax.swing.JComponent;

public class FormVerifier extends InputVerifier
implements ActionListener {

	int MIN_TEMP = 1	;
	int MAX_TEMP = 90;
	int MIN_TIME = 1;
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
	
	//This method checks input, but should cause no side effects.
    public boolean verify(JComponent input) {
        return checkField(input, false);
    }
     
    protected void makeItPretty(JComponent input) {
        checkField(input, true);
    }
     
    protected boolean checkField(JComponent input, boolean changeIt) {
        if (input == amountField) {
            return checkTimeField(changeIt);
        } else if (input == numPeriodsField) {
            return checkTempField(changeIt);
        } else {
            return true; //shouldn't happen
        }
    }
    
    protected boolean checkTempField(boolean change) {
        boolean wasValid = true;
         
        //Parse the value.
        try {
            numPeriods = decimalFormat.parse(numPeriodsField.getText()).
                    intValue();
        } catch (ParseException pe) {
            wasValid = false;
            pe.printStackTrace();
        }
         
        //Value was invalid.
        if ((numPeriods < MIN_TEMP) || (numPeriods > MAX_TEMP)) {
            wasValid = false;
            if (change) {
                if (numPeriods < MIN_TEMP) {
                    numPeriods = MIN_TEMP;
                } else { // numPeriods is greater than MAX_PERIOD
                    numPeriods = MAX_TEMP;
                }
            }
        }
         
        //Whether value was valid or not, format it nicely.
        if (change) {
            numPeriodsField.setText(decimalFormat.format(numPeriods));
            numPeriodsField.selectAll();
        }
         
        return wasValid;
    }
     
    public void actionPerformed(ActionEvent e) {
        JTextField source = (JTextField)e.getSource();
        shouldYieldFocus(source); //ignore return value
        source.selectAll();
    }
}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}



}
