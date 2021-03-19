package br.com.tracevia.webapp.model.dms;

import br.com.tracevia.webapp.model.global.Equipments;

public class DMS extends Equipments {
	
	private int leds;	
	private String dms_ip;
	private Messages message;
	private Messages messageChange;
	private boolean stat;
	private boolean msg_status;
	
	public DMS(int equip_id, String table_id, String creation_date, String creation_username, int number_lanes, String nome,
			String estrada, String cidade, String km, String dir_lane1, String dir_lane2, String dir_lane3, String dir_lane4, String dir_lane5, String dir_lane6, String dir_lane7, String dir_lane8,
			String posicao, int linearPosX, int linearPosY, int linearWidth,
			int mapPosX, int mapPosY, int mapWidth, int height, int dlgPosX, int dlgPosY, int status,
			boolean notificacao, boolean visible, int leds, String dms_ip, boolean stat, boolean msg_status) {
		super(equip_id, table_id, creation_date, creation_username, number_lanes, nome, estrada, cidade, km, 
				dir_lane1, dir_lane2, dir_lane3, dir_lane4, dir_lane5, dir_lane6, dir_lane7, dir_lane8,
				posicao, linearPosX, linearPosY, linearWidth, mapPosX, mapPosY, mapWidth, height, dlgPosX, dlgPosY, status, notificacao,
				visible);
		this.leds = leds;
		this.dms_ip = dms_ip;
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
