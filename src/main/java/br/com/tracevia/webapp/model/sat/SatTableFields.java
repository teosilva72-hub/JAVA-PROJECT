package br.com.tracevia.webapp.model.sat;

import java.util.List;

import br.com.tracevia.webapp.model.global.Equipments;
import br.com.tracevia.webapp.util.LocaleUtil;

public class SatTableFields {
	  
   LocaleUtil localeFields;
         
   public SatTableFields() {
	   
	   localeFields = new LocaleUtil();	
	   localeFields.getResourceBundle(LocaleUtil.LABELS_SAT);
   }
   
   
   // ------------------------------------------------------------------------------------------------------------------------------------------------
   
   /**
    * Método para criação do relatório de contagem (type 1)
    * @author Wellington
	* @version 1.0
	* @since version 1.0
    * @param fields - Array para receber os campos da tabela
    * @param fieldObjectValues - Array para receber os atributos de cada campo
    * @param listSats - lista contendo equipamentos do tipo Sats
    * @return - vazio
    */
   public void vehiclesCountTableHeader(String[] fields, String[] fieldObjectValues, List<Equipments> listSats) {
	   
	    // Table Header Fields
		fields = new String[] {localeFields.getStringKey("sat_report_header_date"), localeFields.getStringKey("sat_report_header_time"), listSats.get(0).getNome(), localeFields.getStringKey("sat_report_header_total")};
		
		// Table Header Objects
		fieldObjectValues = new String[] { "date", "dateTime", "eqp1", "total"};			
					
   }
   
  // ------------------------------------------------------------------------------------------------------------------------------------------------

   /**
    * Método para criação do relatório de contagem por fluxo (type 2)
    * @author Wellington
	* @version 1.0
	* @since version 1.0
    * @param fields - Array para receber os campos da tabela
    * @param fieldObjectValues - Array para receber os atributos de cada campo
    * @return - vazio
    */
    public void vehiclesCountFlowTableHeader(String[] fields, String[] fieldObjectValues) {
	   
    	// Table fields
    		fields = new String[] {localeFields.getStringKey("sat_report_header_date"), localeFields.getStringKey("sat_report_header_time"), 
    			  localeFields.getStringKey("sat_report_header_count_flow_light_vehicles"), localeFields.getStringKey("sat_report_header_count_flow_motorcycle_vehicles"), 
    			  localeFields.getStringKey("sat_report_header_count_flow_heavy_vehicles"),  localeFields.getStringKey("sat_report_header_count_flow_light_vehicles"), 
    			  localeFields.getStringKey("sat_report_header_count_flow_motorcycle_vehicles"), localeFields.getStringKey("sat_report_header_count_flow_heavy_vehicles"),
    			  localeFields.getStringKey("sat_report_header_total")};

    				// Table Objects
    			  fieldObjectValues = new String[] { "date", "dateTime", "lightDir1", "motoDir1", "heavyDir1",
    			"lightDir2", "motoDir2", "heavyDir2", "total"};			
					
   }
   
  // ------------------------------------------------------------------------------------------------------------------------------------------------
   
    /**
     * Método para criação do relatório de fluxo mensal (type 3)
     * @author Wellington
 	 * @version 1.0
 	 * @since version 1.0
     * @param fields - Array para receber os campos da tabela
     * @param fieldObjectValues - Array para receber os atributos de cada campo
     * @return - vazio
     */
     public void monthlyFlowTableHeader(String[] fields, String[] fieldObjectValues) {
 	   
    		// Table fields
			fields = new String[] {localeFields.getStringKey("sat_report_header_date"), localeFields.getStringKey("sat_report_header_time"), 
					localeFields.getStringKey("sat_report_header_month_flow_light_vehicles"), localeFields.getStringKey("sat_report_header_month_flow_commercial_vehicles"), localeFields.getStringKey("sat_report_header_month_flow_motorcycle_vehicles"),
					localeFields.getStringKey("sat_report_header_month_flow_light_vehicles"), localeFields.getStringKey("sat_report_header_month_flow_motorcycle_vehicles"),
					localeFields.getStringKey("sat_report_header_month_flow_commercial_vehicles"), localeFields.getStringKey("sat_report_header_monthly_flow_direction1"), localeFields.getStringKey("sat_report_header_monthly_flow_direction2")};

			// Table Objects
			fieldObjectValues = new String[] {"date", "dateTime", "lightDir1", "heavyDir1", "motoDir1", "lightDir2", "heavyDir2", "motoDir2", "speedValue1", "speedValue2"};
				
 					
    }
    
   // ------------------------------------------------------------------------------------------------------------------------------------------------
    
