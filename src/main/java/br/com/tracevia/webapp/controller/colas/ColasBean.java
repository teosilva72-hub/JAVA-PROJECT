package br.com.tracevia.webapp.controller.colas;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfWriter;

import br.com.tracevia.webapp.methods.TranslationMethods;
import br.com.tracevia.webapp.model.colas.ColasQueue;
import br.com.tracevia.webapp.dao.colas.ColasDAO;
import br.com.tracevia.webapp.util.LocaleUtil;

@ManagedBean(name="colasBean")
@ViewScoped
public class ColasBean {
	LocaleUtil localeColas;
	public List<ColasQueue> queues;
	public ColasQueue queue;
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
	public ColasQueue getQueue() {
		return queue;
	}

	public void setQueue(ColasQueue queue) {
		this.queue = queue;
	}

	public List<ColasQueue> getQueues() {
		return queues;
	}

	@PostConstruct
	public void initalize() {
		SimpleDateFormat formattter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();

		try {
			getAllQueue(formattter.format(date));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		queue = new ColasQueue();
	}

	public void getAllQueue(String date) {
		ColasDAO dao = new ColasDAO();
		
		try {
			queues = dao.history_queue(date);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void pdf() {
		
		try {
			
			TranslationMethods trad = new TranslationMethods();
			localeColas = new LocaleUtil();	
			localeColas.getResourceBundle(LocaleUtil.LABELS_COLAS);
			//String RESULT = "/teste/teste.pdf";
			Document document = new Document();
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter writer = PdfWriter.getInstance(document, baos);
			document.open();
			document.setPageSize(PageSize.A4);
			Paragraph pTitulo = new Paragraph(new Phrase(20F,localeColas.getStringKey("title_page")));
			ColumnText tl = new ColumnText(writer.getDirectContent());
			Paragraph tx = new Paragraph();
			tl.setSimpleColumn(400,820,200,50);
			tx.add(pTitulo);
			tl.addElement(tx);
			tl.go();
			
			Rectangle rowPage = new Rectangle(577, 40, 10, 790); //linha da pagina 

			rowPage.setBorderColor(BaseColor.BLACK);
			rowPage.setBorderWidth(2);
			rowPage.setBorder(Rectangle.BOX);
			document.add(rowPage);
			//final da linda da pagina
			ColumnText ct = new ColumnText(writer.getDirectContent());
			ct.setSimpleColumn(700,0,200,30);
			Paragraph p = new Paragraph();
			p.add("                              Pag 1");//paragrafo Evento
			ct.addElement(p);
			ct.go();
			logo = "C:\\Tracevia\\Software\\External\\Logo\\tuxpan.png";
			File  tuxpan = new File(logo);
			if(tuxpan.exists()) {
				Image tuxpanL = Image.getInstance(logo);
				tuxpanL.setAbsolutePosition(420, 800);
				tuxpanL.scaleAbsolute (100, 30);
				document.add(tuxpanL);
			}
			document.add(new Paragraph("\n\n"));
			document.add(new Paragraph(localeColas.getStringKey("camera")+": "+queue.device));
			document.add(new Paragraph(localeColas.getStringKey("lane")+": "+queue.lane));
			document.add(new Paragraph(localeColas.getStringKey("date")+": "+queue.date));
			document.add(new Paragraph(localeColas.getStringKey("waiting_time")+": "+queue.getTime()));
			String noImageFolder = "C:\\Tracevia\\Software\\External\\Unknown\\";
			File img1 = new File("");
			if(img1.exists()) {
				Image imgX = Image.getInstance(img1.getPath());
				imgX.setAbsolutePosition(50, 200);
				imgX.scaleAbsolute (500, 400);
				document.add(imgX);
			}else {
				Image imgX = Image.getInstance(noImageFolder+"no-image.jpg");
				imgX.setAbsolutePosition(100, 280);
				imgX.scaleAbsolute (200, 150);
				document.add(imgX);
			}
			

			document.close();
			
			// DOWNLOAD

			externalContext.setResponseContentType("application/pdf");
			externalContext.setResponseHeader("Content-Disposition","attachment; filename=\"colas_"+queue.date+".pdf\"");

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