package br.com.tracevia.webapp.dao.global;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.com.tracevia.webapp.cfg.RoadConcessionairesEnum;
import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.model.global.VBV;
import br.com.tracevia.webapp.util.ConnectionFactory;

public class GlobalReportsDAO {
	
	/**
	 * @author Wellington 28/08/2020
	 */
	
	private Connection conn;	
	private PreparedStatement ps;
	private ResultSet rs;
	private String[][] result;
			
	 /* ************************** */
	/* ***** GLOBAL REPORTS ***** */
   /* ************************** */	
	
	/**
	 * @author Wellington Silva -- updated: 24/02/2021	
	 * @version: 1.1
	 * @since 1.0		
	 * @param query - consulta SQL
	 * @param fieldsNumber - n�mero de campos de acordo com a sa�da da query
	 * @param registers - n�mero de registros
	 * @return array contendo dados do relat�rio
	 * @throws Exception
	 */
	
	public String[][] ExecuteQuery(String query,  int registers, int fieldsNumber) throws Exception {		
						
	    // NECESSITA DA CONSTRU��O DE UMA MATRIZ PARA RECEBER OS DADOS DE SA�DA DA QUERY
		// FIELDSNUMBER S�O O NUMERO DE CAMPOS DE ACORDO COM A QUERY A SER EXECUTADA -> RECEBE ESSE PAR�METRO DO BEAN
		/* REGISTERS S�O O N�MERO DE REGISTROS QUE A MATRIZ DEVER� POSSUIR. SUA CONSTRU��O SE DEVE NA ESCOLHA DE DATA DE �NICIO, DATA DE FIM, 
		M�S, ANOS E ETC. */
		result = new String[registers][fieldsNumber];
							
		try {
			   //GET CONNECTION			
			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
																												
				//EXECUTE QUERY				
				ps = conn.prepareStatement(query);
				rs = ps.executeQuery();
									
				int lin = 0;
						
				//RESULT IN RESULTSET	
				  if (rs.isBeforeFirst()) {
					  while (rs.next()) { //Linhas
						
				       for(int col = 0; col < fieldsNumber; col++ ) { // Colunas
				    					    	 
				    	    result[lin][col] = rs.getString((col+1));
				    	    				    	    
				    	   // System.out.println("LIN["+lin+"]COL["+col+"] = "+result[lin][col] );  	  // DEBBUGER  				    	 				      
				        }
				    				     
				     lin++;
				     
				    } 
					
				 } else result = new String[0][0]; //CASO N�O EXISTA REGISTROS REDEFINE TAMANHO DO ARRAY
				
		}finally 
		{
			ConnectionFactory.closeConnection(conn, ps, rs);			
		}
		
		return result;		

	}
	
	   /* ************************** */
	  /* ***** GLOBAL REPORTS ***** */
     /* ************************** */	
	
	
	 /* **************************** */
    /* ***** VBVs METHOD ONLY ***** */
   /* **************************** */	
	
public Integer GetVBVsRegisterNumbers(String query, String equip, String startDate, String endDate) throws Exception {		
		
		DateTimeApplication dta = new DateTimeApplication();
		
		int registers = 0;		
				
		String startDateFormatted = dta.StringDBDateFormat(startDate);
		String endDateFormatted = dta.StringDBDateFormat(endDate);
				        
		try {
			
			 //GET CONNECTION			
			conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
		    
			   // CHECK PROCEDURE EXECUTION FIRST
				ps = conn.prepareStatement(query);					
				ps.setString(1, startDateFormatted);
				ps.setString(2, endDateFormatted);
				ps.setString(3, equip);
				rs = ps.executeQuery();
				
				if (rs != null) {
					while (rs.next()) {

				     registers = rs.getInt(1);
				     				     
				    } 
				 } 
			
		}finally 
		{
			ConnectionFactory.closeConnection(conn, ps, rs);
		}

		return registers;		

	}
	
	public ArrayList<VBV> ExecuteQueryVBVs(String query, String equip, String startDate, String endDate) throws Exception {		
		
		DateTimeApplication dta = new DateTimeApplication();
		
		ArrayList<VBV> lista = new ArrayList<VBV>();
					 
		String startDateFormatted = dta.StringDBDateFormat(startDate);
		String endDateFormatted = dta.StringDBDateFormat(endDate);
				        
		try {
			
			 //GET CONNECTION			
		    if(RoadConcessionaire.roadConcessionaire.equals(RoadConcessionairesEnum.ViaSul.getConcessionaire()))
		           conn = ConnectionFactory.connectToCCR();
		    
		    else if(RoadConcessionaire.roadConcessionaire.equals(RoadConcessionairesEnum.ViaPaulista.getConcessionaire()))
		           conn = ConnectionFactory.connectToViaPaulista();
		    
		    else conn = ConnectionFactory.connectToTraceviaApp();
																							
				//EXECUTE QUERY				
				ps = conn.prepareStatement(query);
				ps.setString(1, startDateFormatted);
				ps.setString(2, endDateFormatted);
				ps.setString(3, equip);				
				rs = ps.executeQuery();
				
				//System.out.println("QUERY: "+query);
				//RESULT IN RESULTSET
				
				if (rs != null) {
					while (rs.next()) {
						
						VBV vbv = new VBV();
						
                        vbv.setSiteID(rs.getInt("siteID"));
						
						vbv.setSeqG(rs.getInt("seqG"));
						vbv.setSeqN(rs.getInt("seqN"));
						
						vbv.setDatetime(rs.getString("datetime_"));										

						String classe = rs.getString("classe");
						if (classe == null)
							vbv.setClassVBV("UC");						
						else
							vbv.setClassVBV(classe);

						int axle = rs.getInt("axlNumber");
						if (axle == 0 || axle == 1)
							vbv.setAxlNumber(2); // Caso 0 ou 1 recebe 2
						else
							vbv.setAxlNumber(axle);					
						
						 vbv.setAxl1W(rs.getInt("axl1W"));
						 vbv.setAxl2W(rs.getInt("axl2W"));
						 vbv.setAxl3W(rs.getInt("axl3W"));
						 vbv.setAxl4W(rs.getInt("axl4W"));
						 vbv.setAxl5W(rs.getInt("axl5W"));
						 vbv.setAxl6W(rs.getInt("axl6W"));
						 vbv.setAxl7W(rs.getInt("axl7W"));
						 vbv.setAxl8W(rs.getInt("axl8W"));
						 vbv.setAxl9W(rs.getInt("axl9W"));
						 
						 vbv.setAxl2D(rs.getInt("axl2D"));
						 vbv.setAxl3D(rs.getInt("axl3D"));
						 vbv.setAxl4D(rs.getInt("axl4D"));
						 vbv.setAxl5D(rs.getInt("axl5D"));
						 vbv.setAxl6D(rs.getInt("axl6D"));
						 vbv.setAxl7D(rs.getInt("axl7D"));
						 vbv.setAxl8D(rs.getInt("axl8D"));
						 vbv.setAxl9D(rs.getInt("axl9D"));
						 											
						 vbv.setGross(rs.getInt("gross"));
						 vbv.setTemperature(rs.getInt("temperature"));
						 vbv.setSpeed(rs.getInt("speed"));
						 vbv.setLane(rs.getInt("lane"));

						 lista.add(vbv); // adiciona na Lista
				   
				    } 
				 }

		    }finally 
		      {
		    	
			ConnectionFactory.closeConnection(conn, ps, rs);
		     
		      }

		return lista;		

	}
	
	 /* **************************** */
    /* ***** VBVs METHOD ONLY ***** */
   /* **************************** */		

}
