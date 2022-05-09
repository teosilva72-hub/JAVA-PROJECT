package br.com.tracevia.webapp.controller.videowall;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name="videoWallMapsView")
@ViewScoped
public class VIDEOWALLBuildMaps implements Serializable {
		
	/**
	 * SERIAL ID
	 */
	
	private static final long serialVersionUID = 8745171820985927202L;

	@PostConstruct
	public void initalize() {
		
	}
		
}
