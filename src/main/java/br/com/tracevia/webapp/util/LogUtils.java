package br.com.tracevia.webapp.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import br.com.tracevia.webapp.methods.DateTimeApplication;

/**
 * Classe para manipulção de Logs
 * @author Wellington 05/10/2021
 * @version 1.0
 * @since 1.0
 *
 */

public class LogUtils {	
	
	// LOGS MAIN FOLDERS
	
	public static String DEBUG = "C:\\Tracevia\\Software\\System\\Logs\\debug\\";
	public static String ERROR = "C:\\Tracevia\\Software\\System\\Logs\\error\\";
	public static String INFO = "C:\\Tracevia\\Software\\System\\Logs\\info\\";
	public static String FATAL = "C:\\Tracevia\\Software\\System\\Logs\\fatal\\";
	public static String TRACE = "C:\\Tracevia\\Software\\System\\Logs\\trace\\";
	public static String WARN = "C:\\Tracevia\\Software\\System\\Logs\\warn\\";
	
	// --------------------------------------------------------------------------------------------
	
	// CLASS PATH
	
	private static String classLocation = LogUtils.class.getCanonicalName();
	
	// --------------------------------------------------------------------------------------------
	
	// CLASS LOG FOLDER
	
	private static String classErrorPath = LogUtils.ERROR.concat("logs_util\\");
		
	// --------------------------------------------------------------------------------------------
	
	// CONSTRUTOR 
	
	public LogUtils(){
		
		createLogFolder(classErrorPath);		
		
	}	
	
	// --------------------------------------------------------------------------------------------
	
	/**
	 * M�todo para gerar logs do tipo DEBUG
	 * @author Wellington 05/10/2021
	 * @version 1.0
	 * @since 1.0
	 * @param fileName - nome do log criado
	 * @param classPath - caminho completo da classe onde log foi criado
	 * @param message - mensagem a ser exibida	
	 */
	public static void logDebug(String fileName, String classPath, String message) {		
		 
		    DateTimeApplication dta = new DateTimeApplication();		     
   	    
		    String pattern = "["+dta.currentDateTimeMillis()+"] [DEBUG]|["+classPath+"] : ";
		    
		    try {
				 
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
		
			writer.newLine();
			writer.write(pattern.concat(message));
			writer.newLine();		
			writer.write("------------------------------------------------------------------------------------------------------------------------------------------------------");	
			writer.newLine();
			writer.flush();
			writer.close();
			
		    }catch(IOException io) {
		    	
		    	StringWriter errors = new StringWriter();
				io.printStackTrace(new PrintWriter(errors));
		    	
		    	logError(classErrorPath, classLocation, io.getMessage(), errors.toString());
		    	
		    }				 		 
       }
	
	// --------------------------------------------------------------------------------------------
	
	/**
	 * M�todo para gerar logs do tipo ERROR
	 * @author Wellington 05/10/2021
	 * @version 1.0
	 * @since 1.0
	 * @param fileName - nome do log criado
	 * @param classPath - caminho completo da classe onde log foi criado
	 * @param message - mensagem a ser exibida
	 * @exception - rastreio da exce��o lan�ada	
	 */
	public static void logError(String fileName, String classPath, String message, String exception) {	
		
	    DateTimeApplication dta = new DateTimeApplication();		     
	     	    
	    String pattern = "["+dta.currentDateTimeMillis()+"] [ERROR]|["+classPath+"] : ";
	    
	    try {
			 
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
	
		writer.newLine();
		writer.write(pattern.concat(message));
		writer.newLine();
		writer.write(pattern.concat(exception));
		writer.newLine();
		writer.write("------------------------------------------------------------------------------------------------------------------------------------------------------");	
		writer.newLine();
		writer.flush();
		writer.close();
		
	    }catch(IOException io) {
	    	
	    	StringWriter errors = new StringWriter();
			io.printStackTrace(new PrintWriter(errors));
	    	
	    	logError(classErrorPath, classLocation, io.getMessage(), errors.toString());
	    	
	    }
				 		 
    }	
	
