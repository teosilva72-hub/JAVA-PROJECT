package br.com.tracevia.webapp.model.hit;

import br.com.tracevia.webapp.model.global.Equipments;

public class HIT extends Equipments {
	
	private double altura_max;
	private double altura_min;
	private String msg1;
	private String msg2;
	private String msg3;
	private String img;
		
	public HIT(int equip_id, String table_id, String equip_type, String equip_ip, String creation_date,
			String creation_username, String update_date, String update_username, String nome, String estrada,
			String cidade, String km, int linearPosX, int linearPosY, int mapPosX, int mapPosY, int mapWidth,
			int height, int linearWidth, int dlgPosX, int dlgPosY, String direction, String directionTo, int status,
			int lastStatus, boolean notificacao, int port, boolean visible, double altura_max, double altura_min,
			String msg1, String msg2, String msg3, String img) {
		
		super(equip_id, table_id, equip_type, equip_ip, creation_date, creation_username, update_date, update_username,
				nome, estrada, cidade, km, linearPosX, linearPosY, mapPosX, mapPosY, mapWidth, height, linearWidth,
				dlgPosX, dlgPosY, direction, directionTo, status, lastStatus, notificacao, port, visible);
		
		this.altura_max = altura_max;
		this.altura_min = altura_min;
		this.msg1 = msg1;
		this.msg2 = msg2;
		this.msg3 = msg3;
		this.img = img;
	}


	public HIT() {super();}


	public double getAltura_max() {
		return altura_max;
	}


	public String getMsg1() {
		return msg1;
	}


	public void setMsg1(String msg1) {
		this.msg1 = msg1;
	}


	public String getMsg2() {
		return msg2;
	}


	public void setMsg2(String msg2) {
		this.msg2 = msg2;
	}


	public String getMsg3() {
		return msg3;
	}


	public void setMsg3(String msg3) {
		this.msg3 = msg3;
	}


	public String getImg() {
		return img;
	}


	public void setImg(String img) {
		this.img = img;
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
