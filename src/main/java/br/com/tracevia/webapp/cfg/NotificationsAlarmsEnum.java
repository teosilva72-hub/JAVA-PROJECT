package br.com.tracevia.webapp.cfg;

public enum NotificationsStatusEnum {
	
	BATTERY_CHARGED("1"),
	BATTERY_LOW("2"),
	DOOR_CLOSED("3"),
	DOOR_OPENED("4"),
	POWER_ON("5"),
	POWER_OFF("6"),
	ON_LINE("7"),
	OFF_LINE("8"),
	PRESENCE_FAR("9"),
	PRESENCE_CLOSE("10"),
	TEMPERATURE_HIGH("11"),
	TEMPERATURE_LOW("12"),
	TRACK_CONNECTED("13"),
	TRACK_DESCONNECTED("14");

	private String status; 	 
	 
    NotificationsStatusEnum(String status) {
        this.status = status;
    }
 
    public String getStatus() {
        return status;
    }	

}
