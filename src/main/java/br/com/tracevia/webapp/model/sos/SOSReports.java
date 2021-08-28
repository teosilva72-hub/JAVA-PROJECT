package br.com.tracevia.webapp.model.sos;

public class SOSReports {
	
	private String date;
	private String time;	
	private String dateTime;
	private String sosName;
	private String total;
	private String batteryVolts;
	private String batteryAmp;
	private String solarVolts;
	private String solarAmp;
	private String openDoor;
	private String doorNumber;
	private String sosStatusOpened;
	private String sosStatusClosed;
	private String sosStatusTime;
	private String sosStatusType;
	private String callStatus;
	private String callReceived;
	private String callAnswered;	
	private String callMissed;	
	private String callEnded;	
	private String callError;	
	private String callDuration;	
	private String operator;
	private int callReceivedAmount;	
	private int callAnsweredAmount;		
	private int callMissedAmount;	
	private int callEndedAmount;	
	private int callErrorAmount;	
	
	
	// REMOVE IN FUTURE UPDATES
	
	private String equipment;
	public String[] equipments;	
	private String startDate;
	private String endDate;
	private String month; 
	private String year;		
	private String period;
	
	//Builder Pattern
	public static class Builder{
		
		public String equipment;
		public String date;
		public String time;	
		public String dateTime;
		public String sosName;
		public String total;
		public String batteryVolts;
		public String batteryAmp;
		public String solarVolts;
		public String solarAmp;
		public String openDoor;
		public String doorNumber;
		public String sosStatusOpened;
		public String sosStatusClosed;
		public String sosStatusTime;
		public String sosStatusType;
		public String callStatus;
		public String callReceived;	
		public String callAnswered;	
		public String callMissed;	
		public String callEnded;
		public String callError;
		public String callDuration;	
		public String operator;
		public int callReceivedAmount;	
		public int callAnsweredAmount;		
		public int callMissedAmount;	
		public int callEndedAmount;	
		public int callErrorAmount;				
				
		// ------------- Builders	
		
