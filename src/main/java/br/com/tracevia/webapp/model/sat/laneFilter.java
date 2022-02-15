package br.com.tracevia.webapp.model.sat;

public class laneFilter {
	
	private int id;
	private int equipId;
	private int lane;
	private String direction;
	
	public laneFilter(int id, int equipId, int lane, String direction) {		
		this.id = id;
		this.equipId = equipId;
		this.lane = lane;
		this.direction = direction;
	}
	
	public laneFilter() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEquipId() {
		return equipId;
	}

	public void setEquipId(int equipId) {
		this.equipId = equipId;
	}

	public int getLane() {
		return lane;
	}

	public void setLane(int lane) {
		this.lane = lane;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}
	
}
