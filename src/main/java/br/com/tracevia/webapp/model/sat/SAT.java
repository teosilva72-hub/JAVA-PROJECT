package br.com.tracevia.webapp.model.sat;

import br.com.tracevia.webapp.model.global.Equipments;

public class SAT extends Equipments {
		
	private int numFaixas;	
	private int quantidadeS1;
	private int velocidadeS1;
	private int quantidadeS2;	
	private int velocidadeS2;
	private int statusInterval;
	private int autos7days1hS1;	
	private int autosCurrent1hS1;
	private int autosProjection1hS1;
	private int autosVolumeS1;
	private int autosVelMedS1;
	private int autos7days1hS2;	
	private int autosCurrent1hS2;
	private int autosProjection1hS2;
	private int autosVolumeS2;
	private int autosVelMedS2;
	private int com7days1hS1;	
	private int comCurrent1hS1;
	private int comProjection1hS1;
	private int comVolumeS1;
	private int comVelMedS1;
	private int com7days1hS2;	
	private int comCurrent1hS2;
	private int comProjection1hS2;
	private int comVolumeS2;
	private int comVelMedS2;
	private int motodays1hS1;	
	private int motoCurrent1hS1;
	private int motoProjection1hS1;
	private int motoVolumeS1;
	private int motoVelMedS1;
	private int moto7days1hS2;	
	private int motoCurrent1hS2;
	private int motoProjection1hS2;
	private int motoVolumeS2;
	private int motoVelMedS2;
	private int total7days1hS1;	
	private int totalCurrent1hS1;
	private int totalProjection1hS1;
	private int totalVolumeS1;
	private int totalVelMedS1;
	private int total7days1hS2;	
	private int totalCurrent1hS2;
	private int totalProjection1hS2;
	private int totalVolumeS2;
	private int totalVelMedS2;	
	private double occupationRateS1;
	private double occupationRateS2;
	private String faixa1;
	private String faixa2;
	private String faixa3;
	private String faixa4;
	private String faixa5;
	private String faixa6;
	private String faixa7;
	private String faixa8;
	private String qtdeFaixas;
	private String sentidos;
	private String sentido1;
	private String sentido2;
	private String sentido1Abbr;
	private String sentido2Abbr;
	private String posicao_nivel_servico;
	private String lastRegister;
	private String lastPackage;	
			
	

