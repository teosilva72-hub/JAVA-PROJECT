package br.com.tracevia.webapp.cfg;

public enum NotificationsAlarmsEnum {
	
	FULL_BATTERY(1),
	LOW_BATTERY(2),
	DOOR_CLOSED(3),
	DOOR_OPENED(4),
	POWER_ON(5),
	POWER_OFF(6),
	ONLINE(7),
	OFFLINE(8),
	NO_PRESENCE(9),
	PRESENCE(10),
	AMBIENT_TEMPERATURE(11),
	HIGH_TEMPERATURE(12),
	TRACK_CONNECTED(13),
	TRACK_DESCONNECTED(14);

	private int alarm; 	 
	 
    NotificationsAlarmsEnum(int alarm) {
        this.alarm = alarm;
    }
 
    public int getAlarm() {
        return alarm;
    }	

}
