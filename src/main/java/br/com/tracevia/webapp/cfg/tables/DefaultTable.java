package br.com.tracevia.webapp.cfg.tables;

public enum DefaultTable {
	
	tableVBV("tb_vbv"),
    tableDados15("tb_dados15");
 				
	private String table; 	 
	 	   
	
	DefaultTable(String table){
	   this.table = table;  
	} 
		   
	
	public String getTable() {
	   return table;
	}

}
