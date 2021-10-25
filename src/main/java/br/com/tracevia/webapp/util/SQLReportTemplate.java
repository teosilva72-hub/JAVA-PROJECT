package br.com.tracevia.webapp.util;

import br.com.tracevia.webapp.model.global.RoadConcessionaire;

/**
 * Classe com modelos para criação de queries SQL para os relatórios 
 * @author Wellington 09/10/2021
 *
 */

public class SQLReportTemplate {
	
	// ----------------------------------------------------------------------------------------------------------------				
	
	// INTERVAL FORMULA VARS
	
	private static String timeFormula05Min = "05*60";
	private static String timeFormula06Min = "06*60";
	private static String timeFormula10Min = "10*60";
	private static String timeFormula15Min = "15*60";
	private static String timeFormula30Min = "30*60";
	private static String timeFormula01Hour = "60*60";
	private static String timeFormula06Hours = "360*60";
	
	// ----------------------------------------------------------------------------------------------------------------	
	
	// INTERVAL TIME TO ADD
	
	private static int secToTimeAdd05Min = 240;
	private static int secToTimeAdd06Min = 300;
	private static int secToTimeAdd10Min = 540;
	private static int secToTimeAdd15Min = 840;
	private static int secToTimeAdd30Min = 1740;
	private static int secToTimeAdd01Hour = 3599;
	private static int secToTimeAdd06Hours = 21599;

	// ----------------------------------------------------------------------------------------------------------------		
		
	// ORDER BY ASC / DESC VARS
	
	// private static String orderByASC = "ASC";
	// private static String orderByDESC = "DESC";
	
	// ----------------------------------------------------------------------------------------------------------------	
	// HEADERS	
	// ----------------------------------------------------------------------------------------------------------------
	
	/**
	 * Método para criar um cabeçalho para relatórios com intervalos
	 * @author Wellington 09/10/2021
	 * @version 1.0
	 * @since 1.0
	 * @param dateColumn - nome da coluna data
	 * @param interval - intervalo a ser usado
	 * @param secToTime - diferença a ser somada de cada intervalo
	 * @return o cabeçalho para criação de uma query
	 */
	public static String queryHeaderIntervals(String dateColumn, String interval, int secToTime) 
	{		
		return "SELECT DATE_FORMAT(data, '%Y-%m-%d') AS data, " + 
				  "CONCAT(CONCAT(DATE_FORMAT((SEC_TO_TIME(TIME_TO_SEC('"+dateColumn+"') - TIME_TO_SEC('"+dateColumn+"')%('"+interval+"'))),'%H:%i'), ' - '), " +
				  "DATE_FORMAT((SEC_TO_TIME((TIME_TO_SEC('"+dateColumn+"') + "+secToTime+" ) - TIME_TO_SEC(data)%('"+interval+"'))),'%H:%i')) 'INTERVAL', ";
		
	}
		
	// ----------------------------------------------------------------------------------------------------------------	
	
	/**
	 * Método para criar um cabeçalho para o relatório de 01 dia
	 * @author Wellington 09/10/2021
	 * @version 1.0
	 * @since 1.0
	 * @param date - nome da coluna do tipo data a ser inserida para executar a query
	 * @return o cabeçalho para criação de uma query
	 */
	public static String queryHeader01Day(String date) 
	{		
		return "SELECT DATE_FORMAT('"+date+"', '%Y-%m-%d') AS data, CONCAT('', ' ----- ') 'INTERVAL', ";
	}
	
	// ----------------------------------------------------------------------------------------------------------------	
	
	/**
	  * Método para criar um cabeçalho para o relatório do tipo mensal
	  * @author Wellington 09/10/2021
	  * @version 1.0
	  * @since 1.0
	  * @param date - nome da coluna do tipo data a ser inserida para executar a query
	  * @return o cabeçalho para criação de uma query
     */
	public static String queryHeaderMonth(String date) 
	{			
		return "SELECT DATE_FORMAT('"+date+"', '%M') 'MONTH_', ";
	}
	
	// ----------------------------------------------------------------------------------------------------------------	
	
	/**
	  * Método para criar um cabeçalho para o relatório do tipo anual
	  * @author Wellington 09/10/2021
	  * @version 1.0
	  * @since 1.0
	  * @param date - nome da coluna do tipo data a ser inserida para executar a query
	  * @return o cabeçalho para criação de uma query
     */
	public static String queryHeaderYear(String date)
	{			
		return "SELECT DATE_FORMAT('"+date+"', '%Y') AS year_, ";
	}
	