		// Equipments
		public Builder equipment(String equipment) {
			this.equipment = equipment;			
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
		
		// DateTime
		public Builder dateTime(String dateTime) {
			this.dateTime = dateTime;
			 return this;	
		}
		
		// SOS Name
		public Builder sosName(String sosName) {
			this.sosName = sosName;
			 return this;	
		}
		
		// Total
		 public Builder total(String total) {
			this.total = total; 
		    return this;			
	     }
		 
		// Battery Volts
		public Builder batteryVolts(String batteryVolts) {
			this.batteryVolts = batteryVolts; 
			  return this;			
		}
		
		// Battery Ampere
		public Builder batteryAmp(String batteryAmp) {
			this.batteryAmp = batteryAmp; 
			return this;			
		}
		
		// Solar Volts
			public Builder solarVolts(String solarVolts) {
				this.solarVolts = solarVolts; 
			    return this;			
		}
				
		// Solar Ampere
		public Builder solarAmp(String solarAmp) {
			this.solarAmp = solarAmp; 
			return this;			
		}
	
		// Open Door
		public Builder openDoor(String openDoor) {
			this.openDoor = openDoor; 
			return this;			
		}	
		
		// Door Number
		public Builder doorNumber(String doorNumber) {
			this.doorNumber = doorNumber; 
			return this;			
		}	
		
		// SOS Status Opened
		public Builder sosStatusOpened(String sosStatusOpened) {
			this.sosStatusOpened = sosStatusOpened; 
			return this;			
		}
		
		// SOS Status Closed
		public Builder sosStatusClosed(String sosStatusClosed) {
			this.sosStatusClosed = sosStatusClosed; 
			return this;			
		}
		
		// SOS Status Duration
		public Builder sosStatusTime(String sosStatusTime) {
			this.sosStatusTime = sosStatusTime; 
			return this;			
		}	
				
		// SOS Status Type
		public Builder sosStatusType(String sosStatusType) {
			this.sosStatusType = sosStatusType; 
			return this;			
		}	
		
		// Call Status
		public Builder callStatus(String callStatus) {
			this.callStatus = callStatus; 
			return this;			
		}
		
		// Call Received
		  public Builder callReceived(String callReceived) {
			this.callReceived = callReceived; 
			return this;			
		}
		  
		// Call Answered
		  public Builder callAnswered(String callAnswered) {
			this.callAnswered = callAnswered; 
			return this;			
		}
		  
		// Call Answered
		  public Builder callMissed(String callMissed) {
			this.callMissed = callMissed; 
			return this;			
		}
		  
		// Call Ended
		  public Builder callEnded(String callEnded) {
			this.callEnded = callEnded; 
			return this;			
		}
		  
		  
		// Call Error
		    public Builder callError(String callError) {
			  this.callError = callError; 
			  return this;			
		}
		    
		// Call Received Amount
		public Builder callReceivedAmount(int callReceivedAmount) {
			this.callReceivedAmount = callReceivedAmount; 
			return this;			
		}
			  
			// Call Answered Amount
			  public Builder callAnsweredAmount(int callAnsweredAmount) {
				this.callAnsweredAmount = callAnsweredAmount; 
				return this;			
			}
			  
			// Call Answered Amount
			  public Builder callMissedAmount(int callMissedAmount) {
				this.callMissedAmount = callMissedAmount; 
				return this;			
			}
			  
			// Call Ended Amount
			  public Builder callEndedAmount(int callEndedAmount) {
				this.callEndedAmount = callEndedAmount; 
				return this;			
			}
			  
			  
			// Call Error Amount
			    public Builder callErrorAmount(int callErrorAmount) {
				  this.callErrorAmount = callErrorAmount; 
				  return this;			
			}
		  
		   // Call Duration
		    public Builder callDuration(String callDuration) {
			  this.callDuration = callDuration; 
			  return this;			
		     }
		  
		  // Operator
		  public Builder operator(String operator) {
			this.operator = operator; 
			return this;			
		}
		  
	   // ---------------------------------------------------------
	   // GETTERS
	   // ---------------------------------------------------------
		  
		public String getDate() {
			return date;
		}

		public String getEquipment() {
			return equipment;
		}

		public String getTime() {
			return time;
		}

		public String getDateTime() {
			return dateTime;
		}

		public String getSosName() {
			return sosName;
		}

		public String getTotal() {
			return total;
		}

		public String getBatteryVolts() {
			return batteryVolts;
		}

		public String getBatteryAmp() {
			return batteryAmp;
		}

		public String getSolarVolts() {
			return solarVolts;
		}

		public String getSolarAmp() {
			return solarAmp;
		}

		public String getOpenDoor() {
			return openDoor;
		}

		public String getDoorNumber() {
			return doorNumber;
		}

		public String getSosStatusOpened() {
			return sosStatusOpened;
		}

		public String getSosStatusClosed() {
			return sosStatusClosed;
		}

		public String getSosStatusTime() {
			return sosStatusTime;
		}

		public String getSosStatusType() {
			return sosStatusType;
		}

		public String getCallStatus() {
			return callStatus;
		}

		public String getCallReceived() {
			return callReceived;
		}

		public String getCallAnswered() {
			return callAnswered;
		}

		public String getCallMissed() {
			return callMissed;
		}

		public String getCallEnded() {
			return callEnded;
		}
		
		public int getCallReceivedAmount() {
			return callReceivedAmount;
		}

		public void setCallReceivedAmount(int callReceivedAmount) {
			this.callReceivedAmount = callReceivedAmount;
		}

		public int getCallAnsweredAmount() {
			return callAnsweredAmount;
		}

		public void setCallAnsweredAmount(int callAnsweredAmount) {
			this.callAnsweredAmount = callAnsweredAmount;
		}

		public int getCallMissedAmount() {
			return callMissedAmount;
		}

		public void setCallMissedAmount(int callMissedAmount) {
			this.callMissedAmount = callMissedAmount;
		}

		public int getCallEndedAmount() {
			return callEndedAmount;
		}

		public void setCallEndedAmount(int callEndedAmount) {
			this.callEndedAmount = callEndedAmount;
		}

		public int getCallErrorAmount() {
			return callErrorAmount;
		}

		public void setCallErrorAmount(int callErrorAmount) {
			this.callErrorAmount = callErrorAmount;
		}

		public String getCallError() {
			return callError;
		}

		public String getCallDuration() {
			return callDuration;
		}

		public String getOperator() {
			return operator;
		}		  		
	}		
		
