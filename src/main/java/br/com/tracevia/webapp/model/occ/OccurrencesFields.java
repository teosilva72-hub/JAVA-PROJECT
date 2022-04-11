package br.com.tracevia.webapp.model.occ;

public class OccurrencesFields {
	private int id;
	private String campo;
	private boolean mandatory;
	private String tipo_campo;
	private String restricao;

	
public boolean isMandatory() {
		return mandatory;
	}
	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}
public OccurrencesFields(int id, String campo, String restricao, boolean mandatory) {
	this.id = id;
	this.campo = campo;
	this.restricao = restricao;
	this.mandatory = mandatory;
}
public OccurrencesFields() {}

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

public String getRestricao() {
	return restricao;
}

public void setRestricao(String restricao) {
	this.restricao = restricao;
}
public String getTipo_campo() {
	return tipo_campo;
}
public void setTipo_campo(String tipo_campo) {
	this.tipo_campo = tipo_campo;
}



}
