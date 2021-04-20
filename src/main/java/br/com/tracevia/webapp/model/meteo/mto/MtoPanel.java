package br.com.tracevia.webapp.model.meteo.mto;

public class MtoPanel {
	
	private String equip;	
	private String equipName;
	private int atmPressure;
	private int relative_humidity;
	private double temperature;
	private double road_temperature;
	private int wind_direction;
	private int wind_speed;
	private int preciptation_rate;
	private int preciptation_rate_hour;
	private int visibility;
	private int line_volts;
	private int battery;
	private int status;
		
	public MtoPanel(String equip, String equipName, int atmPressure, int relative_humidity, double temperature, double road_temperature, int wind_direction,
			int wind_speed, int preciptation_rate, int preciptation_rate_hour, int visibility, int line_volts,
			int battery, int status) {
		
		this.equip = equip;
		this.atmPressure = atmPressure;
		this.relative_humidity = relative_humidity;
		this.temperature = temperature;
		this.road_temperature = road_temperature;
		this.wind_direction = wind_direction;
		this.wind_speed = wind_speed;
		this.preciptation_rate = preciptation_rate;
		this.preciptation_rate_hour = preciptation_rate_hour;
		this.visibility = visibility;
		this.line_volts = line_volts;
		this.battery = battery;
		this.equipName = equipName;
		this.status = status;
	}

	public MtoPanel() {}

	public String getEquip() {
		return equip;
	}

	public void setEquip(String equip) {
		this.equip = equip;
	}

	public int getAtmPressure() {
		return atmPressure;
	}

	public void setAtmPressure(int atmPressure) {
		this.atmPressure = atmPressure;
	}

	public int getRelative_humidity() {
		return relative_humidity;
	}

	public void setRelative_humidity(int relative_humidity) {
		this.relative_humidity = relative_humidity;
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	
	public double getRoad_temperature() {
		return road_temperature;
	}

	public void setRoad_temperature(double road_temperature) {
		this.road_temperature = road_temperature;
	}

	public int getWind_direction() {
		return wind_direction;
	}

	public void setWind_direction(int wind_direction) {
		this.wind_direction = wind_direction;
	}

	public int getWind_speed() {
		return wind_speed;
	}

	public void setWind_speed(int wind_speed) {
		this.wind_speed = wind_speed;
	}

	public int getPreciptation_rate() {
		return preciptation_rate;
	}

	public void setPreciptation_rate(int preciptation_rate) {
		this.preciptation_rate = preciptation_rate;
	}

	public int getPreciptation_rate_hour() {
		return preciptation_rate_hour;
	}

	public void setPreciptation_rate_hour(int preciptation_rate_hour) {
		this.preciptation_rate_hour = preciptation_rate_hour;
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

	public String getEquipName() {
		return equipName;
	}

	public void setEquipName(String equipName) {
		this.equipName = equipName;
	}	

}
