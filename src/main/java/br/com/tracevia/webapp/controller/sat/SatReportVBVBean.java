package br.com.tracevia.webapp.controller.sat;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;

import br.com.tracevia.webapp.controller.global.ListEquipments;
import br.com.tracevia.webapp.dao.global.EquipmentsDAO;
import br.com.tracevia.webapp.dao.sat.VehicleByVehicleDAO;
import br.com.tracevia.webapp.log.SystemLog;
import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.methods.TranslationMethods;
import br.com.tracevia.webapp.model.global.ReportBuild;
import br.com.tracevia.webapp.model.sat.VehicleByVehicle;
import br.com.tracevia.webapp.util.LocaleUtil;
import br.com.tracevia.webapp.util.SessionUtil;

@ManagedBean(name="vbvReportBean")
@RequestScoped
public class SatReportVBVBean {
	
	private List<SelectItem> equipments;
	private List<SelectItem> months;
	private List<SelectItem> years;
		
	private VehicleByVehicle veh;	
	private ReportBuild build;
	
	private boolean file = true;
	
	BufferedWriter writer;  
	ByteArrayOutputStream byteWriter; 
	
	LocaleUtil locale;
	
	String equip, month, monthName, name, year; // FIELDS
		
	// --------------------------------------------------------------------------------------------------------------
	
	private final String errorFolder = SystemLog.ERROR.concat("vbv-bean\\");
	
	// --------------------------------------------------------------------------------------------------------------
	
	@ManagedProperty("#{listEquipsBean}")
	private ListEquipments listEquips;
	
	public ListEquipments getListEquips() {
		return listEquips;
	}

	public void setListEquips(ListEquipments listEquips) {
		this.listEquips = listEquips;
	}
	
	public List<SelectItem> getEquipments() {
		return equipments;
	}
	
	public List<SelectItem> getMonths() {
		return months;
	}

	public List<SelectItem> getYears() {
		return years;
	}
		
	public VehicleByVehicle getVeh() {
		return veh;
	}

	public void setVeh(VehicleByVehicle veh) {
		this.veh = veh;
	}
	
	public ReportBuild getBuild() {
		return build;
	}

	public void setBuild(ReportBuild build) {
		this.build = build;
	}
	
	public String getEquip() {
		return equip;
	}

	public void setEquip(String equip) {
		this.equip = equip;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	public boolean isFile() {
		return file;
	}

	public void setFile(boolean file) {
		this.file = file;
	}
	
	public String getMonthName() {
		return monthName;
	}

	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}

	@PostConstruct
	public void initializer() {
		
		SystemLog.createLogFolder(errorFolder); // CREATE FOLDER IF NOT EXISTS
		
		locale = new LocaleUtil();
		locale.getResourceBundle(LocaleUtil.LABELS_REPORTS);
		
		build = new ReportBuild();
		
		equipments = new ArrayList<SelectItem>();
		months = new ArrayList<SelectItem>();
		years = new ArrayList<SelectItem>();
		
		build.textBool = true;
		
		try {			
				// SELECT FIELD VALUES
			
				equipments = build.selectEquips(listEquips.getSatList());
				months = build.selectMonth();
				years = build.selectYears();
		
			} catch (Exception ex) {
			
				StringWriter errors = new StringWriter();
				ex.printStackTrace(new PrintWriter(errors));
				
				SystemLog.logError(errorFolder.concat("error_select_item"), EquipmentsDAO.class.getCanonicalName(), ex.getMessage(), errors.toString());
				
			}	      
	     }
	
	// -----------------------------------------------------------------------------------------------------------------------------------------------------     
		
