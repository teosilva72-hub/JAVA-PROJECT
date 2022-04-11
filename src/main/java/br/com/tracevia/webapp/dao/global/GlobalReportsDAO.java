package br.com.tracevia.webapp.dao.global;

import java.util.ArrayList;

import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.model.global.ColumnsSql.RowResult;
import br.com.tracevia.webapp.model.global.ResultSql.MapResult;
import br.com.tracevia.webapp.model.global.SQL_Tracevia;
import br.com.tracevia.webapp.model.global.VBV;

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

	/* **************************** */
	/* ***** VBVs METHOD ONLY ***** */
	/* **************************** */

	public Integer GetVBVsRegisterNumbers(String query, String equip, String startDate, String endDate)
			throws Exception {

		DateTimeApplication dta = new DateTimeApplication();

		int registers = 0;

		String startDateFormatted = dta.StringDBDateFormat(startDate);
		String endDateFormatted = dta.StringDBDateFormat(endDate);

		try {
			conn.start(1);

			// CHECK PROCEDURE EXECUTION FIRST
			conn.prepare(query);
			conn.setString(1, startDateFormatted);
			conn.setString(2, endDateFormatted);
			conn.setString(3, equip);
			MapResult resultMap = conn.executeQuery();

			if (resultMap != null) {
				for (RowResult rs : resultMap) {

					registers = rs.getInt(1);

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}

		return registers;

	}

	public ArrayList<VBV> ExecuteQueryVBVs(String query, String equip, String startDate, String endDate)
			throws Exception {

		DateTimeApplication dta = new DateTimeApplication();

		ArrayList<VBV> lista = new ArrayList<VBV>();

		String startDateFormatted = dta.StringDBDateFormat(startDate);
		String endDateFormatted = dta.StringDBDateFormat(endDate);

		try {
			conn.start(1);
			// EXECUTE QUERY
			conn.prepare(query);
			conn.setString(1, startDateFormatted);
			conn.setString(2, endDateFormatted);
			conn.setString(3, equip);
			MapResult resultMap = conn.executeQuery();

			// System.out.println("QUERY: "+query);
			// RESULT IN RESULTSET

			if (resultMap != null) {
				for (RowResult rs : resultMap) {

					VBV vbv = new VBV();

					vbv.setSiteID(rs.getInt("siteID"));

					vbv.setSeqG(rs.getInt("seqG"));
					vbv.setSeqN(rs.getInt("seqN"));

					vbv.setDatetime(rs.getString("datetime_"));

					String classe = rs.getString("classe");
					if (classe == null)
						vbv.setClassVBV("UC");
					else
						vbv.setClassVBV(classe);

					int axle = rs.getInt("axlNumber");
					if (axle == 0 || axle == 1)
						vbv.setAxlNumber(2); // Caso 0 ou 1 recebe 2
					else
						vbv.setAxlNumber(axle);

					vbv.setAxl1W(rs.getInt("axl1W"));
					vbv.setAxl2W(rs.getInt("axl2W"));
					vbv.setAxl3W(rs.getInt("axl3W"));
					vbv.setAxl4W(rs.getInt("axl4W"));
					vbv.setAxl5W(rs.getInt("axl5W"));
					vbv.setAxl6W(rs.getInt("axl6W"));
					vbv.setAxl7W(rs.getInt("axl7W"));
					vbv.setAxl8W(rs.getInt("axl8W"));
					vbv.setAxl9W(rs.getInt("axl9W"));

					vbv.setAxl2D(rs.getInt("axl2D"));
					vbv.setAxl3D(rs.getInt("axl3D"));
					vbv.setAxl4D(rs.getInt("axl4D"));
					vbv.setAxl5D(rs.getInt("axl5D"));
					vbv.setAxl6D(rs.getInt("axl6D"));
					vbv.setAxl7D(rs.getInt("axl7D"));
					vbv.setAxl8D(rs.getInt("axl8D"));
					vbv.setAxl9D(rs.getInt("axl9D"));

					vbv.setGross(rs.getInt("gross"));
					vbv.setTemperature(rs.getInt("temperature"));
					vbv.setSpeed(rs.getInt("speed"));
					vbv.setLane(rs.getInt("lane"));

					lista.add(vbv); // adiciona na Lista

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}

		return lista;

	}

	/* **************************** */
	/* ***** VBVs METHOD ONLY ***** */
	/* **************************** */

}
