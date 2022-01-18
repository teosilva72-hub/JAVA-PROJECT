package br.com.tracevia.webapp.model.occ;

public class OccurenceData2 {
	
	private String data;
	private String	hora;
	private String pedagio;
	private String folio;
	private String report;
	private String sinistro;
	private String direcao;
	private String	kmregistro;
	private String kminicial ;
	private String	kmfinal ;
	private String hrReg ;
	private String	hrchega;
	private String politica;
	private String tipo_veic;
	private String quantidade;
	private String	numveiculo;
	private String marca;
	private String tipo;
	private String modelo;
	private String cor ;
	private String placa;
	private String telefone;
	private String numcond;
	private String nome ;
	private int idade;
	private String saude;
	private String motivo;
	private String observacao;
	private String localFiles;
	private String state_occurrences;
	private Boolean editTable;
	private String nameUser;
	
	/**
	 * @param data
	 * @param hora
	 * @param pedagio
	 * @param folio
	 * @param report
	 * @param sinistro
	 * @param direcao
	 * @param kmregistro
	 * @param kminicial
	 * @param kmfinal
	 * @param hrReg
	 * @param hrchega
	 * @param politica
	 * @param tipo_veic
	 * @param quantidade
	 * @param numveiculo
	 * @param marca
	 * @param tipo
	 * @param modelo
	 * @param cor
	 * @param placa
	 * @param telefone
	 * @param numcond
	 * @param nome
	 * @param idade
	 * @param saude
	 * @param motivo
	 * @param observacao
	 */

	public OccurenceData2(String data, String hora, String pedagio, String folio, String report, String sinistro,
			String direcao, String kmregistro, String kminicial, String kmfinal, String hrReg, String hrchega,
			String politica, String tipo_veic, String quantidade, String numveiculo, String marca, String tipo,
			String modelo, String cor, String placa, String telefone, String numcond, String nome, int idade,
			String saude, String motivo, String observacao,String state_occurrences) {
		
		this.data = data;
		this.hora = hora;
		this.pedagio = pedagio;
		this.folio = folio;
		this.report = report;
		this.sinistro = sinistro;
		this.direcao = direcao;
		this.kmregistro = kmregistro;
		this.kminicial = kminicial;
		this.kmfinal = kmfinal;
		this.hrReg = hrReg;
		this.hrchega = hrchega;
		this.politica = politica;
		this.tipo_veic = tipo_veic;
		this.quantidade = quantidade;
		this.numveiculo = numveiculo;
		this.marca = marca;
		this.tipo = tipo;
		this.modelo = modelo;
		this.cor = cor;
		this.placa = placa;
		this.telefone = telefone;
		this.numcond = numcond;
		this.nome = nome;
		this.idade = idade;
		this.saude = saude;
		this.motivo = motivo;
		this.observacao = observacao;
	}
	
	public OccurenceData2() {}
	
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public String getPedagio() {
		return pedagio;
	}
	public void setPedagio(String pedagio) {
		this.pedagio = pedagio;
	}
	public String getFolio() {
		return folio;
	}
	public void setFolio(String folio) {
		this.folio = folio;
	}
	public String getReport() {
		return report;
	}
	public void setReport(String report) {
		this.report = report;
	}
	public String getSinistro() {
		return sinistro;
	}
	public void setSinistro(String sinistro) {
		this.sinistro = sinistro;
	}
	public String getDirecao() {
		return direcao;
	}
	public void setDirecao(String direcao) {
		this.direcao = direcao;
	}
	public String getKmregistro() {
		return kmregistro;
	}
	public void setKmregistro(String kmregistro) {
		this.kmregistro = kmregistro;
	}
	public String getKminicial() {
		return kminicial;
	}
	public void setKminicial(String kminicial) {
		this.kminicial = kminicial;
	}
	public String getKmfinal() {
		return kmfinal;
	}
	public void setKmfinal(String kmfinal) {
		this.kmfinal = kmfinal;
	}
	public String getHrReg() {
		return hrReg;
	}
	public void setHrReg(String hrReg) {
		this.hrReg = hrReg;
	}
	public String getHrchega() {
		return hrchega;
	}
	public void setHrchega(String hrchega) {
		this.hrchega = hrchega;
	}
	public String getPolitica() {
		return politica;
	}
	public void setPolitica(String politica) {
		this.politica = politica;
	}
	public String getTipo_veic() {
		return tipo_veic;
	}
	public void setTipo_veic(String tipo_veic) {
		this.tipo_veic = tipo_veic;
	}
	public String getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(String quantidade) {
		this.quantidade = quantidade;
	}
	public String getNumveiculo() {
		return numveiculo;
	}
	public void setNumveiculo(String numveiculo) {
		this.numveiculo = numveiculo;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getCor() {
		return cor;
	}
	public void setCor(String cor) {
		this.cor = cor;
	}
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getNumcond() {
		return numcond;
	}
	public void setNumcond(String numcond) {
		this.numcond = numcond;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getIdade() {
		return idade;
	}
	public void setIdade(int idade) {
		this.idade = idade;
	}
	public String getSaude() {
		return saude;
	}
	public void setSaude(String saude) {
		this.saude = saude;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getLocalFiles() {
		// TODO Auto-generated method stub
		return localFiles;
	}
	public void setLocalFiles(String localFiles) {
		this.localFiles = localFiles;
	}

	public String getState_occurrences() {
		// TODO Auto-generated method stub
		return state_occurrences;
		}
	public void setState_occurrences(String state_occurrences) {
		this.state_occurrences = state_occurrences;
	}


	public Boolean getEditTable() {
		// TODO Auto-generated method stub
		return editTable;
		
	}
	public void setEditTable(Boolean editTable) {
		this.editTable = editTable;
	}

	public String getNameUser() {
		// TODO Auto-generated method stub
		return nameUser;
	}

	public void setNameUser(String nameUser) {
		this.nameUser = nameUser;
	}

	public  OccurenceData2 getData1() {
		return data;

	
	

}
