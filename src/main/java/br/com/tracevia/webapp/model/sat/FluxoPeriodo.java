package br.com.tracevia.webapp.model.sat;

public class FluxoPeriodo {
	
	private String equip;
	private String date;						
	private String interval;
	private String direction1;
	private String direction2;
	private int autoS1;	
	private int comS1;
	private int motoS1;
	private int totalS1;
	private int autoS2;	
	private int comS2;
	private int motoS2;
	private int totalS2;
	private int speedAutoS1;	
	private int speedComS1;
	private int speedMotoS1;
	private int speedTotalS1;
	private int speedAutoS2;	
	private int speedComS2;
	private int speedMotoS2;
	private int speedTotalS2;
	private int speed50thAutoS1;	
	private int speed50thComS1;
	private int speed50thMotoS1;
	private int speed50thTotalS1;
	private int speed50thAutoS2;	
	private int speed50thComS2;
	private int speed50thMotoS2;
	private int speed50thTotalS2;
	private int speed85thAutoS1;	
	private int speed85thComS1;
	private int speed85thMotoS1;
	private int speed85thTotalS1;
	private int speed85thAutoS2;	
	private int speed85thComS2;
	private int speed85thMotoS2;
	private int speed85thTotalS2;
	private int speedMaxAutoS1;	
	private int speedMaxComS1;
	private int speedMaxMotoS1;
	private int speedMaxTotalS1;
	private int speedMaxAutoS2;	
	private int speedMaxComS2;
	private int speedMaxMotoS2;
	private int speedMaxTotalS2;
	private int speedMinAutoS1;	
	private int speedMinComS1;
	private int speedMinMotoS1;
	private int speedMinTotalS1;
	private int speedMinAutoS2;	
	private int speedMinComS2;
	private int speedMinMotoS2;
	private int speedMinTotalS2;
	private int speedStdAutoS1;	
	private int speedStdComS1;
	private int speedStdMotoS1;
	private int speedStdTotalS1;
	private int speedStdAutoS2;	
	private int speedStdComS2;
	private int speedStdMotoS2;
	private int speedStdTotalS2;
	
