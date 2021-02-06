package br.com.tracevia.webapp.cfg.tables;

public enum ViaSulTables {
	
	ViaSulVBV("tb_vbv"),
	ViaSulLL("tb_vbv_ll"),
	ViaSulCCR("tb_vbv_ccr");
				
	private String table; 	 
	 	   
	ViaSulTables(String table){
	   this.table = table;  
	} 
		   
	public String getTable() {
	   return table;
	}

}
