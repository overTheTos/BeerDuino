package it.vupo.beerduino.configuration;

public enum GlobalSetting {
	
	INSTANCE;
	
	private boolean manualControl = false;

	public boolean isManualControl() {
		return manualControl;
	}

	public void setManualControl(boolean manualControl) {
		this.manualControl = manualControl;
	}
	
	
}