	public SAT(int equip_id, String table_id, String equip_type, String equip_ip, String creation_date,
			String creation_username, String update_date, String update_username, String nome, String estrada,
			String cidade, String km, int linearPosX, int linearPosY, int mapPosX, int mapPosY, int mapWidth,
			int height, int linearWidth, int dlgPosX, int dlgPosY, String direction, int status, int lastStatus,
			boolean notificacao, int port, boolean visible, int numFaixas, int quantidadeS1, int velocidadeS1,
			int quantidadeS2, int velocidadeS2, int statusInterval, int autos7days1hS1, int autosCurrent1hS1,
			int autosProjection1hS1, int autosVolumeS1, int autosVelMedS1, int autos7days1hS2, int autosCurrent1hS2,
			int autosProjection1hS2, int autosVolumeS2, int autosVelMedS2, int com7days1hS1, int comCurrent1hS1,
			int comProjection1hS1, int comVolumeS1, int comVelMedS1, int com7days1hS2, int comCurrent1hS2,
			int comProjection1hS2, int comVolumeS2, int comVelMedS2, int motodays1hS1, int motoCurrent1hS1,
			int motoProjection1hS1, int motoVolumeS1, int motoVelMedS1, int moto7days1hS2, int motoCurrent1hS2,
			int motoProjection1hS2, int motoVolumeS2, int motoVelMedS2, int total7days1hS1, int totalCurrent1hS1,
			int totalProjection1hS1, int totalVolumeS1, int totalVelMedS1, int total7days1hS2, int totalCurrent1hS2,
			int totalProjection1hS2, int totalVolumeS2, int totalVelMedS2, double occupationRateS1,
			double occupationRateS2, String faixa1, String faixa2, String faixa3, String faixa4, String faixa5,
			String faixa6, String faixa7, String faixa8, String qtdeFaixas, String sentidos, String sentido1,
			String sentido2, String sentido1Abbr, String sentido2Abbr, String posicao_nivel_servico,
			String lastRegister, String lastPackage) {
		
		super(equip_id, table_id, equip_type, equip_ip, creation_date, creation_username, update_date, update_username,
				nome, estrada, cidade, km, linearPosX, linearPosY, mapPosX, mapPosY, mapWidth, height, linearWidth,
				dlgPosX, dlgPosY, direction, status, lastStatus, notificacao, port, visible);
		
		this.numFaixas = numFaixas;
		this.quantidadeS1 = quantidadeS1;
		this.velocidadeS1 = velocidadeS1;
		this.quantidadeS2 = quantidadeS2;
		this.velocidadeS2 = velocidadeS2;
		this.statusInterval = statusInterval;
		this.autos7days1hS1 = autos7days1hS1;
		this.autosCurrent1hS1 = autosCurrent1hS1;
		this.autosProjection1hS1 = autosProjection1hS1;
		this.autosVolumeS1 = autosVolumeS1;
		this.autosVelMedS1 = autosVelMedS1;
		this.autos7days1hS2 = autos7days1hS2;
		this.autosCurrent1hS2 = autosCurrent1hS2;
		this.autosProjection1hS2 = autosProjection1hS2;
		this.autosVolumeS2 = autosVolumeS2;
		this.autosVelMedS2 = autosVelMedS2;
		this.com7days1hS1 = com7days1hS1;
		this.comCurrent1hS1 = comCurrent1hS1;
		this.comProjection1hS1 = comProjection1hS1;
		this.comVolumeS1 = comVolumeS1;
		this.comVelMedS1 = comVelMedS1;
		this.com7days1hS2 = com7days1hS2;
		this.comCurrent1hS2 = comCurrent1hS2;
		this.comProjection1hS2 = comProjection1hS2;
		this.comVolumeS2 = comVolumeS2;
		this.comVelMedS2 = comVelMedS2;
		this.motodays1hS1 = motodays1hS1;
		this.motoCurrent1hS1 = motoCurrent1hS1;
		this.motoProjection1hS1 = motoProjection1hS1;
		this.motoVolumeS1 = motoVolumeS1;
		this.motoVelMedS1 = motoVelMedS1;
		this.moto7days1hS2 = moto7days1hS2;
		this.motoCurrent1hS2 = motoCurrent1hS2;
		this.motoProjection1hS2 = motoProjection1hS2;
		this.motoVolumeS2 = motoVolumeS2;
		this.motoVelMedS2 = motoVelMedS2;
		this.total7days1hS1 = total7days1hS1;
		this.totalCurrent1hS1 = totalCurrent1hS1;
		this.totalProjection1hS1 = totalProjection1hS1;
		this.totalVolumeS1 = totalVolumeS1;
		this.totalVelMedS1 = totalVelMedS1;
		this.total7days1hS2 = total7days1hS2;
		this.totalCurrent1hS2 = totalCurrent1hS2;
		this.totalProjection1hS2 = totalProjection1hS2;
		this.totalVolumeS2 = totalVolumeS2;
		this.totalVelMedS2 = totalVelMedS2;
		this.occupationRateS1 = occupationRateS1;
		this.occupationRateS2 = occupationRateS2;
		this.faixa1 = faixa1;
		this.faixa2 = faixa2;
		this.faixa3 = faixa3;
		this.faixa4 = faixa4;
		this.faixa5 = faixa5;
		this.faixa6 = faixa6;
		this.faixa7 = faixa7;
		this.faixa8 = faixa8;
		this.qtdeFaixas = qtdeFaixas;
		this.sentidos = sentidos;
		this.sentido1 = sentido1;
		this.sentido2 = sentido2;
		this.sentido1Abbr = sentido1Abbr;
		this.sentido2Abbr = sentido2Abbr;
		this.posicao_nivel_servico = posicao_nivel_servico;
		this.lastRegister = lastRegister;
		this.lastPackage = lastPackage;
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

	public int getAutos7days1hS1() {
		return autos7days1hS1;
	}

	public void setAutos7days1hS1(int autos7days1hS1) {
		this.autos7days1hS1 = autos7days1hS1;
	}

	public int getAutosCurrent1hS1() {
		return autosCurrent1hS1;
	}

	public void setAutosCurrent1hS1(int autosCurrent1hS1) {
		this.autosCurrent1hS1 = autosCurrent1hS1;
	}

	public int getAutosProjection1hS1() {
		return autosProjection1hS1;
	}

	public void setAutosProjection1hS1(int autosProjection1hS1) {
		this.autosProjection1hS1 = autosProjection1hS1;
	}

	public int getAutosVolumeS1() {
		return autosVolumeS1;
	}

	public void setAutosVolumeS1(int autosVolumeS1) {
		this.autosVolumeS1 = autosVolumeS1;
	}

	public int getAutosVelMedS1() {
		return autosVelMedS1;
	}

	public void setAutosVelMedS1(int autosVelMedS1) {
		this.autosVelMedS1 = autosVelMedS1;
	}

	public int getAutos7days1hS2() {
		return autos7days1hS2;
	}

	public void setAutos7days1hS2(int autos7days1hS2) {
		this.autos7days1hS2 = autos7days1hS2;
	}

	public int getAutosCurrent1hS2() {
		return autosCurrent1hS2;
	}

	public void setAutosCurrent1hS2(int autosCurrent1hS2) {
		this.autosCurrent1hS2 = autosCurrent1hS2;
	}

	public int getAutosProjection1hS2() {
		return autosProjection1hS2;
	}

	public void setAutosProjection1hS2(int autosProjection1hS2) {
		this.autosProjection1hS2 = autosProjection1hS2;
	}

	public int getAutosVolumeS2() {
		return autosVolumeS2;
	}

	public void setAutosVolumeS2(int autosVolumeS2) {
		this.autosVolumeS2 = autosVolumeS2;
	}

	public int getAutosVelMedS2() {
		return autosVelMedS2;
	}

	public void setAutosVelMedS2(int autosVelMedS2) {
		this.autosVelMedS2 = autosVelMedS2;
	}

	public int getCom7days1hS1() {
		return com7days1hS1;
	}

	public void setCom7days1hS1(int com7days1hS1) {
		this.com7days1hS1 = com7days1hS1;
	}

	public int getComCurrent1hS1() {
		return comCurrent1hS1;
	}

	public void setComCurrent1hS1(int comCurrent1hS1) {
		this.comCurrent1hS1 = comCurrent1hS1;
	}

	public int getComProjection1hS1() {
		return comProjection1hS1;
	}

	public void setComProjection1hS1(int comProjection1hS1) {
		this.comProjection1hS1 = comProjection1hS1;
	}

	public int getComVolumeS1() {
		return comVolumeS1;
	}

	public void setComVolumeS1(int comVolumeS1) {
		this.comVolumeS1 = comVolumeS1;
	}

	public int getComVelMedS1() {
		return comVelMedS1;
	}

	public void setComVelMedS1(int comVelMedS1) {
		this.comVelMedS1 = comVelMedS1;
	}

	public int getCom7days1hS2() {
		return com7days1hS2;
	}

	public void setCom7days1hS2(int com7days1hS2) {
		this.com7days1hS2 = com7days1hS2;
	}

	public int getComCurrent1hS2() {
		return comCurrent1hS2;
	}

	public void setComCurrent1hS2(int comCurrent1hS2) {
		this.comCurrent1hS2 = comCurrent1hS2;
	}

	public int getComProjection1hS2() {
		return comProjection1hS2;
	}

	public void setComProjection1hS2(int comProjection1hS2) {
		this.comProjection1hS2 = comProjection1hS2;
	}

	public int getComVolumeS2() {
		return comVolumeS2;
	}

	public void setComVolumeS2(int comVolumeS2) {
		this.comVolumeS2 = comVolumeS2;
	}

	public int getComVelMedS2() {
		return comVelMedS2;
	}

	public void setComVelMedS2(int comVelMedS2) {
		this.comVelMedS2 = comVelMedS2;
	}

	public int getMotodays1hS1() {
		return motodays1hS1;
	}

	public void setMotodays1hS1(int motodays1hS1) {
		this.motodays1hS1 = motodays1hS1;
	}

	public int getMotoCurrent1hS1() {
		return motoCurrent1hS1;
	}

	public void setMotoCurrent1hS1(int motoCurrent1hS1) {
		this.motoCurrent1hS1 = motoCurrent1hS1;
	}

	public int getMotoProjection1hS1() {
		return motoProjection1hS1;
	}

	public void setMotoProjection1hS1(int motoProjection1hS1) {
		this.motoProjection1hS1 = motoProjection1hS1;
	}

	public int getMotoVolumeS1() {
		return motoVolumeS1;
	}

	public void setMotoVolumeS1(int motoVolumeS1) {
		this.motoVolumeS1 = motoVolumeS1;
	}

	public int getMotoVelMedS1() {
		return motoVelMedS1;
	}

	public void setMotoVelMedS1(int motoVelMedS1) {
		this.motoVelMedS1 = motoVelMedS1;
	}

	public int getMoto7days1hS2() {
		return moto7days1hS2;
	}

	public void setMoto7days1hS2(int moto7days1hS2) {
		this.moto7days1hS2 = moto7days1hS2;
	}

	public int getMotoCurrent1hS2() {
		return motoCurrent1hS2;
	}

	public void setMotoCurrent1hS2(int motoCurrent1hS2) {
		this.motoCurrent1hS2 = motoCurrent1hS2;
	}

	public int getMotoProjection1hS2() {
		return motoProjection1hS2;
	}

	public void setMotoProjection1hS2(int motoProjection1hS2) {
		this.motoProjection1hS2 = motoProjection1hS2;
	}

	public int getMotoVolumeS2() {
		return motoVolumeS2;
	}

	public void setMotoVolumeS2(int motoVolumeS2) {
		this.motoVolumeS2 = motoVolumeS2;
	}

	public int getMotoVelMedS2() {
		return motoVelMedS2;
	}

	public void setMotoVelMedS2(int motoVelMedS2) {
		this.motoVelMedS2 = motoVelMedS2;
	}

	public int getTotal7days1hS1() {
		return total7days1hS1;
	}

	public void setTotal7days1hS1(int total7days1hS1) {
		this.total7days1hS1 = total7days1hS1;
	}

	public int getTotalCurrent1hS1() {
		return totalCurrent1hS1;
	}

	public void setTotalCurrent1hS1(int totalCurrent1hS1) {
		this.totalCurrent1hS1 = totalCurrent1hS1;
	}

	public int getTotalProjection1hS1() {
		return totalProjection1hS1;
	}

	public void setTotalProjection1hS1(int totalProjection1hS1) {
		this.totalProjection1hS1 = totalProjection1hS1;
	}

	public int getTotalVolumeS1() {
		return totalVolumeS1;
	}

	public void setTotalVolumeS1(int totalVolumeS1) {
		this.totalVolumeS1 = totalVolumeS1;
	}

	public int getTotalVelMedS1() {
		return totalVelMedS1;
	}

	public void setTotalVelMedS1(int totalVelMedS1) {
		this.totalVelMedS1 = totalVelMedS1;
	}

	public int getTotal7days1hS2() {
		return total7days1hS2;
	}

	public void setTotal7days1hS2(int total7days1hS2) {
		this.total7days1hS2 = total7days1hS2;
	}

	public int getTotalCurrent1hS2() {
		return totalCurrent1hS2;
	}

	public void setTotalCurrent1hS2(int totalCurrent1hS2) {
		this.totalCurrent1hS2 = totalCurrent1hS2;
	}

	public int getTotalProjection1hS2() {
		return totalProjection1hS2;
	}

	public void setTotalProjection1hS2(int totalProjection1hS2) {
		this.totalProjection1hS2 = totalProjection1hS2;
	}

	public int getTotalVolumeS2() {
		return totalVolumeS2;
	}

	public void setTotalVolumeS2(int totalVolumeS2) {
		this.totalVolumeS2 = totalVolumeS2;
	}

	public int getTotalVelMedS2() {
		return totalVelMedS2;
	}

	public void setTotalVelMedS2(int totalVelMedS2) {
		this.totalVelMedS2 = totalVelMedS2;
	}

	public double getOccupationRateS1() {
		return occupationRateS1;
	}

	public void setOccupationRateS1(double occupation_rate_s1) {
		this.occupationRateS1 = occupation_rate_s1;
	}

	public double getOccupationRateS2() {
		return occupationRateS2;
	}

	public void setOccupationRateS2(double occupationRateS2) {
		this.occupationRateS2 = occupationRateS2;
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

	public String getQtdeFaixas() {
		return qtdeFaixas;
	}

	public void setQtdeFaixas(String qtdeFaixas) {
		this.qtdeFaixas = qtdeFaixas;
	}

	public String getSentidos() {
		return sentidos;
	}

	public void setSentidos(String sentidos) {
		this.sentidos = sentidos;
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

	public String getSentido1Abbr() {
		return sentido1Abbr;
	}

	public void setSentido1Abbr(String sentido1Abbr) {
		this.sentido1Abbr = sentido1Abbr;
	}

	public String getSentido2Abbr() {
		return sentido2Abbr;
	}

	public void setSentido2Abbr(String sentido2Abbr) {
		this.sentido2Abbr = sentido2Abbr;
	}

	public String getPosicao_nivel_servico() {
		return posicao_nivel_servico;
	}

	public void setPosicao_nivel_servico(String posicao_nivel_servico) {
		this.posicao_nivel_servico = posicao_nivel_servico;
	}

	public String getLastRegister() {
		return lastRegister;
	}

	public void setLastRegister(String lastRegister) {
		this.lastRegister = lastRegister;
	}

	public String getLastPackage() {
		return lastPackage;
	}

	public void setLastPackage(String lastPackage) {
		this.lastPackage = lastPackage;
	}

}
