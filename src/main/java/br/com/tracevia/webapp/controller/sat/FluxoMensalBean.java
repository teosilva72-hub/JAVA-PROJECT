package br.com.tracevia.webapp.controller.sat;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderExtent;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Chart;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.charts.AxisCrosses;
import org.apache.poi.ss.usermodel.charts.AxisPosition;
import org.apache.poi.ss.usermodel.charts.ChartAxis;
import org.apache.poi.ss.usermodel.charts.ChartDataSource;
import org.apache.poi.ss.usermodel.charts.ChartLegend;
import org.apache.poi.ss.usermodel.charts.DataSources;
import org.apache.poi.ss.usermodel.charts.LegendPosition;
import org.apache.poi.ss.usermodel.charts.LineChartData;
import org.apache.poi.ss.usermodel.charts.LineChartSeries;
import org.apache.poi.ss.usermodel.charts.ValueAxis;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.PropertyTemplate;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTMarker;

import br.com.tracevia.webapp.dao.global.EquipmentsDAO;
import br.com.tracevia.webapp.dao.sat.FluxoVeiculosDAO;
import br.com.tracevia.webapp.log.SystemLog;
import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.methods.ExcelModels;
import br.com.tracevia.webapp.methods.TranslationMethods;
import br.com.tracevia.webapp.model.global.ColumnModel;
import br.com.tracevia.webapp.model.global.ListEquipments;
import br.com.tracevia.webapp.model.global.ReportBuild;
import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.model.sat.FluxoVeiculos;
import br.com.tracevia.webapp.model.sat.FluxoVeiculos.Builder;
import br.com.tracevia.webapp.model.sat.SAT;
import br.com.tracevia.webapp.util.LocaleUtil;
import br.com.tracevia.webapp.util.SessionUtil;

@ManagedBean(name = "fluxoMensal")
@RequestScoped
public class FluxoMensalBean {
	
	// --------------------------------------------------------------------------------------------------------------
	
	private final String errorFolder = SystemLog.ERROR.concat("monthly-flow-bean\\");
			
	// --------------------------------------------------------------------------------------------------------------
		
	private List<SelectItem> equipments;
	private List<SelectItem> months;
	private List<SelectItem> years;
	private List<ColumnModel> columns; 		
	private List<Builder> resultList;	

	private String direction1, direction2, directionAbr1, directionAbr2, firstLane;
	
	private ReportBuild build;
	
	LocaleUtil localeSat, localeDir, localeReports, localeSheet, localeCalendar;
	
	int rangeHour, rangeInterval, pos, hrPos, last_hour, numberLanes;	
	
	String last_date;

	int[] moto1, auto1, com1, motoACM1, autoACM1, comACM1, moto2, auto2, com2, motoACM2, autoACM2, comACM2, speed1, speed1Aux, speed2, speed2Aux, speedHour1, speedHour2, speedACM1, speedACM2,
	totalACM1, totalACM2, fluxo1, fluxo2, fluxoACM1, fluxoACM2, total, total1, total2 ;

	String[] interval, date, intervalHour, hour;

	int length, minLen, daysInMonth;
	
	String equip, month, year;
	
	private XSSFWorkbook workbook;
	
	SAT sat;
	
	Date date_;
	
	String 	categoriaA = "A", categoriaB = "B", categoriaC = "C", categoriaD = "D", categoriaE = "E", categoriaF = "F",
			var_value = "> 25,0";
	
	double equivNumber = 3.00, fph_value = 0.92 ,  fphn_value = 1.84, flw_value = 1,
			width_value = 3.50, flc_value = 1, obst_value = 2.40, fm_value = 0;
	
	int fssi_value = 100, fa_value = 0;
		
	@ManagedProperty("#{listEquips}")
	private ListEquipments listEquips;
			
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

	public List<SelectItem> getYears() {
		return years;
	}
	
	public List<ColumnModel> getColumns() {
		return columns;
	}
	
	public List<Builder> getResultList() {
		return resultList;
	}
	
	public String getDirection1() {
		return direction1;
	}

	public String getDirection2() {
		return direction2;
	}

	public String getDirectionAbr1() {
		return directionAbr1;
	}

	public String getDirectionAbr2() {
		return directionAbr2;
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
		
		build.fields = new String[] {localeSat.getStringKey("global_date_column"), localeSat.getStringKey("global_interval_column"), 
				localeSat.getStringKey("$label_monthly_flow_motorcycles"), localeSat.getStringKey("$label_monthly_flow_light_vehicles"), localeSat.getStringKey("$label_monthly_flow_commercial_vehicles"),
				localeSat.getStringKey("$label_monthly_flow_motorcycles"), localeSat.getStringKey("$label_monthly_flow_light_vehicles"), localeSat.getStringKey("$label_monthly_flow_commercial_vehicles"), 
				localeSat.getStringKey("$label_monthly_flow_direction_abbr_s1"), localeSat.getStringKey("$label_monthly_flow_direction_abbr_s2")};

		// Table Object
		build.fieldObjectValues = new String[] {"date", "time", "moto1", "light1", "comm1", "moto2", "light2", "comm2", "speed1", "speed2"};
						
		//DESENHAR A TABELA
		columns = build.drawTable(build.fields, build.fieldObjectValues);	
		
		// DIRECTIONS 
		
		direction1 = localeDir.getStringKey("directions_tab_default_direction_1");
		direction2 = localeDir.getStringKey("directions_tab_default_direction_2");
		directionAbr1 = localeDir.getStringKey("directions_direction_1_abbr"); 
		directionAbr2 = localeDir.getStringKey("directions_direction_2_abbr");			

		// GUARDAR VALORES NA SESSION
		SessionUtil.setParam("fields", build.fields);	//Fields
		SessionUtil.setParam("fieldsObject", build.fieldObjectValues); //Objects
		
		
	}
	
	// ----------------------------------------------------------------------------------------------------------------------------------------------------- 
	
	/** Método para obter o fluxo de ve�culos de um determinado mês
	 * @throws Exception
	 * @retunr void 
	 */
	
	 public void getReport() throws Exception {
					 		 
		DateTimeApplication dta = new DateTimeApplication();
			
		List<FluxoVeiculos> lista = new ArrayList<FluxoVeiculos>();
		resultList = new ArrayList<Builder>();
	
		FluxoVeiculosDAO dao = new FluxoVeiculosDAO();
		EquipmentsDAO eqDAO = new EquipmentsDAO();
		
		//FIELDS EXTERNOS ARMAZENADOS NA REQUISIO
		build.fields = (String[]) SessionUtil.getParam("fields");
		build.fieldObjectValues =  (String[]) SessionUtil.getParam("fieldsObject");
				
		 Map<String, String> parameterMap = SessionUtil.getRequestParameterMap();
			
			 equip = parameterMap.get("equip"); // EQUIP	 
			 month = parameterMap.get("month"); // MONTH				
			 year = parameterMap.get("year"); // YEAR
							 
		 int yr = Integer.parseInt(year);
		 int mth = Integer.parseInt(month);
		 
		 YearMonth yearMonthObject = YearMonth.of(yr, mth);
		 daysInMonth = yearMonthObject.lengthOfMonth();		
		 
		 int diaInicial = 1;
		 		 
		 length = (daysInMonth * 96); 
		 minLen = (daysInMonth * 24); 
			 
		try {						
		
		 String startDate = dta.createDate(diaInicial, mth, yr);
		 String endDate = dta.createDate(daysInMonth, mth, yr);
		 String startDateAux = startDate;
					 
		initVariables(length, minLen); 	      

		last_date = startDate;
		last_hour = -1;
		rangeHour = 24;
		rangeInterval = 96;
		pos = 0;
		hrPos = 0;

		int hr = 0, minuto = 0, hrResp = 0, interResp = 0;
		
		sat = new SAT();		
		sat = eqDAO.headerInfoSAT(equip);			
									
		direction1 = sat.getSentido1();
		direction2 = sat.getSentido2();
		directionAbr1 = sat.getSentido1Abbr();
		directionAbr2 = sat.getSentido2Abbr();
		firstLane = sat.getFaixa1();
		numberLanes = sat.getNumFaixas();		
		
		lista = dao.getVehicles(startDate, endDate, equip, firstLane);
																		
		if (!lista.isEmpty()) {

		date = dta.preencherDataPorPeriodo(startDate, length, rangeInterval);
		intervalHour= dta.intervalo15Minutos(length);	
		hour = dta.preencherHora(minLen);			

			for (FluxoVeiculos veh : lista) {

				String data = veh.getDate();							
				String intervalo = veh.getInterval();
				int motoS1 = veh.getMoto1();
				int autoS1 = veh.getAuto1();
				int comercialS1 = veh.getCom1();
				int motoS2 = veh.getMoto2();
				int autoS2 = veh.getAuto2();
				int comercialS2 = veh.getCom2();
				int totalS1 = veh.getTotal1();
				int totalS2 = veh.getTotal2();
				int speedAVGS1 = veh.getSpeed1();
				int speedAVGS2 = veh.getSpeed2();
						
				hr = Integer.parseInt(intervalo.substring(0, 2));
				minuto =  Integer.parseInt(intervalo.substring(3, 5));
				
				// Restri��o caso n�o haja dados nos primeiros registros
				if ((startDateAux!= null) && (!data.equals(startDateAux))) { // Executa uma unica vez

					interResp = dta.daysDifference(startDateAux, data, rangeInterval);	
					pos+= interResp;

					hrResp = dta.daysDifference(startDateAux, data, rangeHour);	
					hrPos+= hrResp;									

					startDateAux = null;
					
				} else if (!data.equals(last_date)) {								

					interResp = dta.daysDifference(last_date, data, rangeInterval);	
					pos+= interResp;

					hrResp = dta.daysDifference(last_date, data, rangeHour);	
					hrPos+= hrResp;	
				}			
				
				preencherDados(pos, hrPos, hr, minuto, motoS1, autoS1, comercialS1, motoS2, autoS2, comercialS2, speedAVGS1, speedAVGS2, totalS1, totalS2);
							
				last_date = data; // Atualiza o dia
			}			

			// ---------------------------------------------------------------------------------------------------------------------------
			
			 build.clearBool = false;
			 build.excelBool = false;
			
			 outPutResult();
			 
			 generateExcel();
					 			
			// REDRAW TABLE
			columns = build.drawTable(build.fields, build.fieldObjectValues);
			
			//UPDATE TABLE JQUERY ON RELOAD PAGE
			SessionUtil.executeScript("drawTable()");				
		 
		} else {
			
			// REDRAW TABLE
			columns = build.drawTable(build.fields, build.fieldObjectValues);
		
			// EXECUTE JS
			SessionUtil.executeScript("alertOptions('#info', '"+localeReports.getStringKey("$message_reports_record_not_found")+"');");
			  
			 // UPDATE TABLE JQUERY ON RELOAD PAGE
			SessionUtil.executeScript("drawTable()");	
			
		}
					
		} catch (Exception ex) {
			ex.printStackTrace();
			
		}		
	}
	 
	// ----------------------------------------------------------------------------------------------------------------------------------------------------- 
	 
	 public void outPutResult() {  
		 
			
			for (int d = 0; d < length; d++) {	
				
				resultList.add(new FluxoVeiculos.Builder().date(date[d]).time(intervalHour[d])
						.motoS1(moto1[d])
						.lightS1(auto1[d])
						.commS1(com1[d])
						.motoS2(moto2[d])
						.lightS2(auto2[d])
						.commS2(com2[d])
						.speedS1(speed1[d])
						.speedS2(speed2[d]));
				
			}		 
	   }
	 
	// -----------------------------------------------------------------------------------------------------------------------------------------------------  
	

	/** M�todo para popular os dados em uma tabela
	 * @param pos - posi��o com base no intervalo de 15 minutos
	 * @param hrPos - posi��o com base nas horas dentre uma data (24 horas)
	 * @param hora - hora obtida
	 * @param minuto - minuto obtido
	 * @param motos1 - motos no sentido 1
	 * @param autos1 - autom�veis no sentido 1
	 * @param coms1 - ve�culos comerciais no sentido 1
	 * @param motos2 - motos no sentido 2
	 * @param autos2 - autom�veis no sentido 2
	 * @param coms2 - ve�culos comerciais no sentido 2
	 * @param faixas - n�mero de faixas da estrada
	 * @param speed - velocidade 
	 * @param faixa1 - sentido da 1� faixa 
	 * @param faixa2 - sentido da 2� faixa
	 * @param faixa3 - sentido da 3� faixa
	 * @param faixa4 - sentido da 4� faixa
	 * @param faixa5 - sentido da 5� faixa
	 * @param faixa6 - sentido da 6� faixa
	 * @param faixa7 - sentido da 7� faixa
	 * @param faixa8 - sentido da 8� faixa
	 * @param total - total de ve�culos
	 * @return void - retorno vazio
	 */

	public void preencherDados(int pos, int hrPos, int hora, int minuto, int motos1, int autos1, int coms1, int motos2, int autos2, int coms2, int speedAvg1, int speedAvg2, 
			int totalS1, int totalS2){
				
		DateTimeApplication dta = new DateTimeApplication();

		int idx = dta.horaIndex15Min(hora); // aponta um �ndice para preencher o array
		
		int i = 0;
		int p = 0;
				
		if (minuto == 0 ) {

			p = pos + idx + i; // Apontar o �ndice

			moto1[p] += motos1;
			auto1[p] += autos1;
			com1[p] += coms1;			
			speed1Aux[p] += speedAvg1;	
			speed1[p] = speed1Aux[p];
			total1[p] += totalS1; 
			totalACM1[p] = total1[p];			
			moto2[p] += motos2;
			auto2[p] += autos2;
			com2[p] += coms2;
			speed2Aux[p] += speedAvg2;
			speed2[p] = speed2Aux[p];
			total2[p] += totalS2; 
			totalACM2[p] = total2[p];		
		}
		
		if (minuto == 15 ) {

			i=1;
			p = pos + idx + i; // Apontar o �ndice

			moto1[p] += motos1;
			auto1[p] += autos1;
			com1[p] += coms1;			
			speed1Aux[p] += speedAvg1;	
			speed1[p] = speed1Aux[p];
			total1[p] += totalS1; 
			totalACM1[p] = total1[p];			
			moto2[p] += motos2;
			auto2[p] += autos2;
			com2[p] += coms2;
			speed2Aux[p] += speedAvg2;
			speed2[p] = speed2Aux[p];
			total2[p] += totalS2; 
			totalACM2[p] = total2[p];		
		}
		
		if (minuto == 30 ) {

			i=2;
			p = pos + idx + i; // Apontar o �ndice

			moto1[p] += motos1;
			auto1[p] += autos1;
			com1[p] += coms1;			
			speed1Aux[p] += speedAvg1;	
			speed1[p] = speed1Aux[p];
			total1[p] += totalS1; 
			totalACM1[p] = total1[p];			
			moto2[p] += motos2;
			auto2[p] += autos2;
			com2[p] += coms2;
			speed2Aux[p] += speedAvg2;
			speed2[p] = speed2Aux[p];
			total2[p] += totalS2; 
			totalACM2[p] = total2[p];		
		}
		
		if (minuto == 45 ) {

			i=3;
			p = pos + idx + i; // Apontar o �ndice

			moto1[p] += motos1;
			auto1[p] += autos1;
			com1[p] += coms1;			
			speed1Aux[p] += speedAvg1;	
			speed1[p] = speed1Aux[p];
			total1[p] += totalS1; 
			totalACM1[p] = total1[p];			
			moto2[p] += motos2;
			auto2[p] += autos2;
			com2[p] += coms2;
			speed2Aux[p] += speedAvg2;
			speed2[p] = speed2Aux[p];
			total2[p] += totalS2; 
			totalACM2[p] = total2[p];		
		}	
		
		taxaFluxoS1(pos, hrPos, idx, hora); 
		taxaFluxoS2(pos, hrPos, idx, hora); 
		
		totalVeiculosACM1(pos, hrPos, idx, hora);
		totalVeiculosACM2(pos, hrPos, idx, hora);	
		
        mediaVelocidadeS1(pos, hrPos, idx, hora);
        mediaVelocidadeS2(pos, hrPos, idx, hora);
              
		last_hour = hora;		
	}		
	
  

	/**M�todo para inicializar arrays utilizados para separa��o de dados 		 
	 * @param maxLen - maxLenanho proporcional ao intervalo de 15 minutos
	 * @param minLen - maxLenanho proporcional ao per�odo de horas
	 * @return void - retorno vazio
	 */
	public void initVariables(int maxLen, int minLen) {

		date = new String[maxLen]; 
		intervalHour = new String[maxLen];
		moto1 = new int[maxLen]; 
		auto1 = new int[maxLen];  
		com1 = new int[maxLen]; 
		moto2 = new int[maxLen];  
		auto2 = new int[maxLen]; 
		com2 = new int[maxLen]; 	
		total = new int[maxLen]; 
		total1 = new int[maxLen];
		total2 = new int[maxLen];	
		fluxo1 = new int[maxLen];
		fluxo2 = new int[maxLen];			
		speed1 = new int[maxLen];
		speed2 = new int[maxLen];	
		speed1Aux = new int[maxLen];
		speed2Aux = new int[maxLen];	
		speedHour1 = new int[maxLen];
		speedHour2 = new int[maxLen];
		totalACM1 = new int[maxLen];
		totalACM2 = new int[maxLen];		

		hour = new String[minLen];
		motoACM1 = new int[minLen];
		autoACM1 = new int[minLen];
		comACM1 = new int[minLen];
		motoACM2 = new int[minLen];
		autoACM2 = new int[minLen];
		comACM2 = new int[minLen];			
		fluxoACM1 = new int[minLen];
		fluxoACM2 = new int[minLen];		
		speedACM1 = new int[minLen];
		speedACM2 = new int[minLen];	
	}
	
	/** M�todo para limpar listas e vari�veis de caixas de sele��o
	 * @throws Exception
	 * @return void */
	
	// ---------------------------------------------------------------------------------

	public void resetForm() throws Exception {
				
		// RESET FIELDS FIRST	
		direction1 = localeDir.getStringKey("directions_tab_default_direction_1");
		direction2 = localeDir.getStringKey("directions_tab_default_direction_2");
		directionAbr1 = localeDir.getStringKey("directions_direction_1_abbr"); 
		directionAbr2 = localeDir.getStringKey("directions_direction_2_abbr");
		numberLanes = 0;
		firstLane = "";
		
	// ---------------------------------------------------------------------------------
		
		//FIELDS EXTERNOS ARMAZENADOS NA REQUISIO
		build.fields = (String[]) SessionUtil.getParam("fields");
		build.fieldObjectValues =  (String[]) SessionUtil.getParam("fieldsObject");
				
		// AFTER REDRAW TABLE
		columns = build.drawTable(build.fields, build.fieldObjectValues);
				
	// ---------------------------------------------------------------------------------
					
		sat = new SAT();
		
		build.excelBool = true;
		build.clearBool = true;
		
		// REMOVE FIELDS FROM SESSION
		
		SessionUtil.remove("workbook");
		SessionUtil.remove("current");
		SessionUtil.remove("fileName");		
				
	}
	
	// ---------------------------------------------------------------------------------
	
	/**
	 * Método aux�liar para calcular a taxa de Fluxo de acordo com sentido 1		 
	 * @param pos - posi��o proporcional ao intervalo de 15 minutos
	 * @param hrPos - posi��o proporcional ao per�odo entre horas
	 * @param idx - indice que varia com per�odo de 15 minutos no intervalo determinada uma hora (Ex.: 0,1,2,3 => hr = 0)		 
	 * @param hr - hora obtida
	 * @return void - retorno vazio
	 */

	public void taxaFluxoS1(int pos, int hrPos, int idx, int hr) {

		int maior = total1[pos + (idx + 0)];

		if (total1[pos + (idx + 1)] > maior)
			maior = total1[pos + (idx + 1)];

		if (total1[pos + (idx + 2)] > maior)
			maior = total1[pos + (idx + 2)];

		if (total1[pos + (idx + 3)] > maior)
			maior = total1[pos + (idx + 3)];

		fluxo1[pos + (idx + 3)] = (maior * 4);
		fluxoACM1[hrPos + hr] = fluxo1[pos + (idx + 3)];
	}

	/**M�todo aux�liar para calcular a taxa de Fluxo de acordo com sentido 2		 
	 * @param pos - posi��o proporcional ao intervalo de 15 minutos
	 * @param hrPos - posi��o proporcional ao per�odo entre horas
	 * @param idx - indice que varia com per�odo de 15 minutos no intervalo determinada uma hora (Ex.: 0,1,2,3 => hr = 0)		 
	 * @param hr - hora obtida
	 * @return void - retorno vazio
	 */

	public void taxaFluxoS2(int pos, int hrPos, int idx, int hr) {

		int maior = total2[pos + (idx + 0)];

		if (total2[pos + (idx + 1)] > maior)
			maior = total2[pos + (idx + 1)];

		if (total2[pos + (idx + 2)] > maior)
			maior = total2[pos + (idx + 2)];

		if (total2[pos + (idx + 3)] > maior)
			maior = total2[pos + (idx + 3)];

		fluxo2[pos + (idx + 3)] = (maior * 4);
		fluxoACM2[hrPos + hr] = fluxo2[pos + (idx + 3)];
	} 

	/**M�todo aux�liar para calcular total de ve�culos (motocicletas, autom�veis e ve�culos comercias) no sentido 1		 
	 * @param pos - posi��o proporcional ao intervalo de 15 minutos
	 * @param hrPos - posi��o proporcional ao per�odo entre horas
	 * @param idx - indice que varia com per�odo de 15 minutos no intervalo determinada uma hora (Ex.: 0,1,2,3 => hr = 0)		 
	 * @param hr - hora obtida
	 * @return void - retorno vazio
	 */

 public void totalVeiculosACM1(int pos, int hrPos, int idx, int hr) {

		motoACM1[hrPos+hr] = moto1[pos + (idx + 0)] + moto1[pos + (idx + 1)] + moto1[pos + (idx + 2)]
				+ moto1[pos + (idx + 3)];

		autoACM1[hrPos+hr] = auto1[pos + (idx + 0)] + auto1[pos + (idx + 1)] + auto1[pos + (idx + 2)]
				+ auto1[pos + (idx + 3)];

		comACM1[hrPos+hr] = com1[pos + (idx + 0)] + com1[pos + (idx + 1)] + com1[pos + (idx + 2)]
				+ com1[pos + (idx + 3)];
	}

	/**M�todo aux�liar para calcular total de ve�culos (motocicletas, autom�veis e ve�culos comercias) no sentido 2		 
	 * @param pos - posi��o proporcional ao intervalo de 15 minutos
	 * @param hrPos - posi��o proporcional ao per�odo entre horas
	 * @param idx - indice que varia com per�odo de 15 minutos no intervalo determinada uma hora (Ex.: 0,1,2,3 => hr = 0)		 
	 * @param hr - hora obtida
	 * @return void - retorno vazio
	 */

	public void totalVeiculosACM2(int pos, int hrPos, int idx, int hr) {

		motoACM2[hrPos+hr] = moto2[pos + (idx + 0)] + moto2[pos + (idx + 1)] + moto2[pos + (idx + 2)]
				+ moto2[pos + (idx + 3)];

		autoACM2[hrPos+hr] = auto2[pos + (idx + 0)] + auto2[pos + (idx + 1)] + auto2[pos + (idx + 2)]
				+ auto2[pos + (idx + 3)];

		comACM2[hrPos+hr] = com2[pos + (idx + 0)] + com2[pos + (idx + 1)] + com2[pos + (idx + 2)]
				+ com2[pos + (idx + 3)];			
	}

	/**M�todo aux�liar para calcular a m�dia de velocidade de acordo com sentido 1		 
	 * @param pos - posi��o proporcional ao intervalo de 15 minutos
	 * @param hrPos - posi��o proporcional ao per�odo entre horas
	 * @param idx - indice que varia com per�odo de 15 minutos no intervalo determinada uma hora (Ex.: 0,1,2,3 => hr = 0)		 
	 * @param hr - hora obtida
	 * @return void - retorno vazio
	 */

	public void mediaVelocidadeS1(int pos, int hrPos, int idx, int hr) {

		int total = 0;

		if(total1[pos + (idx + 0)] != 0) total++;
		if(total1[pos + (idx + 1)] != 0) total++;
		if(total1[pos + (idx + 2)] != 0) total++;
		if(total1[pos + (idx + 3)] != 0) total++;	
		
		if(total == 0)
			total = 1;

		int average = (speed1[pos + (idx + 0)] + speed1[pos + (idx + 1)] + speed1[pos + (idx + 2)]
				+ speed1[pos + (idx + 3)]) / total;		;

		speedHour1[pos + (idx + 3)] = average; 
		speedACM1[hrPos + hr] = average;		
	}

	/**M�todo aux�liar para calcular a m�dia de velocidade de acordo com sentido 2		 
	 * @param pos - posi��o proporcional ao intervalo de 15 minutos
	 * @param hrPos - posi��o proporcional ao per�odo entre horas
	 * @param idx - indice que varia com per�odo de 15 minutos no intervalo determinada uma hora (Ex.: 0,1,2,3 => hr = 0)		 
	 * @param hr - hora obtida
	 * @return void - retorno vazio
	 */

	public void mediaVelocidadeS2(int pos, int hrPos, int idx, int hr ) {

		int total = 0;

		if(total2[pos + (idx + 0)] != 0) total++;
		if(total2[pos + (idx + 1)] != 0) total++;
		if(total2[pos + (idx + 2)] != 0) total++;
		if(total2[pos + (idx + 3)] != 0) total++;	
				
		if(total == 0)
			total = 1;

		int average = (speed2[pos + (idx + 0)] +  speed2[pos + (idx + 1)] +  speed2[pos + (idx + 2)]
				+ speed2[pos + (idx + 3)]) / total;

		speedHour2[pos + (idx + 3)] = average;  
		speedACM2[hrPos + hr] = average;
	}	
	
	
	 /**M�todo para realizar o download do aqruivo Excel
     * @return void - retorno vazio
     * @throws IOException 
     */
    
	public void download() {

		ExcelModels model = new ExcelModels();
		
		XSSFWorkbook workbook = (XSSFWorkbook) SessionUtil.getParam("workbook");
		String currentDate = (String) SessionUtil.getParam("current");
		String fileName = (String) SessionUtil.getParam("fileName");

		String name = fileName+"_"+currentDate;

		try {

			model.download(workbook, name);

		} catch (IOException e) {			
			e.printStackTrace();
		}

	}   		            

     /** M�todo para gerar o arquivo Excel da classe
 	 * @throws IOException
 	 * return void - retorno vazio
 	 */

