package br.com.tracevia.webapp.controller.dai;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import br.com.tracevia.webapp.dao.occ.OccurrencesDAO;
import br.com.tracevia.webapp.model.dai.DAI;
import br.com.tracevia.webapp.model.global.Equipments;

@ManagedBean(name="daiBean")
@ViewScoped
public class daiBean {
	
	@PostConstruct
	public void initalize() throws Exception {	
		try {
			popUp();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void popUp(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();

		try {
			TimeUnit.MINUTES.sleep(1);
			RequestContext request = RequestContext.getCurrentInstance();
			request.execute("btnPopUp();");
			System.out.println("pop-up alerta");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
