package br.com.tracevia.webapp.controller.sat;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.tracevia.webapp.dao.sat.SATinformationsDAO;
import br.com.tracevia.webapp.model.global.Equipments;
import br.com.tracevia.webapp.model.sat.SAT;

@ManagedBean(name = "satMapsView")
@ViewScoped
public class SATBuildMaps {

	static List<? extends Equipments> satList;
	List<SAT> satListValues, satStatus;

	public List<? extends Equipments> getSatList() {
		return satList;
	}

	public List<SAT> getSatListValues() {
		return satListValues;
	}

	public List<SAT> getSatStatus() {
		return satStatus;
	}

	@PostConstruct
	public void initalize() {

		CreateLinearEquipment();

	}

	public void CreateLinearEquipment() {
				
		//System.out.println("CON: "+RoadConcessionaire.roadConcessionaire);

		try {

			try {

				SATinformationsDAO satDAO = new SATinformationsDAO();  
 
				boolean status30 = true, values30 = true, status45 = false, values45 = false, status08 = false,
						values08 = false;
				boolean pass = true; 

				// LISTAS
				satList = new ArrayList<SAT>();
				satListValues = new ArrayList<SAT>();
				satStatus = new ArrayList<SAT>();

				// LISTAR AUXILIARES
				List<SAT> satListValuesAux = new ArrayList<SAT>();
				List<SAT> satListStatusAux = new ArrayList<SAT>();

				// SAT OBJECT
				SAT sat = new SAT();

				///////////////////////////////
				// SAT EQUIPMENTS
				//////////////////////////////
				satList = sat.listEquipments("sat");

				////////////////////////////////////////////////////////////////////////////////////////////
				///// SAT STATUS
				///////////////////////////////////////////////////////////////////////////////////////////
				
				// PREENCHE LISTA COM STATUS DOS ULTIMOS 30 MINUTOS
				//TABELA POSSUI DELAY DE 15 MINUTOS
				satListStatusAux = satDAO.statusByData30();

				// CASO NAO ENCONTRE NENHUM STATUS DO ULTIMOS 30 MINUTOS				
				// VERIFICAÇÃO DA LISTA DE DADOS
				if (satListStatusAux.isEmpty()) {      
					
					satListStatusAux = satDAO.statusByData45();
					
				// VERIFICA SE HÁ DADOS DOS ULTIMOS 45 MINUTOS					
				  if(!satListStatusAux.isEmpty()) { 
						
						status30 = false;						
					    status45 = true;
					
				 // CASO NÃO HAJA DADOS DOS ULTIMOS 45 MINUTOS 
				 // VERIFICA SE HÁ DADOS DAS ULTIMAS 8 HORAS
				}else {
						
						satListStatusAux = satDAO.statusByData08();
						
						if(!satListStatusAux.isEmpty()) { 
							
							status30 = false;						   
						    status08 = true;							
						}
						
						else status30 = false; 
						// DEFINE ESSE VALOR FALSO
						//DEFAULT == TRUE
						
					}
				} // VERIFICAÇÃO DA LISTA DE DADOS 
					
				//VERIFICA O STATUS 45 MINUTOS
				if (status45) {

					// LISTA COM TODOS SATS 
					for (int s = 0; s < satList.size(); s++) { // FOR START

						SAT satListObj = new SAT();
						pass = true; // VERIFICA SE HÁ DADOS NA COMPARAÇÃO ENTRE LISTAS
						
                        //LISTA DE SATS COM DADOS DISPONIVEIS
						for (int r = 0; r < satListStatusAux.size(); r++) {
							//COMPARA IDS ENTRE AS LISTAS
							if (satListStatusAux.get(r).getEquip_id() == satList.get(s).getEquip_id()) {

								satListObj.setStatus(satListStatusAux.get(r).getStatus());

								satStatus.add(satListObj);
								satListStatusAux.remove(r);
								pass = false;

								break;

							}
						}
						
						//CASO NÃO HAJA DADOS ENTRA NESSA CONDIÇÃO
						if (pass) {

							//BUSCA DADOS DAS ULTIMAS 08 HORAS
							satListObj = satDAO.statusByData08(satList.get(s).getEquip_id());

							//SE HOUVER DADOS PREENCHE NA LISTA
							if (satListObj.getEquip_id() != 0)
								satStatus.add(satListObj);
							
							//CASO CONTRARIO PREENCHE COM 0
							else {

								SAT satListObj1 = new SAT();

								satListObj1.setEquip_id(satList.get(s).getEquip_id());
								satListObj1.setStatus(0);

								satStatus.add(satListObj1);

							}
						}
					} // FOR END
		
				// VERIFICA O STATUS 30 MINUTOS
				} else if (status30) {

					// LISTA COM TODOS SATS 
					for (int s = 0; s < satList.size(); s++) { // FOR START

						SAT satListObj = new SAT();
						pass = true; // VERIFICA SE HÁ DADOS NA COMPARAÇÃO ENTRE LISTAS
                         
						//LISTA DE SATS COM DADOS DISPONIVEIS
						for (int r = 0; r < satListStatusAux.size(); r++) {
							//COMPARA IDS ENTRE AS LISTAS
							if (satListStatusAux.get(r).getEquip_id() == satList.get(s).getEquip_id()) {

								satListObj.setStatus(satListStatusAux.get(r).getStatus());

								satStatus.add(satListObj);
								satListStatusAux.remove(r);
								pass = false;

								break;

							}
						}
						
					    //CASO NÃO HAJA DADOS ENTRA NESSA CONDIÇÃO
						if (pass) {

							//BUSCA DADOS DOS ULTIMAS 45 MINUTOS
							satListObj = satDAO.SATstatus45Stat(satList.get(s).getEquip_id());

							//SE HOUVER DADOS PREENCHE NA LISTA
							if (satListObj.getEquip_id() != 0)
								satStatus.add(satListObj);
							
							//BUSCA DADOS DOS ULTIMAS 08 HORAS
							else {
								
								//BUSCA DADOS DAS ULTIMAS 08 HORAS
								satListObj = satDAO.statusByData08(satList.get(s).getEquip_id());

								//SE HOUVER DADOS PREENCHE NA LISTA
								if (satListObj.getEquip_id() != 0)
									satStatus.add(satListObj);
								
								//CASO CONTRARIO PREENCHE COM 0
								else{
							
								  SAT satListObj1 = new SAT();

								  satListObj1.setEquip_id(satList.get(s).getEquip_id());
								  satListObj1.setStatus(0);

								  satStatus.add(satListObj1);
								  
						         }
							   }
							}						
					} // FOR END
                 
				// VERIFICA O STATUS 08 HORAS
				} else if(status08) {
					
					//LISTA COM SATS
					for (int s = 0; s < satList.size(); s++) { // FOR START

						SAT satListObj = new SAT();
						pass = true; // VERIFICA SE HÁ DADOS NA COMPARAÇÃO ENTRE LISTAS
						
						//LISTA DE SATS COM DADOS DISPONIVEIS
						for (int r = 0; r < satListStatusAux.size(); r++) {
							//COMPARA IDS ENTRE AS LISTAS
							if (satListStatusAux.get(r).getEquip_id() == satList.get(s).getEquip_id()) {

								satListObj.setStatus(satListStatusAux.get(r).getStatus());

								satStatus.add(satListObj);
								satListStatusAux.remove(r);
								pass = false;

								break;

							}
						}
						
						//CASO NÃO HAJA DADOS ENTRA NESSA CONDIÇÃO
						//CASO CONTRARIO PREENCHE COM 0
						//NOTIFICAÇÃO????
						if (pass) {							
							
								SAT satListObj1 = new SAT();

								satListObj1.setEquip_id(satList.get(s).getEquip_id());
								satListObj1.setStatus(0);

								satStatus.add(satListObj1);
							
						}
					} // FOR END				
				
			    // CASO NÃO ENCONTROU NADA 
			    // PREENCHE TODOS EQUIPAMENTOS COM ZEROS
				}else intializeNullStatus(satList); 

          ////////////////////////////////////////////////////////////////////////////////////////////
          ///// SAT STATUS
         ///////////////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////////////////////////////////
       ///// SAT VALUES
       ///////////////////////////////////////////////////////////////////////////////////////////
				
				// PREENCHE LISTA COM STATUS DOS ULTIMOS 30 MINUTOS
				// TABELA POSSUI DELAY DE 15 MINUTOS
				satListValuesAux = satDAO.dataInfo30();

				// CASO NAO ENCONTRE NENHUM STATUS DO ULTIMOS 30 MINUTOS				
				// VERIFICAÇÃO DA LISTA DE DADOS
				if(satListValuesAux.isEmpty()) {      
					
					satListValuesAux = satDAO.dataInfo45();
					
				// VERIFICA SE HÁ DADOS DOS ULTIMOS 45 MINUTOS					
				  if(!satListValuesAux.isEmpty()) { 
						
						values30 = false;						
					    values45 = true;
					
				 // CASO NÃO HAJA DADOS DOS ULTIMOS 45 MINUTOS 
				 // VERIFICA SE HÁ DADOS DAS ULTIMAS 8 HORAS
				}else {
						
						satListValuesAux = satDAO.dataInfo08();
						
						if(!satListValuesAux.isEmpty()) { 
							
							values30 = false;						   
						    values08 = true;							
						}
						
						else values30 = false; 
						// DEFINE ESSE VALOR FALSO
						// DEFAULT == TRUE
						
					}
				} // VERIFICAÇÃO DA LISTA DE DADOS 
			
				//VERIFICA O STATUS 45 MINUTOS
				if (values45) {

					// LISTA COM TODOS SATS
					for (int s = 0; s < satList.size(); s++) { // FOR START

						SAT satListObj = new SAT();
						pass = true; // VERIFICA SE HÁ DADOS NA COMPARAÇÃO ENTRE LISTAS

						//LISTA DE SATS COM DADOS DISPONIVEIS
						for (int r = 0; r < satListValuesAux.size(); r++) {
							
							//COMPARA IDS ENTRE AS LISTAS
							if (satListValuesAux.get(r).getEquip_id() == satList.get(s).getEquip_id()) {

								satListObj.setQuantidadeS1(satListValuesAux.get(r).getQuantidadeS1());
								satListObj.setQuantidadeS2(satListValuesAux.get(r).getQuantidadeS2());
								satListObj.setVelocidadeS1(satListValuesAux.get(r).getVelocidadeS1());
								satListObj.setVelocidadeS2(satListValuesAux.get(r).getVelocidadeS2());
								satListObj.setStatusInterval(45);

								satListValues.add(satListObj);
								satListValuesAux.remove(r);
								pass = false; 

								break;

							}
						}
						
						//CASO NAO HAJA DADOS ENTRA NESSA CONDIÇÃO
						if (pass) {
							
							//BUSCA DADOS DAS ULTIMAS 08 HORAS
							satListObj = satDAO.dataInfoByData08(satList.get(s).getEquip_id());
							satListObj.setStatusInterval(8);
							
							//SE HOUVER DADOS PREENCHE NA LISTA
							if (satListObj.getEquip_id() != 0)
								satListValues.add(satListObj);
							
							//CASO CONTRARIO PREENCHE COM 0
							else {

							satListObj.setEquip_id(satList.get(s).getEquip_id());
							satListObj.setQuantidadeS1(0);
							satListObj.setQuantidadeS2(0);
							satListObj.setVelocidadeS1(0);
							satListObj.setVelocidadeS2(0);
							satListObj.setStatusInterval(0);

							satListValues.add(satListObj);
							
						  }
					   }
					} // FOR END

				} else if (values30) {

					// LISTA COM TODOS SATS
					for (int s = 0; s < satList.size(); s++) { // FOR START

						SAT satListObj = new SAT();
						pass = true; // VERIFICA SE HÁ DADOS NA COMPARAÇÃO ENTRE LISTAS

						//LISTA DE SATS COM DADOS DISPONIVEIS
						for (int r = 0; r < satListValuesAux.size(); r++) {
														
							//COMPARA IDS ENTRE AS LISTAS
							if (satListValuesAux.get(r).getEquip_id() == satList.get(s).getEquip_id()) {

								satListObj.setQuantidadeS1(satListValuesAux.get(r).getQuantidadeS1());
								satListObj.setQuantidadeS2(satListValuesAux.get(r).getQuantidadeS2());
								satListObj.setVelocidadeS1(satListValuesAux.get(r).getVelocidadeS1());
								satListObj.setVelocidadeS2(satListValuesAux.get(r).getVelocidadeS2());
								satListObj.setStatusInterval(30);

								satListValues.add(satListObj);
								satListValuesAux.remove(r);
								pass = false;

								break;

							}
						}
						 //CASO NÃO HAJA DADOS ENTRA NESSA CONDIÇÃO
						if (pass) {
							
							//BUSCA DADOS DOS ULTIMAS 45 MINUTOS
							satListObj = satDAO.dataInfoByData45(satList.get(s).getEquip_id());
							satListObj.setStatusInterval(45);
							
							//SE HOUVER DADOS PREENCHE NA LISTA
							if (satListObj.getEquip_id() != 0)
								satListValues.add(satListObj);
							
							else {
								
								//BUSCA DADOS DAS ULTIMAS 08 HORAS
								satListObj = satDAO.dataInfoByData08(satList.get(s).getEquip_id());
								satListObj.setStatusInterval(8);
								
								//SE HOUVER DADOS PREENCHE NA LISTA
								if (satListObj.getEquip_id() != 0)
									satListValues.add(satListObj);
								
								//CASO CONTRARIO PREENCHE COM 0
								else {
								
								SAT satListObj1 = new SAT();

								satListObj1.setEquip_id(satList.get(s).getEquip_id());
								satListObj1.setQuantidadeS1(0);
								satListObj1.setQuantidadeS2(0);
								satListObj1.setVelocidadeS1(0);
								satListObj1.setVelocidadeS2(0);
								satListObj.setStatusInterval(0);

								satListValues.add(satListObj1);
							  }
						   }
						}
					} // FOR END

				// VERIFICA O STATUS 08 HORAS
				}else if(values08) {
					
					// LISTA COM TODOS SATS
					for (int s = 0; s < satList.size(); s++) { // FOR START

						SAT satListObj = new SAT();
						pass = true; // VERIFICA SE HÁ DADOS NA COMPARAÇÃO ENTRE LISTAS

						//LISTA DE SATS COM DADOS DISPONIVEIS
						for (int r = 0; r < satListValuesAux.size(); r++) {
							
							//COMPARA IDS ENTRE AS LISTAS
							if (satListValuesAux.get(r).getEquip_id() == satList.get(s).getEquip_id()) {

								satListObj.setQuantidadeS1(satListValuesAux.get(r).getQuantidadeS1());
								satListObj.setQuantidadeS2(satListValuesAux.get(r).getQuantidadeS2());
								satListObj.setVelocidadeS1(satListValuesAux.get(r).getVelocidadeS1());
								satListObj.setVelocidadeS2(satListValuesAux.get(r).getVelocidadeS2());
								satListObj.setStatusInterval(8);

								satListValues.add(satListObj);
								satListValuesAux.remove(r);
								pass = false; 

								break;

							}
						}
						
						//CASO NAO HAJA DADOS ENTRA NESSA CONDIÇÃO
						if (pass) {
							
							//CASO CONTRARIO PREENCHE COM 0
						
							satListObj.setEquip_id(satList.get(s).getEquip_id());
							satListObj.setQuantidadeS1(0);
							satListObj.setQuantidadeS2(0);
							satListObj.setVelocidadeS1(0);
							satListObj.setVelocidadeS2(0);
							satListObj.setStatusInterval(0);

							satListValues.add(satListObj);							
						  
					   }
					} // FOR END					
					
				   // CASO NÃO ENCONTROU NADA 
				  // PREENCHE TODOS EQUIPAMENTOS COM ZEROS
				}else
					intializeNullList(satList); // CASO Nï¿½O EXISTA VALORES VAI INICIALIZAR COM ZEROS TODOS EQUIPAMENTOS

				 ////////////////////////////////////////////////////////////////////////////////////////////
			     ///// SAT VALUES
			    ///////////////////////////////////////////////////////////////////////////////////////////


				// Caso nï¿½o tenha equipamentos faz nada

			} catch (IndexOutOfBoundsException ex) {

				ex.printStackTrace();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

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
	
	
	public void populateCCRSAT() {
		
		
		
	}

}
