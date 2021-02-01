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
			
			//LISTAS
			satList = new ArrayList<SAT>();
			satListValues = new ArrayList<SAT>();
			satStatus = new ArrayList<SAT>();
			
			//LISTAR AUXILIARES
			List<SAT> satListValuesAux = new ArrayList<SAT>();
			List<SAT> satListStatusAux = new ArrayList<SAT>();
						
		    SAT sat =  new SAT();	
			
			// SAT STRUCTURE EQUIPMENTS
			satList = sat.ListLinearEquipments("sat");	
						
			//SAT STATUS
			satListStatusAux = satDAO.SATstatus15();
			
			if(!satListStatusAux.isEmpty()) 								
				satStatus.addAll(satListStatusAux);			
			
			else intializeNullStatus(satList);
								
			//SAT VALUES
			   satListValuesAux = satDAO.RealTimeSATinfo();
			
			if(!satListValuesAux.isEmpty()) 								
				satListValues.addAll(satListValuesAux);			
			
			else intializeNullList(satList);  
						
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