	// ----------------------------------------------------------------------------------------------------------------	
	
	/**
	  * Método para criar um cabeçalho para o relatório do tipo anual
	  * @author Wellington 09/10/2021
	  * @version 1.0
	  * @since 1.0
	  * @param date - nome da coluna do tipo data a ser inserida para executar a query
	  * @return o cabeçalho para criação de uma query
     */
	public static String queryHeaderDate(String date)
	{			
		return "SELECT DATE_FORMAT('"+date+"', '%d/%m/%Y') AS date_, ";
	}
	
	// ----------------------------------------------------------------------------------------------------------------	
	
     /**
	  * Método para criar um cabeçalho para o relatório do tipo anual
	  * @author Wellington 09/10/2021
	  * @version 1.0
	  * @since 1.0
	  * @param date - nome da coluna do tipo data a ser inserida para executar a query
	  * @return o cabeçalho para criação de uma query
	 */
	public static String queryHeaderTime(String date)
	{			
		return "SELECT DATE_FORMAT('"+date+"', '%H:%i:%s') AS time_, ";
	}
	
	// ----------------------------------------------------------------------------------------------------------------			
	// ----------------------------------------------------------------------------------------------------------------		
	// TABLE REFERENCE
	// ----------------------------------------------------------------------------------------------------------------
			
	/**
	  * Método para referenciar uma tabela SQL
	  * @author Wellington 09/10/2021
	  * @version 1.0
	  * @since 1.0
	  * @param table - nome da tabela a ser referênciada
	  * @return a tabela de referência da pesquisa
     */
	public static String fromTable(String table) 
	{
		return "FROM '"+table+"' tbl ";
	}
	
	// ----------------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------------		
	// JOIN REFERENCE
	// ----------------------------------------------------------------------------------------------------------------
	
	/**
	 * Método para utilizar dados relacionados de duas tabelas associadas
	 * @author Wellington 09/10/2021
	 * @version 1.0
	 * @since 1.0	
	 * @param module - tabela a ser referênciada através do módulo 
	 * @param columnA - coluna originada da tabela de equipamentos
	 * @param columnB - coluna da tabela de dados a ser referênciada
	 * @return uma cláusula de junção entre duas tabelas
	 */
	public static String innerJoin(String module, String columnA, String columnB)
	{
		return "INNER JOIN '"+module+"+_equipment eq ON eq.'"+columnA+"' = tbl.'"+columnB+"' ";
	}
		
	// ----------------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------------
	// INDEX REFERENCE
	// ----------------------------------------------------------------------------------------------------------------
				
	/**
	 * Método para criar um index SQL
	 * @author Wellington 09/10/2021
	 * @version 1.0
	 * @since 1.0	
	 * @param indexName - nome do index a ser utilizado	
	 * @return um index para ser atribuído em uma query
	 */
	public static String useIndex(String indexName)
	{
		return "USE INDEX('"+indexName+"')";
	}
		
	// ----------------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------------
	// WHERE CLAUSE
	// ----------------------------------------------------------------------------------------------------------------
	
	/**
	 * Método para auxiliar para iniciar a cláusula WHERE
	 * @author Wellington 09/10/2021
	 * @version 1.0
	 * @since 1.0	
	 * @return string WHERE para o inicio da cláusula
	 */
    public static String whereClauseBegin() {   	   
   	   return "WHERE ";   	               	 
    }

	// ----------------------------------------------------------------------------------------------------------------

   /**
	 * Método para auxiliar para concatenar cláusula WHERE
	 * @author Wellington 09/10/2021
	 * @version 1.0
	 * @since 1.0	
	 * @return string AND para concatenar a cláusula WHERE
	 */	
	public static String whereClauseConcat() {
   	   
   	   return "AND ";   	              	 
   	 
    }
	
	// ----------------------------------------------------------------------------------------------------------------	
	
	/**
	 * Método para auxiliar para filtrar um intervalo de datas através da data de inicio e data de fim
	 * @author Wellington 09/10/2021
	 * @version 1.0
	 * @since 1.0	
	 * @param startColumn - coluna da data inicial 
	 * @param endColumn - coluna da data final 
	 * @param startDate - data inicial
	 * @param endDate - data final
	 * @return cláusula para filtro de um intervalo entre datas
	 */	 
	public static String whereClauseDate(String startDateColumn, String endDateColumn, String startDate, String endDate) {
   	   
   	   return "'"+startDateColumn+"' >= '"+startDate+"' AND  '"+endDateColumn+"' <= '"+endDate+"' ";   	              	 
   	 
    }	

