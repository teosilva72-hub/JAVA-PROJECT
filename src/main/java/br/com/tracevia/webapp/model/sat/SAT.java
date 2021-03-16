package br.com.tracevia.webapp.model.sat;

import br.com.tracevia.webapp.model.global.Equipments;

public class SAT extends Equipments {
		

	private int numFaixas;		
	private int quantidadeS1;
	private int velocidadeS1;
	private int quantidadeS2;	
	private int velocidadeS2;
	private int statusInterval;
	private String sentido1;
	private String sentido2;
	private String sentido3;
	private String sentido4;
	private String sentido5;
	private String sentido6;
	private String sentido7;
	private String sentido8;
	private String faixa1;
	private String faixa2;
	private String faixa3;
	private String faixa4;
	private String faixa5;
	private String faixa6;
	private String faixa7;
	private String faixa8;
	
	public SAT(int equip_id, String table_id, String creation_date, String creation_username, int number_lanes, String nome,
			String estrada, String cidade, String km, String dir_lane1, String dir_lane2, String dir_lane3, String dir_lane4, String dir_lane5, String dir_lane6, String dir_lane7, String dir_lane8,
			String posicao, int linearPosX, int linearPosY, int linearWidth,
			int mapPosX, int mapPosY, int mapWidth, int height, int dlgPosX, int dlgPosY, int status,
			boolean notificacao, boolean visible, int numFaixas, int quantidadeS1, int velocidadeS1, int quantidadeS2,
			int velocidadeS2, String sentido1, String sentido2, String sentido3, String sentido4, String sentido5, String sentido6, String sentido7, String sentido8, int statusInterval, String faixa1, String faixa2, String faixa3, String faixa4, String faixa5, String faixa6,
			String faixa7, String faixa8) {
		super(equip_id, table_id, creation_date, creation_username, number_lanes, nome, estrada, cidade, km, dir_lane1, dir_lane2, dir_lane3, dir_lane4, dir_lane5, dir_lane6, dir_lane7, dir_lane8,
				posicao, linearPosX, linearPosY, linearWidth, mapPosX, mapPosY, mapWidth, height, dlgPosX, dlgPosY, status, notificacao,
				visible);
		
		this.numFaixas = numFaixas;
		this.quantidadeS1 = quantidadeS1;
		this.velocidadeS1 = velocidadeS1;
		this.quantidadeS2 = quantidadeS2;
		this.velocidadeS2 = velocidadeS2;
		this.sentido1 = sentido1;
		this.sentido2 = sentido2;
		this.sentido1 = sentido3;
		this.sentido2 = sentido4;
		this.sentido1 = sentido5;
		this.sentido2 = sentido6;
		this.sentido1 = sentido7;
		this.sentido2 = sentido8;
		this.statusInterval = statusInterval; 
		this.faixa1 = faixa1;
		this.faixa2 = faixa2;
		this.faixa3 = faixa3;
		this.faixa4 = faixa4;
		this.faixa5 = faixa5;
		this.faixa6 = faixa6;
		this.faixa7 = faixa7;
		this.faixa8 = faixa8;
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
	
	public String getSentido3() {
		return sentido3;
	}

	public void setSentido3(String sentido3) {
		this.sentido3 = sentido3;
	}

	public String getSentido4() {
		return sentido4;
	}

	public void setSentido4(String sentido4) {
		this.sentido4 = sentido4;
	}
	
	public void setSentido51(String sentido5) {
		this.sentido5 = sentido5;
	}

	public String getSentido5() {
		return sentido5;
	}

	public void setSentido5(String sentido5) {
		this.sentido5 = sentido5;
	}
	
	public String getSentido6() {
		return sentido6;
	}

	public void setSentido6(String sentido6) {
		this.sentido6 = sentido6;
	}

	public String getSentido7() {
		return sentido7;
	}

	public void setSentido7(String sentido7) {
		this.sentido7 = sentido7;
	}
	
	public String getSentido8() {
		return sentido8;
	}

	public void setSentido8(String sentido8) {
		this.sentido8 = sentido8;
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
		
}
