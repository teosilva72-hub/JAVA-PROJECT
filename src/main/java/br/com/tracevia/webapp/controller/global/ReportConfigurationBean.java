package br.com.tracevia.webapp.controller.global;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.tracevia.webapp.model.global.Equipments;
import br.com.tracevia.webapp.model.global.ReportBuild;

@ManagedBean(name="reportConfigBean")
@RequestScoped
public class ReportConfigurationBean {
		
	private ReportBuild build;
	
	List<? extends Equipments> listEquips;  

	public ReportBuild getBuild() {
		return build;
	}

	public void setBuild(ReportBuild build) {
		this.build = build;
	}

	public List<? extends Equipments> getListEquips() {
		return listEquips;
	}
	
	
	
	


}
