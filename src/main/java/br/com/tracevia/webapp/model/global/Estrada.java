package br.com.tracevia.webapp.model.global;

public class Estrada {
			
	private int altura;
	private int comprimento; 
	private int posX;
	private int posY;
	private String id;	
	private String nome;
	private String posicao;
	private String concessionaria;
		
	public Estrada(String id, int posX, int posY, int comprimento, int altura,  String nome, String posicao,
			String concessionaria) {
		
		this.id = id;		
		this.posX = posX;
		this.posY = posY;
		this.comprimento = comprimento;
		this.altura = altura;	
		this.nome = nome;		
		this.posicao = posicao;
		this.concessionaria = concessionaria;
	}

	public Estrada() {}	

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getAltura() {
		return altura;
	}
	public void setAltura(int altura) {
		this.altura = altura;
	}
	public int getComprimento() {
		return comprimento;
	}
	public void setComprimento(int comprimento) {
		this.comprimento = comprimento;
	}
	public int getPosX() {
		return posX;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}
	public int getPosY() {
		return posY;
	}
	public void setPosY(int posY) {
		this.posY = posY;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPosicao() {
		return posicao;
	}
	public void setPosicao(String posicao) {
		this.posicao = posicao;
	}
	public String getConcessionaria() {
		return concessionaria;
	}
	public void setConcessionaria(String concessionaria) {
		this.concessionaria = concessionaria;
	}
	
}