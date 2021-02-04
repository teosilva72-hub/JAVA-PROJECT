package br.com.tracevia.webapp.controller.sat;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.tracevia.webapp.dao.sat.SATinformationsDAO;
import br.com.tracevia.webapp.model.global.Equipments;
import br.com.tracevia.webapp.model.sat.SAT;

@ManagedBean(name="satLinearView")
@ViewScoped
public class SATBuildLinear {
	
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
								
		try {	
		
		    try {
			
			SATinformationsDAO satDAO = new SATinformationsDAO();
			
			boolean status15Before = false;
			
			//LISTAS
			satList = new ArrayList<SAT>();
			satListValues = new ArrayList<SAT>();
			satStatus = new ArrayList<SAT>();
			
			//LISTAR AUXILIARES
			List<SAT> satListValuesAux = new ArrayList<SAT>();
			List<SAT> satListStatusAux = new ArrayList<SAT>();
						
		    SAT sat =  new SAT();			    
		   		
		    ///////////////////////////////
			// SAT EQUIPMENTS
		    //////////////////////////////		    
			satList = sat.ListLinearEquipments("sat");	
			
			/////////////////////////////
			//SAT STATUS
			///////////////////////////
			
			//PREENCHE LISTA COM DADOS DOS ULTIMOS 15 MINUTOS
			satListStatusAux = satDAO.SATstatus15();
			
			//CASO NÃO ENCONTRE NENHUM DADO DO ULTIMOS 15 MINUTOS
			//PREENCHE LISTA COM DADOS DOS 15 MINUTOS ANTERIORES
			if(satListStatusAux.isEmpty()) {
				satListStatusAux = satDAO.SATstatus15AAA();
			    status15Before = true;
			}
			
			//CASO HAJA DADOS DOS 15 MINUTOS ANTERIORES EXECUTA ESSE BLOCO
			if(status15Before) {
									
					for(int s = 0; s < satList.size(); s++) { // FOR START	
						
						System.out.println(satList.get(s).getEquip_id());
											
						if(satListStatusAux.size() > 0) {
																	
							SAT satListObj = new SAT();
												 
							satListObj.setStatus(satListStatusAux.get(s).getStatus());
													
							satStatus.add(satListObj);
							satListStatusAux.remove(s);
						
						}
						
						else {
							
							SAT satListObj = new SAT();
							 
							satListObj.setEquip_id(satList.get(s).getEquip_id());
							satListObj.setStatus(0);
													
							satStatus.add(satListObj);
							
						}
						
					} // FOR END											
			    
			} else if(!status15Before){
							
				for(int s = 0; s < satList.size(); s++) { // FOR START	
					
					System.out.println(satList.get(s).getEquip_id());
					//System.out.println(satListStatusAux.get(s).getEquip_id());
					
					//if(satListStatusAux.contains(satList.get(s)))
					//System.out.println(satListStatusAux.isEmpty());
					
					if(satListStatusAux.size() > 0) {
																
						SAT satListObj = new SAT();
											 
						satListObj.setStatus(satListStatusAux.get(s).getStatus());
												
						satStatus.add(satListObj);
						satListStatusAux.remove(s);
					
					}
					
					else {
						
						SAT satListObj = new SAT();
						
						satListObj = satDAO.SATstatus15Before(satList.get(s).getEquip_id());
						
						if(satListObj != null)
							satStatus.add(satListObj);	
						
						else {
							
							SAT satListObj1 = new SAT();
							
							satListObj1.setEquip_id(satList.get(s).getEquip_id());
							satListObj1.setStatus(0);							
						   
							satStatus.add(satListObj1);	
							
						    }	   
					
				} // FOR END								
			}		
				
	     } else intializeNullStatus(satList); //CASO NÃO EXISTA VALORES VAI INICIALIZAR COM ZEROS TODOS EQUIPAMENTOS 
						
           ///////////////////////////
           //SAT STATUS
          /////////////////////////
			
			/////////////////////////
			//SAT VALUES
			////////////////////////
			satListValuesAux = satDAO.RealTimeSATinfo();
			
			if(!satListValuesAux.isEmpty()) { 
				
				for(int s = 0; s < satList.size(); s++)	{	//FOR START		
									
				if(satListValuesAux.size() > 0) {
										
					SAT satListObj = new SAT();
										 
					satListObj.setQuantidadeS1(satListValuesAux.get(s).getQuantidadeS1());
					satListObj.setQuantidadeS2(satListValuesAux.get(s).getQuantidadeS2());
					satListObj.setVelocidadeS1(satListValuesAux.get(s).getVelocidadeS1());
					satListObj.setVelocidadeS2(satListValuesAux.get(s).getVelocidadeS2());
					
					satListValues.add(satListObj);
					satListValuesAux.remove(s);
				}
				
				else {
					
					SAT satListObj = new SAT();
					 
					satListObj.setEquip_id(satList.get(s).getEquip_id());
					satListObj.setQuantidadeS1(0);
					satListObj.setQuantidadeS2(0);
					satListObj.setVelocidadeS1(0);
					satListObj.setVelocidadeS2(0);
					
					satListValues.add(satListObj);
					
				}
				
			} // FOR END
			
			}else intializeNullList(satList);  //CASO NÃO EXISTA VALORES VAI INICIALIZAR COM ZEROS TODOS EQUIPAMENTOS 
			
//            ///////////////////////
//            //SAT VALUES
//            //////////////////////
						
			// Caso não tenha equipamentos faz nada
												
            }catch(IndexOutOfBoundsException ex) {}
		
		}catch(Exception ex) {}		
						
	}		
	
	public void intializeNullList(List<? extends Equipments> satList2) {
				
		for(int i = 0; i < satList2.size(); i++) {
			
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
		
		for(int i = 0; i < satList2.size(); i++) {
			
			SAT sat = new SAT();
			
			sat.setEquip_id(satList2.get(i).getEquip_id());
			sat.setStatus(0);
			
			satStatus.add(sat);			
			
		}		  
		
	}
	

}
