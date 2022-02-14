package br.com.tracevia.webapp.controller.sat;

import java.util.ArrayList;
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

								satListObj.setLastRegister(satListValuesAux.get(r).getLastRegister());
								satListObj.setQuantidadeS1(satListValuesAux.get(r).getQuantidadeS1());
								satListObj.setQuantidadeS2(satListValuesAux.get(r).getQuantidadeS2());
								satListObj.setVelocidadeS1(satListValuesAux.get(r).getVelocidadeS1());
								satListObj.setVelocidadeS2(satListValuesAux.get(r).getVelocidadeS2());
								satListObj.setStatusInterval(15);

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
							satListObj = dao.dataIntervalSingle(equips.getSatList().get(s).getEquip_id(),
									"MINUTE", 30);
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

										satListObj1.setEquip_id(equips.getSatList().get(s).getEquip_id());
										satListObj1.setLastPackage(lastRegisters(equips.getSatList().get(s).getEquip_id())[0]);
										satListObj1.setLastRegister(lastRegisters(equips.getSatList().get(s).getEquip_id())[1]);								
										satListObj1.setQuantidadeS1(0);
										satListObj1.setQuantidadeS2(0);
										satListObj1.setVelocidadeS1(0);
										satListObj1.setVelocidadeS2(0);
										satListObj1.setStatusInterval(0);

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
							pass = true; // VERIFICA SE HÃ� DADOS NA COMPARATIVO ENTRE LISTAS

							// LISTA DE SATS COM DADOS DISPONIVEIS
							for (int r = 0; r < satListValuesAux.size(); r++) {

								// COMPARA IDS ENTRE AS LISTAS
								if (satListValuesAux.get(r).getEquip_id() == equips.getSatList().get(s).getEquip_id()) {

									satListObj.setLastRegister(satListValuesAux.get(r).getLastRegister());
									satListObj.setQuantidadeS1(satListValuesAux.get(r).getQuantidadeS1());
									satListObj.setQuantidadeS2(satListValuesAux.get(r).getQuantidadeS2());
									satListObj.setVelocidadeS1(satListValuesAux.get(r).getVelocidadeS1());
									satListObj.setVelocidadeS2(satListValuesAux.get(r).getVelocidadeS2());
									satListObj.setStatusInterval(30);

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
								satListObj = dao.dataIntervalSingle(equips.getSatList().get(s).getEquip_id(),
									"HOUR", 3);
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

										satListObj1.setEquip_id(equips.getSatList().get(s).getEquip_id());
										satListObj1.setLastPackage(lastRegisters(equips.getSatList().get(s).getEquip_id())[0]);
										satListObj1.setLastRegister(lastRegisters(equips.getSatList().get(s).getEquip_id())[1]);									
										satListObj1.setQuantidadeS1(0);
										satListObj1.setQuantidadeS2(0);
										satListObj1.setVelocidadeS1(0);
										satListObj1.setVelocidadeS2(0);
										satListObj1.setStatusInterval(0);

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

										satListObj.setLastRegister(satListValuesAux.get(r).getLastRegister());
										satListObj.setQuantidadeS1(satListValuesAux.get(r).getQuantidadeS1());
										satListObj.setQuantidadeS2(satListValuesAux.get(r).getQuantidadeS2());
										satListObj.setVelocidadeS1(satListValuesAux.get(r).getVelocidadeS1());
										satListObj.setVelocidadeS2(satListValuesAux.get(r).getVelocidadeS2());
										satListObj.setStatusInterval(3);

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

										satListObj1.setEquip_id(equips.getSatList().get(s).getEquip_id());
										satListObj1.setLastPackage(lastRegisters(equips.getSatList().get(s).getEquip_id())[0]);
										satListObj1.setLastRegister(lastRegisters(equips.getSatList().get(s).getEquip_id())[1]);										
										satListObj1.setQuantidadeS1(0);
										satListObj1.setQuantidadeS2(0);
										satListObj1.setVelocidadeS1(0);
										satListObj1.setVelocidadeS2(0);
										satListObj1.setStatusInterval(0);

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

											satListObj.setLastRegister(satListValuesAux.get(r).getLastRegister());
											satListObj.setQuantidadeS1(satListValuesAux.get(r).getQuantidadeS1());
											satListObj.setQuantidadeS2(satListValuesAux.get(r).getQuantidadeS2());
											satListObj.setVelocidadeS1(satListValuesAux.get(r).getVelocidadeS1());
											satListObj.setVelocidadeS2(satListValuesAux.get(r).getVelocidadeS2());
											satListObj.setStatusInterval(6);

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

										satListObj1.setEquip_id(equips.getSatList().get(s).getEquip_id());
										satListObj1.setLastPackage(lastRegisters(equips.getSatList().get(s).getEquip_id())[0]);
										satListObj1.setLastRegister(lastRegisters(equips.getSatList().get(s).getEquip_id())[1]);										
										satListObj1.setQuantidadeS1(0);
										satListObj1.setQuantidadeS2(0);
										satListObj1.setVelocidadeS1(0);
										satListObj1.setVelocidadeS2(0);
										satListObj1.setStatusInterval(0);

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

		for (int i = 0; i < satList.size(); i++) {

			SAT sat = new SAT();

			sat.setEquip_id(satList.get(i).getEquip_id());	
			sat.setLastPackage(lastRegisters(satList.get(i).getEquip_id())[0]);		
			sat.setLastRegister(lastRegisters(satList.get(i).getEquip_id())[1]);		
			sat.setQuantidadeS1(0);
			sat.setVelocidadeS1(0);
			sat.setQuantidadeS2(0);
			sat.setVelocidadeS2(0);
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
			
			// CHECK IF EXIST DATA
			for (int i=0; i<result.length; i++) {
				  if (result[i] == null) 
					  result[i] = "00:00";				  
			}
			
		 // ----------------------------------------------
											
		} catch (Exception e) {

			e.printStackTrace();
		}

		return result;

	}

	// ----------------------------------------------------------------------------------------------------

}