     /**
      * Método para criação do relatório de fluxo por período (type 4)
      * @author Wellington
  	  * @version 1.0
  	  * @since version 1.0
      * @param fields - Array para receber os campos da tabela
      * @param fieldObjectValues - Array para receber os atributos de cada campo
      * @return - vazio
      */
      public void periodFlowTableHeader(String[] fields, String[] fieldObjectValues) {
  	   
    		// Table fields
			fields = new String[] {localeFields.getStringKey("sat_report_header_date"), localeFields.getStringKey("sat_report_header_time"), localeFields.getStringKey("sat_report_header_equipment"),
					localeFields.getStringKey("sat_report_header_month_flow_light_vehicles"), 	   
					localeFields.getStringKey("sat_report_header_month_flow_commercial_vehicles"), localeFields.getStringKey("sat_report_header_month_flow_motorcycle_vehicles"), 
					localeFields.getStringKey("sat_report_header_total"), localeFields.getStringKey("sat_report_header_period_flow_speed"),
					localeFields.getStringKey("sat_report_header_month_flow_light_vehicles"), 	   
					localeFields.getStringKey("sat_report_header_month_flow_commercial_vehicles"), localeFields.getStringKey("sat_report_header_month_flow_motorcycle_vehicles"), 
					localeFields.getStringKey("sat_report_header_total"), localeFields.getStringKey("sat_report_header_period_flow_speed")};

			// Table Objects
			fieldObjectValues = new String[] {"date", "dateTime", "equipment", "motoDir1", "lightDir1", "heavyDir1", "total1", "speedValue1", "motoDir2", "lightDir2", "heavyDir2", "total2", "speedValue2"};
			
  					
     }
     
    // ------------------------------------------------------------------------------------------------------------------------------------------------
     
      /**
       * Método para criação do relatório de pesagem (type 5)
       * @author Wellington
   	   * @version 1.0
   	   * @since version 1.0
       * @param fields - Array para receber os campos da tabela
       * @param fieldObjectValues - Array para receber os atributos de cada campo
       * @return - vazio
       */
       public void weighingTableHeader(String[] fields, String[] fieldObjectValues) {
   	   
    	     // Table fields
    		fields = new String[] {localeFields.getStringKey("sat_report_header_date"), localeFields.getStringKey("sat_report_header_time"),
    						localeFields.getStringKey("sat_report_header_weighing_car"), localeFields.getStringKey("sat_report_header_weighing_motorcycle"), 	   
    						localeFields.getStringKey("sat_report_header_weighing_trailer"), localeFields.getStringKey("sat_report_header_weighing_semitrailer"), 
    						localeFields.getStringKey("sat_report_header_weighing_2_axles"), localeFields.getStringKey("sat_report_header_weighing_3_axles"),
    						localeFields.getStringKey("sat_report_header_weighing_4_axles"), localeFields.getStringKey("sat_report_header_weighing_5_axles"), 	   
    						localeFields.getStringKey("sat_report_header_weighing_6_axles"), localeFields.getStringKey("sat_report_header_weighing_7_axles"), 
    						localeFields.getStringKey("sat_report_header_weighing_8_axles"), localeFields.getStringKey("sat_report_header_weighing_9_axles"),
    						localeFields.getStringKey("sat_report_header_weighing_10_axles")};

    		// Table Objects
    		fieldObjectValues = new String[] {"date", "dateTime", "class1", "class2", "class3", "class4", "class5", "class6", "class7", "class8", "class9", "class10", "class11", "class12", "class13"};   

      }
      
     // ------------------------------------------------------------------------------------------------------------------------------------------------
          
       /**
        * Método para criação do relatório de classes Via Paulista (type 6)
        * @author Wellington
    	* @version 1.0
    	* @since version 1.0
        * @param fields - Array para receber os campos da tabela
        * @param fieldObjectValues - Array para receber os atributos de cada campo
        * @return - vazio
        */
        public void classTableHeader(String[] fields, String[] fieldObjectValues) {
    	   
        	// Table fields
			fields = new String[] {localeFields.getStringKey("sat_report_header_date"), localeFields.getStringKey("sat_report_header_time"),
					localeFields.getStringKey("sat_report_header_class_light_vehicles"), localeFields.getStringKey("sat_report_header_class_motorcycle_vehicles"), 	   
					localeFields.getStringKey("sat_report_header_class_trailer_vehicle"), localeFields.getStringKey("sat_report_header_class_semitrailer_vehicle"), 
					localeFields.getStringKey("sat_report_header_class_bus_2_axles"), localeFields.getStringKey("sat_report_header_class_bus_3_axles"), 
					localeFields.getStringKey("sat_report_header_class_truck_2_axles"), localeFields.getStringKey("sat_report_header_class_truck_3_axles"),
					localeFields.getStringKey("sat_report_header_class_truck_4_axles"), localeFields.getStringKey("sat_report_header_class_truck_5_axles"), 	   
					localeFields.getStringKey("sat_report_header_class_truck_6_axles"), localeFields.getStringKey("sat_report_header_class_truck_7_axles"), 
					localeFields.getStringKey("sat_report_header_class_truck_8_axles"), localeFields.getStringKey("sat_report_header_class_truck_9_axles"),
					localeFields.getStringKey("sat_report_header_class_truck_10_axles"), localeFields.getStringKey("sat_report_header_total") };

			// Table Objects
			fieldObjectValues = new String[] {"date", "dateTime", "class1", "class2", "class3", "class4", "class5", "class6", "class7", "class8", "class9", "class10", 
					"class11", "class12", "class13", "class14", "class15", "class16", "class17", "total"};

       }
       
