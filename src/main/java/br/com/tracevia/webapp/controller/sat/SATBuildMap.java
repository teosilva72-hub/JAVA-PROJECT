package br.com.tracevia.webapp.controller.sat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.tracevia.webapp.cfg.NotificationType;
import br.com.tracevia.webapp.cfg.NotificationsAlarmsEnum;
import br.com.tracevia.webapp.controller.global.NotificationsBean;
import br.com.tracevia.webapp.dao.sat.DataSatDAO;
import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.model.global.ListEquipments;
import br.com.tracevia.webapp.model.sat.SAT;
import br.com.tracevia.webapp.util.SessionUtil;

@ManagedBean(name = "satMapsView")
@ViewScoped
public class SATBuildMap {

	List<SAT> satListValues, satStatus;

	@ManagedProperty("#{listEquips}")
	private ListEquipments equips;

	public ListEquipments getEquips() {
		return equips;
	}

	public void setEquips(ListEquipments equips) {
		this.equips = equips;
	}

	public List<SAT> getSatListValues() {
		return satListValues;
	}

	public List<SAT> getSatStatus() {
		return satStatus;
	}

	@PostConstruct
	public void initalize() {

		BuildSAT();

	}

	@SuppressWarnings("unlikely-arg-type")
	public void BuildSAT() {

		try {

			try {

				DataSatDAO dao = new DataSatDAO();
				NotificationsBean not = new NotificationsBean();
				DateTimeApplication dt = new DateTimeApplication();
				
				// LIMIT SEARCH				
				int limit = equips.getSatList().size();
				int countLimit = 0;

				// LISTAS
				satListValues = new ArrayList<SAT>();
				satStatus = new ArrayList<SAT>();		
								
				// LISTAR AUXILIARES											
				List<SAT> data15MinList = new ArrayList<SAT>();
				List<SAT> data30MinList = new ArrayList<SAT>();
				List<SAT> data03HourList = new ArrayList<SAT>();
				List<SAT> data06HourList = new ArrayList<SAT>();
				List<SAT> noDataList = new ArrayList<SAT>();
				List<SAT> noDataAllList = new ArrayList<SAT>();
				List<Integer> availabilityList = new ArrayList<Integer>();
				List<Integer> unavailabilityList = new ArrayList<Integer>();
				List<Integer> equipIdList = new ArrayList<Integer>();	
				List<Integer> equipIdListAux = new ArrayList<Integer>();				
				List<Integer> valuesAuxList = new ArrayList<Integer>();
										
				// INIT SAT LIST IDS
				equips.getSatList().forEach(item -> equipIdList.add(item.getEquip_id()));

				///////////////////////////////
				// SAT EQUIPMENTS
				//////////////////////////////

				 data15MinList = dao.dataInterval(limit, "MINUTE", 15);
				
					if(!data15MinList.isEmpty()) {
												
						data15MinList.forEach(item -> availabilityList.add(item.getEquip_id()));
											
						satListValues.addAll(data15MinList);		
						
					    limit = limit - data15MinList.size();
																								
					}
					
					// ----------------------------------------------------------------------- 
					
					if(limit > 0) {
						
						 data30MinList = dao.dataInterval(limit, "MINUTE", 30);
								
								if(!data30MinList.isEmpty()) {
									
									if(!data15MinList.isEmpty()) {
									
										for(SAT value : data30MinList) {
											
											if(!availabilityList.contains(value.getEquip_id())) { 
													availabilityList.add(value.getEquip_id());										
													satListValues.add(value);
													countLimit++;
											}					
										 }	
										
											limit = limit - countLimit;
																																				
										} else {
											
											data30MinList.forEach(item -> availabilityList.add(item.getEquip_id()));																					
											satListValues.addAll(data30MinList);			
											
											limit = limit - data30MinList.size();
											
										}																									
									}							
							    }
					
							// ----------------------------------------------------------------------- 
					
							if(limit > 0) {
								
								 data03HourList = dao.dataInterval(limit, "HOUR", 3);
										
										if(!data03HourList.isEmpty()) {
											
											if(!data30MinList.isEmpty()) {
											
												for(SAT value : data03HourList) {
													
													if(!availabilityList.contains(value.getEquip_id())) { 
															availabilityList.add(value.getEquip_id());										
															satListValues.add(value);	
															countLimit++;
													}					
												 }	
												
													limit = limit - countLimit;
																																						
												} else {
													
													data03HourList.forEach(item -> availabilityList.add(item.getEquip_id()));																											
													satListValues.addAll(data03HourList);	
													
													limit = limit - data03HourList.size();	
													
												}																																		
											}							
									    }
							
							// ----------------------------------------------------------------------- 
							
							if(limit > 0) {
								
								 data06HourList = dao.dataInterval(limit, "HOUR", 6);
										
										if(!data06HourList.isEmpty()) {
											
											if(!data03HourList.isEmpty()) {
											
												for(SAT value : data06HourList) {
													
													if(!availabilityList.contains(value.getEquip_id())) { 
															availabilityList.add(value.getEquip_id());										
															satListValues.add(value);
															countLimit++;
													}					
												 }	
												
													limit = limit - countLimit;
																																						
												} else {
													
													data06HourList.forEach(item -> availabilityList.add(item.getEquip_id()));																										
													satListValues.addAll(data06HourList);
													
													limit = limit - data06HourList.size();
													
												}																					
											}							
									    }
							
							// ----------------------------------------------------------------------- 
							
							if(limit > 0 && !availabilityList.isEmpty()) {
								
								for(int value : equipIdList) {									
									if(!availabilityList.contains(value)) { 
											unavailabilityList.add(value);																												
									}					
								 }		
																												
								noDataList = dao.noDataInterval(limit, unavailabilityList);
								
								// COMPARE IF EXIST VALUES IN DATABASE
								satListValues.forEach(item -> valuesAuxList.add(item.getEquip_id())); // ADD VALUES TO AUX LIST
								noDataList.forEach(item -> valuesAuxList.add(item.getEquip_id())); // ADD NO VALUES TO AUX LIST
																								
								// VERIFICA SE A LISTA POSSUI TODOS OS IDS INDISPONIVEIS
								
								for(int id : equipIdList) {					
									if(!valuesAuxList.contains(id))
										 equipIdListAux.add(id);					
								}
								
								// CASO NÃO TENHA TODOS REGISTROS
								if(!equipIdListAux.isEmpty()) {
									
									noDataAllList.addAll(noDataList);
									noDataAllList.addAll(completeAllEquips(equipIdListAux));
																												
									satListValues.addAll(noDataAllList); // ADD DATA TO A LIST
									
								} else {										
									
									satListValues.addAll(noDataList); // ADD DATA TO A LIST
								}
																							
								limit = 0; // AQUI ZERA O LIMIT
																
							} else if(availabilityList.isEmpty()) intializeNullList(equipIdList);
							
							// ----------------------------------------------------------------------- 
							
							Collections.sort(satListValues); // ORDER SAT LIST
							
							// -----------------------------------------------------------------------												
							
							// SWITCH NOTIFICATION STATUS
							
							if(!availabilityList.isEmpty()) {
										
								for(SAT sat : satListValues) {	
																																																
									if(availabilityList.contains(sat.getEquip_id()) && sat.getStatus() == 0)
										  not.updateStatus(NotificationsAlarmsEnum.ONLINE.getAlarm(), sat.getEquip_id(), NotificationType.SAT.getType(),
												dt.currentDateTime(), true, false);
																				
									else if(unavailabilityList.contains(sat.getEquip_id()) && sat.getStatus() == 1)
										  not.updateStatus(NotificationsAlarmsEnum.OFFLINE.getAlarm(), sat.getEquip_id(),
												NotificationType.SAT.getType(), dt.currentDateTime(), false, true);																					
									
								}
																	
							}
							
						// ----------------------------------------------------------------------- 						
				
			} catch (IndexOutOfBoundsException ex) {

				ex.printStackTrace();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":navbarDropdown2");

	}

	// ----------------------------------------------------------------------------------------------------

	public void intializeNullList(List<Integer> satList) throws Exception {

		DateTimeApplication dt = new DateTimeApplication();
		NotificationsBean not = new NotificationsBean();
		
		List<SAT> noDataList = new ArrayList<SAT>();
		List<SAT> noDataAllList = new ArrayList<SAT>();
		List<Integer> equipIdList = new ArrayList<Integer>();	
		List<Integer> noDataAuxList = new ArrayList<Integer>();	
		List<Integer> unavailabilityList = new ArrayList<Integer>();
		
		// INIT SAT LIST IDS
		equips.getSatList().forEach(item -> equipIdList.add(item.getEquip_id()));
		
		DataSatDAO dao = new DataSatDAO();
		
		noDataList = dao.noDataInterval(equips.getSatList().size(), satList);
				
		noDataList.forEach(item -> noDataAuxList.add(item.getEquip_id())); // CONVERT ID TO INTEGER TYPE
			
		// VERIFICA SE A LISTA POSSUI TODOS OS IDS DISPONIVEIS				
		for(int id : equipIdList) {			
			if(!noDataAuxList.contains(id)) {
				 unavailabilityList.add(id);
			}
		}		
			
		// CASO NÃO TENHA TODOS REGISTROS
		if(!unavailabilityList.isEmpty()) {
			
			noDataAllList.addAll(noDataList);
			noDataAllList.addAll(completeAllEquips(unavailabilityList));
			
			Collections.sort(noDataAllList); // ORDER SAT LIST			
			satListValues.addAll(noDataAllList); // ADD DATA TO A LIST
			
		} else {
			
			 Collections.sort(noDataList); // ORDER SAT LIST
			 satListValues.addAll(noDataList); // ADD DATA TO A LIST
		}	
		
		// -----------------------------------------------------------------------		
					
		// CHECK ONLINE STATUS
		for(SAT sat : satListValues) {	
								
				if(sat.getStatus() == 1) 
					  not.updateStatus(NotificationsAlarmsEnum.OFFLINE.getAlarm(), sat.getEquip_id(),
							NotificationType.SAT.getType(), dt.currentDateTime(), false, true);
		}		
	}

	// ----------------------------------------------------------------------------------------------------
	
	public List<SAT> completeAllEquips(List<Integer> equips){
		
		List<SAT> list = new ArrayList<SAT>();
		
		for(int i = 0; i < equips.size(); i++) {
		
		SAT sat = new SAT();
			
		sat.setEquip_id(equips.get(i));
		sat.setLastPackage("00:00");		
		sat.setLastRegister("00:00"); 					
		sat.setQuantidadeS1(0);
		sat.setVelocidadeS1(0);
		sat.setQuantidadeS2(0);
		sat.setVelocidadeS2(0);
		sat.setStatus(0);			
		sat.setStatusInterval(0);
			
		// -------------------------------
		// TABLE INFO
		// -------------------------------
		
		// TABLE HEADERS 
		
		sat.setCurrentDatetime("07/01/2000 07:00"); //  MAIN HEADER					
		sat.setSevenDaysDatetime("01/01/2000 07:00"); // 7 DAYS HEADER
		sat.setLastOneDatetime("07/01/2000 06:00"); // LAST HOUR HEADER
		sat.setProjectionDatetime("07/01/2000 07:00"); // PROJECTION HEADER	
		
		// -------------------------------
		// DIRECTION S1
		// -------------------------------
		
		// LAST 7 DAYS AND HOUR
		
		sat.setAutos7days1hS1(0);
		sat.setCom7days1hS1(0);
		sat.setMoto7days1hS1(0);
		sat.setTotal7days1hS1(0);
		
		// CURRENT LAST HOUR
		
		sat.setAutosCurrent1hS1(0);
		sat.setComCurrent1hS1(0);
		sat.setMotoCurrent1hS1(0);
		sat.setTotalCurrent1hS1(0);
											
		// CURRENT STATE
							
		sat.setAutosVolumeS1(0);
		sat.setComVolumeS1(0);
		sat.setMotoVolumeS1(0);
		sat.setTotalVolumeS1(0);
		sat.setAutosVelMedS1(0);
		sat.setComVelMedS1(0);
		sat.setMotoVelMedS1(0);
		sat.setTotalVelMedS1(0);					
		sat.setOccupancyRateS1(0.00);
		
	    // PROJECTION
		
		sat.setAutosProjection1hS1(0);
		sat.setComProjection1hS1(0);
		sat.setMotoProjection1hS1(0);
		sat.setTotalProjection1hS1(0);
		
		// -------------------------------
		// DIRECTION S2
		// -------------------------------
		
		// LAST 7 DAYS AND HOUR
		
		sat.setAutos7days1hS2(0);
		sat.setCom7days1hS2(0);
		sat.setMoto7days1hS2(0);
		sat.setTotal7days1hS2(0);
		
		// CURRENT LAST HOUR
		
		sat.setAutosCurrent1hS2(0);
		sat.setComCurrent1hS2(0);
		sat.setMotoCurrent1hS2(0);
		sat.setTotalCurrent1hS2(0);
																	
		sat.setAutosVolumeS2(0);
		sat.setComVolumeS2(0);
		sat.setMotoVolumeS2(0);
		sat.setTotalVolumeS2(0);
		sat.setAutosVelMedS2(0);
		sat.setComVelMedS2(0);
		sat.setMotoVelMedS2(0);
		sat.setTotalVelMedS2(0);
		sat.setOccupancyRateS2(0.00);
		
	    // PROJECTION 
		
		sat.setAutosProjection1hS2(0);
		sat.setComProjection1hS2(0);
		sat.setMotoProjection1hS2(0);
		sat.setTotalProjection1hS2(0);
																												
		list.add(sat);
		
		}
		
		return list;		
	}
	
	// ----------------------------------------------------------------------------------------------------

}
