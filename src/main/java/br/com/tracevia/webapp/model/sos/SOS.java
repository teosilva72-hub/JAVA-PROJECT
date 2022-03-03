package br.com.tracevia.webapp.model.sos;

import br.com.tracevia.webapp.model.global.Equipments;

public class SOS extends Equipments {
	
	private int sos_comms;
	private String sip;	
	private int model;
	private String start_data;
	private String end_data;

	public SOS(int equip_id, String table_id, String equip_type, String equip_ip, String creation_date,
			String creation_username, String update_date, String update_username, String nome, String estrada,
			String cidade, String km, int linearPosX, int linearPosY, int mapPosX, int mapPosY, int mapWidth,
			int height, int linearWidth, int dlgPosX, int dlgPosY, String direction, int status, int lastStatus,
			boolean notificacao, int port, boolean visible, int sos_comms, String sip, int model, String start_data, String end_data) {
		
		super(equip_id, table_id, equip_type, equip_ip, creation_date, creation_username, update_date, update_username,
				nome, estrada, cidade, km, linearPosX, linearPosY, mapPosX, mapPosY, mapWidth, height, linearWidth,
				dlgPosX, dlgPosY, direction, status, lastStatus, notificacao, port, visible);
		
		this.sos_comms = sos_comms;
		this.sip = sip;
		this.model = model;
		this.start_data = start_data;
		this.end_data = end_data;
	}

	public SOS() {		
		super();
	}
	
	public String getStart_data() {
		return start_data;
	}

	public void setStart_data(String start_data) {
		this.start_data = start_data;
	}

	public String getEnd_data() {
		return end_data;
	}

	public void setEnd_data(String end_data) {
		this.end_data = end_data;
	}

	public int getSos_comms() {
		return sos_comms;
	}

	public void setSos_comms(int sos_comms) {
		this.sos_comms = sos_comms;
	}

	public String getSip() {
		return sip;
	}
	
	public void setSip(String sip) {
		this.sip = sip;
		
	}
	
	public int getModel() {
		return model;
	}

	public void setModel(int model) {
		this.model = model;
	}
	
}

	
