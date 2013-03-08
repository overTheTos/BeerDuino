package it.vupo.beerduino.configuration;

//Inutility Class - Only for Arduino Program Loaded On
public class Program {

	/*#include <LiquidCrystal.h>
	#include <OneWire.h>
	#include <DallasTemperature.h>

	// Data wire is plugged into pin 6 on the Arduinotemperature
	#define ONE_WIRE_BUS 6

	const int LED_GREEN_PIN = 7;
	const int RELAY_PIN = 8;
	const int LED_RED_PIN = 9;
	const int LED_YELLOW_PIN = 10;
	//const float RELAY_ON_TEMP = 22.0;
	//const float RELAY_OFF_TEMP = 24.0;

	// Setup a oneWire instance to communicate with any OneWire devices 
	// (not just Maxim/Dallas temperature ICs)
	OneWire oneWire(ONE_WIRE_BUS);

	// Pass our oneWire reference to Dallas Temperature.
	DallasTemperature sensors(&oneWire);

	LiquidCrystal lcd(12, 11, 5, 4, 3, 2);

	char c = 0;

	void setup(){
	  lcd.begin(16, 2);
	  lcd.clear();
	  lcd.print("BeerDuino");
	  pinMode(LED_GREEN_PIN, OUTPUT);
	  pinMode(LED_YELLOW_PIN, OUTPUT);
	  pinMode(LED_RED_PIN, OUTPUT);
	  pinMode(RELAY_PIN, OUTPUT);
	  sensors.begin();
	  Serial.begin(9600);
	}

	void loop(){
	  lcd.setCursor(0, 1);
	  // call sensors.requestTemperatures() to issue a global temperature
	  // request to all devices on the bus
	  sensors.requestTemperatures(); // Send the command to get temperatures
	  // Why "byIndex"? 
	  // You can have more than one IC on the same bus. 
	  // 0 refers to the first IC on the wire
	  float temperature = sensors.getTempCByIndex(0);
	  //Serial.println(temperature); 

	  Serial.print(temperature);
	  lcd.print(temperature);  
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
	      lcd.setCursor(0, 0);
	      lcd.clear();
	      lcd.print("End"); 
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
	      lcd.setCursor(0, 0);
	      lcd.clear();
	      lcd.print("Alarm! Alarm!"); 
	      break;
	    case '4':
	      //ARDUINO_WRITE_BURNER_ON
	      lcd.setCursor(0, 0);
	      lcd.clear();
	      lcd.print("Burner On"); 
	      break;
	    case '5':
	      //ARDUINO_WRITE_ACID_REST
	      lcd.setCursor(0, 0);
	      lcd.clear();
	      lcd.print("Acid Rest"); 
	      break;
	    case '6':
	      //ARDUINO_WRITE_PROTEIN_REST
	      lcd.setCursor(0, 0);
	      lcd.clear();
	      lcd.print("Protein Rest"); 
	      break;
	    case '7':
	      //ARDUINO_WRITE_SACCHARIFICATION
	      lcd.setCursor(0, 0);
	      lcd.clear();
	      lcd.print("Saccharification"); 
	      break;
	    case '8':
	      //ARDUINO_WRITE_MASH_OUT
	      lcd.setCursor(0, 0);
	      lcd.clear();
	      lcd.print("Mash Out"); 
	      break;
	    case '9':
	      //ARDUINO_WRITE_BEERDUINO
	      lcd.setCursor(0, 0);
	      lcd.clear();
	      lcd.print("BeerDuino"); 
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
