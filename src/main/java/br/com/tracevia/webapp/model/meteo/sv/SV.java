package br.com.tracevia.webapp.model.meteo.sv;

import br.com.tracevia.webapp.model.global.Equipments;

public class SV extends Equipments {
	
	private String data_hora;
	private String data;
	private String intervalos;
	private int monthOrDay;	
	private double temp_ambiente;
	private int visibilidade;
	private int status;
	private int bateria_sts;
	private int volts_line;
				
	public SV(int equip_id, String table_id, String equip_type, String creation_date, String creation_username,
			String update_date, String update_username, String nome, String estrada, String cidade, String km,
			String posicao, int linearPosX, int linearPosY, int mapPosX, int mapPosY, int mapWidth, int height,
			int linearWidth, int dlgPosX, int dlgPosY, int status, boolean notificacao, boolean visible,
			String data_hora, String data, String intervalos, int monthOrDay, double temp_ambiente, int visibilidade,
			int status2, int bateria_sts, int volts_line) {
		
		super(equip_id, table_id, equip_type, creation_date, creation_username, update_date, update_username, nome,
				estrada, cidade, km, posicao, linearPosX, linearPosY, mapPosX, mapPosY, mapWidth, height, linearWidth,
				dlgPosX, dlgPosY, status, notificacao, visible);
		
		this.data_hora = data_hora;
		this.data = data;
		this.intervalos = intervalos;
		this.monthOrDay = monthOrDay;
		this.temp_ambiente = temp_ambiente;
		this.visibilidade = visibilidade;	
		this.bateria_sts = bateria_sts;
		this.volts_line = volts_line;
	}


	public SV() {}


	public String getData_hora() {
		return data_hora;
	}


	public void setData_hora(String data_hora) {
		this.data_hora = data_hora;
	}


	public String getData() {
		return data;
	}


	public void setData(String data) {
		this.data = data;
	}


	public String getIntervalos() {
		return intervalos;
	}


	public void setIntervalos(String intervalos) {
		this.intervalos = intervalos;
	}


	public int getMonthOrDay() {
		return monthOrDay;
	}


	public void setMonthOrDay(int monthOrDay) {
		this.monthOrDay = monthOrDay;
	}


	public double getTemp_ambiente() {
		return temp_ambiente;
	}


	public void setTemp_ambiente(double temp_ambiente) {
		this.temp_ambiente = temp_ambiente;
	}


	public int getVisibilidade() {
		return visibilidade;
	}


	public void setVisibilidade(int visibilidade) {
		this.visibilidade = visibilidade;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public int getBateria_sts() {
		return bateria_sts;
	}


	public void setBateria_sts(int bateria_sts) {
		this.bateria_sts = bateria_sts;
	}


	public int getVolts_line() {
		return volts_line;
	}


	public void setVolts_line(int volts_line) {
		this.volts_line = volts_line;
	}
	
	
}