	// --------------------------------------------------------------------------------------------
		
	/**
	 * M�todo para gerar logs do tipo [INFO]
	 * @author Wellington 05/10/2021
	 * @version 1.0
	 * @since 1.0
	 * @param fileName - nome do log a ser criado
	 * @param classPath - caminho completo da classe para adicionar ao log
	 * @param message - mensagem a ser exibida	
	 */
	public static void logInfo(String fileName, String classPath, String message) {		
		 
		  DateTimeApplication dta = new DateTimeApplication();		     
   	    
		    String pattern = "["+dta.currentDateTimeMillis()+"] [INFO]|["+classPath+"] : ";
		    
		    try {
				 
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
		
			writer.newLine();
			writer.write(pattern.concat(message));
			writer.newLine();		
			writer.write("------------------------------------------------------------------------------------------------------------------------------------------------------");	
			writer.newLine();
			writer.flush();
			writer.close();
			
	      }catch(IOException io) {
    	
	    		StringWriter errors = new StringWriter();
				io.printStackTrace(new PrintWriter(errors));
		    	
		    	logError(classErrorPath, classLocation, io.getMessage(), errors.toString());
          }				 		 
    }
	
	// --------------------------------------------------------------------------------------------
	
	/**
	 * M�todo para gerar logs do tipo [FATAL]
	 * @author Wellington 05/10/2021
	 * @version 1.0
	 * @since 1.0
	 * @param fileName - nome do log a ser criado
	 * @param classPath - caminho completo da classe para adicionar ao log
	 * @param message - mensagem a ser exibida	
	 * @exception - rastreio da exce��o lan�ada	
	 */
	public static void logFatal(String fileName, String classPath, String message, String exception) {		
		 
		 DateTimeApplication dta = new DateTimeApplication();		     
  	    
		    String pattern = "["+dta.currentDateTimeMillis()+"] [FATAL]|["+classPath+"] : ";
		    
		    try {
				 
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
		
			writer.newLine();
			writer.write(pattern.concat(message));
			writer.newLine();
			writer.write(pattern.concat(exception));
			writer.newLine();
			writer.write("------------------------------------------------------------------------------------------------------------------------------------------------------");	
			writer.newLine();
			writer.flush();
			writer.close();
			
	       }catch(IOException io) {
    	
	    		StringWriter errors = new StringWriter();
				io.printStackTrace(new PrintWriter(errors));
		    	
		    	logError(classErrorPath, classLocation, io.getMessage(), errors.toString());
	    
          }				 		 
    }	
	
	// --------------------------------------------------------------------------------------------
	
	/**
	 * M�todo para gerar logs do tipo [TRACE]
	 * @author Wellington 05/10/2021
	 * @version 1.0
	 * @since 1.0
	 * @param fileName - nome do log a ser criado
	 * @param classPath - caminho completo da classe para adicionar ao log
	 * @param message - mensagem a ser exibida	
	 */
	public static void logTrace(String fileName, String classPath, String message) throws IOException {		
		 
		 DateTimeApplication dta = new DateTimeApplication();		     
	   	    
		    String pattern = "["+dta.currentDateTimeMillis()+"] [TRACE]|["+classPath+"] : ";
				 
		    try {
		    
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
		
			writer.newLine();
			writer.write(pattern.concat(message));
			writer.newLine();		
			writer.write("------------------------------------------------------------------------------------------------------------------------------------------------------");	
			writer.newLine();
			writer.flush();
			writer.close();
				 		 
	       }catch(IOException io) {    	
	    
	    		StringWriter errors = new StringWriter();
				io.printStackTrace(new PrintWriter(errors));
		    	
		    	logError(classErrorPath, classLocation, io.getMessage(), errors.toString());
		    	
           }
    }
	
	// --------------------------------------------------------------------------------------------
	
