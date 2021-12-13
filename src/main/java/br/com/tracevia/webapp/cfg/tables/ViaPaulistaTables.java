package br.com.tracevia.webapp.cfg.tables;

public enum ViaPaulistaTables {
	
		ViaPaulistaVBV("tb_vbv"),
	    ViaPaulistaDados15("tb_dados15"),
	    ViaPaulistaStatus("tb_status");
					
		private String table; 	 
		 	   
		ViaPaulistaTables(String table){
		   this.table = table;  
		} 
			   
		public String getTable() {
		   return table;
	}

}

