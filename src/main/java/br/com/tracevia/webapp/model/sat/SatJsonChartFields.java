package br.com.tracevia.webapp.model.sat;


import java.util.List;

import br.com.tracevia.webapp.model.global.Equipments;
import br.com.tracevia.webapp.util.LocaleUtil;

public class SatJsonChartFields {
		  
	
	 LocaleUtil localeFields;
	 
	         
	 public SatJsonChartFields() {
		   
		localeFields = new LocaleUtil();	
		localeFields.getResourceBundle(LocaleUtil.LABELS_SAT);
		   
	 }	   
	   
	   // ------------------------------------------------------------------------------------------------------------------------------------------------
	   
	   /**
	    * Método para criação do gráfico de contagem (type 1)
	    * @author Wellington
		* @version 1.0
		* @since version 1.0
	    * @param fields - Array para receber os campos da tabela
	    * @param fieldObjectValues - Array para receber os atributos de cada campo
	    * @param listSats - lista contendo equipamentos do tipo Sats
	    * @param fileName - Variável do nome do arquivo a ser preenchido
	    * @param chartTitle - Váriavel do título do gráfico a ser preenchido
	    * @return - vazio
	    */
	   public void vehiclesCountJSONFields(String[] fields, String[] fieldObjectValues, List<Equipments> listSats, String fileName, String chartTitle) {
		   
		    // JSON Chart Fields
			fields = new String[] {localeFields.getStringKey("sat_reports_chart_haxis"), listSats.get(0).getNome(), localeFields.getStringKey("sat_report_header_total")};
			
			// Chart File Name
			fileName =  localeFields.getStringKey("sat_report_chart_title_count");
			
			// Chart Title
			chartTitle = localeFields.getStringKey("sat_report_chart_file_name_count");
								
	   }
	   
	  // ------------------------------------------------------------------------------------------------------------------------------------------------
	     
	   /**
	    * Método para criação do gráfico de contagem por fluxo (type 2)
	    * @author Wellington
		* @version 1.0
		* @since version 1.0
	    * @param fields - Array para receber os campos da tabela
	    * @param fieldObjectValues - Array para receber os atributos de cada campo
	    * @param fileName - Variável do nome do arquivo a ser preenchido
	    * @param chartTitle - Váriavel do título do gráfico a ser preenchido
	    * @return - vazio
	    */
	    public void vehiclesCountFlowTableHeader(String[] fields, String[] fieldObjectValues, String fileName, String chartTitle) {
		   
	    	// Table fields
	    		fields = new String[] {localeFields.getStringKey("sat_reports_chart_haxis"), 
	    			  localeFields.getStringKey("sat_report_header_count_flow_light_vehicles"), localeFields.getStringKey("sat_report_header_count_flow_motorcycle_vehicles"), 
	    			  localeFields.getStringKey("sat_report_header_count_flow_heavy_vehicles"),  localeFields.getStringKey("sat_report_header_count_flow_light_vehicles"), 
	    			  localeFields.getStringKey("sat_report_header_count_flow_motorcycle_vehicles"), localeFields.getStringKey("sat_report_header_count_flow_heavy_vehicles"),
	    			  localeFields.getStringKey("sat_report_header_total")};
	    		
	    		// Chart File Name
				fileName =  localeFields.getStringKey("sat_report_chart_file_name_count_period");
				
				// Chart Title
				chartTitle = localeFields.getStringKey("sat_report_chart_title_count_period");
	    		
	   }
	   
	  // ------------------------------------------------------------------------------------------------------------------------------------------------
	   	    
	      /**
	       * Método para criação do gráfico de pesagem (type 5)
	       * @author Wellington
	   	   * @version 1.0
	   	   * @since version 1.0
	       * @param fields - Array para receber os campos da tabela
	       * @param fieldObjectValues - Array para receber os atributos de cada campo
	       * @param fileName - Variável do nome do arquivo a ser preenchido
	       * @param chartTitle - Váriavel do título do gráfico a ser preenchido
	       * @return - vazio
	       */
	       public void weighingJSONFields(String[] fields, String[] fieldObjectValues, String fileName, String chartTitle) {
	   	   
	    	     // Table fields
	    		fields = new String[] {localeFields.getStringKey("sat_reports_chart_haxis"), localeFields.getStringKey("sat_report_header_weighing_car"), 
	    				 localeFields.getStringKey("sat_report_header_weighing_motorcycle"), localeFields.getStringKey("sat_report_header_weighing_trailer"), 
	    				 localeFields.getStringKey("sat_report_header_weighing_semitrailer"), localeFields.getStringKey("sat_report_header_weighing_2_axles"), 
	    				 localeFields.getStringKey("sat_report_header_weighing_3_axles"), localeFields.getStringKey("sat_report_header_weighing_4_axles"), 
	    				 localeFields.getStringKey("sat_report_header_weighing_5_axles"), localeFields.getStringKey("sat_report_header_weighing_6_axles"), 
	    				 localeFields.getStringKey("sat_report_header_weighing_7_axles"), localeFields.getStringKey("sat_report_header_weighing_8_axles"), 
	    				 localeFields.getStringKey("sat_report_header_weighing_9_axles"), localeFields.getStringKey("sat_report_header_weighing_10_axles")};

	    		// Chart File Name
				fileName =  localeFields.getStringKey("sat_report_chart_file_name_weighing");
				
				// Chart Title
				chartTitle = localeFields.getStringKey("sat_report_chart_title_weighing");
	      }
	      
