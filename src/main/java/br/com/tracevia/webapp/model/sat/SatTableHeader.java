package br.com.tracevia.webapp.model.sat;

import java.util.List;

import br.com.tracevia.webapp.controller.global.EquipmentsBean;
import br.com.tracevia.webapp.util.LocaleUtil;

public class SatTableHeader {
	
	private String equipDescription;
	private String direction1;
	private String direction2;
	private String directionAbbr1;
	private String directionAbbr2;
		
	public SatTableHeader(String equipDescription, String direction1, String direction2, String directionAbbr1,
			String directionAbbr2) {
	
		this.equipDescription = equipDescription;
		this.direction1 = direction1;
		this.direction2 = direction2;
		this.directionAbbr1 = directionAbbr1;
		this.directionAbbr2 = directionAbbr2;
	}

	public SatTableHeader() {}
		
	public String getEquipDescription() {
		return equipDescription;
	}
	
	public void setEquipDescription(String equipDescription) {
		this.equipDescription = equipDescription;
	}
	
	public String getDirection1() {
		return direction1;
	}
	
	public void setDirection1(String direction1) {
		this.direction1 = direction1;
	}
	
	public String getDirection2() {
		return direction2;
	}
	
	public void setDirection2(String direction2) {
		this.direction2 = direction2;
	}
	
	public String getDirectionAbbr1() {
		return directionAbbr1;
	}
	
	public void setDirectionAbbr1(String directionAbbr1) {
		this.directionAbbr1 = directionAbbr1;
	}
	
	public String getDirectionAbbr2() {
		return directionAbbr2;
	}
	
	public void setDirectionAbbr2(String directionAbbr2) {
		this.directionAbbr2 = directionAbbr2;
	}
	
	// -----------------------------------------------------------------------------
	
	public void initialize() {
		
		LocaleUtil localeSAT = new LocaleUtil();
		localeSAT.getResourceBundle(LocaleUtil.LABELS_SAT);
		
		LocaleUtil localeDIR = new LocaleUtil();
		localeDIR.getResourceBundle(LocaleUtil.LABELS_DIRECTIONS);
		
	   equipDescription = localeSAT.getStringKey("flow_counting_equip_column");    	    	  
	   direction1 = localeDIR.getStringKey("directions_tab_direction_1");
	   direction2 = localeDIR.getStringKey("directions_tab_direction_2");    	  
	   directionAbbr1 = localeDIR.getStringKey("directions_direction_1_abbr");
	   directionAbbr2 = localeDIR.getStringKey("directions_direction_2_abbr");   	 
	 		
	}
	
	// -----------------------------------------------------------------------------
	
      public void satHeaderInformation(String module, List<String> equip_id) {
		  
		  EquipmentsBean eqp = new EquipmentsBean();
		  SAT sat = new SAT();
		   
		 if(module.equals("sat")) {				  
			  
			sat =  eqp.satHeaderInformation(equip_id.get(0));
				 
		   equipDescription = sat.getNome().concat(" "+sat.getKm()).concat(" "+sat.getEstrada());
		   direction1 = sat.getSentido1();
		   direction2 = sat.getSentido2();
		   directionAbbr1 = sat.getSentido1Abbr();
		   directionAbbr2 = sat.getSentido2Abbr();
	   		   
		   
		 }		 
	
		  		  		  
	  }  
	
   // -----------------------------------------------------------------------------
	
}
