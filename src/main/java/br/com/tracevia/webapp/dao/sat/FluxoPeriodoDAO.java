package br.com.tracevia.webapp.dao.sat;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import com.groupdocs.conversion.internal.c.a.pd.internal.imaging.system.Threading.Thread;

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
	
	public List<FluxoPeriodo> getVehicles(String startDate, String endDate, String equipId, String period, SAT sat) {		
				
		List<FluxoPeriodo> lista = new ArrayList<FluxoPeriodo>();			
						
		setDirections(sat.getFaixa1()); // DEFINE DIRECTIONS FIRST

		String temp = "CREATE TEMPORARY TABLE IF NOT EXISTS test_wim.equip as SELECT s.equip_id, CASE WHEN (s.dir_lane1 = s.dir_lane2 AND s.dir_lane1 = s.dir_lane3 AND s.dir_lane1 = s.dir_lane4) THEN 5 " +
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
			
		select+="IFNULL(SUM(IF(d.lane < sentido AND (classe = '"+RoadConcessionaire.classLight+"' OR classe = '"+RoadConcessionaire.classUnknown+"' OR classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"' OR ((classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross < 3501) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross < 3501 ))), 1, 0)), 0) 'SUM_AUTO_S1', " +
			"IFNULL(SUM(IF(d.lane < sentido AND ((classe <> '"+RoadConcessionaire.classLight+"' AND classe <> '"+RoadConcessionaire.classUnknown+"' AND classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND classe <> '"+RoadConcessionaire.classMotorcycle+"' AND classe <> '"+RoadConcessionaire.classSemiTrailer+"' AND classe <> '"+RoadConcessionaire.classTrailer+"') OR (classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross > 3500) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross > 3500)), 1, 0)), 0) 'SUM_COM_S1', " +
			"IFNULL(SUM(IF(d.lane < sentido AND d.classe = '"+RoadConcessionaire.classMotorcycle+"', 1, 0)), 0) 'SUM_MOTO_S1', " +
			"IFNULL(SUM(IF(d.lane < sentido, 1, 0)), 0) 'SUM_TOTAL_S1', " +
			
			"IFNULL(SUM(IF(d.lane >= sentido AND (classe = '"+RoadConcessionaire.classLight+"' OR classe = '"+RoadConcessionaire.classUnknown+"' OR classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"' OR ((classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross < 3501) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross < 3501 ))), 1, 0)), 0) 'SUM_AUTO_S2', " +
			"IFNULL(SUM(IF(d.lane >= sentido AND ((classe <> '"+RoadConcessionaire.classLight+"' AND classe <> '"+RoadConcessionaire.classUnknown+"' AND classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND classe <> '"+RoadConcessionaire.classMotorcycle+"' AND classe <> '"+RoadConcessionaire.classSemiTrailer+"' AND classe <> '"+RoadConcessionaire.classTrailer+"') OR (classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross > 3500) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross > 3500)), 1, 0)), 0) 'SUM_COM_S2', " +
			"IFNULL(SUM(IF(d.lane >= sentido AND d.classe = '"+RoadConcessionaire.classMotorcycle+"', 1, 0)), 0) 'SUM_MOTO_S2', " +
			"IFNULL(SUM(IF(d.lane >= sentido, 1, 0)), 0) 'SUM_TOTAL_S2', " +
		
		/* AVERAGE SPEED */
			
			"IFNULL(ROUND(AVG(IF(d.lane < sentido AND (classe = '"+RoadConcessionaire.classLight+"' OR classe = '"+RoadConcessionaire.classUnknown+"' OR classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"' OR ((classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross < 3501) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross < 3501 ))), d.speed, NULL)), 2), 0) 'AVG_SPEED_AUTO_S1', " +
			"IFNULL(ROUND(AVG(IF(d.lane < sentido AND ((classe <> '"+RoadConcessionaire.classLight+"' AND classe <> '"+RoadConcessionaire.classUnknown+"' AND classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND classe <> '"+RoadConcessionaire.classMotorcycle+"' AND classe <> '"+RoadConcessionaire.classSemiTrailer+"' AND classe <> '"+RoadConcessionaire.classTrailer+"') OR (classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross > 3500) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross > 3500)), d.speed, NULL)), 2), 0) 'AVG_SPEED_COM_S1', " +
			"IFNULL(ROUND(AVG(IF(d.lane < sentido AND d.classe = '"+RoadConcessionaire.classMotorcycle+"', d.speed, NULL)), 2), 0) 'AVG_SPEED_MOTO_S1', " +
			"IFNULL(ROUND(AVG(IF(d.lane < sentido, d.speed, NULL)), 2), 0) 'AVG_SPEED_TOTAL_S1', " +
			
			"IFNULL(ROUND(AVG(IF(d.lane >= sentido AND (classe = '"+RoadConcessionaire.classLight+"' OR classe = '"+RoadConcessionaire.classUnknown+"' OR classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"' OR ((classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross < 3501) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross < 3501 ))), d.speed, NULL)), 2), 0) 'AVG_SPEED_AUTO_S2', " +
			"IFNULL(ROUND(AVG(IF(d.lane >= sentido AND ((classe <> '"+RoadConcessionaire.classLight+"' AND classe <> '"+RoadConcessionaire.classUnknown+"' AND classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND classe <> '"+RoadConcessionaire.classMotorcycle+"' AND classe <> '"+RoadConcessionaire.classSemiTrailer+"' AND classe <> '"+RoadConcessionaire.classTrailer+"') OR (classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross > 3500) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross > 3500)), d.speed, NULL)), 2), 0) 'AVG_SPEED_COM_S2', " +
			"IFNULL(ROUND(AVG(IF(d.lane >= sentido AND d.classe = '"+RoadConcessionaire.classMotorcycle+"', d.speed, NULL)), 2), 0) 'AVG_SPEED_MOTO_S2', " +
			"IFNULL(ROUND(AVG(IF(d.lane >= sentido, d.speed, NULL)), 2), 0) 'AVG_SPEED_TOTAL_S2', " +
		
		/* MEDIAN SPEED 50 */

			"IFNULL(ROUND(AVG(IF(d.lane < sentido AND (classe = '"+RoadConcessionaire.classLight+"' OR classe = '"+RoadConcessionaire.classUnknown+"' OR classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"' OR ((classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross < 3501) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross < 3501 ))), d.speed, NULL)) / 2, 2), 0) 'AVG_SPEED_50_AUTO_S1', " +
			"IFNULL(ROUND(AVG(IF(d.lane < sentido AND ((classe <> '"+RoadConcessionaire.classLight+"' AND classe <> '"+RoadConcessionaire.classUnknown+"' AND classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND classe <> '"+RoadConcessionaire.classMotorcycle+"' AND classe <> '"+RoadConcessionaire.classSemiTrailer+"' AND classe <> '"+RoadConcessionaire.classTrailer+"') OR (classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross > 3500) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross > 3500)), d.speed, NULL)) / 2, 2), 0) 'AVG_SPEED_50_COM_S1', " +
			"IFNULL(ROUND(AVG(IF(d.lane < sentido AND d.classe = '"+RoadConcessionaire.classMotorcycle+"', d.speed, NULL)) / 2, 2), 0) 'AVG_SPEED_50_MOTO_S1', " +
			"IFNULL(ROUND(AVG(IF(d.lane < sentido, d.speed, NULL)) / 2, 2), 0) 'AVG_SPEED_50_TOTAL_S1', " +
			
			"IFNULL(ROUND(AVG(IF(d.lane >= sentido AND (classe = '"+RoadConcessionaire.classLight+"' OR classe = '"+RoadConcessionaire.classUnknown+"' OR classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"' OR ((classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross < 3501) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross < 3501 ))), d.speed, NULL)) / 2, 2), 0) 'AVG_SPEED_50_AUTO_S2', " +
			"IFNULL(ROUND(AVG(IF(d.lane >= sentido AND ((classe <> '"+RoadConcessionaire.classLight+"' AND classe <> '"+RoadConcessionaire.classUnknown+"' AND classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND classe <> '"+RoadConcessionaire.classMotorcycle+"' AND classe <> '"+RoadConcessionaire.classSemiTrailer+"' AND classe <> '"+RoadConcessionaire.classTrailer+"') OR (classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross > 3500) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross > 3500)), d.speed, NULL)) / 2, 2), 0) 'AVG_SPEED_50_COM_S2', " +
			"IFNULL(ROUND(AVG(IF(d.lane >= sentido AND d.classe = '"+RoadConcessionaire.classMotorcycle+"', d.speed, NULL)) / 2, 2), 0) 'AVG_SPEED_50_MOTO_S2', " +
			"IFNULL(ROUND(AVG(IF(d.lane >= sentido, d.speed, NULL)) / 2, 2), 0) 'AVG_SPEED_50_TOTAL_S2', " +
		
		/* MEDIAN SPEED 85 */
	
			"IFNULL(ROUND(AVG(IF(d.lane < sentido AND (classe = '"+RoadConcessionaire.classLight+"' OR classe = '"+RoadConcessionaire.classUnknown+"' OR classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"' OR ((classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross < 3501) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross < 3501 ))), d.speed, NULL)) * 0.85, 2), 0) 'AVG_SPEED_85_AUTO_S1', " +
			"IFNULL(ROUND(AVG(IF(d.lane < sentido AND ((classe <> '"+RoadConcessionaire.classLight+"' AND classe <> '"+RoadConcessionaire.classUnknown+"' AND classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND classe <> '"+RoadConcessionaire.classMotorcycle+"' AND classe <> '"+RoadConcessionaire.classSemiTrailer+"' AND classe <> '"+RoadConcessionaire.classTrailer+"') OR (classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross > 3500) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross > 3500)), d.speed, NULL)) * 0.85, 2), 0) 'AVG_SPEED_85_COM_S1', " +
			"IFNULL(ROUND(AVG(IF(d.lane < sentido AND d.classe = '"+RoadConcessionaire.classMotorcycle+"', d.speed, NULL)) * 0.85, 2), 0) 'AVG_SPEED_85_MOTO_S1', " +
			"IFNULL(ROUND(AVG(IF(d.lane < sentido, d.speed, NULL)) * 0.85, 2), 0) 'AVG_SPEED_85_TOTAL_S1', " +
			
			"IFNULL(ROUND(AVG(IF(d.lane >= sentido AND (classe = '"+RoadConcessionaire.classLight+"' OR classe = '"+RoadConcessionaire.classUnknown+"' OR classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"' OR ((classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross < 3501) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross < 3501 ))), d.speed, NULL)) * 0.85, 2), 0) 'AVG_SPEED_85_AUTO_S2', " +
			"IFNULL(ROUND(AVG(IF(d.lane >= sentido AND ((classe <> '"+RoadConcessionaire.classLight+"' AND classe <> '"+RoadConcessionaire.classUnknown+"' AND classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND classe <> '"+RoadConcessionaire.classMotorcycle+"' AND classe <> '"+RoadConcessionaire.classSemiTrailer+"' AND classe <> '"+RoadConcessionaire.classTrailer+"') OR (classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross > 3500) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross > 3500)), d.speed, NULL)) * 0.85, 2), 0) 'AVG_SPEED_85_COM_S2', " +
			"IFNULL(ROUND(AVG(IF(d.lane >= sentido AND d.classe = '"+RoadConcessionaire.classMotorcycle+"', d.speed, NULL)) * 0.85, 2), 0) 'AVG_SPEED_85_MOTO_S2', " +
			"IFNULL(ROUND(AVG(IF(d.lane >= sentido, d.speed, NULL)) * 0.85, 2), 0) 'AVG_SPEED_85_TOTAL_S2', " +
		
		/* MAX_SPEED_SPEED */
	
			"IFNULL(MAX(IF(d.lane < sentido AND (classe = '"+RoadConcessionaire.classLight+"' OR classe = '"+RoadConcessionaire.classUnknown+"' OR classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"' OR ((classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross < 3501) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross < 3501 ))), d.speed, NULL)), 0) 'MAX_SPEED_AUTO_S1', " +
			"IFNULL(MAX(IF(d.lane < sentido AND ((classe <> '"+RoadConcessionaire.classLight+"' AND classe <> '"+RoadConcessionaire.classUnknown+"' AND classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND classe <> '"+RoadConcessionaire.classMotorcycle+"' AND classe <> '"+RoadConcessionaire.classSemiTrailer+"' AND classe <> '"+RoadConcessionaire.classTrailer+"') OR (classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross > 3500) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross > 3500)), d.speed, NULL)), 0) 'MAX_SPEED_COM_S1', " +
			"IFNULL(MAX(IF(d.lane < sentido AND d.classe = '"+RoadConcessionaire.classMotorcycle+"', d.speed, NULL)), 0) 'MAX_SPEED_MOTO_S1', " +
			"IFNULL(MAX(IF(d.lane < sentido, d.speed, NULL)), 0) 'MAX_SPEED_TOTAL_S1', " +
			
			"IFNULL(MAX(IF(d.lane >= sentido AND (classe = '"+RoadConcessionaire.classLight+"' OR classe = '"+RoadConcessionaire.classUnknown+"' OR classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"' OR ((classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross < 3501) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross < 3501 ))), d.speed, NULL)), 0) 'MAX_SPEED_AUTO_S2', " +
			"IFNULL(MAX(IF(d.lane >= sentido AND ((classe <> '"+RoadConcessionaire.classLight+"' AND classe <> '"+RoadConcessionaire.classUnknown+"' AND classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND classe <> '"+RoadConcessionaire.classMotorcycle+"' AND classe <> '"+RoadConcessionaire.classSemiTrailer+"' AND classe <> '"+RoadConcessionaire.classTrailer+"') OR (classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross > 3500) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross > 3500)), d.speed, NULL)), 0) 'MAX_SPEED_COM_S2', " +
			"IFNULL(MAX(IF(d.lane >= sentido AND d.classe = '"+RoadConcessionaire.classMotorcycle+"', d.speed, NULL)), 0) 'MAX_SPEED_MOTO_S2', " +
			"IFNULL(MAX(IF(d.lane >= sentido, d.speed, NULL)), 0) 'MAX_SPEED_TOTAL_S2', " +
		
		/* MIN_SPEED_ SPEED  */			 
	
			"IFNULL(MIN(IF(d.lane < sentido AND (classe = '"+RoadConcessionaire.classLight+"' OR classe = '"+RoadConcessionaire.classUnknown+"' OR classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"' OR ((classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross < 3501) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross < 3501 ))), d.speed, NULL)), 0) 'MIN_SPEED_AUTO_S1', " +
			"IFNULL(MIN(IF(d.lane < sentido AND ((classe <> '"+RoadConcessionaire.classLight+"' AND classe <> '"+RoadConcessionaire.classUnknown+"' AND classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND classe <> '"+RoadConcessionaire.classMotorcycle+"' AND classe <> '"+RoadConcessionaire.classSemiTrailer+"' AND classe <> '"+RoadConcessionaire.classTrailer+"') OR (classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross > 3500) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross > 3500)), d.speed, NULL)), 0) 'MIN_SPEED_COM_S1', " +
			"IFNULL(MIN(IF(d.lane < sentido AND d.classe = '"+RoadConcessionaire.classMotorcycle+"', d.speed, NULL)), 0) 'MIN_SPEED_MOTO_S1', " +
			"IFNULL(MIN(IF(d.lane < sentido, d.speed, NULL)), 0) 'MIN_SPEED_TOTAL_S1', " +
			
			"IFNULL(MIN(IF(d.lane >= sentido AND (classe = '"+RoadConcessionaire.classLight+"' OR classe = '"+RoadConcessionaire.classUnknown+"' OR classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"' OR ((classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross < 3501) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross < 3501 ))), d.speed, NULL)), 0) 'MIN_SPEED_AUTO_S2', " +
			"IFNULL(MIN(IF(d.lane >= sentido AND ((classe <> '"+RoadConcessionaire.classLight+"' AND classe <> '"+RoadConcessionaire.classUnknown+"' AND classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND classe <> '"+RoadConcessionaire.classMotorcycle+"' AND classe <> '"+RoadConcessionaire.classSemiTrailer+"' AND classe <> '"+RoadConcessionaire.classTrailer+"') OR (classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross > 3500) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross > 3500)), d.speed, NULL)), 0) 'MIN_SPEED_COM_S2', " +
			"IFNULL(MIN(IF(d.lane >= sentido AND d.classe = '"+RoadConcessionaire.classMotorcycle+"', d.speed, NULL)), 0) 'MIN_SPEED_MOTO_S2', " +
			"IFNULL(MIN(IF(d.lane >= sentido, d.speed, NULL)), 0) 'MIN_SPEED_TOTAL_S2', " +
		
		/* STANDARD DEVIATION */
		
			"IFNULL(STD(IF(d.lane < sentido AND (classe = '"+RoadConcessionaire.classLight+"' OR classe = '"+RoadConcessionaire.classUnknown+"' OR classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"' OR ((classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross < 3501) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross < 3501 ))), d.speed, NULL)), 0) 'STD_SPEED_AUTO_S1', " +
			"IFNULL(STD(IF(d.lane < sentido AND ((classe <> '"+RoadConcessionaire.classLight+"' AND classe <> '"+RoadConcessionaire.classUnknown+"' AND classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND classe <> '"+RoadConcessionaire.classMotorcycle+"' AND classe <> '"+RoadConcessionaire.classSemiTrailer+"' AND classe <> '"+RoadConcessionaire.classTrailer+"') OR (classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross > 3500) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross > 3500)), d.speed, NULL)), 0) 'STD_SPEED_COM_S1', " +
			"IFNULL(STD(IF(d.lane < sentido AND d.classe = '"+RoadConcessionaire.classMotorcycle+"', d.speed, NULL)), 0) 'STD_SPEED_MOTO_S1', " +
			"IFNULL(STD(IF(d.lane < sentido, d.speed, NULL)), 0) 'STD_SPEED_TOTAL_S1', " +
			
			"IFNULL(STD(IF(d.lane >= sentido AND (classe = '"+RoadConcessionaire.classLight+"' OR classe = '"+RoadConcessionaire.classUnknown+"' OR classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"' OR ((classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross < 3501) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross < 3501 ))), d.speed, NULL)), 0) 'STD_SPEED_AUTO_S2', " +
			"IFNULL(STD(IF(d.lane >= sentido AND ((classe <> '"+RoadConcessionaire.classLight+"' AND classe <> '"+RoadConcessionaire.classUnknown+"' AND classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND classe <> '"+RoadConcessionaire.classMotorcycle+"' AND classe <> '"+RoadConcessionaire.classSemiTrailer+"' AND classe <> '"+RoadConcessionaire.classTrailer+"') OR (classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross > 3500) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross > 3500)), d.speed, NULL)), 0) 'STD_SPEED_COM_S2', " +
			"IFNULL(STD(IF(d.lane >= sentido AND d.classe = '"+RoadConcessionaire.classMotorcycle+"', d.speed, NULL)), 0) 'STD_SPEED_MOTO_S2', " +
			"IFNULL(STD(IF(d.lane >= sentido, d.speed, NULL)), 0) 'STD_SPEED_TOTAL_S2'";
		
		select += "FROM tb_vbv d " +
				  "LEFT JOIN equip s ON s.equip_id = d.siteID " +  
				  "WHERE d.data BETWEEN ? AND ? AND siteID = ? " ;
						
		// -------------------------------------------------------------------------------------------------------------------------------------
		
		if(period.equals("15 minutes"))			
		   	 select += "GROUP BY SD, INTERVALS ORDER BY SD, INTERVALS ";
				
		if(period.equals("01 hour"))			
			 select += "GROUP BY SD, INTERVALS ORDER BY SD, INTERVALS";
				
		if(period.equals("24 hours"))			
			 select += "GROUP BY SD";		
		
		//System.out.println(select);
				
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
						
				if (result.hasNext()) {
					for (RowResult rs : result) {
						
						FluxoPeriodo pe = new FluxoPeriodo();	
																							
							pe.setDate(rs.getString("SD"));							
							pe.setInterval(rs.getString("INTERVALS"));
														
							pe.setAutoS1(rs.getInt("SUM_AUTO_S1"));
							pe.setComS1(rs.getInt("SUM_COM_S1"));
							pe.setMotoS1(rs.getInt("SUM_MOTO_S1"));
							pe.setTotalS1(rs.getInt("SUM_TOTAL_S1"));
							
							pe.setAutoS2(rs.getInt("SUM_AUTO_S1"));
							pe.setComS2(rs.getInt("SUM_COM_S1"));
							pe.setMotoS2(rs.getInt("SUM_MOTO_S2"));
							pe.setTotalS2(rs.getInt("SUM_TOTAL_S2"));						
							
							pe.setSpeedAutoS1(rs.getInt("AVG_SPEED_AUTO_S1"));
							pe.setSpeedComS1(rs.getInt("AVG_SPEED_COM_S1"));
							pe.setSpeedMotoS1(rs.getInt("AVG_SPEED_MOTO_S1"));
							pe.setSpeedTotalS1(rs.getInt("AVG_SPEED_TOTAL_S1"));
							
							pe.setSpeedAutoS2(rs.getInt("AVG_SPEED_AUTO_S2"));
							pe.setSpeedComS2(rs.getInt("AVG_SPEED_COM_S2"));
							pe.setSpeedMotoS2(rs.getInt("AVG_SPEED_MOTO_S2"));
							pe.setSpeedTotalS2(rs.getInt("AVG_SPEED_TOTAL_S2"));
							
							pe.setSpeed50thAutoS1(rs.getInt("AVG_SPEED_50_AUTO_S1"));
							pe.setSpeed50thComS1(rs.getInt("AVG_SPEED_50_COM_S1"));
							pe.setSpeed50thMotoS1(rs.getInt("AVG_SPEED_50_MOTO_S1"));
							pe.setSpeed50thTotalS1(rs.getInt("AVG_SPEED_50_TOTAL_S1"));
							
							pe.setSpeed50thAutoS2(rs.getInt("AVG_SPEED_50_AUTO_S2"));
							pe.setSpeed50thComS2(rs.getInt("AVG_SPEED_50_COM_S2"));
							pe.setSpeed50thMotoS2(rs.getInt("AVG_SPEED_50_MOTO_S2"));
							pe.setSpeed50thTotalS2(rs.getInt("AVG_SPEED_50_TOTAL_S2"));
							
							pe.setSpeed85thAutoS1(rs.getInt("AVG_SPEED_85_AUTO_S1"));
							pe.setSpeed85thComS1(rs.getInt("AVG_SPEED_85_COM_S1"));
							pe.setSpeed85thMotoS1(rs.getInt("AVG_SPEED_85_MOTO_S1"));
							pe.setSpeed85thTotalS1(rs.getInt("AVG_SPEED_85_TOTAL_S1"));
							
							pe.setSpeed85thAutoS2(rs.getInt("AVG_SPEED_85_AUTO_S2"));
							pe.setSpeed85thComS2(rs.getInt("AVG_SPEED_85_COM_S2"));
							pe.setSpeed85thMotoS2(rs.getInt("AVG_SPEED_85_MOTO_S2"));
							pe.setSpeed85thTotalS2(rs.getInt("AVG_SPEED_85_TOTAL_S2"));
							
							pe.setSpeedMaxAutoS1(rs.getInt("MAX_SPEED_AUTO_S1"));
							pe.setSpeedMaxComS1(rs.getInt("MAX_SPEED_COM_S1"));
							pe.setSpeedMaxMotoS1(rs.getInt("MAX_SPEED_MOTO_S1"));
							pe.setSpeedMaxTotalS1(rs.getInt("MAX_SPEED_TOTAL_S1"));
							
							pe.setSpeedMaxAutoS2(rs.getInt("MAX_SPEED_AUTO_S2"));
							pe.setSpeedMaxComS2(rs.getInt("MAX_SPEED_COM_S2"));
							pe.setSpeedMaxMotoS2(rs.getInt("MAX_SPEED_MOTO_S2"));
							pe.setSpeedMaxTotalS2(rs.getInt("MAX_SPEED_TOTAL_S2"));
							
							pe.setSpeedMinAutoS1(rs.getInt("MIN_SPEED_AUTO_S1"));
							pe.setSpeedMinComS1(rs.getInt("MIN_SPEED_COM_S1"));
							pe.setSpeedMinMotoS1(rs.getInt("MIN_SPEED_MOTO_S1"));
							pe.setSpeedMinTotalS1(rs.getInt("MIN_SPEED_TOTAL_S1"));
							
							pe.setSpeedMinAutoS2(rs.getInt("MIN_SPEED_AUTO_S2"));
							pe.setSpeedMinComS2(rs.getInt("MIN_SPEED_COM_S2"));
							pe.setSpeedMinMotoS2(rs.getInt("MIN_SPEED_MOTO_S2"));
							pe.setSpeedMinTotalS2(rs.getInt("MIN_SPEED_TOTAL_S2"));					
							
							pe.setSpeedStdAutoS1(rs.getInt("STD_SPEED_AUTO_S1"));
							pe.setSpeedStdComS1(rs.getInt("STD_SPEED_COM_S1"));
							pe.setSpeedStdMotoS1(rs.getInt("STD_SPEED_MOTO_S1"));
							pe.setSpeedStdTotalS1(rs.getInt("STD_SPEED_TOTAL_S1"));
							
							pe.setSpeedStdAutoS2(rs.getInt("STD_SPEED_AUTO_S2"));
							pe.setSpeedStdComS2(rs.getInt("STD_SPEED_COM_S2"));
							pe.setSpeedStdMotoS2(rs.getInt("STD_SPEED_MOTO_S2"));
							pe.setSpeedStdTotalS2(rs.getInt("STD_SPEED_TOTAL_S2"));
						
						
						lista.add(pe);
						
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
		
		return lista;
		
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
