package br.com.tracevia.webapp.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import br.com.tracevia.webapp.methods.DateTimeApplication;


public class SystemLog {

	// LOGS MAIN FOLDERS

	public static String DEBUG = "C:\\Tracevia\\Software\\System\\Logs\\debug\\";
	public static String ERROR = "C:\\Tracevia\\Software\\System\\Logs\\error\\";
	public static String INFO = "C:\\Tracevia\\Software\\System\\Logs\\info\\";
	public static String FATAL = "C:\\Tracevia\\Software\\System\\Logs\\fatal\\";
	public static String TRACE = "C:\\Tracevia\\Software\\System\\Logs\\trace\\";
	public static String WARN = "C:\\Tracevia\\Software\\System\\Logs\\warn\\";

	// --------------------------------------------------------------------------------------------

	// CLASS PATH

	private static String classLocation = SystemLog.class.getCanonicalName();

	// --------------------------------------------------------------------------------------------

	// CLASS LOG FOLDER

	private static String classErrorPath = SystemLog.ERROR.concat("logs_util\\");

	// --------------------------------------------------------------------------------------------

	// CONSTRUTOR 

	public SystemLog() {		

		createLogFolder(classErrorPath);		

	}	

	// --------------------------------------------------------------------------------------------

	/**
	 * Método para gerar logs do tipo DEBUG
	 * @author Wellington 11/12/2021
	 * @version 1.0
	 * @since 1.0
	 * @param fileName - nome do log criado
	 * @param classPath - caminho completo da classe onde log foi criado
	 * @param message - mensagem a ser exibida	
	 */
	public static void logDebug(String fileName, String classPath, String message) {		

		DateTimeApplication dta = new DateTimeApplication();		     

		String pattern = "["+dta.currentDateTimeMillis()+"] [DEBUG]|["+classPath+"] : ";

		String newFileName = fileDateTimeFormatter(fileName);

		try {

			BufferedWriter writer = new BufferedWriter(new FileWriter(newFileName, true));

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
	 * Método para gerar logs do tipo ERROR
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

		String newFileName = fileDateTimeFormatter(fileName);

		try {

			BufferedWriter writer = new BufferedWriter(new FileWriter(newFileName, true));

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
	 * Método para gerar logs do tipo [INFO]
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

		String newFileName = fileDateTimeFormatter(fileName);

		try {

			BufferedWriter writer = new BufferedWriter(new FileWriter(newFileName, true));

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
	 * Método para gerar logs do tipo [FATAL]
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

		String newFileName = fileDateTimeFormatter(fileName);

		try {

			BufferedWriter writer = new BufferedWriter(new FileWriter(newFileName, true));

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
	 * Método para gerar logs do tipo [TRACE]
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

		String newFileName = fileDateTimeFormatter(fileName);

		try {

			BufferedWriter writer = new BufferedWriter(new FileWriter(newFileName, true));

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
	 * Método para gerar logs do tipo [WARN]
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

		String newFileName = fileDateTimeFormatter(fileName);

		try {

			BufferedWriter writer = new BufferedWriter(new FileWriter(newFileName, true));

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
	 * Método para gerar logs do tipo [ERROR] SQL
	 * @author Wellington 05/10/2021
	 * @version 1.0
	 * @since 1.0
	 * @param fileName - nome do log a ser criado
	 * @param classPath - caminho completo da classe para adicionar ao log
	 * @param errorCode - código do erro a ser exibido
	 * @param sqlState -  identifica condi��es de erro SQL	
	 * @param message - mensagem a ser exibida	
	 * @exception - rastreio da exce��o lançada		
	 */
	public static void logErrorSQL(String fileName, String classPath, int errorCode, String sqlState, String message, String exception) {	

		DateTimeApplication dta = new DateTimeApplication();		     

		String pattern = "["+dta.currentDateTimeMillis()+"] [ERROR]|["+classPath+"] : ";

		String newFileName = fileDateTimeFormatter(fileName);

		try {

			BufferedWriter writer = new BufferedWriter(new FileWriter(newFileName, true));

			writer.newLine();
			writer.write(pattern.concat(String.valueOf(errorCode)));
			writer.newLine();
			writer.write(pattern.concat(sqlState));
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
	 * Método para criar uma pasta no diret�rio de logs
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
	 * Método para adicionar data ao aqruivo .log antes de sua cria��o
	 * @author Wellington 05/10/2021
	 * @version 1.0
	 * @since 1.0
	 * @param localPath - caminho completo do arquivo (com nome do arquivo)   
	 */
	public static String fileDateTimeFormatter(String localPath) {

		DateTimeApplication dta = new DateTimeApplication();	

		localPath += "_"+dta.currentDateToFile()+".log";		

		return localPath;

	}

	// --------------------------------------------------------------------------------------------

}
