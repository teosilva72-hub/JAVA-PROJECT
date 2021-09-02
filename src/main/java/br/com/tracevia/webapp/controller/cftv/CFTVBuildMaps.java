package br.com.tracevia.webapp.controller.cftv;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import br.com.tracevia.webapp.controller.global.NotificationsBean;
import br.com.tracevia.webapp.dao.cftv.CFTVDAO;
import br.com.tracevia.webapp.model.cftv.CFTV;
import br.com.tracevia.webapp.model.global.ListEquipments;

@ManagedBean(name="cftvMapsView")
@ViewScoped
public class CFTVBuildMaps {
	
	@ManagedProperty("#{listEquips}")
	private ListEquipments equips;
	
	List<CFTV> cftvStatus;
			
	public List<CFTV> getCftvStatus() {
		return cftvStatus;
	}
	
	public ListEquipments getEquips() {
		return equips;
	}

	public void setEquips(ListEquipments equips) {
		this.equips = equips;
	}

	@PostConstruct
	public void initalize() {
		
		BuildCFTV();
				
	}
	
	public void BuildCFTV() {
			
		try {	
		
		try {
			
			//CFTVDAO cftvDAO = new CFTVDAO();  
			//NotificationsBean notif = new NotificationsBean();			
																			
			//cftvStatus = new ArrayList<CFTV>();			
			//cftvStatus = cftvDAO.Status();				
				
            }catch(IndexOutOfBoundsException ex) {}
		
		}catch(Exception ex) {}		
						
	}

}
