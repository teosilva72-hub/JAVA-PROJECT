package br.com.tracevia.webapp.dao.wim;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import br.com.tracevia.webapp.util.ConnectionFactory;

public class WIMDAO {
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;
	
	public int serialNumber() throws Exception{
		String sql = "SELECT max(seqN) FROM wim_vbv";
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
		String sql = "SELECT max(data) FROM wim_vbv";
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
		String sql = "SELECT max(classe) FROM wim_vbv";
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
		String sql = "SELECT max(axlNumber) FROM wim_vbv";
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
		String sql = "SELECT max(axlNumber) FROM wim_vbv";
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
		String sql = "SELECT max(speed) FROM wim_vbv";
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
		String sql = "SELECT max(speed) FROM wim_vbv";
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
		String sql = "SELECT max(axl1W) FROM wim_vbv";
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
		String sql = "SELECT max(axl2W) FROM wim_vbv";
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
		String sql = "SELECT max(axl3W) FROM wim_vbv";
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
		String sql = "SELECT max(axl4W) FROM wim_vbv";
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
		String sql = "SELECT max(axl5W) FROM wim_vbv";
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
		String sql = "SELECT max(axl6W) FROM wim_vbv";
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
		String sql = "SELECT max(axl7W) FROM wim_vbv";
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
		String sql = "SELECT max(axl8W) FROM wim_vbv";
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
		String sql = "SELECT max(axl9W) FROM wim_vbv";
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
		String sql = "SELECT max(axl2D) FROM wim_vbv";
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
		String sql = "SELECT max(axl3D) FROM wim_vbv";
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
		String sql = "SELECT max(axl4D) FROM wim_vbv";
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
		String sql = "SELECT max(axl5D) FROM wim_vbv";
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
		String sql = "SELECT max(axl6D) FROM wim_vbv";
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
		String sql = "SELECT max(axl7D) FROM wim_vbv";
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
		String sql = "SELECT max(axl8D) FROM wim_vbv";
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
		String sql = "SELECT max(axl9D) FROM wim_vbv";
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
	
}
