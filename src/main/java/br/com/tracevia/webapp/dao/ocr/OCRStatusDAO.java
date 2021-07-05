package br.com.tracevia.webapp.dao.ocr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.model.ocr.OCR;
import br.com.tracevia.webapp.util.ConnectionFactory;

public class OCRStatusDAO {
	
	private Connection conn;		
	protected ConnectionFactory connection = new ConnectionFactory();
	private PreparedStatement ps;
	private ResultSet rs;
		
	public List<OCR> ocrStatus() throws Exception {
			
		
		List<OCR> list = new ArrayList<OCR>();		
						
		String select = "SELECT cctv_id, cctv_name, cctv_status FROM cctv_monitor WHERE cctv_type = 'OCR' ";
				
					
	/*  try {
			
		   conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
			
			ps = conn.prepareStatement(select);			
									
			rs = ps.executeQuery();
			
			//System.out.println("DATA LIST 30 SQL: "+select);
			
			if (rs != null) {
				while (rs.next()) {
					
					OCR ocr = new OCR();

			         ocr.setEquip_id(rs.getInt("cctv_id"));
			         ocr.setNome(rs.getString("cctv_name"));
			         ocr.setStatus(rs.getInt("cctv_status"));
			        															
					list.add(ocr);
				}				
			 }			

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {ConnectionFactory.closeConnection(conn, ps, rs);}
*/
				
		return list;
		
	}

}
