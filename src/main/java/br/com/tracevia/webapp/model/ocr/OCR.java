package br.com.tracevia.webapp.model.ocr;

import br.com.tracevia.webapp.model.global.Equipments;

public class OCR extends Equipments {
	
	private String id;
	private String dataHour;
	private String cam;
	private String placa;
	private String plateImage;
	private String vehicleImage;

	public OCR(int equip_id, String table_id, String equip_type, String equip_ip, String creation_date,
			String creation_username, String update_date, String update_username, String nome, String estrada,
			String cidade, String km, int linearPosX, int linearPosY, int mapPosX, int mapPosY, int mapWidth,
			int height, int linearWidth, int dlgPosX, int dlgPosY, String direction, String directionTo, int status,
			int lastStatus, boolean notificacao, int port, boolean visible, String id, String dataHour, String cam,
			String placa, String plateImage, String vehicleImage) {
		
		super(equip_id, table_id, equip_type, equip_ip, creation_date, creation_username, update_date, update_username,
				nome, estrada, cidade, km, linearPosX, linearPosY, mapPosX, mapPosY, mapWidth, height, linearWidth,
				dlgPosX, dlgPosY, direction, directionTo, status, lastStatus, notificacao, port, visible);
		
		this.id = id;
		this.dataHour = dataHour;
		this.cam = cam;
		this.placa = placa;
		this.plateImage = plateImage;
		this.vehicleImage = vehicleImage;
	}

	public OCR() {super();}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getDataHour() {
		return dataHour;
	}
	public void setDataHour(String dataHour) {
		this.dataHour = dataHour;
	}
	public String getCam() {
		return cam;
	}
	public void setCam(String cam) {
		this.cam = cam;
	}
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getPlateImage() {
		return plateImage;
	}

	public void setPlateImage(String plateImage) {
		this.plateImage = plateImage;
	}

	public String getVehicleImage() {
		return vehicleImage;
	}

	public void setVehicleImage(String vehicleImage) {
		this.vehicleImage = vehicleImage;
	}
		
}