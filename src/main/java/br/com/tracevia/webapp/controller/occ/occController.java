package br.com.tracevia.webapp.controller.occ;

import java.util.List;

import javax.annotation.PostConstruct;

import br.com.tracevia.webapp.model.occ.OccurrencesData;

public class occController {
	
	
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
