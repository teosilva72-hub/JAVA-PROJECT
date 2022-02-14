package br.com.tracevia.webapp.dao.sat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.model.global.ListEquipments;
import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.model.global.SQL_Tracevia;
import br.com.tracevia.webapp.model.global.ColumnsSql.RowResult;
import br.com.tracevia.webapp.model.global.ResultSql.MapResult;
import br.com.tracevia.webapp.model.sat.SAT;

public class DataSatDAO {
			
	SQL_Tracevia conn = new SQL_Tracevia();
		
	public List<SAT> dataInterval(ListEquipments equips, String interval, int time) throws Exception {
		
		System.out.println(interval+""+time);
		
		List<SAT> list = new ArrayList<SAT>();
		DateTimeApplication dta = new DateTimeApplication();
		
		int limit = equips.getSatList().size();
									
		String currentDate = null;
			
		Calendar calendar = Calendar.getInstance();	
		int minute = calendar.get(Calendar.MINUTE);
		
		// Obter datas formatadas para os dados
		currentDate = dta.getDataInterval15Min(calendar, minute);
							
		String select = "SELECT d.NOME_ESTACAO AS ESTACAO, " +
				"CASE WHEN " +
 				"MINUTE(45) THEN CONCAT(DATE_FORMAT(d.DATA_HORA , '%H:%i -'), DATE_FORMAT(DATE_ADD(d.DATA_HORA , INTERVAL 14 MINUTE), ' %H:%i')) " +
 				"ELSE CONCAT(DATE_FORMAT(d.DATA_HORA , '%H:%i -'), DATE_FORMAT(DATE_ADD(d.DATA_HORA , INTERVAL 15 MINUTE), ' %H:%i')) END 'PACOTE_HORA', " +
 				"DATE_FORMAT(v.data, '%H:%i') 'DADO_HORA', " +
			
		"SUM(CASE " +
			"WHEN (eq.dir_lane1 = eq.dir_lane2 AND eq.dir_lane1 = eq.dir_lane3 AND eq.dir_lane1 = eq.dir_lane4 AND d.NOME_FAIXA < 5) " +
			  "OR (eq.dir_lane1 = eq.dir_lane2 AND eq.dir_lane1 = eq.dir_lane3 AND d.NOME_FAIXA < 4) " +
			  "OR (eq.dir_lane1 = eq.dir_lane2 AND d.NOME_FAIXA < 3) " +
			  "OR (d.NOME_FAIXA = 1) " +
			"THEN d.VOLUME_TOTAL " +
		"ELSE 0 END) 'VOLUME_TOTAL_S1', " +
		"SUM(CASE " +
			"WHEN (eq.dir_lane1 <> IFNULL(eq.dir_lane2, eq.dir_lane1) AND d.NOME_FAIXA > 1) " +
			  "OR (eq.dir_lane1 <> IFNULL(eq.dir_lane3, eq.dir_lane1) AND d.NOME_FAIXA > 2) " +
			  "OR (eq.dir_lane1 <> IFNULL(eq.dir_lane4, eq.dir_lane1) AND d.NOME_FAIXA > 3) " +
			  "OR (eq.dir_lane1 <> IFNULL(eq.dir_lane5, eq.dir_lane1) AND d.NOME_FAIXA > 4) " +
			"THEN d.VOLUME_TOTAL " +
		"ELSE 0 END) 'VOLUME_TOTAL_S2', " +
		
		"ROUND(AVG(CASE " +
			"WHEN (eq.dir_lane1 = eq.dir_lane2 AND eq.dir_lane1 = eq.dir_lane3 AND eq.dir_lane1 = eq.dir_lane4 AND d.NOME_FAIXA < 5) " +
			  "OR (eq.dir_lane1 = eq.dir_lane2 AND eq.dir_lane1 = eq.dir_lane3 AND d.NOME_FAIXA < 4) " +
			  "OR (eq.dir_lane1 = eq.dir_lane2 AND d.NOME_FAIXA < 3) " +
			  "OR (d.NOME_FAIXA = 1) " +
			"THEN d.VEL_MEDIA_TOTAL " +
		"ELSE NULL END), 0) 'VEL_MEDIA_TOTAL_S1', " +
		"ROUND(AVG(CASE " +
			"WHEN (eq.dir_lane1 <> IFNULL(eq.dir_lane2, eq.dir_lane1) AND d.NOME_FAIXA > 1) " +
			  "OR (eq.dir_lane1 <> IFNULL(eq.dir_lane3, eq.dir_lane1) AND d.NOME_FAIXA > 2) " +
			  "OR (eq.dir_lane1 <> IFNULL(eq.dir_lane4, eq.dir_lane1) AND d.NOME_FAIXA > 3) " +
			  "OR (eq.dir_lane1 <> IFNULL(eq.dir_lane5, eq.dir_lane1) AND d.NOME_FAIXA > 4) " +
			"THEN d.VEL_MEDIA_TOTAL " +
		"ELSE NULL END), 0) 'VEL_MEDIA_TOTAL_S2' " +
	 	 
	 "FROM "+RoadConcessionaire.tableDados15+" d " +
	 "INNER JOIN sat_equipment eq on (eq.equip_id = d.nome_estacao) " +
	 "INNER JOIN tb_vbv v ON (v.siteID = d.nome_estacao) " +
	 "WHERE DATA_HORA BETWEEN DATE_SUB($INTERVAL$) AND ? AND eq.visible = 1 " +
	 "GROUP BY d.DATA_HORA, d.NOME_ESTACAO " +
 	 "ORDER BY d.NOME_ESTACAO, d.DATA_HORA ASC, v.data ASC";
	 
	 try {
			
		 conn.start(1);
			
			conn.prepare_my(select.replace("$INTERVAL$", String.format(" ? , INTERVAL %s %s", time, interval)) + " LIMIT " + limit);
 			conn.prepare_ms(select
			 	.replace("date_format", "FORMAT")
				.replace("%H:%i", "hh:mm")
			 	.replace("IFNULL", "ISNULL")
				.replaceFirst("SELECT", "SELECT TOP " + limit)
				.replace("DATE_SUB($INTERVAL$", String.format("DATEADD(%s, %s, ? ", interval, time)));		
			conn.setString(1, currentDate);		
			conn.setString(2, currentDate);
			
			MapResult result = conn.executeQuery();
			
		 System.out.println(select);
			
			if (result.hasNext()) {
				for (RowResult rs : result) {
					
					SAT sat = new SAT();

					sat.setEquip_id(rs.getInt("ESTACAO"));
					sat.setLastPackage(rs.getString("PACOTE_HORA"));
					sat.setLastRegister(rs.getString("DADO_HORA"));				
					sat.setQuantidadeS1(rs.getInt("VOLUME_TOTAL_S1"));						
					sat.setVelocidadeS1(rs.getInt("VEL_MEDIA_TOTAL_S1"));
					sat.setQuantidadeS2(rs.getInt("VOLUME_TOTAL_S2"));
					sat.setVelocidadeS2(rs.getInt("VEL_MEDIA_TOTAL_S2"));
											
					list.add(sat);
				}
			 }			

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			conn.close();
		}

				
		return list;
		
	}
			 			
