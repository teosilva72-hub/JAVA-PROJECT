package br.com.tracevia.webapp.model.dai;

import java.util.Date;

public class DAIQueue {
	public String name;
	public String direction;
    public String km;
	public int lane;
	public int channel;
	public String incident;
    public Date date;

	public DAIQueue() {}

	public DAIQueue(String name, String direction, String km, int lane, int channel, String incident) {
        this.name = name;
        this.direction = direction;
        this.km = km;
        this.lane = lane;
        this.channel = channel;
        this.incident = incident;
        
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	public String getKm() {
		return km;
	}

	public void setKm(String km) {
		this.km = km;
	}
	
	public int getLane() {
		return lane;
	}

	public void setLane(int lane) {
		this.lane = lane;
	}

	public int getChannel() {
		return channel;
	}

	public void setChannel(int channel) {
		this.channel = channel;
	}


	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getIncident() {
		return incident;
	}

	public void setIncident(String incident) {
		this.incident = incident;
	}

}
