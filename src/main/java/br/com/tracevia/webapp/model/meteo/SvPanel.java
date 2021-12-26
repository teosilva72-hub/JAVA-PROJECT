package br.com.tracevia.webapp.model.meteo;

public class SvPanel {
	
	private String equip;
	private String equipName;
	private double atmPressure;
	private int relative_humidity;
	private double temperature;
	private double road_temperature;
	private double wind_direction;
	private double wind_speed;
	private double absolute_preciptation;
	private double ambient_temperature;
	private int line_volts;
	private int battery;
	private int status;
		
	public SvPanel(String equip, String equipName, double atmPressure, int relative_humidity, double temperature,
			double road_temperature, double wind_direction, double wind_speed, double absolute_preciptation,
			double ambient_temperature, int line_volts, int battery, int status) {
		
		this.equip = equip;
		this.equipName = equipName;
		this.atmPressure = atmPressure;
		this.relative_humidity = relative_humidity;
		this.temperature = temperature;
		this.road_temperature = road_temperature;
		this.wind_direction = wind_direction;
		this.wind_speed = wind_speed;
		this.absolute_preciptation = absolute_preciptation;
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

	public double getAtmPressure() {
		return atmPressure;
	}

	public void setAtmPressure(double atmPressure) {
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

	public double getWind_direction() {
		return wind_direction;
	}

	public void setWind_direction(double wind_direction) {
		this.wind_direction = wind_direction;
	}

	public double getWind_speed() {
		return wind_speed;
	}

	public void setWind_speed(double wind_speed) {
		this.wind_speed = wind_speed;
	}

	public double getAbsolute_preciptation() {
		return absolute_preciptation;
	}

	public void setAbsolute_preciptation(double absolute_preciptation) {
		this.absolute_preciptation = absolute_preciptation;
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
