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
		this.setActive(active);
		
	}
	
		public OcurrenceDetails2 (int id, String valores) {
		
		this.id = id;		
		this.valores = valores;	
		
	}
	
		
		public OcurrenceDetails2() {}

		
		
}
