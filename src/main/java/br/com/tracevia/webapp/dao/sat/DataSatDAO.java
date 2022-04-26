package br.com.tracevia.webapp.dao.sat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.model.global.ColumnsSql.RowResult;
import br.com.tracevia.webapp.model.global.ResultSql.MapResult;
import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.model.global.SQL_Tracevia;
import br.com.tracevia.webapp.model.sat.SAT;

public class DataSatDAO {
			
	SQL_Tracevia conn = new SQL_Tracevia();
		
	public List<SAT> dataInterval(int limit, String interval, int time) throws Exception {
						
		List<SAT> list = new ArrayList<SAT>();
		DateTimeApplication dta = new DateTimeApplication();
											
			String currentDate = null, hourDatetime = null;
			
			Calendar calendar = Calendar.getInstance();	
			int minute = calendar.get(Calendar.MINUTE);
						
			// Obter datas formatadas para os dados
			currentDate = dta.getDataInterval15Min(calendar, minute);	
			
			//System.out.println(currentDate);
			
			// Obter data com a hora formatada
			hourDatetime = dta.getDataIntervalHour(calendar);
			
			//System.out.println(currentDate+"   "+hourDatetime);
			
			String temp = "CREATE TEMPORARY TABLE IF NOT EXISTS equip SELECT eq.equip_id, eq.visible, CASE WHEN (eq.dir_lane1 = eq.dir_lane2 AND eq.dir_lane1 = eq.dir_lane3 AND eq.dir_lane1 = eq.dir_lane4) THEN 5 " +
					"WHEN (eq.dir_lane1 = eq.dir_lane2 AND eq.dir_lane1 = eq.dir_lane3) THEN 4 " +
					"WHEN (eq.dir_lane1 = eq.dir_lane2) THEN 3 " +
					"ELSE 2 END 'sentido' FROM sat_equipment eq; ";  // initialize variable
							
			String last7days = "CREATE TEMPORARY TABLE IF NOT EXISTS last_7_days " +
					"SELECT ld.NOME_ESTACAO, SUM(CASE WHEN NOME_FAIXA < eq.sentido  THEN ld.VOLUME_AUTO ELSE 0 END) 'VOLUME_AUTO_LAST_7_DAYS_S1', " + 
					"SUM(CASE WHEN ld.NOME_FAIXA < eq.sentido THEN (ld.VOLUME_COM + ld.VOLUME_LONGO) ELSE 0 END) 'VOLUME_COM_LAST_7_DAYS_S1', " +
					"SUM(CASE WHEN ld.NOME_FAIXA < eq.sentido THEN ld.VOLUME_MOTOS ELSE 0 END) 'VOLUME_MOTO_LAST_7_DAYS_S1', " +
					"SUM(CASE WHEN ld.NOME_FAIXA < eq.sentido THEN ld.VOLUME_TOTAL ELSE 0 END) 'VOLUME_TOTAL_LAST_7_DAYS_S1', " +
	
					"SUM(CASE WHEN ld.NOME_FAIXA >= eq.sentido  THEN ld.VOLUME_AUTO ELSE 0 END) 'VOLUME_AUTO_LAST_7_DAYS_S2', " +
					"SUM(CASE WHEN ld.NOME_FAIXA >= eq.sentido THEN (ld.VOLUME_COM + ld.VOLUME_LONGO) ELSE 0 END) 'VOLUME_COM_LAST_7_DAYS_S2', " +
					"SUM(CASE WHEN ld.NOME_FAIXA >= eq.sentido THEN ld.VOLUME_MOTOS ELSE 0 END) 'VOLUME_MOTO_LAST_7_DAYS_S2', " +
					"SUM(CASE WHEN ld.NOME_FAIXA >= eq.sentido THEN ld.VOLUME_TOTAL ELSE 0 END) 'VOLUME_TOTAL_LAST_7_DAYS_S2' " +
	
					"FROM tb_dados15 ld " +
					"LEFT JOIN equip eq ON (ld.NOME_ESTACAO = eq.equip_id) " +
					"WHERE ld.DATA_HORA BETWEEN DATE_SUB(?, INTERVAL 7 DAY) AND DATE_SUB(DATE_ADD(DATE_SUB(?, INTERVAL 7 DAY), INTERVAL 1 HOUR), INTERVAL 1 SECOND) GROUP BY ld.NOME_ESTACAO LIMIT 9";

			String lastHour = "CREATE TEMPORARY TABLE IF NOT EXISTS last_hour " +
					"SELECT lh.NOME_ESTACAO, SUM(CASE WHEN NOME_FAIXA < eq.sentido  THEN lh.VOLUME_AUTO ELSE 0 END) 'VOLUME_AUTO_LAST_HOUR_S1', " + 
					"SUM(CASE WHEN lh.NOME_FAIXA < eq.sentido THEN (lh.VOLUME_COM + lh.VOLUME_LONGO) ELSE 0 END) 'VOLUME_COM_LAST_HOUR_S1', " +
					"SUM(CASE WHEN lh.NOME_FAIXA < eq.sentido THEN lh.VOLUME_MOTOS ELSE 0 END) 'VOLUME_MOTO_LAST_HOUR_S1', " +
					"SUM(CASE WHEN lh.NOME_FAIXA < eq.sentido THEN lh.VOLUME_TOTAL ELSE 0 END) 'VOLUME_TOTAL_LAST_HOUR_S1', " +
		
					"SUM(CASE WHEN lh.NOME_FAIXA >= eq.sentido  THEN lh.VOLUME_AUTO ELSE 0 END) 'VOLUME_AUTO_LAST_HOUR_S2', " +
					"SUM(CASE WHEN lh.NOME_FAIXA >= eq.sentido THEN (lh.VOLUME_COM + lh.VOLUME_LONGO) ELSE 0 END) 'VOLUME_COM_LAST_HOUR_S2', " +
					"SUM(CASE WHEN lh.NOME_FAIXA >= eq.sentido THEN lh.VOLUME_MOTOS ELSE 0 END) 'VOLUME_MOTO_LAST_HOUR_S2', " +
					"SUM(CASE WHEN lh.NOME_FAIXA >= eq.sentido THEN lh.VOLUME_TOTAL ELSE 0 END) 'VOLUME_TOTAL_LAST_HOUR_S2' " +
		
					"FROM tb_dados15 lh " +
					"LEFT JOIN equip eq ON (lh.NOME_ESTACAO = eq.equip_id) " +
					"WHERE lh.DATA_HORA BETWEEN DATE_SUB(?, INTERVAL 1 HOUR) AND DATE_SUB(?, INTERVAL 1 SECOND) GROUP BY lh.NOME_ESTACAO LIMIT 9";

			String maxDate = "CREATE TEMPORARY TABLE IF NOT EXISTS maxDate " +
					"SELECT siteID, MAX(data) maxDate FROM tb_vbv GROUP BY siteID LIMIT 9";
							
			String select = "SELECT d.NOME_ESTACAO AS ESTACAO, nt.online_status AS ESTADO_ATUAL, " +
				"IFNULL(CASE WHEN DATEDIFF(NOW(), d.DATA_HORA) > 0 THEN date_format(d.DATA_HORA, '%d/%m/%y %H:%i') ELSE CASE WHEN MINUTE(d.DATA_HORA) = 45 THEN CONCAT(DATE_FORMAT(d.DATA_HORA, '%H:%i -'), " +
				"DATE_FORMAT(DATE_ADD(d.DATA_HORA ,INTERVAL 14 MINUTE), ' %H:%i')) ELSE CONCAT(DATE_FORMAT(d.DATA_HORA, '%H:%i -'), DATE_FORMAT(DATE_ADD(d.DATA_HORA, INTERVAL 15 MINUTE), ' %H:%i')) END END, '07/01/2000 07:00') 'PACOTE_HORA', " +
				"IFNULL(CASE WHEN DATEDIFF(NOW(), md.maxDate) > 0 THEN date_format(md.maxDate, '%d/%m/%y %H:%i') ELSE date_format(md.maxDate, '%H:%i') END, '00:00') 'DADO_HORA', " +
				
				/* COLUMN DATETIME HEADER */
				
				"IFNULL(DATE_FORMAT(DATE_SUB(d.DATA_HORA, INTERVAL 7 DAY),  '%d/%m/%Y  %H:00'), '01/01/2000 07:00') '7_DAYS_HEADER', " +
				"IFNULL(DATE_FORMAT(DATE_SUB(d.DATA_HORA, INTERVAL 1 HOUR), '%d/%m/%Y %H:00'), '07/01/2000 06:00') 'LAST_HOUR_HEADER', " +
				"IFNULL(DATE_FORMAT(d.DATA_HORA, '%d/%m/%Y %H:00'), '07/01/2000 07:00') 'PROJECTION_HEADER', " +
				
				/* 7 DAYS COLUMNS VALUE S1 */
				
				"VOLUME_AUTO_LAST_7_DAYS_S1, " +
			    "VOLUME_COM_LAST_7_DAYS_S1, " +
			    "VOLUME_MOTO_LAST_7_DAYS_S1, " +
				"VOLUME_TOTAL_LAST_7_DAYS_S1, " +
				
				/* 1 HOUR COLUMNS VALUE S1 */
				
				"VOLUME_AUTO_LAST_HOUR_S1, " +
				"VOLUME_COM_LAST_HOUR_S1, " +
				"VOLUME_MOTO_LAST_HOUR_S1, " +
				"VOLUME_TOTAL_LAST_HOUR_S1, " +
				
				/* VOLUME S1 */
				
				"SUM(CASE WHEN d.NOME_FAIXA < sentido THEN d.VOLUME_AUTO ELSE 0 END) 'VOLUME_AUTO_S1', " +
				"SUM(CASE WHEN d.NOME_FAIXA < sentido THEN (d.VOLUME_COM + d.VOLUME_LONGO) ELSE 0 END) 'VOLUME_COM_S1', " +
				"SUM(CASE WHEN d.NOME_FAIXA < sentido THEN d.VOLUME_MOTOS ELSE 0 END) 'VOLUME_MOTO_S1', " +
				"SUM(CASE WHEN d.NOME_FAIXA < sentido THEN d.VOLUME_TOTAL ELSE 0 END) 'VOLUME_TOTAL_S1', " +
				
				/* PROJECTION S1 */
				
				"ROUND((SUM(CASE WHEN d.NOME_FAIXA < sentido THEN d.VOLUME_AUTO ELSE 0 END) * 4), 0) 'VOLUME_AUTO_PROJECTION_S1', " +
				"ROUND((SUM(CASE WHEN d.NOME_FAIXA < sentido THEN (d.VOLUME_COM + d.VOLUME_LONGO) ELSE 0 END) * 4), 0) 'VOLUME_COM_PROJECTION_S1', " +
				"ROUND((SUM(CASE WHEN d.NOME_FAIXA < sentido THEN d.VOLUME_MOTOS ELSE 0 END)  * 4), 0) 'VOLUME_MOTO_PROJECTION_S1', " +
				"ROUND((SUM(CASE WHEN d.NOME_FAIXA < sentido THEN d.VOLUME_TOTAL ELSE 0 END) * 4), 0) 'VOLUME_TOTAL_PROJECTION_S1', " +
				
				 /* TAXA OCUPACAO S1 */  
				 
				"IFNULL(ROUND(AVG(CASE WHEN d.NOME_FAIXA < sentido THEN (d.TAXA_OCUPACAO * 100) ELSE 0 END), 2), 0) 'TAXA_OCUPACAO_S1', " +
				
				/* VELOCIDADE S1 */
				
				"CASE WHEN d.NOME_FAIXA < sentido THEN ROUND(AVG(NULLIF(d.VEL_MEDIA_AUTOS, 0)), 0) ELSE 0 END 'VEL_MEDIA_AUTOS_S1', " +
				"ROUND(NULLIF(AVG(CASE WHEN d.NOME_FAIXA  < sentido THEN (d.VOLUME_COM * d.VEL_MEDIA_COM + d.VOLUME_LONGO * d.VEL_MEDIA_LONGO) / (d.VOLUME_COM + d.VOLUME_LONGO) ELSE NULL END), 0), 0) 'VEL_MEDIA_COM_S1', " +
				"CASE WHEN d.NOME_FAIXA < sentido THEN ROUND(AVG(NULLIF(d.VEL_MEDIA_MOTOS, 0)), 0) ELSE 0 END 'VEL_MEDIA_MOTO_S1', " + 
				"CASE WHEN d.NOME_FAIXA < sentido THEN ROUND(AVG(NULLIF(d.VEL_MEDIA_TOTAL, 0)), 0) ELSE 0 END 'VEL_MEDIA_TOTAL_S1', " +
				
				/* 7 DAYS COLUMNS VALUE S2 */
				
				"VOLUME_AUTO_LAST_7_DAYS_S2, " +
				"VOLUME_COM_LAST_7_DAYS_S2, " +
				"VOLUME_MOTO_LAST_7_DAYS_S2, " +
				"VOLUME_TOTAL_LAST_7_DAYS_S2, " +
				
				/* 1 HOUR COLUMNS VALUE S2*/
				
				"VOLUME_AUTO_LAST_HOUR_S2, " +
				"VOLUME_COM_LAST_HOUR_S2, " +
				"VOLUME_MOTO_LAST_HOUR_S2, " +
				"VOLUME_TOTAL_LAST_HOUR_S2, " +
				
				/* VOLUME S2 */
				
				"SUM(CASE WHEN d.NOME_FAIXA >= sentido THEN d.VOLUME_AUTO ELSE 0 END) 'VOLUME_AUTO_S2', " +
				"SUM(CASE WHEN d.NOME_FAIXA >= sentido THEN (d.VOLUME_COM + d.VOLUME_LONGO) ELSE 0 END) 'VOLUME_COM_S2', " +
				"SUM(CASE WHEN d.NOME_FAIXA >= sentido THEN d.VOLUME_MOTOS ELSE 0 END) 'VOLUME_MOTO_S2', " +
				"SUM(CASE WHEN d.NOME_FAIXA >= sentido THEN d.VOLUME_TOTAL ELSE 0 END) 'VOLUME_TOTAL_S2', " +
				
				/* PROJECTION S2 */
				
				"ROUND((SUM(CASE WHEN d.NOME_FAIXA >= sentido THEN d.VOLUME_AUTO ELSE 0 END) * 4), 0) 'VOLUME_AUTO_PROJECTION_S2', " +
				"ROUND((SUM(CASE WHEN d.NOME_FAIXA >= sentido THEN (d.VOLUME_COM + d.VOLUME_LONGO) ELSE 0 END) * 4), 0) 'VOLUME_COM_PROJECTION_S2', " +
				"ROUND((SUM(CASE WHEN d.NOME_FAIXA >= sentido THEN d.VOLUME_MOTOS ELSE 0 END) * 4), 0) 'VOLUME_MOTO_PROJECTION_S2', " +
				"ROUND((SUM(CASE WHEN d.NOME_FAIXA >= sentido THEN d.VOLUME_TOTAL ELSE 0 END) * 4), 0) 'VOLUME_TOTAL_PROJECTION_S2', " +
				
				 /* TAXA OCUPACAO S2 */ 
				 
				"IFNULL(ROUND(AVG(CASE WHEN d.NOME_FAIXA >= sentido THEN (d.TAXA_OCUPACAO * 100) ELSE 0 END), 2),0) 'TAXA_OCUPACAO_S2', " +
				
				/* VELOCIDADE S2 */
				
				"ROUND(AVG(NULLIF(CASE WHEN d.NOME_FAIXA >= sentido THEN d.VEL_MEDIA_AUTOS ELSE 0 END, 0)), 0) 'VEL_MEDIA_AUTOS_S2', " +
				"ROUND(NULLIF(AVG(CASE WHEN d.NOME_FAIXA >= sentido THEN (d.VOLUME_COM * d.VEL_MEDIA_COM + d.VOLUME_LONGO * d.VEL_MEDIA_LONGO) / (d.VOLUME_COM + d.VOLUME_LONGO) ELSE NULL END), 0), 0) 'VEL_MEDIA_COM_S2', " +
				"ROUND(AVG(NULLIF(CASE WHEN d.NOME_FAIXA >= sentido THEN d.VEL_MEDIA_MOTOS ELSE 0 END, 0)), 0) 'VEL_MEDIA_MOTO_S2', " +
				"ROUND(AVG(NULLIF(CASE WHEN d.NOME_FAIXA >= sentido THEN d.VEL_MEDIA_TOTAL ELSE 0 END, 0)), 0) 'VEL_MEDIA_TOTAL_S2' " +
				
				"FROM "+RoadConcessionaire.tableDados15+" d " +
				"LEFT JOIN equip eq ON (d.NOME_ESTACAO = eq.equip_id) " +
				"LEFT JOIN maxDate md ON md.siteID = d.NOME_ESTACAO " +
				"LEFT JOIN last_7_days dy ON (d.NOME_ESTACAO = dy.NOME_ESTACAO) " +
				"LEFT JOIN last_hour lh ON (d.NOME_ESTACAO = lh.NOME_ESTACAO) " +
				"LEFT JOIN notifications_status nt ON (d.NOME_ESTACAO = nt.equip_id) AND 'SAT' = nt.equip_type " +
				
			    "WHERE d.DATA_HORA BETWEEN DATE_SUB($INTERVAL$) AND ? AND eq.visible = 1 " +
			    "GROUP BY d.NOME_ESTACAO " +	
		 	    "ORDER BY d.DATA_HORA DESC ";
	 
	 try {
			
		 	conn.start(1);
		 
			conn.prepare(temp);
			conn.executeUpdate();
			
			// ------------------------------
			
			conn.prepare(last7days);
			conn.setString(1, hourDatetime);		
			conn.setString(2, hourDatetime);
			conn.executeUpdate();
			
			// ------------------------------
			
			conn.prepare(lastHour);
			conn.setString(1, hourDatetime);		
			conn.setString(2, hourDatetime);
			conn.executeUpdate();
			
			// ------------------------------
						
			conn.prepare(maxDate);
			conn.executeUpdate();
			
			// ------------------------------
						
			conn.prepare_my(select.replace("$INTERVAL$", String.format(" ? , INTERVAL %s %s", time, interval)) + " LIMIT " + limit);
 			conn.prepare_ms(select
			 	.replace("date_format", "FORMAT")
				.replace("%H:%i", "hh:mm")
			 	.replace("IFNULL", "ISNULL")
				.replaceFirst("SELECT", "SELECT TOP " + limit)
				.replace("DATE_SUB($INTERVAL$", String.format("DATEADD(%s, -%s, ? ", interval, time)));		
			 				
			conn.setString(1, currentDate);		
			conn.setString(2, currentDate);
			
			MapResult result = conn.executeQuery();
			
			//System.out.println("ORIGIN: "+select);		 	
			
			if (result.hasNext()) {
				for (RowResult rs : result) {
					
					SAT sat = new SAT();
					
					// PRIMARY INFORMATION

					sat.setEquip_id(rs.getInt("ESTACAO"));
					sat.setLastPackage(rs.getString("PACOTE_HORA"));
					sat.setLastRegister(rs.getString("DADO_HORA"));	
					sat.setQuantidadeS1(rs.getInt("VOLUME_TOTAL_S1"));						
					sat.setVelocidadeS1(rs.getInt("VEL_MEDIA_TOTAL_S1"));
					sat.setQuantidadeS2(rs.getInt("VOLUME_TOTAL_S2"));
					sat.setVelocidadeS2(rs.getInt("VEL_MEDIA_TOTAL_S2"));
					sat.setStatus(rs.getInt("ESTADO_ATUAL")); 				
 					sat.setStatusInterval(time);
					
					// -------------------------------
					// TABLE INFO
					// -------------------------------
					
					// TABLE HEADERS 
					
					sat.setCurrentDatetime(rs.getString("PACOTE_HORA")); //  MAIN HEADER					
					sat.setSevenDaysDatetime(rs.getString("7_DAYS_HEADER")); // 7 DAYS HEADER
					sat.setLastOneDatetime(rs.getString("LAST_HOUR_HEADER")); // LAST HOUR HEADER
					sat.setProjectionDatetime(rs.getString("PROJECTION_HEADER")); // PROJECTION HEADER	
					
					// -------------------------------
					// DIRECTION S1
					// -------------------------------
					
					// LAST 7 DAYS AND HOUR
					
					sat.setAutos7days1hS1(rs.getInt("VOLUME_AUTO_LAST_7_DAYS_S1"));
					sat.setCom7days1hS1(rs.getInt("VOLUME_COM_LAST_7_DAYS_S1"));
					sat.setMoto7days1hS1(rs.getInt("VOLUME_MOTO_LAST_7_DAYS_S1"));
					sat.setTotal7days1hS1(rs.getInt("VOLUME_TOTAL_LAST_7_DAYS_S1"));
					
					// CURRENT LAST HOUR
					
					sat.setAutosCurrent1hS1(rs.getInt("VOLUME_AUTO_LAST_HOUR_S1"));
					sat.setComCurrent1hS1(rs.getInt("VOLUME_COM_LAST_HOUR_S1"));
					sat.setMotoCurrent1hS1(rs.getInt("VOLUME_MOTO_LAST_HOUR_S1"));
					sat.setTotalCurrent1hS1(rs.getInt("VOLUME_TOTAL_LAST_HOUR_S1"));
														
					// CURRENT STATE
										
					sat.setAutosVolumeS1(rs.getInt("VOLUME_AUTO_S1"));
					sat.setComVolumeS1(rs.getInt("VOLUME_COM_S1"));
					sat.setMotoVolumeS1(rs.getInt("VOLUME_MOTO_S1"));
					sat.setTotalVolumeS1(rs.getInt("VOLUME_TOTAL_S1"));
					sat.setAutosVelMedS1(rs.getInt("VEL_MEDIA_AUTOS_S1"));
					sat.setComVelMedS1(rs.getInt("VEL_MEDIA_COM_S1"));
					sat.setMotoVelMedS1(rs.getInt("VEL_MEDIA_MOTO_S1"));
					sat.setTotalVelMedS1(rs.getInt("VEL_MEDIA_TOTAL_S1"));					
					sat.setOccupancyRateS1(rs.getDouble("TAXA_OCUPACAO_S1"));
					
				    // PROJECTION
					
					sat.setAutosProjection1hS1(rs.getInt("VOLUME_AUTO_PROJECTION_S1"));
					sat.setComProjection1hS1(rs.getInt("VOLUME_COM_PROJECTION_S1"));
					sat.setMotoProjection1hS1(rs.getInt("VOLUME_MOTO_PROJECTION_S1"));
					sat.setTotalProjection1hS1(rs.getInt("VOLUME_TOTAL_PROJECTION_S1"));
					
					// -------------------------------
					// DIRECTION S2
					// -------------------------------
					
					// LAST 7 DAYS AND HOUR
					
					sat.setAutos7days1hS2(rs.getInt("VOLUME_AUTO_LAST_7_DAYS_S2"));
					sat.setCom7days1hS2(rs.getInt("VOLUME_COM_LAST_7_DAYS_S2"));
					sat.setMoto7days1hS2(rs.getInt("VOLUME_MOTO_LAST_7_DAYS_S2"));
					sat.setTotal7days1hS2(rs.getInt("VOLUME_TOTAL_LAST_7_DAYS_S2"));
					
					// CURRENT LAST HOUR
					
					sat.setAutosCurrent1hS2(rs.getInt("VOLUME_AUTO_LAST_HOUR_S2"));
					sat.setComCurrent1hS2(rs.getInt("VOLUME_COM_LAST_HOUR_S2"));
					sat.setMotoCurrent1hS2(rs.getInt("VOLUME_MOTO_LAST_HOUR_S2"));
					sat.setTotalCurrent1hS2(rs.getInt("VOLUME_TOTAL_LAST_HOUR_S2"));
																				
					sat.setAutosVolumeS2(rs.getInt("VOLUME_AUTO_S2"));
					sat.setComVolumeS2(rs.getInt("VOLUME_COM_S2"));
					sat.setMotoVolumeS2(rs.getInt("VOLUME_MOTO_S2"));
					sat.setTotalVolumeS2(rs.getInt("VOLUME_TOTAL_S2"));
					sat.setAutosVelMedS2(rs.getInt("VEL_MEDIA_AUTOS_S2"));
					sat.setComVelMedS2(rs.getInt("VEL_MEDIA_COM_S2"));
					sat.setMotoVelMedS2(rs.getInt("VEL_MEDIA_MOTO_S2"));
					sat.setTotalVelMedS2(rs.getInt("VEL_MEDIA_TOTAL_S2"));
					sat.setOccupancyRateS2(rs.getDouble("TAXA_OCUPACAO_S2"));
					
				    // PROJECTION 
					
					sat.setAutosProjection1hS2(rs.getInt("VOLUME_AUTO_PROJECTION_S2"));
					sat.setComProjection1hS2(rs.getInt("VOLUME_COM_PROJECTION_S2"));
					sat.setMotoProjection1hS2(rs.getInt("VOLUME_MOTO_PROJECTION_S2"));
					sat.setTotalProjection1hS2(rs.getInt("VOLUME_TOTAL_PROJECTION_S2"));
																															
					list.add(sat);
					
					// -------------------------------
					// TABLE INFO
					// -------------------------------
					
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
  
	public List<SAT> noDataInterval(int limit, List<Integer> equips) throws Exception {
  		
  		List<SAT> list = new ArrayList<SAT>();
 		 		 	 	 		 					
 		String select = "SELECT d.NOME_ESTACAO AS ESTACAO, nt.online_status AS ESTADO_ATUAL, " +
 				  "IFNULL(CASE WHEN DATEDIFF(NOW(), d.DATA_HORA) > 0 THEN date_format(d.DATA_HORA, '%d/%m/%y %H:%i') ELSE " + 			
 				  "CASE WHEN DATEDIFF(NOW(), d.DATA_HORA) > 0 THEN date_format(d.DATA_HORA, '%d/%m/%y %H:%i') ELSE CASE WHEN MINUTE(d.DATA_HORA) = 45 THEN CONCAT(DATE_FORMAT(d.DATA_HORA, '%H:%i -'), " + 
				  "DATE_FORMAT(DATE_ADD(d.DATA_HORA ,INTERVAL 14 MINUTE), ' %H:%i')) ELSE CONCAT(DATE_FORMAT(d.DATA_HORA, '%H:%i -'), DATE_FORMAT(DATE_ADD(d.DATA_HORA, INTERVAL 15 MINUTE), ' %H:%i')) END END END, '00:00') 'PACOTE_HORA', " +
				  "IFNULL(CASE WHEN DATEDIFF(NOW(), md.maxDate) > 0 THEN date_format(md.maxDate, '%d/%m/%y %H:%i') ELSE date_format(md.maxDate, '%H:%i') END, '00:00') 'DADO_HORA' " +
										
			 	  "FROM "+RoadConcessionaire.tableDados15+" d " +
			 	  "LEFT JOIN sat_equipment eq ON (d.NOME_ESTACAO = eq.equip_id) " +	
			 	  "LEFT JOIN notifications_status nt ON (d.NOME_ESTACAO = nt.equip_id) AND 'SAT' = nt.equip_type " +
			 	  
			 	  "LEFT JOIN " +
					"( " +
					    "SELECT siteID, MAX(data) maxDate FROM tb_vbv " +
					    "GROUP BY siteID LIMIT "+ limit +   // LIMIT == NUMBER OF EQUIPS
					") md ON md.siteID = eq.equip_id " +
					    
			 	  "WHERE d.NOME_ESTACAO IN(";
 		
 		for(int i = 0; i < equips.size(); i++) {
 			
 			select += equips.get(i);
 			
 			if(i < (equips.size() - 1))
 				select +=", ";
 					
 		}
 		
 		select += ") AND eq.visible = 1 " +
 				  "GROUP BY d.NOME_ESTACAO, d.DATA_HORA " +
 	              "ORDER BY d.DATA_HORA DESC ";
 	 	 					
 	 try {
 			
 		 conn.start(1);
 			
 			conn.prepare_my(select + " LIMIT " + limit);
 			conn.prepare_ms(select
				.replace("date_format", "FORMAT")
				.replace("%d/%m/%y", "dd/MM/yyyy ")
				.replace("%H:%i", "hh:mm")
				.replace("DATEDIFF(", "DATEDIFF(day, ")
				.replace("NOW()", "GETDATE()")
			 	.replaceFirst("SELECT", "SELECT TOP 1")); 					
 			 						
 			MapResult result = conn.executeQuery();
 			
 			// System.out.println(select);
 			  			 
 			 List<Integer> checkList = new ArrayList<Integer>(); // LISTA PARA VERIFICAR O ULTIMO ID
 			  			 		 			
 			if (result.hasNext()) {
 				for (RowResult rs : result) { 		
 					
 					 if(!checkList.contains(rs.getInt("ESTACAO"))) { 					    	   	
 					
	 					SAT sat = new SAT();
	 					
	 					sat.setEquip_id(rs.getInt("ESTACAO"));
	 					sat.setLastPackage(rs.getString("PACOTE_HORA"));		
	 					sat.setLastRegister(rs.getString("DADO_HORA")); 					
	 					sat.setQuantidadeS1(0);
	 					sat.setVelocidadeS1(0);
	 					sat.setQuantidadeS2(0);
	 					sat.setVelocidadeS2(0);
	 					sat.setStatus(rs.getInt("ESTADO_ATUAL"));			
	 					sat.setStatusInterval(0);
	 					
	 					// -------------------------------
						// TABLE INFO
						// -------------------------------
						
						// TABLE HEADERS 
						
						sat.setCurrentDatetime("07/01/2000 07:00"); //  MAIN HEADER					
						sat.setSevenDaysDatetime("01/01/2000 07:00"); // 7 DAYS HEADER
						sat.setLastOneDatetime("07/01/2000 06:00"); // LAST HOUR HEADER
						sat.setProjectionDatetime("07/01/2000 07:00"); // PROJECTION HEADER	
						
						// -------------------------------
						// DIRECTION S1
						// -------------------------------
						
						// LAST 7 DAYS AND HOUR
						
						sat.setAutos7days1hS1(0);
						sat.setCom7days1hS1(0);
						sat.setMoto7days1hS1(0);
						sat.setTotal7days1hS1(0);
						
						// CURRENT LAST HOUR
						
						sat.setAutosCurrent1hS1(0);
						sat.setComCurrent1hS1(0);
						sat.setMotoCurrent1hS1(0);
						sat.setTotalCurrent1hS1(0);
															
						// CURRENT STATE
											
						sat.setAutosVolumeS1(0);
						sat.setComVolumeS1(0);
						sat.setMotoVolumeS1(0);
						sat.setTotalVolumeS1(0);
						sat.setAutosVelMedS1(0);
						sat.setComVelMedS1(0);
						sat.setMotoVelMedS1(0);
						sat.setTotalVelMedS1(0);					
						sat.setOccupancyRateS1(0.00);
						
					    // PROJECTION
						
						sat.setAutosProjection1hS1(0);
						sat.setComProjection1hS1(0);
						sat.setMotoProjection1hS1(0);
						sat.setTotalProjection1hS1(0);
						
						// -------------------------------
						// DIRECTION S2
						// -------------------------------
						
						// LAST 7 DAYS AND HOUR
						
						sat.setAutos7days1hS2(0);
						sat.setCom7days1hS2(0);
						sat.setMoto7days1hS2(0);
						sat.setTotal7days1hS2(0);
						
						// CURRENT LAST HOUR
						
						sat.setAutosCurrent1hS2(0);
						sat.setComCurrent1hS2(0);
						sat.setMotoCurrent1hS2(0);
						sat.setTotalCurrent1hS2(0);
																					
						sat.setAutosVolumeS2(0);
						sat.setComVolumeS2(0);
						sat.setMotoVolumeS2(0);
						sat.setTotalVolumeS2(0);
						sat.setAutosVelMedS2(0);
						sat.setComVelMedS2(0);
						sat.setMotoVelMedS2(0);
						sat.setTotalVelMedS2(0);
						sat.setOccupancyRateS2(0.00);
						
					    // PROJECTION 
						
						sat.setAutosProjection1hS2(0);
						sat.setComProjection1hS2(0);
						sat.setMotoProjection1hS2(0);
						sat.setTotalProjection1hS2(0);
																																
						list.add(sat);
						
						checkList.add(rs.getInt("ESTACAO"));
						
	 					}				
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
	 
}
