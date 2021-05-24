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
