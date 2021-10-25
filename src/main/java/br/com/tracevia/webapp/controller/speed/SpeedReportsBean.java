package br.com.tracevia.webapp.controller.speed;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;

import br.com.tracevia.webapp.dao.global.EquipmentsDAO;
import br.com.tracevia.webapp.dao.global.NewReportsDAO;
import br.com.tracevia.webapp.dao.speed.SpeedQueryBody;
import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.methods.SQLModel;
import br.com.tracevia.webapp.model.global.Columns;
import br.com.tracevia.webapp.model.global.Equipments;
import br.com.tracevia.webapp.model.global.ReportBuild;
import br.com.tracevia.webapp.model.global.ReportSelection;
import br.com.tracevia.webapp.model.speed.SpeedReport.Builder;
import br.com.tracevia.webapp.util.ExcelTemplate;
import br.com.tracevia.webapp.util.LocaleUtil;
import br.com.tracevia.webapp.util.LogUtils;
import br.com.tracevia.webapp.util.ReportUtil;
import br.com.tracevia.webapp.util.SessionUtil;

/**
 * Classe para gerar relatórios para classe SPEED
 * @author Wellington 07/10/2021
 * @version 1.0
 * @since 1.0
 *
 */

@ManagedBean(name="speedBean")
@RequestScoped
public class SpeedReportsBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// ----------------------------------------------------------------------------------------------------------------
	
	private static String DATE_COLUMN = "save_date";
	private static String TABLE = "speed_registry";
	private static String MODULE = "speed";
	private static String INDEX_NAME = "idx_date_speed_id";
	private static String JOIN_COLUMN = "equip_id";
	private static String EQUIP_COLUMN = "id_speed";
		
	// ----------------------------------------------------------------------------------------------------------------
	
	private ReportSelection select;
	private ReportBuild build;
	
	public List<Builder> resultList;	
	List<? extends Equipments> listSpeed;  
			
	public String[] equipNames;
		
	public ReportSelection getSelect() {
		return select;
	}
		
	public void setSelect(ReportSelection select) {
		this.select = select;
	}
	
	public ReportBuild getBuild() {
		return build;
	}
	
	public void setBuild(ReportBuild build) {
		this.build = build;
	}
		
	public List<Builder> getResultList() {
	    return resultList;
    }
				
	// ----------------------------------------------------------------------------------------------------------------
		
		// CLASS PATH

		private static String classLocation = SpeedReportsBean.class.getCanonicalName();
		
		// --------------------------------------------------------------------------------------------
		
		// CLASS LOG FOLDER
		
		private static String classErrorPath = LogUtils.ERROR.concat("reports\\speed\\");
		
		// --------------------------------------------------------------------------------------------
		
		// EXCEPTION FILENAMES
		
		private static String ioExceptionLog = classErrorPath.concat("io_exception_");
		
		// --------------------------------------------------------------------------------------------		
		
		// CONSTRUTOR 
	
	@PostConstruct
	public void initialize() {
		
		// Instantiate Objects - build and select class
		
		build = new ReportBuild();
		select = new ReportSelection();	
				
		// ---------------------------------------------------------------------------
				   		
		// SELECT ITEMS LISTS		
        build.equipments = new ArrayList<SelectItem>();	  
        build.periods = new ArrayList<SelectItem>();
        
        listSpeed = new ArrayList<Equipments>();
                
        // COLUMNS LIST
        build.columns = new ArrayList<Columns>();
        
        //DEFINE LOCALE
        build.localeLabels = LocaleUtil.setLocale(LocaleUtil.LABELS_SPEED);
                  
        try {
        	
        	EquipmentsDAO dao = new EquipmentsDAO();		 
			listSpeed = dao.EquipmentSelectOptions("speed");
        	
        	// EQUIPMENTS
        	 build.equipments = build.selectEquips(listSpeed);
        	 select.equipments = new String[build.equipments.size()];  
        	        	                	 			
		} catch (Exception e) {			
			e.printStackTrace();
		}
                 	
    	// ----------------------------------------------------------------------------
        	
    	 // Disabled Buttons
    	 build.clearBool = true;
    	 build.excelBool = true;
    	 build.chartBool = true;						
	 
	 }	
	
    // ----------------------------------------------------------------------------------------------------------------
	
	/**
	 * Método para a seleção do cabeçalho do relatório
	 * @author Wellington 07/10/2021
	 * @version 1.0
	 * @since version 1.0
	 * @param type - Identificação do tipo do relatório	
	 */
	public void createFields(String type) {
		
		// FILTRO DO TIPO DE RELATÓRIO	
				
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	        	
		if(type.equals("1")) {
		
		//Table Fields
		 build.fields = new String[]{build.localeLabels.getStringKey("speed_reports_table_date"), build.localeLabels.getStringKey("speed_reports_table_time"),
				build.localeLabels.getStringKey("speed_reports_table_id"), build.localeLabels.getStringKey("speed_reports_table_speed"), 
				build.localeLabels.getStringKey("speed_reports_table_direction"), build.localeLabels.getStringKey("speed_reports_table_equipment")};
		
		// Table Objects
		 build.fieldObjectValues = new String[] {"date", "time", "register", "speed", "direction", "equipment"};
		 
		}
		
       // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  		          	
		     // DESENHAR TABLE
		     build.drawTable(build.columns, build.fields);
		 
			// GUARDAR VALORES NA SESSÃO		     
		     SessionUtil.setParam("fieldsLength", build.fields.length); //Length of Fields
		     SessionUtil.setParam("fields", build.fields);	//Fields
		    // SessionUtil.setParam("jsonFields", build.jsonFields);	//Fields
		   //  SessionUtil.setParam("fieldsObject", build.fieldObjectValues); //Objects
		
	  }
	
   // ----------------------------------------------------------------------------------------------------------------
  	
	 /**
	  * Método parar processar os dados de um relatório
	  * @author Wellington 07/10/2021
	  * @version 1.0
	  * @since version 1.0	
	 */	 
	 public void getReport(String type){
		 
		     resetForm(); // RESET FORM BEFORE FILL ANOTHER  
		     		     		     
		     // VARIABLES	   
							 
		   
		        // GET REQUEST PARAMS
		  		   				    	
		    	try {	   	
		    								
		    	/* GET MULTIPLE PARAMS */
		    		
		    	select.isMulti = false; // DEFAULT FALSE
		    		
				select.equipments = SessionUtil.getRequestParameterValuesMap().get("equips"); // EQUIPMENTS PARAM ARRAY				
				//select.vehicles = SessionUtil.getRequestParameterValuesMap().get("vehicles"); // VEHICLES PARAM ARRAY						
				//select.axles = SessionUtil.getRequestParameterValuesMap().get("axles"); // AXLES PARAM ARRAY		
				//select.classes = SessionUtil.getRequestParameterValuesMap().get("classes"); // CLASSES PARAM ARRAY
			
				/* GET SINGLE PARAMS */	
				
				//select.equipment = SessionUtil.getRequestParameterMap().get("equip"); // EQUIPMENT PARAM
				select.startDate = SessionUtil.getRequestParameterMap().get("dateStart"); // START DATE PARAM
				select.endDate = SessionUtil.getRequestParameterMap().get("dateEnd"); // END DATE PARAM
				//select.period = SessionUtil.getRequestParameterMap().get("periods"); // PERIODS PARAM
				//select.month = SessionUtil.getRequestParameterMap().get("month"); // MONTH PARAM				
				//select.year = SessionUtil.getRequestParameterMap().get("year"); // YEAR PARAM							
				select.jsTableId = SessionUtil.getRequestParameterMap().get("jsTable"); // JS TABLE ID PARAM		
				select.jsTableScrollHeight = SessionUtil.getRequestParameterMap().get("jsTableScrollHeight"); // JS TABLE SCROLL HEIGHT PARAM
				select.fieldLength = (int) SessionUtil.getExternalContext().getSessionMap().get("fieldsLength");
				
				// CHECK IF IS MULTI SELECT EQUIP
				if(SessionUtil.getRequestParameterMap().get("isMulti") != null)				
			        select.isMulti =  Boolean.valueOf(SessionUtil.getRequestParameterMap().get("isMulti")); // IF IS NOT NULL CHANGE THE VALUE
							
		    	} catch (IOException e) {
		    		
		    		StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));
															    		
		    		LogUtils.logError(LogUtils.fileDateTimeFormatter(ioExceptionLog), classLocation, e.getMessage(), errors.toString());
		    					
				}
		    	
		    	// --------------------------------------------------------------------------------------------------------------------------
		    	
		    	// RESULT LIST TO OUTPUT DATA
		    	resultList = new ArrayList<Builder>();
		    			    	
		        if(select.isMulti)
		        	processMultiData(build, select, type);
		        		    		    	
		 
	          }     
		  
	   // ----------------------------------------------------------------------------------------------------------------
	   // ----------------------------------------------------------------------------------------------------------------
	   // QUERY BUILD
	   // ----------------------------------------------------------------------------------------------------------------
		 		 
		 /**
		  * Método para seleção de queries por tipo de relatório
		  * @author Wellington 13/10/2021
		  * @version 1.0
		  * @since 1.0
		  * @param type - id do relatório
		  * @param model - objeto da classe SQLModel (modelo de query a ser selecionado)
		  * @param main - corpo principal da query
		  * @param model - objeto da classe ReportSelection
		  * @return query a ser executada
		  */
		  public String selectQueryByTypeMulti(String type, SQLModel model, SpeedQueryBody main, ReportSelection select, int index) { 
			
			String query = "";
			
			switch(type) {
			
			//case "1": query = model.BuildSpeedQuery(DATE_COLUMN, main.SpeedRecordsBody(), TABLE, INDEX_NAME, MODULE, JOIN_COLUMN, JOIN_COLUMN, select.startDate, select.endDate, EQUIP_COLUMN, select.equipments[index]); break;			
			
			default: query = ""; break;
						
			}
			
			return query;			
		}
		
	    // ----------------------------------------------------------------------------------------------------------------
		// ----------------------------------------------------------------------------------------------------------------
		// RESET FORM
		// ----------------------------------------------------------------------------------------------------------------
				
	    /**
		 * Método para resetar valores de formulários nos relatórios
		 * @author Wellington 07/10/2021
		 * @version 1.1
		 * @since version 1.0	
		 */
		 public void resetForm() {
			
			// Limpa valores da sessão
			build.resetReportValues();
			
			// Reinicializa valores armazenados nas variáveis abaixo
			build = new ReportBuild();
			select = new ReportSelection();
			
			if(!resultList.isEmpty())
			     resultList.clear();
			
		}	
		 
	  // ----------------------------------------------------------------------------------------------------------------
	  // ----------------------------------------------------------------------------------------------------------------
	  // PROCESS DATA
      // ----------------------------------------------------------------------------------------------------------------
		 
		    /**
			 * Método para o processamento de dados
			 * @author Wellington 19/10/2021
			 * @version 1.0
			 * @since version 1.0
			 * @param build - objeto da classe report build
			 * @param select - objeto da classe report select
			 */
			public void processData(ReportBuild build, ReportSelection select, String type, String query, String period, int periodRange, String tableId, String scrollHgt) {
				
				NewReportsDAO dao = new NewReportsDAO(); // GLOBAL REPORT DAO	
				DateTimeApplication dta = new DateTimeApplication(); // DATE TIME APPLICATION
				SQLModel model = new SQLModel(); // SQL MODEL TEMPLATE
				SpeedQueryBody speedBody = new SpeedQueryBody(); // SPEED BODY QUERY
				 
				String startDate = null, endDate = null, data_anterior = null; // DATES VARS
				
		        // GET REQUEST PARAMS	
		    	
		    	// OBTER O NÚMERO DE REGISTROS (PERIODO E DATAS SELECIONADAS)	
		    	// REGISTERS A SER UTILIZADO PARA RECEBER DADOS DO BANCO DE DADOS
				ReportBuild.numRegisters = dta.RegistersNumbers(select.startDate, select.endDate, select.period);
							
				try {
				
			    // DIFERENÇA DE DIAS 
				// INTERLAVO DE DIAS ENTRA AS DUAS DATAS SELECIONADAS	
				if(select.startDate != null && select.endDate != null)
					
					build.daysCount = ((int) dta.diferencaDias(select.startDate, select.endDate) + 1);
					
				} catch (ParseException e) {
						
						e.printStackTrace();
				}

				    // INTERVALO POR PERIODO (INTERVALO PARA INCREMENTO DE VALORES)
				    build.breakTime = dta.periodsRange(select.period);
				    
				   // ---------------------------------------------------------------------------				    
					
					try {
						
						startDate = dta.StringDBDateFormatInitial(select.startDate);						
					    endDate = dta.StringDBDateFormatEnd(select.endDate);
										
					} catch (ParseException e) {
						
						e.printStackTrace();
					}
					
				  // ---------------------------------------------------------------------------		
					
					// VARIÁVEL PARA RECEBER DADOS DO BANCO DE DADOS			
					build.resultQuery = new String[ReportBuild.numRegisters][ReportBuild.fieldsNumber];
									
					try {
						
						// GO DADDY
				      //  build.query = selectQueryByType(type, model, speedBody, select);
						
						String[][] auxResult = dao.ExecuteSQLQuery(build.query);
										
						if(auxResult.length > 0) {
							
							 // PROCESS DATA
					    	 ReportUtil.executeProcessData(build, dta, auxResult, build.resultQuery, period, startDate, periodRange);
												
					    	 // OUTPUT DATA
					    	 outPutData(type);
					    	 							
					         // EXCEL OUTPUT
					    	 outPutExcel(type);
					  
					    	 build.clearBool = false; // BOTÃO DE LIMPAR  	 
					      	 build.excelBool = false; // LINK DE DOWNLOAD DO EXCEL
					    	 build.chartBool = false; // LINK PARA ACESSAR O GRÁFICO
																				
							// UPDATE RESET BUTTON VIEW
							RequestContext.getCurrentInstance().update("form-btns:#btn-tab-reset");

							// UPDATE BUTTON GENERATE EXCEL VIEW
							RequestContext.getCurrentInstance().update("form-excel:#excel-act");	
							
							// UPDATE TABLE JQUERY ON RELOAD PAGE VIEW
							RequestContext.getCurrentInstance().execute("drawTable('#"+tableId+"', '"+scrollHgt+"');");	
							
							} else {
								
								  // EXECUTE JS
								  RequestContext.getCurrentInstance().execute("hideMessage();");
								  
								  // UPDATE TABLE JQUERY ON RELOAD PAGE
								  RequestContext.getCurrentInstance().execute("drawTable('#"+tableId+"', '"+scrollHgt+"'); showMessage();");	
							  }
														
					} catch (Exception e) {						
						e.printStackTrace();
					}
		 
			}
			
			// ----------------------------------------------------------------------------------------------------------------
			
		       /**
				 * Método para o processamento de dados
				 * @author Wellington 19/10/2021
				 * @version 1.0
				 * @since version 1.0
				 * @param build - objeto da classe report build
				 * @param select - objeto da classe report select
				 */
				public void processMultiData(ReportBuild build, ReportSelection select, String type) {
					
					NewReportsDAO dao = new NewReportsDAO();	// GLOBAL REPORT DAO	
					DateTimeApplication dta = new DateTimeApplication(); // DATE TIME APPLICATION
					SQLModel model = new SQLModel(); // SQL MODEL TEMPLATE
					SpeedQueryBody speedBody = new SpeedQueryBody(); // SPEED BODY QUERY
					 
					String startDate = null, endDate = null, lastDate = null, lastEquip = null; // DATES VARS
					
			        // GET REQUEST PARAMS	
			    	
			    	// OBTER O NÚMERO DE REGISTROS (PERIODO E DATAS SELECIONADAS)	
			    	// REGISTERS A SER UTILIZADO PARA RECEBER DADOS DO BANCO DE DADOS
					ReportBuild.numRegisters = dta.RegistersNumbers(select.startDate, select.endDate, select.period);
					
					// MULTI EQUIP DEFINITION
					ReportBuild.numRegisters = ReportBuild.numRegisters * select.equipments.length;
					              								
					try {
					
				    // DIFERENÇA DE DIAS 
					// INTERLAVO DE DIAS ENTRA AS DUAS DATAS SELECIONADAS	
					if(select.startDate != null && select.endDate != null)
						
						build.daysCount = ((int) dta.diferencaDias(select.startDate, select.endDate) + 1);
						
					} catch (ParseException e) {
							
							e.printStackTrace();
					}

					    // INTERVALO POR PERIODO (INTERVALO PARA INCREMENTO DE VALORES)
					    build.breakTime = dta.periodsRange(select.period);
					    
					   // ---------------------------------------------------------------------------				    
						
						try {
							
							startDate = dta.StringDBDateFormatInitial(select.startDate);						
						    endDate = dta.StringDBDateFormatEnd(select.endDate);
						  
											
						} catch (ParseException e) {
							
							e.printStackTrace();
						}
						
					   // ---------------------------------------------------------------------------	
						
						// STARTS EQUIP WITH FIRST SELECTION
						lastEquip = select.equipments[0];		
						
						// STARTS EQUIPS SHEET NAME
						equipNames = new String[select.equipments.length];										
						
					    int fields = (int) SessionUtil.getExternalContext().getSessionMap().get("fieldsLength"); // Length of Fields
					    
					    ReportBuild.fieldsNumber = ReportUtil.totalFieldsNumber(select, fields, true);
						
						// VARIÁVEL PARA RECEBER DADOS DO BANCO DE DADOS			
						build.resultQuery = new String[ReportBuild.numRegisters][ReportBuild.fieldsNumber];
												
						// NOME DOS EQUIPAMENTOS
					    dta.fillEquipName(listSpeed, build.resultQuery, select.equipments, equipNames, 0, 2, ReportBuild.numRegisters, build.breakTime, build.daysCount, startDate);
						
					    // PREENCHER DATA E HORA
					    ReportUtil.initializeDataInterval(build.resultQuery, select.period, build.breakTime, startDate);
					    
					    int pos = 0;
						int empty = 0;
						int inc = 0;
						int iterator = 0;
											
						// COUNTER TO RUN ARRAY
						int counter = build.daysCount * build.breakTime;
					    					    
					    try {
							
						  for(int k = 0; k < select.equipments.length; k++) {
							  
						    // GO DADDY
						    build.query = selectQueryByTypeMulti(type, model, speedBody, select, k);
							
							String[][] auxResult = dao.ExecuteSQLQuery(build.query);
											
							if(auxResult.length > 0)
																					 
						    	 ReportUtil.executeMultiProcessData(build,dta, auxResult, build.resultQuery, select.period, startDate, endDate, lastDate, lastEquip, build.breakTime, iterator, counter, pos, inc); // PROCESS DATA						    	 															
						    									
								 else {
									
									pos += counter;
									empty++;
																		
								  }								
							  } // LOOP END
						  
						  // -------------------------------------------------------------------------------------------------------------------------------
						  
							if(empty < select.equipments.length) {
								
								 // OUTPUT DATA
						    	 outPutData(type);
						    	 							
						        // EXCEL OUTPUT
						    	// outPutExcel(type);
						  
						    	 build.clearBool = false; // BOTÃO DE LIMPAR		    	 
						      	 build.excelBool = false; // LINK DE DOWNLOAD DO EXCEL
						    	 build.chartBool = false; // LINK PARA ACESSAR O GRÁFICO	
																					
								// UPDATE RESET BUTTON VIEW
								RequestContext.getCurrentInstance().update("form-btns:#btn-tab-reset");

								// UPDATE BUTTON GENERATE EXCEL VIEW
								RequestContext.getCurrentInstance().update("form-excel:#excel-act");	
								
								// UPDATE TABLE JQUERY ON RELOAD PAGE VIEW
								RequestContext.getCurrentInstance().execute("drawTable('#"+select.jsTableId+"', '"+select.jsTableScrollHeight+"');");	
								
							}else {
								
								  // EXECUTE JS
								  RequestContext.getCurrentInstance().execute("hideMessage();");
								  
								  // UPDATE TABLE JQUERY ON RELOAD PAGE
								  RequestContext.getCurrentInstance().execute("drawTable('#"+select.jsTableId+"', '"+select.jsTableScrollHeight+"'); showMessage();");	
								
							}
															
						} catch (Exception e) {						
							e.printStackTrace();
						}
			 
				}
					
		// ----------------------------------------------------------------------------------------------------------------
	    // ----------------------------------------------------------------------------------------------------------------
		// OUTPUT DATA VIEW	
	    // ----------------------------------------------------------------------------------------------------------------
			
			public void outPutData(String type) {
				
				//FIELDS EXTERNOS ARMAZENADOS NA REQUISIÇÃO
				build.fields = (String[]) SessionUtil.getExternalContext().getSessionMap().get("fields");
				build.fieldObjectValues = (String[]) SessionUtil.getExternalContext().getSessionMap().get("fieldsObject");
				
				switch(type) {
				
				case "1": SpeedReportBuilder.speedRecords(resultList, build.resultQuery); break;     
								       
				// ----------------------------------------------------------------------------------------------------------------
				
				}		
				
				  // DRAW TABLEEEEE
		          build.drawTable(build.columns, build.fields);
			
			  }
			
			// ----------------------------------------------------------------------------------------------------------------
			// OUTPUT EXCEL	
		    // ----------------------------------------------------------------------------------------------------------------
						
			public void outPutExcel(String type) {
				
				EquipmentsDAO dao = new EquipmentsDAO();
				DateTimeApplication dta = new DateTimeApplication();
								
				// INFO TWO-DIMENSIONAL ARRAY 
				String[][] info = new String[select.equipments.length][4];	
											
				 try {
					 
				switch(type) {
				
				case "1":
						
					info = dao.multiGenericInfo(select.equipments, MODULE);	
					
					build.fileName = "";								 
					
				 }
				
				} catch (Exception e) {					
						e.printStackTrace();
			  }	
				 
				 // DEFINIR VALORES NA SESSÃO
				 SessionUtil.getExternalContext().getSessionMap().put("datetime", dta.currentDateToFile());
				 SessionUtil.getExternalContext().getSessionMap().put("fileName", build.fileName);
					
			}	
			
		   // ----------------------------------------------------------------------------------------------------------------
		   // ----------------------------------------------------------------------------------------------------------------
		   // DOWNLOAD FILE
		   // ----------------------------------------------------------------------------------------------------------------
		 
			
			public void download() {
				
				ExcelTemplate template = new ExcelTemplate();
				
				// MANTER VALORES NA SESSÃO
				String fileDate = (String) SessionUtil.getExternalContext().getSessionMap().get("datetime");
				String fileName = (String) SessionUtil.getExternalContext().getSessionMap().get("fileName");
				
				String file = fileName+"_"+fileDate;
							
				try {
					
					template.download(file);
					
				} catch (IOException e) {	
					
					e.printStackTrace();
				}
				
			}
			
		// ----------------------------------------------------------------------------------------------------------------
			
}
