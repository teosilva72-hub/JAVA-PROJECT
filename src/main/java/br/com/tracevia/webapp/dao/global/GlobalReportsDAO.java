package br.com.tracevia.webapp.dao.global;

import br.com.tracevia.webapp.model.global.ColumnsSql.RowResult;
import br.com.tracevia.webapp.model.global.ResultSql.MapResult;
import br.com.tracevia.webapp.model.global.SQL_Tracevia;

public class GlobalReportsDAO {

	SQL_Tracevia conn = new SQL_Tracevia();
	
	/**
	 * @author Wellington 28/08/2020
	 */

	private String[][] result;

	/* ************************** */
	/* ***** GLOBAL REPORTS ***** */
	/* ************************** */

	/**
	 * @author Wellington Silva -- updated: 24/02/2021
	 * @version: 1.1
	 * @since 1.0
	 * @param query        - consulta SQL
	 * @param fieldsNumber - n�mero de campos de acordo com a sa�da da query
	 * @param registers    - n�mero de registros
	 * @return array contendo dados do relat�rio
	 * @throws Exception
	 */

	public String[][] ExecuteQuery(String query, int registers, int fieldsNumber) throws Exception {

		// NECESSITA DA CONSTRU��O DE UMA MATRIZ PARA RECEBER OS DADOS DE SA�DA DA QUERY
		// FIELDSNUMBER S�O O NUMERO DE CAMPOS DE ACORDO COM A QUERY A SER EXECUTADA ->
		// RECEBE ESSE PAR�METRO DO BEAN
		/*
		 * REGISTERS S�O O N�MERO DE REGISTROS QUE A MATRIZ DEVER� POSSUIR. SUA
		 * CONSTRU��O SE DEVE NA ESCOLHA DE DATA DE �NICIO, DATA DE FIM,
		 * M�S, ANOS E ETC.
		 */
		result = new String[registers][fieldsNumber];

		try {
			conn.start(1);
			// GET CONNECTION

			// EXECUTE QUERY
			conn.prepare(query);
			MapResult resultMap = conn.executeQuery();

			int lin = 0;

			// RESULT IN RESULTSET
			if (result != null) {
				for (RowResult rs : resultMap) { // Linhas

					for (int col = 0; col < fieldsNumber; col++) { // Colunas

						result[lin][col] = rs.getString((col + 1));

						// System.out.println("LIN["+lin+"]COL["+col+"] = "+result[lin][col] ); //
						// DEBBUGER
					}

					lin++;

				}

			} else
				result = new String[0][0]; // CASO N�O EXISTA REGISTROS REDEFINE TAMANHO DO ARRAY

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}

		return result;

	}

	/* ************************** */
	/* ***** GLOBAL REPORTS ***** */
	/* ************************** */

}
