package br.com.tracevia.webapp.controller.dai;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name="daiMapsView")
@ViewScoped
public class DAIBuildMaps implements Serializable {
	
	/**
	 * SERIAL ID
	 */
	private static final long serialVersionUID = 1L;


	@PostConstruct
	public void initalize() {			
		
	}	

}
