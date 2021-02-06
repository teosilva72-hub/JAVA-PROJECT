package br.com.tracevia.webapp.cfg.tables;

public enum TraceviaTables {
		
		TraceviaVBV("sat_vbv");
		//TraceviaLL("sat_ll"),
		//TraceviaCCR("sat_ccr");
					
		private String table; 	 
		 	   
		TraceviaTables(String table){
		   this.table = table;  
		} 
			   
		public String getTable() {
		   return table;
		}

	}

