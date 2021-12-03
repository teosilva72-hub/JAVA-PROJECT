package br.com.tracevia.webapp.controller.sat;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.tracevia.webapp.cfg.NotificationsAlarmsEnum;
import br.com.tracevia.webapp.cfg.NotificationsTypeEnum;
import br.com.tracevia.webapp.controller.global.NotificationsBean;
import br.com.tracevia.webapp.dao.sat.DataSatDAO;
import br.com.tracevia.webapp.model.global.Equipments;
import br.com.tracevia.webapp.model.global.ListEquipments;
import br.com.tracevia.webapp.model.global.Notifications;
import br.com.tracevia.webapp.model.sat.SAT;

@ManagedBean(name = "satMapsView")
@ViewScoped
public class SATBuildMap {

	List<SAT> satListValues, satStatus;

	@ManagedProperty("#{listEquips}")
	private ListEquipments equips;

	public List<SAT> getSatListValues() {
		return satListValues;
	}

	public List<SAT> getSatStatus() {
		return satStatus;
	}

	public ListEquipments getEquips() {
		return equips;
	}

	public void setEquips(ListEquipments equips) {
		this.equips = equips;
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
				
				boolean pass = true;

				// LISTAS										

				satListValues = new ArrayList<SAT>();
				satStatus = new ArrayList<SAT>();

				// LISTAR AUXILIARES
				List<SAT> satListValuesAux = new ArrayList<SAT>();				
				List<Notifications> listStatus = new ArrayList<Notifications>();
				
				
				///////////////////////////////
				// SAT EQUIPMENTS
				//////////////////////////////
				
				listStatus = not.getNotificationStatus(NotificationsTypeEnum.SAT.getType());		

				// PREENCHE LISTA COM STATUS DOS ULTIMOS 30 MINUTOS
				// TABELA POSSUI DELAY DE 15 MINUTOS
	     
		satListValuesAux = dao.data30min();
		
		// CHECK DATA IN 30 MIN
				
		if(!satListValuesAux.isEmpty()) {

			// LISTA COM TODOS SATS
			for (int s = 0; s < equips.getSatList().size(); s++) { // FOR START

				SAT satListObj = new SAT();
				pass = true; // VERIFICA SE HÃ� DADOS NA COMPARATIVO ENTRE LISTAS

				//LISTA DE SATS COM DADOS DISPONIVEIS
				for (int r = 0; r < satListValuesAux.size(); r++) {
												
					//COMPARA IDS ENTRE AS LISTAS
					if (satListValuesAux.get(r).getEquip_id() == equips.getSatList().get(s).getEquip_id()) {

						satListObj.setQuantidadeS1(satListValuesAux.get(r).getQuantidadeS1());
						satListObj.setQuantidadeS2(satListValuesAux.get(r).getQuantidadeS2());
						satListObj.setVelocidadeS1(satListValuesAux.get(r).getVelocidadeS1());
						satListObj.setVelocidadeS2(satListValuesAux.get(r).getVelocidadeS2());
						satListObj.setStatusInterval(30);

						satListValues.add(satListObj);
						satListValuesAux.remove(r);
						pass = false;
						
						 if(listStatus.get(s).getStatus() == 0)
							 not.updateNotificationStatus(NotificationsAlarmsEnum.ONLINE.getAlarm(), equips.getSatList().get(s).getEquip_id(), NotificationsTypeEnum.SAT.getType());
					

						break;

					}
				}
				 //CASO Nï¿½O HAJA DADOS ENTRA NESSA CONDIï¿½ï¿½O
				if (pass) {
					
					// BUSCA DADOS DOS ULTIMAS 03 HORAS
					satListObj = dao.data03HSingle(equips.getSatList().get(s).getEquip_id());
					satListObj.setStatusInterval(3);
					
					//SE HOUVER DADOS PREENCHE NA LISTA
					if (satListObj.getEquip_id() != 0) {
						satListValues.add(satListObj);
						
					    if(listStatus.get(s).getStatus() == 0)
							 not.updateNotificationStatus(NotificationsAlarmsEnum.ONLINE.getAlarm(), equips.getSatList().get(s).getEquip_id(), NotificationsTypeEnum.SAT.getType());
						
					}
					
					else {
						
						//BUSCA DADOS DAS ULTIMAS 08 HORAS
						satListObj = dao.data06HSingle(equips.getSatList().get(s).getEquip_id());
						satListObj.setStatusInterval(6);
						
						//SE HOUVER DADOS PREENCHE NA LISTA
						if (satListObj.getEquip_id() != 0) {
							satListValues.add(satListObj);
							
						   if(listStatus.get(s).getStatus() == 0)
								not.updateNotificationStatus(NotificationsAlarmsEnum.ONLINE.getAlarm(), equips.getSatList().get(s).getEquip_id(), NotificationsTypeEnum.SAT.getType());
													
						}
						
						//CASO CONTRARIO PREENCHE COM 0
						else {
						
						SAT satListObj1 = new SAT();

						satListObj1.setEquip_id(equips.getSatList().get(s).getEquip_id());
						satListObj1.setQuantidadeS1(0);
						satListObj1.setQuantidadeS2(0);
						satListObj1.setVelocidadeS1(0);
						satListObj1.setVelocidadeS2(0);
						satListObj.setStatusInterval(0);

						satListValues.add(satListObj1);
						
						if(listStatus.get(s).getStatus() == 1)
							not.updateNotificationStatus(NotificationsAlarmsEnum.OFFLINE.getAlarm(), equips.getSatList().get(s).getEquip_id(), NotificationsTypeEnum.SAT.getType());

					  }
				   }
				}
			} // FOR END

		// PASSO 03 HORAS
			
		} else {
			
			satListValuesAux = dao.data03hrs();				
			
			if(!satListValuesAux.isEmpty()) { 

				// LISTA COM TODOS SATS
				for (int s = 0; s < equips.getSatList().size(); s++) { // FOR START

					SAT satListObj = new SAT();
					pass = true; // VERIFICA SE Hï¿½ DADOS NA COMPARAï¿½ï¿½O ENTRE LISTAS

					//LISTA DE SATS COM DADOS DISPONIVEIS
					for (int r = 0; r < satListValuesAux.size(); r++) {

						//COMPARA IDS ENTRE AS LISTAS
						if (satListValuesAux.get(r).getEquip_id() == equips.getSatList().get(s).getEquip_id()) {

							satListObj.setQuantidadeS1(satListValuesAux.get(r).getQuantidadeS1());
							satListObj.setQuantidadeS2(satListValuesAux.get(r).getQuantidadeS2());
							satListObj.setVelocidadeS1(satListValuesAux.get(r).getVelocidadeS1());
							satListObj.setVelocidadeS2(satListValuesAux.get(r).getVelocidadeS2());
							satListObj.setStatusInterval(3);

							satListValues.add(satListObj);
							satListValuesAux.remove(r);
							pass = false; 
							
							 if(listStatus.get(s).getStatus() == 0)
								 not.updateNotificationStatus(NotificationsAlarmsEnum.ONLINE.getAlarm(), equips.getSatList().get(s).getEquip_id(), NotificationsTypeEnum.SAT.getType());
						
							break;

						}
					}

					//CASO NAO HAJA DADOS ENTRA NESSA CONDIÃ‡ÃƒO
					if (pass) {

						//BUSCA DADOS DAS ULTIMAS 08 HORAS
						satListObj = dao.data06HSingle(equips.getSatList().get(s).getEquip_id());
						satListObj.setStatusInterval(6);

						//SE HOUVER DADOS PREENCHE NA LISTA
						if (satListObj.getEquip_id() != 0) {
							satListValues.add(satListObj);
						
							 if(listStatus.get(s).getStatus() == 0)
								 not.updateNotificationStatus(NotificationsAlarmsEnum.ONLINE.getAlarm(), equips.getSatList().get(s).getEquip_id(), NotificationsTypeEnum.SAT.getType());
						
							
						}

								//CASO CONTRARIO PREENCHE COM 0
								else {
	
									satListObj.setEquip_id(equips.getSatList().get(s).getEquip_id());
									satListObj.setQuantidadeS1(0);
									satListObj.setQuantidadeS2(0);
									satListObj.setVelocidadeS1(0);
									satListObj.setVelocidadeS2(0);
									satListObj.setStatusInterval(0);
	
									satListValues.add(satListObj);
									
									 if(listStatus.get(s).getStatus() == 1)
										 not.updateNotificationStatus(NotificationsAlarmsEnum.OFFLINE.getAlarm(), equips.getSatList().get(s).getEquip_id(), NotificationsTypeEnum.SAT.getType());
								
	
								}
							}
				} // FOR END
				
			// PASSO 06 HORAS

			} else {
				
				satListValuesAux = dao.data06hrs();	
				
				if(!satListValuesAux.isEmpty()) {
				
				// LISTA COM TODOS SATS
				for (int s = 0; s < equips.getSatList().size(); s++) { // FOR START

					SAT satListObj = new SAT();
					pass = true; // VERIFICA SE Hï¿½ DADOS NA COMPARAï¿½ï¿½O ENTRE LISTAS

					//LISTA DE SATS COM DADOS DISPONIVEIS
					for (int r = 0; r < satListValuesAux.size(); r++) {

						//COMPARA IDS ENTRE AS LISTAS
						if (satListValuesAux.get(r).getEquip_id() == equips.getSatList().get(s).getEquip_id()) {

							satListObj.setQuantidadeS1(satListValuesAux.get(r).getQuantidadeS1());
							satListObj.setQuantidadeS2(satListValuesAux.get(r).getQuantidadeS2());
							satListObj.setVelocidadeS1(satListValuesAux.get(r).getVelocidadeS1());
							satListObj.setVelocidadeS2(satListValuesAux.get(r).getVelocidadeS2());
							satListObj.setStatusInterval(6);

							satListValues.add(satListObj);
							satListValuesAux.remove(r);
							pass = false; 
							
							 if(listStatus.get(s).getStatus() == 0)
								 not.updateNotificationStatus(NotificationsAlarmsEnum.ONLINE.getAlarm(), equips.getSatList().get(s).getEquip_id(), NotificationsTypeEnum.SAT.getType());
						

							break;

						}
					}

					//CASO NAO HAJA DADOS ENTRA NESSA CONDIï¿½ï¿½O
					if (pass) {

						//CASO CONTRARIO PREENCHE COM 0

						satListObj.setEquip_id(equips.getSatList().get(s).getEquip_id());
						satListObj.setQuantidadeS1(0);
						satListObj.setQuantidadeS2(0);
						satListObj.setVelocidadeS1(0);
						satListObj.setVelocidadeS2(0);
						satListObj.setStatusInterval(0);

						satListValues.add(satListObj);			
						
						 if(listStatus.get(s).getStatus() == 1)
							 not.updateNotificationStatus(NotificationsAlarmsEnum.OFFLINE.getAlarm(), equips.getSatList().get(s).getEquip_id(), NotificationsTypeEnum.SAT.getType());
					

					}
				} // FOR END	
				
				} else intializeNullList(equips.getSatList()); // CASO Nï¿½O EXISTA VALORES VAI INICIALIZAR COM ZEROS TODOS EQUIPAMENTOS
											
			    }				
		     }
		
			} catch (IndexOutOfBoundsException ex) {

				ex.printStackTrace();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":navbarDropdown2");

	}

	public void intializeNullList(List<? extends Equipments> satList2) {

		for (int i = 0; i < satList2.size(); i++) {

			SAT sat = new SAT();

			sat.setEquip_id(satList2.get(i).getEquip_id());
			sat.setQuantidadeS1(0);
			sat.setVelocidadeS1(0);
			sat.setQuantidadeS2(0);
			sat.setVelocidadeS2(0);

			satListValues.add(sat);

		}
	}

	public void intializeNullStatus(List<? extends Equipments> satList2) {

		for (int i = 0; i < satList2.size(); i++) {

			SAT sat = new SAT();

			sat.setEquip_id(satList2.get(i).getEquip_id());
			sat.setStatus(0);

			satStatus.add(sat);

		}

	}

}
