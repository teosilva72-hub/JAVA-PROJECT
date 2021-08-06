package br.com.tracevia.webapp.model.meteo.mto;

import br.com.tracevia.webapp.model.global.Equipments;

public class MTO extends Equipments{

	private String data_hora;
	private String data;
	private String intervalos;
	private int monthOrDay;	
	private int press_atmosferica;
	private int umi_relativa;
	private int absol_precipitacao;
	private int velocidadeMed_vento;
	private int direcao_vento;
	private double temp_ar;
	private double temp_estrada;	
	private int visibilidade;
	private int bateria_sts;
	private int volts_line;
		
		public MTO(int equip_id, String table_id, String equip_type, String equip_ip, int port, String creation_date,
			String creation_username, String update_date, String update_username, String nome, String estrada,
			String cidade, String km, int linearPosX, int linearPosY, int mapPosX, int mapPosY, int mapWidth,
			int height, int linearWidth, int dlgPosX, int dlgPosY, int model, String master_sip, int status, int lastStatus, boolean notificacao,
			boolean visible, String data_hora, String data, String intervalos, int monthOrDay, int press_atmosferica,
			int umi_relativa, int absol_precipitacao, int velocidadeMed_vento, int direcao_vento, double temp_ar,
			double temp_estrada, int visibilidade, int bateria_sts, int volts_line) {
		
			super(equip_id, table_id, equip_type, equip_ip, port, creation_date, creation_username, update_date, update_username,
				nome, estrada, cidade, km, linearPosX, linearPosY, mapPosX, mapPosY, mapWidth, height, linearWidth,
				dlgPosX, dlgPosY, model, master_sip, status, lastStatus, notificacao, visible);
			
		this.data_hora = data_hora;
		this.data = data;
		this.intervalos = intervalos;
		this.monthOrDay = monthOrDay;
		this.press_atmosferica = press_atmosferica;
		this.umi_relativa = umi_relativa;
		this.absol_precipitacao = absol_precipitacao;
		this.velocidadeMed_vento = velocidadeMed_vento;
		this.direcao_vento = direcao_vento;
		this.temp_ar = temp_ar;
		this.temp_estrada = temp_estrada;
		this.visibilidade = visibilidade;
		this.bateria_sts = bateria_sts;
		this.volts_line = volts_line;
		
	}


		public MTO(){}


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


		public int getPress_atmosferica() {
			return press_atmosferica;
		}


		public void setPress_atmosferica(int press_atmosferica) {
			this.press_atmosferica = press_atmosferica;
		}


		public int getUmi_relativa() {
			return umi_relativa;
		}


		public void setUmi_relativa(int umi_relativa) {
			this.umi_relativa = umi_relativa;
		}


		public int getAbsol_precipitacao() {
			return absol_precipitacao;
		}


		public void setAbsol_precipitacao(int absol_precipitacao) {
			this.absol_precipitacao = absol_precipitacao;
		}


		public int getVelocidadeMed_vento() {
			return velocidadeMed_vento;
		}


		public void setVelocidadeMed_vento(int velocidadeMed_vento) {
			this.velocidadeMed_vento = velocidadeMed_vento;
		}


		public int getDirecao_vento() {
			return direcao_vento;
		}


		public void setDirecao_vento(int direcao_vento) {
			this.direcao_vento = direcao_vento;
		}


		public double getTemp_ar() {
			return temp_ar;
		}


		public void setTemp_ar(double temp_ar) {
			this.temp_ar = temp_ar;
		}


		public double getTemp_estrada() {
			return temp_estrada;
		}


		public void setTemp_estrada(double temp_estrada) {
			this.temp_estrada = temp_estrada;
		}


		public int getVisibilidade() {
			return visibilidade;
		}


		public void setVisibilidade(int visibilidade) {
			this.visibilidade = visibilidade;
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
