package br.com.tracevia.webapp.dao.sat;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import br.com.tracevia.webapp.log.SystemLog;
import br.com.tracevia.webapp.model.global.ColumnsSql.RowResult;
import br.com.tracevia.webapp.model.global.ResultSql.MapResult;
import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.model.global.SQL_Tracevia;
import br.com.tracevia.webapp.model.sat.FluxoPeriodo;
import br.com.tracevia.webapp.model.sat.SAT;

public class FluxoPeriodoDAO {
	
	SQL_Tracevia conn = new SQL_Tracevia();
	
	String direction1, direction2; 
			
	// --------------------------------------------------------------------------------------------------------------
		
	// LOGS FOLDER
	
	private final String errorFolder = SystemLog.ERROR.concat("period-flow-dao\\");
	
	// --------------------------------------------------------------------------------------------------------------

	public FluxoPeriodoDAO() throws Exception {

		SystemLog.createLogFolder(errorFolder);
		
	}	
	
	// --------------------------------------------------------------------------------------------------------------
		
	/** Método para obter dados para construir o relatório do fluxo por periodo
	 * @author Wellington 09/01/2021 
	 * @version 1.0
	 * @since 1.0 
	 * @param startDate data de inicio
	 * @param endDate data de término	
	 * @param equipId id do equipamento
	 * @param period periodo de busca
	 * @param laneDir1 primeira faixa do equipamento 
	 * @return lista de objetos do tipo FluxoPeriodos
	 *  
	 */	
	