 	public void generateExcel() throws IOException {
 		
 		workbook = new XSSFWorkbook(); // init Workbook

 		// come�o a criar o arquivo excel de acordo com minha necessidade 		 		
 		TranslationMethods tm = new TranslationMethods();
 		DateTimeApplication dta = new DateTimeApplication();
 		 		
 		String fileName = sat.getNome()+"_"+tm.MonthAbbreviation(month)+"_"+tm.YearAbbreviation(year);
 		
 		//Define Values in session map !important	
 		SessionUtil.setParam("workbook", workbook);
		SessionUtil.setParam("current", dta.currentTime());
		SessionUtil.setParam("fileName", fileName);   
 		
 		try {

 			// ------ BASE BEGIN ------//					
 			XSSFSheet sheet = workbook.createSheet(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_sheet_base"));
 			PropertyTemplate propertyTemplate = new PropertyTemplate();
 			PropertyTemplate propertyDays = new PropertyTemplate();
 			PropertyTemplate propertyVDM = new PropertyTemplate();
 			PropertyTemplate propertyPDL = new PropertyTemplate();
 			PropertyTemplate propertyPDO = new PropertyTemplate();
 			PropertyTemplate propertyGF = new PropertyTemplate();
 			PropertyTemplate propertyGD = new PropertyTemplate();

 			// Estilo - BASE
 			CellStyle style = workbook.createCellStyle();
 			Font font = workbook.createFont();
 			font.setColor(HSSFColor.HSSFColorPredefined.DARK_BLUE.getIndex());
 			font.setBold(true);
 			font.setFontName(HSSFFont.FONT_ARIAL);
 			font.setFontHeightInPoints((short) 10);
 			style.setFont(font);
 			style.setAlignment(HorizontalAlignment.CENTER);
 			style.setFillBackgroundColor(IndexedColors.WHITE.getIndex());

 			// END

 			// Corpo da planilha
 			CellStyle style1 = workbook.createCellStyle();
 			Font fontBody = workbook.createFont();
 			fontBody.setFontName(HSSFFont.FONT_ARIAL);
 			fontBody.setFontHeightInPoints((short) 10);
 			style1.setFont(fontBody);
 			style1.setAlignment(HorizontalAlignment.CENTER);
 			style1.setFillBackgroundColor(IndexedColors.WHITE.getIndex()); // Cor na sele��o da c�lula

 			// Style Data and Hora
 			CellStyle styleDH = workbook.createCellStyle();
 			Font fontdh = workbook.createFont();
 			fontdh.setColor(HSSFColor.HSSFColorPredefined.DARK_BLUE.getIndex());
 			fontdh.setBold(true);
 			fontdh.setFontName(HSSFFont.FONT_ARIAL);
 			fontdh.setFontHeightInPoints((short) 10);
 			styleDH.setFont(fontdh);
 			styleDH.setVerticalAlignment(VerticalAlignment.CENTER); // Centralizar no vertical
 			styleDH.setAlignment(HorizontalAlignment.CENTER); // Centralizar na horizontal
 			styleDH.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
 			// Style Data e Hora

 			// Style Background color
 			XSSFCellStyle backgroundStyle = workbook.createCellStyle();
 			backgroundStyle.setFont(fontBody);
 			backgroundStyle.setAlignment(HorizontalAlignment.CENTER);
 			backgroundStyle.setFillBackgroundColor(IndexedColors.GREY_25_PERCENT.getIndex());

 			// Style Background color
 			XSSFCellStyle backgroundDayStyle = workbook.createCellStyle();
 			Font fontds = workbook.createFont();
 			fontds.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
 			fontds.setBold(true);
 			fontds.setFontName(HSSFFont.FONT_ARIAL);
 			fontds.setFontHeightInPoints((short) 10);
 			backgroundDayStyle.setFont(fontds);
 			backgroundDayStyle.setAlignment(HorizontalAlignment.CENTER);
 			backgroundDayStyle.setFillBackgroundColor(IndexedColors.DARK_BLUE.getIndex());

 			// Cabe�alho 
 			XSSFRow rowHeading = sheet.createRow((short) 1);
 			rowHeading.createCell(0).setCellValue("");
 			rowHeading.createCell(1).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_date_tab"));
 			rowHeading.createCell(2).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_hour_tab"));
 			rowHeading.createCell(3).setCellValue(direction1);
 			rowHeading.createCell(6).setCellValue(direction2);
 			rowHeading.createCell(10).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_speed_operations_tab"));

 			rowHeading.getCell(0).setCellStyle(style);
 			rowHeading.getCell(1).setCellStyle(styleDH);

 			rowHeading.getCell(2).setCellStyle(styleDH);
 			rowHeading.getCell(3).setCellStyle(style);
 			rowHeading.getCell(6).setCellStyle(style);
 			rowHeading.getCell(10).setCellStyle(style);

 			// Linha Abaixo
 			// Sentido 1
 			Row rowHeading2 = sheet.createRow((short) 2);
 			rowHeading2.createCell(3).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_auto_tab"));
 			rowHeading2.createCell(4).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_moto_tab"));
 			rowHeading2.createCell(5).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_com_tab"));

 			// Sentido 2
 			rowHeading2.createCell(6).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_auto_tab"));
 			rowHeading2.createCell(7).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_moto_tab"));
 			rowHeading2.createCell(8).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_com_tab"));

 			//Sentido 1 e Sentido 2F
 			rowHeading2.createCell(10).setCellValue(directionAbr1);
 			rowHeading2.createCell(11).setCellValue(directionAbr2);

 			for(int i=3; i < 9; i++)
 				rowHeading2.getCell(i).setCellStyle(style);	

 			for(int i=10; i < 12; i++)
 				rowHeading2.getCell(i).setCellStyle(style);	

 			// Mesclar c�lulas
 			sheet.addMergedRegion(CellRangeAddress.valueOf("B2:B3"));
 			sheet.addMergedRegion(CellRangeAddress.valueOf("C2:C3"));
 			sheet.addMergedRegion(CellRangeAddress.valueOf("D2:F2"));
 			sheet.addMergedRegion(CellRangeAddress.valueOf("G2:I2"));
 			sheet.addMergedRegion(CellRangeAddress.valueOf("K2:L2"));

 			// Largura da Coluna
 			sheet.setColumnWidth(0, 700);
 			sheet.setColumnWidth(1, 3500);
 			sheet.setColumnWidth(2, 3500);
 			sheet.setColumnWidth(10, 3650);
 			sheet.setColumnWidth(11, 3650);

 			int total = 0;

 			for (int rowIndex = 3, i = 0; rowIndex <= length && i <= length; rowIndex++, i++) {

 				propertyTemplate.drawBorders(new CellRangeAddress(1, rowIndex, 1, 8), BorderStyle.THIN,
 						BorderExtent.ALL);
 				propertyTemplate.drawBorders(new CellRangeAddress(1, rowIndex, 10, 11), BorderStyle.THIN,
 						BorderExtent.ALL);

 				// Criar linhas e c�lulas
 				@SuppressWarnings("unused")
 				Row rowsx = sheet.createRow(rowIndex);
 				XSSFRow rowx = sheet.getRow(rowIndex);

 				// C�lulas - LESTE
 				Cell date_ = rowx.createCell(1);
 				date_.setCellValue(date[i]); 
 				rowx.getCell(1).setCellStyle(style1);

 				rowx = sheet.getRow(rowIndex);
 				Cell hora = rowx.createCell(2); 
 				hora.setCellValue(intervalHour[i]);
 				rowx.getCell(2).setCellStyle(style1); // Estilo

 				rowx = sheet.getRow(rowIndex);
 				Cell motos1 = rowx.createCell(3); 
 				motos1.setCellValue(moto1[i]);
 				
 				rowx.getCell(3).setCellStyle(style1); // Estilo

 				rowx = sheet.getRow(rowIndex);
 				Cell autos1 = rowx.createCell(4); 
 				autos1.setCellValue(auto1[i]);
 				rowx.getCell(4).setCellStyle(style1); // Estilo

 				rowx = sheet.getRow(rowIndex);
 				Cell comercials1 = rowx.createCell(5); 
 				comercials1.setCellValue(com1[i]);
 				rowx.getCell(5).setCellStyle(style1); // Estilo

 				rowx = sheet.getRow(rowIndex);
 				Cell motos2 = rowx.createCell(6);
 				motos2.setCellValue(moto2[i]);
 				rowx.getCell(6).setCellStyle(style1); // Estilo

 				rowx = sheet.getRow(rowIndex);
 				Cell autos2 = rowx.createCell(7);
 				autos2.setCellValue(auto2[i]); 
 				rowx.getCell(7).setCellStyle(style1); // Estilo

 				rowx = sheet.getRow(rowIndex);
 				Cell comericals2 = rowx.createCell(8); 
 				comericals2.setCellValue(com2[i]); 
 				rowx.getCell(8).setCellStyle(style1); // Estilo

 				rowx = sheet.getRow(rowIndex);
 				Cell velocidades1 = rowx.createCell(10); 
 				velocidades1.setCellValue(speed1[i]);
 				rowx.getCell(10).setCellStyle(style1); // Estilo

 				rowx = sheet.getRow(rowIndex);
 				Cell velocidades2 = rowx.createCell(11); 
 				velocidades2.setCellValue(speed2[i]);
 				rowx.getCell(11).setCellStyle(style1); // Estilo

 				total = rowIndex;
 			}

 			int totalRow = total + 1;

 			// Linha Total Veiculos - Tabela BASE
 			Row rowxbase = sheet.createRow(totalRow);
 			rowxbase.createCell(2).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_low_total"));
 			rowxbase.getCell(2).setCellStyle(style);

 			Cell totalMotoBase1 = rowxbase.createCell(3);  
 			totalMotoBase1.setCellFormula("SUM(D4:D" + totalRow + ")");
 			rowxbase.getCell(3).setCellStyle(style1);

 			Cell totalAutoBase1 = rowxbase.createCell(4); 
 			totalAutoBase1.setCellFormula("SUM(E4:E" + totalRow + ")");
 			rowxbase.getCell(4).setCellStyle(style1);

 			Cell totalComericalBase1 = rowxbase.createCell(5); 
 			totalComericalBase1.setCellFormula("SUM(F4:F" + totalRow + ")");
 			rowxbase.getCell(5).setCellStyle(style1);

 			Cell totalMotoBase2 = rowxbase.createCell(6); 
 			totalMotoBase2.setCellFormula("SUM(G4:G" + totalRow + ")");
 			rowxbase.getCell(6).setCellStyle(style1);

 			Cell totalAutoBase2 = rowxbase.createCell(7); 
 			totalAutoBase2.setCellFormula("SUM(H4:H" + totalRow + ")");
 			rowxbase.getCell(7).setCellStyle(style1);

 			Cell totalComercialBase2 = rowxbase.createCell(8); 
 			totalComercialBase2.setCellFormula("SUM(I4:I" + totalRow + ")");
 			rowxbase.getCell(8).setCellStyle(style1);

 			propertyTemplate.drawBorders(new CellRangeAddress(totalRow, totalRow, 2, 8), BorderStyle.THIN,
 					BorderExtent.ALL);

 			propertyTemplate.applyBorders(sheet);

 			// ******* BASE - END ******* //

 			// ------ DIAS BEGIN ------//
 			XSSFSheet sheetDays = null;

 			int ini = 0; // indice para defini��o do deslocamento inicial - Tab dia: intervalo de 15min
 			int fim = 96; // indice para defini��o do deslocamento final - Tab dia: intervalo de 15min
 			int ini_2 = 0;// indice para defini��o do deslocamento inicial - Tab dia: intervalo de 1hora
 			int fim_2 = 24; // indice para defini��o do deslocamento final - Tab dia: intervalo de 1hora

 			// Implemetar data na planilha
 			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
 			String dia = "";
 			String diaSemana = "";

 			/**** STYLE SHEET DAYS ****/
 			// Header Text Title
 			CellStyle styleDays = workbook.createCellStyle();
 			Font fontDays = workbook.createFont();
 			fontDays.setBold(true);
 			fontDays.setFontName(HSSFFont.FONT_ARIAL);
 			fontDays.setFontHeightInPoints((short) 10);
 			styleDays.setFont(fontDays);
 			styleDays.setAlignment(HorizontalAlignment.LEFT);
 			styleDays.setFillBackgroundColor(IndexedColors.WHITE.getIndex());

 			// CELL Header Data
 			CellStyle styleDaysData = workbook.createCellStyle();
 			styleDaysData.setFont(fontDays);
 			styleDaysData.setAlignment(HorizontalAlignment.CENTER);
 			styleDaysData.setFillBackgroundColor(IndexedColors.WHITE.getIndex());

 			// Header Text Description
 			CellStyle styleDays1 = workbook.createCellStyle();
 			Font fontDays1 = workbook.createFont();
 			fontDays1.setFontName(HSSFFont.FONT_ARIAL);
 			fontDays1.setFontHeightInPoints((short) 10);
 			styleDays1.setFont(fontDays1);
 			styleDays1.setAlignment(HorizontalAlignment.CENTER);
 			styleDays1.setFillBackgroundColor(IndexedColors.WHITE.getIndex());

 			// CELL Header Text
 			CellStyle styleDays2 = workbook.createCellStyle();
 			Font fontDays2 = workbook.createFont();
 			fontDays2.setColor(HSSFColor.HSSFColorPredefined.DARK_BLUE.getIndex());
 			fontDays2.setBold(true);
 			fontDays2.setFontName(HSSFFont.FONT_ARIAL);
 			fontDays2.setFontHeightInPoints((short) 10);
 			styleDays2.setFont(fontDays2);
 			styleDays2.setAlignment(HorizontalAlignment.CENTER);
 			styleDays2.setFillBackgroundColor(IndexedColors.WHITE.getIndex());

 			// CELL Header Horas
 			CellStyle styleDaysH = workbook.createCellStyle();
 			styleDaysH.setFont(fontDays2);
 			styleDaysH.setVerticalAlignment(VerticalAlignment.CENTER); // Centralizar no vertical
 			styleDaysH.setAlignment(HorizontalAlignment.CENTER); // Centralizar na horizontal
 			styleDaysH.setFillBackgroundColor(IndexedColors.WHITE.getIndex());

 			// Cell Text Content
 			CellStyle styleDays3 = workbook.createCellStyle();
 			Font fontDays3 = workbook.createFont();
 			fontDays3.setBold(true);
 			fontDays3.setFontName(HSSFFont.FONT_ARIAL);
 			fontDays3.setFontHeightInPoints((short) 10);
 			styleDays3.setFont(fontDays3);
 			styleDays3.setAlignment(HorizontalAlignment.CENTER);
 			styleDays3.setFillBackgroundColor(IndexedColors.WHITE.getIndex());

 			/*** STYLE SHEET DAYS END ***/
 			
 			int mes = Integer.parseInt(month);
 			int ano = Integer.parseInt(year);

 			for (int d = 1; d <= daysInMonth; d++) {

 				// Data na planilha
 				GregorianCalendar gc = new GregorianCalendar();

 				// Formatar apresenta��o da Data
 				if (d < 10 && Integer.parseInt(month) < 10)
 					dia = "0"+ d +"/0"+ mes +"/" + ano;

 				if (d < 10 && mes >= 10)
 					dia = "0"+ d +"/"+ mes +"/" + ano;		

 				if (d >= 10 && mes < 10)
 					dia = d + "/0" + mes + "/" + ano;

 				if(d > 9 && mes > 9)
 					dia = d + "/" + mes + "/" + ano;

 				date_ = sdf.parse(dia);

 				gc.setTime(date_);
 				
 				int dia_semana = gc.get(GregorianCalendar.DAY_OF_WEEK);

 				// Recebe um n�mero que determina qual � o dia da semana, sendo 1 - Domingo ...
 				// 7 S�bado
 				switch (dia_semana) {

 				case 1:
 					diaSemana = localeCalendar.getStringKey("sunday");
 					break;
 				case 2:
 					diaSemana = localeCalendar.getStringKey("monday");
 					break;
 				case 3:
 					diaSemana = localeCalendar.getStringKey("tuesday");
 					break;
 				case 4:
 					diaSemana = localeCalendar.getStringKey("wednesday");
 					break;
 				case 5:
 					diaSemana = localeCalendar.getStringKey("thursday");
 					break;
 				case 6:
 					diaSemana = localeCalendar.getStringKey("friday");
 					break;
 				case 7:
 					diaSemana = localeCalendar.getStringKey("saturday");
 					break;
 				}
 				
 				// ---------------------------------------------------------

 				// Criação das tabs
 				String sheetName = String.valueOf(d); 				
 				sheetDays = workbook.createSheet(sheetName);
 				 				
 				Row rowHead1 = sheetDays.createRow((short) 1);
 				rowHead1.createCell(1).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_strech_title"));
 				rowHead1.createCell(7).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_low_date"));
 				rowHead1.createCell(8).setCellValue(dia);
 				rowHead1.getCell(1).setCellStyle(styleDays);
 				rowHead1.getCell(7).setCellStyle(styleDaysData);
 				rowHead1.getCell(8).setCellStyle(styleDays1);

 				Row rowHead2 = sheetDays.createRow((short) 2);
 				rowHead2.createCell(1).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_company")+": "+RoadConcessionaire.roadConcessionaire+"");
 				rowHead2.createCell(7).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_day_week"));
 				rowHead2.createCell(8).setCellValue(diaSemana);
 				rowHead2.getCell(1).setCellStyle(styleDays);
 				rowHead2.getCell(7).setCellStyle(styleDaysData);
 				rowHead2.getCell(8).setCellStyle(styleDays1);

 				Row rowHead3 = sheetDays.createRow((short) 3);
 				rowHead3.createCell(1).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_lot_tab")+": "+ano);
 				rowHead3.getCell(1).setCellStyle(styleDays);

 				// Equipamento
 				Row rowHeadSat= sheetDays.createRow((short) 4);
 				rowHeadSat.createCell(1).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_low_equipment"));
 				rowHeadSat.createCell(2).setCellValue(sat.getNome());

 				rowHeadSat.getCell(1).setCellStyle(styleDays);
 				rowHeadSat.getCell(2).setCellStyle(styleDays1);

 				// Sentido 1
 				Row rowHead4 = sheetDays.createRow((short) 5);
 				rowHead4.createCell(1).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_road"));
 				rowHead4.createCell(2).setCellValue(sat.getEstrada());
 				rowHead4.createCell(3).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_strech"));
 				rowHead4.createCell(4).setCellValue(sat.getKm());
 				rowHead4.createCell(5).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_complement"));
 				rowHead4.createCell(6).setCellValue(sat.getKm());
 				rowHead4.createCell(7).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_equiv"));
 				rowHead4.createCell(8).setCellValue(equivNumber);

 				// Estilo
 				rowHead4.getCell(1).setCellStyle(styleDays);
 				rowHead4.getCell(2).setCellStyle(styleDays1);
 				rowHead4.getCell(3).setCellStyle(styleDays3);
 				rowHead4.getCell(4).setCellStyle(styleDays1);
 				rowHead4.getCell(5).setCellStyle(styleDays1);
 				rowHead4.getCell(6).setCellStyle(styleDays1);
 				rowHead4.getCell(7).setCellStyle(styleDays);
 				rowHead4.getCell(8).setCellStyle(styleDays1);

 				// Sentido 2
 				rowHead4.createCell(11).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_road"));
 				rowHead4.createCell(12).setCellValue(sat.getEstrada());
 				rowHead4.createCell(13).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_strech"));
 				rowHead4.createCell(14).setCellValue(sat.getKm());
 				rowHead4.createCell(15).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_complement"));
 				rowHead4.createCell(16).setCellValue(sat.getKm());
 				rowHead4.createCell(17).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_equiv"));
 				rowHead4.createCell(18).setCellValue(equivNumber);

 				// Estilo				
 				rowHead4.getCell(11).setCellStyle(styleDays);
 				rowHead4.getCell(12).setCellStyle(styleDays1);
 				rowHead4.getCell(13).setCellStyle(styleDays);
 				rowHead4.getCell(14).setCellStyle(styleDays1);
 				rowHead4.getCell(15).setCellStyle(styleDays1);
 				rowHead4.getCell(16).setCellStyle(styleDays1);
 				rowHead4.getCell(17).setCellStyle(styleDays);
 				rowHead4.getCell(18).setCellStyle(styleDays1);

 				// Sentido 1
 				Row rowHead5 = sheetDays.createRow((short) 8);
 				rowHead5.createCell(1).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_low_hour"));
 				rowHead5.createCell(2).setCellValue(direction1);
 				rowHead5.createCell(6).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_low_total"));
 				rowHead5.createCell(7).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_rate"));
 				rowHead5.createCell(8).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_speed_op"));

 				// Estilo				
 				rowHead5.getCell(1).setCellStyle(styleDaysH);
 				rowHead5.getCell(2).setCellStyle(styleDays2);
 				rowHead5.getCell(6).setCellStyle(styleDays2);
 				rowHead5.getCell(7).setCellStyle(styleDays2);
 				rowHead5.getCell(8).setCellStyle(styleDays2);

 				// Sentido 2
 				rowHead5.createCell(11).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_low_hour"));
 				rowHead5.createCell(12).setCellValue(direction2);
 				rowHead5.createCell(16).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_low_total"));
 				rowHead5.createCell(17).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_rate"));
 				rowHead5.createCell(18).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_speed_op"));

 				// Estilo
 				rowHead5.getCell(11).setCellStyle(styleDaysH);
 				rowHead5.getCell(12).setCellStyle(styleDays2);
 				rowHead5.getCell(16).setCellStyle(styleDays2);
 				rowHead5.getCell(17).setCellStyle(styleDays2);
 				rowHead5.getCell(18).setCellStyle(styleDays2);

 				// Sentido 1
 				rowHead5.createCell(21).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_low_hour"));
 				rowHead5.createCell(22).setCellValue(direction1);
 				rowHead5.createCell(25).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_rate"));
 				rowHead5.createCell(26).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_speed"));

 				// Estilo 
 				rowHead5.getCell(21).setCellStyle(styleDaysH);								
 				rowHead5.getCell(22).setCellStyle(styleDays2);
 				rowHead5.getCell(25).setCellStyle(styleDays2);
 				rowHead5.getCell(26).setCellStyle(styleDays2);

 				// Sentido 2
 				rowHead5.createCell(28).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_low_hour"));
 				rowHead5.createCell(29).setCellValue(direction2);
 				rowHead5.createCell(32).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_rate"));
 				rowHead5.createCell(33).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_speed"));

 				// Estilo 
 				rowHead5.getCell(28).setCellStyle(styleDaysH);
 				rowHead5.getCell(29).setCellStyle(styleDays2);
 				rowHead5.getCell(32).setCellStyle(styleDays2);
 				rowHead5.getCell(33).setCellStyle(styleDays2);


 				Row rowHead6 = sheetDays.createRow((short) 9);

 				// Sentido 1
 				rowHead6.createCell(2).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_motorcycle"));
 				rowHead6.createCell(3).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_light"));
 				rowHead6.createCell(4).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_comm"));
 				rowHead6.createCell(5).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_low_total"));
 				rowHead6.createCell(6).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_accumulated"));
 				rowHead6.createCell(7).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_cph"));
 				rowHead6.createCell(8).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_fifteen_min"));
 				rowHead6.createCell(9).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_sixty_min"));

 				// Estilo
 				for(int i=2; i< 10; i++) 
 					rowHead6.getCell(i).setCellStyle(styleDays2);		

 				// Sentido 2
 				rowHead6.createCell(12).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_motorcycle"));
 				rowHead6.createCell(13).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_light"));
 				rowHead6.createCell(14).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_comm"));
 				rowHead6.createCell(15).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_low_total"));
 				rowHead6.createCell(16).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_accumulated"));
 				rowHead6.createCell(17).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_cph"));
 				rowHead6.createCell(18).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_fifteen_min"));
 				rowHead6.createCell(19).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_sixty_min"));

 				// Estilo
 				for(int i=12; i< 20; i++)
 					rowHead6.getCell(i).setCellStyle(styleDays2);

 				// Sentido 1
 				rowHead6.createCell(22).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_motorcycle"));
 				rowHead6.createCell(23).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_light"));
 				rowHead6.createCell(24).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_comm"));
 				rowHead6.createCell(25).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_cph"));
 				rowHead6.createCell(26).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_kmh"));

 				// Estilo
 				for(int i=22; i< 27; i++)
 					rowHead6.getCell(i).setCellStyle(styleDays2);				

 				// Sentido 2
 				rowHead6.createCell(29).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_motorcycle"));
 				rowHead6.createCell(30).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_light"));
 				rowHead6.createCell(31).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_comm"));
 				rowHead6.createCell(32).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_cph"));
 				rowHead6.createCell(33).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_kmh"));

 				// Estilo
 				for(int i=29; i< 34; i++)
 					rowHead6.getCell(i).setCellStyle(styleDays2);
 				
 				// ----------------------------------------------------------
	 				 				
 				// Largura da Coluna
 	 			sheetDays.setColumnWidth(0, 700);
 	 			sheetDays.setColumnWidth(1, 3500);
 	 			sheetDays.setColumnWidth(8, 3500);
 	 			sheetDays.setColumnWidth(9, 3500);
 	 			sheetDays.setColumnWidth(11, 3500);
 	 			sheetDays.setColumnWidth(18, 3500);
 	 			sheetDays.setColumnWidth(19, 3500);
 	 			sheetDays.setColumnWidth(21, 3500);
 	 			sheetDays.setColumnWidth(28, 3500);
 	 			
 	 			// Tamanhos AutoSize
 	 			
 				for(int i= 2; i < 8; i++)					
 					sheetDays.autoSizeColumn(i);
 				
 				for(int i= 12; i < 18; i++)					
 					sheetDays.autoSizeColumn(i);
 				
 				for(int i= 22; i < 27; i++)					
 					sheetDays.autoSizeColumn(i);
 				
 				for(int i= 29; i < 34; i++)					
 					sheetDays.autoSizeColumn(i); 
 				
 				// ----------------------------------------------------------
 				 
 				// *** Mesclar C�lulas *** //

 				//Cabe�alho										
 				sheetDays.addMergedRegion(CellRangeAddress.valueOf("B2:E2"));
 				sheetDays.addMergedRegion(CellRangeAddress.valueOf("B3:D3"));
 				sheetDays.addMergedRegion(CellRangeAddress.valueOf("B4:C4"));					

 				// Tabela intervalo A - Sentido 1
 				sheetDays.addMergedRegion(CellRangeAddress.valueOf("B9:B10"));
 				sheetDays.addMergedRegion(CellRangeAddress.valueOf("C9:F9"));
 				sheetDays.addMergedRegion(CellRangeAddress.valueOf("I9:J9"));

 				// Tabela intervalo B - Sentido 2					
 				sheetDays.addMergedRegion(CellRangeAddress.valueOf("L9:L10"));
 				sheetDays.addMergedRegion(CellRangeAddress.valueOf("M9:P9"));
 				sheetDays.addMergedRegion(CellRangeAddress.valueOf("S9:T9"));

 				//Tabela hor�ria C - Sentido 1
 				sheetDays.addMergedRegion(CellRangeAddress.valueOf("W9:Y9"));
 				sheetDays.addMergedRegion(CellRangeAddress.valueOf("V9:V10"));

 				//Tabela hor�ria D - Sentido 2
 				sheetDays.addMergedRegion(CellRangeAddress.valueOf("AD9:AF9"));					
 				sheetDays.addMergedRegion(CellRangeAddress.valueOf("AC9:AC10"));

 				// *** Mesclar c�lulas - end *** //

 				// *** Bordas *** //

 				// Tabela intervalo A - Sentido 1
 				propertyDays.drawBorders(new CellRangeAddress(8, 8, 1, 9), BorderStyle.MEDIUM, BorderExtent.TOP);
 				propertyDays.drawBorders(new CellRangeAddress(9, 9, 1, 9), BorderStyle.MEDIUM, BorderExtent.BOTTOM);

 				// Tabela intervalo B - Sentido 2
 				propertyDays.drawBorders(new CellRangeAddress(8, 8, 11, 19), BorderStyle.MEDIUM, BorderExtent.TOP);
 				propertyDays.drawBorders(new CellRangeAddress(9, 9, 11, 19), BorderStyle.MEDIUM, BorderExtent.BOTTOM);

 				// Tabela hor�ria C - Sentido 1
 				propertyDays.drawBorders(new CellRangeAddress(8, 8, 21, 26), BorderStyle.MEDIUM, BorderExtent.TOP);
 				propertyDays.drawBorders(new CellRangeAddress(9, 9, 21, 26), BorderStyle.MEDIUM, BorderExtent.BOTTOM);

 				// Tabela hor�ria D - Sentido 2
 				propertyDays.drawBorders(new CellRangeAddress(8, 8, 28, 33), BorderStyle.MEDIUM, BorderExtent.TOP);
 				propertyDays.drawBorders(new CellRangeAddress(9, 9, 28, 33 ), BorderStyle.MEDIUM, BorderExtent.BOTTOM);

 				//Tabelas intervalos A e B - Sentidos 1 e 2
 				propertyDays.drawBorders(new CellRangeAddress(106, 106, 1, 9), BorderStyle.MEDIUM, BorderExtent.BOTTOM);
 				propertyDays.drawBorders(new CellRangeAddress(106, 106, 11, 19), BorderStyle.MEDIUM,
 						BorderExtent.BOTTOM);

 				//Tabela hor�ria C - Sentido 1
 				propertyDays.drawBorders(new CellRangeAddress(34, 34, 21, 26), BorderStyle.MEDIUM, BorderExtent.TOP);
 				propertyDays.drawBorders(new CellRangeAddress(34, 34, 21, 26), BorderStyle.MEDIUM, BorderExtent.BOTTOM);

 				//Tabela hor�ria D - Sentido 2
 				propertyDays.drawBorders(new CellRangeAddress(34, 34, 28, 33), BorderStyle.MEDIUM, BorderExtent.TOP);
 				propertyDays.drawBorders(new CellRangeAddress(34, 34, 28, 33), BorderStyle.MEDIUM, BorderExtent.BOTTOM);

 				// Popular Sheet(TAB dia - intervalos)
 				if (!sheetDays.getSheetName().equals("1")) {
 					ini += 96;
 					fim += 96;
 					ini_2 += 24;
 					fim_2 += 24;
 				}

 				int rwd = 13;

 				// Preencher as Bordas entre intervalos de tempo
 				for (int x = 0; x < 24; x++) {
 					propertyDays.drawBorders(new CellRangeAddress(rwd, rwd, 1, 9), BorderStyle.MEDIUM,
 							BorderExtent.BOTTOM);
 					propertyDays.drawBorders(new CellRangeAddress(rwd, rwd, 11, 19), BorderStyle.MEDIUM,
 							BorderExtent.BOTTOM);
 					rwd += 4;
 				}

 				for (int rowIndex = 10, j = ini; rowIndex < 107 && j < fim; rowIndex++, j++) {

 					// Criar linhas e c�lulas
 					@SuppressWarnings("unused")
 					Row rowsx = sheetDays.createRow(rowIndex);
 					XSSFRow rowx = sheetDays.getRow(rowIndex);

 					// C�lulas - Sentido 1
 					Cell intervaloS1 = rowx.createCell(1);   
 					intervaloS1.setCellValue(intervalHour[j]);
 					intervaloS1.setCellStyle(styleDays3);

 					rowx = sheetDays.getRow(rowIndex);
 					Cell motos1 = rowx.createCell(2);
 					motos1.setCellValue(moto1[j]);
 					motos1.setCellStyle(styleDays1);

 					rowx = sheetDays.getRow(rowIndex);
 					Cell autos1 = rowx.createCell(3); 
 					autos1.setCellValue(auto1[j]);
 					autos1.setCellStyle(styleDays1);

 					rowx = sheetDays.getRow(rowIndex);
 					Cell comericals1 = rowx.createCell(4); 
 					comericals1.setCellValue(com1[j]);
 					comericals1.setCellStyle(styleDays1);

 					rowx = sheetDays.getRow(rowIndex); 
 					Cell totals1 = rowx.createCell(5);
 					totals1.setCellValue(total1[j]);
 					totals1.setCellStyle(styleDays1);

 					rowx = sheetDays.getRow(rowIndex);
 					Cell totalACMS1 = rowx.createCell(6); 
 					totalACMS1.setCellValue(totalACM1[j]);
 					totalACMS1.setCellStyle(styleDays1);

 					rowx = sheetDays.getRow(rowIndex);
 					Cell fluxos1 = rowx.createCell(7);
 					fluxos1.setCellValue(fluxo1[j]); 
 					fluxos1.setCellStyle(styleDays3);

 					rowx = sheetDays.getRow(rowIndex);
 					Cell velocidadeS1 = rowx.createCell(8) ; 
 					velocidadeS1.setCellValue(speed1[j]);
 					velocidadeS1.setCellStyle(styleDays3);

 					rowx = sheetDays.getRow(rowIndex);
 					Cell velocidadeHora1 = rowx.createCell(9); 
 					velocidadeHora1.setCellValue(speedHour1[j]);
 					velocidadeHora1.setCellStyle(styleDays3);

 					// Linha Total Veiculos - Tabela Acumulada (Sentido 1)

 					Row rowx2 = sheetDays.createRow(106);
 					rowx2.createCell(1).setCellValue("Total");
 					rowx2.getCell(1).setCellStyle(styleDays2);

 					Cell totalMotos1 = rowx2.createCell(2); 
 					totalMotos1.setCellFormula("SUM(C11:C106)");
 					totalMotos1.setCellStyle(styleDays2);

 					Cell totalAutos1 = rowx2.createCell(3); 
 					totalAutos1.setCellFormula("SUM(D11:D106)");
 					totalAutos1.setCellStyle(styleDays2);

 					Cell totalComercials1 = rowx2.createCell(4); 
 					totalComercials1.setCellFormula("SUM(E11:E106)");
 					totalComercials1.setCellStyle(styleDays2);

 					Cell totalS1 = rowx2.createCell(5);  
 					totalS1.setCellFormula("SUM(C107:E107)");
 					totalS1.setCellStyle(styleDays2);

 					Cell totalACMS01 = rowx2.createCell(6);   
 					totalACMS01.setCellValue("---");
 					totalACMS01.setCellStyle(styleDays2);

 					Cell totalFluxoS1 = rowx2.createCell(7); 
 					totalFluxoS1.setCellValue("---");
 					totalFluxoS1.setCellStyle(styleDays2);

 					Cell totalVelocidade15S1 = rowx2.createCell(8);   
 					totalVelocidade15S1.setCellValue("---");
 					totalVelocidade15S1.setCellStyle(styleDays2); 

 					Cell totalVelocidade60S1 = rowx2.createCell(9); 
 					totalVelocidade60S1.setCellValue("---");
 					totalVelocidade60S1.setCellStyle(styleDays2);

 					// C�lulas - Sentido 2
 					Cell intervaloS2 = rowx.createCell(11); 
 					intervaloS2.setCellValue(intervalHour[j]);
 					intervaloS2.setCellStyle(styleDays3); 

 					rowx = sheetDays.getRow(rowIndex);
 					Cell motos2 = rowx.createCell(12); 
 					motos2.setCellValue(moto2[j]);
 					motos2.setCellStyle(styleDays1);

 					rowx = sheetDays.getRow(rowIndex);
 					Cell autos2 = rowx.createCell(13);
 					autos2.setCellValue(auto2[j]);
 					autos2.setCellStyle(styleDays1);

 					rowx = sheetDays.getRow(rowIndex);
 					Cell comercials2 = rowx.createCell(14); 
 					comercials2.setCellValue(com2[j]);
 					comercials2.setCellStyle(styleDays1);

 					rowx = sheetDays.getRow(rowIndex);
 					Cell totals2 = rowx.createCell(15);  
 					totals2.setCellValue(total2[j]);
 					totals2.setCellStyle(styleDays1);

 					rowx = sheetDays.getRow(rowIndex);
 					Cell totalACMs2 = rowx.createCell(16); 
 					totalACMs2.setCellValue(totalACM2[j]);
 					totalACMs2.setCellStyle(styleDays1);

 					rowx = sheetDays.getRow(rowIndex);
 					Cell fluxoS2 = rowx.createCell(17); 
 					fluxoS2.setCellValue(fluxo2[j]);
 					fluxoS2.setCellStyle(styleDays3);

 					rowx = sheetDays.getRow(rowIndex);
 					Cell velocidadeS2 = rowx.createCell(18);
 					velocidadeS2.setCellValue(speed2[j]);
 					velocidadeS2.setCellStyle(styleDays3); 

 					rowx = sheetDays.getRow(rowIndex);
 					Cell velocidadeHora2 = rowx.createCell(19);  
 					velocidadeHora2.setCellValue(speedHour2[j]);
 					velocidadeHora2.setCellStyle(styleDays3);

 					// Linha Total Veiculos - Tabela Acumulada (Sentido 2)

 					rowx2.createCell(11).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_low_total"));
 					rowx2.getCell(11).setCellStyle(styleDays2);

 					Cell totalMotos2 = rowx2.createCell(12); 
 					totalMotos2.setCellFormula("SUM(M11:M106)");
 					totalMotos2.setCellStyle(styleDays2);

 					Cell totalAutos2 = rowx2.createCell(13); 
 					totalAutos2.setCellFormula("SUM(N11:N106)");
 					totalAutos2.setCellStyle(styleDays2);

 					Cell totalComercials2 = rowx2.createCell(14); 
 					totalComercials2.setCellFormula("SUM(O11:O106)");
 					totalComercials2.setCellStyle(styleDays2);

 					Cell totalS2 = rowx2.createCell(15);
 					totalS2.setCellFormula("SUM(M107:O107)"); 
 					totalS2.setCellStyle(styleDays2);

 					Cell TotalACMS2 = rowx2.createCell(16); 
 					TotalACMS2.setCellValue("---");
 					TotalACMS2.setCellStyle(styleDays2);

 					Cell totalFluxoS2 = rowx2.createCell(17); 
 					totalFluxoS2.setCellValue("---");
 					totalFluxoS2.setCellStyle(styleDays2);

 					Cell totalVelocidade15S2 = rowx2.createCell(18);
 					totalVelocidade15S2 .setCellValue("---");
 					totalVelocidade15S2 .setCellStyle(styleDays2);

 					Cell totalVelocidade60S2 = rowx2.createCell(19);
 					totalVelocidade60S2.setCellValue("---");
 					totalVelocidade60S2.setCellStyle(styleDays2);
 				}

 				//Dados das tabelas hor�rias C e D
 				for (int rowIndex = 10, k = ini_2; rowIndex <= 34 && k < fim_2; rowIndex++, k++) {

 					// Inserir dados

 					XSSFRow rowx = sheetDays.getRow(rowIndex);

 					// C�lulas - Sentido1 (Hora) 
 					rowx = sheetDays.getRow(rowIndex);
 					Cell horaIntervaloS1 = rowx.createCell(21); 
 					horaIntervaloS1.setCellValue(hour[k]);
 					horaIntervaloS1.setCellStyle(styleDays3);

 					rowx = sheetDays.getRow(rowIndex);
 					Cell motocicletaACM1 = rowx.createCell(22); 
 					motocicletaACM1.setCellValue(motoACM1[k]); 
 					motocicletaACM1.setCellStyle(styleDays1);

 					rowx = sheetDays.getRow(rowIndex);
 					Cell automovelACM1 = rowx.createCell(23);  
 					automovelACM1.setCellValue(autoACM1[k]);
 					automovelACM1.setCellStyle(styleDays1);

 					rowx = sheetDays.getRow(rowIndex);
 					Cell comercialACM1 = rowx.createCell(24);  
 					comercialACM1.setCellValue(comACM1[k]);
 					comercialACM1.setCellStyle(styleDays1);

 					rowx = sheetDays.getRow(rowIndex);
 					Cell fluxoACMS1 = rowx.createCell(25); 
 					fluxoACMS1.setCellValue(fluxoACM1[k]); 
 					fluxoACMS1.setCellStyle(styleDays1);

 					rowx = sheetDays.getRow(rowIndex); 
 					Cell velocidadeACM1 = rowx.createCell(26); 
 					velocidadeACM1.setCellValue(speedACM1[k]);
 					velocidadeACM1.setCellStyle(styleDays1);

 					// Linha Total Veiculos - Tabela Acumulada Sentido1
 					rowx = sheetDays.getRow(34);
 					rowx.createCell(21).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_low_total"));
 					rowx.getCell(21).setCellStyle(styleDays2);

 					Cell totalMotocicletaACM1 = rowx.createCell(22); 
 					totalMotocicletaACM1.setCellFormula("SUM(W11:W34)");
 					totalMotocicletaACM1.setCellStyle(styleDays2);

 					Cell totalAutomovelACM1 = rowx.createCell(23); 
 					totalAutomovelACM1.setCellFormula("SUM(X11:X34)");
 					totalAutomovelACM1.setCellStyle(styleDays2); 

 					Cell totalComercialACM1 = rowx.createCell(24);  
 					totalComercialACM1.setCellFormula("SUM(Y11:Y34)");
 					totalComercialACM1.setCellStyle(styleDays2);

 					Cell totalFluxoACM1 = rowx.createCell(25); 
 					totalFluxoACM1.setCellFormula("SUM(W35:Y35)");
 					totalFluxoACM1.setCellStyle(styleDays2);

 					Cell totalVelocidadeACM1 = rowx.createCell(26); 
 					totalVelocidadeACM1.setCellValue("---");
 					totalVelocidadeACM1.setCellStyle(styleDays2);						

 					// C�lulas - Sentido2
 					rowx = sheetDays.getRow(rowIndex);
 					Cell intervaloHoraS2 = rowx.createCell(28);
 					intervaloHoraS2.setCellValue(hour[k]);
 					intervaloHoraS2.setCellStyle(styleDays3);

 					rowx = sheetDays.getRow(rowIndex);
 					Cell motocicletaACM2 = rowx.createCell(29);   
 					motocicletaACM2.setCellValue(motoACM2[k]);
 					motocicletaACM2.setCellStyle(styleDays1); 

 					rowx = sheetDays.getRow(rowIndex);
 					Cell automovelACM2 = rowx.createCell(30);  
 					automovelACM2.setCellValue(autoACM2[k]);
 					automovelACM2.setCellStyle(styleDays1);

 					rowx = sheetDays.getRow(rowIndex); 
 					Cell comercialACM2 = rowx.createCell(31); 
 					comercialACM2.setCellValue(comACM2[k]);
 					comercialACM2.setCellStyle(styleDays1);

 					rowx = sheetDays.getRow(rowIndex); 
 					Cell fluxoACMS2 = rowx.createCell(32); 
 					fluxoACMS2.setCellValue(fluxoACM2[k]);
 					fluxoACMS2.setCellStyle(styleDays1);

 					rowx = sheetDays.getRow(rowIndex);
 					Cell velocidadeACM2 = rowx.createCell(33); 
 					velocidadeACM2.setCellValue(speedACM2[k]);
 					velocidadeACM2.setCellStyle(styleDays1);

 					// Linha Total Veiculos - Tabela Acumulada (OESTE)
 					rowx = sheetDays.getRow(34);
 					rowx.createCell(28).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_low_total"));
 					rowx.getCell(28).setCellStyle(styleDays2);

 					Cell totalMotocicletaACM2 = rowx.createCell(29); 
 					totalMotocicletaACM2.setCellFormula("SUM(AD11:AD34)");
 					totalMotocicletaACM2.setCellStyle(styleDays2);

 					Cell totalAutomovelACM2 = rowx.createCell(30);  
 					totalAutomovelACM2.setCellFormula("SUM(AE11:AE34)");
 					totalAutomovelACM2.setCellStyle(styleDays2);

 					Cell totalComercialACM2 = rowx.createCell(31);  
 					totalComercialACM2.setCellFormula("SUM(AF11:AF34)");
 					totalComercialACM2.setCellStyle(styleDays2);

 					Cell totalFluxoACM2 = rowx.createCell(32); 
 					totalFluxoACM2.setCellFormula("SUM(AD35:AF35)"); 
 					totalFluxoACM2.setCellStyle(styleDays2);

 					Cell totalVelocidadeACM2 = rowx.createCell(33); 
 					totalVelocidadeACM2.setCellValue("---");
 					totalVelocidadeACM2.setCellStyle(styleDays2);

 				}

 				propertyDays.applyBorders(sheetDays);
 			}

 			// ------ VDM M�S - BEGIN ------//

 			// Estilo

 			/**** STYLE SHEET VDM ****/
 			// Header Text Title
 			CellStyle styleVDM = workbook.createCellStyle();
 			Font fontVDM = workbook.createFont();
 			fontVDM.setBold(true);
 			fontVDM.setFontName(HSSFFont.FONT_ARIAL);
 			fontVDM.setFontHeightInPoints((short) 10);
 			styleVDM.setFont(fontVDM);
 			styleVDM.setAlignment(HorizontalAlignment.LEFT);
 			styleVDM.setFillBackgroundColor(IndexedColors.WHITE.getIndex());

 			// Header Text Description
 			CellStyle styleVDM1 = workbook.createCellStyle();
 			Font fontVDM1 = workbook.createFont();
 			fontVDM1.setFontName(HSSFFont.FONT_ARIAL);
 			fontVDM1.setFontHeightInPoints((short) 10);
 			styleVDM1.setFont(fontVDM1);
 			styleVDM1.setAlignment(HorizontalAlignment.CENTER);
 			styleVDM1.setFillBackgroundColor(IndexedColors.WHITE.getIndex());

 			// CELL Header Text
 			CellStyle styleVDM2 = workbook.createCellStyle();
 			Font fontVDM2 = workbook.createFont();
 			fontVDM2.setColor(HSSFColor.HSSFColorPredefined.DARK_BLUE.getIndex());
 			fontVDM2.setBold(true);
 			fontVDM2.setFontName(HSSFFont.FONT_ARIAL);
 			fontVDM2.setFontHeightInPoints((short) 10);
 			styleVDM2.setFont(fontDays2);
 			styleVDM2.setAlignment(HorizontalAlignment.CENTER);
 			styleVDM2.setFillBackgroundColor(IndexedColors.WHITE.getIndex());

 			// CELL Header Horas
 			CellStyle styleVDMH = workbook.createCellStyle();
 			styleVDMH.setFont(fontVDM2);
 			styleVDMH.setVerticalAlignment(VerticalAlignment.CENTER); // Centralizar no vertical
 			styleVDMH.setAlignment(HorizontalAlignment.CENTER); // Centralizar na horizontal
 			styleVDMH.setFillBackgroundColor(IndexedColors.WHITE.getIndex());

 			// Cell Text Content
 			CellStyle styleVDM3 = workbook.createCellStyle();
 			Font fontVDM3 = workbook.createFont();
 			fontVDM3.setBold(true);
 			fontVDM3.setFontName(HSSFFont.FONT_ARIAL);
 			fontVDM3.setFontHeightInPoints((short) 10);
 			styleVDM3.setFont(fontVDM3);
 			styleVDM3.setAlignment(HorizontalAlignment.CENTER);
 			styleVDM3.setFillBackgroundColor(IndexedColors.WHITE.getIndex());

 			/*** STYLE SHEET VDM END ***/

 			XSSFSheet sheetVDM = workbook.createSheet(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_sheet_vdm"));

 			Row rowVDM1 = sheetVDM.createRow((short) 1);
 			rowVDM1.createCell(1).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_strech_title"));
 			rowVDM1.getCell(1).setCellStyle(styleVDM);

 			Row rowVDM2 = sheetVDM.createRow((short) 2);
 			rowVDM2.createCell(1).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_company")+": "+RoadConcessionaire.roadConcessionaire+"");
 			rowVDM2.getCell(1).setCellStyle(styleVDM);

 			Row rowVDM3 = sheetVDM.createRow((short) 3);
 			rowVDM3.createCell(1).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_lot_tab")+": "+ano);
 			rowVDM3.getCell(1).setCellStyle(styleVDM);

 			// Equipamento
 			Row rowVDMSat= sheetVDM.createRow((short) 4);
 			rowVDMSat.createCell(1).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_low_equipment"));
 			rowVDMSat.createCell(2).setCellValue(sat.getNome());

 			rowVDMSat.getCell(1).setCellStyle(styleVDM);
 			rowVDMSat.getCell(2).setCellStyle(styleVDM1);

 			Row rowVDM4 = sheetVDM.createRow((short) 5);
 			rowVDM4.createCell(1).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_road"));
 			rowVDM4.createCell(2).setCellValue(sat.getEstrada());
 			rowVDM4.createCell(3).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_strech"));
 			rowVDM4.createCell(4).setCellValue(sat.getKm());
 			rowVDM4.createCell(5).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_complement"));
 			rowVDM4.createCell(6).setCellValue(sat.getKm());
 			rowVDM4.createCell(7).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_low_month")+":");
 			rowVDM4.createCell(8).setCellValue(tm.monthComparison(mes) + " / " + ano);

 			// Estilo
 			rowVDM4.getCell(1).setCellStyle(styleVDM);
 			rowVDM4.getCell(2).setCellStyle(styleVDM1);
 			rowVDM4.getCell(3).setCellStyle(styleVDM3);
 			rowVDM4.getCell(4).setCellStyle(styleVDM1);
 			rowVDM4.getCell(5).setCellStyle(styleVDM1);
 			rowVDM4.getCell(6).setCellStyle(styleVDM1);
 			rowVDM4.getCell(7).setCellStyle(styleVDM3);
 			rowVDM4.getCell(8).setCellStyle(styleVDM1);

 			Row rowVDM5 = sheetVDM.createRow((short) 8);
 			// Sentido 1
 			rowVDM5.createCell(1).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_low_hour"));
 			rowVDM5.createCell(2).setCellValue(direction1);
 			rowVDM5.createCell(5).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_rate"));

 			// Estilo
 			rowVDM5.getCell(1).setCellStyle(styleVDMH);
 			rowVDM5.getCell(2).setCellStyle(styleVDM2);
 			rowVDM5.getCell(5).setCellStyle(styleVDM2);

 			// Sentido 2
 			rowVDM5.createCell(7).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_low_hour"));
 			rowVDM5.createCell(8).setCellValue(direction2);
 			rowVDM5.createCell(11).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_rate"));

 			// Estilo
 			rowVDM5.getCell(7).setCellStyle(styleVDMH);
 			rowVDM5.getCell(8).setCellStyle(styleVDM2);
 			rowVDM5.getCell(11).setCellStyle(styleVDM2);

 			Row rowVDM6 = sheetVDM.createRow((short) 9);

 			rowVDM6.createCell(2).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_motorcycle"));
 			rowVDM6.createCell(3).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_light"));
 			rowVDM6.createCell(4).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_comm"));
 			rowVDM6.createCell(5).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_cph"));

 			// Estilo
 			for(int i=2; i<6; i++)
 				rowVDM6.getCell(i).setCellStyle(styleVDM2);

 			rowVDM6.createCell(8).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_motorcycle"));
 			rowVDM6.createCell(9).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_light"));
 			rowVDM6.createCell(10).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_comm"));
 			rowVDM6.createCell(11).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_cph"));

 			// Estilo
 			for(int i=8; i < 12; i++)
 				rowVDM6.getCell(i).setCellStyle(styleVDM2);

 			// Mesclar c�lulas

 			//Cabe�alho
 			sheetVDM.addMergedRegion(CellRangeAddress.valueOf("B2:E2"));
 			sheetVDM.addMergedRegion(CellRangeAddress.valueOf("B3:D3"));
 			sheetVDM.addMergedRegion(CellRangeAddress.valueOf("B4:C4"));
 			sheetVDM.addMergedRegion(CellRangeAddress.valueOf("I6:K6"));

 			//Tabelas A e B
 			sheetVDM.addMergedRegion(CellRangeAddress.valueOf("B9:B10"));
 			sheetVDM.addMergedRegion(CellRangeAddress.valueOf("C9:E9"));
 			sheetVDM.addMergedRegion(CellRangeAddress.valueOf("I9:K9"));				

 			// Largura da Coluna
 			sheetVDM.setColumnWidth(0, 700);
 			sheetVDM.setColumnWidth(1, 3500);				
 			sheetVDM.setColumnWidth(7, 3500);

 			// Largura AutoSize
 			for(int i=2; i < 6; i++)
 				sheetVDM.autoSizeColumn(i);			

 			for(int i=8; i < 12; i++)
 				sheetVDM.autoSizeColumn(i);	

 			// Tabela A - Sentido 1
 			propertyVDM.drawBorders(new CellRangeAddress(8, 8, 1, 5), BorderStyle.MEDIUM, BorderExtent.TOP);
 			propertyVDM.drawBorders(new CellRangeAddress(9, 9, 1, 5), BorderStyle.MEDIUM, BorderExtent.BOTTOM);

 			//Tabela B - Sentido 2
 			propertyVDM.drawBorders(new CellRangeAddress(8, 8, 7, 11), BorderStyle.MEDIUM, BorderExtent.TOP);
 			propertyVDM.drawBorders(new CellRangeAddress(9, 9, 7, 11), BorderStyle.MEDIUM, BorderExtent.BOTTOM);

 			// *** Total *** //

 			// Tabela C - Sentido 1
 			propertyVDM.drawBorders(new CellRangeAddress(34, 34, 1, 5), BorderStyle.MEDIUM, BorderExtent.TOP);
 			propertyVDM.drawBorders(new CellRangeAddress(34, 34, 1, 5), BorderStyle.MEDIUM, BorderExtent.BOTTOM);

 			// Tabela D - Sentido 2
 			propertyVDM.drawBorders(new CellRangeAddress(34, 34, 7, 11), BorderStyle.MEDIUM, BorderExtent.TOP);
 			propertyVDM.drawBorders(new CellRangeAddress(34, 34, 7, 11), BorderStyle.MEDIUM, BorderExtent.BOTTOM);

 			// Popular Tabelas
 			for (int rowIndex = 10, k = 0; rowIndex <= 34 && k < 24; rowIndex++, k++) {

 				@SuppressWarnings("unused")
 				Row rowsx = sheetVDM.createRow(rowIndex);
 				XSSFRow rowVDM = sheetVDM.getRow(rowIndex);

 				// C�lulas - Sentido 1
 				rowVDM = sheetVDM.getRow(rowIndex);
 				Cell horaVDM1 = rowVDM.createCell(1);  
 				horaVDM1.setCellValue(k);
 				horaVDM1.setCellStyle(styleVDM3);

 				rowVDM = sheetVDM.getRow(rowIndex);
 				Cell motoVDM1 = rowVDM.createCell(2);  
 				motoVDM1.setCellFormula(
 						"ROUND((SUM('1:" + daysInMonth + "'!W" + (rowIndex + 1) + ")/" + daysInMonth + "), 0)");
 				motoVDM1.setCellStyle(styleVDM1);

 				rowVDM = sheetVDM.getRow(rowIndex);
 				Cell autoVDM1 = rowVDM.createCell(3);
 				autoVDM1.setCellFormula(
 						"ROUND((SUM('1:" + daysInMonth + "'!X" + (rowIndex + 1) + ")/" + daysInMonth + "), 0)");
 				autoVDM1.setCellStyle(styleVDM1);

 				rowVDM = sheetVDM.getRow(rowIndex); 
 				Cell comercialVDM1 = rowVDM.createCell(4);
 				comercialVDM1.setCellFormula(
 						"ROUND((SUM('1:" + daysInMonth + "'!Y" + (rowIndex + 1) + ")/" + daysInMonth + "), 0)");
 				comercialVDM1.setCellStyle(styleVDM1);

 				rowVDM = sheetVDM.getRow(rowIndex);
 				Cell fluxoVDM1 = rowVDM.createCell(5); 
 				fluxoVDM1.setCellFormula("SUM(C" + (rowIndex + 1) + " : E" + (rowIndex + 1) + ")");
 				fluxoVDM1.setCellStyle(styleVDM1);

 				// Linha Total Veiculos - M�s (Sentido 1)
 				Row rowTotal = sheetVDM.createRow(34);
 				rowTotal.createCell(1).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_low_total"));
 				rowTotal.getCell(1).setCellStyle(styleVDM2);

 				Cell totalMotos1 = rowTotal.createCell(2); 
 				totalMotos1.setCellFormula("SUM(C11:C34)"); 
 				totalMotos1.setCellStyle(styleVDM2);

 				Cell totalAutos1 = rowTotal.createCell(3); 
 				totalAutos1.setCellFormula("SUM(D11:D34)"); 
 				totalAutos1.setCellStyle(styleVDM2);

 				Cell totalComercial1 = rowTotal.createCell(4);  
 				totalComercial1.setCellFormula("SUM(E11:E34)"); 
 				totalComercial1.setCellStyle(styleVDM2);

 				Cell totalFluxo1 = rowTotal.createCell(5);
 				totalFluxo1.setCellFormula("SUM(C35:E35)"); 
 				totalFluxo1.setCellStyle(styleVDM2);

 				// C�lulas - Sentido 2
 				rowVDM = sheetVDM.getRow(rowIndex);
 				Cell horaVDM2 = rowVDM.createCell(7);  
 				horaVDM2.setCellValue(k);
 				horaVDM2.setCellStyle(styleVDM3);

 				rowVDM = sheetVDM.getRow(rowIndex); 
 				Cell motoVDM2 = rowVDM.createCell(8); 
 				motoVDM2.setCellFormula(
 						"ROUND((SUM('1:" + daysInMonth + "'!AD" + (rowIndex + 1) + ")/" + daysInMonth + "), 0)");
 				motoVDM2.setCellStyle(styleVDM1);

 				rowVDM = sheetVDM.getRow(rowIndex);
 				Cell autoVDM2 = rowVDM.createCell(9); 
 				autoVDM2.setCellFormula(
 						"ROUND((SUM('1:" + daysInMonth + "'!AE" + (rowIndex + 1) + ")/" + daysInMonth + "), 0)");
 				autoVDM2.setCellStyle(styleVDM1);

 				rowVDM = sheetVDM.getRow(rowIndex);
 				Cell comercialVDM2 = rowVDM.createCell(10);  
 				comercialVDM2.setCellFormula(
 						"ROUND((SUM('1:" + daysInMonth + "'!AF" + (rowIndex + 1) + ")/" + daysInMonth + "), 0)");
 				comercialVDM2.setCellStyle(styleVDM1);

 				Cell fluxoVDM2 = rowVDM.createCell(11); 
 				fluxoVDM2.setCellFormula("SUM(I" + (rowIndex + 1) + " : K" + (rowIndex + 1) + ")");
 				fluxoVDM2.setCellStyle(styleVDM1);

 				// Linha Total Veiculos - M�s (Sentido 2)
 				rowTotal.createCell(7).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_low_total"));
 				rowTotal.getCell(7).setCellStyle(styleVDM2);

 				Cell totalMotos2 = rowTotal.createCell(8);
 				totalMotos2.setCellFormula("SUM(I11:I34)"); 
 				totalMotos2.setCellStyle(styleVDM2);

 				Cell totalAutos2 = rowTotal.createCell(9); 
 				totalAutos2.setCellFormula("SUM(J11:J34)");
 				totalAutos2.setCellStyle(styleVDM2);

 				Cell totalComercial2 = rowTotal.createCell(10); 
 				totalComercial2.setCellFormula("SUM(K11:K34)");
 				totalComercial2.setCellStyle(styleVDM2);

 				Cell totalFluxo2 = rowTotal.createCell(11); 
 				totalFluxo2.setCellFormula("SUM(I35:K35)");
 				totalFluxo2.setCellStyle(styleVDM2);
 			}

 			propertyVDM.applyBorders(sheetVDM);

 			// ------ VDM M�S - END ------//

 			// Criar as Tabs Aqui!
 			XSSFSheet sheetGraficoFluxo = workbook.createSheet(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_sheet_data_chart_flow"));
 			XSSFSheet sheetGraficoDensidade = workbook.createSheet(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_sheet_data_chart_density"));
 			XSSFSheet sheetAnalisePDL = workbook.createSheet(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_sheet_double_lane_highway")+direction1);
 			XSSFSheet sheetAnalisePDO = workbook.createSheet(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_sheet_double_lane_highway")+direction2);
 			
 			// Estilo

 			/**** STYLE SHEET Gr�fico Fluxo ****/
 			// Header Text Title
 			CellStyle styleGF = workbook.createCellStyle();
 			Font fontGF = workbook.createFont();
 			fontGF.setBold(true);
 			fontGF.setColor(HSSFColor.HSSFColorPredefined.DARK_BLUE.getIndex());
 			fontGF.setFontName(HSSFFont.FONT_ARIAL);
 			fontGF.setFontHeightInPoints((short) 10);
 			styleGF.setFont(fontGF);
 			styleGF.setAlignment(HorizontalAlignment.CENTER);
 			styleGF.setFillBackgroundColor(IndexedColors.WHITE.getIndex());

 			// Header Text Description
 			CellStyle styleGF1 = workbook.createCellStyle();
 			Font fontGF1 = workbook.createFont();
 			fontGF1.setFontName(HSSFFont.FONT_ARIAL);
 			fontGF1.setFontHeightInPoints((short) 10);
 			styleGF1.setFont(fontGF1);
 			styleGF1.setAlignment(HorizontalAlignment.CENTER);
 			styleGF1.setFillBackgroundColor(IndexedColors.WHITE.getIndex());

 			// Header Text Title
 			CellStyle styleGFH = workbook.createCellStyle();
 			Font fontGFH = workbook.createFont();
 			fontGFH.setBold(true);
 			fontGFH.setFontName(HSSFFont.FONT_ARIAL);
 			fontGFH.setFontHeightInPoints((short) 10);
 			styleGFH.setFont(fontGFH);
 			styleGFH.setAlignment(HorizontalAlignment.CENTER);
 			styleGFH.setFillBackgroundColor(IndexedColors.WHITE.getIndex());

 			// Day and Hour
 			CellStyle styleGFDH = workbook.createCellStyle();
 			Font fontGFDH = workbook.createFont();
 			fontGFDH.setBold(true);
 			fontGFDH.setFontName(HSSFFont.FONT_ARIAL);
 			fontGFDH.setFontHeightInPoints((short) 10);
 			fontGFDH.setColor(HSSFColor.HSSFColorPredefined.DARK_BLUE.getIndex());
 			styleGFDH.setFont(fontGFDH);
 			styleGFDH.setAlignment(HorizontalAlignment.CENTER);
 			styleGFDH.setVerticalAlignment(VerticalAlignment.CENTER);
 			styleGFDH.setFillBackgroundColor(IndexedColors.WHITE.getIndex());

 			/*** STYLE SHEET Gr�fico Fluxo - END ***/

 			// ------ Dados Gr�fico Fluxo - BEGIN ------//

 			Row rowGF1 = sheetGraficoFluxo.createRow((short) 1);
 			rowGF1.createCell(1).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_low_day"));
 			rowGF1.createCell(2).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_low_hour"));
 			rowGF1.createCell(3).setCellValue(direction1);
 			rowGF1.createCell(6).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_rate"));
 			rowGF1.createCell(7).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_vp"));
 			rowGF1.createCell(8).setCellValue(direction2);
 			rowGF1.createCell(11).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_rate"));
 			rowGF1.createCell(12).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_vp"));
 			rowGF1.createCell(13).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_mvs_road")+" " + direction1);
 			rowGF1.createCell(18).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_mvs_road")+" " + direction2);

 			// Estilo
 			rowGF1.getCell(1).setCellStyle(styleGFDH);
 			rowGF1.getCell(2).setCellStyle(styleGFDH);
 			rowGF1.getCell(3).setCellStyle(styleGF);
 			rowGF1.getCell(6).setCellStyle(styleGF);
 			rowGF1.getCell(7).setCellStyle(styleGF);
 			rowGF1.getCell(8).setCellStyle(styleGF);
 			rowGF1.getCell(11).setCellStyle(styleGF);
 			rowGF1.getCell(12).setCellStyle(styleGF);
 			rowGF1.getCell(13).setCellStyle(styleGF);
 			rowGF1.getCell(18).setCellStyle(styleGF);

 			Row rowGF2 = sheetGraficoFluxo.createRow((short) 2);				
 			rowGF2.createCell(3).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_motorcycle"));
 			rowGF2.createCell(4).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_light"));
 			rowGF2.createCell(5).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_comm"));
 			rowGF2.createCell(6).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_cph"));
 			rowGF2.createCell(7).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_cph"));
 			rowGF2.createCell(8).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_motorcycle"));
 			rowGF2.createCell(9).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_light"));
 			rowGF2.createCell(10).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_comm"));
 			rowGF2.createCell(11).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_cph"));
 			rowGF2.createCell(12).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_cph"));
 			rowGF2.createCell(13).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nsa"));
 			rowGF2.createCell(14).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nsb"));
 			rowGF2.createCell(15).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nsc"));
 			rowGF2.createCell(16).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nsd"));
 			rowGF2.createCell(17).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nse"));
 			rowGF2.createCell(18).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nsa"));
 			rowGF2.createCell(19).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nsb"));
 			rowGF2.createCell(20).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nsc"));
 			rowGF2.createCell(21).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nsd"));
 			rowGF2.createCell(22).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nse"));

 			// Estilo			
 			for(int i=3; i < 23; i++)
 				rowGF2.getCell(i).setCellStyle(styleGF);				

 			// Mesclar c�lulas

 			//Tabela
 			sheetGraficoFluxo.addMergedRegion(CellRangeAddress.valueOf("B2:B3"));
 			sheetGraficoFluxo.addMergedRegion(CellRangeAddress.valueOf("C2:C3"));
 			sheetGraficoFluxo.addMergedRegion(CellRangeAddress.valueOf("D2:F2"));
 			sheetGraficoFluxo.addMergedRegion(CellRangeAddress.valueOf("I2:K2"));
 			sheetGraficoFluxo.addMergedRegion(CellRangeAddress.valueOf("N2:R2"));
 			sheetGraficoFluxo.addMergedRegion(CellRangeAddress.valueOf("S2:W2"));

 			// Largura da Coluna
 			sheetGraficoFluxo.setColumnWidth(0, 700);				
 			sheetGraficoFluxo.setColumnWidth(3, 4300);
 			sheetGraficoFluxo.setColumnWidth(4, 4300);
 			sheetGraficoFluxo.setColumnWidth(5, 4500);
 			sheetGraficoFluxo.setColumnWidth(6, 4000);
 			sheetGraficoFluxo.setColumnWidth(7, 4300);
 			sheetGraficoFluxo.setColumnWidth(8, 4300);
 			sheetGraficoFluxo.setColumnWidth(9, 4000);
 			sheetGraficoFluxo.setColumnWidth(10, 4500);
 			sheetGraficoFluxo.setColumnWidth(11, 4000);
 			sheetGraficoFluxo.setColumnWidth(12, 3000);
 			sheetGraficoFluxo.setColumnWidth(13, 3000);
 			sheetGraficoFluxo.setColumnWidth(14, 3000);
 			sheetGraficoFluxo.setColumnWidth(15, 3000);
 			sheetGraficoFluxo.setColumnWidth(16, 3000);
 			sheetGraficoFluxo.setColumnWidth(17, 3000);
 			sheetGraficoFluxo.setColumnWidth(18, 3000);
 			sheetGraficoFluxo.setColumnWidth(19, 3000);
 			sheetGraficoFluxo.setColumnWidth(20, 3000);

 			// Bordas
 			propertyGF.drawBorders(new CellRangeAddress(1, 1, 1, 22), BorderStyle.MEDIUM, BorderExtent.TOP);
 			propertyGF.drawBorders(new CellRangeAddress(2, 2, 1, 22), BorderStyle.MEDIUM, BorderExtent.BOTTOM);

 			int rwgf = 26;
 			for (int dx = 1; dx <= daysInMonth; dx++) {

 				propertyGF.drawBorders(new CellRangeAddress(rwgf, rwgf, 1, 22), BorderStyle.THIN, BorderExtent.BOTTOM);
 				rwgf += 24;
 			}

 			int length = ((24 * daysInMonth) + 2);
 			int h = 0;
 			int d = 1;
 			int i = 1;
 			int index = 11;
 			int rwx = 19;

 			for (int rowIndex = 3; rowIndex <= length; rowIndex++) {

 				if (h == 24)
 					h = 0; // zerar indice = h;
 				if (index == 35) {
 					index = 11;
 					i++;
 				} // resetar index ao valor inicial e incrementa um dia

 				@SuppressWarnings("unused")
 				Row row = sheetGraficoFluxo.createRow(rowIndex);
 				XSSFRow rowFlx = sheetGraficoFluxo.getRow(rowIndex);
 				Cell day = rowFlx.createCell(1);				
 				day.setCellStyle(styleGF);

 				// Incrementar dias
 				if (h == 0) {
 					rowFlx = sheetGraficoFluxo.getRow(rowIndex);
 					day.setCellValue(d);
 					d++;
 				}

 				rowFlx = sheetGraficoFluxo.getRow(rowIndex);
 				Cell hora = rowFlx.createCell(2);
 				hora.setCellValue(h);
 				hora.setCellStyle(styleGFH);
 				h++;

 				rowFlx = sheetGraficoFluxo.getRow(rowIndex);
 				Cell motos1 = rowFlx.createCell(3); 
 				motos1.setCellFormula("'" + i + "'!W" + index + "");
 				motos1.setCellStyle(styleGF1); 

 				rowFlx = sheetGraficoFluxo.getRow(rowIndex);
 				Cell autos1 = rowFlx.createCell(4);
 				autos1.setCellFormula("'" + i + "'!X" + index + "");
 				autos1.setCellStyle(styleGF1);

 				rowFlx = sheetGraficoFluxo.getRow(rowIndex);
 				Cell comercial1 = rowFlx.createCell(5); 
 				comercial1.setCellFormula("'" + i + "'!Y" + index + "");
 				comercial1.setCellStyle(styleGF1);

 				rowFlx = sheetGraficoFluxo.getRow(rowIndex);
 				Cell fluxo1 = rowFlx.createCell(6); 
 				fluxo1.setCellFormula("'" + i + "'!Z" + index + "");
 				fluxo1.setCellStyle(styleGF1);

 				rowFlx = sheetGraficoFluxo.getRow(rowIndex);
 				Cell VP1 = rowFlx.createCell(7); 
 				VP1.setCellFormula(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_sheet_double_lane_highway")+direction1+ "!H" + rwx + "");
 				VP1.setCellStyle(styleGF1);

 				rowFlx = sheetGraficoFluxo.getRow(rowIndex);
 				Cell moto2 = rowFlx.createCell(8); 
 				moto2.setCellFormula("'" + i + "'!AD" + index + "");
 				moto2.setCellStyle(styleGF1);

 				rowFlx = sheetGraficoFluxo.getRow(rowIndex);
 				Cell autos2 = rowFlx.createCell(9); 
 				autos2.setCellFormula("'" + i + "'!AE" + index + "");
 				autos2.setCellStyle(styleGF1);

 				rowFlx = sheetGraficoFluxo.getRow(rowIndex);
 				Cell comercial2 = rowFlx.createCell(10);  
 				comercial2.setCellFormula("'" + i + "'!AF" + index + "");
 				comercial2.setCellStyle(styleGF1);

 				rowFlx = sheetGraficoFluxo.getRow(rowIndex);
 				Cell fluxo2 = rowFlx.createCell(11);
 				fluxo2.setCellFormula("'" + i + "'!AG" + index + "");
 				fluxo2.setCellStyle(styleGF1);

 				rowFlx = sheetGraficoFluxo.getRow(rowIndex);
 				Cell VP2 = rowFlx.createCell(12); 
 				VP2.setCellFormula(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_sheet_double_lane_highway")+direction2 +"!H" + rwx + "");
 				VP2.setCellStyle(styleGF1);

 				// MVS - Pista Leste
 				rowFlx = sheetGraficoFluxo.getRow(rowIndex); 
 				Cell NSA1 = rowFlx.createCell(13); 
 				NSA1.setCellFormula(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_sheet_double_lane_highway")+direction1 + "!$G$6");
 				NSA1.setCellStyle(styleGF1);

 				rowFlx = sheetGraficoFluxo.getRow(rowIndex);
 				Cell NSB1 = rowFlx.createCell(14); 
 				NSB1.setCellFormula(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_sheet_double_lane_highway")+direction1 + "!$G$7");
 				NSB1.setCellStyle(styleGF1);

 				rowFlx = sheetGraficoFluxo.getRow(rowIndex);
 				Cell NSC1 = rowFlx.createCell(15); 
 				NSC1.setCellFormula(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_sheet_double_lane_highway")+direction1 + "!$G$8");
 				NSC1.setCellStyle(styleGF1);

 				rowFlx = sheetGraficoFluxo.getRow(rowIndex);
 				Cell NSD1 = rowFlx.createCell(16); 
 				NSD1.setCellFormula(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_sheet_double_lane_highway")+direction1 + "!$G$9");
 				NSD1.setCellStyle(styleGF1);

 				rowFlx = sheetGraficoFluxo.getRow(rowIndex); 
 				Cell NSE1 = rowFlx.createCell(17);
 				NSE1.setCellFormula(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_sheet_double_lane_highway")+direction1 + "!$G$10");
 				NSE1.setCellStyle(styleGF1);

 				// MVS - Pista Oeste
 				rowFlx = sheetGraficoFluxo.getRow(rowIndex);
 				Cell NSA2 = rowFlx.createCell(18); 
 				NSA2.setCellFormula(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_sheet_double_lane_highway")+direction2 + "!$G$6");
 				NSA2.setCellStyle(styleGF1);

 				rowFlx = sheetGraficoFluxo.getRow(rowIndex);
 				Cell NSB2 = rowFlx.createCell(19); 
 				NSB2.setCellFormula(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_sheet_double_lane_highway")+direction2 + "!$G$7");
 				NSB2.setCellStyle(styleGF1);

 				rowFlx = sheetGraficoFluxo.getRow(rowIndex);
 				Cell NSC2 = rowFlx.createCell(20); 
 				NSC2.setCellFormula(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_sheet_double_lane_highway")+direction2 + "!$G$8");
 				NSC2.setCellStyle(styleGF1); 

 				rowFlx = sheetGraficoFluxo.getRow(rowIndex);
 				Cell NSD2 = rowFlx.createCell(21);
 				NSD2.setCellFormula(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_sheet_double_lane_highway")+direction2 + "!$G$9");
 				NSD2.setCellStyle(styleGF1);

 				rowFlx = sheetGraficoFluxo.getRow(rowIndex); 
 				Cell NSE2 = rowFlx.createCell(22);
 				NSE2.setCellFormula(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_sheet_double_lane_highway")+direction2 + "!$G$10");
 				NSE2.setCellStyle(styleGF1);

 				index++;
 				rwx++;
 			}

 			propertyGF.applyBorders(sheetGraficoFluxo);

 			// ------ Dados Gr�fico Fluxo - END ------//

 			// ------ Dados Gr�fico Densidade - BEGIN ------//

 			// Estilo

 			/**** STYLE SHEET Gr�fico Densidade ****/
 			// Header Text Title
 			CellStyle styleGD = workbook.createCellStyle();
 			Font fontGD = workbook.createFont();
 			fontGD.setBold(true);
 			fontGD.setColor(HSSFColor.HSSFColorPredefined.DARK_BLUE.getIndex());
 			fontGD.setFontName(HSSFFont.FONT_ARIAL);
 			fontGD.setFontHeightInPoints((short) 10);
 			styleGD.setFont(fontGD);
 			styleGD.setAlignment(HorizontalAlignment.CENTER);
 			styleGD.setFillBackgroundColor(IndexedColors.WHITE.getIndex());

 			// Header Text Description
 			CellStyle styleGD1 = workbook.createCellStyle();
 			Font fontGD1 = workbook.createFont();
 			fontGD1.setFontName(HSSFFont.FONT_ARIAL);
 			fontGD1.setFontHeightInPoints((short) 10);
 			styleGD1.setFont(fontGF1);
 			styleGD1.setAlignment(HorizontalAlignment.CENTER);
 			styleGD1.setFillBackgroundColor(IndexedColors.WHITE.getIndex());

 			// Header Text Title
 			CellStyle styleGDH = workbook.createCellStyle();
 			Font fontGDH = workbook.createFont();
 			fontGDH.setBold(true);
 			fontGDH.setFontName(HSSFFont.FONT_ARIAL);
 			fontGDH.setFontHeightInPoints((short) 10);
 			styleGDH.setFont(fontGDH);
 			styleGDH.setAlignment(HorizontalAlignment.CENTER);
 			styleGDH.setFillBackgroundColor(IndexedColors.WHITE.getIndex());

 			// Day and Hour
 			CellStyle styleGDDH = workbook.createCellStyle();
 			Font fontGDDH = workbook.createFont();
 			fontGDDH.setBold(true);
 			fontGDDH.setFontName(HSSFFont.FONT_ARIAL);
 			fontGDDH.setFontHeightInPoints((short) 10);
 			fontGDDH.setColor(HSSFColor.HSSFColorPredefined.DARK_BLUE.getIndex());
 			styleGDDH.setFont(fontGDDH);
 			styleGDDH.setAlignment(HorizontalAlignment.CENTER);
 			styleGDDH.setVerticalAlignment(VerticalAlignment.CENTER);
 			styleGDDH.setFillBackgroundColor(IndexedColors.WHITE.getIndex());

 			/*** STYLE SHEET Gr�fico Densidade - END ***/

 			Row rowGFD = sheetGraficoDensidade.createRow((short) 1);
 			rowGFD.createCell(1).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_low_day"));
 			rowGFD.createCell(2).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_low_hour"));
 			rowGFD.createCell(3).setCellValue(direction1);
 			rowGFD.createCell(4).setCellValue(direction2);
 			rowGFD.createCell(5).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_max_density_road")+" "+ direction1);
 			rowGFD.createCell(10).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_max_density_road")+" "+ direction2);

 			// Estilo
 			rowGFD.getCell(1).setCellStyle(styleGDDH);
 			rowGFD.getCell(2).setCellStyle(styleGDDH);
 			rowGFD.getCell(3).setCellStyle(styleGD);
 			rowGFD.getCell(4).setCellStyle(styleGD);
 			rowGFD.getCell(5).setCellStyle(styleGD);
 			rowGFD.getCell(10).setCellStyle(styleGD);

 			Row rowGFD2 = sheetGraficoDensidade.createRow((short) 2);				
 			rowGFD2.createCell(3).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_density"));
 			rowGFD2.createCell(4).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_density"));
 			rowGFD2.createCell(5).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nsa"));
 			rowGFD2.createCell(6).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nsb"));
 			rowGFD2.createCell(7).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nsc"));
 			rowGFD2.createCell(8).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nsd"));
 			rowGFD2.createCell(9).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nse"));
 			rowGFD2.createCell(10).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nsa"));
 			rowGFD2.createCell(11).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nsb"));
 			rowGFD2.createCell(12).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nsc"));
 			rowGFD2.createCell(13).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nsd"));
 			rowGFD2.createCell(14).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nse"));		
 			
 			for(int k=3; k < 15; k++)
 				rowGFD2.getCell(k).setCellStyle(styleGD);

 			// Mesclar c�lulas
 			sheetGraficoDensidade.addMergedRegion(CellRangeAddress.valueOf("B2:B3"));
 			sheetGraficoDensidade.addMergedRegion(CellRangeAddress.valueOf("C2:C3"));
 			sheetGraficoDensidade.addMergedRegion(CellRangeAddress.valueOf("F2:J2"));
 			sheetGraficoDensidade.addMergedRegion(CellRangeAddress.valueOf("K2:O2"));

 			// Largura da Coluna
 			sheetGraficoDensidade.setColumnWidth(0, 700);				
 			sheetGraficoDensidade.setColumnWidth(3, 3800);
 			sheetGraficoDensidade.setColumnWidth(4, 3800);
 			sheetGraficoDensidade.setColumnWidth(5, 3500);
 			sheetGraficoDensidade.setColumnWidth(6, 3500);
 			sheetGraficoDensidade.setColumnWidth(7, 3500);
 			sheetGraficoDensidade.setColumnWidth(8, 3500);
 			sheetGraficoDensidade.setColumnWidth(9, 3500);
 			sheetGraficoDensidade.setColumnWidth(10, 3500);
 			sheetGraficoDensidade.setColumnWidth(11, 3500);
 			sheetGraficoDensidade.setColumnWidth(12, 3500);
 			sheetGraficoDensidade.setColumnWidth(13, 3500);
 			sheetGraficoDensidade.setColumnWidth(14, 3500);

 			// Bordas
 			propertyGD.drawBorders(new CellRangeAddress(1, 1, 1, 14), BorderStyle.MEDIUM, BorderExtent.TOP);
 			propertyGD.drawBorders(new CellRangeAddress(2, 2, 1, 14), BorderStyle.MEDIUM, BorderExtent.BOTTOM);

 			int rwgd = 26;
 			for (int dx = 1; dx <= daysInMonth; dx++) {

 				propertyGD.drawBorders(new CellRangeAddress(rwgd, rwgd, 1, 14), BorderStyle.THIN, BorderExtent.BOTTOM);
 				rwgd += 24;
 			}

 			int length1 = ((24 * daysInMonth) + 2);
 			int h1 = 0;
 			int d1 = 1;
 			int den = 19;

 			for (int rowIndex = 3; rowIndex <= length1; rowIndex++) {

 				if (h1 == 24)
 					h1 = 0; // zerar indice = h;

 				@SuppressWarnings("unused")
 				Row rowd = sheetGraficoDensidade.createRow(rowIndex);
 				XSSFRow rowFlxd = sheetGraficoDensidade.getRow(rowIndex);
 				Cell day = rowFlxd.createCell(1);
 				day.setCellStyle(styleGD);

 				// Incrementar dias
 				if (h1 == 0) {
 					rowFlxd = sheetGraficoDensidade.getRow(rowIndex);
 					day.setCellValue(d1);
 					d1++;
 				}

 				rowFlxd = sheetGraficoDensidade.getRow(rowIndex);
 				Cell hora = rowFlxd.createCell(2);
 				hora.setCellValue(h1);
 				hora.setCellStyle(styleGDH);
 				h1++;

 				rowFlxd = sheetGraficoDensidade.getRow(rowIndex); 
 				Cell dir1 = rowFlxd.createCell(3); 
 				dir1.setCellFormula(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_sheet_double_lane_highway")+direction1 + "!J" + den + "");
 				dir1.setCellStyle(styleGD1);

 				rowFlxd = sheetGraficoDensidade.getRow(rowIndex);
 				Cell dir2 = rowFlxd.createCell(4); 
 				dir2.setCellFormula(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_sheet_double_lane_highway")+direction2 + "!J" + den + "");
 				dir2.setCellStyle(styleGD1);

 				// Max. Densidade - Pista Sentido1
 				rowFlxd = sheetGraficoDensidade.getRow(rowIndex);
 				Cell NSA1 = rowFlxd.createCell(5); 
 				NSA1.setCellFormula(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_sheet_double_lane_highway")+direction1 + "!$I$6");
 				NSA1.setCellStyle(styleGD1);

 				rowFlxd = sheetGraficoDensidade.getRow(rowIndex);
 				Cell NSB1 = rowFlxd.createCell(6); 
 				NSB1.setCellFormula(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_sheet_double_lane_highway")+direction1 + "!$I$7");
 				NSB1.setCellStyle(styleGD1);

 				rowFlxd = sheetGraficoDensidade.getRow(rowIndex);
 				Cell NSC1 = rowFlxd.createCell(7); 
 				NSC1.setCellFormula(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_sheet_double_lane_highway")+direction1 + "!$I$8");
 				NSC1.setCellStyle(styleGD1);

 				rowFlxd = sheetGraficoDensidade.getRow(rowIndex);
 				Cell NSD1 = rowFlxd.createCell(8); 
 				NSD1.setCellFormula(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_sheet_double_lane_highway")+direction1 + "!$I$9");
 				NSD1.setCellStyle(styleGD1);

 				rowFlxd = sheetGraficoDensidade.getRow(rowIndex);
 				Cell NSE1 = rowFlxd.createCell(9);
 				NSE1.setCellFormula(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_sheet_double_lane_highway")+direction1 + "!$I$10");
 				NSE1.setCellStyle(styleGD1);

 				// Max. Densidade - Pista Sentido 2
 				rowFlxd = sheetGraficoDensidade.getRow(rowIndex);
 				Cell NAS2 = rowFlxd.createCell(10);
 				NAS2.setCellFormula(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_sheet_double_lane_highway")+direction2 + "!$I$6");
 				NAS2.setCellStyle(styleGD1); 

 				rowFlxd = sheetGraficoDensidade.getRow(rowIndex);
 				Cell NSB2 = rowFlxd.createCell(11);
 				NSB2.setCellFormula(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_sheet_double_lane_highway")+direction2 + "!$I$7");
 				NSB2.setCellStyle(styleGD1);

 				rowFlxd = sheetGraficoDensidade.getRow(rowIndex);
 				Cell NSC2 = rowFlxd.createCell(12); 
 				NSC2.setCellFormula(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_sheet_double_lane_highway")+direction2 + "!$I$8");
 				NSC2.setCellStyle(styleGD1); 

 				rowFlxd = sheetGraficoDensidade.getRow(rowIndex);
 				Cell NSD2 = rowFlxd.createCell(13);
 				NSD2.setCellFormula(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_sheet_double_lane_highway")+direction2 + "!$I$9");
 				NSD2.setCellStyle(styleGD1);

 				rowFlxd = sheetGraficoDensidade.getRow(rowIndex);
 				Cell NSE2 = rowFlxd.createCell(14);
 				NSE2.setCellFormula(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_sheet_double_lane_highway")+direction2 + "!$I$10");
 				NSE2.setCellStyle(styleGD1);

 				den++;
 			}

 			propertyGD.applyBorders(sheetGraficoDensidade);

 			// ------ Dados Gr�fico Densidade - END ------//


 			// ------ An�lise Pista_Dupla_Sentido1 - BEGIN ------//

 			// Estilo

 			/**** STYLE SHEET Pista Dupla Sentido 1 ****/

 			// Header Text Title
 			CellStyle stylePDL = workbook.createCellStyle();
 			Font fontPDL = workbook.createFont();
 			fontPDL.setBold(true);
 			fontPDL.setFontName(HSSFFont.FONT_ARIAL);
 			fontPDL.setFontHeightInPoints((short) 10);
 			stylePDL.setFont(fontPDL);
 			stylePDL.setWrapText(true);
 			stylePDL.setAlignment(HorizontalAlignment.CENTER);
 			stylePDL.setFillBackgroundColor(IndexedColors.WHITE.getIndex());

 			// Header Text Title - MIDDLE
 			CellStyle stylePDL2 = workbook.createCellStyle();
 			stylePDL2.setFont(fontPDL);
 			stylePDL2.setAlignment(HorizontalAlignment.CENTER);
 			stylePDL2.setVerticalAlignment(VerticalAlignment.CENTER);
 			stylePDL2.setFillBackgroundColor(IndexedColors.WHITE.getIndex());

 			// Header Text Title - SHORT
 			CellStyle stylePDLS = workbook.createCellStyle();
 			Font fontPDLS = workbook.createFont();
 			fontPDLS.setFontHeightInPoints((short) 8);
 			stylePDLS.setFont(fontPDLS);
 			stylePDLS.setAlignment(HorizontalAlignment.LEFT);
 			stylePDLS.setFillBackgroundColor(IndexedColors.WHITE.getIndex());

 			// Header Text Description
 			CellStyle stylePDL1 = workbook.createCellStyle();
 			Font fontPDL1 = workbook.createFont();
 			fontPDL1.setFontName(HSSFFont.FONT_ARIAL);
 			fontPDL1.setFontHeightInPoints((short) 10);
 			stylePDL1.setFont(fontPDL1);
 			stylePDL1.setAlignment(HorizontalAlignment.CENTER);
 			stylePDL1.setFillBackgroundColor(IndexedColors.WHITE.getIndex());

 			// Header Text Title - MIDDLE
 			CellStyle stylePDL3 = workbook.createCellStyle();
 			stylePDL3.setFont(fontPDL1);
 			stylePDL3.setAlignment(HorizontalAlignment.CENTER);
 			stylePDL3.setVerticalAlignment(VerticalAlignment.CENTER);
 			stylePDL3.setFillBackgroundColor(IndexedColors.WHITE.getIndex());

 			/*** STYLE SHEET Pista Dupla Sentido1 - END ***/

 			Row rowPDL1 = sheetAnalisePDL.createRow((short) 1);
 			rowPDL1.createCell(1).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_company")+": "+RoadConcessionaire.roadConcessionaire+" - "+ano);
 			rowPDL1.getCell(1).setCellStyle(stylePDL);

 			Row rowPDL2 = sheetAnalisePDL.createRow((short) 2);
 		    rowPDL2.createCell(1).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_multitrack_track_segment_header"));
 			rowPDL2.getCell(1).setCellStyle(stylePDL);

 			Row rowPDL4 = sheetAnalisePDL.createRow((short) 4);
 			rowPDL4.createCell(1).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_road_header"));
 			rowPDL4.createCell(2).setCellValue(sat.getEstrada() + " " + direction1);
 			rowPDL4.createCell(3).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_ground_header"));
 			rowPDL4.createCell(4).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_ground"));
 			rowPDL4.createCell(5).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_service_level_header"));
 			rowPDL4.createCell(6).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_service_level"));
 			rowPDL4.createCell(7).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_service_level_bw"));
 			rowPDL4.createCell(8).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_service_level_max"));

 			// Estilo
 			rowPDL4.getCell(1).setCellStyle(stylePDL3);
 			rowPDL4.getCell(2).setCellStyle(stylePDL3);
 			rowPDL4.getCell(3).setCellStyle(stylePDL3);
 			rowPDL4.getCell(4).setCellStyle(stylePDL3);
 			rowPDL4.getCell(5).setCellStyle(stylePDL3);
 			rowPDL4.getCell(6).setCellStyle(stylePDL3);
 			rowPDL4.getCell(7).setCellStyle(stylePDL3);
 			rowPDL4.getCell(8).setCellStyle(stylePDL3);

 			Row rowPDL5 = sheetAnalisePDL.createRow((short) 5);
 			rowPDL5.createCell(1).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_strech_header")+": ");
 			rowPDL5.createCell(2).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_th1_road_header") + sat.getEstrada());
 			rowPDL5.createCell(3).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_et_road_header"));
 			rowPDL5.createCell(4).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_analyze_dbl_number"));
 			rowPDL5.createCell(5).setCellValue(categoriaA);
 			rowPDL5.createCell(6)
 			.setCellFormula("IF($E$7>=100,700,IF($E$7>=90,630,IF($E$7>=80,560,IF($E$7>=70,490,490))))");
 			rowPDL5.createCell(7)
 			.setCellFormula("IF($E$7>=100,100,IF($E$7>=90,90,IF($E$7>=80,80,IF($E$7>=70,70,70))))");
 			rowPDL5.createCell(8).setCellFormula("ROUND(+G6/H6, 1)");

 			// Estilo
 			rowPDL5.getCell(1).setCellStyle(stylePDL1);
 			rowPDL5.getCell(2).setCellStyle(stylePDL1);
 			rowPDL5.getCell(3).setCellStyle(stylePDL1);
 			rowPDL5.getCell(4).setCellStyle(stylePDL1);
 			rowPDL5.getCell(5).setCellStyle(stylePDL1);
 			rowPDL5.getCell(6).setCellStyle(stylePDL1);
 			rowPDL5.getCell(7).setCellStyle(stylePDL1);
 			rowPDL5.getCell(8).setCellStyle(stylePDL1);

 			Row rowPDL6 = sheetAnalisePDL.createRow((short) 6);
 			rowPDL6.createCell(1).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_analyze_dbl_lanes_header")+":");
 			rowPDL6.createCell(2).setCellValue(sat.getNumFaixas());
 			rowPDL6.createCell(3).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_analyze_dbl_ffs_header"));
 			rowPDL6.createCell(4).setCellFormula("E8-E9-E10-E11-C12");
 			rowPDL6.createCell(5).setCellValue(categoriaB);
 			rowPDL6.createCell(6)
 			.setCellFormula("IF($E$7>=100,1100,IF($E$7>=90,990,IF($E$7>=80,880,IF($E$7>=70,770,770))))");
 			rowPDL6.createCell(7)
 			.setCellFormula("IF($E$7>=100,100,IF($E$7>=90,90,IF($E$7>=80,80,IF($E$7>=70,70,70))))");
 			rowPDL6.createCell(8).setCellFormula("ROUND(+G7/H7,1)");

 			// Estilo
 			rowPDL6.getCell(1).setCellStyle(stylePDL1);
 			rowPDL6.getCell(2).setCellStyle(stylePDL1);
 			rowPDL6.getCell(3).setCellStyle(stylePDL1);
 			rowPDL6.getCell(4).setCellStyle(stylePDL1);
 			rowPDL6.getCell(5).setCellStyle(stylePDL1);
 			rowPDL6.getCell(6).setCellStyle(stylePDL1);
 			rowPDL6.getCell(7).setCellStyle(stylePDL1);
 			rowPDL6.getCell(8).setCellStyle(stylePDL1);

 			Row rowPDL7 = sheetAnalisePDL.createRow((short) 7);
 			rowPDL7.createCell(1).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_analyze_dbl_fph_header")+":");
 			rowPDL7.createCell(2).setCellValue(fph_value);
 			rowPDL7.createCell(3).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_analyze_dbl_ffsi_header"));
 			rowPDL7.createCell(4).setCellValue(fssi_value);
 			rowPDL7.createCell(5).setCellValue(categoriaC);
 			rowPDL7.createCell(6)
 			.setCellFormula("IF($E$7>=100,1575,IF($E$7>=90,1435,IF($E$7>=80,1280,IF($E$7>=70,1120,1120))))");
 			rowPDL7.createCell(7)
 			.setCellFormula("IF($E$7>=100,98.4,IF($E$7>=90,89.8,IF($E$7>=80,80,IF($E$7>=70,70,70))))");
 			rowPDL7.createCell(8).setCellFormula("ROUND(+G8/H8,1)");

 			// Estilo
 			rowPDL7.getCell(1).setCellStyle(stylePDL1);
 			rowPDL7.getCell(2).setCellStyle(stylePDL1);
 			rowPDL7.getCell(3).setCellStyle(stylePDL1);
 			rowPDL7.getCell(4).setCellStyle(stylePDL1);
 			rowPDL7.getCell(5).setCellStyle(stylePDL1);
 			rowPDL7.getCell(6).setCellStyle(stylePDL1);
 			rowPDL7.getCell(7).setCellStyle(stylePDL1);
 			rowPDL7.getCell(8).setCellStyle(stylePDL1);

 			Row rowPDL8 = sheetAnalisePDL.createRow((short) 8);
 			rowPDL8.createCell(1).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_analyze_dbl_fphn_header")+":");
 			rowPDL8.createCell(2).setCellValue(fphn_value);
 			rowPDL8.createCell(3).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_analyze_dbl_flw_header"));
 			rowPDL8.createCell(4).setCellValue(flw_value);
 			rowPDL8.createCell(5).setCellValue(categoriaD);
 			rowPDL8.createCell(6)
 			.setCellFormula("IF($E$7>=100,2015,IF($E$7>=90,1860,IF($E$7>=80,1705,IF($E$7>=70,1530,1530))))");
 			rowPDL8.createCell(7)
 			.setCellFormula("IF($E$7>=100,91.5,IF($E$7>=90,84.7,IF($E$7>=80,77.6,IF($E$7>=70,69.6,69.6))))");
 			rowPDL8.createCell(8).setCellFormula("ROUND(+G9/H9,1)");

 			// Estilo
 			rowPDL8.getCell(1).setCellStyle(stylePDL1);
 			rowPDL8.getCell(2).setCellStyle(stylePDL1);
 			rowPDL8.getCell(3).setCellStyle(stylePDL1);
 			rowPDL8.getCell(4).setCellStyle(stylePDL1);
 			rowPDL8.getCell(5).setCellStyle(stylePDL1);
 			rowPDL8.getCell(6).setCellStyle(stylePDL1);
 			rowPDL8.getCell(7).setCellStyle(stylePDL1);
 			rowPDL8.getCell(8).setCellStyle(stylePDL1);

 			Row rowPDL9 = sheetAnalisePDL.createRow((short) 9);
 			rowPDL9.createCell(1).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_analyze_dbl_width_header")+":");
 			rowPDL9.createCell(2).setCellValue(width_value);
 			rowPDL9.createCell(3).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_analyze_dbl_flc_header"));
 			rowPDL9.createCell(4).setCellValue(flc_value);
 			rowPDL9.createCell(5).setCellValue(categoriaE);
 			rowPDL9.createCell(6)
 			.setCellFormula("IF($E$7>=100,2200,IF($E$7>=90,2100,IF($E$7>=80,2000,IF($E$7>=70,1900,1900))))");
 			rowPDL9.createCell(7)
 			.setCellFormula("IF($E$7>=100,88,IF($E$7>=90,80.8,IF($E$7>=80,74.1,IF($E$7>=70,67.9,67.9))))");
 			rowPDL9.createCell(8).setCellFormula("ROUND(+G10/H10,1)");

 			// Estilo
 			rowPDL9.getCell(1).setCellStyle(stylePDL1);
 			rowPDL9.getCell(2).setCellStyle(stylePDL1);
 			rowPDL9.getCell(3).setCellStyle(stylePDL1);
 			rowPDL9.getCell(4).setCellStyle(stylePDL1);
 			rowPDL9.getCell(5).setCellStyle(stylePDL1);
 			rowPDL9.getCell(6).setCellStyle(stylePDL1);
 			rowPDL9.getCell(7).setCellStyle(stylePDL1);
 			rowPDL9.getCell(8).setCellStyle(stylePDL1);

 			Row rowPDL10 = sheetAnalisePDL.createRow((short) 10);
 			rowPDL10.createCell(1).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_analyze_dbl_obst_header"));
 			rowPDL10.createCell(2).setCellValue(obst_value);
 			rowPDL10.createCell(3).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_analyze_dbl_fm_header"));
 			rowPDL10.createCell(4).setCellValue(fm_value);
 			rowPDL10.createCell(5).setCellValue(categoriaF);
 			rowPDL10.createCell(6).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_analyze_dbl_var_header"));
 			rowPDL10.createCell(7).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_analyze_dbl_var_header"));
 			rowPDL10.createCell(8).setCellValue(var_value);

 			// Estilo
 			rowPDL10.getCell(1).setCellStyle(stylePDL1);
 			rowPDL10.getCell(2).setCellStyle(stylePDL1);
 			rowPDL10.getCell(3).setCellStyle(stylePDL1);
 			rowPDL10.getCell(4).setCellStyle(stylePDL1);
 			rowPDL10.getCell(5).setCellStyle(stylePDL1);
 			rowPDL10.getCell(6).setCellStyle(stylePDL1);
 			rowPDL10.getCell(7).setCellStyle(stylePDL1);
 			rowPDL10.getCell(8).setCellStyle(stylePDL1);

 			Row rowPDL11 = sheetAnalisePDL.createRow((short) 11);
 			rowPDL11.createCell(1).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_analyze_dbl_fa_header"));
 			rowPDL11.createCell(2).setCellValue(fa_value);

 			// Estilo
 			rowPDL11.getCell(1).setCellStyle(stylePDL1);
 			rowPDL11.getCell(2).setCellStyle(stylePDL1);

 			Row rowPDL12 = sheetAnalisePDL.createRow((short) 12);
 			rowPDL12.createCell(1).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_analyze_dbl_description1"));
 			rowPDL12.getCell(1).setCellStyle(stylePDLS);

 			Row rowPDL13 = sheetAnalisePDL.createRow((short) 13);
 			rowPDL13.createCell(1).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_analyze_dbl_description2"));
 			rowPDL13.getCell(1).setCellStyle(stylePDLS);

 			Row rowPDL14 = sheetAnalisePDL.createRow((short) 14);
 			rowPDL14.createCell(1).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_analyze_dbl_description3"));
 			rowPDL14.getCell(1).setCellStyle(stylePDLS);

 			Row rowPDL16 = sheetAnalisePDL.createRow((short) 16);
 			rowPDL16.createCell(1).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_low_hour"));
 			rowPDL16.createCell(2).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_low_date"));
 			rowPDL16.createCell(3).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_analyze_dbl_volume_rsd"));
 			rowPDL16.createCell(6).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_analyze_dbl_rate_of")+"\n"+localeSheet.getStringKey("$label_excel_sheet_monthly_flow_flow"));
 			rowPDL16.createCell(7).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_analyze_dbl_vp"));
 			rowPDL16.createCell(8).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_speed")+"\n"+localeSheet.getStringKey("$label_excel_sheet_monthly_flow_raised"));
 			rowPDL16.createCell(9).setCellValue(categoriaD);
 			rowPDL16.createCell(10).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_analyze_dbl_ns_calculated"));
 			rowPDL16.createCell(12).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_analyze_dbl_hour_of_lvl")+": ");
 			rowPDL16.createCell(16).setCellValue(tm.monthComparison(mes)+ " / " + ano);

 			// Estilo
 			rowPDL16.getCell(1).setCellStyle(stylePDL2);
 			rowPDL16.getCell(2).setCellStyle(stylePDL2);
 			rowPDL16.getCell(3).setCellStyle(stylePDL);
 			rowPDL16.getCell(6).setCellStyle(stylePDL);
 			rowPDL16.getCell(7).setCellStyle(stylePDL2);
 			rowPDL16.getCell(8).setCellStyle(stylePDL);
 			rowPDL16.getCell(9).setCellStyle(stylePDL2);
 			rowPDL16.getCell(10).setCellStyle(stylePDL2);
 			rowPDL16.getCell(12).setCellStyle(stylePDL);
 			rowPDL16.getCell(16).setCellStyle(stylePDL);

 			Row rowPDL17 = sheetAnalisePDL.createRow((short) 17);
 			rowPDL17.createCell(3).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_motorcycle"));
 			rowPDL17.createCell(4).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_light"));
 			rowPDL17.createCell(5).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_comm"));
 			rowPDL17.createCell(12).setCellValue(categoriaA);
 			rowPDL17.createCell(13).setCellValue(categoriaB);
 			rowPDL17.createCell(14).setCellValue(categoriaC);
 			rowPDL17.createCell(15).setCellValue(categoriaD);
 			rowPDL17.createCell(16).setCellValue(categoriaE);
 			rowPDL17.createCell(17).setCellValue(categoriaF);

 			// Estilo
 			rowPDL17.getCell(3).setCellStyle(stylePDL);
 			rowPDL17.getCell(4).setCellStyle(stylePDL);
 			rowPDL17.getCell(5).setCellStyle(stylePDL);
 			rowPDL17.getCell(12).setCellStyle(stylePDL);
 			rowPDL17.getCell(13).setCellStyle(stylePDL);
 			rowPDL17.getCell(14).setCellStyle(stylePDL);
 			rowPDL17.getCell(15).setCellStyle(stylePDL);
 			rowPDL17.getCell(16).setCellStyle(stylePDL);
 			rowPDL17.getCell(17).setCellStyle(stylePDL);

 			// Mesclar c�lulas
 			//Cabe�alho
 			sheetAnalisePDL.addMergedRegion(CellRangeAddress.valueOf("B2:J2"));
 			sheetAnalisePDL.addMergedRegion(CellRangeAddress.valueOf("B3:J3"));
 			sheetAnalisePDL.addMergedRegion(CellRangeAddress.valueOf("B13:D13"));
 			sheetAnalisePDL.addMergedRegion(CellRangeAddress.valueOf("B14:D14"));
 			sheetAnalisePDL.addMergedRegion(CellRangeAddress.valueOf("B15:D15"));

 			//Tabela
 			sheetAnalisePDL.addMergedRegion(CellRangeAddress.valueOf("B17:B18"));
 			sheetAnalisePDL.addMergedRegion(CellRangeAddress.valueOf("C17:C18"));	
 			sheetAnalisePDL.addMergedRegion(CellRangeAddress.valueOf("D17:F17"));	
 			sheetAnalisePDL.addMergedRegion(CellRangeAddress.valueOf("G17:G18"));
 			sheetAnalisePDL.addMergedRegion(CellRangeAddress.valueOf("H17:H18"));
 			sheetAnalisePDL.addMergedRegion(CellRangeAddress.valueOf("I17:I18"));
 			sheetAnalisePDL.addMergedRegion(CellRangeAddress.valueOf("J17:J18"));
 			sheetAnalisePDL.addMergedRegion(CellRangeAddress.valueOf("K17:K18"));

 			sheetAnalisePDL.addMergedRegion(CellRangeAddress.valueOf("M17:P17"));
 			sheetAnalisePDL.addMergedRegion(CellRangeAddress.valueOf("Q17:R17"));

 			// largura c�lula
 			sheetAnalisePDL.setColumnWidth(0, 1200);
 			sheetAnalisePDL.setColumnWidth(1, 3800); 			
 			sheetAnalisePDL.setColumnWidth(3, 4300);
 			sheetAnalisePDL.setColumnWidth(4, 4300);
 			sheetAnalisePDL.setColumnWidth(5, 5000);
 			sheetAnalisePDL.setColumnWidth(6, 4300);
 			sheetAnalisePDL.setColumnWidth(7, 3800);
 			sheetAnalisePDL.setColumnWidth(8, 4300);
 			sheetAnalisePDL.setColumnWidth(9, 4300);
 			sheetAnalisePDL.setColumnWidth(10, 3500);
 			sheetAnalisePDL.setColumnWidth(11, 3000);
 			sheetAnalisePDL.setColumnWidth(12, 3000);
 			sheetAnalisePDL.setColumnWidth(13, 3000);
 			sheetAnalisePDL.setColumnWidth(14, 3000);
 			sheetAnalisePDL.setColumnWidth(15, 3000);
 			sheetAnalisePDL.setColumnWidth(16, 3000);
 			
 			sheetAnalisePDL.autoSizeColumn(2);

 			// Atura da C�lula
 			rowPDL4.setHeight((short) 622);

 			// *** Bordas

 			// Primeira tabela
 			propertyPDL.drawBorders(new CellRangeAddress(4, 4, 1, 8), BorderStyle.MEDIUM, BorderExtent.TOP);
 			propertyPDL.drawBorders(new CellRangeAddress(4, 4, 1, 8), BorderStyle.MEDIUM, BorderExtent.BOTTOM);

 			propertyPDL.drawBorders(new CellRangeAddress(4, 10, 4, 4), BorderStyle.MEDIUM, BorderExtent.RIGHT);
 			propertyPDL.drawBorders(new CellRangeAddress(4, 11, 1, 1), BorderStyle.MEDIUM, BorderExtent.LEFT);

 			propertyPDL.drawBorders(new CellRangeAddress(11, 11, 1, 2), BorderStyle.MEDIUM, BorderExtent.BOTTOM);
 			propertyPDL.drawBorders(new CellRangeAddress(11, 11, 2, 2), BorderStyle.MEDIUM, BorderExtent.RIGHT);

 			propertyPDL.drawBorders(new CellRangeAddress(10, 10, 3, 8), BorderStyle.MEDIUM, BorderExtent.BOTTOM);
 			propertyPDL.drawBorders(new CellRangeAddress(4, 10, 8, 8), BorderStyle.MEDIUM, BorderExtent.RIGHT);

 			// *** Bordas Internas - Primeira Tabela

 			// Colunas
 			propertyPDL.drawBorders(new CellRangeAddress(4, 11, 1, 1), BorderStyle.THIN, BorderExtent.RIGHT);
 			propertyPDL.drawBorders(new CellRangeAddress(4, 11, 2, 2), BorderStyle.THIN, BorderExtent.RIGHT);
 			propertyPDL.drawBorders(new CellRangeAddress(4, 10, 3, 3), BorderStyle.THIN, BorderExtent.RIGHT);
 			propertyPDL.drawBorders(new CellRangeAddress(4, 10, 5, 5), BorderStyle.THIN, BorderExtent.RIGHT);
 			propertyPDL.drawBorders(new CellRangeAddress(4, 10, 6, 6), BorderStyle.THIN, BorderExtent.RIGHT);
 			propertyPDL.drawBorders(new CellRangeAddress(4, 10, 7, 7), BorderStyle.THIN, BorderExtent.RIGHT);

 			// Linhas
 			propertyPDL.drawBorders(new CellRangeAddress(5, 5, 1, 8), BorderStyle.THIN, BorderExtent.BOTTOM);
 			propertyPDL.drawBorders(new CellRangeAddress(6, 6, 1, 8), BorderStyle.THIN, BorderExtent.BOTTOM);
 			propertyPDL.drawBorders(new CellRangeAddress(7, 7, 1, 8), BorderStyle.THIN, BorderExtent.BOTTOM);
 			propertyPDL.drawBorders(new CellRangeAddress(8, 8, 1, 8), BorderStyle.THIN, BorderExtent.BOTTOM);
 			propertyPDL.drawBorders(new CellRangeAddress(9, 9, 1, 8), BorderStyle.THIN, BorderExtent.BOTTOM);
 			propertyPDL.drawBorders(new CellRangeAddress(10, 10, 1, 2), BorderStyle.THIN, BorderExtent.BOTTOM);

 			// *** Bordas Internas - END

 			// Bordas Descri��o - Tabela de Dados
 			propertyPDL.drawBorders(new CellRangeAddress(16, 16, 1, 10), BorderStyle.THIN, BorderExtent.TOP);
 			propertyPDL.drawBorders(new CellRangeAddress(17, 17, 1, 10), BorderStyle.THIN, BorderExtent.BOTTOM);

 			propertyPDL.drawBorders(new CellRangeAddress(16, 16, 12, 17), BorderStyle.THIN, BorderExtent.TOP);
 			propertyPDL.drawBorders(new CellRangeAddress(17, 17, 12, 17), BorderStyle.THIN, BorderExtent.BOTTOM);

 			// Bordas Internas - Tabela de Dados

 			// Colunas
 			propertyPDL.drawBorders(new CellRangeAddress(16, 17, 1, 10), BorderStyle.THIN, BorderExtent.ALL);
 			propertyPDL.drawBorders(new CellRangeAddress(16, 17, 12, 17), BorderStyle.THIN, BorderExtent.ALL);

 			// Linhas
 			propertyPDL.drawBorders(new CellRangeAddress(16, 16, 3, 4), BorderStyle.THIN, BorderExtent.BOTTOM);
 			propertyPDL.drawBorders(new CellRangeAddress(16, 16, 12, 17), BorderStyle.THIN, BorderExtent.BOTTOM);

 			int tam2 = ((24 * daysInMonth) + 18);
 			int hour = 0;
 			int day_ = 1;
 			int idx = 4;
 			int row = 19;
 			int num = 14;
 			int t = 19;
 			String data = " ";
 			int rowMax = 0;

 			for (int rowIndex = 18; rowIndex < tam2; rowIndex++) {

 				@SuppressWarnings("unused")
 				Row rowIdx = sheetAnalisePDL.createRow(rowIndex);
 				XSSFRow rowDPL = sheetAnalisePDL.getRow(rowIndex);

 				propertyPDL.drawBorders(new CellRangeAddress(18, rowIndex, 1, 10), BorderStyle.THIN, BorderExtent.ALL);
 				propertyPDL.drawBorders(new CellRangeAddress(18, rowIndex, 12, 17), BorderStyle.THIN, BorderExtent.ALL);

 				if (hour == 24) {
 					hour = 0;
 					day_++;
 					num = 14;
 				}

 				rowDPL = sheetAnalisePDL.getRow(rowIndex);
 				Cell hora = rowDPL.createCell(1);
 				hora.setCellValue(hour);
 				hora.setCellStyle(stylePDL1);

 				// preencher dias
 				if (day_ < 10 && mes < 10)
 					data = "0" + day_ + "/0" + mes + "/" + ano;
 				else if (day_ >= 10 && mes < 10)
 					data = day_ + "/0" + mes + "/" + ano;
 				else
 					data = "" + day_ + "/" + mes + "/" + ano;

 				rowDPL = sheetAnalisePDL.getRow(rowIndex);
 				Cell date = rowDPL.createCell(2);
 				date.setCellValue(data);
 				date.setCellStyle(stylePDL1);

 				rowDPL = sheetAnalisePDL.getRow(rowIndex);
 				Cell motos1 = rowDPL.createCell(3);
 				motos1.setCellFormula(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_sheet_data_chart_flow")+"!D" + idx + "");
 				motos1.setCellStyle(stylePDL1);

 				rowDPL = sheetAnalisePDL.getRow(rowIndex);
 				Cell autos1 = rowDPL.createCell(4);
 				autos1.setCellFormula(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_sheet_data_chart_flow")+"!E" + idx + "");
 				autos1.setCellStyle(stylePDL1);

 				rowDPL = sheetAnalisePDL.getRow(rowIndex);
 				Cell comercial = rowDPL.createCell(5);
 				comercial.setCellFormula(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_sheet_data_chart_flow")+"!F" + idx + "");
 				comercial.setCellStyle(stylePDL1);

 				rowDPL = sheetAnalisePDL.getRow(rowIndex);
 				Cell fluxo1 = rowDPL.createCell(6); 
 				fluxo1.setCellFormula(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_sheet_data_chart_flow")+"!G" + idx + "");
 				fluxo1.setCellStyle(stylePDL);

 				rowDPL = sheetAnalisePDL.getRow(rowIndex); 
 				Cell VP1 = rowDPL.createCell(7);
 				VP1.setCellFormula("G" + row + "/($C$7)");
 				VP1.setCellStyle(stylePDL1);

 				rowDPL = sheetAnalisePDL.getRow(rowIndex);
 				Cell velocidade1 = rowDPL.createCell(8); 
 				velocidade1.setCellFormula("'" + day_ + "'!$I$" + num + "");
 				velocidade1.setCellStyle(stylePDL1);

 				rowDPL = sheetAnalisePDL.getRow(rowIndex);
 				Cell D = rowDPL.createCell(9);
 				D.setCellFormula("ROUND(IF(I" + t + ",H" + t + "/I" + t + ",0), 1)");
 				D.setCellStyle(stylePDL1);
 				t++;

 				rowDPL = sheetAnalisePDL.getRow(rowIndex);
 				Cell nsCalc = rowDPL.createCell(10);
 				nsCalc.setCellFormula("IF(J" + row + "<=$I$6,$F$6,IF(J" + row + "<=$I$7,$F$7,IF(J" + row
 						+ "<=$I$8,$F$8,IF(J" + row + "<=$I$9,$F$9,IF(J" + row + "<=$I$10,$F$10,$F$11)))))");
 				nsCalc.setCellStyle(stylePDL);

 				rowDPL = sheetAnalisePDL.getRow(rowIndex);
 				Cell A = rowDPL.createCell(12);
 				A.setCellFormula("IF($K" + row + "=M$18,1,0)");
 				A.setCellStyle(stylePDL);

 				rowDPL = sheetAnalisePDL.getRow(rowIndex);
 				Cell B = rowDPL.createCell(13);
 				B.setCellFormula("IF($K" + row + "=N$18,1,0)");
 				B.setCellStyle(stylePDL);

 				rowDPL = sheetAnalisePDL.getRow(rowIndex);
 				Cell C = rowDPL.createCell(14);
 				C.setCellFormula("IF($K" + row + "=O$18,1,0)");
 				C.setCellStyle(stylePDL);

 				rowDPL = sheetAnalisePDL.getRow(rowIndex);
 				Cell D_ = rowDPL.createCell(15);
 				D_.setCellFormula("IF($K" + row + "=P$18,1,0)");
 				D_.setCellStyle(stylePDL);

 				rowDPL = sheetAnalisePDL.getRow(rowIndex);
 				Cell E = rowDPL.createCell(16);
 				E.setCellFormula("IF($K" + row + "=Q$18,1,0)");
 				E.setCellStyle(stylePDL);

 				rowDPL = sheetAnalisePDL.getRow(rowIndex);
 				Cell F = rowDPL.createCell(17);
 				F.setCellFormula("IF($K" + row + "=R$18,1,0)");
 				F.setCellStyle(stylePDL);
 				hour++;
 				idx++;
 				row++;
 				num = num + 4;
 				rowMax = rowIndex; // Retornar o indice m�ximo
 			}

 			int max = (rowMax + 1); // Vari�vel para criar linha ap�s gerar todos os indices
 			int dsc = (rowMax + 3); // Vari�vel para criar linha abaixo da �ltima criada

 			Row rowPDLNew1 = sheetAnalisePDL.createRow((short) max);
 			rowPDLNew1.createCell(5).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_max_flow")+": ");
 			rowPDLNew1.createCell(6).setCellFormula("MAX(G19:G" + (rowMax + 1) + ")");
 			rowPDLNew1.createCell(7).setCellFormula("MAX(H19:H" + (rowMax + 1) + ")");
 			rowPDLNew1.createCell(8).setCellFormula("MAX(I19:I" + (rowMax + 1) + ")");

 			// Estilo
 			rowPDLNew1.getCell(5).setCellStyle(stylePDL);
 			rowPDLNew1.getCell(6).setCellStyle(stylePDL1);
 			rowPDLNew1.getCell(7).setCellStyle(stylePDL1);
 			rowPDLNew1.getCell(8).setCellStyle(stylePDL1);

 			Row rowPDLNew2 = sheetAnalisePDL.createRow((short) dsc);
 			rowPDLNew2.createCell(12).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_hour_per_service_level_flow"));
 			rowPDLNew2.createCell(16).setCellValue(tm.monthComparison(mes)+ " / " + ano);
 			rowPDLNew2.createCell(18).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_low_total"));
 			rowPDLNew2.createCell(19).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_observation"));

 			// Estilo
 			rowPDLNew2.getCell(12).setCellStyle(stylePDL);
 			rowPDLNew2.getCell(16).setCellStyle(stylePDL);
 			rowPDLNew2.getCell(18).setCellStyle(stylePDL2);
 			rowPDLNew2.getCell(19).setCellStyle(stylePDL2);

 			// Mesclar c�lulas
 			sheetAnalisePDL.addMergedRegion(CellRangeAddress.valueOf("M" + (dsc + 1) + ":P" + (dsc + 1) + ""));
 			sheetAnalisePDL.addMergedRegion(CellRangeAddress.valueOf("Q" + (dsc + 1) + ":R" + (dsc + 1) + ""));
 			sheetAnalisePDL.addMergedRegion(CellRangeAddress.valueOf("S" + (dsc + 1) + ":S" + (dsc + 2) + ""));
 			sheetAnalisePDL.addMergedRegion(CellRangeAddress.valueOf("T" + (dsc + 1) + ":V" + (dsc + 2) + ""));
 			sheetAnalisePDL.addMergedRegion(CellRangeAddress.valueOf("T" + (dsc + 3) + ":V" + (dsc + 3) + ""));

 			Row rowPDLNew3 = sheetAnalisePDL.createRow((short) (dsc + 1));
 			rowPDLNew3.createCell(12).setCellValue(categoriaA);
 			rowPDLNew3.createCell(13).setCellValue(categoriaB);
 			rowPDLNew3.createCell(14).setCellValue(categoriaC);
 			rowPDLNew3.createCell(15).setCellValue(categoriaD);
 			rowPDLNew3.createCell(16).setCellValue(categoriaE);
 			rowPDLNew3.createCell(17).setCellValue(categoriaF);

 			// Estilo
 			rowPDLNew3.getCell(12).setCellStyle(stylePDL);
 			rowPDLNew3.getCell(13).setCellStyle(stylePDL);
 			rowPDLNew3.getCell(14).setCellStyle(stylePDL);
 			rowPDLNew3.getCell(15).setCellStyle(stylePDL);
 			rowPDLNew3.getCell(16).setCellStyle(stylePDL);
 			rowPDLNew3.getCell(17).setCellStyle(stylePDL);

 			Row rowPDLNew4 = sheetAnalisePDL.createRow((short) (dsc + 2));
 			rowPDLNew4.createCell(12).setCellFormula("SUM(M19:M" + (rowMax + 1) + ")");
 			rowPDLNew4.createCell(13).setCellFormula("SUM(N19:N" + (rowMax + 1) + ")");
 			rowPDLNew4.createCell(14).setCellFormula("SUM(O19:O" + (rowMax + 1) + ")");
 			rowPDLNew4.createCell(15).setCellFormula("SUM(P19:P" + (rowMax + 1) + ")");
 			rowPDLNew4.createCell(16).setCellFormula("SUM(Q19:Q" + (rowMax + 1) + ")");
 			rowPDLNew4.createCell(17).setCellFormula("SUM(R19:R" + (rowMax + 1) + ")");
 			rowPDLNew4.createCell(18).setCellFormula("SUM(M" + (dsc + 3) + ":R" + (dsc + 3) + ")");
 			rowPDLNew4.createCell(19).setCellValue("");

 			// Estilo
 			rowPDLNew4.getCell(12).setCellStyle(stylePDL);
 			rowPDLNew4.getCell(13).setCellStyle(stylePDL);
 			rowPDLNew4.getCell(14).setCellStyle(stylePDL);
 			rowPDLNew4.getCell(15).setCellStyle(stylePDL);
 			rowPDLNew4.getCell(16).setCellStyle(stylePDL);
 			rowPDLNew4.getCell(17).setCellStyle(stylePDL);
 			rowPDLNew4.getCell(18).setCellStyle(stylePDL);
 			rowPDLNew4.getCell(19).setCellStyle(stylePDL1);

 			// Linhas
 			propertyPDL.drawBorders(new CellRangeAddress(max, max, 5, 8), BorderStyle.THIN, BorderExtent.ALL);
 			propertyPDL.drawBorders(new CellRangeAddress(dsc, (dsc + 2), 12, 21), BorderStyle.THIN, BorderExtent.ALL);

 			propertyPDL.applyBorders(sheetAnalisePDL);
 			// ------ An�lise Pista_Dupla_Sentido 1 - END ------//

 			// ------ An�lise Pista_Dupla_Sentido 2 - BEGIN ------//

 			// Header Text Title
 			CellStyle stylePDO = workbook.createCellStyle();
 			Font fontPDO = workbook.createFont();
 			fontPDO.setBold(true);
 			fontPDO.setFontName(HSSFFont.FONT_ARIAL);
 			fontPDO.setFontHeightInPoints((short) 10);
 			stylePDO.setFont(fontPDO);
 			stylePDO.setWrapText(true);
 			stylePDO.setAlignment(HorizontalAlignment.CENTER);
 			stylePDO.setFillBackgroundColor(IndexedColors.WHITE.getIndex());

 			// Header Text Title - MIDDLE
 			CellStyle stylePDO2 = workbook.createCellStyle();
 			stylePDO2.setFont(fontPDO);
 			stylePDO2.setAlignment(HorizontalAlignment.CENTER);
 			stylePDO2.setVerticalAlignment(VerticalAlignment.CENTER);
 			stylePDO2.setFillBackgroundColor(IndexedColors.WHITE.getIndex());

 			// Header Text Title - SHORT
 			CellStyle stylePDOS = workbook.createCellStyle();
 			Font fontPDOS = workbook.createFont();
 			fontPDOS.setFontHeightInPoints((short) 8);
 			stylePDOS.setFont(fontPDOS);
 			stylePDOS.setAlignment(HorizontalAlignment.LEFT);
 			stylePDOS.setFillBackgroundColor(IndexedColors.WHITE.getIndex());

 			// Header Text Description
 			CellStyle stylePDO1 = workbook.createCellStyle();
 			Font fontPDO1 = workbook.createFont();
 			fontPDO1.setFontName(HSSFFont.FONT_ARIAL);
 			fontPDO1.setFontHeightInPoints((short) 10);
 			stylePDO1.setFont(fontPDO1);
 			stylePDO1.setAlignment(HorizontalAlignment.CENTER);
 			stylePDO1.setFillBackgroundColor(IndexedColors.WHITE.getIndex());

 			// Header Text Title - MIDDLE
 			CellStyle stylePDO3 = workbook.createCellStyle();
 			stylePDO3.setFont(fontPDO1);
 			stylePDO3.setAlignment(HorizontalAlignment.CENTER);
 			stylePDO3.setVerticalAlignment(VerticalAlignment.CENTER);
 			stylePDO3.setFillBackgroundColor(IndexedColors.WHITE.getIndex());

 			Row rowPDO1 = sheetAnalisePDO.createRow((short) 1);
 			rowPDO1.createCell(1).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_company")+": "+RoadConcessionaire.roadConcessionaire+" - "+ano);
 			rowPDO1.getCell(1).setCellStyle(stylePDO);

 			Row rowPDO2 = sheetAnalisePDO.createRow((short) 2);
 			rowPDO2.createCell(1).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_multitrack_track_segment_header"));
 			rowPDO2.getCell(1).setCellStyle(stylePDO);

 			Row rowPDO4 = sheetAnalisePDO.createRow((short) 4);
 			rowPDO4.createCell(1).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_road_header"));
 			rowPDO4.createCell(2).setCellValue(sat.getEstrada() + " " + direction2);
 			rowPDO4.createCell(3).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_ground_header"));
 			rowPDO4.createCell(4).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_ground"));
 			rowPDO4.createCell(5).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_service_level_header"));
 			rowPDO4.createCell(6).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_service_level"));
 			rowPDO4.createCell(7).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_service_level_bw"));
 			rowPDO4.createCell(8).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_service_level_max"));

 			// Estilo
 			rowPDO4.getCell(1).setCellStyle(stylePDO3);
 			rowPDO4.getCell(2).setCellStyle(stylePDO3);
 			rowPDO4.getCell(3).setCellStyle(stylePDO3);
 			rowPDO4.getCell(4).setCellStyle(stylePDO3);
 			rowPDO4.getCell(5).setCellStyle(stylePDO3);
 			rowPDO4.getCell(6).setCellStyle(stylePDO3);
 			rowPDO4.getCell(7).setCellStyle(stylePDO3);
 			rowPDO4.getCell(8).setCellStyle(stylePDO3);

 			Row rowPDO5 = sheetAnalisePDO.createRow((short) 5);
 			rowPDO5.createCell(1).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_strech_header")+": ");
 			rowPDO5.createCell(2).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_th1_road_header") + sat.getEstrada());
 			rowPDO5.createCell(3).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_et_road_header"));
 			rowPDO5.createCell(4).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_analyze_dbl_number"));
 			rowPDO5.createCell(5).setCellValue(categoriaA);
 			rowPDO5.createCell(6)
 			.setCellFormula("IF($E$7>=100,700,IF($E$7>=90,630,IF($E$7>=80,560,IF($E$7>=70,490,490))))");
 			rowPDO5.createCell(7)
 			.setCellFormula("IF($E$7>=100,100,IF($E$7>=90,90,IF($E$7>=80,80,IF($E$7>=70,70,70))))");
 			rowPDO5.createCell(8).setCellFormula("ROUND(+G6/H6, 1)");

 			// Estilo
 			rowPDO5.getCell(1).setCellStyle(stylePDO1);
 			rowPDO5.getCell(2).setCellStyle(stylePDO1);
 			rowPDO5.getCell(3).setCellStyle(stylePDO1);
 			rowPDO5.getCell(4).setCellStyle(stylePDO1);
 			rowPDO5.getCell(5).setCellStyle(stylePDO1);
 			rowPDO5.getCell(6).setCellStyle(stylePDO1);
 			rowPDO5.getCell(7).setCellStyle(stylePDO1);
 			rowPDO5.getCell(8).setCellStyle(stylePDO1);

 			Row rowPDO6 = sheetAnalisePDO.createRow((short) 6);
 			rowPDO6.createCell(1).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_analyze_dbl_lanes_header")+":");
 			rowPDO6.createCell(2).setCellValue(sat.getNumFaixas());
 			rowPDO6.createCell(3).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_analyze_dbl_ffs_header"));
 			rowPDO6.createCell(4).setCellFormula("E8-E9-E10-E11-C12");
 			rowPDO6.createCell(5).setCellValue(categoriaB);
 			rowPDO6.createCell(6)
 			.setCellFormula("IF($E$7>=100,1100,IF($E$7>=90,990,IF($E$7>=80,880,IF($E$7>=70,770,770))))");
 			rowPDO6.createCell(7)
 			.setCellFormula("IF($E$7>=100,100,IF($E$7>=90,90,IF($E$7>=80,80,IF($E$7>=70,70,70))))");
 			rowPDO6.createCell(8).setCellFormula("ROUND(+G7/H7,1)");

 			// Estilo
 			rowPDO6.getCell(1).setCellStyle(stylePDO1);
 			rowPDO6.getCell(2).setCellStyle(stylePDO1);
 			rowPDO6.getCell(3).setCellStyle(stylePDO1);
 			rowPDO6.getCell(4).setCellStyle(stylePDO1);
 			rowPDO6.getCell(5).setCellStyle(stylePDO1);
 			rowPDO6.getCell(6).setCellStyle(stylePDO1);
 			rowPDO6.getCell(7).setCellStyle(stylePDO1);
 			rowPDO6.getCell(8).setCellStyle(stylePDO1);

 			Row rowPDO7 = sheetAnalisePDO.createRow((short) 7);
 			rowPDO7.createCell(1).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_analyze_dbl_fph_header")+":");
 			rowPDO7.createCell(2).setCellValue(fph_value);
 			rowPDO7.createCell(3).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_analyze_dbl_ffsi_header"));
 			rowPDO7.createCell(4).setCellValue(fssi_value);
 			rowPDO7.createCell(5).setCellValue(categoriaC);
 			rowPDO7.createCell(6)
 			.setCellFormula("IF($E$7>=100,1575,IF($E$7>=90,1435,IF($E$7>=80,1280,IF($E$7>=70,1120,1120))))");
 			rowPDO7.createCell(7)
 			.setCellFormula("IF($E$7>=100,98.4,IF($E$7>=90,89.8,IF($E$7>=80,80,IF($E$7>=70,70,70))))");
 			rowPDO7.createCell(8).setCellFormula("ROUND(+G8/H8,1)");

 			// Estilo
 			rowPDO7.getCell(1).setCellStyle(stylePDO1);
 			rowPDO7.getCell(2).setCellStyle(stylePDO1);
 			rowPDO7.getCell(3).setCellStyle(stylePDO1);
 			rowPDO7.getCell(4).setCellStyle(stylePDO1);
 			rowPDO7.getCell(5).setCellStyle(stylePDO1);
 			rowPDO7.getCell(6).setCellStyle(stylePDO1);
 			rowPDO7.getCell(7).setCellStyle(stylePDO1);
 			rowPDO7.getCell(8).setCellStyle(stylePDO1);

 			Row rowPDO8 = sheetAnalisePDO.createRow((short) 8);
 			rowPDO8.createCell(1).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_analyze_dbl_fphn_header")+":");
 			rowPDO8.createCell(2).setCellValue(fphn_value);
 			rowPDO8.createCell(3).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_analyze_dbl_flw_header"));
 			rowPDO8.createCell(4).setCellValue(flw_value);
 			rowPDO8.createCell(5).setCellValue(categoriaD);
 			rowPDO8.createCell(6)
 			.setCellFormula("IF($E$7>=100,2015,IF($E$7>=90,1860,IF($E$7>=80,1705,IF($E$7>=70,1530,1530))))");
 			rowPDO8.createCell(7)
 			.setCellFormula("IF($E$7>=100,91.5,IF($E$7>=90,84.7,IF($E$7>=80,77.6,IF($E$7>=70,69.6,69.6))))");
 			rowPDO8.createCell(8).setCellFormula("ROUND(+G9/H9,1)");

 			// Estilo
 			rowPDO8.getCell(1).setCellStyle(stylePDO1);
 			rowPDO8.getCell(2).setCellStyle(stylePDO1);
 			rowPDO8.getCell(3).setCellStyle(stylePDO1);
 			rowPDO8.getCell(4).setCellStyle(stylePDO1);
 			rowPDO8.getCell(5).setCellStyle(stylePDO1);
 			rowPDO8.getCell(6).setCellStyle(stylePDO1);
 			rowPDO8.getCell(7).setCellStyle(stylePDO1);
 			rowPDO8.getCell(8).setCellStyle(stylePDO1);

 			Row rowPDO9 = sheetAnalisePDO.createRow((short) 9);
 			rowPDO9.createCell(1).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_analyze_dbl_width_header")+":");
 			rowPDO9.createCell(2).setCellValue(width_value);
 			rowPDO9.createCell(3).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_analyze_dbl_flc_header"));
 			rowPDO9.createCell(4).setCellValue(flc_value);
 			rowPDO9.createCell(5).setCellValue(categoriaE);
 			rowPDO9.createCell(6)
 			.setCellFormula("IF($E$7>=100,2200,IF($E$7>=90,2100,IF($E$7>=80,2000,IF($E$7>=70,1900,1900))))");
 			rowPDO9.createCell(7)
 			.setCellFormula("IF($E$7>=100,88,IF($E$7>=90,80.8,IF($E$7>=80,74.1,IF($E$7>=70,67.9,67.9))))");
 			rowPDO9.createCell(8).setCellFormula("ROUND(+G10/H10,1)");

 			// Estilo
 			rowPDO9.getCell(1).setCellStyle(stylePDO1);
 			rowPDO9.getCell(2).setCellStyle(stylePDO1);
 			rowPDO9.getCell(3).setCellStyle(stylePDO1);
 			rowPDO9.getCell(4).setCellStyle(stylePDO1);
 			rowPDO9.getCell(5).setCellStyle(stylePDO1);
 			rowPDO9.getCell(6).setCellStyle(stylePDO1);
 			rowPDO9.getCell(7).setCellStyle(stylePDO1);
 			rowPDO9.getCell(8).setCellStyle(stylePDO1);

 			Row rowPDO10 = sheetAnalisePDO.createRow((short) 10);
 			rowPDO10.createCell(1).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_analyze_dbl_obst_header"));
 			rowPDO10.createCell(2).setCellValue(obst_value);
 			rowPDO10.createCell(3).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_analyze_dbl_fm_header"));
 			rowPDO10.createCell(4).setCellValue(fm_value);
 			rowPDO10.createCell(5).setCellValue(categoriaF);
 			rowPDO10.createCell(6).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_analyze_dbl_var_header"));
 			rowPDO10.createCell(7).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_analyze_dbl_var_header"));
 			rowPDO10.createCell(8).setCellValue(var_value);

 			// Estilo
 			rowPDO10.getCell(1).setCellStyle(stylePDO1);
 			rowPDO10.getCell(2).setCellStyle(stylePDO1);
 			rowPDO10.getCell(3).setCellStyle(stylePDO1);
 			rowPDO10.getCell(4).setCellStyle(stylePDO1);
 			rowPDO10.getCell(5).setCellStyle(stylePDO1);
 			rowPDO10.getCell(6).setCellStyle(stylePDO1);
 			rowPDO10.getCell(7).setCellStyle(stylePDO1);
 			rowPDO10.getCell(8).setCellStyle(stylePDO1);

 			Row rowPDO11 = sheetAnalisePDO.createRow((short) 11);
 			rowPDO11.createCell(1).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_analyze_dbl_fa_header"));
 			rowPDO11.createCell(2).setCellValue(fa_value);

 			// Estilo
 			rowPDO11.getCell(1).setCellStyle(stylePDO1);
 			rowPDO11.getCell(2).setCellStyle(stylePDO1);

 			Row rowPDO12 = sheetAnalisePDO.createRow((short) 12);
 			rowPDO12.createCell(1).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_analyze_dbl_description1"));
 			rowPDO12.getCell(1).setCellStyle(stylePDOS);

 			Row rowPDO13 = sheetAnalisePDO.createRow((short) 13);
 			rowPDO13.createCell(1).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_analyze_dbl_description2"));
 			rowPDO13.getCell(1).setCellStyle(stylePDOS);

 			Row rowPDO14 = sheetAnalisePDO.createRow((short) 14);
 			rowPDO14.createCell(1).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_analyze_dbl_description3"));
 			rowPDO14.getCell(1).setCellStyle(stylePDOS);

 			Row rowPDO16 = sheetAnalisePDO.createRow((short) 16);
 			rowPDO16.createCell(1).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_low_hour"));
 			rowPDO16.createCell(2).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_low_date"));
 			rowPDO16.createCell(3).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_analyze_dbl_volume_rsd"));
 			rowPDO16.createCell(6).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_analyze_dbl_rate_of")+"\n"+localeSheet.getStringKey("$label_excel_sheet_monthly_flow_flow"));
 			rowPDO16.createCell(7).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_analyze_dbl_vp"));
 			rowPDO16.createCell(8).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_speed")+"\n"+localeSheet.getStringKey("$label_excel_sheet_monthly_flow_raised"));
 			rowPDO16.createCell(9).setCellValue(categoriaD);
 			rowPDO16.createCell(10).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_analyze_dbl_ns_calculated"));
 			rowPDO16.createCell(12).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_analyze_dbl_hour_of_lvl")+": ");
 			rowPDO16.createCell(16).setCellValue(tm.monthComparison(mes) + " / " + ano);				

 			// Estilo
 			rowPDO16.getCell(1).setCellStyle(stylePDO2);
 			rowPDO16.getCell(2).setCellStyle(stylePDO2);
 			rowPDO16.getCell(3).setCellStyle(stylePDO);
 			rowPDO16.getCell(6).setCellStyle(stylePDO);
 			rowPDO16.getCell(7).setCellStyle(stylePDO2);
 			rowPDO16.getCell(8).setCellStyle(stylePDO);
 			rowPDO16.getCell(9).setCellStyle(stylePDO2);
 			rowPDO16.getCell(10).setCellStyle(stylePDO2);
 			rowPDO16.getCell(12).setCellStyle(stylePDO);
 			rowPDO16.getCell(16).setCellStyle(stylePDO);

 			Row rowPDO17 = sheetAnalisePDO.createRow((short) 17);
 			rowPDO17.createCell(3).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_motorcycle"));
 			rowPDO17.createCell(4).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_light"));
 			rowPDO17.createCell(5).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_comm"));
 			rowPDO17.createCell(12).setCellValue(categoriaA);
 			rowPDO17.createCell(13).setCellValue(categoriaB);
 			rowPDO17.createCell(14).setCellValue(categoriaC);
 			rowPDO17.createCell(15).setCellValue(categoriaD);
 			rowPDO17.createCell(16).setCellValue(categoriaE);
 			rowPDO17.createCell(17).setCellValue(categoriaD);

 			// Estilo
 			rowPDO17.getCell(3).setCellStyle(stylePDO);
 			rowPDO17.getCell(4).setCellStyle(stylePDO);
 			rowPDO17.getCell(5).setCellStyle(stylePDO);
 			rowPDO17.getCell(12).setCellStyle(stylePDO);
 			rowPDO17.getCell(13).setCellStyle(stylePDO);
 			rowPDO17.getCell(14).setCellStyle(stylePDO);
 			rowPDO17.getCell(15).setCellStyle(stylePDO);
 			rowPDO17.getCell(16).setCellStyle(stylePDO);
 			rowPDO17.getCell(17).setCellStyle(stylePDO);

 			// Mesclar c�lulas
 			sheetAnalisePDO.addMergedRegion(CellRangeAddress.valueOf("B2:J2"));
 			sheetAnalisePDO.addMergedRegion(CellRangeAddress.valueOf("B3:J3"));
 			sheetAnalisePDO.addMergedRegion(CellRangeAddress.valueOf("B13:D13"));					
 			sheetAnalisePDO.addMergedRegion(CellRangeAddress.valueOf("B14:D14"));
 			sheetAnalisePDO.addMergedRegion(CellRangeAddress.valueOf("B15:D15"));

 			//Tabela
 			sheetAnalisePDO.addMergedRegion(CellRangeAddress.valueOf("B17:B18"));
 			sheetAnalisePDO.addMergedRegion(CellRangeAddress.valueOf("C17:C18"));
 			sheetAnalisePDO.addMergedRegion(CellRangeAddress.valueOf("D17:F17"));
 			sheetAnalisePDO.addMergedRegion(CellRangeAddress.valueOf("G17:G18"));
 			sheetAnalisePDO.addMergedRegion(CellRangeAddress.valueOf("H17:H18"));
 			sheetAnalisePDO.addMergedRegion(CellRangeAddress.valueOf("I17:I18"));
 			sheetAnalisePDO.addMergedRegion(CellRangeAddress.valueOf("J17:J18"));
 			sheetAnalisePDO.addMergedRegion(CellRangeAddress.valueOf("K17:K18"));

 			//Tabela 2
 			sheetAnalisePDO.addMergedRegion(CellRangeAddress.valueOf("M17:P17"));
 			sheetAnalisePDO.addMergedRegion(CellRangeAddress.valueOf("Q17:R17"));

 			// largura c�lula
 			sheetAnalisePDO.setColumnWidth(0, 1200);
 			sheetAnalisePDO.setColumnWidth(1, 3800); 		
 			sheetAnalisePDO.setColumnWidth(3, 4300);
 			sheetAnalisePDO.setColumnWidth(4, 4300);
 			sheetAnalisePDO.setColumnWidth(5, 5000);
 			sheetAnalisePDO.setColumnWidth(6, 4300);
 			sheetAnalisePDO.setColumnWidth(7, 3800);
 			sheetAnalisePDO.setColumnWidth(8, 4300);
 			sheetAnalisePDO.setColumnWidth(9, 4300);
 			sheetAnalisePDO.setColumnWidth(10, 3500);
 			sheetAnalisePDO.setColumnWidth(11, 3000);
 			sheetAnalisePDO.setColumnWidth(12, 3000);
 			sheetAnalisePDO.setColumnWidth(13, 3000);
 			sheetAnalisePDO.setColumnWidth(14, 3000);
 			sheetAnalisePDO.setColumnWidth(15, 3000);
 			sheetAnalisePDO.setColumnWidth(16, 3000);
 			
 			sheetAnalisePDO.autoSizeColumn(2);

 			// Atura da C�lula
 			rowPDO4.setHeight((short) 622);

 			// *** Bordas

 			// Primeira tabela
 			propertyPDO.drawBorders(new CellRangeAddress(4, 4, 1, 8), BorderStyle.MEDIUM, BorderExtent.TOP);
 			propertyPDO.drawBorders(new CellRangeAddress(4, 4, 1, 8), BorderStyle.MEDIUM, BorderExtent.BOTTOM);
 			propertyPDO.drawBorders(new CellRangeAddress(4, 10, 4, 4), BorderStyle.MEDIUM, BorderExtent.RIGHT);
 			propertyPDO.drawBorders(new CellRangeAddress(4, 11, 1, 1), BorderStyle.MEDIUM, BorderExtent.LEFT);
 			propertyPDO.drawBorders(new CellRangeAddress(11, 11, 1, 2), BorderStyle.MEDIUM, BorderExtent.BOTTOM);
 			propertyPDO.drawBorders(new CellRangeAddress(11, 11, 2, 2), BorderStyle.MEDIUM, BorderExtent.RIGHT);
 			propertyPDO.drawBorders(new CellRangeAddress(10, 10, 3, 8), BorderStyle.MEDIUM, BorderExtent.BOTTOM);
 			propertyPDO.drawBorders(new CellRangeAddress(4, 10, 8, 8), BorderStyle.MEDIUM, BorderExtent.RIGHT);

 			// *** Bordas Internas - Primeira Tabela

 			// Colunas
 			propertyPDO.drawBorders(new CellRangeAddress(4, 11, 1, 1), BorderStyle.THIN, BorderExtent.RIGHT);
 			propertyPDO.drawBorders(new CellRangeAddress(4, 10, 2, 2), BorderStyle.THIN, BorderExtent.RIGHT);
 			propertyPDO.drawBorders(new CellRangeAddress(4, 10, 3, 3), BorderStyle.THIN, BorderExtent.RIGHT);
 			propertyPDO.drawBorders(new CellRangeAddress(4, 10, 5, 5), BorderStyle.THIN, BorderExtent.RIGHT);
 			propertyPDO.drawBorders(new CellRangeAddress(4, 10, 6, 6), BorderStyle.THIN, BorderExtent.RIGHT);
 			propertyPDO.drawBorders(new CellRangeAddress(4, 10, 7, 7), BorderStyle.THIN, BorderExtent.RIGHT);

 			// Linhas
 			propertyPDO.drawBorders(new CellRangeAddress(5, 5, 1, 8), BorderStyle.THIN, BorderExtent.BOTTOM);
 			propertyPDO.drawBorders(new CellRangeAddress(6, 6, 1, 8), BorderStyle.THIN, BorderExtent.BOTTOM);
 			propertyPDO.drawBorders(new CellRangeAddress(7, 7, 1, 8), BorderStyle.THIN, BorderExtent.BOTTOM);
 			propertyPDO.drawBorders(new CellRangeAddress(8, 8, 1, 8), BorderStyle.THIN, BorderExtent.BOTTOM);
 			propertyPDO.drawBorders(new CellRangeAddress(9, 9, 1, 8), BorderStyle.THIN, BorderExtent.BOTTOM);
 			propertyPDO.drawBorders(new CellRangeAddress(10, 10, 1, 2), BorderStyle.THIN, BorderExtent.BOTTOM);

 			// *** Bordas Internas - END

 			// Bordas Descri��o - Tabela de Dados
 			propertyPDO.drawBorders(new CellRangeAddress(16, 16, 1, 10), BorderStyle.THIN, BorderExtent.TOP);
 			propertyPDO.drawBorders(new CellRangeAddress(17, 17, 1, 10), BorderStyle.THIN, BorderExtent.BOTTOM);

 			propertyPDO.drawBorders(new CellRangeAddress(16, 16, 12, 17), BorderStyle.THIN, BorderExtent.TOP);
 			propertyPDO.drawBorders(new CellRangeAddress(17, 17, 12, 17), BorderStyle.THIN, BorderExtent.BOTTOM);

 			// Bordas Internas - Tabela de Dados

 			// Colunas
 			propertyPDO.drawBorders(new CellRangeAddress(16, 17, 1, 10), BorderStyle.THIN, BorderExtent.ALL);
 			propertyPDO.drawBorders(new CellRangeAddress(16, 17, 12, 17), BorderStyle.THIN, BorderExtent.ALL);

 			// Linhas
 			propertyPDO.drawBorders(new CellRangeAddress(16, 16, 3, 4), BorderStyle.THIN, BorderExtent.BOTTOM);
 			propertyPDO.drawBorders(new CellRangeAddress(16, 16, 12, 17), BorderStyle.THIN, BorderExtent.BOTTOM);

 			int tam3 = ((24 * daysInMonth) + 18);
 			int hour1 = 0;
 			int dayx_ = 1;
 			int idxx = 4;
 			int rowx = 19;
 			int numx = 14;
 			int tx = 19;
 			String datax = " ";
 			int rowMaxi = 0;

 			for (int rowIndex = 18; rowIndex < tam3; rowIndex++) {

 				@SuppressWarnings("unused")
 				Row rowId1x = sheetAnalisePDO.createRow(rowIndex);
 				XSSFRow rowDPO = sheetAnalisePDO.getRow(rowIndex);

 				propertyPDO.drawBorders(new CellRangeAddress(18, rowIndex, 1, 10), BorderStyle.THIN, BorderExtent.ALL);
 				propertyPDO.drawBorders(new CellRangeAddress(18, rowIndex, 12, 17), BorderStyle.THIN, BorderExtent.ALL);

 				if (hour1 == 24) {
 					hour1 = 0;
 					dayx_++;
 					numx = 14;
 				}

 				rowDPO = sheetAnalisePDO.getRow(rowIndex);
 				Cell hora = rowDPO.createCell(1);
 				hora.setCellValue(hour1);
 				hora.setCellStyle(stylePDO1);

 				// preencher dias
 				if (dayx_ < 10 && mes < 10)
 					datax = "0" + dayx_ + "/0" + mes + "/" + ano;
 				else if (dayx_ >= 10 && mes < 10)
 					datax = dayx_ + "/0" + mes + "/" + ano;
 				else
 					datax = "" + dayx_ + "/" + mes + "/" + ano;

 				rowDPO = sheetAnalisePDO.getRow(rowIndex);
 				Cell date = rowDPO.createCell(2);
 				date.setCellValue(datax);
 				date.setCellStyle(stylePDO1);  

 				rowDPO = sheetAnalisePDO.getRow(rowIndex);
 				Cell motos2 = rowDPO.createCell(3);
 				motos2.setCellFormula(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_sheet_data_chart_flow")+"!I" + idxx + "");
 				motos2.setCellStyle(stylePDO1);

 				rowDPO = sheetAnalisePDO.getRow(rowIndex); 
 				Cell autos2 = rowDPO.createCell(4);
 				autos2.setCellFormula(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_sheet_data_chart_flow")+"!J" + idxx + "");
 				autos2.setCellStyle(stylePDO1);

 				rowDPO = sheetAnalisePDO.getRow(rowIndex);
 				Cell comercial2 = rowDPO.createCell(5); 
 				comercial2.setCellFormula(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_sheet_data_chart_flow")+"!K" + idxx + "");
 				comercial2.setCellStyle(stylePDO1);

 				rowDPO = sheetAnalisePDO.getRow(rowIndex);
 				Cell fluxo2 = rowDPO.createCell(6);
 				fluxo2.setCellFormula(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_sheet_data_chart_flow")+"!L" + idxx + "");
 				fluxo2.setCellStyle(stylePDO);

 				rowDPO = sheetAnalisePDO.getRow(rowIndex); 
 				Cell VP2 = rowDPO.createCell(7);
 				VP2.setCellFormula("G" + rowx + "/($C$7)");

 				rowDPO = sheetAnalisePDO.getRow(rowIndex);
 				Cell velocidade2 = rowDPO.createCell(8);
 				velocidade2.setCellFormula("'" + dayx_ + "'!$I$" + numx + "");
 				velocidade2.setCellStyle(stylePDO1); 

 				rowDPO = sheetAnalisePDO.getRow(rowIndex);
 				Cell D = rowDPO.createCell(9);
 				D.setCellFormula("ROUND(IF(I" + tx + ",H" + tx + "/I" + tx + ",0), 1)");
 				D.setCellStyle(stylePDO1);
 				tx++;

 				rowDPO = sheetAnalisePDO.getRow(rowIndex);
 				Cell nsCalc2 = rowDPO.createCell(10); 
 				nsCalc2.setCellFormula("IF(J" + rowx + "<=$I$6,$F$6,IF(J" + rowx + "<=$I$7,$F$7,IF(J" + rowx
 						+ "<=$I$8,$F$8,IF(J" + rowx + "<=$I$9,$F$9,IF(J" + rowx + "<=$I$10,$F$10,$F$11)))))");
 				nsCalc2.setCellStyle(stylePDO);

 				rowDPO = sheetAnalisePDO.getRow(rowIndex);
 				Cell A = rowDPO.createCell(12);
 				A.setCellFormula("IF($K" + rowx + "=M$18,1,0)");
 				A.setCellStyle(stylePDO);

 				rowDPO = sheetAnalisePDO.getRow(rowIndex);
 				Cell B = rowDPO.createCell(13);
 				B.setCellFormula("IF($K" + rowx + "=N$18,1,0)");
 				B.setCellStyle(stylePDO);

 				rowDPO = sheetAnalisePDO.getRow(rowIndex);
 				Cell C = rowDPO.createCell(14);
 				C.setCellFormula("IF($K" + rowx + "=O$18,1,0)");
 				C.setCellStyle(stylePDO);

 				rowDPO = sheetAnalisePDO.getRow(rowIndex);
 				Cell D_ = rowDPO.createCell(15);
 				D_.setCellFormula("IF($K" + rowx + "=P$18,1,0)");
 				D_.setCellStyle(stylePDO);

 				rowDPO = sheetAnalisePDO.getRow(rowIndex);
 				Cell E = rowDPO.createCell(16);
 				E.setCellFormula("IF($K" + rowx + "=Q$18,1,0)");
 				E.setCellStyle(stylePDO);

 				rowDPO = sheetAnalisePDO.getRow(rowIndex);
 				Cell F = rowDPO.createCell(17);
 				F.setCellFormula("IF($K" + rowx + "=R$18,1,0)");
 				F.setCellStyle(stylePDO);
 				hour1++;
 				idxx++;
 				rowx++;
 				numx = numx + 4;

 				rowMaxi = rowIndex; // Retornar o indice m�ximo
 			}

 			int maxi = (rowMaxi + 1); // Vari�vel para criar linha ap�s gerar todos os indices
 			int dsci = (rowMaxi + 3); // Vari�vel para criar linha abaixo da �ltima criada

 			Row rowPDONew1 = sheetAnalisePDO.createRow((short) maxi);
 			rowPDONew1.createCell(5).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_max_flow"));
 			rowPDONew1.createCell(6).setCellFormula("MAX(G19:G" + (rowMaxi + 1) + ")");
 			rowPDONew1.createCell(7).setCellFormula("MAX(H19:H" + (rowMaxi + 1) + ")");
 			rowPDONew1.createCell(8).setCellFormula("MAX(I19:I" + (rowMaxi + 1) + ")");

 			// Estilo				
 			rowPDONew1.getCell(5).setCellStyle(stylePDO);
 			rowPDONew1.getCell(6).setCellStyle(stylePDO1);
 			rowPDONew1.getCell(7).setCellStyle(stylePDO1);
 			rowPDONew1.getCell(8).setCellStyle(stylePDO1);

 			Row rowPDONew2 = sheetAnalisePDO.createRow((short) dsci);
 			rowPDONew2.createCell(12).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_hour_per_service_level_flow"));
 			rowPDONew2.createCell(16).setCellValue(tm.monthComparison(mes) + " / " + ano);
 			rowPDONew2.createCell(18).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_low_total"));
 			rowPDONew2.createCell(19).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_observation"));

 			// Estilo
 			rowPDONew2.getCell(12).setCellStyle(stylePDO);
 			rowPDONew2.getCell(16).setCellStyle(stylePDO);
 			rowPDONew2.getCell(18).setCellStyle(stylePDO2);
 			rowPDONew2.getCell(19).setCellStyle(stylePDO2);

 			// Mesclar c�lulas
 			sheetAnalisePDO.addMergedRegion(CellRangeAddress.valueOf("M" + (dsci + 1) + ":P" + (dsci + 1) + ""));
 			sheetAnalisePDO.addMergedRegion(CellRangeAddress.valueOf("Q" + (dsci + 1) + ":R" + (dsci + 1) + ""));
 			sheetAnalisePDO.addMergedRegion(CellRangeAddress.valueOf("S" + (dsci + 1) + ":S" + (dsci + 2) + ""));
 			sheetAnalisePDO.addMergedRegion(CellRangeAddress.valueOf("T" + (dsci + 1) + ":V" + (dsci + 2) + ""));
 			sheetAnalisePDO.addMergedRegion(CellRangeAddress.valueOf("T" + (dsci + 3) + ":V" + (dsci + 3) + ""));

 			Row rowPDONew3 = sheetAnalisePDO.createRow((short) (dsci + 1));
 			rowPDONew3.createCell(12).setCellValue(categoriaA);
 			rowPDONew3.createCell(13).setCellValue(categoriaB);
 			rowPDONew3.createCell(14).setCellValue(categoriaC);
 			rowPDONew3.createCell(15).setCellValue(categoriaD);
 			rowPDONew3.createCell(16).setCellValue(categoriaE);
 			rowPDONew3.createCell(17).setCellValue(categoriaF);

 			// Estilo				
 			rowPDONew3.getCell(12).setCellStyle(stylePDO);
 			rowPDONew3.getCell(13).setCellStyle(stylePDO);
 			rowPDONew3.getCell(14).setCellStyle(stylePDO);
 			rowPDONew3.getCell(15).setCellStyle(stylePDO);
 			rowPDONew3.getCell(16).setCellStyle(stylePDO);
 			rowPDONew3.getCell(17).setCellStyle(stylePDO);

 			Row rowPDONew4 = sheetAnalisePDO.createRow((short) (dsci + 2));
 			rowPDONew4.createCell(12).setCellFormula("SUM(M19:M" + (rowMaxi + 1) + ")");
 			rowPDONew4.createCell(13).setCellFormula("SUM(N19:N" + (rowMaxi + 1) + ")");
 			rowPDONew4.createCell(14).setCellFormula("SUM(O19:O" + (rowMaxi + 1) + ")");
 			rowPDONew4.createCell(15).setCellFormula("SUM(P19:P" + (rowMaxi + 1) + ")");
 			rowPDONew4.createCell(16).setCellFormula("SUM(Q19:Q" + (rowMaxi + 1) + ")");
 			rowPDONew4.createCell(17).setCellFormula("SUM(R19:R" + (rowMaxi + 1) + ")");
 			rowPDONew4.createCell(18).setCellFormula("SUM(M" + (dsci + 3) + ":R" + (dsci + 3) + ")");
 			rowPDONew4.createCell(19).setCellValue("");

 			// Estilo				
 			rowPDONew4.getCell(12).setCellStyle(stylePDO);
 			rowPDONew4.getCell(13).setCellStyle(stylePDO);
 			rowPDONew4.getCell(14).setCellStyle(stylePDO);
 			rowPDONew4.getCell(15).setCellStyle(stylePDO);
 			rowPDONew4.getCell(16).setCellStyle(stylePDO);
 			rowPDONew4.getCell(17).setCellStyle(stylePDO);
 			rowPDONew4.getCell(18).setCellStyle(stylePDO1);
 			rowPDONew4.getCell(19).setCellStyle(stylePDO);

 			// Linhas
 			propertyPDO.drawBorders(new CellRangeAddress(maxi, maxi, 5, 8), BorderStyle.THIN, BorderExtent.ALL);
 			propertyPDO.drawBorders(new CellRangeAddress(dsci, (dsci + 2), 12, 21), BorderStyle.THIN, BorderExtent.ALL);

 			propertyPDO.applyBorders(sheetAnalisePDO);

 			// ------ An�lise Pista_Dupla_Oeste - END ------//

 			// Criar Sheets
 			XSSFSheet sheetGraficoFluxo1 = null;
 			XSSFSheet sheetGraficoDensidade1 = null;

 			int gfIni = 3;
 			int gfFim = 122;
 			int dia_mes = 1;
 			int dia_ini = 1;
 			int dia_fim = dia_ini + 4;

 			int diax_mes = 1;
 			int diax_ini = 1;
 			int diax_fim = dia_ini + 4;

 			// ------ Gr�fico Fluxo 1 - Begin ------------- //

 			// Estilo - Gr�ficos

 			// T�tulo
 			CellStyle styleG = workbook.createCellStyle();
 			Font fontG = workbook.createFont();
 			fontG.setColor(HSSFColor.HSSFColorPredefined.DARK_BLUE.getIndex());
 			fontG.setBold(true);
 			fontG.setFontName(HSSFFont.FONT_ARIAL);
 			fontG.setFontHeightInPoints((short) 10);
 			styleG.setFont(fontG);
 			styleG.setAlignment(HorizontalAlignment.CENTER);
 			styleG.setFillBackgroundColor(IndexedColors.WHITE.getIndex());

 			// Descri��es
 			CellStyle styleDG = workbook.createCellStyle();
 			Font fontDG = workbook.createFont();
 			fontDG.setBold(true);
 			fontDG.setFontName(HSSFFont.FONT_ARIAL);
 			fontDG.setFontHeightInPoints((short) 10);
 			styleDG.setFont(fontDG);
 			styleDG.setAlignment(HorizontalAlignment.CENTER);
 			styleDG.setFillBackgroundColor(IndexedColors.WHITE.getIndex());

 			for (int g = 1; g <= 6; g++) {

 				sheetGraficoFluxo1 = workbook.createSheet(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_chart_title_flow")+ g + "");

 				Row rowGrf1_1 = sheetGraficoFluxo1.createRow((short) 0);
 				rowGrf1_1.createCell(1).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_company")+": "+RoadConcessionaire.roadConcessionaire+"");
 				rowGrf1_1.getCell(1).setCellStyle(styleG);

 				Row rowGrf1_2 = sheetGraficoFluxo1.createRow((short) 1);
 				rowGrf1_2.createCell(1).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_lot_tab")+": "+ano);
 				rowGrf1_2.getCell(1).setCellStyle(styleG);

 				Row rowGrf1_3 = sheetGraficoFluxo1.createRow((short) 2);
 				rowGrf1_3.createCell(1).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_chart_rate_of_flow_lane"));
 				rowGrf1_3.getCell(1).setCellStyle(styleG);

 				Row rowGrf1_4 = sheetGraficoFluxo1.createRow((short) 3);
 				rowGrf1_4.createCell(1).setCellValue(
 						sat.getEstrada() +" - "+localeSheet.getStringKey("$label_excel_sheet_monthly_flow_chart_strech_uniform")+""+sat.getKm() +" "+localeSheet.getStringKey("$label_excel_sheet_monthly_flow_chart_strech_uniform")+""+ sat.getKm() + " ("+localeSheet.getStringKey("$label_excel_sheet_monthly_flow_chart_track")+" "+ direction1 +")");
 				rowGrf1_4.getCell(1).setCellStyle(styleG);

 				Row rowGrf1_5 = sheetGraficoFluxo1.createRow((short) 10);
 				rowGrf1_5.createCell(0).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nse"));
 				rowGrf1_5.getCell(0).setCellStyle(styleG);

 				Row rowGrf1_6 = sheetGraficoFluxo1.createRow((short) 14);
 				rowGrf1_6.createCell(0).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nsd"));
 				rowGrf1_6.getCell(0).setCellStyle(styleG);

 				Row rowGrf1_7 = sheetGraficoFluxo1.createRow((short) 19);
 				rowGrf1_7.createCell(0).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nsc"));
 				rowGrf1_7.getCell(0).setCellStyle(styleG);

 				Row rowGrf1_8 = sheetGraficoFluxo1.createRow((short) 25);
 				rowGrf1_8.createCell(0).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nsb"));
 				rowGrf1_8.getCell(0).setCellStyle(styleG);

 				Row rowGrf1_9 = sheetGraficoFluxo1.createRow((short) 33);
 				rowGrf1_9.createCell(0).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nsa"));
 				rowGrf1_9.getCell(0).setCellStyle(styleG);

 				sheetGraficoFluxo1.addMergedRegion(CellRangeAddress.valueOf("B1:O1"));
 				sheetGraficoFluxo1.addMergedRegion(CellRangeAddress.valueOf("B2:O2"));
 				sheetGraficoFluxo1.addMergedRegion(CellRangeAddress.valueOf("B3:O3"));
 				sheetGraficoFluxo1.addMergedRegion(CellRangeAddress.valueOf("B4:O4"));

 				sheetGraficoFluxo1.addMergedRegion(CellRangeAddress.valueOf("B44:O44"));
 				sheetGraficoFluxo1.addMergedRegion(CellRangeAddress.valueOf("B45:O45"));
 				sheetGraficoFluxo1.addMergedRegion(CellRangeAddress.valueOf("B46:O46"));
 				sheetGraficoFluxo1.addMergedRegion(CellRangeAddress.valueOf("B47:O47"));

 				if (g < 6) {
 					// Mesclar c�lulas - Sentido1

 					sheetGraficoFluxo1.addMergedRegion(CellRangeAddress.valueOf("B38:D38"));
 					sheetGraficoFluxo1.addMergedRegion(CellRangeAddress.valueOf("E38:G38"));
 					sheetGraficoFluxo1.addMergedRegion(CellRangeAddress.valueOf("H38:J38"));
 					sheetGraficoFluxo1.addMergedRegion(CellRangeAddress.valueOf("K38:M38"));
 					sheetGraficoFluxo1.addMergedRegion(CellRangeAddress.valueOf("N38:P38"));

 					// Mesclar C�lulas - Sentido2

 					sheetGraficoFluxo1.addMergedRegion(CellRangeAddress.valueOf("B81:D81"));
 					sheetGraficoFluxo1.addMergedRegion(CellRangeAddress.valueOf("E81:G81"));
 					sheetGraficoFluxo1.addMergedRegion(CellRangeAddress.valueOf("H81:J81"));
 					sheetGraficoFluxo1.addMergedRegion(CellRangeAddress.valueOf("K81:M81"));
 					sheetGraficoFluxo1.addMergedRegion(CellRangeAddress.valueOf("N81:P81"));

 				}

 				if (g == 6 && daysInMonth == 28) {

 					// Mesclar C�lulas - Sentido1
 					sheetGraficoFluxo1.addMergedRegion(CellRangeAddress.valueOf("B38:D38"));
 					sheetGraficoFluxo1.addMergedRegion(CellRangeAddress.valueOf("E38:G38"));
 					sheetGraficoFluxo1.addMergedRegion(CellRangeAddress.valueOf("H38:J38"));

 					// Mesclar C�lulas - Sentido2				
 					sheetGraficoFluxo1.addMergedRegion(CellRangeAddress.valueOf("B81:D81"));
 					sheetGraficoFluxo1.addMergedRegion(CellRangeAddress.valueOf("E81:G81"));
 					sheetGraficoFluxo1.addMergedRegion(CellRangeAddress.valueOf("H81:J81"));

 				}

 				if (g == 6 && daysInMonth == 29) {

 					// Mesclar C�lulas - Sentido1
 					sheetGraficoFluxo1.addMergedRegion(CellRangeAddress.valueOf("B38:D38"));
 					sheetGraficoFluxo1.addMergedRegion(CellRangeAddress.valueOf("E38:G38"));
 					sheetGraficoFluxo1.addMergedRegion(CellRangeAddress.valueOf("H38:J38"));
 					sheetGraficoFluxo1.addMergedRegion(CellRangeAddress.valueOf("K38:M38"));

 					// Mesclar C�lulas - Sentido2
 					sheetGraficoFluxo1.addMergedRegion(CellRangeAddress.valueOf("B81:D81"));
 					sheetGraficoFluxo1.addMergedRegion(CellRangeAddress.valueOf("E81:G81"));
 					sheetGraficoFluxo1.addMergedRegion(CellRangeAddress.valueOf("H81:J81"));
 					sheetGraficoFluxo1.addMergedRegion(CellRangeAddress.valueOf("K81:M81"));

 				}

 				if (g == 6 && daysInMonth == 30) {

 					// Mesclar C�lulas - Sentido1
 					sheetGraficoFluxo1.addMergedRegion(CellRangeAddress.valueOf("B38:D38"));
 					sheetGraficoFluxo1.addMergedRegion(CellRangeAddress.valueOf("E38:G38"));
 					sheetGraficoFluxo1.addMergedRegion(CellRangeAddress.valueOf("H38:J38"));
 					sheetGraficoFluxo1.addMergedRegion(CellRangeAddress.valueOf("K38:M38"));
 					sheetGraficoFluxo1.addMergedRegion(CellRangeAddress.valueOf("N38:P38"));

 					// Mesclar C�lulas - Sentido2
 					sheetGraficoFluxo1.addMergedRegion(CellRangeAddress.valueOf("B81:D81"));
 					sheetGraficoFluxo1.addMergedRegion(CellRangeAddress.valueOf("E81:G81"));
 					sheetGraficoFluxo1.addMergedRegion(CellRangeAddress.valueOf("H81:J81"));
 					sheetGraficoFluxo1.addMergedRegion(CellRangeAddress.valueOf("K81:M81"));
 					// sheetGraficoFluxo1.addMergedRegion(CellRangeAddress.valueOf("N81:M81"));

 				}

 				if (g == 6 && daysInMonth == 31) {

 					// Mesclar C�lulas - Sentido1
 					sheetGraficoFluxo1.addMergedRegion(CellRangeAddress.valueOf("B38:D38"));
 					sheetGraficoFluxo1.addMergedRegion(CellRangeAddress.valueOf("E38:G38"));
 					sheetGraficoFluxo1.addMergedRegion(CellRangeAddress.valueOf("H38:J38"));
 					sheetGraficoFluxo1.addMergedRegion(CellRangeAddress.valueOf("K38:M38"));
 					sheetGraficoFluxo1.addMergedRegion(CellRangeAddress.valueOf("N38:P38"));
 					sheetGraficoFluxo1.addMergedRegion(CellRangeAddress.valueOf("Q38:S38"));

 					// Mesclar C�lulas - Sentido2
 					sheetGraficoFluxo1.addMergedRegion(CellRangeAddress.valueOf("B81:D81"));
 					sheetGraficoFluxo1.addMergedRegion(CellRangeAddress.valueOf("E81:G81"));
 					sheetGraficoFluxo1.addMergedRegion(CellRangeAddress.valueOf("H81:J81"));
 					sheetGraficoFluxo1.addMergedRegion(CellRangeAddress.valueOf("K81:M81"));
 					sheetGraficoFluxo1.addMergedRegion(CellRangeAddress.valueOf("N81:P81"));
 					sheetGraficoFluxo1.addMergedRegion(CellRangeAddress.valueOf("Q81:S81"));

 				}

 				sheetGraficoFluxo1.addMergedRegion(CellRangeAddress.valueOf("G40:J40"));
 				sheetGraficoFluxo1.addMergedRegion(CellRangeAddress.valueOf("K40:L40"));
 				sheetGraficoFluxo1.addMergedRegion(CellRangeAddress.valueOf("G83:J83"));
 				sheetGraficoFluxo1.addMergedRegion(CellRangeAddress.valueOf("K83:L83"));

 				/* Gr�fico - Sentido1 */

 				@SuppressWarnings("rawtypes")
 				Drawing drawing = sheetGraficoFluxo1.createDrawingPatriarch();
 				ClientAnchor anchor = null;
 				anchor = drawing.createAnchor(0, 0, 0, 0, 1, 6, 16, 35);

 				Chart chart = drawing.createChart(anchor);
 				ChartLegend legend = chart.getOrCreateLegend();
 				legend.setPosition(LegendPosition.RIGHT);

 				LineChartData dados = chart.getChartDataFactory().createLineChartData();

 				ChartAxis bottomAxis = chart.getChartAxisFactory().createCategoryAxis(AxisPosition.BOTTOM);
 				ValueAxis leftAxis = chart.getChartAxisFactory().createValueAxis(AxisPosition.LEFT);
 				leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);

 				// Testar outros dias
 				if (g == 6 && daysInMonth == 28)
 					gfFim -= 48;
 				if (g == 6 && daysInMonth == 29)
 					gfFim -= 24;
 				if (g == 6 && daysInMonth == 30)
 					gfFim += 0;
 				if (g == 6 && daysInMonth == 31)
 					gfFim += 24;

 				ChartDataSource<Number> xs = DataSources.fromNumericCellRange(sheetGraficoFluxo,
 						new CellRangeAddress(gfIni, gfFim, 2, 2));
 				ChartDataSource<Number> ys = DataSources.fromNumericCellRange(sheetGraficoFluxo,
 						new CellRangeAddress(gfIni, gfFim, 6, 6));
 				ChartDataSource<Number> ys1 = DataSources.fromNumericCellRange(sheetGraficoFluxo,
 						new CellRangeAddress(gfIni, gfFim, 11, 11));
 				ChartDataSource<Number> ys2 = DataSources.fromNumericCellRange(sheetGraficoFluxo,
 						new CellRangeAddress(gfIni, gfFim, 12, 12));
 				ChartDataSource<Number> ys3 = DataSources.fromNumericCellRange(sheetGraficoFluxo,
 						new CellRangeAddress(gfIni, gfFim, 13, 13));
 				ChartDataSource<Number> ys4 = DataSources.fromNumericCellRange(sheetGraficoFluxo,
 						new CellRangeAddress(gfIni, gfFim, 14, 14));
 				ChartDataSource<Number> ys5 = DataSources.fromNumericCellRange(sheetGraficoFluxo,
 						new CellRangeAddress(gfIni, gfFim, 15, 15));

 				LineChartSeries seriesL = dados.addSeries(xs, ys);
 				seriesL.setTitle(direction1);
 				LineChartSeries seriesL1 = dados.addSeries(xs, ys1);
 				seriesL1.setTitle(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nsa"));
 				LineChartSeries seriesL2 = dados.addSeries(xs, ys2);
 				seriesL2.setTitle(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nsb"));
 				LineChartSeries seriesL3 = dados.addSeries(xs, ys3);
 				seriesL3.setTitle(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nsc"));
 				LineChartSeries seriesL4 = dados.addSeries(xs, ys4);
 				seriesL4.setTitle(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nsd"));
 				LineChartSeries seriesL5 = dados.addSeries(xs, ys5);
 				seriesL5.setTitle(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nse"));

 				chart.plot(dados, bottomAxis, leftAxis);

 				Row rowGrf1_10 = sheetGraficoFluxo1.createRow((short) 37);

 				if (g < 6) { // CHART DAY LABEL

 					rowGrf1_10.createCell(1).setCellValue(dia_mes);
 					rowGrf1_10.createCell(4).setCellValue((dia_mes + 1));
 					rowGrf1_10.createCell(7).setCellValue((dia_mes + 2));
 					rowGrf1_10.createCell(10).setCellValue((dia_mes + 3));

 					// Estilo
 					rowGrf1_10.getCell(1).setCellStyle(styleDG);
 					rowGrf1_10.getCell(4).setCellStyle(styleDG);
 					rowGrf1_10.getCell(7).setCellStyle(styleDG);
 					rowGrf1_10.getCell(10).setCellStyle(styleDG);

 					if (dia_mes == 6) {
 						rowGrf1_10.createCell(13).setCellValue(10);
 						rowGrf1_10.getCell(13).setCellStyle(styleDG);
 					}

 					else {
 						rowGrf1_10.createCell(13).setCellValue((dia_mes + 4));
 						rowGrf1_10.getCell(13).setCellStyle(styleDG);
 					}
 				}

 				if (g == 6 && daysInMonth == 28) {
 					rowGrf1_10.createCell(1).setCellValue(dia_mes);
 					rowGrf1_10.createCell(4).setCellValue((dia_mes + 1));
 					rowGrf1_10.createCell(7).setCellValue((dia_mes + 2));
 					rowGrf1_10.createCell(10).setCellValue("");
 					rowGrf1_10.createCell(13).setCellValue("");

 					// Estilo
 					rowGrf1_10.getCell(1).setCellStyle(styleDG);
 					rowGrf1_10.getCell(4).setCellStyle(styleDG);
 					rowGrf1_10.getCell(7).setCellStyle(styleDG);
 					rowGrf1_10.getCell(10).setCellStyle(styleDG);
 					rowGrf1_10.getCell(13).setCellStyle(styleDG);

 					// Coordenadas do ultimo gr�fico
 					CTMarker chartEndCoords = CTMarker.Factory.newInstance();
 					chartEndCoords.setCol(10);
 					chartEndCoords.setColOff(0);
 					chartEndCoords.setRow(35);
 					chartEndCoords.setRowOff(0);
 					((XSSFDrawing) drawing).getCTDrawing().getTwoCellAnchorArray(0).setTo(chartEndCoords);

 				}

 				if (g == 6 && daysInMonth == 29) {
 					rowGrf1_10.createCell(1).setCellValue(dia_mes);
 					rowGrf1_10.createCell(4).setCellValue((dia_mes + 1));
 					rowGrf1_10.createCell(7).setCellValue((dia_mes + 2));
 					rowGrf1_10.createCell(10).setCellValue((dia_mes + 3));
 					rowGrf1_10.createCell(13).setCellValue("");

 					// Estilo
 					rowGrf1_10.getCell(1).setCellStyle(styleDG);
 					rowGrf1_10.getCell(4).setCellStyle(styleDG);
 					rowGrf1_10.getCell(7).setCellStyle(styleDG);
 					rowGrf1_10.getCell(10).setCellStyle(styleDG);
 					rowGrf1_10.getCell(13).setCellStyle(styleDG);

 					// Coordenadas do ultimo gr�fico
 					CTMarker chartEndCoords = CTMarker.Factory.newInstance();
 					chartEndCoords.setCol(13);
 					chartEndCoords.setColOff(0);
 					chartEndCoords.setRow(35);
 					chartEndCoords.setRowOff(0);
 					((XSSFDrawing) drawing).getCTDrawing().getTwoCellAnchorArray(0).setTo(chartEndCoords);

 				}

 				if (g == 6 && daysInMonth == 30) {
 					rowGrf1_10.createCell(1).setCellValue(dia_mes);
 					rowGrf1_10.createCell(4).setCellValue((dia_mes + 1));
 					rowGrf1_10.createCell(7).setCellValue((dia_mes + 2));
 					rowGrf1_10.createCell(10).setCellValue((dia_mes + 3));
 					rowGrf1_10.createCell(13).setCellValue((dia_mes + 4));

 					// Estilo
 					rowGrf1_10.getCell(1).setCellStyle(styleDG);
 					rowGrf1_10.getCell(4).setCellStyle(styleDG);
 					rowGrf1_10.getCell(7).setCellStyle(styleDG);
 					rowGrf1_10.getCell(10).setCellStyle(styleDG);
 					rowGrf1_10.getCell(13).setCellStyle(styleDG);

 					// Coordenadas do ultimo gr�fico
 					CTMarker chartEndCoords = CTMarker.Factory.newInstance();
 					chartEndCoords.setCol(16);
 					chartEndCoords.setColOff(0);
 					chartEndCoords.setRow(35);
 					chartEndCoords.setRowOff(0);
 					((XSSFDrawing) drawing).getCTDrawing().getTwoCellAnchorArray(0).setTo(chartEndCoords);

 				}

 				if (g == 6 && daysInMonth == 31) {
 					rowGrf1_10.createCell(1).setCellValue(dia_mes);
 					rowGrf1_10.createCell(4).setCellValue((dia_mes + 1));
 					rowGrf1_10.createCell(7).setCellValue((dia_mes + 2));
 					rowGrf1_10.createCell(10).setCellValue((dia_mes + 3));
 					rowGrf1_10.createCell(13).setCellValue((dia_mes + 4));
 					rowGrf1_10.createCell(16).setCellValue((dia_mes + 5));

 					// Estilo
 					rowGrf1_10.getCell(1).setCellStyle(styleDG);
 					rowGrf1_10.getCell(4).setCellStyle(styleDG);
 					rowGrf1_10.getCell(7).setCellStyle(styleDG);
 					rowGrf1_10.getCell(10).setCellStyle(styleDG);
 					rowGrf1_10.getCell(13).setCellStyle(styleDG);
 					rowGrf1_10.getCell(16).setCellStyle(styleDG);

 					// Coordenadas do �ltimo gr�fico
 					CTMarker chartEndCoords = CTMarker.Factory.newInstance();
 					chartEndCoords.setCol(19);
 					chartEndCoords.setColOff(0);
 					chartEndCoords.setRow(35);
 					chartEndCoords.setRowOff(0);
 					((XSSFDrawing) drawing).getCTDrawing().getTwoCellAnchorArray(0).setTo(chartEndCoords);
 				}

 				dia_mes = dia_mes + 5;

 				if (g == 6)
 					dia_mes = 1;

 				Row rowGrf1_11 = sheetGraficoFluxo1.createRow((short) 39);
 				rowGrf1_11.createCell(5).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_chart_period"));
 				rowGrf1_11.getCell(5).setCellStyle(styleG);

 				String mth_ = "";
 				String dd = "";
 				String df = "";

 				if (dia_ini <= 9)
 					dd = "0";
 				else
 					dd = "";
 				if (dia_fim <= 9)
 					df = "0";
 				else
 					df = "";
 				if (mes < 10)
 					mth_ = "0";
 				else
 					mth_ = "";

 				if (g < 6) {
 					rowGrf1_11.createCell(6).setCellValue("" + dd + "" + dia_ini + "/" + mth_ + "" + mes + "/" + ano
 							+ " a " + df + "" + dia_fim + "/" + mth_ + "" + mes + "/" + ano + "");
 					rowGrf1_11.getCell(6).setCellStyle(styleG);
 				}

 				if (g == 6 && daysInMonth == 28) {
 					rowGrf1_11.createCell(6).setCellValue("" + dd + "" + dia_ini + "/" + mth_ + "" + mes + "/" + ano
 							+ " a " + df + "" + daysInMonth + "/" + mth_ + "" + mes + "/" + ano + "");
 					rowGrf1_11.getCell(6).setCellStyle(styleG);
 				}

 				if (g == 6 && daysInMonth == 29) {
 					rowGrf1_11.createCell(6).setCellValue("" + dd + "" + dia_ini + "/" + mth_ + "" + mes + "/" + ano
 							+ " a " + df + "" + daysInMonth + "/" + mth_ + "" + mes + "/" + ano + "");
 					rowGrf1_11.getCell(6).setCellStyle(styleG);
 				}

 				if (g == 6 && daysInMonth == 30) {
 					rowGrf1_11.createCell(6).setCellValue("" + dd + "" + dia_ini + "/" + mth_ + "" + mes + "/" + ano
 							+ " a " + df + "" + daysInMonth + "/" + mth_ + "" + mes + "/" + ano + "");
 					rowGrf1_11.getCell(6).setCellStyle(styleG);
 				}

 				if (g == 6 && daysInMonth == 31) {
 					rowGrf1_11.createCell(6).setCellValue("" + dd + "" + dia_ini + "/" + mth_ + "" + mes + "/" + ano
 							+ " a " + df + "" + daysInMonth + "/" + mth_ + "" + mes + "/" + ano + "");
 					rowGrf1_11.getCell(6).setCellStyle(styleG);
 				}

 				rowGrf1_11.createCell(10).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_chart_24_hours"));
 				rowGrf1_11.getCell(10).setCellStyle(styleG);

 				dia_ini = dia_ini + 5;
 				dia_fim = dia_fim + 5;

 				if (g == 6) {
 					dia_ini = 1;
 					dia_fim = 5;
 				}

 				Row rowGrf1_12 = sheetGraficoFluxo1.createRow((short) 43);
 				rowGrf1_12.createCell(1).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_company")+": "+RoadConcessionaire.roadConcessionaire+"");
 				rowGrf1_12.getCell(1).setCellStyle(styleG);

 				Row rowGrf1_13 = sheetGraficoFluxo1.createRow((short) 44);
 				rowGrf1_13.createCell(1).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_lot_tab")+": "+ano);
 				rowGrf1_13.getCell(1).setCellStyle(styleG);

 				Row rowGrf1_14 = sheetGraficoFluxo1.createRow((short) 45);
 				rowGrf1_14.createCell(1).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_chart_rate_of_flow_lane"));
 				rowGrf1_14.getCell(1).setCellStyle(styleG);

 				Row rowGrf1_15 = sheetGraficoFluxo1.createRow((short) 46);
 				rowGrf1_15.createCell(1).setCellValue(
 						sat.getEstrada() +" - "+localeSheet.getStringKey("$label_excel_sheet_monthly_flow_chart_strech_uniform")+""+sat.getKm() +" "+localeSheet.getStringKey("$label_excel_sheet_monthly_flow_chart_strech_uniform_comp")+""+ sat.getEstrada() + " ("+localeSheet.getStringKey("$label_excel_sheet_monthly_flow_chart_track")+" "+ direction2 +")");
 				rowGrf1_15.getCell(1).setCellStyle(styleG);

 				Row rowGrf1_16 = sheetGraficoFluxo1.createRow((short) 53);
 				rowGrf1_16.createCell(0).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nse"));
 				rowGrf1_16.getCell(0).setCellStyle(styleG);

 				Row rowGrf1_17 = sheetGraficoFluxo1.createRow((short) 57);
 				rowGrf1_17.createCell(0).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nsd"));
 				rowGrf1_17.getCell(0).setCellStyle(styleG);

 				Row rowGrf1_18 = sheetGraficoFluxo1.createRow((short) 62);
 				rowGrf1_18.createCell(0).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nsc"));
 				rowGrf1_18.getCell(0).setCellStyle(styleG);

 				Row rowGrf1_19 = sheetGraficoFluxo1.createRow((short) 68);
 				rowGrf1_19.createCell(0).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nsb"));
 				rowGrf1_19.getCell(0).setCellStyle(styleG);

 				Row rowGrf1_20 = sheetGraficoFluxo1.createRow((short) 76);
 				rowGrf1_20.createCell(0).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nsa"));
 				rowGrf1_20.getCell(0).setCellStyle(styleG);

 				// Gr�fico Oeste

 				@SuppressWarnings("rawtypes")
 				Drawing drawing1 = sheetGraficoFluxo1.createDrawingPatriarch();
 				ClientAnchor anchor1 = drawing1.createAnchor(0, 0, 0, 0, 1, 49, 16, 79);

 				Chart chart1 = drawing1.createChart(anchor1);
 				ChartLegend legend1 = chart1.getOrCreateLegend();
 				legend1.setPosition(LegendPosition.RIGHT);

 				LineChartData dadosx = chart1.getChartDataFactory().createLineChartData();

 				ChartAxis bottomAxis1 = chart1.getChartAxisFactory().createCategoryAxis(AxisPosition.BOTTOM);
 				ValueAxis leftAxis1 = chart1.getChartAxisFactory().createValueAxis(AxisPosition.LEFT);
 				leftAxis1.setCrosses(AxisCrosses.AUTO_ZERO);

 				ChartDataSource<Number> xso = DataSources.fromNumericCellRange(sheetGraficoFluxo,
 						new CellRangeAddress(gfIni, gfFim, 2, 2));
 				ChartDataSource<Number> yso = DataSources.fromNumericCellRange(sheetGraficoFluxo,
 						new CellRangeAddress(gfIni, gfFim, 10, 10));
 				ChartDataSource<Number> ys1o = DataSources.fromNumericCellRange(sheetGraficoFluxo,
 						new CellRangeAddress(gfIni, gfFim, 16, 16));
 				ChartDataSource<Number> ys2o = DataSources.fromNumericCellRange(sheetGraficoFluxo,
 						new CellRangeAddress(gfIni, gfFim, 17, 17));
 				ChartDataSource<Number> ys3o = DataSources.fromNumericCellRange(sheetGraficoFluxo,
 						new CellRangeAddress(gfIni, gfFim, 18, 18));
 				ChartDataSource<Number> ys4o = DataSources.fromNumericCellRange(sheetGraficoFluxo,
 						new CellRangeAddress(gfIni, gfFim, 19, 19));
 				ChartDataSource<Number> ys5o = DataSources.fromNumericCellRange(sheetGraficoFluxo,
 						new CellRangeAddress(gfIni, gfFim, 20, 20));

 				LineChartSeries seriesO1 = dadosx.addSeries(xso, yso);
 				seriesO1.setTitle(direction2);
 				LineChartSeries seriesO2 = dadosx.addSeries(xso, ys1o);
 				seriesO2.setTitle(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nsa"));
 				LineChartSeries seriesO3 = dadosx.addSeries(xso, ys2o);
 				seriesO3.setTitle(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nsb"));
 				LineChartSeries seriesO4 = dadosx.addSeries(xso, ys3o);
 				seriesO4.setTitle(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nsc"));
 				LineChartSeries seriesO5 = dadosx.addSeries(xso, ys4o);
 				seriesO5.setTitle(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nsd"));
 				LineChartSeries seriesO6 = dadosx.addSeries(xso, ys5o);
 				seriesO6.setTitle(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nse"));

 				chart1.plot(dadosx, bottomAxis1, leftAxis1);

 				Row rowGrf1_21 = sheetGraficoFluxo1.createRow((short) 80);

 				if (g < 6) { // CHART DAY LABELS
 				
 					rowGrf1_21.createCell(1).setCellValue(diax_mes);
 					rowGrf1_21.createCell(4).setCellValue((diax_mes + 1));
 					rowGrf1_21.createCell(7).setCellValue((diax_mes + 2));
 					rowGrf1_21.createCell(10).setCellValue((diax_mes + 3));

 					// Estilo
 					rowGrf1_21.getCell(1).setCellStyle(styleDG);
 					rowGrf1_21.getCell(4).setCellStyle(styleDG);
 					rowGrf1_21.getCell(7).setCellStyle(styleDG);
 					rowGrf1_21.getCell(10).setCellStyle(styleDG);

 					if (diax_mes == 6) {
 						rowGrf1_21.createCell(13).setCellValue(10);
 						rowGrf1_21.getCell(13).setCellStyle(styleDG);
 					}

 					else {
 						rowGrf1_21.createCell(13).setCellValue((diax_mes + 4));
 						rowGrf1_21.getCell(13).setCellStyle(styleDG);
 					}
 				}

 				if (g == 6 && daysInMonth == 28) {
 					rowGrf1_21.createCell(1).setCellValue(diax_mes);
 					rowGrf1_21.createCell(4).setCellValue((diax_mes + 1));
 					rowGrf1_21.createCell(7).setCellValue((diax_mes + 2));
 					rowGrf1_21.createCell(10).setCellValue("");
 					rowGrf1_21.createCell(13).setCellValue("");

 					// Estilo
 					rowGrf1_21.getCell(1).setCellStyle(styleDG);
 					rowGrf1_21.getCell(4).setCellStyle(styleDG);
 					rowGrf1_21.getCell(7).setCellStyle(styleDG);

 					// Coordenadas do ultimo gr�fico
 					CTMarker chartEndCoords1 = CTMarker.Factory.newInstance();
 					chartEndCoords1.setCol(10);
 					chartEndCoords1.setColOff(0);
 					chartEndCoords1.setRow(79);
 					chartEndCoords1.setRowOff(0);
 					((XSSFDrawing) drawing).getCTDrawing().getTwoCellAnchorArray(1).setTo(chartEndCoords1);

 				}

 				if (g == 6 && daysInMonth == 29) {
 					rowGrf1_21.createCell(1).setCellValue(diax_mes);
 					rowGrf1_21.createCell(4).setCellValue((diax_mes + 1));
 					rowGrf1_21.createCell(7).setCellValue((diax_mes + 2));
 					rowGrf1_21.createCell(10).setCellValue((diax_mes + 3));
 					rowGrf1_21.createCell(13).setCellValue("");

 					// Estilo
 					rowGrf1_21.getCell(1).setCellStyle(styleDG);
 					rowGrf1_21.getCell(4).setCellStyle(styleDG);
 					rowGrf1_21.getCell(7).setCellStyle(styleDG);
 					rowGrf1_21.getCell(10).setCellStyle(styleDG);

 					// Coordenadas do ultimo gr�fico
 					CTMarker chartEndCoords1 = CTMarker.Factory.newInstance();
 					chartEndCoords1.setCol(13);
 					chartEndCoords1.setColOff(0);
 					chartEndCoords1.setRow(79);
 					chartEndCoords1.setRowOff(0);
 					((XSSFDrawing) drawing).getCTDrawing().getTwoCellAnchorArray(1).setTo(chartEndCoords1);

 				}

 				if (g == 6 && daysInMonth == 30) {
 					rowGrf1_21.createCell(1).setCellValue(diax_mes);
 					rowGrf1_21.createCell(4).setCellValue((diax_mes + 1));
 					rowGrf1_21.createCell(7).setCellValue((diax_mes + 2));
 					rowGrf1_21.createCell(10).setCellValue((diax_mes + 3));
 					rowGrf1_21.createCell(13).setCellValue((diax_mes + 4));

 					// Estilo
 					rowGrf1_21.getCell(1).setCellStyle(styleDG);
 					rowGrf1_21.getCell(4).setCellStyle(styleDG);
 					rowGrf1_21.getCell(7).setCellStyle(styleDG);
 					rowGrf1_21.getCell(10).setCellStyle(styleDG);
 					rowGrf1_21.getCell(13).setCellStyle(styleDG);

 					// Coordenadas do ultimo gr�fico
 					CTMarker chartEndCoords1 = CTMarker.Factory.newInstance();
 					chartEndCoords1.setCol(16);
 					chartEndCoords1.setColOff(0);
 					chartEndCoords1.setRow(79);
 					chartEndCoords1.setRowOff(0);
 					((XSSFDrawing) drawing).getCTDrawing().getTwoCellAnchorArray(1).setTo(chartEndCoords1);

 				}

 				if (g == 6 && daysInMonth == 31) {
 					rowGrf1_21.createCell(1).setCellValue(diax_mes);
 					rowGrf1_21.createCell(4).setCellValue((diax_mes + 1));
 					rowGrf1_21.createCell(7).setCellValue((diax_mes + 2));
 					rowGrf1_21.createCell(10).setCellValue((diax_mes + 3));
 					rowGrf1_21.createCell(13).setCellValue((diax_mes + 4));
 					rowGrf1_21.createCell(16).setCellValue((diax_mes + 5));

 					// Estilo
 					rowGrf1_21.getCell(1).setCellStyle(styleDG);
 					rowGrf1_21.getCell(4).setCellStyle(styleDG);
 					rowGrf1_21.getCell(7).setCellStyle(styleDG);
 					rowGrf1_21.getCell(10).setCellStyle(styleDG);
 					rowGrf1_21.getCell(13).setCellStyle(styleDG);
 					rowGrf1_21.getCell(16).setCellStyle(styleDG);

 					// Coordenadas do �ltimo gr�fico
 					CTMarker chartEndCoords1 = CTMarker.Factory.newInstance();
 					chartEndCoords1.setCol(19);
 					chartEndCoords1.setColOff(0);
 					chartEndCoords1.setRow(79);
 					chartEndCoords1.setRowOff(0);
 					((XSSFDrawing) drawing).getCTDrawing().getTwoCellAnchorArray(1).setTo(chartEndCoords1);

 				}

 				diax_mes = diax_mes + 5;

 				if (g == 6)
 					diax_mes = 1;

 				Row rowGrf1_22 = sheetGraficoFluxo1.createRow((short) 82);
 				rowGrf1_22.createCell(5).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_chart_period"));
 				rowGrf1_22.getCell(5).setCellStyle(styleG);

 				String mthx_ = "";
 				String ddx = "";
 				String dfx = "";

 				if (diax_ini <= 9)
 					ddx = "0";
 				else
 					ddx = "";
 				if (diax_fim <= 9)
 					dfx = "0";
 				else
 					dfx = "";
 				if (mes < 10)
 					mthx_ = "0";
 				else
 					mthx_ = "";

 				if (g < 6) {
 					rowGrf1_22.createCell(6).setCellValue("" + ddx + "" + diax_ini + "/" + mthx_ + "" + mes + "/" + ano
 							+ " a " + dfx + "" + diax_fim + "/" + mthx_ + "" + mes + "/" + ano + "");
 					rowGrf1_22.getCell(6).setCellStyle(styleG);
 				}

 				if (g == 6 && daysInMonth == 28) {
 					rowGrf1_22.createCell(6).setCellValue("" + ddx + "" + diax_ini + "/" + mthx_ + "" + mes + "/" + ano
 							+ " a " + dfx + "" + daysInMonth + "/" + mthx_ + "" + mes + "/" + ano + "");
 					rowGrf1_22.getCell(6).setCellStyle(styleG);
 				}

 				if (g == 6 && daysInMonth == 29) {
 					rowGrf1_22.createCell(6).setCellValue("" + ddx + "" + diax_ini + "/" + mthx_ + "" + mes + "/" + ano
 							+ " a " + dfx + "" + daysInMonth + "/" + mthx_ + "" + mes + "/" + ano + "");
 					rowGrf1_22.getCell(6).setCellStyle(styleG);
 				}

 				if (g == 6 && daysInMonth == 30) {
 					rowGrf1_22.createCell(6).setCellValue("" + ddx + "" + diax_ini + "/" + mthx_ + "" + mes + "/" + ano
 							+ " a " + dfx + "" + daysInMonth + "/" + mthx_ + "" + mes + "/" + ano + "");
 					rowGrf1_22.getCell(6).setCellStyle(styleG);
 				}

 				if (g == 6 && daysInMonth == 31) {
 					rowGrf1_22.createCell(6).setCellValue("" + ddx + "" + diax_ini + "/" + mthx_ + "" + mes + "/" + ano
 							+ " a " + dfx + "" + daysInMonth + "/" + mthx_ + "" + mes + "/" + ano + "");
 					rowGrf1_22.getCell(6).setCellStyle(styleG);
 				}

 				rowGrf1_22.createCell(10).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_chart_24_hours"));
 				rowGrf1_22.getCell(10).setCellStyle(styleG);

 				diax_ini = diax_ini + 5;
 				diax_fim = diax_fim + 5;

 				if (g == 6) {
 					diax_ini = 1;
 					diax_fim = 5;
 				}

 				if (g < 6) {
 					gfIni = gfIni + 120;
 					gfFim = gfFim + 120;
 				}

 			}

 			// ------ Gr�fico Fluxo - END ---------- //

 			int gdIni = 3;
 			int gdFim = 122;
 			int day_mth = 1;
 			int day_ini = 1;
 			int day_fim = day_ini + 4;

 			int dayx_mes = 1;
 			int dayx_ini = 1;
 			int dayx_fim = dia_ini + 4;

 			// ------ Gr�fico Densidade 1 - Begin ------------- //

 			for (int gd = 1; gd <= 6; gd++) {

 				sheetGraficoDensidade1 = workbook.createSheet(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_chart_title_density")+ gd + "");

 				Row rowGrd1_1 = sheetGraficoDensidade1.createRow((short) 0);
 				rowGrd1_1.createCell(1).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_company")+": "+RoadConcessionaire.roadConcessionaire+"");
 				rowGrd1_1.getCell(1).setCellStyle(styleG);

 				Row rowGrd1_2 = sheetGraficoDensidade1.createRow((short) 1);
 				rowGrd1_2.createCell(1).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_lot_tab")+": "+ano);
 				rowGrd1_2.getCell(1).setCellStyle(styleG);

 				Row rowGrd1_3 = sheetGraficoDensidade1.createRow((short) 2);
 				rowGrd1_3.createCell(1).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_chart_rate_of_flow_lane"));
 				rowGrd1_3.getCell(1).setCellStyle(styleG);

 				Row rowGrd1_4 = sheetGraficoDensidade1.createRow((short) 3);
 				rowGrd1_4.createCell(1).setCellValue(sat.getEstrada() +" - "+localeSheet.getStringKey("$label_excel_sheet_monthly_flow_chart_strech_uniform")+""+sat.getKm() +" "+localeSheet.getStringKey("$label_excel_sheet_monthly_flow_chart_strech_uniform_comp")+""+ sat.getKm() + " ("+localeSheet.getStringKey("$label_excel_sheet_monthly_flow_chart_track")+" "+ direction1 +")");
 				rowGrd1_4.getCell(1).setCellStyle(styleG);

 				Row rowGrd1_5 = sheetGraficoDensidade1.createRow((short) 10);
 				rowGrd1_5.createCell(0).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nse"));
 				rowGrd1_5.getCell(0).setCellStyle(styleG);

 				Row rowGrd1_6 = sheetGraficoDensidade1.createRow((short) 14);
 				rowGrd1_6.createCell(0).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nsd"));
 				rowGrd1_6.getCell(0).setCellStyle(styleG);

 				Row rowGrd1_7 = sheetGraficoDensidade1.createRow((short) 19);
 				rowGrd1_7.createCell(0).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nsc"));
 				rowGrd1_7.getCell(0).setCellStyle(styleG);

 				Row rowGrd1_8 = sheetGraficoDensidade1.createRow((short) 25);
 				rowGrd1_8.createCell(0).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nsb"));
 				rowGrd1_8.getCell(0).setCellStyle(styleG);

 				Row rowGrd1_9 = sheetGraficoDensidade1.createRow((short) 33);
 				rowGrd1_9.createCell(0).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nsa"));
 				rowGrd1_9.getCell(0).setCellStyle(styleG);

 				sheetGraficoDensidade1.addMergedRegion(CellRangeAddress.valueOf("B1:O1"));
 				sheetGraficoDensidade1.addMergedRegion(CellRangeAddress.valueOf("B2:O2"));
 				sheetGraficoDensidade1.addMergedRegion(CellRangeAddress.valueOf("B3:O3"));
 				sheetGraficoDensidade1.addMergedRegion(CellRangeAddress.valueOf("B4:O4"));

 				sheetGraficoDensidade1.addMergedRegion(CellRangeAddress.valueOf("B44:O44"));
 				sheetGraficoDensidade1.addMergedRegion(CellRangeAddress.valueOf("B45:O45"));
 				sheetGraficoDensidade1.addMergedRegion(CellRangeAddress.valueOf("B46:O46"));
 				sheetGraficoDensidade1.addMergedRegion(CellRangeAddress.valueOf("B47:O47"));

 				if (gd < 6) {
 					// Mesclar c�lulas - Leste

 					sheetGraficoDensidade1.addMergedRegion(CellRangeAddress.valueOf("B38:D38"));
 					sheetGraficoDensidade1.addMergedRegion(CellRangeAddress.valueOf("E38:G38"));
 					sheetGraficoDensidade1.addMergedRegion(CellRangeAddress.valueOf("H38:J38"));
 					sheetGraficoDensidade1.addMergedRegion(CellRangeAddress.valueOf("K38:M38"));
 					sheetGraficoDensidade1.addMergedRegion(CellRangeAddress.valueOf("N38:P38"));

 					// Mesclar C�lulas - Oeste

 					sheetGraficoDensidade1.addMergedRegion(CellRangeAddress.valueOf("B81:D81"));
 					sheetGraficoDensidade1.addMergedRegion(CellRangeAddress.valueOf("E81:G81"));
 					sheetGraficoDensidade1.addMergedRegion(CellRangeAddress.valueOf("H81:J81"));
 					sheetGraficoDensidade1.addMergedRegion(CellRangeAddress.valueOf("K81:M81"));
 					sheetGraficoDensidade1.addMergedRegion(CellRangeAddress.valueOf("N81:P81"));

 				}

 				if (gd == 6 && daysInMonth == 28) {

 					// Mesclar C�lulas - Leste
 					sheetGraficoDensidade1.addMergedRegion(CellRangeAddress.valueOf("B38:D38"));
 					sheetGraficoDensidade1.addMergedRegion(CellRangeAddress.valueOf("E38:G38"));
 					sheetGraficoDensidade1.addMergedRegion(CellRangeAddress.valueOf("H38:J38"));

 					// Mesclar C�lulas - Oeste					
 					sheetGraficoDensidade1.addMergedRegion(CellRangeAddress.valueOf("B81:D81"));
 					sheetGraficoDensidade1.addMergedRegion(CellRangeAddress.valueOf("E81:G81"));
 					sheetGraficoDensidade1.addMergedRegion(CellRangeAddress.valueOf("H81:J81"));
 				}

 				if (gd == 6 && daysInMonth == 29) {

 					// Mesclar C�lulas - Leste
 					sheetGraficoDensidade1.addMergedRegion(CellRangeAddress.valueOf("B38:D38"));
 					sheetGraficoDensidade1.addMergedRegion(CellRangeAddress.valueOf("E38:G38"));
 					sheetGraficoDensidade1.addMergedRegion(CellRangeAddress.valueOf("H38:J38"));
 					sheetGraficoDensidade1.addMergedRegion(CellRangeAddress.valueOf("K38:M38"));

 					// Mesclar C�lulas - Oeste
 					sheetGraficoDensidade1.addMergedRegion(CellRangeAddress.valueOf("B81:D81"));
 					sheetGraficoDensidade1.addMergedRegion(CellRangeAddress.valueOf("E81:G81"));
 					sheetGraficoDensidade1.addMergedRegion(CellRangeAddress.valueOf("H81:J81"));
 					sheetGraficoDensidade1.addMergedRegion(CellRangeAddress.valueOf("K81:M81"));

 				}

 				if (gd == 6 && daysInMonth == 30) {

 					// Mesclar C�lulas - Leste
 					sheetGraficoDensidade1.addMergedRegion(CellRangeAddress.valueOf("B38:D38"));
 					sheetGraficoDensidade1.addMergedRegion(CellRangeAddress.valueOf("E38:G38"));
 					sheetGraficoDensidade1.addMergedRegion(CellRangeAddress.valueOf("H38:J38"));
 					sheetGraficoDensidade1.addMergedRegion(CellRangeAddress.valueOf("K38:M38"));
 					sheetGraficoDensidade1.addMergedRegion(CellRangeAddress.valueOf("N38:P38"));

 					// Mesclar C�lulas - Oeste
 					sheetGraficoDensidade1.addMergedRegion(CellRangeAddress.valueOf("B81:D81"));
 					sheetGraficoDensidade1.addMergedRegion(CellRangeAddress.valueOf("E81:G81"));
 					sheetGraficoDensidade1.addMergedRegion(CellRangeAddress.valueOf("H81:J81"));
 					sheetGraficoDensidade1.addMergedRegion(CellRangeAddress.valueOf("K81:M81"));
 					// sheetGraficoDensidade1.addMergedRegion(CellRangeAddress.valueOf("N81:M81"));

 				}

 				if (gd == 6 && daysInMonth == 31) {

 					// Mesclar C�lulas - Leste
 					sheetGraficoDensidade1.addMergedRegion(CellRangeAddress.valueOf("B38:D38"));
 					sheetGraficoDensidade1.addMergedRegion(CellRangeAddress.valueOf("E38:G38"));
 					sheetGraficoDensidade1.addMergedRegion(CellRangeAddress.valueOf("H38:J38"));
 					sheetGraficoDensidade1.addMergedRegion(CellRangeAddress.valueOf("K38:M38"));
 					sheetGraficoDensidade1.addMergedRegion(CellRangeAddress.valueOf("N38:P38"));
 					sheetGraficoDensidade1.addMergedRegion(CellRangeAddress.valueOf("Q38:S38"));

 					// Mesclar C�lulas - Oeste
 					sheetGraficoDensidade1.addMergedRegion(CellRangeAddress.valueOf("B81:D81"));
 					sheetGraficoDensidade1.addMergedRegion(CellRangeAddress.valueOf("E81:G81"));
 					sheetGraficoDensidade1.addMergedRegion(CellRangeAddress.valueOf("H81:J81"));
 					sheetGraficoDensidade1.addMergedRegion(CellRangeAddress.valueOf("K81:M81"));
 					sheetGraficoDensidade1.addMergedRegion(CellRangeAddress.valueOf("N81:P81"));
 					sheetGraficoDensidade1.addMergedRegion(CellRangeAddress.valueOf("Q81:S81"));

 				}

 				sheetGraficoDensidade1.addMergedRegion(CellRangeAddress.valueOf("G40:J40"));
 				sheetGraficoDensidade1.addMergedRegion(CellRangeAddress.valueOf("K40:L40"));
 				sheetGraficoDensidade1.addMergedRegion(CellRangeAddress.valueOf("G83:J83"));
 				sheetGraficoDensidade1.addMergedRegion(CellRangeAddress.valueOf("K83:L83"));

 				/* Gr�fico - Leste */

 				@SuppressWarnings("rawtypes")
 				Drawing drawing2 = sheetGraficoDensidade1.createDrawingPatriarch();
 				ClientAnchor anchor2 = null;
 				anchor2 = drawing2.createAnchor(0, 0, 0, 0, 1, 6, 16, 35);

 				Chart chart2 = drawing2.createChart(anchor2);
 				ChartLegend legend2 = chart2.getOrCreateLegend();
 				legend2.setPosition(LegendPosition.RIGHT);

 				LineChartData dados2 = chart2.getChartDataFactory().createLineChartData();

 				ChartAxis bottomAxis2 = chart2.getChartAxisFactory().createCategoryAxis(AxisPosition.BOTTOM);
 				ValueAxis leftAxis2 = chart2.getChartAxisFactory().createValueAxis(AxisPosition.LEFT);
 				leftAxis2.setCrosses(AxisCrosses.AUTO_ZERO);

 				// Testar outros dias
 				if (gd == 6 && daysInMonth == 28)
 					gdFim -= 48;
 				if (gd == 6 && daysInMonth == 29)
 					gdFim -= 24;
 				if (gd == 6 && daysInMonth == 30)
 					gdFim += 0;
 				if (gd == 6 && daysInMonth == 31)
 					gdFim += 24;

 				ChartDataSource<Number> xst = DataSources.fromNumericCellRange(sheetGraficoDensidade,
 						new CellRangeAddress(gdIni, gdFim, 2, 2));
 				ChartDataSource<Number> yst = DataSources.fromNumericCellRange(sheetGraficoDensidade,
 						new CellRangeAddress(gdIni, gdFim, 3, 3));
 				ChartDataSource<Number> yst1 = DataSources.fromNumericCellRange(sheetGraficoDensidade,
 						new CellRangeAddress(gdIni, gdFim, 5, 5));
 				ChartDataSource<Number> yst2 = DataSources.fromNumericCellRange(sheetGraficoDensidade,
 						new CellRangeAddress(gdIni, gdFim, 6, 6));
 				ChartDataSource<Number> yst3 = DataSources.fromNumericCellRange(sheetGraficoDensidade,
 						new CellRangeAddress(gdIni, gdFim, 7, 7));
 				ChartDataSource<Number> yst4 = DataSources.fromNumericCellRange(sheetGraficoDensidade,
 						new CellRangeAddress(gdIni, gdFim, 8, 8));
 				ChartDataSource<Number> yst5 = DataSources.fromNumericCellRange(sheetGraficoDensidade,
 						new CellRangeAddress(gdIni, gdFim, 9, 9));

 				LineChartSeries seriesLD = dados2.addSeries(xst, yst);
 				seriesLD.setTitle(direction1);
 				LineChartSeries seriesL1D = dados2.addSeries(xst, yst1);
 				seriesL1D.setTitle(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nsa"));
 				LineChartSeries seriesL2D = dados2.addSeries(xst, yst2);
 				seriesL2D.setTitle(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nsb"));
 				LineChartSeries seriesL3D = dados2.addSeries(xst, yst3);
 				seriesL3D.setTitle(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nsc"));
 				LineChartSeries seriesL4D = dados2.addSeries(xst, yst4);
 				seriesL4D.setTitle(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nsd"));
 				LineChartSeries seriesL5D = dados2.addSeries(xst, yst5);
 				seriesL5D.setTitle(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nse"));

 				chart2.plot(dados2, bottomAxis2, leftAxis2); 

 				Row rowGrd1_10 = sheetGraficoDensidade1.createRow((short) 37);
 				
 				if (gd < 6) { // DAYS LABEL FOR CHART

 					rowGrd1_10.createCell(1).setCellValue(day_mth);
 					rowGrd1_10.createCell(4).setCellValue((day_mth + 1));
 					rowGrd1_10.createCell(7).setCellValue((day_mth + 2));
 					rowGrd1_10.createCell(10).setCellValue((day_mth + 3));

 					// Estilo
 					rowGrd1_10.getCell(1).setCellStyle(styleDG);
 					rowGrd1_10.getCell(4).setCellStyle(styleDG);
 					rowGrd1_10.getCell(7).setCellStyle(styleDG);
 					rowGrd1_10.getCell(10).setCellStyle(styleDG);

 					if (dayx_mes == 6) {
 						rowGrd1_10.createCell(13).setCellValue(10);
 						rowGrd1_10.getCell(13).setCellStyle(styleDG);
 					}

 					else {
 						rowGrd1_10.createCell(13).setCellValue((dayx_mes + 4));
 						rowGrd1_10.getCell(13).setCellStyle(styleDG);
 					}
 				}

 				if (gd == 6 && daysInMonth == 28) {
 					rowGrd1_10.createCell(1).setCellValue(dayx_mes);
 					rowGrd1_10.createCell(4).setCellValue((dayx_mes + 1));
 					rowGrd1_10.createCell(7).setCellValue((dayx_mes + 2));
 					rowGrd1_10.createCell(10).setCellValue("");
 					rowGrd1_10.createCell(13).setCellValue("");

 					// Estilo
 					rowGrd1_10.getCell(1).setCellStyle(styleDG);
 					rowGrd1_10.getCell(4).setCellStyle(styleDG);
 					rowGrd1_10.getCell(7).setCellStyle(styleDG);

 					// Coordenadas do ultimo gr�fico
 					CTMarker chartEndCoords2 = CTMarker.Factory.newInstance();
 					chartEndCoords2.setCol(10);
 					chartEndCoords2.setColOff(0);
 					chartEndCoords2.setRow(35);
 					chartEndCoords2.setRowOff(0);
 					((XSSFDrawing) drawing2).getCTDrawing().getTwoCellAnchorArray(0).setTo(chartEndCoords2);

 				}

 				if (gd == 6 && daysInMonth == 29) {
 					rowGrd1_10.createCell(1).setCellValue(day_mth);
 					rowGrd1_10.createCell(4).setCellValue((day_mth + 1));
 					rowGrd1_10.createCell(7).setCellValue((day_mth + 2));
 					rowGrd1_10.createCell(10).setCellValue((day_mth + 3));
 					rowGrd1_10.createCell(13).setCellValue("");

 					// Estilo
 					rowGrd1_10.getCell(1).setCellStyle(styleDG);
 					rowGrd1_10.getCell(4).setCellStyle(styleDG);
 					rowGrd1_10.getCell(7).setCellStyle(styleDG);
 					rowGrd1_10.getCell(10).setCellStyle(styleDG);

 					// Coordenadas do ultimo gr�fico
 					CTMarker chartEndCoords2 = CTMarker.Factory.newInstance();
 					chartEndCoords2.setCol(13);
 					chartEndCoords2.setColOff(0);
 					chartEndCoords2.setRow(35);
 					chartEndCoords2.setRowOff(0);
 					((XSSFDrawing) drawing2).getCTDrawing().getTwoCellAnchorArray(0).setTo(chartEndCoords2);
 				}

 				if (gd == 6 && daysInMonth == 30) {
 					rowGrd1_10.createCell(1).setCellValue(day_mth);
 					rowGrd1_10.createCell(4).setCellValue((day_mth + 1));
 					rowGrd1_10.createCell(7).setCellValue((day_mth + 2));
 					rowGrd1_10.createCell(10).setCellValue((day_mth + 3));
 					rowGrd1_10.createCell(13).setCellValue((day_mth + 4));

 					// Estilo
 					rowGrd1_10.getCell(1).setCellStyle(styleDG);
 					rowGrd1_10.getCell(4).setCellStyle(styleDG);
 					rowGrd1_10.getCell(7).setCellStyle(styleDG);
 					rowGrd1_10.getCell(10).setCellStyle(styleDG);
 					rowGrd1_10.getCell(13).setCellStyle(styleDG);

 					// Coordenadas do ultimo gr�fico
 					CTMarker chartEndCoords2 = CTMarker.Factory.newInstance();
 					chartEndCoords2.setCol(16);
 					chartEndCoords2.setColOff(0);
 					chartEndCoords2.setRow(35);
 					chartEndCoords2.setRowOff(0);
 					((XSSFDrawing) drawing2).getCTDrawing().getTwoCellAnchorArray(0).setTo(chartEndCoords2);
 				}

 				if (gd == 6 && daysInMonth == 31) {
 					rowGrd1_10.createCell(1).setCellValue(day_mth);
 					rowGrd1_10.createCell(4).setCellValue((day_mth + 1));
 					rowGrd1_10.createCell(7).setCellValue((day_mth + 2));
 					rowGrd1_10.createCell(10).setCellValue((day_mth + 3));
 					rowGrd1_10.createCell(13).setCellValue((day_mth + 4));
 					rowGrd1_10.createCell(16).setCellValue((day_mth + 5));

 					// Estilo
 					rowGrd1_10.getCell(1).setCellStyle(styleDG);
 					rowGrd1_10.getCell(4).setCellStyle(styleDG);
 					rowGrd1_10.getCell(7).setCellStyle(styleDG);
 					rowGrd1_10.getCell(10).setCellStyle(styleDG);
 					rowGrd1_10.getCell(13).setCellStyle(styleDG);
 					rowGrd1_10.getCell(16).setCellStyle(styleDG);

 					// Coordenadas do �ltimo gr�fico
 					CTMarker chartEndCoords2 = CTMarker.Factory.newInstance();
 					chartEndCoords2.setCol(19);
 					chartEndCoords2.setColOff(0);
 					chartEndCoords2.setRow(35);
 					chartEndCoords2.setRowOff(0);
 					((XSSFDrawing) drawing2).getCTDrawing().getTwoCellAnchorArray(0).setTo(chartEndCoords2);

 				}

 				day_mth = day_mth + 5;

 				if (gd == 6)
 					day_mth = 1;

 				Row rowGrd1_11 = sheetGraficoDensidade1.createRow((short) 39);
 				rowGrd1_11.createCell(5).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_chart_period"));
 				rowGrd1_11.getCell(5).setCellStyle(styleDG);

 				String mtht_ = "";
 				String ddt = "";
 				String dft = "";

 				if (day_ini <= 9)
 					ddt = "0";
 				else
 					ddt = "";
 				if (day_fim <= 9)
 					dft = "0";
 				else
 					dft = "";
 				if (mes < 10)
 					mtht_ = "0";
 				else
 					mtht_ = "";

 				if (gd < 6) {
 					rowGrd1_11.createCell(6).setCellValue("" + ddt + "" + day_ini + "/" + mtht_ + "" + mes + "/" + ano
 							+ " a " + dft + "" + day_fim + "/" + mtht_ + "" + mes + "/" + ano + "");
 					rowGrd1_11.getCell(6).setCellStyle(styleDG);
 				}

 				if (gd == 6 && daysInMonth == 28) {
 					rowGrd1_11.createCell(6).setCellValue("" + ddt + "" + day_ini + "/" + mtht_ + "" + mes + "/" + ano
 							+ " a " + dft + "" + daysInMonth + "/" + mtht_ + "" + mes + "/" + ano + "");
 					rowGrd1_11.getCell(6).setCellStyle(styleDG);
 				}

 				if (gd == 6 && daysInMonth == 29) {
 					rowGrd1_11.createCell(6).setCellValue("" + ddt + "" + day_ini + "/" + mtht_ + "" + mes + "/" + ano
 							+ " a " + dft + "" + daysInMonth + "/" + mtht_ + "" + mes + "/" + ano + "");
 					rowGrd1_11.getCell(6).setCellStyle(styleDG);
 				}

 				if (gd == 6 && daysInMonth == 30) {
 					rowGrd1_11.createCell(6).setCellValue("" + ddt + "" + day_ini + "/" + mtht_ + "" + mes + "/" + ano
 							+ " a " + dft + "" + daysInMonth + "/" + mtht_ + "" + mes + "/" + ano + "");
 					rowGrd1_11.getCell(6).setCellStyle(styleDG);
 				}

 				if (gd == 6 && daysInMonth == 31) {
 					rowGrd1_11.createCell(6).setCellValue("" + ddt + "" + day_ini + "/" + mtht_ + "" + mes + "/" + ano
 							+ " a " + dft + "" + daysInMonth + "/" + mtht_ + "" + mes + "/" + ano + "");
 					rowGrd1_11.getCell(6).setCellStyle(styleDG);
 				}

 				rowGrd1_11.createCell(10).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_chart_24_hours"));
 				rowGrd1_11.getCell(10).setCellStyle(styleDG);

 				day_ini = day_ini + 5;
 				day_fim = day_fim + 5;

 				if (gd == 6) {
 					day_ini = 1;
 					day_fim = 5;
 				}

 				Row rowGrd1_12 = sheetGraficoDensidade1.createRow((short) 43);
 				rowGrd1_12.createCell(1).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_company")+": "+RoadConcessionaire.roadConcessionaire+"");
 				rowGrd1_12.getCell(1).setCellStyle(styleG);

 				Row rowGrd1_13 = sheetGraficoDensidade1.createRow((short) 44);
 				rowGrd1_13.createCell(1).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_lot_tab")+": "+ano);
 				rowGrd1_13.getCell(1).setCellStyle(styleG);

 				Row rowGrd1_14 = sheetGraficoDensidade1.createRow((short) 45); 
 				rowGrd1_14.createCell(1).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_chart_rate_of_flow_lane"));
 				rowGrd1_14.getCell(1).setCellStyle(styleG);

 				Row rowGrd1_15 = sheetGraficoDensidade1.createRow((short) 46);
 				rowGrd1_15.createCell(1).setCellValue(
 						sat.getEstrada() +" - "+localeSheet.getStringKey("$label_excel_sheet_monthly_flow_chart_strech_uniform")+""+sat.getKm() +" "+localeSheet.getStringKey("$label_excel_sheet_monthly_flow_chart_strech_uniform_comp")+""+ sat.getKm() + " ("+localeSheet.getStringKey("$label_excel_sheet_monthly_flow_chart_track")+" "+ direction2 +")");
 				rowGrd1_15.getCell(1).setCellStyle(styleG);

 				Row rowGrd1_16 = sheetGraficoDensidade1.createRow((short) 53);
 				rowGrd1_16.createCell(0).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nse"));
 				rowGrd1_16.getCell(0).setCellStyle(styleG);

 				Row rowGrd1_17 = sheetGraficoDensidade1.createRow((short) 57);
 				rowGrd1_17.createCell(0).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nsd"));
 				rowGrd1_17.getCell(0).setCellStyle(styleG);

 				Row rowGrd1_18 = sheetGraficoDensidade1.createRow((short) 62);
 				rowGrd1_18.createCell(0).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nsc"));
 				rowGrd1_18.getCell(0).setCellStyle(styleG);

 				Row rowGrd1_19 = sheetGraficoDensidade1.createRow((short) 68);
 				rowGrd1_19.createCell(0).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nsb"));
 				rowGrd1_19.getCell(0).setCellStyle(styleG);

 				Row rowGrd1_20 = sheetGraficoDensidade1.createRow((short) 76);
 				rowGrd1_20.createCell(0).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nsa"));
 				rowGrd1_20.getCell(0).setCellStyle(styleG);

 				// Gr�fico Oeste

 				@SuppressWarnings("rawtypes")
 				Drawing drawing3 = sheetGraficoDensidade1.createDrawingPatriarch();
 				ClientAnchor anchor3 = drawing3.createAnchor(0, 0, 0, 0, 1, 49, 16, 79);

 				Chart chart3 = drawing3.createChart(anchor3);
 				ChartLegend legend3 = chart3.getOrCreateLegend();
 				legend3.setPosition(LegendPosition.RIGHT);

 				LineChartData dados3 = chart3.getChartDataFactory().createLineChartData();

 				ChartAxis bottomAxis3 = chart3.getChartAxisFactory().createCategoryAxis(AxisPosition.BOTTOM);
 				ValueAxis leftAxis3 = chart3.getChartAxisFactory().createValueAxis(AxisPosition.LEFT);
 				leftAxis3.setCrosses(AxisCrosses.AUTO_ZERO);

 				ChartDataSource<Number> xsto = DataSources.fromNumericCellRange(sheetGraficoDensidade,
 						new CellRangeAddress(gdIni, gdFim, 2, 2));
 				ChartDataSource<Number> ysto = DataSources.fromNumericCellRange(sheetGraficoDensidade,
 						new CellRangeAddress(gdIni, gdFim, 4, 4));
 				ChartDataSource<Number> yst1o = DataSources.fromNumericCellRange(sheetGraficoDensidade,
 						new CellRangeAddress(gdIni, gdFim, 10, 10));
 				ChartDataSource<Number> yst2o = DataSources.fromNumericCellRange(sheetGraficoDensidade,
 						new CellRangeAddress(gdIni, gdFim, 11, 11));
 				ChartDataSource<Number> yst3o = DataSources.fromNumericCellRange(sheetGraficoDensidade,
 						new CellRangeAddress(gdIni, gdFim, 12, 12));
 				ChartDataSource<Number> yst4o = DataSources.fromNumericCellRange(sheetGraficoDensidade,
 						new CellRangeAddress(gdIni, gdFim, 13, 13));
 				ChartDataSource<Number> yst5o = DataSources.fromNumericCellRange(sheetGraficoDensidade,
 						new CellRangeAddress(gdIni, gdFim, 14, 14));

 				LineChartSeries seriesO1D = dados3.addSeries(xsto, ysto);
 				seriesO1D.setTitle(direction2);
 				LineChartSeries seriesO2D = dados3.addSeries(xsto, yst1o);
 				seriesO2D.setTitle(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nsa"));
 				LineChartSeries seriesO3D = dados3.addSeries(xsto, yst2o);
 				seriesO3D.setTitle(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nsb"));
 				LineChartSeries seriesO4D = dados3.addSeries(xsto, yst3o);
 				seriesO4D.setTitle(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nsc"));
 				LineChartSeries seriesO5D = dados3.addSeries(xsto, yst4o);
 				seriesO5D.setTitle(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nsd"));
 				LineChartSeries seriesO6D = dados3.addSeries(xsto, yst5o);
 				seriesO6D.setTitle(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_nse"));

 				chart3.plot(dados3, bottomAxis3, leftAxis3);

 				Row rowGrd1_21 = sheetGraficoDensidade1.createRow((short) 80);

 				if (gd < 6) { // CHART DAY LABEL
 				
 					rowGrd1_21.createCell(1).setCellValue(dayx_mes);
 					rowGrd1_21.createCell(4).setCellValue((dayx_mes + 1));
 					rowGrd1_21.createCell(7).setCellValue((dayx_mes + 2));
 					rowGrd1_21.createCell(10).setCellValue( (dayx_mes + 3));

 					// Estilo
 					rowGrd1_21.getCell(1).setCellStyle(styleDG);
 					rowGrd1_21.getCell(4).setCellStyle(styleDG);
 					rowGrd1_21.getCell(7).setCellStyle(styleDG);
 					rowGrd1_21.getCell(10).setCellStyle(styleDG);

 					if (dayx_mes == 6) {
 						rowGrd1_21.createCell(13).setCellValue(10);
 						rowGrd1_21.getCell(13).setCellStyle(styleDG);
 					}

 					else {
 						rowGrd1_21.createCell(13).setCellValue((dayx_mes + 4));
 						rowGrd1_21.getCell(13).setCellStyle(styleDG);
 					}
 				}

 				if (gd == 6 && daysInMonth == 28) {
 					rowGrd1_21.createCell(1).setCellValue(dayx_mes);
 					rowGrd1_21.createCell(4).setCellValue((dayx_mes + 1));
 					rowGrd1_21.createCell(7).setCellValue((dayx_mes + 2));
 					rowGrd1_21.createCell(10).setCellValue("");
 					rowGrd1_21.createCell(13).setCellValue("");

 					// Estilo
 					rowGrd1_21.getCell(1).setCellStyle(styleDG);
 					rowGrd1_21.getCell(4).setCellStyle(styleDG);
 					rowGrd1_21.getCell(7).setCellStyle(styleDG);
 					rowGrd1_21.getCell(10).setCellStyle(styleDG);
 					rowGrd1_21.getCell(13).setCellStyle(styleDG);

 					// Coordenadas do ultimo gr�fico
 					CTMarker chartEndCoords3 = CTMarker.Factory.newInstance();
 					chartEndCoords3.setCol(10);
 					chartEndCoords3.setColOff(0);
 					chartEndCoords3.setRow(79);
 					chartEndCoords3.setRowOff(0);
 					((XSSFDrawing) drawing3).getCTDrawing().getTwoCellAnchorArray(1).setTo(chartEndCoords3);

 				}

 				if (gd == 6 && daysInMonth == 29) {
 					rowGrd1_21.createCell(1).setCellValue(dayx_mes);
 					rowGrd1_21.createCell(4).setCellValue((dayx_mes + 1));
 					rowGrd1_21.createCell(7).setCellValue((dayx_mes + 2));
 					rowGrd1_21.createCell(10).setCellValue((dayx_mes + 3));
 					rowGrd1_21.createCell(13).setCellValue("");

 					// Estilo
 					rowGrd1_21.getCell(1).setCellStyle(styleDG);
 					rowGrd1_21.getCell(4).setCellStyle(styleDG);
 					rowGrd1_21.getCell(7).setCellStyle(styleDG);
 					rowGrd1_21.getCell(10).setCellStyle(styleDG);
 					rowGrd1_21.getCell(13).setCellStyle(styleDG);

 					// Coordenadas do ultimo gr�fico
 					CTMarker chartEndCoords3 = CTMarker.Factory.newInstance();
 					chartEndCoords3.setCol(13);
 					chartEndCoords3.setColOff(0);
 					chartEndCoords3.setRow(79);
 					chartEndCoords3.setRowOff(0);
 					((XSSFDrawing) drawing3).getCTDrawing().getTwoCellAnchorArray(1).setTo(chartEndCoords3);

 				}

 				if (gd == 6 && daysInMonth == 30) {
 					rowGrd1_21.createCell(1).setCellValue(dayx_mes);
 					rowGrd1_21.createCell(4).setCellValue((dayx_mes + 1));
 					rowGrd1_21.createCell(7).setCellValue((dayx_mes + 2));
 					rowGrd1_21.createCell(10).setCellValue((dayx_mes + 3));
 					rowGrd1_21.createCell(13).setCellValue((dayx_mes + 4));

 					// Estilo
 					rowGrd1_21.getCell(1).setCellStyle(styleDG);
 					rowGrd1_21.getCell(4).setCellStyle(styleDG);
 					rowGrd1_21.getCell(7).setCellStyle(styleDG);
 					rowGrd1_21.getCell(10).setCellStyle(styleDG);
 					rowGrd1_21.getCell(13).setCellStyle(styleDG);

 					// Coordenadas do ultimo gr�fico
 					CTMarker chartEndCoords3 = CTMarker.Factory.newInstance();
 					chartEndCoords3.setCol(16);
 					chartEndCoords3.setColOff(0);
 					chartEndCoords3.setRow(79);
 					chartEndCoords3.setRowOff(0);
 					((XSSFDrawing) drawing3).getCTDrawing().getTwoCellAnchorArray(1).setTo(chartEndCoords3);

 				}

 				if (gd == 6 && daysInMonth == 31) {
 					rowGrd1_21.createCell(1).setCellValue(dayx_mes);
 					rowGrd1_21.createCell(4).setCellValue((dayx_mes + 1));
 					rowGrd1_21.createCell(7).setCellValue((dayx_mes + 2));
 					rowGrd1_21.createCell(10).setCellValue((dayx_mes + 3));
 					rowGrd1_21.createCell(13).setCellValue((dayx_mes + 4));
 					rowGrd1_21.createCell(16).setCellValue((dayx_mes + 5));

 					// Estilo
 					rowGrd1_21.getCell(1).setCellStyle(styleDG);
 					rowGrd1_21.getCell(4).setCellStyle(styleDG);
 					rowGrd1_21.getCell(7).setCellStyle(styleDG);
 					rowGrd1_21.getCell(10).setCellStyle(styleDG);
 					rowGrd1_21.getCell(13).setCellStyle(styleDG);
 					rowGrd1_21.getCell(16).setCellStyle(styleDG);

 					// Coordenadas do �ltimo gr�fico
 					CTMarker chartEndCoords3 = CTMarker.Factory.newInstance();
 					chartEndCoords3.setCol(19);
 					chartEndCoords3.setColOff(0);
 					chartEndCoords3.setRow(79);
 					chartEndCoords3.setRowOff(0);
 					((XSSFDrawing) drawing3).getCTDrawing().getTwoCellAnchorArray(1).setTo(chartEndCoords3);

 				}

 				dayx_mes = dayx_mes + 5;

 				if (gd == 6)
 					dayx_mes = 1;

 				Row rowGrd1_22 = sheetGraficoDensidade1.createRow((short) 82);
 				rowGrd1_22.createCell(5).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_chart_period"));
 				rowGrd1_22.getCell(5).setCellStyle(styleG);

 				String mthxx_ = "";
 				String ddxx = "";
 				String dfxx = "";

 				if (dayx_ini <= 9)
 					ddxx = "0";
 				else
 					ddxx = "";
 				if (dayx_fim <= 9)
 					dfxx = "0";
 				else
 					dfxx = "";
 				if (mes < 10)
 					mthxx_ = "0";
 				else
 					mthxx_ = "";

 				if (gd < 6) {
 					rowGrd1_22.createCell(6).setCellValue("" + ddxx + "" + dayx_ini + "/" + mthxx_ + "" + mes + "/"
 							+ ano + "  a  " + dfxx + "" + dayx_fim + "/" + mthxx_ + "" + mes + "/" + ano + "");
 					rowGrd1_22.getCell(6).setCellStyle(styleG);
 				}

 				if (gd == 6 && daysInMonth == 28) {
 					rowGrd1_22.createCell(6).setCellValue("" + ddxx + "" + dayx_ini + "/" + mthxx_ + "" + mes + "/"
 							+ ano + "  a  " + dfxx + "" + daysInMonth + "/" + mthxx_ + "" + mes + "/" + ano + "");
 					rowGrd1_22.getCell(6).setCellStyle(styleG);
 				}

 				if (gd == 6 && daysInMonth == 29) {
 					rowGrd1_22.createCell(6).setCellValue("" + ddxx + "" + dayx_ini + "/" + mthxx_ + "" + mes + "/"
 							+ ano + "  a  " + dfxx + "" + daysInMonth + "/" + mthxx_ + "" + mes + "/" + ano + "");
 					rowGrd1_22.getCell(6).setCellStyle(styleG);
 				}

 				if (gd == 6 && daysInMonth == 30) {
 					rowGrd1_22.createCell(6).setCellValue("" + ddxx + "" + dayx_ini + "/" + mthxx_ + "" + mes + "/"
 							+ ano + "  a  " + dfxx + "" + daysInMonth + "/" + mthxx_ + "" + mes + "/" + ano + "");
 					rowGrd1_22.getCell(6).setCellStyle(styleG);
 				}

 				if (gd == 6 && daysInMonth == 31) {
 					rowGrd1_22.createCell(6).setCellValue("" + ddxx + "" + dayx_ini + "/" + mthxx_ + "" + mes + "/"
 							+ ano + "  a  " + dfxx + "" + daysInMonth + "/" + mthxx_ + "" + mes + "/" + ano + "");
 					rowGrd1_22.getCell(6).setCellStyle(styleG);
 				}

 				rowGrd1_22.createCell(10).setCellValue(localeSheet.getStringKey("$label_excel_sheet_monthly_flow_chart_24_hours"));
 				rowGrd1_22.getCell(10).setCellStyle(styleG);

 				dayx_ini = dayx_ini + 5;
 				dayx_fim = dayx_fim + 5;

 				if (gd == 6) {
 					dayx_ini = 1;
 					dayx_fim = 5;
 				}

 				if (gd < 6) {
 					gdIni = gdIni + 120;
 					gdFim = gdFim + 120;
 				}
 			}

 			// ------ Gr�fico Densidade - END ---------- //		    	 	

 		} catch (Exception ex) {
 			ex.printStackTrace();
 		}
 	 }	
	
  }
