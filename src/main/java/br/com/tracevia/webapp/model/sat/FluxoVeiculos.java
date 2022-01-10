package br.com.tracevia.webapp.model.sat;

public class FluxoVeiculos {
	
	private String date;						
	private String interval;
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
		
	public FluxoVeiculos(String date, String interval, int moto1, int auto1, int com1, int moto2, int auto2, int com2,
			int total1, int total2, int speed1, int speed2) {
	
		this.date = date;
		this.interval = interval;
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

	public FluxoVeiculos() {}
     
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
   

	// Builder Pattern
	public static class Builder{
		
		private String date;
		private String time;
        private int light1;
        private int moto1;
        private int comm1;
        private int light2;
        private int moto2;
        private int comm2;
        private int speed1;
        private int speed2;
        private int speedHour1;
        private int speedHour2;
        private int total1;
        private int total2;
        private int totalACM1;
        private int totalACM2;
        private int fluxo1;
        private int fluxo2;
        private int fluxoACM1;
        private int fluxoACM2;
        private int lightACM1;
        private int motoACM1;
        private int commACM1;
        private int lightACM2;
        private int motoACM2;
        private int commACM2;
        private int speedACM1;
        private int speedACM2;
        		
		// Constructors		
		
		public Builder date(String date) {
			this.date = date; 
		    return this;			
		}
		
		// TIME
		public Builder time(String time) {
			this.time = time;			
			return this;			
		}
		
		// MOTO S1
		public Builder motoS1(int moto1) {
			this.moto1 = moto1;			
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
		
		// MOTO S2
		public Builder motoS2(int moto2) {
			this.moto2 = moto2;			
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
		
		// SPEED S1
		public Builder speedS1(int speed1) {
			this.speed1 = speed1;			
			return this;			
		}	
		
		// SPEED S2
		public Builder speedS2(int speed2) {
			this.speed2 = speed2;			
			return this;			
		}
		
		// SPEED S1
		public Builder speedHourS1(int speedHour1) {
			this.speedHour1 = speedHour1;			
			return this;			
		}	
		
		// SPEED S2
		public Builder speedHourS2(int speedHour2) {
			this.speedHour2 = speedHour2;			
			return this;			
		}	

		// TOTAL S1
		public Builder totalS1(int total1) {
			this.total1 = total1;			
			return this;			
		}	
		
		// TOTAL S2
		public Builder totalS2(int total2) {
			this.total2 = total2;			
			return this;			
		}	
		
		// TOTAL ACM S1
		public Builder totalACMS1(int totalACM1) {
			this.totalACM1 = totalACM1;			
			return this;			
		}	
		
		// TOTAL ACM S2
		public Builder totalACMS2(int totalACM2) {
			this.totalACM2 = totalACM2;			
			return this;			
		}	
		
		// FLUXO S1
		public Builder fluxoS1(int fluxo1) {
			this.fluxo1 = fluxo1;			
			return this;			
		}	
		
		// FLUXO S2
		public Builder fluxoS2(int fluxo2) {
			this.fluxo2 = fluxo2;			
			return this;			
		}	
		
		// FLUXO ACM S1
		public Builder fluxoACMS1(int fluxoACM1) {
			this.fluxoACM1 = fluxoACM1;			
			return this;			
		}	
		
		// FLUXO ACM S2
		public Builder fluxoACMS2(int fluxoACM2) {
			this.fluxoACM2 = fluxoACM2;			
			return this;			
		}	
		
		// MOTO ACM S1
		public Builder motoACMS1(int motoACM1) {
			this.motoACM1 = motoACM1;			
			return this;			
		}
		
		// LIGHT ACM S1
		public Builder lightACMS1(int lightACM1) {
			this.lightACM1 = lightACM1;			
			return this;			
		}
				
		// COMM ACM S1
		public Builder commACMS1(int commACM1) {
			this.commACM1 = commACM1;			
			return this;			
		}
		
		// SPEED ACM S1
		public Builder speedACMS1(int speedACM1) {
			this.speedACM1 = speedACM1;			
			return this;			
		}	

		// MOTO ACM S2
		public Builder motoACMS2(int motoACM2) {
			this.motoACM2 = motoACM2;			
			return this;			
		}
		
		// LIGHT ACM S2
		public Builder lightACMS2(int lightACM2) {
			this.lightACM2 = lightACM2;			
			return this;			
		}
				
		// COMM ACM S2
		public Builder commACMS2(int commACM2) {
			this.commACM2 = commACM2;			
			return this;			
		}
				
		// SPEED ACM S2
		public Builder speedACMS2(int speedACM2) {
			this.speedACM2 = speedACM2;			
			return this;			
		}
		
		// GETTERS

		public String getDate() {
			return date;
		}

		public String getTime() {
			return time;
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

		public int getSpeedHour1() {
			return speedHour1;
		}

		public int getSpeedHour2() {
			return speedHour2;
		}

		public int getTotal1() {
			return total1;
		}

		public int getTotal2() {
			return total2;
		}

		public int getTotalACM1() {
			return totalACM1;
		}

		public int getTotalACM2() {
			return totalACM2;
		}

		public int getFluxo1() {
			return fluxo1;
		}

		public int getFluxo2() {
			return fluxo2;
		}

		public int getFluxoACM1() {
			return fluxoACM1;
		}

		public int getFluxoACM2() {
			return fluxoACM2;
		}

		public int getLightACM1() {
			return lightACM1;
		}

		public int getMotoACM1() {
			return motoACM1;
		}

		public int getCommACM1() {
			return commACM1;
		}

		public int getLightACM2() {
			return lightACM2;
		}

		public int getMotoACM2() {
			return motoACM2;
		}

		public int getCommACM2() {
			return commACM2;
		}

		public int getSpeedACM1() {
			return speedACM1;
		}

		public int getSpeedACM2() {
			return speedACM2;
		}
	}

}
