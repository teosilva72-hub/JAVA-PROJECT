package br.com.tracevia.webapp.model.speed;

import br.com.tracevia.webapp.model.global.Equipments;

public class Speed extends Equipments {
	
	private String equip_ip_speed;
	private String equip_ip_radar;
	
	public Speed(int equip_id, String table_id, String equip_type, String equip_ip, String creation_date,
			String creation_username, String update_date, String update_username, String nome, String estrada,
			String cidade, String km, int linearPosX, int linearPosY, int mapPosX, int mapPosY, int mapWidth,
			int height, int linearWidth, int dlgPosX, int dlgPosY, int status, int lastStatus, boolean notificacao,
			boolean visible, String equip_ip_speed, String equip_ip_radar) {
		
		super(equip_id, table_id, equip_type, equip_ip, creation_date, creation_username, update_date, update_username,
				nome, estrada, cidade, km, linearPosX, linearPosY, mapPosX, mapPosY, mapWidth, height, linearWidth,
				dlgPosX, dlgPosY, status, lastStatus, notificacao, visible);
		
		this.equip_ip_speed = equip_ip_speed;
		this.equip_ip_radar = equip_ip_radar;
	}

	public Speed() {}

	public String getEquip_ip_speed() {
		return equip_ip_speed;
	}

	public void setEquip_ip_speed(String equip_ip_speed) {
		this.equip_ip_speed = equip_ip_speed;
	}

	public String getEquip_ip_radar() {
		return equip_ip_radar;
	}

	public void setEquip_ip_radar(String equip_ip_radar) {
		this.equip_ip_radar = equip_ip_radar;
	}
			
	
}
