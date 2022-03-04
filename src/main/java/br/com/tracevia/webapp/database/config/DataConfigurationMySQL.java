package br.com.tracevia.webapp.database.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.tracevia.webapp.cfg.RoadConcessionairesEnum;

public class DataConfigurationMySQL {
	
	private static Connection traceviaAppConn; 
	private static Connection traceviaCoreConn; 
	private static Connection traceviaITSConn;
	private static Connection viaSulConn;
	private static Connection viaPaulistaConn; 
		
	//private static final Logger connectionLog = LogManager.getLogger(ConnectionFactory.class.getName());
		
   public static Connection connectToTraceviaApp() throws Exception {
		
		traceviaAppConn = null;
		
		try {
				
			  Class.forName("com.mysql.cj.jdbc.Driver");
				traceviaAppConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tracevia_app?useTimezone=true&serverTimezone=UTC"
						+ "&autoReconnect=true&useSSL=false", "root", "trcvbr18"); 
				
														
		}catch(ClassNotFoundException cfe) {
			System.out.println("Driver n�o encontrado: "+cfe.getMessage());
			
						
		}catch(SQLException sql) {
			System.out.println("SQLException: " + sql.getMessage());
		    System.out.println("SQLState: " + sql.getSQLState());
		    System.out.println("Erro: " + sql.getErrorCode());
		    System.out.println("StackTrace: " + sql.getStackTrace());	
		    
		  
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		
		
		}
		
		return traceviaAppConn;
	}
	
	
  public static Connection connectToTraceviaCore() throws Exception {
		
		traceviaCoreConn = null;
		
		try {
				
			  Class.forName("com.mysql.cj.jdbc.Driver");
				traceviaCoreConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tracevia_core?useTimezone=true&serverTimezone=UTC"
						+ "&autoReconnect=true&useSSL=false", "root", "well123"); 
							
		}catch(ClassNotFoundException cfe) {
			System.out.println("Driver n�o encontrado: "+cfe.getMessage());
						
		}catch(SQLException sql) {
			System.out.println("SQLException: " + sql.getMessage());
		    System.out.println("SQLState: " + sql.getSQLState());
		    System.out.println("Erro: " + sql.getErrorCode());
		    System.out.println("StackTrace: " + sql.getStackTrace());		   
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return traceviaCoreConn;
	}
  
  public static Connection connectToTraceviaITS() throws Exception {
		
		traceviaITSConn = null;
		
		try {
				
			  Class.forName("com.mysql.cj.jdbc.Driver");
				traceviaITSConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tracevia_its?useTimezone=true&serverTimezone=UTC"
						+ "&autoReconnect=true&useSSL=false", "root", "well123"); 
							
		}catch(ClassNotFoundException cfe) {
			System.out.println("Driver n�o encontrado: "+cfe.getMessage());
						
		}catch(SQLException sql) {
			System.out.println("SQLException: " + sql.getMessage());
		    System.out.println("SQLState: " + sql.getSQLState());
		    System.out.println("Erro: " + sql.getErrorCode());
		    System.out.println("StackTrace: " + sql.getStackTrace());		   
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return traceviaITSConn;
	}
  
  public static Connection connectToCCR() throws Exception {
		
		viaSulConn = null;
		
		try {
				
			  Class.forName("com.mysql.cj.jdbc.Driver");
				viaSulConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/trcv_sat?useTimezone=true&serverTimezone=UTC"
						+ "&autoReconnect=true&useSSL=false", "root", "well123"); 
							
		}catch(ClassNotFoundException cfe) {
			System.out.println("Driver n�o encontrado: "+cfe.getMessage());
						
		}catch(SQLException sql) {
			System.out.println("SQLException: " + sql.getMessage());
		    System.out.println("SQLState: " + sql.getSQLState());
		    System.out.println("Erro: " + sql.getErrorCode());
		    System.out.println("StackTrace: " + sql.getStackTrace());		   
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return viaSulConn;
	}
  
  public static Connection connectToViaPaulista() throws Exception {
		
		viaPaulistaConn = null;
		
		try {
				
			  Class.forName("com.mysql.cj.jdbc.Driver");
				viaPaulistaConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test_wim?useTimezone=true&serverTimezone=UTC"
						+ "&autoReconnect=true&useSSL=false", "root", "well123"); 
							
		}catch(ClassNotFoundException cfe) {
			System.out.println("Driver n�o encontrado: "+cfe.getMessage());
						
		}catch(SQLException sql) {
			System.out.println("SQLException: " + sql.getMessage());
		    System.out.println("SQLState: " + sql.getSQLState());
		    System.out.println("Erro: " + sql.getErrorCode());
		    System.out.println("StackTrace: " + sql.getStackTrace());		   
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return viaPaulistaConn;
	}
    
	public static void closeConnection(Connection conn, PreparedStatement ps, ResultSet rs) throws Exception {
		
		try {
			
			close(conn, ps, rs);
	
		}catch(Exception e) {
		System.out.println("Erro ao encerrar conex�o: "+e.getMessage());
	}		
}

	public static void closeConnection(Connection conn, PreparedStatement ps) throws Exception {
		
		try {
		
			close(conn, ps, null);
	
		}catch(Exception e) {
		System.out.println("Erro ao encerrar conex�o: "+e.getMessage());
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
