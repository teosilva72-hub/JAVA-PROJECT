package br.com.tracevia.webapp.model.global;

import java.io.Serializable;

public class ColumnModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private String field; 
	private String value; 

	public ColumnModel(String field, String value) {
		this.field = field;
		this.value = value;
	}

	public String getField() {
		return field;
	}

	public String getValue() {
		return value;
	}

}
