package br.com.tracevia.webapp.controller.wimReport;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfWriter;

import br.com.tracevia.webapp.dao.wim.WIMDAO;
import br.com.tracevia.webapp.methods.TranslationMethods;
import br.com.tracevia.webapp.model.global.ColumnModel;
import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.model.meteo.mto.MtoReports.Builder;
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
	private String silueta ="", image1="", image2="";
	public String getImage1() {
		try {

			Path path = Paths.get(image1);
			byte[] file = Files.readAllBytes(path);
			return Base64.getEncoder().encodeToString(file);
		} catch (IOException e) {
			return "";
		}
	}
	public void setImage1(String image1) {
		this.image1 = image1;
	}
	public String getImage2() {
		try {

			Path path = Paths.get(image2);
			byte[] file = Files.readAllBytes(path);
			return Base64.getEncoder().encodeToString(file);
		} catch (IOException e) {
			return "";
		}
	}
	public void setImage2(String image2) {
		this.image2 = image2;
	}
	public String getSilueta() {
		try {

			Path path = Paths.get(silueta);
			byte[] file = Files.readAllBytes(path);
			return Base64.getEncoder().encodeToString(file);
		} catch (IOException e) {
			return "";
		}
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
	private String noImageFolder = "C:\\Tracevia\\Software\\External\\Unknown\\";
	@PostConstruct
	public void initalize(){
		
		image1 = noImageFolder + "no-image.png";
		image2 = noImageFolder + "no-image.png";
		silueta = noImageFolder + "no-image.png";
		reset = true;
		date = data;
		date.setSeqN("-");
		date.setData("-");
		date.setClasse("-");
		date.setNumberAxes("-");
		date.setSpeed("-");
		date.setPbtTotal("-");
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
		TranslationMethods trad = new TranslationMethods();
		if(classe.equals("1")) {
			if(gross < 6000) {
				rateTxt = trad.wimLabels("indicator3")+" "+ gross;
				request.execute("sizeNormal();");
			}else if( gross > 6000 && gross < 6450){
				request.execute("sizeAtenttion();");
				rateTxt = trad.wimLabels("indicator2")+" "+ gross;
			}else {
				request.execute("sizeAtenttion();");
				rateTxt = trad.wimLabels("indicator1")+" "+ gross;
			}
		}else if(classe.equals("2")){
			if(gross < 10000) {
				request.execute("sizeNormal();");
				rateTxt = trad.wimLabels("indicator3")+" "+ gross;
			}
		}else if(classe.equals("4")){
			if(gross < 17000) {
				request.execute("sizeNormal();");
				rateTxt = trad.wimLabels("indicator3")+" "+ gross;
			}else if(gross > 17000 && gross <= 17850){
				request.execute("sizeAtenttion();");
				rateTxt = trad.wimLabels("indicator2")+" "+ gross;
			}else{
				request.execute("sizeAcima();");
				rateTxt = trad.wimLabels("indicator1")+" "+ gross;
			}
		}else if(classe.equals("5")) {
			//essa classe falta definir o peso
			if(gross < 17000) {
				request.execute("sizeNormal();");
				rateTxt = trad.wimLabels("indicator3")+" "+ gross;
			}else if(gross > 17000 && gross <= 17850){
				request.execute("sizeAtenttion();");
				rateTxt = trad.wimLabels("indicator2")+" "+ gross;
			}else{
				request.execute("sizeAcima();");
				rateTxt = trad.wimLabels("indicator1")+" "+ gross;
			}
		}else if(classe.equals("6")||classe.equals("7")||classe.equals("8")){
			if(gross < 25500) {
				request.execute("sizeNormal();");
				rateTxt = trad.wimLabels("indicator3")+" "+ gross;
			}else if(gross > 25500 && gross <= 26775){
				request.execute("sizeAtenttion();");
				rateTxt = trad.wimLabels("indicator2")+" "+ gross;
			}else{
				request.execute("sizeAcima();");
				rateTxt = trad.wimLabels("indicator1")+" "+ gross;
			}
		}else if(classe.equals("9")){
			if(gross < 6000) {
				request.execute("sizeNormal();");
				rateTxt = trad.wimLabels("indicator3")+" "+ gross;
			}else if(gross > 6000 && gross <= 6450){
				request.execute("sizeAtenttion();");
				rateTxt = trad.wimLabels("indicator2")+" "+ gross;
			}else {
				request.execute("sizeAcima();");
				rateTxt = trad.wimLabels("indicator1")+" "+ gross;
			}
			//classe carro
		}else if(classe.equals("10")){
			if(gross < 51000) {
				request.execute("sizeNormal();");
				rateTxt = trad.wimLabels("indicator3")+" "+ gross;
			}else if(gross > 51000 && gross <= 53500){
				request.execute("sizeAtenttion();");
				rateTxt = trad.wimLabels("indicator2")+" "+ gross;
			}else{
				request.execute("sizeAcima();");
				rateTxt = trad.wimLabels("indicator1")+" "+ gross;
			}
		}else if(classe.equals("11")||classe.equals("E9")){
			if(gross < 68000) {
				request.execute("sizeNormal();");
				rateTxt = trad.wimLabels("indicator3")+" "+ gross;
			}else if(gross > 68000 && gross <= 71400){
				request.execute("sizeAtenttion();");
				rateTxt = trad.wimLabels("indicator2")+" "+ gross;
			}else{
				request.execute("sizeAcima();");
				rateTxt = trad.wimLabels("indicator1")+" "+ gross;
			}
		}else if(classe.equals("2A")){
			if(gross < 10000) {
				request.execute("sizeNormal();");
				rateTxt = trad.wimLabels("indicator3")+" "+ gross;
			}else if(gross > 10000 && gross <= 10500) {
				request.execute("sizeAtenttion();");
				rateTxt = trad.wimLabels("indicator2")+" "+ gross;
			}else {
				request.execute("sizeAcima();");
				rateTxt = trad.wimLabels("indicator1")+" "+ gross;
			}
			//classe onibus E3
		}else if(classe.equals("4A")){
			if(gross < 13500) {
				request.execute("sizeNormal();");
				rateTxt = trad.wimLabels("indicator3")+" "+ gross;
			}else if(gross > 13500 && gross <= 14175) {
				request.execute("sizeAtenttion();");
				rateTxt = trad.wimLabels("indicator2")+" "+ gross;
			}else {
				request.execute("sizeAcima();");
				rateTxt = trad.wimLabels("indicator1")+" "+ gross;
			}
		}else if(classe.equals("10N")){
			if(gross < 70001) {
				request.execute("sizeNormal();");
				rateTxt = trad.wimLabels("indicator3")+" "+ gross;
			}else if(gross > 70001 && gross <= 73500){
				request.execute("sizeAtenttion();");
				rateTxt = trad.wimLabels("indicator2")+" "+ gross;
			}else{
				request.execute("sizeAcima();");
				rateTxt = trad.wimLabels("indicator1")+" "+ gross;
			}
		}
	}
	public void silueta() {
		String imageSil = noImageFolder;
		String img = "C:\\teste\\";
		try {
			date = dao.searchId(rowkey);

			RequestContext request = RequestContext.getCurrentInstance();
			String classe = date.getClasse();
			if(classe.equals("1")) {
				silueta = imageSil+"10.png";
				image1 = img+"carro1.jpeg";
				image2 = img+"teste/carro2.jpg";
			}else if(classe.equals("2")){
				silueta = imageSil+"2.png";
				image1 = img+"e2-2.jpg";
				image2 = img+"teste/e2-1.jpg";
			}else if(classe.equals("3")){
				silueta = imageSil+"onibusE2.jpg";
				image1 = "";
				image2 = "";
			}else if(classe.equals("4")){
				silueta = imageSil+"3.png";
				image1 = img+"caminhao1.jpg";
				image2 = img+"aminhao2.jpg";
			}else if(classe.equals("5")){
				silueta = imageSil+"";
				image1 = img+"caminhao1.jpg";
				image2 = img+"/aminhao2.jpg";
				//falta definir imagem
			}else if(classe.equals("6")){
				silueta = imageSil+"4.png";
				image1 = img+"caminhao4-1.jpg";
				image2 = img+"caminhao4-2.jpg";
			}else if(classe.equals("7")) {
				silueta = imageSil+"E5.jpg";
				image1 = img+"caminhao5-1.jpg";
				image2 = img+"caminhao5-2.jpg";
			}else if(classe.equals("8")) {
				silueta = imageSil+"tracevia.jpg";
				image1 = img+"tracevia.jpg";
				image2 = img+"tracevia.jpg";
			}else if(classe.equals("9")) {
				silueta = imageSil+"moto.png";
				image1 = img+"hornet1.jpg";
				image2 = img+"hornet2.jpg";
			}else if(classe.equals("2A")) {
				silueta = imageSil+"onibusE2.jpg";
				image1 = img+"onibus1.jpg";
				image2 = img+"onibus2.jpeg";
			}else if(classe.equals("4A")) {
				silueta = imageSil+"onibusE3.jpg";
				image1 = img+"onibus1-2.jpg";
				image2 = img+"onibus2-1.jpg";
			}else if(classe.equals("2N")) {
				silueta = imageSil+"tracevia.jpg";
				image1 = img+"e2-1.jpg";
				image2 = img+"e2-2.jpg";
			}else if(classe.equals("3N")) {
				silueta = imageSil+"tracevia.jpg";
				image1 = img+"eixo3-1.jpg";
				image2 = img+"eixo3-2.jpg";
			}else if(classe.equals("4N")) {
				silueta = imageSil+"tracevia.jpg";
				image1 = img+"caminhao4-1.jpg";
				image2 = img+"caminhao4-1.jpg";
			}else if(classe.equals("5N")) {
				silueta = imageSil+"tracevia.jpg";
				image1 = img+"caminhao5-1.jpg";
				image2 = img+"caminhao5-2.jpg";
			}else if(classe.equals("6N")) {
				silueta = imageSil+"tracevia.jpg";
				image1 = img+"caminhao6-1.jpg";
				image2 = img+"caminhao6-1.jpg";
			}else if(classe.equals("7N")) {
				silueta = imageSil+"tracevia.jpg";
				image1 = img+"caminhao7-1.jpg";
				image2 = img+"caminhao7-2.jpg";
			}else if(classe.equals("8N")) {
				silueta = imageSil+"tracevia.jpg";
				image1 = img+"tracevia.jpg";
				image2 = img+"tracevia.jpg";
			}else if(classe.equals("E9")) {
				silueta = imageSil+"tracevia.jpg";
				image1 = img+"caminhao9-1.jpg";
				image2 = img+"caminhao9-1.jpg";
			}else if(classe.equals("10N")) {
				silueta = imageSil+"tracevia.jpg";
				image1 = img+"tracevia.jpg";
				image2 = img+"tracevia.jpg";
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
	public static String RESULT = "/teste/";
	public void downloadPdf() throws Exception {
		System.out.println("chegamos");
		indicator();
		silueta();
		// criação do documento
		try {
			date = dao.searchId(rowkey);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TranslationMethods trad = new TranslationMethods();
		RESULT = "/teste/"+date.getSeqN()+".pdf";
		Document document = new Document();
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfWriter writer = PdfWriter.getInstance(document, baos);
		document.open();
		document.setPageSize(PageSize.A4);
		Paragraph pTitulo = new Paragraph(new Phrase(20F,trad.wimLabels("WIM REPORT"), FontFactory.getFont(FontFactory.HELVETICA, 17F)));
		ColumnText tl = new ColumnText(writer.getDirectContent());
		Paragraph tx = new Paragraph();
		tl.setSimpleColumn(400,820,200,50);
		tx.add(pTitulo);
		tl.addElement(tx);
		tl.go();
		document.add(new Paragraph("\n"));
		document.add(new Paragraph(trad.wimLabels("INFORMATION1")+"\n\n"));
		document.add(new Paragraph(trad.wimLabels("N SERIAL")+": "+date.getSeqN()
				+"              "+trad.wimLabels("DATEHOUR")+ date.getData()+"               "
				+trad.wimLabels("CLASSE")+": "+date.getClasse()));
		document.add(new Paragraph(trad.wimLabels("AXES")+": "+date.getNumberAxes()
				+"               "+trad.wimLabels("SPEED")+": "+ date.getSpeed()
				+"               "+trad.wimLabels("PBT")+": "+date.getPbtTotal()));
		document.add(new Paragraph("_____________________________________________________________________________\n\n"));
		document.add(new Paragraph(trad.wimLabels("indicator")+": "+getRateTxt()+"\n\n"));
		document.add(new Paragraph(trad.wimLabels("INFORMATION2")+"\n\n"));
		document.add(new Paragraph(trad.wimLabels("AXES")+"     "+trad.wimLabels("TYPE")+"       "
								  +trad.wimLabels("WEIGHT")+"       "+trad.wimLabels("DSTAXES")+"            "));
		document.add(new Paragraph("1          "+trad.wimLabels("TYPE")+"       "+date.getAxl1W()+"               0"));
		document.add(new Paragraph("2          "+trad.wimLabels("TYPE")+"       "+date.getAxl2W()+"                "+date.getAxl2D()));
		document.add(new Paragraph("3          "+trad.wimLabels("TYPE")+"       "+date.getAxl3W()+"                "+date.getAxl3D()));
		document.add(new Paragraph("4          "+trad.wimLabels("TYPE")+"       "+date.getAxl4W()+"                "+date.getAxl4D()));
		document.add(new Paragraph("5          "+trad.wimLabels("TYPE")+"       "+date.getAxl5W()+"                "+date.getAxl5D()));
		document.add(new Paragraph("6          "+trad.wimLabels("TYPE")+"       "+date.getAxl6W()+"                "+date.getAxl6D()));
		document.add(new Paragraph("7          "+trad.wimLabels("TYPE")+"       "+date.getAxl7W()+"                "+date.getAxl7D()));
		document.add(new Paragraph("8          "+trad.wimLabels("TYPE")+"       "+date.getAxl8W()+"                "+date.getAxl8D()));
		document.add(new Paragraph("9          "+trad.wimLabels("TYPE")+"       "+date.getAxl9W()+"                "+date.getAxl9D()));
		
		Image imgX = Image.getInstance(getImage1());
		imgX.setAbsolutePosition(60, 250);
		imgX.scaleAbsolute (200, 150);
		
		Image imgY = Image.getInstance(getImage2());
		imgY.setAbsolutePosition(300, 250);
		imgY.scaleAbsolute (200, 150);
		//passando a imagem
		document.add(imgX);
		document.add(imgY);
		document.add(new Paragraph());
		Rectangle rowPage = new Rectangle(577, 40, 10, 790); //linha da pagina 

		rowPage.setBorderColor(BaseColor.BLACK);
		rowPage.setBorderWidth(2);
		rowPage.setBorder(Rectangle.BOX);
		document.add(rowPage);
		//final da linda da pagina
		ColumnText ct = new ColumnText(writer.getDirectContent());
		ct.setSimpleColumn(700,0,200,30);
		Paragraph p = new Paragraph();
		p.add("                              "+"Pag 1");//paragrafo Evento
		ct.addElement(p);
		ct.go();

		document.add(new Paragraph(""));


		document.close();
		FileOutputStream fos = new FileOutputStream(RESULT);
		fos.write(baos.toByteArray());
		fos.close();  
		// DOWNLOAD

		externalContext.setResponseContentType("application/pdf");
		externalContext.setResponseHeader("Content-Disposition","attachment; filename=\""+"OCC.pdf\"");

		externalContext.setResponseContentLength(baos.size());

		OutputStream responseOutputStream = externalContext.getResponseOutputStream();  
		baos.writeTo(responseOutputStream);
		responseOutputStream.flush();
		responseOutputStream.close();


		facesContext.responseComplete();  

		// DOWNLOAD
	}
}