	/**
	 * M�todo para gerar logs do tipo [WARN]
	 * @author Wellington 05/10/2021
	 * @version 1.0
	 * @since 1.0
	 * @param fileName - nome do log a ser criado
	 * @param classPath - caminho completo da classe para adicionar ao log
	 * @param message - mensagem a ser exibida	
	 */
	public static void logWarn(String fileName, String classPath, String message) throws IOException {		
		 
		 DateTimeApplication dta = new DateTimeApplication();		     
	   	    
		    String pattern = "["+dta.currentDateTimeMillis()+"] [WARN]|["+classPath+"] : ";
		    
		    try {
				 
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
		
			writer.newLine();
			writer.write(pattern.concat(message));
			writer.newLine();		
			writer.write("------------------------------------------------------------------------------------------------------------------------------------------------------");	
			writer.newLine();
			writer.flush();
			writer.close();
			
	     }catch(IOException io) {
	    	 
	    	StringWriter errors = new StringWriter();
			io.printStackTrace(new PrintWriter(errors));
		    	
		    logError(classErrorPath, classLocation, io.getMessage(), errors.toString());    	
	    
         }				 		 
    }
	
	// --------------------------------------------------------------------------------------------
	
	/**
	 * M�todo para gerar logs do tipo [ERROR] SQL
	 * @author Wellington 05/10/2021
	 * @version 1.0
	 * @since 1.0
	 * @param fileName - nome do log a ser criado
	 * @param classPath - caminho completo da classe para adicionar ao log
	 * @param errorCode - c�digo do erro a ser exibida	
	 * @param sqlState -  identifica condi��es de erro SQL	
	 * @param message - mensagem a ser exibida	
     * @exception - rastreio da exce��o lan�ada		
	 */
	public static void logErrorSQL(String fileName, String classPath, int errorCode, String sqlState, String message, String exception) {	
		
	    DateTimeApplication dta = new DateTimeApplication();		     
	     	    
	    String pattern = "["+dta.currentDateTimeMillis()+"] [ERROR]|["+classPath+"] : ";
	    
	    try {
			 
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
	
		writer.newLine();
		writer.write(pattern.concat(String.valueOf(errorCode)));
		writer.newLine();
		writer.write(pattern.concat(sqlState));
		writer.newLine();
		writer.write(pattern.concat(message != null ? message : "No detail"));
		writer.newLine();
		writer.write(pattern.concat(exception));
		writer.newLine();
		writer.write("------------------------------------------------------------------------------------------------------------------------------------------------------");	
		writer.newLine();
		writer.flush();
		writer.close();
		
        }catch(IOException io) {
        	
        	StringWriter errors = new StringWriter();
			io.printStackTrace(new PrintWriter(errors));
	    	
	    	logError(classErrorPath, classLocation, io.getMessage(), errors.toString());
	    		    
        }				 		 
    }
	
	// --------------------------------------------------------------------------------------------
	
	/**
	 * M�todo para criar uma pasta no diret�rio de logs
	 * @author Wellington 05/10/2021
	 * @version 1.0
	 * @since 1.0
	 * @param localPath - caminho da pasta    
	 */
	public static void createLogFolder(String localPath) {

		String directoryName = localPath;

		File directory = new File(directoryName);
			
		if (!directory.exists()){
			  directory.mkdirs();	      
		}

	}
	
	// --------------------------------------------------------------------------------------------
	
	/**
	 * M�todo para adicionar data ao aqruivo .log antes de sua criação
	 * @author Wellington 05/10/2021
	 * @version 1.0
	 * @since 1.0
	 * @param localPath - caminho completo do arquivo (com nome do arquivo)   
	 */
	public static String fileDateTimeFormatter(String localPath) {
				
		 DateTimeApplication dta = new DateTimeApplication();	
		 
		 localPath += dta.currentDateToFile()+".log";		
		
		 return localPath;
		
	}
	
	// --------------------------------------------------------------------------------------------

}