	public String[][] getVehicles(String startDate, String endDate, String equipId, String period, SAT sat, int fieldsNumber, int numRegisters) {		
							
		setDirections(sat.getFaixa1()); // DEFINE DIRECTIONS FIRST
		
		String[][] resultSet = new String[numRegisters][fieldsNumber];

		String temp = "CREATE TEMPORARY TABLE IF NOT EXISTS equip as SELECT s.equip_id, CASE WHEN (s.dir_lane1 = s.dir_lane2 AND s.dir_lane1 = s.dir_lane3 AND s.dir_lane1 = s.dir_lane4) THEN 5 " +
							"WHEN (s.dir_lane1 = s.dir_lane2 AND s.dir_lane1 = s.dir_lane3) THEN 4 " +
							"WHEN (s.dir_lane1 = s.dir_lane2) THEN 3 " +
							"ELSE 2 END 'sentido' FROM sat_equipment s;"; // initialize variable		
		String select = "";
							
		if(period.equals("15 minutes"))			
			   select += "SELECT DATE_FORMAT(data, '%Y-%m-%d') AS SD, " + 	
					    "DATE_FORMAT((SEC_TO_TIME(TIME_TO_SEC(data) - TIME_TO_SEC(data)%(15*60))),'%H:%i') AS INTERVALS, ";				
				
		if(period.equals("01 hour"))			
			   select += "SELECT DATE_FORMAT(data, '%Y-%m-%d') AS SD, " + 					
					    "DATE_FORMAT((SEC_TO_TIME(TIME_TO_SEC(data) - TIME_TO_SEC(data)%(60*60))),'%H:00') AS INTERVALS, ";		
				
		if(period.equals("24 hours"))			
			   select += "SELECT DATE_FORMAT(data, '%Y-%m-%d') AS SD, ' ---- ' AS  INTERVALS, ";			
				
		// -------------------------------------------------------------------------------------------------------------------------------------
							
		/* SUM VEHICLES */    
			
		select+="IFNULL(SUM(IF(lane < sentido AND (classe = '"+RoadConcessionaire.classLight+"' OR classe = '"+RoadConcessionaire.classUnknown+"' OR classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"' OR ((classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross < 3501) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross < 3501 ))), 1, 0)), 0) 'SUM_AUTO_S1', " +
			"IFNULL(SUM(IF(lane < sentido AND ((classe <> '"+RoadConcessionaire.classLight+"' AND classe <> '"+RoadConcessionaire.classUnknown+"' AND classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND classe <> '"+RoadConcessionaire.classMotorcycle+"' AND classe <> '"+RoadConcessionaire.classSemiTrailer+"' AND classe <> '"+RoadConcessionaire.classTrailer+"') OR (classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross > 3500) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross > 3500)), 1, 0)), 0) 'SUM_COM_S1', " +
			"IFNULL(SUM(IF(lane < sentido AND classe = '"+RoadConcessionaire.classMotorcycle+"', 1, 0)), 0) 'SUM_MOTO_S1', " +
			"IFNULL(SUM(IF(lane < sentido, 1, 0)), 0) 'SUM_TOTAL_S1', " +
			
			"IFNULL(SUM(IF(lane >= sentido AND (classe = '"+RoadConcessionaire.classLight+"' OR classe = '"+RoadConcessionaire.classUnknown+"' OR classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"' OR ((classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross < 3501) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross < 3501 ))), 1, 0)), 0) 'SUM_AUTO_S2', " +
			"IFNULL(SUM(IF(lane >= sentido AND ((classe <> '"+RoadConcessionaire.classLight+"' AND classe <> '"+RoadConcessionaire.classUnknown+"' AND classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND classe <> '"+RoadConcessionaire.classMotorcycle+"' AND classe <> '"+RoadConcessionaire.classSemiTrailer+"' AND classe <> '"+RoadConcessionaire.classTrailer+"') OR (classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross > 3500) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross > 3500)), 1, 0)), 0) 'SUM_COM_S2', " +
			"IFNULL(SUM(IF(lane >= sentido AND classe = '"+RoadConcessionaire.classMotorcycle+"', 1, 0)), 0) 'SUM_MOTO_S2', " +
			"IFNULL(SUM(IF(lane >= sentido, 1, 0)), 0) 'SUM_TOTAL_S2', " +
		
		/* AVERAGE SPEED */
			
			"IFNULL(ROUND(AVG(IF(lane < sentido AND (classe = '"+RoadConcessionaire.classLight+"' OR classe = '"+RoadConcessionaire.classUnknown+"' OR classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"' OR ((classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross < 3501) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross < 3501 ))), speed, NULL)), 0), 0) 'AVG_SPEED_AUTO_S1', " +
			"IFNULL(ROUND(AVG(IF(lane < sentido AND ((classe <> '"+RoadConcessionaire.classLight+"' AND classe <> '"+RoadConcessionaire.classUnknown+"' AND classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND classe <> '"+RoadConcessionaire.classMotorcycle+"' AND classe <> '"+RoadConcessionaire.classSemiTrailer+"' AND classe <> '"+RoadConcessionaire.classTrailer+"') OR (classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross > 3500) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross > 3500)), speed, NULL)), 0), 0) 'AVG_SPEED_COM_S1', " +
			"IFNULL(ROUND(AVG(IF(lane < sentido AND classe = '"+RoadConcessionaire.classMotorcycle+"', speed, NULL)), 0), 0) 'AVG_SPEED_MOTO_S1', " +
			"IFNULL(ROUND(AVG(IF(lane < sentido, speed, NULL)), 0), 0) 'AVG_SPEED_TOTAL_S1', " +
			
			"IFNULL(ROUND(AVG(IF(lane >= sentido AND (classe = '"+RoadConcessionaire.classLight+"' OR classe = '"+RoadConcessionaire.classUnknown+"' OR classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"' OR ((classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross < 3501) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross < 3501 ))), speed, NULL)), 0), 0) 'AVG_SPEED_AUTO_S2', " +
			"IFNULL(ROUND(AVG(IF(lane >= sentido AND ((classe <> '"+RoadConcessionaire.classLight+"' AND classe <> '"+RoadConcessionaire.classUnknown+"' AND classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND classe <> '"+RoadConcessionaire.classMotorcycle+"' AND classe <> '"+RoadConcessionaire.classSemiTrailer+"' AND classe <> '"+RoadConcessionaire.classTrailer+"') OR (classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross > 3500) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross > 3500)), speed, NULL)), 0), 0) 'AVG_SPEED_COM_S2', " +
			"IFNULL(ROUND(AVG(IF(lane >= sentido AND classe = '"+RoadConcessionaire.classMotorcycle+"', speed, NULL)), 0), 0) 'AVG_SPEED_MOTO_S2', " +
			"IFNULL(ROUND(AVG(IF(lane >= sentido, speed, NULL)), 0), 0) 'AVG_SPEED_TOTAL_S2', " +
		
		/* MEDIAN SPEED 50 */

			"IFNULL(ROUND(AVG(IF(lane < sentido AND (classe = '"+RoadConcessionaire.classLight+"' OR classe = '"+RoadConcessionaire.classUnknown+"' OR classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"' OR ((classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross < 3501) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross < 3501 ))), speed, NULL)) / 2, 0), 0) 'AVG_SPEED_50_AUTO_S1', " +
			"IFNULL(ROUND(AVG(IF(lane < sentido AND ((classe <> '"+RoadConcessionaire.classLight+"' AND classe <> '"+RoadConcessionaire.classUnknown+"' AND classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND classe <> '"+RoadConcessionaire.classMotorcycle+"' AND classe <> '"+RoadConcessionaire.classSemiTrailer+"' AND classe <> '"+RoadConcessionaire.classTrailer+"') OR (classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross > 3500) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross > 3500)), speed, NULL)) / 2, 0), 0) 'AVG_SPEED_50_COM_S1', " +
			"IFNULL(ROUND(AVG(IF(lane < sentido AND classe = '"+RoadConcessionaire.classMotorcycle+"', speed, NULL)) / 2, 0), 0) 'AVG_SPEED_50_MOTO_S1', " +
			"IFNULL(ROUND(AVG(IF(lane < sentido, speed, NULL)) / 2, 0), 0) 'AVG_SPEED_50_TOTAL_S1', " +
			
			"IFNULL(ROUND(AVG(IF(lane >= sentido AND (classe = '"+RoadConcessionaire.classLight+"' OR classe = '"+RoadConcessionaire.classUnknown+"' OR classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"' OR ((classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross < 3501) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross < 3501 ))), speed, NULL)) / 2, 0), 0) 'AVG_SPEED_50_AUTO_S2', " +
			"IFNULL(ROUND(AVG(IF(lane >= sentido AND ((classe <> '"+RoadConcessionaire.classLight+"' AND classe <> '"+RoadConcessionaire.classUnknown+"' AND classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND classe <> '"+RoadConcessionaire.classMotorcycle+"' AND classe <> '"+RoadConcessionaire.classSemiTrailer+"' AND classe <> '"+RoadConcessionaire.classTrailer+"') OR (classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross > 3500) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross > 3500)), speed, NULL)) / 2, 0), 0) 'AVG_SPEED_50_COM_S2', " +
			"IFNULL(ROUND(AVG(IF(lane >= sentido AND classe = '"+RoadConcessionaire.classMotorcycle+"', speed, NULL)) / 2, 0), 0) 'AVG_SPEED_50_MOTO_S2', " +
			"IFNULL(ROUND(AVG(IF(lane >= sentido, speed, NULL)) / 2, 0), 0) 'AVG_SPEED_50_TOTAL_S2', " +
		
		/* MEDIAN SPEED 85 */
	
			"IFNULL(ROUND(AVG(IF(lane < sentido AND (classe = '"+RoadConcessionaire.classLight+"' OR classe = '"+RoadConcessionaire.classUnknown+"' OR classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"' OR ((classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross < 3501) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross < 3501 ))), speed, NULL)) * 0.85, 0), 0) 'AVG_SPEED_85_AUTO_S1', " +
			"IFNULL(ROUND(AVG(IF(lane < sentido AND ((classe <> '"+RoadConcessionaire.classLight+"' AND classe <> '"+RoadConcessionaire.classUnknown+"' AND classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND classe <> '"+RoadConcessionaire.classMotorcycle+"' AND classe <> '"+RoadConcessionaire.classSemiTrailer+"' AND classe <> '"+RoadConcessionaire.classTrailer+"') OR (classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross > 3500) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross > 3500)), speed, NULL)) * 0.85, 0), 0) 'AVG_SPEED_85_COM_S1', " +
			"IFNULL(ROUND(AVG(IF(lane < sentido AND classe = '"+RoadConcessionaire.classMotorcycle+"', speed, NULL)) * 0.85, 0), 0) 'AVG_SPEED_85_MOTO_S1', " +
			"IFNULL(ROUND(AVG(IF(lane < sentido, speed, NULL)) * 0.85, 0), 0) 'AVG_SPEED_85_TOTAL_S1', " +
			
			"IFNULL(ROUND(AVG(IF(lane >= sentido AND (classe = '"+RoadConcessionaire.classLight+"' OR classe = '"+RoadConcessionaire.classUnknown+"' OR classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"' OR ((classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross < 3501) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross < 3501 ))), speed, NULL)) * 0.85, 0), 0) 'AVG_SPEED_85_AUTO_S2', " +
			"IFNULL(ROUND(AVG(IF(lane >= sentido AND ((classe <> '"+RoadConcessionaire.classLight+"' AND classe <> '"+RoadConcessionaire.classUnknown+"' AND classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND classe <> '"+RoadConcessionaire.classMotorcycle+"' AND classe <> '"+RoadConcessionaire.classSemiTrailer+"' AND classe <> '"+RoadConcessionaire.classTrailer+"') OR (classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross > 3500) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross > 3500)), speed, NULL)) * 0.85, 0), 0) 'AVG_SPEED_85_COM_S2', " +
			"IFNULL(ROUND(AVG(IF(lane >= sentido AND classe = '"+RoadConcessionaire.classMotorcycle+"', speed, NULL)) * 0.85, 0), 0) 'AVG_SPEED_85_MOTO_S2', " +
			"IFNULL(ROUND(AVG(IF(lane >= sentido, speed, NULL)) * 0.85, 0), 0) 'AVG_SPEED_85_TOTAL_S2', " +
		
		/* MAX_SPEED_SPEED */
	
			"IFNULL(MAX(IF(lane < sentido AND (classe = '"+RoadConcessionaire.classLight+"' OR classe = '"+RoadConcessionaire.classUnknown+"' OR classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"' OR ((classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross < 3501) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross < 3501 ))), speed, NULL)), 0) 'MAX_SPEED_AUTO_S1', " +
			"IFNULL(MAX(IF(lane < sentido AND ((classe <> '"+RoadConcessionaire.classLight+"' AND classe <> '"+RoadConcessionaire.classUnknown+"' AND classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND classe <> '"+RoadConcessionaire.classMotorcycle+"' AND classe <> '"+RoadConcessionaire.classSemiTrailer+"' AND classe <> '"+RoadConcessionaire.classTrailer+"') OR (classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross > 3500) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross > 3500)), speed, NULL)), 0) 'MAX_SPEED_COM_S1', " +
			"IFNULL(MAX(IF(lane < sentido AND classe = '"+RoadConcessionaire.classMotorcycle+"', speed, NULL)), 0) 'MAX_SPEED_MOTO_S1', " +
			"IFNULL(MAX(IF(lane < sentido, speed, NULL)), 0) 'MAX_SPEED_TOTAL_S1', " +
			
			"IFNULL(MAX(IF(lane >= sentido AND (classe = '"+RoadConcessionaire.classLight+"' OR classe = '"+RoadConcessionaire.classUnknown+"' OR classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"' OR ((classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross < 3501) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross < 3501 ))), speed, NULL)), 0) 'MAX_SPEED_AUTO_S2', " +
			"IFNULL(MAX(IF(lane >= sentido AND ((classe <> '"+RoadConcessionaire.classLight+"' AND classe <> '"+RoadConcessionaire.classUnknown+"' AND classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND classe <> '"+RoadConcessionaire.classMotorcycle+"' AND classe <> '"+RoadConcessionaire.classSemiTrailer+"' AND classe <> '"+RoadConcessionaire.classTrailer+"') OR (classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross > 3500) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross > 3500)), speed, NULL)), 0) 'MAX_SPEED_COM_S2', " +
			"IFNULL(MAX(IF(lane >= sentido AND classe = '"+RoadConcessionaire.classMotorcycle+"', speed, NULL)), 0) 'MAX_SPEED_MOTO_S2', " +
			"IFNULL(MAX(IF(lane >= sentido, speed, NULL)), 0) 'MAX_SPEED_TOTAL_S2', " +
		
		/* MIN_SPEED_ SPEED  */			 
	
			"IFNULL(MIN(IF(lane < sentido AND (classe = '"+RoadConcessionaire.classLight+"' OR classe = '"+RoadConcessionaire.classUnknown+"' OR classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"' OR ((classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross < 3501) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross < 3501 ))), speed, NULL)), 0) 'MIN_SPEED_AUTO_S1', " +
			"IFNULL(MIN(IF(lane < sentido AND ((classe <> '"+RoadConcessionaire.classLight+"' AND classe <> '"+RoadConcessionaire.classUnknown+"' AND classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND classe <> '"+RoadConcessionaire.classMotorcycle+"' AND classe <> '"+RoadConcessionaire.classSemiTrailer+"' AND classe <> '"+RoadConcessionaire.classTrailer+"') OR (classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross > 3500) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross > 3500)), speed, NULL)), 0) 'MIN_SPEED_COM_S1', " +
			"IFNULL(MIN(IF(lane < sentido AND classe = '"+RoadConcessionaire.classMotorcycle+"', speed, NULL)), 0) 'MIN_SPEED_MOTO_S1', " +
			"IFNULL(MIN(IF(lane < sentido, speed, NULL)), 0) 'MIN_SPEED_TOTAL_S1', " +
			
			"IFNULL(MIN(IF(lane >= sentido AND (classe = '"+RoadConcessionaire.classLight+"' OR classe = '"+RoadConcessionaire.classUnknown+"' OR classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"' OR ((classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross < 3501) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross < 3501 ))), speed, NULL)), 0) 'MIN_SPEED_AUTO_S2', " +
			"IFNULL(MIN(IF(lane >= sentido AND ((classe <> '"+RoadConcessionaire.classLight+"' AND classe <> '"+RoadConcessionaire.classUnknown+"' AND classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND classe <> '"+RoadConcessionaire.classMotorcycle+"' AND classe <> '"+RoadConcessionaire.classSemiTrailer+"' AND classe <> '"+RoadConcessionaire.classTrailer+"') OR (classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross > 3500) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross > 3500)), speed, NULL)), 0) 'MIN_SPEED_COM_S2', " +
			"IFNULL(MIN(IF(lane >= sentido AND classe = '"+RoadConcessionaire.classMotorcycle+"', speed, NULL)), 0) 'MIN_SPEED_MOTO_S2', " +
			"IFNULL(MIN(IF(lane >= sentido, speed, NULL)), 0) 'MIN_SPEED_TOTAL_S2', " +
		
		/* STANDARD DEVIATION */
		
			"IFNULL(ROUND(STD(IF(lane < sentido AND (classe = '"+RoadConcessionaire.classLight+"' OR classe = '"+RoadConcessionaire.classUnknown+"' OR classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"' OR ((classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross < 3501) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross < 3501 ))), speed, NULL)), 0), 0) 'STD_SPEED_AUTO_S1', " +
			"IFNULL(ROUND(STD(IF(lane < sentido AND ((classe <> '"+RoadConcessionaire.classLight+"' AND classe <> '"+RoadConcessionaire.classUnknown+"' AND classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND classe <> '"+RoadConcessionaire.classMotorcycle+"' AND classe <> '"+RoadConcessionaire.classSemiTrailer+"' AND classe <> '"+RoadConcessionaire.classTrailer+"') OR (classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross > 3500) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross > 3500)), speed, NULL)), 0), 0) 'STD_SPEED_COM_S1', " +
			"IFNULL(ROUND(STD(IF(lane < sentido AND classe = '"+RoadConcessionaire.classMotorcycle+"', speed, NULL)), 0), 0) 'STD_SPEED_MOTO_S1', " +
			"IFNULL(ROUND(STD(IF(lane < sentido, speed, NULL)), 0), 0) 'STD_SPEED_TOTAL_S1', " +
			
			"IFNULL(ROUND(STD(IF(lane >= sentido AND (classe = '"+RoadConcessionaire.classLight+"' OR classe = '"+RoadConcessionaire.classUnknown+"' OR classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"' OR ((classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross < 3501) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross < 3501 ))), speed, NULL)), 0), 0) 'STD_SPEED_AUTO_S2', " +
			"IFNULL(ROUND(STD(IF(lane >= sentido AND ((classe <> '"+RoadConcessionaire.classLight+"' AND classe <> '"+RoadConcessionaire.classUnknown+"' AND classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND classe <> '"+RoadConcessionaire.classMotorcycle+"' AND classe <> '"+RoadConcessionaire.classSemiTrailer+"' AND classe <> '"+RoadConcessionaire.classTrailer+"') OR (classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross > 3500) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross > 3500)), speed, NULL)), 0), 0) 'STD_SPEED_COM_S2', " +
			"IFNULL(ROUND(STD(IF(lane >= sentido AND classe = '"+RoadConcessionaire.classMotorcycle+"', speed, NULL)), 0), 0) 'STD_SPEED_MOTO_S2', " +
			"IFNULL(ROUND(STD(IF(lane >= sentido, speed, NULL)), 0), 0) 'STD_SPEED_TOTAL_S2'";
		
		select += "FROM tb_vbv " +
				  "LEFT JOIN equip s ON tb_vbv.siteID = s.equip_id " +  
				  "WHERE data BETWEEN ? AND ? AND siteID = ? " ;
						
		// -------------------------------------------------------------------------------------------------------------------------------------
		
		if(period.equals("15 minutes"))			
		   	 select += "GROUP BY SD, INTERVALS ORDER BY SD, INTERVALS ";
				
		if(period.equals("01 hour"))			
			 select += "GROUP BY SD, INTERVALS ORDER BY SD, INTERVALS";
				
		if(period.equals("24 hours"))			
			 select += "GROUP BY SD";		
		
		System.out.println(select);
				
		try {
		
			conn.start(1);
			
			conn.prepare(temp);
			conn.executeUpdate();
			
			conn.prepare_my(select);
			conn.prepare_ms(select
				.replace("DATE_FORMAT", "FORMAT")
				.replace("%Y-%m-%d", "yyyy-MM-dd")
				.replace("HOUR(", "DATEPART(HOUR, ")
				.replace("MINUTE(", "DATEPART(MINUTE, ")
				.replace("IFNULL", "ISNULL"));
			conn.setString(1, startDate);
		    conn.setString(2, endDate);
		    conn.setString(3, equipId);
		  		    
		   // System.out.println("DT: "+startDate+" DT: "+endDate+" EQ: "+equipId);
														
			MapResult result = conn.executeQuery();
			
			int row = 0;
						
				if (result.hasNext()) {
					for (RowResult rs : result) {
						
						for (int col = 0; col < fieldsNumber; col++) { // Colunas

							resultSet[row][col] = rs.getString((col + 1));
						
						    //System.out.println("LIN["+row+"]COL["+col+"] = "+resultSet[row][col] );
							// DEBBUGER
						}

						row++;										
					}
				}		
		
		} catch (Exception sqle) {
			
			StringWriter errors = new StringWriter();
			sqle.printStackTrace(new PrintWriter(errors));
			
			sqle.printStackTrace();
			
			//SystemLog.logErrorSQL(errorFolder.concat("error_get_vehicle"), FluxoPeriodoDAO.class.getCanonicalName(), sqle.hashCode(), sqle.toString(), sqle.getMessage(), errors.toString());
									
		}finally {
			
			conn.close();
		
		}
		
		return resultSet;
		
	}
	
	// --------------------------------------------------------------------------------------------------------------
	
		/** Método para ordenar os sentidos de um equipamento
		 * @author Wellington da Silva criado em 01/03/2019 10:06 - atualizado em 06/01/2022 18:00	
		 * @version 1.2
		 * @since 1.0 
		 * @param lane1 primeira faixa do equipamento
		 *  
		 */	
		
		public void setDirections(String lane1) {
			
			switch(lane1) {
			
			case "N": direction1 = "N"; direction2 = "S"; break;
			case "S": direction1 = "S"; direction2 = "N"; break;
			case "L": direction1 = "L"; direction2 = "O"; break;
			case "O": direction1 = "O"; direction2 = "L"; break;
						
			}
		}
		
	// --------------------------------------------------------------------------------------------------------------
}
