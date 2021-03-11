package br.com.tracevia.webapp.model.comms;

import br.com.tracevia.webapp.model.global.Equipments;

public class COMMS extends Equipments {

	public COMMS() {
		super();		
	}

	public COMMS(int equip_id, String table_id, String creation_date, String creation_username, int number_lanes, String nome,
			String estrada, String cidade, String km, String dir_lane1, String dir_lane2, String dir_lane3, String dir_lane4, String dir_lane5, String dir_lane6, String dir_lane7, String dir_lane8,
			String posicao, int linearPosX, int linearPosY, int linearWidth,
			int mapPosX, int mapPosY, int mapWidth, int height, int dlgPosX, int dlgPosY, int status,
			boolean notificacao, boolean visible, int leds, String dms_ip, boolean stat, boolean msg_status) {
		super(equip_id, table_id, creation_date, creation_username, number_lanes, nome, estrada, cidade, km, 
				dir_lane1, dir_lane2, dir_lane3, dir_lane4, dir_lane5, dir_lane6, dir_lane7, dir_lane8,
				posicao, linearPosX, linearPosY, linearWidth, mapPosX, mapPosY, mapWidth, height, dlgPosX, dlgPosY, status, notificacao,
				visible);
		// TODO Auto-generated constructor stub
	}

	

	

	
	

}
