package br.com.tracevia.webapp.model.colas;


public class ColasData {
	String id;
	String lane;
	String cam;
	String date;
	String hour;
	String waiting;
	
	public ColasData(String id, String lane, String cam,
					String date, String hour, String waiting) {
		this.id = id;
		this.lane = lane;
		this.cam = cam;
		this.date = date;
		this.hour = hour;
		this.waiting = waiting;
		
	}
	public ColasData() {}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLane() {
		return lane;
	}
	public void setLane(String lane) {
		this.lane = lane;
	}
	public String getCam() {
		return cam;
	}
	public void setCam(String cam) {
		this.cam = cam;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
	public String getWaiting() {
		return waiting;
	}
	public void setWaiting(String waiting) {
		this.waiting = waiting;
	}
	
}