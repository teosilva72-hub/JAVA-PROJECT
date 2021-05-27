package br.com.tracevia.webapp.dao.wim;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.model.occ.OccurrencesData;
import br.com.tracevia.webapp.model.wim.WimData;
import br.com.tracevia.webapp.util.ConnectionFactory;

public class WIMDAO {
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;
	WimData data = new WimData();
	
	/////////////////INICIO/////////////////////////////////////////////
	/////////WIM REALTIME//////////////////////////////////////////////
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
}
