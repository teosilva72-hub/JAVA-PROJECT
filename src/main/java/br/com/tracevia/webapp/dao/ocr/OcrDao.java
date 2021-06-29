package br.com.tracevia.webapp.dao.ocr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.model.ocr.OcrData;
import br.com.tracevia.webapp.util.ConnectionFactory;

public class OcrDao{
	private Connection conn;
	private PreparedStatement ps, ps1;
	private ResultSet rs, rs1;
	OcrData data = new OcrData();

	public ArrayList<OcrData> searchTable(String start, String end, String classe) throws Exception {
		String search = "SELECT * FROM ocr_data WHERE datetime BETWEEN'"+ start +"'AND'"+ end +"'AND site_name ='"+ classe+"'";
		String search1 = "SELECT * FROM ocr_data WHERE datetime BETWEEN'"+ start +"'AND'"+ end+"'";
		ArrayList<OcrData> list = new ArrayList<OcrData>();
		try {
			conn = ConnectionFactory.connectToTraceviaApp();
			ps = conn.prepareStatement(search);
			rs = ps.executeQuery();
			if (rs.isBeforeFirst()) {
				while (rs.next()) {
					System.out.println("tem classe");
					OcrData data = new OcrData();			
					data.setId(rs.getString(1));
					data.setDataHour(rs.getString(2));
					data.setCam(rs.getString(3));
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
	public OcrData searchCam(String cam) throws Exception {
		OcrData search = new OcrData();
		//Script dos atributos que as infor��es ser�o requisitadas
		String query = "SELECT * FROM ocr_data WHERE site_name ='"+cam+"'ORDER BY id_ocr_data desc limit 1";
		DateTimeApplication dtm = new DateTimeApplication();
		try {
			conn = ConnectionFactory.connectToTraceviaApp();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			if(rs != null) {
				while(rs.next()) {
					search.setId(rs.getString(1));
					search.setCam(rs.getString(2));
					search.setDataHour(rs.getString(3));
					search.setPlaca(rs.getString(4));
				}
			}
		}catch(SQLException sqlbuscar) {
			sqlbuscar.printStackTrace();

		}finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}
		//passando os valores dos atributos para a vari�vel occ
		return search;

	}
	public OcrData lastRegister() throws Exception {
		OcrData search = new OcrData();
		//Script dos atributos que as infor��es ser�o requisitadas
		String query = "SELECT * FROM ocr_data ORDER BY id_ocr_data desc limit 1";
		DateTimeApplication dtm = new DateTimeApplication();
		try {
			conn = ConnectionFactory.connectToTraceviaApp();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			if(rs != null) {
				while(rs.next()) {
					search.setId(rs.getString(1));
					search.setCam(rs.getString(2));
					search.setDataHour(rs.getString(3));
					search.setPlaca(rs.getString(4));
				}
			}
		}catch(SQLException sqlbuscar) {
			sqlbuscar.printStackTrace();

		}finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}
		//passando os valores dos atributos para a vari�vel occ
		return search;

	}
}