package br.com.tracevia.webapp.controller.wimReport;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
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
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import br.com.tracevia.webapp.dao.wim.WIMDAO;
import br.com.tracevia.webapp.methods.TranslationMethods;
import br.com.tracevia.webapp.model.global.ColumnModel;
import br.com.tracevia.webapp.model.meteo.mto.MtoReports.Builder;
import br.com.tracevia.webapp.model.wim.WimData;

@ManagedBean(name="wimReport")
@ViewScoped
public class wimReport {

	private WIMDAO dao = new WIMDAO();
	private WimData data = new WimData();
	private String logo;

	public String getLogo() {
		try {

			Path path = Paths.get(logo);
			byte[] file = Files.readAllBytes(path);
			return Base64.getEncoder().encodeToString(file);
		} catch (IOException e) {
			return "";
		}
	}
	private String noImage;

	private String dateInitial, dateFinal, minuteInitial,
	minuteFinal, classs, hourInitial, hourFinal;

	String silFolder = "C:\\Tracevia\\Software\\External\\Wim\\Silhuetas\\";

	String vehiclesFolder = "C:\\Tracevia\\Software\\External\\Wim\\Veiculos\\";

	String noImageFolder = "C:\\Tracevia\\Software\\External\\Unknown\\";

	private int rowkey;
	private boolean selectedRow;

	private List<WimData> list;

	private RequestContext request = RequestContext.getCurrentInstance();
	private String[] dstAxes, weight;

	public int gross;
	private String nEvent, dateHour, category, nAxes, speed, pbtTotal, size;
	private String rateTxt, image, imagePlate, imageSil;

	private List<Builder> resultList;	
	private List<ColumnModel> columns;

	private boolean reset, search;

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
	public List<WimData> getList() {
		return list;
	}
	public void setList(List<WimData> list) {
		this.list = list;
	}	

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

	public String getRateTxt() {
		return rateTxt;
	}
	public void setRateTxt(String rateTxt) {
		this.rateTxt = rateTxt;
	}

	public int getGross() {
		return gross;
	}
	public void setGross(int gross) {
		this.gross = gross;
	}

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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getImagePlate() {
		return imagePlate;
	}

	public void setImagePlate(String imagePlate) {
		this.imagePlate = imagePlate;
	}

	public String getImageSil() {
		return imageSil;
	}

	public void setImageSil(String imageSil) {
		this.imageSil = imageSil;
	}

