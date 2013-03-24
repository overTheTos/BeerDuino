package it.vupo.beerduino.bean;

public class MashStep {
	
	private boolean isCurrentStep;
	private String stepName;
	private Integer	stepTime;
	private Integer	restTime;
	private Integer restStartTemp;
	private Integer restStopTemp;
	
	public boolean isCurrentStep() {
		return isCurrentStep;
	}
	public void setCurrentStep(boolean isCurrentStep) {
		this.isCurrentStep = isCurrentStep;
	}
	public String getStepName() {
		return stepName;
	}
	public void setStepName(String stepName) {
		this.stepName = stepName;
	}
	public Integer getStepTime() {
		return stepTime;
	}
	public void setStepTime(Integer stepTime) {
		this.stepTime = stepTime;
	}
	public Integer getRestTime() {
		return restTime;
	}
	public void setRestTime(Integer restTime) {
		this.restTime = restTime;
	}
	public Integer getRestStartTemp() {
		return restStartTemp;
	}
	public void setRestStartTemp(Integer restStartTemp) {
		this.restStartTemp = restStartTemp;
	}
	public Integer getRestStopTemp() {
		return restStopTemp;
	}
	public void setRestStopTemp(Integer restStopTemp) {
		this.restStopTemp = restStopTemp;
	}
	
}
