package br.com.tracevia.webapp.controller.ocr;

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
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfWriter;

import br.com.tracevia.webapp.dao.global.EquipmentsDAO;
import br.com.tracevia.webapp.dao.ocr.reportDAO;
import br.com.tracevia.webapp.methods.TranslationMethods;
import br.com.tracevia.webapp.model.global.Equipments;
import br.com.tracevia.webapp.model.ocr.OCR;
import br.com.tracevia.webapp.util.LocaleUtil;

@ViewScoped
@ManagedBean(name="OcrReport")
public class OcrReport{
	LocaleUtil localeOCR;
	private OCR data;
	private reportDAO dao;
	private EquipmentsDAO equipDAO;
	List<? extends Equipments> listOcr; 

	private String dtStart, hrStart,
	minStart, dtFinal, hrFinal,
	minFinal, camera, imageVeh, imagePlt, ftpFolder, noImageFolder;

	private List<SelectItem> minutos, horas, cams;
	private List<OCR> list;
	private int rowkey;
	private boolean selectedRow;

	
	public String getImageVeh() {
		try {

			Path path = Paths.get(imageVeh);								
			byte[] file = Files.readAllBytes(path);
			return Base64.getEncoder().encodeToString(file);
		} catch (IOException e) {
			e.printStackTrace();

			return "";
		}
	}
	
	public void setImageVeh(String imageVeh) {
		this.imageVeh = imageVeh;
	}

	public String getImagePlt() {
		try {

			Path path = Paths.get(imagePlt);
			byte[] file = Files.readAllBytes(path);
			return Base64.getEncoder().encodeToString(file);
		} catch (IOException e) {
			return "";
		}
	}
	
	public void setImagePlt(String imagePlt) {
		this.imagePlt = imagePlt;
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

	public List<SelectItem> getCams() {
		return cams;
	}


	@PostConstruct
	public void initialize() {
		localeOCR = new LocaleUtil();	
		localeOCR.getResourceBundle(LocaleUtil.LABELS_OCR);
		RequestContext.getCurrentInstance().execute("getTr()");

		ftpFolder = "C:\\Cameras\\OCR\\NormalType\\"; 
		noImageFolder = "C:\\Tracevia\\Software\\External\\Unknown\\";
		
		imageVeh = noImageFolder + "no-image.png";
		imagePlt = noImageFolder + "no-image.png";
		
		horas = new ArrayList<SelectItem>();
		minutos = new ArrayList<SelectItem>();

		equipDAO = new EquipmentsDAO();		
		cams = new ArrayList<SelectItem>();


		try {

			listOcr = equipDAO.EquipmentSelectOptions("ocr");

		} catch (Exception e1) {			
			e1.printStackTrace();
		}

		//filtro câmera

		for (Equipments e : listOcr) {
			SelectItem s = new SelectItem();

			s.setValue(e.getNome());
			s.setLabel(e.getNome());

			cams.add(s);				
		}

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

		dao = new reportDAO();
		data = new OCR();
				
		imageVeh = noImageFolder + "no-image.png";
		imagePlt = noImageFolder + "no-image.png";
		

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

			dao = new reportDAO();
			data = new OCR();

			data = dao.searchId(rowkey);

			String dt = data.getDataHour();

			dt = dt.replaceAll("\\.","");
			dt = dt.replaceAll("-", "");
			dt = dt.replaceAll(":", "");			
			dt = dt.replaceAll(" ", "");
			
			String subFolder = dt.substring(0, 8);

			File f = new File(ftpFolder+data.getCam()+"\\"+subFolder+"\\"+data.getCam()+"_"+dt+"_"+data.getPlaca()+".jpg");
			File g = new File(ftpFolder+data.getCam()+"\\"+subFolder+"\\Plate"+data.getCam()+"_"+dt+"_"+data.getPlaca()+".jpg");
			
			System.out.println(f.getPath());
			System.out.println(g.getPath());
			
			if(f.exists()) 						
				imageVeh = f.getPath();
			
			else imageVeh= noImageFolder + "no-image.png";

			if(g.exists())	
				imagePlt = g.getPath();

			else imagePlt = noImageFolder + "no-image.png";							


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void pdf() throws Exception {

		// criação do  documento
		Document document = new Document();
		TranslationMethods trad = new TranslationMethods();

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();	

		ByteArrayOutputStream baos = new ByteArrayOutputStream(); //SOLUTION

		try {
			localeOCR = new LocaleUtil();	
			localeOCR.getResourceBundle(LocaleUtil.LABELS_OCR);
			dao = new reportDAO();
			data = new OCR();

			data = dao.searchId(getRowkey());
			String dt = data.getDataHour();

			dt = dt.replaceAll("\\.","");
			dt = dt.replaceAll("-", "");
			dt = dt.replaceAll(":", "");			
			dt = dt.replaceAll(" ", "");
			
			String subFolder = dt.substring(0, 8);

			imageVeh = ftpFolder+data.getCam()+"\\"+subFolder+"\\"+data.getCam()+"_"+dt+"_"+data.getPlaca()+".jpg";
			imagePlt = ftpFolder+data.getCam()+"\\"+subFolder+"\\"+"\\Plate"+data.getCam()+"_"+dt+"_"+data.getPlaca()+".jpg";
			
			PdfWriter writer = PdfWriter.getInstance(document, baos);

			document.open();

			document.setPageSize(PageSize.A4);
			Paragraph pTitulo = new Paragraph(new Phrase(20F,localeOCR.getStringKey("ocr_report_title")));
			ColumnText tl = new ColumnText(writer.getDirectContent());
			Paragraph tx = new Paragraph();

			tl.setSimpleColumn(400,820,200,50);
			tx.add(pTitulo);
			tl.addElement(tx);
			tl.go();

			document.add(new Paragraph("\n\n"));
			document.add(new Paragraph(localeOCR.getStringKey("ocr_number_label")+": "+ data.getId()			
			+"\n"+localeOCR.getStringKey("ocr_dataHour_label")+": " + data.getDataHour()
			+"\n"+localeOCR.getStringKey("ocr_cam_label")+": "+ data.getCam()
			+"\n"+localeOCR.getStringKey("ocr_placa_label")+": "+ data.getPlaca()));
			Image imgX = Image.getInstance(imageVeh);
			imgX.setAbsolutePosition(60, 500);
			imgX.scaleAbsolute (200, 150);
			
			Image imgY = Image.getInstance(imagePlt);
			imgY.setAbsolutePosition(300, 500);
			imgY.scaleAbsolute (200, 150);
			//passando a imagem
			document.add(imgX);
			document.add(imgY);
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

		}catch(DocumentException de) {
			System.err.println(de.getMessage());
		}
		catch(IOException ioe) {
			System.err.println(ioe.getMessage());
		}

		document.close();

		externalContext.setResponseContentType("application/pdf");
		externalContext.setResponseHeader("Content-Disposition","attachment; filename=\""+data.getCam()+"_"+data.getPlaca()+".pdf\"");

		externalContext.setResponseContentLength(baos.size());

		OutputStream responseOutputStream = externalContext.getResponseOutputStream();  
		baos.writeTo(responseOutputStream);
		responseOutputStream.flush();
		responseOutputStream.close();


		facesContext.responseComplete();  
	}
}