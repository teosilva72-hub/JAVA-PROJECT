package br.com.tracevia.webapp.model.global;

public class NotificationsCount {
	
 private int total;
 private int battery;
 private int connection;
 private int door;
 private int energy;
 private int events;
 private int presence;
 private int temperature;

 public NotificationsCount(int total, int battery, int connection, int door, int energy, int events, int presence, int temperature)
 {
     this.total = total;
     this.battery = battery;
     this.connection = connection;
     this.door = door;
     this.energy = energy;
     this.events = events;
     this.presence = presence;
     this.temperature = temperature;
 }

public NotificationsCount() { }

public int getTotal() {
	return total;
}

public void setTotal(int total) {
	this.total = total;
}

public int getBattery() {
	return battery;
}

public void setBattery(int battery) {
	this.battery = battery;
}

public int getConnection() {
	return connection;
}

public void setConnection(int connection) {
	this.connection = connection;
}

public int getDoor() {
	return door;
}

public void setDoor(int door) {
	this.door = door;
}

public int getEnergy() {
	return energy;
}

public void setEnergy(int energy) {
	this.energy = energy;
}

public int getEvents() {
	return events;
}

public void setEvents(int events) {
	this.events = events;
}

public int getPresence() {
	return presence;
}

public void setPresence(int presence) {
	this.presence = presence;
}

public int getTemperature() {
	return temperature;
}

public void setTemperature(int temperature) {
	this.temperature = temperature;
}
     
     
}
