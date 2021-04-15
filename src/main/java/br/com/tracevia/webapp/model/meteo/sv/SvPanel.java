package br.com.tracevia.webapp.model.meteo.vs;

public class VsPanel {
	
	private String equip;
	private String equipName;
	private double ambient_temperature;
	private int visibility;
	private int line_volts;
	private int battery;
	private int status;
	
	public VsPanel(String equip, String equipName, double ambient_temperature, int visibility, int line_volts, int battery,
			int status) {
		
		this.equip = equip;
		this.equipName = equipName;
		this.ambient_temperature = ambient_temperature;
		this.visibility = visibility;
		this.line_volts = line_volts;
		this.battery = battery;
		this.status = status;
	}
	
	public VsPanel() {}


	public String getEquip() {
		return equip;
	}


	public void setEquip(String equip) {
		this.equip = equip;
	}


	public String getEquipName() {
		return equipName;
	}


	public void setEquipName(String equipName) {
		this.equipName = equipName;
	}

	public double getAmbient_temperature() {
		return ambient_temperature;
	}

	public void setAmbient_temperature(double ambient_temperature) {
		this.ambient_temperature = ambient_temperature;
	}

	public int getVisibility() {
		return visibility;
	}

	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}


	public int getLine_volts() {
		return line_volts;
	}


	public void setLine_volts(int line_volts) {
		this.line_volts = line_volts;
	}


	public int getBattery() {
		return battery;
	}


	public void setBattery(int battery) {
		this.battery = battery;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}
	
}
