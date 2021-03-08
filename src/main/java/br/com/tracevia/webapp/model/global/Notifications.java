package br.com.tracevia.webapp.model.global;

public class Notifications {
	
	private int status;
	private int equipId;
	private String viewedBgColor;
	private String title;
	private String equipName;
	private String dateTime;
	private String type;
	private String description;

		
	public Notifications(int status, int equipId, String viewedBgColor, String title, String equipName, String dateTime, String type, String description) {
		
		this.status = status;
		this.equipId = equipId;
		this.viewedBgColor = viewedBgColor;
		this.title = title;
		this.equipName = equipName;
		this.dateTime = dateTime;
		this.type = type;
		this.description = description;
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
	public void setEquipId(int notifEquipId) {
		this.equipId = notifEquipId;
	}	
	
	public String getViewedBgColor() {
		return viewedBgColor;
	}

	public void setViewedBgColor(String viewedBgColor) {
		this.viewedBgColor = viewedBgColor;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getEquipName() {
		return equipName;
	}
	public void setEquipName(String equipName) {
		this.equipName = equipName;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}	
	
}
