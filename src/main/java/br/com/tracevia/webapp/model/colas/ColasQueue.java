package br.com.tracevia.webapp.model.colas;

import java.util.Date;

public class ColasQueue {
	public int device;
	public String direction;
    public int lane;
    public int local;
    public String km;
    public Date date;

	public ColasQueue() {}

	public ColasQueue(int device, String direction, int lane, int local, String km) {
        this.device = device;
        this.direction = direction;
        this.lane = lane;
        this.local = local;
        this.km = km;
    }

	public int getDevice() {
		return device;
	}

	public void setDevice(int device) {
		this.device = device;
	}
	
	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public int getLane() {
		return lane;
	}

	public void setLane(int lane) {
		this.lane = lane;
	}

	public int getLocal() {
		return local;
	}

	public void setLocal(int local) {
		this.local = local;
	}

	public String getKm() {
		return km;
	}

	public void setKm(String km) {
		this.km = km;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getTime() {
		switch (this.local) {
			case 0:
				return "0 minute";
				
			case 2:
				return "< 1 minute";
				
			case 1:
				return "2 minute";
				
			case 3:
				return "3 minute";
			
			default:
				return "";
		}
	}

}
