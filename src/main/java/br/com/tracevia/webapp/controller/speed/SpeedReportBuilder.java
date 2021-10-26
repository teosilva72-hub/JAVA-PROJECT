package br.com.tracevia.webapp.controller.speed;

import java.util.List;

import br.com.tracevia.webapp.model.global.ReportBuild;
import br.com.tracevia.webapp.model.speed.SpeedReport;
import br.com.tracevia.webapp.model.speed.SpeedReport.Builder;

/**
 * Classe para criar método com construtor Builder (SPEED)
 * @author Wellington 19/10/2021
 * @version 1.0
 * @since 1.0
 */

public class SpeedReportBuilder {
		
	 public static void speedRecords(List<Builder> resultList, String[][] array) {
		 
		 for(int k = 0; k < ReportBuild.numRegisters; k++) {       			

				resultList.add(new SpeedReport.Builder().date(array[k][0])
						.time(array[k][1])
						.register(array[k][2] == null? 0 : Integer.parseInt(array[k][2]))
						.speed(array[k][3] == null? 0 : Integer.parseInt(array[k][3]))
						.direction(array[k][4])
						.equipment(array[k][5]));			    				 

			   }		 
	      }
	 
	// ----------------------------------------------------------------------------------------------------------------

}
