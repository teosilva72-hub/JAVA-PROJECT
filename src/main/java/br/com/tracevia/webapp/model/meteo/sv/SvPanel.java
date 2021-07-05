package br.com.tracevia.webapp.model.meteo.sv;

public class SvPanel {
	
	private String equip;
	private String equipName;
	private double ambient_temperature;
	private int line_volts;
	private int battery;
	private int status;
	
	public SvPanel(String equip, String equipName, double ambient_temperature, int line_volts, int battery,
			int status) {
		
		this.equip = equip;
		this.equipName = equipName;
		this.ambient_temperature = ambient_temperature;	
		this.line_volts = line_volts;
		this.battery = battery;
		this.status = status;
	}
	
	public SvPanel() {}


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