	 public void getTextFile() {
		 		 		 
		 TranslationMethods tm = new TranslationMethods(); // Translation Methods
		 		 		 
		 try {
			 
		 	DateTimeApplication dta = new DateTimeApplication();
		 	 		 	
		 	 List<VehicleByVehicle> list = new ArrayList<VehicleByVehicle>();
			  
			  VehicleByVehicleDAO dao = new VehicleByVehicleDAO();
			  EquipmentsDAO equipDAO = new EquipmentsDAO();
			 			 
				 Map<String, String> parameterMap = SessionUtil.getRequestParameterMap();
				
				 equip = parameterMap.get("equip"); // EQUIP	 
				 month = parameterMap.get("month"); // MONTH				
				 year = parameterMap.get("year"); // YEAR
				 
				 int strHour = 0, strMin = 0, strSec = 0;
				 int endHour = 23, endMin = 59, endSec = 59;
				 
				 int yr = Integer.parseInt(year);
				 int mth = Integer.parseInt(month);
				 
				 YearMonth yearMonthObject = YearMonth.of(yr, mth);
				 int daysInMonth = yearMonthObject.lengthOfMonth();				

				 int diaInicial = 1;
							
				 try {
					 
					 String startDate = dta.createDateTime(diaInicial, mth, yr, strHour, strMin, strSec);
					 String endDate = dta.createDateTime(daysInMonth, mth, yr, endHour, endMin, endSec);
					 
					 name = equipDAO.equipmentName("sat", equip);
					 
					 list = dao.getVehicles(startDate, endDate, equip);
					 					 				
						if(!list.isEmpty()) {
							
							//long begin = System.currentTimeMillis();
											
							byteWriter = new ByteArrayOutputStream();
							writer = new BufferedWriter(new OutputStreamWriter(byteWriter));
																		
							for(int i = 0; i < list.size(); i++) {
								
								writer.write(list.get(i).getSiteID() + ";");
								writer.write(list.get(i).getSeqG() + ";");
								writer.write(list.get(i).getSeqN() + ";");
								writer.write(list.get(i).getDateTime() + ";");
								writer.write(list.get(i).getClassVBV()+ ";");
								writer.write(list.get(i).getAxlNumber() + ";");
								writer.write(list.get(i).getAxl1W() + ";");
								writer.write(list.get(i).getAxl2W() + ";");
								writer.write(list.get(i).getAxl3W() + ";");
								writer.write(list.get(i).getAxl4W() + ";");
								writer.write(list.get(i).getAxl5W() + ";");
								writer.write(list.get(i).getAxl6W() + ";");
								writer.write(list.get(i).getAxl7W() + ";");
								writer.write(list.get(i).getAxl8W() + ";");
								writer.write(list.get(i).getAxl9W() + ";");
								writer.write(list.get(i).getAxl2D() + ";");
								writer.write(list.get(i).getAxl3D() + ";");
								writer.write(list.get(i).getAxl4D() + ";");
								writer.write(list.get(i).getAxl5D() + ";");
								writer.write(list.get(i).getAxl6D() + ";");
								writer.write(list.get(i).getAxl7D() + ";");
								writer.write(list.get(i).getAxl8D() + ";");
								writer.write(list.get(i).getAxl9D() + ";");			
								writer.write(list.get(i).getGross() + ";");				
								writer.write(list.get(i).getTemperature() + ";");					
								writer.write(list.get(i).getSpeed() + ";");
								writer.write(list.get(i).getLane() + ";");					
													
								writer.newLine();
							}
							
							writer.flush();			
							writer.close();
																					
							// SEND TO FRONT END								
							 monthName = tm.monthComparison(Integer.parseInt(month));	
							 																																											
							 SessionUtil.getExternalContext().getSessionMap().put("name_", name); 	
							 SessionUtil.getExternalContext().getSessionMap().put("month_", month); 						
							 SessionUtil.getExternalContext().getSessionMap().put("year_", year); 	
							 SessionUtil.getExternalContext().getSessionMap().put("bytes", byteWriter.toByteArray()); 
							 
							 build.textBool = false;
							 
							 SessionUtil.executeScript("$('#label').removeClass('d-none'); $('#label').html('<strong>"+name+"</strong> "+monthName+" "+year+"');");
													 							 													
							//long end = System.currentTimeMillis();
							//System.out.println("Tempo de gravação: " + (end - begin));
														
							} else SessionUtil.executeScript("alertOptions('#info', '"+locale.getStringKey("$message_reports_record_not_found")+"');");
						
							// AFTER SUBMIT CLEAN FIELDS					
							SessionUtil.executeScript("$('#equip').val(''); $('span[for=equip]').removeClass('valid-icon-visible').addClass('valid-icon-hidden');");
							SessionUtil.executeScript("$('#month').val(''); $('span[for=month]').removeClass('valid-icon-visible').addClass('valid-icon-hidden');");
							SessionUtil.executeScript("$('#year').val(''); $('span[for=year]').removeClass('valid-icon-visible').addClass('valid-icon-hidden');");
						
	    } catch (ParseException e) {		 
		    e.printStackTrace();
	    }
			
		} catch (IOException e) {			
			e.printStackTrace();
		}		 	 
	 }	 
  
	 public void downloadFile() throws Exception {
		 		 	
		 	String name = (String) SessionUtil.getExternalContext().getSessionMap().get("name_");
		 	String month = (String) SessionUtil.getExternalContext().getSessionMap().get("month_");		
		    String year = (String) SessionUtil.getExternalContext().getSessionMap().get("year_");
		 	byte[] bytes = (byte[]) SessionUtil.getExternalContext().getSessionMap().get("bytes");
		 
		    build.downloadTextFile(name, month, year, bytes);
		
		}		
	 
	 // -----------------------------------------------------------------------------------------------------------------------------------------------------   
	 
	 public void resetForm() {
		 			 
		 SessionUtil.getExternalContext().getSessionMap().remove("name_"); 	
		 SessionUtil.getExternalContext().getSessionMap().remove("month_");	
		 SessionUtil.getExternalContext().getSessionMap().remove("year_");
		 SessionUtil.getExternalContext().getSessionMap().remove("bytes");
		 
		// build = new ReportBuild();
		 byteWriter = new ByteArrayOutputStream();
		 writer = null;
		 
		 equip = null;
		 name = null;
		 month = null;
		 monthName = null;
		 year = null;
		 		 
		 build.textBool = true;
		 		 
		 SessionUtil.executeScript("$('#label').addClass('d-none');('#label').html('');");		
		 
	 }
	 
	 // -----------------------------------------------------------------------------------------------------------------------------------------------------  
		
  }
