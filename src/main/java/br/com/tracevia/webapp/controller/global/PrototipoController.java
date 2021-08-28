package br.com.tracevia.webapp.controller.global;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.tracevia.webapp.model.global.ReportBuild;
import br.com.tracevia.webapp.model.global.ReportSelection;

@ManagedBean(name="prototipoBean")
@RequestScoped
public class PrototipoController implements Serializable{

	/**
	 * @author Wellington 20-08-2021
	 */
	private static final long serialVersionUID = 1L;
		
	private ReportSelection select;
	private ReportBuild build;
	
	
	
	

}
