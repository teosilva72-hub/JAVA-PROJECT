package br.com.tracevia.webapp.controller.sat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import br.com.tracevia.webapp.controller.global.ListEquipments;
import br.com.tracevia.webapp.dao.sat.MaintenanceDAO;
import br.com.tracevia.webapp.model.global.Equipments;
import br.com.tracevia.webapp.model.sat.Maintenance;
import br.com.tracevia.webapp.model.sat.SAT;

@ManagedBean(name="maintenanceSatView")
@ViewScoped
public class MaintenanceBean{

private int hora;
private int dia;
private String[] hours;
private String[] days;

List<Maintenance> satListLanes, satStatus;

@ManagedProperty("#{listEquipsBean}")
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

public String[] getDays() {
	return days;
}

public void setDays(String[] days) {
	this.days = days;
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
		
		/*for (Maintenance status : satListLanes) {			
			System.out.println(Arrays.toString(status.getLaneZero()[0]));
		}*/
		
	} catch (Exception e) {			
		e.printStackTrace();
	}
}
	
// ----------------------------------------------------------------

	public void hours() throws Exception{
		
		Calendar calendar = Calendar.getInstance();				
		calendar.add(Calendar.HOUR_OF_DAY, -23);
		
		hora = calendar.get(Calendar.HOUR_OF_DAY);
		dia = calendar.get(Calendar.DATE);
					
		int hour = hora; 
		int day = dia;
		
		hours = new String[24];
		days = new String[24];
		 
			for(int h = 0; h < 24; h++){
				 
					if(hour > 23) {
					 	  hour = 0;
					      day++;
					 }
				       
			 	if(hour < 10)
			 		hours[h] ="0" + hour;				 	    
			 	else hours[h] = String.valueOf(hour);
			 	
			 	days[h] = String.valueOf(day);
			 	
			 	System.out.println(hour);
			 	
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
		Maintenance mainList;

		int index;
					
		int[] zeroMinStatus = new int[24];
		int[] fifteenMinStatus = new int[24];
		int[] thirtyMinStatus = new int[24];
		int[] forty_fiveMinStatus = new int[24];
		
		int[][] zeroMinLanes = new int[8][24];
		int[][] fifteenMinLanes = new int[8][24];
		int[][] thirtyMinLanes = new int[8][24];
		int[][] forty_fiveMinLanes = new int[8][24];
		
		try {			
			hours(); // DEFINE HOURS
						
			satListStatusAux = dao.getDadosOn(hours, days);  // SET LIST STATS AUX 
			satListLanesAux = dao.getDados15(hours, days);  // SET LIST LANES AUX
			
			if(!satListStatusAux.isEmpty()) {
				for (Equipments sat : equips.getSatList()) {
					index = satListStatusAux.size() - 1;
					
					mainList = new Maintenance();

					mainList.setData("");
					mainList.setHora("");
					mainList.setSiteId(sat.getEquip_id());
					mainList.setStatus(0);
					mainList.setNumberLanes(2);
					mainList.setStatusZero(zeroMinStatus);
					mainList.setStatusFifteen(fifteenMinStatus);
					mainList.setStatusThirty(thirtyMinStatus);
					mainList.setStatusFortyFive(forty_fiveMinStatus);

					for (; index >= 0; index--) { // FOR START
						Maintenance status = satListStatusAux.get(index);

						// COMPARA IDS ENTRE AS LISTAS
						if (status.getSiteId() == sat.getEquip_id()) {
							mainList = status;
							
							if(index >= 0) // EVITAR BUG
								satListStatusAux.remove(index);
							break;
						}			
					}	

					satStatus.add(mainList);
				}
			} else
				intializeStatusNullList(equips.getSatList());
			
			if(!satListLanesAux.isEmpty()) {
				for (Equipments sat : equips.getSatList()) {
					index = satListLanesAux.size() - 1;

					mainList = new Maintenance();

					mainList.setData("");
					mainList.setHora("");
					mainList.setSiteId(sat.getEquip_id());					
					mainList.setLaneZero(zeroMinLanes);
					mainList.setLaneFifteen(fifteenMinLanes);
					mainList.setLaneThirty(thirtyMinLanes);
					mainList.setLaneFortyFive(forty_fiveMinLanes);
					
					for (; index >= 0; index--) { // FOR START
						Maintenance lanes = satListLanesAux.get(index);

						// COMPARA IDS ENTRE AS LISTAS
						if (lanes.getSiteId() == sat.getEquip_id()) {
							mainList = lanes;
							
							if(index >= 0) // EVITAR BUG
								satListLanesAux.remove(index);			
							break;			
						}		
					}

					satListLanes.add(mainList);
				}
			} else
				intializeLanesNullList(equips.getSatList());										
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
  // ----------------------------------------------------------------
	
	public void intializeStatusNullList(List<SAT> satList) throws Exception {
		
		//DateTimeApplication dt = new DateTimeApplication();
		//NotificationsBean not = new NotificationsBean();
		
		int[] zeroMinStatus = new int[24];
		int[] fifteenMinStatus = new int[24];
		int[] thirtyMinStatus = new int[24];
		int[] forty_fiveMinStatus = new int[24];
		

		for (int i = 0; i < satList.size(); i++) {
            
			Maintenance main= new Maintenance();
				
				main.setSiteId(satList.get(i).getEquip_id());
				main.setData("");
				main.setHora("");		
				main.setStatus(0);
				main.setNumberLanes(satList.get(i).getNumFaixas());
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
			
			int[][] zeroMinLanes = new int[8][24];
			int[][] fifteenMinLanes = new int[8][24];
			int[][] thirtyMinLanes = new int[8][24];
			int[][] forty_fiveMinLanes = new int[8][24];
			

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
