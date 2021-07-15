package br.com.tracevia.webapp.model.sat;

import br.com.tracevia.webapp.model.global.Equipments;

public class SAT extends Equipments {
		
	private int numFaixas;		
	private int quantidadeS1;
	private int velocidadeS1;
	private int quantidadeS2;	
	private int velocidadeS2;
	private int statusInterval;
	private String faixa1;
	private String faixa2;
	private String faixa3;
	private String faixa4;
	private String faixa5;
	private String faixa6;
	private String faixa7;
	private String faixa8;
	private String sentido1;
	private String sentido2;
	private String posicao_nivel_servico;
		
	public SAT(int equip_id, String table_id, String equip_type, String creation_date, String creation_username,
			String update_date, String update_username, String nome, String estrada, String cidade, String km,
			int linearPosX, int linearPosY, int mapPosX, int mapPosY, int mapWidth, int height, int linearWidth,
			int dlgPosX, int dlgPosY, int status, int last_status, boolean notificacao, boolean visible, int numFaixas,
			int quantidadeS1, int velocidadeS1, int quantidadeS2, int velocidadeS2, int statusInterval, String faixa1,
			String faixa2, String faixa3, String faixa4, String faixa5, String faixa6, String faixa7, String faixa8,
			String sentido1, String sentido2, String posicao_nivel_servico) {
		
		super(equip_id, table_id, equip_type, creation_date, creation_username, update_date, update_username, nome,
				estrada, cidade, km, linearPosX, linearPosY, mapPosX, mapPosY, mapWidth, height, linearWidth, dlgPosX,
				dlgPosY, status, notificacao, visible);
		
		this.numFaixas = numFaixas;
		this.quantidadeS1 = quantidadeS1;
		this.velocidadeS1 = velocidadeS1;
		this.quantidadeS2 = quantidadeS2;
		this.velocidadeS2 = velocidadeS2;
		this.statusInterval = statusInterval;
		this.faixa1 = faixa1;
		this.faixa2 = faixa2;
		this.faixa3 = faixa3;
		this.faixa4 = faixa4;
		this.faixa5 = faixa5;
		this.faixa6 = faixa6;
		this.faixa7 = faixa7;
		this.faixa8 = faixa8;
		this.sentido1 = sentido1;
		this.sentido2 = sentido2;
		this.posicao_nivel_servico = posicao_nivel_servico;
	}

	public SAT() {
		super();		
	}
		
	public int getNumFaixas() {
		return numFaixas;
	}
	public void setNumFaixas(int numFaixas) {
		this.numFaixas = numFaixas;
	}	
	
	public int getQuantidadeS1() {
		return quantidadeS1;
	}

	public void setQuantidadeS1(int quantidadeS1) {
		this.quantidadeS1 = quantidadeS1;
	}

	public int getVelocidadeS1() {
		return velocidadeS1;
	}

	public void setVelocidadeS1(int velocidadeS1) {
		this.velocidadeS1 = velocidadeS1;
	}

	public int getQuantidadeS2() {
		return quantidadeS2;
	}

	public void setQuantidadeS2(int quantidadeS2) {
		this.quantidadeS2 = quantidadeS2;
	}

	public int getVelocidadeS2() {
		return velocidadeS2;
	}

	public void setVelocidadeS2(int velocidadeS2) {
		this.velocidadeS2 = velocidadeS2;
	}
			
	public int getStatusInterval() {
		return statusInterval;
	}

	public void setStatusInterval(int statusInterval) {
		this.statusInterval = statusInterval;
	}

	public String getFaixa1() {
		return faixa1;
	}
	public void setFaixa1(String faixa1) {
		this.faixa1 = faixa1;
	}
	public String getFaixa2() {
		return faixa2;
	}
	public void setFaixa2(String faixa2) {
		this.faixa2 = faixa2;
	}
	public String getFaixa3() {
		return faixa3;
	}
	public void setFaixa3(String faixa3) {
		this.faixa3 = faixa3;
	}
	public String getFaixa4() {
		return faixa4;
	}
	public void setFaixa4(String faixa4) {
		this.faixa4 = faixa4;
	}
	public String getFaixa5() {
		return faixa5;
	}
	public void setFaixa5(String faixa5) {
		this.faixa5 = faixa5;
	}
	public String getFaixa6() {
		return faixa6;
	}
	public void setFaixa6(String faixa6) {
		this.faixa6 = faixa6;
	}
	public String getFaixa7() {
		return faixa7;
	}
	public void setFaixa7(String faixa7) {
		this.faixa7 = faixa7;
	}
	public String getFaixa8() {
		return faixa8;
	}
	public void setFaixa8(String faixa8) {
		this.faixa8 = faixa8;
	}

	public String getSentido1() {
		return sentido1;
	}

	public void setSentido1(String sentido1) {
		this.sentido1 = sentido1;
	}

	public String getSentido2() {
		return sentido2;
	}

	public void setSentido2(String sentido2) {
		this.sentido2 = sentido2;
	}

	public String getPosicao_nivel_servico() {
		return posicao_nivel_servico;
	}

	public void setPosicao_nivel_servico(String posicao_nivel_servico) {
		this.posicao_nivel_servico = posicao_nivel_servico;
	}

			
}