	     // ------------------------------------------------------------------------------------------------------------------------------------------------
	          
	       /**
	        * Método para criação do gráfico de classes Via Paulista (type 6)
	        * @author Wellington
	    	* @version 1.0
	    	* @since version 1.0
	        * @param fields - Array para receber os campos da tabela
	        * @param fieldObjectValues - Array para receber os atributos de cada campo
	        * @param fileName - Variável do nome do arquivo a ser preenchido
	        * @param chartTitle - Váriavel do título do gráfico a ser preenchido
	        * @return - vazio
	        */
	        public void classJSONFields(String[] fields, String[] fieldObjectValues, String fileName, String chartTitle) {
	    	   
	        	// Table fields
	        	fields = new String[] {localeFields.getStringKey("sat_reports_chart_haxis"),
	        			localeFields.getStringKey("sat_report_header_class_light_vehicles"), localeFields.getStringKey("sat_report_header_class_motorcycle_vehicles"), 	   
	        			localeFields.getStringKey("sat_report_header_class_trailer_vehicle"), localeFields.getStringKey("sat_report_header_class_semitrailer_vehicle"), 
	        			localeFields.getStringKey("sat_report_header_class_bus_2_axles"), localeFields.getStringKey("sat_report_header_class_bus_3_axles"), 
	        			localeFields.getStringKey("sat_report_header_class_truck_2_axles"), localeFields.getStringKey("sat_report_header_class_truck_3_axles"),
	        			localeFields.getStringKey("sat_report_header_class_truck_4_axles"), localeFields.getStringKey("sat_report_header_class_truck_5_axles"), 	   
	        			localeFields.getStringKey("sat_report_header_class_truck_6_axles"), localeFields.getStringKey("sat_report_header_class_truck_7_axles"), 
	        			localeFields.getStringKey("sat_report_header_class_truck_8_axles"), localeFields.getStringKey("sat_report_header_class_truck_9_axles"),
	        			localeFields.getStringKey("sat_report_header_class_truck_10_axles"), localeFields.getStringKey("sat_report_header_total") };

	        	// Chart File Name
				fileName =  localeFields.getStringKey("sat_report_chart_file_name_classes");
				
				// Chart Title
				chartTitle = localeFields.getStringKey("sat_report_chart_title_classes");
	       }
	       
	      // ------------------------------------------------------------------------------------------------------------------------------------------------
	           
	        /**
	         * Método para criação do relatório de Eixos (type 7)
	         * @author Wellington
	     	 * @version 1.0
	     	 * @since version 1.0
	         * @param fields - Array para receber os campos da tabela
	         * @param fieldObjectValues - Array para receber os atributos de cada campo
	         * @param fileName - Variável do nome do arquivo a ser preenchido
	         * @param chartTitle - Váriavel do título do gráfico a ser preenchido
	         * @return - vazio
	         */
	         public void axleJSONFields(String[] fields, String[] fieldObjectValues, String fileName, String chartTitle) {
	     	   
	        	// Table fields
	 			fields = new String[] {localeFields.getStringKey("sat_reports_chart_haxis"),
	 					localeFields.getStringKey("sat_report_header_axles_2_axles"), localeFields.getStringKey("sat_report_header_axles_3_axles"),
	 					localeFields.getStringKey("sat_report_header_axles_4_axles"), localeFields.getStringKey("sat_report_header_axles_5_axles"), 	   
	 					localeFields.getStringKey("sat_report_header_axles_6_axles"), localeFields.getStringKey("sat_report_header_axles_7_axles"), 
	 					localeFields.getStringKey("sat_report_header_axles_8_axles"), localeFields.getStringKey("sat_report_header_axles_9_axles"),
	 					localeFields.getStringKey("sat_report_header_axles_10_axles"), localeFields.getStringKey("sat_report_header_total") };

	        	// Chart File Name
				fileName =  localeFields.getStringKey("sat_report_chart_file_name_axles");
				
				// Chart Title
				chartTitle = localeFields.getStringKey("sat_report_chart_title_axles");
	 			
	        }
	        
