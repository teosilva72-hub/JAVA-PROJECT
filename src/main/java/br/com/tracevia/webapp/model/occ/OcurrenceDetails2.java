package br.com.tracevia.webapp.model.occ;

public class OcurrenceDetails2 {

	private int id;
	private String campo;
	private String valores;
	private boolean active;
	
	public OcurrenceDetails2(int id, String campo, String valores, boolean active) {
		
		this.id = id;
		this.campo = campo;
		this.valores = valores;
		this.active = active;
		
	}
	
	public OcurrenceDetails2 (int id, String valores) {
	
	this.id = id;		
	this.valores = valores;	
	
    }
			
	public OcurrenceDetails2() {}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
		
		
}
