package br.com.tracevia.webapp.controller.ocr;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
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

import br.com.tracevia.webapp.dao.global.EquipmentsDAO;
import br.com.tracevia.webapp.dao.ocr.reportDAO;
import br.com.tracevia.webapp.methods.TranslationMethods;
import br.com.tracevia.webapp.model.global.Equipments;
import br.com.tracevia.webapp.model.global.RoadConcessionaire;
import br.com.tracevia.webapp.model.ocr.OCR;
import br.com.tracevia.webapp.util.ImageUtil;
import br.com.tracevia.webapp.util.LocaleUtil;

@ViewScoped
@ManagedBean(name="OcrReport")
public class OcrReport{
	LocaleUtil localeOCR;
	private OCR data;
	private OCR others;
	private reportDAO dao;
	private EquipmentsDAO equipDAO;
	List<? extends Equipments> listOcr; 

	private String dtStart, hrStart,
	minStart, dtFinal, hrFinal,
	minFinal, camera, imageVeh, imagePlt, imageVehPdf, imagePltPdf, ftpFolder, all_img;
	
	String noImage;

	private List<SelectItem> minutos, horas, cams;
	private List<OCR> list;
	private int rowkey;
	private boolean selectedRow;

	public String getAll_img() {
		return all_img;
	}

	public void setAll_img(String all_img) {
		this.all_img = all_img;
	}

	public OCR getOthers() {
		return others;
	}

	public void setOthers(OCR others) {
		this.others = others;
	}

	public String getImageVeh() {
		return imageVeh;
	}
	
	public void setImageVeh(String imageVeh) {
		this.imageVeh = imageVeh;
	}

	public String getImagePlt() {
		return imagePlt;
	}
	private String logo;
	
