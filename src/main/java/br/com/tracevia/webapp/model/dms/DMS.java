package br.com.tracevia.webapp.model.dms;

import br.com.tracevia.webapp.model.global.Equipments;

public class DMS extends Equipments {
	
	private int leds;
	private int dms_type;
	private String dmsType_name;
	private String dms_ip;	
	private Messages message;
	private Messages messageChange;
	private boolean stat;
	private boolean msg_status;
		
	public DMS(int equip_id, String table_id, String equip_type, String equip_ip, String creation_date,
			String creation_username, String update_date, String update_username, String nome, String estrada,
			String cidade, String km, int linearPosX, int linearPosY, int mapPosX, int mapPosY, int mapWidth,
			int height, int linearWidth, int dlgPosX, int dlgPosY, int status, int lastStatus, boolean notificacao,
			boolean visible, int leds, int dms_type, String dmsType_name, String dms_ip, Messages message,
			Messages messageChange, boolean stat, boolean msg_status) {
		
		super(equip_id, table_id, equip_type, equip_ip, creation_date, creation_username, update_date, update_username,
				nome, estrada, cidade, km, linearPosX, linearPosY, mapPosX, mapPosY, mapWidth, height, linearWidth,
				dlgPosX, dlgPosY, status, lastStatus, notificacao, visible);
		
		this.leds = leds;
		this.dms_type = dms_type;
		this.dmsType_name = dmsType_name;
		this.dms_ip = dms_ip;
		this.message = message;
		this.messageChange = messageChange;
		this.stat = stat;
		this.msg_status = msg_status;
	}

	public DMS() {
		super();		
	}

	public int getLeds() {
		return leds;
	}

	public void setLeds(int leds) {
		this.leds = leds;
	}

	public int getDms_type() {
		return dms_type;
	}

	public void setDms_type(int dms_type) {
		this.dms_type = dms_type;
	}

	public String getDmsType_name() {
		return dmsType_name;
	}

	public void setDmsType_name(String dmsType_name) {
		this.dmsType_name = dmsType_name;
	}

	public String getDms_ip() {
		return dms_ip;
	}

	public void setDms_ip(String dms_ip) {
		this.dms_ip = dms_ip;
	}

	public Messages getMessage() {
		return message;
	}

	public void setMessage(Messages message) {
		this.message = message;
	}

	public Messages getMessageChange() {
		return messageChange;
	}

	public void setMessageChange(Messages messageChange) {
		this.messageChange = messageChange;
	}

	public boolean isStat() {
		return stat;
	}

	public void setStat(boolean stat) {
		this.stat = stat;
	}

	public boolean isMsg_status() {
		return msg_status;
	}

	public void setMsg_status(boolean msg_status) {
		this.msg_status = msg_status;
	}
	


	
}
