package br.com.tracevia.webapp.dao.sat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.model.global.ListEquipments;
import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.model.sat.SAT;
import br.com.tracevia.webapp.util.ConnectionFactory;

public class DataSatDAO {
			
	private Connection conn;			
	private PreparedStatement ps;
	private ResultSet rs;
		
	public List<SAT> dataInterval(ListEquipments equips, String queryInterval) throws Exception {
		
		List<SAT> list = new ArrayList<SAT>();
		DateTimeApplication dta = new DateTimeApplication();
		
		int limit = equips.getSatList().size();
									
		String currentDate = null;
			
		Calendar calendar = Calendar.getInstance();	
		int minute = calendar.get(Calendar.MINUTE);
		
		// Obter datas formatadas para os dados
		currentDate = dta.getDataInterval15Min(calendar, minute);
							
		String select = "SELECT d.NOME_ESTACAO, d.DATA_HORA, date_format(d.DATA_HORA, '%H:%i') 'DADO_HORA', " +
			
        "CASE " +
		"WHEN eq.number_lanes = 2 AND (eq.dir_lane1 <> eq.dir_lane2) THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 1, d.VOLUME_TOTAL , NULL)), 0), 0) " +
		"WHEN eq.number_lanes = 3 THEN " +
		"CASE " + 
		"WHEN (eq.dir_lane1 = eq.dir_lane2) THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 1 OR d.NOME_FAIXA = 2, d.VOLUME_TOTAL , NULL)), 0), 0) " +	
		"ELSE IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 1 , d.VOLUME_TOTAL , NULL)), 0), 0) " +
		"END " +
		
		"WHEN (eq.number_lanes = 4 AND eq.dir_lane1 = eq.dir_lane2) THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 1 OR d.NOME_FAIXA = 2, d.VOLUME_TOTAL , NULL )), 0), 0) " +
		
		"WHEN eq.number_lanes = 5 THEN " +
		"CASE " + 
		"WHEN (eq.dir_lane1 = eq.dir_lane2 AND eq.dir_lane1 = eq.dir_lane3) THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 1 OR d.NOME_FAIXA = 2 OR d.NOME_FAIXA = 3, d.VOLUME_TOTAL , NULL)), 0), 0) " +	 
		"ELSE IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 1 OR d.NOME_FAIXA = 2, d.VOLUME_TOTAL , NULL)), 0), 0) " +
		"END " +
		
		"WHEN eq.number_lanes = 6 AND (eq.dir_lane1 = eq.dir_lane2 AND eq.dir_lane1 = eq.dir_lane3) THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 1 OR d.NOME_FAIXA = 2 OR d.NOME_FAIXA = 3, d.VOLUME_TOTAL , NULL)), 0), 0) " +
		
		"WHEN eq.number_lanes = 7 THEN " +
		"CASE " + "WHEN (dir_lane1 = dir_lane2 AND dir_lane1 = dir_lane3 AND dir_lane1 = dir_lane4) THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 1 OR d.NOME_FAIXA = 2 OR d.NOME_FAIXA = 3 OR d.NOME_FAIXA = 4, d.VOLUME_TOTAL , NULL)), 0), 0) " +
		"ELSE IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 1 OR d.NOME_FAIXA = 2 OR d.NOME_FAIXA = 3, d.VOLUME_TOTAL , NULL)), 0), 0) " + 
		"END " +
		"WHEN eq.number_lanes = 8 AND (eq.dir_lane1 = eq.dir_lane2 AND eq.dir_lane1 = eq.dir_lane3 AND eq.dir_lane1 = eq.dir_lane4) THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 1 OR d.NOME_FAIXA = 2 OR d.NOME_FAIXA = 3 OR d.NOME_FAIXA = 4, d.VOLUME_TOTAL , NULL )), 0), 0) " +
		"ELSE 0 " +
		"END 'VOLUME_TOTAL_S1', " +
		
		"CASE " +
		"WHEN eq.number_lanes = 2 AND (eq.dir_lane1 <> eq.dir_lane2) THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 2, d.VOLUME_TOTAL , NULL)), 0), 0) " +
		
		"WHEN eq.number_lanes = 3 THEN " +
		"CASE " + "WHEN (eq.dir_lane1 <> eq.dir_lane2) THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 2 OR d.NOME_FAIXA = 3 , d.VOLUME_TOTAL , NULL)), 0), 0) " +
		"ELSE IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 3 , d.VOLUME_TOTAL , NULL)), 0), 0) " + 
		"END " +
		
		"WHEN (eq.number_lanes = 4 AND eq.dir_lane3 = eq.dir_lane4) THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 3 OR d.NOME_FAIXA = 4, d.VOLUME_TOTAL , NULL )), 0), 0) " +
		
		"WHEN eq.number_lanes = 5 THEN " +
		"CASE " + 
		"WHEN (eq.dir_lane3 = eq.dir_lane4 AND eq.dir_lane3 = eq.dir_lane5) THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 4 OR d.NOME_FAIXA = 5 OR d.NOME_FAIXA = 6, d.VOLUME_TOTAL , NULL)), 0), 0) " +   
		"ELSE IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 4 OR d.NOME_FAIXA = 5 , d.VOLUME_TOTAL , NULL)), 0), 0) " +
		"END " +
		
		"WHEN eq.number_lanes = 6 AND (eq.dir_lane4 = eq.dir_lane5 AND eq.dir_lane4 = eq.dir_lane6) THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 4 OR d.NOME_FAIXA = 5 OR d.NOME_FAIXA = 6, d.VOLUME_TOTAL , NULL )), 0), 0) " +
		
		"WHEN eq.number_lanes = 7 THEN " +
		"CASE " + "WHEN (dir_lane4 = dir_lane5 AND dir_lane4 = dir_lane6 AND dir_lane4 = dir_lane7) THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 4 OR d.NOME_FAIXA = 5 OR d.NOME_FAIXA = 6 OR d.NOME_FAIXA = 7, d.VOLUME_TOTAL , NULL)), 0), 0) " +
		"ELSE IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 5 OR d.NOME_FAIXA = 6 OR d.NOME_FAIXA = 7, d.VOLUME_TOTAL , NULL)), 0), 0) " + 
		"END " +
		"WHEN eq.number_lanes = 8 AND (eq.dir_lane5 = eq.dir_lane6 AND eq.dir_lane5 = eq.dir_lane7 AND eq.dir_lane5 = eq.dir_lane8) THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 5 OR d.NOME_FAIXA = 6 OR d.NOME_FAIXA = 7 OR d.NOME_FAIXA = 8, d.VOLUME_TOTAL , NULL )), 0), 0) " +
		"ELSE 0 " +
		"END 'VOLUME_TOTAL_S2', " +
		
		"CASE " +
		"WHEN eq.number_lanes = 2 AND (eq.dir_lane1 <> eq.dir_lane2) THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA = 1, d.VEL_MEDIA_TOTAL, NULL),0)), 0), 0) " +  
		"WHEN eq.number_lanes = 3 THEN " +
		"CASE " + 
		"WHEN (eq.dir_lane1 = eq.dir_lane2) THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA = 1 OR d.NOME_FAIXA = 2, d.VEL_MEDIA_TOTAL, NULL),0)), 0), 0) " +	
		"ELSE IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA = 1 , d.VEL_MEDIA_TOTAL , NULL),0)), 0), 0) " +
		"END " +
		
		"WHEN eq.number_lanes = 4 AND (eq.dir_lane1 = eq.dir_lane2) THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA = 1 OR d.NOME_FAIXA = 2, d.VEL_MEDIA_TOTAL, NULL ),0)), 0), 0) " +
		
		"WHEN eq.number_lanes = 5 THEN " +
		"CASE " + 
		"WHEN (eq.dir_lane1 = eq.dir_lane2 AND eq.dir_lane1 = eq.dir_lane3) THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA = 1 OR d.NOME_FAIXA = 2 OR d.NOME_FAIXA = 3, d.VEL_MEDIA_TOTAL , NULL),0)), 0), 0) " +	 
		"ELSE IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA = 1 OR d.NOME_FAIXA = 2, d.VEL_MEDIA_TOTAL, NULL),0)), 0), 0) " +
		"END " +
		
		"WHEN eq.number_lanes = 6 AND (eq.dir_lane1 = eq.dir_lane2 AND eq.dir_lane1 = eq.dir_lane3) THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA = 1 OR d.NOME_FAIXA = 2 OR d.NOME_FAIXA = 3, d.VEL_MEDIA_TOTAL, NULL ),0)), 0), 0) " +
		
		"WHEN eq.number_lanes = 7 THEN " +
		"CASE " + "WHEN (dir_lane1 = dir_lane2 AND dir_lane1 = dir_lane3 AND dir_lane1 = dir_lane4)THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA = 1 OR d.NOME_FAIXA = 2 OR d.NOME_FAIXA = 3 OR d.NOME_FAIXA = 4, d.VEL_MEDIA_TOTAL, NULL),0)), 0), 0) " +
		"ELSE IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA = 1 OR d.NOME_FAIXA = 2 OR d.NOME_FAIXA = 3, d.VEL_MEDIA_TOTAL, NULL),0)), 0), 0) " + 
		"END " +
		"WHEN eq.number_lanes = 8 AND (eq.dir_lane1 = eq.dir_lane2 AND eq.dir_lane1 = eq.dir_lane3 AND eq.dir_lane1 = eq.dir_lane4) THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA = 1 OR d.NOME_FAIXA = 2 OR d.NOME_FAIXA = 3 OR d.NOME_FAIXA = 4, d.VEL_MEDIA_TOTAL, NULL ),0)), 0), 0) " +
		"ELSE 0 " +
		"END 'VEL_MEDIA_TOTAL_S1', " +
		
		"CASE " +
		"WHEN eq.number_lanes = 2 AND (eq.dir_lane1 <> eq.dir_lane2) THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA = 2, d.VEL_MEDIA_TOTAL , NULL),0)), 0), 0) " +
		
		"WHEN eq.number_lanes = 3 THEN " +
		"CASE " + "WHEN (eq.dir_lane1 <> eq.dir_lane2) THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA = 2 OR d.NOME_FAIXA = 3 , d.VEL_MEDIA_TOTAL , NULL),0)), 0), 0) " +
		"ELSE IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA = 3 , d.VEL_MEDIA_TOTAL , NULL),0)), 0), 0) " +
		"END " +
		
		"WHEN eq.number_lanes = 4 AND (eq.dir_lane3 = eq.dir_lane4) THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA = 3 OR d.NOME_FAIXA = 4, d.VEL_MEDIA_TOTAL , NULL),0)), 0), 0) " +
		
		"WHEN eq.number_lanes = 5 THEN " +
		"CASE " + 
		"WHEN (eq.dir_lane3 = eq.dir_lane4 AND eq.dir_lane3 = eq.dir_lane5) THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA = 3 OR d.NOME_FAIXA = 4 OR d.NOME_FAIXA = 5, d.VEL_MEDIA_TOTAL , NULL),0)), 0), 0) " +   
		"ELSE IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA = 4 OR d.NOME_FAIXA = 5 , d.VEL_MEDIA_TOTAL , NULL),0)), 0), 0) " +
		"END " +
		
		"WHEN eq.number_lanes = 6 AND (eq.dir_lane4 = eq.dir_lane5 AND eq.dir_lane4 = eq.dir_lane6) THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA = 4 OR d.NOME_FAIXA = 5 OR d.NOME_FAIXA = 6, d.VEL_MEDIA_TOTAL , NULL ),0)), 0), 0) " +
		
		"WHEN eq.number_lanes = 7 THEN " +
		"CASE " + "WHEN (dir_lane4 = dir_lane5 AND dir_lane4 = dir_lane6 AND dir_lane4 = dir_lane7) THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA = 4 OR d.NOME_FAIXA = 5 OR d.NOME_FAIXA = 6 OR d.NOME_FAIXA = 7, d.VEL_MEDIA_TOTAL , NULL),0)), 0), 0) " +
		"ELSE IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA = 5 OR d.NOME_FAIXA = 6 OR d.NOME_FAIXA = 7, d.VEL_MEDIA_TOTAL , NULL),0)), 0), 0) " +
		"END " +
		"WHEN eq.number_lanes = 8 AND (eq.dir_lane5 = eq.dir_lane6 AND eq.dir_lane5 = eq.dir_lane7 AND eq.dir_lane5 = eq.dir_lane8) THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA = 5 OR d.NOME_FAIXA = 6 OR d.NOME_FAIXA = 7 OR d.NOME_FAIXA = 8, d.VEL_MEDIA_TOTAL, NULL),0)), 0), 0) " +
		"ELSE 0 " +
		"END 'VEL_MEDIA_TOTAL_S2' " +
	    	    
	    "FROM "+RoadConcessionaire.tableDados15+" d " +
	    "INNER JOIN sat_equipment eq on (eq.equip_id = d.nome_estacao) " +
	    "WHERE " + queryInterval +
	    "GROUP BY d.DATA_HORA, d.NOME_ESTACAO " +
        "ORDER BY d.NOME_ESTACAO, d.DATA_HORA DESC " +
	    "LIMIT "+limit + " ";		
	 
	  try {
			
		    conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
			
			ps = conn.prepareStatement(select);			
			ps.setString(1, currentDate);		
			ps.setString(2, currentDate);
			
			rs = ps.executeQuery();
			
		  //  System.out.println(select);
			
			if (rs != null) {
				while (rs.next()) {
					
					SAT sat = new SAT();

					sat.setEquip_id(rs.getInt("d.NOME_ESTACAO"));
					sat.setDataTime(rs.getString("DADO_HORA"));
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
           
     public SAT dataIntervalSingle(int equip, String interval) throws Exception {
    		
    		SAT sat = new SAT();
    		DateTimeApplication dta = new DateTimeApplication();
    		
    		String currentDate = null;
    			
    		Calendar calendar = Calendar.getInstance();	
    		int minute = calendar.get(Calendar.MINUTE);
    		
    		//Obter datas formatadas para os dados
    		currentDate = dta.getDataInterval15Min(calendar, minute);
    		    					
    		String select = "SELECT d.NOME_ESTACAO, d.DATA_HORA, date_format(d.DATA_HORA, '%H:%i') 'DADO_HORA', " +
    		
		    "CASE " +
			"WHEN eq.number_lanes = 2 AND (eq.dir_lane1 <> eq.dir_lane2) THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 1, d.VOLUME_TOTAL , NULL)), 0), 0) " +
			"WHEN eq.number_lanes = 3 THEN " +
			"CASE " + 
			"WHEN (eq.dir_lane1 = eq.dir_lane2) THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 1 OR d.NOME_FAIXA = 2, d.VOLUME_TOTAL , NULL)), 0), 0) " +	
			"ELSE IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 1 , d.VOLUME_TOTAL , NULL)), 0), 0) " +
			"END " +
			
			"WHEN (eq.number_lanes = 4 AND eq.dir_lane1 = eq.dir_lane2) THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 1 OR d.NOME_FAIXA = 2, d.VOLUME_TOTAL , NULL )), 0), 0) " +
			
			"WHEN eq.number_lanes = 5 THEN " +
			"CASE " + 
			"WHEN (eq.dir_lane1 = eq.dir_lane2 AND eq.dir_lane1 = eq.dir_lane3) THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 1 OR d.NOME_FAIXA = 2 OR d.NOME_FAIXA = 3, d.VOLUME_TOTAL , NULL)), 0), 0) " +	 
			"ELSE IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 1 OR d.NOME_FAIXA = 2, d.VOLUME_TOTAL , NULL)), 0), 0) " +
			"END " +
			
			"WHEN eq.number_lanes = 6 AND (eq.dir_lane1 = eq.dir_lane2 AND eq.dir_lane1 = eq.dir_lane3) THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 1 OR d.NOME_FAIXA = 2 OR d.NOME_FAIXA = 3, d.VOLUME_TOTAL , NULL)), 0), 0) " +
			
			"WHEN eq.number_lanes = 7 THEN " +
			"CASE " + "WHEN (dir_lane1 = dir_lane2 AND dir_lane1 = dir_lane3 AND dir_lane1 = dir_lane4) THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 1 OR d.NOME_FAIXA = 2 OR d.NOME_FAIXA = 3 OR d.NOME_FAIXA = 4, d.VOLUME_TOTAL , NULL)), 0), 0) " +
			"ELSE IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 1 OR d.NOME_FAIXA = 2 OR d.NOME_FAIXA = 3, d.VOLUME_TOTAL , NULL)), 0), 0) " + 
			"END " +
			"WHEN eq.number_lanes = 8 AND (eq.dir_lane1 = eq.dir_lane2 AND eq.dir_lane1 = eq.dir_lane3 AND eq.dir_lane1 = eq.dir_lane4) THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 1 OR d.NOME_FAIXA = 2 OR d.NOME_FAIXA = 3 OR d.NOME_FAIXA = 4, d.VOLUME_TOTAL , NULL )), 0), 0) " +
			"ELSE 0 " +
			"END 'VOLUME_TOTAL_S1', " +
			
			"CASE " +
			"WHEN eq.number_lanes = 2 AND (eq.dir_lane1 <> eq.dir_lane2) THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 2, d.VOLUME_TOTAL , NULL)), 0), 0) " +
			
			"WHEN eq.number_lanes = 3 THEN " +
			"CASE " + "WHEN (eq.dir_lane1 <> eq.dir_lane2) THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 2 OR d.NOME_FAIXA = 3 , d.VOLUME_TOTAL , NULL)), 0), 0) " +
			"ELSE IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 3 , d.VOLUME_TOTAL , NULL)), 0), 0) " + 
			"END " +
			
			"WHEN (eq.number_lanes = 4 AND eq.dir_lane3 = eq.dir_lane4) THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 3 OR d.NOME_FAIXA = 4, d.VOLUME_TOTAL , NULL )), 0), 0) " +
			
			"WHEN eq.number_lanes = 5 THEN " +
			"CASE " + 
			"WHEN (eq.dir_lane3 = eq.dir_lane4 AND eq.dir_lane3 = eq.dir_lane5) THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 4 OR d.NOME_FAIXA = 5 OR d.NOME_FAIXA = 6, d.VOLUME_TOTAL , NULL)), 0), 0) " +   
			"ELSE IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 4 OR d.NOME_FAIXA = 5 , d.VOLUME_TOTAL , NULL)), 0), 0) " +
			"END " +
			
			"WHEN eq.number_lanes = 6 AND (eq.dir_lane4 = eq.dir_lane5 AND eq.dir_lane4 = eq.dir_lane6) THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 4 OR d.NOME_FAIXA = 5 OR d.NOME_FAIXA = 6, d.VOLUME_TOTAL , NULL )), 0), 0) " +
			
			"WHEN eq.number_lanes = 7 THEN " +
			"CASE " + "WHEN (dir_lane4 = dir_lane5 AND dir_lane4 = dir_lane6 AND dir_lane4 = dir_lane7) THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 4 OR d.NOME_FAIXA = 5 OR d.NOME_FAIXA = 6 OR d.NOME_FAIXA = 7, d.VOLUME_TOTAL , NULL)), 0), 0) " +
			"ELSE IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 5 OR d.NOME_FAIXA = 6 OR d.NOME_FAIXA = 7, d.VOLUME_TOTAL , NULL)), 0), 0) " + 
			"END " +
			"WHEN eq.number_lanes = 8 AND (eq.dir_lane5 = eq.dir_lane6 AND eq.dir_lane5 = eq.dir_lane7 AND eq.dir_lane5 = eq.dir_lane8) THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 5 OR d.NOME_FAIXA = 6 OR d.NOME_FAIXA = 7 OR d.NOME_FAIXA = 8, d.VOLUME_TOTAL , NULL )), 0), 0) " +
			"ELSE 0 " +
			"END 'VOLUME_TOTAL_S2', " +
			
			"CASE " +
			"WHEN eq.number_lanes = 2 AND (eq.dir_lane1 <> eq.dir_lane2) THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA = 1, d.VEL_MEDIA_TOTAL, NULL),0)), 0), 0) " +  
			"WHEN eq.number_lanes = 3 THEN " +
			"CASE " + 
			"WHEN (eq.dir_lane1 = eq.dir_lane2) THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA = 1 OR d.NOME_FAIXA = 2, d.VEL_MEDIA_TOTAL, NULL),0)), 0), 0) " +	
			"ELSE IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA = 1 , d.VEL_MEDIA_TOTAL , NULL),0)), 0), 0) " +
			"END " +
			
			"WHEN eq.number_lanes = 4 AND (eq.dir_lane1 = eq.dir_lane2) THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA = 1 OR d.NOME_FAIXA = 2, d.VEL_MEDIA_TOTAL, NULL ),0)), 0), 0) " +
			
			"WHEN eq.number_lanes = 5 THEN " +
			"CASE " + 
			"WHEN (eq.dir_lane1 = eq.dir_lane2 AND eq.dir_lane1 = eq.dir_lane3) THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA = 1 OR d.NOME_FAIXA = 2 OR d.NOME_FAIXA = 3, d.VEL_MEDIA_TOTAL , NULL),0)), 0), 0) " +	 
			"ELSE IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA = 1 OR d.NOME_FAIXA = 2, d.VEL_MEDIA_TOTAL, NULL),0)), 0), 0) " +
			"END " +
			
			"WHEN eq.number_lanes = 6 AND (eq.dir_lane1 = eq.dir_lane2 AND eq.dir_lane1 = eq.dir_lane3) THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA = 1 OR d.NOME_FAIXA = 2 OR d.NOME_FAIXA = 3, d.VEL_MEDIA_TOTAL, NULL ),0)), 0), 0) " +
			
			"WHEN eq.number_lanes = 7 THEN " +
			"CASE " + "WHEN (dir_lane1 = dir_lane2 AND dir_lane1 = dir_lane3 AND dir_lane1 = dir_lane4)THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA = 1 OR d.NOME_FAIXA = 2 OR d.NOME_FAIXA = 3 OR d.NOME_FAIXA = 4, d.VEL_MEDIA_TOTAL, NULL),0)), 0), 0) " +
			"ELSE IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA = 1 OR d.NOME_FAIXA = 2 OR d.NOME_FAIXA = 3, d.VEL_MEDIA_TOTAL, NULL),0)), 0), 0) " + 
			"END " +
			"WHEN eq.number_lanes = 8 AND (eq.dir_lane1 = eq.dir_lane2 AND eq.dir_lane1 = eq.dir_lane3 AND eq.dir_lane1 = eq.dir_lane4) THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA = 1 OR d.NOME_FAIXA = 2 OR d.NOME_FAIXA = 3 OR d.NOME_FAIXA = 4, d.VEL_MEDIA_TOTAL, NULL ),0)), 0), 0) " +
			"ELSE 0 " +
			"END 'VEL_MEDIA_TOTAL_S1', " +
			
			"CASE " +
			"WHEN eq.number_lanes = 2 AND (eq.dir_lane1 <> eq.dir_lane2) THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA = 2, d.VEL_MEDIA_TOTAL , NULL),0)), 0), 0) " +
			
			"WHEN eq.number_lanes = 3 THEN " +
			"CASE " + "WHEN (eq.dir_lane1 <> eq.dir_lane2) THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA = 2 OR d.NOME_FAIXA = 3 , d.VEL_MEDIA_TOTAL , NULL),0)), 0), 0) " +
			"ELSE IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA = 3 , d.VEL_MEDIA_TOTAL , NULL),0)), 0), 0) " +
			"END " +
			
			"WHEN eq.number_lanes = 4 AND (eq.dir_lane3 = eq.dir_lane4) THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA = 3 OR d.NOME_FAIXA = 4, d.VEL_MEDIA_TOTAL , NULL),0)), 0), 0) " +
			
			"WHEN eq.number_lanes = 5 THEN " +
			"CASE " + 
			"WHEN (eq.dir_lane3 = eq.dir_lane4 AND eq.dir_lane3 = eq.dir_lane5) THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA = 3 OR d.NOME_FAIXA = 4 OR d.NOME_FAIXA = 5, d.VEL_MEDIA_TOTAL , NULL),0)), 0), 0) " +   
			"ELSE IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA = 4 OR d.NOME_FAIXA = 5 , d.VEL_MEDIA_TOTAL , NULL),0)), 0), 0) " +
			"END " +
			
			"WHEN eq.number_lanes = 6 AND (eq.dir_lane4 = eq.dir_lane5 AND eq.dir_lane4 = eq.dir_lane6) THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA = 4 OR d.NOME_FAIXA = 5 OR d.NOME_FAIXA = 6, d.VEL_MEDIA_TOTAL , NULL ),0)), 0), 0) " +
			
			"WHEN eq.number_lanes = 7 THEN " +
			"CASE " + "WHEN (dir_lane4 = dir_lane5 AND dir_lane4 = dir_lane6 AND dir_lane4 = dir_lane7) THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA = 4 OR d.NOME_FAIXA = 5 OR d.NOME_FAIXA = 6 OR d.NOME_FAIXA = 7, d.VEL_MEDIA_TOTAL , NULL),0)), 0), 0) " +
			"ELSE IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA = 5 OR d.NOME_FAIXA = 6 OR d.NOME_FAIXA = 7, d.VEL_MEDIA_TOTAL , NULL),0)), 0), 0) " +
			"END " +
			"WHEN eq.number_lanes = 8 AND (eq.dir_lane5 = eq.dir_lane6 AND eq.dir_lane5 = eq.dir_lane7 AND eq.dir_lane5 = eq.dir_lane8) THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA = 5 OR d.NOME_FAIXA = 6 OR d.NOME_FAIXA = 7 OR d.NOME_FAIXA = 8, d.VEL_MEDIA_TOTAL, NULL),0)), 0), 0) " +
			"ELSE 0 " +
			"END 'VEL_MEDIA_TOTAL_S2' " +
 			    
    	    "FROM "+RoadConcessionaire.tableDados15+" d " +
    	    "INNER JOIN sat_equipment eq on (eq.equip_id = d.nome_estacao) " +
    	    "WHERE eq.equip_id = ? AND " + interval + " "+  
    	    "GROUP BY d.DATA_HORA " +
            "ORDER BY d.DATA_HORA DESC LIMIT 1 ";
    	  					
    	  try {
    			
    		    conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
    			
    			ps = conn.prepareStatement(select);
    			ps.setInt(1, equip);	
    			ps.setString(2, currentDate);
    			ps.setString(3, currentDate);
    						
    			rs = ps.executeQuery();
    			
    			// System.out.println(select);
    			
    			if (rs != null) {
    				while (rs.next()) {
    				
    					sat.setEquip_id(rs.getInt("d.NOME_ESTACAO"));
    					sat.setDataTime(rs.getString("DADO_HORA"));
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
              
     public String dataTimeLastRegister(int equip) throws Exception {
 		 		  	 	 		    					
 		String select = "SELECT IF(DATEDIFF(NOW(), d.DATA_HORA) > 0, date_format(d.DATA_HORA, '%d/%m/%y %H:%i'), date_format(d.DATA_HORA, '%H:%i')) 'DADO_HORA' " +
 				   			    
 	    "FROM "+RoadConcessionaire.tableDados15+" d " +
 	    "INNER JOIN sat_equipment eq on (eq.equip_id = d.nome_estacao) " +
 	    "WHERE eq.equip_id = ? AND eq.visible = 1 " +
 	    "ORDER BY d.NOME_ESTACAO DESC LIMIT 1";
 		
 		String  satLastRegister = "";
 	  					
 	  try {
 			
 		    conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
 			
 			ps = conn.prepareStatement(select);
 			ps.setInt(1, equip);	
 			 						
 			rs = ps.executeQuery();
 			
 			// System.out.println(select);
 			 		 			
 			if (rs != null) {
 				while (rs.next()) {
 					 					 		 					
 					satLastRegister = rs.getString("DADO_HORA");
 				 									
 				}				
 			 }			

 		} catch (SQLException e) {
 			e.printStackTrace();
 		}finally {ConnectionFactory.closeConnection(conn, ps, rs);}

 				
 		return satLastRegister;
 		
 	}        
     
 // -------------------------------------------------------------------------------------------------------------------------------------------------
  
}
