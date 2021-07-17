package br.com.tracevia.webapp.model.sos;

import br.com.tracevia.webapp.model.global.Equipments;

public class SOS extends Equipments {
	
	private int sos_comms;

	public SOS(int equip_id, String table_id, String equip_type, String equip_ip, String creation_date,
			String creation_username, String update_date, String update_username, String nome, String estrada,
			String cidade, String km, int linearPosX, int linearPosY, int mapPosX, int mapPosY, int mapWidth,
			int height, int linearWidth, int dlgPosX, int dlgPosY, int status, int lastStatus, boolean notificacao,
			boolean visible) {
		
		super(equip_id, table_id, equip_type, equip_ip, creation_date, creation_username, update_date, update_username, nome,
				estrada, cidade, km, linearPosX, linearPosY, mapPosX, mapPosY, mapWidth, height, linearWidth, dlgPosX, dlgPosY,
				status, lastStatus, notificacao, visible);		
	}

	public SOS() {		
		super();
	}

	public int getSos_comms() {
		return sos_comms;
	}

	public void setSos_comms(int sos_comms) {
		this.sos_comms = sos_comms;
	}
	
	

}
