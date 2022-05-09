package br.com.tracevia.webapp.controller.cftv;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name="cftvMapsView")
@ViewScoped
public class CFTVBuildMaps implements Serializable {
	
	/**
	 * SERIAL ID
	 */
	private static final long serialVersionUID = 765196399505145894L;



	@PostConstruct
	public void initalize() {
					
	}	
}
