package br.com.tracevia.webapp.controller.sat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import br.com.tracevia.webapp.dao.sat.MaintenanceDAO;
import br.com.tracevia.webapp.model.global.Equipments;
import br.com.tracevia.webapp.model.global.ListEquipments;
import br.com.tracevia.webapp.model.sat.Maintenance;

@ManagedBean(name="maintenanceSatView")
@ViewScoped
public class MaintenanceBean{

private int hora;
private String[] hours;

List<Maintenance> satListLanes, satStatus;

@ManagedProperty("#{listEquips}")
private ListEquipments equips;

public ListEquipments getEquips() {
	return equips;
}

public void setEquips(ListEquipments equips) {
	this.equips = equips;
}

public String[] getHours() {
	return hours;
}
public void setHours(String[] hours) {
	this.hours = hours;
}

public List<Maintenance> getSatListLanes() {
	return satListLanes;
}
public List<Maintenance> getSatStatus() {
	return satStatus;
}

@PostConstruct
public void initializer() {
		
	try {						
	
		BuildMaintenance();
		
	/*	for (Maintenance status : satStatus) {			
			System.out.println(Arrays.toString(status.getStatusZero()[0]));
		}*/
		
	} catch (Exception e) {			
		e.printStackTrace();
	}
}
	
// ----------------------------------------------------------------

	public void hours() throws Exception{
		
		hora =  LocalDateTime.now().getHour();
		
		int hour = (hora + 1); 
		
		hours = new String[24];
		 
			for(int h = 0; h < 24; h++){
				 if(hour > 23)
				 	  hour = 0;
				 	if(hour < 10)
				 		hours[h] ="0" + hour;
				 	else hours[h] = String.valueOf(hour);
				 	hour++;
			 }
	} 
	
// ----------------------------------------------------------------
	
