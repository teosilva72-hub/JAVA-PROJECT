package br.com.tracevia.webapp.util;

import java.sql.Connection;

import br.com.tracevia.webapp.cfg.RoadConcessionairesEnum;
import br.com.tracevia.webapp.model.global.RoadConcessionaire;

import java.sql.*;

public class ConnectionFactory {
	
	private static Connection connection;
	private static Connection connection2;
	private static Connection connection3;
	private static Connection connection4;
	private static Connection connection5;
		
	//private static final Logger connectionLog = LogManager.getLogger(ConnectionFactory.class.getName());
		
   public static Connection connectToTraceviaApp() throws Exception {
		
		connection = null;
		
		try {
				
			  Class.forName("com.mysql.cj.jdbc.Driver");
				connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tracevia_app?useTimezone=true&serverTimezone=UTC"
						+ "&autoReconnect=true&useSSL=false", "root", "trcvbr18"); 
							
		}catch(ClassNotFoundException cfe) {
			System.out.println("Driver não encontrado: "+cfe.getMessage());
						
		}catch(SQLException sql) {
			System.out.println("SQLException: " + sql.getMessage());
		    System.out.println("SQLState: " + sql.getSQLState());
		    System.out.println("Erro: " + sql.getErrorCode());
		    System.out.println("StackTrace: " + sql.getStackTrace());		   
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return connection;
	}
	
	
  public static Connection connectToTraceviaCore() throws Exception {
		
		connection2 = null;
		
		try {
				
			  Class.forName("com.mysql.cj.jdbc.Driver");
				connection2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/tracevia_core?useTimezone=true&serverTimezone=UTC"
						+ "&autoReconnect=true&useSSL=false", "root", "trcvbr18"); 
							
		}catch(ClassNotFoundException cfe) {
			System.out.println("Driver não encontrado: "+cfe.getMessage());
						
		}catch(SQLException sql) {
			System.out.println("SQLException: " + sql.getMessage());
		    System.out.println("SQLState: " + sql.getSQLState());
		    System.out.println("Erro: " + sql.getErrorCode());
		    System.out.println("StackTrace: " + sql.getStackTrace());		   
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return connection2;
	}
  
  public static Connection connectToTraceviaITS() throws Exception {
		
		connection3 = null;
		
		try {
				
			  Class.forName("com.mysql.cj.jdbc.Driver");
				connection3 = DriverManager.getConnection("jdbc:mysql://localhost:3306/tracevia_its?useTimezone=true&serverTimezone=UTC"
						+ "&autoReconnect=true&useSSL=false", "root", "trcvbr18"); 
							
		}catch(ClassNotFoundException cfe) {
			System.out.println("Driver não encontrado: "+cfe.getMessage());
						
		}catch(SQLException sql) {
			System.out.println("SQLException: " + sql.getMessage());
		    System.out.println("SQLState: " + sql.getSQLState());
		    System.out.println("Erro: " + sql.getErrorCode());
		    System.out.println("StackTrace: " + sql.getStackTrace());		   
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return connection3;
	}
  
  public static Connection connectToCCR() throws Exception {
		
		connection4 = null;
		
		try {
				
			  Class.forName("com.mysql.cj.jdbc.Driver");
				connection4 = DriverManager.getConnection("jdbc:mysql://localhost:3306/trcv_sat?useTimezone=true&serverTimezone=UTC"
						+ "&autoReconnect=true&useSSL=false", "root", "trcvbr18"); 
							
		}catch(ClassNotFoundException cfe) {
			System.out.println("Driver não encontrado: "+cfe.getMessage());
						
		}catch(SQLException sql) {
			System.out.println("SQLException: " + sql.getMessage());
		    System.out.println("SQLState: " + sql.getSQLState());
		    System.out.println("Erro: " + sql.getErrorCode());
		    System.out.println("StackTrace: " + sql.getStackTrace());		   
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return connection4;
	}
  
  public static Connection connectToViaPaulista() throws Exception {
		
		connection5 = null;
		
		try {
				
			  Class.forName("com.mysql.cj.jdbc.Driver");
				connection5 = DriverManager.getConnection("jdbc:mysql://localhost:3306/test_wim?useTimezone=true&serverTimezone=UTC"
						+ "&autoReconnect=true&useSSL=false", "root", "trcvbr18"); 
							
		}catch(ClassNotFoundException cfe) {
			System.out.println("Driver não encontrado: "+cfe.getMessage());
						
		}catch(SQLException sql) {
			System.out.println("SQLException: " + sql.getMessage());
		    System.out.println("SQLState: " + sql.getSQLState());
		    System.out.println("Erro: " + sql.getErrorCode());
		    System.out.println("StackTrace: " + sql.getStackTrace());		   
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return connection5;
	}
    
	public static void closeConnection(Connection conn, PreparedStatement ps, ResultSet rs) throws Exception {
		
		try {
			
			close(conn, ps, rs);
	
		}catch(Exception e) {
		System.out.println("Erro ao encerrar conexão: "+e.getMessage());
	}		
}

	public static void closeConnection(Connection conn, PreparedStatement ps) throws Exception {
		
		try {
		
			close(conn, ps, null);
	
		}catch(Exception e) {
		System.out.println("Erro ao encerrar conexão: "+e.getMessage());
	}		
  }

	private static void close(Connection conn, PreparedStatement ps, ResultSet rs) throws Exception {
		try {
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
		} catch (Exception e) {
			System.out.println("Erro ao encerrar conexão: "+e.getMessage());
		}
	}
	
	
	//Identificar conexão com a Base de Dados
	public static Connection useConnection(String roadConcessionaire) throws Exception {
		
		Connection conn = null;
				
		 //GET CONNECTION			
	    if(roadConcessionaire.equals(RoadConcessionairesEnum.ViaSul.getConcessionaire()))
	           conn = connectToCCR();
	    
	    else if(roadConcessionaire.equals(RoadConcessionairesEnum.ViaPaulista.getConcessionaire()))
	           conn = connectToViaPaulista();
	    
	    else conn = connectToTraceviaApp();

	    return conn;
		
	}
	
}
