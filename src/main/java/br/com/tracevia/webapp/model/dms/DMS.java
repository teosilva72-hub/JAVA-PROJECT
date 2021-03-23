package br.com.tracevia.webapp.model.dms;

import br.com.tracevia.webapp.model.global.Equipments;

public class DMS extends Equipments {
	
	private int leds;
	private int dms_type;
	private int dms_driver;
	private String dms_ip;	
	private Messages message;
	private Messages messageChange;
	private boolean stat;
	private boolean msg_status;
	
	public DMS(int equip_id, String table_id, String creation_date, String creation_username, String update_date,
			String update_username, String nome, String estrada, String cidade, String km, String posicao,
			int linearPosX, int linearPosY, int mapPosX, int mapPosY, int mapWidth, int height, int linearWidth,
			int dlgPosX, int dlgPosY, int status, boolean notificacao, boolean visible, int leds, int dms_type,
			int dms_driver, String dms_ip, Messages message, Messages messageChange, boolean stat, boolean msg_status) {
		
		super(equip_id, table_id, creation_date, creation_username, update_date, update_username, nome, estrada, cidade,
				km, posicao, linearPosX, linearPosY, mapPosX, mapPosY, mapWidth, height, linearWidth, dlgPosX, dlgPosY,
				status, notificacao, visible);
		
		this.leds = leds;
		this.dms_type = dms_type;
		this.dms_driver = dms_driver;
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
	
	public int getDms_driver() {
		return dms_driver;
	}

	public void setDms_driver(int dms_driver) {
		this.dms_driver = dms_driver;
	}

	public String getDms_ip() {
		return dms_ip;
	}

	public void setDms_ip(String dms_ip) {
		this.dms_ip = dms_ip;
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

	public Messages getMessageChange() {
		return messageChange;
	}

	public void setMessageChange(Messages messageChange) {
		this.messageChange = messageChange;
	}

	public Messages getMessage() {
		return message;
	}

	public void setMessage(Messages message) {
		this.message = message;
	}


	
}