	@PostConstruct
	public void initalize(){

		noImage = noImageFolder + "no-image.jpg";

		reset = true;

		data.setSeqN("-");
		data.setDatetime("-");
		data.setClasse("-");
		data.setAxlNumber("-");
		data.setSpeed("-");
		data.setPbtTotal("-");
		data.setSize("-");

		setImage(getImagePath(noImage));
		setImagePlate(getImagePath(noImage));
		setImageSil(getImagePath(noImage));

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

	public void indicator(WimData data) {

		RequestContext request = RequestContext.getCurrentInstance();
		String classe = data.getClasse();

		gross = Integer.parseInt(data.getPbtTotal());
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
		}else if(classe.equals("3")){
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


	public void getInfo() {
		//chamadas de métodos
		//indicator();
		//silueta();
		if(isSelectedRow() == true) {
			try {

				data = dao.searchId(rowkey);	

				indicator(data);
				File img1 = new File(vehiclesFolder+data.getImage());
				File img2 = new File(vehiclesFolder+data.getImagePlate());
				File sil = new File(silFolder+data.getImageSil());
				//VEHICLE IMAGE
				if(img1.exists())				
					image = getImagePath(vehiclesFolder+data.getImage());

				else image = getImagePath(noImageFolder+"no-image.jpg");

				//PLATE IMAGE
				if(img2.exists())				
					imagePlate = getImagePath(vehiclesFolder+data.getImagePlate());

				else imagePlate = getImagePath(noImageFolder+"no-image.jpg");

				//SIL IMAGE
				if(sil.exists())				
					imageSil = getImagePath(silFolder+data.getImageSil());

				else imageSil = getImagePath(noImageFolder+"no-image.jpg");				

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		request.execute("btnTable();");
	}

	public void downloadPdf() throws Exception {

		// criação do documento
		Document document = new Document();
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		TranslationMethods trad = new TranslationMethods();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		try {

			PdfWriter writer = PdfWriter.getInstance(document, baos);
			document.open();
			document.setPageSize(PageSize.A4);
			Paragraph pTitulo = new Paragraph(new Phrase(20F,trad.wimLabels("WIM REPORT"), FontFactory.getFont(FontFactory.HELVETICA, 17F)));
			ColumnText tl = new ColumnText(writer.getDirectContent());
			Paragraph tx = new Paragraph();
			tl.setSimpleColumn(400,780,200,50);
			tx.add(pTitulo);
			tl.addElement(tx);
			tl.go();
			document.add(new Paragraph("\n\n\n\n"));
			document.add(new Paragraph(trad.wimLabels("INFORMATION1")+"\n\n"));
			document.add(new Paragraph(trad.wimLabels("N SERIAL")+": "+data.getSeqN()
			+"              "+trad.wimLabels("DATAHOUR")+": "+ data.getDatetime()+"               "
			+trad.wimLabels("CLASSE")+": "+data.getClasse()));
			document.add(new Paragraph(trad.wimLabels("AXES")+": "+data.getAxlNumber()
			+"               "+trad.wimLabels("SPEED")+": "+ data.getSpeed()
			+"               "+trad.wimLabels("PBT")+": "+data.getPbtTotal()));
			document.add(new Paragraph("_____________________________________________________________________________\n\n"));
			document.add(new Paragraph(trad.wimLabels("indicator")+": "+getRateTxt()+"\n\n"));
			document.add(new Paragraph(trad.wimLabels("INFORMATION2")+"\n\n"));
			logo = "C:\\Tracevia\\Software\\External\\Logo\\tuxpan.png";
			File  tuxpan = new File(logo);
			if(tuxpan.exists()) {
				Image tuxpanL = Image.getInstance(logo);
				tuxpanL.setAbsolutePosition(420, 800);
				tuxpanL.scaleAbsolute (100, 30);
				document.add(tuxpanL);
			}
			File img1 = new File(vehiclesFolder+data.getImage());
			File img2 = new File(vehiclesFolder+data.getImagePlate());
			if(img1.exists()) {
				Image imgX = Image.getInstance(vehiclesFolder+data.getImage());
				imgX.setAbsolutePosition(100, 230);
				imgX.scaleAbsolute (200, 150);
				document.add(imgX);
			}else {
				Image imgX = Image.getInstance(noImageFolder+"no-image.jpg");
				imgX.setAbsolutePosition(100, 230);
				imgX.scaleAbsolute (200, 150);
				document.add(imgX);
			}
			if(img2.exists()) {
				Image imgY = Image.getInstance(vehiclesFolder+data.getImagePlate());
				imgY.setAbsolutePosition(300, 230);
				imgY.scaleAbsolute (200, 150);
				document.add(imgY);
			}else {
				Image imgY = Image.getInstance(noImageFolder+"no-image.jpg");
				imgY.setAbsolutePosition(300, 230);
				imgY.scaleAbsolute (200, 150);
				document.add(imgY);
			}
			//passando a imagem


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
			PdfPTable table1 = new PdfPTable(3);
			PdfPTable table2 = new PdfPTable(3);
			PdfPTable table3 = new PdfPTable(3);
			PdfPTable table4 = new PdfPTable(3);
			PdfPTable table5 = new PdfPTable(3);
			PdfPTable table6 = new PdfPTable(3);
			PdfPTable table7 = new PdfPTable(3);
			PdfPTable table8 = new PdfPTable(3);
			PdfPTable table9 = new PdfPTable(3);
			PdfPTable table10 = new PdfPTable(3);
			table1.addCell(trad.wimLabels("AXES"));table1.addCell(trad.wimLabels("TYPE"));
			table1.addCell(trad.wimLabels("WEIGHT"));table1.addCell(trad.wimLabels("DSTAXES"));
			document.add(table1);
			table2.addCell("1");table2.addCell(data.getAxlType()[0]);
			table2.addCell(data.getAxlWeight()[0]);table2.addCell(data.getAxlDist()[1]);
			document.add(table2);
			table3.addCell("2");table3.addCell(data.getAxlType()[1]);
			table3.addCell(data.getAxlWeight()[1]);table3.addCell(data.getAxlDist()[1]);
			document.add(table3);
			table4.addCell("3");table4.addCell(data.getAxlType()[2]);
			table4.addCell(data.getAxlWeight()[2]);table4.addCell(data.getAxlDist()[2]);
			document.add(table4);
			table5.addCell("4");table5.addCell(data.getAxlType()[3]);
			table5.addCell(data.getAxlWeight()[3]);table5.addCell(data.getAxlDist()[3]);
			document.add(table5);
			table6.addCell("5");table6.addCell(data.getAxlType()[4]);
			table6.addCell(data.getAxlWeight()[4]);table6.addCell(data.getAxlDist()[4]);
			document.add(table6);
			table7.addCell("6");table7.addCell(data.getAxlType()[5]);
			table7.addCell(data.getAxlWeight()[5]);table7.addCell(data.getAxlDist()[5]);
			document.add(table7);
			table8.addCell("7");table8.addCell(data.getAxlType()[6]);
			table8.addCell(data.getAxlWeight()[6]);table2.addCell(data.getAxlDist()[6]);
			document.add(table8);
			table9.addCell("8");table9.addCell(data.getAxlType()[7]);
			table9.addCell(data.getAxlWeight()[7]);table9.addCell(data.getAxlDist()[7]);
			document.add(table9);
			table10.addCell("9");table10.addCell(data.getAxlType()[8]);
			table10.addCell(data.getAxlWeight()[8]);table10.addCell(data.getAxlDist()[8]);
			document.add(table10);
			document.add(new Paragraph(""));

		}catch(DocumentException de) {
			System.err.println(de.getMessage());
		}
		catch(IOException ioe) {
			System.err.println(ioe.getMessage());
		}
		document.close();

		// DOWNLOAD
		data = dao.searchId(rowkey);
		externalContext.setResponseContentType("application/pdf");
		externalContext.setResponseHeader("Content-Disposition","attachment; filename=\"wim_"+data.getSeqN()+".pdf\"");

		externalContext.setResponseContentLength(baos.size());

		OutputStream responseOutputStream = externalContext.getResponseOutputStream();  
		baos.writeTo(responseOutputStream);
		responseOutputStream.flush();
		responseOutputStream.close();


		facesContext.responseComplete();  

	}

	public String getImagePath(String image) {

		try {

			Path path = Paths.get(image);								
			byte[] file = Files.readAllBytes(path);
			return Base64.getEncoder().encodeToString(file);

		} catch (IOException e) {											
			return "";
		}

	}
}