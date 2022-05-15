package br.com.tracevia.webapp.model.global;

public class DirectionModel {

	private int equipId; 
	private String direction;	
		
	public DirectionModel(int equipId, String direction) {
		super();
		this.equipId = equipId;
		this.direction = direction;
	}
	
	public DirectionModel() {}	
	
	public int getEquipId() {
		return equipId;
	}
	
	public void setEquipId(int equipId) {
		this.equipId = equipId;
	}
	
	public String getDirection() {
		return direction;
	}
	
	public void setDirection(String direction) {
		this.direction = direction;
	}
	
}
