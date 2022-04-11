package br.com.tracevia.webapp.methods;

import br.com.tracevia.webapp.util.SQLReportTemplate;

/**
 * Classe modelo para criação de queries modularizadas para usar em diversos relatórios
 * @version 1.0
 * @since 1.0
 * @author Wellington 13/10/21
 * 
 */

public class SQLModel {
		
	/**
	 * Método exclusivo para ser utilizado no relatório SPEED RECORDS
	 * @author Wellington 13/10/21
	 * @version 1.0
	 * @since 1.0
	 * @param dateColumn - coluna de data da tabela do banco de dados	
	 * @param queryBody - corpo principal da query
	 * @param table - nome da tabela a ser utilizada
	 * @param indexName - nome do index a ser utilizado
	 * @param module - nome do módulo para compor a tabela de equipamento no JOIN
	 * @param innerColumnA - nome da coluna para compor o campo da tabela A do JOIN
	 * @param innerColumnB - nome da coluna para compor o campo da tabela B do JOIN
	 * @param startDateTime - data inicial a ser pesquisada na cláusula WHERE
	 * @param endDateTime - data final a ser pesquisada na cláusula WHERE
	 * @param equipColumn - coluna do id do equipamento na tabela do banco de dados
	 * @param equips - matriz contendo ids dos equipamentos
	 * @return uma query SQL a ser executada
	 */
	public String BuildSpeedQuery(String dateColumn, String queryBody, String table, String indexName,String startDateTime, String endDateTime, String equipColumn, String equip) { 	   						 	 
		
		return SQLReportTemplate.queryHeaderDate(dateColumn).concat(SQLReportTemplate.queryHeaderTime(dateColumn)).concat(queryBody).concat(SQLReportTemplate.fromTable(table))
				.concat(SQLReportTemplate.useIndex(indexName)).concat(SQLReportTemplate.whereClauseBegin())
				.concat(SQLReportTemplate.whereClauseDateBetween(dateColumn, startDateTime, endDateTime))
				.concat(SQLReportTemplate.whereClauseEquip(equipColumn, equip));			
					
	 }
	
	// ----------------------------------------------------------------------------------------------------------------	
	

}
