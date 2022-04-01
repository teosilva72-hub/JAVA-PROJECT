package br.com.tracevia.webapp.controller.sat;

import java.util.ArrayList;
import java.util.Calendar;
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
import br.com.tracevia.webapp.model.global.Equipments;
import br.com.tracevia.webapp.model.global.ListEquipments;
import br.com.tracevia.webapp.model.global.NotificationsAlert;
import br.com.tracevia.webapp.model.sat.SAT;

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

	public void BuildSAT() {

		try {

			try {

				DataSatDAO dao = new DataSatDAO();
				NotificationsBean not = new NotificationsBean();
				DateTimeApplication dt = new DateTimeApplication();

				boolean pass = true;

				// LISTAS

				satListValues = new ArrayList<SAT>();
				satStatus = new ArrayList<SAT>();

				// LISTAR AUXILIARES
				List<SAT> satListValuesAux = new ArrayList<SAT>();
				List<NotificationsAlert> listStatus = new ArrayList<NotificationsAlert>();

				///////////////////////////////
				// SAT EQUIPMENTS
				//////////////////////////////

				listStatus = not.notificationStatus(NotificationType.SAT.getType());

				// PREENCHE LISTA COM STATUS DOS ULTIMOS 30 MINUTOS
				// TABELA POSSUI DELAY DE 15 MINUTOS

				satListValuesAux = dao.dataInterval(equips, "MINUTE", 15);
															
				// START CHEKING 15 MIN
				if (!satListValuesAux.isEmpty()) {

					// LISTA COM TODOS SATS
					for (int s = 0; s < equips.getSatList().size(); s++) { // FOR START

						SAT satListObj = new SAT();
						pass = true; // VERIFICA SE HÃ� DADOS NA COMPARATIVO ENTRE LISTAS

						// LISTA DE SATS COM DADOS DISPONIVEIS
						for (int r = 0; r < satListValuesAux.size(); r++) {

							// COMPARA IDS ENTRE AS LISTAS
							if (satListValuesAux.get(r).getEquip_id() == equips.getSatList().get(s).getEquip_id()) {

								satListObj = popDataSat(satListValuesAux, r, 15); // RECEIVE OBJECT
								
								satListValues.add(satListObj);
								satListValuesAux.remove(r);
								pass = false;

								if (!listStatus.get(s).isOnlineStatus())
									not.updateStatus(NotificationsAlarmsEnum.ONLINE.getAlarm(),
											equips.getSatList().get(s).getEquip_id(), NotificationType.SAT.getType(),
											dt.currentDateTime(), true, false);

								break;

							} // IF CONDITION
						} // FOR END

						// CASO NÃO HAJA DADOS ENTRA NESSA CONDIÇÃO
						if (pass) {

							// BUSCA DADOS DOS ULTIMAS 30 MINUTOS
							satListObj = dao.dataIntervalSingle(equips.getSatList().get(s).getEquip_id(),"MINUTE", 30);
							satListObj.setStatusInterval(30);

							// SE HOUVER DADOS PREENCHE NA LISTA
							if (satListObj.getEquip_id() != 0) {
								satListValues.add(satListObj);

								if (!listStatus.get(s).isOnlineStatus())
									not.updateStatus(NotificationsAlarmsEnum.ONLINE.getAlarm(),
											equips.getSatList().get(s).getEquip_id(), NotificationType.SAT.getType(),
											dt.currentDateTime(), true, false);

							} else {

								// BUSCA DADOS DAS ULTIMAS 03 HORAS
								satListObj = dao.dataIntervalSingle(equips.getSatList().get(s).getEquip_id(), "HOUR", 3);
								satListObj.setStatusInterval(3);

								// SE HOUVER DADOS PREENCHE NA LISTA
								if (satListObj.getEquip_id() != 0) {
									satListValues.add(satListObj);

									if (!listStatus.get(s).isOnlineStatus())
										not.updateStatus(NotificationsAlarmsEnum.ONLINE.getAlarm(),
												equips.getSatList().get(s).getEquip_id(),
												NotificationType.SAT.getType(), dt.currentDateTime(), true, false);

								} else {

									// BUSCA DADOS DAS ULTIMAS 06 HORAS
									satListObj = dao.dataIntervalSingle(equips.getSatList().get(s).getEquip_id(),"HOUR", 6);
									satListObj.setStatusInterval(6);

									// SE HOUVER DADOS PREENCHE NA LISTA
									if (satListObj.getEquip_id() != 0) {
										satListValues.add(satListObj);

										if (!listStatus.get(s).isOnlineStatus())
											not.updateStatus(NotificationsAlarmsEnum.ONLINE.getAlarm(),
													equips.getSatList().get(s).getEquip_id(),
													NotificationType.SAT.getType(), dt.currentDateTime(), true, false);

									} else {

										SAT satListObj1 = new SAT(); 
										
										satListObj = popNoDataSat(equips.getSatList().get(s).getEquip_id()); // RECEIVE OBJECT
																		
										satListValues.add(satListObj1);

										if (listStatus.get(s).isOnlineStatus())
											not.updateStatus(NotificationsAlarmsEnum.OFFLINE.getAlarm(),
													equips.getSatList().get(s).getEquip_id(),
													NotificationType.SAT.getType(), dt.currentDateTime(), false, true);

									} // ELSE
								} // ELSE
							} // ELSE
						} // PASS
					} // FOR END

					// --------------------------------------------------------------------------------------------------------------------------------------------

				} else {

					// START CHEKING 30 MIN
					satListValuesAux = dao.dataInterval(equips, "MINUTE", 30);

					if (!satListValuesAux.isEmpty()) {

						// LISTA COM TODOS SATS
						for (int s = 0; s < equips.getSatList().size(); s++) { // FOR START

							SAT satListObj = new SAT();
							pass = true; // VERIFICA SE HÁ DADOS NA COMPARATIVO ENTRE LISTAS

							// LISTA DE SATS COM DADOS DISPONIVEIS
							for (int r = 0; r < satListValuesAux.size(); r++) {

								// COMPARA IDS ENTRE AS LISTAS
								if (satListValuesAux.get(r).getEquip_id() == equips.getSatList().get(s).getEquip_id()) {

									satListObj = popDataSat(satListValuesAux, r, 30); // RECEIVE OBJECT																							
								
									satListValues.add(satListObj);
									satListValuesAux.remove(r);
									pass = false;

									if (!listStatus.get(s).isOnlineStatus())
										not.updateStatus(NotificationsAlarmsEnum.ONLINE.getAlarm(),
												equips.getSatList().get(s).getEquip_id(),
												NotificationType.SAT.getType(), dt.currentDateTime(), true, false);

									break;

								}
							}

							// CASO NÃO HAJA DADOS ENTRA NESSA CONDIÇÃO

							if (pass) {

								// BUSCA DADOS DAS ULTIMAS 03 HORAS
								satListObj = dao.dataIntervalSingle(equips.getSatList().get(s).getEquip_id(),"HOUR", 3);
								satListObj.setStatusInterval(3);

								// SE HOUVER DADOS PREENCHE NA LISTA
								if (satListObj.getEquip_id() != 0) {
									satListValues.add(satListObj);

									if (!listStatus.get(s).isOnlineStatus())
										not.updateStatus(NotificationsAlarmsEnum.ONLINE.getAlarm(),
												equips.getSatList().get(s).getEquip_id(),
												NotificationType.SAT.getType(), dt.currentDateTime(), true, false);

								} else {

									// BUSCA DADOS DAS ULTIMAS 06 HORAS
									satListObj = dao.dataIntervalSingle(equips.getSatList().get(s).getEquip_id(),
										"HOUR", 6);
									satListObj.setStatusInterval(6);
									
									// SE HOUVER DADOS PREENCHE NA LISTA
									if (satListObj.getEquip_id() != 0) {
										satListValues.add(satListObj);

										if (!listStatus.get(s).isOnlineStatus())
											not.updateStatus(NotificationsAlarmsEnum.ONLINE.getAlarm(),
													equips.getSatList().get(s).getEquip_id(),
													NotificationType.SAT.getType(), dt.currentDateTime(), true, false);

									} else {

										SAT satListObj1 = new SAT();
									
										satListObj = popNoDataSat(equips.getSatList().get(s).getEquip_id()); // RECEIVE OBJECT

										satListValues.add(satListObj1);

										if (listStatus.get(s).isOnlineStatus())
											not.updateStatus(NotificationsAlarmsEnum.OFFLINE.getAlarm(),
													equips.getSatList().get(s).getEquip_id(),
													NotificationType.SAT.getType(), dt.currentDateTime(), false, true);

									} // ELSE
								} // ELSE
							} // PASS
						} // FOR END

					} else {

						// START CHEKING 03 HOUR
						satListValuesAux = dao.dataInterval(equips, "HOUR", 3);

						if (!satListValuesAux.isEmpty()) {

							// LISTA COM TODOS SATS
							for (int s = 0; s < equips.getSatList().size(); s++) { // FOR START

								SAT satListObj = new SAT();
								pass = true; // VERIFICA SE HÃ� DADOS NA COMPARATIVO ENTRE LISTAS

								// LISTA DE SATS COM DADOS DISPONIVEIS
								for (int r = 0; r < satListValuesAux.size(); r++) {

									// COMPARA IDS ENTRE AS LISTAS
									if (satListValuesAux.get(r).getEquip_id() == equips.getSatList().get(s)
											.getEquip_id()) {

										satListObj = popDataSat(satListValuesAux, r, 3); // RECEIVE OBJECT	

										satListValues.add(satListObj);
										satListValuesAux.remove(r);
										pass = false;

										if (!listStatus.get(s).isOnlineStatus())
											not.updateStatus(NotificationsAlarmsEnum.ONLINE.getAlarm(),
													equips.getSatList().get(s).getEquip_id(),
													NotificationType.SAT.getType(), dt.currentDateTime(), true, false);

										break;

									}
								}

								// CASO NÃO HAJA DADOS ENTRA NESSA CONDIÇÃO

								if (pass) {

									// BUSCA DADOS DAS ULTIMAS 06 HORAS
									satListObj = dao.dataIntervalSingle(equips.getSatList().get(s).getEquip_id(),"HOUR", 6);
									satListObj.setStatusInterval(6);

									// SE HOUVER DADOS PREENCHE NA LISTA
									if (satListObj.getEquip_id() != 0) {
										satListValues.add(satListObj);

										if (!listStatus.get(s).isOnlineStatus())
											not.updateStatus(NotificationsAlarmsEnum.ONLINE.getAlarm(),
													equips.getSatList().get(s).getEquip_id(),
													NotificationType.SAT.getType(), dt.currentDateTime(), true, false);

									} else {

										SAT satListObj1 = new SAT();

										satListObj = popNoDataSat(equips.getSatList().get(s).getEquip_id()); // RECEIVE OBJECT
										
										System.out.println("PCKG: "+satListObj.getLastPackage());
										
										satListValues.add(satListObj1);

										if (listStatus.get(s).isOnlineStatus())
											not.updateStatus(NotificationsAlarmsEnum.OFFLINE.getAlarm(),
													equips.getSatList().get(s).getEquip_id(),
													NotificationType.SAT.getType(), dt.currentDateTime(), false, true);

									} // ELSE
								} // ELSE
							} // FOR END

						} else {

							// START CHEKING 06 HOUR
							satListValuesAux = dao.dataInterval(equips, "HOUR", 6);

							if (!satListValuesAux.isEmpty()) {

								// LISTA COM TODOS SATS
								for (int s = 0; s < equips.getSatList().size(); s++) { // FOR START

									SAT satListObj = new SAT();
									pass = true; // VERIFICA SE HÃ� DADOS NA COMPARATIVO ENTRE LISTAS

									// LISTA DE SATS COM DADOS DISPONIVEIS
									for (int r = 0; r < satListValuesAux.size(); r++) {

										// COMPARA IDS ENTRE AS LISTAS
										if (satListValuesAux.get(r).getEquip_id() == equips.getSatList().get(s)
												.getEquip_id()) {

											satListObj = popDataSat(satListValuesAux, r, 6); // RECEIVE OBJECT	

											satListValues.add(satListObj);
											satListValuesAux.remove(r);
											pass = false;

											if (!listStatus.get(s).isOnlineStatus())
												not.updateStatus(NotificationsAlarmsEnum.ONLINE.getAlarm(),
														equips.getSatList().get(s).getEquip_id(),
														NotificationType.SAT.getType(), dt.currentDateTime(), true,
														false);

											break;
										}
									}

									// CASO NÃO HAJA DADOS ENTRA NESSA CONDIÇÃO

									if (pass) {

										SAT satListObj1 = new SAT();

										satListObj = popNoDataSat(equips.getSatList().get(s).getEquip_id()); // RECEIVE OBJECT
										satListValues.add(satListObj1);

										if (listStatus.get(s).isOnlineStatus())
											not.updateStatus(NotificationsAlarmsEnum.OFFLINE.getAlarm(),
													equips.getSatList().get(s).getEquip_id(),
													NotificationType.SAT.getType(), dt.currentDateTime(), false, true);

									} // PASS END
								} // FOR END

							} else
								intializeNullList(equips.getSatList()); // CASO NÃO EXISTA VALORES VAI INICIALIZAR COM
																		// ZEROS TODOS EQUIPAMENTOS

						} // ELSE END
					} // ELSE END
				} // FIRST ELSE CONDITION (30 MIN)

			} catch (IndexOutOfBoundsException ex) {

				ex.printStackTrace();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":navbarDropdown2");

	}

	// ----------------------------------------------------------------------------------------------------

	public void intializeNullList(List<? extends Equipments> satList) throws Exception {

		DateTimeApplication dt = new DateTimeApplication();
		NotificationsBean not = new NotificationsBean();
			
		String current = dt.showInterval15Min();
		String lastSevenDays = dt.showIntervalHour(true, false);
		String lastHour = dt.showIntervalHour(false, true);
		String projection = dt.showIntervalHour(false, false);
		
		for (int i = 0; i < satList.size(); i++) {

			SAT sat = new SAT();
			
			// PRIMARY INFO
			
			sat.setEquip_id(satList.get(i).getEquip_id());	
			sat.setLastPackage(lastRegisters(satList.get(i).getEquip_id())[0]);		
			sat.setLastRegister(lastRegisters(satList.get(i).getEquip_id())[1]);		
			sat.setQuantidadeS1(0);
			sat.setVelocidadeS1(0);
			sat.setQuantidadeS2(0);
			sat.setVelocidadeS2(0);
			sat.setStatusInterval(0);									
			
			// -------------------------------
			// TABLE INFO
			// -------------------------------
			
			// TABLE HEADERS 
			
			sat.setCurrentDatetime(current);
			sat.setSevenDaysDatetime(lastSevenDays);
			sat.setLastOneDatetime(lastHour);
			sat.setProjectionDatetime(projection);																														
		
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
			
			// PROJECTION 
			
			sat.setAutosProjection1hS1(0);
			sat.setComProjection1hS1(0);
			sat.setMotoProjection1hS1(0);
			sat.setTotalProjection1hS1(0);
			
			// CURRENT STATE
								
			sat.setAutosVolumeS1(0);
			sat.setComVolumeS1(0);
			sat.setMotoVolumeS1(0);
			sat.setTotalVolumeS1(0);
			sat.setAutosVelMedS1(0);
			sat.setComVelMedS1(0);
			sat.setMotoVelMedS1(0);
			sat.setTotalVelMedS1(0);					
			sat.setOccupancyRateS1(0);
			
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
			
			// PROJECTION 
			
			sat.setAutosProjection1hS2(0);
			sat.setComProjection1hS2(0);
			sat.setMotoProjection1hS2(0);
			sat.setTotalProjection1hS2(0);
			
			// CURRENT STATE
													
			sat.setAutosVolumeS2(0);
			sat.setComVolumeS2(0);
			sat.setMotoVolumeS2(0);
			sat.setTotalVolumeS2(0);
			sat.setAutosVelMedS2(0);
			sat.setComVelMedS2(0);
			sat.setMotoVelMedS2(0);
			sat.setTotalVelMedS2(0);
			sat.setOccupancyRateS2(0.00);	
			
			// STATUS INTERVAL 
			
			sat.setStatusInterval(0);

			satListValues.add(sat);

			not.updateStatus(NotificationsAlarmsEnum.OFFLINE.getAlarm(), satList.get(i).getEquip_id(),
					NotificationType.SAT.getType(), dt.currentDateTime(), false, true);

		}
	}

	// ----------------------------------------------------------------------------------------------------

	public String[] lastRegisters(int equipId) {

		DataSatDAO dao = new DataSatDAO();
		String[] result = new String[2];

		try {

			result = dao.lastRegisters(equipId);			
						
		 // ----------------------------------------------
											
		} catch (Exception e) {

			e.printStackTrace();
		}

		return result;

	}

	// ----------------------------------------------------------------------------------------------------
	
	public SAT popDataSat(List<SAT> mySatList, int index, int statusInteindexval) {
				
		SAT sat = new SAT();
		
		// PRIMARY INFO
					
		sat.setLastPackage(mySatList.get(index).getLastPackage() != null ?  mySatList.get(index).getLastPackage() : "00:00");
		sat.setLastRegister(mySatList.get(index).getLastRegister() != null ? mySatList.get(index).getLastRegister() : "00:00");
		sat.setQuantidadeS1(mySatList.get(index).getQuantidadeS1());
		sat.setQuantidadeS2(mySatList.get(index).getQuantidadeS2());
		sat.setVelocidadeS1(mySatList.get(index).getVelocidadeS1());
		sat.setVelocidadeS2(mySatList.get(index).getVelocidadeS2());
		
		// -------------------------------
		// TABLE INFO
		// -------------------------------
		
		// TABLE HEADERS 
		
		sat.setCurrentDatetime(mySatList.get(index).getCurrentDatetime());
		sat.setSevenDaysDatetime(mySatList.get(index).getSevenDaysDatetime());
		sat.setLastOneDatetime(mySatList.get(index).getLastOneDatetime());
		sat.setProjectionDatetime(mySatList.get(index).getProjectionDatetime());																														
	
		// -------------------------------
		// DIRECTION S1
		// -------------------------------
		
		// LAST 7 DAYS AND HOUR
		
		sat.setAutos7days1hS1(mySatList.get(index).getAutos7days1hS1());
		sat.setCom7days1hS1(mySatList.get(index).getCom7days1hS1());
		sat.setMoto7days1hS1(mySatList.get(index).getMoto7days1hS1());
		sat.setTotal7days1hS1(mySatList.get(index).getTotal7days1hS1());
		
		// CURRENT LAST HOUR
		
		sat.setAutosCurrent1hS1(mySatList.get(index).getAutosCurrent1hS1());
		sat.setComCurrent1hS1(mySatList.get(index).getComCurrent1hS1());
		sat.setMotoCurrent1hS1(mySatList.get(index).getMotoCurrent1hS1());
		sat.setTotalCurrent1hS1(mySatList.get(index).getTotalCurrent1hS1());
		
		// PROJECTION 
		
		sat.setAutosProjection1hS1(mySatList.get(index).getAutosProjection1hS1());
		sat.setComProjection1hS1(mySatList.get(index).getComProjection1hS1());
		sat.setMotoProjection1hS1(mySatList.get(index).getMotoProjection1hS1());
		sat.setTotalProjection1hS1(mySatList.get(index).getTotalProjection1hS1());
		
		// CURRENT STATE
							
		sat.setAutosVolumeS1(mySatList.get(index).getAutosVolumeS1());
		sat.setComVolumeS1(mySatList.get(index).getComVolumeS1());
		sat.setMotoVolumeS1(mySatList.get(index).getMotoVolumeS1());
		sat.setTotalVolumeS1(mySatList.get(index).getTotalVolumeS1());
		sat.setAutosVelMedS1(mySatList.get(index).getAutosVelMedS1());
		sat.setComVelMedS1(mySatList.get(index).getComVelMedS1());
		sat.setMotoVelMedS1(mySatList.get(index).getMotoVelMedS1());
		sat.setTotalVelMedS1(mySatList.get(index).getTotalVelMedS1());					
		sat.setOccupancyRateS1(mySatList.get(index).getOccupancyRateS1());
		
		// -------------------------------
		// DIRECTION S2
		// -------------------------------
		
		// LAST 7 DAYS AND HOUR
		
		sat.setAutos7days1hS2(mySatList.get(index).getAutos7days1hS2());
		sat.setCom7days1hS2(mySatList.get(index).getCom7days1hS2());
		sat.setMoto7days1hS2(mySatList.get(index).getMoto7days1hS2());
		sat.setTotal7days1hS2(mySatList.get(index).getTotal7days1hS2());
		
		// CURRENT LAST HOUR
		
		sat.setAutosCurrent1hS2(mySatList.get(index).getAutosCurrent1hS2());
		sat.setComCurrent1hS2(mySatList.get(index).getComCurrent1hS2());
		sat.setMotoCurrent1hS2(mySatList.get(index).getMotoCurrent1hS2());
		sat.setTotalCurrent1hS2(mySatList.get(index).getTotalCurrent1hS2());
		
		// PROJECTION 
		
		sat.setAutosProjection1hS2(mySatList.get(index).getAutosProjection1hS2());
		sat.setComProjection1hS2(mySatList.get(index).getComProjection1hS2());
		sat.setMotoProjection1hS2(mySatList.get(index).getMotoProjection1hS2());
		sat.setTotalProjection1hS2(mySatList.get(index).getTotalProjection1hS2());
		
		// CURRENT STATE
												
		sat.setAutosVolumeS2(mySatList.get(index).getAutosVolumeS2());
		sat.setComVolumeS2(mySatList.get(index).getComVolumeS2());
		sat.setMotoVolumeS2(mySatList.get(index).getMotoVolumeS2());
		sat.setTotalVolumeS2(mySatList.get(index).getTotalVolumeS2());
		sat.setAutosVelMedS2(mySatList.get(index).getAutosVelMedS2());
		sat.setComVelMedS2(mySatList.get(index).getComVelMedS2());
		sat.setMotoVelMedS2(mySatList.get(index).getMotoVelMedS2());
		sat.setTotalVelMedS2(mySatList.get(index).getTotalVelMedS2());
		sat.setOccupancyRateS2(mySatList.get(index).getOccupancyRateS2());	
		
		// STATUS INTERVAL 
		
		sat.setStatusInterval(statusInteindexval);
					
		return sat; // ADD TO MAIN LIST
									
	}
	
	// ----------------------------------------------------------------------------------------------------
	
	public SAT popNoDataSat(int equipId) {
		
		DateTimeApplication dt = new DateTimeApplication();
		
		String current = dt.showInterval15Min();
		String lastSevenDays = dt.showIntervalHour(true, false);
		String lastHour = dt.showIntervalHour(false, true);
		String projection = dt.showIntervalHour(false, false);
					
		SAT sat = new SAT();
				
		sat.setEquip_id(equipId);	
		sat.setLastPackage("RUIZ"); 		
	//	sat.setLastRegister("00:00");		
		sat.setQuantidadeS1(0);
		sat.setVelocidadeS1(0);
		sat.setQuantidadeS2(0);
		sat.setVelocidadeS2(0);
		
		// PRIMARY INFO
					
		// -------------------------------
		// TABLE INFO
		// -------------------------------
		
		// TABLE HEADERS 
		
		sat.setCurrentDatetime(current);
		sat.setSevenDaysDatetime(lastSevenDays);
		sat.setLastOneDatetime(lastHour);
		sat.setProjectionDatetime(projection);																														
	
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
		
		// PROJECTION 
		
		sat.setAutosProjection1hS1(0);
		sat.setComProjection1hS1(0);
		sat.setMotoProjection1hS1(0);
		sat.setTotalProjection1hS1(0);
		
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
		
		// PROJECTION 
		
		sat.setAutosProjection1hS2(0);
		sat.setComProjection1hS2(0);
		sat.setMotoProjection1hS2(0);
		sat.setTotalProjection1hS2(0);
		
		// CURRENT STATE
												
		sat.setAutosVolumeS2(0);
		sat.setComVolumeS2(0);
		sat.setMotoVolumeS2(0);
		sat.setTotalVolumeS2(0);
		sat.setAutosVelMedS2(0);
		sat.setComVelMedS2(0);
		sat.setMotoVelMedS2(0);
		sat.setTotalVelMedS2(0);
		sat.setOccupancyRateS2(0.00);	
		
		// STATUS INTERVAL 
		
		sat.setStatusInterval(0);
					
		return sat; // ADD TO MAIN LIST
									
	}
	
	// ----------------------------------------------------------------------------------------------------
}
