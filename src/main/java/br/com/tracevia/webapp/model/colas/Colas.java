package br.com.tracevia.webapp.model.colas;

import br.com.tracevia.webapp.model.global.Equipments;

public class Colas extends Equipments {

	public Colas() {
		super();		
	}

	public Colas(int equip_id, String table_id, String equip_type, String equip_ip, int port, String creation_date,
			String creation_username, String update_date, String update_username, String nome, String estrada,
			String cidade, String km, int linearPosX, int linearPosY, int mapPosX, int mapPosY, int mapWidth,
			int height, int linearWidth, int dlgPosX, int dlgPosY, int model, String master_sip, int status, int lastStatus, boolean notificacao,
			boolean visible) {
		super(equip_id, table_id, equip_type, equip_ip, port, creation_date, creation_username, update_date, update_username, nome,
				estrada, cidade, km, linearPosX, linearPosY, mapPosX, mapPosY, mapWidth, height, linearWidth, dlgPosX, dlgPosY,
				model, master_sip, status, lastStatus, notificacao, visible);
		// TODO Auto-generated constructor stub
	}
		

}
