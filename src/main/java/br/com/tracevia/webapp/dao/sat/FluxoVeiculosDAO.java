package br.com.tracevia.webapp.dao.sat;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import br.com.tracevia.webapp.dao.global.EquipmentsDAO;
import br.com.tracevia.webapp.log.SystemLog;
import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.model.global.SQL_Tracevia;
import br.com.tracevia.webapp.model.global.ColumnsSql.RowResult;
import br.com.tracevia.webapp.model.global.ResultSql.MapResult;
import br.com.tracevia.webapp.model.sat.FluxoVeiculos;

public class FluxoVeiculosDAO {

	SQL_Tracevia conn = new SQL_Tracevia();
	
	String direction1, direction2; 
		
	// --------------------------------------------------------------------------------------------------------------
		
	// LOGS FOLDER
	
	private final String errorFolder = SystemLog.ERROR.concat("monthly-flow-dao\\");
	
	// --------------------------------------------------------------------------------------------------------------

	public FluxoVeiculosDAO() throws Exception {

		SystemLog.createLogFolder(errorFolder);
		
	}	
	
	// --------------------------------------------------------------------------------------------------------------
	
	/** Método para obter dados para construir o relatório do fluxo mensal
	 * @author Wellington da Silva criado em 01/03/2019 10:06 - atualizado em 06/01/2022 18:00	 
	 * @version 1.2
	 * @since 1.0 
	 * @param startDate data de inicio
	 * @param endDate data de término	
	 * @param equipId id do equipamento
	 * @param numberLanes número de linhas do equipamento
	 * @param laneDir1 primeira faixa do equipamento 
	 * @return lista de objetos do tipo FluxoVeiculos
	 *  
	 */	

