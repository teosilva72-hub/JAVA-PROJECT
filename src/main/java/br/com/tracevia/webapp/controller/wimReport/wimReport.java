package br.com.tracevia.webapp.controller.wimReport;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;
import br.com.tracevia.webapp.controller.occ.OccurrencesBean;
import br.com.tracevia.webapp.dao.wim.WIMDAO;
import br.com.tracevia.webapp.model.global.ColumnModel;
import br.com.tracevia.webapp.model.meteo.mto.MtoReports.Builder;
import br.com.tracevia.webapp.model.occ.OccurrencesData;
import br.com.tracevia.webapp.model.wim.WimData;

@ManagedBean(name="wimReport")
@ViewScoped
public class wimReport {


	private WIMDAO dao = new WIMDAO();
	private WimData data = new WimData();
	private WimData date;
	
	public WimData getDate() {
		return date;
	}
	public void setDate(WimData date) {
		this.date = date;
	}
	public WimData getData() {
		return data;
	}
	public void setData(WimData data) {
		this.data = data;
	}
	private List<SelectItem> minutos, horas, classes;

	public List<SelectItem> getMinutos() {
		return minutos;
	}
	public List<SelectItem> getHoras() {
		return horas;
	}
	public void setHoras(List<SelectItem> horas) {
		this.horas = horas;
	}
	public void setMinutos(List<SelectItem> minutos) {
		this.minutos = minutos;
	}

	public List<SelectItem> getClasses() {
		return classes;
	}
	public void setClasses(List<SelectItem> classes) {
		this.classes = classes;
	}
	private String dateInitial, dateFinal, minuteInitial,
	minuteFinal, classs, hourInitial, hourFinal;
	
	public String getHourInitial() {
		return hourInitial;
	}
	public void setHourInitial(String hourInitial) {
		this.hourInitial = hourInitial;
	}
	public String getHourFinal() {
		return hourFinal;
	}
	public void setHourFinal(String hourFinal) {
		this.hourFinal = hourFinal;
	}
	public String getDateInitial() {
		return dateInitial;
	}
	public void setDateInitial(String dateInitial) {
		this.dateInitial = dateInitial;
	}
	public String getDateFinal() {
		return dateFinal;
	}
	public void setDateFinal(String dateFinal) {
		this.dateFinal = dateFinal;
	}
	public String getMinuteInitial() {
		return minuteInitial;
	}
	public void setMinuteInitial(String minuteInitial) {
		this.minuteInitial = minuteInitial;
	}
	public String getMinuteFinal() {
		return minuteFinal;
	}
	public void setMinuteFinal(String minuteFinal) {
		this.minuteFinal = minuteFinal;
	}
	public String getClasss() {
		return classs;
	}
	public void setClasss(String classs) {
		this.classs = classs;
	}
	private List<WimData> list;
	
	public List<WimData> getList() {
		return list;
	}
	public void setList(List<WimData> list) {
		this.list = list;
	}
	private List<Builder> resultList;	
	private List<ColumnModel> columns;
	
	public List<ColumnModel> getColumns() {
		return columns;
	}
	public void setColumns(List<ColumnModel> columns) {
		this.columns = columns;
	}
	public List<Builder> getResultList() {
		return resultList;
	}
	public void setResultList(List<Builder> resultList) {
		this.resultList = resultList;
	}
	private String [] teste;
	
	public String[] getTeste() {
		return teste;
	}
	public void setTeste(String[] teste) {
		this.teste = teste;
	}
	private int rowkey;
	private boolean selectedRow;
	
	public boolean isSelectedRow() {
		return selectedRow;
	}
	public void setSelectedRow(boolean selectedRow) {
		this.selectedRow = selectedRow;
	}
	public int getRowkey() {
		return rowkey;
	}
	public void setRowkey(int rowkey) {
		this.rowkey = rowkey;
	}
	private RequestContext request = RequestContext.getCurrentInstance();
	private String[] dstAxes, weight;
	
	public String[] getDstAxes() {
		return dstAxes;
	}
	public void setDstAxes(String[] dstAxes) {
		this.dstAxes = dstAxes;
	}
	public String[] getWeight() {
		return weight;
	}
	public void setWeight(String[] weight) {
		this.weight = weight;
	}
	private String nEvent, dateHour, category, nAxes, speed, pbtTotal, size;
	