	       // ------------------------------------------------------------------------------------------------------------------------------------------------
	            
	      /**
	       * Método para criação do gráfico de velocidade (type 8)
	       * @author Wellington
		   * @version 1.0
		   * @since version 1.0
	       * @param fields - Array para receber os campos da tabela
	       * @param fieldObjectValues - Array para receber os atributos de cada campo
	       * @param fileName - Variável do nome do arquivo a ser preenchido
	       * @param chartTitle - Váriavel do título do gráfico a ser preenchido
	       * @return - vazio
	       */
	      public void speedJSONFields(String[] fields, String[] fieldObjectValues, String fileName, String chartTitle) {
		   
		    // Table Header Fields
			fields = new String[] {localeFields.getStringKey("sat_reports_chart_haxis"),
					localeFields.getStringKey("sat_report_header_speed_until_50km"), localeFields.getStringKey("sat_report_header_speed_50_to_70km"),
					localeFields.getStringKey("sat_report_header_speed_70_to_90km"), localeFields.getStringKey("sat_report_header_speed_90_to_120km"), 	   
					localeFields.getStringKey("sat_report_header_speed_120_to_150km"), localeFields.getStringKey("sat_report_header_speed_over_150km"), 
					localeFields.getStringKey("sat_report_header_total") };
					
			// Chart File Name
			fileName =  localeFields.getStringKey("sat_report_chart_file_name_speed");
			
			// Chart Title
			chartTitle = localeFields.getStringKey("sat_report_chart_title_speed");			
	     }
	   
	      // ------------------------------------------------------------------------------------------------------------------------------------------------

	      /**
	       * Método para criação do gráfico de classes LL (type 9)
	       * @author Wellington
	   	   * @version 1.0
	       * @since version 1.0
	       * @param fields - Array para receber os campos da tabela
	       * @param fieldObjectValues - Array para receber os atributos de cada campo
	       * @param fileName - Variável do nome do arquivo a ser preenchido
	       * @param chartTitle - Váriavel do título do gráfico a ser preenchido
	       * @return - vazio
	       */
	       public void classLLJSONFields(String[] fields, String[] fieldObjectValues, String fileName, String chartTitle) {
	   	   
	    	// Table fields
	    	 fields = new String[] {localeFields.getStringKey("sat_reports_chart_haxis"),
	    			localeFields.getStringKey("sat_report_header_class_motorcycle_vehicles"), localeFields.getStringKey("sat_report_header_class_light_vehicles"), 
	    			localeFields.getStringKey("sat_report_header_class_small_vehicles"), localeFields.getStringKey("sat_report_header_class_long_vehicles"), 
	    		    localeFields.getStringKey("sat_report_header_class_bus_vehicles"), localeFields.getStringKey("sat_report_header_total")};		   

	    	// Chart File Name
			 fileName =  localeFields.getStringKey("sat_report_chart_file_name_classes_ll");
				
			// Chart Title
			 chartTitle = localeFields.getStringKey("sat_report_chart_title_classes_ll");	
			
	      }
	      
	     // ------------------------------------------------------------------------------------------------------------------------------------------------
	           
	       /**
	        * Método para criação do gráfico de tipos LL (type 10)
	        * @author Wellington
	        * @version 1.0
	        * @since version 1.0
	        * @param fields - Array para receber os campos da tabela
	        * @param fieldObjectValues - Array para receber os atributos de cada campo
	        * @param fileName - Variável do nome do arquivo a ser preenchido
	        * @param chartTitle - Váriavel do título do gráfico a ser preenchido
	        * @return - vazio
	        */
	        public void typeLLJSONFields(String[] fields, String[] fieldObjectValues, String fileName, String chartTitle) {
	    	   
	        	// Table fields
	        	fields = new String[] {localeFields.getStringKey("sat_reports_chart_haxis"),
	        			localeFields.getStringKey("sat_report_header_class_light_vehicles"), localeFields.getStringKey("sat_report_header_class_commercials_vehicles"), 
	        			localeFields.getStringKey("sat_report_header_total")};	

	        	// Chart File Name
				fileName =  localeFields.getStringKey("sat_report_chart_file_name_axles_ll");
					
				// Chart Title
				chartTitle = localeFields.getStringKey("sat_report_chart_title_axles_ll");	
	        				
	       }
	       
