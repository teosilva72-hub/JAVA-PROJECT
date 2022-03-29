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
						
		List<SAT> list = new ArrayList<SAT>();
		DateTimeApplication dta = new DateTimeApplication();
		
		int limit = equips.getSatList().size();
									
		String currentDate = null;
			
		Calendar calendar = Calendar.getInstance();	
		int minute = calendar.get(Calendar.MINUTE);
		
		String hourDatetime = dta.getDataIntervalHour(calendar);
				
		// Obter datas formatadas para os dados
		currentDate = dta.getDataInterval15Min(calendar, minute);
							
			String select = "SELECT d.NOME_ESTACAO AS ESTACAO, CASE WHEN DATEDIFF(NOW(), d.DATA_HORA) > 0 THEN date_format(d.DATA_HORA, '%d/%m/%y %H:%i') ELSE CASE WHEN MINUTE(d.DATA_HORA) = 45 THEN CONCAT(DATE_FORMAT(d.DATA_HORA, '%H:%i -'), " + 
			"DATE_FORMAT(DATE_ADD(d.DATA_HORA ,INTERVAL 14 MINUTE), ' %H:%i')) ELSE CONCAT(DATE_FORMAT(d.DATA_HORA, '%H:%i -'), DATE_FORMAT(DATE_ADD(d.DATA_HORA, INTERVAL 15 MINUTE), ' %H:%i')) END END 'PACOTE_HORA', " +
			"CASE WHEN DATEDIFF(NOW(), md.maxDate) > 0 THEN date_format(md.maxDate, '%d/%m/%y %H:%i') ELSE date_format(md.maxDate, '%H:%i') END 'DADO_HORA', " +
			
			/* MAIN HEADER */
			"CASE WHEN MINUTE(d.DATA_HORA) = 45 THEN CONCAT(DATE_FORMAT(d.DATA_HORA, '%d/%m/%y  %H:%i -'),DATE_FORMAT(DATE_ADD(d.DATA_HORA ,INTERVAL 14 MINUTE), ' %H:%i')) " +
			"ELSE CONCAT(DATE_FORMAT(d.DATA_HORA, '%d/%m/%y %H:%i -'), DATE_FORMAT(DATE_ADD(d.DATA_HORA, INTERVAL 15 MINUTE), ' %H:%i')) END 'MAJOR_HEADER', " +
			
			/* COLUMN HEADER */
			
			"DATE_FORMAT(DATE_SUB(d.DATA_HORA, INTERVAL 7 DAY),  '%d/%m/%Y  %H:00') '7_DAYS_HEADER' , " +
			"DATE_FORMAT(DATE_SUB(d.DATA_HORA, INTERVAL 1 HOUR), '%d/%m/%Y %H:00') 'LAST_HOUR_HEADER' , " +
			"DATE_FORMAT(d.DATA_HORA, '%d/%m/%Y %H:00') 'PROJECTION_HEADER' , " +
			
			/* 7 DAYS COLUMNS VALUE S1 */
			
			"SUM(CASE WHEN (eq.dir_lane1 = eq.dir_lane2 AND eq.dir_lane1 = eq.dir_lane3 AND eq.dir_lane1 = eq.dir_lane4 AND sd.faixa_7_days < 5) OR (eq.dir_lane1 = eq.dir_lane2 AND eq.dir_lane1 = eq.dir_lane3 AND sd.faixa_7_days < 4) OR (eq.dir_lane1 = eq.dir_lane2 AND sd.faixa_7_days < 3) OR (sd.faixa_7_days = 1) THEN sd.volumeAutos_7_days ELSE 0 END) 'VOLUME_AUTO_LAST_7_DAYS_S1', " +
			
			"SUM(CASE WHEN (eq.dir_lane1 = eq.dir_lane2 AND eq.dir_lane1 = eq.dir_lane3 AND eq.dir_lane1 = eq.dir_lane4 AND sd.faixa_7_days < 5) OR (eq.dir_lane1 = eq.dir_lane2 AND eq.dir_lane1 = eq.dir_lane3 AND sd.faixa_7_days < 4) OR (eq.dir_lane1 = eq.dir_lane2 AND sd.faixa_7_days < 3) OR (sd.faixa_7_days = 1) THEN (sd.volumeCom_7_days + sd.volumeLongo_7_days) ELSE 0 END) 'VOLUME_COM_LAST_7_DAYS_S1', " +
			
			"SUM(CASE WHEN (eq.dir_lane1 = eq.dir_lane2 AND eq.dir_lane1 = eq.dir_lane3 AND eq.dir_lane1 = eq.dir_lane4 AND sd.faixa_7_days < 5) OR (eq.dir_lane1 = eq.dir_lane2 AND eq.dir_lane1 = eq.dir_lane3 AND sd.faixa_7_days < 4) OR (eq.dir_lane1 = eq.dir_lane2 AND sd.faixa_7_days < 3) OR (sd.faixa_7_days = 1) THEN sd.volumeMoto_7_days ELSE 0 END) 'VOLUME_MOTO_LAST_7_DAYS_S1', " +
			
			"SUM(CASE WHEN (eq.dir_lane1 = eq.dir_lane2 AND eq.dir_lane1 = eq.dir_lane3 AND eq.dir_lane1 = eq.dir_lane4 AND sd.faixa_7_days < 5) OR (eq.dir_lane1 = eq.dir_lane2 AND eq.dir_lane1 = eq.dir_lane3 AND sd.faixa_7_days < 4) OR (eq.dir_lane1 = eq.dir_lane2 AND sd.faixa_7_days < 3) OR (sd.faixa_7_days = 1) THEN sd.volumeTotal_7_days ELSE 0 END) 'VOLUME_TOTAL_LAST_7_DAYS_S1', " +
			
			/* 1 HOUR COLUMNS VALUE S1 */
			
			"SUM(CASE WHEN (eq.dir_lane1 = eq.dir_lane2 AND eq.dir_lane1 = eq.dir_lane3 AND eq.dir_lane1 = eq.dir_lane4 AND lh.faixa_1_hour < 5) OR (eq.dir_lane1 = eq.dir_lane2 AND eq.dir_lane1 = eq.dir_lane3 AND lh.faixa_1_hour < 4) OR (eq.dir_lane1 = eq.dir_lane2 AND lh.faixa_1_hour < 3) OR (lh.faixa_1_hour = 1) THEN lh.volumeAutos_1_hour ELSE 0 END) 'VOLUME_AUTO_LAST_HOUR_S1', " +
			
			"SUM(CASE WHEN (eq.dir_lane1 = eq.dir_lane2 AND eq.dir_lane1 = eq.dir_lane3 AND eq.dir_lane1 = eq.dir_lane4 AND lh.faixa_1_hour < 5) OR (eq.dir_lane1 = eq.dir_lane2 AND eq.dir_lane1 = eq.dir_lane3 AND lh.faixa_1_hour < 4) OR (eq.dir_lane1 = eq.dir_lane2 AND lh.faixa_1_hour < 3) OR (lh.faixa_1_hour = 1) THEN (lh.volumeCom_1_hour + lh.volumeLongo_1_hour) ELSE 0 END) 'VOLUME_COM_LAST_HOUR_S1', " +
			
			"SUM(CASE WHEN (eq.dir_lane1 = eq.dir_lane2 AND eq.dir_lane1 = eq.dir_lane3 AND eq.dir_lane1 = eq.dir_lane4 AND lh.faixa_1_hour < 5) OR (eq.dir_lane1 = eq.dir_lane2 AND eq.dir_lane1 = eq.dir_lane3 AND lh.faixa_1_hour < 4) OR (eq.dir_lane1 = eq.dir_lane2 AND lh.faixa_1_hour < 3) OR (lh.faixa_1_hour = 1) THEN lh.volumeMoto_1_hour ELSE 0 END) 'VOLUME_MOTO_LAST_HOUR_S1', " +
			
			"SUM(CASE WHEN (eq.dir_lane1 = eq.dir_lane2 AND eq.dir_lane1 = eq.dir_lane3 AND eq.dir_lane1 = eq.dir_lane4 AND lh.faixa_1_hour < 5) OR (eq.dir_lane1 = eq.dir_lane2 AND eq.dir_lane1 = eq.dir_lane3 AND lh.faixa_1_hour < 4) OR (eq.dir_lane1 = eq.dir_lane2 AND lh.faixa_1_hour < 3) OR (lh.faixa_1_hour = 1) THEN lh.volumeTotal_1_hour ELSE 0 END) 'VOLUME_TOTAL_LAST_HOUR_S1', " +
			
			/* PROJECTION S1 */
			
			"(SUM(CASE WHEN (eq.dir_lane1 = eq.dir_lane2 AND eq.dir_lane1 = eq.dir_lane3 AND eq.dir_lane1 = eq.dir_lane4 AND d.NOME_FAIXA < 5) OR (eq.dir_lane1 = eq.dir_lane2 AND eq.dir_lane1 = eq.dir_lane3 AND d.NOME_FAIXA < 4) OR (eq.dir_lane1 = eq.dir_lane2 AND d.NOME_FAIXA < 3) OR (d.NOME_FAIXA = 1) THEN d.VOLUME_AUTO ELSE 0 END) * 4) 'VOLUME_AUTO_PROJECTION_S1', " +
			"(SUM(CASE WHEN (eq.dir_lane1 = eq.dir_lane2 AND eq.dir_lane1 = eq.dir_lane3 AND eq.dir_lane1 = eq.dir_lane4 AND d.NOME_FAIXA < 5) OR (eq.dir_lane1 = eq.dir_lane2 AND eq.dir_lane1 = eq.dir_lane3 AND d.NOME_FAIXA < 4) OR (eq.dir_lane1 = eq.dir_lane2 AND d.NOME_FAIXA < 3) OR (d.NOME_FAIXA = 1) THEN (d.VOLUME_COM + d.VOLUME_LONGO) ELSE 0 END) * 4) 'VOLUME_COM_PROJECTION_S1', " +
			"(SUM(CASE WHEN (eq.dir_lane1 = eq.dir_lane2 AND eq.dir_lane1 = eq.dir_lane3 AND eq.dir_lane1 = eq.dir_lane4 AND d.NOME_FAIXA < 5) OR (eq.dir_lane1 = eq.dir_lane2 AND eq.dir_lane1 = eq.dir_lane3 AND d.NOME_FAIXA < 4) OR (eq.dir_lane1 = eq.dir_lane2 AND d.NOME_FAIXA < 3) OR (d.NOME_FAIXA = 1) THEN d.VOLUME_MOTOS ELSE 0 END) * 4) 'VOLUME_MOTO_PROJECTION_S1', " +
			"(SUM(CASE WHEN (eq.dir_lane1 = eq.dir_lane2 AND eq.dir_lane1 = eq.dir_lane3 AND eq.dir_lane1 = eq.dir_lane4 AND d.NOME_FAIXA < 5) OR (eq.dir_lane1 = eq.dir_lane2 AND eq.dir_lane1 = eq.dir_lane3 AND d.NOME_FAIXA < 4) OR (eq.dir_lane1 = eq.dir_lane2 AND d.NOME_FAIXA < 3) OR (d.NOME_FAIXA = 1) THEN d.VOLUME_TOTAL ELSE 0 END) * 4) 'VOLUME_TOTAL_PROJECTION_S1', " +
			
			/* VOLUME S1 */
			
			"SUM(CASE WHEN (eq.dir_lane1 = eq.dir_lane2 AND eq.dir_lane1 = eq.dir_lane3 AND eq.dir_lane1 = eq.dir_lane4 AND d.NOME_FAIXA < 5) OR (eq.dir_lane1 = eq.dir_lane2 AND eq.dir_lane1 = eq.dir_lane3 AND d.NOME_FAIXA < 4) OR (eq.dir_lane1 = eq.dir_lane2 AND d.NOME_FAIXA < 3) OR (d.NOME_FAIXA = 1) THEN d.VOLUME_AUTO ELSE 0 END) 'VOLUME_AUTO_S1', " +
			"SUM(CASE WHEN (eq.dir_lane1 = eq.dir_lane2 AND eq.dir_lane1 = eq.dir_lane3 AND eq.dir_lane1 = eq.dir_lane4 AND d.NOME_FAIXA < 5) OR (eq.dir_lane1 = eq.dir_lane2 AND eq.dir_lane1 = eq.dir_lane3 AND d.NOME_FAIXA < 4) OR (eq.dir_lane1 = eq.dir_lane2 AND d.NOME_FAIXA < 3) OR (d.NOME_FAIXA = 1) THEN (d.VOLUME_COM + d.VOLUME_LONGO) ELSE 0 END) 'VOLUME_COM_S1', " +
			"SUM(CASE WHEN (eq.dir_lane1 = eq.dir_lane2 AND eq.dir_lane1 = eq.dir_lane3 AND eq.dir_lane1 = eq.dir_lane4 AND d.NOME_FAIXA < 5) OR (eq.dir_lane1 = eq.dir_lane2 AND eq.dir_lane1 = eq.dir_lane3 AND d.NOME_FAIXA < 4) OR (eq.dir_lane1 = eq.dir_lane2 AND d.NOME_FAIXA < 3) OR (d.NOME_FAIXA = 1) THEN d.VOLUME_MOTOS ELSE 0 END) 'VOLUME_MOTO_S1', " +
			"SUM(CASE WHEN (eq.dir_lane1 = eq.dir_lane2 AND eq.dir_lane1 = eq.dir_lane3 AND eq.dir_lane1 = eq.dir_lane4 AND d.NOME_FAIXA < 5) OR (eq.dir_lane1 = eq.dir_lane2 AND eq.dir_lane1 = eq.dir_lane3 AND d.NOME_FAIXA < 4) OR (eq.dir_lane1 = eq.dir_lane2 AND d.NOME_FAIXA < 3) OR (d.NOME_FAIXA = 1) THEN d.VOLUME_TOTAL ELSE 0 END) 'VOLUME_TOTAL_S1', " +
			
			 /* TAXA OCUPACAO S1 */
			 
			"CASE WHEN eq.number_lanes = 2 THEN (IFNULL(ROUND(AVG(IF(d.NOME_FAIXA = 1, d.TAXA_OCUPACAO * 100, 0)), 2), 0)) " +
			"WHEN eq.number_lanes = 3 THEN (IFNULL(ROUND(AVG(IF(d.NOME_FAIXA < 2, d.TAXA_OCUPACAO * 100, 0)), 2), 0)) " +
			"WHEN eq.number_lanes = 4 THEN (IFNULL(ROUND(AVG(IF(d.NOME_FAIXA < 3, d.TAXA_OCUPACAO * 100, 0)), 2), 0)) " +
			"WHEN eq.number_lanes = 5 THEN (IFNULL(ROUND(AVG(IF(d.NOME_FAIXA < 4, d.TAXA_OCUPACAO * 100, 0)), 2), 0)) " +
			"WHEN eq.number_lanes = 6 THEN (IFNULL(ROUND(AVG(IF(d.NOME_FAIXA < 4, d.TAXA_OCUPACAO * 100, 0)), 2), 0)) " +
			"WHEN eq.number_lanes = 7 THEN (IFNULL(ROUND(AVG(IF(d.NOME_FAIXA < 5, d.TAXA_OCUPACAO * 100, 0)), 2), 0)) " +
			"WHEN eq.number_lanes = 8 THEN (IFNULL(ROUND(AVG(IF(d.NOME_FAIXA < 5, d.TAXA_OCUPACAO * 100, 0)), 2), 0)) " +
			"ELSE 0 END 'TAXA_OCUPACAO_S1', " +
			
			/* VELOCIDADE S1 */
			
			"CASE WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA = 2, d.VEL_MEDIA_AUTOS , NULL),0)), 0), 0) WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA > 1 AND (eq.dir_lane1 <> eq.dir_lane2), d.VEL_MEDIA_AUTOS , NULL),0)), 0), 0) WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA > 2, d.VEL_MEDIA_AUTOS , NULL ),0)), 0), 0) WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA > 3 AND (eq.dir_lane1 <> eq.dir_lane4), d.VEL_MEDIA_AUTOS , NULL ),0)), 0), 0) WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA > 3, d.VEL_MEDIA_AUTOS, NULL),0)), 0), 0) " +
				 "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA > 4 AND (eq.dir_lane1 <> eq.dir_lane5), d.VEL_MEDIA_AUTOS , NULL ),0)), 0), 0) WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA > 4, d.VEL_MEDIA_AUTOS , NULL ),0)), 0), 0) ELSE 0 END 'VEL_MEDIA_AUTOS_S2', " +
			 
			"CASE WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(SUM(IF(d.NOME_FAIXA = 1, ((d.VOLUME_COM * d.VEL_MEDIA_COM) + (d.VOLUME_LONGO * d.VEL_MEDIA_LONGO)) / (d.VOLUME_COM + d.VOLUME_LONGO)  , NULL)), 0), 0) " + 
			"WHEN eq.number_lanes = 3 THEN (IFNULL(ROUND((SUM(NULLIF(IF(d.NOME_FAIXA = 1, ((d.VOLUME_COM * d.VEL_MEDIA_COM) + (d.VOLUME_LONGO * d.VEL_MEDIA_LONGO)) / (d.VOLUME_COM + d.VOLUME_LONGO), 0), 0)) / 2 ), 0), 0)) " +
			"WHEN eq.number_lanes = 4 THEN (IFNULL(ROUND((SUM(NULLIF(IF(d.NOME_FAIXA < 3, ((d.VOLUME_COM * d.VEL_MEDIA_COM) + (d.VOLUME_LONGO * d.VEL_MEDIA_LONGO)) / (d.VOLUME_COM + d.VOLUME_LONGO), 0), 0)) / 2 ), 0), 0)) " +
			"WHEN eq.number_lanes = 5 THEN (IFNULL(ROUND((SUM(NULLIF(IF(d.NOME_FAIXA < 4, ((d.VOLUME_COM * d.VEL_MEDIA_COM) + (d.VOLUME_LONGO * d.VEL_MEDIA_LONGO)) / (d.VOLUME_COM + d.VOLUME_LONGO), 0), 0)) / 2 ), 0), 0)) " +
			"WHEN eq.number_lanes = 6 THEN (IFNULL(ROUND((SUM(NULLIF(IF(d.NOME_FAIXA < 4, ((d.VOLUME_COM * d.VEL_MEDIA_COM) + (d.VOLUME_LONGO * d.VEL_MEDIA_LONGO)) / (d.VOLUME_COM + d.VOLUME_LONGO), 0), 0)) / 2 ), 0), 0)) " +
			"WHEN eq.number_lanes = 7 THEN (IFNULL(ROUND((SUM(NULLIF(IF(d.NOME_FAIXA < 5, ((d.VOLUME_COM * d.VEL_MEDIA_COM) + (d.VOLUME_LONGO * d.VEL_MEDIA_LONGO)) / (d.VOLUME_COM + d.VOLUME_LONGO), 0), 0)) / 2 ), 0), 0)) " +
			"WHEN eq.number_lanes = 8 THEN (IFNULL(ROUND((SUM(NULLIF(IF(d.NOME_FAIXA < 5, ((d.VOLUME_COM * d.VEL_MEDIA_COM) + (d.VOLUME_LONGO * d.VEL_MEDIA_LONGO)) / (d.VOLUME_COM + d.VOLUME_LONGO), 0), 0)) / 2 ), 0), 0)) " +
			"ELSE 0 END 'VEL_MEDIA_COM_S1', " +
			
			"CASE WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA = 1, d.VEL_MEDIA_MOTOS , NULL), 0)), 0), 0)	WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA = 1 AND (eq.dir_lane1 <> eq.dir_lane2), d.VEL_MEDIA_MOTOS , NULL), 0)), 0), 0) WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA < 3, d.VEL_MEDIA_MOTOS , NULL ), 0)), 0), 0) WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA < 4 AND (eq.dir_lane1 = eq.dir_lane3), d.VEL_MEDIA_MOTOS , NULL ), 0)), 0), 0) WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA < 4, d.VEL_MEDIA_MOTOS, NULL), 0)), 0), 0) " +
			    "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA < 5 AND (eq.dir_lane1 <> eq.dir_lane4), d.VEL_MEDIA_MOTOS , NULL ), 0)), 0), 0) WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA < 5, d.VEL_MEDIA_MOTOS , NULL ), 0)), 0), 0) ELSE 0 END 'VEL_MEDIA_MOTO_S1', " +
			  
			 "CASE WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA = 1, d.VEL_MEDIA_TOTAL , NULL), 0)), 0), 0)	WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA = 1 AND (eq.dir_lane1 <> eq.dir_lane2), d.VEL_MEDIA_TOTAL , NULL), 0)), 0), 0) WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA < 3, d.VEL_MEDIA_TOTAL , NULL ), 0)), 0), 0) WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA < 4 AND (eq.dir_lane1 = eq.dir_lane3), d.VEL_MEDIA_TOTAL , NULL ), 0)), 0), 0) WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA < 4, d.VEL_MEDIA_TOTAL, NULL), 0)), 0), 0) " + 
			    "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA < 5 AND (eq.dir_lane1 <> eq.dir_lane4), d.VEL_MEDIA_TOTAL , NULL ), 0)), 0), 0) WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA < 5, d.VEL_MEDIA_TOTAL , NULL ), 0)), 0), 0) ELSE 0 END 'VEL_MEDIA_TOTAL_S1', " +
			  
			/* 7 DAYS COLUMNS VALUE S2 */
			
			"SUM(CASE WHEN (eq.dir_lane1 <> IFNULL(eq.dir_lane2, eq.dir_lane1) AND sd.faixa_7_days > 1) OR (eq.dir_lane1 <> IFNULL(eq.dir_lane3, eq.dir_lane1) AND sd.faixa_7_days > 2) OR (eq.dir_lane1 <> IFNULL(eq.dir_lane4, eq.dir_lane1) AND sd.faixa_7_days > 3) OR (eq.dir_lane1 <> IFNULL(eq.dir_lane5, eq.dir_lane1) AND sd.faixa_7_days > 4) THEN sd.volumeAutos_7_days ELSE 0 END) 'VOLUME_AUTO_LAST_7_DAYS_S2', " +
			
			"SUM(CASE WHEN (eq.dir_lane1 <> IFNULL(eq.dir_lane2, eq.dir_lane1) AND sd.faixa_7_days > 1) OR (eq.dir_lane1 <> IFNULL(eq.dir_lane3, eq.dir_lane1) AND sd.faixa_7_days > 2) OR (eq.dir_lane1 <> IFNULL(eq.dir_lane4, eq.dir_lane1) AND sd.faixa_7_days > 3) OR (eq.dir_lane1 <> IFNULL(eq.dir_lane5, eq.dir_lane1) AND sd.faixa_7_days > 4) THEN (sd.volumeCom_7_days + sd.volumeLongo_7_days) ELSE 0 END) 'VOLUME_COM_LAST_7_DAYS_S2', " +
			
			"SUM(CASE WHEN (eq.dir_lane1 <> IFNULL(eq.dir_lane2, eq.dir_lane1) AND sd.faixa_7_days > 1) OR (eq.dir_lane1 <> IFNULL(eq.dir_lane3, eq.dir_lane1) AND sd.faixa_7_days > 2) OR (eq.dir_lane1 <> IFNULL(eq.dir_lane4, eq.dir_lane1) AND sd.faixa_7_days > 3) OR (eq.dir_lane1 <> IFNULL(eq.dir_lane5, eq.dir_lane1) AND sd.faixa_7_days > 4) THEN sd.volumeMoto_7_days ELSE 0 END) 'VOLUME_MOTO_LAST_7_DAYS_S2', " +
			
			"SUM(CASE WHEN (eq.dir_lane1 <> IFNULL(eq.dir_lane2, eq.dir_lane1) AND sd.faixa_7_days > 1) OR (eq.dir_lane1 <> IFNULL(eq.dir_lane3, eq.dir_lane1) AND sd.faixa_7_days > 2) OR (eq.dir_lane1 <> IFNULL(eq.dir_lane4, eq.dir_lane1) AND sd.faixa_7_days > 3) OR (eq.dir_lane1 <> IFNULL(eq.dir_lane5, eq.dir_lane1) AND sd.faixa_7_days > 4) THEN sd.volumeTotal_7_days ELSE 0 END) 'VOLUME_TOTAL_LAST_7_DAYS_S2', " +
			
			/* LAST HOUR COLUMNS VALUE S2 */
			
			"SUM(CASE WHEN (eq.dir_lane1 <> IFNULL(eq.dir_lane2, eq.dir_lane1) AND lh.faixa_1_hour > 1) OR (eq.dir_lane1 <> IFNULL(eq.dir_lane3, eq.dir_lane1) AND lh.faixa_1_hour > 2) OR (eq.dir_lane1 <> IFNULL(eq.dir_lane4, eq.dir_lane1) AND lh.faixa_1_hour > 3) OR (eq.dir_lane1 <> IFNULL(eq.dir_lane5, eq.dir_lane1) AND lh.faixa_1_hour > 4) THEN lh.volumeAutos_1_hour ELSE 0 END) 'VOLUME_AUTO_LAST_HOUR_S2', " +
			
			"SUM(CASE WHEN (eq.dir_lane1 <> IFNULL(eq.dir_lane2, eq.dir_lane1) AND lh.faixa_1_hour > 1) OR (eq.dir_lane1 <> IFNULL(eq.dir_lane3, eq.dir_lane1) AND lh.faixa_1_hour > 2) OR (eq.dir_lane1 <> IFNULL(eq.dir_lane4, eq.dir_lane1) AND lh.faixa_1_hour > 3) OR (eq.dir_lane1 <> IFNULL(eq.dir_lane5, eq.dir_lane1) AND lh.faixa_1_hour > 4) THEN (lh.volumeCom_1_hour + lh.volumeLongo_1_hour) ELSE 0 END) 'VOLUME_COM_LAST_HOUR_S2', " +
			
			"SUM(CASE WHEN (eq.dir_lane1 <> IFNULL(eq.dir_lane2, eq.dir_lane1) AND lh.faixa_1_hour > 1) OR (eq.dir_lane1 <> IFNULL(eq.dir_lane3, eq.dir_lane1) AND lh.faixa_1_hour > 2) OR (eq.dir_lane1 <> IFNULL(eq.dir_lane4, eq.dir_lane1) AND lh.faixa_1_hour > 3) OR (eq.dir_lane1 <> IFNULL(eq.dir_lane5, eq.dir_lane1) AND lh.faixa_1_hour > 4) THEN lh.volumeMoto_1_hour ELSE 0 END) 'VOLUME_MOTO_LAST_HOUR_S2', " +
			
			"SUM(CASE WHEN (eq.dir_lane1 <> IFNULL(eq.dir_lane2, eq.dir_lane1) AND lh.faixa_1_hour > 1) OR (eq.dir_lane1 <> IFNULL(eq.dir_lane3, eq.dir_lane1) AND lh.faixa_1_hour > 2) OR (eq.dir_lane1 <> IFNULL(eq.dir_lane4, eq.dir_lane1) AND lh.faixa_1_hour > 3) OR (eq.dir_lane1 <> IFNULL(eq.dir_lane5, eq.dir_lane1) AND lh.faixa_1_hour > 4) THEN lh.volumeTotal_1_hour ELSE 0 END) 'VOLUME_TOTAL_LAST_HOUR_S2', " +
			
			/* PROJECTION S2 */
			
			"(SUM(CASE WHEN (eq.dir_lane1 <> IFNULL(eq.dir_lane2, eq.dir_lane1) AND d.NOME_FAIXA > 1) OR (eq.dir_lane1 <> IFNULL(eq.dir_lane3, eq.dir_lane1) AND d.NOME_FAIXA > 2) OR (eq.dir_lane1 <> IFNULL(eq.dir_lane4, eq.dir_lane1) AND d.NOME_FAIXA > 3) OR (eq.dir_lane1 <> IFNULL(eq.dir_lane5, eq.dir_lane1) AND d.NOME_FAIXA > 4) THEN d.VOLUME_AUTO ELSE 0 END) * 4) 'VOLUME_AUTO_PROJECTION_S2', " +
			"(SUM(CASE WHEN (eq.dir_lane1 <> IFNULL(eq.dir_lane2, eq.dir_lane1) AND d.NOME_FAIXA > 1) OR (eq.dir_lane1 <> IFNULL(eq.dir_lane3, eq.dir_lane1) AND d.NOME_FAIXA > 2) OR (eq.dir_lane1 <> IFNULL(eq.dir_lane4, eq.dir_lane1) AND d.NOME_FAIXA > 3) OR (eq.dir_lane1 <> IFNULL(eq.dir_lane5, eq.dir_lane1) AND d.NOME_FAIXA > 4) THEN (d.VOLUME_COM + d.VOLUME_LONGO) ELSE 0 END) * 4)'VOLUME_COM_PROJECTION_S2', " +
			"(SUM(CASE WHEN (eq.dir_lane1 <> IFNULL(eq.dir_lane2, eq.dir_lane1) AND d.NOME_FAIXA > 1) OR (eq.dir_lane1 <> IFNULL(eq.dir_lane3, eq.dir_lane1) AND d.NOME_FAIXA > 2) OR (eq.dir_lane1 <> IFNULL(eq.dir_lane4, eq.dir_lane1) AND d.NOME_FAIXA > 3) OR (eq.dir_lane1 <> IFNULL(eq.dir_lane5, eq.dir_lane1) AND d.NOME_FAIXA > 4) THEN d.VOLUME_MOTOS ELSE 0 END) * 4) 'VOLUME_MOTO_PROJECTION_S2', " +
			"(SUM(CASE WHEN (eq.dir_lane1 <> IFNULL(eq.dir_lane2, eq.dir_lane1) AND d.NOME_FAIXA > 1) OR (eq.dir_lane1 <> IFNULL(eq.dir_lane3, eq.dir_lane1) AND d.NOME_FAIXA > 2) OR (eq.dir_lane1 <> IFNULL(eq.dir_lane4, eq.dir_lane1) AND d.NOME_FAIXA > 3) OR (eq.dir_lane1 <> IFNULL(eq.dir_lane5, eq.dir_lane1) AND d.NOME_FAIXA > 4) THEN d.VOLUME_TOTAL ELSE 0 END) * 4) 'VOLUME_TOTAL_PROJECTION_S2', " +
			
			/* VOLUME S2 */
			
			"SUM(CASE WHEN (eq.dir_lane1 <> IFNULL(eq.dir_lane2, eq.dir_lane1) AND d.NOME_FAIXA > 1) OR (eq.dir_lane1 <> IFNULL(eq.dir_lane3, eq.dir_lane1) AND d.NOME_FAIXA > 2) OR (eq.dir_lane1 <> IFNULL(eq.dir_lane4, eq.dir_lane1) AND d.NOME_FAIXA > 3) OR (eq.dir_lane1 <> IFNULL(eq.dir_lane5, eq.dir_lane1) AND d.NOME_FAIXA > 4) THEN d.VOLUME_AUTO ELSE 0 END) 'VOLUME_AUTO_S2', " +
			"SUM(CASE WHEN (eq.dir_lane1 <> IFNULL(eq.dir_lane2, eq.dir_lane1) AND d.NOME_FAIXA > 1) OR (eq.dir_lane1 <> IFNULL(eq.dir_lane3, eq.dir_lane1) AND d.NOME_FAIXA > 2) OR (eq.dir_lane1 <> IFNULL(eq.dir_lane4, eq.dir_lane1) AND d.NOME_FAIXA > 3) OR (eq.dir_lane1 <> IFNULL(eq.dir_lane5, eq.dir_lane1) AND d.NOME_FAIXA > 4) THEN (d.VOLUME_COM + d.VOLUME_LONGO) ELSE 0 END) 'VOLUME_COM_S2', " +
			"SUM(CASE WHEN (eq.dir_lane1 <> IFNULL(eq.dir_lane2, eq.dir_lane1) AND d.NOME_FAIXA > 1) OR (eq.dir_lane1 <> IFNULL(eq.dir_lane3, eq.dir_lane1) AND d.NOME_FAIXA > 2) OR (eq.dir_lane1 <> IFNULL(eq.dir_lane4, eq.dir_lane1) AND d.NOME_FAIXA > 3) OR (eq.dir_lane1 <> IFNULL(eq.dir_lane5, eq.dir_lane1) AND d.NOME_FAIXA > 4) THEN d.VOLUME_MOTOS ELSE 0 END) 'VOLUME_MOTO_S2', " +
			"SUM(CASE WHEN (eq.dir_lane1 <> IFNULL(eq.dir_lane2, eq.dir_lane1) AND d.NOME_FAIXA > 1) OR (eq.dir_lane1 <> IFNULL(eq.dir_lane3, eq.dir_lane1) AND d.NOME_FAIXA > 2) OR (eq.dir_lane1 <> IFNULL(eq.dir_lane4, eq.dir_lane1) AND d.NOME_FAIXA > 3) OR (eq.dir_lane1 <> IFNULL(eq.dir_lane5, eq.dir_lane1) AND d.NOME_FAIXA > 4) THEN d.VOLUME_TOTAL ELSE 0 END) 'VOLUME_TOTAL_S2', " +
			
			/* TAXA OCUPACAO S2 */
			 
			"CASE WHEN eq.number_lanes = 2 THEN (IFNULL(ROUND(AVG(IF(d.NOME_FAIXA = 2, d.TAXA_OCUPACAO * 100, 0)), 2), 0)) " +
			"WHEN eq.number_lanes = 3 THEN (IFNULL(ROUND(AVG(IF(d.NOME_FAIXA > 1, d.TAXA_OCUPACAO * 100, 0)), 2), 0)) " +
			"WHEN eq.number_lanes = 4 THEN (IFNULL(ROUND(AVG(IF(d.NOME_FAIXA > 2 , d.TAXA_OCUPACAO * 100, 0)), 2), 0)) " +
			"WHEN eq.number_lanes = 5 THEN (IFNULL(ROUND(AVG(IF(d.NOME_FAIXA > 3, d.TAXA_OCUPACAO * 100, 0)), 2), 0)) " +
			"WHEN eq.number_lanes = 6 THEN (IFNULL(ROUND(AVG(IF(d.NOME_FAIXA > 3, d.TAXA_OCUPACAO * 100, 0)), 2), 0)) " +
			"WHEN eq.number_lanes = 7 THEN (IFNULL(ROUND(AVG(IF(d.NOME_FAIXA > 4, d.TAXA_OCUPACAO * 100, 0)), 2), 0)) " +
			"WHEN eq.number_lanes = 8 THEN (IFNULL(ROUND(AVG(IF(d.NOME_FAIXA > 4, d.TAXA_OCUPACAO * 100, 0)), 2), 0)) " +
			"ELSE 0 END 'TAXA_OCUPACAO_S2', " +
			
			/* VELOCIDADE S2 */
			
			"CASE WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA = 2, d.VEL_MEDIA_AUTOS , NULL),0)), 0), 0) WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA > 1 AND (eq.dir_lane1 <> eq.dir_lane2), d.VEL_MEDIA_AUTOS , NULL),0)), 0), 0) WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA > 2, d.VEL_MEDIA_AUTOS , NULL ),0)), 0), 0) WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA > 3 AND (eq.dir_lane1 <> eq.dir_lane4), d.VEL_MEDIA_AUTOS , NULL ),0)), 0), 0) WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA > 3, d.VEL_MEDIA_AUTOS, NULL),0)), 0), 0) " +
				 "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA > 4 AND (eq.dir_lane1 <> eq.dir_lane5), d.VEL_MEDIA_AUTOS , NULL ),0)), 0), 0) WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA > 4, d.VEL_MEDIA_AUTOS , NULL ),0)), 0), 0) ELSE 0 END 'VEL_MEDIA_AUTOS_S2', " +
			        
			"CASE WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(SUM(NULLIF(IF(d.NOME_FAIXA = 2, ((d.VOLUME_COM * d.VEL_MEDIA_COM) + (d.VOLUME_LONGO * d.VEL_MEDIA_LONGO)) / (d.VOLUME_COM + d.VOLUME_LONGO), NULL), 0)), 0), 0) " +
			"WHEN eq.number_lanes = 3 THEN (IFNULL(ROUND((SUM(NULLIF(IF(d.NOME_FAIXA > 1, ((d.VOLUME_COM * d.VEL_MEDIA_COM) + (d.VOLUME_LONGO * d.VEL_MEDIA_LONGO)) / (d.VOLUME_COM + d.VOLUME_LONGO), 0), 0)) / 2 ), 0), 0)) " +
			"WHEN eq.number_lanes = 4 THEN (IFNULL(ROUND((SUM(NULLIF(IF(d.NOME_FAIXA > 2, ((d.VOLUME_COM * d.VEL_MEDIA_COM) + (d.VOLUME_LONGO * d.VEL_MEDIA_LONGO)) / (d.VOLUME_COM + d.VOLUME_LONGO), 0), 0)) / 2 ), 0), 0)) " +
			"WHEN eq.number_lanes = 5 THEN (IFNULL(ROUND((SUM(NULLIF(IF(d.NOME_FAIXA > 3, ((d.VOLUME_COM * d.VEL_MEDIA_COM) + (d.VOLUME_LONGO * d.VEL_MEDIA_LONGO)) / (d.VOLUME_COM + d.VOLUME_LONGO), 0), 0)) / 2 ), 0), 0)) " +
			"WHEN eq.number_lanes = 6 THEN (IFNULL(ROUND((SUM(NULLIF(IF(d.NOME_FAIXA > 3, ((d.VOLUME_COM * d.VEL_MEDIA_COM) + (d.VOLUME_LONGO * d.VEL_MEDIA_LONGO)) / (d.VOLUME_COM + d.VOLUME_LONGO), 0), 0)) / 2 ), 0), 0)) " +
			"WHEN eq.number_lanes = 7 THEN (IFNULL(ROUND((SUM(NULLIF(IF(d.NOME_FAIXA > 4, ((d.VOLUME_COM * d.VEL_MEDIA_COM) + (d.VOLUME_LONGO * d.VEL_MEDIA_LONGO)) / (d.VOLUME_COM + d.VOLUME_LONGO), 0), 0)) / 2 ), 0), 0)) " +
			"WHEN eq.number_lanes = 8 THEN (IFNULL(ROUND((SUM(NULLIF(IF(d.NOME_FAIXA > 4, ((d.VOLUME_COM * d.VEL_MEDIA_COM) + (d.VOLUME_LONGO * d.VEL_MEDIA_LONGO)) / (d.VOLUME_COM + d.VOLUME_LONGO), 0), 0)) / 2 ), 0), 0)) " +
			"ELSE 0 END 'VEL_MEDIA_COM_S2', " +
			
			"CASE WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA = 2, d.VEL_MEDIA_MOTOS , NULL),0)), 0), 0) WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA > 1 AND (eq.dir_lane1 <> eq.dir_lane2), d.VEL_MEDIA_MOTOS , NULL),0)), 0), 0) WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA > 2, d.VEL_MEDIA_MOTOS , NULL ),0)), 0), 0) WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA > 3 AND (eq.dir_lane1 <> eq.dir_lane4), d.VEL_MEDIA_MOTOS , NULL ),0)), 0), 0) WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA > 3, d.VEL_MEDIA_MOTOS, NULL),0)), 0), 0) " +
				 "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA > 4 AND (eq.dir_lane1 <> eq.dir_lane5), d.VEL_MEDIA_MOTOS , NULL ),0)), 0), 0) WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA > 4, d.VEL_MEDIA_MOTOS , NULL ),0)), 0), 0) ELSE 0 END 'VEL_MEDIA_MOTOS_S2', " +
			     
			"CASE WHEN eq.number_lanes = 2 THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA = 2, d.VEL_MEDIA_TOTAL , NULL),0)), 0), 0) WHEN eq.number_lanes = 3 THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA > 1 AND (eq.dir_lane1 <> eq.dir_lane2), d.VEL_MEDIA_TOTAL , NULL),0)), 0), 0) WHEN eq.number_lanes = 4 THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA > 2, d.VEL_MEDIA_TOTAL , NULL ),0)), 0), 0) WHEN eq.number_lanes = 5 THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA > 3 AND (eq.dir_lane1 <> eq.dir_lane4), d.VEL_MEDIA_TOTAL , NULL ),0)), 0), 0) WHEN eq.number_lanes = 6 THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA > 3, d.VEL_MEDIA_TOTAL, NULL),0)), 0), 0) " +
				 "WHEN eq.number_lanes = 7 THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA > 4 AND (eq.dir_lane1 <> eq.dir_lane5), d.VEL_MEDIA_TOTAL , NULL ),0)), 0), 0) WHEN eq.number_lanes = 8 THEN IFNULL(ROUND(AVG(NULLIF(IF(d.NOME_FAIXA > 4, d.VEL_MEDIA_TOTAL , NULL ),0)), 0), 0) ELSE 0 END 'VEL_MEDIA_TOTAL_S2' " +
			   		   
		   
		   "FROM "+RoadConcessionaire.tableDados15+" d " +
		   
			"INNER JOIN sat_equipment eq on (eq.equip_id = d.nome_estacao) " +
			
			"LEFT JOIN " +
			"( " +
			   "SELECT siteID, MAX(data) maxDate FROM tb_vbv " +
			   "GROUP BY siteID " +
			") md ON md.siteID = eq.equip_id " +
			
			"LEFT JOIN " +
			"( " +
			   "SELECT NOME_ESTACAO, NOME_FAIXA faixa_7_days, SUM(VOLUME_AUTO) volumeAutos_7_days, SUM(VOLUME_COM) volumeCom_7_days, SUM(VOLUME_LONGO) volumeLongo_7_days, SUM(VOLUME_MOTOS) volumeMoto_7_days, " +
			   "SUM(VOLUME_TOTAL) volumeTotal_7_days FROM tb_dados15 " +
			   "WHERE  DATA_HORA BETWEEN DATE_SUB('2022-03-23 16:00:00', INTERVAL 7 DAY) AND DATE_SUB(DATE_ADD(DATE_SUB('2022-03-23 16:00:00', INTERVAL 7 DAY), INTERVAL 1 HOUR), INTERVAL 1 SECOND) " +
			   "GROUP BY NOME_ESTACAO, NOME_FAIXA " +
			   
			") sd ON sd.NOME_ESTACAO = d.NOME_ESTACAO AND sd.faixa_7_days = d.NOME_FAIXA " +
			
			"LEFT JOIN " +
			"( " +
			   "SELECT NOME_ESTACAO, NOME_FAIXA faixa_1_hour, SUM(VOLUME_AUTO) volumeAutos_1_hour, SUM(VOLUME_COM) volumeCom_1_hour, SUM(VOLUME_LONGO) volumeLongo_1_hour, SUM(VOLUME_MOTOS) volumeMoto_1_hour, " +
			   "SUM(VOLUME_TOTAL) volumeTotal_1_hour FROM tb_dados15 " +
			   "WHERE  DATA_HORA BETWEEN DATE_SUB('2022-03-23 16:00:00', INTERVAL 1 HOUR) AND DATE_SUB('2022-03-23 16:00:00', INTERVAL 1 SECOND) " +
			   "GROUP BY NOME_ESTACAO, NOME_FAIXA " +
			   
			" ) lh ON lh.NOME_ESTACAO = d.NOME_ESTACAO AND lh.faixa_1_hour = d.NOME_FAIXA " +
		   
	
		   "WHERE DATA_HORA BETWEEN DATE_SUB($INTERVAL$) AND ? AND eq.visible = 1 " +
		   "GROUP BY d.DATA_HORA, d.NOME_ESTACAO " +	
	 	   "ORDER BY d.DATA_HORA DESC ";
	 
	 try {
			
		 conn.start(1);
			
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
			
		// System.out.println("ORIGIN: "+select);		 	
			
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
					sat.setAutosVolumeS1(rs.getInt("VOLUME_AUTO_S1"));
					sat.setComVolumeS1(rs.getInt("VOLUME_COM_S1"));
					sat.setMotoVolumeS1(rs.getInt("VOLUME_MOTO_S1"));
					sat.setTotalVolumeS1(rs.getInt("VOLUME_TOTAL_S1"));
					sat.setAutosVelMedS1(rs.getInt("VEL_MEDIA_AUTO_S1"));
					sat.setComVelMedS1(rs.getInt("VEL_MEDIA_COM_S1"));
					sat.setMotoVelMedS1(rs.getInt("VEL_MEDIA_MOTO_S1"));
					sat.setTotalVelMedS1(rs.getInt("VEL_MEDIA_TOTAL_S1"));					
					sat.setOccupationRateS1((rs.getDouble("TAXA_OCUPACAO_S1")));
					sat.setAutosVolumeS2(rs.getInt("VOLUME_AUTO_S2"));
					sat.setComVolumeS2(rs.getInt("VOLUME_COM_S2"));
					sat.setMotoVolumeS2(rs.getInt("VOLUME_MOTO_S2"));
					sat.setTotalVolumeS2(rs.getInt("VOLUME_TOTAL_S2"));
					sat.setAutosVelMedS2(rs.getInt("VEL_MEDIA_AUTO_S2"));
					sat.setComVelMedS2(rs.getInt("VEL_MEDIA_COM_S2"));
					sat.setMotoVelMedS2(rs.getInt("VEL_MEDIA_MOTO_S2"));
					sat.setTotalVelMedS2(rs.getInt("VEL_MEDIA_TOTAL_S2"));
					sat.setOccupationRateS2((rs.getDouble("TAXA_OCUPACAO_S2")));
																															
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
				"CASE WHEN DATEDIFF(NOW(), d.DATA_HORA) > 0 THEN date_format(d.DATA_HORA, '%d/%m/%y %H:%i') ELSE " + 			
				"CASE WHEN " +
				"MINUTE(d.DATA_HORA) = 45 THEN CONCAT(DATE_FORMAT(d.DATA_HORA, '%H:%i -'), DATE_FORMAT(DATE_ADD(d.DATA_HORA ,INTERVAL 14 MINUTE), ' %H:%i')) ELSE CONCAT(DATE_FORMAT(d.DATA_HORA, '%H:%i -'), DATE_FORMAT(DATE_ADD(d.DATA_HORA, INTERVAL 15 MINUTE), ' %H:%i')) END " + 
				"END 'PACOTE_HORA', " +				
				"(SELECT CASE WHEN DATEDIFF(NOW(), MAX(data)) > 0 THEN date_format(MAX(data), '%d/%m/%y %H:%i') ELSE date_format(MAX(data), '%H:%i') END FROM tb_vbv WHERE siteID = ?) 'DADO_HORA', " +
		
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
		
		"ROUND(AVG(NULLIF(CASE " +
			"WHEN (eq.dir_lane1 = eq.dir_lane2 AND eq.dir_lane1 = eq.dir_lane3 AND eq.dir_lane1 = eq.dir_lane4 AND d.NOME_FAIXA < 5) " +
			  "OR (eq.dir_lane1 = eq.dir_lane2 AND eq.dir_lane1 = eq.dir_lane3 AND d.NOME_FAIXA < 4) " +
			  "OR (eq.dir_lane1 = eq.dir_lane2 AND d.NOME_FAIXA < 3) " +
			  "OR (d.NOME_FAIXA = 1) " +
			"THEN d.VEL_MEDIA_TOTAL " +
		"ELSE 0 END, 0)), 0) 'VEL_MEDIA_TOTAL_S1', " +
		"ROUND(AVG(NULLIF(CASE " +
			"WHEN (eq.dir_lane1 <> IFNULL(eq.dir_lane2, eq.dir_lane1) AND d.NOME_FAIXA > 1) " +
			  "OR (eq.dir_lane1 <> IFNULL(eq.dir_lane3, eq.dir_lane1) AND d.NOME_FAIXA > 2) " +
			  "OR (eq.dir_lane1 <> IFNULL(eq.dir_lane4, eq.dir_lane1) AND d.NOME_FAIXA > 3) " +
			  "OR (eq.dir_lane1 <> IFNULL(eq.dir_lane5, eq.dir_lane1) AND d.NOME_FAIXA > 4) " +
			"THEN d.VEL_MEDIA_TOTAL " +
		"ELSE 0 END, 0)), 0) 'VEL_MEDIA_TOTAL_S2' " +
 			 
 	 "FROM "+RoadConcessionaire.tableDados15+" d " +
 	 "INNER JOIN sat_equipment eq on (eq.equip_id = d.nome_estacao) " +
 	 "WHERE eq.equip_id = ? AND DATA_HORA BETWEEN DATE_SUB($INTERVAL$) AND ? AND eq.visible = 1 "+ 
 	 "GROUP BY d.DATA_HORA " + 
 	 "ORDER BY d.DATA_HORA DESC ";
 	 					
 	 	try {
 			
 		 conn.start(1);
 			
 			conn.prepare_my(select.replace("$INTERVAL$", String.format(" ? , INTERVAL %s %s", time, interval)) + " LIMIT 1");
 				conn.prepare_ms(select
					.replace("date_format", "FORMAT")
					.replace("%H:%i", "hh:mm")
					.replace("IFNULL", "ISNULL")
					.replaceFirst("SELECT", "SELECT TOP 1")
					.replace("DATE_SUB($INTERVAL$", String.format("DATEADD(%s, -%s, ? ", interval, time)));
 			conn.setInt(1, equip);	
 			conn.setInt(2, equip);	
 			conn.setString(3, currentDate);
 			conn.setString(4, currentDate);
 						
 			MapResult result = conn.executeQuery();
 			
 			  System.out.println(select);
 			
 			if (result.hasNext()) {
 				for (RowResult rs : result) {
 				
 					sat.setEquip_id(rs.getInt("ESTACAO"));
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
 						"(SELECT CASE WHEN DATEDIFF(NOW(), MAX(data)) > 0 THEN date_format(MAX(data), '%d/%m/%y %H:%i') ELSE date_format(MAX(data), '%H:%i') END FROM tb_vbv WHERE siteID = ?) 'DADO_HORA' " +
 	 
 	 "FROM "+RoadConcessionaire.tableDados15+" d " +
 	 "INNER JOIN sat_equipment eq ON (eq.equip_id = d.nome_estacao) " +
 	 "WHERE eq.equip_id = ? AND eq.visible = 1 " +
 	 "ORDER BY d.DATA_HORA DESC ";
 		
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
 			conn.setInt(2, equip);	
 			 						
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
