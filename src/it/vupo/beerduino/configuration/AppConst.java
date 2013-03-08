package it.vupo.beerduino.configuration;

public class AppConst {

	public final static String APPLICATION_NAME = "BeerDuino";
	
	/**
	 * Turn off all the components
	 */
	public final static byte ARDUINO_SHUTDOWN = '0';
	
	/**
	 * Light the green led
	 */
	public final static byte ARDUINO_GREEN_LED_ON = '1';
	
	/**
	 * Switch on the relay and light the yellow led
	 */
	public final static byte ARDUINO_RELAY_AND_YELLOW_LED_ON = '2';
 
	/**
	 * Light the red led and switch on the buzzer 
	 */
	public final static byte ARDUINO_ALARM_ON = '3';
	
	/**
	 * Write on LCD "Burner On"
	 */
	public final static byte ARDUINO_WRITE_BURNER_ON = '4';
	
	/**
	 * Write on LCD "Acid Rest"
	 */
	public final static byte ARDUINO_WRITE_ACID_REST = '5';
	
	/**
	 * Write on LCD "Protein Rest"
	 */
	public final static byte ARDUINO_WRITE_PROTEIN_REST = '6';
	
	/**
	 * Write on LCD "Saccharification"
	 */
	public final static byte ARDUINO_WRITE_SACCHARIFICATION = '7';
	
	/**
	 * Write on LCD "Mash Out"
	 */
	public final static byte ARDUINO_WRITE_MASH_OUT = '8';
	
	/**
	 * Write on LCD "BeerDuino"
	 */
	public final static byte ARDUINO_WRITE_BEERDUINO = '9';
	
}
