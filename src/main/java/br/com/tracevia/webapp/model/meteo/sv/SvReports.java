package br.com.tracevia.webapp.model.meteo.sv;

import br.com.tracevia.webapp.model.meteo.mto.MtoReports.Builder;

public class SvReports {
	
	 //Builder Pattern
	public static class Builder{
					
		private String month;
		private String date;
		private String dateTime;
		private String status;
		private int dayOfTheMonth;
	    private double atmPressure;
		private int relative_humidity;
		private double temperature;
		private double road_temperature;
		private double wind_direction;
		private double wind_speed;
		private double absolute_precipitation;	
     	private double ambient_temperature;		
		private int line_volts;
		private int battery;
		
		//BUILDERS 	
		public Builder date(String date) {
			this.date = date; 
		    return this;			
		}
		
		public Builder dateTime(String dateTime) {
			this.dateTime = dateTime;			
			return this;			
		}
		
		public Builder month(String month) {
			this.month = month;			
			return this;			
		}
		
		public Builder status(String status) {
			this.status = status;			
			return this;			
		}			
					
		public Builder dayOfMonth(int day) {
			this.dayOfTheMonth = day;			
			return this;			
		}	
		
		public Builder atmPressure(double atmPressure) {
			this.atmPressure = atmPressure;			
			return this;			
		}
		
		public Builder relative_humidity(int relative_humidity) {
			this.relative_humidity = relative_humidity;			
			return this;			
		}
		
		public Builder temperature(double temperature) {
			this.temperature = temperature;			
			return this;			
		}
		
		public Builder roadTemperature(double road_temperature) {
			this.road_temperature = road_temperature;			
			return this;			
		}
		
		public Builder windDir(double wind_direction) {
			this.wind_direction = wind_direction;			
			return this;			
		}
		
		public Builder windSpeed(double wind_speed) {
			this.wind_speed = wind_speed;			
			return this;			
		}
		
		public Builder absolutePreciptation(double absolute_precipitation) {
			this.absolute_precipitation = absolute_precipitation;			
			return this;			
		}							
								
		public Builder EnvTemperature(double temperature) {
			this.ambient_temperature = temperature;			
			return this;			
		}
								
		public Builder lineVolts(int line_volts) {
			this.line_volts = line_volts;			
			return this;			
		}
		
		public Builder battery(int battery) {
			this.battery = battery;			
			return this;			
		}
				
		//GETTERS

		public String getMonth() {
			return month;
		}

		public String getDate() {
			return date;
		}

		public String getDateTime() {
			return dateTime;
		}

		public String getStatus() {
			return status;
		}

		public int getDayOfTheMonth() {
			return dayOfTheMonth;
		}
		
		public double getAtmPressure() {
			return atmPressure;
		}

		public int getRelative_humidity() {
			return relative_humidity;
		}

		public double getTemperature() {
			return temperature;
		}
					
		public double getRoad_temperature() {
			return road_temperature;
		}

		public double getWind_direction() {
			return wind_direction;
		}

		public double getWind_speed() {
			return wind_speed;
		}

		public double getAbsolute_precipitation() {
			return absolute_precipitation;
		}		
			
		public double getAmbient_temperature() {
			return ambient_temperature;
		}

		public int getLine_volts() {
			return line_volts;
		}

		public int getBattery() {
			return battery;
		}
					
	}
	
	private String month; 
	private String startMonth; 
	private String endMonth; 
	private String year;	
	private String equipment;
	private String period;
	private String startDate;
	private String endDate;
	public int[] equipments;	
	private String date;
	private String dateTime;
					
	public SvReports(String month, String startMonth, String endMonth, String year, String equipment, String period,
		String startDate, String endDate, int[] equipments, String date, String dateTime) {
		
		this.month = month;
		this.startMonth = startMonth;
		this.endMonth = endMonth;
		this.year = year;
		this.equipment = equipment;
		this.period = period;
		this.startDate = startDate;
		this.endDate = endDate;
		this.equipments = equipments;
		this.date = date;
		this.dateTime = dateTime;
	}
		
	public SvReports() {}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getStartMonth() {
		return startMonth;
	}

	public void setStartMonth(String startMonth) {
		this.startMonth = startMonth;
	}

	public String getEndMonth() {
		return endMonth;
	}

	public void setEndMonth(String endMonth) {
		this.endMonth = endMonth;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getEquipment() {
		return equipment;
	}

	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public int[] getEquipments() {
		return equipments;
	}

	public void setEquipments(int[] equipments) {
		this.equipments = equipments;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}


}