      // ------------------------------------------------------------------------------------------------------------------------------------------------
           
        /**
         * Método para criação do relatório de Eixos (type 7)
         * @author Wellington
     	 * @version 1.0
     	 * @since version 1.0
         * @param fields - Array para receber os campos da tabela
         * @param fieldObjectValues - Array para receber os atributos de cada campo
         * @return - vazio
         */
         public void axleTableHeader(String[] fields, String[] fieldObjectValues) {
     	   
        	// Table fields
 			fields = new String[] {localeFields.getStringKey("sat_report_header_date"), localeFields.getStringKey("sat_report_header_time"),
 					localeFields.getStringKey("sat_report_header_axles_2_axles"), localeFields.getStringKey("sat_report_header_axles_3_axles"),
 					localeFields.getStringKey("sat_report_header_axles_4_axles"), localeFields.getStringKey("sat_report_header_axles_5_axles"), 	   
 					localeFields.getStringKey("sat_report_header_axles_6_axles"), localeFields.getStringKey("sat_report_header_axles_7_axles"), 
 					localeFields.getStringKey("sat_report_header_axles_8_axles"), localeFields.getStringKey("sat_report_header_axles_9_axles"),
 					localeFields.getStringKey("sat_report_header_axles_10_axles"), localeFields.getStringKey("sat_report_header_total") };

 			// Table Objects
 			fieldObjectValues = new String[] {"date", "dateTime", "axles_1", "axles_2", "axles_3", "axles_4", "axles_5", "axles_6", "axles_7", "axles_8", "axles_9", "total"};

        }
        
       // ------------------------------------------------------------------------------------------------------------------------------------------------
            
      /**
       * Método para criação do relatório de velocidade (type 8)
       * @author Wellington
	   * @version 1.0
	   * @since version 1.0
       * @param fields - Array para receber os campos da tabela
       * @param fieldObjectValues - Array para receber os atributos de cada campo
       * @return - vazio
       */
      public void speedTableHeader(String[] fields, String[] fieldObjectValues) {
	   
	    // Table Header Fields
		fields = new String[] {localeFields.getStringKey("sat_report_header_date"), localeFields.getStringKey("sat_report_header_time"),
				localeFields.getStringKey("sat_report_header_speed_until_50km"), localeFields.getStringKey("sat_report_header_speed_50_to_70km"),
				localeFields.getStringKey("sat_report_header_speed_70_to_90km"), localeFields.getStringKey("sat_report_header_speed_90_to_120km"), 	   
				localeFields.getStringKey("sat_report_header_speed_120_to_150km"), localeFields.getStringKey("sat_report_header_speed_over_150km"), 
				localeFields.getStringKey("sat_report_header_total") };
				
		// Table Header Objects
		fieldObjectValues = new String[] {"date", "dateTime", "speed50km", "speed70km", "speed90km", "speed120km", "speed150km", "speed150km_bigger", "total"};
						
     }
   
      // ------------------------------------------------------------------------------------------------------------------------------------------------

      /**
       * Método para criação do relatório de classes LL (type 9)
       * @author Wellington
   	   * @version 1.0
       * @since version 1.0
       * @param fields - Array para receber os campos da tabela
       * @param fieldObjectValues - Array para receber os atributos de cada campo
       * @return - vazio
       */
       public void classLLTableHeader(String[] fields, String[] fieldObjectValues) {
   	   
    	// Table fields
    	 fields = new String[] {localeFields.getStringKey("sat_report_header_date"), localeFields.getStringKey("sat_report_header_time"),
    			localeFields.getStringKey("sat_report_header_class_motorcycle_vehicles"), localeFields.getStringKey("sat_report_header_class_light_vehicles"), 
    			localeFields.getStringKey("sat_report_header_class_small_vehicles"), localeFields.getStringKey("sat_report_header_class_long_vehicles"), 
    		    localeFields.getStringKey("sat_report_header_class_bus_vehicles"), localeFields.getStringKey("sat_report_header_total")};		   

    			// Table Objects
    		    fieldObjectValues = new String[] {"date", "dateTime", "class1", "class2", "class3", "class4", "class5", "total"};
      }
      
