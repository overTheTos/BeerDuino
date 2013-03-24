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
}
