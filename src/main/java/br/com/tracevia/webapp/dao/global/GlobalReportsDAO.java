package br.com.tracevia.webapp.dao.global;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.com.tracevia.webapp.controller.sat.SatReportsController;
import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.model.global.VBV;
import br.com.tracevia.webapp.model.sat.SAT;
import br.com.tracevia.webapp.util.ConnectionFactory;

public class GlobalReportsDAO {
	
	/**
	 * @author Wellington 28/08/2020
	 */
	
	private Connection conn;	
	private PreparedStatement ps;
	private ResultSet rs;
			
	 /* ************************** */
	/* ***** GLOBAL REPORTS ***** */
   /* ************************** */	
	
	/**
	 * 
	 * @param procedure - store procedure para gerar datas
	 * @param query - query de consulta
	 * @param startDate - data inicial
	 * @param endDate - data final
	 * @param fields - colunas dos campos do relatório
	 * @return lista de objetos
	 * @throws Exception
	 */
	
	public String[][] ExecuteQuery(String procedure, String query, String startDate, String endDate) throws Exception {		
				
		//Classe DateTimeApplication
		DateTimeApplication dta = new DateTimeApplication();
		
		//Parametros vindo do Bean
		int registers = SatReportsController.getNumRegisters();
		int fieldsNumber = SatReportsController.getFieldsNumber();
		
		//System.out.println("Fields: "+fieldsNumber);
		
		//System.out.println("REGI: "+registers); Works!!!
				
          // Matriz para alocar dados do ResultSet
		 // fieldsNumber -> Campos de acordo com cada campo da query
		// registers -> Números de registros ( periodo x ( qtde dias selecionados )
		String[][] result = new String[fieldsNumber][registers];
		
		//Formatando datas
		String startDateFormatted = dta.StringDBDateFormat(startDate);
		String endDateFormatted = dta.StringDBDateFormat(endDate);
				
		//Adicionando horas, minutos e segundos
		startDateFormatted += DateTimeApplication.HOUR_TIME_FORMAT_START_DATE;
		endDateFormatted += DateTimeApplication.HOUR_TIME_FORMAT_END_DATE;
				        
		try {
			   //GET CONNECTION 
			    conn = ConnectionFactory.connectToTraceviaApp();
			
			   // CHECK PROCEDURE EXECUTION FIRST
				ps = conn.prepareStatement(procedure);			
				ps.setString(1, startDateFormatted);
				ps.setString(2, endDateFormatted);

				ps.executeUpdate();
																			
				//EXECUTE QUERY				
				ps = conn.prepareStatement(query);
				rs = ps.executeQuery();
				
				System.out.println("QUERY: "+query);			
								
				//RESULT IN RESULTSET
							
				int lin = 0;

				if (rs != null) {
					while (rs.next()) { //Linhas
						
				     for(int col = 0; col < fieldsNumber; col++ )	{ //Colunas
				    					    	 
				    	    result[col][lin] = rs.getString((col+1));
				    	    				    	    
				    	    //System.out.println("COL["+col+"]LIN["+lin+"] = "+result[col][lin] );  	  //DEBBUGER  
				    	 				      
				        }
				    				     
				     lin++;
				     
				    } 
				 } 


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
			    conn = ConnectionFactory.connectToTraceviaApp();
			
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
			    conn = ConnectionFactory.connectToTraceviaApp();
																							
				//EXECUTE QUERY				
				ps = conn.prepareStatement(query);
				ps.setString(1, startDateFormatted);
				ps.setString(2, endDateFormatted);
				ps.setString(3, equip);				
				rs = ps.executeQuery();
				
				System.out.println("QUERY: "+query);
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