	public List<FluxoVeiculos> getVehicles(String startDate, String endDate, String equipId, String laneDir1, int numberLanes) {		

		List<FluxoVeiculos> lista = new ArrayList<FluxoVeiculos>();		
											
			setDirections(laneDir1); // DEFINE DIRECTIONS FIRST
			

			String select = "SELECT DATE_FORMAT(data, '%Y-%m-%d') AS SD, " +
					"DATE_FORMAT((SEC_TO_TIME(TIME_TO_SEC(data) - TIME_TO_SEC(data)%(15*60))),'%H:%i') AS INTERVALS, ";
			
			if(numberLanes == 2) {				
           
						// Sentido 1
				
				select+= "IFNULL(ROUND(SUM(IF(((lane = 1) AND (eq.dir_lane1 = '"+direction1+"') AND (classe = '"+RoadConcessionaire.classLight+"' OR (classe='"+RoadConcessionaire.classUnknown+"' AND axlNumber < 10) OR classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"' OR (classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross < 3501) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross < 3501 ))), 1, NULL )),0),0) 'AUTOS1', " +
						 "IFNULL(ROUND(SUM(IF(((lane=1) AND (eq.dir_lane1 = '"+direction1+"') AND (classe <> '"+RoadConcessionaire.classLight+"' AND classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (classe <> '"+RoadConcessionaire.classUnknown+"' AND axlNumber < 10))), 1, NULL )),0),0) 'COMS1', " +
						 "IFNULL(ROUND(SUM(IF(((lane=1) AND (eq.dir_lane1 = '"+direction1+"') AND (classe = '"+RoadConcessionaire.classMotorcycle+"')), 1, NULL )),0),0) 'MOTOS1', " +
						 "IFNULL(ROUND(SUM(IF((lane=1) AND (eq.dir_lane1 = '"+direction1+"'), 1, NULL)),0),0) 'TOTALS1', " +			
						 "IFNULL(ROUND(AVG(IF((lane=1) AND (eq.dir_lane1 = '"+direction1+"'), speed, NULL)),0),0) 'SPEEDS1', " +
						
						 // Sentido 2
						
						 "IFNULL(ROUND(SUM(IF(((lane=2) AND (eq.dir_lane2 = '"+direction2+"') AND (classe = '"+RoadConcessionaire.classLight+"' OR (classe='"+RoadConcessionaire.classUnknown+"' AND axlNumber < 10) OR classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"' OR (classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross < 3501) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross < 3501 ))), 1, NULL )),0),0) 'AUTOS2', " +
						 "IFNULL(ROUND(SUM(IF(((lane=2) AND (eq.dir_lane2 = '"+direction2+"') AND (classe <> '"+RoadConcessionaire.classLight+"' AND classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (classe <> '"+RoadConcessionaire.classUnknown+"' AND axlNumber < 10))), 1, NULL )),0),0) 'COMS2', " +
						 "IFNULL(ROUND(SUM(IF(((lane=2) AND (eq.dir_lane2 = '"+direction2+"') AND (classe = '"+RoadConcessionaire.classMotorcycle+"')), 1, NULL )),0),0) 'MOTOS2', " +
						 "IFNULL(ROUND(SUM(IF((lane=2) AND (eq.dir_lane2 = '"+direction2+"'), 1, NULL)),0),0) 'TOTALS2', " +
						 "IFNULL(ROUND(AVG(IF((lane=2) AND (eq.dir_lane2 = '"+direction2+"'), speed, NULL)),0),0) 'SPEEDS2' "; 
			  
				}
			
			if(numberLanes == 3) {				
		           
						// Sentido 1
		
				select+= "IFNULL(ROUND(SUM(IF(((lane = 1) AND (eq.dir_lane1 = '"+direction1+"') AND (classe = '"+RoadConcessionaire.classLight+"' OR (classe='"+RoadConcessionaire.classUnknown+"' AND axlNumber < 10) OR classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"' OR (classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross < 3501) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross < 3501 ))), 1, NULL )),0),0) 'AUTOS1', " +
						 "IFNULL(ROUND(SUM(IF(((lane=1) AND (eq.dir_lane1 = '"+direction1+"') AND (classe <> '"+RoadConcessionaire.classLight+"' AND classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (classe <> '"+RoadConcessionaire.classUnknown+"' AND axlNumber < 10))), 1, NULL )),0),0) 'COMS1', " +
						 "IFNULL(ROUND(SUM(IF(((lane=1) AND (eq.dir_lane1 = '"+direction1+"') AND (classe = '"+RoadConcessionaire.classMotorcycle+"')), 1, NULL )),0),0) 'MOTOS1', " +
						 "IFNULL(ROUND(SUM(IF((lane=1) AND (eq.dir_lane1 = '"+direction1+"'), 1, NULL)),0),0) 'TOTALS1', " +			
						 "IFNULL(ROUND(AVG(IF((lane=1) AND (eq.dir_lane1 = '"+direction1+"'), speed, NULL)),0),0) 'SPEEDS1', " +
						
						 // Sentido 2
						
						 "IFNULL(ROUND(SUM(IF(((lane=2 OR lane = 3) AND (eq.dir_lane2 = '"+direction2+"' OR eq.dir_lane3 = '"+direction2+"') AND (classe = '"+RoadConcessionaire.classLight+"' OR (classe='"+RoadConcessionaire.classUnknown+"' AND axlNumber < 10) OR classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"' OR (classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross < 3501) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross < 3501 ))), 1, NULL )),0),0) 'AUTOS2', " +
						 "IFNULL(ROUND(SUM(IF(((lane=2 OR lane = 3) AND (eq.dir_lane2 = '"+direction2+"' OR eq.dir_lane3 = '"+direction2+"') AND (classe <> '"+RoadConcessionaire.classLight+"' AND classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (classe <> '"+RoadConcessionaire.classUnknown+"' AND axlNumber < 10))), 1, NULL )),0),0) 'COMS2', " +
						 "IFNULL(ROUND(SUM(IF(((lane=2 OR lane = 3) AND (eq.dir_lane2 = '"+direction2+"' OR eq.dir_lane3 = '"+direction2+"') AND (classe = '"+RoadConcessionaire.classMotorcycle+"')), 1, NULL )),0),0) 'MOTOS2', " +
						 "IFNULL(ROUND(SUM(IF((lane=2 OR lane = 3) AND (eq.dir_lane2 = '"+direction2+"' OR eq.dir_lane3 = '"+direction2+"'), 1, NULL)),0),0) 'TOTALS2', " +
						 "IFNULL(ROUND(AVG(IF((lane=2 OR lane = 3) AND (eq.dir_lane2 = '"+direction2+"' OR eq.dir_lane3 = '"+direction2+"'), speed, NULL)),0),0) 'SPEEDS2' "; 
			  
				}
			
			if(numberLanes == 4) {				
		           
						// Sentido 1

				select+= "IFNULL(ROUND(SUM(IF(((lane = 1 OR lane = 2) AND (eq.dir_lane1 = '"+direction1+"' OR eq.dir_lane2 = '"+direction1+"') AND (classe = '"+RoadConcessionaire.classLight+"' OR (classe='"+RoadConcessionaire.classUnknown+"' AND axlNumber < 10) OR classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"' OR (classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross < 3501) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross < 3501 ))), 1, NULL )),0),0) 'AUTOS1', " +
						 "IFNULL(ROUND(SUM(IF(((lane=1 OR lane = 2) AND (eq.dir_lane1 = '"+direction1+"' OR eq.dir_lane2 = '"+direction1+"') AND (classe <> '"+RoadConcessionaire.classLight+"' AND classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (classe <> '"+RoadConcessionaire.classUnknown+"' AND axlNumber < 10))), 1, NULL )),0),0) 'COMS1', " +
						 "IFNULL(ROUND(SUM(IF(((lane=1 OR lane = 2) AND (eq.dir_lane1 = '"+direction1+"' OR eq.dir_lane2 = '"+direction1+"') AND (classe = '"+RoadConcessionaire.classMotorcycle+"')), 1, NULL )),0),0) 'MOTOS1', " +
						 "IFNULL(ROUND(SUM(IF((lane=1 OR lane = 2) AND (eq.dir_lane1 = '"+direction1+"' OR eq.dir_lane2 = '"+direction1+"'), 1, NULL)),0),0) 'TOTALS1', " +			
						 "IFNULL(ROUND(AVG(IF((lane=1 OR lane = 2) AND (eq.dir_lane1 = '"+direction1+"' OR eq.dir_lane2 = '"+direction1+"'), speed, NULL)),0),0) 'SPEEDS1', " +
						
						 // Sentido 2
						
						 "IFNULL(ROUND(SUM(IF(((lane=3 OR lane = 4) AND (eq.dir_lane3 = '"+direction2+"' OR eq.dir_lane4 = '"+direction2+"') AND (classe = '"+RoadConcessionaire.classLight+"' OR (classe='"+RoadConcessionaire.classUnknown+"' AND axlNumber < 10) OR classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"' OR (classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross < 3501) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross < 3501 ))), 1, NULL )),0),0) 'AUTOS2', " +
						 "IFNULL(ROUND(SUM(IF(((lane=3 OR lane = 4) AND (eq.dir_lane3 = '"+direction2+"' OR eq.dir_lane4 = '"+direction2+"') AND (classe <> '"+RoadConcessionaire.classLight+"' AND classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (classe <> '"+RoadConcessionaire.classUnknown+"' AND axlNumber < 10))), 1, NULL )),0),0) 'COMS2', " +
						 "IFNULL(ROUND(SUM(IF(((lane=3 OR lane = 4) AND (eq.dir_lane3 = '"+direction2+"' OR eq.dir_lane4 = '"+direction2+"') AND (classe = '"+RoadConcessionaire.classMotorcycle+"')), 1, NULL )),0),0) 'MOTOS2', " +
						 "IFNULL(ROUND(SUM(IF((lane=3 OR lane = 4) AND (eq.dir_lane3 = '"+direction2+"' OR eq.dir_lane4 = '"+direction2+"'), 1, NULL)),0),0) 'TOTALS2', " +
						 "IFNULL(ROUND(AVG(IF((lane=3 OR lane = 4) AND (eq.dir_lane3 = '"+direction2+"' OR eq.dir_lane4 = '"+direction2+"'), speed, NULL)),0),0) 'SPEEDS2' "; 
			  
				}
			
			if(numberLanes == 5) {				
		           
						// Sentido 1

				select+= "IFNULL(ROUND(SUM(IF(((lane = 1 OR lane = 2) AND (eq.dir_lane1 = '"+direction1+"' OR eq.dir_lane2 = '"+direction1+"') AND (classe = '"+RoadConcessionaire.classLight+"' OR (classe='"+RoadConcessionaire.classUnknown+"' AND axlNumber < 10) OR classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"' OR (classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross < 3501) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross < 3501 ))), 1, NULL )),0),0) 'AUTOS1', " +
						 "IFNULL(ROUND(SUM(IF(((lane=1 OR lane = 2) AND (eq.dir_lane1 = '"+direction1+"' OR eq.dir_lane2 = '"+direction1+"') AND (classe <> '"+RoadConcessionaire.classLight+"' AND classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (classe <> '"+RoadConcessionaire.classUnknown+"' AND axlNumber < 10))), 1, NULL )),0),0) 'COMS1', " +
						 "IFNULL(ROUND(SUM(IF(((lane=1 OR lane = 2) AND (eq.dir_lane1 = '"+direction1+"' OR eq.dir_lane2 = '"+direction1+"') AND (classe = '"+RoadConcessionaire.classMotorcycle+"')), 1, NULL )),0),0) 'MOTOS1', " +
						 "IFNULL(ROUND(SUM(IF((lane=1 OR lane = 2) AND (eq.dir_lane1 = '"+direction1+"' OR eq.dir_lane2 = '"+direction1+"'), 1, NULL)),0),0) 'TOTALS1', " +			
						 "IFNULL(ROUND(AVG(IF((lane=1 OR lane = 2) AND (eq.dir_lane1 = '"+direction1+"' OR eq.dir_lane2 = '"+direction1+"'), speed, NULL)),0),0) 'SPEEDS1', " +
						
						 // Sentido 2
						
						 "IFNULL(ROUND(SUM(IF(((lane=3 OR lane = 4 OR lane = 5) AND (eq.dir_lane3 = '"+direction2+"' OR eq.dir_lane4 = '"+direction2+"' OR eq.dir_lane5 = '"+direction2+"') AND (classe = '"+RoadConcessionaire.classLight+"' OR (classe='"+RoadConcessionaire.classUnknown+"' AND axlNumber < 10) OR classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"' OR (classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross < 3501) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross < 3501 ))), 1, NULL )),0),0) 'AUTOS2', " +
						 "IFNULL(ROUND(SUM(IF(((lane=3 OR lane = 4 OR lane = 5) AND (eq.dir_lane3 = '"+direction2+"' OR eq.dir_lane4 = '"+direction2+"' OR eq.dir_lane5 = '"+direction2+"') AND (classe <> '"+RoadConcessionaire.classLight+"' AND classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (classe <> '"+RoadConcessionaire.classUnknown+"' AND axlNumber < 10))), 1, NULL )),0),0) 'COMS2', " +
						 "IFNULL(ROUND(SUM(IF(((lane=3 OR lane = 4 OR lane = 5) AND (eq.dir_lane3 = '"+direction2+"' OR eq.dir_lane4 = '"+direction2+"' OR eq.dir_lane5 = '"+direction2+"') AND (classe = '"+RoadConcessionaire.classMotorcycle+"')), 1, NULL )),0),0) 'MOTOS2', " +
						 "IFNULL(ROUND(SUM(IF((lane=3 OR lane = 4 OR lane = 5) AND (eq.dir_lane3 = '"+direction2+"' OR eq.dir_lane4 = '"+direction2+"' OR eq.dir_lane5 = '"+direction2+"'), 1, NULL)),0),0) 'TOTALS2', " +
						 "IFNULL(ROUND(AVG(IF((lane=3 OR lane = 4 OR lane = 5) AND (eq.dir_lane3 = '"+direction2+"' OR eq.dir_lane4 = '"+direction2+"' OR eq.dir_lane5 = '"+direction2+"'), speed, NULL)),0),0) 'SPEEDS2' "; 
			  
				}
			
			if(numberLanes == 6) {				
		           
						// Sentido 1

				select+= "IFNULL(ROUND(SUM(IF(((lane = 1 OR lane = 2 OR lane = 3) AND (eq.dir_lane1 = '"+direction1+"' OR eq.dir_lane2 = '"+direction1+"' OR eq.dir_lane3 = '"+direction1+"') AND (classe = '"+RoadConcessionaire.classLight+"' OR (classe='"+RoadConcessionaire.classUnknown+"' AND axlNumber < 10) OR classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"' OR (classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross < 3501) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross < 3501 ))), 1, NULL )),0),0) 'AUTOS1', " +
						 "IFNULL(ROUND(SUM(IF(((lane=1 OR lane = 2 OR lane = 3) AND (eq.dir_lane1 = '"+direction1+"' OR eq.dir_lane2 = '"+direction1+"' OR eq.dir_lane3 = '"+direction1+"') AND (classe <> '"+RoadConcessionaire.classLight+"' AND classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (classe <> '"+RoadConcessionaire.classUnknown+"' AND axlNumber < 10))), 1, NULL )),0),0) 'COMS1', " +
						 "IFNULL(ROUND(SUM(IF(((lane=1 OR lane = 2 OR lane = 3) AND (eq.dir_lane1 = '"+direction1+"' OR eq.dir_lane2 = '"+direction1+"' OR eq.dir_lane3 = '"+direction1+"') AND (classe = '"+RoadConcessionaire.classMotorcycle+"')), 1, NULL )),0),0) 'MOTOS1', " +
						 "IFNULL(ROUND(SUM(IF((lane=1 OR lane = 2 OR lane = 3) AND (eq.dir_lane1 = '"+direction1+"' OR eq.dir_lane2 = '"+direction1+"' OR eq.dir_lane3 = '"+direction1+"'), 1, NULL)),0),0) 'TOTALS1', " +			
						 "IFNULL(ROUND(AVG(IF((lane=1 OR lane = 2 OR lane = 3) AND (eq.dir_lane1 = '"+direction1+"' OR eq.dir_lane2 = '"+direction1+"' OR eq.dir_lane3 = '"+direction1+"'), speed, NULL)),0),0) 'SPEEDS1', " +
						
						 // Sentido 2
						
						 "IFNULL(ROUND(SUM(IF(((lane=4 OR lane = 5 OR lane = 6) AND (eq.dir_lane4 = '"+direction2+"' OR eq.dir_lane5 = '"+direction2+"' OR eq.dir_lane6 = '"+direction2+"') AND (classe = '"+RoadConcessionaire.classLight+"' OR (classe='"+RoadConcessionaire.classUnknown+"' AND axlNumber < 10) OR classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"' OR (classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross < 3501) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross < 3501 ))), 1, NULL )),0),0) 'AUTOS2', " +
						 "IFNULL(ROUND(SUM(IF(((lane=4 OR lane = 5 OR lane = 6) AND (eq.dir_lane4 = '"+direction2+"' OR eq.dir_lane5 = '"+direction2+"' OR eq.dir_lane6 = '"+direction2+"') AND (classe <> '"+RoadConcessionaire.classLight+"' AND classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (classe <> '"+RoadConcessionaire.classUnknown+"' AND axlNumber < 10))), 1, NULL )),0),0) 'COMS2', " +
						 "IFNULL(ROUND(SUM(IF(((lane=4 OR lane = 5 OR lane = 6) AND (eq.dir_lane4 = '"+direction2+"' OR eq.dir_lane5 = '"+direction2+"' OR eq.dir_lane6 = '"+direction2+"') AND (classe = '"+RoadConcessionaire.classMotorcycle+"')), 1, NULL )),0),0) 'MOTOS2', " +
						 "IFNULL(ROUND(SUM(IF((lane=4 OR lane = 5 OR lane = 6) AND (eq.dir_lane4 = '"+direction2+"' OR eq.dir_lane5 = '"+direction2+"' OR eq.dir_lane6 = '"+direction2+"'), 1, NULL)),0),0) 'TOTALS2', " +
						 "IFNULL(ROUND(AVG(IF((lane=4 OR lane = 5 OR lane = 6) AND (eq.dir_lane4 = '"+direction2+"' OR eq.dir_lane5 = '"+direction2+"' OR eq.dir_lane6 = '"+direction2+"'), speed, NULL)),0),0) 'SPEEDS2' "; 
			  
				}
			
			if(numberLanes == 7) {				
		           
						// Sentido 1
				
				select+= "IFNULL(ROUND(SUM(IF(((lane = 1 OR lane = 2 OR lane = 3) AND (eq.dir_lane1 = '"+direction1+"' OR eq.dir_lane2 = '"+direction1+"' OR eq.dir_lane3 = '"+direction1+"') AND (classe = '"+RoadConcessionaire.classLight+"' OR (classe='"+RoadConcessionaire.classUnknown+"' AND axlNumber < 10) OR classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"' OR (classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross < 3501) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross < 3501 ))), 1, NULL )),0),0) 'AUTOS1', " +
						 "IFNULL(ROUND(SUM(IF(((lane=1 OR lane = 2 OR lane = 3) AND (eq.dir_lane1 = '"+direction1+"' OR eq.dir_lane2 = '"+direction1+"' OR eq.dir_lane3 = '"+direction1+"') AND (classe <> '"+RoadConcessionaire.classLight+"' AND classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (classe <> '"+RoadConcessionaire.classUnknown+"' AND axlNumber < 10))), 1, NULL )),0),0) 'COMS1', " +
						 "IFNULL(ROUND(SUM(IF(((lane=1 OR lane = 2 OR lane = 3) AND (eq.dir_lane1 = '"+direction1+"' OR eq.dir_lane2 = '"+direction1+"' OR eq.dir_lane3 = '"+direction1+"') AND (classe = '"+RoadConcessionaire.classMotorcycle+"')), 1, NULL )),0),0) 'MOTOS1', " +
						 "IFNULL(ROUND(SUM(IF((lane=1 OR lane = 2 OR lane = 3) AND (eq.dir_lane1 = '"+direction1+"' OR eq.dir_lane2 = '"+direction1+"' OR eq.dir_lane3 = '"+direction1+"'), 1, NULL)),0),0) 'TOTALS1', " +			
						 "IFNULL(ROUND(AVG(IF((lane=1 OR lane = 2 OR lane = 3) AND (eq.dir_lane1 = '"+direction1+"' OR eq.dir_lane2 = '"+direction1+"' OR eq.dir_lane3 = '"+direction1+"'), speed, NULL)),0),0) 'SPEEDS1', " +
						
						 // Sentido 2
						
						 "IFNULL(ROUND(SUM(IF(((lane=4 OR lane = 5 OR lane = 6 OR lane = 7) AND (eq.dir_lane4 = '"+direction2+"' OR eq.dir_lane5 = '"+direction2+"' OR eq.dir_lane6 = '"+direction2+"' OR eq.dir_lane7 = '"+direction2+"') AND (classe = '"+RoadConcessionaire.classLight+"' OR (classe='"+RoadConcessionaire.classUnknown+"' AND axlNumber < 10) OR classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"' OR (classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross < 3501) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross < 3501 ))), 1, NULL )),0),0) 'AUTOS2', " +
						 "IFNULL(ROUND(SUM(IF(((lane=4 OR lane = 5 OR lane = 6 OR lane = 7) AND (eq.dir_lane4 = '"+direction2+"' OR eq.dir_lane5 = '"+direction2+"' OR eq.dir_lane6 = '"+direction2+"' OR eq.dir_lane7 = '"+direction2+"') AND (classe <> '"+RoadConcessionaire.classLight+"' AND classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (classe <> '"+RoadConcessionaire.classUnknown+"' AND axlNumber < 10))), 1, NULL )),0),0) 'COMS2', " +
						 "IFNULL(ROUND(SUM(IF(((lane=4 OR lane = 5 OR lane = 6 OR lane = 7) AND (eq.dir_lane4 = '"+direction2+"' OR eq.dir_lane5 = '"+direction2+"' OR eq.dir_lane6 = '"+direction2+"' OR eq.dir_lane7 = '"+direction2+"') AND (classe = '"+RoadConcessionaire.classMotorcycle+"')), 1, NULL )),0),0) 'MOTOS2', " +
						 "IFNULL(ROUND(SUM(IF((lane=4 OR lane = 5 OR lane = 6 OR lane = 7) AND (eq.dir_lane4 = '"+direction2+"' OR eq.dir_lane5 = '"+direction2+"' OR eq.dir_lane6 = '"+direction2+"' OR eq.dir_lane7 = '"+direction2+"'), 1, NULL)),0),0) 'TOTALS2', " +
						 "IFNULL(ROUND(AVG(IF((lane=4 OR lane = 5 OR lane = 6 OR lane = 7) AND (eq.dir_lane4 = '"+direction2+"' OR eq.dir_lane5 = '"+direction2+"' OR eq.dir_lane6 = '"+direction2+"' OR eq.dir_lane7 = '"+direction2+"'), speed, NULL)),0),0) 'SPEEDS2' "; 
			  
				}
			
			if(numberLanes == 8) {				
		           
						// Sentido 1
				
						select+= "IFNULL(ROUND(SUM(IF(((lane = 1 OR lane = 2 OR lane = 3 OR lane = 4) AND (eq.dir_lane1 = '"+direction1+"' OR eq.dir_lane2 = '"+direction1+" OR eq.dir_lane3 = '"+direction1+" OR eq.dir_lane4 = '"+direction1+"') AND (classe = '"+RoadConcessionaire.classLight+"' OR (classe='"+RoadConcessionaire.classUnknown+"' AND axlNumber < 10) OR classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"' OR (classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross < 3501) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross < 3501 ))), 1, NULL )),0),0) 'AUTOS1', " +
						 "IFNULL(ROUND(SUM(IF(((lane=1 OR lane = 2 OR lane = 3 OR lane = 4) AND (eq.dir_lane1 = '"+direction1+"' OR eq.dir_lane2 = '"+direction1+"' OR eq.dir_lane3 = '"+direction1+"' OR eq.dir_lane4 = '"+direction1+"') AND (classe <> '"+RoadConcessionaire.classLight+"' AND classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (classe <> '"+RoadConcessionaire.classUnknown+"' AND axlNumber < 10))), 1, NULL )),0),0) 'COMS1', " +
						 "IFNULL(ROUND(SUM(IF(((lane=1 OR lane = 2 OR lane = 3 OR lane = 4) AND (eq.dir_lane1 = '"+direction1+"' OR eq.dir_lane2 = '"+direction1+"' OR eq.dir_lane3 = '"+direction1+"' OR eq.dir_lane4 = '"+direction1+"') AND (classe = '"+RoadConcessionaire.classMotorcycle+"')), 1, NULL )),0),0) 'MOTOS1', " +
						 "IFNULL(ROUND(SUM(IF((lane=1 OR lane = 2 OR lane = 3 OR lane = 4) AND (eq.dir_lane1 = '"+direction1+"' OR eq.dir_lane2 = '"+direction1+"' OR eq.dir_lane3 = '"+direction1+"' OR eq.dir_lane4 = '"+direction1+"'), 1, NULL)),0),0) 'TOTALS1', " +			
						 "IFNULL(ROUND(AVG(IF((lane=1 OR lane = 2 OR lane = 3 OR lane = 4) AND (eq.dir_lane1 = '"+direction1+"' OR eq.dir_lane2 = '"+direction1+"' OR eq.dir_lane3 = '"+direction1+"' OR eq.dir_lane4 = '"+direction1+"'), speed, NULL)),0),0) 'SPEEDS1', " +
						
						 // Sentido 2
						
						 "IFNULL(ROUND(SUM(IF(((lane = 5 OR lane = 6 OR lane = 7 OR lane = 8) AND (eq.dir_lane5 = '"+direction2+"' OR eq.dir_lane6 = '"+direction2+"' OR eq.dir_lane7 = '"+direction2+"' OR eq.dir_lane8 = '"+direction2+"') AND (classe = '"+RoadConcessionaire.classLight+"' OR (classe='"+RoadConcessionaire.classUnknown+"' AND axlNumber < 10) OR classe = '"+RoadConcessionaire.classNotIdentifiedAxl2+"' OR (classe = '"+RoadConcessionaire.classSemiTrailer+"' AND gross < 3501) OR (classe = '"+RoadConcessionaire.classTrailer+"' AND gross < 3501 ))), 1, NULL )),0),0) 'AUTOS2', " +
						 "IFNULL(ROUND(SUM(IF(((lane = 5 OR lane = 6 OR lane = 7 OR lane = 8) AND (eq.dir_lane5 = '"+direction2+"' OR eq.dir_lane6 = '"+direction2+"' OR eq.dir_lane7 = '"+direction2+"' OR eq.dir_lane8 = '"+direction2+"') AND (classe <> '"+RoadConcessionaire.classLight+"' AND classe <> '"+RoadConcessionaire.classNotIdentifiedAxl2+"' AND classe <> '"+RoadConcessionaire.classMotorcycle+"' AND (classe <> '"+RoadConcessionaire.classUnknown+"' AND axlNumber < 10))), 1, NULL )),0),0) 'COMS2', " +
						 "IFNULL(ROUND(SUM(IF(((lane = 5 OR lane = 6 OR lane = 7 OR lane = 8) AND (eq.dir_lane5 = '"+direction2+"' OR eq.dir_lane6 = '"+direction2+"' OR eq.dir_lane7 = '"+direction2+"' OR eq.dir_lane8 = '"+direction2+"') AND (classe = '"+RoadConcessionaire.classMotorcycle+"')), 1, NULL )),0),0) 'MOTOS2', " +
						 "IFNULL(ROUND(SUM(IF((lane = 5 OR lane = 6 OR lane = 7 OR lane = 8) AND (eq.dir_lane5 = '"+direction2+"' OR eq.dir_lane6 = '"+direction2+"' OR eq.dir_lane7 = '"+direction2+"' OR eq.dir_lane8 = '"+direction2+"'), 1, NULL)),0),0) 'TOTALS2', " +
						 "IFNULL(ROUND(AVG(IF((lane = 5 OR lane = 6 OR lane = 7 OR lane = 8) AND (eq.dir_lane5 = '"+direction2+"' OR eq.dir_lane6 = '"+direction2+"' OR eq.dir_lane7 = '"+direction2+"' OR eq.dir_lane8 = '"+direction2+"'), speed, NULL)),0),0) 'SPEEDS2' "; 
			  
			   }											 		 					 													
		 								
			 			select += "FROM tb_vbv " +
								  "LEFT JOIN sat_equipment eq ON tb_vbv.siteID = eq.equip_id " + 
								  "WHERE data BETWEEN ? AND ? AND siteID = ? " +
								  "GROUP BY SD, INTERVALS ";					
			
					try {
				
						conn.start(1);
			
						conn.prepare_my(select);
						conn.prepare_ms(select
							.replace("DATE_FORMAT", "FORMAT")
							.replace("%Y-%m-%d", "yyyy-MM-dd")
							.replace("%H:%i", "hh:mm")
							.replace("HOUR(", "DATEPART(HOUR, ")
							.replace("MINUTE(", "DATEPART(MINUTE, ")
							.replaceAll("IFNULL", "ISNULL"));
						conn.setString(1, startDate);
						conn.setString(2, endDate);
						conn.setString(3, equipId);
						
						//System.out.println(select);
																	
						MapResult result = conn.executeQuery();
												
							if (result.hasNext()) {
								for (RowResult rs : result) {
				
									FluxoVeiculos veh = new FluxoVeiculos();														
				
									veh.setDate(rs.getString("SD"));	
									veh.setInterval(rs.getString("INTERVALS"));
									veh.setMoto1(rs.getInt("MOTOS1"));
									veh.setAuto1(rs.getInt("AUTOS1"));
									veh.setCom1(rs.getInt("COMS1"));
									veh.setTotal1(rs.getInt("TOTALS1"));
									veh.setSpeed1(rs.getInt("SPEEDS1"));				
									veh.setMoto2(rs.getInt("MOTOS2"));
									veh.setAuto2(rs.getInt("AUTOS2"));
									veh.setCom2(rs.getInt("COMS2"));	
									veh.setTotal2(rs.getInt("TOTALS2"));
									veh.setSpeed2(rs.getInt("SPEEDS2"));
													
									lista.add(veh);							    
								}							
							}
					
					} catch (Exception sqle) {
						
						StringWriter errors = new StringWriter();
						sqle.printStackTrace(new PrintWriter(errors));
												
						SystemLog.logErrorSQL(errorFolder.concat("error_get_vehicle"), FluxoVeiculosDAO.class.getCanonicalName(), sqle.hashCode(), sqle.toString(), sqle.getMessage(), errors.toString());
												
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