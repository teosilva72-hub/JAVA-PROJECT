package br.com.tracevia.webapp.dao.ocr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.model.ocr.OCR;
import br.com.tracevia.webapp.model.wim.WimData;
import br.com.tracevia.webapp.util.ConnectionFactory;

public class reportDAO {
	private Connection conn;
	private PreparedStatement ps, ps1;
	private ResultSet rs, rs1;
	OCR data = new OCR();
	
	public ArrayList<OCR> searchTable(String start, String end, String classe) throws Exception {
		
		String search = "SELECT * FROM ocr_data WHERE datetime BETWEEN'"+ start +"'AND'"+ end +"'AND site_name ='"+ classe+"'";
		String search1 = "SELECT * FROM ocr_data WHERE datetime BETWEEN'"+ start +"'AND'"+ end+"'";
		
		ArrayList<OCR> list = new ArrayList<OCR>();
		
		try {
			
			conn = ConnectionFactory.connectToTraceviaApp();
			
			ps = conn.prepareStatement(search);
			rs = ps.executeQuery();
			ps1 = conn.prepareStatement(search1);
			rs1 = ps1.executeQuery();
			
			System.out.println(search);
			
			if (rs.isBeforeFirst()) {
				while (rs.next()) {
					
					System.out.println("tem classe");
					
					OCR data = new OCR();	
					
					data.setId(rs.getString(1));
					data.setCam(rs.getString(2));
					data.setDataHour(rs.getString(3));
					
					data.setPlaca(rs.getString(4));
					
					list.add(data);
				}				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;

	}
	public ArrayList<OCR> searchTable2(String start, String end) throws Exception {
		
		String search = "SELECT * FROM ocr_data WHERE datetime BETWEEN'"+ start +"'AND'"+ end +"'";
		ArrayList<OCR> list = new ArrayList<OCR>();
		
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
					
					list.add(data);
				}				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;

	}
	public OCR searchId(int id) throws Exception {
		
		OCR data = new OCR();
		
		String search = "SELECT * FROM ocr_data WHERE id_ocr_data ='"+ id +"'";
		DateTimeApplication dtm = new DateTimeApplication();
		
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
				}				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;

	}
}