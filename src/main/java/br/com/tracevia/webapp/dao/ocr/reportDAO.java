package br.com.tracevia.webapp.dao.ocr;

import java.sql.SQLException;
import java.util.ArrayList;

import br.com.tracevia.webapp.methods.TranslationMethods;
import br.com.tracevia.webapp.model.global.ColumnsSql.RowResult;
import br.com.tracevia.webapp.model.global.ResultSql.MapResult;
import br.com.tracevia.webapp.model.global.SQL_Tracevia;
import br.com.tracevia.webapp.model.ocr.OCR;

public class reportDAO {

	SQL_Tracevia conn;
	
	public reportDAO() {
				
		 conn = new SQL_Tracevia();
	}

	public ArrayList<OCR> searchTable(String start, String end, String classe, String plate, String x) throws Exception {
		
		String query = "";
		
		if(x.equals("0") || x.equals("1")) {
			query = "SELECT ocr.id_ocr_data, ocr.site_name, " +	 		
			      "ocr.datetime, ocr.plate, eq.km, " + 			
			      "eq.direction FROM ocr_data ocr " +
		          "INNER JOIN ocr_equipment eq " +			
				  "ON eq.name = ocr.site_name AND ocr.datetime " +
				  "WHERE datetime BETWEEN'"+ start +"'AND'"+ end +"'AND site_name ='"+ classe+"' AND plate !='"+plate+"'";
		}
		if(x.equals("2")) {
			query = "SELECT ocr.id_ocr_data, ocr.site_name, " +	 		
				      "ocr.datetime, ocr.plate, eq.km, " + 			
				      "eq.direction FROM ocr_data ocr " +
			          "INNER JOIN ocr_equipment eq " +			
					  "ON eq.name = ocr.site_name AND ocr.datetime " +
					  "WHERE datetime BETWEEN'"+ start +"'AND'"+ end +"'AND site_name ='"+ classe+"' AND plate ='"+plate+"'";
		}
		
	
		 ArrayList<OCR> list = new ArrayList<OCR>();
		 TranslationMethods tr = new TranslationMethods();

		try {
			
			conn.start(1);
			
			conn.prepare(query);
			
			MapResult result = conn.executeQuery();
			
			if (result.hasNext()) {
				for (RowResult rs : result) {	
														
					OCR data = new OCR();	
					
					data.setId(rs.getString(1));
					data.setCam(rs.getString(2));
					data.setDataHour(rs.getString(3));					
					data.setPlaca(rs.getString(4));
					data.setKm(rs.getString(5));
					data.setDirection(tr.translateDirections(rs.getString(6)));
					
					list.add(data);
				}				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}finally {
			
			conn.close();
		}

		return list;

	}
	public ArrayList<OCR> searchTable2(String start, String end, String plate, String x) throws Exception {
		String search = "";
		if(x.equals("0") || x.equals("1")) {
			search = "SELECT ocr.id_ocr_data, ocr.site_name, " +	 		
					"ocr.datetime, ocr.plate, eq.km, " + 			
					"eq.direction FROM ocr_data ocr " +
					"INNER JOIN ocr_equipment eq " +			
					"ON eq.name = ocr.site_name AND ocr.datetime " +				
					"BETWEEN '"+start+"' AND '" +end+"' AND plate !='"+plate+"'";
		}
		if(x.equals("2")) {
			search = "SELECT ocr.id_ocr_data, ocr.site_name, " +	 		
					"ocr.datetime, ocr.plate, eq.km, " + 			
					"eq.direction FROM ocr_data ocr " +
					"INNER JOIN ocr_equipment eq " +			
					"ON eq.name = ocr.site_name AND ocr.datetime " +				
					"BETWEEN '"+start+"' AND '" +end+"' AND plate ='"+plate+"'";
		}
		ArrayList<OCR> list = new ArrayList<OCR>();
		 TranslationMethods tr = new TranslationMethods();
		
		try {
			
			conn.start(1);
			
			conn.prepare(search);
			
			MapResult result = conn.executeQuery();
			
			if (result.hasNext()) {
				for (RowResult rs : result) {	
																				
					OCR data = new OCR();	
					
					data.setId(rs.getString(1));
					data.setCam(rs.getString(2));
					data.setDataHour(rs.getString(3));
					data.setPlaca(rs.getString(4));
					data.setKm(rs.getString(5));
					data.setDirection(tr.translateDirections(rs.getString(6)));
					
					list.add(data);
				}				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
			conn.close();
		}

		return list;

	}
	public OCR searchId(int id, String cam) throws Exception {
		
		OCR data = new OCR();
		
		String search = "SELECT ocr.id_ocr_data, ocr.site_name, "
				+ "ocr.datetime, ocr.plate, "
				+"eq.km, eq.direction "
				+"FROM ocr_data ocr "
				+"INNER JOIN ocr_equipment eq ON eq.name = ocr.site_name "
				+"WHERE ocr.id_ocr_data ='"+id+"'";
				
		 TranslationMethods tr = new TranslationMethods();
		
		try {
			
			conn.start(1);
			
			conn.prepare(search);
			
			MapResult result = conn.executeQuery();
			
			if (result.hasNext()) {
				for (RowResult rs : result) {	
					data.setId(rs.getString(1));
					data.setCam(rs.getString(2));
					data.setDataHour(rs.getString(3));
					data.setPlaca(rs.getString(4));
					data.setKm(rs.getString(5));
					data.setDirection(tr.translateDirections(rs.getString(6)));
				}				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
			conn.close();
		}

		return data;

	}
}