	public FluxoPeriodo(String equip, String date, String interval, String direction1, String direction2, int autoS1,
			int comS1, int motoS1, int totalS1, int autoS2, int comS2, int motoS2, int totalS2, int speedAutoS1,
			int speedComS1, int speedMotoS1, int speedTotalS1, int speedAutoS2, int speedComS2, int speedMotoS2,
			int speedTotalS2, int speed50thAutoS1, int speed50thComS1, int speed50thMotoS1, int speed50thTotalS1,
			int speed50thAutoS2, int speed50thComS2, int speed50thMotoS2, int speed50thTotalS2, int speed85thAutoS1,
			int speed85thComS1, int speed85thMotoS1, int speed85thTotalS1, int speed85thAutoS2, int speed85thComS2,
			int speed85thMotoS2, int speed85thTotalS2, int speedMaxAutoS1, int speedMaxComS1, int speedMaxMotoS1,
			int speedMaxTotalS1, int speedMaxAutoS2, int speedMaxComS2, int speedMaxMotoS2, int speedMaxTotalS2,
			int speedMinAutoS1, int speedMinComS1, int speedMinMotoS1, int speedMinTotalS1, int speedMinAutoS2,
			int speedMinComS2, int speedMinMotoS2, int speedMinTotalS2, int speedStdAutoS1, int speedStdComS1,
			int speedStdMotoS1, int speedStdTotalS1, int speedStdAutoS2, int speedStdComS2, int speedStdMotoS2,
			int speedStdTotalS2) {
		
		this.equip = equip;
		this.date = date;
		this.interval = interval;
		this.direction1 = direction1;
		this.direction2 = direction2;
		this.autoS1 = autoS1;
		this.comS1 = comS1;
		this.motoS1 = motoS1;
		this.totalS1 = totalS1;
		this.autoS2 = autoS2;
		this.comS2 = comS2;
		this.motoS2 = motoS2;
		this.totalS2 = totalS2;
		this.speedAutoS1 = speedAutoS1;
		this.speedComS1 = speedComS1;
		this.speedMotoS1 = speedMotoS1;
		this.speedTotalS1 = speedTotalS1;
		this.speedAutoS2 = speedAutoS2;
		this.speedComS2 = speedComS2;
		this.speedMotoS2 = speedMotoS2;
		this.speedTotalS2 = speedTotalS2;
		this.speed50thAutoS1 = speed50thAutoS1;
		this.speed50thComS1 = speed50thComS1;
		this.speed50thMotoS1 = speed50thMotoS1;
		this.speed50thTotalS1 = speed50thTotalS1;
		this.speed50thAutoS2 = speed50thAutoS2;
		this.speed50thComS2 = speed50thComS2;
		this.speed50thMotoS2 = speed50thMotoS2;
		this.speed50thTotalS2 = speed50thTotalS2;
		this.speed85thAutoS1 = speed85thAutoS1;
		this.speed85thComS1 = speed85thComS1;
		this.speed85thMotoS1 = speed85thMotoS1;
		this.speed85thTotalS1 = speed85thTotalS1;
		this.speed85thAutoS2 = speed85thAutoS2;
		this.speed85thComS2 = speed85thComS2;
		this.speed85thMotoS2 = speed85thMotoS2;
		this.speed85thTotalS2 = speed85thTotalS2;
		this.speedMaxAutoS1 = speedMaxAutoS1;
		this.speedMaxComS1 = speedMaxComS1;
		this.speedMaxMotoS1 = speedMaxMotoS1;
		this.speedMaxTotalS1 = speedMaxTotalS1;
		this.speedMaxAutoS2 = speedMaxAutoS2;
		this.speedMaxComS2 = speedMaxComS2;
		this.speedMaxMotoS2 = speedMaxMotoS2;
		this.speedMaxTotalS2 = speedMaxTotalS2;
		this.speedMinAutoS1 = speedMinAutoS1;
		this.speedMinComS1 = speedMinComS1;
		this.speedMinMotoS1 = speedMinMotoS1;
		this.speedMinTotalS1 = speedMinTotalS1;
		this.speedMinAutoS2 = speedMinAutoS2;
		this.speedMinComS2 = speedMinComS2;
		this.speedMinMotoS2 = speedMinMotoS2;
		this.speedMinTotalS2 = speedMinTotalS2;
		this.speedStdAutoS1 = speedStdAutoS1;
		this.speedStdComS1 = speedStdComS1;
		this.speedStdMotoS1 = speedStdMotoS1;
		this.speedStdTotalS1 = speedStdTotalS1;
		this.speedStdAutoS2 = speedStdAutoS2;
		this.speedStdComS2 = speedStdComS2;
		this.speedStdMotoS2 = speedStdMotoS2;
		this.speedStdTotalS2 = speedStdTotalS2;
	}

	public FluxoPeriodo() {}

	
	public String getEquip() {
		return equip;
	}