	public String getLogo() {
		return logo;
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

		ftpFolder = "C:\\Cameras\\OCR_Types\\"; 
				
		noImage = "no-image.jpg";
					
		String unknownImage = ImageUtil.getInternalImagePathAndEncodeToBase64("images", "unknown", noImage);
							
		imageVeh = unknownImage;
		imagePlt = unknownImage;
		
		horas = new ArrayList<SelectItem>();
		minutos = new ArrayList<SelectItem>();

		equipDAO = new EquipmentsDAO();		
		cams = new ArrayList<SelectItem>();

		try {

			listOcr = equipDAO.equipmentSelectOptions("ocr");

		} catch (Exception e1) {			
			e1.printStackTrace();
		}

		//filtro cãmera

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
	
	public void search() throws IOException {
		System.out.println("entramos no pesquisar");
		dao = new reportDAO();
		data = new OCR();
			
		String start = dtStart+" "+ hrStart+":"+minStart;
		String end = dtStart+" 23:59:00";

		if(camera == "Todos") camera ="";

		String cam = camera;
		
		String all_search = "";
		if(all_img.equals("0")) all_search = "";
		else if(all_img.equals("1") || all_img.equals("2")) all_search = "XXXXXXX";
		//System.out.println(all_img);
 		if(cam != "") {
 				System.out.println("condição camera entramos");
			try {
				
				list = dao.searchTable(start, cam, all_search, all_img, end);

				RequestContext.getCurrentInstance().execute("getTr()");
				RequestContext.getCurrentInstance().execute("dataPicker()");

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}else {

			String start2 = dtStart+" "+ hrStart+":"+minStart;
			String end2 = dtFinal+" "+ hrFinal+":"+minFinal;

			RequestContext.getCurrentInstance().execute("getTr()");
			RequestContext.getCurrentInstance().execute("dataPicker()");

			try {
				list = dao.searchTable2(start2, end2, all_search, all_img);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void idGet() {

		try {
			
			dao = new reportDAO();
			data = new OCR();
			
			data = dao.searchId(rowkey, camera);
			
			String dt = data.getDataHour();
			dt = dt.replaceAll("\\.","");
			dt = dt.replaceAll("-", "");
			dt = dt.replaceAll(":", "");			
			dt = dt.replaceAll(" ", "");
			
			String subFolder = dt.substring(0, 8);
			String nameVeh = data.getCam().replaceAll(" ", "_");	
			
			String sourceFolder = ftpFolder.concat(nameVeh).concat("\\"+subFolder);
			
			//WEB
			String vehImg = sourceFolder.concat("\\"+nameVeh).concat("_"+dt+"_").concat(data.getPlaca()+".jpg");
			String plateImg = sourceFolder.concat("\\Plate"+nameVeh).concat("_"+dt+"_").concat(data.getPlaca()+".jpg");									
			String blankImage = ImageUtil.getInternalImagePath("images", "unknown", noImage);
			
			// CREATE FILES			
			File veh = new File(vehImg);
			File plate = new File(plateImg);
												
			if(veh.exists()) 						
				imageVeh = ImageUtil.encodeToBase64(vehImg);
			
			else imageVeh = ImageUtil.encodeToBase64(blankImage);
			
			if(plate.exists())	
				imagePlt = ImageUtil.encodeToBase64(plateImg);

			else imagePlt = ImageUtil.encodeToBase64(blankImage);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void pdf() throws Exception {

		// criação do  documento
		Document document = new Document();
		//TranslationMethods trad = new TranslationMethods();

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();	

		ByteArrayOutputStream baos = new ByteArrayOutputStream(); //SOLUTION

		try {
			
			localeOCR = new LocaleUtil();	
			localeOCR.getResourceBundle(LocaleUtil.LABELS_OCR);
			dao = new reportDAO();
			data = new OCR();

			data = dao.searchId(getRowkey(), camera);
			String dt = data.getDataHour();

			dt = dt.replaceAll("\\.","");
			dt = dt.replaceAll("-", "");
			dt = dt.replaceAll(":", "");			
			dt = dt.replaceAll(" ", "");
			
			String subFolder = dt.substring(0, 8);
			String nameVeh = data.getCam().replaceAll(" ", "_");
			
			String sourceFolder = ftpFolder.concat(nameVeh).concat("\\"+subFolder);
			
			//PDF
			String vehImg = sourceFolder.concat("\\"+nameVeh).concat("_"+dt+"_").concat(data.getPlaca()+".jpg");
			String plateImg = sourceFolder.concat("\\Plate"+nameVeh).concat("_"+dt+"_").concat(data.getPlaca()+".jpg");									
			String blankImg = ImageUtil.getInternalImagePath("images", "unknown", noImage);
						
			// CREATE FILES			
			File veh = new File(vehImg);
			File plate = new File(plateImg);
									
			if(veh.exists()) 						
				imageVehPdf = vehImg;
			
			else imageVehPdf = blankImg;

			if(plate.exists())	
				imagePltPdf = plateImg;

			else imagePltPdf = blankImg;
				
			PdfWriter writer = PdfWriter.getInstance(document, baos);

			document.open();

			document.setPageSize(PageSize.A4);
			Paragraph pTitulo = new Paragraph(new Phrase(27F,localeOCR.getStringKey("ocr_report_vehicles_report"), FontFactory.getFont(FontFactory.HELVETICA, 20F)));
			ColumnText tl = new ColumnText(writer.getDirectContent());
			Paragraph tx = new Paragraph();
			
			logo = ImageUtil.getInternalImagePath("images", "files", RoadConcessionaire.externalImagePath);
			
			if(!logo.equals("")) {
				Image image2 = Image.getInstance(logo);
				image2.setAbsolutePosition(420, 800);
				image2.scaleAbsolute (100, 30);
				document.add(image2);
			}
			
			tl.setSimpleColumn(400,780,200,50);
			tx.add(pTitulo);
			tl.addElement(tx);
			tl.go();
			Rectangle rowPage = new Rectangle(577, 40, 10, 790); //linha da pagina 

			rowPage.setBorderColor(BaseColor.BLACK);
			rowPage.setBorderWidth(2);
			rowPage.setBorder(Rectangle.BOX);
			document.add(rowPage);
			document.add(new Paragraph("\n\n\n\n"));
			PdfPTable table1 = new PdfPTable(2);
			PdfPTable table2 = new PdfPTable(2);
			PdfPTable table3 = new PdfPTable(2);
			PdfPTable table4 = new PdfPTable(2);
			PdfPTable table5 = new PdfPTable(2);
			PdfPTable table6 = new PdfPTable(2);
			
			table1.addCell(localeOCR.getStringKey("ocr_number_label"));
			table1.addCell(data.getId());
			table2.addCell(localeOCR.getStringKey("ocr_dataHour_label"));
			table2.addCell(data.getDataHour());
			table3.addCell(localeOCR.getStringKey("ocr_cam_label"));
			table3.addCell(data.getCam());
			table4.addCell(localeOCR.getStringKey("ocr_placa_label"));
			table4.addCell(data.getPlaca());
			table5.addCell(localeOCR.getStringKey("km"));
			table5.addCell(data.getKm());
			table6.addCell(localeOCR.getStringKey("direction"));
			table6.addCell(data.getDirection());
			document.add(table1);
			document.add(table4);
			document.add(table2);
			document.add(table3);
			document.add(table6);
			document.add(table5);
			Image imgX = Image.getInstance(imageVehPdf);
			imgX.setAbsolutePosition(60, 480);
			imgX.scaleAbsolute (220, 150);
			
			Image imgY = Image.getInstance(imagePltPdf);
			imgY.setAbsolutePosition(300, 480);
			imgY.scaleAbsolute (220, 150);
			//passando a imagem
			document.add(imgX);
			document.add(imgY);
			
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
