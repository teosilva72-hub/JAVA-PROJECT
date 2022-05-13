package br.com.tracevia.webapp.dao.ocr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.tracevia.webapp.methods.TranslationMethods;
import br.com.tracevia.webapp.model.ocr.OCR;
import br.com.tracevia.webapp.util.ConnectionFactory;

public class reportDAO {
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;
	OCR data = new OCR();

	public ArrayList<OCR> searchTable(String start, String classe, String plate, String x, String end) throws Exception {
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
		
		System.out.println(query);
		 ArrayList<OCR> list = new ArrayList<OCR>();
		 TranslationMethods tr = new TranslationMethods();

		try {
			
			conn = ConnectionFactory.connectToTraceviaApp();
			
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
					
			//System.out.println(search);
			
			if (rs.isBeforeFirst()) {
				while (rs.next()) {
										
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
			
			conn = ConnectionFactory.connectToTraceviaApp();			
			ps = conn.prepareStatement(search);
			rs = ps.executeQuery();
			
			if (rs.isBeforeFirst()) {
				while (rs.next()) {
					
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
			conn = ConnectionFactory.connectToTraceviaApp();
			ps = conn.prepareStatement(search);
			rs = ps.executeQuery();
			
			if (rs.isBeforeFirst()) {
				while (rs.next()) {
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
		}

		return data;

	}
}
