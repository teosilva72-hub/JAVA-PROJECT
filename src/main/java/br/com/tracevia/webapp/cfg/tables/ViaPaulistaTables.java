package br.com.tracevia.webapp.cfg.tables;

public enum ViaPaulistaTables {
	
		ViaPaulistaVBV("tb_vbv");
		//ViaPaulistaLL("tb_vbv_ll"),
		//ViaPaulistaCCR("tb_vbv_paulista");
					
		private String table; 	 
		 	   
		ViaPaulistaTables(String table){
		   this.table = table;  
		} 
			   
		public String getTable() {
		   return table;
		}

	}

