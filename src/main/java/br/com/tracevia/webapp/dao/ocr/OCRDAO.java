package br.com.tracevia.webapp.dao.ocr;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.tracevia.webapp.model.ocr.OCR;
import br.com.tracevia.webapp.util.ConnectionFactory;

public class OCRDAO{
	
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;
	
	private String ftpFolder;
	
	public OCRDAO() {
		
		ftpFolder = "C:\\Cameras\\OCR\\NormalType\\"; ;
		
	}

	public ArrayList<OCR> searchTable(String start, String end, String classe) throws Exception {
		
		String search = "SELECT * FROM ocr_data WHERE datetime BETWEEN'"+ start +"'AND'"+ end +"'AND site_name ='"+ classe+"'";
		
		String search1 = "SELECT * FROM ocr_data WHERE datetime BETWEEN'"+ start +"'AND'"+ end+"'";
		
		ArrayList<OCR> list = new ArrayList<OCR>();
		
		try {
			
			conn = ConnectionFactory.connectToTraceviaApp();
			
			ps = conn.prepareStatement(search);
			
			rs = ps.executeQuery();
			
			if (rs.isBeforeFirst()) {
				while (rs.next()) {									
				
					OCR data = new OCR();
					
					data.setId(rs.getString(1));
					data.setDataHour(rs.getString(2));
					data.setCam(rs.getString(3));
					data.setPlaca(rs.getString(4));
					data.setVehicleImage(findVehicleImage(data));
					data.setPlateImage(findPlateImage(data));
					
					list.add(data);
				}				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;

	}
	
	public OCR searchCam(String cam) throws Exception {
		
		OCR search = new OCR();
		
		//Script dos atributos que as infor��es ser�o requisitadas
		String query = "SELECT * FROM ocr_data WHERE site_name ='"+cam+"'ORDER BY id_ocr_data desc limit 1";
			
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
					search.setVehicleImage(findVehicleImage(search));
					search.setPlateImage(findPlateImage(search));
					
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
	public OCR lastRegister() throws Exception {
		
		OCR search = new OCR();
		
		//Script dos atributos que as infor��es ser�o requisitadas
		String query = "SELECT * FROM ocr_data ORDER BY id_ocr_data desc limit 1";
				
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
					search.setVehicleImage(findVehicleImage(search));
					search.setPlateImage(findPlateImage(search));
										
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
	
	public String findVehicleImage(OCR data) {
						
		String dateVeh = formataDados(data.getDataHour());
		String subFolder = dateVeh.substring(0, 8);		
		String nameVeh = data.getCam().replaceAll(" ", "_");
				
		return ftpFolder+""+nameVeh+"\\"+subFolder+"\\"+nameVeh+"_"+dateVeh+"_"+data.getPlaca()+".jpg";					
		
	}
	
	public String findPlateImage(OCR data) {
						
		String dateVeh = formataDados(data.getDataHour());
		String subFolder = dateVeh.substring(0, 8);		
		String nameVeh = data.getCam().replaceAll(" ", "_");		
				
		return ftpFolder+""+nameVeh+"\\"+subFolder+"\\Plate"+nameVeh+"_"+dateVeh+"_"+data.getPlaca()+".jpg";					
		
	}
	
	public static String formataDados(String dado){
		
		   dado = dado.replaceAll("\\.","");
		   dado = dado.replaceAll("-", "");
		   dado = dado.replaceAll(":", "");
		   dado = dado.replaceAll(" ", "");
		   
		   return dado;
		}
	//////////////////////////////////////////////////
	//////////OCR DETAILS////////////////////////////
public OCR OCR1() throws Exception {
		OCR search = new OCR();
		//Script dos atributos que as infor��es ser�o requisitadas
		String query = "SELECT * FROM ocr_data WHERE site_name= 'ocr_01' ORDER BY id_ocr_data desc limit 1";
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
					search.setVehicleImage(findVehicleImage(search));
					search.setPlateImage(findPlateImage(search));
										
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
public OCR OCR2() throws Exception {
	OCR search = new OCR();
	//Script dos atributos que as infor��es ser�o requisitadas
	String query = "SELECT * FROM ocr_data WHERE site_name= 'ocr_02' ORDER BY id_ocr_data desc limit 1";
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
				search.setVehicleImage(findVehicleImage(search));
				search.setPlateImage(findPlateImage(search));
									
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
public OCR OCR3() throws Exception {
	OCR search = new OCR();
	//Script dos atributos que as infor��es ser�o requisitadas
	String query = "SELECT * FROM ocr_data WHERE site_name= 'ocr_03' ORDER BY id_ocr_data desc limit 1";
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
				search.setVehicleImage(findVehicleImage(search));
				search.setPlateImage(findPlateImage(search));
									
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
public OCR OCR4() throws Exception {
	OCR search = new OCR();
	//Script dos atributos que as infor��es ser�o requisitadas
	String query = "SELECT * FROM ocr_data WHERE site_name= 'ocr_04' ORDER BY id_ocr_data desc limit 1";
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
				search.setVehicleImage(findVehicleImage(search));
				search.setPlateImage(findPlateImage(search));
									
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
public OCR OCR5() throws Exception {
	OCR search = new OCR();
	//Script dos atributos que as infor��es ser�o requisitadas
	String query = "SELECT * FROM ocr_data WHERE site_name= 'ocr_05' ORDER BY id_ocr_data desc limit 1";
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
				search.setVehicleImage(findVehicleImage(search));
				search.setPlateImage(findPlateImage(search));
									
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
public OCR OCR6() throws Exception {
	OCR search = new OCR();
	//Script dos atributos que as infor��es ser�o requisitadas
	String query = "SELECT * FROM ocr_data WHERE site_name= 'ocr_06' ORDER BY id_ocr_data desc limit 1";
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
				search.setVehicleImage(findVehicleImage(search));
				search.setPlateImage(findPlateImage(search));
									
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
public OCR OCR7() throws Exception {
	OCR search = new OCR();
	//Script dos atributos que as infor��es ser�o requisitadas
	String query = "SELECT * FROM ocr_data WHERE site_name= 'ocr_07' ORDER BY id_ocr_data desc limit 1";
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
				search.setVehicleImage(findVehicleImage(search));
				search.setPlateImage(findPlateImage(search));
									
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
public OCR OCR8() throws Exception {
	OCR search = new OCR();
	//Script dos atributos que as infor��es ser�o requisitadas
	String query = "SELECT * FROM ocr_data WHERE site_name= 'ocr_08' ORDER BY id_ocr_data desc limit 1";
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
				search.setVehicleImage(findVehicleImage(search));
				search.setPlateImage(findPlateImage(search));
									
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
public OCR OCR9() throws Exception {
	OCR search = new OCR();
	//Script dos atributos que as infor��es ser�o requisitadas
	String query = "SELECT * FROM ocr_data WHERE site_name= 'ocr_09' ORDER BY id_ocr_data desc limit 1";
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
				search.setVehicleImage(findVehicleImage(search));
				search.setPlateImage(findPlateImage(search));
									
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
public OCR OCR10() throws Exception {
	OCR search = new OCR();
	//Script dos atributos que as infor��es ser�o requisitadas
	String query = "SELECT * FROM ocr_data WHERE site_name= 'ocr_10' ORDER BY id_ocr_data desc limit 1";
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
				search.setVehicleImage(findVehicleImage(search));
				search.setPlateImage(findPlateImage(search));
									
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