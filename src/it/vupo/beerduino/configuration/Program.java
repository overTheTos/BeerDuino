package it.vupo.beerduino.configuration;

//Inutility Class - Only for Arduino Program Loaded On
public class Program {

	/*#include <OneWire.h>
	#include <DallasTemperature.h>

	// Data wire is plugged into pin 6 on the Arduinotemperature
	#define ONE_WIRE_BUS 6

	const int LED_GREEN_PIN = 7;
	const int RELAY_PIN = 8;
	const int LED_RED_PIN = 9;
	const int LED_YELLOW_PIN = 10;

	// Setup a oneWire instance to communicate with any OneWire devices 
	// (not just Maxim/Dallas temperature ICs)
	OneWire oneWire(ONE_WIRE_BUS);

	// Pass our oneWire reference to Dallas Temperature.
	DallasTemperature sensors(&oneWire);

	char c = 0;

	void setup(){
	  pinMode(LED_GREEN_PIN, OUTPUT);
	  pinMode(LED_YELLOW_PIN, OUTPUT);
	  pinMode(LED_RED_PIN, OUTPUT);
	  pinMode(RELAY_PIN, OUTPUT);
	  sensors.begin();
	  Serial.begin(9600);
	}

	void loop(){
	  // call sensors.requestTemperatures() to issue a global temperature
	  // request to all devices on the bus
	  sensors.requestTemperatures(); // Send the command to get temperatures
	  // Why "byIndex"? 
	  // You can have more than one IC on the same bus. 
	  // 0 refers to the first IC on the wire
	  float temperature = sensors.getTempCByIndex(0);
	  //Serial.println(temperature); 

	  Serial.print(temperature);
	  //delay(1000);  
	  // Wait for a character to arrive at the serial port.
	  if( Serial.available() > 0 )
	  {
	    // Read one byte (character).
	    c = Serial.read();
	    switch( c )
	    {
	    case '0':
	      //ARDUINO_SHUTDOWN
	      digitalWrite( LED_GREEN_PIN, LOW );
	      digitalWrite( LED_YELLOW_PIN, LOW );
	      digitalWrite( RELAY_PIN, LOW );
	      digitalWrite( LED_RED_PIN, LOW );
	      break;
	    case '1':
	      //ARDUINO_GREEN_LED_ON
	      digitalWrite( LED_GREEN_PIN, HIGH );
	      digitalWrite( LED_YELLOW_PIN, LOW );
	      digitalWrite( RELAY_PIN, LOW );
	      digitalWrite( LED_RED_PIN, LOW );
	      break;
	    case '2':
	      //ARDUINO_RELAY_ON - ARDUINO_YELLOW_LED_ON
	      digitalWrite( LED_GREEN_PIN, LOW );
	      digitalWrite( LED_YELLOW_PIN, HIGH );
	      digitalWrite( RELAY_PIN, HIGH );
	      digitalWrite( LED_RED_PIN, LOW );
	      break;
	    case '3':
	      //ARDUINO_ALARM_ON 
	      digitalWrite( LED_GREEN_PIN, LOW );
	      digitalWrite( LED_YELLOW_PIN, LOW );
	      digitalWrite( RELAY_PIN, LOW );
	      digitalWrite( LED_RED_PIN, HIGH );
	      break;

	      //case 'q':
	      //case 'Q':
	      // Send the reading from the analog pin throught the serial port.
	      //  Serial.println( analogRead( analogPin ) );
	      //  break;
	    }
	  }
	}*/
	
}
