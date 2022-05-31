package br.com.tracevia.webapp.dao.cftv;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;

import br.com.tracevia.webapp.model.global.ColumnsSql.RowResult;
import br.com.tracevia.webapp.model.global.Equipments;
import br.com.tracevia.webapp.model.global.ResultSql.MapResult;
import br.com.tracevia.webapp.model.global.SQL_Tracevia;
import br.com.tracevia.webapp.util.LogUtils;

public class CFTVDAO {

	SQL_Tracevia conn;
	
	// --------------------------------------------------------------------------------------------
	
	private static String classLocation = CFTVDAO.class.getCanonicalName(); // CLASS	
	private static String classErrorPath = LogUtils.ERROR.concat("cftv-dao\\");	 // CLASS LOG FOLDER
	private static String cftvDAOExceptionLog = classErrorPath.concat("cftvdao_exception_"); // CLASS LOG FILE NAME
	
	// --------------------------------------------------------------------------------------------
		
	public CFTVDAO() {
		
	 conn = new SQL_Tracevia();
	 
	 LogUtils.createLogFolder(classErrorPath);		
		
	}
	
	// --------------------------------------------------------------------------------------------
		
	public Equipments getTotalId() throws Exception {
		
		Equipments list = new Equipments();	
		
		String query = "SELECT MAX(equip_id) FROM cftv_equipment";
		
		try {
			
			conn.start(1);
			conn.prepare(query);
			
			MapResult result = conn.executeQuery();
			
			if (result.hasNext()) {
				for (RowResult rs : result) {
					
					list.setEquip_id(rs.getInt(1));
					
				}				
			}
			
		} catch (SQLException e) {
			
			 StringWriter errors = new StringWriter(); 
			 e.printStackTrace(new PrintWriter(errors));	

			 LogUtils.logError(LogUtils.fileDateTimeFormatter(cftvDAOExceptionLog),  classLocation, e.getMessage(), errors.toString());
			
		}finally {
			
			conn.close();
		}
		
		return list;
	}
	
	// -------------------------------------------------------------------
}