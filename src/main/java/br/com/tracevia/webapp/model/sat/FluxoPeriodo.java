package br.com.tracevia.webapp.model.sat;

public class FluxoPeriodo {
	
	private String equip;
	private String date;						
	private String interval;
	private String direction1;
	private String direction2;
	private int moto1;
	private int auto1;
	private int com1;
	private int moto2;
	private int auto2;
	private int com2;
	private int total1;
	private int total2;
	private int speed1;
	private int speed2;
	
	public FluxoPeriodo(String equip, String date, String interval, String direction1, String direction2, int moto1, int auto1,
			int com1, int moto2, int auto2, int com2, int total1, int total2, int speed1, int speed2) {
	
		this.equip = equip;
		this.date = date;
		this.interval = interval;
		this.direction1 = direction1;
		this.direction2 = direction2;
		this.moto1 = moto1;
		this.auto1 = auto1;
		this.com1 = com1;
		this.moto2 = moto2;
		this.auto2 = auto2;
		this.com2 = com2;
		this.total1 = total1;
		this.total2 = total2;
		this.speed1 = speed1;
		this.speed2 = speed2;
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

	public int getMoto1() {
		return moto1;
	}

	public void setMoto1(int moto1) {
		this.moto1 = moto1;
	}

	public int getAuto1() {
		return auto1;
	}

	public void setAuto1(int auto1) {
		this.auto1 = auto1;
	}

	public int getCom1() {
		return com1;
	}

	public void setCom1(int com1) {
		this.com1 = com1;
	}

	public int getMoto2() {
		return moto2;
	}

	public void setMoto2(int moto2) {
		this.moto2 = moto2;
	}

	public int getAuto2() {
		return auto2;
	}

	public void setAuto2(int auto2) {
		this.auto2 = auto2;
	}

	public int getCom2() {
		return com2;
	}

	public void setCom2(int com2) {
		this.com2 = com2;
	}

	public int getTotal1() {
		return total1;
	}

	public void setTotal1(int total1) {
		this.total1 = total1;
	}

	public int getTotal2() {
		return total2;
	}

	public void setTotal2(int total2) {
		this.total2 = total2;
	}

	public int getSpeed1() {
		return speed1;
	}

	public void setSpeed1(int speed1) {
		this.speed1 = speed1;
	}

	public int getSpeed2() {
		return speed2;
	}

	public void setSpeed2(int speed2) {
		this.speed2 = speed2;
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
