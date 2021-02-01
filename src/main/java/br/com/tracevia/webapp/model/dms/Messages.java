package br.com.tracevia.webapp.model.dms;

public class Messages {
	
	private int id_reg;
	private int id_imagem;
	private int id_modify;
	private int equip;
	private String id;
	private String tipo;
	private String nome;
	private String imagem;
	private String texto1;
	private String texto2;
	private String texto3;
	private String tableImg;
	private String tableMessage;
	private boolean activeMessage;	
				
	public Messages(int id_reg, int id_imagem, int id_modify, int equip, String id, String tipo, String nome,
			String imagem, String texto1, String texto2, String texto3, String tableImg, String tableMessage,
			 boolean activeMessage) {		
		this.id_reg = id_reg;
		this.id_imagem = id_imagem;
		this.id_modify = id_modify;
		this.equip = equip;
		this.id = id;
		this.tipo = tipo;
		this.nome = nome;
		this.imagem = imagem;
		this.texto1 = texto1;
		this.texto2 = texto2;
		this.texto3 = texto3;
		this.tableImg = tableImg;
		this.tableMessage = tableMessage;		
		this.activeMessage = activeMessage;
	}

	public Messages() {}
			
	public int getId_reg() {
		return id_reg;
	}

	public void setId_reg(int id_reg) {
		this.id_reg = id_reg;
	}

	public int getId_imagem() {
		return id_imagem;
	}
	
	public void setId_imagem(int id_imagem) {		
		this.id_imagem = id_imagem;
	}
						
	public int getId_modify() {
		return id_modify;
	}

	public void setId_modify(int id_modify) {
		this.id_modify = id_modify;
	}
		
	public int getEquip() {
		return equip;
	}

	public void setEquip(int equip) {
		this.equip = equip;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getTexto1() {
		return texto1;
	}

	public void setTexto1(String texto1) {
		this.texto1 = texto1;
	}

	public String getTexto2() {
		return texto2;
	}

	public void setTexto2(String texto2) {
		this.texto2 = texto2;
	}

	public String getTexto3() {
		return texto3;
	}

	public void setTexto3(String texto3) {
		this.texto3 = texto3;
	}
		
	public String getTableImg() {
		return tableImg;
	}

	public void setTableImg(String tableImg) {
		this.tableImg = tableImg;
	}

	public String getTableMessage() {
		return tableMessage;
	}

	public void setTableMessage(String tableMessage) {
		this.tableMessage = tableMessage;
	}
				
	public boolean isActiveMessage() {
		return activeMessage;
	}

	public void setActiveMessage(boolean activeMessage) {
		this.activeMessage = activeMessage;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id_reg;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Messages other = (Messages) obj;
		if (id_reg != other.id_reg)
			return false;
		return true;
	}
	
}
