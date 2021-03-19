package br.com.tracevia.webapp.model.dms;

import java.util.ArrayList;
import java.util.List;

public class Messages {

	private int id_message;
	private int id_modify;
	private int equip;
	private String id;
	private String tipo;
	private String nome;
	private List<Pages> pages;
	private boolean activeMessage;
	private int driver;

	public Messages(int id_message, int id_modify, int equip, String id, String tipo, String nome,
			boolean activeMessage, int driver) {
		this.id_message = id_message;
		this.id_modify = id_modify;
		this.equip = equip;
		this.id = id;
		this.tipo = tipo;
		this.nome = nome;
		this.activeMessage = activeMessage;
		this.driver = driver;
		this.pages = new ArrayList<Pages>();
	}

	public Messages() {
		this.pages = new ArrayList<Pages>();
	}

	public int getId_message() {
		return id_message;
	}

	public void setId_message(int id_message) {
		this.id_message = id_message;
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

	public int getDriver() {
		return driver;
	}

	public void setDriver(int driver) {
		this.driver = driver;
	}

	public boolean isActiveMessage() {
		return activeMessage;
	}

	public void setActiveMessage(boolean activeMessage) {
		this.activeMessage = activeMessage;
	}

	// Driver 1
	public void setPages(String text1, String text2, String text3, int id_image, String image, float timer, int idx) {
		Pages pages = new Pages(text1, text2, text3, id_image, image, timer, idx);
		this.pages.add(pages);
	}
	
	// Driver 2
	public void setPages(String text1, String text2, int id_image, String image, float timer, int idx) {
		Pages pages = new Pages(text1, text2, id_image, image, timer, idx);
		this.pages.add(pages);
	}
	
	// Driver 3
	public void setPages(String text1, String text2, String text3, int id_image, String image, int id_image2, String image2, float timer, int idx) {
		Pages pages = new Pages(text1, text2, text3, id_image, image, id_image2, image2, timer, idx);
		this.pages.add(pages);
	}

	// Void
	public void setPages(int page) {
		this.pages.add(new Pages());
	}

	public boolean revision() {
		boolean revision = false;
		
		if (pages.size() == 0)
			return false;

		for (Pages page : pages) {
			if (page.contain && page.timer != 0 && !revision)
				continue;
			else
				revision = true;
			page.timer = 0;
		}

		return true;
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
		private float timer;
		private int id_image;
		private String image;
		private int id_image2;
		private String image2;
		private int page;

		// Driver 1
		public Pages(String text1, String text2, String text3, int id_image, String image, float timer, int page) {
			this.text1 = text1;
			this.text2 = text2;
			this.text3 = text3;
			this.id_image = id_image;
			this.image = image;
			this.timer = timer;
			this.page = page;
			this.contain = true;
		}
		
		// Driver 2
		public Pages(String text1, String text2, int id_image, String image, float timer, int page) {
			this.text1 = text1;
			this.text2 = text2;
			this.id_image = id_image;
			this.image = image;
			this.timer = timer;
			this.page = page;
			this.contain = true;
		}
		
		// Driver 3
		public Pages(String text1, String text2, String text3, int id_image, String image, int id_image2, String image2,
				float timer, int page) {
			this.text1 = text1;
			this.text2 = text2;
			this.text3 = text3;
			this.id_image = id_image;
			this.image = image;
			this.id_image2 = id_image2;
			this.image2 = image2;
			this.timer = timer;
			this.page = page;
			this.contain = true;
		}

		// Void
		public Pages() {
			this.text1 = "";
			this.text2 = "";
			this.text3 = "";
			this.id_image = 0;
			this.image = "000_6464.bmp";
			this.id_image2 = 0;
			this.image2 = "000_6464.bmp";
			this.timer = 0;
			this.page = 0;
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

		public float getTimer() {
			return timer;
		}

		public int getId_image() {
			return id_image;
		}

		public String getImage() {
			return image;
		}

		public int getId_image2() {
			return id_image2;
		}

		public String getImage2() {
			return image2;
		}

		public Boolean getContain() {
			return contain;
		}

		public int getPage() {
			return page;
		}
	}
}
