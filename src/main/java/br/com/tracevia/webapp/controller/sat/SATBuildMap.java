package br.com.tracevia.webapp.controller.sat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.tracevia.webapp.cfg.NotificationType;
import br.com.tracevia.webapp.controller.global.ListEquipments;
import br.com.tracevia.webapp.controller.global.NotificationsBean;
import br.com.tracevia.webapp.dao.sat.DataSatDAO;
import br.com.tracevia.webapp.model.sat.SAT;

@ManagedBean(name = "satMapsView")
@ViewScoped
public class SATBuildMap implements Serializable {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 2747230733734887530L;
	
	List<SAT> satListValues;	
	List<Integer> availabilityList; 
	List<Integer> unavailabilityList; 
	
	NotificationsBean not;
	
	DataSatDAO dao;
		
	@ManagedProperty("#{listEquipsBean}")
	ListEquipments equips;
				
	public void setEquips(ListEquipments equips) {
		this.equips = equips;
	}	
			
	public ListEquipments getEquips() {
		return equips;
	}

	public List<SAT> getSatListValues() {
		return satListValues;
	}
	
	public List<Integer> getAvailabilityList() {
		return availabilityList;
	}

	public List<Integer> getUnavailabilityList() {
		return unavailabilityList;
	}
	
	@PostConstruct
	public void init() {
		
		not = new NotificationsBean(); // INSTANCE NOTIFICATIONS
		
		satListValues = BuildSAT();	// BUILD SAT VALUES
		
		try {
			
			not.updateOnlineStatus(satListValues, availabilityList, unavailabilityList, NotificationType.SAT.getType()); // UPDATE NOTIFICATIONS
			not.count(); // UPDATES NUMBER
			not.notifications(); // UPDATE NOTIFICATIONS LIST
			
		} catch (Exception e) {			
			e.printStackTrace();
		}			   				
	}

	// ----------------------------------------------------------------------------------------------------------------

	/**
	 * Método de carregamento das informações dos SATs.
	 * @author Wellington 26/05/2022
	 * @version 1.0
	 * @since 1.0
	 * @return lista de objetos com informações dos equipamentos.
	 */

	public List<SAT> BuildSAT() {

		dao = new DataSatDAO(); // GLOBAL SAT DATA OBJECT ACCESS
			
		List<SAT> satListValuesAux = new ArrayList<SAT>();	
		
		// LISTAS
		availabilityList = new ArrayList<Integer>();
		unavailabilityList = new ArrayList<Integer>();
		
		try {

			try {
				
				// LIMIT SEARCH				
				int limit = equips.getSatList().size();								
					
				// LISTAR AUXILIARES											
				List<SAT> data15MinList = new ArrayList<SAT>();
				List<SAT> data30MinList = new ArrayList<SAT>();
				List<SAT> data03HourList = new ArrayList<SAT>();
				List<SAT> data06HourList = new ArrayList<SAT>();
				List<SAT> noDataList = new ArrayList<SAT>();			
				List<Integer> equipIdList = new ArrayList<Integer>();			
				List<Integer> availableAuxList = new ArrayList<Integer>();
				List<Integer> unavailableAuxList = new ArrayList<Integer>();
										
				// INIT SAT LIST IDS
				equips.getSatList().forEach(item -> equipIdList.add(item.getEquip_id()));

				//---------------------------------------------------------------

				// TEMPORARY TABLES SQL
											
				dao.temporaryEquip(); // CALL TEMP EQUIP DIRECTIONS
				
				dao.temporaryMaxDate(); // CALL TEMP MAX DATE VBV
				
				dao.temporaryLastHourInterval(); // CALL LAST HOUR INTERVAL
				
				dao.temporaryLast7DaysInterval(); // CALL LAST 7 DAYS INTERVAL 
				
				dao.temporaryLast7Days(); // CALL LAST 7 DAYS DATA
				
				dao.temporaryLastHour(); // CALL LAST HOUR DATA
				
				// TEMPORARY TABLES SQL

				//---------------------------------------------------------------
			
				 data15MinList = dao.dataInterval(limit, "MINUTE", 15, availabilityList);
				 
					  if(!data15MinList.isEmpty()) {

							data15MinList.forEach(item -> availabilityList.add(item.getEquip_id()));

							satListValuesAux.addAll(data15MinList);

							limit = limit - data15MinList.size();

						}
					
				// -----------------------------------------------------------------------
					
				if(limit > 0) {

					 data30MinList = dao.dataInterval(limit, "MINUTE", 30, availabilityList);

							if(!data30MinList.isEmpty()) {

								if(!data15MinList.isEmpty()) {

									for(SAT value : data30MinList) {

											availabilityList.add(value.getEquip_id());
											satListValuesAux.add(value);
									 }

										limit = limit - data30MinList.size();

								} else {

										data30MinList.forEach(item -> availabilityList.add(item.getEquip_id()));
										satListValuesAux.addAll(data30MinList);

										limit = limit - data30MinList.size();

									}
							}
				}

				// -----------------------------------------------------------------------

				if(limit > 0) {
								
					 data03HourList = dao.dataInterval(limit, "HOUR", 3, availabilityList);
								 										
							if(!data03HourList.isEmpty()) {

								if(!data30MinList.isEmpty()) {

									for(SAT value : data03HourList) {

											availabilityList.add(value.getEquip_id());

											satListValuesAux.add(value);

									 }

										limit = limit - data03HourList.size();

								} else {

										data03HourList.forEach(item -> availabilityList.add(item.getEquip_id()));
										satListValuesAux.addAll(data03HourList);

										limit = limit - data03HourList.size();

									}
							}
				}
							
				// -----------------------------------------------------------------------
							
				if(limit > 0) {
																
					 data06HourList = dao.dataInterval(limit, "HOUR", 6, availabilityList);
								 																 																
							if(!data06HourList.isEmpty()) {

								if(!data03HourList.isEmpty()) {

									for(SAT value : data06HourList) {

											availabilityList.add(value.getEquip_id());
											satListValuesAux.add(value);
									 }

										limit = limit - data06HourList.size();

								} else {

										data06HourList.forEach(item -> availabilityList.add(item.getEquip_id()));
										satListValuesAux.addAll(data06HourList);

										limit = limit - data06HourList.size();

								}
							}
				}

				// -----------------------------------------------------------------------

				if(limit > 0 && !availabilityList.isEmpty()) { // CASO NÃO EXISTA NAS VALORES

						noDataList = dao.noDataInterval(limit, availabilityList, false);
								
							if(!noDataList.isEmpty()) {

									noDataList.forEach(item -> unavailabilityList.add(item.getEquip_id())); // ADD NO VALUES TO AUX LIST
									satListValuesAux.addAll(noDataList); // ADD DATA TO A LIST

									limit =  limit - noDataList.size(); // LIMIT

								if(limit > 0) {	// CASO O LIMITE AINDA SEJA MAIOR QUE ZERO ENTRA NA PROXIMA CONDICAO

									availabilityList.forEach(item -> availableAuxList.add(item));
									unavailabilityList.forEach(item -> availableAuxList.add(item));

									for(int id : equipIdList) {
										if(!availableAuxList.contains(id))
											 unavailableAuxList.add(id);

									 }

									satListValuesAux.addAll(completeAllEquips(unavailableAuxList));	// COMPLETA OS EQUIPAMENTOS

									limit = 0;

								}

							} else { // CASO NAO EXISTA VALORES NA LISTA ENTRA AQUI

								for(int id : equipIdList) {
									if(!availabilityList.contains(id))
										unavailabilityList.add(id);
								}

								satListValuesAux.addAll(completeAllEquips(unavailabilityList));

								limit = 0;
							}

				} else if(availabilityList.isEmpty())
				{
						satListValuesAux = intializeNullList(limit, equipIdList);

				}
							
						// ----------------------------------------------------------------------- 
													
						Collections.sort(satListValuesAux); // ORDER SAT LIST
						
						// -----------------------------------------------------------------------												
										
				} catch (IndexOutOfBoundsException ex) {

					ex.printStackTrace();
				}

				} catch (Exception ex) {
					ex.printStackTrace();
				}

				FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":navbarDropdown2");

