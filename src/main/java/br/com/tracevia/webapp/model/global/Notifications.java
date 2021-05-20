package br.com.tracevia.webapp.model.global;

public class Notifications {
	
	private int status;
	private int equipId;	
	private int alarmType;
	private String equipName;
	private String equipType;
	private String dateTime;	
	private String description;
	private String km;
	private String viewedBgColor;
			
	public Notifications(int status, int equipId, int alarmType, String equipName, String equipType, String dateTime,
			String description, String km, String viewedBgColor) {
		
		this.status = status;
		this.equipId = equipId;
		this.alarmType = alarmType;
		this.equipName = equipName;
		this.equipType = equipType;
		this.dateTime = dateTime;
		this.description = description;
		this.km = km;
		this.viewedBgColor = viewedBgColor;
	}

	public Notifications() {}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getEquipId() {
		return equipId;
	}

	public void setEquipId(int equipId) {
		this.equipId = equipId;
	}

	public int getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(int alarmType) {
		this.alarmType = alarmType;
	}

	public String getEquipName() {
		return equipName;
	}

	public void setEquipName(String equipName) {
		this.equipName = equipName;
	}

	public String getEquipType() {
		return equipType;
	}

	public void setEquipType(String equipType) {
		this.equipType = equipType;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getKm() {
		return km;
	}

	public void setKm(String km) {
		this.km = km;
	}

	public String getViewedBgColor() {
		return viewedBgColor;
	}

	public void setViewedBgColor(String viewedBgColor) {
		this.viewedBgColor = viewedBgColor;
	}	
	
	
		
}