  // -------------------------------------------------------------------------------------------------------------------------------------------------
  
  public SAT dataIntervalSingle(int equip, String interval, int time) throws Exception {
 		
 		SAT sat = new SAT();
 		DateTimeApplication dta = new DateTimeApplication();
 		
 		String currentDate = null;
 			
 		Calendar calendar = Calendar.getInstance();	
 		int minute = calendar.get(Calendar.MINUTE);
 		
 		//Obter datas formatadas para os dados
 		currentDate = dta.getDataInterval15Min(calendar, minute);
 		 					
 		String select = "SELECT d.NOME_ESTACAO AS ESTACAO, " +
 				"CASE WHEN " +
 				"MINUTE(45) THEN CONCAT(DATE_FORMAT(d.DATA_HORA , '%H:%i -'), DATE_FORMAT(DATE_ADD(d.DATA_HORA , INTERVAL 14 MINUTE), ' %H:%i')) " +
 				"ELSE CONCAT(DATE_FORMAT(d.DATA_HORA , '%H:%i -'), DATE_FORMAT(DATE_ADD(d.DATA_HORA , INTERVAL 15 MINUTE), ' %H:%i')) END 'PACOTE_HORA', " +
 				"DATE_FORMAT(v.data, '%H:%i') 'DADO_HORA', " +
 		
		 "SUM(CASE " +
			"WHEN (eq.dir_lane1 = eq.dir_lane2 AND eq.dir_lane1 = eq.dir_lane3 AND eq.dir_lane1 = eq.dir_lane4 AND d.NOME_FAIXA < 5) " +
			  "OR (eq.dir_lane1 = eq.dir_lane2 AND eq.dir_lane1 = eq.dir_lane3 AND d.NOME_FAIXA < 4) " +
			  "OR (eq.dir_lane1 = eq.dir_lane2 AND d.NOME_FAIXA < 3) " +
			  "OR (d.NOME_FAIXA = 1) " +
			"THEN d.VOLUME_TOTAL " +
		"ELSE 0 END) 'VOLUME_TOTAL_S1', " +
		"SUM(CASE " +
			"WHEN (eq.dir_lane1 <> IFNULL(eq.dir_lane2, eq.dir_lane1) AND d.NOME_FAIXA > 1) " +
			  "OR (eq.dir_lane1 <> IFNULL(eq.dir_lane3, eq.dir_lane1) AND d.NOME_FAIXA > 2) " +
			  "OR (eq.dir_lane1 <> IFNULL(eq.dir_lane4, eq.dir_lane1) AND d.NOME_FAIXA > 3) " +
			  "OR (eq.dir_lane1 <> IFNULL(eq.dir_lane5, eq.dir_lane1) AND d.NOME_FAIXA > 4) " +
			"THEN d.VOLUME_TOTAL " +
		"ELSE 0 END) 'VOLUME_TOTAL_S2', " +
		
		"ROUND(AVG(CASE " +
			"WHEN (eq.dir_lane1 = eq.dir_lane2 AND eq.dir_lane1 = eq.dir_lane3 AND eq.dir_lane1 = eq.dir_lane4 AND d.NOME_FAIXA < 5) " +
			  "OR (eq.dir_lane1 = eq.dir_lane2 AND eq.dir_lane1 = eq.dir_lane3 AND d.NOME_FAIXA < 4) " +
			  "OR (eq.dir_lane1 = eq.dir_lane2 AND d.NOME_FAIXA < 3) " +
			  "OR (d.NOME_FAIXA = 1) " +
			"THEN d.VEL_MEDIA_TOTAL " +
		"ELSE NULL END), 0) 'VEL_MEDIA_TOTAL_S1', " +
		"ROUND(AVG(CASE " +
			"WHEN (eq.dir_lane1 <> IFNULL(eq.dir_lane2, eq.dir_lane1) AND d.NOME_FAIXA > 1) " +
			  "OR (eq.dir_lane1 <> IFNULL(eq.dir_lane3, eq.dir_lane1) AND d.NOME_FAIXA > 2) " +
			  "OR (eq.dir_lane1 <> IFNULL(eq.dir_lane4, eq.dir_lane1) AND d.NOME_FAIXA > 3) " +
			  "OR (eq.dir_lane1 <> IFNULL(eq.dir_lane5, eq.dir_lane1) AND d.NOME_FAIXA > 4) " +
			"THEN d.VEL_MEDIA_TOTAL " +
		"ELSE NULL END), 0) 'VEL_MEDIA_TOTAL_S2' " +
 			 
 	 "FROM "+RoadConcessionaire.tableDados15+" d " +
 	 "INNER JOIN sat_equipment eq on (eq.equip_id = d.nome_estacao) " +
 	 "INNER JOIN tb_vbv v ON (v.siteID = d.nome_estacao) " +
 	 "WHERE eq.equip_id = ? AND DATA_HORA BETWEEN DATE_SUB($INTERVAL$) AND ? AND eq.visible = 1 "+ 
 	 "GROUP BY d.DATA_HORA " +
 	 "ORDER BY d.DATA_HORA ASC, v.data ASC ";
 	 					
 	 	try {
 			
 		 conn.start(1);
 			
 			conn.prepare_my(select.replace("$INTERVAL$", String.format(" ? , INTERVAL %s %s", time, interval)) + " LIMIT 1");
 				conn.prepare_ms(select
					.replace("date_format", "FORMAT")
					.replace("%H:%i", "hh:mm")
					.replace("IFNULL", "ISNULL")
					.replaceFirst("SELECT", "SELECT TOP 1")
					.replace("DATE_SUB($INTERVAL$", String.format("DATEADD(%s, %s, ? ", interval, time)));
 			conn.setInt(1, equip);	
 			conn.setString(2, currentDate);
 			conn.setString(3, currentDate);
 						
 			MapResult result = conn.executeQuery();
 			
 			 System.out.println(select);
 			
 			if (result.hasNext()) {
 				for (RowResult rs : result) {
 				
 					sat.setEquip_id(rs.getInt("d.NOME_ESTACAO"));
 					sat.setLastPackage(rs.getString("PACOTE_HORA"));
 					sat.setLastRegister(rs.getString("DADO_HORA")); 					
 					sat.setQuantidadeS1(rs.getInt("VOLUME_TOTAL_S1"));						
 					sat.setVelocidadeS1(rs.getInt("VEL_MEDIA_TOTAL_S1"));	
 					sat.setQuantidadeS2(rs.getInt("VOLUME_TOTAL_S2"));	
 					sat.setVelocidadeS2(rs.getInt("VEL_MEDIA_TOTAL_S2"));	
 									
 				}				
 			 }			

 		} catch (Exception e) {
 			e.printStackTrace();
 		}finally {
				conn.close();
			}

 				
 		return sat;
 		
 	} 
 
