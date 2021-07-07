package br.com.tracevia.webapp.dao.wim;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;

import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.model.wim.WimData;
import br.com.tracevia.webapp.util.ConnectionFactory;

public class WIMDAO {
	
	private Connection conn;
	private PreparedStatement ps, ps1;
	private ResultSet rs, rs1;
	
	WimData data = new WimData();
		
	//////////////////////WIM RELATORIO//////////////////////////////////////////////
	public ArrayList<WimData>search(String start, String end, String classe) throws Exception {
		
		String search = "SELECT data, seqN FROM wim_vbv WHERE data BETWEEN'"+ start +"'AND'"+ end +"'AND classe ='"+ classe+"'";
		String search1 = "SELECT data, seqN FROM wim_vbv WHERE data BETWEEN'"+ start +"'AND'"+ end+"'";
		ArrayList<WimData> list = new ArrayList<WimData>();
		
		try {
			
			conn = ConnectionFactory.connectToTraceviaApp();
			ps = conn.prepareStatement(search);
			ps1 = conn.prepareStatement(search1);
			rs = ps.executeQuery();
			rs1 = ps1.executeQuery();
			
			if (rs.isBeforeFirst() && classe != null) {
				while (rs.next()) {
					WimData data = new WimData();			
						data.setDatetime(rs.getString(1));
						data.setSerialNumber(rs.getString(2));
						list.add(data);
				}				
			 }else {
				 
				 while (rs1.next()) {
						WimData data = new WimData();			
							data.setDatetime(rs1.getString(1));
							data.setSerialNumber(rs1.getString(2));
							list.add(data);
					}				
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public WimData searchId(int id) throws Exception {

		WimData search = new WimData();
		
		//Script dos atributos que as infor��es ser�o requisitadas
		String query = "SELECT seqN, data, classe, axlNumber, speed, gross, size, image, image_plate, image_sil, axl1W, axl2W, axl3W, axl4W, axl5W, axl6W, axl7W, axl8W, axl9W, " + 
				"axl2D, axl3D, axl4D, axl5D, axl6D, axl7D, axl8D, axl9D, axl1T, axl2T, axl3T, axl4T, axl5T, axl6T, axl7T, axl8T, axl9T " +
				"FROM wim_vbv WHERE seqN ='"+id+"' ORDER BY vbv_id DESC LIMIT 1";
			
		try {

			conn = ConnectionFactory.connectToTraceviaApp();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			
			if(rs != null) {
				while(rs.next()) {
					
					search.setSeqN(rs.getString(1));
					search.setDatetime(rs.getString(2));
					search.setClasse(rs.getString(3));
					search.setAxlNumber(rs.getString(4));
					search.setSpeed(rs.getString(5));
					search.setPbtTotal(rs.getString(6));
					search.setSize(rs.getString(7));
					search.setImage(rs.getString(8));
					search.setImagePlate(rs.getString(9));
					search.setImageSil(rs.getString(10));
																			
					  String[] weight = new String[9];
						weight [0] = rs.getString(11);
						weight [1] = rs.getString(12);
						weight [2] = rs.getString(13);
						weight [3] = rs.getString(14);
						weight [4] = rs.getString(15);
						weight [5] = rs.getString(16);
						weight [6] = rs.getString(17);
						weight [7] = rs.getString(18);
						weight [8] = rs.getString(19);
						
						search.setAxlWeight(weight);
						
						//distancia entre os eixos
						String[] dstAxes = new String[9];
						dstAxes [0] = rs.getString(20);
						dstAxes [1] = rs.getString(21);
						dstAxes [2] = rs.getString(22);
						dstAxes [3] = rs.getString(23);
						dstAxes [4] = rs.getString(24);
						dstAxes [5] = rs.getString(25);
						dstAxes [6] = rs.getString(26);
						dstAxes [7] = rs.getString(27);	
						dstAxes [8] = " - ";

						search.setAxlDist(dstAxes);
						
						//distancia entre os eixos
						String[] typeAxes = new String[9];
						typeAxes [0] = rs.getString(28);
						typeAxes [1] = rs.getString(29);
						typeAxes [2] = rs.getString(30);
						typeAxes [3] = rs.getString(31);
						typeAxes [4] = rs.getString(32);
						typeAxes [5] = rs.getString(33);
						typeAxes [6] = rs.getString(34);
						typeAxes [7] = rs.getString(35);
						typeAxes [8] = rs.getString(36);
						
						search.setAxlType(typeAxes);
												
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
	/////////WIM REALTIME////////////////////////////////////////////////////////////
	public boolean updateFilePath(String data, String classe, int axlNumber, int axl1w, int axl2w, int axl3w, int axl4w,
			int axl5w, int axl6w, int axl7w, int axl8w, int axl9w, int axl2d, int axl3d, int axl4d, int axl5d,
			int axl6d, int axl7d, int axl8d, int axl9d, int gross, int speed) throws Exception {

		boolean status = false;

		String query = "UPDATE wim_vbv SET data = ?, classe = ?, axlNumber = ?, axl1W = ?,  axl2W = ?,"
				+ " axl3W = ?, axl4W = ?, axl5W = ?, axl6W = ?, axl7W = ?, axl8W = ?, axl9W = ?,"
				+ "axl2D = ?, axl3D = ?, axl4D = ?, axl5D = ?, axl6D = ?, axl7D = ?, axl8D = ?, axl9D = ?,"
				+ "gross = ?, speed = ? WHERE vbv_id = 1491931";	

		try {
			conn = ConnectionFactory.connectToTraceviaApp();
			ps = conn.prepareStatement(query);
			ps.setString(1, data);
			ps.setString(2, classe);
			ps.setInt(3, axlNumber);
			ps.setInt(4, axl1w);
			ps.setInt(5, axl2w);
			ps.setInt(6, axl3w);
			ps.setInt(7, axl4w);
			ps.setInt(8, axl5w);
			ps.setInt(9, axl6w);
			ps.setInt(10, axl7w);
			ps.setInt(11, axl8w);
			ps.setInt(12, axl9w);
			ps.setInt(13, axl2d);
			ps.setInt(14, axl3d);
			ps.setInt(15, axl4d);
			ps.setInt(16, axl5d);
			ps.setInt(17, axl6d);
			ps.setInt(18, axl7d);
			ps.setInt(19, axl8d);
			ps.setInt(20, axl9d);
			ps.setInt(21, gross);
			ps.setInt(22, speed);
			
			int res = ps.executeUpdate();

			if(res > 0)
				status = true;

		}catch (SQLException alterarPath){
			throw new Exception("Erro ao alterar path: " + alterarPath);
		}finally {
			ConnectionFactory.closeConnection(conn, ps);
		}	

		return status;
	}
	public int serialNumber() throws Exception{
		String sql = "SELECT seqN from wim_vbv ORDER BY vbv_id desc limit 1;";
		int value = 0; 
		try {
			conn = ConnectionFactory.connectToTraceviaApp();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs != null) {
				while(rs.next()) {
					value = rs.getInt(1);
				}
			}
		}finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}
		//retornando o valor do �ltimo a id para a variavel (value)
		return value;
	}
	public String dateHour() throws Exception{
		String sql = "SELECT data from wim_vbv ORDER BY vbv_id desc limit 1";
		String value = ""; 
		try {
			conn = ConnectionFactory.connectToTraceviaApp();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			if(rs != null) {
				while(rs.next()) {
					value = rs.getString(1);
				}
			}
			
		}finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}
		//retornando o valor do �ltimo a id para a variavel (value)
		return value;
	}
	public String classe() throws Exception{
		String sql = "SELECT classe from wim_vbv ORDER BY vbv_id desc limit 1";
		String value = ""; 
		
		try {
			
			conn = ConnectionFactory.connectToTraceviaApp();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			if(rs != null) {
				while(rs.next()) {
					value = rs.getString(1);
				}
			}
			
		}finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}
		
		//retornando o valor do �ltimo a id para a variavel (value)
		return value;
	}
	public String Classe() throws Exception{
		String sql = "SELECT data from wim_vbv ORDER BY vbv_id desc limit 1";
		String value = ""; 
		
		try {
			
			conn = ConnectionFactory.connectToTraceviaApp();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			if(rs != null) {
				while(rs.next()) {
					value = rs.getString(1);
				}
			}
			
		}finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}
		//retornando o valor do �ltimo a id para a variavel (value)
		return value;
	}
	public String eixo() throws Exception{
		
		String sql = "SELECT axlNumber from wim_vbv ORDER BY vbv_id desc limit 1";
		String value = ""; 
		
		try {
			
			conn = ConnectionFactory.connectToTraceviaApp();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			if(rs != null) {
				while(rs.next()) {
					value = rs.getString(1);
				}
			}
			
		}finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}
		//retornando o valor do �ltimo a id para a variavel (value)
		return value;
	}
	public String speed() throws Exception{
		
		String sql = "SELECT speed from wim_vbv ORDER BY vbv_id desc limit 1";
		String value = ""; 
		
		try {
			
			conn = ConnectionFactory.connectToTraceviaApp();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			if(rs != null) {
				while(rs.next()) {
					value = rs.getString(1);
				}
			}
		}finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}
		
		//retornando o valor do �ltimo a id para a variavel (value)
		return value;
	}
	
