	package br.com.tracevia.webapp.controller.sos;

	import java.io.BufferedWriter;
	import java.io.ByteArrayOutputStream;
	import java.io.IOException;
	import java.util.ArrayList;
	import java.util.List;
	import java.util.Map;

	import javax.annotation.PostConstruct;
	import javax.faces.bean.ManagedBean;
	import javax.faces.bean.RequestScoped;
	import javax.faces.context.ExternalContext;
	import javax.faces.context.FacesContext;
	import javax.faces.model.SelectItem;

	import org.primefaces.context.RequestContext;

	import br.com.tracevia.webapp.dao.global.EquipmentsDAO;
	import br.com.tracevia.webapp.dao.sos.SOSMainQueries;
	import br.com.tracevia.webapp.dao.sos.SOSQueryModels;
	import br.com.tracevia.webapp.dao.sos.SOSReportDAO;
	import br.com.tracevia.webapp.methods.DateTimeApplication;
	import br.com.tracevia.webapp.methods.ExcelModels;
	import br.com.tracevia.webapp.model.global.ColumnModel;
	import br.com.tracevia.webapp.model.global.Equipments;
	import br.com.tracevia.webapp.model.global.RoadConcessionaire;
	import br.com.tracevia.webapp.model.sos.SOS;
	import br.com.tracevia.webapp.model.sos.SOSReports;
	import br.com.tracevia.webapp.util.LocaleUtil;
	import br.com.tracevia.webapp.util.QueriesReportsModels;

	@ManagedBean(name="sosAlarmBean")
	@RequestScoped
	public class SOSAlarmReport  {
		
		private SOSReports sosReport;
		private List<SelectItem> equipments;  
		private List<SOSReports> resultList;
		private List<String> header;  
		private List<ColumnModel> columns;  
		List<? extends Equipments> listSOS;  
		
		//Locale Docs
		LocaleUtil localeLabel, localeCalendar, localeDir, localePeriods;  

		int periodRange, daysInMonth, daysCount; 
		
		String jsTableId, jsTableScrollHeight;

		String[] fields, jsonFields, fieldObjectValues, fieldsAux, fieldObjAux; //Nome dos campos // Valores de cada campo -> Atribuidos a variavis do modelo  

		String[][] resultQuery;
		
		String procedure, query, queryCount, module, fileName, currentDate, direction1, direction2, directionLabel1, directionLabel2, 
		directionValue1, directionValue2, equipId; 
		
		//Dates
		String start, end;

		BufferedWriter writer;  
		ByteArrayOutputStream byteWriter; 

		EquipmentsDAO equipDAO;
		ExcelModels model;
			
		String displayEquipInfo, displayDirection1, displayDirection2;
		
		// Equipments
		String[] equips;

		private boolean clearBool, excelBool, chartBool;

		public SOSReports getSosReport() {
			return sosReport;
		}

		public void setSosReport(SOSReports sosReport) {
			this.sosReport = sosReport;
		}

		public List<SelectItem> getEquipments() {
			return equipments;
		}

		public List<SOSReports> getResultList() {
			return resultList;
		}

		public List<String> getHeader() {
			return header;
		}

		public List<ColumnModel> getColumns() {
			return columns;
		}
		
		public String getDirectionLabel1() {
			return directionLabel1;
		}

		public void setDirectionLabel1(String directionLabel1) {
			this.directionLabel1 = directionLabel1;
		}

		public String getDirectionLabel2() {
			return directionLabel2;
		}

		public void setDirectionLabel2(String directionLabel2) {
			this.directionLabel2 = directionLabel2;
		}

		public String getDirectionValue1() {
			return directionValue1;
		}

		public void setDirectionValue1(String directionValue1) {
			this.directionValue1 = directionValue1;
		}

		public String getDirectionValue2() {
			return directionValue2;
		}

		public void setDirectionValue2(String directionValue2) {
			this.directionValue2 = directionValue2;
		}

		public String getEquipId() {
			return equipId;
		}

		public void setEquipId(String equipId) {
			this.equipId = equipId;
		}

		public boolean isClearBool() {
			return clearBool;
		}

		public void setClearBool(boolean clearBool) {
			this.clearBool = clearBool;
		}

		public boolean isExcelBool() {
			return excelBool;
		}

		public void setExcelBool(boolean excelBool) {
			this.excelBool = excelBool;
		}
			
		public boolean isChartBool() {
			return chartBool;
		}

		public void setChartBool(boolean chartBool) {
			this.chartBool = chartBool;
		}
				
		public String getDisplayEquipInfo() {
			return displayEquipInfo;
		}

		public void setDisplayEquipInfo(String displayEquipInfo) {
			this.displayEquipInfo = displayEquipInfo;
		}
		
		
		public String getDisplayDirection1() {
			return displayDirection1;
		}

		public void setDisplayDirection1(String displayDirection1) {
			this.displayDirection1 = displayDirection1;
		}

		public String getDisplayDirection2() {
			return displayDirection2;
		}

		public void setDisplayDirection2(String displayDirection2) {
			this.displayDirection2 = displayDirection2;
		}
		 
		@PostConstruct
		public void initialize() {

			localeLabel = new LocaleUtil();	
			localeLabel.getResourceBundle(LocaleUtil.LABELS_SOS);

			localeCalendar = new LocaleUtil();	
			localeCalendar.getResourceBundle(LocaleUtil.LABELS_CALENDAR);
			
			localePeriods = new LocaleUtil();	
			localePeriods.getResourceBundle(LocaleUtil.LABELS_PERIODS);
			
			// MODULE
			module = "sos";		
				
			/* EQUIPMENTS SELECTION */	
			
			sosReport = new SOSReports();	
			equipments = new ArrayList<SelectItem>();		
			
			listSOS = new ArrayList<SOS>();

			try {

				EquipmentsDAO dao = new EquipmentsDAO();	
				
				listSOS = dao.EquipmentSelectOptions(module);

				sosReport.equipments = new String[listSOS.size()];
									
			} catch (Exception e1) {			
				e1.printStackTrace();
			}

			for (Equipments e : listSOS) {
				
				SelectItem s = new SelectItem();
							
				s.setValue(e.getEquip_id());
				s.setLabel(e.getNome());
				
				equipments.add(s);				
			}
			
			//Disabled
			clearBool = true;
			excelBool = true;
		
		}
		
		///////////////////////////////////
		//CREATE REPORTS
		/////////////////////////////////  

		// ----------------------------------------------------------------------------------------------------------------------------
		
	    //////DESENHAR TABLES 	

		/**
		 * Mtodo para criar os headers
		 * @param field - headers
		 * @param objectValue - Valores de cada header estanciados em objetos
		 */
		public void drawTable(String[] field, String[] objectValue) {

			columns = new ArrayList<ColumnModel>();

			for(int i = 0; i < field.length; i++)
				columns.add(new ColumnModel(field[i], objectValue[i]));		

		}

		
		// ----------------------------------------------------------------------------------------------------------------------------
		
		//////DESENHAR TABLES 	
		
		public void CreateFields() {

			FacesContext facesContext = FacesContext.getCurrentInstance();

			// -----------------------------------------------------------------------------
										
			// SOS ALARMS REPORT
										
			//Table Fields
			 fields = new String[]{localeLabel.getStringKey("sos_report_column_date"), 	
					 localeLabel.getStringKey("sos_report_column_type"), 
					 localeLabel.getStringKey("sos_report_column_opened"),
					 localeLabel.getStringKey("sos_report_column_closed"),
					 localeLabel.getStringKey("sos_report_column_time")};
			
			// Table Objects
			 fieldObjectValues = new String[] {"date", "sosStatusType", "sosStatusOpened", "sosStatusClosed", "sosStatusTime"};
									
		  // -----------------------------------------------------------------------------
									
		  //DESENHAR A TABELA
		  drawTable(fields, fieldObjectValues);	

						//GUARDAR VALORES NA SESSION
						facesContext.getExternalContext().getSessionMap().put("fieldsLength", fields.length); //Length of Fields
						facesContext.getExternalContext().getSessionMap().put("fields", fields);	//Fields
						facesContext.getExternalContext().getSessionMap().put("jsonFields", jsonFields);	//Fields
						facesContext.getExternalContext().getSessionMap().put("fieldsObject", fieldObjectValues); //Objects
					
		        }
		
		         // ----------------------------------------------------------------------------------------------------------------------

		  ////BUILD REPORTS
		
		// REPORTS MODELS
		// Recebe uma String que define qual o reltorio vai ser chamado

		/**
		 * Mtodo par criar um relatrio de acordo com tipo
		 * @param type tipo do relatrio 
		 * @throws Exception
		 */	
		public void GetReports() throws Exception{
			
			//RESET ON RESTART
		    resetFormValues();

			//Get external application contents
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();

			SOSQueryModels models = new SOSQueryModels(); //QuerieReportsModel class
			SOSMainQueries sosModels = new SOSMainQueries(); // SOS MODEL QUERIE	    
			DateTimeApplication dta = new DateTimeApplication(); //DateTimeApsplication class

			SOSReportDAO dao = new SOSReportDAO();	//GlobalReportsDAO
					
			String startDate = null, endDate = null;
						
			/*** Obter parmetros que vem no submit de cada pesquisa ***/

			//Get request values for multiple fields selection
			Map<String, String[]> parameterMap = (Map<String, String[]>) externalContext.getRequestParameterValuesMap();

			//Get Request single values
			Map<String, String> parameterMap2 = (Map<String, String>) externalContext.getRequestParameterMap();

			/**** Multiple Params ***/
			
			//param for equipments array
			equips = parameterMap.get("equips");

			// ---------------------------------------------------------------------------------------------------------------------------

			/**** Single Params ***/

			//param for equipment
			sosReport.setEquipment(parameterMap2.get("equip"));
			
			//param for periods
			sosReport.setPeriod(parameterMap2.get("periods"));

			//param for dateStart
			sosReport.setStartDate(parameterMap2.get("dateStart"));

			//param for dateEnd
			sosReport.setEndDate(parameterMap2.get("dateEnd"));
						
			//Table id
			jsTableId = parameterMap2.get("jsTable");
			
			//Table Scroll Height 
			 jsTableScrollHeight = parameterMap2.get("jsTableScrollHeight");
			
			/**** Single Params ***/

			//Initialize ResultList
			//Builder class -> for construct objects
			resultList = new ArrayList<SOSReports>();
			
			int pos = 0;
			

	       // -------------------------------------------------------------------------------------------------------------------------
				 
			startDate = dta.StringDBDateFormat(sosReport.getStartDate());
			endDate = dta.StringDBDateFormat(sosReport.getEndDate());
						
			start = dta.DateTimeToStringIni(startDate); 
			end = dta.DateTimeToStringFim(endDate);
			
			 String query = SOSAlarmRegister(models, sosModels.AlarmsStatus(sosReport.getEquipment()), QueriesReportsModels.USE_INDEX_IDX_SOS);
			 
			// System.out.println(query);
			 		 
			 resultList = dao.callAlarms(query);
			 
			 if(!resultList.isEmpty()) {
				 
			 fields = (String[]) externalContext.getSessionMap().get("fields");
			 		 
			 resultQuery = new String[resultList.size()][fields.length];
			 
			 for(SOSReports sos : resultList) {
				 								
					resultQuery[pos][0] = sos.getDate();
					resultQuery[pos][1] = sos.getSosStatusType();
					resultQuery[pos][2] = sos.getSosStatusOpened();
					resultQuery[pos][3] = sos.getSosStatusClosed();				
					resultQuery[pos][4] = sos.getSosStatusTime();
							
					pos++;
												
			 }
			 	 		
			 
				// DRAW TABLE -- BUILD HEADER
								
		   // ---------------------------------------------------------------------------------------------------------------------------
					
				
			    //// NEW METHOD

				//SADA PARA A TABELA
				reBuildTable();
				
				//SADA DO EXCEL
				ExcelOutPut();

				//BOTO DE LIMPAR 
				setClearBool(false);

				//LINK DE DOWNLOAD DO EXCEL
				setExcelBool(false);	
																	
				//UPDATE RESET BUTTON
				RequestContext.getCurrentInstance().update("form-btns:#btn-tab-reset");

				//UPDATE BUTTON GENERATE EXCEL
				RequestContext.getCurrentInstance().update("form-excel:#excel-act");	
				
				//UPDATE TABLE JQUERY ON RELOAD PAGE
				RequestContext.getCurrentInstance().execute("drawTable('#"+jsTableId+"', '"+jsTableScrollHeight+"');");	
									    					
				//CASO CONTRARIO ENTRA AQUI
				
			} else {
						    
			      // EXECUTE JS
				  RequestContext.getCurrentInstance().execute("hideMessage();");
					  
			      // UPDATE TABLE JQUERY ON RELOAD PAGE
			      RequestContext.getCurrentInstance().execute("drawTable('#"+jsTableId+"', '"+jsTableScrollHeight+"'); showMessage();");	
					  			     											
		    }
			
	     }

		
		//// BUILD REPORTS
		
	   // ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	    /**
	     * @author Wellington 20/08/2021
	     * Método para criar montar uma query a ser executada
	     * @param models - Objeto do tipo QuerieReportsModels
	     * @param mainQuery - Query principal a ser adicionada
	     * @param index - Index a ser usada na query
	     * @return
	   */

	    public String SOSAlarmRegister(SOSQueryModels models, String mainQuery, String index) {    	 

	 	String query = null;

	 	query = models.BuildSOSQueryBase(models.SOSSingleAlarmsQueryHeader(), mainQuery, models.SOSAlarmsFromTable(), models.UseIndex(index),
	 			models.WhereClauseSOSAlarms(start, end, sosReport.getEquipment()),  models.SOSSingleAlarmsQueryGroupAndOrderBy());
	 	
	 	return query;
	 	
	    }     
	     
	  // ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	    /////// RESET FORM VALUES
		
			public void resetFormValues() {
				
				FacesContext facesContext = FacesContext.getCurrentInstance();	
				ExternalContext externalContext = facesContext.getExternalContext();

				//Reset object => call on click reset button
				sosReport = new SOSReports();
							
				externalContext.getSessionMap().remove("xlsModel");
				externalContext.getSessionMap().remove("current");
				externalContext.getSessionMap().remove("fileName");		
				externalContext.getSessionMap().remove("fields");
				externalContext.getSessionMap().remove("jsonFields");
				externalContext.getSessionMap().remove("fieldsObject");

				// Fields again
				CreateFields();
				
			}

			 // ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

			
			// PREENCHER SAIDA DE DADOS 
			// POR TIPO DE RELATRIO
			public void reBuildTable() {  

				//ACESSAR DADOS DO RELATRIOF
				FacesContext facesContext = FacesContext.getCurrentInstance();
				ExternalContext externalContext = facesContext.getExternalContext();

				//FIELDS EXTERNOS ARMAZENADOS NA REQUISIO
				fields = (String[]) externalContext.getSessionMap().get("fields");
				fieldObjectValues =  (String[]) externalContext.getSessionMap().get("fieldsObject");
									 
				// DRAW TABLE -- BUILD HEADER
				drawTable(fields, fieldObjectValues);

				}		
	    
			 // ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
			
			public void ExcelOutPut() throws Exception {
				
				FacesContext facesContext = FacesContext.getCurrentInstance();		

				DateTimeApplication dta = new DateTimeApplication();
			
				//Initialize Excel Class Object
				//Excel Model Class
				model = new ExcelModels(); 
				
				EquipmentsDAO dao = new EquipmentsDAO();  // EquipmentsDAO Class
				Equipments info = new Equipments(); // SAT Class
				
				String equip = "", road = "", km = "", lanes = "", city = "";  //Equipment Info
				
				
				// Get equipment values from DB			
				info = dao.GenericInfo(module, sosReport.getEquipment());
				
				//Define fileName
				String fileName = localeLabel.getStringKey("sos_excel_file_name_alarms"); // File Name
				String title = localeLabel.getStringKey("sos_excel_file_title_alarms"); // File Title
				String sheetName = localeLabel.getStringKey("sos_excel_file_sheet_name_alarm"); // File sheetName

				String[] mergedCells = new String[] {"A1:B4", "C1:H4", "I1:J4"}; // Merge cells
				
				int[] col = new int[] {3500, 4500, 4000, 4000, 4000, 4000, 4000, 4000}; //SET size to columns

				int dateStartCell = 8, dateEndCell = 9; //Col ini & end date

				//Equipment Info
				equip = info.getNome();
				road = info.getEstrada(); 
				km = info.getKm(); 
			    city = info.getCidade();
			    lanes = " ---- ";

				model.StandardFonts();  //Set Font
				model.StandardStyles(); //Set Style
				model.StandardBorders(); // Set Borders
				
				model.defaultStringReport(fields, RoadConcessionaire.externalImagePath, title, sheetName, equip, city, road, km, lanes, sosReport.getStartDate(), sosReport.getEndDate(), mergedCells, col, dateStartCell, dateEndCell, resultQuery);
						
				//Define Values in session map !important
				facesContext.getExternalContext().getSessionMap().put("xlsModel", model); 
				facesContext.getExternalContext().getSessionMap().put("current", dta.currentTime());
				facesContext.getExternalContext().getSessionMap().put("fileName", fileName); 
			}
		
			 // ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

			
			public void download() {

				FacesContext facesContext = FacesContext.getCurrentInstance();	
				ExternalContext externalContext = facesContext.getExternalContext();

				model = (ExcelModels) externalContext.getSessionMap().get("xlsModel");
				currentDate = (String) externalContext.getSessionMap().get("current");
				fileName = (String) externalContext.getSessionMap().get("fileName");

				String name = fileName+"_"+currentDate;
					
				try {
					
					model.download(ExcelModels.workbook, name);
					
				} catch (IOException e) {			
					e.printStackTrace();
				}
				finally {
					//externalContext.getSessionMap().remove("xlsModel");
					//externalContext.getSessionMap().remove("current");
					//externalContext.getSessionMap().remove("fileName");					
				}

			}      
	    


}
