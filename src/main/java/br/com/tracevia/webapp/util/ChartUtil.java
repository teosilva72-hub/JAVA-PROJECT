package br.com.tracevia.webapp.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.primefaces.context.RequestContext;

import com.google.gson.Gson;

import br.com.tracevia.webapp.methods.TranslationMethods;

public class ChartUtil {

	private static String dateFormat = "dd/MM/yyyy";
	private static String datetimeFormat = "dd/MM/yyyy HH:mm";

	Gson gson;

	// ----------------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------------
	// CREATE ROWS
	// ----------------------------------------------------------------------------------------------------------------			

	/**
	 * Método para criar um gráfico
	 * @author Wellington 16/10/2021
	 * @version 1.0
	 * @since 1.0 	
	 * @param chartBool - 
	 * @param vAxisTitle
	 * @param chartTitle
	 * @param chartImageName
	 * @param jsColumn
	 * @param jsData
	 * @param period
	 * @param jsonFields
	 * @param jsonArray
	 */
	public void fileGsonSingle(boolean chartBool, String vAxisTitle, String chartTitle, String chartImageName, String jsColumn, String jsData, String period, 
			String[] jsonFields, String[][] jsonArray) {

		TranslationMethods trm = new TranslationMethods();

		LocalDateTime local =  LocalDateTime.now();

		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm");  
		String formatDateTime = local.format(format);  

		chartTitle+= " - " + trm.periodName(period); // IF IS NOT A SINGLE EQUIPEMENT DO THIS

		chartImageName += formatDateTime; //NAME OF FILE WITH TIME

		if(!chartBool) {

			// Create a new instance of Gson
			gson = new Gson();

			// Converting multidimensional array into JSON	      
			jsColumn = gson.toJson(jsonFields);	

			jsData = gson.toJson(jsonArray);	

			jsData = jsData.toString().replaceAll("\"", "");	        
			// jsData = jsData.toString().replaceAll("\\(", "\\'");
			// jsData.toString().replaceAll("\\)", "\\'");
			jsData = jsData.toString().replaceAll("null", "0");	


			if(period.equals("24 hours"))
				RequestContext.getCurrentInstance().execute("reDrawChart("+jsColumn+", "+jsData+", '"+chartTitle+"', '"+vAxisTitle+"', '"+ dateFormat +"', '"+chartImageName+"');");

			else RequestContext.getCurrentInstance().execute("reDrawChart("+jsColumn+", "+jsData+", '"+chartTitle+"','"+vAxisTitle+"', '"+ datetimeFormat +"', '"+chartImageName+"');");

		}
	}	

	// ----------------------------------------------------------------------------------------------------------------			


}
