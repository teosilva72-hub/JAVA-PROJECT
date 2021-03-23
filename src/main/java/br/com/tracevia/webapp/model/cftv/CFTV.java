package br.com.tracevia.webapp.model.cftv;

import br.com.tracevia.webapp.model.global.Equipments;

public class CFTV extends Equipments{
	
	private int cftv_serial;
		
	public CFTV(int equip_id, String table_id, String creation_date, String creation_username, String update_date,
			String update_username, String nome, String estrada, String cidade, String km, String posicao,
			int linearPosX, int linearPosY, int mapPosX, int mapPosY, int mapWidth, int height, int linearWidth,
			int dlgPosX, int dlgPosY, int status, boolean notificacao, boolean visible, int cftv_serial) {
		
		super(equip_id, table_id, creation_date, creation_username, update_date, update_username, nome, estrada, cidade,
				km, posicao, linearPosX, linearPosY, mapPosX, mapPosY, mapWidth, height, linearWidth, dlgPosX, dlgPosY,
				status, notificacao, visible);
		
		this.cftv_serial = cftv_serial;
	}


	public CFTV() {
		super();		
	}


	public int getCftv_serial() {
		return cftv_serial;
	}


	public void setCftv_serial(int cftv_serial) {
		this.cftv_serial = cftv_serial;
	}
	
}
