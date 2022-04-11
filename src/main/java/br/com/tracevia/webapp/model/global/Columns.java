package br.com.tracevia.webapp.model.global;

import java.io.Serializable;

public class Columns implements Serializable {

	private static final long serialVersionUID = 1L;

	private String field; 

	public Columns(String field) {
		this.field = field;

	}

	public String getField() {
		return field;
	}


}