	// ----------------------------------------------------------------------------------------------------------------

   /**
	 * Método para auxiliar para filtrar um intervalo de datas através de um campo
	 * @author Wellington 09/10/2021
	 * @version 1.0
	 * @since 1.0	
	 * @param dateColumn - coluna da data a ser verificada
	 * @param startDate - data inicial
	 * @param endDate - data final
	 * @return cláusula para filtro de um intervalo entre datas
	 */	  
	public static String whereClauseDateBetween(String dateColumn, String startDate, String endDate) {
   	   
   	   return "'"+dateColumn+"' BETWEEN '"+startDate+"' AND '"+endDate+"' ";   	              	 
   	 
    }

	// ----------------------------------------------------------------------------------------------------------------	
	
    /**
	 * Método para auxiliar para filtrar o id do equipamento
	 * @author Wellington 09/10/2021
	 * @version 1.0
	 * @since 1.0	
	 * @param equipColumn - coluna do id do equipamento 
	 * @param equip - id do equipamento 
	 * @return cláusula para filtro do id de equipamento
	 */	 
	public static String whereClauseEquip(String equipColumn, String equipId) {
   	   
   	   return "'"+equipColumn+"' = '"+equipId+"' ";   	              	 
   	 
    }

	// ----------------------------------------------------------------------------------------------------------------

    /**
	 * Método para auxiliar para filtrar o tipo de equipamento
	 * @author Wellington 09/10/2021
	 * @version 1.0
	 * @since 1.0	
	 * @param typeColumn - coluna do tipo de equipamento 
	 * @param type - tipo do equipamento
	 * @return cláusula para filtro do tipo de equipamento
	 */	 
	public static String whereClauseType(String typeColumn, String type) {
   	   
   	   return "'"+typeColumn+"' = '"+type+"' ";   	              	 
   	 
    }	
	
	// ----------------------------------------------------------------------------------------------------------------
	
	  /**
		 * Método para auxiliar para filtrar os equipamentos
		 * @author Wellington 11/10/2021
		 * @version 1.0
		 * @since 1.0	
		 * @param equipColumn - coluna dos equipamentos 
		 * @param equips - id dos equipamentos selecionados
		 * @return cláusula para filtro dos equipamentos
		 */	 
		public static String whereClauseEquipments(String equipColumn, String[] equips) {
	   	   
			String query = equipColumn.concat(" IN(");
			
			String aux = "";
	   	 
			for(int i = 0; i < equips.length; i++) {
				
				aux = equips[i];
			
			  if(equips[i] != equips[equips.length-1]) 			  
				    aux += ", ";			  
			  }
						
			query = aux.concat(") ");
									
			return query;   	              	 
	   	 
	    }	
		
		// ----------------------------------------------------------------------------------------------------------------
		
		/**
		 * Método para filtar veículos por classes (CCR) de acordo com seleção
		 * @author Wellington 09/10/2021
		 * @version 1.0
		 * @since 1.0	
		 * @param vehicles - nome do index a ser utilizado	
		 * @return cláusula para filtro de classes
		 */	 
		  public static String vehicleCCRSelectionWhereClause(String[] vehicles) {
		  	   
		  	   String query ="";
		  	             	   
		  	   if(vehicles.length == 1) {
		  		   if(vehicles[0].equals("1"))
		  				query += " AND classe = '"+RoadConcessionaire.classCCRLight+"' ";
		  		   
		  		   if(vehicles[0].equals("2"))
		 				query += " AND classe = '"+RoadConcessionaire.classCCRMotorcycle+"' ";
		  		   
		  		   if(vehicles[0].equals("3"))
		  			    query += " AND classe NOT IN ('"+RoadConcessionaire.classCCRLight+"' , '"+RoadConcessionaire.classCCRMotorcycle+"') ";  		         	  
		  		   
		  	   }	  	   
		 	  		   
		  	  if(vehicles.length == 2) {
		  		  if(vehicles[0].equals("1") && vehicles[1].equals("2"))
		  			   query += " AND classe IN ('"+RoadConcessionaire.classCCRLight+"' , '"+RoadConcessionaire.classCCRMotorcycle+"') ";
				   		   
		  		  if(vehicles[0].equals("1") && vehicles[1].equals("3"))
		  			  query += " AND classe <> '"+RoadConcessionaire.classCCRMotorcycle+"' ";
		  		  
		  		  if(vehicles[0].equals("2") && vehicles[1].equals("3"))
		  			  query += " AND classe <> '"+RoadConcessionaire.classCCRLight+"' ";
		  	  }            			   
		  			   	  	   
		  	   return query;
		  	   
		     }
		  
