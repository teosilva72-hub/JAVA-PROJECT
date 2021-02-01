package br.com.tracevia.webapp.model.mto;

public class MtoReports {
		
	    //Builder Pattern
		public static class Builder{
						
			private String month;
			private String date;
			private String dateTime;
			private String status;
			private int dayOfTheMonth;
			private int atmPressure;
			private int relative_humidity;
			private int temperature;
			private int wind_direction;
			private int wind_speed;
			private int preciptation_rate;
			private int preciptation_rate_hour;
			private int visibility;
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
			
			public Builder atmPressure(int atmPressure) {
				this.atmPressure = atmPressure;			
				return this;			
			}
			
			public Builder relative_humidity(int relative_humidity) {
				this.relative_humidity = relative_humidity;			
				return this;			
			}
			
			public Builder temperature(int temperature) {
				this.temperature = temperature;			
				return this;			
			}
			
			public Builder windDir(int wind_direction) {
				this.wind_direction = wind_direction;			
				return this;			
			}
			
			public Builder windSpeed(int wind_speed) {
				this.wind_speed = wind_speed;			
				return this;			
			}
			
			public Builder preciptationRate(int preciptation_rate) {
				this.preciptation_rate = preciptation_rate;			
				return this;			
			}
			
			public Builder preciptationRateHour(int preciptation_rate_hour) {
				this.preciptation_rate_hour = preciptation_rate_hour;			
				return this;			
			}
			
			public Builder visibility(int visibility) {
				this.visibility = visibility;			
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

			public int getAtmPressure() {
				return atmPressure;
			}

			public int getRelative_humidity() {
				return relative_humidity;
			}

			public int getTemperature() {
				return temperature;
			}

			public int getWind_direction() {
				return wind_direction;
			}

			public int getWind_speed() {
				return wind_speed;
			}

			public int getPreciptation_rate() {
				return preciptation_rate;
			}

			public int getPreciptation_rate_hour() {
				return preciptation_rate_hour;
			}

			public int getVisibility() {
				return visibility;
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
					
	public MtoReports(String month, String startMonth, String endMonth, String year, String equipment, String period,
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
		
	public MtoReports() {}

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
