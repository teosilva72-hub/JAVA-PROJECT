package br.com.tracevia.webapp.model.videowall;

import br.com.tracevia.webapp.model.global.Equipments;

public class VIDEOWALL extends Equipments {

	public VIDEOWALL() {
		super();		
	}

	public VIDEOWALL(int equip_id, String table_id, String creation_date, String creation_username, String nome,
			String estrada, String cidade, String km, String posicao, int linearPosX, int linearPosY, int linearWidth,
			int mapPosX, int mapPosY, int mapWidth, int height, int dlgPosX, int dlgPosY, int status,
			boolean notificacao, boolean visible) {
		super(equip_id, table_id, creation_date, creation_username, nome, estrada, cidade, km, posicao, linearPosX, linearPosY,
				linearWidth, mapPosX, mapPosY, mapWidth, height, dlgPosX, dlgPosY, status, notificacao, visible);
		// TODO Auto-generated constructor stub
	}

}