		// ----------------------------------------------------------------------------------------------------------------
		  
		    /**
			  * Método para filtar veículos por classes de acordo com seleção
			  * @author Wellington 09/10/2021
			  * @version 1.0
			  * @since 1.0	
			  * @param vehicles - nome do index a ser utilizado	
			  * @return cláusula para filtro de classes
			 */	 
		     public static String vehicleSelectionWhereClause(String[] vehicles) {
		    	   
		    	   String query ="";
		    	             	   
		    	   if(vehicles.length == 1) {
		    		   if(vehicles[0].equals("1"))
		    				query += " AND classe = '"+RoadConcessionaire.classLight+"' ";
		    		   
		    		   if(vehicles[0].equals("2"))
		   				query += " AND classe = '"+RoadConcessionaire.classMotorcycle+"' ";
		    		   
		    		   if(vehicles[0].equals("3"))
		    			    query += " AND classe NOT IN ('"+RoadConcessionaire.classLight+"' , '"+RoadConcessionaire.classMotorcycle+"') ";
		  		        
		    		         		    		   
		    	   }
		    		   
		    	  if(vehicles.length == 2) {
		    		  if(vehicles[0].equals("1") && vehicles[1].equals("2"))
		    			   query += " AND classe IN ('"+RoadConcessionaire.classLight+"' , '"+RoadConcessionaire.classMotorcycle+"') ";
		  		   		   
		    		  if(vehicles[0].equals("1") && vehicles[1].equals("3"))
		    			  query += " AND classe <> '"+RoadConcessionaire.classMotorcycle+"' ";
		    		  
		    		  if(vehicles[0].equals("2") && vehicles[1].equals("3"))
		    			  query += " AND classe <> '"+RoadConcessionaire.classLight+"' ";
		    	  }                			   
		    			   
		    	   
		    	   return query;
		       }
			
		// ----------------------------------------------------------------------------------------------------------------
		// GROUP BY
		// ----------------------------------------------------------------------------------------------------------------
		
		 /**
		 * Método para iniciar o agrupamento (GROUP BY)
		 * @author Wellington 11/10/2021
		 * @version 1.0
		 * @since 1.0			
		 * @return o início do agrupamento
		 */	 
		public static String GroupByBegin() {
	   	   	
			return  "GROUP BY ";	              	 
	   	 
	    }	
		
		// ----------------------------------------------------------------------------------------------------------------
		
		 /**
		 * Método para auxiliar para concatenar cláusula ORDER BY
		 * @author Wellington 09/10/2021
		 * @version 1.0
		 * @since 1.0	
		 * @return string ',' para concatenar a cláusula ORDER BY
		 */	
		 public static String groupByConcat() {
	   	   
	   	   return ", ";   	              	 
	   	 
	     }

	   // ----------------------------------------------------------------------------------------------------------------	
   						
		 /**
		 * Método para ordenar por data
		 * @author Wellington 11/10/2021
		 * @version 1.0
		 * @since 1.0	
		 * @param dateColumn - coluna da data 	
		 * @return agrupamento por data
		 */	 
		public static String GroupByDate(String dateColumn) {
	   	   	
			return  "DATE('"+dateColumn+"') ";	              	 
	   	 
	    }	
		
		// ----------------------------------------------------------------------------------------------------------------
				
	   /**
		* Método para ordenar por intervalo
		* @author Wellington 11/10/2021
		* @version 1.0
		* @since 1.0	
		* @param dateColumn - coluna da data
		* @param interval - fórmula intervalo
		* @return agrupamento por intervalo
		*/	 
		public static String GroupByInterval(String dateColumn, String interval) {
			   	   						
		return  "sec_to_time(time_to_sec('"+dateColumn+"')- time_to_sec('"+interval+"')%('"+interval+"')) ";
			              	 			   	 
		}	
		
		// ----------------------------------------------------------------------------------------------------------------
		
		/**
		* Método para agrupar pelo id equipamento
		* @author Wellington 11/10/2021
		* @version 1.0
		* @since 1.0	
		* @param equipColumn - coluna do equipamento	
		* @return agrupamento por equipamento id
		*/	 
		public static String GroupByEquip(String equipIdColumn) {
			   	   						
		return equipIdColumn;
				              	 			   	 
		}	
			