	public RequestContext getRequest() {
		return request;
	}
	public void setRequest(RequestContext request) {
		this.request = request;
	}
	public String getnEvent() {
		return nEvent;
	}
	public void setnEvent(String nEvent) {
		this.nEvent = nEvent;
	}
	public String getDateHour() {
		return dateHour;
	}
	public void setDateHour(String dateHour) {
		this.dateHour = dateHour;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getnAxes() {
		return nAxes;
	}
	public void setnAxes(String nAxes) {
		this.nAxes = nAxes;
	}
	public String getSpeed() {
		return speed;
	}
	public void setSpeed(String speed) {
		this.speed = speed;
	}
	public String getPbtTotal() {
		return pbtTotal;
	}
	public void setPbtTotal(String pbtTotal) {
		this.pbtTotal = pbtTotal;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	private String rateTxt;
	public String getRateTxt() {
		return rateTxt;
	}
	public void setRateTxt(String rateTxt) {
		this.rateTxt = rateTxt;
	}
	public int gross;
	public int getGross() {
		return gross;
	}
	public void setGross(int gross) {
		this.gross = gross;
	}
	private String silueta, image1, image2;
	public String getImage1() {
		return image1;
	}
	public void setImage1(String image1) {
		this.image1 = image1;
	}
	public String getImage2() {
		return image2;
	}
	public void setImage2(String image2) {
		this.image2 = image2;
	}
	public String getSilueta() {
		return silueta;
	}
	public void setSilueta(String silueta) {
		this.silueta = silueta;
	}
	private boolean reset, search;
	
	public boolean isReset() {
		return reset;
	}
	public void setReset(boolean reset) {
		this.reset = reset;
	}
	public boolean isSearch() {
		return search;
	}
	public void setSearch(boolean search) {
		this.search = search;
	}
	@PostConstruct
	public void initalize(){
		reset = true;
		date = data;
		date.setSeqN("-");
		date.setData("-");
		date.setClasse("-");
		date.setNumberAxes("-");
		date.setSpeed("-");
		date.setPbtTotal("-");
		image1 = "/teste/tracevia.jpg";
		image2 = "/teste/tracevia.jpg";
		silueta = "/teste/tracevia.jpg";
		minutos = new  ArrayList<SelectItem>();
		for(int m = 0; m < 60; m++){				
			if (m < 10)
				minutos.add(new SelectItem("0"+String.valueOf(m), "0"+String.valueOf(m)));
			else 
				minutos.add(new SelectItem(String.valueOf(m), String.valueOf(m)));
		}
		horas = new  ArrayList<SelectItem>();
		for(int m = 0; m < 24; m++){				
			if (m < 10)
				horas.add(new SelectItem("0"+String.valueOf(m), "0"+String.valueOf(m)));
			else 
				horas.add(new SelectItem(String.valueOf(m), String.valueOf(m)));
		}
		classes = new ArrayList<SelectItem>();
		classes.add(new SelectItem("1", "1"));
		classes.add(new SelectItem("2", "2"));
		classes.add(new SelectItem("3", "3"));
		classes.add(new SelectItem("4", "4"));
		classes.add(new SelectItem("5", "5"));
		classes.add(new SelectItem("6", "6"));
		classes.add(new SelectItem("7", "7"));
		classes.add(new SelectItem("8", "8"));
		classes.add(new SelectItem("9", "9"));
		classes.add(new SelectItem("10", "10"));
		classes.add(new SelectItem("11", "11"));
		classes.add(new SelectItem("2A", "2A"));
		classes.add(new SelectItem("4A", "4A"));
		classes.add(new SelectItem("E9", "E9"));
		classes.add(new SelectItem("10N", "10N"));
		RequestContext.getCurrentInstance().execute("getTr()");
		
		weight = new String[10];
		weight [1] = "";
		weight [2] = "";
		weight [3] = "";
		weight [4] = "";
		weight [5] = "";
		weight [6] = "";
		weight [7] = "";
		weight [8] = "";
		weight [9] = "";
		//distancia
		dstAxes = new String[10];
		
		dstAxes [1] = "";
		dstAxes [2] = "";
		dstAxes [3] = "";
		dstAxes [4] = "";
		dstAxes [5] = "";
		dstAxes [6] = "";
		dstAxes [7] = "";
		dstAxes [8] = "";
		dstAxes [9] = "";
	
	}
	public void search() {
		boolean checked = true;
		String dtInitial, dtFinal, hInitial, hFinal,
		mInitial, mFinal, classe, second;
		dtInitial = getDateInitial();
		dtFinal = getDateFinal();
		hInitial = getHourInitial();
		hFinal = getHourFinal();
		mInitial = getMinuteInitial();
		mFinal = getMinuteFinal();
		second = "00";
		classe = getClasss();
		if(checked == true) {
			//formatting date and time
			String start = dtInitial+" "+hInitial+":"+mInitial+":"+second;
			String end = dtFinal+" "+hFinal+":"+mFinal+":"+second;
			if(start != null && end != null) {
				//pass dice for wim dao
				try {
					list = dao.search(start, end, classe);
					RequestContext.getCurrentInstance().execute("getTr()");
					RequestContext.getCurrentInstance().execute("dataPicker()");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	public void indicator() {
		try {
			date = dao.searchId(rowkey);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RequestContext request = RequestContext.getCurrentInstance();
		String classe = date.getClasse();
		gross = Integer.parseInt(date.getPbtTotal());
		if(classe.equals("1")) {
			if(gross < 6000) {
				rateTxt = "Normal weight " + gross;
				request.execute("sizeNormal();");
			}else if( gross > 6000 && gross < 6450){
				request.execute("sizeAtenttion();");
				rateTxt = "In tolerance " + gross;
			}else {
				request.execute("sizeAtenttion();");
				rateTxt = "In tolerance " + gross;
			}
		}else if(classe.equals("2")){
			if(gross < 10000) {
				request.execute("sizeNormal();");
				rateTxt = "Normal weight " + gross;
			}
		}else if(classe.equals("4")){
			if(gross < 17000) {
				request.execute("sizeNormal();");
				rateTxt = "Normal weight " + gross;
			}else if(gross > 17000 && gross <= 17850){
				request.execute("sizeAtenttion();");
				rateTxt = "In tolerance " + gross;
			}else{
				request.execute("sizeAcima();");
				rateTxt = "overweight " + gross;
			}
		}else if(classe.equals("5")) {
			//essa classe falta definir o peso
			if(gross < 17000) {
				request.execute("sizeNormal();");
				rateTxt = "Normal weight " + gross;
			}else if(gross > 17000 && gross <= 17850){
				request.execute("sizeAtenttion();");
				rateTxt = "In tolerance " + gross;
			}else{
				request.execute("sizeAcima();");
				rateTxt = "overweight " + gross;
			}
		}else if(classe.equals("6")||classe.equals("7")||classe.equals("8")){
			if(gross < 25500) {
				request.execute("sizeNormal();");
				rateTxt = "Normal weight " + gross;
			}else if(gross > 25500 && gross <= 26775){
				request.execute("sizeAtenttion();");
				rateTxt = "In tolerance " + gross;
			}else{
				request.execute("sizeAcima();");
				rateTxt = "overweight " + gross;
			}
		}else if(classe.equals("9")){
			if(gross < 6000) {
				request.execute("sizeNormal();");
				rateTxt = "Normal weight " + gross;
			}else if(gross > 6000 && gross <= 6450){
				request.execute("sizeAtenttion();");
				rateTxt = "In tolerance " + gross;
			}else {
				request.execute("sizeAcima();");
				rateTxt = "overweight " + gross;
			}
			//classe carro
		}else if(classe.equals("10")){
			if(gross < 51000) {
				request.execute("sizeNormal();");
				rateTxt = "Normal weight " + gross;
			}else if(gross > 51000 && gross <= 53500){
				request.execute("sizeAtenttion();");
				rateTxt = "In tolerance " + gross;
			}else{
				request.execute("sizeAcima();");
				rateTxt = "overweight " + gross;
			}
		}else if(classe.equals("11")||classe.equals("E9")){
			if(gross < 68000) {
				request.execute("sizeNormal();");
				rateTxt = "Normal weight " + gross;
			}else if(gross > 68000 && gross <= 71400){
				request.execute("sizeAtenttion();");
				rateTxt = "In tolerance " + gross;
			}else{
				request.execute("sizeAcima();");
				rateTxt = "overweight " + gross;
			}
		}else if(classe.equals("2A")){
			if(gross < 10000) {
				request.execute("sizeNormal();");
				rateTxt = "Normal weight " + gross;
			}else if(gross > 10000 && gross <= 10500) {
				request.execute("sizeAtenttion();");
				rateTxt = "In tolerance " + gross;
			}else {
				request.execute("sizeAcima();");
				rateTxt = "overweight " + gross;
			}
			//classe onibus E3
		}else if(classe.equals("4A")){
			if(gross < 13500) {
				request.execute("sizeNormal();");
				rateTxt = "Normal weight " + gross;
			}else if(gross > 13500 && gross <= 14175) {
				request.execute("sizeAtenttion();");
				rateTxt = "In tolerance " + gross;
			}else {
				request.execute("sizeAcima();");
				rateTxt = "overweight " + gross;
			}
		}else if(classe.equals("10N")){
			if(gross < 70001) {
				request.execute("sizeNormal();");
				rateTxt = "Normal weight " + gross;
			}else if(gross > 70001 && gross <= 73500){
				request.execute("sizeAtenttion();");
				rateTxt = "In tolerance " + gross;
			}else{
				request.execute("sizeAcima();");
				rateTxt = "overweight " + gross;
			}
		}
	}
	public void silueta() {
		String image = "/teste/sil/";
		try {
			date = dao.searchId(rowkey);
		
		RequestContext request = RequestContext.getCurrentInstance();
		String classe = date.getClasse();
		if(classe.equals("1")) {
			silueta = image+"10.png";
			image1 = "/teste/carro1.jpeg";
			image2 = "/teste/carro2.jpg";
		}else if(classe.equals("2")){
			silueta = image+"2.png";
			image1 = "/teste/e2-2.jpg";
			image2 = "/teste/e2-1.jpg";
		}else if(classe.equals("3")){
			silueta = image+"onibusE2.jpg";
			image1 = "";
			image2 = "";
		}else if(classe.equals("4")){
			silueta = image+"3.png";
			image1 = "/teste/caminhao1.jpg";
			image2 = "/teste/aminhao2.jpg";
		}else if(classe.equals("5")){
			silueta = image+"";
			image1 = "/teste/caminhao1.jpg";
			image2 = "/teste/aminhao2.jpg";
			//falta definir imagem
		}else if(classe.equals("6")){
			silueta = image+"4.png";
			image1 = "/teste/caminhao4-1.jpg";
			image2 = "/teste/caminhao4-2.jpg";
		}else if(classe.equals("7")) {
			silueta = image+"E5.jpg";
			image1 = "/teste/caminhao5-1.jpg";
			image2 = "/teste/caminhao5-2.jpg";
		}else if(classe.equals("8")) {
			silueta = image+"tracevia.jpg";
			image1 = "/teste/caminhao5-1.jpg";
			image2 = "/teste/caminhao5-2.jpg";
		}else if(classe.equals("9")) {
			silueta = image+"moto.png";
			image1 = "/teste/hornet1.jpg";
			image2 = "/teste/hornet2.jpg";
		}else if(classe.equals("2A")) {
			silueta = image+"onibusE2.jpg";
			image1 = "/teste/onibus1.jpg";
			image2 = "/teste/onibus2.jpeg";
		}else if(classe.equals("4A")) {
			silueta = image+"onibusE3.jpg";
			image1 = "/teste/onibus1-2.jpg";
			image2 = "/teste/onibus2-1.jpg";
		}else if(classe.equals("2N")) {
			silueta = image+"tracevia.jpg";
			image1 = "/teste/e2-1.jpg";
			image2 = "/teste/e2-2.jpg";
		}else if(classe.equals("3N")) {
			silueta = image+"tracevia.jpg";
			image1 = "/teste/eixo3-1.jpg";
			image2 = "/teste/eixo3-2.jpg";
		}else if(classe.equals("4N")) {
			silueta = image+"tracevia.jpg";
			image1 = "/teste/caminhao4-1.jpg";
			image2 = "/teste/caminhao4-1.jpg";
		}else if(classe.equals("5N")) {
			silueta = image+"tracevia.jpg";
			image1 = "/teste/caminhao5-1.jpg";
			image2 = "/teste/caminhao5-2.jpg";
		}else if(classe.equals("6N")) {
			silueta = image+"tracevia.jpg";
			image1 = "/teste/caminhao6-1.jpg";
			image2 = "/teste/caminhao6-1.jpg";
		}else if(classe.equals("7N")) {
			silueta = image+"tracevia.jpg";
			image1 = "/teste/caminhao7-1.jpg";
			image2 = "/teste/caminhao7-2.jpg";
		}else if(classe.equals("8N")) {
			silueta = image+"tracevia.jpg";
			image1 = "/teste/tracevia.jpg";
			image2 = "/teste/tracevia.jpg";
		}else if(classe.equals("E9")) {
			silueta = image+"tracevia.jpg";
			image1 = "/teste/caminhao9-1.jpg";
			image2 = "/teste/caminhao9-1.jpg";
		}else if(classe.equals("10N")) {
			silueta = image+"tracevia.jpg";
			image1 = "/teste/tracevia.jpg";
			image2 = "/teste/tracevia.jpg";
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void getInfo() {
		//chamadas de métodos
		indicator();
		silueta();
		if(isSelectedRow() == true) {
			try {
				date = dao.searchId(rowkey);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//peso
			weight = new String[10];
			weight [1] = date.getAxl1W();
			weight [2] = date.getAxl2W();
			weight [3] = date.getAxl3W();
			weight [4] = date.getAxl4W();
			weight [5] = date.getAxl5W();
			weight [6] = date.getAxl6W();
			weight [7] = date.getAxl7W();
			weight [8] = date.getAxl8W();
			weight [9] = date.getAxl9W();
			//distancia
			dstAxes = new String[10];
			
			dstAxes [1] = "0";
			dstAxes [2] = date.getAxl2D();
			dstAxes [3] = date.getAxl3D();
			dstAxes [4] = date.getAxl4D();
			dstAxes [5] = date.getAxl5D();
			dstAxes [6] = date.getAxl6D();
			dstAxes [7] = date.getAxl7D();
			dstAxes [8] = date.getAxl8D();
			dstAxes [9] = date.getAxl9D();
		}
		System.out.println(getRowkey()+" "+isSelectedRow());
		request.execute("btnTable();");
	}
}