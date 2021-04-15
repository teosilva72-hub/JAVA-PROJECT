package br.com.tracevia.webapp.model.meteo.rs;

public class RsPanel {

	private String equip;
	private String equipName;
	private double road_temperature;
	private int line_volts;
	private int battery;
	private int status;
	
	public RsPanel(String equip, String equipName, double road_temperature, int line_volts, int battery, int status) {
		this.equip = equip;
		this.equipName = equipName;
		this.road_temperature = road_temperature;
		this.line_volts = line_volts;
		this.battery = battery;
		this.status = status;
	}
	
	public RsPanel() {}

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

	public double getRoad_temperature() {
		return road_temperature;
	}

	public void setRoad_temperature(double road_temperature) {
		this.road_temperature = road_temperature;
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