		// ----------------------------------------------------------------------------------------------------------------
		// ----------------------------------------------------------------------------------------------------------------
		// ORDER BY
		// ----------------------------------------------------------------------------------------------------------------
		
		/**
		* Método para iniciar ordenação
		* @author Wellington 11/10/2021
		* @version 1.0
		* @since 1.0
		* @return inicio da ordenação
		*/	 
		public static String OrderByBegin() {
			   	   						
		return  "ORDER BY ";
			              	 			   	 
		}	
					
		// ----------------------------------------------------------------------------------------------------------------
		
		/**
		* Método para ordenar por data e hora
		* @author Wellington 11/10/2021
		* @version 1.0
		* @since 1.0	
		* @param dateColumn - coluna da data	
		* @param orderType - tipo de ordenação ASC ou DESC
		* @return ordenação por data e hora
		*/	 
		public static String OrderByDateTime(String dateColumn, String orderType) {
			   	   						
		return  "'"+dateColumn+"' "+orderType;
			              	 			   	 
		}	
					
		// ----------------------------------------------------------------------------------------------------------------
		
		/**
		* Método para ordenar por data
		* @author Wellington 11/10/2021
		* @version 1.0
		* @since 1.0	
		* @param dateColumn - coluna da data	
		* @param orderType - tipo de ordenação ASC ou DESC
		* @return ordenação por data
		*/	 
		public static String OrderByDate(String dateColumn, String orderType) {
			   	   						
		return  "DATE('"+dateColumn+"') "+orderType;
			              	 			   	 
		}	
					
		// ----------------------------------------------------------------------------------------------------------------
				
		/**
		* Método para ordenar por dia
		* @author Wellington 11/10/2021
		* @version 1.0
		* @since 1.0	
		* @param dateColumn - coluna da data	
		* @param orderType - tipo de ordenação ASC ou DESC
		* @return ordenação por dia
		*/	 
		public static String OrderByDay(String dateColumn, String orderType) {
			   	   						
		return  "DAY('"+dateColumn+"') "+orderType;
			              	 			   	 
		}	
					
		// ----------------------------------------------------------------------------------------------------------------
		
		/**
		 * Método para ordenar por mês
		 * @author Wellington 11/10/2021
		 * @version 1.0
		 * @since 1.0	
		 * @param dateColumn - coluna da data	
		 * @return ordenação por mês
		*/	 
		public static String OrderByMonth(String dateColumn, String orderType) {
			   	   						
		return  "MONTH('"+dateColumn+"') "+orderType;
			              	 			   	 
		}	
					
		// ----------------------------------------------------------------------------------------------------------------
		// ----------------------------------------------------------------------------------------------------------------
		// METHODS
		// ----------------------------------------------------------------------------------------------------------------
				
		/**
		 * Método para obter o cabeçalho de uma query através de um intervalo
		 * @author Wellington 11/10/2021
		 * @version 1.0
		 * @since 1.0
		 * @param dateColumn - Coluna de data
		 * @param interval - intervalo selecionado
		 * @return um cabeçalho para uma query SQL
		 */
		public static String queryHeader(String dateColumn, String interval) {
			
			if(interval.equals("05 minutes"))
				return queryHeaderIntervals(dateColumn, timeFormula05Min, secToTimeAdd05Min); 

			if(interval.equals("06 minutes"))
				return queryHeaderIntervals(dateColumn, timeFormula06Min, secToTimeAdd06Min); 

			if(interval.equals("10 minutes"))
				return queryHeaderIntervals(dateColumn, timeFormula10Min, secToTimeAdd10Min); 

			if(interval.equals("15 minutes"))
				return queryHeaderIntervals(dateColumn, timeFormula15Min, secToTimeAdd15Min); 

			if(interval.equals("30 minutes"))
				return queryHeaderIntervals(dateColumn, timeFormula30Min, secToTimeAdd30Min); 

			if(interval.equals("01 hour"))
				return queryHeaderIntervals(dateColumn, timeFormula01Hour, secToTimeAdd01Hour); 

			if(interval.equals("06 hours"))
				return queryHeaderIntervals(dateColumn, timeFormula06Hours, secToTimeAdd06Hours); 

			if(interval.equals("24 hours"))
				return queryHeader01Day(dateColumn); 	

			if(interval.equals("month"))
				return queryHeaderMonth(dateColumn); 	

			if(interval.equals("year"))
				return queryHeaderYear(dateColumn); 									 	

			return null;
			
		}
		
		// ----------------------------------------------------------------------------------------------------------------
		// ----------------------------------------------------------------------------------------------------------------
		
		
}
