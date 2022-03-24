package br.com.tracevia.webapp.model.hit;

import br.com.tracevia.webapp.model.global.Equipments;

public class HIT extends Equipments {
	
	private double altura_max;
	private double altura_min;
	
	
	public HIT(int equip_id, String table_id, String equip_type, String equip_ip, String creation_date,
			String creation_username, String update_date, String update_username, String nome, String estrada,
			String cidade, String km, int linearPosX, int linearPosY, int mapPosX, int mapPosY, int mapWidth,
			int height, int linearWidth, int dlgPosX, int dlgPosY, String direction, int status, int lastStatus,
			boolean notificacao, int port, boolean visible, double altura_max, double altura_min) {
		
		super(equip_id, table_id, equip_type, equip_ip, creation_date, creation_username, update_date, update_username,
				nome, estrada, cidade, km, linearPosX, linearPosY, mapPosX, mapPosY, mapWidth, height, linearWidth,
				dlgPosX, dlgPosY, direction, status, lastStatus, notificacao, port, visible);
		
		this.altura_max = altura_max;
		this.altura_min = altura_min;
	}
	
	
	public HIT() {}


	public double getAltura_max() {
		return altura_max;
	}


	public void setAltura_max(double altura_max) {
		this.altura_max = altura_max;
	}


	public double getAltura_min() {
		return altura_min;
	}


	public void setAltura_min(double altura_min) {
		this.altura_min = altura_min;
	}
	
	

}
