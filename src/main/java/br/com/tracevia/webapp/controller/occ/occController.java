package br.com.tracevia.webapp.controller.occ;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import br.com.tracevia.webapp.model.occ.OccurrencesData;

public class occController implements Serializable {
		
	/**
	 * SERIAL ID
	 */
	private static final long serialVersionUID = -750658232518820295L;

	@PostConstruct
	public void init() {
		
	}
	
	public List<OccurrencesData> getOccurrences() {
		return occurrences;
	}

	public void setOccurrences(List<OccurrencesData> occurrences) {
		this.occurrences = occurrences;
	}

	//variaveis
	private List<OccurrencesData> occurrences;
}
