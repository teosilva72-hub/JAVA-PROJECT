package br.com.tracevia.webapp.dao.ocr;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.tracevia.webapp.model.global.SQL_Tracevia;
import br.com.tracevia.webapp.model.global.ColumnsSql.RowResult;
import br.com.tracevia.webapp.model.global.ResultSql.MapResult;
import br.com.tracevia.webapp.model.ocr.OCR;

public class OCRDAO{
	
	SQL_Tracevia conn = new SQL_Tracevia();
	
	private String ftpFolder, noImageFolder;
	
	public OCRDAO() {
		
		ftpFolder = "C:\\Cameras\\OCR_Types\\";
		
		noImageFolder = "C:\\Tracevia\\Software\\External\\Unknown\\";
				
	}

	public ArrayList<OCR> searchTable(String start, String end, String ocr_site_name) throws Exception {
		
		String search = "SELECT * FROM ocr_data WHERE datetime BETWEEN'"+ start +"'AND'"+ end +"'AND site_name ='"+ ocr_site_name+"'";
		
		//String search1 = "SELECT * FROM ocr_data WHERE datetime BETWEEN'"+ start +"'AND'"+ end+"'";
		
		ArrayList<OCR> list = new ArrayList<OCR>();
		
		try {
			
			conn.start(1);
			
			conn.prepare(search);
			
			MapResult result = conn.executeQuery();
			
			if (result.hasNext()) {
				for (RowResult rs : result) {									
				
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
			conn.start(1);
			conn.prepare(query);
			MapResult result = conn.executeQuery();
			if(result.hasNext()) {
				for (RowResult rs : result) {
					
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
			conn.close();
		}
		
		//passando os valores dos atributos para a vari�vel occ
		return search;

	}
	public OCR lastRegister() throws Exception {
		
		OCR search = new OCR();
		
		//Script dos atributos que as infor��es ser�o requisitadas
		String query = "SELECT * FROM ocr_data ORDER BY id_ocr_data desc limit 1";
				
		try {
			
			conn.start(1);
			conn.prepare(query);
			MapResult result = conn.executeQuery();
			
			if(result.hasNext()) {
				for (RowResult rs : result) {
					
					search.setId(rs.getString(1));
					search.setCam(rs.getString(2));
					search.setDataHour(rs.getString(3));
					search.setPlaca(rs.getString(4));
					search.setVehicleImage(findVehicleImage(search));
					search.setPlateImage(findPlateImage(search));
					
					//System.out.println(search.getVehicleImage());
										
				}
			}
			
		}catch(SQLException sqlbuscar) {
			sqlbuscar.printStackTrace();

		}finally {
			conn.close();
		}
		//passando os valores dos atributos para a vari�vel occ
		return search;

	}
	
	public String findVehicleImage(OCR data) {
		
		String path = "";
						
		String dateVeh = formataDados(data.getDataHour());
		String subFolder = dateVeh.substring(0, 8);		
		String nameVeh = data.getCam().replaceAll(" ", "_");
				
		if(data.getPlaca().equals("XXXXXXX") || data.getPlaca().equals(""))
			 path = noImageFolder.concat("no-image.jpg");
			// path =  ftpFolder+""+nameVeh+"\\"+subFolder+"\\Plate"+nameVeh+"_"+dateVeh+"_.jpg";
		
		else path = ftpFolder+""+nameVeh+"\\"+subFolder+"\\"+nameVeh+"_"+dateVeh+"_"+data.getPlaca()+".jpg";
					
		return path;
				
	}
	
	public String findPlateImage(OCR data) {			
	
		String path = "";
		String dateVeh = formataDados(data.getDataHour());
		String subFolder = dateVeh.substring(0, 8);		
		String nameVeh = data.getCam().replaceAll(" ", "_");
				
		if(data.getPlaca().equals("XXXXXXX") || data.getPlaca().equals(""))
			path = noImageFolder.concat("no-image.jpg");
		  //path = ftpFolder+""+nameVeh+"\\"+subFolder+"\\Plate"+nameVeh+"_"+dateVeh+"_.jpg";
		
		  
		
		else path = ftpFolder+""+nameVeh+"\\"+subFolder+"\\Plate"+nameVeh+"_"+dateVeh+"_"+data.getPlaca()+".jpg";
							
		return path;			
		
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
			
			conn.start(1);
			conn.prepare(query);
			conn.setString(1, cam);
			
			MapResult result = conn.executeQuery();
			
			if(result.hasNext()) {
				for (RowResult rs : result) {
					
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
			conn.close();
		}
		//passando os valores dos atributos para a vari�vel occ
		return ocr;

	}
   
   
   public List<OCR> Status() throws Exception {
		
		
		List<OCR> list = new ArrayList<OCR>();		
						
		String select = "SELECT equip_id, equip_name, equip_last_status, equip_status FROM connection_monitor WHERE equip_type = 'OCR' ";
									
	  try {
			
		   conn.start(1);
			
			conn.prepare(select);			
									
			MapResult result = conn.executeQuery();
			
			if (result.hasNext()) {
				for (RowResult rs : result) {
					
					OCR ocr = new OCR();

			         ocr.setEquip_id(rs.getInt("equip_id"));
			         ocr.setNome(rs.getString("equip_name"));
			         ocr.setStatus(rs.getInt("equip_status"));			      
			        															
					list.add(ocr);
				}				
			 }			

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {conn.close();}
 				
		return list;
		
	}

}