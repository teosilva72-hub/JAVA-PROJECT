package br.com.tracevia.webapp.model.global;

public class NotificationsAlert {

private int equipId;
private int alarmType;
private String equipType;
private String equipIP;
private String equipName;
private String km;
private String dateTime;
private String description;
private String descriptionEmail;
private String viewedBgColor;
private int timer;
private boolean batteryStatus;
private boolean batteryLastStatus;
private boolean batteryEmail;
private boolean onlineStatus;
private boolean onlineLastStatus;
private boolean onlineEmail;

public NotificationsAlert(int equipId, int alarmType, String equipType, String equipIP, String equipName, String km, String dateTime, String description, String descriptionEmail, String viewedBgColor, int timer, boolean onlineStatus, boolean onlineLastStatus, boolean onlineEmail, boolean batteryStatus, boolean batteryLastStatus, boolean batteryEmail)
{
	this.equipId = equipId;
	this.alarmType = alarmType;
	this.equipType = equipType;
	this.equipIP = equipIP;
	this.equipName = equipName;
	this.km = km;
	this.dateTime = dateTime;
	this.description = description;
	this.descriptionEmail = descriptionEmail;
	this.viewedBgColor = viewedBgColor;
	this.timer = timer;
	this.onlineStatus = onlineStatus;
	this.onlineLastStatus = onlineLastStatus;
	this.onlineEmail = onlineEmail;
	this.batteryStatus = batteryStatus;
	this.batteryLastStatus = batteryLastStatus;
	this.batteryEmail = batteryEmail;
}

public NotificationsAlert() { }

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

public String getEquipType() {
	return equipType;
}

public void setEquipType(String equipType) {
	this.equipType = equipType;
}

public String getEquipIP() {
	return equipIP;
}

public void setEquipIP(String equipIP) {
	this.equipIP = equipIP;
}

public String getEquipName() {
	return equipName;
}

public void setEquipName(String equipName) {
	this.equipName = equipName;
}

public String getKm() {
	return km;
}

public void setKm(String km) {
	this.km = km;
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

public String getDescriptionEmail() {
	return descriptionEmail;
}

public void setDescriptionEmail(String descriptionEmail) {
	this.descriptionEmail = descriptionEmail;
}

public String getViewedBgColor() {
	return viewedBgColor;
}

public void setViewedBgColor(String viewedBgColor) {
	this.viewedBgColor = viewedBgColor;
}

public int getTimer() {
	return timer;
}

public void setTimer(int timer) {
	this.timer = timer;
}

public boolean isBatteryStatus() {
	return batteryStatus;
}

public void setBatteryStatus(boolean batteryStatus) {
	this.batteryStatus = batteryStatus;
}

public boolean isBatteryLastStatus() {
	return batteryLastStatus;
}

public void setBatteryLastStatus(boolean batteryLastStatus) {
	this.batteryLastStatus = batteryLastStatus;
}

public boolean isBatteryEmail() {
	return batteryEmail;
}

public void setBatteryEmail(boolean batteryEmail) {
	this.batteryEmail = batteryEmail;
}

public boolean isOnlineStatus() {
	return onlineStatus;
}

public void setOnlineStatus(boolean onlineStatus) {
	this.onlineStatus = onlineStatus;
}

public boolean isOnlineLastStatus() {
	return onlineLastStatus;
}

public void setOnlineLastStatus(boolean onlineLastStatus) {
	this.onlineLastStatus = onlineLastStatus;
}

public boolean isOnlineEmail() {
	return onlineEmail;
}

public void setOnlineEmail(boolean onlineEmail) {
	this.onlineEmail = onlineEmail;
}


}
