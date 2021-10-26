package br.com.tracevia.webapp.dao.speed;

public class SpeedQueryBody {
	
	/**
	 * Método para criar o corpo principal da query do relatório Speed Records
	 * @author Wellington 13/10/2021
	 * @version 1.0
	 * @since 1.0
	 * @return o corpo principal da query
	 */			
	public String SpeedRecordsBody() {
		
		String query = "id_speed, " +
				"sp.speed, " + 
				"eq.direction, " + 
				"eq.name ";				
					
		return query;
		
	}
	
	// -----------------------------------------------------------------
	
 

}