				dao.connectionClose(); // CLOSE CONNECTION

				return satListValuesAux;
	
	}

	// ----------------------------------------------------------------------------------------------------------------

	/**
	 * Método para inicializar equipamentos sem dados.
	 * @author Wellington 26/05/2022
	 * @version 1.0
	 * @since 1.0
	 * @param limit número de equipamentos disponíveis
	 * @param equipIdList lista de ids dos equipamentos
	 * @throws Exception
	 * @return lista de objetos com informações dos equipamentos.
	 * */

	public List<SAT> intializeNullList(int limit, List<Integer> equipIdList) throws Exception {
				
		// LISTS
		unavailabilityList = new ArrayList<Integer>();
		
		// AUX LISTS
		List<SAT> satListValuesAux = new ArrayList<SAT>();
		List<SAT> noDataList = new ArrayList<SAT>();	
		List<Integer> noDataAuxList = new ArrayList<Integer>();	
	
		noDataList = dao.noDataInterval(limit, equipIdList, true);
		
		if(!noDataList.isEmpty()) {
			
			// COMPARE IF EXIST VALUES IN DATABASE								
			noDataList.forEach(item -> unavailabilityList.add(item.getEquip_id())); // ADD NO VALUES TO AUX LIST																								
			satListValuesAux.addAll(noDataList); // ADD DATA TO A LIST
																										
			limit =  limit - noDataList.size(); // LIMIT
						
			if(limit > 0) {
				
				for(int id : equipIdList) {			
					if(!unavailabilityList.contains(id))
						 noDataAuxList.add(id);					
				}
				
			  satListValuesAux.addAll(completeAllEquips(noDataAuxList));
			  
			  limit = 0;
												
			}
												
		} else {
			
			  satListValuesAux.addAll(completeAllEquips(equipIdList));
			  
			  limit = 0;
			  			
		}
						
		return satListValuesAux;
	}

	// ----------------------------------------------------------------------------------------------------------------

	/**
	 * Método auxiliar para completar informações de equipamentos sem dados.
	 * @author Wellington 26/05/2022
	 * @version 1.0
	 * @since 1.0
	 * @param equips lista de ids dos equipamentos
	 * @throws Exception
	 * @return lista de objetos com informações dos equipamentos.
	 * */
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

	// ----------------------------------------------------------------------------------------------------------------
	
}
