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

public class OCRDAO{
	
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;
	
	private String ftpFolder;
	
	public OCRDAO() {
		
		ftpFolder = "C:\\Cameras\\OCR\\NormalType\\";
		
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
					
					System.out.println(search.getVehicleImage());
										
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
	
   public OCR GetOCRdetail(String cam) throws Exception {
		
	     OCR ocr = new OCR();
		
		//Script dos atributos que as infor��es ser�o requisitadas
		
		String query = "SELECT * FROM ocr_data WHERE site_name = ? ORDER BY id_ocr_data desc limit 1";
		
		try {
			
			conn = ConnectionFactory.connectToTraceviaApp();
			ps = conn.prepareStatement(query);
			ps.setString(1, cam);
			
			rs = ps.executeQuery();
			
			if(rs != null) {
				while(rs.next()) {
					
					ocr.setId(rs.getString(1));
					ocr.setCam(rs.getString(2));
					ocr.setDataHour(rs.getString(3));
					ocr.setPlaca(rs.getString(4));
					ocr.setVehicleImage(findVehicleImage(ocr));
					ocr.setPlateImage(findPlateImage(ocr));
										
				}
			}
			
		}catch(SQLException sqlbuscar) {
			sqlbuscar.printStackTrace();

		}finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}
		//passando os valores dos atributos para a vari�vel occ
		return ocr;

	}
   
   
   public List<OCR> Status() throws Exception {
		
		
		List<OCR> list = new ArrayList<OCR>();		
						
		String select = "SELECT equip_id, equip_name, equip_last_status, equip_status FROM connection_monitor WHERE equip_type = 'OCR' ";
									
	  try {
			
		   conn = ConnectionFactory.useConnection(RoadConcessionaire.roadConcessionaire);
			
			ps = conn.prepareStatement(select);			
									
			rs = ps.executeQuery();
			
			if (rs != null) {
				while (rs.next()) {
					
					OCR ocr = new OCR();

			         ocr.setEquip_id(rs.getInt("equip_id"));
			         ocr.setNome(rs.getString("equip_name"));
			         ocr.setStatus(rs.getInt("equip_status"));			      
			        															
					list.add(ocr);
				}				
			 }			

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {ConnectionFactory.closeConnection(conn, ps, rs);}
 				
		return list;
		
	}

}