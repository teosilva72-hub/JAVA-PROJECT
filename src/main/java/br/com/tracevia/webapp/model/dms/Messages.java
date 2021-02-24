package br.com.tracevia.webapp.model.dms;

import java.util.List;

public class Messages {

	private int id_message;
	private int id_image;
	private int id_modify;
	private int equip;
	private String id;
	private String tipo;
	private String nome;
	private String image;
	private List<Pages> pages;
	private String tableImg;
	private String tableMessage;
	private boolean activeMessage;

	public Messages(int id_message, int id_image, int id_modify, int equip, String id, String tipo, String nome,
			String image, String texto1, String texto2, String texto3, String tableImg, String tableMessage,
			boolean activeMessage) {
		this.id_message = id_message;
		this.id_image = id_image;
		this.id_modify = id_modify;
		this.equip = equip;
		this.id = id;
		this.tipo = tipo;
		this.nome = nome;
		this.image = image;
		this.tableImg = tableImg;
		this.tableMessage = tableMessage;
		this.activeMessage = activeMessage;
	}

	public Messages() {
	}

	public int getId_message() {
		return id_message;
	}

	public void setId_message(int id_message) {
		this.id_message = id_message;
	}

	public int getId_image() {
		return id_image;
	}

	public void setId_image(int id_image) {
		this.id_image = id_image;
	}

	public List<Pages> getPages() {
		return pages;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
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

	public void setPages(String text1, String text2, String text3) {
		Pages page;
		if (text1 != "" && text2 != "" && text3 != "")
			page = new Pages(text1, text2, text3);
		else
			page = new Pages();
		this.pages.add(page);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id_message;
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
		if (id_message != other.id_message)
			return false;
		return true;
	}

	public class Pages {
		private Boolean contain;
		private String text1;
		private String text2;
		private String text3;

		public Pages(String text1, String text2, String text3) {
			this.text1 = text1;
			this.text2 = text2;
			this.text3 = text3;
			this.contain = true;
		}

		public Pages() {
			this.contain = false;
		}

		public String getText1() {
			return text1;
		}

		public String getText2() {
			return text2;
		}

		public String getText3() {
			return text3;
		}

		public Boolean getContain() {
			return contain;
		}
	}
}
