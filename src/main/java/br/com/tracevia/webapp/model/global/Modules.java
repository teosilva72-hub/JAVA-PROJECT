package br.com.tracevia.webapp.model.global;

public class Modules {
	
	private int moduleID;
	private String module;	
	private double battery_voltage;	
	private boolean enabled;
		
	public Modules(int moduleID, String module, double battery_voltage, boolean enabled) {
		
		this.moduleID = moduleID;
		this.module = module;	
		this.battery_voltage = battery_voltage;
		this.enabled = enabled;
	}
	
	public Modules() {}
	
	public int getModuleID() {
		return moduleID;
	}
	
	public void setModuleID(int moduleID) {
		this.moduleID = moduleID;
	}
	
	public String getModule() {
		return module;
	}
	
	public void setModule(String module) {
		this.module = module;
	}
				
	public double getBattery_voltage() {
		return battery_voltage;
	}

	public void setBattery_voltage(double battery_voltage) {
		this.battery_voltage = battery_voltage;
	}

	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
		

}
