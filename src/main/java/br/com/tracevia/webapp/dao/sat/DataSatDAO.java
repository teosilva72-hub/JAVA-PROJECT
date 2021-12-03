package br.com.tracevia.webapp.dao.sat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.model.sat.SAT;
import br.com.tracevia.webapp.util.ConnectionFactory;

public class DataSatDAO {
			
	private Connection conn;			
	private PreparedStatement ps;
	private ResultSet rs;
	
	
	public List<SAT> data30min() throws Exception {
		
		List<SAT> list = new ArrayList<SAT>();
		DateTimeApplication dta = new DateTimeApplication();
		
		String currentDate = null;
			
		Calendar calendar = Calendar.getInstance();	
		int minute = calendar.get(Calendar.MINUTE);
		
		//Obter datas formatadas para os dados
		currentDate = dta.getCurrentDateDados15CCR(calendar, minute);
							
		String select = "SELECT d.NOME_ESTACAO, d.DATA_HORA, " +
		
		"IFNULL(ROUND(SUM(IF(d.NOME_FAIXA IN(SELECT lane FROM filter_directions WHERE direction = (SELECT direction FROM filter_directions WHERE lane = 1)), d.VOLUME_TOTAL , NULL)), 0), 0) 'VOLUME_TOTAL_S1', " +
		"IFNULL(ROUND(SUM(IF(d.NOME_FAIXA IN(SELECT lane FROM filter_directions WHERE direction NOT IN (SELECT direction FROM filter_directions WHERE lane = 1)), d.VOLUME_TOTAL , NULL)), 0), 0) 'VOLUME_TOTAL_S2', " +
		
		"IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA IN(SELECT lane FROM filter_directions WHERE direction = (SELECT direction FROM filter_directions WHERE lane = 1)), d.VEL_MEDIA_TOTAL , NULL), 0)), 0), 0) 'VEL_MEDIA_TOTAL_S1', " +
		"IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA IN(SELECT lane FROM filter_directions WHERE direction NOT IN (SELECT direction FROM filter_directions WHERE lane = 1)), d.VEL_MEDIA_TOTAL , NULL), 0)), 0), 0) 'VEL_MEDIA_TOTAL_S2' " +  
							    
	    "FROM "+RoadConcessionaire.tableDados15+" d " +
	    "INNER JOIN sat_equipment eq on (eq.equip_id = d.nome_estacao) " +
	    "WHERE DATA_HORA BETWEEN DATE_SUB( ? , INTERVAL 30 MINUTE) AND ? AND eq.visible = 1 " +
	    "GROUP BY d.NOME_ESTACAO " +
	    "ORDER BY d.DATA_HORA ASC ";
					
	  try {
			
		    conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
			
			ps = conn.prepareStatement(select);			
			ps.setString(1, currentDate);		
			ps.setString(2, currentDate);
			
			rs = ps.executeQuery();
			
			//System.out.println("30 M: "+select);
			
			if (rs != null) {
				while (rs.next()) {
					
					SAT sat = new SAT();

					sat.setEquip_id(rs.getInt("d.NOME_ESTACAO"));
					sat.setQuantidadeS1(rs.getInt("VOLUME_TOTAL_S1"));						
					sat.setVelocidadeS1(rs.getInt("VEL_MEDIA_TOTAL_S1"));	
					sat.setQuantidadeS2(rs.getInt("VOLUME_TOTAL_S2"));	
					sat.setVelocidadeS2(rs.getInt("VEL_MEDIA_TOTAL_S2"));	
															
					list.add(sat);
				}				
			 }			

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {ConnectionFactory.closeConnection(conn, ps, rs);}

				
		return list;
		
	}
			
	// -------------------------------------------------------------------------------------------------------------------------------------------------
			
        public List<SAT> data03hrs() throws Exception {
		
		List<SAT> list = new ArrayList<SAT>();
		DateTimeApplication dta = new DateTimeApplication();
		
		String currentDate = null;
			
		Calendar calendar = Calendar.getInstance();	
		int minute = calendar.get(Calendar.MINUTE);
		
		//Obter datas formatadas para os dados
		currentDate = dta.getCurrentDateDados15CCR(calendar, minute);
						
		String select = "SELECT d.NOME_ESTACAO, d.DATA_HORA, " +
		
		"IFNULL(ROUND(SUM(IF(d.NOME_FAIXA IN(SELECT lane FROM filter_directions WHERE direction = (SELECT direction FROM filter_directions WHERE lane = 1)), d.VOLUME_TOTAL , NULL)), 0), 0) 'VOLUME_TOTAL_S1', " +
		"IFNULL(ROUND(SUM(IF(d.NOME_FAIXA IN(SELECT lane FROM filter_directions WHERE direction NOT IN (SELECT direction FROM filter_directions WHERE lane = 1)), d.VOLUME_TOTAL , NULL)), 0), 0) 'VOLUME_TOTAL_S2', " +
		
		"IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA IN(SELECT lane FROM filter_directions WHERE direction = (SELECT direction FROM filter_directions WHERE lane = 1)), d.VEL_MEDIA_TOTAL , NULL), 0)), 0), 0) 'VEL_MEDIA_TOTAL_S1', " +
		"IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA IN(SELECT lane FROM filter_directions WHERE direction NOT IN (SELECT direction FROM filter_directions WHERE lane = 1)), d.VEL_MEDIA_TOTAL , NULL), 0)), 0), 0) 'VEL_MEDIA_TOTAL_S2' " +  

	    "FROM "+RoadConcessionaire.tableDados15+" d " +
	    "INNER JOIN sat_equipment eq on (eq.equip_id = d.nome_estacao) " +
	    "WHERE DATA_HORA BETWEEN DATE_SUB( ? , INTERVAL 3 HOUR) AND ? AND eq.visible = 1 " +
	    "GROUP BY d.NOME_ESTACAO " +
	    "ORDER BY d.DATA_HORA ASC";
					
	  try {
			
		  conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
		  
			ps = conn.prepareStatement(select);			
			ps.setString(1, currentDate);
			ps.setString(2, currentDate);
						
			rs = ps.executeQuery();
			
		//System.out.println("03 HR: "+select);
			
			if (rs != null) {
				while (rs.next()) {
					
					SAT sat = new SAT();

					sat.setEquip_id(rs.getInt("d.NOME_ESTACAO"));
					sat.setQuantidadeS1(rs.getInt("VOLUME_TOTAL_S1"));						
					sat.setVelocidadeS1(rs.getInt("VEL_MEDIA_TOTAL_S1"));	
					sat.setQuantidadeS2(rs.getInt("VOLUME_TOTAL_S2"));	
					sat.setVelocidadeS2(rs.getInt("VEL_MEDIA_TOTAL_S2"));	
															
					list.add(sat);
				}				
			 }			

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {ConnectionFactory.closeConnection(conn, ps, rs);}

				
		return list;
		
	}
			
   // -------------------------------------------------------------------------------------------------------------------------------------------------
    	     
        public List<SAT> data06hrs() throws Exception {
		
		List<SAT> list = new ArrayList<SAT>();
		DateTimeApplication dta = new DateTimeApplication();
		
		String currentDate = null;
			
		Calendar calendar = Calendar.getInstance();	
		int minute = calendar.get(Calendar.MINUTE);
		
		//Obter datas formatadas para os dados
		currentDate = dta.getCurrentDateDados15CCR(calendar, minute);
						
		String select = "SELECT d.NOME_ESTACAO, d.DATA_HORA, " +
		
        "IFNULL(ROUND(SUM(IF(d.NOME_FAIXA IN(SELECT lane FROM filter_directions WHERE direction = (SELECT direction FROM filter_directions WHERE lane = 1)), d.VOLUME_TOTAL , NULL)), 0), 0) 'VOLUME_TOTAL_S1', " +
        "IFNULL(ROUND(SUM(IF(d.NOME_FAIXA IN(SELECT lane FROM filter_directions WHERE direction NOT IN (SELECT direction FROM filter_directions WHERE lane = 1)), d.VOLUME_TOTAL , NULL)), 0), 0) 'VOLUME_TOTAL_S2', " +

        "IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA IN(SELECT lane FROM filter_directions WHERE direction = (SELECT direction FROM filter_directions WHERE lane = 1)), d.VEL_MEDIA_TOTAL , NULL), 0)), 0), 0) 'VEL_MEDIA_TOTAL_S1', " +
        "IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA IN(SELECT lane FROM filter_directions WHERE direction NOT IN (SELECT direction FROM filter_directions WHERE lane = 1)), d.VEL_MEDIA_TOTAL , NULL), 0)), 0), 0) 'VEL_MEDIA_TOTAL_S2' " +  
					    
	    "FROM "+RoadConcessionaire.tableDados15+" d " +
	    "INNER JOIN sat_equipment eq on (eq.equip_id = d.nome_estacao) " +
	    "WHERE DATA_HORA BETWEEN DATE_SUB( ? , INTERVAL 6 HOUR) AND ? AND eq.visible = 1 " +
	    "GROUP BY d.NOME_ESTACAO " +
	    "ORDER BY d.DATA_HORA ASC";
					
	  try {
			
		  conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
		  
			ps = conn.prepareStatement(select);			
			ps.setString(1, currentDate);
			ps.setString(2, currentDate);
						
			rs = ps.executeQuery();
			
			//System.out.println("06 HR: "+select);
			
			if (rs != null) {
				while (rs.next()) {
					
					SAT sat = new SAT();

					sat.setEquip_id(rs.getInt("d.NOME_ESTACAO"));
					sat.setQuantidadeS1(rs.getInt("VOLUME_TOTAL_S1"));						
					sat.setVelocidadeS1(rs.getInt("VEL_MEDIA_TOTAL_S1"));	
					sat.setQuantidadeS2(rs.getInt("VOLUME_TOTAL_S2"));	
					sat.setVelocidadeS2(rs.getInt("VEL_MEDIA_TOTAL_S2"));	
															
					list.add(sat);
				}				
			 }			

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {ConnectionFactory.closeConnection(conn, ps, rs);}

				
		return list;
		
	}
			
   // -------------------------------------------------------------------------------------------------------------------------------------------------
    	
        public SAT data03HSingle(int equip) throws Exception {
    		
    		SAT sat = new SAT();
    		DateTimeApplication dta = new DateTimeApplication();
    		
    		String currentDate = null;
    			
    		Calendar calendar = Calendar.getInstance();	
    		int minute = calendar.get(Calendar.MINUTE);
    		
    		//Obter datas formatadas para os dados
    		currentDate = dta.getCurrentDateDados15CCR(calendar, minute);
    		    		    					
    		String select = "SELECT d.NOME_ESTACAO, d.DATA_HORA, " +
    		
            "IFNULL(ROUND(SUM(IF(d.NOME_FAIXA IN(SELECT lane FROM filter_directions WHERE direction = (SELECT direction FROM filter_directions WHERE lane = 1)), d.VOLUME_TOTAL , NULL)), 0), 0) 'VOLUME_TOTAL_S1', " +
            "IFNULL(ROUND(SUM(IF(d.NOME_FAIXA IN(SELECT lane FROM filter_directions WHERE direction NOT IN (SELECT direction FROM filter_directions WHERE lane = 1)), d.VOLUME_TOTAL , NULL)), 0), 0) 'VOLUME_TOTAL_S2', " +

            "IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA IN(SELECT lane FROM filter_directions WHERE direction = (SELECT direction FROM filter_directions WHERE lane = 1)), d.VEL_MEDIA_TOTAL , NULL), 0)), 0), 0) 'VEL_MEDIA_TOTAL_S1', " +
            "IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA IN(SELECT lane FROM filter_directions WHERE direction NOT IN (SELECT direction FROM filter_directions WHERE lane = 1)), d.VEL_MEDIA_TOTAL , NULL), 0)), 0), 0) 'VEL_MEDIA_TOTAL_S2' " +  
    				    
    	    "FROM "+RoadConcessionaire.tableDados15+" d " +
    	    "INNER JOIN sat_equipment eq on (eq.equip_id = d.nome_estacao) " +
    	    "WHERE eq.equip_id = ? AND DATA_HORA BETWEEN DATE_SUB( ? , INTERVAL 3 HOUR) AND ? AND eq.visible = 1 ";
    	  					
    	  try {
    			
    		  conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
    			
    			ps = conn.prepareStatement(select);
    			ps.setInt(1, equip);	
    			ps.setString(2, currentDate);
    			ps.setString(3, currentDate);
    						
    			rs = ps.executeQuery();
    			
    			//System.out.println("03 HR SG: "+select);
    			
    			if (rs != null) {
    				while (rs.next()) {
    				
    					sat.setEquip_id(rs.getInt("d.NOME_ESTACAO"));
    					sat.setQuantidadeS1(rs.getInt("VOLUME_TOTAL_S1"));						
    					sat.setVelocidadeS1(rs.getInt("VEL_MEDIA_TOTAL_S1"));	
    					sat.setQuantidadeS2(rs.getInt("VOLUME_TOTAL_S2"));	
    					sat.setVelocidadeS2(rs.getInt("VEL_MEDIA_TOTAL_S2"));	
    									
    				}				
    			 }			

    		} catch (SQLException e) {
    			e.printStackTrace();
    		}finally {ConnectionFactory.closeConnection(conn, ps, rs);}

    				
    		return sat;
    		
    	}        
        
    // -------------------------------------------------------------------------------------------------------------------------------------------------
           
     public SAT data06HSingle(int equip) throws Exception {
    		
    		SAT sat = new SAT();
    		DateTimeApplication dta = new DateTimeApplication();
    		
    		String currentDate = null;
    			
    		Calendar calendar = Calendar.getInstance();	
    		int minute = calendar.get(Calendar.MINUTE);
    		
    		//Obter datas formatadas para os dados
    		currentDate = dta.getCurrentDateDados15CCR(calendar, minute);
    		    					
    		String select = "SELECT d.NOME_ESTACAO, d.DATA_HORA, " +
    		
            "IFNULL(ROUND(SUM(IF(d.NOME_FAIXA IN(SELECT lane FROM filter_directions WHERE direction = (SELECT direction FROM filter_directions WHERE lane = 1)), d.VOLUME_TOTAL , NULL)), 0), 0) 'VOLUME_TOTAL_S1', " +
            "IFNULL(ROUND(SUM(IF(d.NOME_FAIXA IN(SELECT lane FROM filter_directions WHERE direction NOT IN (SELECT direction FROM filter_directions WHERE lane = 1)), d.VOLUME_TOTAL , NULL)), 0), 0) 'VOLUME_TOTAL_S2', " +

            "IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA IN(SELECT lane FROM filter_directions WHERE direction = (SELECT direction FROM filter_directions WHERE lane = 1)), d.VEL_MEDIA_TOTAL , NULL), 0)), 0), 0) 'VEL_MEDIA_TOTAL_S1', " +
            "IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA IN(SELECT lane FROM filter_directions WHERE direction NOT IN (SELECT direction FROM filter_directions WHERE lane = 1)), d.VEL_MEDIA_TOTAL , NULL), 0)), 0), 0) 'VEL_MEDIA_TOTAL_S2' " +  
	    				    
    	    "FROM "+RoadConcessionaire.tableDados15+" d " +
    	    "INNER JOIN sat_equipment eq on (eq.equip_id = d.nome_estacao) " +
    	    "WHERE eq.equip_id = ? AND DATA_HORA BETWEEN DATE_SUB( ? , INTERVAL 6 HOUR) AND ? AND eq.visible = 1 ";
    	  					
    	  try {
    			
    		  conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
    			
    			ps = conn.prepareStatement(select);
    			ps.setInt(1, equip);	
    			ps.setString(2, currentDate);
    			ps.setString(3, currentDate);
    						
    			rs = ps.executeQuery();
    			
    			// System.out.println("06 HR SG: "+select);
    			
    			if (rs != null) {
    				while (rs.next()) {
    				
    					sat.setEquip_id(rs.getInt("d.NOME_ESTACAO"));
    					sat.setQuantidadeS1(rs.getInt("VOLUME_TOTAL_S1"));						
    					sat.setVelocidadeS1(rs.getInt("VEL_MEDIA_TOTAL_S1"));	
    					sat.setQuantidadeS2(rs.getInt("VOLUME_TOTAL_S2"));	
    					sat.setVelocidadeS2(rs.getInt("VEL_MEDIA_TOTAL_S2"));	
    									
    				}				
    			 }			

    		} catch (SQLException e) {
    			e.printStackTrace();
    		}finally {ConnectionFactory.closeConnection(conn, ps, rs);}

    				
    		return sat;
    		
    	}        
        
    // -------------------------------------------------------------------------------------------------------------------------------------------------
           
}