     // ------------------------------------------------------------------------------------------------------------------------------------------------
           
       /**
        * Método para criação do relatório de tipos LL (type 10)
        * @author Wellington
        * @version 1.0
        * @since version 1.0
        * @param fields - Array para receber os campos da tabela
        * @param fieldObjectValues - Array para receber os atributos de cada campo
        * @return - vazio
        */
        public void typeLLTableHeader(String[] fields, String[] fieldObjectValues) {
    	   
        	// Table fields
        	fields = new String[] {localeFields.getStringKey("sat_report_header_date"), localeFields.getStringKey("sat_report_header_time"),
        			localeFields.getStringKey("sat_report_header_class_light_vehicles"), localeFields.getStringKey("sat_report_header_class_commercials_vehicles"), 
        			localeFields.getStringKey("sat_report_header_total")};	

        	// Table Objects
        	fieldObjectValues = new String[] {"date", "dateTime", "class1", "class2", "total"};
        				
       }
       
      // ------------------------------------------------------------------------------------------------------------------------------------------------
      
        /**
         * Método para criação do relatório de velocidade LL (type 11)
         * @author Wellington
         * @version 1.0
         * @since version 1.0
         * @param fields - Array para receber os campos da tabela
         * @param fieldObjectValues - Array para receber os atributos de cada campo
         * @return - vazio
         */
         public void speedLLTableHeader(String[] fields, String[] fieldObjectValues) {
     	   
        	// Table Header Fields
     		fields = new String[] {localeFields.getStringKey("sat_report_header_date"), localeFields.getStringKey("sat_report_header_time"),
     				localeFields.getStringKey("sat_report_header_speed_until_50km"), localeFields.getStringKey("sat_report_header_speed_50_to_70km"),
     				localeFields.getStringKey("sat_report_header_speed_70_to_90km"), localeFields.getStringKey("sat_report_header_speed_90_to_120km"), 	   
     				localeFields.getStringKey("sat_report_header_speed_120_to_150km"), localeFields.getStringKey("sat_report_header_speed_over_150km"), 
     				localeFields.getStringKey("sat_report_header_total") };
     				
     		// Table Header Objects
     		fieldObjectValues = new String[] {"date", "dateTime", "speed50km", "speed70km", "speed90km", "speed120km", "speed150km", "speed150km_bigger", "total"};
     						
        }
        
       // ------------------------------------------------------------------------------------------------------------------------------------------------
           
      /**
       * Método para criação do relatório de Classes CCR (type 12)
       * @author Wellington
   	   * @version 1.0
   	   * @since version 1.0
       * @param fields - Array para receber os campos da tabela
       * @param fieldObjectValues - Array para receber os atributos de cada campo
       * @return - vazio
       */
       public void classCCRTableHeader(String[] fields, String[] fieldObjectValues) {
   	   
    		// Table fields
			fields = new String[] {localeFields.getStringKey("sat_report_header_date"), localeFields.getStringKey("sat_report_header_time"),
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

			// Table Objects
			fieldObjectValues = new String[] {"date", "dateTime", "class1", "class2", "class3", "class4", "class5", "class6", "class7", "class8", "class9", "class10", 
					"class11", "class12", "class13", "class14", "class15", "class16", "class17", "class18", "class19", "class20", "total"};

      }
   
       // ------------------------------------------------------------------------------------------------------------------------------------------------

       /**
        * Método para criação do relatório de velocidade multi seleção (type 13)
        * @author Wellington
    	* @version 1.0
    	* @since version 1.0
        * @param fields - Array para receber os campos da tabela
        * @param fieldObjectValues - Array para receber os atributos de cada campo
        * @return - vazio
        */
        public void speedMultiSelectTableHeader(String[] fields, String[] fieldObjectValues) {
    	   
        	// Table Header Fields
     		fields = new String[] {localeFields.getStringKey("sat_report_header_date"), localeFields.getStringKey("sat_report_header_time"),
     				localeFields.getStringKey("sat_report_header_equipment"), localeFields.getStringKey("sat_report_header_speed_until_50km"), 
     				localeFields.getStringKey("sat_report_header_speed_50_to_70km"), localeFields.getStringKey("sat_report_header_speed_70_to_90km"), 
     				localeFields.getStringKey("sat_report_header_speed_90_to_120km"), localeFields.getStringKey("sat_report_header_speed_120_to_150km"), 
     				localeFields.getStringKey("sat_report_header_speed_over_150km"), localeFields.getStringKey("sat_report_header_total") };
     				
     		// Table Header Objects
     		fieldObjectValues = new String[] {"date", "dateTime", "equipment", "speed50km", "speed70km", "speed90km", "speed120km", "speed150km", "speed150km_bigger", "total"};
     						
       }
    
        // ------------------------------------------------------------------------------------------------------------------------------------------------

       
       
       
}
