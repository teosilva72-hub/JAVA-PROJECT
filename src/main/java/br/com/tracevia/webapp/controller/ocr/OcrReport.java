package br.com.tracevia.webapp.controller.ocr;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
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
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfWriter;

import br.com.tracevia.webapp.dao.ocr.reportDAO;
import br.com.tracevia.webapp.model.ocr.OCR;

@ViewScoped
@ManagedBean(name="OcrReport")
public class OcrReport{
	private OCR data = new OCR();
	private OCR date;
	private String dtStart, hrStart,
	minStart, dtFinal, hrFinal,
	minFinal, camera, img1 = "", img2 = "",
	pasta = "C:\\teste\\" ;

	private List<SelectItem> minutos, horas, classes;
	private List<OCR> list;
	private int rowkey;
	private boolean selectedRow;
	private reportDAO dao = new reportDAO();

	public String getImg1() {
		try {

			Path path = Paths.get(img1);
			byte[] file = Files.readAllBytes(path);
			return Base64.getEncoder().encodeToString(file);
		} catch (IOException e) {
			return "";
		}

	}


	public void setImg1(String img1) {
		this.img1 = img1;
	}


	public String getImg2() {
		try {

			Path path = Paths.get(img2);
			byte[] file = Files.readAllBytes(path);
			return Base64.getEncoder().encodeToString(file);
		} catch (IOException e) {
			return "";
		}
	}


	public void setImg2(String img2) {
		this.img2 = img2;
	}


	public OCR getData() {
		return data;
	}


	public String getCamera() {
		return camera;
	}


	public void setCamera(String camera) {
		this.camera = camera;
	}


	public String getDtStart() {
		return dtStart;
	}


	public void setDtStart(String dtStart) {
		this.dtStart = dtStart;
	}


	public String getHrStart() {
		return hrStart;
	}


	public void setHrStart(String hrStart) {
		this.hrStart = hrStart;
	}


	public String getMinStart() {
		return minStart;
	}


	public void setMinStart(String minStart) {
		this.minStart = minStart;
	}


	public String getDtFinal() {
		return dtFinal;
	}


	public void setDtFinal(String dtFinal) {
		this.dtFinal = dtFinal;
	}


	public String getHrFinal() {
		return hrFinal;
	}


	public void setHrFinal(String hrFinal) {
		this.hrFinal = hrFinal;
	}


	public String getMinFinal() {
		return minFinal;
	}


	public void setMinFinal(String minFinal) {
		this.minFinal = minFinal;
	}


	public void setData(OCR data) {
		this.data = data;
	}


	public OCR getDate() {
		return date;
	}


	public void setDate(OCR date) {
		this.date = date;
	}


	public List<SelectItem> getMinutos() {
		return minutos;
	}


	public void setMinutos(List<SelectItem> minutos) {
		this.minutos = minutos;
	}


	public List<SelectItem> getHoras() {
		return horas;
	}


	public void setHoras(List<SelectItem> horas) {
		this.horas = horas;
	}


	public List<SelectItem> getClasses() {
		return classes;
	}


	public void setClasses(List<SelectItem> classes) {
		this.classes = classes;
	}


	public List<OCR> getList() {
		return list;
	}


	public void setList(List<OCR> list) {
		this.list = list;
	}


	public int getRowkey() {
		return rowkey;
	}


	public void setRowkey(int rowkey) {
		this.rowkey = rowkey;
	}


	public boolean isSelectedRow() {
		return selectedRow;
	}


	public void setSelectedRow(boolean selectedRow) {
		this.selectedRow = selectedRow;
	}


	@PostConstruct
	public void initialize() {
		RequestContext.getCurrentInstance().execute("getTr()");
		img1 = pasta+"no-image.png";
		img2 = pasta+"no-image.png";
		horas = new ArrayList<SelectItem>();
		minutos = new ArrayList<SelectItem>();
		classes = new ArrayList<SelectItem>();
		classes.add(new SelectItem("ocr_1"));
		classes.add(new SelectItem("ocr_2"));
		classes.add(new SelectItem("ocr_3"));
		classes.add(new SelectItem("ocr_4"));
		classes.add(new SelectItem("ocr_5"));
		classes.add(new SelectItem("ocr_6"));
		for(int x = 0; x < 24; x++) {
			if (x < 10)
				horas.add(new SelectItem("0"+String.valueOf(x), "0"+String.valueOf(x)));
			else 
				horas.add(new SelectItem(String.valueOf(x), String.valueOf(x)));
		}for(int m = 0; m < 60; m++){				
			if (m < 10)
				minutos.add(new SelectItem("0"+String.valueOf(m), "0"+String.valueOf(m)));
			else 
				minutos.add(new SelectItem(String.valueOf(m), String.valueOf(m)));
		}
	}
	public String path() throws IOException {
		return Base64.getEncoder().encodeToString(Files.readAllBytes(Paths.get("C:\\Users\\mateu\\Pictures\\bahianorte.jpg")));
	}

	public void search() throws IOException {
		img1 = pasta+"no-image.png";
		img2 = pasta+"no-image.png";
		String start = dtStart+" "+ hrStart+":"+minStart;
		String end = dtFinal+" "+ hrFinal+":"+minFinal;
		if(camera == "Todos") camera ="";
		String cam = camera;

		if(cam != "") {

			try {
				System.out.println("com classe");
				list = dao.searchTable(start, end, cam);
				RequestContext.getCurrentInstance().execute("getTr()");
				RequestContext.getCurrentInstance().execute("dataPicker()");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}else {
			System.out.println("sem classe");
			String start2 = dtStart+" "+ hrStart+":"+minStart;
			String end2 = dtFinal+" "+ hrFinal+":"+minFinal;
			RequestContext.getCurrentInstance().execute("getTr()");
			RequestContext.getCurrentInstance().execute("dataPicker()");
			try {
				list = dao.searchTable2(start2, end2);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void idGet() {
		System.out.println(rowkey);
		try {
			data = dao.searchId(rowkey);
			String dt = data.getDataHour();
			dt = dt.replaceAll("-", "");
			dt = dt.replaceAll(":", "");
			dt = dt.replaceAll(" ", "");
			String pl = data.getPlaca();
			pl = pl.replaceAll("-", "");
			pl = pl.replaceAll(" ", "");
			img1 = pasta+data.getCam()+"_"+data.getDataHour()+"_"+pl+".jpg";
			img2 = pasta+"Plate"+data.getCam()+"_"+data.getDataHour()+"_"+pl+".jpg";
			System.out.println(img1);
			System.out.println(img2);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void pdf() {
		try {
			date = dao.searchId(getRowkey());
			String RESULT = "/teste/"+date.getId()+".pdf";
			Document document = new Document();
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter writer = PdfWriter.getInstance(document, baos);
			document.open();
			document.setPageSize(PageSize.A4);
			Paragraph pTitulo = new Paragraph(new Phrase(20F,"OCR REPORT"));
			ColumnText tl = new ColumnText(writer.getDirectContent());
			Paragraph tx = new Paragraph();
			tl.setSimpleColumn(400,820,200,50);
			tx.add(pTitulo);
			tl.addElement(tx);
			tl.go();
			document.add(new Paragraph("\n\n"));
			document.add(new Paragraph("ID: "+ date.getId()
			+"\nData/Hour: " + date.getDataHour()
			+"\nCâmera: "+ date.getCam()
			+"\nPlaca: "+ date.getPlaca()));
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

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}