 // -------------------------------------------------------------------------------------------------------------------------------------------------
 
  public String[] lastRegisters(int equip) throws Exception {
 		 		 	 	 		 					
 		String select = "SELECT CASE WHEN DATEDIFF(NOW(), d.DATA_HORA) > 0 THEN date_format(d.DATA_HORA, '%d/%m/%y %H:%i') ELSE " + 			
 						"CASE WHEN " +
 						"MINUTE(d.DATA_HORA) = 45 THEN CONCAT(DATE_FORMAT(d.DATA_HORA, '%H:%i -'), DATE_FORMAT(DATE_ADD(d.DATA_HORA ,INTERVAL 14 MINUTE), ' %H:%i')) ELSE CONCAT(DATE_FORMAT(d.DATA_HORA, '%H:%i -'), DATE_FORMAT(DATE_ADD(d.DATA_HORA, INTERVAL 15 MINUTE), ' %H:%i')) END " + 
 						"END 'PACOTE_HORA', " +
 						"CASE WHEN DATEDIFF(NOW(), v.data) > 0 THEN date_format(v.data, '%d/%m/%y %H:%i') ELSE date_format(v.data, '%H:%i') END 'DADO_HORA' " +
 				
 	 "FROM "+RoadConcessionaire.tableDados15+" d " +
 	 "INNER JOIN sat_equipment eq ON (eq.equip_id = d.nome_estacao) " +
 	 "INNER JOIN tb_vbv v ON (v.siteID = d.nome_estacao) " +
 	 "WHERE eq.equip_id = ? AND eq.visible = 1 " +
 	 "ORDER BY d.DATA_HORA DESC, v.data DESC";
 		
 	 String[] lastRegisters = new String[2];
 	 					
 	 try {
 			
 		 conn.start(1);
 			
 			conn.prepare_my(select + " LIMIT 1");
 			conn.prepare_ms(select
				.replace("date_format", "FORMAT")
				.replace("%d/%m/%y", "dd/MM/yyyy ")
				.replace("%H:%i", "hh:mm")
				.replace("DATEDIFF(", "DATEDIFF(day, ")
				.replace("NOW()", "GETDATE()")
			 	.replaceFirst("SELECT", "SELECT TOP 1"));
 			conn.setInt(1, equip);	
 			 						
 			MapResult result = conn.executeQuery();
 			
 			// System.out.println(select);
 			 		 			
 			if (result.hasNext()) {
 				for (RowResult rs : result) { 					 					
 					 					 		 					
 					lastRegisters[0] = rs.getString("PACOTE_HORA") == "" ? "00:00" : rs.getString("PACOTE_HORA");
 					lastRegisters[1] = rs.getString("DADO_HORA") == "" ? "00:00" : rs.getString("DADO_HORA");
 				 									
 				}				
 			 }			

 		} catch (Exception e) {
 			e.printStackTrace();
 		}finally {
			 conn.close();
		 }

 				
 		return lastRegisters;
 		
 	} 
  
 // -------------------------------------------------------------------------------------------------------------------------------------------------
  
}