	public SOSReports() {}

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

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getSosName() {
		return sosName;
	}

	public void setSosName(String sosName) {
		this.sosName = sosName;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getBatteryVolts() {
		return batteryVolts;
	}

	public void setBatteryVolts(String batteryVolts) {
		this.batteryVolts = batteryVolts;
	}

	public String getBatteryAmp() {
		return batteryAmp;
	}

	public void setBatteryAmp(String batteryAmp) {
		this.batteryAmp = batteryAmp;
	}

	public String getSolarVolts() {
		return solarVolts;
	}

	public void setSolarVolts(String solarVolts) {
		this.solarVolts = solarVolts;
	}

	public String getSolarAmp() {
		return solarAmp;
	}

	public void setSolarAmp(String solarAmp) {
		this.solarAmp = solarAmp;
	}

	public String getOpenDoor() {
		return openDoor;
	}

	public void setOpenDoor(String openDoor) {
		this.openDoor = openDoor;
	}

	public String getDoorNumber() {
		return doorNumber;
	}

	public void setDoorNumber(String doorNumber) {
		this.doorNumber = doorNumber;
	}

	public String getSosStatusOpened() {
		return sosStatusOpened;
	}

	public void setSosStatusOpened(String sosStatusOpened) {
		this.sosStatusOpened = sosStatusOpened;
	}

	public String getSosStatusClosed() {
		return sosStatusClosed;
	}

	public void setSosStatusClosed(String sosStatusClosed) {
		this.sosStatusClosed = sosStatusClosed;
	}

	public String getCallStatus() {
		return callStatus;
	}

	public void setCallStatus(String callStatus) {
		this.callStatus = callStatus;
	}
		
	public String getSosStatusTime() {
		return sosStatusTime;
	}

	public void setSosStatusTime(String sosStatusTime) {
		this.sosStatusTime = sosStatusTime;
	}

	public String getSosStatusType() {
		return sosStatusType;
	}

	public void setSosStatusType(String sosStatusType) {
		this.sosStatusType = sosStatusType;
	}

	public String getCallReceived() {
		return callReceived;
	}

	public void setCallReceived(String callReceived) {
		this.callReceived = callReceived;
	}
	
	public String getCallAnswered() {
		return callAnswered;
	}

	public void setCallAnswered(String callAnswered) {
		this.callAnswered = callAnswered;
	}

	public String getCallMissed() {
		return callMissed;
	}

	public void setCallMissed(String callMissed) {
		this.callMissed = callMissed;
	}

	public String getCallEnded() {
		return callEnded;
	}

	public void setCallEnded(String callEnded) {
		this.callEnded = callEnded;
	}
	
	public String getCallError() {
		return callError;
	}

	public void setCallError(String callError) {
		this.callError = callError;
	}
	
	public int getCallReceivedAmount() {
		return callReceivedAmount;
	}

	public void setCallReceivedAmount(int callReceivedAmount) {
		this.callReceivedAmount = callReceivedAmount;
	}

	public int getCallAnsweredAmount() {
		return callAnsweredAmount;
	}

	public void setCallAnsweredAmount(int callAnsweredAmount) {
		this.callAnsweredAmount = callAnsweredAmount;
	}

	public int getCallMissedAmount() {
		return callMissedAmount;
	}

	public void setCallMissedAmount(int callMissedAmount) {
		this.callMissedAmount = callMissedAmount;
	}

	public int getCallEndedAmount() {
		return callEndedAmount;
	}

	public void setCallEndedAmount(int callEndedAmount) {
		this.callEndedAmount = callEndedAmount;
	}

	public int getCallErrorAmount() {
		return callErrorAmount;
	}

	public void setCallErrorAmount(int callErrorAmount) {
		this.callErrorAmount = callErrorAmount;
	}

	public String getCallDuration() {
		return callDuration;
	}

	public void setCallDuration(String callDuration) {
		this.callDuration = callDuration;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
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

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}
		

}