	public String pbtTotal() throws Exception{
		String sql = "SELECT gross from wim_vbv ORDER BY vbv_id desc limit 1";
		String value = ""; 
		try {
			conn = ConnectionFactory.connectToTraceviaApp();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs != null) {
				while(rs.next()) {
					value = rs.getString(1);
				}
			}
		}finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}
		//retornando o valor do �ltimo a id para a variavel (value)
		return value;
	}
	public int axl1W() throws Exception{
		String sql = "SELECT axl1W from wim_vbv ORDER BY vbv_id desc limit 1";
		int value = 0; 
		try {
			conn = ConnectionFactory.connectToTraceviaApp();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs != null) {
				while(rs.next()) {
					value = rs.getInt(1);
				}
			}
		}finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}
		//retornando o valor do �ltimo a id para a variavel (value)
		return value;
	}
	public int axl2W() throws Exception{
		String sql = "SELECT axl2W from wim_vbv ORDER BY vbv_id desc limit 1";
		int value = 0; 
		try {
			conn = ConnectionFactory.connectToTraceviaApp();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs != null) {
				while(rs.next()) {
					value = rs.getInt(1);
				}
			}
		}finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}
		//retornando o valor do �ltimo a id para a variavel (value)
		return value;
	}
	public int axl3W() throws Exception{
		String sql = "SELECT axl3W from wim_vbv ORDER BY vbv_id desc limit 1";
		int value = 0;  
		try {
			conn = ConnectionFactory.connectToTraceviaApp();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs != null) {
				while(rs.next()) {
					value = rs.getInt(1);
				}
			}
		}finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}
		//retornando o valor do �ltimo a id para a variavel (value)
		return value;
	}
	public int axl4W() throws Exception{
		String sql = "SELECT axl4W from wim_vbv ORDER BY vbv_id desc limit 1";
		int value = 0; 
		try {
			conn = ConnectionFactory.connectToTraceviaApp();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs != null) {
				while(rs.next()) {
					value = rs.getInt(1);
				}
			}
		}finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}
		//retornando o valor do �ltimo a id para a variavel (value)
		return value;
	}
	public int axl5W() throws Exception{
		String sql = "SELECT axl5W from wim_vbv ORDER BY vbv_id desc limit 1";
		int value = 0; 
		try {
			conn = ConnectionFactory.connectToTraceviaApp();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs != null) {
				while(rs.next()) {
					value = rs.getInt(1);
				}
			}
		}finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}
		//retornando o valor do �ltimo a id para a variavel (value)
		return value;
	}
	public int axl6W() throws Exception{
		String sql = "SELECT axl6W from wim_vbv ORDER BY vbv_id desc limit 1";
		int value = 0;  
		try {
			conn = ConnectionFactory.connectToTraceviaApp();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs != null) {
				while(rs.next()) {
					value = rs.getInt(1);
				}
			}
		}finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}
		//retornando o valor do �ltimo a id para a variavel (value)
		return value;
	}
	public int axl7W() throws Exception{
		String sql = "SELECT axl7W from wim_vbv ORDER BY vbv_id desc limit 1";
		int value = 0; 
		try {
			conn = ConnectionFactory.connectToTraceviaApp();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs != null) {
				while(rs.next()) {
					value = rs.getInt(1);
				}
			}
		}finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}
		//retornando o valor do �ltimo a id para a variavel (value)
		return value;
	}
	public int axl8W() throws Exception{
		String sql = "SELECT axl8W from wim_vbv ORDER BY vbv_id desc limit 1";
		int value = 0; 
		try {
			conn = ConnectionFactory.connectToTraceviaApp();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs != null) {
				while(rs.next()) {
					value = rs.getInt(1);
				}
			}
		}finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}
		//retornando o valor do �ltimo a id para a variavel (value)
		return value;
	}
	public int axl9W() throws Exception{
		String sql = "SELECT axl9W from wim_vbv ORDER BY vbv_id desc limit 1";
		int value = 0;  
		try {
			conn = ConnectionFactory.connectToTraceviaApp();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs != null) {
				while(rs.next()) {
					value = rs.getInt(1);
				}
			}
		}finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}
		//retornando o valor do �ltimo a id para a variavel (value)
		return value;
	}
	public int axl2D() throws Exception{
		String sql ="SELECT axl2D from wim_vbv ORDER BY vbv_id desc limit 1";
		int value = 0; 
		try {
			conn = ConnectionFactory.connectToTraceviaApp();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs != null) {
				while(rs.next()) {
					value = rs.getInt(1);
				}
			}
		}finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}
		//retornando o valor do �ltimo a id para a variavel (value)
		return value;
	}
	public int axl3D() throws Exception{
		String sql = "SELECT axl3D from wim_vbv ORDER BY vbv_id desc limit 1";
		int value = 0; 
		try {
			conn = ConnectionFactory.connectToTraceviaApp();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs != null) {
				while(rs.next()) {
					value = rs.getInt(1);
				}
			}
		}finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}
		//retornando o valor do �ltimo a id para a variavel (value)
		return value;
	}
	public int axl4D() throws Exception{
		String sql = "SELECT axl4D from wim_vbv ORDER BY vbv_id desc limit 1";
		int value = 0; 
		try {
			conn = ConnectionFactory.connectToTraceviaApp();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs != null) {
				while(rs.next()) {
					value = rs.getInt(1);
				}
			}
		}finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}
		//retornando o valor do �ltimo a id para a variavel (value)
		return value;
	}
	public int axl5D() throws Exception{
		String sql = "SELECT axl5D from wim_vbv ORDER BY vbv_id desc limit 1";
		int value = 0;  
		try {
			conn = ConnectionFactory.connectToTraceviaApp();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs != null) {
				while(rs.next()) {
					value = rs.getInt(1);
				}
			}
		}finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}
		//retornando o valor do �ltimo a id para a variavel (value)
		return value;
	}
	public int axl6D() throws Exception{
		String sql = "SELECT axl6D from wim_vbv ORDER BY vbv_id desc limit 1";
		int value = 0;  
		try {
			conn = ConnectionFactory.connectToTraceviaApp();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs != null) {
				while(rs.next()) {
					value = rs.getInt(1);
				}
			}
		}finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}
		//retornando o valor do �ltimo a id para a variavel (value)
		return value;
	}
	public int axl7D() throws Exception{
		String sql = "SELECT axl7D from wim_vbv ORDER BY vbv_id desc limit 1";
		int value = 0;  
		try {
			conn = ConnectionFactory.connectToTraceviaApp();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs != null) {
				while(rs.next()) {
					value = rs.getInt(1);
				}
			}
		}finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}
		//retornando o valor do �ltimo a id para a variavel (value)
		return value;
	}
	public int axl8D() throws Exception{
		String sql = "SELECT axl8D from wim_vbv ORDER BY vbv_id desc limit 1";
		int value = 0; 
		try {
			conn = ConnectionFactory.connectToTraceviaApp();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs != null) {
				while(rs.next()) {
					value = rs.getInt(1);
				}
			}
		}finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}
		//retornando o valor do �ltimo a id para a variavel (value)
		return value;
	}
	public int axl9D() throws Exception{
		String sql = "SELECT axl9D from wim_vbv ORDER BY vbv_id desc limit 1";
		int value = 0;  
		try {
			conn = ConnectionFactory.connectToTraceviaApp();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs != null) {
				while(rs.next()) {
					value = rs.getInt(1);
				}
			}
		}finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}
		//retornando o valor do �ltimo a id para a variavel (value)
		return value;
	}
	public boolean color(boolean status) throws Exception {
		// System.out.println("DATA: "+data.getData_number()+"\nType: "+data.getAction_type());
		boolean x = false;
		//script dos atributos que ser�o atualizados as informa�oes do banco de dados
		String query = "UPDATE wim_color SET status = ? WHERE id = 1";
		DateTimeApplication dtm = new DateTimeApplication();
		try {
			//atributos que ser�o atualizados quando o m�todo for chamado
			conn = ConnectionFactory.connectToTraceviaApp();
			ps = conn.prepareStatement(query);
			ps.setBoolean(1, status);
			int answer = ps.executeUpdate();
			if(answer > 0) 
				status = true;
		}catch (SQLException i){
			throw new Exception("Erro ao alterar dados: " + i);
		}finally {
			ConnectionFactory.closeConnection(conn, ps);
		}		
		return x;
	}
	
	private boolean q;
	public boolean searchColor() throws Exception{
		String sql = "SELECT status FROM wim_color where id= 1";		
			conn = ConnectionFactory.connectToTraceviaApp();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs != null) {
				while(rs.next()) {
					q = rs.getBoolean(1);
				}
			}
		
		//retornando o valor do �ltimo a id para a variavel (value)
		return q;
	}
	 ///FINAL/////////////////////////////////////////////////////////////
	//WIM REALTIME//////////////////////////////////////////////////////
	
	//WIM SAVE
	
	public void saveVehicle(WimData data) throws Exception {
						
		String query = "INSERT INTO wim_vbv (vbv_id, siteID, seqG, seqN, data, image, image_plate, image_sil, classe, axlNumber, " +
		"axl1W, axl2W, axl3W, axl4W, axl5W, axl6W, axl7W, axl8W, axl9W, "+
		"axl2D, axl3D, axl4D, axl5D, axl6D, axl7D, axl8D, axl9D, "+
		"axl1T, axl2T, axl3T, axl4T, axl5T, axl6T, axl7T, axl8T, axl9T, gross, speed, size ) " +
		"VALUES (null,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
				
		try {
		
			conn = ConnectionFactory.connectToTraceviaApp();
			ps = conn.prepareStatement(query);
			
			ps.setInt(1, data.getId());
			ps.setInt(2, Integer.parseInt(data.getSerialNumber()));
			ps.setInt(3, Integer.parseInt(data.getSerialNumber()));
			ps.setString(4, data.getDatetime());
			ps.setString(5, data.getImage());
			ps.setString(6, data.getImagePlate());
			ps.setString(7, data.getImageSil());
			ps.setString(8, data.getClasse());
			ps.setString(9, data.getAxlNumber());			
			ps.setString(10, data.getAxlWeight()[0]);
			ps.setString(11, data.getAxlWeight()[1]);
			ps.setString(12, data.getAxlWeight()[2]);
			ps.setString(13, data.getAxlWeight()[3]);
			ps.setString(14, data.getAxlWeight()[4]);
			ps.setString(15, data.getAxlWeight()[5]);
			ps.setString(16, data.getAxlWeight()[6]);
			ps.setString(17, data.getAxlWeight()[7]);
			ps.setString(18, data.getAxlWeight()[8]);
			ps.setString(19, data.getAxlDist()[0]);
			ps.setString(20, data.getAxlDist()[1]);
			ps.setString(21, data.getAxlDist()[2]);
			ps.setString(22, data.getAxlDist()[3]);
			ps.setString(23, data.getAxlDist()[4]);
			ps.setString(24, data.getAxlDist()[5]);
			ps.setString(25, data.getAxlDist()[6]);
			ps.setString(26, data.getAxlDist()[7]);		
			ps.setString(27, data.getAxlType()[0]);
			ps.setString(28, data.getAxlType()[1]);
			ps.setString(29, data.getAxlType()[2]);
			ps.setString(30, data.getAxlType()[3]);
			ps.setString(31, data.getAxlType()[4]);
			ps.setString(32, data.getAxlType()[5]);
			ps.setString(33, data.getAxlType()[6]);
			ps.setString(34, data.getAxlType()[7]);
			ps.setString(35, data.getAxlType()[8]);		
			ps.setString(36, data.getPbtTotal());
			ps.setString(37, data.getSpeed());
			ps.setString(38, data.getSize());
									
			ps.executeUpdate();
		
		}catch (SQLException i){
			throw new Exception("Erro ao inserir dados: " + i);
		}finally {
			ConnectionFactory.closeConnection(conn, ps);
		}		
						
	}
	
	   public String getImagePath(String image) {
			
			try {

				Path path = Paths.get(image);								
				  byte[] file = Files.readAllBytes(path);
				  return Base64.getEncoder().encodeToString(file);
				  
			} catch (IOException e) {											
				return "";
			}

		}
		
}
