package br.com.tracevia.webapp.model.sos;

public class SosData{
	
	private String id;
	private String equip_id;
	private String start_date;
	private String end_date;
	private String answered_date;
	private String user;
	private String state_Id;
	private String audio_file;
	
	public SosData(String id, String start_date, String end_date, String answered_date,
			String user, String state_id, String audio_file, String equip_id) {
		
		this.id = id; this.start_date = start_date; this.end_date = end_date; this.audio_file = audio_file;
		this.answered_date = answered_date; this.user = user; this.start_date = start_date; this.equip_id = equip_id;
		
	}
	public SosData() {
		
	}
	
	public String getEquip_id() {
		return equip_id;
	}
	public void setEquip_id(String equip_id) {
		this.equip_id = equip_id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public String getAnswered_date() {
		return answered_date;
	}
	public void setAnswered_date(String answered_date) {
		this.answered_date = answered_date;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getState_Id() {
		return state_Id;
	}
	public void setState_Id(String state_Id) {
		this.state_Id = state_Id;
	}
	public String getAudio_file() {
		return audio_file;
	}
	public void setAudio_file(String audio_file) {
		this.audio_file = audio_file;
	}
		
}