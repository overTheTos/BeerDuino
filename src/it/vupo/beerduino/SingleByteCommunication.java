package it.vupo.beerduino;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

public class SingleByteCommunication implements SerialPortEventListener {
	SerialPort serialPort;
	/** The port we're normally going to use. */
	private static final String PORT_NAMES[] = {// "/dev/tty.usbserial-A9007UX1",
												// // Mac
												// OS
												// X
	// "/dev/ttyUSB0", // Linux
	// "COM6", // Windows
	"COM4", // Windows
	};
	/** Buffered input stream from the port */
	private InputStream input;
	/** The output stream to the port */
	private OutputStream output;
	/** Milliseconds to block while waiting for port open */
	private static final int TIME_OUT = 2000;
	/** Default bits per second for COM port. */
	private static final int DATA_RATE = 9600;
	
	private Float temperatura;

	public void initialize() {
		CommPortIdentifier portId = null;
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

		// iterate through, looking for the port
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum
					.nextElement();
			for (String portName : PORT_NAMES) {
				if (currPortId.getName().equals(portName)) {
					portId = currPortId;
					break;
				}
			}
		}

		if (portId == null) {
			System.out.println("Could not find COM port.");
			return;
		} else {
			System.out.println("Found your Port");
		}

		try {
			// open serial port, and use class name for the appName.
			serialPort = (SerialPort) portId.open(this.getClass().getName(),
					TIME_OUT);

			// set port parameters
			serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

			// open the streams
			input = serialPort.getInputStream();
			output = serialPort.getOutputStream();

			// add event listeners
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	/**
	 * This should be called when you stop using the port. This will prevent
	 * port locking
	 */
	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	/**
	 * This Method can be called to print a single byte to the serial connection
	 */
	public void sendSingleByte(byte myByte) {
		try {
			output.write(myByte);
			output.flush();
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	/**
	 * This Method is called when Serialdata is recieved
	 */
	public synchronized void serialEvent(SerialPortEvent oEvent) {
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {

				byte[] buffer = new byte[1024];
				int len = -1;
				try {

					String temp = new String();
					while ((len = this.input.read(buffer)) > 0) {
						temp += new String(buffer, 0, len);

					}
					Float temperature = Float.parseFloat(temp.trim());
//					System.out
//							.println("Temperatura sensore (°C): " + temperature);
					
					this.temperatura = temperature;
					
					byte outputByte = '0';

					if (temperature <= 22.0) {
						outputByte = '1';
					} else if (temperature > 22.0 && temperature <= 24.0) {
						outputByte = '2';
					} else {
						outputByte = '3';
					}

					sendSingleByte(outputByte);

				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				System.err.println(e.toString());
			}
		}
	}

	public Float getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(Float temperatura) {
		this.temperatura = temperatura;
	}


}