	public void setEquip(String equip) {
		this.equip = equip;
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

	public String getDirection1() {
		return direction1;
	}

	public void setDirection1(String direction1) {
		this.direction1 = direction1;
	}

	public String getDirection2() {
		return direction2;
	}

	public void setDirection2(String direction2) {
		this.direction2 = direction2;
	}

	public int getAutoS1() {
		return autoS1;
	}

	public void setAutoS1(int autoS1) {
		this.autoS1 = autoS1;
	}

	public int getComS1() {
		return comS1;
	}

	public void setComS1(int comS1) {
		this.comS1 = comS1;
	}

	public int getMotoS1() {
		return motoS1;
	}

	public void setMotoS1(int motoS1) {
		this.motoS1 = motoS1;
	}

	public int getTotalS1() {
		return totalS1;
	}

	public void setTotalS1(int totalS1) {
		this.totalS1 = totalS1;
	}

	public int getAutoS2() {
		return autoS2;
	}

	public void setAutoS2(int autoS2) {
		this.autoS2 = autoS2;
	}

	public int getComS2() {
		return comS2;
	}

	public void setComS2(int comS2) {
		this.comS2 = comS2;
	}

	public int getMotoS2() {
		return motoS2;
	}

	public void setMotoS2(int motoS2) {
		this.motoS2 = motoS2;
	}

	public int getTotalS2() {
		return totalS2;
	}

	public void setTotalS2(int totalS2) {
		this.totalS2 = totalS2;
	}

	public int getSpeedAutoS1() {
		return speedAutoS1;
	}

	public void setSpeedAutoS1(int speedAutoS1) {
		this.speedAutoS1 = speedAutoS1;
	}

	public int getSpeedComS1() {
		return speedComS1;
	}

	public void setSpeedComS1(int speedComS1) {
		this.speedComS1 = speedComS1;
	}

	public int getSpeedMotoS1() {
		return speedMotoS1;
	}

	public void setSpeedMotoS1(int speedMotoS1) {
		this.speedMotoS1 = speedMotoS1;
	}

	public int getSpeedTotalS1() {
		return speedTotalS1;
	}

	public void setSpeedTotalS1(int speedTotalS1) {
		this.speedTotalS1 = speedTotalS1;
	}

	public int getSpeedAutoS2() {
		return speedAutoS2;
	}

	public void setSpeedAutoS2(int speedAutoS2) {
		this.speedAutoS2 = speedAutoS2;
	}

	public int getSpeedComS2() {
		return speedComS2;
	}

	public void setSpeedComS2(int speedComS2) {
		this.speedComS2 = speedComS2;
	}

	public int getSpeedMotoS2() {
		return speedMotoS2;
	}

	public void setSpeedMotoS2(int speedMotoS2) {
		this.speedMotoS2 = speedMotoS2;
	}

	public int getSpeedTotalS2() {
		return speedTotalS2;
	}

	public void setSpeedTotalS2(int speedTotalS2) {
		this.speedTotalS2 = speedTotalS2;
	}

	public int getSpeed50thAutoS1() {
		return speed50thAutoS1;
	}

	public void setSpeed50thAutoS1(int speed50thAutoS1) {
		this.speed50thAutoS1 = speed50thAutoS1;
	}

	public int getSpeed50thComS1() {
		return speed50thComS1;
	}

	public void setSpeed50thComS1(int speed50thComS1) {
		this.speed50thComS1 = speed50thComS1;
	}

	public int getSpeed50thMotoS1() {
		return speed50thMotoS1;
	}

	public void setSpeed50thMotoS1(int speed50thMotoS1) {
		this.speed50thMotoS1 = speed50thMotoS1;
	}

	public int getSpeed50thTotalS1() {
		return speed50thTotalS1;
	}

	public void setSpeed50thTotalS1(int speed50thTotalS1) {
		this.speed50thTotalS1 = speed50thTotalS1;
	}

	public int getSpeed50thAutoS2() {
		return speed50thAutoS2;
	}

	public void setSpeed50thAutoS2(int speed50thAutoS2) {
		this.speed50thAutoS2 = speed50thAutoS2;
	}

	public int getSpeed50thComS2() {
		return speed50thComS2;
	}

	public void setSpeed50thComS2(int speed50thComS2) {
		this.speed50thComS2 = speed50thComS2;
	}

	public int getSpeed50thMotoS2() {
		return speed50thMotoS2;
	}

	public void setSpeed50thMotoS2(int speed50thMotoS2) {
		this.speed50thMotoS2 = speed50thMotoS2;
	}

	public int getSpeed50thTotalS2() {
		return speed50thTotalS2;
	}

	public void setSpeed50thTotalS2(int speed50thTotalS2) {
		this.speed50thTotalS2 = speed50thTotalS2;
	}

	public int getSpeed85thAutoS1() {
		return speed85thAutoS1;
	}

	public void setSpeed85thAutoS1(int speed85thAutoS1) {
		this.speed85thAutoS1 = speed85thAutoS1;
	}

	public int getSpeed85thComS1() {
		return speed85thComS1;
	}

	public void setSpeed85thComS1(int speed85thComS1) {
		this.speed85thComS1 = speed85thComS1;
	}

	public int getSpeed85thMotoS1() {
		return speed85thMotoS1;
	}

	public void setSpeed85thMotoS1(int speed85thMotoS1) {
		this.speed85thMotoS1 = speed85thMotoS1;
	}

	public int getSpeed85thTotalS1() {
		return speed85thTotalS1;
	}

	public void setSpeed85thTotalS1(int speed85thTotalS1) {
		this.speed85thTotalS1 = speed85thTotalS1;
	}

	public int getSpeed85thAutoS2() {
		return speed85thAutoS2;
	}

	public void setSpeed85thAutoS2(int speed85thAutoS2) {
		this.speed85thAutoS2 = speed85thAutoS2;
	}

	public int getSpeed85thComS2() {
		return speed85thComS2;
	}

	public void setSpeed85thComS2(int speed85thComS2) {
		this.speed85thComS2 = speed85thComS2;
	}

	public int getSpeed85thMotoS2() {
		return speed85thMotoS2;
	}

	public void setSpeed85thMotoS2(int speed85thMotoS2) {
		this.speed85thMotoS2 = speed85thMotoS2;
	}

	public int getSpeed85thTotalS2() {
		return speed85thTotalS2;
	}

	public void setSpeed85thTotalS2(int speed85thTotalS2) {
		this.speed85thTotalS2 = speed85thTotalS2;
	}

	public int getSpeedMaxAutoS1() {
		return speedMaxAutoS1;
	}

	public void setSpeedMaxAutoS1(int speedMaxAutoS1) {
		this.speedMaxAutoS1 = speedMaxAutoS1;
	}

	public int getSpeedMaxComS1() {
		return speedMaxComS1;
	}

	public void setSpeedMaxComS1(int speedMaxComS1) {
		this.speedMaxComS1 = speedMaxComS1;
	}

	public int getSpeedMaxMotoS1() {
		return speedMaxMotoS1;
	}

	public void setSpeedMaxMotoS1(int speedMaxMotoS1) {
		this.speedMaxMotoS1 = speedMaxMotoS1;
	}

	public int getSpeedMaxTotalS1() {
		return speedMaxTotalS1;
	}

	public void setSpeedMaxTotalS1(int speedMaxTotalS1) {
		this.speedMaxTotalS1 = speedMaxTotalS1;
	}

	public int getSpeedMaxAutoS2() {
		return speedMaxAutoS2;
	}

	public void setSpeedMaxAutoS2(int speedMaxAutoS2) {
		this.speedMaxAutoS2 = speedMaxAutoS2;
	}

	public int getSpeedMaxComS2() {
		return speedMaxComS2;
	}

	public void setSpeedMaxComS2(int speedMaxComS2) {
		this.speedMaxComS2 = speedMaxComS2;
	}

	public int getSpeedMaxMotoS2() {
		return speedMaxMotoS2;
	}

	public void setSpeedMaxMotoS2(int speedMaxMotoS2) {
		this.speedMaxMotoS2 = speedMaxMotoS2;
	}

	public int getSpeedMaxTotalS2() {
		return speedMaxTotalS2;
	}

	public void setSpeedMaxTotalS2(int speedMaxTotalS2) {
		this.speedMaxTotalS2 = speedMaxTotalS2;
	}

	public int getSpeedMinAutoS1() {
		return speedMinAutoS1;
	}

	public void setSpeedMinAutoS1(int speedMinAutoS1) {
		this.speedMinAutoS1 = speedMinAutoS1;
	}

	public int getSpeedMinComS1() {
		return speedMinComS1;
	}

	public void setSpeedMinComS1(int speedMinComS1) {
		this.speedMinComS1 = speedMinComS1;
	}

	public int getSpeedMinMotoS1() {
		return speedMinMotoS1;
	}

	public void setSpeedMinMotoS1(int speedMinMotoS1) {
		this.speedMinMotoS1 = speedMinMotoS1;
	}

	public int getSpeedMinTotalS1() {
		return speedMinTotalS1;
	}

	public void setSpeedMinTotalS1(int speedMinTotalS1) {
		this.speedMinTotalS1 = speedMinTotalS1;
	}

	public int getSpeedMinAutoS2() {
		return speedMinAutoS2;
	}

	public void setSpeedMinAutoS2(int speedMinAutoS2) {
		this.speedMinAutoS2 = speedMinAutoS2;
	}

	public int getSpeedMinComS2() {
		return speedMinComS2;
	}

	public void setSpeedMinComS2(int speedMinComS2) {
		this.speedMinComS2 = speedMinComS2;
	}

	public int getSpeedMinMotoS2() {
		return speedMinMotoS2;
	}

	public void setSpeedMinMotoS2(int speedMinMotoS2) {
		this.speedMinMotoS2 = speedMinMotoS2;
	}

	public int getSpeedMinTotalS2() {
		return speedMinTotalS2;
	}

	public void setSpeedMinTotalS2(int speedMinTotalS2) {
		this.speedMinTotalS2 = speedMinTotalS2;
	}

	public int getSpeedStdAutoS1() {
		return speedStdAutoS1;
	}

	public void setSpeedStdAutoS1(int speedStdAutoS1) {
		this.speedStdAutoS1 = speedStdAutoS1;
	}

	public int getSpeedStdComS1() {
		return speedStdComS1;
	}

	public void setSpeedStdComS1(int speedStdComS1) {
		this.speedStdComS1 = speedStdComS1;
	}

	public int getSpeedStdMotoS1() {
		return speedStdMotoS1;
	}

	public void setSpeedStdMotoS1(int speedStdMotoS1) {
		this.speedStdMotoS1 = speedStdMotoS1;
	}

	public int getSpeedStdTotalS1() {
		return speedStdTotalS1;
	}

	public void setSpeedStdTotalS1(int speedStdTotalS1) {
		this.speedStdTotalS1 = speedStdTotalS1;
	}

	public int getSpeedStdAutoS2() {
		return speedStdAutoS2;
	}

	public void setSpeedStdAutoS2(int speedStdAutoS2) {
		this.speedStdAutoS2 = speedStdAutoS2;
	}

	public int getSpeedStdComS2() {
		return speedStdComS2;
	}

	public void setSpeedStdComS2(int speedStdComS2) {
		this.speedStdComS2 = speedStdComS2;
	}

	public int getSpeedStdMotoS2() {
		return speedStdMotoS2;
	}

	public void setSpeedStdMotoS2(int speedStdMotoS2) {
		this.speedStdMotoS2 = speedStdMotoS2;
	}

	public int getSpeedStdTotalS2() {
		return speedStdTotalS2;
	}

	public void setSpeedStdTotalS2(int speedStdTotalS2) {
		this.speedStdTotalS2 = speedStdTotalS2;
	}


	// --------------------------------------------

	// Builder Pattern
	public static class Builder{
		
		private String equip;
		private String date;
		private String time;
		private String direction1;
		private String direction2;
        private int light1;
        private int moto1;
        private int comm1;
        private int light2;
        private int moto2;
        private int comm2;
        private int speed1;
        private int speed2;     
        private int total1;
        private int total2;
              		
		// Constructors		
		
    	public Builder equip(String equip) {
			this.equip = equip; 
		    return this;			
		}
    	
		public Builder date(String date) {
			this.date = date; 
		    return this;			
		}
		
		// TIME
		public Builder time(String time) {
			this.time = time;			
			return this;			
		}
		
		// DIRECTION 1
		public Builder direction1(String direction1) {
			this.direction1 = direction1;			
			return this;			
		}
				
		// DIRECTION 2
		public Builder direction2(String direction2) {
			this.direction2 = direction2;			
			return this;			
		}				
				
		// LIGHT S1
		public Builder lightS1(int light1) {
			this.light1 = light1;			
			return this;			
		}		
		
		// COMM S1
		public Builder commS1(int comm1) {
			this.comm1 = comm1;			
			return this;			
		}
		
		// MOTO S1
		public Builder motoS1(int moto1) {
			this.moto1 = moto1;			
			return this;			
		}
		
		// TOTAL S1
		public Builder totalS1(int total1) {
			this.total1 = total1;			
			return this;			
		}
		
		// SPEED S1
		public Builder speedS1(int speed1) {
			this.speed1 = speed1;			
			return this;			
		}	
				
		// LIGHT S2
		public Builder lightS2(int light2) {
			this.light2 = light2;			
			return this;			
		}		
		
		// COMM S2
		public Builder commS2(int comm2) {
			this.comm2 = comm2;			
			return this;			
		}
		
		// MOTO S2
		public Builder motoS2(int moto2) {
			this.moto2 = moto2;			
			return this;			
		}		
		
		// TOTAL S2
		public Builder totalS2(int total2) {
			this.total2 = total2;			
			return this;			
		}	
		
		// SPEED S2
		public Builder speedS2(int speed2) {
			this.speed2 = speed2;			
			return this;			
		}
		
		// ONLY GETTERS
		
		public String getEquip() {
			return equip;
		}
		
		public String getDate() {
			return date;
		}
	
		public String getTime() {
			return time;
		}

		public String getDirection1() {
			return direction1;
		}

		public String getDirection2() {
			return direction2;
		}

		public int getLight1() {
			return light1;
		}

		public int getMoto1() {
			return moto1;
		}

		public int getComm1() {
			return comm1;
		}

		public int getLight2() {
			return light2;
		}

		public int getMoto2() {
			return moto2;
		}

		public int getComm2() {
			return comm2;
		}

		public int getSpeed1() {
			return speed1;
		}

		public int getSpeed2() {
			return speed2;
		}

		public int getTotal1() {
			return total1;
		}

		public int getTotal2() {
			return total2;
		}
		
	}
}
