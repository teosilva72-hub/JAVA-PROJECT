package br.com.tracevia.webapp.dao.global;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.com.tracevia.webapp.model.global.ReportBuild;
import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.util.ConnectionFactory;

 /**
  * Classe para obter relatórios padronizados
  * @author Wellington 09/10/2021
 */

public class NewReportsDAO {
		
	private Connection conn;	
	private PreparedStatement ps;
	private ResultSet rs;
			
	/**
	 * Método para executar uma query SQL
	 * @author Wellington Silva 09/10/2021	
	 * @version: 1.0
	 * @since 1.0		
	 * @param query - consulta SQL
	 * @return dados para preenchimento de um relatório
	 * @throws Exception
	 */	
	public String[][] ExecuteSQLQuery(String query) throws Exception {		
						
	    // VARIAVEL PARA PREENCHER VALORES DO RESULTADO DO BANCO DE DADOS
		String[][] result = new String[ReportBuild.numRegisters][ReportBuild.fieldsNumber];
							
		try {
			   
			   // GET CONNECTION		
			   conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
																												
				// EXECUTE QUERY				
				ps = conn.prepareStatement(query);
				rs = ps.executeQuery();
									
				int lin = 0;
						
				// RESULT IN RESULTSET	
				  if (rs.isBeforeFirst()) {
					  while (rs.next()) { //Linhas
						
				       for(int col = 0; col < ReportBuild.fieldsNumber; col++ ) { // Colunas
				    					    	 
				    	    result[lin][col] = rs.getString((col+1));
				    	    				    	    
				    	   // System.out.println("LIN["+lin+"]COL["+col+"] = "+result[lin][col] );  	  // DEBBUGER  				    	 				      
				        }
				    				     
				     lin++;
				     
				    } 
					
				 } else result = new String[0][0]; // CASO NÃO EXISTA REGISTROS REDEFINE TAMANHO DO ARRAY
				
		}finally 
		  {
			ConnectionFactory.closeConnection(conn, ps, rs);			
		  }
		
		return result;		

	}
	
	
  // ----------------------------------------------------------------------------------------------------------------

}
