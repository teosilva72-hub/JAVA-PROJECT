package br.com.tracevia.webapp.model.meteo;

import br.com.tracevia.webapp.model.global.Equipments;

public class Meteo extends Equipments{
				
	private String dateTime;
	private String date;
	private String interval;
	private int monthOrDay;
	private int atmospheric_pressure;
	private int relative_humidity;
	private int absolute_precipitation;
	private int wind_speed;
	private int wind_direction;
	private double air_temperature;
	private double road_temperature;
	private double ambient_temperature;
	private int visibilidade;
	private int battery;
	private int volts_line;	
	private int config_id;
			
	public Meteo(int equip_id, String table_id, String equip_type, String equip_ip, String creation_date,
			String creation_username, String update_date, String update_username, String nome, String estrada,
			String cidade, String km, int linearPosX, int linearPosY, int mapPosX, int mapPosY, int mapWidth,
			int height, int linearWidth, int dlgPosX, int dlgPosY, String direction, String directionTo, int status,
			int lastStatus, boolean notificacao, int port, boolean visible, String dateTime, String date,
			String interval, int monthOrDay, int atmospheric_pressure, int relative_humidity,
			int absolute_precipitation, int wind_speed, int wind_direction, double air_temperature,
			double road_temperature, double ambient_temperature, int visibilidade, int battery, int volts_line,
			int config_id) {
		
		super(equip_id, table_id, equip_type, equip_ip, creation_date, creation_username, update_date, update_username,
				nome, estrada, cidade, km, linearPosX, linearPosY, mapPosX, mapPosY, mapWidth, height, linearWidth,
				dlgPosX, dlgPosY, direction, directionTo, status, lastStatus, notificacao, port, visible);
		
		this.dateTime = dateTime;
		this.date = date;
		this.interval = interval;
		this.monthOrDay = monthOrDay;
		this.atmospheric_pressure = atmospheric_pressure;
		this.relative_humidity = relative_humidity;
		this.absolute_precipitation = absolute_precipitation;
		this.wind_speed = wind_speed;
		this.wind_direction = wind_direction;
		this.air_temperature = air_temperature;
		this.road_temperature = road_temperature;
		this.ambient_temperature = ambient_temperature;
		this.visibilidade = visibilidade;
		this.battery = battery;
		this.volts_line = volts_line;
		this.config_id = config_id;
	}

	public Meteo() {super();}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getInterval() {
		return interval;
	}

	public void setInterval(String interval) {
		this.interval = interval;
	}

	public int getMonthOrDay() {
		return monthOrDay;
	}

	public void setMonthOrDay(int monthOrDay) {
		this.monthOrDay = monthOrDay;
	}

	public int getAtmospheric_pressure() {
		return atmospheric_pressure;
	}

	public void setAtmospheric_pressure(int atmospheric_pressure) {
		this.atmospheric_pressure = atmospheric_pressure;
	}

	public int getRelative_humidity() {
		return relative_humidity;
	}

	public void setRelative_humidity(int relative_humidity) {
		this.relative_humidity = relative_humidity;
	}

	public int getAbsolute_precipitation() {
		return absolute_precipitation;
	}

	public void setAbsolute_precipitation(int absolute_precipitation) {
		this.absolute_precipitation = absolute_precipitation;
	}

	public int getWind_speed() {
		return wind_speed;
	}

	public void setWind_speed(int wind_speed) {
		this.wind_speed = wind_speed;
	}

	public int getWind_direction() {
		return wind_direction;
	}

	public void setWind_direction(int wind_direction) {
		this.wind_direction = wind_direction;
	}

	public double getAir_temperature() {
		return air_temperature;
	}

	public void setAir_temperature(double air_temperature) {
		this.air_temperature = air_temperature;
	}

	public double getRoad_temperature() {
		return road_temperature;
	}

	public void setRoad_temperature(double road_temperature) {
		this.road_temperature = road_temperature;
	}

	public double getAmbient_temperature() {
		return ambient_temperature;
	}

	public void setAmbient_temperature(double ambient_temperature) {
		this.ambient_temperature = ambient_temperature;
	}

	public int getVisibilidade() {
		return visibilidade;
	}

	public void setVisibilidade(int visibilidade) {
		this.visibilidade = visibilidade;
	}

	public int getBattery() {
		return battery;
	}

	public void setBattery(int battery) {
		this.battery = battery;
	}

	public int getVolts_line() {
		return volts_line;
	}

	public void setVolts_line(int volts_line) {
		this.volts_line = volts_line;
	}

	public int getConfig_id() {
		return config_id;
	}

	public void setConfig_id(int config_id) {
		this.config_id = config_id;
	}
	
}
