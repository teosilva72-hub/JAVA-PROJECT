package br.com.tracevia.webapp.model.occ;

public class OccurrencesDetails {
	
	private int id;
	private String campo;
	private String valores;
	private boolean active;
	
	public OccurrencesDetails(int id, String campo, String valores, boolean active) {
		
		this.id = id;
		this.campo = campo;
		this.valores = valores;
		this.setActive(active);
		
	}
	
public OccurrencesDetails(int id, String valores) {
		
		this.id = id;		
		this.valores = valores;	
		
	}
	
	
	



	public OccurrencesDetails() {}





	public String getCampo() {
		return campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

	public String getValores() {
		return valores;
	}

	public void setValores(String valores) {
		this.valores = valores;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	
	
	
	
	
	
}