	      // ------------------------------------------------------------------------------------------------------------------------------------------------
	      
	        /**
	         * Método para criação do gráfico de velocidade LL (type 11)
	         * @author Wellington
	         * @version 1.0
	         * @since version 1.0
	         * @param fields - Array para receber os campos da tabela
	         * @param fieldObjectValues - Array para receber os atributos de cada campo
	         * @param fileName - Variável do nome do arquivo a ser preenchido
	         * @param chartTitle - Váriavel do título do gráfico a ser preenchido
	         * @return - vazio
	         */
	         public void speedLLJSONFields(String[] fields, String[] fieldObjectValues, String fileName, String chartTitle) {
	     	   
	        	// Table Header Fields
	     		fields = new String[] {localeFields.getStringKey("sat_reports_chart_haxis"),
	     				localeFields.getStringKey("sat_report_header_speed_until_50km"), localeFields.getStringKey("sat_report_header_speed_50_to_70km"),
	     				localeFields.getStringKey("sat_report_header_speed_70_to_90km"), localeFields.getStringKey("sat_report_header_speed_90_to_120km"), 	   
	     				localeFields.getStringKey("sat_report_header_speed_120_to_150km"), localeFields.getStringKey("sat_report_header_speed_over_150km"), 
	     				localeFields.getStringKey("sat_report_header_total") };
	     			
	     	    // Chart File Name
				fileName =  localeFields.getStringKey("sat_report_chart_file_name_speed_ll");
					
				// Chart Title
				chartTitle = localeFields.getStringKey("sat_report_chart_title_speed_ll");	
	     					
	        }
	        
	       // ------------------------------------------------------------------------------------------------------------------------------------------------
	           
	      /**
	       * Método para criação do gráfico de Classes CCR (type 12)
	       * @author Wellington
	   	   * @version 1.0
	   	   * @since version 1.0
	       * @param fields - Array para receber os campos da tabela
	       * @param fieldObjectValues - Array para receber os atributos de cada campo
	       * @param fileName - Variável do nome do arquivo a ser preenchido
	       * @param chartTitle - Váriavel do título do gráfico a ser preenchido
	       * @return - vazio
	       */
	       public void classCCRJSONFields(String[] fields, String[] fieldObjectValues, String fileName, String chartTitle) {
	   	   
	    		// Table fields
				fields = new String[] {localeFields.getStringKey("sat_reports_chart_haxis"),
						localeFields.getStringKey("sat_report_header_class_light_vehicles"), localeFields.getStringKey("sat_report_header_class_motorcycle_vehicles"), 	   
						localeFields.getStringKey("sat_report_header_class_trailer_vehicle"), localeFields.getStringKey("sat_report_header_class_semitrailer_vehicle"), 
						localeFields.getStringKey("sat_report_header_class_truck_simple1_2_axles"), localeFields.getStringKey("sat_report_header_class_truck_simple2_2_axles"), 
						localeFields.getStringKey("sat_report_header_class_truck_2_axles"), localeFields.getStringKey("sat_report_header_class_truck_3_axles"),
						localeFields.getStringKey("sat_report_header_class_truck_4_axles"), localeFields.getStringKey("sat_report_header_class_truck_5_axles"), 	   
						localeFields.getStringKey("sat_report_header_class_truck_6_axles"), localeFields.getStringKey("sat_report_header_class_truck_7_axles"), 
						localeFields.getStringKey("sat_report_header_class_truck_8_axles"), localeFields.getStringKey("sat_report_header_class_truck_9_axles"),
						localeFields.getStringKey("sat_report_header_class_truck_10_axles"), localeFields.getStringKey("sat_report_header_class_bus_2_axles"),
						localeFields.getStringKey("sat_report_header_class_bus_3_axles"), localeFields.getStringKey("sat_report_header_class_bus_4_axles"),
						localeFields.getStringKey("sat_report_header_class_bus_5_axles"), localeFields.getStringKey("sat_report_header_class_bus_6_axles"),					
						localeFields.getStringKey("sat_report_header_total") };

				 // Chart File Name
				fileName =  localeFields.getStringKey("sat_report_chart_file_name_classes_ccr");
					
				// Chart Title
				chartTitle = localeFields.getStringKey("sat_report_chart_title_classes_ccr");	
				
	      }
	   
	       // ------------------------------------------------------------------------------------------------------------------------------------------------

}
