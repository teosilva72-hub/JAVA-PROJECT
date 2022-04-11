package br.com.tracevia.webapp.model.speed;

public class SpeedReport {
	
	//Builder Pattern
		public static class Builder{
			
			private int register;
			private int speed;
			private String date;
			private String time;
			private String direction;
			private String equipment;
			
			// Register	
			public Builder register(int register) {
			   this.register = register; 
			   return this;			
			}
			
			// Speed
			public Builder speed(int speed) {
				this.speed = speed; 
				return this;			
			}
		
			// Date	
			public Builder date(String date) {
				this.date = date; 
			    return this;			
			}
			
			// Time		
			public Builder time(String time) {
				this.time = time; 
			    return this;			
			}
			
			// Time		
			public Builder direction(String direction) {
				this.direction = direction; 
			    return this;			
			}
			
			// Time		
			public Builder equipment(String equipment) {
				this.equipment = equipment; 
				return this;			
			}
			
			// GETTERS
			public int getRegister() {
				return register;
			}

			public int getSpeed() {
				return speed;
			}

			public String getDate() {
				return date;
			}

			public String getTime() {
				return time;
			}

			public String getDirection() {
				return direction;
			}

			public String getEquipment() {
				return equipment;
			}						
		}
		
	// Results
		
	private int register;
	private int speed;
	private String date;
	private String time;
	private String direction;
	private String equipment;
	public String[] equipments;	
	private String period;
	private String startDate;
	private String endDate;
				
	public SpeedReport(int register, int speed, String date, String time, String direction, String equipment,
			String[] equipments, String period, String startDate, String endDate) {
		
		this.register = register;
		this.speed = speed;
		this.date = date;
		this.time = time;
		this.direction = direction;
		this.equipment = equipment;
		this.equipments = equipments;
		this.period = period;
		this.startDate = startDate;
		this.endDate = endDate;
		
	}
			
	public SpeedReport() {}

	public int getRegister() {
		return register;
	}
	public void setRegister(int register) {
		this.register = register;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getEquipment() {
		return equipment;
	}
	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}
	public String[] getEquipments() {
		return equipments;
	}
	
	public void setEquipments(String[] equipments) {
		this.equipments = equipments;
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
		
}
