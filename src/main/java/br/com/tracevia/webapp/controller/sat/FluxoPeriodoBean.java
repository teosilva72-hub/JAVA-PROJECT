package br.com.tracevia.webapp.controller.sat;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;

import br.com.tracevia.webapp.controller.global.LoginAccountBean;
import br.com.tracevia.webapp.dao.global.EquipmentsDAO;
import br.com.tracevia.webapp.dao.sat.FluxoPeriodoDAO;
import br.com.tracevia.webapp.log.SystemLog;
import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.model.global.ColumnModel;
import br.com.tracevia.webapp.model.global.ListEquipments;
import br.com.tracevia.webapp.model.global.ReportBuild;
import br.com.tracevia.webapp.model.sat.FluxoVeiculos;
import br.com.tracevia.webapp.model.sat.FluxoVeiculos.Builder;
import br.com.tracevia.webapp.util.LocaleUtil;
import br.com.tracevia.webapp.util.SessionUtil;

@ManagedBean(name="fluxoPeriodo")
@RequestScoped
public class FluxoPeriodoBean {
	
	    // --------------------------------------------------------------------------------------------------------------
	
		private final String errorFolder = SystemLog.ERROR.concat("period-flow-bean\\");
				
		// --------------------------------------------------------------------------------------------------------------
			
		private List<SelectItem> equipments;
		private List<SelectItem> periods;
		private List<SelectItem> months;
		private List<SelectItem> years;
		private List<ColumnModel> columns;  
			
		private List<Builder> resultList;	
		
		private ReportBuild build;
		
		LocaleUtil localeSat, localeDir, localeReports, localeSheet, localeCalendar;
			
		int length, minLen, daysInMonth;
		
		String[] equip;

		String period, month, year;
				
		@ManagedProperty("#{loginAccount}")
		private LoginAccountBean login;
		
		@ManagedProperty("#{listEquips}")
		private ListEquipments listEquips;
		
		public LoginAccountBean getLogin() {
			return login;
		}
		
		public void setLogin(LoginAccountBean login) {
			this.login = login;
		}
		
		public ListEquipments getListEquips() {
			return listEquips;
		}
		
		public void setListEquips(ListEquipments listEquips) {
			this.listEquips = listEquips;
		}
			
		public ReportBuild getBuild() {
			return build;
		}

		public void setBuild(ReportBuild build) {
			this.build = build;
		}
		
		public List<SelectItem> getEquipments() {
			return equipments;
		}
		
		public List<SelectItem> getMonths() {
			return months;
		}
			
		public List<SelectItem> getPeriods() {
			return periods;
		}

		public List<SelectItem> getYears() {
			return years;
		}
		
		public List<ColumnModel> getColumns() {
			return columns;
		}
		
		public List<Builder> getResultList() {
			return resultList;
		}	

		// ----------------------------------------------------------------------------------------------------------------------------------------------------- 
		
		@PostConstruct
		public void init() {
			
			localeDir = new LocaleUtil();
			localeDir.getResourceBundle(LocaleUtil.LABELS_DIRECTIONS);
			
			localeCalendar = new LocaleUtil();
			localeCalendar.getResourceBundle(LocaleUtil.LABELS_CALENDAR);
			
			localeSat = new LocaleUtil();
			localeSat.getResourceBundle(LocaleUtil.LABELS_SAT);
			
			localeReports = new LocaleUtil();
			localeReports.getResourceBundle(LocaleUtil.LABELS_REPORTS);
			
			localeSheet = new LocaleUtil();		
			localeSheet.getResourceBundle(LocaleUtil.LABELS_EXCELSHEET);
							
		    build = new ReportBuild();
			
			equipments = new ArrayList<SelectItem>();
			months = new ArrayList<SelectItem>();
			years = new ArrayList<SelectItem>();
			
			build.clearBool = true;
			build.excelBool = true;
			
			try {
				
				    // SELECT FIELD VALUES
			
					equipments = build.selectEquips(listEquips.getSatList());
					periods = build.selectPeriods();
					months = build.selectMonth();
					years = build.selectYears();
		
				} catch (Exception ex) {
			
					StringWriter errors = new StringWriter();
					ex.printStackTrace(new PrintWriter(errors));
				
					SystemLog.logError(errorFolder.concat("error_select_item"), EquipmentsDAO.class.getCanonicalName(), ex.getMessage(), errors.toString());
				
				}	      
		   }
		
		// ----------------------------------------------------------------------------------------------------------------------------------------------------- 
		
		public void createFields() {
			
			build.fields = new String[] {localeSat.getStringKey("global_equipment_column"), localeSat.getStringKey("global_date_column"), localeSat.getStringKey("global_interval_column"), 
					localeSat.getStringKey("$labels_period_flow_direction"), localeSat.getStringKey("$labels_period_flow_light_vehicles"), localeSat.getStringKey("$labels_period_flow_commercial_vehicles"), 
					localeSat.getStringKey("$labels_period_flow_motorcycle"),  localeSat.getStringKey("global_total_column"), localeSat.getStringKey("$labels_period_flow_speed"),	localeSat.getStringKey("$labels_period_flow_direction"), 
					localeSat.getStringKey("$labels_period_flow_light_vehicles"), localeSat.getStringKey("$labels_period_flow_commercial_vehicles"), localeSat.getStringKey("$labels_period_flow_motorcycle"),
					localeSat.getStringKey("global_total_column"), localeSat.getStringKey("$labels_period_flow_speed")};

			// Table Object 
			build.fieldObjectValues = new String[] {"equip", "date", "time", "direction1", "light1", "comm1", "moto1", "total1", "speed1", "direction2", "light2", "comm2", "moto2", "total2", "speed2"};
							
			//DESENHAR A TABELA
			columns = build.drawTable(build.fields, build.fieldObjectValues);						

			// GUARDAR VALORES NA SESSION
			SessionUtil.setParam("fields", build.fields);	//Fields
			SessionUtil.setParam("fieldsObject", build.fieldObjectValues); //Objects
			
			
		}
		
		// ----------------------------------------------------------------------------------------------------------------------------------------------------- 
		
		
		 public void getReport() throws Exception {
	 		 
				DateTimeApplication dta = new DateTimeApplication();
					
				List<FluxoVeiculos> lista = new ArrayList<FluxoVeiculos>();
				resultList = new ArrayList<Builder>();
			
				FluxoPeriodoDAO dao = new FluxoPeriodoDAO();
				EquipmentsDAO eqDAO = new EquipmentsDAO();
				
				//FIELDS EXTERNOS ARMAZENADOS NA REQUISIO
				build.fields = (String[]) SessionUtil.getParam("fields");
				build.fieldObjectValues =  (String[]) SessionUtil.getParam("fieldsObject");
						
				 Map<String, String> parameterMap = SessionUtil.getRequestParameterMap();
				 Map<String, String[]> multiParameterMap = SessionUtil.getRequestParameterValuesMap();
					
					 equip = multiParameterMap.get("equips"); // EQUIP	 
					 period = parameterMap.get("periods"); // PERIOD
					 month = parameterMap.get("month"); // MONTH				
					 year = parameterMap.get("year"); // YEAR
									 
					 
		 }
		 
	  // ----------------------------------------------------------------------------------------------------------------------------------------------------- 

}
