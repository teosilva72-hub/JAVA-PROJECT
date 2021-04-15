package br.com.tracevia.webapp.model.mto;

import br.com.tracevia.webapp.model.global.Equipments;

public class MTO extends Equipments{

	private String data_hora;
	private String data;
	private String intervalos;
	private int monthOrDay;	
	private int press_atmosferica;
	private int umi_relativa;
	private int taxa_precipitacao;
	private int taxa_precipitacao1h;
	private int velocidadeMed_vento;
	private int direcao_vento;
	private int temp_ar;
	private int visibilidade;
	private int status;
	private int bateria_sts;
	private int volts_line;
		
	
	public MTO(int equip_id, String table_id, String equip_type, String creation_date, String creation_username,
			String update_date, String update_username, String nome, String estrada, String cidade, String km,
			String posicao, int linearPosX, int linearPosY, int mapPosX, int mapPosY, int mapWidth, int height,
			int linearWidth, int dlgPosX, int dlgPosY, int status, boolean notificacao, boolean visible,
			String data_hora, String data, String intervalos, int monthOrDay, int press_atmosferica, int umi_relativa,
			int taxa_precipitacao, int taxa_precipitacao1h, int velocidadeMed_vento, int direcao_vento, int temp_ar,
			int visibilidade, int status2, int bateria_sts, int volts_line) {
		
		super(equip_id, table_id, equip_type, creation_date, creation_username, update_date, update_username, nome,
				estrada, cidade, km, posicao, linearPosX, linearPosY, mapPosX, mapPosY, mapWidth, height, linearWidth,
				dlgPosX, dlgPosY, status, notificacao, visible);
		
		this.data_hora = data_hora;
		this.data = data;
		this.intervalos = intervalos;
		this.monthOrDay = monthOrDay;
		this.press_atmosferica = press_atmosferica;
		this.umi_relativa = umi_relativa;
		this.taxa_precipitacao = taxa_precipitacao;
		this.taxa_precipitacao1h = taxa_precipitacao1h;
		this.velocidadeMed_vento = velocidadeMed_vento;
		this.direcao_vento = direcao_vento;
		this.temp_ar = temp_ar;
		this.visibilidade = visibilidade;
		status = status2;
		this.bateria_sts = bateria_sts;
		this.volts_line = volts_line;
	}

	public MTO() {}

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

	public int getTaxa_precipitacao() {
		return taxa_precipitacao;
	}

	public void setTaxa_precipitacao(int taxa_precipitacao) {
		this.taxa_precipitacao = taxa_precipitacao;
	}

	public int getTaxa_precipitacao1h() {
		return taxa_precipitacao1h;
	}

	public void setTaxa_precipitacao1h(int taxa_precipitacao1h) {
		this.taxa_precipitacao1h = taxa_precipitacao1h;
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

	public int getTemp_ar() {
		return temp_ar;
	}

	public void setTemp_ar(int temp_ar) {
		this.temp_ar = temp_ar;
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