	public void BuildMaintenance() {
												
		satListLanes= new ArrayList<Maintenance>();
		satStatus = new ArrayList<Maintenance>();
		
		// LISTAR AUXILIARES
		List<Maintenance> satListStatusAux = new ArrayList<Maintenance>();
		List<Maintenance> satListLanesAux = new ArrayList<Maintenance>();	
		
		MaintenanceDAO dao = new MaintenanceDAO();
					
		int[][] zeroMinStatus = new int[0][24];
		int[][] fifteenMinStatus = new int[0][24];
		int[][] thirtyMinStatus = new int[0][24];
		int[][] forty_fiveMinStatus = new int[0][24];
		
		int[][] zeroMinLanes = new int[0][24];
		int[][] fifteenMinLanes = new int[0][24];
		int[][] thirtyMinLanes = new int[0][24];
		int[][] forty_fiveMinLanes = new int[0][24];
		
		try {			
		
			hours(); // DEFINE HOURS
						
			satListStatusAux = dao.getDadosOn(hours);  // SET LIST STATS AUX 
			
			satListLanesAux = dao.getDados15(hours);  // SET LIST LANES AUX
							
			
		if(!satListStatusAux.isEmpty()) {
			
			for (int s = 0; s < equips.getSatList().size(); s++) { // FOR START
				
				for (int r = 0; r < satListStatusAux.size(); r++) {
					
					Maintenance mainList = new Maintenance();

					 // COMPARA IDS ENTRE AS LISTAS
					if (satListStatusAux.get(r).getSiteId() == equips.getSatList().get(s).getEquip_id()) {
						
							mainList.setData(satListStatusAux.get(r).getData());
							mainList.setHora(satListStatusAux.get(r).getHora());
							mainList.setSiteId(satListStatusAux.get(r).getSiteId());
							mainList.setStatus(satListStatusAux.get(r).getStatus());				
							mainList.setNumberLanes(satListStatusAux.get(r).getNumberLanes());
							mainList.setStatusZero(satListStatusAux.get(r).getStatusZero());
							mainList.setStatusFifteen(satListStatusAux.get(r).getStatusFifteen());
							mainList.setStatusThirty(satListStatusAux.get(r).getStatusThirty());
							mainList.setStatusFortyFive(satListStatusAux.get(r).getStatusFortyFive());	
							
							satStatus.add(mainList);
							
							if(satListStatusAux.size() != 0 && satListStatusAux.size() != 1) // EVITAR BUG
								satListStatusAux.remove(r);						
					}
					
					else {
						
						mainList.setData("");
						mainList.setHora("");
						mainList.setSiteId(equips.getSatList().get(s).getEquip_id());
						mainList.setStatus(0);
						mainList.setNumberLanes(2);
						mainList.setStatusZero(zeroMinStatus);
						mainList.setStatusFifteen(fifteenMinStatus);
						mainList.setStatusThirty(thirtyMinStatus);
						mainList.setStatusFortyFive(forty_fiveMinStatus);	
						
						satStatus.add(mainList);
						
					}			
				}			
		     }
			
		 }else intializeStatusNullList(equips.getSatList());
		
		
		if(!satListLanesAux.isEmpty()) {
			
			for (int s = 0; s < equips.getSatList().size(); s++) { // FOR START
				
				for (int r = 0; r < satListLanesAux.size(); r++) {
					
					Maintenance mainList = new Maintenance();

					 // COMPARA IDS ENTRE AS LISTAS
					if (satListLanesAux.get(r).getSiteId() == equips.getSatList().get(s).getEquip_id()) {
											
							mainList.setData(satListLanesAux.get(r).getData());
							mainList.setHora(satListLanesAux.get(r).getHora());
							mainList.setSiteId(satListLanesAux.get(r).getSiteId());						
							mainList.setLaneZero(satListLanesAux.get(r).getLaneZero());
							mainList.setLaneFifteen(satListLanesAux.get(r).getLaneFifteen());
							mainList.setLaneThirty(satListLanesAux.get(r).getLaneThirty());
							mainList.setLaneFortyFive(satListLanesAux.get(r).getLaneFortyFive());	
							
							satListLanes.add(mainList);
							
							if(satListLanesAux.size() != 0 && satListLanesAux.size() != 1) // EVITAR BUG
							    satListLanesAux.remove(r);						
					}
					
					else {
											
						mainList.setData("");
						mainList.setHora("");
						mainList.setSiteId(equips.getSatList().get(s).getEquip_id());					
						mainList.setLaneZero(zeroMinLanes);
						mainList.setLaneFifteen(fifteenMinLanes);
						mainList.setLaneThirty(thirtyMinLanes);
						mainList.setLaneFortyFive(forty_fiveMinLanes);	
						
						satListLanes.add(mainList);
						
					}			
				}			
		     }
			
		 }else intializeLanesNullList(equips.getSatList());
													
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
  // ----------------------------------------------------------------
	
	public void intializeStatusNullList(List<? extends Equipments> satList) throws Exception {
		
		//DateTimeApplication dt = new DateTimeApplication();
		//NotificationsBean not = new NotificationsBean();
		
		int[][] zeroMinStatus = new int[0][24];
		int[][] fifteenMinStatus = new int[0][24];
		int[][] thirtyMinStatus = new int[0][24];
		int[][] forty_fiveMinStatus = new int[0][24];
		

		for (int i = 0; i < satList.size(); i++) {
            
			Maintenance main= new Maintenance();
				
				main.setSiteId(satList.get(i).getEquip_id());
				main.setData("");
				main.setHora("");		
				main.setStatus(0);
				main.setStatusZero(zeroMinStatus);
				main.setStatusFifteen(fifteenMinStatus);
				main.setStatusThirty(thirtyMinStatus);
				main.setStatusFortyFive(forty_fiveMinStatus);	

			satStatus.add(main);
			
			//not.updateStatus(NotificationsAlarmsEnum.OFFLINE.getAlarm(), satList.get(i).getEquip_id(), NotificationType.SAT.getType(), dt.currentDateTime(), false, true);

		}
	}
	
	// ----------------------------------------------------------------------------------------------------
		
		public void intializeLanesNullList(List<? extends Equipments> satList) throws Exception {
			
		//	DateTimeApplication dt = new DateTimeApplication();
		//	NotificationsBean not = new NotificationsBean();
			
			int[][] zeroMinLanes = new int[0][24];
			int[][] fifteenMinLanes = new int[0][24];
			int[][] thirtyMinLanes = new int[0][24];
			int[][] forty_fiveMinLanes = new int[0][24];
			

			for (int i = 0; i < satList.size(); i++) {
	            
				Maintenance main= new Maintenance();
					
					main.setSiteId(satList.get(i).getEquip_id());
					main.setData("");
					main.setHora("");		
					main.setLane(0);
					main.setVolume(0);	
					main.setLaneZero(zeroMinLanes);
					main.setLaneFifteen(fifteenMinLanes);
					main.setLaneThirty(thirtyMinLanes);
					main.setLaneFortyFive(forty_fiveMinLanes);	

				satListLanes.add(main);
				
				//not.updateStatus(NotificationsAlarmsEnum.OFFLINE.getAlarm(), satList.get(i).getEquip_id(), NotificationType.SAT.getType(), dt.currentDateTime(), false, true);

			}
		}
		
		// ----------------------------------------------------------------------------------------------------
	
	
}
