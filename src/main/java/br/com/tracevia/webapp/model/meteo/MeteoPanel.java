package br.com.tracevia.webapp.model.meteo;

public class MeteoPanel {
	
	private String equip;
	private String equipName;
	private double atmosphericPressure;
	private int relativeHumidity;
	private double temperature;
	private double roadTemperature;
	private double windDirection;
	private double windSpeed;
	private double absolutePreciptation;
	private double WaterFilmHeight;
	private int visibility;
	private int lineVolts;
	private int battery;
	private int status;
	
	public MeteoPanel(String equip, String equipName, double atmosphericPressure, int relativeHumidity,
			double temperature, double roadTemperature, double windDirection, double windSpeed,
			double absolutePreciptation, double waterFilmHeight, int visibility, int lineVolts, int battery,
			int status) {
	
		this.equip = equip;
		this.equipName = equipName;
		this.atmosphericPressure = atmosphericPressure;
		this.relativeHumidity = relativeHumidity;
		this.temperature = temperature;
		this.roadTemperature = roadTemperature;
		this.windDirection = windDirection;
		this.windSpeed = windSpeed;
		this.absolutePreciptation = absolutePreciptation;
		WaterFilmHeight = waterFilmHeight;
		this.visibility = visibility;
		this.lineVolts = lineVolts;
		this.battery = battery;
		this.status = status;
	}
		
	public MeteoPanel(){}

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


	public double getAtmosphericPressure() {
		return atmosphericPressure;
	}


	public void setAtmosphericPressure(double atmosphericPressure) {
		this.atmosphericPressure = atmosphericPressure;
	}


	public int getRelativeHumidity() {
		return relativeHumidity;
	}


	public void setRelativeHumidity(int relativeHumidity) {
		this.relativeHumidity = relativeHumidity;
	}


	public double getTemperature() {
		return temperature;
	}


	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}


	public double getRoadTemperature() {
		return roadTemperature;
	}


	public void setRoadTemperature(double roadTemperature) {
		this.roadTemperature = roadTemperature;
	}


	public double getWindDirection() {
		return windDirection;
	}


	public void setWindDirection(double windDirection) {
		this.windDirection = windDirection;
	}


	public double getWindSpeed() {
		return windSpeed;
	}


	public void setWindSpeed(double windSpeed) {
		this.windSpeed = windSpeed;
	}


	public double getAbsolutePreciptation() {
		return absolutePreciptation;
	}


	public void setAbsolutePreciptation(double absolutePreciptation) {
		this.absolutePreciptation = absolutePreciptation;
	}


	public double getWaterFilmHeight() {
		return WaterFilmHeight;
	}


	public void setWaterFilmHeight(double waterFilmHeight) {
		WaterFilmHeight = waterFilmHeight;
	}


	public int getVisibility() {
		return visibility;
	}


	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}


	public int getLineVolts() {
		return lineVolts;
	}


	public void setLineVolts(int lineVolts) {
		this.lineVolts = lineVolts;
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
